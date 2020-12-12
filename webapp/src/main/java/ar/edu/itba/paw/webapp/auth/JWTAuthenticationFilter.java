package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.services.DoctorService;
import ar.edu.itba.paw.interfaces.services.PatientService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.webapp.exceptions.ExceptionResponseWriter;
import ar.edu.itba.paw.webapp.exceptions.MissingAcceptsException;
import ar.edu.itba.paw.webapp.media_types.LoginMIME;
import ar.edu.itba.paw.webapp.media_types.MIMEHelper;
import ar.edu.itba.paw.webapp.media_types.UserMIME;
import ar.edu.itba.paw.webapp.media_types.parsers.serializers.UserMeSerializer;
import ar.edu.itba.paw.webapp.models.UserCredentials;
import ar.edu.itba.paw.webapp.models.UserMe;
import ar.edu.itba.paw.webapp.models.UserMeFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.util.Collection;

@Component
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private UserService userService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private JWTAuthenticator authenticator;

    @Value("classpath:token.key")
    private Resource secretResource;

    public JWTAuthenticationFilter() throws IOException {
        this.setFilterProcessesUrl("/api/login");
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!HttpMethod.POST.equalsIgnoreCase(request.getMethod())) {
            ExceptionResponseWriter.setError(response, Status.METHOD_NOT_ALLOWED);
            return null;
        }
        try {
            MIMEHelper.assertServerType(request);
            MIMEHelper.assertClientType(request, LoginMIME.POST);
        } catch (MissingAcceptsException e) {
            ExceptionResponseWriter.setError(response, Status.NOT_ACCEPTABLE);
            return null;
        }

        UserCredentials credentials;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
        } catch (UnrecognizedPropertyException e) {
            ExceptionResponseWriter.setError(response, Status.BAD_REQUEST);
            return null;
        } catch (IOException e) {
            ExceptionResponseWriter.setError(response, Status.INTERNAL_SERVER_ERROR);
            return null;
        }

        try {
            return this.authenticator.attemptAuthentication(credentials);
        } catch (AuthenticationException e) {
            ExceptionResponseWriter.setError(response, Status.FORBIDDEN, "Invalid username or password");
            return null;
        } catch (Exception e) {
            ExceptionResponseWriter.setError(response, Status.INTERNAL_SERVER_ERROR);
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        ar.edu.itba.paw.models.User user = this.userService.findByUsername(((User) authentication.getPrincipal()).getUsername()).get();
        this.authenticator.createAndRefreshJWT(authentication, user, response);

        UserMe userMe;
        Collection<Doctor> doctors = this.doctorService.findByUser(user);
        if (doctors.size() == 0) {
            userMe = UserMeFactory.withPatients(user, this.patientService.findByUser(user));
        } else {
            userMe = UserMeFactory.withDoctors(user, doctors);
        }

        response.setStatus(Status.OK.getStatusCode());
        response.setHeader(Constants.CONTENT_TYPE, UserMIME.ME);
        response.getWriter().append(UserMeSerializer.instance.toJson(userMe).toString());
    }
}
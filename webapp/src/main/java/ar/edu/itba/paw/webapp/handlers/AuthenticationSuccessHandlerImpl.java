package ar.edu.itba.paw.webapp.handlers;

import ar.edu.itba.paw.webapp.auth.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String targetUrl = this.getTargetUrl(authentication, httpServletRequest);
        if (!httpServletResponse.isCommitted()) {
            this.redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, targetUrl);
        }
    }

    private String getTargetUrl(Authentication authentication, HttpServletRequest request) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isPatient = false;
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_" + UserRole.UNVERIFIED.name())) {
                String query = request.getQueryString();
                return "/verifyEmail" + ((query == null) ? "" : ("?" + query));
            }
            if (authority.getAuthority().equals("ROLE_" + UserRole.STAFF.name())) {
                return "/staff/home";
            } else if (authority.getAuthority().equals("ROLE_" + UserRole.PATIENT.name())) {
                isPatient = true;
            }
        }
        if (isPatient)
            return "/patient/home";
        return "/";
    }
}

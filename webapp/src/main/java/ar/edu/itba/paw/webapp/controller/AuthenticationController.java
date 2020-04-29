package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.CountryService;
import ar.edu.itba.paw.interfaces.services.LocalityService;
import ar.edu.itba.paw.interfaces.services.ProvinceService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.services.exceptions.EmailAlreadyExistsException;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.controller.utils.GenericController;
import ar.edu.itba.paw.webapp.controller.utils.JsonResponse;
import ar.edu.itba.paw.webapp.form.authentication.PatientSignUpForm;
import ar.edu.itba.paw.webapp.form.authentication.StaffSignUpForm;
import ar.edu.itba.paw.webapp.form.authentication.UserLoginForm;
import ar.edu.itba.paw.webapp.transformer.LocalityTransformer;
import ar.edu.itba.paw.webapp.transformer.ProvinceTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AuthenticationController extends GenericController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private LocalityService localityService;
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private ProvinceTransformer provinceTransformer;
    @Autowired
    private LocalityTransformer localityTransformer;

    @RequestMapping(value = "/signup/staff/provinces/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JsonResponse getProvinces(@PathVariable String id) {
        return this.formatJsonResponse(() -> {
            Country country = new Country();
            country.setId(id);
            Collection<Province> provinces = this.provinceService.findByCountry(country);
            return this.provinceTransformer.transform(provinces);
        });
    }

    @RequestMapping(value = "/signup/staff/localities/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JsonResponse getLocalities(@PathVariable Integer id) {
        return this.formatJsonResponse(() -> {
            Province province = new Province();
            province.setId(id);
            Collection<Locality> localities = this.localityService.findByProvince(province);
            return this.localityTransformer.transform(localities);
        });
    }

    @RequestMapping(value = "/signup/staff", method = RequestMethod.POST)
    public ModelAndView signupStaffAction(@Valid @ModelAttribute("signupForm") final StaffSignUpForm form, final BindingResult errors, HttpServletRequest request, HttpServletResponse response) {
        if (errors.hasErrors()) {
            return this.signupStaffIndex(form);
        }
        if (!form.getPassword().equals(form.getRepeatPassword())) {
            errors.reject("Equals.signupForm.repeatPassword", null, "Error");
            return this.signupStaffIndex(form);
        }
        Optional<Locality> locality = this.localityService.findById(form.getLocalityId());
        if (!locality.isPresent()) {
            errors.reject("NotNull.signupForm.localityId", null, "Error");
            return this.signupStaffIndex(form);
        }

        User newUser = form.getAsUser();

        Office office = new Office();
        office.setEmail(newUser.getEmail());
        office.setName("Consultorio de " + newUser.getFirstName() + " " + newUser.getSurname());
        office.setLocality(locality.get());
        office.setStreet(form.getAddress());
        try {
            this.userService.create(newUser, office);
        } catch (EmailAlreadyExistsException e) {
            errors.reject("EmailAlreadyTaken.signupForm.email", null, "Error");
            return this.signupStaffIndex(form);
        }
        this.authenticateSignedUpUser(newUser, form.getPassword(), request);
        return new ModelAndView("redirect:/staff/home");
    }

    @RequestMapping(value = "/signup/patient", method = RequestMethod.POST)
    public ModelAndView signupPatientAction(@Valid @ModelAttribute("signupForm") final PatientSignUpForm form, final BindingResult errors, HttpServletRequest request, HttpServletResponse response) {
        if (errors.hasErrors()) {
            return this.signupPatientIndex(form);
        }
        if (!form.getPassword().equals(form.getRepeatPassword())) {
            errors.reject("Equals.signupForm.repeatPassword", null, "Error");
            return this.signupPatientIndex(form);
        }

        User newUser;
        try {
            newUser = this.userService.create(form.getAsUser());
        } catch (EmailAlreadyExistsException e) {
            errors.reject("EmailAlreadyTaken.signupForm.email", null, "Error");
            return this.signupPatientIndex(form);
        }

        this.authenticateSignedUpUser(newUser, form.getPassword(), request);
        return new ModelAndView("redirect:/patient/home");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginIndex(@ModelAttribute("loginForm") final UserLoginForm form) {
        return new ModelAndView("authentication/login");
    }

    @RequestMapping(value = "/signup/staff", method = RequestMethod.GET)
    public ModelAndView signupStaffIndex(@ModelAttribute("signupForm") final StaffSignUpForm form) {
        Map<String, String> countryMap = this.countryService.list().stream().collect(Collectors.toMap(Country::getId, Country::getName));
        ModelAndView modelAndView = new ModelAndView("authentication/signupStaff");
        modelAndView.addObject("countryMap", countryMap);
        return modelAndView;
    }

    @RequestMapping(value = "/signup/patient", method = RequestMethod.GET)
    public ModelAndView signupPatientIndex(@ModelAttribute("signupForm") final PatientSignUpForm form) {
        return new ModelAndView("authentication/signupPatient");
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signupIndex() {
        return new ModelAndView("authentication/signup");
    }

    private void authenticateSignedUpUser(User user, String password, HttpServletRequest request) {
        try {
            String username = user.getEmail();
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            // generate session if one doesn't exist
            request.getSession();

            Authentication authenticatedUser = this.authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

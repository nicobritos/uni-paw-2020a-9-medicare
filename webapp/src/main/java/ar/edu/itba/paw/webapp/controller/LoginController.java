package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.MediCareException;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.controller.utils.GenericController;
import ar.edu.itba.paw.webapp.controller.utils.JsonResponse;
import ar.edu.itba.paw.webapp.form.UserLoginForm;
import ar.edu.itba.paw.webapp.form.UserSignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class LoginController extends GenericController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JsonResponse signupAction(@Valid @RequestBody final UserSignUpForm form, final BindingResult errors) {
        return this.formatJsonResponse(() -> {
            if (errors.hasErrors()) {
                throw new MediCareException(this.getErrorMessages(errors.getAllErrors()));
            }

            User newUser = this.userService.create(form.getAsUser());
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("id", newUser.getId());
            return parameters;
        });
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JsonResponse loginAction(@Valid @RequestBody final UserLoginForm form, final BindingResult errors) {
        return this.formatJsonResponse(() -> {
            if (errors.hasErrors()) {
                throw new MediCareException(this.getErrorMessages(errors.getAllErrors()));
            }

            Optional<User> user = this.userService.login(form.getEmail(), form.getPassword());
            if (!user.isPresent()) {
                throw new MediCareException("Credenciales invalidas");
            }

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("id", user.get().getId());
            return parameters;
        });
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginIndex() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signupIndex() {
        return new ModelAndView("register");
    }
}

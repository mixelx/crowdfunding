package com.course.startItProject.controller;

import com.course.startItProject.entity.ConfirmationToken;
import com.course.startItProject.entity.User;
import com.course.startItProject.service.impl.ConfirmationTokenServiceImpl;
import com.course.startItProject.service.impl.UserServiceImpl;
import com.course.startItProject.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AuthController {
    @Autowired
    private UserValidator userValidator;

    @Autowired
    private ConfirmationTokenServiceImpl confirmationTokenServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping("/registration")
    public String RegistrationController(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid @ModelAttribute User user, BindingResult result) {
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            return "registration";
        }
        userServiceImpl.saveUser(user);
        return "/login";
    }

    @RequestMapping(value = "/confirm-account", method = RequestMethod.GET)
    public String confirmUserAccount(@RequestParam("token") String confirmationToken) {
        ConfirmationToken token = confirmationTokenServiceImpl.findByConfirmationToken(confirmationToken);
        return confirmationTokenServiceImpl.confirmAccount(token, userServiceImpl);
    }
}

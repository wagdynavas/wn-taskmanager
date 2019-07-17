package com.wagdynavas.wntaskmanager.controllers;

import com.wagdynavas.wntaskmanager.models.User;
import com.wagdynavas.wntaskmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginn() {
        return "account/login";
    }

    @GetMapping("/registration")
    public ModelAndView registration() {
        ModelAndView view = new ModelAndView();
        view.setViewName("account/register");
        view.addObject("user", new User());
        return view;
    }

    @PostMapping("/registration")
    public ModelAndView registration(@Valid User user, BindingResult result) {
        return userService.createUser(user, result);
    }

}

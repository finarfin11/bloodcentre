package com.softuni.bloodcentre.web.controllers;

import com.softuni.bloodcentre.web.Anntotations.PageTitle;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/home")
    @PageTitle("Home")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView getRegisterForm(ModelAndView modelAndView) {
        modelAndView.setViewName("home/home.html");
        return modelAndView;
    }
}

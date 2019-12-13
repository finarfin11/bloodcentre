package com.softuni.bloodcentre.web.controllers;

import com.softuni.bloodcentre.service.models.LoginUserServiceModel;
import com.softuni.bloodcentre.service.services.AuthService;
import com.softuni.bloodcentre.web.Anntotations.PageTitle;
import com.softuni.bloodcentre.web.models.LoginUserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
    @PageTitle("Login")
    public String getLoginForm() {
        return "auth/login.html";
    }

//    @PostMapping("/")
//    public String login(@ModelAttribute LoginUserModel model, HttpSession session) {
//            LoginUserServiceModel serviceModel = this.authService.login(model);
//            session.setAttribute("user", serviceModel);
//            return "redirect:/users/table";
//    }

    @ExceptionHandler(NoResultException.class)
    public ModelAndView defaultErrorHandler(NoResultException exception, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", exception.getMessage());
        mav.addObject("url", request.getRequestURL());
        return mav;
    }
}

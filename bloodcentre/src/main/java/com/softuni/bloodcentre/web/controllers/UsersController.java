package com.softuni.bloodcentre.web.controllers;

import com.softuni.bloodcentre.service.services.DepartmentService;
import com.softuni.bloodcentre.service.services.UserService;
import com.softuni.bloodcentre.web.models.DeleteUserModel;
import com.softuni.bloodcentre.web.models.EditUserModel;
import com.softuni.bloodcentre.web.models.RegisterUserModel;
import com.softuni.bloodcentre.web.models.ViewUserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;
    private final DepartmentService departmentService;
    private final ModelMapper modelMapper;

    @Autowired
    public UsersController(UserService userService, DepartmentService departmentService, ModelMapper modelMapper) {
        this.userService = userService;
        this.departmentService = departmentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/table")
    public ModelAndView getUsersTable(ModelAndView modelAndView) {
        List<ViewUserModel> viewUserModels = this.userService.findAllUsers().stream()
                .map(userServiceModel -> this.modelMapper.map(userServiceModel, ViewUserModel.class)).collect(Collectors.toList());
        modelAndView.setViewName("users/users-table.html");
        modelAndView.addObject("users" ,viewUserModels);
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView getRegisterForm(@ModelAttribute("registerModel") RegisterUserModel registerUserModel, ModelAndView modelAndView) {
        List<String> departments = getDepartmentNames();
        modelAndView.addObject("departmentNames", departments);
        modelAndView.setViewName("users/register.html");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute("registerModel") RegisterUserModel model, BindingResult bindingResult) {
        ModelAndView mav;
        if(bindingResult.hasErrors()) {
            mav = new ModelAndView("users/register.html");
            List<String> departments = getDepartmentNames();
            mav.addObject("departmentNames", departments);
            return mav;
        }

        this.userService.addUser(model);
        mav = new ModelAndView("redirect:/users/table");
        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditForm(ModelAndView modelAndView, @PathVariable("id") long id) {
        EditUserModel editUserModel = this.modelMapper
                .map(this.userService.findById(id), EditUserModel.class);
        List<String> departments = getDepartmentNames();
        modelAndView.addObject("departmentNames", departments);
        modelAndView.setViewName("users/edit.html");
        modelAndView.addObject("user", editUserModel);
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute EditUserModel model, @PathVariable("id") long id) {
        this.userService.editUser(model, id);
        return "redirect:/users/table";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView getDeleteForm(ModelAndView modelAndView, @PathVariable("id") long id) {
        DeleteUserModel deleteUserModel = this.modelMapper
                .map(this.userService.findById(id),DeleteUserModel.class);
        List<String> departments = getDepartmentNames();
        modelAndView.addObject("departmentNames", departments);
        modelAndView.setViewName("users/delete.html");
        modelAndView.addObject("user", deleteUserModel);
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        this.userService.deleteUser(id);
        return "redirect:/users/table";
    }


    @ExceptionHandler(NoResultException.class)
    public ModelAndView defaultErrorHandler(NoResultException exception, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", exception.getMessage());
        mav.addObject("url", request.getRequestURL());
        return mav;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ModelAndView defaultErrorHandler(DuplicateKeyException exception, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", exception.getMessage());
        mav.addObject("url", request.getRequestURL());
        return mav;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView defaultErrorHandler(IllegalArgumentException exception, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", exception.getMessage());
        mav.addObject("url", request.getRequestURL());
        return mav;
    }

    private List<String> getDepartmentNames() {
        return this.departmentService.findAllDepartments()
                .stream().map(d -> d.getName()).collect(Collectors.toList());
    }

    @ModelAttribute("registerModel")
    public RegisterUserModel registerModel() {
        return new RegisterUserModel();
    }

    @ModelAttribute("editModel")
    public EditUserModel editModel() {
        return new EditUserModel();
    }
}

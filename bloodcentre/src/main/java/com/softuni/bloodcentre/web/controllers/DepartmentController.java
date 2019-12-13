package com.softuni.bloodcentre.web.controllers;

import com.softuni.bloodcentre.service.models.RegisterDepartmentServiceModel;
import com.softuni.bloodcentre.service.services.DepartmentService;
import com.softuni.bloodcentre.web.Anntotations.PageTitle;
import com.softuni.bloodcentre.web.models.EditDepartmentModel;
import com.softuni.bloodcentre.web.models.RegisterDepartmentModel;
import com.softuni.bloodcentre.web.models.ViewDepartmentModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final ModelMapper modelMapper;

    @Autowired
    public DepartmentController(DepartmentService departmentService, ModelMapper modelMapper) {
        this.departmentService = departmentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/table")
    @PageTitle("Departments")
    @PreAuthorize("hasAnyAuthority('Administration')")
    public ModelAndView getDepartmentsTable(ModelAndView modelAndView) {
        List<ViewDepartmentModel> viewDepartmentModels = this.departmentService.findAllDepartments()
                .stream().map(d -> this.modelMapper.map(d, ViewDepartmentModel.class))
                .collect(Collectors.toList());
        modelAndView.setViewName("/departments/departments-table.html");
        modelAndView.addObject("departments", viewDepartmentModels);
        return modelAndView;
    }

    @GetMapping("/register")
    @PageTitle("Register Department")
    @PreAuthorize("hasAnyAuthority('Administration')")
    public String getRegisterForm(@ModelAttribute("registerModel") RegisterDepartmentModel model) {
        return "/departments/register.html";
    }

    @PostMapping("/register")
    @PreAuthorize("hasAnyAuthority('Administration')")
    public String register(@Valid @ModelAttribute("registerModel") RegisterDepartmentModel model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "departments/register.html";
        }

        this.departmentService.addDepartment(this.modelMapper.map(model, RegisterDepartmentServiceModel.class));
        return "redirect:/departments/table";
    }

    @GetMapping("/edit/{id}")
    @PageTitle("Edit Department")
    @PreAuthorize("hasAnyAuthority('Administration')")
    public ModelAndView getEditForm(@ModelAttribute("editModel") EditDepartmentModel model, ModelAndView modelAndView, @PathVariable("id") long id) {
        EditDepartmentModel editDepartmentModel = this.departmentService.getEditDepartmentModel(id);
        modelAndView.addObject("department", editDepartmentModel);
        modelAndView.addObject("id", id);
        modelAndView.setViewName("departments/edit.html");
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('Administration')")
    public ModelAndView edit(@Valid @ModelAttribute("editModel") EditDepartmentModel model, BindingResult bindingResult, @PathVariable("id") long id){
        ModelAndView mav;
        if(bindingResult.hasErrors()) {
            mav = new ModelAndView("departments/edit.html");
            mav.addObject("id", id);
            mav.addObject("department", model);
            return mav;
        }

        mav = new ModelAndView("redirect:/departments/table");
        this.departmentService.editDepartment(model, id);
        return mav;
    }

    @GetMapping("/delete/{id}")
    @PageTitle("Delete Department")
    @PreAuthorize("hasAnyAuthority('Administration')")
    public ModelAndView getDeleteForm(ModelAndView modelAndView, @PathVariable("id") long id) {
        EditDepartmentModel editDepartmentModel = this.departmentService.getEditDepartmentModel(id);
        modelAndView.addObject("department", editDepartmentModel);
        modelAndView.setViewName("departments/delete.html");
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('Administration')")
    public String delete(@PathVariable("id") long id){
        this.departmentService.deleteDepartment(id);
        return "redirect:/departments/table";
    }

    @ExceptionHandler(NoResultException.class)
    public ModelAndView defaultErrorHandler(NoResultException exception, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", exception.getMessage());
        mav.addObject("url", request.getRequestURL());
        return mav;
    }

    @ModelAttribute("registerModel")
    public RegisterDepartmentModel registerModel() {
        return new RegisterDepartmentModel();
    }

    @ModelAttribute("editModel")
    public EditDepartmentModel editModel() {
        return new EditDepartmentModel();
    }
}

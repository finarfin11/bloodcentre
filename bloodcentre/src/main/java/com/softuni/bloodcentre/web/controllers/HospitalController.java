package com.softuni.bloodcentre.web.controllers;

import com.softuni.bloodcentre.service.services.HospitalService;
import com.softuni.bloodcentre.web.models.EditHospitalModel;
import com.softuni.bloodcentre.web.models.RegisterHospitalModel;
import com.softuni.bloodcentre.web.models.ViewHospitalModel;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/hospitals")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping("/register")
    public String getRegisterForm(@ModelAttribute("registerModel") RegisterHospitalModel registerHospitalModel) {
        return "hospitals/register.html";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerModel") RegisterHospitalModel registerHospitalModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "hospitals/register.html";
        }

        this.hospitalService.addHospital(registerHospitalModel);
        return "redirect:/hospitals/table";
    }

    @GetMapping("/table")
    public ModelAndView getBloodProductsTable(ModelAndView modelAndView) {
        List<ViewHospitalModel> viewHospitalModelList = this.hospitalService.findAllHospitals();
        modelAndView.setViewName("hospitals/table.html");
        modelAndView.addObject("hospitals", viewHospitalModelList);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditForm(@ModelAttribute("editModel") EditHospitalModel model, ModelAndView modelAndView, @PathVariable("id") long id) {
        EditHospitalModel editHospitalModel = this.hospitalService.getEditHospitalModel(id);
        modelAndView.addObject("hospital", editHospitalModel);
        modelAndView.setViewName("hospitals/edit.html");
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView edit(@Valid @ModelAttribute("editModel") EditHospitalModel model, BindingResult bindingResult, @PathVariable("id") long id){
        ModelAndView mav;
        if(bindingResult.hasErrors()) {
            mav = new ModelAndView("hospitals/edit.html");
            mav.addObject("id", id);
            mav.addObject("hospital", model);
            return mav;
        }

        mav = new ModelAndView("redirect:/hospitals/table");
        this.hospitalService.editHospital(model, id);
        return mav;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView getDeleteForm(ModelAndView modelAndView, @PathVariable("id") long id) {
        EditHospitalModel editHospitalModel = this.hospitalService.getEditHospitalModel(id);
        modelAndView.addObject("hospital", editHospitalModel);
        modelAndView.setViewName("hospitals/delete.html");
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id){
        this.hospitalService.deleteHospital(id);
        return "redirect:/hospitals/table";
    }

    @ExceptionHandler(NoResultException.class)
    public ModelAndView defaultErrorHandler(NoResultException exception, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", exception.getMessage());
        mav.addObject("url", request.getRequestURL());
        return mav;
    }

    @ModelAttribute("registerModel")
    public RegisterHospitalModel registerModel() {
        return new RegisterHospitalModel();
    }

    @ModelAttribute("editModel")
    public EditHospitalModel editModel() {
        return new EditHospitalModel();
    }
}

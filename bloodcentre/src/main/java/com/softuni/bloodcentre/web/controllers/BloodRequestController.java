package com.softuni.bloodcentre.web.controllers;

import com.softuni.bloodcentre.service.services.BloodProductTypeService;
import com.softuni.bloodcentre.service.services.BloodRequestService;
import com.softuni.bloodcentre.service.services.RequestStateService;
import com.softuni.bloodcentre.web.models.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/requests")
public class BloodRequestController {

    private final BloodProductTypeService bloodProductTypeService;
    private final BloodRequestService bloodRequestService;
    private final RequestStateService requestStateService;

    public BloodRequestController(BloodProductTypeService bloodProductTypeService, BloodRequestService bloodRequestService, RequestStateService requestStateService) {
        this.bloodProductTypeService = bloodProductTypeService;
        this.bloodRequestService = bloodRequestService;
        this.requestStateService = requestStateService;
    }

    @GetMapping("/register")
    public ModelAndView getRegisterForm(@ModelAttribute("registerModel") RegisterBloodRequestModel model, ModelAndView modelAndView) {
        List<String> bloodProductTypeList = this.bloodProductTypeService.getBloodProductTypes();
        modelAndView.addObject("productTypes", bloodProductTypeList);
        modelAndView.setViewName("blood_requests/register.html");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute("registerModel") RegisterBloodRequestModel registerBloodRequestModel, BindingResult bindingResult) {
        ModelAndView mav;
        if (bindingResult.hasErrors()) {
            mav = new ModelAndView("blood_requests/register.html");
            mav.addObject("productTypes", this.bloodProductTypeService.getBloodProductTypes());
            return mav;
        }

        mav = new ModelAndView("redirect:/requests/table");
        this.bloodRequestService.addBloodRequest(registerBloodRequestModel);
        return mav;
    }

    @GetMapping("/table")
    public ModelAndView getBloodProductsTable(ModelAndView modelAndView) {
        List<ViewRequestModel> viewRequestModels = this.bloodRequestService.findAllRequests();
        modelAndView.setViewName("blood_requests/table.html");
        modelAndView.addObject("requests", viewRequestModels);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditForm(ModelAndView modelAndView, @PathVariable("id") long id) {
        EditRequestModel editRequestModel = this.bloodRequestService.getEditRequestModel(id);
        List<String> bloodProductTypes = this.bloodProductTypeService.getBloodProductTypes();
        List<String> requestStates = this.requestStateService.getBloodRequestStates();
        modelAndView.addObject("productTypes", bloodProductTypes);
        modelAndView.addObject("requestStates", requestStates);
        modelAndView.addObject("request", editRequestModel);
        modelAndView.setViewName("blood_requests/edit.html");
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute EditRequestPostModel model, @PathVariable("id") long id){
        this.bloodRequestService.editRequest(model, id);
        return "redirect:/requests/table";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView getDeleteForm(ModelAndView modelAndView, @PathVariable("id") long id) {
        EditRequestModel editRequestModel = this.bloodRequestService.getEditRequestModel(id);
        modelAndView.addObject("request", editRequestModel);
        modelAndView.setViewName("blood_requests/delete.html");
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id){
        this.bloodRequestService.deleteRequest(id);
        return "redirect:/requests/table";
    }

    @ExceptionHandler(NoResultException.class)
    public ModelAndView defaultErrorHandler(NoResultException exception, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", exception.getMessage());
        mav.addObject("url", request.getRequestURL());
        return mav;
    }

    @ModelAttribute("registerModel")
    public RegisterBloodRequestModel registerModel() {
        return new RegisterBloodRequestModel();
    }

    @ModelAttribute("editModel")
    public EditRequestPostModel editModel() {
        return new EditRequestPostModel();
    }
}

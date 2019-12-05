package com.softuni.bloodcentre.web.controllers;

import com.softuni.bloodcentre.data.models.BloodSampleStatus;
import com.softuni.bloodcentre.data.models.BloodType;
import com.softuni.bloodcentre.service.services.BloodSampleService;
import com.softuni.bloodcentre.service.services.BloodSampleStatusService;
import com.softuni.bloodcentre.service.services.BloodTypeService;
import com.softuni.bloodcentre.web.models.DeleteBloodSampleModel;
import com.softuni.bloodcentre.web.models.EditBloodSampleModel;
import com.softuni.bloodcentre.web.models.RegisterBloodSampleModel;
import com.softuni.bloodcentre.web.models.ViewBloodSampleModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/bloodSamples")
public class BloodSampleController {

    private final BloodSampleService bloodSampleService;
    private final BloodTypeService bloodTypeService;
    private final BloodSampleStatusService bloodSampleStatusService;
    private final ModelMapper modelMapper;

    @Autowired
    public BloodSampleController(BloodSampleService bloodSampleService, BloodTypeService bloodTypeService, BloodSampleStatusService bloodSampleStatusService, ModelMapper modelMapper) {
        this.bloodSampleService = bloodSampleService;
        this.bloodTypeService = bloodTypeService;
        this.bloodSampleStatusService = bloodSampleStatusService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/table/{id}")
    public ModelAndView getBloodSamplesTable(@PathVariable("id") long id, ModelAndView modelAndView) {
        List<ViewBloodSampleModel> bloodSampleModels = this.bloodSampleService.findAllSamples(id);
        modelAndView.addObject("bloodSampleModels", bloodSampleModels);
        modelAndView.addObject("id", id);
        modelAndView.setViewName("blood_samples/samples-table.html");
        return modelAndView;
    }

    @GetMapping("/register/{id}")
    public ModelAndView getRegisterForm(@ModelAttribute("registerModel") RegisterBloodSampleModel registerBloodSampleModel, ModelAndView modelAndView, @PathVariable("id") long id) {
        modelAndView.addObject("id", id);
        List<BloodType> bloodTypes = this.bloodTypeService.getBloodTypes();
        modelAndView.addObject(bloodTypes);
        List<BloodSampleStatus> statusList = this.bloodSampleStatusService.getBloodStatusList();
        modelAndView.addObject(statusList);
        modelAndView.setViewName("blood_samples/register.html");
        return modelAndView;
    }

    @PostMapping("/register/{id}")
    public ModelAndView register(@Valid @ModelAttribute("registerModel") RegisterBloodSampleModel registerBloodSampleModel, BindingResult bindingResult, @PathVariable("id") long id) {
        ModelAndView mav;
        if(bindingResult.hasErrors()) {
            mav = new ModelAndView("blood_samples/register.html");
            mav.addObject(this.bloodTypeService.getBloodTypes());
            mav.addObject(this.bloodSampleStatusService.getBloodStatusList());
            mav.addObject("id", id);
            return mav;
        }

        mav = new ModelAndView("redirect:/bloodSamples/table/{id}");
        this.bloodSampleService.addBloodSample(registerBloodSampleModel, id);
        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditForm(@ModelAttribute("editModel") EditBloodSampleModel model, ModelAndView modelAndView, @PathVariable("id") long id) {
        EditBloodSampleModel editBloodSampleModel = this.modelMapper
                .map(this.bloodSampleService.findById(id), EditBloodSampleModel.class);
        List<BloodType> bloodTypes = this.bloodTypeService.getBloodTypes();
        modelAndView.addObject(bloodTypes);
        List<BloodSampleStatus> statusList = this.bloodSampleStatusService.getBloodStatusList();
        modelAndView.addObject(statusList);
        modelAndView.setViewName("blood_samples/edit.html");
        modelAndView.addObject("bloodSample", editBloodSampleModel);
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView edit(@Valid @ModelAttribute("editModel") EditBloodSampleModel model, BindingResult bindingResult, @PathVariable("id") long id) {
        ModelAndView mav;
        if (bindingResult.hasErrors()) {
            mav = new ModelAndView("blood_samples/edit.html");
            mav.addObject(this.bloodTypeService.getBloodTypes());
            mav.addObject(this.bloodSampleStatusService.getBloodStatusList());
            mav.addObject("bloodSample", model);
            return mav;
        }

        mav = new ModelAndView("redirect:/donations/table");
        this.bloodSampleService.editBloodSample(model, id);
        return mav;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView getDeleteForm(ModelAndView modelAndView, @PathVariable("id") long id) {
        DeleteBloodSampleModel deleteBloodSampleModel = this.modelMapper
                .map(this.bloodSampleService.findById(id), DeleteBloodSampleModel.class);
        modelAndView.setViewName("blood_samples/delete.html");
        modelAndView.addObject("bloodSample", deleteBloodSampleModel);
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        this.bloodSampleService.deleteBloodSample(id);
        return "redirect:/donations/table";
    }

    @ExceptionHandler(NoResultException.class)
    public ModelAndView defaultErrorHandler(NoResultException exception, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", exception.getMessage());
        mav.addObject("url", request.getRequestURL());
        return mav;
    }

    @ModelAttribute("registerModel")
    public RegisterBloodSampleModel registerModel() {
        return new RegisterBloodSampleModel();
    }

    @ModelAttribute("editModel")
    public EditBloodSampleModel editModel() {
        return new EditBloodSampleModel();
    }
}

package com.softuni.bloodcentre.web.controllers;

import com.softuni.bloodcentre.data.models.Sex;
import com.softuni.bloodcentre.service.services.DonorService;
import com.softuni.bloodcentre.service.services.GenderService;
import com.softuni.bloodcentre.web.Anntotations.PageTitle;
import com.softuni.bloodcentre.web.models.DeleteDonorModel;
import com.softuni.bloodcentre.web.models.EditDonorModel;
import com.softuni.bloodcentre.web.models.RegisterDonorModel;
import com.softuni.bloodcentre.web.models.ViewDonorModel;
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

@Controller
@RequestMapping("/donors")
public class DonorController {
    private final DonorService donorService;
    private final ModelMapper modelMapper;
    private final GenderService genderService;

    @Autowired
    public DonorController(DonorService donorService, ModelMapper modelMapper, GenderService genderService) {
        this.donorService = donorService;
        this.modelMapper = modelMapper;
        this.genderService = genderService;
    }

    @GetMapping("/register")
    @PageTitle("Register Donor")
    @PreAuthorize("hasAnyAuthority('Donations', 'Administration')")
    public ModelAndView getRegisterForm(@ModelAttribute("registerModel") RegisterDonorModel model, ModelAndView modelAndView) {
        List<Sex> sexList = this.genderService.getSexList();
        modelAndView.addObject(sexList);
        modelAndView.setViewName("donors/register.html");
        return modelAndView;
    }

    @PostMapping("/register")
    @PreAuthorize("hasAnyAuthority('Donations', 'Administration')")
    public String register(@Valid @ModelAttribute("registerModel") RegisterDonorModel registerDonorModel, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "donors/register.html";
        }

        this.donorService.addDonor(registerDonorModel);
        return "redirect:/donors/table";
    }

    @GetMapping("/table")
    @PageTitle("Registered Donors")
    @PreAuthorize("hasAnyAuthority('Donations', 'Administration')")
    public ModelAndView getDonorsTable(ModelAndView modelAndView) {
        List<ViewDonorModel> viewDonorModels = this.donorService.findAllDonors();
        modelAndView.addObject("donors", viewDonorModels);
        modelAndView.setViewName("donors/donors-table.html");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    @PageTitle("Edit Donor")
    @PreAuthorize("hasAnyAuthority('Donations', 'Administration')")
    public ModelAndView getEditForm(@ModelAttribute("editModel") EditDonorModel model, ModelAndView modelAndView, @PathVariable("id") long id) {
        EditDonorModel editDonorModel = this.modelMapper
                .map(this.donorService.findById(id), EditDonorModel.class);
        List<Sex> sexList = this.genderService.getSexList();
        modelAndView.addObject(sexList);
        modelAndView.setViewName("donors/edit.html");
        modelAndView.addObject("id", id);
        modelAndView.addObject("donor", editDonorModel);
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('Donations', 'Administration')")
    public ModelAndView edit(@Valid @ModelAttribute("editModel") EditDonorModel model, BindingResult bindingResult, @PathVariable("id") long id) {
        ModelAndView mav;
        if(bindingResult.hasErrors()) {
            mav = new ModelAndView("donors/edit.html");
            mav.addObject("id", id);
            mav.addObject("donor", model);
            return mav;
        }

        this.donorService.editDonor(model, id);
        mav = new ModelAndView("redirect:/donors/table");
        return mav;
    }

    @GetMapping("/delete/{id}")
    @PageTitle("Delete Donor")
    @PreAuthorize("hasAnyAuthority('Donations', 'Administration')")
    public ModelAndView getDeleteForm(ModelAndView modelAndView, @PathVariable("id") long id) {
        DeleteDonorModel deleteDonorModel = this.modelMapper
                .map(this.donorService.findById(id),DeleteDonorModel.class);
        modelAndView.setViewName("donors/delete.html");
        modelAndView.addObject("donor", deleteDonorModel);
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('Donations', 'Administration')")
    public String delete(@PathVariable("id") long id) {
        this.donorService.deleteDonor(id);
        return "redirect:/donors/table";
    }

    @ExceptionHandler(NoResultException.class)
    public ModelAndView defaultErrorHandler(NoResultException exception, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", exception.getMessage());
        mav.addObject("url", request.getRequestURL());
        return mav;
    }

    @ModelAttribute("registerModel")
    public RegisterDonorModel registerModel() {
        return new RegisterDonorModel();
    }

    @ModelAttribute("editModel")
    public EditDonorModel editModel() {
        return new EditDonorModel();
    }
}

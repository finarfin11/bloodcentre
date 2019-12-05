package com.softuni.bloodcentre.web.controllers;

import com.softuni.bloodcentre.data.models.BloodProcessState;
import com.softuni.bloodcentre.service.services.BloodDonationService;
import com.softuni.bloodcentre.service.services.BloodProcessStateService;
import com.softuni.bloodcentre.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.InvalidNameException;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/donations")
public class BloodDonationController {
    private final BloodDonationService bloodDonationService;
    private final BloodDonationService donationService;
    private final BloodProcessStateService bloodProcessStateService;

    @Autowired
    public BloodDonationController(BloodDonationService bloodDonationService, BloodDonationService donationService, BloodProcessStateService bloodProcessStateService) {
        this.bloodDonationService = bloodDonationService;
        this.donationService = donationService;
        this.bloodProcessStateService = bloodProcessStateService;
    }

    @GetMapping("/register")
    public ModelAndView getRegisterForm(@ModelAttribute("registerModel") RegisterDonationModel registerDonationModel, ModelAndView modelAndView) {
        modelAndView.setViewName("donations/register.html");
        return modelAndView;
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerModel") RegisterDonationModel registerDonationModel, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "donations/register.html";
        }

        this.bloodDonationService.addDonation(registerDonationModel);
        return "redirect:/donations/table";
    }

    @GetMapping("/table")
    public ModelAndView getDonationsTable(ModelAndView modelAndView) {
        List<ViewDonationModel> donations = this.bloodDonationService.findAllDonations();
        modelAndView.addObject("donations", donations);
        modelAndView.setViewName("donations/donations-table.html");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditForm(@ModelAttribute("editModel") EditDonationModel model, ModelAndView modelAndView, @PathVariable("id") long id) {
        EditDonationModel editDonationModel = this.donationService.getEditDonationViewModel(id);
        modelAndView.setViewName("donations/edit.html");
        modelAndView.addObject("id", id);
        modelAndView.addObject("donation", editDonationModel);
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView edit(@Valid @ModelAttribute("editModel") EditDonationModel model, BindingResult bindingResult, @PathVariable("id") long id) {
        ModelAndView mav;
        if(bindingResult.hasErrors()) {
            mav = new ModelAndView("donations/edit.html");
            mav.addObject("id", id);
            mav.addObject("donation", model);
            return mav;
        }

        this.donationService.editDonation(model, id);
        mav = new ModelAndView("redirect:/donations/table");
        return mav;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView getDeleteForm(ModelAndView modelAndView, @PathVariable("id") long id) {
        EditDonationModel deleteDonationModel = this.bloodDonationService.getEditDonationViewModel(id);
        modelAndView.setViewName("donations/delete.html");
        modelAndView.addObject("donation", deleteDonationModel);
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        this.donationService.deleteDonation(id);
        return "redirect:/donations/table";
    }

    @GetMapping("/state/{id}")
    public ModelAndView getBloodStateForm(ModelAndView modelAndView, @PathVariable("id") long id) {
        EditDonationModel editDonationModel = this.donationService.getEditDonationViewModel(id);
        List<BloodProcessState> bloodProcessStates = this.bloodProcessStateService.getBloodStates();
        modelAndView.addObject("states", bloodProcessStates);
        modelAndView.setViewName("donations/set-state.html");
        modelAndView.addObject("donation", editDonationModel);
        return modelAndView;
    }

    @PostMapping("/state/{id}")
    public String setBloodState(@ModelAttribute SetBloodStateModel model, @PathVariable("id") long id) {
        this.donationService.setBloodState(model, id);
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
    public RegisterDonationModel registerModel() {
        return new RegisterDonationModel();
    }

    @ModelAttribute("editModel")
    public EditDonationModel editModel() {
        return new EditDonationModel();
    }
}

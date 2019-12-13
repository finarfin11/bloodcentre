package com.softuni.bloodcentre.web.controllers;

import com.softuni.bloodcentre.service.services.BloodProductService;
import com.softuni.bloodcentre.service.services.BloodProductTypeService;
import com.softuni.bloodcentre.service.services.ProductAvailabilityService;
import com.softuni.bloodcentre.web.Anntotations.PageTitle;
import com.softuni.bloodcentre.web.models.EditBloodProductModel;
import com.softuni.bloodcentre.web.models.EditProductModel;
import com.softuni.bloodcentre.web.models.RegisterBloodProductModel;
import com.softuni.bloodcentre.web.models.ViewProductModel;
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
@RequestMapping("/products")
public class BloodProductController {
    private final BloodProductTypeService bloodProductTypeService;
    private final BloodProductService bloodProductService;
    private final ProductAvailabilityService productAvailabilityService;

    @Autowired
    public BloodProductController(BloodProductTypeService bloodProductTypeService, BloodProductService bloodProductService, ProductAvailabilityService productAvailabilityService) {
        this.bloodProductTypeService = bloodProductTypeService;
        this.bloodProductService = bloodProductService;
        this.productAvailabilityService = productAvailabilityService;
    }


    @GetMapping("/register")
    @PageTitle("Register Blood Product")
    @PreAuthorize("hasAnyAuthority('Administration', 'Processing')")
    public ModelAndView getRegisterForm(@ModelAttribute("registerModel") RegisterBloodProductModel registerBloodProductModel, ModelAndView modelAndView) {
        List<String> bloodProductTypeList = this.bloodProductTypeService.getBloodProductTypes();
        modelAndView.addObject("productTypes", bloodProductTypeList);
        modelAndView.setViewName("blood_products/register.html");
        return modelAndView;
    }

    @PostMapping("/register")
    @PreAuthorize("hasAnyAuthority('Administration', 'Processing')")
    public ModelAndView register(@Valid @ModelAttribute("registerModel") RegisterBloodProductModel registerBloodProductModel, BindingResult bindingResult) {
        ModelAndView mav;
        if(bindingResult.hasErrors()) {
            mav = new ModelAndView("blood_products/register.html");
            List<String> bloodProductTypeList = this.bloodProductTypeService.getBloodProductTypes();
            mav.addObject("productTypes", bloodProductTypeList);
            return mav;
        }

        this.bloodProductService.addBloodProduct(registerBloodProductModel);
        mav = new ModelAndView("redirect:/products/table");
        return mav;
    }

    @GetMapping("/table")
    @PageTitle("Blood Products")
    @PreAuthorize("hasAnyAuthority('Administration', 'Processing', 'Storage')")
    public ModelAndView getBloodProductsTable(ModelAndView modelAndView) {
        List<ViewProductModel> viewProductModels = this.bloodProductService.findAllProducts();
        modelAndView.setViewName("blood_products/products-table.html");
        modelAndView.addObject("bloodProducts", viewProductModels);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    @PageTitle("Edit Blood Product")
    @PreAuthorize("hasAnyAuthority('Administration', 'Processing', 'Storage')")
    public ModelAndView getEditForm(@ModelAttribute("editModel") EditBloodProductModel model, ModelAndView modelAndView, @PathVariable("id") long id) {
        EditProductModel editProductModel = this.bloodProductService.getEditDonationModel(id);
        setEnumTypesToModel(modelAndView);
        modelAndView.addObject("product", editProductModel);
        modelAndView.setViewName("blood_products/edit.html");
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('Administration', 'Processing', 'Storage')")
    public ModelAndView edit(@Valid @ModelAttribute("editModel") EditBloodProductModel model, BindingResult bindingResult, @PathVariable("id") long id){
        ModelAndView mav;
        if(bindingResult.hasErrors()) {
            mav = new ModelAndView("blood_products/edit.html");
            setEnumTypesToModel(mav);
            mav.addObject("id", id);
            mav.addObject("product", model);
            return mav;
        }

        mav = new ModelAndView("redirect:/products/table");
        this.bloodProductService.editBloodProduct(model, id);
        return mav;
    }

    private void setEnumTypesToModel(ModelAndView mav) {
        List<String> bloodProductTypes = this.bloodProductTypeService.getBloodProductTypes();
        List<String> productAvailabilityStates = this.productAvailabilityService.getProductAvailabilityStates();
        mav.addObject("productTypes", bloodProductTypes);
        mav.addObject("availability", productAvailabilityStates);
    }

    @GetMapping("/delete/{id}")
    @PageTitle("Delete Blood Product")
    @PreAuthorize("hasAnyAuthority('Administration', 'Processing', 'Storage')")
    public ModelAndView getDeleteForm(ModelAndView modelAndView, @PathVariable("id") long id) {
        EditProductModel editProductModel = this.bloodProductService.getEditDonationModel(id);
        modelAndView.addObject("product", editProductModel);
        modelAndView.setViewName("blood_products/delete.html");
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('Administration', 'Processing', 'Storage')")
    public String delete(@PathVariable("id") long id){
        this.bloodProductService.deleteBloodProduct(id);
        return "redirect:/products/table";
    }

    @ExceptionHandler(NoResultException.class)
    public ModelAndView defaultErrorHandler(NoResultException exception, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", exception.getMessage());
        mav.addObject("url", request.getRequestURL());
        return mav;
    }

    @ModelAttribute("registerModel")
    public RegisterBloodProductModel registerModel() {
        return new RegisterBloodProductModel();
    }

    @ModelAttribute("editModel")
    public EditBloodProductModel editModel() {
        return new EditBloodProductModel();
    }
}

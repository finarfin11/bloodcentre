package com.softuni.bloodcentre.service.impl;

import com.softuni.bloodcentre.data.models.BloodProduct;
import com.softuni.bloodcentre.data.models.BloodProductType;
import com.softuni.bloodcentre.data.models.ProductAvailability;
import com.softuni.bloodcentre.data.repositories.BloodProductRepository;
import com.softuni.bloodcentre.service.models.BloodProductServiceModel;
import com.softuni.bloodcentre.service.services.BloodProductService;
import com.softuni.bloodcentre.web.models.EditBloodProductModel;
import com.softuni.bloodcentre.web.models.EditProductModel;
import com.softuni.bloodcentre.web.models.RegisterBloodProductModel;
import com.softuni.bloodcentre.web.models.ViewProductModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BloodProductServiceImpl implements BloodProductService {

    private final BloodProductRepository bloodProductRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BloodProductServiceImpl(BloodProductRepository bloodProductRepository, ModelMapper modelMapper) {
        this.bloodProductRepository = bloodProductRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BloodProduct addBloodProduct(RegisterBloodProductModel registerBloodProductModel) {
        BloodProduct bloodProduct = this.modelMapper.map(registerBloodProductModel, BloodProduct.class);
        bloodProduct.setProductAvailability(ProductAvailability.AVAILABLE);
        bloodProduct.setType(BloodProductType.valueOf(registerBloodProductModel.getType().toUpperCase().replaceAll("\\s", "")));
        return this.bloodProductRepository.save(bloodProduct);
    }

    @Override
    public List<ViewProductModel> findAllProducts() {
        List<BloodProductServiceModel> serviceModels = this.bloodProductRepository.findAll()
                .stream().map(bloodProduct -> this.modelMapper.map(bloodProduct, BloodProductServiceModel.class)).collect(Collectors.toList());
        return serviceModels.stream().map(sm -> this.modelMapper.map(sm, ViewProductModel.class)).collect(Collectors.toList());
    }

    @Override
    public EditProductModel getEditDonationModel(long id) {
        BloodProductServiceModel bloodProductServiceModel = this.modelMapper.map(this.findById(id), BloodProductServiceModel.class);
        EditProductModel editProductModel = new EditProductModel();
        editProductModel.setId(bloodProductServiceModel.getId());
        editProductModel.setAmount(bloodProductServiceModel.getAmount());
        editProductModel.setDateOfDonation(bloodProductServiceModel.getDateOfDonation());
        editProductModel.setType(bloodProductServiceModel.getType());
        editProductModel.setProductAvailability(bloodProductServiceModel.getProductAvailability());
        return editProductModel;
    }

    @Override
    public BloodProduct findById(long id) {
        return this.bloodProductRepository.findById(id).orElseThrow(() -> new NoResultException("Product with the given id was not found!"));
    }

    @Override
    public BloodProduct editBloodProduct(EditBloodProductModel model, long id) {
        BloodProduct bloodProduct = this.findById(id);
        bloodProduct.setType(BloodProductType.valueOf(model.getType().toUpperCase().replaceAll("\\s", "")));
        bloodProduct.setProductAvailability(ProductAvailability.valueOf(model.getProductAvailability().toUpperCase().replaceAll("\\s", "")));
        bloodProduct.setDateOfDonation(model.getDateOfDonation());
        bloodProduct.setAmount(model.getAmount());
        return this.bloodProductRepository.save(bloodProduct);
    }

    @Override
    public void deleteBloodProduct(long id) {
        BloodProduct bloodProduct = this.findById(id);
        this.bloodProductRepository.delete(bloodProduct);
    }
}

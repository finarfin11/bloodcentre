package com.softuni.bloodcentre.service.services;

import com.softuni.bloodcentre.data.models.BloodProduct;
import com.softuni.bloodcentre.web.models.EditBloodProductModel;
import com.softuni.bloodcentre.web.models.EditProductModel;
import com.softuni.bloodcentre.web.models.RegisterBloodProductModel;
import com.softuni.bloodcentre.web.models.ViewProductModel;

import java.util.List;

public interface BloodProductService {

    BloodProduct addBloodProduct(RegisterBloodProductModel registerBloodProductModel);

    List<ViewProductModel> findAllProducts();

    EditProductModel getEditDonationModel(long id);

    BloodProduct findById(long id);

    BloodProduct editBloodProduct(EditBloodProductModel model, long id);

    void deleteBloodProduct(long id);

}

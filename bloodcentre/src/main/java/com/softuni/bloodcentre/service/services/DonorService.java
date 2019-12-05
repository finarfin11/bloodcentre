package com.softuni.bloodcentre.service.services;

import com.softuni.bloodcentre.data.models.Donor;
import com.softuni.bloodcentre.web.models.EditDonorModel;
import com.softuni.bloodcentre.web.models.RegisterDonorModel;
import com.softuni.bloodcentre.web.models.ViewDonorModel;

import javax.naming.InvalidNameException;
import java.util.List;
public interface DonorService {
    Donor addDonor(RegisterDonorModel registerDonorModel);

    List<ViewDonorModel> findAllDonors();

    Donor findByName(String name);

    Donor findById(long id);

    Donor editDonor(EditDonorModel editDonorModel, long id);

    void deleteDonor(long id);
}

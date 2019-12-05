package com.softuni.bloodcentre.service.services;

import com.softuni.bloodcentre.data.models.Hospital;
import com.softuni.bloodcentre.web.models.EditHospitalModel;
import com.softuni.bloodcentre.web.models.RegisterHospitalModel;
import com.softuni.bloodcentre.web.models.ViewHospitalModel;

import java.util.List;

public interface HospitalService {
    Hospital addHospital(RegisterHospitalModel registerHospitalModel);

    List<ViewHospitalModel> findAllHospitals();

    EditHospitalModel getEditHospitalModel(long id);

    Hospital findById(long id);

    Hospital editHospital(EditHospitalModel model, long id);

    void deleteHospital(long id);

    Hospital findByName(String name);

    Hospital save(Hospital hospital);
}

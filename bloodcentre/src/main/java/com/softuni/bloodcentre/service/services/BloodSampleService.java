package com.softuni.bloodcentre.service.services;

import com.softuni.bloodcentre.data.models.BloodSample;
import com.softuni.bloodcentre.web.models.EditBloodSampleModel;
import com.softuni.bloodcentre.web.models.RegisterBloodSampleModel;
import com.softuni.bloodcentre.web.models.ViewBloodSampleModel;

import java.util.List;

public interface BloodSampleService {
    List<ViewBloodSampleModel> findAllSamples(long id);
    BloodSample findById(long id);
    void addBloodSample(RegisterBloodSampleModel registerBloodSampleModel, long id);
    BloodSample editBloodSample(EditBloodSampleModel editBloodSampleModel, long id);
    void deleteBloodSample(long id);
}

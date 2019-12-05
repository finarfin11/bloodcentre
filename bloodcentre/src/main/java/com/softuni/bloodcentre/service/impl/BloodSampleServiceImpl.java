package com.softuni.bloodcentre.service.impl;

import com.softuni.bloodcentre.data.models.BloodDonation;
import com.softuni.bloodcentre.data.models.BloodSample;
import com.softuni.bloodcentre.data.repositories.BloodSampleRepository;
import com.softuni.bloodcentre.service.models.BloodDonationServiceModel;
import com.softuni.bloodcentre.service.models.BloodSampleServiceModel;
import com.softuni.bloodcentre.service.services.BloodDonationService;
import com.softuni.bloodcentre.service.services.BloodSampleService;
import com.softuni.bloodcentre.web.models.EditBloodSampleModel;
import com.softuni.bloodcentre.web.models.RegisterBloodSampleModel;
import com.softuni.bloodcentre.web.models.ViewBloodSampleModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BloodSampleServiceImpl implements BloodSampleService {
    private final BloodSampleRepository bloodSampleRepository;
    private final ModelMapper modelMapper;
    private final BloodDonationService bloodDonationService;

    @Autowired
    public BloodSampleServiceImpl(BloodSampleRepository bloodSampleRepository, ModelMapper modelMapper, BloodDonationService bloodDonationService) {
        this.bloodSampleRepository = bloodSampleRepository;
        this.modelMapper = modelMapper;
        this.bloodDonationService = bloodDonationService;
    }


    @Override
    public void addBloodSample(RegisterBloodSampleModel registerBloodSampleModel, long id) {
        BloodDonation donation = this.bloodDonationService.findById(id);
        BloodSampleServiceModel serviceModel = this.modelMapper.map(registerBloodSampleModel, BloodSampleServiceModel.class);
        BloodSample sample = this.bloodSampleRepository.save(this.modelMapper.map(serviceModel, BloodSample.class));
        donation.getBloodSamples().add(sample);
        sample.setBloodDonation(donation);
        this.bloodSampleRepository.save(sample);
        this.bloodDonationService.save(donation);
    }

    @Override
    public List<ViewBloodSampleModel> findAllSamples(long id) {
        List<BloodSampleServiceModel> serviceModels = this.bloodSampleRepository.findAllByBloodDonation_Id(id)
                .stream().filter(Optional::isPresent).map(Optional::get).map(bloodSample -> this.modelMapper.map(bloodSample, BloodSampleServiceModel.class))
                .collect(Collectors.toList());
        return serviceModels.stream().map(sm -> this.modelMapper.map(sm, ViewBloodSampleModel.class)).collect(Collectors.toList());
    }

    @Override
    public BloodSample findById(long id) {
        return this.bloodSampleRepository.findById(id).orElseThrow(() -> new NoResultException("Blood Sample with the given id was not found!"));
    }

    @Override
    public BloodSample editBloodSample(EditBloodSampleModel editBloodSampleModel, long id) {
       BloodSample bloodSample = this.bloodSampleRepository.findById(id).orElseThrow(() -> new NoResultException("Blood Sample with the given id was not found!"));
       bloodSample.setNotes(editBloodSampleModel.getNotes());
       bloodSample.setBloodSampleStatus(editBloodSampleModel.getBloodSampleStatus());
       bloodSample.setBloodType(editBloodSampleModel.getBloodType());
       return this.bloodSampleRepository.save(bloodSample);
    }

    @Override
    public void deleteBloodSample(long id) {
        BloodSample bloodSample = this.bloodSampleRepository.findById(id).orElseThrow(() -> new NoResultException("Blood Sample with the given id was not found!"));
        this.bloodSampleRepository.delete(bloodSample);
    }
}

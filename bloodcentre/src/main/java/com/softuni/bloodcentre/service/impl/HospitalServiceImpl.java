package com.softuni.bloodcentre.service.impl;

import com.softuni.bloodcentre.data.models.Hospital;
import com.softuni.bloodcentre.data.repositories.HospitalRepository;
import com.softuni.bloodcentre.service.models.HospitalServiceModel;
import com.softuni.bloodcentre.service.services.HospitalService;
import com.softuni.bloodcentre.web.models.EditHospitalModel;
import com.softuni.bloodcentre.web.models.RegisterHospitalModel;
import com.softuni.bloodcentre.web.models.ViewHospitalModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public HospitalServiceImpl(HospitalRepository hospitalRepository, ModelMapper modelMapper) {
        this.hospitalRepository = hospitalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Hospital addHospital(RegisterHospitalModel registerHospitalModel) {
        return this.hospitalRepository.save(this.modelMapper.map(registerHospitalModel, Hospital.class));
    }

    @Override
    public List<ViewHospitalModel> findAllHospitals() {
        List<HospitalServiceModel> serviceModels = this.hospitalRepository.findAll()
                .stream().map(hospital -> this.modelMapper.map(hospital, HospitalServiceModel.class)).collect(Collectors.toList());
        return serviceModels.stream().map(sm -> this.modelMapper.map(sm, ViewHospitalModel.class)).collect(Collectors.toList());
    }

    @Override
    public EditHospitalModel getEditHospitalModel(long id) {
        HospitalServiceModel serviceModel = this.modelMapper.map(this.findById(id), HospitalServiceModel.class);
        return this.modelMapper.map(serviceModel, EditHospitalModel.class);
    }

    @Override
    public Hospital findById(long id) {
        return this.hospitalRepository.findById(id).orElseThrow(() -> new NoResultException("Hospital with the given id was not found!"));
    }

    @Override
    public Hospital editHospital(EditHospitalModel model, long id) {
        Hospital hospital = this.findById(id);
        HospitalServiceModel serviceModel = this.modelMapper.map(model, HospitalServiceModel.class);
        hospital.setAddress(serviceModel.getAddress());
        hospital.setName(serviceModel.getName());
        hospital.setPhoneNumber(serviceModel.getPhoneNumber());
        return this.hospitalRepository.save(hospital);
    }

    @Override
    public void deleteHospital(long id) {
        Hospital hospital = this.findById(id);
        this.hospitalRepository.delete(hospital);
    }

    @Override
    public Hospital findByName(String name) {
        return this.hospitalRepository.findHospitalByName(name).orElseThrow(() -> new NoResultException("Hospital with the given name was not found!"));
    }

    @Override
    public Hospital save(Hospital hospital) {
        return this.hospitalRepository.saveAndFlush(hospital);
    }
}

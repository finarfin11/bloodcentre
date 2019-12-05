package com.softuni.bloodcentre.service.impl;

import com.softuni.bloodcentre.data.models.Donor;
import com.softuni.bloodcentre.data.repositories.DonorRepository;
import com.softuni.bloodcentre.service.models.RegisterDonorServiceModel;
import com.softuni.bloodcentre.service.services.DonorService;
import com.softuni.bloodcentre.web.models.EditDonorModel;
import com.softuni.bloodcentre.web.models.RegisterDonorModel;
import com.softuni.bloodcentre.web.models.ViewDonorModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.InvalidNameException;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonorServiceImpl implements DonorService {
    private final ModelMapper modelMapper;
    private final DonorRepository donorRepository;

    @Autowired
    public DonorServiceImpl(ModelMapper modelMapper, DonorRepository donorRepository) {
        this.modelMapper = modelMapper;
        this.donorRepository = donorRepository;
    }

    @Override
    public Donor addDonor(RegisterDonorModel registerDonorModel) {
        RegisterDonorServiceModel serviceModel = this.modelMapper.map(registerDonorModel, RegisterDonorServiceModel.class);
        return this.donorRepository.save(this.modelMapper.map(serviceModel, Donor.class));
    }

    @Override
    public List<ViewDonorModel> findAllDonors() {
        return this.donorRepository.findAll().stream().map(donor -> this.modelMapper.map(donor, ViewDonorModel.class)).collect(Collectors.toList());
    }

    @Override
    public Donor findByName(String name) {
        return this.donorRepository.findByFullName(name).orElseThrow(() -> new NoResultException("Donor with the given name was not found!"));
    }

    @Override
    public Donor findById(long id) {
        Donor donor = this.donorRepository.findById(id).orElseThrow(() -> new NoResultException("Donor with the given id was not found!"));
        return donor;
    }

    @Override
    public Donor editDonor(EditDonorModel editDonorModel, long id) {
        Donor donor = this.donorRepository.findById(id).orElseThrow(() -> new NoResultException("Donor with the given id was not found!"));
        donor.setFullName(editDonorModel.getFullName());
        donor.setAge(editDonorModel.getAge());
        donor.setCurrentAddress(editDonorModel.getCurrentAddress());
        donor.setEmail(editDonorModel.getEmail());
        donor.setGpName(editDonorModel.getGpName());
        donor.setSex(editDonorModel.getSex());
        donor.setPhoneNumber(editDonorModel.getPhoneNumber());
        donor.setIdCardNumber(editDonorModel.getIdCardNumber());
        donor.setUcn(editDonorModel.getUcn());
        donor.setNotes(editDonorModel.getNotes());
        return this.donorRepository.save(donor);
    }

    @Override
    public void deleteDonor(long id) {
        Donor donor = this.donorRepository.findById(id).orElseThrow(() -> new NoResultException("Donor with the given id was not found!"));
        this.donorRepository.delete(donor);
    }
}

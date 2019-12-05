package com.softuni.bloodcentre.service.impl;

import com.softuni.bloodcentre.data.models.BloodDonation;
import com.softuni.bloodcentre.data.models.BloodProcessState;
import com.softuni.bloodcentre.data.models.Donor;
import com.softuni.bloodcentre.data.repositories.BloodDonationRepository;
import com.softuni.bloodcentre.service.models.BloodDonationServiceModel;
import com.softuni.bloodcentre.service.services.BloodDonationService;
import com.softuni.bloodcentre.service.services.DonorService;
import com.softuni.bloodcentre.web.models.EditDonationModel;
import com.softuni.bloodcentre.web.models.RegisterDonationModel;
import com.softuni.bloodcentre.web.models.SetBloodStateModel;
import com.softuni.bloodcentre.web.models.ViewDonationModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.naming.InvalidNameException;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BloodDonationServiceImpl implements BloodDonationService {
    private final BloodDonationRepository bloodDonationRepository;
    private final DonorService donorService;
    private final ModelMapper modelMapper;

    public BloodDonationServiceImpl(BloodDonationRepository bloodDonationRepository, DonorService donorService, ModelMapper modelMapper) {
        this.bloodDonationRepository = bloodDonationRepository;
        this.donorService = donorService;
        this.modelMapper = modelMapper;
    }

    @Override
    public BloodDonation addDonation(RegisterDonationModel registerDonationModel) {
        BloodDonationServiceModel bloodDonationServiceModel = this.modelMapper.map(registerDonationModel, BloodDonationServiceModel.class);
        bloodDonationServiceModel.setBloodProcessState(BloodProcessState.UNPROCESSED);
        bloodDonationServiceModel.setBloodSamples(new ArrayList<>());
        Donor donor = this.donorService.findByName(registerDonationModel.getDonorName());
        bloodDonationServiceModel.setDonor(donor);
        return this.bloodDonationRepository.save(this.modelMapper.map(bloodDonationServiceModel, BloodDonation.class));
    }

    @Override
    public List<ViewDonationModel> findAllDonations() {
        List<BloodDonationServiceModel> serviceModels = this.bloodDonationRepository.findAll().stream()
                .map(donation -> this.modelMapper.map(donation, BloodDonationServiceModel.class)).collect(Collectors.toList());
        return serviceModels.stream().map(sm -> this.modelMapper.map(sm, ViewDonationModel.class)).collect(Collectors.toList());
    }

    @Override
    public BloodDonation findById(long id) {
        BloodDonation donation = this.bloodDonationRepository.findById(id).orElseThrow(() -> new NoResultException("Donation with the given id was not found!"));
        return donation;
    }

    @Override
    public BloodDonation save(BloodDonation donation) {
        return this.bloodDonationRepository.save(donation);
    }

    @Override
    public EditDonationModel getEditDonationViewModel(long id) {
        BloodDonationServiceModel bloodDonationServiceModel = this.modelMapper
                .map(this.findById(id), BloodDonationServiceModel.class);
        EditDonationModel editDonationModel = new EditDonationModel();
        editDonationModel.setId(bloodDonationServiceModel.getId());
        editDonationModel.setDonorName(bloodDonationServiceModel.getDonor().getFullName());
        editDonationModel.setAmount(bloodDonationServiceModel.getAmount());
        editDonationModel.setDateOfDonation(bloodDonationServiceModel.getDateOfDonation());
        return editDonationModel;
    }

    @Override
    public BloodDonation editDonation(EditDonationModel model, long id) {
        BloodDonation bloodDonation = this.findById(id);
        Donor donor = this.donorService.findByName(model.getDonorName());
        bloodDonation.setDonor(donor);
        bloodDonation.setDateOfDonation(model.getDateOfDonation());
        bloodDonation.setAmount(model.getAmount());
        return this.bloodDonationRepository.save(bloodDonation);
    }

    @Override
    public void deleteDonation(long id) {
        BloodDonation bloodDonation = this.bloodDonationRepository.findById(id).orElseThrow(() -> new NoResultException("Donation with the given id was not found!"));
        this.bloodDonationRepository.delete(bloodDonation);
    }

    @Override
    public List<BloodDonation> findAllCleanDonations() {
        List<BloodDonation> cleanDonations = this.bloodDonationRepository.findAllCleanDonations();
        return cleanDonations;
    }

    @Override
    public BloodDonation setBloodProcessed(long id) {
        BloodDonation donation = this.findById(id);
        donation.setBloodProcessState(BloodProcessState.PROCESSED);
        return this.bloodDonationRepository.save(donation);
    }

    @Override
    public BloodDonation setBloodState(SetBloodStateModel model, long id) {
        BloodDonation bloodDonation = this.bloodDonationRepository.findById(id).orElseThrow(() -> new NoResultException("Donation with the given id was not found!"));
        bloodDonation.setBloodProcessState(model.getBloodProcessState());
        return this.bloodDonationRepository.save(bloodDonation);
    }


}

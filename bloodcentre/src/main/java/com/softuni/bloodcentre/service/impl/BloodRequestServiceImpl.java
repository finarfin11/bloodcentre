package com.softuni.bloodcentre.service.impl;

import com.softuni.bloodcentre.data.models.BloodProductType;
import com.softuni.bloodcentre.data.models.BloodRequest;
import com.softuni.bloodcentre.data.models.Hospital;
import com.softuni.bloodcentre.data.models.RequestState;
import com.softuni.bloodcentre.data.repositories.BloodRequestRepository;
import com.softuni.bloodcentre.service.models.BloodRequestServiceModel;
import com.softuni.bloodcentre.service.models.RequestServiceModel;
import com.softuni.bloodcentre.service.services.BloodRequestService;
import com.softuni.bloodcentre.service.services.HospitalService;
import com.softuni.bloodcentre.web.models.EditRequestModel;
import com.softuni.bloodcentre.web.models.EditRequestPostModel;
import com.softuni.bloodcentre.web.models.RegisterBloodRequestModel;
import com.softuni.bloodcentre.web.models.ViewRequestModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BloodRequestServiceImpl implements BloodRequestService {

    private final BloodRequestRepository bloodRequestRepository;
    private final ModelMapper modelMapper;
    private final HospitalService hospitalService;

    @Autowired
    public BloodRequestServiceImpl(BloodRequestRepository bloodRequestRepository, ModelMapper modelMapper, HospitalService hospitalService) {
        this.bloodRequestRepository = bloodRequestRepository;
        this.modelMapper = modelMapper;
        this.hospitalService = hospitalService;
    }

    @Override
    public BloodRequest addBloodRequest(RegisterBloodRequestModel registerBloodRequestModel) {
        BloodRequestServiceModel serviceModel = this.modelMapper.map(registerBloodRequestModel, BloodRequestServiceModel.class);
        serviceModel.setRequestState(RequestState.WAITING);
        serviceModel.setBloodProductType(BloodProductType.valueOf(registerBloodRequestModel.getBloodProductType().toUpperCase().replaceAll("\\s", "")));
        Hospital hospital = this.hospitalService.findByName(registerBloodRequestModel.getHospitalName());
        serviceModel.setHospital(hospital);
        BloodRequest bloodRequest = this.modelMapper.map(serviceModel, BloodRequest.class);
        hospital.getBloodRequests().add(this.bloodRequestRepository.saveAndFlush(bloodRequest));
        this.hospitalService.save(hospital);
        return bloodRequest;
    }

    @Override
    public BloodRequest findById(long id) {
        return this.bloodRequestRepository.findById(id).orElseThrow(() -> new NoResultException("Request with the given id was not found!"));
    }

    @Override
    public List<ViewRequestModel> findAllRequests() {
        List<BloodRequestServiceModel> serviceModels = this.bloodRequestRepository.findAll().stream().map(req -> this.modelMapper.map(req, BloodRequestServiceModel.class)).collect(Collectors.toList());
        return serviceModels.stream().map(sm -> this.modelMapper.map(sm, ViewRequestModel.class)).collect(Collectors.toList());
    }

    @Override
    public EditRequestModel getEditRequestModel(long id) {
        BloodRequest bloodRequest = this.bloodRequestRepository.findById(id).orElseThrow(() -> new NoResultException("Request with the given id was not found!"));
        RequestServiceModel serviceModel = this.modelMapper.map(bloodRequest, RequestServiceModel.class);
        return this.modelMapper.map(serviceModel, EditRequestModel.class);
    }

    @Override
    public BloodRequest editRequest(EditRequestPostModel model, long id) {
        RequestServiceModel serviceModel = this.modelMapper.map(model, RequestServiceModel.class);
        Hospital hospital = this.hospitalService.findByName(model.getHospital());
        serviceModel.setHospital(hospital);
        serviceModel.setBloodProductType(BloodProductType.valueOf(model.getBloodProductType().toUpperCase().replaceAll("\\s", "")));
        serviceModel.setRequestState(RequestState.valueOf(model.getRequestState().toUpperCase().replaceAll("\\s", "")));
        BloodRequest request = this.findById(id);
        request = this.modelMapper.map(serviceModel, BloodRequest.class);
        return this.bloodRequestRepository.save(request);
    }

    @Override
    public void deleteRequest(long id) {
        BloodRequest request = this.findById(id);
        this.bloodRequestRepository.delete(request);
    }
}

package com.softuni.bloodcentre.services;

import com.softuni.bloodcentre.base.TestBase;
import com.softuni.bloodcentre.data.models.Hospital;
import com.softuni.bloodcentre.data.repositories.HospitalRepository;
import com.softuni.bloodcentre.service.services.HospitalService;
import com.softuni.bloodcentre.web.models.EditHospitalModel;
import com.softuni.bloodcentre.web.models.EditRequestPostModel;
import com.softuni.bloodcentre.web.models.RegisterHospitalModel;
import com.softuni.bloodcentre.web.models.ViewHospitalModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HospitalServiceTests extends TestBase {
    private Hospital testHospital;
    private List<Hospital> testHospitals;
    private EditHospitalModel editModel;
    private RegisterHospitalModel registerModel;

    @MockBean
    HospitalRepository hospitalRepository;

    @Autowired
    HospitalService hospitalService;

    @BeforeEach
    public void init() {
        testHospital = new Hospital();
        testHospital.setId(1);
        testHospital.setName("name");
        testHospital.setAddress("address");
        testHospital.setPhoneNumber("phone");
        testHospitals = new ArrayList<>();
        Mockito.when(hospitalRepository.findById(testHospital.getId())).thenReturn(java.util.Optional.of(testHospital));
        Mockito.when(hospitalRepository.saveAndFlush(testHospital)).thenReturn(testHospital);
        Mockito.when(hospitalRepository.save(testHospital)).thenReturn(testHospital);
        Mockito.when(hospitalRepository.findAll()).thenReturn(testHospitals);
        Mockito.when(hospitalRepository.findHospitalByName(testHospital.getName())).thenReturn(java.util.Optional.ofNullable(testHospital));
    }

    @Test
    public void hospital_Service_Find_By_Valid_Id_Should_Return_Correct_Result() {
        Hospital actualDonation = hospitalService.findById(1);
        assertEquals(testHospital, actualDonation);
    }

    @Test
    public void hospital_Service_Find_By_Invalid_Id_Should_Throw() {
        assertThrows(NoResultException.class, () -> hospitalService.findById(3));
    }

    @Test
    public void hospital_Service_Find_By_Name_Should_Return_Correct_Result() {
        Hospital actualHospital = hospitalService.findByName("name");
        assertEquals(testHospital, actualHospital);
    }

    @Test
    public void hospital_Service_Find_By_Name_Throw_When_Not_Exists() {
        assertThrows(NoResultException.class, () -> hospitalService.findByName("missingName"));
    }

    @Test
    public void hospital_Service_Save_Should_Work_Properly() {
        hospitalService.save(testHospital);
        ArgumentCaptor<Hospital> argumentCaptor = ArgumentCaptor.forClass(Hospital.class);
        Mockito.verify(hospitalRepository).saveAndFlush(argumentCaptor.capture());
        Hospital hospital = argumentCaptor.getValue();
        assertNotNull(hospital);
        assertEquals(testHospital.getBloodRequests(), hospital.getBloodRequests());
        assertEquals(testHospital.getName(), hospital.getName());
        assertEquals(testHospital.getAddress(), hospital.getAddress());
        assertEquals(testHospital.getPhoneNumber(), hospital.getPhoneNumber());
    }

    @Test
    public void hospital_Service_Edit_Hospital_Should_Work_Correctly() {
        editModel = new EditHospitalModel();
        editModel.setAddress("newAddress");
        editModel.setName("newName");
        editModel.setPhoneNumber("newNumber");
        Hospital editedHospital = hospitalService.editHospital(editModel, testHospital.getId());
        assertEquals(editedHospital.getPhoneNumber(), testHospital.getPhoneNumber());
        assertEquals(editedHospital.getName(), testHospital.getName());
        assertEquals(editedHospital.getAddress(), testHospital.getAddress());
        assertEquals(editedHospital.getBloodRequests(), testHospital.getBloodRequests());
    }

    @Test
    public void hospital_Service_Add_Hospital_Should_Work_Correctly() {
        Hospital newHospital = new Hospital();
        testHospitals.add(newHospital);
        List<ViewHospitalModel> actualHospitals = hospitalService.findAllHospitals();
        assertEquals(testHospitals.size(), actualHospitals.size());
    }

    @Test
    public void hospital_Service_Add_Hospital_Should_Return_Correct_Result() {
        registerModel = new RegisterHospitalModel();
        registerModel.setAddress("address");
        registerModel.setName("name");
        registerModel.setPhoneNumber("phone");
        hospitalService.addHospital(registerModel);
        ArgumentCaptor<Hospital> argumentCaptor = ArgumentCaptor.forClass(Hospital.class);
        Mockito.verify(hospitalRepository).save(argumentCaptor.capture());
        Hospital hospital = argumentCaptor.getValue();
        assertNotNull(hospital);
        assertEquals(registerModel.getAddress(), hospital.getAddress());
        assertEquals(registerModel.getName(), hospital.getName());
        assertEquals(registerModel.getPhoneNumber(), hospital.getPhoneNumber());
    }

    @Test
    public void hospital_Service_Get_Edit_Hospital_Model_Should_Work_Correctly() {
        editModel = hospitalService.getEditHospitalModel(testHospital.getId());
        assertEquals(editModel.getAddress(), testHospital.getAddress());
        assertEquals(editModel.getName(), testHospital.getName());
        assertEquals(editModel.getPhoneNumber(), testHospital.getPhoneNumber());
    }
}

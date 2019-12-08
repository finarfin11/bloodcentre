package com.softuni.bloodcentre.services;

import com.softuni.bloodcentre.base.TestBase;
import com.softuni.bloodcentre.data.models.BloodDonation;
import com.softuni.bloodcentre.data.models.BloodSample;
import com.softuni.bloodcentre.data.models.BloodSampleStatus;
import com.softuni.bloodcentre.data.models.BloodType;
import com.softuni.bloodcentre.data.repositories.BloodDonationRepository;
import com.softuni.bloodcentre.data.repositories.BloodSampleRepository;
import com.softuni.bloodcentre.service.models.BloodSampleServiceModel;
import com.softuni.bloodcentre.service.services.BloodSampleService;
import com.softuni.bloodcentre.web.models.EditBloodSampleModel;
import com.softuni.bloodcentre.web.models.RegisterBloodSampleModel;
import com.softuni.bloodcentre.web.models.ViewBloodSampleModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.NoResultException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BloodSampleServiceTests extends TestBase {
    private BloodSample testSample;
    private BloodDonation bloodDonation;
    private List<Optional<BloodSample>> testSamples;
    private List<BloodSample> bloodSamples;
    private RegisterBloodSampleModel registerModel;
    private EditBloodSampleModel editModel;

    @MockBean
    BloodSampleRepository bloodSampleRepository;

    @MockBean
    BloodDonationRepository bloodDonationRepository;

    @MockBean
    ModelMapper modelMapper;

    @Autowired
    BloodSampleService bloodSampleService;

    @BeforeEach
    public void init() {
        testSample = new BloodSample();
        testSample.setId(1);
        testSample.setBloodSampleStatus(BloodSampleStatus.UNTESTED);
        testSample.setNotes("notes");
        testSample.setBloodType(BloodType.A_NEGATIVE);
        bloodDonation = new BloodDonation();
        bloodDonation.setId(5);
        bloodDonation.setBloodSamples(new ArrayList<>());
        testSample.setBloodDonation(bloodDonation);
        testSamples = new ArrayList<>();
        registerModel = new RegisterBloodSampleModel();
        registerModel.setBloodType(BloodType.AB_POSITIVE);
        editModel = new EditBloodSampleModel();
        Mockito.when(bloodSampleRepository.findById(testSample.getId())).thenReturn(java.util.Optional.of(testSample));
        Mockito.when(bloodSampleRepository.findAllByBloodDonation_Id(bloodDonation.getId())).thenReturn(testSamples);
        Mockito.when(bloodSampleRepository.save
                (modelMapper.map(modelMapper.map(registerModel, BloodSampleServiceModel.class), BloodSample.class))).thenReturn(testSample);
        Mockito.when(bloodDonationRepository.findById(bloodDonation.getId())).thenReturn(java.util.Optional.ofNullable(bloodDonation));
        Mockito.when(bloodDonationRepository.save(bloodDonation)).thenReturn(bloodDonation);
        Mockito.when(bloodSampleRepository.save(testSample)).thenReturn(testSample);
    }

    @Test
    public void bloodSample_Service_Find_By_Valid_Id_Should_Return_Correct_Result() {
        BloodSample actualSample = bloodSampleService.findById(1);
        assertEquals(testSample, actualSample);
    }

    @Test
    public void bloodSample_Service_Find_By_Invalid_Id_Should_Throw() {
        assertThrows(NoResultException.class, () -> bloodSampleService.findById(3));
    }

    @Test
    public void bloodSample_Service_Add_Sample_Should_Work_Correctly() {
        bloodSampleService.addBloodSample(registerModel, bloodDonation.getId());
        assertEquals(bloodDonation.getBloodSamples().size(), 1);
        assertEquals(bloodDonation.getBloodSamples().get(0), testSample);
    }

    @Test
    public void bloodSample_Service_Add_Sample_Should_Throw_When_Id_Not_Exists() {
        assertThrows(NoResultException.class, () -> bloodSampleService.addBloodSample(registerModel, 44));
    }

    @Test
    public void bloodSample_Service_Edit_Sample_Should_Work_Correctly() {
        editModel.setBloodSampleStatus(BloodSampleStatus.CLEAN);
        editModel.setBloodType(BloodType.B_NEGATIVE);
        editModel.setNotes("newNotes");
        BloodSample editedSample = bloodSampleService.editBloodSample(editModel, testSample.getId());
        assertEquals(editedSample.getBloodType(), testSample.getBloodType());
        assertEquals(editedSample.getBloodSampleStatus(), testSample.getBloodSampleStatus());
        assertEquals(editedSample.getNotes(), testSample.getNotes());
    }

    @Test
    public void bloodSample_Service_FindAll_Sample_Should_Work_Correctly() {
        testSamples.add(Optional.of(new BloodSample()));
        testSamples.add(Optional.of(new BloodSample()));
        List<ViewBloodSampleModel> actualSamples = bloodSampleService.findAllSamples(bloodDonation.getId());
        assertEquals(actualSamples.size(), testSamples.size());
    }
}

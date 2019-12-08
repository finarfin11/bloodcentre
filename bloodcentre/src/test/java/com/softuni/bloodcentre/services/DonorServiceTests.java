package com.softuni.bloodcentre.services;

import com.softuni.bloodcentre.base.TestBase;
import com.softuni.bloodcentre.data.models.Donor;
import com.softuni.bloodcentre.data.repositories.DonorRepository;
import com.softuni.bloodcentre.service.services.DonorService;
import com.softuni.bloodcentre.web.models.EditDonorModel;
import com.softuni.bloodcentre.web.models.RegisterDonorModel;
import com.softuni.bloodcentre.web.models.ViewDonorModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DonorServiceTests extends TestBase {
    private Donor testDonor;
    private List<Donor> testDonors;
    private RegisterDonorModel registerModel;
    private EditDonorModel editModel;

    @MockBean
    DonorRepository donorRepository;

    @Autowired
    DonorService donorService;

    @Autowired
    ModelMapper modelMapper;

    @BeforeEach
    public void init() {
        testDonor = new Donor();
        testDonor.setId(1);
        testDonor.setUcn("123");
        testDonor.setFullName("fullName");
        testDonors = new ArrayList<>();
        Mockito.when(donorRepository.findById(testDonor.getId())).thenReturn(java.util.Optional.of(testDonor));
        Mockito.when(donorRepository.save(testDonor)).thenReturn(testDonor);
        Mockito.when(donorRepository.findByFullName(testDonor.getFullName())).thenReturn(java.util.Optional.ofNullable(testDonor));
        Mockito.when(donorRepository.findAll()).thenReturn(testDonors);
    }

    @Test
    public void donor_Service_Find_By_Valid_Id_Should_Return_Correct_Result() {
        Donor actualDonor = donorService.findById(1);
        assertEquals(testDonor, actualDonor);
    }

    @Test
    public void donor_Service_Find_By_Invalid_Id_Should_Throw() {
        assertThrows(NoResultException.class, () -> donorService.findById(3));
    }

    @Test
    public void donor_Service_Add_Donor_Should_Return_Correct_Result() {
        registerModel = new RegisterDonorModel();
        registerModel.setFullName("fullName");
        registerModel.setAge(50);
        registerModel.setCurrentAddress("address");
        registerModel.setEmail("email@");
        registerModel.setGpName("gp");
        registerModel.setIdCardNumber("idCard");
        registerModel.setUcn("ucn");
        registerModel.setSex("Male");
        donorService.addDonor(registerModel);
        ArgumentCaptor<Donor> argumentCaptor = ArgumentCaptor.forClass(Donor.class);
        Mockito.verify(donorRepository).save(argumentCaptor.capture());
        Donor donor = argumentCaptor.getValue();
        assertNotNull(donor);
        assertEquals(registerModel.getFullName(), donor.getFullName());
    }

    @Test
    public void donor_Service_Add_Donor_Should_Work_Correctly() {
        Donor newDonor = new Donor();
        newDonor.setFullName("newDonor");
        testDonors.add(newDonor);
        List<ViewDonorModel> actualDonors = donorService.findAllDonors();
        assertEquals(testDonors.size(), actualDonors.size());
    }

    @Test
    public void donor_Service_FindAll_Should_Work_Correctly() {
        Donor newDonor = new Donor();
        newDonor.setFullName("newDonor");
        testDonors.add(newDonor);
        assertEquals(testDonors.size(), donorService.findAllDonors().size());
    }

    @Test
    public void donor_Service_Find_By_Name_Should_Return_Correct_Result() {
        Donor actualDonor = donorService.findByName("fullName");
        assertEquals(testDonor, actualDonor);
    }

    @Test
    public void donor_Service_Find_By_Name_Should_Throw_When_Not_Exists() {
        assertThrows(NoResultException.class, () -> donorService.findByName("missingName"));
    }

    @Test
    public void donor_Service_Edit_Donor_Should_Work_Correctly() {
        editModel = new EditDonorModel();
        editModel.setFullName("newName");
        editModel.setAge(20);
        editModel.setPhoneNumber("newNumber");
        editModel.setCurrentAddress("newAddress");
        editModel.setSex("Female");
        editModel.setEmail("newEmail");
        editModel.setIdCardNumber("newId");
        editModel.setUcn("newUcn");
        editModel.setGpName("newGp");
        editModel.setNotes("notes");
        Donor editedDonor = donorService.editDonor(editModel, 1);
        assertEquals(editModel.getAge(), editedDonor.getAge());
        assertEquals(editModel.getCurrentAddress(), editedDonor.getCurrentAddress());
        assertEquals(editModel.getEmail(), editedDonor.getEmail());
        assertEquals(editModel.getFullName(), editedDonor.getFullName());
        assertEquals(editModel.getGpName(), editedDonor.getGpName());
        assertEquals(editModel.getIdCardNumber(), editedDonor.getIdCardNumber());
        assertEquals(editModel.getUcn(), editedDonor.getUcn());
        assertEquals(editModel.getNotes(), editedDonor.getNotes());
    }
}

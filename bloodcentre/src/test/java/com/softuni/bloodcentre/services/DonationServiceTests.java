package com.softuni.bloodcentre.services;

import com.softuni.bloodcentre.base.TestBase;
import com.softuni.bloodcentre.data.models.BloodDonation;
import com.softuni.bloodcentre.data.models.BloodProcessState;
import com.softuni.bloodcentre.data.models.Donor;
import com.softuni.bloodcentre.data.repositories.BloodDonationRepository;
import com.softuni.bloodcentre.data.repositories.DonorRepository;
import com.softuni.bloodcentre.service.services.BloodDonationService;
import com.softuni.bloodcentre.web.models.EditDonationModel;
import com.softuni.bloodcentre.web.models.RegisterDonationModel;
import com.softuni.bloodcentre.web.models.SetBloodStateModel;
import com.softuni.bloodcentre.web.models.ViewDonationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DonationServiceTests extends TestBase {
    private BloodDonation testDonation;
    private List<BloodDonation> testDonations;
    private RegisterDonationModel registerModel;
    private Donor donor;
    private EditDonationModel editModel;

    @MockBean
    BloodDonationRepository donationRepository;

    @MockBean
    DonorRepository donorRepository;

    @Autowired
    BloodDonationService donationService;

    @BeforeEach
    public void init() {
        testDonation = new BloodDonation();
        testDonation.setId(1);
        testDonation.setBloodProcessState(BloodProcessState.UNPROCESSED);
        testDonation.setAmount(new BigDecimal(300));
        testDonation.setDateOfDonation(new Date(2019-12-12));
        testDonation.setBloodSamples(new ArrayList<>());
        registerModel = new RegisterDonationModel();
        registerModel.setDonorName("donorName");
        registerModel.setAmount(new BigDecimal(400));
        donor = new Donor();
        donor.setFullName("donorName");
        testDonations = new ArrayList<>();
        Mockito.when(donationRepository.findById(testDonation.getId())).thenReturn(java.util.Optional.of(testDonation));
        Mockito.when(donationRepository.save(testDonation)).thenReturn(testDonation);
        Mockito.when(donorRepository.findByFullName(registerModel.getDonorName())).thenReturn(java.util.Optional.ofNullable(donor));
        Mockito.when(donationRepository.findAll()).thenReturn(testDonations);
        Mockito.when(donationRepository.findAllCleanDonations()).thenReturn(testDonations);
    }

    @Test
    public void donation_Service_Find_By_Valid_Id_Should_Return_Correct_Result() {
        BloodDonation actualDonation = donationService.findById(1);
        assertEquals(testDonation, actualDonation);
    }

    @Test
    public void donation_Service_Find_By_Invalid_Id_Should_Throw() {
        assertThrows(NoResultException.class, () -> donationService.findById(3));
    }

    @Test
    public void donation_Service_Add_Donation_Should_Return_Correct_Result() {
        donationService.addDonation(registerModel);
        ArgumentCaptor<BloodDonation> argumentCaptor = ArgumentCaptor.forClass(BloodDonation.class);
        Mockito.verify(donationRepository).save(argumentCaptor.capture());
        BloodDonation bloodDonation = argumentCaptor.getValue();
        assertNotNull(bloodDonation);
        assertEquals(registerModel.getAmount(), bloodDonation.getAmount());
        assertEquals(registerModel.getDonorName(), bloodDonation.getDonor().getFullName());
    }

    @Test
    public void donation_Service_Add_Donation_Should_Work_Correctly() {
        BloodDonation newDonation = new BloodDonation();
        testDonations.add(newDonation);
        List<ViewDonationModel> actualDonations = donationService.findAllDonations();
        assertEquals(testDonations.size(), actualDonations.size());
    }

    @Test
    public void donation_Service_Set_Blood_State_Should_Work_Properly() {
        SetBloodStateModel bloodStateModel = new SetBloodStateModel();
        bloodStateModel.setBloodProcessState(BloodProcessState.PROCESSED);
        donationService.setBloodState(bloodStateModel, testDonation.getId());
        ArgumentCaptor<BloodDonation> argumentCaptor = ArgumentCaptor.forClass(BloodDonation.class);
        Mockito.verify(donationRepository).save(argumentCaptor.capture());
        BloodDonation donation = argumentCaptor.getValue();
        assertNotNull(donation);
        assertEquals(bloodStateModel.getBloodProcessState(), donation.getBloodProcessState());
    }

    @Test
    public void donation_Service_Save_Should_Work_Properly() {
        donationService.save(testDonation);
        ArgumentCaptor<BloodDonation> argumentCaptor = ArgumentCaptor.forClass(BloodDonation.class);
        Mockito.verify(donationRepository).save(argumentCaptor.capture());
        BloodDonation donation = argumentCaptor.getValue();
        assertNotNull(donation);
        assertEquals(testDonation.getBloodProcessState(), donation.getBloodProcessState());
        assertEquals(testDonation.getAmount(), donation.getAmount());
        assertEquals(testDonation.getDateOfDonation(), donation.getDateOfDonation());
    }

    @Test
    public void donation_Service_Set_Blood_State_Processed_Should_Work_Properly() {
        testDonation.setBloodProcessState(BloodProcessState.UNPROCESSED);
        donationService.setBloodProcessed(testDonation.getId());
        ArgumentCaptor<BloodDonation> argumentCaptor = ArgumentCaptor.forClass(BloodDonation.class);
        Mockito.verify(donationRepository).save(argumentCaptor.capture());
        BloodDonation donation = argumentCaptor.getValue();
        assertNotNull(donation);
        assertEquals(BloodProcessState.PROCESSED, donation.getBloodProcessState());
    }

    @Test
    public void donation_Service_Find_All_Clean_Donations_Should_Return_Correct_Result() {
        testDonations.add(testDonation);
        List<BloodDonation> cleanDonations = donationService.findAllCleanDonations();
        assertEquals(cleanDonations.size(), testDonations.size());
        assertEquals(cleanDonations.get(0), testDonations.get(0));
    }

    @Test
    public void donation_Service_Edit_Donation_Should_Work_Correctly() {
        editModel = new EditDonationModel();
        editModel.setAmount(new BigDecimal(440));
        editModel.setDonorName("donorName");
        editModel.setDateOfDonation(new Date(2019, 11, 11));
        BloodDonation editedDonation = donationService.editDonation(editModel, testDonation.getId());
        assertEquals(editedDonation.getAmount(), testDonation.getAmount());
        assertEquals(editedDonation.getDateOfDonation(), testDonation.getDateOfDonation());
        assertEquals(editedDonation.getDonor(), testDonation.getDonor());
    }
}

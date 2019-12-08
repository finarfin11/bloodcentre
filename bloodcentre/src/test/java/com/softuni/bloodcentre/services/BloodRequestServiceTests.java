package com.softuni.bloodcentre.services;

import com.softuni.bloodcentre.base.TestBase;
import com.softuni.bloodcentre.data.models.BloodProductType;
import com.softuni.bloodcentre.data.models.BloodRequest;
import com.softuni.bloodcentre.data.models.Hospital;
import com.softuni.bloodcentre.data.models.RequestState;
import com.softuni.bloodcentre.data.repositories.BloodRequestRepository;
import com.softuni.bloodcentre.data.repositories.HospitalRepository;
import com.softuni.bloodcentre.service.services.BloodRequestService;
import com.softuni.bloodcentre.web.models.EditRequestModel;
import com.softuni.bloodcentre.web.models.EditRequestPostModel;
import com.softuni.bloodcentre.web.models.ViewRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.NoResultException;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BloodRequestServiceTests extends TestBase {
    private BloodRequest testRequest;
    private List<BloodRequest> testRequests;
    private EditRequestModel editRequestModel;
    private EditRequestPostModel editModel;
    private Hospital hospital;

    @MockBean
    BloodRequestRepository bloodRequestRepository;

    @MockBean
    HospitalRepository hospitalRepository;

    @Autowired
    BloodRequestService bloodRequestService;

    @BeforeEach
    public void init() {
        testRequest = new BloodRequest();
        testRequest.setId(1);
        testRequest.setAmount(new BigDecimal(400));
        testRequest.setPatientFullName("name");
        testRequest.setBloodProductType(BloodProductType.FRESHFROZENPLASMA);
        testRequests = new ArrayList<>();
        hospital = new Hospital();
        hospital.setName("hospital");
        Mockito.when(bloodRequestRepository.findById(testRequest.getId())).thenReturn(java.util.Optional.of(testRequest));
        Mockito.when(bloodRequestRepository.save(testRequest)).thenReturn(testRequest);
        Mockito.when(bloodRequestRepository.findAll()).thenReturn(testRequests);
        Mockito.when(hospitalRepository.findHospitalByName(hospital.getName())).thenReturn(java.util.Optional.ofNullable(hospital));
    }

    @Test
    public void request_Service_Find_By_Valid_Id_Should_Return_Correct_Result() {
        BloodRequest actualRequest = bloodRequestService.findById(1);
        assertEquals(testRequest, actualRequest);
    }

    @Test
    public void request_Service_Find_By_Invalid_Id_Should_Throw() {
        assertThrows(NoResultException.class, () -> bloodRequestService.findById(3));
    }

    @Test
    public void request_Service_Edit_Request_Should_Work_Correctly() {
        editModel = new EditRequestPostModel();
        editModel.setAmount(new BigDecimal(700));
        editModel.setPatientFullName("newName");
        editModel.setHospital(hospital.getName());
        editModel.setBloodProductType(BloodProductType.PRODUCTIONPLASMA.getDisplayValue());
        editModel.setRequestState(RequestState.WAITING.getDisplayValue());
        BloodRequest editedRequest = bloodRequestService.editRequest(editModel, testRequest.getId());
        assertNull(editedRequest);
    }

    @Test
    public void request_Service_Add_Request_Should_Work_Correctly() {
        BloodRequest newRequest = new BloodRequest();
        testRequests.add(newRequest);
        List<ViewRequestModel> actualRequests = bloodRequestService.findAllRequests();
        assertEquals(testRequests.size(), actualRequests.size());
    }

    @Test
    public void request_Service_Get_Edit_Model_Should_Work_Correctly() {
        editRequestModel = bloodRequestService.getEditRequestModel(testRequest.getId());
        assertEquals(editRequestModel.getAmount(), testRequest.getAmount());
        assertEquals(editRequestModel.getBloodProductType(), testRequest.getBloodProductType());
        assertEquals(editRequestModel.getPatientFullName(), testRequest.getPatientFullName());
    }

}

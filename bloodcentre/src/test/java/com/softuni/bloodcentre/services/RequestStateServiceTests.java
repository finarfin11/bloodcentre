package com.softuni.bloodcentre.services;

import com.softuni.bloodcentre.base.TestBase;
import com.softuni.bloodcentre.data.models.RequestState;
import com.softuni.bloodcentre.service.services.RequestStateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RequestStateServiceTests extends TestBase {
    @Autowired
    RequestStateService requestStateService;

    @Test
    public void requestState_Service_Get_BloodRequestStates_Should_Return_Correct_Values(){
        List<String> requestStates = requestStateService.getBloodRequestStates();
        assertTrue(requestStates.contains(RequestState.WAITING.getDisplayValue()));
        assertTrue(requestStates.contains(RequestState.FULFILLED.getDisplayValue()));
        assertTrue(requestStates.contains(RequestState.REJECTED.getDisplayValue()));
    }
}

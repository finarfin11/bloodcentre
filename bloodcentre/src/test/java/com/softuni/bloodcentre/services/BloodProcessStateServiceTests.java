package com.softuni.bloodcentre.services;

import com.softuni.bloodcentre.base.TestBase;
import com.softuni.bloodcentre.data.models.BloodProcessState;
import com.softuni.bloodcentre.service.services.BloodProcessStateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class BloodProcessStateServiceTests extends TestBase {
    @Autowired
    BloodProcessStateService bloodProcessStateService;

    @Test
    public void bloodProcessState_Service_Get_States_List_Should_Return_Correct_Values(){
        List<BloodProcessState> stateList = bloodProcessStateService.getBloodStates();
        assertTrue(stateList.contains(BloodProcessState.PROCESSED));
        assertTrue(stateList.contains(BloodProcessState.UNPROCESSED));
    }
}

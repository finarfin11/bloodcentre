package com.softuni.bloodcentre.services;

import com.softuni.bloodcentre.base.TestBase;
import com.softuni.bloodcentre.data.models.BloodSampleStatus;
import com.softuni.bloodcentre.service.services.BloodSampleStatusService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BloodSampleStatusServiceTests extends TestBase {
    @Autowired
    BloodSampleStatusService bloodSampleStatusService;

    @Test
    public void bloodSampleStatus_Service_Get_Status_List_Should_Return_Correct_Values(){
        List<BloodSampleStatus> statusList = bloodSampleStatusService.getBloodStatusList();
        assertTrue(statusList.contains(BloodSampleStatus.ANTIERYTHROCYTEPOSITIVE));
        assertTrue(statusList.contains(BloodSampleStatus.CLEAN));
        assertTrue(statusList.contains(BloodSampleStatus.INFECTED));
        assertTrue(statusList.contains(BloodSampleStatus.UNTESTED));
    }
}

package com.softuni.bloodcentre.services;

import com.softuni.bloodcentre.base.TestBase;
import com.softuni.bloodcentre.data.models.BloodType;
import com.softuni.bloodcentre.service.services.BloodTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BloodTypesServiceTests extends TestBase {
    @Autowired
    BloodTypeService bloodTypeService;

    @Test
    public void bloodType_Service_Get_Blood_Types_Should_Return_Correct_Values(){
        List<BloodType> bloodTypes = bloodTypeService.getBloodTypes();
        assertTrue(bloodTypes.contains(BloodType.O_NEGATIVE));
        assertTrue(bloodTypes.contains(BloodType.O_POSITIVE));
        assertTrue(bloodTypes.contains(BloodType.A_NEGATIVE));
        assertTrue(bloodTypes.contains(BloodType.A_POSITIVE));
        assertTrue(bloodTypes.contains(BloodType.B_NEGATIVE));
        assertTrue(bloodTypes.contains(BloodType.B_POSITIVE));
        assertTrue(bloodTypes.contains(BloodType.AB_NEGATIVE));
        assertTrue(bloodTypes.contains(BloodType.AB_POSITIVE));
    }
}

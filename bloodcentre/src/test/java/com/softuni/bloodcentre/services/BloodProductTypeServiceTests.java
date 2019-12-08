package com.softuni.bloodcentre.services;

import com.softuni.bloodcentre.base.TestBase;
import com.softuni.bloodcentre.data.models.BloodProductType;
import com.softuni.bloodcentre.service.services.BloodProductTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BloodProductTypeServiceTests extends TestBase {
    @Autowired
    BloodProductTypeService bloodProductTypeService;

    @Test
    public void bloodProductType_Service_Get_Type_List_Should_Return_Correct_Values(){
        List<String> typeList = bloodProductTypeService.getBloodProductTypes();
        assertTrue(typeList.contains(BloodProductType.ERYTHROCYTECONCENTRATE.getDisplayValue()));
        assertTrue(typeList.contains(BloodProductType.FRESHFROZENPLASMA.getDisplayValue()));
        assertTrue(typeList.contains(BloodProductType.PRODUCTIONPLASMA.getDisplayValue()));
        assertTrue(typeList.contains(BloodProductType.THROMBOCYTECONCENTRATE.getDisplayValue()));
    }
}

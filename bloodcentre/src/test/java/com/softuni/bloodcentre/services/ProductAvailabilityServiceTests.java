package com.softuni.bloodcentre.services;

import com.softuni.bloodcentre.base.TestBase;
import com.softuni.bloodcentre.data.models.ProductAvailability;
import com.softuni.bloodcentre.service.services.ProductAvailabilityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductAvailabilityServiceTests extends TestBase {
    @Autowired
    ProductAvailabilityService productAvailabilityService;

    @Test
    public void productAvailability_Service_Get_Availability_List_Should_Return_Correct_Values(){
        List<String> availabilityList = productAvailabilityService.getProductAvailabilityStates();
        assertTrue(availabilityList.contains(ProductAvailability.AVAILABLE.getDisplayValue()));
        assertTrue(availabilityList.contains(ProductAvailability.UNAVAILABLE.getDisplayValue()));
    }
}

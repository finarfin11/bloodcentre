package com.softuni.bloodcentre.service.impl;

import com.softuni.bloodcentre.data.models.ProductAvailability;
import com.softuni.bloodcentre.service.services.ProductAvailabilityService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductAvailabilityServiceImpl implements ProductAvailabilityService {
    @Override
    public List<String> getProductAvailabilityStates() {
        return Arrays.asList(ProductAvailability.values()).stream().map(pa -> pa.getDisplayValue()).collect(Collectors.toList());
    }
}

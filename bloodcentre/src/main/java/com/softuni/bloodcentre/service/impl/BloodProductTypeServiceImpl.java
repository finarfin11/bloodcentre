package com.softuni.bloodcentre.service.impl;

import com.softuni.bloodcentre.data.models.BloodProductType;
import com.softuni.bloodcentre.service.services.BloodProductTypeService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BloodProductTypeServiceImpl implements BloodProductTypeService {
    @Override
    public List<String> getBloodProductTypes() {
        return Arrays.asList(BloodProductType.values()).stream().map(pt -> pt.getDisplayValue()).collect(Collectors.toList());
    }
}

package com.softuni.bloodcentre.service.impl;

import com.softuni.bloodcentre.data.models.BloodType;
import com.softuni.bloodcentre.service.services.BloodTypeService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BloodTypeServiceImpl implements BloodTypeService {
    @Override
    public List<BloodType> getBloodTypes() {
        return Arrays.asList(BloodType.values());
    }
}

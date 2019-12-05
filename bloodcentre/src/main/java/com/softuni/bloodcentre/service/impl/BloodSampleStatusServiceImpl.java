package com.softuni.bloodcentre.service.impl;

import com.softuni.bloodcentre.data.models.BloodSampleStatus;
import com.softuni.bloodcentre.service.services.BloodSampleStatusService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BloodSampleStatusServiceImpl implements BloodSampleStatusService {
    @Override
    public List<BloodSampleStatus> getBloodStatusList() {
        return Arrays.asList(BloodSampleStatus.values());
    }
}

package com.softuni.bloodcentre.service.impl;

import com.softuni.bloodcentre.data.models.BloodProcessState;
import com.softuni.bloodcentre.service.services.BloodProcessStateService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BloodProcessStateServiceImpl implements BloodProcessStateService {
    @Override
    public List<BloodProcessState> getBloodStates() {
        return Arrays.asList(BloodProcessState.values());
    }
}

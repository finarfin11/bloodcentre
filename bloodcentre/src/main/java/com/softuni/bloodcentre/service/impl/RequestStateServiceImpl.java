package com.softuni.bloodcentre.service.impl;

import com.softuni.bloodcentre.data.models.RequestState;
import com.softuni.bloodcentre.service.services.RequestStateService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestStateServiceImpl implements RequestStateService {
    @Override
    public List<String> getBloodRequestStates() {
        return Arrays.asList(RequestState.values()).stream().map(rs -> rs.getDisplayValue()).collect(Collectors.toList());
    }
}

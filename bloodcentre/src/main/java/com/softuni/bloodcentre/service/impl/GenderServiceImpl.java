package com.softuni.bloodcentre.service.impl;

import com.softuni.bloodcentre.data.models.Sex;
import com.softuni.bloodcentre.service.services.GenderService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class GenderServiceImpl implements GenderService {
    @Override
    public List<Sex> getSexList() {
        return Arrays.asList(Sex.values());
    }
}

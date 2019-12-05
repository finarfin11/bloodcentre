package com.softuni.bloodcentre.web.models;

import com.softuni.bloodcentre.data.models.BloodProcessState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SetBloodStateModel {
    private BloodProcessState bloodProcessState;
}

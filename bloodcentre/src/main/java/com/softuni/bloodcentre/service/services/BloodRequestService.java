package com.softuni.bloodcentre.service.services;

import com.softuni.bloodcentre.data.models.BloodRequest;
import com.softuni.bloodcentre.web.models.EditRequestModel;
import com.softuni.bloodcentre.web.models.EditRequestPostModel;
import com.softuni.bloodcentre.web.models.RegisterBloodRequestModel;
import com.softuni.bloodcentre.web.models.ViewRequestModel;

import java.util.List;

public interface BloodRequestService {

    BloodRequest addBloodRequest(RegisterBloodRequestModel registerBloodRequestModel);

    BloodRequest findById(long id);

    List<ViewRequestModel> findAllRequests();

    EditRequestModel getEditRequestModel(long id);

    BloodRequest editRequest(EditRequestPostModel model, long id);

    void deleteRequest(long id);
}

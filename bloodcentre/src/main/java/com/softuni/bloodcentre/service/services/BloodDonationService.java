package com.softuni.bloodcentre.service.services;

import com.softuni.bloodcentre.data.models.BloodDonation;
import com.softuni.bloodcentre.service.models.BloodDonationServiceModel;
import com.softuni.bloodcentre.web.models.*;

import javax.naming.InvalidNameException;
import java.util.List;

public interface BloodDonationService {
    BloodDonation addDonation(RegisterDonationModel registerDonationModel);
    List<ViewDonationModel> findAllDonations();
    BloodDonation findById(long id);
    BloodDonation save(BloodDonation donation);
    EditDonationModel getEditDonationViewModel(long id);
    BloodDonation editDonation(EditDonationModel editDonationModel, long id);
    void deleteDonation(long id);
    List<BloodDonation> findAllCleanDonations();
    BloodDonation setBloodProcessed(long id);
    BloodDonation setBloodState(SetBloodStateModel model, long id);
}

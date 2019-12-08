package com.softuni.bloodcentre.services;

import com.softuni.bloodcentre.base.TestBase;
import com.softuni.bloodcentre.data.models.BloodProduct;
import com.softuni.bloodcentre.data.models.BloodProductType;
import com.softuni.bloodcentre.data.models.ProductAvailability;
import com.softuni.bloodcentre.data.repositories.BloodProductRepository;
import com.softuni.bloodcentre.service.services.BloodProductService;
import com.softuni.bloodcentre.web.models.EditBloodProductModel;
import com.softuni.bloodcentre.web.models.EditProductModel;
import com.softuni.bloodcentre.web.models.RegisterBloodProductModel;
import com.softuni.bloodcentre.web.models.ViewProductModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.NoResultException;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BloodProductServiceTests extends TestBase {
    private BloodProduct testProduct;
    private List<BloodProduct> testProducts;
    private RegisterBloodProductModel registerModel;
    private EditBloodProductModel editModel;
    private EditProductModel getEditModel;

    @MockBean
    BloodProductRepository bloodProductRepository;

    @Autowired
    BloodProductService bloodProductService;

    @BeforeEach
    public void init() {
        testProduct = new BloodProduct();
        testProduct.setId(1);
        testProducts = new ArrayList<>();
        registerModel = new RegisterBloodProductModel();
        editModel = new EditBloodProductModel();
        Mockito.when(bloodProductRepository.findById(testProduct.getId())).thenReturn(java.util.Optional.of(testProduct));
        Mockito.when(bloodProductRepository.save(testProduct)).thenReturn(testProduct);
        Mockito.when(bloodProductRepository.findAll()).thenReturn(testProducts);
    }

    @Test
    public void bloodProduct_Service_Find_By_Valid_Id_Should_Return_Correct_Result() {
        BloodProduct actualProduct = bloodProductService.findById(1);
        assertEquals(testProduct, actualProduct);
    }

    @Test
    public void bloodProduct_Service_Find_By_Invalid_Id_Should_Throw() {
        assertThrows(NoResultException.class, () -> bloodProductService.findById(3));
    }

    @Test
    public void bloodProduct_Service_Add_Product_Should_Work_Correctly() {
        BloodProduct newProduct = new BloodProduct();
        testProducts.add(newProduct);
        List<ViewProductModel> actualProducts = bloodProductService.findAllProducts();
        assertEquals(testProducts.size(), actualProducts.size());
    }

    @Test
    public void bloodProduct_Service_Add_Product_Should_Return_Correct_Result() {
        registerModel.setAmount(new BigDecimal(500));
        registerModel.setDateOfDonation(new Date(2019, 11, 11));
        registerModel.setType(BloodProductType.FRESHFROZENPLASMA.getDisplayValue());
        bloodProductService.addBloodProduct(registerModel);
        ArgumentCaptor<BloodProduct> argumentCaptor = ArgumentCaptor.forClass(BloodProduct.class);
        Mockito.verify(bloodProductRepository).save(argumentCaptor.capture());
        BloodProduct bloodProduct = argumentCaptor.getValue();
        assertNotNull(bloodProduct);
        assertEquals(registerModel.getType(), bloodProduct.getType().getDisplayValue());
        assertEquals(registerModel.getAmount(), bloodProduct.getAmount());
        assertEquals(registerModel.getDateOfDonation(), bloodProduct.getDateOfDonation());
    }

    @Test
    public void bloodProduct_Service_Get_Edit_Product_Model_Should_Work_Correctly() {
        getEditModel = bloodProductService.getEditDonationModel(testProduct.getId());
        assertEquals(getEditModel.getAmount(), testProduct.getAmount());
        assertEquals(getEditModel.getDateOfDonation(), testProduct.getDateOfDonation());
        assertEquals(getEditModel.getType(), testProduct.getType());
    }

    @Test
    public void bloodProduct_Service_Edit_Hospital_Should_Work_Correctly() {
        editModel.setAmount(new BigDecimal(900));
        editModel.setDateOfDonation(new Date(2018, 9, 9));
        editModel.setType(BloodProductType.PRODUCTIONPLASMA.getDisplayValue());
        editModel.setProductAvailability(ProductAvailability.AVAILABLE.getDisplayValue());
        BloodProduct editedProduct = bloodProductService.editBloodProduct(editModel, testProduct.getId());
        assertEquals(testProduct.getAmount(), editedProduct.getAmount());
        assertEquals(testProduct.getDateOfDonation(), editedProduct.getDateOfDonation());
        assertEquals(testProduct.getType(), editedProduct.getType());
        assertEquals(testProduct.getProductAvailability(), editedProduct.getProductAvailability());
    }
}

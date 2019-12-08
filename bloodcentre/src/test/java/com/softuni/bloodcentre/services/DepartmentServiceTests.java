package com.softuni.bloodcentre.services;

import com.softuni.bloodcentre.base.TestBase;
import com.softuni.bloodcentre.data.models.Department;
import com.softuni.bloodcentre.data.repositories.DepartmentRepository;
import com.softuni.bloodcentre.service.models.DepartmentServiceModel;
import com.softuni.bloodcentre.service.models.RegisterDepartmentServiceModel;
import com.softuni.bloodcentre.service.services.DepartmentService;
import com.softuni.bloodcentre.web.models.EditDepartmentModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;

import javax.persistence.NoResultException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class DepartmentServiceTests extends TestBase {
    private Department testDepartment;
    private RegisterDepartmentServiceModel registerModel;
    private DepartmentServiceModel serviceModel;
    private EditDepartmentModel editModel;
    private List<Department> testDepartments;

    @MockBean
    DepartmentRepository departmentRepository;

    @Autowired
    DepartmentService departmentService;

    @BeforeEach
    public void init() {
        testDepartment = new Department();
        testDepartment.setId(1);
        testDepartment.setName("name");
        serviceModel = new DepartmentServiceModel();
        registerModel = new RegisterDepartmentServiceModel();
        editModel = new EditDepartmentModel();
        testDepartments = new ArrayList<>();
        Mockito.when(departmentRepository.findById(testDepartment.getId())).thenReturn(java.util.Optional.of(testDepartment));
        Mockito.when(departmentRepository.save(testDepartment)).thenReturn(testDepartment);
        Mockito.when(departmentRepository.findByName("name")).thenReturn(java.util.Optional.ofNullable(testDepartment));
        Mockito.when(departmentRepository.findAll()).thenReturn(testDepartments);
    }


    @Test
    public void department_Service_Find_By_Name_Should_Return_Correct_Result() {
        Department actualDepartment = departmentService.findByName("name");
        assertEquals(testDepartment, actualDepartment);
    }

    @Test
    public void department_Service_Find_By_Name_Should_Throw_When_Not_Exists() {
        assertThrows(NoResultException.class, () -> departmentService.findByName("missingName"));
    }

    @Test
    public void department_Service_Add_Department_Should_Work_Correctly() {
        testDepartments.add(new Department());
        List<DepartmentServiceModel> actualDepartments = departmentService.findAllDepartments();
        assertEquals(testDepartments.size(), actualDepartments.size());
    }

    @Test
    public void department_Service_Add_Should_Throw_When_Name_Exists() {
        registerModel.setName("name");
        assertThrows(DuplicateKeyException.class, () -> departmentService.addDepartment(registerModel));
    }

    @Test
    public void department_Service_Edit_Should_Work_Correctly() {
        editModel.setName("newDepartment");
        Department editedDepartment = departmentService.editDepartment(editModel, 1);
        assertEquals(editedDepartment.getName(), testDepartment.getName());
    }

    @Test
    public void department_Service_Get_Edit_Model_Should_Work_Correctly() {
        editModel = departmentService.getEditDepartmentModel(1);
        assertEquals(editModel.getName(), testDepartment.getName());
    }
}

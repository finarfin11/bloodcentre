package com.softuni.bloodcentre.service.services;

import com.softuni.bloodcentre.data.models.Department;
import com.softuni.bloodcentre.service.models.DepartmentServiceModel;
import com.softuni.bloodcentre.service.models.RegisterDepartmentServiceModel;
import com.softuni.bloodcentre.web.models.EditDepartmentModel;

import java.util.List;

public interface DepartmentService {
    Department addDepartment(RegisterDepartmentServiceModel model);
    List<DepartmentServiceModel> findAllDepartments();
    Department findByName(String departmentName);
    EditDepartmentModel getEditDepartmentModel(long id);
    Department editDepartment(EditDepartmentModel model, long id);
    void deleteDepartment(long id);
}

package com.softuni.bloodcentre.service.impl;

import com.softuni.bloodcentre.data.models.Department;
import com.softuni.bloodcentre.data.repositories.DepartmentRepository;
import com.softuni.bloodcentre.service.models.DepartmentServiceModel;
import com.softuni.bloodcentre.service.models.RegisterDepartmentServiceModel;
import com.softuni.bloodcentre.service.services.DepartmentService;
import com.softuni.bloodcentre.web.models.EditDepartmentModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Department addDepartment(RegisterDepartmentServiceModel model) {
        return this.departmentRepository.save(this.modelMapper.map(model, Department.class));
    }

    @Override
    public List<DepartmentServiceModel> findAllDepartments() {
        return this.departmentRepository.findAll().stream()
                .map(department -> this.modelMapper.map(department, DepartmentServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Department findByName(String departmentName) {
        return departmentRepository.findByName(departmentName);
    }

    @Override
    public EditDepartmentModel getEditDepartmentModel(long id) {
        Department department = this.departmentRepository.findById(id).orElseThrow(() -> new NoResultException("Department with the given id was not found!"));
        DepartmentServiceModel serviceModel = this.modelMapper.map(department, DepartmentServiceModel.class);
        return this.modelMapper.map(serviceModel, EditDepartmentModel.class);
    }

    @Override
    public Department editDepartment(EditDepartmentModel model, long id) {
        Department department = this.departmentRepository.findById(id).orElseThrow(() -> new NoResultException("Department with the given id was not found!"));
        department.setName(model.getName());
        return this.departmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(long id) {
        Department department = this.departmentRepository.findById(id).orElseThrow(() -> new NoResultException("Department with the given id was not found!"));
        this.departmentRepository.delete(department);
    }
}

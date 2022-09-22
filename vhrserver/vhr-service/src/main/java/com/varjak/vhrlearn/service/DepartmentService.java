package com.varjak.vhrlearn.service;

import com.varjak.vhrlearn.mapper.DepartmentMapper;
import com.varjak.vhrlearn.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;


    public List<Department> getAllDepartment() {
        return departmentMapper.getAllDepartmentByParentId(-1);
    }

    public void addDepartment(Department department) {
        department.setEnabled(true);
        departmentMapper.addDepartment(department);
    }

    public void delDepartment(Department department) {
        departmentMapper.delDepartment(department);
    }
}

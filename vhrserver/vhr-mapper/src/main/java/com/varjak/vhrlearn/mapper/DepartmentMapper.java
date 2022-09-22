package com.varjak.vhrlearn.mapper;

import com.varjak.vhrlearn.model.Department;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    List<Department> getAllDepartmentByParentId(Integer pid);

    //调用存储过程
    void addDepartment(Department department);

    //调用存储过程，存储过程中会判断是否有其他依赖
    void delDepartment(Department department);
}
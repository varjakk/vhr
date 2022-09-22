package com.varjak.vhrlearn.mapper;

import com.varjak.vhrlearn.model.Employee;
import com.varjak.vhrlearn.model.Employeeec;
import com.varjak.vhrlearn.model.RespPageBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface EmployeeecMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employeeec record);

    int insertSelective(Employeeec record);

    Employeeec selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employeeec record);

    int updateByPrimaryKey(Employeeec record);
}
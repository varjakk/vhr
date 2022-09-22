package com.varjak.vhrlearn.service;

import com.varjak.vhrlearn.mapper.EmployeeMapper;
import com.varjak.vhrlearn.model.Employee;
import com.varjak.vhrlearn.model.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    public RespPageBean getEmployeeByPage(Integer page, Integer size, String keywords) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> employees = employeeMapper.getEmployeeByPage(page, size, keywords);
        Long total = employeeMapper.getTotal(keywords);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(employees);
        respPageBean.setTotal(total);
        return respPageBean;
    }

    public Integer addEmp(Employee employee) {
        return employeeMapper.insertSelective(employee);
    }

    public Integer getMaxWorkID() {
        return employeeMapper.getMaxWorkID();
    }

    public Integer deleteEmpById(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    public int updateEmp(Employee employee) {
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }
}

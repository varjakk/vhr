package com.varjak.vhrlearn.service;

import com.varjak.vhrlearn.mapper.SalaryMapper;
import com.varjak.vhrlearn.model.Salary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component
public class SalaryService {
    @Autowired
    SalaryMapper salaryMapper;

    public List<Salary> getAllSalaries() {
        return salaryMapper.getAllSalaries();
    }

    public int addSalary(Salary salary) {
        salary.setCreateDate(new Date());
        return salaryMapper.insertSelective(salary);
    }

    public int deleteSalary(Integer id) {
        return salaryMapper.deleteByPrimaryKey(id);
    }

    public int updateSalary(Salary salary) {
        return salaryMapper.updateByPrimaryKeySelective(salary);
    }
}

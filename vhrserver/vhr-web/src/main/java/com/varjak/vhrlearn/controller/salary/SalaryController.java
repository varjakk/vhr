package com.varjak.vhrlearn.controller.salary;

import com.varjak.vhrlearn.model.RespBean;
import com.varjak.vhrlearn.model.Salary;
import com.varjak.vhrlearn.service.SalaryService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.Buffer;
import java.util.List;

@RestController
@RequestMapping("/salary/sob")
public class SalaryController {
    @Autowired
    SalaryService salaryService;

    @GetMapping("/")
    public List<Salary> getAllSalaries() {
        return salaryService.getAllSalaries();
    }

    @PostMapping("/")
    public RespBean addSalary(@RequestBody Salary salary) {
        if (salaryService.addSalary(salary) == 1) {
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteSalary(@PathVariable Integer id){
        if (salaryService.deleteSalary(id) == 1) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @PutMapping("/")
    public RespBean updateSalary(@RequestBody Salary salary){
        if (salaryService.updateSalary(salary) == 1) {
            return RespBean.ok("修改成功");
        }
        return RespBean.error("修改失败");
    }
}

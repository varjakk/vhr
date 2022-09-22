package com.varjak.vhrlearn.controller.system.basic;

import com.varjak.vhrlearn.model.Department;
import com.varjak.vhrlearn.model.RespBean;
import com.varjak.vhrlearn.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @GetMapping("/")
    public List<Department> getAllDepartment() {
        return departmentService.getAllDepartment();
    }

    @PostMapping("/")
    public RespBean addDepartment(@RequestBody Department department) {
        departmentService.addDepartment(department);
        if (department.getResult() == 1) {
            return RespBean.ok("添加成功", department);
        }
        return RespBean.error("添加失败");
    }

    @DeleteMapping("/{id}")
    public RespBean delDepartment(@PathVariable Integer id) {
        Department department = new Department();
        department.setId(id);
        departmentService.delDepartment(department);
        if (department.getResult() == -2) {
            RespBean.error("该部门下存在子部门，删除失败");
        } else if (department.getResult() == -1) {
            RespBean.error("该部门下存在员工，删除失败");
        } else if (department.getResult() == 1) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }
}

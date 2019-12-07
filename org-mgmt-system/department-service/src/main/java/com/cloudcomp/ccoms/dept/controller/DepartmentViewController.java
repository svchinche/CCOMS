package com.cloudcomp.ccoms.dept.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudcomp.ccoms.dept.service.DepartmentService;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
@RequestMapping(value = { "/" })
public class DepartmentViewController {

    @Autowired
    DepartmentService deptSvc;

    @GetMapping("/pretty")
    public String showSignUpForm(Model model) {
        model.addAttribute("depts", deptSvc.getDeptswithEmps());
        return "show_pretty_output";
    }

}

package com.cloudcomp.ccoms.emp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudcomp.ccoms.emp.dao.EmployeeRepository;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
@RequestMapping(value = { "/" })
public class EmployeeViewController {

    @Autowired
    EmployeeRepository empRep;

    @GetMapping("/pretty")
    public String showSignUpForm(Model model) {
        model.addAttribute("emps", empRep.findAll());
        return "show_pretty_output";
    }

}

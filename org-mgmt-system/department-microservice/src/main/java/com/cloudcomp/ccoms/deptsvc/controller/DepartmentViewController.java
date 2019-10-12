package com.cloudcomp.ccoms.deptsvc.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudcomp.ccoms.deptsvc.model.Department;

@Controller
@RequestMapping(value = { "/", "/department" })
public class DepartmentViewController {

    @Autowired
    DepartmentRestController deptRC;


    @GetMapping("/pretty")
    public String showSignUpForm( Model model) {
        model.addAttribute("depts", deptRC.getDeptswithEmps());
        return "show_pretty_output";
    }

    
   
}

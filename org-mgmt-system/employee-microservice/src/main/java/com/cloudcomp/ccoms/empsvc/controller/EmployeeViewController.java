package com.cloudcomp.ccoms.empsvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudcomp.ccoms.empsvc.repository.EmployeeRepository;


@Controller
@RequestMapping(value={"/","/employee"})
public class EmployeeViewController {

    @Autowired
    EmployeeRepository empRep;
    @GetMapping("/pretty")
    public String showSignUpForm(Model model) {
        model.addAttribute("emps", empRep.findAll());
        return "show_pretty_output";
    }
    
}

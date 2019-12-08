package com.cloudcomp.ccoms.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudcomp.ccoms.org.service.OrganizationService;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
@RequestMapping(value = { "/" })
public class OrganizationViewController {

    @Autowired
    OrganizationService orgSvc;

    @GetMapping("/pretty")
    public String showSignUpForm(Model model) {
        model.addAttribute("orgs", orgSvc.getDeptsEmpsAndOrgsInfo());
        return "show_pretty_output";
    }

}

package com.cloudcomp.ccoms.orgsvc.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_EMPTY)
public class Department {

    
    private int id;
    private String name;
    private int orgId;
    private List<Employee> emps;


    public Department() {
    }

    public Department(int deptId , int orgId , String deptName) {        
        super();
        this.id=deptId;
        this.orgId=orgId;
        this.name = deptName;
    }

    public String getDeptName() {
        return name;
    }

    public void setDeptName(String deptName) {
        this.name = deptName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public List<Employee> getEmps() {
        return emps;
    }

    public void setEmps(List<Employee> emps) {
        this.emps = emps;
    }   
  
}

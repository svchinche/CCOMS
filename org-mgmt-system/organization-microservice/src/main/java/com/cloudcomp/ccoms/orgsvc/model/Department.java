package com.cloudcomp.ccoms.orgsvc.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_EMPTY)
public class Department {

    
    private int deptId;
    private String deptName;
    private int orgId;
    private List<Employee> emps;


    public Department() {
    }

    public Department(int deptId , int orgId , String deptName) {        
        super();
        this.deptId=deptId;
        this.orgId=orgId;
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }


    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public void setEmps(List<Employee> emps) {
        this.emps = emps;
    }


    public List<Employee> getEmps() {
        return emps;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }


}

package com.cloudcomp.ccoms.dept.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "department")
@JsonInclude(value = Include.NON_EMPTY)
public class Department {

    @Id
    @Indexed
    private Long id;
    private String name;
    private int orgId;
    private List<Employee> emps = new ArrayList<>();

    public Department(Long deptId, int orgId, String name) {
        super();
        this.id = deptId;
        this.orgId = orgId;
        this.name = name;
    }

}

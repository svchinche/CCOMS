package com.cloudcomp.ccoms.emp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@ApiModel(description = "All details about the Employee. ")
@Document(collection = "employee")
public class Employee {


    @Id
    @Indexed
    private Long id;

    private String name;
    private int age;
    private String position;

    int deptId;
    int orgId;

}

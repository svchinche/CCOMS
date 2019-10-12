package com.cloudcomp.ccoms.orgsvc.model;


import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@Document(collection = "employee")
@JsonInclude(value = Include.NON_EMPTY)
public class Employee {

    private int id;
    private String name;
    private int age;
    private String position;
    
    public Employee(){
        
    }
    public Employee(int id, String name, int age, String position) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.position = position;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    
    
   
    
}

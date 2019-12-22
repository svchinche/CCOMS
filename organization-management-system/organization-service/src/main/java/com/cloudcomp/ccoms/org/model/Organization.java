package com.cloudcomp.ccoms.org.model;

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
@Document(collection = "organization")
@JsonInclude(value = Include.NON_EMPTY)
public class Organization {

    @Id
    @Indexed
    private Long id;
    private String name;
    private String address;
    private List<Department> depts;
    private Department dept;
    private List<Employee> emps;
        
    public Organization() {  
    }
    
    public Organization(Long orgId,String name, String address){
        this.id=orgId;
        this.name=name;
        this.address=address;
    }
    
}

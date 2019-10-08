package com.org.java.spring.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.org.java.spring.bean.Organization;
import com.org.java.spring.service.OrganizationService;

@RestController
@RequestMapping(value={"/","/organization"})
public class OrganizationController {
	@Autowired
	OrganizationService OrganizationService;
	@GetMapping(value="/",produces = MediaType.APPLICATION_JSON_VALUE)
	public String get(){
		return "Please give url as Spring-Boot-Rest/Organization/get";
		
	}
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Organization> getOrganizationById(@PathVariable("id") String id) {
        System.out.println("Fetching Organization with id " + id);
        Optional<Organization> Organization = OrganizationService.findById(id);
        if (Organization == null) {
            return new ResponseEntity<Organization>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Organization>(HttpStatus.OK);
    }
    
	 @PostMapping(value="/create",headers="Accept=application/json")
	 public ResponseEntity<Void> createOrganization(@RequestBody Organization Organization, UriComponentsBuilder ucBuilder){
	     System.out.println("Creating Organization "+Organization.getName());
	     OrganizationService.createOrganization(Organization);
	     HttpHeaders headers = new HttpHeaders();
	     headers.setLocation(ucBuilder.path("/Organization/{id}").buildAndExpand(Organization.getId()).toUri());
	     return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	 }

	 @GetMapping(value="/get", headers="Accept=application/json")
	 public List<Organization> getAllOrganization() {	 
	  List<Organization> tasks=OrganizationService.getOrganization();
	  return tasks;
	
	 }

	@PutMapping(value="/update", headers="Accept=application/json")
	public ResponseEntity<String> updateOrganization(@RequestBody Organization currentOrganization)
	{
		System.out.println("sd");
	Optional<Organization> Organization = OrganizationService.findById(currentOrganization.getId());
	if (Organization==null) {
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}
	OrganizationService.update(currentOrganization, currentOrganization.getId());
	return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{id}", headers ="Accept=application/json")
	public ResponseEntity<Organization> deleteOrganization(@PathVariable("id") String id){
		Optional<Organization> Organization = OrganizationService.findById(id);
		if (Organization == null) {
			return new ResponseEntity<Organization>(HttpStatus.NOT_FOUND);
		}
		OrganizationService.deleteOrganizationById(id);
		return new ResponseEntity<Organization>(HttpStatus.NO_CONTENT);
	}
	
	@PatchMapping(value="/{id}", headers="Accept=application/json")
	public ResponseEntity<Organization> updateOrganizationPartially(@PathVariable("id") String id, @RequestBody Organization currentOrganization){
		Optional<Organization> Organization = OrganizationService.findById(id);
		if(Organization ==null){
			return new ResponseEntity<Organization>(HttpStatus.NOT_FOUND);
		}
		Organization org =	OrganizationService.updatePartially(currentOrganization, id);
		return new ResponseEntity<Organization>(org, HttpStatus.OK);
	}
}

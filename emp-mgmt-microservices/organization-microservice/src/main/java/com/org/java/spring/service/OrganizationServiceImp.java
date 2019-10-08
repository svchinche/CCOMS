package com.org.java.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.org.java.spring.bean.Organization;
import com.org.java.spring.repository.OrganizationRepository;

@Service
@Transactional
public class OrganizationServiceImp implements OrganizationService {
	@Autowired
	OrganizationRepository orgRepository;

	public void createOrganization(Organization org) {
		// TODO Auto-generated method stub
	    orgRepository.save(org);
	}

	public List<Organization> getOrganization() {
		// TODO Auto-generated method stub
		return (List<Organization>) orgRepository.findAll();
	}

	public Optional<Organization> findById(String id) {
		// TODO Auto-generated method stub
		return orgRepository.findById(id);
	}

	public Organization update(Organization user, String l) {
		// TODO Auto-generated method stub
		return orgRepository.save(user);
	}

	public void deleteOrganizationById(String id) {
		// TODO Auto-generated method stub
	    orgRepository.deleteById(id);
	}

	public Organization updatePartially(Organization org, String id) {
		// TODO Auto-generated method stub
		return orgRepository.save(org);
	}



}

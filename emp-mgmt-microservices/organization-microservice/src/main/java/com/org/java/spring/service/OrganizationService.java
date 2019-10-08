package com.org.java.spring.service;

import java.util.List;
import java.util.Optional;

import com.org.java.spring.bean.Organization;

public interface OrganizationService {
	public void createOrganization(Organization user);
	public List<Organization> getOrganization();
	public Optional<Organization> findById(String id);
	public Organization update(Organization user, String l);
	public void deleteOrganizationById(String id);
	public Organization updatePartially(Organization user, String id);
}

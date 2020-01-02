Feature: Testing an Employee Management Service REST API's - CRUD Operations
    Users should be able to submit GET,POST,DELETE,PUT requests to a web service.

Background:
    Given: Following Employee information is availale in store
      | Id | Name   | Age | Position          | Dept_id | Org_id |
      | 1  | Suyog  | 29  | DevOps Engineer   | 21      | 2      |
      | 2  | Sachin | 29  | DevOps Engineer-2 | 11      | 1      |
      | 3  | Suraj  | 29  | DevOps Engineer-2 | 13      | 1      |
	
Scenario: Get Employee information using Emp-id
    When I set REST API URL with endpoint "1" 
	  And Send HTTP GET request
    Then I should get valid HTTP response code 200 for "GET." 
	  And Response BODY "GET" with employee "1" information.
	  
Scenario: Get Employee information using Dept-id
    When I set REST API URL with URL mapping dept/"11" 
	  And Send GET HTTP request
    Then I should get valid HTTP response code 200 for "GET." 
	  And Response BODY "GET" with Employee information belongs to "11" department.
	  
Scenario: Get Employee information using Org-id
    When I set REST API with URL mapping org/"1"
	  And Send GET HTTP request
    Then I should get valid HTTP response code 200 for "GET." 
	  And Response BODY "GET" with Employee information belongs to "1" Organization.
	  
Scenario: Get all employee information
    When I set REST API with URL mapping get
	  And Send GET HTTP request
    Then I should get valid HTTP response code 200 for "GET." 
	  And Response BODY "GET" with all Employee information.
	  
Scenario: Update Existing Employee information
    When I set REST API with update resource
    And formed JSON data of "employee information"
	  And Send PUT request
    Then I should get valid HTTP response code 200 for "PUT." 

Scenario: Update Employee information which is not available
    When I set REST API with update resource
    And formed JSON data of employee information
    But Employee is not available in available store.
	  And Send PUT request
    Then I should get invalid HTTP response code 404 for "PUT." 
	  

Scenario: Add one Employee information at a time.
    When I set HTTP REST API with add resource 
    And formed JSON data of employee information
	  And Send POST HTTP request
    Then I should get valid HTTP response code 200 for "POST" 
	

Scenario: Add all employee information at once.
    When I set HTTP REST API with add resource 
    And formed JSON data of employees information
	  And Send POST HTTP request
    Then I should get valid HTTP response code 200 for "POST"
	  
Scenario: Delete employee information using EmpId.
    When I set REST API with endpoint "1"
	  And Send DELETE HTTP request
    Then I should get valid HTTP response code 200 for "GET." 
	  And Response BODY "GET" with employee "1" information.
	  

Scenario: Error message should shown when no api input is provided.
    When I set REST API without resource
	  And Send GET HTTP request
    Then I should get valid HTTP response code 200 for "GET." 
    And Proper error message should be shown


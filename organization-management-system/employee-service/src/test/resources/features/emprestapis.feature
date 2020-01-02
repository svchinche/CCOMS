Feature: Testing an Employee Management Service Rest API's - CRUD Operations
Users should be able to submit GET,POST,DELETE,PUT requests to a web service.

Background:
	Given I have added two employees information in store

Scenario: Get Employee Information
    When I set GET rest API with endpoint "1"
    And I Set HEADER param request content type as "application/json." 
	  And Send GET HTTP request
    Then I should get valid HTTP response code 200 for "GET." 
	  And Response BODY "GET" with employee "1" information.

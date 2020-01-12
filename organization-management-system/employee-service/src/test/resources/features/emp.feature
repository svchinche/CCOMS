Feature: Testing an Employee Management Service REST API's - CRUD Operations
   Users should be able to submit GET,POST,DELETE,PUT requests to a web service

Background: 
Given Following Employee information is available in store
  | id | name   | age | position          | deptId  | orgId  |
  |  1 | Suyog  |  29 | DevOps Engineer   |  21     |  2     |
  |  2 | Sachin |  29 | DevOps Engineer-2 |  11     |  1     |
  |  3 | Suraj  |  29 | DevOps Engineer-2 |  13     |  1     |

Scenario: Get Employee information using Emp-id
When I set HTTP GET request RESTful API with URI 1
Then I should get valid HTTP response code 200 of GET request for respective employee information

Scenario: Get Employee information using Dept-id
When I set HTTP GET request RESTful API with dept with 11 id URI
Then I should get valid HTTP response code 200 of GET request for respective dept

Scenario: Get Employee information using Org-id
When I set HTTP GET request RESTful API with org with 2 id URI
Then I should get valid HTTP response code 200 of GET request for respective org

Scenario: Get all Employees information
When I set HTTP GET request RESTful API with get URI
Then I should get valid HTTP response code 200 of GET request for all emps

Scenario: Get all Employees information with Pretty
When I set HTTP GET request RESTful API with pretty URI
Then I should get valid HTTP response code 200 of GET request with all employees information in UI

Scenario: Add one Employee information at a time
When I set HTTP POST request RESTful API with resource URI
Then I should get valid HTTP response code 200 of POST request to add one employee at a time

Scenario: Add all Employees information at once
When I set HTTP POST request RESTful API with resource URI - all at once
Then I should get valid HTTP response code 200 of POST request to add all employee at once

Scenario: Delete Employee information using EmpId
When I set HTTP DELETE REST API with URI 1
Then I should get valid HTTP response code 200 of DELETE request to delete Employee

Scenario: Error message should shown when no api input is provided
When I set HTTP GET request without any resource mentioned in API
Then I should get valid HTTP response code 200 for that request with warning message

Scenario: All Geters and Setters should be invoked to improve code quality
When I initialize all Getters and Setters
Then All Getters and Setters should be initialized 

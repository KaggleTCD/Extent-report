Feature: Free CRM Login Action

Scenario: Free CRM Login Test Scenario
	Given User is already on Login Page
	When Title of login page is Free CRM
	Then User enters username and password
	And User clicks on login button
	And user is on home page
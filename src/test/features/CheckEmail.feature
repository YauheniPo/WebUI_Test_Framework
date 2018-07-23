@check_email
Feature: Check recipient and sender email letter

  Background:
    Given test data
    And connecting 'sender' email store
    And connecting 'recipient' email store
    And deleting email data
    And creating letter with text 'epam_e_popovich'
    And sending letter to recipient
    And sending letter in sender send folder

  Scenario: Checking email data of letter
    Given tut.by Home Page is opened
    When I did click to 'email'
    Then Authorize Email Form is opened
    When I was authorized as 'sender'
    Then Email Page is opened
    When I did click to 'sent' folder
    And I chose 'sender' last letter
    Then I chose 'logout' in account menu
    When I opened starting Home Page
    Then tut.by Home Page is opened
    When I did click to 'email'
    Then Authorize Email Form is opened
    When I was authorized as 'recipient'
    Then Email Page is opened
    When I did click to 'inbox' folder
    And I chose 'recipient' last letter
    When I chose 'logout' in account menu
    Then I checked letters data
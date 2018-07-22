@check_email
Feature: Check recipient and sender email letter

  Background:
    Given test data
    And 'sender' connecting email store
    And 'recipient' connecting email store
    And deleting email data
    And generation letter with text 'epam_e_popovich'
    And sending letter to recipient
    And sending letter in send folder

  Scenario: Checking email data of letter
    Given tut.by Home Page is opened


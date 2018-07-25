@check_email
Feature: Check recipient and sender email letter

  Scenario Outline: Checking email data of letter
    Given test data from "<datafile>"

#    Given sending email letter by test data from "<datafile>"
#    When I sing in 'sender' email account
#    Then getting 'sent' data last letter
#    And doing logout
#    When I sing in 'recipient' email account
#    Then getting 'inbox' data last letter
#    And doing logout
#    And I checked letters data

    And connecting 'sender' email store
    And connecting 'recipient' email store
    And deleting email data
    And creating letter with text 'epam_e_popovich'
    And sending letter to recipient
    And sending letter in sender send folder
    When tut.by Home Page is opened
    And I did click to 'email'
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

    Examples:
      | datafile     |
      | accounts.csv |
      | accounts.xml |
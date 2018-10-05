@check_email_data_provider
Feature: Check recipient and sender email letter

  Scenario: Checking email data of letter
    Given sending email letter by test data from file and with text 'epam_e_popovich'
    When tut.by Home Page is opened
    Then the 'sender' logged in
    When getting a data of the last letter from the 'sent' folder of 'sender'
    Then 'sender' doing logout
    Then the 'recipient' logged in
    When getting a data of the last letter from the 'inbox' folder of 'recipient'
    Then 'recipient' doing logout
    And checking data letters
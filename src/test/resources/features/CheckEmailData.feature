@check_email
Feature: Check recipient and sender email letter

  Scenario: Checking email data of letter
    Given email test data:
      | Sender Mail Login       | Sender Mail Password | Recipient Mail Login    | Recipient Mail Password |
      | epam1.popovich@tut.by   | epampopovich         | epam2.popovich@tut.by   | epam2popovich           |
    And sending email letter by test data from file and with text 'epam_e_popovich'
    When tut.by Home Page is opened
    Then the 'sender' logged in
    When getting a data of the last letter from the 'sent' folder of 'sender'
    Then 'sender' doing logout
    Then the 'recipient' logged in
    When getting a data of the last letter from the 'inbox' folder of 'recipient'
    Then 'recipient' doing logout
    And checking data letters

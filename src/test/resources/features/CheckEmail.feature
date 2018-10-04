@check_email
Feature: Check recipient and sender email letter

  Scenario Outline: Checking email data of letter
    Given data the sender: "<sender_login>", "<sender_password>", and the recipient: "<recipient_login>", "<recipient_password>"
    And sending email letter by test data from file and with text 'epam_e_popovich'
    When tut.by Home Page is opened
    Then the 'sender' logged in
    When getting a data of the last letter from the 'sent' folder of 'sender'
    Then 'sender' doing logout
    Then the 'recipient' logged in
    When getting a data of the last letter from the 'inbox' folder of 'recipient'
    Then 'recipient' doing logout
    And checking data letters

    Examples:
      | sender_login          | sender_password | recipient_login       | recipient_password |
      | epam1.popovich@tut.by | epampopovich    | epam2.popovich@tut.by | epam2popovich      |
      | epam2.popovich@tut.by | epam2popovich   | epam1.popovich@tut.by | epampopovich       |
# WebUI_Test_Framework

Test - https://github.com/YauheniPo/WebUI_Test_Framework/blob/master/src/test/java/demo/test/tests/TutByEmailTest.java

Test Framework - https://github.com/YauheniPo/WebUI_Test_Framework/tree/master/src/test/java/webdriver

Framework and Test Properties - https://github.com/YauheniPo/WebUI_Test_Framework/tree/master/src/test/resources

Scenario:
Manually creates two e-mail boxes at tut.by
Test steps:
1. Send e-mail from account 1 to account 2 (use Java Mail API)
2. Log in to acc1 from UI. Check e-mail in Sent present
3. Log in to acc2 from UI. Check e-mail in Inbox

Used TestNG, Maven
Store accounts in xml, csv and any SQL DB (in all 3 data storages)
Tests should provide detailed log, make screenshots on failure and provide results.
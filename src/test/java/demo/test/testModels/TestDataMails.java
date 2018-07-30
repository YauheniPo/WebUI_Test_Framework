package demo.test.testModels;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The type Test data mails.
 */
@Getter
@AllArgsConstructor
public class TestDataMails {
    private final String senderMailLogin;
    private final String senderMailPassword;
    private final String recipientMailLogin;
    private final String recipientMailPassword;
}
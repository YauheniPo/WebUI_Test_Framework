package demo.test.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * The type Mail.
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Mail {
    private final String subject;
    private final String text;
    private final String fromAddress;
    private final String toAddress;
}
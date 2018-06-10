package demo.test.Models;

import java.util.Objects;

/**
 * The type Mail.
 */
public class Mail {
    private String subject;
    private String text;
    private String fromAddress;
    private String toAddress;

    /**
     * Instantiates a new Mail.
     *
     * @param subject     the subject
     * @param text        the text
     * @param fromAddress the from address
     * @param toAddress   the to address
     */
    public Mail(String subject, String text, String fromAddress, String toAddress) {
        this.subject = subject;
        this.text = text;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mail mail = (Mail) o;
        return Objects.equals(subject, mail.subject) &&
                Objects.equals(text, mail.text) &&
                Objects.equals(fromAddress, mail.fromAddress) &&
                Objects.equals(toAddress, mail.toAddress);
    }

    @Override
    public String toString() {
        return "Mail{" +
                "subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", fromAddress='" + fromAddress + '\'' +
                ", toAddress='" + toAddress + '\'' +
                '}';
    }
}
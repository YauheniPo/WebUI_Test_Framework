package demo.test.Models;

import java.util.Objects;

public class Mail {
    private String subject;
    private String text;
    private String fromAddress;
    private String toAddress;

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
}
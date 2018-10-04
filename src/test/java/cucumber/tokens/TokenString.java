package cucumber.tokens;

import cucumber.transform.Processor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class TokenString {
    private Object value;

    public TokenString(String value) {
        this.value = new Processor().transform(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public int toInteger() {
        return Integer.valueOf(value.toString());
    }
}
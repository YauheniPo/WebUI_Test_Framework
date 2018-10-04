package cucumber.transform;

public class Processor extends cucumber.api.Transformer<String> {

    @Override
    public String transform(String token) {
        return new Transformer().transform(token);
    }
}
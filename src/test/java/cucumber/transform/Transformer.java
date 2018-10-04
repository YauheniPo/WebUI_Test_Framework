package cucumber.transform;

import cucumber.transform.impl.TestInfoTransformerImpl;

import java.util.ArrayList;
import java.util.List;

public class Transformer {
    private final List<ITransformer> transformers;

    public Transformer(List<ITransformer> transformers) {
        this.transformers = transformers;
        transformers.add(new TestInfoTransformerImpl());
    }

    public Transformer() {
        this(new ArrayList<>());
    }

    public String transform(String token) {
        for (ITransformer transformer : transformers) {
            if (token.contains(transformer.getToken())) {
                return (String) transformer.transformData(token);
            }
        }
        return token;
    }
}
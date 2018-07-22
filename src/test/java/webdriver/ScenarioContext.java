package webdriver;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Scenario context.
 */
public class ScenarioContext {
    private static volatile ScenarioContext contextInstance = null;
    private static final Map<String, Object> context = new HashMap<>();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ScenarioContext getInstance() {
        if (contextInstance == null) {
            synchronized (context) {
                if (contextInstance == null)
                    contextInstance = new ScenarioContext();
            }
        }
        return contextInstance;
    }

    /**
     * Instantiates a new Scenario context.
     */
    private ScenarioContext() {
    }

    /**
     * Sets context.
     *
     * @param <T>    the type parameter
     * @param name   the name
     * @param object the object
     */
    public <T> void setContext(String name, T object) {
        context.put(name, object);
    }

    /**
     * Gets context obj.
     *
     * @param name the name
     * @return the context obj
     */
    public Object getContextObj(String name) {
        return context.get(name);
    }

    /**
     * Clear scenario.
     */
    public void clearScenario() {
        context.clear();
    }
}

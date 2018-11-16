package webdriver;

import org.aeonbits.owner.ConfigFactory;
import webdriver.resources.IVisualEnvironment;

public class VisualTest extends BaseDriverTest {
    protected IVisualEnvironment visualEnv = ConfigFactory.create(IVisualEnvironment.class);

}

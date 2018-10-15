package webdriver;

import org.aeonbits.owner.ConfigFactory;
import webdriver.resources.IVisualEnvironment;

public class VisualTest extends BaseTest {
    protected IVisualEnvironment visualEnv = ConfigFactory.create(IVisualEnvironment.class);

}

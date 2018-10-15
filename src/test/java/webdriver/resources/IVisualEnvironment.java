package webdriver.resources;

import org.aeonbits.owner.Config;

import static webdriver.resources.Constants.PROPERTIES_VISUAL;

@Config.Sources(value = {"classpath:" + PROPERTIES_VISUAL})
public interface IVisualEnvironment extends Config {

    @DefaultValue("screenshots")
    String screenshotsDir();

    @DefaultValue(".png")
    String imageExtention();

    @DefaultValue("artifacts")
    String snapshotsEqualsResultsPath();
}
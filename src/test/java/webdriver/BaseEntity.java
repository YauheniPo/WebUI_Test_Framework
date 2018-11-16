package webdriver;

import com.epam.reportportal.testng.ReportPortalTestNGListener;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.testng.IInvokedMethodListener;
import org.testng.IReporter;
import org.testng.annotations.Listeners;

/**
 * The type Base entity.
 */
@Listeners({ReportPortalTestNGListener.class})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseEntity implements IInvokedMethodListener, IReporter {
    protected static final Logger LOGGER = Logger.getInstance();
    protected static final AssertWrapper ASSERT_WRAPPER = AssertWrapper.getInstance();
}
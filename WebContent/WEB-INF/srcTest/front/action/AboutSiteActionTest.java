package front.action;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.property.PropertyTester.*;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import front.actionform.FrontAboutSiteActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class AboutSiteActionTest {

    public static class executeTest extends MockStrutsTestCase {

        private static final String ACTION_PATH = "/aboutSite.do";
        private static final String SITE_URL
                = getProperty("environment.properties").getProperty("site.url");

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccess() {
            actionPerform();

            verifyForward("success");

            FrontAboutSiteActionForm actual = (FrontAboutSiteActionForm) getActionForm();

            assertThat(actual.getSiteUrl(), is(SITE_URL));
        }

    }

}

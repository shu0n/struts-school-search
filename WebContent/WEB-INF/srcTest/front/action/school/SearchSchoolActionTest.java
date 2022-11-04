package front.action.school;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.property.PropertyTester.*;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import front.actionform.school.FrontSearchSchoolActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class SearchSchoolActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SearchSchoolActionTest.xml") {
        @Override
        protected void before() throws Exception {
            executeQuery("DELETE FROM mail_templetes");
            executeQuery("DELETE FROM contacts");
            executeQuery("DELETE FROM contact_statuses");
            executeQuery("DELETE FROM favorites");
            executeQuery("DELETE FROM messages");
            executeQuery("DELETE FROM entries");
            executeQuery("DELETE FROM entry_statuses");
            executeQuery("DELETE FROM schools");
            executeQuery("DELETE FROM categories");
            executeQuery("DELETE FROM accounts");
            executeQuery("DELETE FROM prefectures");
            executeQuery("DELETE FROM sexes");
            executeQuery("DELETE FROM admins");
            executeQuery("DELETE FROM admin_departments");
        }
    };

    public static class executeTest extends MockStrutsTestCase {

        private static final String ACTION_PATH = "/searchSchool.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteInCaseSearchByArrayOfSchoolIdAndRegistrantAccountIdAndSchoolCategoryIdAndSchoolPrefectureId() {
            FrontSearchSchoolActionForm inForm = new FrontSearchSchoolActionForm();
            String[] schoolCategoryIdArray = {"43", "44", "45"};
            inForm.setSchoolCategoryIdArray(schoolCategoryIdArray);
            String[] schoolPrefectureIdArray = {"15", "16"};
            inForm.setSchoolPrefectureIdArray(schoolPrefectureIdArray);
            setActionForm(inForm);
            actionPerform();

            verifyForwardPath("/listSchool.do");

            int[] expectedSchoolIdArray = {2, 7};

            FrontSearchSchoolActionForm actual = (FrontSearchSchoolActionForm) getActionForm();

            for(int i = 0; i < actual.getSchoolExtendFormList().size(); i++) {
                assertThat(actual.getSchoolExtendFormList().get(i).getSchoolId(), is(expectedSchoolIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseSearchByRangeOfSchoolFee() {
            FrontSearchSchoolActionForm inForm = new FrontSearchSchoolActionForm();
            inForm.setMinSchoolFee("1000");
            inForm.setMaxSchoolFee("4000");
            setActionForm(inForm);
            actionPerform();

            verifyForwardPath("/listSchool.do");

            int[] expectedSchoolIdArray = {3, 4, 5};

            FrontSearchSchoolActionForm actual = (FrontSearchSchoolActionForm) getActionForm();

            for(int i = 0; i < actual.getSchoolExtendFormList().size(); i++) {
                assertThat(actual.getSchoolExtendFormList().get(i).getSchoolId(), is(expectedSchoolIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseSearchByLikeSchoolName() {
            FrontSearchSchoolActionForm inForm = new FrontSearchSchoolActionForm();
            inForm.setLikeSchoolName("検証");
            setActionForm(inForm);
            actionPerform();

            verifyForwardPath("/listSchool.do");

            int[] expectedSchoolIdArray = {2, 7};

            FrontSearchSchoolActionForm actual = (FrontSearchSchoolActionForm) getActionForm();

            for(int i = 0; i < actual.getSchoolExtendFormList().size(); i++) {
                assertThat(actual.getSchoolExtendFormList().get(i).getSchoolId(), is(expectedSchoolIdArray[i]));
            }
        }

    }

}

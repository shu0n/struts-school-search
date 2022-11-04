package front.action.message;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.property.PropertyTester.*;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import front.actionform.message.FrontDeleteSendedMessageActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class DeleteSendedMessageActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.DeleteSendedMessageActionTest.xml") {
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

        private static final String ACTION_PATH = "/deleteSendedMessage.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccess() throws Exception {
            addRequestParameter("messageId", "31");
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "3");
            actionPerform();

            verifyForward("success");

            FrontDeleteSendedMessageActionForm actual = (FrontDeleteSendedMessageActionForm) getActionForm();

            assertThat(actual, is(nullValue()));

            // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.DeleteSendedMessageActionTest.testExecuteToForwardSuccess.xml"));
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            ITable expectedTable = replacementDataSet.getTable("messages");

            // 実際のテーブル情報の取得に必要な変数を生成する。
            String tableName = "messages";
            String sqlQuery = "SELECT * FROM messages WHERE message_id=31";
            String[] ignoreCols = {"sender_flag_updated_at"};

            Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNoParameterExist() throws Exception {
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "3");
            actionPerform();

            verifyForward("unexistence");
        }

        @Test
        public void testExecuteInCaseLogout() throws Exception {
            addRequestParameter("messageId", "32");
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/deleteSendedMessage.do?messageId=32");
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNonRecipientAccount() throws Exception {
            addRequestParameter("messageId", "32");
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "3");
            actionPerform();

            verifyForward("unexistence");
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseReceievedMessageDeleted() throws Exception {
            addRequestParameter("messageId", "33");
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "3");
            actionPerform();

            verifyForward("unexistence");
        }

    }

}

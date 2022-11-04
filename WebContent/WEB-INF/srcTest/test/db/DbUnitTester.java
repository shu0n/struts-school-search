package test.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.dbunit.AbstractDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class DbUnitTester extends AbstractDatabaseTester implements TestRule {

    private final String connectionUrl; // DBのURL
    private final String username; // DBアクセス時のユーザー名
    private final String password; // DBアクセス時のパスワード
    private final String fixture; // テスト実行前のDBセットアップ用のファイル名
    private final Map<String, Integer> relativeDateMap; // 日時型のカラムに現在日時からの相対日時を設定するためのxml記法と値(日)を格納したマップ

    public DbUnitTester(String driverClass, String connectionUrl) {
        this(driverClass, connectionUrl, null, null, null, null, null);
    }

    public DbUnitTester(String driverClass, String connectionUrl, String username, String password) {
        this(driverClass, connectionUrl, username, password, null, null, null);
    }

    public DbUnitTester(String driverClass, String connectionUrl, String username, String password, String schema) {
        this(driverClass, connectionUrl, username, password, schema, null, null);
    }

    public DbUnitTester(
            String driverClass, String connectionUrl, String username, String password, String schema, String fixture) {
        this(driverClass, connectionUrl, username, password, schema, fixture, null);
    }

    public DbUnitTester(
            String driverClass,
            String connectionUrl,
            String username,
            String password,
            String schema,
            String fixture,
            Map<String, Integer> relativeDateMap) {
        super(schema);
        this.connectionUrl = connectionUrl;
        this.username = username;
        this.password = password;
        this.fixture = fixture;
        this.relativeDateMap = relativeDateMap;
        assertNotNullNorEmpty("driverClass", driverClass);
        try {
            Class.forName(driverClass);
        } catch(ClassNotFoundException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * DBへの接続状態を取得するためのメソッド
     * @return DBへの接続状態
     */
    @Override
    public IDatabaseConnection getConnection() throws Exception {
        Connection conn = null;
        if(username == null && password == null) {
            conn = DriverManager.getConnection(connectionUrl);
        } else {
            conn = DriverManager.getConnection(connectionUrl, username, password);
        }
        DatabaseConnection dbConnection = new DatabaseConnection(conn, getSchema());
        DatabaseConfig config = dbConnection.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new OracleDataTypeFactory());

        return dbConnection;
    }

    /**
     * SQLを実行するためのメソッド
     * @param sql SQL文
     * @throws Exception
     */
    public void executeQuery(String sql) throws Exception {
        Connection conn = getConnection().getConnection();
        conn.createStatement().execute(sql);
        conn.setAutoCommit(false);
        conn.commit();
        conn.close();
    }

    protected void before() throws Exception {}

    protected void after() throws Exception {}

    /**
     * DBセットアップ用のテーブル情報を生成するためのメソッド
     * @return テスト実行前のDBセットアップ用のファイル名をもとに生成したテーブル情報
     * @throws Exception
     */
    public IDataSet createDataSet() throws Exception {
        if(fixture != null) {
            FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
            builder.setColumnSensing(true);
            IDataSet dataSet = builder.build(getClass().getResourceAsStream(fixture));
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(dataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            if(relativeDateMap != null) {
                for(Map.Entry<String, Integer> entry: relativeDateMap.entrySet()) {
                    replacementDataSet.addReplacementObject(
                            entry.getKey(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(
                                    DateUtils.addDays(new Date(), entry.getValue())));
                }
            }

            return replacementDataSet;
        } else {
            return null;
        }
    };

    /**
     * 事前・事後処理などを含めてテストを実行するためのメソッド
     * @param base テスト実行時の制御クラス
     * @param description テストケースのメタ情報
     * @return テスト実行時の制御クラス
     */
    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                before();
                if(createDataSet() != null) {
                    setDataSet(createDataSet());
                }
                onSetup();
                try {
                    base.evaluate();
                } finally {
                    try {
                        after();
                    } finally {
                        onTearDown();
                    }
                }
            }
        };
    }

}

package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectorDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * データベースとの接続状態を取得するためのメソッド
     * @return DBとの接続状態
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection connectDatabase() throws IOException, ClassNotFoundException, SQLException {
        // プロパティ情報を取得する。
        Properties props = getProperties();

        // JDBCドライバーを読み込む。
        String jdbcDriver = props.getProperty("driverClassName"); // JDBCドライバー
        Class.forName(jdbcDriver);
        // 接続に必要な情報を読み込む。
        String url = props.getProperty("db.url"); // DBの接続先URL
        String user = props.getProperty("db.user"); // DBに接続するユーザー
        String password = props.getProperty("db.password"); // DBに接続するためのパスワード
        // DBに接続する。
        connection = DriverManager.getConnection(url, user, password);

        // DBとの接続状態を戻す。
        return connection;
    }

    /**
     * プロパティ情報を取得するためのメソッド
     */
    Properties getProperties() throws IOException {
        // プロパティ情報を扱うクラスのインスタンスを生成する。
        Properties props = new Properties();
        // プロパティファイル名をもとに内容を読み込む。
        InputStream inputStream = ConnectorDAO.class.getClassLoader().getResourceAsStream("environment.properties");
        if(inputStream == null) {
            // 読み込んだオブジェクトがNULLの場合はI/O例外を投げる。
            throw new IOException();
        }
        // 読み込んだ内容をもとにプロパティ情報を生成する。
        props.load(inputStream);

        // 生成したプロパティ情報を戻す。
        return props;
    }

}

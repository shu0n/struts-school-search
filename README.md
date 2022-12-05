# struts-school-search
以下の２点を目的としたWebアプリケーションである。
- 主催しているスクール情報を登録して生徒の募集ができること。
- 興味のあるスクールを検索して受講の申込ができること。
- 詳細は下記ファイルを参照すること。
    - [スクール検索サイト 要件](/SITE_REQUIREMENT.md)
    - [スクール検索サイト テーブル一覧](/TABLE_LIST.md)
    - [スクール検索サイト 機能一覧](/FUNCTION_LIST.md)
    - [スクール検索サイト 画面一覧](/SCREEN_LIST.md)

## 動作環境
- macOS v13.0
- Docker Engine v20.10.20
- Docker Desktop v4.12.0
- Apache v2.4.6
- Tomcat v8.5.83
- Java v1.8.0_45
- struts v1.3.10

## 事前準備
1. メールサーバ用のイメージのビルドとコンテナの起動を行う。
    - [リポジトリ](https://github.com/shu0n/postfix)
1. DBサーバ用のイメージのビルドとコンテナの起動を行う。
    - [リポジトリ](https://github.com/shu0n/oracle)

## 初回起動
1. 任意のディレクトリにリポジトリをチェックアウトする。(以下は、macOS v11.61のホームディレクトリにチェックアウトした場合)
1. `./struts-school-search/struts-docker-compose/`に移動する。
1. `docker-compose.yml.sample`をもとに`docker-compose.yml`を作成する。
1. 作成した`docker-compose.yml`内の設定値を自環境に合わせて編集する。
1. `../Webcontent/WEB-INF/props/`に移動する。
1. `environment.properties.sample`をもとに`environment.properties`を作成する。
1. 作成した`environment.properties`内の設定値をコンテナ環境に合わせて編集する。
1. `./struts-school-search/struts-docker-compose/`に移動する。
1. `docker-compose up -d`のコマンドを実行する。
1. イメージのビルドとコンテナの起動が完了した後に、Webブラウザから`http://localhost/struts-school-search/`にアクセスする。
1. `http://localhost/struts-school-search/admin/index.do`にアクセスする。
1. ログインフォームに`メールアドレス:user@mail.example.com`、`パスワード：password`を入力してログインする。

## 再起動
1. 任意のディレクトリで`docker restart struts-tomcat`のコマンドを実行する。
1. コンテナの再起動が完了した後に、Webブラウザから`http://localhost/struts-school-search/`にアクセスする。

## テストソースの実行
1. 任意のディレクトリで`docker exec -it struts-tomcat /bin/bash`のコマンドを実行する。
1. コンテナに入った後、`/etc/tomcat/webapps/struts-school-search/WEB-INF`に移動する。
1. `./propsTest/`配下の設定ファイルの値をコンテナ環境に合わせて編集する。
1. 単一のテストクラスを実行する場合は、`ant test -DtestClassName={完全修飾クラス名}`のコマンドを実行する。
1. 全てのテストクラスを実行する場合は、`ant testAll`のコマンドを実行する。

## ディレクトリ構成
<pre>
.
├── README.md ⇦ READMEを記載したファイル
├── SITE_REQUIREMENT.md ⇦ サイトの要件を記載したファイル
├── TABLE_LIST.md ⇦ DBのテーブル一覧を記載したファイル
├── FUNCTION_LIST.md ⇦ サイトの機能一覧を記載したファイル
├── SCREEN_LIST.md ⇦ サイトの画面一覧を記載したファイル
├── struts-docker-compose
│   ├── docker-compose.yml.sample ⇦ Webサーバとアプリケーションサーバのコンテナをまとめたdocker-composeのymlファイルのサンプル
│   ├── httpd ⇦ Webサーバ用のイメージのビルドとコンテナの起動に必要なファイルを格納したディレクトリ
│   └── tomcat ⇦ アプリケーションサーバ用のイメージのビルドとコンテナの起動に必要なファイルを格納したディレクトリ
└── WebContent
    ├── index.do ⇦ トップ画面に「/」でアクセスできるようにするためのファイル
    ├── front
    │   ├── jsp ⇦ フロント画面で使用するjspファイルを格納したディレクトリ
    │   ├── js ⇦ フロント画面で使用するjsファイルを格納したディレクトリ
    │   └── css ⇦　フロント画面で使用するcssファイルを格納したディレクトリ
    ├── admin
    │   ├── jsp ⇦ 管理画面で使用するjspファイルを格納したディレクトリ
    │   ├── js ⇦ 管理画面で使用するjsファイルを格納したディレクトリ
    │   └── css ⇦　管理画面で使用するcssファイルを格納したディレクトリ
    ├── error
    │   ├── jsp ⇦ エラー画面で使用するjspファイルを格納したディレクトリ
    │   ├── js ⇦ エラー画面で使用するjsファイルを格納したディレクトリ
    │   └── css ⇦ エラー画面で使用するcssファイルを格納したディレクトリ
    ├── img ⇦ サイトで使用する画像やアップロードされた画像の格納先ディレクトリ
    └── WEB-INF
        ├── src
        │   ├── front
        │   │   ├── action ⇦ フロント画面のアクションクラスのjavaファイルを格納したディレクトリ
        │   │   └── actionform ⇦ フロント画面のアクションクラスで使用するアクションフォームクラスのjavaファイルを格納したディレクトリ
        │   ├── admin
        │   │   ├── action ⇦ 管理画面のアクションクラスのjavaファイルを格納したディレクトリ
        │   │   └── actionform ⇦ 管理画面のアクションクラスで使用するアクションフォームクラスのjavaファイルを格納したディレクトリ
        │   ├── model ⇦ 業務ロジックを実行するためのクラスのjavaファイルを格納したディレクトリ
        │   ├── util ⇦ 汎用的な処理（日時処理、ファイル処理、ログ処理など）を実行するためのクラスのjavaファイルを格納したディレクトリ
        │   ├── dao ⇦ DBへのCRUDを実行するためのDAOクラスのjavaファイルを格納したディレクトリ
        │   ├── actionform ⇦ DBのテーブルのカラムに対応したアクションフォームクラスとWebアプリケーションで使用する項目を追加したアクションフォームクラスのjavaファイルを格納したディレクトリ
        │   ├── exception ⇦ 独自の例外クラスのjavaファイルを格納したディレクトリ
        │   └── filters ⇦ 文字エンコードを指定するクラスのjavaファイルを格納したディレクトリ
        ├── srcTest ⇦ 単体試験を実行するテストクラスのjavaファイルを格納したディレクトリ(配下のディレクトリ構成は./srcと対応)
        ├── props ⇦ ソースのclassファイルの実行に使用する設定ファイルを格納したディレクトリ
        ├── propsTest ⇦ テストソースのclassファイルの実行に使用する設定ファイルを格納したディレクトリ
        ├── lib ⇦ ソースのclassファイル実行に使用するライブラリを格納したディレクトリ
        ├── libTest ⇦ テストソースのclassファイルの実行に使用するライブラリを格納したディレクトリ
        ├── logs ⇦ ログファイルの格納先ディレクトリ
        ├── validation-front.xml ⇦ 管理画面で使用するvalidationのxmlファイル
        ├── validation-admin.xml ⇦ 管理画面で使用するvalidationのxmlファイル
        ├── struts-config-front.xml ⇦ フロント画面で使用するstruts-configのxmlファイル
        ├── struts-config-admin.xml ⇦ 管理画面で使用するstruts-configのxmlファイル
        ├── web.xml ⇦ web.xmlファイル
        └── build.xml　⇦　Apache Ant実行用のxmlファイル
</pre>

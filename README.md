# JISAjaxJSONSample
AjaxでJSONを扱う際に任意のエンコーディングを指定できるように実装したサンプル

### 動作確認環境
##### OS,ミドルウェア
- windows8.1
- jdk11
- [WildFly](https://wildfly.org/)(15.0.0.Final)
##### ブラウザ
- Chrome（時点最新）
- FireFox（時点最新）
- IE11

### 実行方法
プロジェクトのルートディレクトに移動してmavenコマンドを実行します。
「プロジェクトのルートディレクトリ/target」配下に生成されるwarファイルをアプリケーションサーバーにデプロイします。
```
mvn clean package
```
アプリケーションサーバーの起動後、以下のURLにアクセスすると動作確認用の画面が表示されます。

http://[ホスト]:[ポート]/JISAjaxJSONSample/

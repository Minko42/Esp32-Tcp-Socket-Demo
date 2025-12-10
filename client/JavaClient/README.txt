Esp32-Tcp-Socket-Demo - Java Client 実行マニュアル

このディレクトリには、ESP32とTCPソケット通信を行うJavaクライアントの
ソースコードが含まれている。

■ 動作環境
- Java Development Kit (JDK) 11 以上
- ネットワーク接続（ESP32と同じWi-Fiネットワークに接続する必要がある）

■ 実行前の準備
1. 'JavaClient/config.properties.sample'のファイルから以下の行を探し、ESP32のIPアドレス（シリアルモニタで確認したもの）に書き換える。
   server.ip=192.168.xx.xx
2. ファイルの名前をconfig.propertiesに変更して保存する。

■ コンパイルと実行方法
コマンドプロンプト（またはターミナル）を開き、この `JavaClient` フォルダに
移動してから、以下のコマンドを順番に入力する。

1. コンパイル
   javac -encoding UTF-8 src/Esp32.java

2. 実行
   java -cp src Esp32

■ 操作方法
プログラムが起動し "Connected successfully!" と表示されれば、通信成功。
以下のキーを入力して Enter を押すとLEDを操作することができる。

- 1 : LEDを点灯 (ON)
- 0 : LEDを消灯 (OFF)
- q : 通信を切断して終了

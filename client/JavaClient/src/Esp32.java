import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;


//Wi-Fi経由でESP32に接続し、コマンドを送信するプログラム.
public class Esp32 {
    public static void main(String[] args) {
        //設定ファイルの読み込み処理.
        Properties prop = new Properties();
        String ipAddress = "";
        int port = 0;

        try (InputStream input = new FileInputStream("config.properties")) {
            //プロパティファイルの読み込み.
            prop.load(input);
            
            //IPアドレス取得.
            ipAddress = prop.getProperty("server.ip");
            // 文字列を数値に変換 (ポート番号).
            port = Integer.parseInt(prop.getProperty("server.port"));

        } catch (IOException ex) {
            System.err.println("設定ファイル(config.properties)が見つかりません。");
            System.err.println("config.properties.sample を参考に作成してください。");
            return; // 終了する
        }

        System.out.println("Connecting to ESP32 (" + ipAddress + ")...");
        
        //ソケット接続の確立.
        try (Socket socket = new Socket(ipAddress, port)) {
            System.out.println("Connected successfully!");

            //出力ストリームの取得.
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            //入力ストリームの取得.
            Scanner serverScanner = new Scanner(socket.getInputStream());

            //ユーザー入力.
            Scanner scanner = new Scanner(System.in);
            
            printInstructions();

            while (true) {
                System.out.print("Command > ");
                String input = scanner.nextLine();

                //終了コマンド.
                if ("q".equalsIgnoreCase(input)) {
                    System.out.println("Exiting...");
                    break;
                }

                // コマンド送信.
                writer.println(input);
                writer.flush();
                System.out.println("[Sent]: " + input);

                //ESP32からの応答受信.
                if (serverScanner.hasNextLine()) {
                    String response = serverScanner.nextLine();
                    System.out.println("ESP32 says: " + response);
                }
            }

        } catch (Exception e) {
            System.err.println("Connection Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //操作方法の表示.
    private static void printInstructions() {
        System.out.println("-------------------------");
        System.out.println(" [1] Turn LED ON");
        System.out.println(" [0] Turn LED OFF");
        System.out.println(" [q] Quit");
        System.out.println("-------------------------");
    }
}
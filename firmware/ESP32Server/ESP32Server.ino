#include <WiFi.h>
#include "secrets.h"

// ポート80で待機.
WiFiServer server(80);

const int LED_pin = 2; 

void setup() {
  Serial.begin(115200);
  pinMode(LED_pin, OUTPUT);

  Serial.print("Connecting to ");
  Serial.println(SECRET_SSID);
  
  // WiFi接続開始.
  WiFi.begin(SECRET_SSID, SECRET_PASS);

  // 接続完了まで待機.
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("\nWiFi connected.");
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());

  // サーバー開始.
  server.begin();
}

void loop() {
  // クライアントの接続を待機.
  WiFiClient client = server.available();

  if (client) {
    Serial.println("New Client connected.");
    
    while (client.connected()) {
      // クライアントからのデータを待機.
      if (client.available()) {
        char c = client.read();
        Serial.write(c);

        if (c == '1') {
          digitalWrite(LED_pin, HIGH);
          client.println("OK: LED ON");
        } else if (c == '0') {
          digitalWrite(LED_pin, LOW);
          client.println("OK: LED OFF");
        }
      }
    }
    
    // クライアント切断.
    client.stop();
    Serial.println("\nClient Disconnected.");
  }
}
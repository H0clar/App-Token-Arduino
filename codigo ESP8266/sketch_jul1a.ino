/* Tomas Aranguis y Julio Romero */



#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <FirebaseESP8266.h>


#include <addons/TokenHelper.h>


#include <addons/RTDBHelper.h>

/* 1. credenciales wifi */
#define WIFI_SSID "Aca"
#define WIFI_PASSWORD "123456789"

/* 2. apy key */
#define API_KEY "AIzaSyA12C6sybqBn29zoU8aO7WSjqRFoPeYDRY"

/* 3. url DB firebase */
#define DATABASE_URL "julio-romero-default-rtdb.firebaseio.com"

/* 4. credenciales, no hace falta que sean reales solo se tienen que crear en la base de datos */
#define USER_EMAIL "jurom22@gmail.com"
#define USER_PASSWORD "123456789"




FirebaseData fbdo;

FirebaseAuth auth;
FirebaseConfig config;

unsigned long sendDataPrevMillis = 0;

unsigned long count = 0;

void setup()
{
  Serial.begin(115200);

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);

  Serial.print("Connecting to Wi-Fi");
  while (WiFi.status() != WL_CONNECTED)
  {
    Serial.print(".");
    delay(300);
  }
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();

  Serial.printf("Firebase Client v%s\n\n", FIREBASE_CLIENT_VERSION);

 
  config.api_key = API_KEY;

 
  auth.user.email = USER_EMAIL;
  auth.user.password = USER_PASSWORD;

 
  config.database_url = DATABASE_URL;

 
  config.token_status_callback = tokenStatusCallback; // see addons/TokenHelper.h

  Firebase.begin(&config, &auth);

 
  Firebase.reconnectWiFi(true);

  Firebase.setDoubleDigits(5);
}

void loop()
{
  if (Firebase.ready() && (millis() - sendDataPrevMillis > 20000 || sendDataPrevMillis == 0))
  {
    sendDataPrevMillis = millis();

    // generador del codigo random 
    int randomCode = random(100000, 999999);

    // Actualizar el codigo en la bd
    if (Firebase.setInt(fbdo, "/code", randomCode))
    {
      Serial.printf("Code sent: %d\n", randomCode);
    }
    else
    {
      Serial.printf("Failed to send code. Error: %s\n", fbdo.errorReason().c_str());
    }

    Serial.println();
  }
}



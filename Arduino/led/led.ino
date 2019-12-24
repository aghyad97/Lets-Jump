//LEDs
#include <FastLED.h>
#include <SoftwareSerial.h>
SoftwareSerial mySerial(11, 12); 
#define START 1 //Start state with 1 byte
#define LOOKFORHAND 2 // LOOKFORHAND state with 2 bytes
#define LOOKFORJUMP 4 // LOOKFORJUMP state with 4 bytes
#define WAITING 8 // WAITING state with 8 bytes
#define NUM_LEDS 30 // Number of led used
#define DATA_PIN 4 // Data pin for the leds
#define CLOCK_PIN 13 //clock pin on the arduino


// Define the array of leds
CRGB leds[NUM_LEDS];
const int handControlPin = 2;
const int groundPin = 3;
const byte JUMP = 0b100000; // byte for the recieving message from Java for the Jump
const byte HAND = 0b100101; // byte for the recieving message from Java for the hand
byte modeL = 0b11111111;  // byte for default mode which is do nothing

byte S = START; // create a byte for the state and intialize it to start state

void setup() { 
  // Default leds setup by the FastLed library
      FastLED.addLeds<WS2812B, DATA_PIN, RGB>(leds, NUM_LEDS);
      FastLED.setBrightness(64);
      Serial.begin(9600);
      while (!Serial) {
        ; // wait for serial port to connect. Needed for native USB port only
      }
      mySerial.begin(9600);
      pinMode(handControlPin, OUTPUT);
      pinMode(groundPin, OUTPUT);
      digitalWrite(groundPin, LOW);
      delay(500);
}

void loop() { 
    if (mySerial.available()){
       modeL = mySerial.read();
       if (modeL == HAND){ S = LOOKFORHAND; }// if you recieve message of the hand then the state will be equal to LOOKFORHAND
       else if (modeL == JUMP){ S = LOOKFORJUMP; } // if you recieve message of the hand then the state will be equal to LOOKFORHAND 
       else{ S = WAITING; }  // if did not recieve, then the state is waiting till it recieves a message
    }
    if (S == LOOKFORJUMP) { // if state equals to LOOKFORJUMP, then turn on the leds for jump
      Serial.println("got j!"); // for debugging purposes
      for(int i=0;i<NUM_LEDS;i++){
       leds[i] = CRGB::Green;
       }
      FastLED.show();
      FastLED.delay(50);
      for(int i=0;i<NUM_LEDS;i++){
       leds[i] = CRGB::Black;
      }
       FastLED.show();
       FastLED.delay(50); 
       Serial.println(mode);
   }
    else if (S == LOOKFORHAND) { // if state equals to LOOKFORJUMP, then turn on the leds for jump
       digitalWrite(handControlPin, HIGH);
       FastLED.delay(50); 
       digitalWrite(handControlPin, LOW);
       FastLED.delay(50);
       Serial.println(mode);
    }
}

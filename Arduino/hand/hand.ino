/* 
Hand arduino code with State design pattern
*/

#include <SoftwareSerial.h>
#define START 1 //Start state with 1 byte
#define LOOKFORHAND 2 // LOOKFORHAND state with 2 bytes
#define WAITING 8 // WAITING state with 8 bytes
SoftwareSerial mySerial(11, 12); // TX, RX ports on the arduino

const int ypin = A2; // y-axis port on the arduino
int y[3]; // array of size 3 to store values from the accelerometer 
float y1, absy1, oldy1, change; 
const byte RecHand = 0b100101; // byte for the recieving message from Java
const byte SendHand = 0b110001; // byte for the sending message from Java
byte modeH = 0b11111111; // byte for default mode which is do nothing
const float G = 0.4; // constant change to compare to it

byte S = START; // create a byte for the state and intialize it to start state

void readY (float *y1) // function for reading the values from acc.
{
  y[0] = analogRead(ypin);
  delay(10);
  y[1] = analogRead(ypin);
  delay(10);
  y[2] = analogRead(ypin); // Reads three times for low pass filter
  delay(10);
  y[0] = y[1];
  y[1] = y[2];
  y[2] = analogRead(ypin);
  *y1 = (float)(y[0] + y[1] + y[2]) / 3; //applies low pass filter
}

void getG(float *ay1) // function for getting acceleration for acc. in Gs + 3
{
  float y1 ;
  int yvoltage;
  readY(&y1);
  yvoltage = map(y1, 0, 1023, 0, 6000); // maps values of 0 to 1024, to 0 to 6000
  *ay1 = (float)yvoltage / 1000; // divides by a 1000
}


void setup() {
  // initialize the serial communications:
  Serial.begin(9600);
  mySerial.begin(9600);
}



void loop() {
  if (mySerial.available()){
    modeH = mySerial.read();
    if (modeH == RecHand){ S = LOOKFORHAND;}// if you recieve message of the hand then the state will be equal to LOOKFORHAND 
    else { S = WAITING;} // if did not recieve, then the state is waiting till it recieves a message
  }
    getG(&y1); // gets acc in Gs + 3 of the Y axis
    Serial.print(y1);
    Serial.println(" G");
    //mySerial.write('n'); // keeps sending n to recieveing xbee, necessary so Java is not stuck on reading
    // below code is used to detect a jump by detecting a spike in accelerometer value reading
    change = abs(y1 - oldy1);
    oldy1 = y1; // changes value of oldy1
    if (change >= G) { // if the new value is greater by 1.15 times the old value, that means the hand was raised
      if (S == LOOKFORHAND){ // if state equals LOOKFORHAND, then enter the state of sending the message to java
        Serial.println("HAND!!");
        mySerial.write(SendHand); // writes to xbee that a jump has been detected
        delay(1500);
        getG(&y1); // get new reading from getG function
        oldy1 = y1; // equate the new y to the old to compare to it in the future
      }
    }
    delay(50);
}

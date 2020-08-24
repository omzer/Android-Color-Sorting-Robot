#include <Servo.h>

Servo motor;
int pos = 0;
void setup() {
  motor.attach(3);
  Serial.begin(9600);
  motor.write(0);
  delay(1000);
}

void loop() {
    motor.write(180 - pos);
    pos = 180 - pos;
    delay(600);
}

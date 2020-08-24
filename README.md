⚠️ This project is still in progress 👷‍♂️🛠
 
# Project info
## Idea 🤔💡
The idea of the project is to create a robot that sorts the candies into a buckets based on their color, it's inspired by [this project](https://create.arduino.cc/projecthub/user421848217/how-to-make-color-sorting-machine-8278c9) from the Ardunio create Hub page.
  
## Background 📝
The intent beahind the project was to create an interesting project that visualize the "bucketing" algorithm (Like in bucket sort), and combine all the knowledge I know in one project (Android + Arduino + Electronics + Machine Learning).

# Technichal Details
## How to identify colors 🎨
Used [Teachable Machine](https://teachablemachine.withgoogle.com/) to create a tflite model that I can use on Android, the model was trained using images data set that I took myself from each angle under all possible light conditions for all the supported colors:

![](https://github.com/omzer/Android-Color-Sorting-Robot/blob/master/app/src/main/res/github_resources/data_set.png?raw=true) 

And to test the accuracy of the model I tested it on a real life data by creating an App that guess the color presented and colors the App background accordingly:

![](https://github.com/omzer/Android-Color-Sorting-Robot/blob/master/app/src/main/res/github_resources/testing_gif.gif?raw=true) 

And you might ask yourself, WHY I DIDN't USE COLOR SENSOR? 
Color sensors are great and will make the build process much easier, but I'm new to Machine Learning and image processing, so I decided to go with the hard approach and classify the colors using my phone instead of the color sensor to sharpen my newly learned skill, plus Machine Learning is cool 🤖.

P.S: For this project I'm only dealing with 5 colors (red, green, blue, yellow and orange).

## How the phone speaks to the Ardunio 📲
The flow is simple, once the App opens, it asks the user to connect via bluetooth to the Bluetooth Module on the Arduino, and once that connection established, the phone will start getting images from the camera each 200ms and try to guess the presented color.

Then using the connection established with the Ardunio, it will send the guessed color, and the rest is to Arduino to handle in the [Arduino code](https://github.com/omzer/Android-Color-Sorting-Robot/blob/master/app/src/main/res/github_resources/ardunio.ino).

# Flight Simulator Desktop Application

<p align="center">
  <img src="/readme_images/ptm_project_img.jpg" width="750">
</p>

## Introduction

In this repository, I will introduce milestone 5 in the final project of the Advanced Software Development course.<br />
The goal in this milestone was to Build and design a GUI for the flight simulator using JavaFX, to calculate the flight route and fly the plane using the previous milestones.


## Design and functionality of the app

<p align="center">
  <img src="/readme_images/ptm_project_img_app.jpg" width="700">
</p>

* **Connect** – The connect button will open a popup window and by entering ip and port we will connect to the flight simulator.
The app will connect to the simulator as a client, this will also enable the use of all the functionality of the application.

* **Load data** – The Load Data button will open a folder of CSV files that are used as maps.
When a CSV file is loaded then a map of the uploaded data is displayed.
The map will be displayed in colors based on the height of each point in the map, the lower the area the color will be red and the higher the area the color will be greener.
We will sample from the simulator the exact location of the aircraft, as a result, an icon will be displayed in the same location on the map.

* **Calculate path** - The Calculate path button will open a popup window and by entering ip and port we will connect to a server that solves search problems ([A server that I built on milestones 1-3](https://github.com/AlonKoren/PTM_project-Milestones1-3)).
Clicking on any point on the map will set a destination for the aircraft, and the server will calculate the cheapest route to that point.

* **Load + Autopilot** – Loads the script with the airplane commands and thanks to the Interpreter that I built, the plane goes into autopilot mode and executes the commands in the script.

* **Manual** – The manual button will move the aircraft from autopilot mode to manual mode where the user controls the aircraft with the joystick.


### UML diagram of the JavaFX Desktop Application

The application is divided into two independent components, one component is the MapController, and the other consists of two additional components, the JoystickController and the DisplayScriptController.

<p align="center">
  <img src="/readme_images/UML_javaFX_app.jpg" width="550">
</p>



### MVVM Architecture

For building the app, I chose to use the MVVM architecture.

<p align="center">
  <img src="/readme_images/MVVM_architecture.jpg" width="550">
</p>

<br /><br /><br /><br />
**This project was made for my Advanced Software Development Course, Which was taught by Dr. Eliahu Khalastchi<br /> at The College of Management Academic Studies .<br />
You are welcome to look at my project presentation in the video attached here ([Project Presentation Video](https://youtu.be/NNdkB9Fo16Y)).<br />**

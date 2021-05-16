# Fitness-Training-Application

This application aims to help both trainers and gym goers to adapt to the pandemic by offering a variety of fitness programs.
## Technologies
* Java 15
* JavaFX 15 (as GUI)
* Maven (Build Tool)
* Nitrite Java (as Database)
## Setup and Run
#### Clone the repository:

Using the command

```
git clone https://github.com/fis2021/Fitness-Training-Application
```
#### Verify that the project builds locally:

Open the command line, cd into the project and then type:

```
mvn clean install
```
#### Open it in an IDE:

You can use IntelliJ or Eclipse, and then import the project as a Maven project.

#### Run the project using Maven

To start and run the project using Maven use the following command in the command line: 

```

mvn javafx:run
```

## Registration
The user needs to register into the application using one of the 2 roles:
* Trainer
* Client
## Trainer
A Trainer can edit fitness programs from their profile, view a list of Clients that applied to their zoom meeting and accept or deny them.
## Client
After logging in, a client can view the programs offered by the trainers and apply to their zoom meeting.

# Rabbit Run 2D Arcade Game

## Project Overview
We aim to design and develop an exciting game, **"Rabbit Run"**.

## Team Members
- **Vansh Bansal**
- **Vivien Li**
- **Vansh Thakur Rana**
- **Lovejeet Nanua**

## Project Goals
Our primary goal for is to build a fun and engaging game called *Rabbit Run*. In this game, players will navigate each map and reach the exit, while collecting all the regular rewards on the map and reaching the level's score goal. The game ends once the enemy catches up with the player or the player collects enough punishments to obtain a score of zero or less.

## How To Run The Game
Follow the steps below to build, run, and test the game. Ensure you are in the same directory as your `pom.xml` file.

**Build the Game:**\
To build the project and make the Jar file, run the following command:\
`mvn clean compile`\
<br />
**Run the Game:**\
To execute the game, use the following command:\
`mvn exec:java`\
<br />
**Run Tests:**\
To execute the test suite, use this command:\
`mvn test`\
<br />
**Create Jar File:**\
To create the jar executable, use this command:\
`mvn clean package -DskipTests -Dmaven.javadoc.skip=true`\
*The jar file will be located inside the Artifacts folder*
\
<br />
**Run Jar File:**\
The jar file is located inside the Artifacts folder, use the following command to execute it:\
`java -jar Artifacts/RabbitRun-1.0.jar`
\
<br />
**Generate Javadoc Files:**\
The Javadoc files are located inside Artifacts\javadoc\apidocs, use the following command to generate them:\
`mvn javadoc:javadoc`

## Project Demonstration

Check out our project demonstration video to see **Rabbit Run** in action! The video provides an overview of the gameplay, features, and mechanics.

[![Watch the video](https://img.youtube.com/vi/_ElJut3ddKc/0.jpg)](https://youtu.be/_ElJut3ddKc)

Click [here](https://youtu.be/_ElJut3ddKc) to watch the video on YouTube.
# Mancala
Assignment for QSD to create a model and functionality for the Mancala game.

##0.0.1
Initial version with a working model control, but no view or webservice (only simple logging for the view).
The main MancalaMain simulates one game between two players, by selecting random possible moves.
Basic unittesting of the model and control
Simple logging
Created a simulation to visualize a game
No checked exceptions yet
No null checks
Implemented all Mancala rules and tested them

#RoadMap:

##0.0.2-SNAPSHOT (expected todo)
Expect work for production delivery
- more logging 
- unittest coverage bit higher
- create Player object
- creat Webservice simple
- hold state of moves, and checks the lastPlayer moved
- use Spring for factories,validations, nullchecks, properties, dependency injection and webservice
- build docker image
- make deployable through a pipeline and testable via OPENAPI
- implement equals and hashes of value objects, ensuring immutable
- use mock framework

##0.0.3-SNAPSHOT
Expected work for production delivery
- create client webinterface
- create basic integration test
- use integration test and mock framework (like wiremock)

##0.0.4-SNAPSHOT
more to find out


#Build instrucions
install maven and set on PATH
set MAVEN_HOME to maven dir
set JAVA_HOME to installed java 17 jdk

##build and test
mvn clean install

##if sonarqube is installed
mvn clean verify sonar:sonar -Dsonar.token=<TOKEN>
<Toke>n = token of user in SonarCube
 
##run
from ide import project from git, click on MancalaMain to simulate one game
from java command line:"
 - build the version with maven
 - in target dir run: 

#Github
https://github.com/jpdehaas/mancala


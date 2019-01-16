# Projet Hadoop/Web

This document contains instruction to run our map reduce jobs and the REST API that goes with it.

## Prerequisites
The version control system Git must be installed on the system, if not run:  
``sudo apt install git``  
The project build system Maven must be installed on the system, if not run:  
``sudo apt install maven``

## Building the API
First setup the project directory run:  
``git clone https://github.com/AlexChanson/BDMA_Hadoop``  
Then go to the directory:  
``cd BDMA_Hadoop``  
Now you need to build the jar, this is simple as maven does everything with one simple command:  
``mvn package``  

This last step may take a few minutes as maven needs to download all need dependencies.
Wait until the build success message appears:  
<pre>[<font color="#5C5CFF"><b>INFO</b></font>] <b>------------------------------------------------------------------------</b>
[<font color="#5C5CFF"><b>INFO</b></font>] <font color="#00FF00"><b>BUILD SUCCESS</b></font>
[<font color="#5C5CFF"><b>INFO</b></font>] <b>------------------------------------------------------------------------</b>
</pre>

## Running the API
Maven has placed the compiled jar in the subdirectory `target` subdirectory, it can be run using:  
`java -jar target/hadoop-0.1.0.jar`  
This will start the embedded Tomcat server on port _8090_

## Testing the API
To test the API it is recommended to ruse the popular command line utility _curl_

### Question 1
Running `curl http://localhost:8090/Aiwsbu/v1/students/2001000814/transcripts/L1` on the test server yields the following result:  

# Projet Hadoop/Web

This document contains instruction to run our map reduce jobs and the REST API that goes with it.

## Prerequisites
The version control system Git must be installed on the system, if not run:  
``sudo apt install git``  
The project build system Maven must be installed on the system, if not run:  
``sudo apt install maven``

## Downloading the JAR
The build process might be slow especially on the virtual machine, if you do not wish to build the two JAR files required to run our project you can simply download the latest versions here:  
[Map Reduce jobs]()  
[REST API]()  
You can skip "Building" steps.  
We also provide an API endpoint at home.alexc.ovh port 80.
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
Running `curl http://localhost:8090/Aiwsbu/v1/students/2001000814/transcripts/L1` on our server yields the following result:  
```json
{"Email":"","Program":"3","Second":[{"Grade":7.31,"Code":"S01A006","Name":"Unknown"},{"Grade":14.45,"Code":"S02A005","Name":"Unknown"},{"Grade":9.68,"Code":"S02B026","Name":"Unknown"},{"Grade":5.52,"Code":"S03A006","Name":"Unknown"},{"Grade":15.6,"Code":"S04A007","Name":"Unknown"},{"Grade":16.66,"Code":"S04B039","Name":"Djgebtl"},{"Grade":0.03,"Code":"S05A005","Name":"Unknown"},{"Grade":3.21,"Code":"S05A010","Name":"Unknown"},{"Grade":11.59,"Code":"S07A001","Name":"Unknown"},{"Grade":17.91,"Code":"S07A003","Name":"Unknown"},{"Grade":6.81,"Code":"S07B005","Name":"Ays Tinbukyhvtmp"},{"Grade":12.04,"Code":"S07B023","Name":"Unknown"},{"Grade":9.77,"Code":"S07B039","Name":"Unknown"},{"Grade":5.93,"Code":"S07B046","Name":"Unknown"},{"Grade":4.9,"Code":"S08B009","Name":"Unknown"},{"Grade":1.34,"Code":"S09A007","Name":"Unknown"},{"Grade":5.99,"Code":"S09B025","Name":"Unknown"},{"Grade":1.54,"Code":"S10B025","Name":"Unknown"}],"First":[{"Grade":19.89,"Code":"S01A005","Name":"Unknown"},{"Grade":9.64,"Code":"S01B039","Name":"Unknown"},{"Grade":9.43,"Code":"S02B033","Name":"Unknown"},{"Grade":11.97,"Code":"S03A006","Name":"Unknown"},{"Grade":13.52,"Code":"S03A008","Name":"Ywdji Okwufjkvieye Rrrcpffklj"},{"Grade":9.73,"Code":"S03B029","Name":"Unknown"},{"Grade":10.92,"Code":"S04A001","Name":"Unknown"},{"Grade":12.22,"Code":"S04B003","Name":"Unknown"},{"Grade":15.9,"Code":"S04B024","Name":"Unknown"},{"Grade":14.49,"Code":"S04B031","Name":"Unknown"},{"Grade":3.76,"Code":"S05B020","Name":"Unknown"},{"Grade":19.53,"Code":"S05B043","Name":"Unknown"},{"Grade":13.05,"Code":"S07A004","Name":"Unknown"},{"Grade":7.49,"Code":"S07A005","Name":"Unknown"},{"Grade":14.93,"Code":"S07A009","Name":"Unknown"},{"Grade":17.69,"Code":"S08A005","Name":"Unknown"},{"Grade":10.74,"Code":"S09A003","Name":"Unknown"},{"Grade":1.53,"Code":"S09B020","Name":"Ko Pfxasolmmnq"},{"Grade":2.18,"Code":"S09B032","Name":"Unknown"},{"Grade":19.85,"Code":"S10A004","Name":"Unknown"}],"Name":"Okhbvpghwfypu Nic"}
```
### Question 2
Running `curl http://localhost:8090/Aiwsbu/v1/rates/01` on our server yields the following results:  
```json
[{"Year":2002,"Rate":0.465},{"Year":2003,"Rate":0.48},{"Year":2004,"Rate":0.496},{"Year":2005,"Rate":0.514},{"Year":2006,"Rate":0.521},{"Year":2007,"Rate":0.505},{"Year":2008,"Rate":0.461},{"Year":2009,"Rate":0.495},{"Year":2010,"Rate":0.475},{"Year":2011,"Rate":0.497},{"Year":2012,"Rate":0.511},{"Year":2013,"Rate":0.479},{"Year":2014,"Rate":0.489},{"Year":2015,"Rate":0.487},{"Year":2016,"Rate":0.492},{"Year":2017,"Rate":0.503},{"Year":2018,"Rate":0.508},{"Year":2019,"Rate":0.491}]
```
### Question 3
Running `` on our server yields the following results:  
```json

```
### Question 4
Running `` on our server yields the following results:  
```json

```
### Question 5
Running `` on our server yields the following results:  
```json

```
### Question 6
Running `` on our server yields the following results:  
```json

```
### Question 7
Running `` on our server yields the following results:  
```json

```
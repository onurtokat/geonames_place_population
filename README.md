# Place Population Calculation with Radius(km)

Project purpose is to calculate population with given Place name and radius inputs. [Geonames data](http://download.geonames.org/export/dump/) is used as data source. 

## Getting Started

This project have requirements:

● Provides information for at least 10 big countries

This project has been tested on top 10 big countries (in terms of the data size). 

```HTML
Name                    Last modified      Size
US.zip                  2018-10-05 02:26   66M  
CN.zip                  2018-10-05 02:21   23M  
NO.zip                  2018-10-05 02:24   15M  
IN.zip                  2018-10-05 02:22   14M  
RU.zip                  2018-10-05 02:25   12M  
MX.zip                  2018-10-05 02:23   12M  
IR.zip                  2018-10-05 02:22   12M  
TH.zip                  2018-10-05 02:25  8.6M  
ID.zip                  2018-10-05 02:22  8.1M  
CA.zip                  2018-10-05 02:20  7.4M
```

Total of row count is 6249794.

● Potentially scalable and extendable to cover the entire world

Application provides user interface to upload new country data file to system

● Fast

Datas are stored in embedded MongoDB. Other in memory embedded db technologies have been tested. When comes to 
reading speed, MongoDB is the best choice.

● Self-contained: no use of external resources during computation

Spring boot provides self-contained. Application server and Database embedded itself. Therefore, it does not require  
external resources. 

● Accepts input (place name and radius) and returns a JSON output

Web user interface is provided to insert place name and radius inputs on this project.

### Prerequisites

You can run the application from the command line with Maven. Or you can build a single executable JAR file that contains all the necessary dependencies, classes, and resources, and run that. This makes it easy to ship, version, and deploy the service as an application throughout the development lifecycle, across different environments, and so forth.

Also, WAR file can be generated to be deployed inside the containers. spring-boot-maven-plugin supports as well. But 
additional configuration may required for embedded container dependencies.

In this application, when Spring boot wake up, application begins to load initial data to mongo db. Therefore, it is required to wait loading data to mongo db.

Data format which will be used for formating should be same as geonames data format like;


```HTML
The main 'geoname' table has the following fields :
---------------------------------------------------
geonameid         : integer id of record in geonames database
name              : name of geographical point (utf8) varchar(200)
asciiname         : name of geographical point in plain ascii characters, varchar(200)
alternatenames    : alternatenames, comma separated, ascii names automatically transliterated, convenience attribute from alternatename table, varchar(10000)
latitude          : latitude in decimal degrees (wgs84)
longitude         : longitude in decimal degrees (wgs84)
feature class     : see http://www.geonames.org/export/codes.html, char(1)
feature code      : see http://www.geonames.org/export/codes.html, varchar(10)
country code      : ISO-3166 2-letter country code, 2 characters
cc2               : alternate country codes, comma separated, ISO-3166 2-letter country code, 200 characters
admin1 code       : fipscode (subject to change to iso code), see exceptions below, see file admin1Codes.txt for display names of this code; varchar(20)
admin2 code       : code for the second administrative division, a county in the US, see file admin2Codes.txt; varchar(80) 
admin3 code       : code for third level administrative division, varchar(20)
admin4 code       : code for fourth level administrative division, varchar(20)
population        : bigint (8 byte int) 
elevation         : in meters, integer
dem               : digital elevation model, srtm3 or gtopo30, average elevation of 3''x3'' (ca 90mx90m) or 30''x30'' (ca 900mx900m) area in meters, integer. srtm processed by cgiar/ciat.
timezone          : the iana timezone id (see file timeZone.txt) varchar(40)
modification date : date of last modification in yyyy-MM-dd format
```

### Installing

You can run the application from the command line with Maven. In the project directory which contains pom.xml file, You 
can run the application using 

```HTML
mvn spring-boot:run
```

Or you can build the JAR file with 

```HTML
mvn clean package 
```

Then you can run the JAR file:

```HTML
java -jar target/geonames_place_population-0.0.1-SNAPSHOT.jar
```


If you want to use as WAR file, you need to change packaging tag as war in the pom.xml;

```HTML
<packaging>war</packaging>
```

and, again run the application using

```HTML
mvn clean package
```

war file can be seen inside the target directory.

File uploading web page can be accessed;

```HTML
localhost:8080/
```

In this page, the data file can be uploaded to insert mongo db.

Searching with place name and radius page can be accessed as;

```HTML
localhost:8080/search
```

## Running the tests

Test classes can found under src/test/java/com.onurtokat/ directory.

```HTML
/config/MongoDbSpringIntegrationTest.class
/controllers/MainControllerTest.class
/init/PlaceDataInitTest.class
/services/PlaceServiceImplTest.class
/storage/FileSystemStorageServiceTest.class
/utility/DistanceCalculatorTest.class
/SmokeTest.class
/SpringBootMongoDbApplicationTest.class
```
### Break down into end to end tests

<li>MongoDB configuration correctness have been checked</li>
<li>GET and POST mapping method returns have been checked</li>
<li>Data loading steps and exception throwns have been checked</li>
<li>Repository data correctness have been checked</li>
<li>File operations and exceptions have been checked</li>
<li>Haversine formula for distance calculation correctness has been checked</li>
<li>Simulated controller</li>
<li>SpringBoot application checked with mvc</li>  

## Deployment

 This project can be ran as JAR in the;
 
 <li>Locally for testing</li>
 <li>On a server</li>
 <li>On a server hosted by cloud provider</li>
 <li>In a container</li>
 <li>In a container hosted by cloud provider</li>

## Built With

* [Spring Boot 2.0.0.M7](http://spring.io/guides) - The Spring Boot framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Embedded MongoDB 1.50.5](https://www.mongodb.com/) - Used to generate Embedded Database
* [Thymleaf](https://www.thymeleaf.org/) - Used to web framework
 

## Authors

Onur Tokat

## Limitations

* Due to geonames data, data format requires tab delimited  
* Assuming geonames data contains correct data values
* When Spring boot wake up, application begins to load initial data to mongo db. Therefore, it is required to wait loading data to mongo db.
* Application have multi-file upload functionality, but not completed. Therefore, cannot be used

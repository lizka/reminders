#REMINDERS

##Description

This app serves as a reminders-api which can serve as a backend for a potential application. It provides:
* endpoints to handle reminders (create, read)
* a poller which polls Liberty Global schedule-api for schedule data about created reminders 

To use the code you need to have java 8 installed and maven 3+.

##Archetype

The app was generated using a maven archetype for dropwizard apps, prepared by liberty-global. To use it, just type in 

```mvn archetype:generate -DarchetypeGroupId=pl.najda -DarchetypeArtifactId=dw-archetype -DarchetypeVersion=1.1``` 

or just 

```mvn archetype:generate```

and then choose the proper archetype (called ```dw-archetype```)

##Usage
* Building the app 

```mvn clean install```

* Starting app

```java -jar {path_to_jar} server {path_to_conf_file}```

should be something like this:

```java -jar reminders-service/target/reminders-service-1.0-SNAPSHOT.jar server conf/config.yml```

* Create a reminder for a user 

```curl -X POST -v -H "Content-type:application/json" localhost:8082/users/{userId}/reminders/ -d "{ \"title\" : \"TEST\" }" ```

* Get user reminders

```curl -X GET -v localhost:8082/users/{userId}/reminders ```

* Get specific reminder

```curl -XGET -v localhost:8082/users/{userId}/reminders/{reminderId} ```

* Administrator page (built in dropwizard): http://localhost:8083/

##Useful links
* Dropwizard documentation : http://www.dropwizard.io/getting-started.html
* Some Martin Fowler : http://martinfowler.com/articles/richardsonMaturityModel.html, https://www.youtube.com/watch?v=wgdBVIX9ifA, http://martinfowler.com/articles/microservices.html
* Netflix lessons : http://nginx.com/blog/adopting-microservices-at-netflix-lessons-for-team-and-process-design


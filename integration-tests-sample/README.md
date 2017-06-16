# integration-tests-sample

This sample application shows how to package a simple Java/MongoDB application with Docker. It highlights also how to do integration tests.

## Running MongoDB and the application:


Compile the application and generate the docker images

```
mvn clean install -Papp-docker-image
``` 


Start all the services 

```
docker-compose --file docker/docker-compose.yml up -d
```

Run the application
```
docker-compose --file docker/docker-compose.yml \
   run integration-tests-sample \
   java -jar \
   /maven/jar/integration-tests-sample-1.0-SNAPSHOT-jar-with-dependencies.jar mongo 
```

Stop all the services
```
docker-compose --file docker/docker-compose.yml down
``` 

## Running integration tests:

Start mongo service with a special `docker-compose-yml` file created for testing. This file does not map any ports or volumes:

```
docker-compose --file src/test/resources/docker-compose.yml up -d mongo 
```

Run the application

```
docker-compose --file src/test/resources/docker-compose.yml \
               run integration-tests-sample \
               java -jar /maven/jar/integration-tests-sample-1.0-SNAPSHOT-jar-with-dependencies.jar mongo 
```

Run the integration tests

```
docker-compose --file src/test/resources/docker-compose.yml \
               run integration-tests-sample-tests mvn -f /maven/code/pom.xml \
               -Dmaven.repo.local=/m2/repository -Pintegration-test verify 
```

If you want to remote debug tests, run instead
```
docker run -v ~/.m2/repository:/m2/repository \
       -p 5005:5005 --link mongo:mongo \
       --net resources_default \
       integration-tests-sample-tests mvn -f /maven/code/pom.xml \
       -Dmaven.repo.local=/m2/repository  -Pintegration-test\
       verify -Dmaven.failsafe.debug
```

Stop all the services

```
docker-compose --file src/test/resources/docker-compose.yml down
```

No state will be saved, so next time you run the tests, your database will be empty.

If you are running integration tests inside a tool like Jenkins, for example, you may want to be able to run simultaneous tests. In this case, you need to start the application in a separate network. To do that, use the `-p` switch with docker:

```
docker-compose -p app-$BUILD_NUMBER --file docker/docker-compose.yml up -d
```
In this example, the variable `BUILD_NUMBER` is a Jenkins variable that contains the current build number. Running this way, the services started by a build will run in a network used only by that build.



# Generates the images
mvn clean install -Papp-docker-image

# Starts mongo service
docker-compose --file src/test/resources/docker-compose.yml up -d mongo 

# Waits for services do start
sleep 30

# Run our application
docker-compose --file src/test/resources/docker-compose.yml run integration-tests-sample java -jar /maven/jar/integration-tests-sample-1.0-SNAPSHOT-jar-with-dependencies.jar mongo 

# Run our integration tests
docker-compose --file src/test/resources/docker-compose.yml run integration-tests-sample mvn -f /maven/code/pom.xml -Dmaven.repo.local=/m2/repository -Pintegration-test verify 
# If you want to remote debug tests, run instead
# docker run -v ~/.m2/repository:/m2/repository -p 5005:5005 --link mongo:mongo --net resources_default integration-tests-sample mvn -f /maven/code/pom.xml -Dmaven.repo.local=/m2/repository -Pintegration-test verify -Dmaven.failsafe.debug


# Stop all the services
docker-compose --file src/test/resources/docker-compose.yml down

# debugging-sample
This project shows how to debug Java applications inside Docker containers.

## How to run the demo

Build the project:

```
mvn clean install
```

A new image will be generated. See:

```
> docker images

REPOSITORY         TAG                 IMAGE ID            CREATED             SIZE
debugging-sample   latest              f13295b35871        2 minutes ago       610 MB
```

Open the project in your IDE and set a breakpoint. For example:

![Netbeans with breakpoint](https://raw.githubusercontent.com/fabianenardon/docker-java-issues-demo/master/debugging-sample/images/debug1.png)

Then, run the application inside Docker with the debugging instruction:

```
> docker run -p5005:5005 -e JAVA_OPTIONS=\
 '-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005' \
 debugging-sample
```

The JAVA_OPTIONS variable is defined in our `pom.xml` in the image definition. This instruction will make the application stop and wait until the IDE connects to the debugging port.

Go to the IDE and attach the debug to the application, using port 5005 in this example:

![Netbeans attaching debug](https://raw.githubusercontent.com/fabianenardon/docker-java-issues-demo/master/debugging-sample/images/debug2.png)

![Netbeans attaching debug with port](https://raw.githubusercontent.com/fabianenardon/docker-java-issues-demo/master/debugging-sample/images/debug3.png)

The application will run inside Docker and stop at your breakpoint.
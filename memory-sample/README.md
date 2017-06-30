# memory-sample
This project is a demo for common memory issues faced when running Java applications inside Docker containers.

## How to run the demo

Build the project:

```
mvn clean install
```

A new image will be generated. See:

```
> docker images

REPOSITORY         TAG                 IMAGE ID            CREATED             SIZE
memory-sample      latest              9a4703216893        2 days ago          610 MB
```

Give more than 100M memory to your Docker machine and then run a container, giving 100M to it:

```
> docker run --memory 100M memory-sample
```

The application will print how much memory it is using. You will notice that Docker will kill the application when it exceeds 100M of memory. There will be no OutOfMemoryException. You will also notice that the application will print the total memory available as something above 100M. This is the VM default when no max memory is specified. This shows that the java vm is not aware of the `--memory` switch used by Docker. This switch just means "kill the application if it allocates more than 100M", and this is just what Docker does.

To fix this, run the application like this:

```
> docker run --memory 100M -e JAVA_OPTIONS='-Xmx100m' memory-sample
```


As of Java 8u131 there is now an experimental VM option so the JVM is aware of the --memory switch used by Docker:

```
> docker run --memory 100M -e JAVA_OPTIONS='-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap' memory-sample
```

The JAVA_OPTION variable is defined in our pom.xml, when the image is created.

You'll notice that when running with JAVA_OPTIONS switch, the total memory reported by the application will be 100M and you'll get an OutOfMemoryException, as expected, when the memory allocated goes over 100M.

There is an excellent discussion about this on this article, by Rafael Benevides: https://developers.redhat.com/blog/2017/03/14/java-inside-docker/

I had also a lot of interesting insights about this subject from Elijah Zupancic (https://twitter.com/shitsukoisaru), from [Joyent](https://www.joyent.com/).





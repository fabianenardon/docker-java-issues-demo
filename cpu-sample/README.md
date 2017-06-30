# cpu-sample
This project is a demo for common cpu issues faced when running Java applications inside Docker containers.

## How to run the demo

Build the custom openjdk-numcpus-lib image:
```
docker build ../docker-images/openjdk-numcpus-lib/ -t openjdk-numcpus-lib:latest
```

Build the project:

```
mvn clean install
```

Two new images will be generated. See:

```
> docker images

REPOSITORY               TAG                 IMAGE ID            CREATED             SIZE
cpu-sample               latest              xxxxxxxxxxxx        5 seconds ago       610 MB
cpu-sample-numcpus-lib   latest              xxxxxxxxxxxx        5 seconds ago       610 MB
```

If you run a container inside Docker, it will use the number of CPUs from the HOST machine to calculate how many threads it can use in parallel, even if you limit the CPUs with --cpus, because the application relies on Runtime.getRuntime().availableProcessors() and the JVM is not aware of the --cpus switch.

```
> docker run -e LOOP=2 --cpus=2 cpu-sample
Jun 20, 2017 9:42:41 PM com.mycompany.cpu.sample.App main
INFO: cores 4
points: 200000000
points_per_thread: 50000000
number_of_executions: 2
pi = 3.14163464	error = 0.0013% 	in: 6663 ms
pi = 3.1417028	error = 0.0035% 	in: 6293 ms
```


To fix this cpu issue, you have two options:

1. Change your application to not rely on Runtime.getRuntime().availableProcessors() to calculate available CPUs

or
2. Use a custom LD_PRELOAD library to override the JVM default behavior and change the number of available CPUs. This is useful if you can not change the application. 

[cpu-sample-numcpus-lib](../docker-images/openjdk-numcpus-lib/)
Image  has a custom LD_PRELOAD already compiled and an environment variable exported.

```
> docker run -e _NUM_CPUS=2 -e LOOP=2 --cpus=2  cpu-sample-numcpus-lib
Jun 20, 2017 9:49:23 PM com.mycompany.cpu.sample.App main
INFO: cores 2
points: 200000000
points_per_thread: 100000000
number_of_executions: 2
pi = 3.14149862	error = 0.0030% 	in: 2107 ms
pi = 3.14148676	error = 0.0034% 	in: 3310 ms
```



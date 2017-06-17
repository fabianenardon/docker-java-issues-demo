# entropy-sample
This project is a demo for common entropy issues faced when running Java applications inside Docker containers.

## How to run the demo

Build the project:

```
mvn clean install
```

A new image will be generated. See:

```
> docker images

REPOSITORY         TAG                 IMAGE ID            CREATED             SIZE
entropy-sample     latest              xxxxxxxxxxxx        5 seconds ago       610 MB
```

If you run a container using a Doker inside a host with low entropy, the application will block until the host system has enough entropy:

```
> docker run entropy-sample
Jun 17, 2017 7:02:56 PM com.mycompany.entropy.sample.App main
INFO:
Requesting 2 random bytes
```


To fix this blocking issue, you have a few options:

1. Edit $JAVA_HOME/jre/lib/security/java.security and change the default blocking securerandom.strongAlgorithms to NativePRNGNonBlocking:SUN

```
> sed -i 's/^\(securerandom.strongAlgorithms\)=.*$/\1=NativePRNGNonBlocking:SUN/' $JAVA_HOME/jre/lib/security/java.security
```

or
2. Use a non blocking instance, specifying the algorithm and provider when calling SecureRandom 

```
SecureRandom.getInstance("NativePRNGNonBlocking", "SUN");
```

or
3. Install in the Host system (outside docker container) an entropy daemon like haveged to improve the entropy pool.
```
Debian/Ubuntu: apt-get install haveged
RHEL/CentOS/Fedora: yum install haveged
```

After making one of above changes, you should not have a blocking issue:

```
$ docker run entropy-sample
Jun 17, 2017 7:41:25 PM com.mycompany.entropy.sample.App main
INFO:
Requesting 2 random bytes

[89, -55]

Jun 17, 2017 7:41:25 PM com.mycompany.entropy.sample.App main
INFO: Took 56 ms
```

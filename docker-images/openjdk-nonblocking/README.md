# openjdk-nonblocking
This project is a demo for commons issues when running java applications on docker
This is a docker demo image with changed securerandom.strongAlgorithms, using a non-blocking implementation

To build this image:
```
> docker build . -t openjdk-nonblocking
```
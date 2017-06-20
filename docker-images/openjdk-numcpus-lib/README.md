# openjdk-numcpus-lib
This project is a demo for commons issues when running java applications on docker
This is a docker demo image with a custom LD_PRELOAD library written in C, and a LD_PRELOAD environment variable already set.

To build this image:
```
> docker build . -t openjdk-numcpus-lib
```
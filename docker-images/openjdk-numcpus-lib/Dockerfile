FROM openjdk:latest

COPY lib/numcpus.c /var/tmp/

RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y gcc && \
    gcc -O3 -fPIC -shared -Wl,-soname,libnumcpus.so -o /var/lib/libnumcpus.so /var/tmp/numcpus.c

ENV LD_PRELOAD /var/lib/libnumcpus.so
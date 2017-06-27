FROM openjdk:latest

RUN sed -i 's/^\(securerandom.strongAlgorithms\)=.*$/\1=NativePRNGNonBlocking:SUN/' $JAVA_HOME/jre/lib/security/java.security

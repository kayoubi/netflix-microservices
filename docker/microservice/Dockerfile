FROM ubuntu:16.04

RUN apt-get update -y && apt-get install -y git && mkdir /app && mkdir /app/microservice

RUN echo deb http://http.debian.net/debian jessie-backports main >> /etc/apt/sources.list
RUN apt-get install -y openjdk-8-jdk
RUN update-alternatives --config java

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64

WORKDIR /app/microservice
COPY build/ /app/microservice
RUN ./gradlew clean build
EXPOSE 9090

CMD ["./gradlew", "run"]


# docker build -t kayoubi/meetup --network=host .
# docker run -d -p 9090:9090 --net packNet --name meetup kayoubi/meetup
# docker run -d -p 9090:9090 --net packNet --name meetup --link redis:localhost kayoubi/meetup
#   this didn't work!! even though we set it in GuiceBindings maybe becaues it's "localhost"?
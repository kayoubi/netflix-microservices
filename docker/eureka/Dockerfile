FROM tomcat:8.5

RUN apt-get update -y && apt-get install -y git && mkdir /app

RUN echo deb http://http.debian.net/debian jessie-backports main >> /etc/apt/sources.list
RUN apt-get install -y openjdk-8-jdk
RUN update-alternatives --config java

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64

WORKDIR /app 
RUN git clone https://github.com/Netflix/eureka.git

WORKDIR /app/eureka
RUN git checkout 1.4.x && ./gradlew clean build -x test

RUN cp /app/eureka/eureka-server/build/libs/eureka-server-*.war /usr/local/tomcat/webapps/eureka.war

EXPOSE 8080

# docker build -t eureka . --network=host
# run -d -p 8080:8080 --net packNet --ip 172.10.0.55 --name eureka kayoubi/eureka
# http://localhost:8080/eureka/ this is mac can't use the IP address from above so I opened the port 8080 (-p 8080:8080) and used "localhost"
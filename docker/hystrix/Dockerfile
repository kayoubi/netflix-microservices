FROM tomcat:8.5

RUN apt-get update -y && apt-get install -y git && mkdir /app

RUN echo deb http://http.debian.net/debian jessie-backports main >> /etc/apt/sources.list
RUN apt-get install -y openjdk-8-jdk
RUN update-alternatives --config java

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64

WORKDIR /app 
RUN git clone https://github.com/Netflix/Hystrix.git

WORKDIR /app/Hystrix
RUN git checkout 1.4.x && ./gradlew clean build -x test

RUN cp /app/Hystrix/hystrix-dashboard/build/libs/hystrix-dashboard-*.war /usr/local/tomcat/webapps/hystrix-dashboard.war

EXPOSE 8080

# docker build -t hystrixdash . --network=host
# docker run -d -p 8181:8080 --net packNet --ip 172.10.0.60 --name hystrixdash kayoubi/hystrixdash
# http://localhost:8181/hystrix-dashboard/ this is mac can't use the IP address from above so I opened the port 8181 (-p 8181:8080) and used "localhost"

# running hystrix producer 
# docker run -d -p 7070:7070 --network packNet --ip 172.10.0.71 --name hystrixprod diegopacheco/hystrixprod
# then using http://172.10.0.71:7070/hystrix.stream (in the dashboard) note not using localhost but the ip-address since both running in the same network :) while accessing the service from the browser was something like http://localhost:7070/calc/sum?a=10&b=6
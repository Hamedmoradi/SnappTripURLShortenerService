FROM adoptopenjdk/openjdk11
VOLUME /tmp
ENV TZ=Asia/Tehran
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ARG JAR_FILE=target/SnappTripURLShortenerService-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /opt/app.jar
WORKDIR /opt
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8080

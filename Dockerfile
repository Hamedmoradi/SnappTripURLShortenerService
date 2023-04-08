FROM adoptopenjdk/maven-openjdk11
VOLUME /tmp
ENV TZ=Asia/Tehran
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


COPY src /home/app/src
COPY pom.xml /home/app
RUN  mvn -f /home/app/pom.xml clean install -X -DskipTests
ARG JAR_FILE=target/SnappTripURLShortenerService-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /opt/app.jar
WORKDIR /opt
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
ENTRYPOINT exec java $JAVA_OPTS -jar /opt/app.jar --spring.datasource.url=$SPRING_DATASOURCE_URL --spring.redis.host=${REDIS_URL}
EXPOSE 8080

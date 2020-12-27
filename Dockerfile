#FROM openjdk:8-jdk
#
#COPY . /board
#WORKDIR /board
#
#RUN chmod +x ./gradlew
#CMD ["./gradlew","bootRun"]

FROM openjdk:8-jdk AS builder

LABEL maintainer="ydh9517@naver.com"

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM openjdk:8-jdk
COPY --from=builder build/libs/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","/app.jar"]

#ARG JAR_FILE=build/libs/*jar
#
#ADD ${JAR_FILE} board.jar
#
#ENTRYPOINT ["java","-jar","/board.jar"]


# ---------
#COPY . /board
#WORKDIR /board
#
#VOLUME /temp
#EXPOSE 8080
#
#RUN chmod +x ./gradlew
#CMD ["./gradlew","bootRun"]

# ------
#ARG JAR_FILE=build/libs/0.0.1-SNAPSHOT.jar
#ADD ${JAR_FILE} boardApp.jar
#
#ENTRYPOINT ["java","-jar","/boardApp.jar"]
#docker stop $(docker ps -a -q)
# docker rm $(docker ps -a -q)
# docker rmi $(docker images -q)
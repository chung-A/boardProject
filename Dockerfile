#FROM openjdk:8-jdk
#
#COPY . /board
#WORKDIR /board
#
#RUN chmod +x ./gradlew
#CMD ["./gradlew","bootRun"]

FROM openjdk:8-jdk

LABEL maintainer="ydh9517@naver.com"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=build/libs/*jar

ADD ${JAR_FILE} board.jar

ENTRYPOINT ["java","-jar","/board.jar"]


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
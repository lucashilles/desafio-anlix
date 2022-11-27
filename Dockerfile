FROM registry.access.redhat.com/ubi8/openjdk-11:1.11

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en'
ENV APP_HOME=/usr/app/
ENV JAVA_OPTS="-Djava.net.preferIPv4Stack=true"

WORKDIR $APP_HOME
COPY build.gradle gradle.properties gradlew settings.gradle $APP_HOME
COPY gradle $APP_HOME/gradle
COPY src $APP_HOME/src

USER root

RUN chmod +x gradlew
RUN ./gradlew quarkusGoOffline

WORKDIR /usr/app

EXPOSE 8080 5005
ENTRYPOINT ./gradlew quarkusDev -Djvm.args="-Djava.net.preferIPv4Stack=true"


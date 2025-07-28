FROM openjdk:17-jdk-alpine

WORKDIR /app

RUN apk add --no-cache musl-locales musl-locales-lang \
    && echo 'export LANG=en_US.UTF-8' > /etc/profile.d/locale.sh \
    && echo 'export LC_ALL=en_US.UTF-8' >> /etc/profile.d/locale.sh

ENV LANG=en_US.UTF-8
ENV LC_ALL=en_US.UTF-8

COPY target/t1-camp-backend-1.0-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]

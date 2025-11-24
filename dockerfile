# FROM eclipse-temurin:17-jdk AS builder
#
# WORKDIR /build
#
# COPY gradlew .
# COPY gradle gradle
#
# RUN chmod +x gradlew
#
# COPY build.gradle.kts .
# COPY settings.gradle.kts .
#
# RUN ./gradlew dependencies --no-daemon || true
#
# COPY src src
#
# RUN ./gradlew clean bootJar --no-daemon -x test
#
# FROM eclipse-temurin:17-jre
#
# WORKDIR /app
#
# COPY ./build/libs/student-semester-enrollment-0.0.1-SNAPSHOT.jar student-semester-enrollment.jar
#
# EXPOSE 8080
#
# ENTRYPOINT ["java", "-jar", "student-semester-enrollment.jar"]

FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY gradlew .
COPY gradle gradle
RUN chmod +x gradlew

COPY build.gradle.kts .
COPY settings.gradle.kts .
RUN ./gradlew dependencies --no-daemon || true

COPY src src

CMD ["./gradlew", "bootRun"]

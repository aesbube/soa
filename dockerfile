FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY ./build/libs/student-semester-enrollment-0.0.1-SNAPSHOT.jar student-semester-enrollment.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "student-semester-enrollment.jar"]
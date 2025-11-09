# Build stage
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /build

# Copy Gradle wrapper first
COPY gradlew .
COPY gradle gradle

# Make gradlew executable
RUN chmod +x gradlew

# Copy only dependency-related files
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Download dependencies (this layer will be cached)
RUN ./gradlew dependencies --no-daemon || true

# Now copy source code
COPY src src

# Build the application
RUN ./gradlew clean bootJar --no-daemon -x test

# Runtime stage
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY ./build/libs/student-semester-enrollment-0.0.1-SNAPSHOT.jar student-semester-enrollment.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "student-semester-enrollment.jar"]
# syntax=docker/dockerfile:1
FROM eclipse-temurin:25-jdk AS build
WORKDIR /workspace

COPY gradlew settings.gradle* build.gradle* gradle.properties* ./
COPY gradle ./gradle

# Copy ALL modules referenced in settings.gradle
COPY mcp-server/build.gradle mcp-server/
COPY web/build.gradle web/

# Only copy source for the module you're building
COPY mcp-server/src mcp-server/src

RUN ./gradlew --no-daemon :mcp-server:bootJar -x test

FROM eclipse-temurin:25
WORKDIR /app
COPY --from=build /workspace/mcp-server/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]

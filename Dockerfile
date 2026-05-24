# ========== Stage 1: Build ==========
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests -B

# ========== Stage 2: Deploy ==========
FROM tomcat:10.1-jdk17-temurin
# Remove default Tomcat webapps
RUN rm -rf /usr/local/tomcat/webapps/*
# Copy WAR as ROOT for deployment at /
COPY --from=build /app/target/biblioteca.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]

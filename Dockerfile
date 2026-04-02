# ============================================
# ETAPA 1: Build con Maven
# ============================================
FROM maven:3.17-eclipse-temurin-17 AS builder

WORKDIR /build

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# ============================================
# ETAPA 2: Runtime con Tomcat
# ============================================
FROM tomcat:10-jdk17

COPY --from=builder /build/target/tienda-cafe.war $CATALINA_HOME/webapps/ROOT.war

EXPOSE 8080

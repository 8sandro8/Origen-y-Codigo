# ============================================
# ETAPA 1: Build con Maven
# ============================================
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /build

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# ============================================
# ETAPA 2: Runtime con Tomcat
# ============================================
FROM tomcat:10.1-jdk21

# Copiar el driver MariaDB al classpath global de Tomcat
# Esto resuelve el bug "No suitable driver found for jdbc:mariadb"
COPY --from=builder /root/.m2/repository/org/mariadb/jdbc/mariadb-java-client/3.4.1/mariadb-java-client-3.4.1.jar $CATALINA_HOME/lib/

COPY --from=builder /build/target/ROOT.war $CATALINA_HOME/webapps/ROOT.war

EXPOSE 8080
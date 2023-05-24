FROM openjdk:17-oracle
WORKDIR /app
COPY ./target/ms-seguridad-1.0.jar app.jar
ENV _JAVA_OPTIONS "-Xmx512m -Xms256m -XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom  -Xverify:none -Djava.awt.headless=true  -Dfile.encoding=UTF-8 -Dspring.backgroundpreinitializer.ignore=true -Dspring.jmx.enabled=false"
ENTRYPOINT ["java","-jar", "app.jar"]
FROM openjdk:11
ADD build/libs/discovery-1.0.war application.war
ENTRYPOINT ["java", "-jar", "application.war"]
EXPOSE ${DISCOVERY_PORT}

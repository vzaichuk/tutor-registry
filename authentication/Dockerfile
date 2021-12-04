FROM openjdk:11
COPY build/libs/authentication-1.0.war application.war
COPY wait-for-discovery.sh wait-for-discovery.sh
RUN chmod +x wait-for-discovery.sh
EXPOSE 8081
CMD ./wait-for-discovery.sh discovery-service 8761 && java -jar application.war

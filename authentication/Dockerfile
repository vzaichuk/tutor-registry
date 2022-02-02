FROM openjdk:11
COPY build/libs/authentication-1.0.war application.war
COPY wait-for-discovery.sh wait-for-discovery.sh
RUN chmod +x wait-for-discovery.sh
EXPOSE ${AUTHENTICATION_PORT}
CMD ./wait-for-discovery.sh ${DISCOVERY_HOST} ${DISCOVERY_PORT} ${WAIT_TIME} && java -jar application.war

FROM openjdk:11
ADD build/libs/discovery-1.0.war application.war
COPY wait-for-mongo.sh wait-for-mongo.sh
COPY wait-for-mysql.sh wait-for-mysql.sh
RUN chmod +x wait-for-mongo.sh
RUN chmod +x wait-for-mysql.sh
RUN apt update && apt install -y netcat
EXPOSE ${DISCOVERY_PORT}
CMD ./wait-for-mongo.sh ${MONGO_HOST} ${MONGO_PORT} ${WAIT_TIME} && ./wait-for-mysql.sh ${MYSQL_HOST} ${MYSQL_PORT} ${WAIT_TIME} && java -jar application.war

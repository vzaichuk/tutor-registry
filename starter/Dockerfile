FROM openjdk:11
ADD build/libs/starter-1.0-all.jar application.jar
COPY wait-for-mongo.sh wait-for-mongo.sh
COPY wait-for-mysql.sh wait-for-mysql.sh
RUN chmod +x wait-for-mongo.sh
RUN chmod +x wait-for-mysql.sh
RUN apt update && apt install -y netcat
CMD ./wait-for-mongo.sh ${MONGO_HOST} ${MONGO_PORT} ${WAIT_TIME} && ./wait-for-mysql.sh ${MYSQL_HOST} ${MYSQL_PORT} ${WAIT_TIME} && java -jar application.jar
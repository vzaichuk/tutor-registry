FROM openjdk:11
ADD build/libs/starter-1.0-all.jar application.jar
COPY wait-for-mongo.sh wait-for-mongo.sh
RUN chmod +x wait-for-mongo.sh
CMD ./wait-for-mongo.sh ${MONGO_HOST} ${MONGO_PORT} && java -jar application.jar

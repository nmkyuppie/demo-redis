FROM eclipse-temurin:17-jdk-jammy
EXPOSE 8080
ADD target/redis-0.0.2-SNAPSHOT.jar redis.jar
CMD ["java","-jar","/redis.jar"]
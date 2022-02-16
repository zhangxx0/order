FROM hub.c.163.com/library/java:8-alpine

MAINTAINER xinxin 377241804@qq.com

ADD server/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app.jar"]
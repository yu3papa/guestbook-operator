FROM bellsoft/liberica-openjdk-alpine:21

COPY target/guestbook-operator-1.0.jar /app/app.jar

EXPOSE 80

CMD ["java", "-jar", "/app/app.jar"]
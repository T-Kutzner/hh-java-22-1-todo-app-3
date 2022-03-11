FROM openjdk:17

ENV ENVIRONMENT=prod

LABEL Tanja_Kutzner="t.kutzner@arcor.de"

ADD backend/target/todo-app-backend-0.0.1-SNAPSHOT.jar todo-app.jar

CMD [ "sh", "-c", "java -jar /todo-app.jar" ]
FROM openjdk:8

COPY target/arosaje-*.jar arosaje.jar

EXPOSE 8081

CMD [ "java","-jar","arosaje.jar" ]
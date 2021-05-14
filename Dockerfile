FROM tomcat:10.0-jdk11-openjdk-slim
COPY /target/manager.war /usr/local/tomcat/webapps/manager.war
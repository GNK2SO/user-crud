FROM tomcat
COPY /target/manager.war /usr/local/tomcat/webapps/manager.war
EXPOSE 8080
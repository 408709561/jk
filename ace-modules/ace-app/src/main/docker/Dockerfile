FROM livingobjects/jre8
VOLUME /tmp
ADD ace-app.jar app.jar
ADD wait-for-it.sh /wait-for-it.sh
RUN bash -c 'touch /app.jar'
RUN bash -c 'chmod 777 /wait-for-it.sh'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

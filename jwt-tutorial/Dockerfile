FROM java8:alpine
RUN addgroup --gid 10011 -S appgroup && \
    adduser --uid 10010 -S -G appgroup appuser
RUN mkdir /opt/app &&\
    cd /opt/app
COPY target/*.jar application.jar
USER appuser
CMD ["java","-jar","application.jar"]
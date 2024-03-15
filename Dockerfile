FROM bellsoft/liberica-runtime-container:jdk-21-slim-musl AS builder

ENV JAVA_OPTS "-Xms128m -Xmx2048m -Duser.timezone=Europe/Moscow -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=10051 -Dcom.sun.management.jmxremote.rmi.port=10051 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.local.only=false -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

ENV WD /usr/local/ex

RUN mkdir -p "$WD"

WORKDIR $WD
COPY . $WD

EXPOSE 8080 10051

ENTRYPOINT java -jar $JAVA_OPTS $WD/build/libs/ex.jar

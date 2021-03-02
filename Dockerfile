FROM repo.gabia.com/gabia/base/openjdk:13-alpine

VOLUME /tmp
ADD /build/libs/*.jar app.jar

ARG GKMS_URL
ENV GKMS_URL=${GKMS_URL}

ARG GKMS_ROLE_ID
ENV GKMS_ROLE_ID=${GKMS_ROLE_ID}

ARG GKMS_SECRET_ID
ENV GKMS_SECRET_ID=${GKMS_SECRET_ID}

ARG GKMS_ENGINE_NAME
ENV GKMS_ENGINE_NAME=${GKMS_ENGINE_NAME}

ARG BUILD_TYPE
ENV BUILD_TYPE=${BUILD_TYPE}

ENTRYPOINT ["java",\
 "-server",\
 "-Xms256m",\
 "-Xmx256m",\
 "-Dserver.shutdown=graceful",\
 "-Dspring.lifecycle.timeout-per-shutdown-phase=25s",\
 "-jar", "/app.jar"]
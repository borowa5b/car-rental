FROM quay.io/keycloak/keycloak:latest
LABEL maintainer="borowa5b@gmail.com"
EXPOSE 8080

# Fly.io deployment
WORKDIR /opt/keycloak
RUN keytool -genkeypair -storepass password -storetype PKCS12 -keyalg RSA -keysize 2048 -dname "CN=server" -alias server -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -keystore conf/server.keystore
ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start"]

# Local deployment
#ENV KC_BOOTSTRAP_ADMIN_USERNAME=admin
#ENV KC_BOOTSTRAP_ADMIN_PASSWORD=admin
#ENV KC_DB=postgres
#ENV KC_DB_URL=jdbc:postgresql://host.docker.internal:5432/car-rental-keycloak
#ENV KC_DB_USERNAME=postgres
#ENV KC_DB_PASSWORD=postgres
#ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev"]
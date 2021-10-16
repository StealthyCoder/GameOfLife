FROM alpine:3.14.2
LABEL maintainer="stealthycoder@stealthycoder.com"

ENV JPDA_ADDRESS="5005"
ENV JPDA_TRANSPORT="dt_socket"
ENV SUSPEND_DEBUG=n
ENV ORG_GRADLE_CONSOLE=plain
ENV GRADLE_USER_HOME=/data/gradle

RUN apk update && \    
    apk add ca-certificates \
    sudo \
    vim \
    bash \
    wget \
    unzip\
    openjdk11 && \
    adduser -u 1000 -D go-adm && \
    adduser -u 1001 -D go && \
    adduser -u 1002 -D go-alt && \
    adduser -u 501 -D go-mac && \
    echo "%adm ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers && \
    echo "Set disable_coredump false" >> /etc/sudo.conf && \
    addgroup go-adm adm && \
    addgroup go adm && \
    addgroup go-alt adm && \
    addgroup go-mac adm && \
    mkdir -p /srv/http /data/gradle && \
    chmod 0775 -R /srv && \
    cd /opt && \
    sudo wget -q "https://services.gradle.org/distributions/gradle-7.2-bin.zip" && \
    sudo unzip -q "gradle-7.2-bin.zip" && \
    sudo rm "gradle-7.2-bin.zip" && \
    sudo mv "/opt/gradle-7.2" /opt/gradle && \
    sudo ln -s /opt/gradle/bin/gradle /usr/local/bin/gradle && \
    echo -e '#!/usr/bin/env bash\n\
    sudo chown -R $(id -u):$(id -g) /data/gradle\n\
    gradle build -x test\n\
    /usr/bin/env bash -l -c "$*" \n\
' >> /srv/entrypoint.sh  && \
    chmod +x /srv/entrypoint.sh && \
    chown go:go -R /srv && \
    rm -r /var/cache/apk/*

ENTRYPOINT [ "/srv/entrypoint.sh" ]
WORKDIR /srv/http
USER go:go

CMD ["/usr/bin/java", "-jar", "/srv/http/build/libs/*.jar"]

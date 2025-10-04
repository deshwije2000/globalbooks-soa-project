FROM tomcat:8.5-jdk17-corretto-al2

ENV ODE_VERSION=1.3.6
ENV ODE_URL=https://archive.apache.org/dist/ode/apache-ode-war-${ODE_VERSION}.zip

RUN yum update -y && yum install -y wget unzip && \
    wget ${ODE_URL} -O /tmp/ode.zip && \
    unzip /tmp/ode.zip -d /tmp && \
    mv /tmp/apache-ode-war-${ODE_VERSION} /usr/local/tomcat/webapps/ode && \
    rm -rf /tmp/* && \
    yum remove -y wget unzip && yum clean all
COPY ode-tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
COPY ode-server.xml /usr/local/tomcat/conf/server.xml   
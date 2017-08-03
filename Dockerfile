FROM 192.168.109.250:5000/openshift/cox-eap70-base
USER root

ADD standalone-openshift.xml /opt/eap/standalone/configuration/standalone-openshift.xml
RUN chown jboss:jboss /opt/eap/standalone/configuration/standalone-openshift.xml

ADD dbtest-1.0.war /opt/eap/standalone/deployments/dbtest-1.0.war
RUN chown jboss:jboss /opt/eap/standalone/deployments/dbtest-1.0.war
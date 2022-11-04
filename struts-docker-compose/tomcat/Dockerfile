FROM guligo/jdk-maven-ant:latest

# aptを最新化する。
RUN apt-get update

# 変数を設定する。
ENV TOMCAT_MAJOR 8
ENV TOMCAT_VERSION 8.5.83
ENV TOMCAT_HOME /etc/tomcat

# Tomcatをインストールする。
RUN cd /tmp
RUN wget https://downloads.apache.org/tomcat/tomcat-${TOMCAT_MAJOR}/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz
RUN mkdir tomcat-${TOMCAT_VERSION}
RUN tar -zxvf apache-tomcat-${TOMCAT_VERSION}.tar.gz --directory tomcat-${TOMCAT_VERSION} --strip-components=1
RUN mv tomcat-${TOMCAT_VERSION} ${TOMCAT_HOME}
ENV CATALINA_HOME ${TOMCAT_HOME}
ENV PATH ${PATH}:${CATALINA_HOME}/bin

# 必要なファイルを追加する。
ADD tomcat-users.xml ${TOMCAT_HOME}/conf/
ADD server.xml ${TOMCAT_HOME}/conf/
ADD context.xml ${TOMCAT_HOME}/webapps/manager/META-INF/

# 必要なディレクトリを作成する。
RUN mkdir ${TOMCAT_HOME}/webapps/struts-school-search
RUN chmod -R +x ${TOMCAT_HOME}/webapps/struts-school-search

# 不要なディレクトリを削除する。
RUN rm -rf ${TOMCAT_HOME}/webapps/docs
RUN rm -rf ${TOMCAT_HOME}/webapps/examples
RUN rm -rf ${TOMCAT_HOME}/webapps/host-manager
RUN rm -rf ${TOMCAT_HOME}/webapps/ROOT

# 環境をクリーンアップする。
RUN rm apache-tomcat-${TOMCAT_VERSION}.tar.gz
RUN unset TOMCAT_MAJOR
RUN unset TOMCAT_VERSION
RUN unset TOMCAT_HOME

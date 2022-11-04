FROM centos:7

# yumの最新化して必要なライブラリをインストールする。
RUN yum -y update && yum clean all
RUN yum -y install httpd httpd-devel gcc* make && yum clean all

# 設定ファイルを追加する。
ADD httpd-proxy.conf /etc/httpd/conf.d/

# 起動スクリプトを追加して実行する。
ADD run-httpd.sh /run-httpd.sh
RUN chmod -v +x /run-httpd.sh

# httpdを起動する。
CMD ["/run-httpd.sh"]

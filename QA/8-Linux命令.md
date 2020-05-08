# Linux高级命令
1、**整体性能监控 top**。内存，磁盘，网络等等。load average负载均衡，一分钟五分钟十五分钟系统的平均负载值，加起来除以三，超过60%，就说明压力重。  
```
top
```
2、**CPU压力监控** vmstat -n 3 2，采样2次，每次间隔3秒钟。  
输出列表中，us代表用户进程的CPU消耗，sy代表系统本身线程的消耗。  
id标识处于空闲CPU，wa标识等待io的CPU，st标识来自于一个虚拟机偷取的CPU时间。  
mpstat -P ALL 查看所有CPU核的信息，也可以采样 mpstat -P ALL 2 2
````
vmstat -n 3 2
mpstat -P ALL 2 2
````
3、**内存**。free -m 以MB为单位查看系统整体的内存使用情况， free -g 以GB为单位。
```
free
free -m 
free -g
```
4、**磁盘存储**。
```
#显示存储空间大小
df -h

#人性化显示各存储空间大小
df -ah

#显示所有存储系统空间使用情况,同时显示存储系统的文件系统类型
df -aT

#查看本地文件，不显示网络磁盘
df -ahlT

#显示当前文件夹的空间使用情况
du -sh

#查看/home文件夹的空间使用情况
du -h --max-depth=1 /home

#看当前文件及文件中包含的子文件夹大小
du -ch

#查看某个文件容量大小
du -h file.txt

#查看多个文件容量大小
du -h file1.txt file2.txt 
```
5、**磁盘IO监控** iostat -xdk 2 3 两秒一次，总共采样3次.  
rkB/s 每秒读取数据量KB  
wkB/s 每秒写入数据量KB
svctm IO请求的平均服务时间 毫秒
await IO请求的平均等待时间 毫秒
util 一秒内处理IO请求的时间占比，接近100代表IO带宽打满。await远高于svctm代表IO等待队列太长。

```
[root@tool ~]# iostat -xdk 2 3
Linux 2.6.32-642.el6.x86_64 (tool) 	2020年05月08日 	_x86_64_	(8 CPU)

Device:         rrqm/s   wrqm/s     r/s     w/s    rkB/s    wkB/s avgrq-sz avgqu-sz   await r_await w_await  svctm  %util
sda               1.83     6.84    4.55    0.83   127.86    30.65    58.94     0.09   16.67    8.01   64.37   3.83   2.06

Device:         rrqm/s   wrqm/s     r/s     w/s    rkB/s    wkB/s avgrq-sz avgqu-sz   await r_await w_await  svctm  %util
sda               0.00     0.00    0.50    0.00     4.00     0.00    16.00     0.01   10.00   10.00    0.00  10.00   0.50

Device:         rrqm/s   wrqm/s     r/s     w/s    rkB/s    wkB/s avgrq-sz avgqu-sz   await r_await w_await  svctm  %util
sda               0.00     0.00    0.00    0.00     0.00     0.00     0.00     0.00    0.00    0.00    0.00   0.00   0.00

```

6、**网络IO** ifstat 1 各个网卡的in out(没有需要下载)
```
ifstat 1
```
















》开放/限制端口：
若要允许远程连接，请在 RHEL 的防火墙上打开 SQL Server 端口。
例如：默认的 SQL Server 端口为 TCP 1433。 
如果为防火墙使用的是 FirewallD，则可以使用以下命令：
sudo firewall-cmd --zone=public --add-port=1433/tcp --permanent
sudo firewall-cmd --reload
》限制端口
firewall-cmd --zone=public --remove-port=22/tcp --permanent
》查看当前系统打开的所有端口
firewall-cmd --zone=public --list-ports


》curl发送请求，获取数据
curl -h来查看请求参数的含义
-v 显示请求的信息
-X 选项指定其它协议
例如：
curl -v -X GET 'http://10.140.76.57:80/'
》eureka手动下线
curl -v -X DELETE 'http://rm:123456@10.188.180.41:2010/eureka/apps/BS-STORE-SORT-SERVERS/10.188.180.41:4453'

》开放端口2:
/sbin/iptables -I INPUT -p tcp --dport 8011 -j ACCEPT #开启8011端口 
/etc/rc.d/init.d/iptables save #保存配置 
/etc/rc.d/init.d/iptables restart #重启服务 
查看端口是否已经开放
/etc/init.d/iptables status


》查看占用端口的进程pid：命令netstat
netstat -nlp|grep :80
[root@localhost sbin]# netstat -nlp|grep :80
tcp        0      0 0.0.0.0:80                  0.0.0.0:*                   LISTEN      8246/nginx 
》命令ps，可以查看已知进程PID的执行目录的详细信息
ps -ef | grep 8246


》telnet IP 端口
yum install telnet


》查找文件：find / -name "*svn*"
》查找程序：whereis java
》查找某个系统命令的位置（在PATH变量指定的路径中）：which php
》看PATH路径  echo $JAVA_HOME

》查看文件大小：ls -lht

》查查看文件系统格式：df -T -h

》查看系统版本：cat /etc/redhat-release

》查看CPU信息：cat /proc/cpuinfo

》看MAC地址  ip address show

》查看系统内存使用量和交换区使用量：free -m

》查看内存大小的详细信息：cat /proc/meminfo

》查看磁盘以及分区情况



》查看和修改Linux的时间
1. 查看时间和日期
命令 ： date  
格式化显示：date "+%Y-%m-%d %H:%M:%S"
2.设置时间和日期
例如：将系统日期设定成2009年11月3日的命令
命令 ： date -s 11/03/2009
将系统时间设定成下午5点55分55秒的命令
命令 ： date -s 17:55:55
3.查看时区：
命令：date -R 
或：date +"%Z %z"
-0500 为-5时区
4.置中国时区使用亚洲/上海（+8）
命令：cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
5.同步网络时间
可使用ntpdate进行时间同步。命令如下：sudo ntpdate <ip address>。
检查是否安装了ntpdate : rpm -qa | grep ntpdate
如果没有安装则运行如下命令：yum install ntpdate
例如：
sudo ntpdate ntp1.aliyun.com #阿里云NTP服务
6. 将当前时间和日期写入BIOS，避免重启后失效
命令 ： hwclock -w


》自启动文件脚本（目录/etc/init.d，文件一般以d结尾）
添加命令：chkconfig --add /etc/init.d/redisd
查看启动项，命令：chkconfig --list
设置命令启动：chkconfig redisd on
关闭命令：chkconfig redisd off
删除命令为：chkconfig --del redisd


》授权sh脚本（777读/写/执行权限）
chmod 777 ./*.sh


》压缩文件
gzip，压缩文件名：zip或gz，解压命令：unzip
bzip2，压缩文件名：bz，解压命令；bzip2 -d
tar -zcvf 压缩文件名.tar.gz 原文件
（例如：tar -zcvf myarchive.tar /etc /root/anaconda-ks.cfg将 /etc/ 目录和 /root/anaconda-ks.cfg 文件打包进去）


》解压文件
tar -zxvf myarchive.tar -C /tmp/ 使用-C :选项后边加上解压到的指定的文件的路径。


》查历史日志：
ls -lrt *04-26*
bzcat console.log.2018-04-28.81.bz2 |more
解压1：bzip2 -d FileName.bz2
vi -y9999999 FileName  /   export LANG=zh_CN
ctrl+U往上，ctrl+D往下
view 日志名 esc:q!
cat SFINTF*log.2018-06-29|grep ProxyInvocationHandler
grep RadiusStatusQuery SFINTF*.log
traceroute ip
df -g 看各用户存储情况
du -g|awk '$1>1 {print $1,$2}' 看大于1G的文件目录


》FTP：
第一步：
ssh sfinter@10.174.22.28
cd /home/sfinter/deploy/sfCentre/WEB-INF/lib
ftp 10.168.141.14
ftp>bin  (二进制传输)
ftp>put sfCentre.jar
Ctrl+Z退出 
:q
rm sfCentre.jar (备份28接口主机上的jar包)
第二步：从14主机上取jar包，更新后再上传到14主机上
第三步：
ssh sfinter@10.174.22.28
cd /home/sfinter/deploy/sfCentre/WEB-INF/lib
ftp>bin  (二进制传输)
ftp>get 文件


》svn重启：
ps -ef|grep svn查看进程
svnserve -d -r /var/svn/svnrepos重启（find / -name "*svnrepos*"，svnrepos为svn数据库路径）


》db2启动：
su - db2inst1
db2start


》rabbitmq启动
./rabbitmq-server -detached
启动web管理插件
./rabbitmq-plugins enable rabbitmq_management


》./nginx 重新加载命令./nginx -s reload，
出现
nginx: [error] invalid PID number "" in "/usr/local/nginx/logs/nginx.pid"问题。
解决办法：
第一步：执行命令? killall -9 nginx? ?杀掉nginx 进程
第二步：执行命令??/usr/local/nginx/sbin/nginx -c /usr/local/nginx/conf/nginx.conf 重新加载配置文件
第三步：执行命令??./nginx -s reload 重新启动nginx


》jar包打到本地maven仓库
mvn install:install-file -DgroupId=com.hsit -DartifactId=common-utils -Dversion=1.0-SNAPSHOT -Dpackaging=jar -Dfile=F:/IdeaProjects/hsit_yunnan/rm/code/yn-rm/common-utils/target/common-utils-1.0-SNAPSHOT.jar


》看java进程：
java命令：jps
linux命令,根据pid线程id,，H代表查看线程：top -H -P pid
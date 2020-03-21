#!/bin/sh
echo '开始更新版本...'
echo '更新html文件...'
svn up /home/website/langqibox
if [ $? -eq 0 ];then
    echo '...更新成功.'
fi
echo '更新java代码...'
svn up /home/workspace/langqibox
if [ $? -eq 0 ];then
    echo '...更新成功.'
fi
echo '开始打war包...'
cd /home/workspace/langqibox
gradle build -x test
echo '...打包完毕.'
cp /home/workspace/langqibox/build/libs/langqibox-0.0.1.war /home/tomcat/webapps/langqibox.war
echo '重启tomcat'
catalina.sh stop
catalina.sh start
echo '...重启Tomcat完毕.'
echo '5秒后打开tomcat日志，如果启动成功请按 Ctrl+C 退出'
sleep 5
tail -f /home/tomcat/logs/catalina.out

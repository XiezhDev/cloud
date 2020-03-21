#!/bin/sh
echo '开始更新版本...'

echo '更新代码...'
git pull
if [ $? -eq 0 ];then
    echo '...更新成功.'
fi
echo '开始打war包...'
cd /app/webapp/huigecloud
gradle build -x test
cd /app/webapp/huigecloud/build/libs
mv huigecloud-0.0.1-SNAPSHOT.war huigecloud.war
echo '...打包完毕.'
echo '重启tomcat'
docker stop f2034231efc5
docker start f2034231efc5
echo '...重启Tomcat完毕.'
echo '5秒后打开tomcat日志，如果启动成功请按 Ctrl+C 退出'
sleep 5
docker exec -it f2034231efc5 tail -f /usr/local/tomcat/logs/catalina.out

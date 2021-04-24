#!/bin/bash
APP_NAME=xcky-order-1.0.jar

is_exist(){
  pid=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}' `
  if [ -z "${pid}" ]; then
   return 1
  else
    return 0
  fi
}

start(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is already running. pid=${pid} ."
    kill -9 ${pid}
  fi

  cd target
  nohup /opt/Java/jdk-11.0.8/bin/java -server -Xmx128M -Xms128M -jar $APP_NAME &
  pid=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}' `
  echo "${APP_NAME} had run. pid=${pid} ."
}

case "$1" in
  "start")
    start
    ;;
esac

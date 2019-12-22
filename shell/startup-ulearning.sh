#!/bin/bash
nohup java -Xms128m -Xmx256m -jar -Dfile.encoding=utf-8 ulearning-register.jar >> console.log &
sleep 10
nohup java -Xms128m -Xmx256m -jar -Dfile.encoding=utf-8 ulearning-config.jar >> console.log &
sleep 20
nohup java -Xms128m -Xmx256m -jar -Dfile.encoding=utf-8 ulearning-monitor-manage.jar >> console.log &
nohup java -Xms128m -Xmx256m -jar -Dfile.encoding=utf-8 ulearning-monitor-manage.jar >> console.log &
nohup java -Xms128m -Xmx256m -jar -Dfile.encoding=utf-8 ulearning-system-manage.jar >> console.log &
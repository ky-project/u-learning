#!/bin/bash
if [ -f "./ulearning-register.jar" ]; then
	nohup java -Xms128m -Xmx256m -jar -Dfile.encoding=utf-8 ulearning-register.jar > console.log &
fi
sleep 50

if [ -f "./ulearning-config.jar" ]; then
	nohup java -Xms64m -Xmx128m -jar -Dfile.encoding=utf-8 ulearning-config.jar > console.log &
fi
sleep 60

if [ -f "./xxl-job-admin.jar" ]; then
	nohup java -Xms128m -Xmx256m -jar -Dfile.encoding=utf-8 xxl-job-admin.jar > console.log &
fi
sleep 20

if [ -f "./ulearning-gateway.jar" ]; then
	nohup java -Xms128m -Xmx256m -jar -Dfile.encoding=utf-8 ulearning-gateway.jar > console.log &
fi

if [ -f "./ulearning-monitor-manage.jar" ]; then
	nohup java -Xms128m -Xmx256m -jar -Dfile.encoding=utf-8 ulearning-monitor-manage.jar > console.log &
fi

if [ -f "./ulearning-system-manage.jar" ]; then
	nohup java -Xms128m -Xmx256m -jar -Dfile.encoding=utf-8 ulearning-system-manage.jar > console.log &
fi

if [ -f "./ulearning-teacher.jar" ]; then
	nohup java -Xms128m -Xmx256m -jar -Dfile.encoding=utf-8 ulearning-teacher.jar > console.log &
fi

if [ -f "./ulearning-student.jar" ]; then
	nohup java -Xms128m -Xmx256m -jar -Dfile.encoding=utf-8 ulearning-student.jar > console.log &
fi
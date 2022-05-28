#!/bin/bash
kill $(ps -ef | grep 'ulearning-system-manage.jar' | grep -v grep | awk '{print $2}')
kill $(ps -ef | grep 'ulearning-monitor-manage.jar' | grep -v grep | awk '{print $2}')
kill $(ps -ef | grep 'ulearning-gateway.jar' | grep -v grep | awk '{print $2}')
kill $(ps -ef | grep 'ulearning-teacher.jar' | grep -v grep | awk '{print $2}')
kill $(ps -ef | grep 'ulearning-student.jar' | grep -v grep | awk '{print $2}')
kill $(ps -ef | grep 'xxl-job-admin.jar' | grep -v grep | awk '{print $2}')
kill $(ps -ef | grep 'ulearning-config.jar' | grep -v grep | awk '{print $2}')
kill $(ps -ef | grep 'ulearning-register.jar' | grep -v grep | awk '{print $2}')
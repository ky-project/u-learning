#!/bin/bash
kill $(ps -aux | grep 'ulearning-system-manage' | awk 'NR==2{print $2}')
kill $(ps -aux | grep 'ulearning-monitor-manage' | awk 'NR==2{print $2}')
kill $(ps -aux | grep 'ulearning-gateway' | awk 'NR==2{print $2}')
kill $(ps -aux | grep 'ulearning-config' | awk 'NR==2{print $2}')
kill $(ps -aux | grep 'ulearning-register' | awk 'NR==2{print $2}')
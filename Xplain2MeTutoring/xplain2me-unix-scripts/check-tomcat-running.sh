#!/bin/bash +x

set -vx
# --------------------------------------------
#
# Checks if the Tomcat instance for the 
# web application running - if not, it
# attempt to start-up the application server.
# 
# Must be run as root user.
#
# --------------------------------------------

# variables
TOMCAT_HOME="/usr/local/apache-tomcat-8.0.14-xplain2me"
COUNT=0

# check the number of tomcat instances running
COUNT=`ps -ef | grep -v grep | grep 'org.apache.catalina.startup.Bootstrap' | grep 'xplain2me' | wc -l`

# if Tomcat is not running, start up tomcat
if [ ${COUNT} -eq 0 ]; then
	echo "No tomcat instances found, starting tomcat..."
	cd ${TOMCAT_HOME}/bin
	./startup.sh
	
# if only one instance is running then do nothing
elif [ ${COUNT} -eq 1 ]; then
	echo "Tomcat is already running - found one instance."

# else if more than one instance is running
else
	
	# kill all the instances running
	for PID in `ps -ef | grep -v grep | grep 'org.apache.catalina.startup.Bootstrap' | grep 'xplain2me' | awk '{print $2 }'`;
	do
		echo "killing tomcat instance with PID: ${PID}..."
		kill -9 ${PID}
	done
	
	# then startup the tomcat server again
	sleep 10
	
	echo "Starting tomcat server..."
	cd ${TOMCAT_HOME}/bin
	./startup.sh
fi

exit 0
#!/bin/bash

set -vx
# --------------------------------------------
#
# Checks the directory /tmp/xplain2me
# for any new war files that should be
# deployed to the tomcat server.
# 
# Must be run as root user.
#
# --------------------------------------------

# VARIABLES
FEED_DIRECTORY="/tmp/xplain2me"
WAR_FILE=
NUM_OF_WAR_FILES=0
CONTEXT_PATH="/xplain2me"
VERSION=

# read in the arguments
if [ $# -eq 1 ]; then
	echo "Reading in the version number..."
	VERSION=$1
	
else
	echo "No version found as an argument, exiting."
	exit 1
fi

# check if the feed directory exists
if [ ! -d $FEED_DIRECTORY ]; then
	echo "The feed directory does not exist."
	echo "The process will create this directory and then exit."
	mkdir -p $FEED_DIRECTORY
	exit 1
fi

# check if the war file exists
cd $FEED_DIRECTORY
NUM_OF_WAR_FILES=`ls -l *.war | wc -l`

# if too many war files, exit
if [ $NUM_OF_WAR_FILES -ne 1 ]; then
	echo "Please ensure that only one war file exists in the feed directory: $FEED_DIRECTORY"
	exit 1
fi

# get the war file name
WAR_FILE=`ls -l *.war | awk '{ print $9 }'`

# deploy the war file to tomcat
DEPLOY_URL="http://manager:8to0much@localhost:9000/manager/text/deploy?path=${CONTEXT_PATH}&update=true"

if [ `curl -T "${FEED_DIRECTORY}/${WAR_FILE}" "$DEPLOY_URL" | grep 'OK - Deployed application' | wc -l` -eq 1 ]; then 

	# copy the war file to the home directory
	cd /home/tsepo/xplain2me/deployment/war-archives/$VERSION
	rm -f *.war
	cp $FEED_DIRECTORY/$WAR_FILE .

	# delete the war file
	cd $FEED_DIRECTORY
	rm -f $WAR_FILE
else
	echo "Deployment failed..."
	exit 1

fi

exit 0

#!/bin/bash

COMPARE_HOME=`dirname $0`
LIB_PATH=${COMPARE_HOME}/lib

#export JAVA_HOME=${JAVA_HOME_1_7}
#export PATH=${JAVA_HOME}/bin:$PATH

unset LD_LIBRARY_PATH

if [ -f /ebi/lsf/ebi/conf/profile.lsf ]
then
    source /ebi/lsf/ebi/conf/profile.lsf
fi

export LD_LIBRARY_PATH=/ebi/research/software/Linux_x86_64/opt/stow/libsbml-5.x-svn-libxml2/lib
#export LD_LIBRARY_PATH=/ebi/research/software/Linux_x86_64/opt/stow/libsbml-5.0.0-libxml2-centos-4.6/lib:/ebi/research/software/Linux_x86_64/opt/stow/centos4-libraries/lib
#export LD_LIBRARY_PATH=/ebi/research/software/Linux_x86_64/opt/stow/libsbml-4.0.0-xerces/lib:$LD_LIBRARY_PATH
#export LD_LIBRARY_PATH=/usr/local/lib:$LD_LIBRARY_PATH

if [ $# -lt 1 ] 
 then
     echo "Usage: "
     echo "       compare [file.xml | folder]"     
     echo "              will compare the objects read by jsbml and libSBML"
     exit 1
fi

SBML_DIR=$1

LOG_FILE=${COMPARE_HOME}/log/`basename $SBML_DIR .xml`-compare-export-`date +%F`.log

TEST_CLASS=org.sbml.jsbml.test.SBMLReaderTest
#TEST_CLASS=org.sbml.jsbml.test.ReactionReadTest

JAVA_ARGS="  -Djava.util.logging.config.file=logging.properties "
# -XX:+UseSerialGC  -Xms1024m -Xms4096M  


COMMAND="bsub -o $LOG_FILE java "
#COMMAND="java -Djava.util.logging.config.file=logging.properties "


if [ "`which bsub 2> /dev/null`" == "" ] ; then
    COMMAND="java "
fi

export CLASSPATH=

for jarFile in $LIB_PATH/*.jar
do
    export CLASSPATH=$CLASSPATH:$jarFile
done

# JSBML_LIB_PATH=../../trunk/lib
# for jarFile in $JSBML_LIB_PATH/*.jar
# do
#     export CLASSPATH=$CLASSPATH:$jarFile
# done

for jarFile in $COMPARE_HOME/build/*.jar
do
    export CLASSPATH=$jarFile:$CLASSPATH
done



if [ -d $SBML_DIR ]
then
    for file in $SBML_DIR/*.xml
    do

	echo File name = $file

	## we have to make different log files because we don't know when they will be executed after being submitted in the queue
	LOG_FILE_MULTI=${COMPARE_HOME}/log/`basename $file .xml`-compare-export-`date +%F`.log
#	echo "------------------------------------------------------------" >> $LOG_FILE_MULTI   2>&1
#	echo "`date +"%F %R"`" >> $LOG_FILE_MULTI  2>&1
#	echo "`basename $0`: Comparing file '$file'..." >> $LOG_FILE_MULTI  2>&1
#	echo "------------------------------------------------------------" >> $LOG_FILE_MULTI  2>&1
# output removed to allow the log file to be zero byte if no errors.

	if [ ! "$COMMAND" == "java " ] ; then
	    # we are on a cluster node
	    COMMAND="bsub -o $LOG_FILE_MULTI java ${JAVA_ARGS} "
	fi

	# echo $LOG_FILE_MULTI
	
	$COMMAND ${JAVA_ARGS} ${TEST_CLASS} $file  >> $LOG_FILE_MULTI  2>&1
	#sleep 0.1
    done
else
    echo "------------------------------------------------------------" >> $LOG_FILE  2>&1
    echo "`date +"%F %R"`" >> $LOG_FILE  2>&1
    echo "`basename $0`: Comparing file '$1'..." >> $LOG_FILE  2>&1
    echo "------------------------------------------------------------" >> $LOG_FILE  2>&1
    
    $COMMAND ${JAVA_ARGS} ${TEST_CLASS} $SBML_DIR >> $LOG_FILE  2>&1

fi



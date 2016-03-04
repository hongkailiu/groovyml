#! /bin/bash
cd ..
APP_HOME=$(cd "$(dirname "$0")"; pwd)
#JAVA_HOME=/usr/java/jdk1.6.0_31
LIB_HOME=$APP_HOME/lib
CONF_HOME=$APP_HOME/conf
FILE_HOME=$APP_HOME/file
MAIN=com.example.groovy.shell.MyScriptExecutor

cd $LIB_HOME
for l in `ls`
do
    CLASSPATH=$CLASSPATH:$LIB_HOME/$l
done

export APP_HOME JAVA_HOME CLASSPATH LIB_HOME MAIN

echo JAVA_HOME $JAVA_HOME
echo APP_HOME $APP_HOME
echo LIB_HOME $LIB_HOME
echo CONF_HOME $CONF_HOME
echo CLASSPATH $CLASSPATH

#somehow we need an extra "/" for spring config file when using absolute path
#$JAVA_HOME/bin/java -Dlestat.home=$APP_HOME -Dlogfile.name=lestate-mapred.0.log $MAIN mapred 20140605 3 -libjars $LIB_HOME/lestat-hdfs-0.0.1-SNAPSHOT.jar
#$JAVA_HOME/bin/java -Xms512m -Xmx512m -Xmn128m -Dapp.home=$APP_HOME -Dlogfile.name=test-app.0.log $MAIN wordcount
$JAVA_HOME/bin/java -XX:MaxPermSize=1M $MAIN ${FILE_HOME}/test.groovy

#!/usr/bin/env bash

DUMP_DIR='./dumps'
LOG_DIR='./logs'

function mk_dir(){
    if [[ ! -e $1 ]]; then
	mkdir $1
    elif [[ ! -d $1 ]]; then
        echo "$1 already exists but is not a directory" 1>&2
    fi
}

mk_dir "${DUMP_DIR}"
mk_dir "${LOG_DIR}"


REMOTE_DEBUG="-agentlib:jdwp=transport=dt_socket,address=14000,server=y,suspend=n"
JAVA_MEMORY="-Xms502m  -Xmx502m  -XX:MaxMetaspaceSize=128m"
PRG="./target/hw04-gc.jar"
DUMP="-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${DUMP_DIR}"
LOG="-verbose:gc  -Xloggc:${LOG_DIR}/gc_pid_%p.log  -XX:+PrintGCDateStamps  -XX:+PrintGCDetails  -XX:+UseGCLogFileRotation  -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=128M"
JMX_MANAGE='-Dcom.sun.management.jmxremote.port=15000  -Dcom.sun.management.jmxremote.authenticate=false  -Dcom.sun.management.jmxremote.ssl=false'

#https://habr.com/ru/post/154089/

#Copy + MarkSweepCompact
GC="-XX:+UseSerialGC"

#G1 Young + G1 Mixed
#GC="-XX:+UseG1GC"

#PS Scavenge + PS MarkSweep
#GC="-XX:+UseParallelGC -XX:+UseParallelOldGC"

#ParNew + MarkSweepCompact
#GC="-XX:+UseParNewGC"

#ParNew + ConcurrentMarkSweep
#GC="-XX:+UseConcMarkSweepGC -XX:+UseParNewGC"

#Copy + ConcurrentMarkSweep
#GC="-XX:+UseConcMarkSweepGC -XX:-UseParNewGC"

java ${REMOTE_DEBUG} ${DUMP} ${LOG} ${JMX_MANAGE} ${JAVA_MEMORY} ${GC} -jar ${PRG}



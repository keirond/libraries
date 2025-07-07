#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

PROJECT="pt-kafka-producer"
VERSION="0.0.1"
BUILD="${SCRIPT_DIR}/../target/${PROJECT}-${VERSION}.jar"
LIB="${SCRIPT_DIR}/../target/lib/*"
MAIN="org.keiron.libraries.kafka.pt.Main"

java -cp "${BUILD}:${LIB}" ${MAIN}
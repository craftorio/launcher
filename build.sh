#!/bin/bash
VERSION=1.4
export LAUNCHER_VERSION=${LAUNCHER_VERSION-$VERSION}
./gradlew build
java -jar "./bin/stringer.jar" "./build/libs/launcher-${LAUNCHER_VERSION}.jar" "./build/libs/launcher.jar"
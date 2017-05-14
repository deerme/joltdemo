#!/bin/bash
#
# Installs the jolt jar into your local maven repo
#
mvn install:install-file -Dfile=jolt.jar \
    -DgroupId=oracle \
    -DartifactId=jolt \
    -Dversion=1.0 \
    -Dpackaging=jar
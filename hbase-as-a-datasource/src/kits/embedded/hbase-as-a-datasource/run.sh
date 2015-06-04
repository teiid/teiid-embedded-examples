#!/bin/sh

TEIID_PATH=../../lib/*:../../optional/hbase/*:../../optional/jdbc/*:../../optional/*:../../optional/tm/*

java -cp ${TEIID_PATH} org.teiid.example.TeiidEmbeddedHBaseDataSource "$@"

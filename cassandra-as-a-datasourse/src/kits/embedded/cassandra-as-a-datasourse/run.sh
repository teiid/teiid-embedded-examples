#!/bin/sh

TEIID_PATH=../../lib/*:../../optional/cassandra/*:../../optional/slf4j/*:../../optional/netty/*

java -cp ${TEIID_PATH} org.teiid.example.TeiidEmbeddedCassandraDataSource "$@"

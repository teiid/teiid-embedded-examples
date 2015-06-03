#!/bin/sh

TEIID_PATH=../../lib/*:../../optional/file/*:../../optional/jdbc/*:../../optional/jdbc/h2/*:../../optional/*:../../optional/tm/*

java -cp ${TEIID_PATH} org.teiid.example.TeiidEmbeddedPortfolio "$@"

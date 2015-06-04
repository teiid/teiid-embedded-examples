#!/bin/sh

TEIID_PATH=../../lib/*:../../optional/webservice/*

java -cp ${TEIID_PATH} org.teiid.example.GenericSoap "$@"

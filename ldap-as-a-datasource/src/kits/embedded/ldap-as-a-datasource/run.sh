#!/bin/sh

TEIID_PATH=../../lib/*:../../optional/ldap/*

java -cp ${TEIID_PATH} org.teiid.example.TeiidEmbeddedLDAPDataSource "$@"

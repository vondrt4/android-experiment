#!/bin/bash

PROVIDER=bcprov-jdk16-1.45.jar

CERT_FILE="$1"
CERTSTORE="../res/raw/keystore.bks"

if [ -a $CERTSTORE ]; then
    rm $CERTSTORE || exit 1
fi

keytool \
      -import \
      -v \
      -trustcacerts \
      -alias 0 \
      -file <(openssl x509 -in ${CERT_FILE}) \
      -keystore $CERTSTORE \
      -storetype BKS \
      -provider org.bouncycastle.jce.provider.BouncyCastleProvider \
      -providerpath $PROVIDER \
      -storepass kevinflynn

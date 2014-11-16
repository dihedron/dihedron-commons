#!/bin/bash


JAR="dihedron-commons-1.0.1-SNAPSHOT.jar"
DESTINATION="/var/www/html/websign/jars"

JARSIGNER=/usr/lib/jvm/java-7-oracle/bin/jarsigner
ALIAS=dihedron
STOREPATH=/home/andrea/.m2/dihedron.jks
STOREPASS=dihedron

_BUILD=false
_CLEAN=false
_INSTALL=false
_SIGN=false


for KEY in "$@"; do
	case $KEY in
		-h|--help)
			echo "compiles, signs and installs the current project: use"
			echo "     $0 [-b|--build] [-c|--clean] [-i|--install] [-s|--sign]"
			echo "or alternatively"
			echo "     $0 -a|--all"
			;;
		-b|--build)
			_BUILD=true
			;;
		-c|--clean)
			_CLEAN=true
			;;
		-i|--install)
			_INSTALL=true
			;;
		-s|--sign)
			_SIGN=true
			;;
		-a|--all)
			_BUILD=true
			_CLEAN=true
			_INSTALL=true
			_SIGN=true
			;;
		*)
			# unknown option
			echo "unknown option $KEY"
			;;
	esac
done

if $_CLEAN; then
	echo "cleaning jar..."
	mvn clean
fi

if $_BUILD; then
	echo "building jar..."
	mvn install
fi

if $_INSTALL; then
	echo "installing jar..."
	mv target/$JAR $DESTINATION
fi

if $_SIGN; then
	if [ -f target/$JAR ]; then
		echo "signing jar in target directory..."
		$JARSIGNER -keystore $STOREPATH -storepass $STOREPASS -signedjar target/$JAR target/$JAR $ALIAS
	elif [ -f $DESTINATION/$JAR ]; then
		echo "signing jar in destination directory..."
		$JARSIGNER -keystore $STOREPATH -storepass $STOREPASS -signedjar $DESTINATION/$JAR $DESTINATION/$JAR $ALIAS
	fi
fi

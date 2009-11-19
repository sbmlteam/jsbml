#!/bin/bash
#
# This program installs the latest version of libSBML using the
# latest version of Xerces on your system relative to the location
# of this script in the folder ./lib.
#
# Created at 2007-04-05
# last modified: 2009-02-02
# Author: Andreas Draeger
#
# usage: ./installLibSBML.sh
#
# The architecure of the machine is guessed using uname.
# This script requires gpp and javac (SDK) to be installed on your system.

echo "installLibSBML.sh Copyright (C) 2009 Andreas Dräger"
echo "This program comes with ABSOLUTELY NO WARRANTY."
echo "This is free software, and you are welcome to redistribute it"
echo "under certain conditions. See GNU LGPL for details."


#########################################
# I N S T A L L A T I O N    S E T U P  #
#########################################

currdir=$(pwd)
installdir=${0%%/*}
if test -e ${installdir}/lib; then
  echo "Directory ${installdir}/lib does already exist."
else
  mkdir ${installdir}/lib
fi
installdir=$(cd ${installdir}; pwd)
installdir=${installdir}/lib

os=$(echo $(uname) | tr "[:upper:]" "[:lower:]")
architecture=$(uname -m)
echo "I N S T A L L A T I O N   S U M M A R Y"
echo "======================================="
echo "Current directory:      "$currdir
echo "Installation directory: "$installdir
echo "Operating system:       "$os
echo "Computer architecture:  "$architecture
echo "======================================="
os=${os// /_}
architecture=${architecture##*_}
if [ $architecture != "64" ]; then
  architecture=""
fi
if test -e ${installdir}/${os}${architecture}; then
  echo "Directory ${installdir}/${os}${architecture} does already exist."
else
  mkdir ${installdir}/${os}${architecture}
fi
if test -e ${installdir}/tmp; then
  echo "Directory ${installdir}/tmp does already exist."
else
  mkdir ${installdir}/tmp
fi

#########################################
# X E R C E S - I N S T A L L A T I O N #
#########################################

echo "Installing XERCES on your system"
cd ${installdir}/tmp # now we are in the tmp directory.
wget http://mirrorspace.org/apache/xerces/c/3/sources/xerces-c-3.0.0.tar.gz
mkdir ${installdir}/${os}${architecture}/xerces
gunzip xerces-c-3.0.0.tar.gz
tar -xvf xerces-c-3.0.0.tar
rm xerces-c-3.0.0.tar
cd xerces-c-3.0.0
sh configure --enable-msgloader-inmemory --prefix=${installdir}/${os}${architecture}/xerces --exec-prefix=${installdir}/${os}${architecture}/xerces
make
make install
make clean

###########################################
# L I B S B M L   I N S T A L L A T I O N #
###########################################

echo "Installing libSBML on your system"
cd ${installdir}/tmp # now we are in the tmp directory.
wget http://downloads.sourceforge.net/project/sbml/libsbml/4.0.0/libsbml-4.0.0-src.zip
#wget http://surfnet.dl.sourceforge.net/sourceforge/sbml/libsbml-3.4.0-src.zip
#wget http://switch.dl.sourceforge.net/sourceforge/sbml/libsbml-3.3.2-src.zip
#wget http://ovh.dl.sourceforge.net/sourceforge/sbml/libsbml-3.3.1-src.zip
mkdir ${installdir}/${os}${architecture}/libSBML
unzip libsbml-4.0.0-src.zip
cd libsbml-4.0.0
sh configure --with-java --with-xerces=${installdir}/${os}${architecture}/xerces \
  --prefix=${installdir}/${os}${architecture}/libSBML \
  --exec-prefix=${installdir}/${os}${architecture}/libSBML
#--with-swig=${installdir}/${os}${architecture}/swig #--with-perl --with-lisp
#--with-python --with-matlab
# cp ${installdir}/../patch-SBase_read-20090207.zip .
# unzip patch-SBase_read-20090207.zip
# patch -p1 < patch-SBase_read-20090207
make
make install
make clean
/sbin/ldconfig
if [ "$LD_LIBRARY_PATH" == "" ]; then
  export LD_LIBRARY_PATH=$installdir/${os}${architecture}/libSBML
else
  export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$installdir/${os}${architecture}/libSBML
fi
# make docs         # optional
# make install-docs # optional

###########################################
# C L E A R     W O R K S P A C E         #
###########################################

echo "Cleaning workspace"
cd $currdir # move back to the directory where we started.
rm -rf ${installdir}/tmp
rm -rf ${installdir}/${os}${architecture}/libSBML/include
rm -rf ${installdir}/${os}${architecture}/xerces/include

echo "done."
exit 0

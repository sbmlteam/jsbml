#!/bin/bash
#
#
# $Id: updateSBO.sh 157 2010-11-10 16:27:08Z andreas-draeger $
# $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/files/updateSBO.sh $
#
# ===================================================================================
# Copyright (c) 2009 the copyright is held jointly by the individual
# authors. See the file AUTHORS for the list of authors.
#
# This file is part of jsbml, the pure java SBML library. Please visit
# http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
# to get the latest version of jsbml.
#
# jsbml is free software: you can redistribute it and/or modify
# it under the terms of the GNU Lesser General Public License as published by
# the Free Software Foundation, either version 2.1 of the License, or
# (at your option) any later version.
#
# jsbml is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public License
# along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
#
# ===================================================================================
#
#
# This program downloads the latest version of the
# Systems Biology Ontology (SBO) into the folder
# ./resources/ relative to the location of this script.
#
# Created at 2007-04-05
# last modified: 2010-11-10
# Author: Andreas Draeger
#
# usage: ./updateSBO.sh

echo "updateSBO.sh Copyright (c) 2009 the JSBML team"
echo "This program comes with ABSOLUTELY NO WARRANTY."
echo "This is free software, and you are welcome to redistribute it"
echo "under certain conditions. See GNU LGPL for details."

wget http://www.ebi.ac.uk/sbo/exports/Main/SBO_OBO.obo
POSITION=$(pwd)
cd ${0%%/*}"/.."
LOCATION=$(pwd)
cd ${POSITION}
LOCATION=${LOCATION}"/resources/org/sbml/jsbml/resources/cfg/"
mv SBO_OBO.obo ${LOCATION}
chmod 664 ${LOCATION}"SBO_OBO.obo"

exit 0

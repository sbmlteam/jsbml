#!/usr/bin/env python
# =============================================================================
# @file   extractErrors.py
# @brief  Extracts SBML errors from libSBML
# @author Roman Schulte
# =============================================================================
#
# This file is part of the JSBML offline validator project.
#
# This script expects two input parameters, where the first is the location
# of the SBMLErrors.json and the second one the outputpath for the newly created
# Java interface. Example:
#
# createErrorCodeInterface.py ./SBMLErrors.json ./test/
#
# If needed this script will create new directories.

import sys
import json

from libsbml import *

package_codes = [['CORE',          0],
                 ['COMP',    1000000],
                 ['REQ',     1100000],
                 ['SPATIAL', 1200000],
                 ['RENDER',  1300000],
                 ['FBC',     2000000],
                 ['QUAL',    3000000],
                 ['GROUPS',  4000000],
                 ['DISTRIB', 5000000],
                 ['LAYOUT',  6000000],
                 ['MULTI',   7000000],
                 ['ARRAYS',  8000000],
                 ['DYN',     9000000]]

def error_package(code):
    code = int(float(code) / 100000) * 100000
    for pkg in package_codes:
        if pkg[1] == code:
            return pkg
    return ['PACKAGE_NOT_FOUND', 0]

file_name = sys.argv[1]
fp = open(file_name)
contents = fp.read()
data = json.loads(contents)

file = '''/*\n
 * $Id$\n
 * $URL$\n
 * ----------------------------------------------------------------------------\n
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>\n
 * for the latest version of JSBML and more information about SBML.\n
 *\n
 * Copyright (C) 2009-2016 jointly by the following organizations:\n
 * 1. The University of Tuebingen, Germany\n
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK\n
 * 3. The California Institute of Technology, Pasadena, CA, USA\n
 * 4. The University of California, San Diego, La Jolla, CA, USA\n
 * 5. The Babraham Institute, Cambridge, UK\n
 *\n
 * This library is free software; you can redistribute it and/or modify it\n
 * under the terms of the GNU Lesser General Public License as published by\n
 * the Free Software Foundation. A copy of the license agreement is provided\n
 * in the file named "LICENSE.txt" included with this software distribution\n
 * and also available online as <http://sbml.org/Software/JSBML/License>.\n
 * ----------------------------------------------------------------------------\n
 */\n
package org.sbml.jsbml.validator.factory;
\n\npublic interface SBMLErrorCodes { \n \n'''

for key in sorted(data, key=int):
    code = int(key)
    pkg = error_package(code)
    short = code - pkg[1]
    file += '\n\t /**\n\t  * Error code ' + str(code) + ':'
    message = data[key]["Message"]
    break_after = 80
    char_count = break_after
    for block in message.split():
        if (char_count + len(block)) > break_after:
            file += '\n\t  * ' + block + ' '
            char_count = len(block) + 1
        else:
            file += block + ' '
            char_count += len(block) + 1

    file += '\n\t  */\n \t public static final int ' + pkg[0] + "_" + str(short).zfill(5) + " = " + str(code) + "; \n"
file += '}'



if len(sys.argv) > 2:
    filename = sys.argv[2]
    dir = os.path.dirname(filename)

    if not os.path.exists(dir):
        os.makedirs(dir)

    with open(dir + "/SBMLErrorCodes.java", "w") as text_file:
        text_file.write(file)
        print 'done'
else:

    print file

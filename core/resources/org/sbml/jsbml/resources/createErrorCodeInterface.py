#!/usr/bin/env python
# =============================================================================
# @file   createErrorCodeInterface.py
# @brief  Extracts SBML errors from the json file and creates an interface that contains all error codes.
# @author Roman Schulte
# =============================================================================
#
# This file is part of the JSBML offline validator project.
#
# This script expects two input parameters, where the first is the location
# of the SBMLErrors.json and the second one the output path for the newly created
# Java interface. Example:
#
# python createErrorCodeInterface.py ./SBMLErrors.json ../../../../../src/org/sbml/jsbml/validator/offline/factory/
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

file = '''/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */\n
package org.sbml.jsbml.validator.offline.factory;
\n\n
/**
 * Automatically generated file, using the python scripts extractErrors.py on the libSBML source code
 * and createErrorCodeInterface.py on the generated json file.
 *
 * @since 1.2
 */
public interface SBMLErrorCodes { \n \n'''

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
            file += '\n\t  * ' + block.replace("<", "&lt;").replace(">", "&gt;") + ' '
            char_count = len(block) + 1
        else:
            file += block.replace("<", "&lt;").replace(">", "&gt;") + ' '
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

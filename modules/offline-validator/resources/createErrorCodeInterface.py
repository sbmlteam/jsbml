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

file = '''package org.sbml.jsbml.validator.factory;
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

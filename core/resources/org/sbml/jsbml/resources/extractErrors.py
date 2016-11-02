#!/usr/bin/env python
# =============================================================================
# @file   extractErrors.py
# @brief  Extracts SBML errors from libSBML
# @author Roman Schulte
# =============================================================================
#
# This file is part of the JSBML offline validator project.
#
# The script will print all the error messages as formated JSON file.
# All error codes are stored in one large JSON object.
# The error code will be the key to the error object, which provides
# the needed informations:
# - Available (optional): indicates until which version of SBML this error is supported
# - Category: category of the error
# - DefaultSeverity: the severity class of this error in the most levels/versions
# - Message: describes the rule behind this error
# - Package: determines the package of the error (default is 'core')
# - SeverityLxVy: If the error has a different severity classes in level x /
#                 version y, then the different severity class is declared here.
#
# Example syntax:
# {
#   ...
#   "10212": {
#       "Available": "L2V1",
#       "Category": "MathML consistency",
#       "DefaultSeverity": "error",
#       "Message": "The types of values within <piecewise> operators must all be consistent: the set of expressions that make up the first arguments of the <piece> and <otherwise> operators within the same <piecewise> operator should all return values of the same type.\n",
#       "Package": "core",
#       "SeverityL1V1": "na",
#       "SeverityL1V2": "na",
#       "SeverityL2V1": "warning"
#       },
#   ...
# }
#
# This library is free software; you can redistribute it and/or modify it
# under the terms of the GNU Lesser General Public License as published by
# the Free Software Foundation.  A copy of the license agreement is provided
# in the file named "LICENSE.txt" included with this software distribution
# and also available online as http://sbml.org/software/libsbml/license.html
# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->


import sys
import json

from types import *
from imp import *
from string import *
from libsbml import *


# -----------------------------------------------------------------------------
# Globals.
# -----------------------------------------------------------------------------

# The currently-known SBML Levels and Versions.

sbml_levels_versions = [[1, 1], [1, 2],
                        [2, 1], [2, 2], [2, 3], [2, 4], [2, 5],
                        [3, 1], [3, 2]]

# Set of error codes that we ignore for purposes of documentation.

ignored_error_codes = {9999, 10599, 20905, 21112, 29999, 90000, 90501, 99502,
                       99503, 99504, 99994, 99995, 99999}

# Package error code ranges.  The numbers are the low end start values.

package_codes = [['core',          0],
                 ['comp',    1000000],
                 ['req',     1100000],
                 ['spatial', 1200000],
                 ['render',  1300000],
                 ['fbc',     2000000],
                 ['qual',    3000000],
                 ['groups',  4000000],
                 ['distrib', 5000000],
                 ['layout',  6000000],
                 ['multi',   7000000],
                 ['arrays',  8000000],
                 ['dyn',     9000000]]

# Our approach to finding error codes starts with numbers and then rummages
# through the list of symbols in the libSBML Python module to find the symbol
# that corresponds to each number.  That works well enough for our purposes
# when the number is above 100, but for lower numbers, we have collisions
# because there are many short enumerations in libSBML.  We don't care about
# numbers lower than 10000 when doing --enum or --doc, but we do for --web,
# where we try to list all error codes.  All of those enumerations turn into
# the same numbers starting from 0, so if you grab a symbol from the Python
# module and examine its value and find that it, say, "3", you can't tell
# which enumeration it came from.  You can only do that if you know the
# symbol names you care about ahead of time.  Since we don't want to
# hard-code all possible libSBML error codes here (avoiding that scenario is
# the point of this program!), we have to find some other way to distinguish
# the cases.  The approach taken here is to only worry about the numbers
# below 1000 using a table that enumerates the symbols we care about; when we
# iterate over all error numbers, we always explicitly map the numbers to the
# following codes.

low_numbered_errors = {0: 'XMLUnknownError',
                       1: 'XMLOutOfMemory',
                       2: 'XMLFileUnreadable',
                       3: 'XMLFileUnwritable',
                       4: 'XMLFileOperationError',
                       5: 'XMLNetworkAccessError',
                       101: 'InternalXMLParserError',
                       102: 'UnrecognizedXMLParserCode',
                       103: 'XMLTranscoderError'}

# -----------------------------------------------------------------------------
# Main code for --doc.
# -----------------------------------------------------------------------------


def write_doc(module):
    data = {}
    for errNum in sorted(get_numeric_constants(module)):
        if errNum < 9999999:
            pkg_name = ""
            if errNum > 99999:
                pkg_name = error_package_with_code(errNum)
            e = SBMLError(errNum, 3, 1, '', 0, 0, 0, 0, pkg_name, 1)
            if e.isValid():
                data = add_error_as_json(e, data)

    # for package in package_codes:
    #     pkg_name = package[0]
    #     pkg_start = package[1]
    #     pkg_end = pkg_start + 1000000
    #     for errNum in sorted(get_numeric_constants(module, pkg_start, pkg_end)):
    #         e = SBMLError(errNum, 3, 1, '', 0, 0, 0, 0, pkg_name, 1)
    #         data = add_error_as_json(e, data)
    print json.dumps(data, indent=4, sort_keys=True)


def add_error_as_json(e, data):
    errorData = {}
    errorData['Message'] = e.getMessage()
    errorData['Package'] = error_package(e)
    errorData['Category'] = e.getCategoryAsString()
    errorData['ShortMessage'] = e.getShortMessage()
    errorData = add_error_severity(e, errorData)
    data[e.getErrorId()] = errorData
    return data

def error_package(error):
    code = error.getErrorId()
    return error_package_with_code(code)

def error_package_with_code(code):
    code = int(float(code) / 100000) * 100000
    for pkg in package_codes:
        if pkg[1] == code:
            return pkg[0]
    return 'PACKAGE_NOT_FOUND'


def add_error_severity(error, data):
    errors = {}
    for lv in sbml_levels_versions:
        errors["L{}V{}".format(lv[0], lv[1])] = SBMLError(error.getErrorId(), lv[0], lv[1])

    # If not available since L1V1 search the first encounter
    sinceLevel = 1
    sinceVersion = 1
    if 1 > errors["L1V1"].getSeverity() > 3:
        for lv in sbml_levels_versions:
            lvString = "L{}V{}".format(lv[0], lv[1])
            if 1 > errors[lvString].getSeverity() > 3:
                data["Available"] = lvString
                sinceLevel = lv[0]
                sinceVersion = lv[1]
                break

    # counts the encounter of every severity
    severityCounter = [0, 0, 0, 0]
    for lv in sbml_levels_versions:
        if lv[0] >= sinceLevel and lv[1] >= sinceVersion:
            lvString = "L{}V{}".format(lv[0], lv[1])
            e = errors[lvString]
            severity = e.getSeverity()
            if (severity >= 1 and severity <= 3):
                severityCounter[severity] += 1
            else:
                severityCounter[0] += 1
        # data['SeverityL{}V{}'.format(lv[0], lv[1])] = severity
        # get_severity_class(severity)
        # data["Test"] = severityCounter

    # calculates the default severity
    defaultSeverity = 0
    for i in range(1, 4):
        # print '{} > {} = {}'.format(severityCounter[i], severityCounter[defaultSeverity], severityCounter[i] > severityCounter[defaultSeverity])
        if severityCounter[i] > severityCounter[defaultSeverity]:
            defaultSeverity = i
    defaultSeverity = get_severity_class(defaultSeverity)
    data["DefaultSeverity"] = defaultSeverity

    # adds severities if LV differs form the default
    for lv in sbml_levels_versions:
        if lv[0] > sinceLevel or (lv[0] == sinceLevel and lv[1] >= sinceVersion):
            lvString = "L{}V{}".format(lv[0], lv[1])
            e = errors[lvString]
            severity = get_severity_class(e.getSeverity())
            if severity != defaultSeverity:
                data['SeverityL{}V{}'.format(lv[0], lv[1])] = severity

    return data
    #   output += '<td class="meaning">{}</td>\n'.format(to_html(e.getShortMessage()))
    #   for lv in sbml_levels_versions:
    #     e = SBMLError(errNum, lv[0], lv[1])
    #     output += '<td class="{}"></td>\n'.format(get_severity_class(e.getSeverity()))
    # else:
    #   for package in package_codes:
    #     pkg_name  = package[0]
    #     pkg_start = package[1]
    #     pkg_end   = pkg_start + 1000000
    #     if errNum > pkg_start and errNum < pkg_end:
    #       e = SBMLError(errNum, 3, 1, '', 0, 0, 0, 0, pkg_name, 1)
    #       if not e.isValid():
    #         return ''
    #       output += '<td class="meaning">{}</td>\n'.format(to_html(e.getShortMessage()))
    #       for lv in range(0, len(sbml_levels_versions) - 1):
    #         output += '<td class="{}"></td>\n'.format(get_severity_class(0))
    #       output += '<td class="{}"></td>\n'.format(get_severity_class(e.getSeverity()))
    #       break
    #
    # output += '</tr>\n'
    # print_progress()
    # return output


# -----------------------------------------------------------------------------
# Helper functions.
# -----------------------------------------------------------------------------

def get_module():
    m = find_module("libsbml")
    return load_module('_libsbml', m[0], m[1], m[2]) # python 2.7 syntax for load_module
    #return load_module('_libsbml', None, m[1], m[2])


def get_numeric_constants(module, low=0, high=90000000):
    constants = set()
    for symbol in dir(module):
        try:                                # Some symbols don't have a value,
            # so we have to guard against that.
            value = eval(symbol)
            if isinstance(value, int) and low <= value and value <= high:
                constants.add(value)
        except:
            continue
    return constants


def get_symbol(module, number):
    # This is an inefficient way of getting the string corresponding to a
    # given error number, but we don't care because this application isn't
    # performance-critical.  The algorithm is also fragile, but if we're
    # careful about the range of values attempted (i.e., numbers from 10000
    # to 100000), it should be okay.  Unfortunately, values below 100 hits
    # a number of small enumerations in libSBML, so we have to special-case
    # that.  Really, this whole thing is just awful.
    #
    if number in low_numbered_errors.keys():
        return low_numbered_errors[number]

    symbols = dir(module)
    attributes = [None] * len(symbols)
    for i in range(0, len(symbols)):
        attributes[i] = getattr(module, symbols[i])
    for i in range(0, len(symbols)):
        if isinstance(attributes[i], int) and attributes[i] == number:
            return symbols[i]


def get_severity_class(severity):
    if (severity == 3):
        return "fatal"
    elif (severity == 2):
        return "error"
    elif (severity == 1):
        return "warning"
    else:
        return "na"


def main(args):
    """Usage:
        will extract error messages form libSBML
    """

    # OK, let's do this thing.

    module = get_module()
    write_doc(module)

if __name__ == '__main__':
    main(sys.argv)

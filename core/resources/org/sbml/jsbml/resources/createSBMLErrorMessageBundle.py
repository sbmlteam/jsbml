#!/usr/bin/env python
# =============================================================================
# @file   createSBMLErrorMessagebundle.py
# @brief  Creates a java ListResourceBundle that contains each error code message
# @author Nicolas Rodriguez
# =============================================================================
#
# This file is part of the JSBML offline validator project.
#
# This script expects two input parameters, where the first is the location
# of the SBMLErrors.json and the second one the output path for the newly created
# Java class. Example:
#
# python createSBMLErrorMessagebundle.py ./SBMLErrors.json ../../../../../src/org/sbml/jsbml/validator/offline/i18n/
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
package org.sbml.jsbml.validator.offline.i18n;
\n
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.validator.offline.factory.SBMLErrorCodes;


/**
 * Contains the messages for each {@link SBMLError} in the English language.
 * 
 * <p>The key for each message is the integer defined for each {@link SBMLError} in {@link SBMLErrorCodes}.</p>
 * 
 * <p>Automatically generated file, using the python scripts extractErrors.py on the libSBML source code
 * and createSBMLErrorMessageBundle.py on the generated json file.</p>
 *
 * @see ResourceBundle
 * @since 1.3
 */
public class SBMLErrorMessage extends ResourceBundle { \n \n
  /**
   * 
   */
  private static final Map<String, String> contents = new HashMap<String, String>();
  
  static {
      '''

for key in sorted(data, key=int):
    code = int(key)
    pkg = error_package(code)
    short = code - pkg[1]
    # file += '\n\t /**\n\t  * Error code ' + str(code) + ':'
    message = data[key]["Message"]
    
    # Remove the line return in the cases where there is a reference to the SBML specifications
    message = message.replace(".\nR", " (R")
    message = message.replace(". \nR", " (R")
    
    # Remove the line return in the cases where there is no reference to the SBML specifications
    message = message.replace(".\n", ".")
    message = message.replace(". \n", ".")
        
    # Remove the additional line return in the cases where there is a reference to the SBML specifications
    message = message.replace("\n", ").")        
    
    # quote double quote
    message = message.replace("\"", "\\\"")

    file += '\n        contents.put(Integer.toString(SBMLErrorCodes.' + pkg[0] + "_" + str(short).zfill(5) + "), \"" + message + "\");\n"
    
    
file += '''  } \n

  @Override
  protected Object handleGetObject(String key) {

    return contents.get(key);
  }

  @Override
  public Enumeration<String> getKeys() {
    
    return java.util.Collections.enumeration(contents.keySet());
  }

}\n'''



if len(sys.argv) > 2:
    filename = sys.argv[2]
    dir = os.path.dirname(filename)

    if not os.path.exists(dir):
        os.makedirs(dir)

    with open(dir + "/SBMLErrorMessage.java", "w") as text_file:
        text_file.write(file)
        print 'done'
else:

    print file

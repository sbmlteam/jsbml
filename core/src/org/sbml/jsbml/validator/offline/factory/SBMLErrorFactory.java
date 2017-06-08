/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2017 jointly by the following organizations:
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
 */

package org.sbml.jsbml.validator.offline.factory;

import java.io.File;
import java.io.FileReader;
import java.lang.ref.SoftReference;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.util.Message;

/**
 * Creates {@link SBMLError} populated with values coming from a json file.
 * 
 * <p>This json file is generated from the libSBML source tree.</p>
 * 
 * @author Roman
 * @author rodrigue
 * @since 1.2
 */
public class SBMLErrorFactory {

  /**
   * Key for the 'Available' value in the JSON file
   */
  private static final String              JSON_KEY_AVAILABLE           = "Available";
  /**
   * Key for the 'Message' value in the JSON file
   */
  private static final String              JSON_KEY_MESSAGE             = "Message";
  /**
   * Key for the 'ShortMessage' value in the JSON file
   */
  private static final String              JSON_KEY_SHORT_MESSAGE       = "ShortMessage";
  /**
   * Key for the 'Package' value in the JSON file
   */
  private static final String              JSON_KEY_PACKAGE             = "Package";
  /**
   * Key for the 'Category' value in the JSON file
   */
  private static final String              JSON_KEY_CATEGORY            = "Category";
  /**
   * Key for the 'DefaultSeverity' value in the JSON file
   */
  private static final String              JSON_KEY_DEFAULT_SEVERITY    = "DefaultSeverity";
  /**
   * Key for the 'Severity' value for a certain SBML level and version in the JSON file
   */
  private static final String              JSON_KEY_UNFORMATED_SEVERITY = "SeverityL%dV%d";
  
  /**
   * the cache for the json object to avoid to read the json file too often.
   */
  private static SoftReference<JSONObject> cachedJson;


  /**
   * Creates a new {@link SBMLError} instance. 
   * 
   * @param id the error id
   * @param level the SBML level
   * @param version the SBML version
   * @return a new {@link SBMLError} instance. 
   */
  public static SBMLError createError(int id, int level, int version) {
    JSONObject errors = null;
    
    // Trying to get the json object from the cache
    if (SBMLErrorFactory.cachedJson != null) {
      errors = cachedJson.get();
    }
    
    // if the json object is null, populate it from the json file and put it on the cache.
    if (errors == null) {
      try {
        String fileName = "../../../resources/SBMLErrors.json";

        File file = new File(SBMLErrorFactory.class.getResource(fileName).getFile());
        JSONParser parser = new JSONParser();
        
        errors = (JSONObject) (parser.parse(new FileReader(file)));
      } catch (Exception e) {
        e.printStackTrace();
      }
      SBMLErrorFactory.cachedJson = new SoftReference<JSONObject>(errors);
    }
    
    if (errors != null && errors.containsKey("" + id)) {
      JSONObject errorEntry = (JSONObject) errors.get("" + id);
      
      if (errorEntry != null && isAvailable(errorEntry, level, version)) {
        SBMLError e = new SBMLError();
        
        e.setCode(id);
        e.setCategory((String) errorEntry.get(JSON_KEY_CATEGORY));
        e.setPackage((String) errorEntry.get(JSON_KEY_PACKAGE));
        
        Message m = new Message();
        m.setMessage((String) errorEntry.get(JSON_KEY_MESSAGE));
        m.setLang("en");
        e.setMessage(m);
        
        Message sm = new Message();
        sm.setMessage((String) errorEntry.get(JSON_KEY_SHORT_MESSAGE));
        sm.setLang("en");
        e.setShortMessage(sm);
        
        Object sev = errorEntry.get(SBMLErrorFactory.getSeverityKey(level, version));
        
        if (sev == null) {
          sev = errorEntry.get(SBMLErrorFactory.JSON_KEY_DEFAULT_SEVERITY);
        }
        e.setSeverity((String) sev);

        return e;
      }
    }
    
    return null;
  }


  /**
   * Returns {@code true} if the error is available for the given SBML level and version.
   * 
   * @param error the json SBML error object
   * @param level the SBML level
   * @param version the SBML version
   * @return {@code true} if the error is available for the given SBML level and version.
   */
  private static boolean isAvailable(JSONObject error, int level, int version) {
    String minLv = (String) (error.get(JSON_KEY_AVAILABLE));
    
    if (minLv != null) {

      // Kicks out the first Character (because it will always be a 'L')
      // Split the String at the 'V' character
      // The remaining Strings should be the level and version value as String
      String[] blocks = minLv.substring(1).split("V");
      
      // Check if there are really just 2 Strings left
      if (blocks.length == 2) {
        int l = Integer.parseInt(blocks[0]);
        int v = Integer.parseInt(blocks[1]);
        
        // Return true if level is greater as the minimal level
        // Or if the level is equal, but the version greater equal as the min
        // version.
        return (level > l) || (level == l && version >= v);
      }
    }
    
    return true;
  }


  /**
   * Gets the severity json key for a given SBML level and version.
   * 
   * @param level the SBML level
   * @param version the SBML version
   * @return the severity json key for a given SBML level and version.
   */
  private static String getSeverityKey(int level, int version) {
    return String.format(JSON_KEY_UNFORMATED_SEVERITY, level, version);
  }
}

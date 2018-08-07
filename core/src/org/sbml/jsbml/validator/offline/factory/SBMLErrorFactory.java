/*
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
 */

package org.sbml.jsbml.validator.offline.factory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.SoftReference;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.sbml.jsbml.Assignment;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.util.Message;
import org.sbml.jsbml.validator.offline.i18n.SBMLErrorMessage;
import org.sbml.jsbml.validator.offline.i18n.SBMLErrorPostMessage;
import org.sbml.jsbml.validator.offline.i18n.SBMLErrorPreMessage;
import org.sbml.jsbml.validator.offline.i18n.SBMLErrorShortMessage;

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
   * 
   */
  private static ResourceBundle sbmlErrorMessageBundle;

  /**
   * 
   */
  private static ResourceBundle sbmlErrorShortMessageBundle;

  /**
   * 
   */
  private static ResourceBundle sbmlErrorPostMessageBundle;

  /**
   * 
   */
  private static ResourceBundle sbmlErrorPreMessageBundle;


  /**
   * Creates a new {@link SBMLError} instance.
   * 
   * @param id the error id
   * @param level the SBML level
   * @param version the SBML version
   * @return a new {@link SBMLError} instance.
   */
  public static SBMLError createError(int id, int level, int version) {
    return createError(id, level, version, false, null);
  }

  /**
   * Creates a new {@link SBMLError} instance.
   * 
   * @param id the error id
   * @param level the SBML level
   * @param version the SBML version
   * @param customMessage if {@code true}, the method will attempt to create a custom message passing the id of the given {@link SBase}
   * @param sbase an SBase which did provoke the {@link SBMLError}. If it's id is not define, no custom message will be returned.
   * @return a new {@link SBMLError} instance.
   */
  public static SBMLError createError(int id, int level, int version, boolean customMessage, SBase sbase) {
    JSONObject errors = null;

    // Trying to get the json object from the cache
    if (SBMLErrorFactory.cachedJson != null) {
      errors = cachedJson.get();
    }

    // if the json object is null, populate it from the json file and put it on the cache.
    if (errors == null) {
      try {
        InputStream in = SBMLErrorFactory.class.getResourceAsStream("/org/sbml/jsbml/resources/SBMLErrors.json");
        JSONParser parser = new JSONParser();

        errors = (JSONObject) (parser.parse(new InputStreamReader(in)));

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
        e.setSource(sbase);

        //
        // Building the error message from the various ResourceBundle
        String bundleKey = Integer.toString(id);
        StringBuilder messageBuilder = new StringBuilder();

        ResourceBundle preMessageBundle = getSBMLErrorPreMessageBundle(new Locale("en", "EN", "L" + level + "V" + version));
        String preMessageI18n = getBundleString(preMessageBundle, bundleKey);

        if (preMessageI18n != null && preMessageI18n.trim().length() > 0)
        {
          messageBuilder.append(preMessageI18n).append('\n');
        }

        String messageI18n = getSBMLErrorMessageBundle().getString(bundleKey);

        if (messageI18n != null && messageI18n.trim().length() > 0)
        {
          messageBuilder.append(messageI18n);
        }
        else
        {
          // getting the message from the json file
          messageI18n = (String) errorEntry.get(JSON_KEY_MESSAGE);

          // TODO - debug log about this error id
          System.out.println("DEBUG - error id '" + id + "' has no message !!");
        }

        if (customMessage && sbase != null) {
          String postMessageI18n = getBundleString(getSBMLErrorPostMessageBundle(), bundleKey);

          if (postMessageI18n != null && postMessageI18n.trim().length() > 0)
          {
            if (sbase instanceof Assignment) {
              messageBuilder.append('\n').append(MessageFormat.format(postMessageI18n, ((Assignment) sbase).getVariable()));
            } else if (sbase instanceof Trigger || sbase instanceof Delay) {
              messageBuilder.append('\n').append(MessageFormat.format(postMessageI18n, ((Event) sbase.getParent()).getId()));
            } else {
              messageBuilder.append('\n').append(MessageFormat.format(postMessageI18n, sbase.getId()));
            }
          }
          else
          {
            // let's do a default post message
            if (sbase.isSetId()) {
              postMessageI18n = getSBMLErrorPostMessageBundle().getString(SBMLErrorPostMessage.DEFAULT_POST_MESSAGE_WITH_ID);
              messageBuilder.append('\n').append(MessageFormat.format(postMessageI18n, sbase.getElementName(), sbase.getId()));
            }
            else if (sbase.isSetMetaId()) {
              postMessageI18n = getSBMLErrorPostMessageBundle().getString(SBMLErrorPostMessage.DEFAULT_POST_MESSAGE_WITH_METAID);
              messageBuilder.append('\n').append(MessageFormat.format(postMessageI18n, sbase.getElementName(), sbase.getMetaId()));
            }
            else if (sbase instanceof InitialAssignment) {
              postMessageI18n = getSBMLErrorPostMessageBundle().getString(SBMLErrorPostMessage.DEFAULT_POST_MESSAGE_WITH_SYMBOL);
              messageBuilder.append('\n').append(MessageFormat.format(postMessageI18n, sbase.getElementName(), ((InitialAssignment) sbase).getVariable()));
            }
            else if (sbase instanceof Assignment) {
              postMessageI18n = getSBMLErrorPostMessageBundle().getString(SBMLErrorPostMessage.DEFAULT_POST_MESSAGE_WITH_VARIABLE);
              messageBuilder.append('\n').append(MessageFormat.format(postMessageI18n, sbase.getElementName(), ((Assignment) sbase).getVariable()));
            }
            else if (sbase instanceof KineticLaw) {
              postMessageI18n = getSBMLErrorPostMessageBundle().getString(SBMLErrorPostMessage.DEFAULT_POST_MESSAGE_KINETIC_LAW);
              messageBuilder.append('\n').append(MessageFormat.format(postMessageI18n, ((SBase) sbase.getParent()).getId()));
            }
            else {
              postMessageI18n = getSBMLErrorPostMessageBundle().getString(SBMLErrorPostMessage.DEFAULT_POST_MESSAGE);
              messageBuilder.append('\n').append(MessageFormat.format(postMessageI18n, sbase.getElementName())); // TODO - create a better String with the potential attributes or/and the position in the file.
            }
          }
        }

        Message m = new Message();
        m.setMessage(messageBuilder.toString());
        m.setLang("en");
        e.setMessage(m);

        // Building the short message from the various ResourceBundle
        ResourceBundle shortMessageBundle = getSBMLErrorShortMessageBundle();
        String shortMessageI18n = shortMessageBundle.getString(bundleKey);

        Message sm = new Message();

        if (shortMessageI18n != null) {
          sm.setMessage(shortMessageI18n);
          sm.setLang("en"); // TODO - set the language of the bundle or Locale
        }
        else {
          sm.setMessage((String) errorEntry.get(JSON_KEY_SHORT_MESSAGE));
          sm.setLang("en");
        }
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
   * Gets the String corresponding to the given key in the bundle, returns null if the key is not found.
   * 
   * <p>This method catch {@link MissingResourceException} so that methods using it do not have to
   * worry about it, they just have to deal with null values.</p>
   * 
   * @param bundle the {@link ResourceBundle} where to search
   * @param key the key to search in the bundle
   * @return the String corresponding to the given key in the bundle, returns null if the key is not found.
   */
  public static String getBundleString(ResourceBundle bundle, String key) {

    String value = null;

    try {
      value = bundle.getString(key);
    }
    catch (MissingResourceException e) {
      // nothing to do, just return null
    }

    return value;
  }

  /**
   * Returns the {@link SBMLErrorMessage} bundle associated with the current {@link Locale}.
   * 
   * @return the {@link SBMLErrorMessage} bundle associated with the current {@link Locale}.
   */
  public static ResourceBundle getSBMLErrorMessageBundle() {

    if (sbmlErrorMessageBundle == null) {
      sbmlErrorMessageBundle = ResourceBundle.getBundle("org.sbml.jsbml.validator.offline.i18n.SBMLErrorMessage");
    }

    // TODO - if the Local language is not 'en' change the package where to search for the bundle

    return sbmlErrorMessageBundle;
  }

  /**
   * Returns the {@link SBMLErrorShortMessage} bundle associated with the current {@link Locale}.
   * 
   * @return the {@link SBMLErrorShortMessage} bundle associated with the current {@link Locale}.
   */
  public static ResourceBundle getSBMLErrorShortMessageBundle() {

    if (sbmlErrorShortMessageBundle == null) {
      sbmlErrorShortMessageBundle = ResourceBundle.getBundle("org.sbml.jsbml.validator.offline.i18n.SBMLErrorShortMessage");
    }

    return sbmlErrorShortMessageBundle;
  }

  /**
   * Returns the {@link SBMLErrorPostMessage} bundle associated with the current {@link Locale}.
   * 
   * @return the {@link SBMLErrorPostMessage} bundle associated with the current {@link Locale}.
   */
  public static ResourceBundle getSBMLErrorPostMessageBundle() {

    if (sbmlErrorPostMessageBundle == null) {
      sbmlErrorPostMessageBundle = ResourceBundle.getBundle("org.sbml.jsbml.validator.offline.i18n.SBMLErrorPostMessage");
    }

    return sbmlErrorPostMessageBundle;
  }

  /**
   * Returns the {@link SBMLErrorPreMessage} bundle associated with the current {@link Locale}.
   * 
   * @return the {@link SBMLErrorPreMessage} bundle associated with the current {@link Locale}.
   */
  public static ResourceBundle getSBMLErrorPreMessageBundle() {

    if (sbmlErrorPreMessageBundle == null) {
      sbmlErrorPreMessageBundle = ResourceBundle.getBundle("org.sbml.jsbml.validator.offline.i18n.SBMLErrorPreMessage");
    }

    return sbmlErrorPreMessageBundle;
  }

  /**
   * Returns the {@link SBMLErrorPreMessage} bundle associated with the given {@link Locale}.
   * 
   * @param locale the locale
   * @return the {@link SBMLErrorPreMessage} bundle associated with the given {@link Locale}.
   */
  public static ResourceBundle getSBMLErrorPreMessageBundle(Locale locale) {

    return ResourceBundle.getBundle("org.sbml.jsbml.validator.offline.i18n.SBMLErrorPreMessage", locale);
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

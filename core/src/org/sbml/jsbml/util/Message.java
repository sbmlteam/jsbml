/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
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
package org.sbml.jsbml.util;

/**
 * @since 0.8
 */
public class Message {

  /**
   * 
   */
  private String lang;
  /**
   * 
   */
  private String message;

  /**
   * 
   */
  public Message() {
  }

  /**
   * 
   * @return
   */
  public String getLang() {
    return lang;
  }

  /**
   * 
   * @return
   */
  public String getMessage() {
    return message;
  }

  /**
   * 
   * @param lang
   */
  public void setLang(String lang) {
    this.lang = lang;
  }

  /**
   * 
   * @param messageContent
   */
  public void setMessage(String messageContent) {
    message = messageContent;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return StringTools.concat("Message [lang=", lang, ", messageContent=",
      message, "]").toString();
  }

}

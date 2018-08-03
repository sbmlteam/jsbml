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

package org.sbml.jsbml.util;

import java.util.List;
import java.util.Properties;

import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.xml.XMLNode;


/**
 * Contains some useful methods to manipulate 'COBRA SBML'
 *
 *
 * @author Nicolas Rodriguez
 * @since 1.1
 */
public class CobraUtil {
  
  private static final transient Logger logger = Logger.getLogger(CobraUtil.class);
  
  /**
   * Parses the notes of the given {@link SBase} element.
   *
   *<p>The notes are expecting to have some 'p' elements with
   * a content of the form 'KEY: VALUE'. The key will be used for the property
   * name and the value will be the property value. The key is inserted even is the value
   * is an empty String.
   * 
   * <p>Below are examples for a species and a reaction:
   * 
   *  <pre>
  &lt;body xmlns="http://www.w3.org/1999/xhtml"&gt;
    &lt;p&gt;FORMULA: H4N&lt;/p&gt;
    &lt;p&gt;CHARGE: 1&lt;/p&gt;
    &lt;p&gt;HEPATONET_1.0_ABBREVIATION: HC00765&lt;/p&gt;
    &lt;p&gt;EHMN_ABBREVIATION: C01342&lt;/p&gt;
    &lt;p&gt;INCHI: InChI=1S/H3N/h1H3/p+1&lt;/p&gt;
  &lt;/body&gt;
</pre>

<pre>
  &lt;body xmlns="http://www.w3.org/1999/xhtml"&gt;
    &lt;p&gt;GENE_ASSOCIATION: 1594.1&lt;/p&gt;
    &lt;p&gt;SUBSYSTEM: Vitamin D metabolism&lt;/p&gt;
    &lt;p&gt;EC Number: &lt;/p&gt;
    &lt;p&gt;Confidence Level: 4&lt;/p&gt;
    &lt;p&gt;AUTHORS: PMID:14671156,PMID:9333115&lt;/p&gt;
    &lt;p&gt;NOTES: based on Vitamins, G.F.M. Ball,2004, Blackwell publishing, 1st ed (book) pg.196 IT&lt;/p&gt;
  &lt;/body&gt;
</pre>

   * @param sbase
   * @return a {@link Properties} object that store all the KEY/VALUE pair found in the notes. If the given {@link SBase}
   * has no notes or if the notes are not of the expected format, an empty {@link Properties} object is returned.
   */
  public static Properties parseCobraNotes(SBase sbase) {
    Properties props = new Properties();
    
    if (sbase.isSetNotes()) {
      XMLNode notes = sbase.getNotes();
      // just in case no body element is present
      XMLNode parent = notes;
      
      // Getting the body element
      XMLNode body = notes.getChildElement("body", null);
      
      if (body == null) {
        // in some models a 'p' element is used instead of the 'body'
        body = notes.getChildElement("p", null);
      }
      
      if (body != null) {
        parent = body;
      } 
      
      // Getting the all the p elements (only direct child of 'parent')
      List<XMLNode> pNodes = parent.getChildElements("p", null);
      
      for (XMLNode pNode : pNodes) {
        if (pNode.getChildCount() > 0) {
          String content = pNode.getChild(0).getCharacters();
          String key = "", value = "";

          int firstColonIndex = content.indexOf(':');

          if (firstColonIndex != -1) {
            key = content.substring(0, firstColonIndex);
            value = content.substring(firstColonIndex + 1).trim();

            props.setProperty(key, value);
          } else if (logger.isDebugEnabled()) {
            logger.debug("The content of one of the 'p' element does not seems to respect the expected pattern (KEY: VALUE), found '" + content + "'");
          }
        } // else if (logger.isDebugEnabled()) {
          // logger.debug("The content of one of the 'p' element does not seems to respect the expected pattern (KEY: VALUE), found no children !");
        //}
      }
    }
    
    return props;
  }
  
  /**
   * Deletes the notes of the given {@link SBase} element and writes the content of the {@link Properties} object to the notes of the {@link SBase}.
   *
   * @param sbase 
   * @param properties
   */
  public static void writeCobraNotes(SBase sbase, Properties properties) {
    sbase.unsetNotes();
    for (Object pElement : properties.keySet()) {
      try {
        sbase.appendNotes("<body xmlns=\"" + JSBML.URI_XHTML_DEFINITION + "\"><p>" + pElement + ": " + properties.getProperty((String)pElement) + "</p></body>");
      } catch (XMLStreamException e) {
        e.printStackTrace();
      }
    }
  }
  
  /**
   * 
   * 
   * @param sbase
   * @param props
   */
  public static void appendCobraNotes(SBase  sbase, Properties properties) {

    for (Object pElement : properties.keySet()) {
      try {
        sbase.appendNotes("<body xmlns=\"" + JSBML.URI_XHTML_DEFINITION + "\"><p>" + pElement + ": " + properties.getProperty((String)pElement) + "</p></body>");
      } catch (XMLStreamException e) {
        e.printStackTrace();
      }
    }
  }

}

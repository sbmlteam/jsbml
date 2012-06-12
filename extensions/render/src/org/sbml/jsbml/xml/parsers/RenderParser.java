/*
 * $Id:  RenderParser.java 21:49:24 jakob $
 * $URL: RenderParser.java $
 *
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2012 jointly by the following organizations: 
 * 1. The University of Tuebingen, Germany 
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK 
 * 3. The California Institute of Technology, Pasadena, CA, USA 
 * 
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation. A copy of the license agreement is provided 
 * in the file named "LICENSE.txt" included with this software distribution 
 * and also available online as <http://sbml.org/Software/JSBML/License>. 
 * ---------------------------------------------------------------------------- 
 */ 
package org.sbml.jsbml.xml.parsers;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ListOf.Type;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.qual.FunctionTerm;
import org.sbml.jsbml.ext.qual.QualConstant;
import org.sbml.jsbml.ext.qual.QualitativeModel;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.ext.render.RenderLayoutPlugin;
import org.sbml.jsbml.ext.render.RenderModelPlugin;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;


/**
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Eugen Netz
 * @author Jan Rudolph
 * @version $Rev$
 * @since 1.0
 * @date 04.06.2012
 */
public class RenderParser extends AbstractReaderWriter {
  
  /**
   * The logger for this RenderParser
   */
  private Logger logger = Logger.getLogger(RenderParser.class);

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getListOfSBMLElementsToWrite(java.lang.Object)
   */
  @Override
  public ArrayList<Object> getListOfSBMLElementsToWrite(Object sbase) {

    if (logger.isDebugEnabled()) {
      logger.debug("getListOfSBMLElementsToWrite : " + sbase.getClass().getCanonicalName());
    }
    
    ArrayList<Object> listOfElementsToWrite = new ArrayList<Object>();

    if (sbase instanceof SBMLDocument) {
      // nothing to do
      // TODO : the 'required' attribute is written even if there is no plugin class for the SBMLDocument, so I am not totally sure how this is done.
    } 
    else if (sbase instanceof ListOf<?>) {
      // if the sbase is a ListOf, we could have a RenderModelPlugin attached to it
      ListOf<?> listOf = (ListOf<?>)sbase;
      if (listOf.getSBaseListType() == Type.other) {
        
        SBasePlugin plugin = listOf.getExtension(RenderConstants.namespaceURI);
        
        if (plugin != null) {
          RenderModelPlugin rmp = (RenderModelPlugin) plugin;

          // then add its extension children to the list of elements to write
          Enumeration<TreeNode> children = rmp.children();
          while (children.hasMoreElements()) {
            listOfElementsToWrite.add(children.nextElement());
          }           
        }
      }
    } 
    else if (sbase instanceof Layout) {
      // if the sbase is a Layout get its extension
      RenderLayoutPlugin rlp = (RenderLayoutPlugin)((Layout)sbase).getExtension(RenderConstants.namespaceURI);
      
      // then add its extension children to the list of elements to write
      Enumeration<TreeNode> children = rlp.children();
      while (children.hasMoreElements()) {
        listOfElementsToWrite.add(children.nextElement());
      }           
    } 
    else if (sbase instanceof TreeNode) {
      Enumeration<TreeNode> children = ((TreeNode) sbase).children();
      
      while (children.hasMoreElements()) {
        listOfElementsToWrite.add(children.nextElement());
      }
    }
    
    if (listOfElementsToWrite.isEmpty()) {
      listOfElementsToWrite = null;
    } else if (logger.isDebugEnabled()) {
      logger.debug("getListOfSBMLElementsToWrite size = " + listOfElementsToWrite.size());
    }

    return listOfElementsToWrite;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#processStartElement(java.lang.String, java.lang.String, boolean, boolean, java.lang.Object)
   */
  @Override
  public Object processStartElement(String elementName, String prefix,
    boolean hasAttributes, boolean hasNamespaces, Object contextObject) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#processEndElement(java.lang.String, java.lang.String, boolean, java.lang.Object)
   */
  @Override
  public boolean processEndElement(String elementName, String prefix,
    boolean isNested, Object contextObject) {
    // TODO Auto-generated method stub
    return super.processEndElement(elementName, prefix, isNested, contextObject);
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getShortLabel()
   */
  @Override
  public String getShortLabel() {
    return RenderConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getNamespaceURI()
   */
  @Override
  public String getNamespaceURI() {
    return RenderConstants.namespaceURI;
  }
  
}

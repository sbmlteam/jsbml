/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml.xml.parsers;

import java.io.StringWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.log4j.Logger;
import org.codehaus.staxmate.SMOutputFactory;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.xml.XMLNode;

import com.ctc.wstx.stax.WstxOutputFactory;

/**
 *
 * 
 * @author Nicolas Rodriguez
 * @since 0.8
 * @version $Rev$
 */
public class XMLNodeWriter {

  /**
   * 
   */
  private int nodeDepth;
  /**
   * 
   */
	private int indentCount;
	/**
	 * 
	 */
	private char indentChar;
	/**
	 * 
	 */
	private XMLStreamWriter writer;
	
	/**
	 * 
	 */
	private static transient final Logger logger = Logger.getLogger(XMLNodeWriter.class);

	/**
	 * 
	 * @param writer
	 * @param depth
	 * @param indentCount
	 * @param indentChar
	 */
	public XMLNodeWriter(XMLStreamWriter writer, int depth, int indentCount, char indentChar) { 
		if (writer == null) {
			throw new IllegalArgumentException(
					"Cannot create a XMLNodeWriter with a null writer.");
		}
		this.writer = writer;
		this.nodeDepth = depth;
		this.indentCount = indentCount;
		this.indentChar = indentChar;
	}

	/**
	 * 
	 * @param xmlNode
	 * @return
	 */
	public static String toXML(XMLNode xmlNode) {
		String xml = "";
		StringWriter stream = new StringWriter();
		
		SMOutputFactory smFactory = new SMOutputFactory(WstxOutputFactory
				.newInstance());

		try {
			XMLStreamWriter writer = smFactory.createStax2Writer(stream);
			
//			writer.writeCharacters("\n");

			XMLNodeWriter xmlNodewriter = new XMLNodeWriter(writer, 0, 2, ' ');

			xmlNodewriter.write(xmlNode);

			writer.close();

		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		
		xml = stream.toString();
		
		return xml;
	}
	
	/**
	 * 
	 * @param xmlNode
	 * @throws XMLStreamException
	 */
	public void write(XMLNode xmlNode) throws XMLStreamException {
	  writer.writeCharacters(StringTools.fill(nodeDepth, indentChar));
	  write(xmlNode, nodeDepth);
	}

  /**
   * @param xmlNode
   * @param length
   * @throws XMLStreamException 
   */
  private void write(XMLNode xmlNode, int depth) throws XMLStreamException {
    boolean isRoot = false, isTopElement = false;
    
    if (xmlNode.getName().equals("message") || (!xmlNode.isSetParent())) {
      isRoot = true;
    }
    if (!isRoot && (!(xmlNode.getParent() instanceof XMLNode))) {
      isTopElement = true;
    }    
    
    if (xmlNode.isElement() && !xmlNode.getName().equals("message")) {
  	  logger.debug("write(XMLNode, int) - begin - node name, isRoot, isTopElement = " + xmlNode.getName() + ", " + isRoot + ", " + isTopElement);

  	  if (!(isRoot || isTopElement)) {
    	  logger.debug("writing the indentation 0 -> 'indentCount - depth' !??");
        writer.writeCharacters(StringTools.fill(indentCount - depth, indentChar));
      }
      if (xmlNode.getPrefix() != null && xmlNode.getPrefix().trim().length() > 0) {
    	  if (xmlNode.getURI() != null && xmlNode.getURI().trim().length() > 0) {
    		  if (logger.isDebugEnabled()) {
    			  logger.debug("calling writeStartElement with prefix, name and uri = " + xmlNode.getPrefix() + ", " + xmlNode.getName() + ", " + xmlNode.getURI());
    		  }
    		  writer.writeStartElement(xmlNode.getPrefix(), xmlNode.getName(), xmlNode.getURI());
    	  } else {
    		  logger.debug("calling writeStartElement with prefix and name");
    		  writer.writeStartElement(xmlNode.getPrefix(), xmlNode.getName());
    	  }
      } else {
		  logger.debug("calling writeStartElement with name only");
        writer.writeStartElement(xmlNode.getName());
      }      
      
      int nbNamespaces = xmlNode.getNamespacesLength();
      
      for (int i = 0; i < nbNamespaces; i++) {      
        String uri = xmlNode.getNamespaceURI(i);
        String prefix = xmlNode.getNamespacePrefix(i);
        writer.writeNamespace(prefix, uri);
      }
      
      // write the xmlNode attributes
      int nbAttributes = xmlNode.getAttributesLength();
      
      for (int i = 0; i < nbAttributes; i++) {
        String attrName = xmlNode.getAttrName(i);
        String attrURI = xmlNode.getAttrURI(i);
        String attrPrefix = xmlNode.getAttrPrefix(i);
        String attrValue = xmlNode.getAttrValue(i);
        
        if (attrPrefix != null && attrPrefix.trim().length() != 0) {
        	if (logger.isDebugEnabled()) {
        		logger.debug("writeAttribute(attrPrefix, attrURI, attrName, attrValue) = " + attrPrefix + ", " + attrURI + ", " + attrName + ", " + attrValue);
        	}
        	writer.writeAttribute(attrPrefix, attrURI, attrName, attrValue);
        } else if (attrURI != null && attrURI.length() != 0) {
        	if (logger.isDebugEnabled()) {
        		logger.debug("writeAttribute(attrURI, attrName, attrValue) = " + attrURI + ", " + attrName + ", " + attrValue);
        	}
          writer.writeAttribute(attrURI, attrName, attrValue);
        } else {
        	if (logger.isDebugEnabled()) {
        		logger.debug("writeAttribute(attrName, attrValue) = " + attrName + ", " + attrValue);
        	}
          writer.writeAttribute(attrName, attrValue);
        }
      }
      if (xmlNode.getChildCount() > 0) {
    	  logger.debug("writing a new line and the indentation 1 -> 'depth + indentCount'");
          writer.writeCharacters("\n");
          writer.writeCharacters(StringTools.fill(depth + indentCount, indentChar));        
      }

    } else if (xmlNode.isText()) {
      logger.debug("writing some text characters = @" + xmlNode.getCharacters().trim() + "@");
      writer.writeCharacters(xmlNode.getCharacters().trim());
    }


    boolean isNested = false;
    long nbChildren = xmlNode.getChildCount();
    
    logger.debug("write(XMLNode, int) - nb Children = " + nbChildren);
    		
    for (int i = 0; i < nbChildren; i++) {
      XMLNode child = xmlNode.getChildAt(i);
      if (!child.isText()) {
        isNested = true;
        logger.debug("writing the indentation 2 -> 'depth - indentCount'");
        writer.writeCharacters(StringTools.fill((depth - indentCount),
          indentChar));
      }
      write(child, depth + indentCount);
    }
    
    if (xmlNode.isElement() && !xmlNode.getName().equals("message")) {
      if ((isRoot || isTopElement) && (xmlNode.getChildCount() > 0)) {
    	  logger.debug("writing a new line and the indentation 3  -> 'depth'");
        writer.writeCharacters("\n");
        writer.writeCharacters(StringTools.fill(depth, indentChar));
      } else if (isNested) {
    	  logger.debug("writing the indentation 4");
        writer.writeCharacters(StringTools.fill(
          ((depth - indentCount) / indentCount) * indentCount, indentChar));
      }
      writer.writeEndElement();
      if ((!(isRoot || isTopElement)) && (xmlNode.getParent().getParent() != null)) {
    	  logger.debug("writing a new line and the indentation 5 -> 'depth'");
        writer.writeCharacters("\n");
        writer.writeCharacters(StringTools.fill(depth, indentChar));
      }
    }
    logger.debug("write(XMLNode, int) - end");
  }
	
	
}

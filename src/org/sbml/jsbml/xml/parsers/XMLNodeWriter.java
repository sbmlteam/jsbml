/*
 * $Id$
 * $URL$
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

import java.io.StringWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.log4j.Logger;
import org.codehaus.staxmate.SMOutputFactory;
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

	private String indent;
	private XMLStreamWriter writer;
	
	private Logger logger = Logger.getLogger(XMLNodeWriter.class);

	/**
	 * 
	 * @param writer
	 * @param indent
	 */
	public XMLNodeWriter(XMLStreamWriter writer, String indent) { 
		if (writer == null) {
			throw new IllegalArgumentException(
					"Cannot create a XMLNodeWriter with a null writer.");
		}

		this.writer = writer;
		this.indent = indent;
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

			XMLNodeWriter xmlNodewriter = new XMLNodeWriter(writer, "");

			xmlNodewriter.write(xmlNode);

			writer.close();

		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		
		xml = stream.toString();
		
		return xml;
	}
	
	
	public void write(XMLNode xmlNode) throws XMLStreamException {
		
		boolean isRoot = false;
		
		// Notes are handled correctly when reading
		if (xmlNode.getName().equals("message")) {
			writer.writeCharacters(indent);
			isRoot = true;
		}
		
		if (xmlNode.isElement() && !xmlNode.getName().equals("message")) {
			if (xmlNode.getPrefix() != null) {
				writer.writeStartElement(xmlNode.getPrefix(), xmlNode.getName(), xmlNode.getURI());
			} else {
				writer.writeStartElement(xmlNode.getName());
			}
			
			if (isRoot) {
				writer.writeCharacters("\n");
				writer.writeCharacters(indent + "  ");				
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

				if (attrPrefix.length() != 0) {
					// TODO : check if we need to pass null for URI if not defined and if we could use only one method
					writer.writeAttribute(attrPrefix, attrURI, attrName, attrValue);
				} else if (attrURI.length() != 0) {
					writer.writeAttribute(attrURI, attrName, attrValue);
				} else {
					writer.writeAttribute(attrName, attrValue);
				}
			}
		} else if (xmlNode.isText()) {

			logger.debug("writing some text : characters = @" + xmlNode.getCharacters() + "@");

			writer.writeCharacters(xmlNode.getCharacters());
		}


		long nbChildren = xmlNode.getChildCount();

		for (int i = 0; i < nbChildren; i++) {
			XMLNode child = xmlNode.getChildAt(i);
			write(child);
		}

		if (xmlNode.isElement() && !xmlNode.getName().equals("message")) {
			if (isRoot) {
				writer.writeCharacters("\n");
				writer.writeCharacters(indent);				
			}

			writer.writeEndElement();
		}
	}

	
}

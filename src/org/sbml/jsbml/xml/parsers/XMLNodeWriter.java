/*
 * $Id$
 * $URL$
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */
package org.sbml.jsbml.xml.parsers;

import java.io.StringWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.codehaus.staxmate.SMOutputFactory;
import org.sbml.jsbml.xml.XMLNode;

import com.ctc.wstx.stax.WstxOutputFactory;



/**
 *
 * 
 * @author rodrigue
 * 
 */
public class XMLNodeWriter {

	private String indent;
	private XMLStreamWriter writer;
	
	

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
		
		if (xmlNode.getName().equals("notes") || xmlNode.getName().equals("notes")) {
			writer.writeCharacters(indent);
			isRoot = true;
		}
		
		if (xmlNode.isElement()) {
			writer.writeStartElement(xmlNode.getName());
			if (isRoot) {
				writer.writeCharacters("\n");
				writer.writeCharacters(indent + "  ");				
			}
		} else if (xmlNode.isText()) {
			writer.writeCharacters(xmlNode.getCharacters());
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
		
		
		long nbChildren = xmlNode.getNumChildren();
		
		for (int i = 0; i < nbChildren; i++) {
			XMLNode child = xmlNode.getChild(i);
			write(child);
		}

		if (xmlNode.isElement()) {
			if (isRoot) {
				writer.writeCharacters("\n");
				writer.writeCharacters(indent);				
			}
			
			writer.writeEndElement();
		}
	}
	
	
}
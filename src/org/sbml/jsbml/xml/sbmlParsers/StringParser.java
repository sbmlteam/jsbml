/*
 * $Id$
 * $URL$
 *
 *
 *==================================================================================
 * Copyright (c) 2009 the copyright is held jointly by the individual
 * authors. See the file AUTHORS for the list of authors.
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

package org.sbml.jsbml.xml.sbmlParsers;

import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.xml.stax.ReadingParser;

/**
 * A StringParser can be used to store the MathML expressions and/or the html expressions into a String or StringBuffer in the
 * SBML component.
 * @author marine3
 *
 */
public class StringParser implements ReadingParser{
	
	/**
	 * String to be able to detect what type of notes this parser is parsing. It can be 'notes', 'message' or 'math'.
	 */
	private String typeOfNotes = "";

	/**
	 * 
	 * @return the typeOfNotes of this ReadingParser.
	 */
	public String getTypeOfNotes() {
		return typeOfNotes;
	}

	/**
	 * Sets the typeOfNote of this parser.
	 * @param typeOfNotes
	 */
	public void setTypeOfNotes(String typeOfNotes) {
		this.typeOfNotes = typeOfNotes;
	}
	
	/**
	 * 
	 * @param contextObject
	 * @return the appropriate StringBuffer depending on what is the contextObject
	 */
	private StringBuffer getStringBufferFor(Object contextObject){
		StringBuffer buffer = null;

		// If the contextObject is a Constraint instance, this method return the messageBuffer
		// of constraint.
		if (typeOfNotes.equals("message") && contextObject instanceof Constraint){
			Constraint constraint = (Constraint) contextObject;
			buffer = constraint.getMessageBuffer();
			
		}
		// If the contextObject is a SBase instance, this method return the notesBuffer
		// of this component.
		else if ((typeOfNotes.equals("notes") || typeOfNotes.equals("")) && contextObject instanceof SBase){
			SBase sbase = (SBase) contextObject;
			buffer = sbase.getNotesBuffer();
		}
		// If the contextObject is a MathContainer instance, this parser return the mathBuffer
		// of this component.
		else if (typeOfNotes.equals("math") && contextObject instanceof MathContainer){
			MathContainer mathContainer = (MathContainer) contextObject;
			buffer = mathContainer.getMathBuffer();
		}
		else {
			// TODO : SBML syntax error, throw an exception?
		}
		
		return buffer;
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject)
	 */
	public void processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {
		
		StringBuffer buffer = getStringBufferFor(contextObject);
		
		if (buffer != null){
			if (!prefix.equals("")){
				buffer.append(" "+prefix+":"+attributeName+"=\""+value+"\"");
			}
			else {
				buffer.append(" "+attributeName+"=\""+value+"\"");
			}
			
			if (isLastAttribute){
				buffer.append("> \n");
			}
		}
		else {
			if (contextObject instanceof Annotation){
				Annotation annotation = (Annotation) contextObject;
				
				if (!prefix.equals("")){
					annotation.appendNoRDFAnnotation(" "+prefix+":"+attributeName+"=\""+value+"\"");
				}
				else {
					annotation.appendNoRDFAnnotation(" "+attributeName+"=\""+value+"\"");
				}
				
				if (isLastAttribute){
					annotation.appendNoRDFAnnotation("> \n");
				}
			}
		}
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processCharactersOf(String elementName, String characters,
			Object contextObject)
	 */
	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		
		StringBuffer buffer = getStringBufferFor(contextObject);
		// TODO trye to find a way to encode all the special characters.
		characters = characters.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");

		
		if (buffer != null){
			buffer.append(characters + " \n");
		}
		else {
			if (contextObject instanceof Annotation){
				Annotation annotation = (Annotation) contextObject;
				annotation.appendNoRDFAnnotation(characters + " \n");
			}
		}
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject)
	 */
	public void processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {

		StringBuffer buffer = getStringBufferFor(contextObject);
		
		if (buffer != null){
			if (isNested && buffer.toString().endsWith("> \n")){
				int bufferLength = buffer.length();
				buffer.delete(bufferLength - 3, bufferLength);
			}
			
			if (isNested){
				buffer.append("/> \n");
			}
			else {
				if (!prefix.equals("")){
					buffer.append("</"+prefix+":"+elementName+"> \n");
				}
				else {
					buffer.append("</"+elementName+"> \n");
				}
			}
		}
		else {
			if (contextObject instanceof Annotation){
				Annotation annotation = (Annotation) contextObject;
				StringBuilder builder = annotation.getAnnotationBuilder();

				if (isNested && annotation.getNoRDFAnnotation().endsWith("> \n")){
					int builderLength = builder.length();
					builder.delete(builderLength - 4, builderLength - 1);
				}
				
				if (isNested){
					annotation.appendNoRDFAnnotation("/> \n");
				}
				else {
					if (!prefix.equals("")){
						annotation.appendNoRDFAnnotation("</"+prefix+":"+elementName+"> \n");
					}
					else {
						annotation.appendNoRDFAnnotation("</"+elementName+"> \n");
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces,
			Object contextObject)
	 */
	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces,
			Object contextObject) {
		StringBuffer buffer = null;
		
		if (elementName.equals("math") && contextObject instanceof MathContainer){
			MathContainer mathContainer = (MathContainer) contextObject;
			buffer = new StringBuffer();
			mathContainer.setMathBuffer(buffer);
			this.typeOfNotes = elementName;
		}
		else {
			buffer = getStringBufferFor(contextObject);
		}
		
		if (buffer != null){

			if (!prefix.equals("")){
				buffer.append("<"+prefix+":"+elementName);
			}
			else {
				buffer.append("<"+elementName);
			}
			
			if (!hasAttributes && !hasNamespaces){
				buffer.append("> \n");
			}
		}
		else {
			if (contextObject instanceof Annotation){
				Annotation annotation = (Annotation) contextObject;

				if (!prefix.equals("")){
					annotation.appendNoRDFAnnotation("<"+prefix+":"+elementName);
				}
				else {
					annotation.appendNoRDFAnnotation("<"+elementName);
				}
				
				if (!hasAttributes && !hasNamespaces){
					annotation.appendNoRDFAnnotation("> \n");
				}
			}
		}
		return contextObject;
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndDocument(SBMLDocument sbmlDocument)
	 */
	public void processEndDocument(SBMLDocument sbmlDocument) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject)
	 */
	public void processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject) {
		
		StringBuffer buffer = getStringBufferFor(contextObject);

		if (buffer != null){
			if (!prefix.equals("")){
				buffer.append(" "+prefix+":"+localName+"=\""+URI+"\"");
			}
			else {
				buffer.append(" "+localName+"=\""+URI+"\"");
			}
			if (!hasAttributes && isLastNamespace){
				buffer.append("> \n");
			}
		}
		else {
			if (contextObject instanceof Annotation){
				Annotation annotation = (Annotation) contextObject;
				if (!prefix.equals("")){
					annotation.appendNoRDFAnnotation(" "+prefix+":"+localName+"=\""+URI+"\"");
				}
				else {
					annotation.appendNoRDFAnnotation(" "+localName+"=\""+URI+"\"");
				}
				
				if (!hasAttributes && isLastNamespace){
					annotation.appendNoRDFAnnotation("> \n");
				}
			}
		}
	}
}

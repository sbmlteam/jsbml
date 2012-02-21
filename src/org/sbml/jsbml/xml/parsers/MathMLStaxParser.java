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

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.util.StringTools;

/**
 * A MathMLStaxParser is used to parse the MathML expressions injected into a SBML
 * file. The name space URI of this parser is
 * "http://www.w3.org/1998/Math/MathML". This parser is able to read and write
 * MathML expressions (implements ReadingParser and WritingParser).
 * 
 * @author Nicolas Rodriguez
 * @since 0.8
 * @version $Rev$
 */
public class MathMLStaxParser implements ReadingParser {
	

	/**
	 * The number of white spaces for the indent if this is to be used.
	 */
	private short indent;

	/**
	 * Decides whether or not the content of the MathML string should be
	 * indented.
	 */
	private boolean indenting;

	/**
	 * If true no XML declaration will be written into the MathML output,
	 * otherwise the XML version will appear at the beginning of the output.
	 */
	private boolean omitXMLDeclaration;

	private transient Logger logger = Logger.getLogger(MathMLStaxParser.class);
	
	/**
	 * 
	 * @return
	 */
	public int getIndent() {
		return indent;
	}

	/**
	 * 
	 * @return
	 */
	public boolean getIndenting() {
		return indenting;
	}


	/**
	 * 
	 * @return
	 */
	public boolean getOmitXMLDeclaration() {
		return omitXMLDeclaration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processAttribute(String
	 * ElementName, String AttributeName, String value, String prefix, boolean
	 * isLastAttribute, Object contextObject)
	 */
	public void processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {
		// Process the possible attributes.
		// the sbml:units attribute is handle by the SBMLCoreParser.

		if (! (contextObject instanceof ASTNode)) {
			logger.debug("processAttribute : !!!!!!!!! context is not an ASTNode ( " +
					contextObject.getClass());
			return;
		}
		
		ASTNode astNode = (ASTNode) contextObject;
		
		// System.out.println("MathMLStaxParser : processAttribute called");
		// System.out.println("MathMLStaxParser : processAttribute : element name = " + elementName + ", attribute name = " + attributeName + 
		// ", value = " + value + ", prefix = " + prefix + ", " + isLastAttribute + ", " + contextObject);
		
		// Possible value : type, id, style, class, encoding, definitionURL ...
		if (attributeName.equals("type")) {
			astNode.setIsSetNumberType(true);
		}
		if (attributeName.equals("definitionURL")) {
			astNode.setDefinitionURL(value);
		}
		
		if (attributeName.equals("type") || attributeName.equals("definitionURL")) {
			astNode.setType(value);
			// System.out.println("MathMLStaxParser : processAttribute : astNode Type = " + astNode.getType());
		} else if (attributeName.equals("id")) {
			astNode.setId(value);
		} else if (attributeName.equals("style")) {
			astNode.setStyle(value);
		} else if (attributeName.equals("class")) {
			astNode.setClassName(value);
		} else if (attributeName.equals("encoding")) {
			astNode.setEncoding(value);
		}
		
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processCharactersOf(String
	 * elementName, String characters, Object contextObject)
	 */
	public void processCharactersOf(String elementName, String characters, Object contextObject) {
		// process the text content of the mathMl node, mainly ci, cn and csymbol should have content
		
		logger.debug("processCharactersOf called");
		logger.debug("processCharactersOf : element name = " + elementName + ", characters = " + characters);
		
		// Depending of the type of ASTNode, we need to do different things
		if (! (contextObject instanceof ASTNode)) {
			logger.debug("processCharactersOf : !!!!!!!!! context is not an ASTNode ( " + 
					contextObject.getClass() + " )!!!!!!!!!!");
			return;
		}
		
		ASTNode astNode = (ASTNode) contextObject;
		
		// System.out.println("MathMLStaxParser : processCharactersOf : context type : " + astNode.getType());

		FunctionDefinition functionDef = null;
		
		try {
			functionDef = astNode.getParentSBMLObject().getModel().getFunctionDefinition(characters.trim());
		} catch(NullPointerException e) {
			// TODO : this does not work when we do not set the model object when reading a mathML XML String by it's own. 
			// It should not happen in theory but when reading only a mathML block, it is happening and
			// functionDefinition are not properly recognized
			logger.warn("WARNING : cannot recognize properly functionDefinition in mathML block !!!");
		}

		if (functionDef != null) {
			logger.debug("MathMLStaxParser : processCharactersOf : function found !!");
			astNode.setType(Type.FUNCTION);
		}

		if (astNode.isName() || astNode.isFunction()) {
			astNode.setName(characters.trim());
		} else if (astNode.isInteger()) {
			astNode.setValue(StringTools.parseSBMLInt(characters.trim()));
		} else if (astNode.isRational()) {
			if (elementName == null) {
				astNode.setValue(astNode.getNumerator(), StringTools.parseSBMLInt(characters.trim()));
			} else {
				astNode.setValue(StringTools.parseSBMLInt(characters.trim()), (int) 0);
			}
		} else if (astNode.getType().equals(Type.REAL_E)) {
			if (elementName == null) {
				astNode.setValue(astNode.getMantissa(), StringTools.parseSBMLInt(characters.trim()));
			} else {
				astNode.setValue(StringTools.parseSBMLDouble(characters.trim()), (int) 0);
			}
		} else if (astNode.isReal()) {
			astNode.setValue(Double.valueOf(characters.trim()));
		} else if (astNode.getType().equals(Type.FUNCTION_DELAY)) { 
			astNode.setName(characters.trim());
		} else {
			logger.warn("processCharactersOf : !!!!!!!!! I don't know what to do with that : " +
					elementName + " !!!!!!!!!!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndDocument(SBMLDocument
	 * sbmlDocument)
	 */
	public void processEndDocument(SBMLDocument sbmlDocument) {

		// System.out.println("MathMLStaxParser : processEndDocument called");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndElement(String
	 * ElementName, String prefix, boolean isNested, Object contextObject)
	 */
	public boolean processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {
		
		logger.debug("processEndElement called");
		logger.debug("processEndElement : element name = " + elementName);
		if (elementName.equals("sep")) {
			return false;
		} else if (contextObject instanceof MathContainer) {
			try {
				// logger.debug("processEndElement : formula = " + ((MathContainer) contextObject).getMath());
			} catch (Exception e) {
				System.out.println("MathMLStaxParser : processEndElement : Exception " + e.getLocalizedMessage());				
			}
			return false;
			
		} else if (contextObject instanceof ASTNode) {
			ASTNode astNode = (ASTNode) contextObject;
			
			// add other type of ASTNode in the test ??
			if ((astNode.isFunction() || astNode.isOperator() || astNode.isRelational() || 
					astNode.isLogical()) && !elementName.equals("apply") && !elementName.equals("piecewise")) 
			{
				logger.debug("processEndElement : stack stay the same. ASTNode type = " + astNode.getType());
				return false;
				
			}
		}
		
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processNamespace(String
	 * elementName, String URI, String prefix, String localName, boolean
	 * hasAttributes, boolean isLastNamespace, Object contextObject)
	 */
	public void processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject) 
	{
		// TODO 
		
		logger.debug("processNamespace called");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processStartElement(String
	 * ElementName, String prefix, boolean hasAttributes, boolean hasNamespaces,
	 * Object contextObject)
	 */
	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces, Object contextObject) {
		
		logger.debug("processStartElement called");
		logger.debug("processStartElement : element name = " + elementName); // +  
					// ", prefix = " + prefix + ", hasAttributes = " + hasAttributes + ", hasNamespace = " + hasNamespaces);
			// + ", " + contextObject);
		
		if (elementName.equals("math") || elementName.equals("apply") || elementName.equals("sep") 
				|| elementName.equals("piece") || elementName.equals("otherwise") 
				|| elementName.equals("bvar") || elementName.equals("degree") || elementName.equals("logbase")) {
			// we do nothing
			return null;
		}
		
		MathContainer mathContainer = null;
		ASTNode parentASTNode = null;
		boolean setMath = false;
		
		if (contextObject instanceof MathContainer) {
			mathContainer = (MathContainer) contextObject;
			if (mathContainer.getMath() == null) {
				setMath = true;
			} else {
				// because normal operator are written <operator/> in mathML and so the parent ASTNode is not any more the contextObject
				parentASTNode = mathContainer.getMath();
				// System.out.println("MathMLStaxParser : processStartElement parent type : " + parentASTNode.getType());
			}
		} else if (contextObject instanceof ASTNode) {
			
			parentASTNode = ((ASTNode) contextObject);
			mathContainer = parentASTNode.getParentSBMLObject();
			// System.out.println("MathMLStaxParser : processStartElement parent type : " + parentASTNode.getType());
		} else {
			// Should never happen
			logger.debug("processStartElement : !!!!!!!!!!! Should not have been here !!!!!!!!!!!");
			logger.debug("processStartElement : contextObject.classname = " + contextObject.getClass().getName());
			return null;
		}
		
		ASTNode astNode = new ASTNode(mathContainer); 
		astNode.setType(elementName);
		
		if (setMath) {
			mathContainer.setMath(astNode);
		} else {
			parentASTNode.addChild(astNode);
		} 
		
		/*else if (astNode.isNumber() || astNode.isConstant() || astNode.getType().equals(ASTNode.Type.NAME)
				|| astNode.getType().equals(ASTNode.Type.NAME_TIME) || astNode.isInfinity() || astNode.isNaN()
				|| astNode.isNegInfinity()) 
		{
			return parentASTNode;
		} else if () {
		
		*/
		
		return astNode;
	}

	/**
	 * @param indent
	 *            the indent to set
	 */
	public void setIndent(int indent) {
		if ((indent < 0) || (Short.MAX_VALUE < indent)) {
			throw new IllegalArgumentException(String.format(
					"indent %d is out of the range [0, %d].",
					indent, Short.toString(Short.MAX_VALUE)));
		}
		this.indent = (short) indent;
	}

	/**
	 * @param indenting
	 *            the indenting to set
	 */
	public void setIndenting(boolean indenting) {
		this.indenting = indenting;
	}

	/**
	 * @param omitXMLDeclaration
	 *            the omitXMLDeclaration to set
	 */
	public void setOmitXMLDeclaration(boolean omitXMLDeclaration) {
		this.omitXMLDeclaration = omitXMLDeclaration;
	}


}

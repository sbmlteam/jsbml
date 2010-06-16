/*
 * $Id: MathMLTest.java 117 2009-12-16 09:53:01Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/test/org/sbml/jsbml/xml/test/MathMLTest.java $
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
package org.sbml.jsbml.xml.test;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.ASTNode.Type;

/**
 * 
 * @author Andreas Dr&auml;ger
 * 
 */
public class MathMLTest {

	/**
	 * 
	 */
	public MathMLTest() {
		SBMLDocument doc = new SBMLDocument(1, 1);
		Model m = doc.createModel("id");
		FunctionDefinition fd = m.createFunctionDefinition("fd");
		ASTNode math = new ASTNode(Type.LAMBDA, fd); 
		math.addChild(ASTNode.abs(Double.NEGATIVE_INFINITY, fd));
		fd.setMath(math);
		try {
			System.out.println(math.toMathML());
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public static void main(String args[]) {
		new MathMLTest();
	}

}

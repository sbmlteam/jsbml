/*
 * $Id: FormulaTest.java 102 2009-12-13 19:52:50Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/io/FormulaTest.java $
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

package org.sbml.jsbml.test;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.xml.libsbml.LibSBMLReader;
import org.sbml.jsbml.xml.stax.SBMLReader;

/**
 * @author Andreas Dr&auml;ger
 * @date 2010-12-01
 * @version $Rev$
 */
public class LibSBMLioTest {

	static {
		try {
			System.loadLibrary("sbmlj");
			// Extra check to be sure we have access to libSBML:
			Class.forName("org.sbml.libsbml.libsbml");
		} catch (Throwable e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * 
	 * @param args just the path to one SBML file.
	 * @throws XMLStreamException 
	 * @throws IOException 
	 */
	public static void main(String args[]) throws XMLStreamException, IOException {
		org.sbml.libsbml.SBMLReader libReader = new org.sbml.libsbml.SBMLReader(); 
		org.sbml.libsbml.SBMLDocument libDoc = libReader.readSBML(args[0]);
		LibSBMLReader libTranslator = new LibSBMLReader();
		SBMLDocument doc1 = new SBMLReader().readSBML(args[0]);
		SBMLDocument doc2 = libTranslator.convertSBMLDocument(libDoc);
		System.out.println(doc1.equals(doc2));
	}
	
}

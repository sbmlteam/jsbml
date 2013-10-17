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
 package org.sbml.jsbml.test;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.text.parser.ParseException;

/**
 * 
 * @version $Rev$
 * @since 1.0
 */
public class MathMLTest {

	/**
	 * 
	 * @param args
	 * @throws ParseException
	 * @throws XMLStreamException
	 * @throws SBMLException
	 */
	@SuppressWarnings("deprecation")
	public static void main(String args[]) throws ParseException, XMLStreamException, SBMLException {
		SBMLDocument doc = new SBMLDocument(1, 2);
		Model m = doc.createModel();
		Compartment c = m.createCompartment("c1");
		Species s1 = m.createSpecies("s1", c);
		Species s2 = m.createSpecies("s2", c);
		Reaction r = m.createReaction("r1");
		r.createReactant(null, s1);
		r.createProduct(null, s2);
		KineticLaw kl = r.createKineticLaw();
		kl.setFormula("s1 * 3");
		SBMLWriter.write(doc, System.out, ' ', (short) 2);
	}
	
}

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

import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.test.gui.JTreeOfSBML;
import org.sbml.jsbml.util.SimpleTreeNodeChangeListener;
import org.sbml.jsbml.xml.stax.SBMLWriter;

/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class SpeciesAnnotationTest extends SimpleTreeNodeChangeListener {

	public SpeciesAnnotationTest() throws XMLStreamException, SBMLException {
		SBMLDocument doc = new SBMLDocument(2, 4);
		doc.addTreeNodeChangeListener(this);
		Model model = doc.createModel("model_test");
		Species s1 = model.createSpecies("s1", model.createCompartment("c1"));
		s1.setMetaId("meta_" + s1.getId());
		// Not necessary anymore.
		// s1.getAnnotation().addRDFAnnotationNamespace("bqbiol", "",
		// "http://biomodels.net/biology-qualifiers/");
		s1.addCVTerm(new CVTerm(CVTerm.Type.BIOLOGICAL_QUALIFIER,
				CVTerm.Qualifier.BQB_HAS_PART, "urn:miriam:obo.chebi:CHEBI:15422"));
		System.out.println("==================================");
		new SBMLWriter().write(doc, System.out);
		new JTreeOfSBML(doc);
	}

	/**
	 * @param args
	 * @throws SBMLException 
	 * @throws XMLStreamException 
	 */
	public static void main(String[] args) throws XMLStreamException, SBMLException {
		new SpeciesAnnotationTest();
	}

}

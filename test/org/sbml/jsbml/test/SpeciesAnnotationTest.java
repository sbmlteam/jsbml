/*
 * $Id:  SpeciesAnnotationTest.java 17:20:59 draeger $
 * $URL: SpeciesAnnotationTest.java $
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
package org.sbml.jsbml.test;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.test.gui.JTreeOfSBML;
import org.sbml.jsbml.util.SimpleSBaseChangeListener;
import org.sbml.jsbml.xml.stax.SBMLWriter;

/**
 * @author Andreas Dr&auml;ger
 * 
 */
public class SpeciesAnnotationTest extends SimpleSBaseChangeListener {

	public SpeciesAnnotationTest() throws XMLStreamException, SBMLException {
		SBMLDocument doc = new SBMLDocument(2, 4);
		doc.addChangeListener(this);
		Model model = doc.createModel("model_test");
		Species s1 = model.createSpecies("s1", model.createCompartment("c1"));
		s1.setMetaId("meta_" + s1.getId());
        s1.getAnnotation().addRDFAnnotationNamespace("bqbiol", "", "http://biomodels.net/biology-qualifiers/");
		s1.addCVTerm(new CVTerm(CVTerm.Type.BIOLOGICAL_QUALIFIER,
				CVTerm.Qualifier.BQB_HAS_PART, "urn:miriam:obo.chebi:CHEBI:15422"));
		System.out.println("==================================");
		SBMLWriter.write(doc, System.out);
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

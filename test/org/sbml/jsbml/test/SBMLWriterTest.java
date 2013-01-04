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

import java.io.BufferedOutputStream;
import java.util.Calendar;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.CVTerm.Qualifier;
import org.sbml.jsbml.CVTerm.Type;
import org.sbml.jsbml.Creator;
import org.sbml.jsbml.History;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.xml.stax.SBMLWriter;

/**
 * 
 * @author 
 * @since 0.8
 * @version $Rev$
 */
public class SBMLWriterTest {

	public static void main(String args[]) {
		SBMLDocument doc = new SBMLDocument(2, 4);
		doc.setNotes("<body>Senseless test commentar</body>");

		Model m = doc.createModel("model");

		CVTerm term = new CVTerm();
		term.setQualifierType(Type.MODEL_QUALIFIER);
		term.setModelQualifierType(Qualifier.BQM_IS);
		term.addResource("urn:miriam:kegg.pathway:hsa00010");
		m.addCVTerm(term);

		History history = new History();
		Creator creator = new Creator();
		creator.setFamilyName("Dr\u00e4ger");
		creator.setGivenName("Andreas");
		creator.setEmail("andreas.draeger@uni-tuebingen.de");
		creator.setOrganization("Universit\u00e4t T\u00fcbingen");
		history.addCreator(creator);
		history.setCreatedDate(Calendar.getInstance().getTime());
		history.addModifiedDate(Calendar.getInstance().getTime());
		m.setHistory(history);

		m
				.setNotes("<body>A senseless test model with a senseless notes element.</body>");

		m.getUnitDefinition("substance").getUnit(0).setScale(-3);

		try {
			new SBMLWriter().write(doc, new BufferedOutputStream(System.out),
					"SBMLWriterTest", "");
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (SBMLException e) {
			e.printStackTrace();
		}
	}

}

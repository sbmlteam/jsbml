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

import org.junit.Ignore;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.util.SimpleTreeNodeChangeListener;
import org.sbml.jsbml.xml.stax.SBMLWriter;

/**
 * @author Andreas Dr&auml;ger
 * @date 2010-11-12
 * @since 0.8
 * @version $Rev$
 */
@Ignore
public class EventTest extends SimpleTreeNodeChangeListener {

	/**
	 * 
	 * @throws ParseException
	 * @throws XMLStreamException
	 * @throws SBMLException
	 */
	public EventTest() throws ParseException, XMLStreamException, SBMLException {
		SBMLDocument doc = new SBMLDocument(3, 1);
		doc.addTreeNodeChangeListener(this);
		Model model = doc.createModel("event_model");
		Compartment c = model.createCompartment("compartment");
		model.createSpecies("s1", c);
		model.createSpecies("s2", c);
		Event ev = model.createEvent();
		Trigger trigger = ev.createTrigger(false, true, ASTNode.parseFormula("3 >= 2"));
		trigger.setMath(ASTNode.geq(new ASTNode(ASTNode.Type.NAME_TIME),
				new ASTNode(10)));
		ev.createPriority(ASTNode.parseFormula("25"));
		ev.createDelay(ASTNode.parseFormula("2"));
		ev.createEventAssignment("s1", ASTNode.parseFormula("s2"));
		System.out.println("==================================");
		new SBMLWriter().write(doc, System.out);
		System.out.println("\n==================================");
		doc.setLevelAndVersion(2, 4);
		System.out.println("==================================");
		new SBMLWriter().write(doc, System.out);
	}

	/**
	 * @param args
	 * @throws ParseException
	 * @throws SBMLException
	 * @throws XMLStreamException
	 */
	public static void main(String[] args) throws ParseException,
			XMLStreamException, SBMLException {
		new EventTest();
	}
}

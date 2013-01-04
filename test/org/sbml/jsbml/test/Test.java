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

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.util.SimpleTreeNodeChangeListener;
import org.sbml.jsbml.xml.stax.SBMLWriter;

/**
 * 
 * @author
 * @since 0.8
 * @version $Rev$
 */
public class Test {

	/**
	 * @param args
	 * @throws SBMLException
	 * @throws XMLStreamException
	 */
	public static void main(String[] args) throws XMLStreamException,
			SBMLException {
		SBMLDocument doc = new SBMLDocument(2, 4);
		doc.addTreeNodeChangeListener(new SimpleTreeNodeChangeListener());
		Model model = doc.createModel("test_model");
		
		Parameter k1 = model.createParameter("k1");
		Parameter k2 = model.createParameter("k2");

    k1.addCVTerm(new CVTerm(CVTerm.Qualifier.BQB_IS, "test"));
    
		k1.setConstant(false);
		k2.setConstant(false);
		
		Event event = model.createEvent("test_event");
		
		Trigger trigger = event.createTrigger();
		trigger.setMath(ASTNode.geq(new ASTNode(ASTNode.Type.NAME_TIME),
				new ASTNode(10)));
		
		EventAssignment assignment1 = event.createEventAssignment();
		assignment1.setVariable(k1);
		assignment1.setMath(new ASTNode(34));
		
		EventAssignment assignment2 = event.createEventAssignment();
		assignment2.setVariable(k2);
		assignment2.setMath(new ASTNode(k1));
		
		new SBMLWriter().write(doc, System.out);
	}

}

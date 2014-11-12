/*
 * $Id$
 * $URL$
 *
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2014 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
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
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Priority;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.math.parser.ParseException;

/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class PriorityTest {

  /**
   * @param args
   * @throws ParseException
   * @throws SBMLException
   * @throws XMLStreamException
   */
  @SuppressWarnings("deprecation")
  public static void main(String[] args) throws ParseException,
  XMLStreamException, SBMLException {
    SBMLDocument doc = new SBMLDocument(3, 1);
    Model model = doc.createModel("test_model");
    Parameter p = model.createParameter("p1");
    p.setValue(1d);
    Event e = model.createEvent("e1");
    Priority prior = e.createPriority();
    prior.setMath(new ASTNode(1));
    Trigger t = e.createTrigger();
    t.setFormula("time == 1");
    EventAssignment ea = e.createEventAssignment(p, ASTNode
      .parseFormula("3"));
    e.addEventAssignment(ea);
    System.out.println((new SBMLWriter()).writeSBMLToString(doc));
  }

}

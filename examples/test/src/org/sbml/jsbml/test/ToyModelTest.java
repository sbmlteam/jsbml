/*
 * $Id: ToyModelTest.java 2109 2015-01-05 04:50:45Z andreas-draeger $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/examples/test/src/org/sbml/jsbml/test/ToyModelTest.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Creator;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.History;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.util.SimpleTreeNodeChangeListener;
import org.sbml.jsbml.xml.stax.SBMLWriter;

/**
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev: 2109 $
 */
public class ToyModelTest {

  /**
   * @param args
   * @throws SBMLException
   * @throws XMLStreamException
   * @throws ParseException
   */
  public static void main(String[] args) throws XMLStreamException,
  SBMLException, ParseException {

    SBMLDocument doc = new SBMLDocument(2, 4);
    doc.addTreeNodeChangeListener(new SimpleTreeNodeChangeListener());
    Model model = doc.createModel("test_model");

    Creator c = new Creator("Hans", "Wurst",
      "Institute for Interesting Biology", "ovidiu.radulescu@univ-rennes1.fr");
    History h = new History();
    h.addCreator(c);
    model.setHistory(h);
    //		model.appendNotes("This is a very interesting model.");
    model.appendNotes("<body xmlns=\"http://www.w3.org/1999/xhtml\"><h1>Model of &#8220;Apoptosis&#8221; in &#8220;Homo sapiens (human)&#8221;</h1>Apoptosis is a genetically controlled mechanisms of cell death involved in the regulation of tissue homeostasis. The 2 major pathways of apoptosis are the extrinsic (Fas and other TNFR superfamily members and ligands) and the intrinsic (mitochondria-associated) pathways, both of which are found in the cytoplasm. The extrinsic pathway is triggered by death receptor engagement, which initiates a signaling cascade mediated by caspase-8 activation. Caspase-8 both feeds directly into caspase-3 activation and stimulates the release of cytochrome c by the mitochondria. Caspase-3 activation leads to the degradation of cellular proteins necessary to maintain cell survival and integrity. The intrinsic pathway occurs when various apoptotic stimuli trigger the release of cytochrome c from the mitochondria (independently of caspase-8 activation). Cytochrome c interacts with Apaf-1 and caspase-9 to promote the activation of caspase-3. Recent studies point to the ER as a third subcellular compartment implicated in apoptotic execution. Alterations in Ca2+ homeostasis and accumulation of misfolded proteins in the ER cause ER stress. Prolonged ER stress can result in the activation of BAD and/or caspase-12, and execute apoptosis.<br/><a href=\"http://www.genome.jp/kegg/pathway/hsa/hsa04210.png\"><img src=\"http://www.genome.jp/kegg/pathway/hsa/hsa04210.png\" alt=\"http://www.genome.jp/kegg-bin/show_pathway?hsa04210\"/></a><br/><a href=\"http://www.genome.jp/kegg-bin/show_pathway?hsa04210\">Original Entry</a><br/><div align=\"right\"></div><br/></body>");

    Parameter k1 = model.createParameter("k1");
    Parameter k2 = model.createParameter("k2");

    k1.setConstant(false);
    k2.setConstant(false);

    k1.addCVTerm(new CVTerm(CVTerm.Qualifier.BQB_IS, "test"));

    Event event = model.createEvent("test_event");

    Trigger trigger = event.createTrigger();
    //		trigger.setMath(ASTNode.geq(new ASTNode(ASTNode.Type.NAME_TIME),
    //				new ASTNode(10)));
    trigger.setMath(ASTNode.parseFormula("time >= 10"));

    EventAssignment assignment1 = event.createEventAssignment();
    assignment1.setVariable(k1);
    assignment1.setMath(new ASTNode(34));

    EventAssignment assignment2 = event.createEventAssignment();
    assignment2.setVariable(k2);
    assignment2.setMath(new ASTNode(k1));

    new SBMLWriter().write(doc, System.out);
  }

}

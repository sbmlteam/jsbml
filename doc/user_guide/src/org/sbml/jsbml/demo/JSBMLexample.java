/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
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
package org.sbml.jsbml.demo;

import java.beans.PropertyChangeEvent;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.*;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.util.TreeNodeRemovedEvent;

/** Creates an {@link SBMLDocument} and writes its contents to a file. **/
public class JSBMLexample implements TreeNodeChangeListener {

  public JSBMLexample() throws Exception {
    // Create a new SBMLDocument object, using SBML Level 3 Version 1.
    SBMLDocument doc = new SBMLDocument(3, 1);
    doc.addTreeNodeChangeListener(this);

    // Create a new SBML model, and add a compartment to it.
    Model model = doc.createModel("test_model");
    Compartment compartment = model.createCompartment("default");
    compartment.setSize(1d);

    // Create a model history object and add author information to it.
    History hist = model.getHistory(); // Will create the History, if it does not exist
    Creator creator = new Creator("Given Name", "Family Name", "Organisation", "My@EMail.com");
    hist.addCreator(creator);

    // Create some sample content in the SBML model.
    Species specOne = model.createSpecies("test_spec1", compartment);
    Species specTwo = model.createSpecies("test_spec2", compartment);
    Reaction sbReaction = model.createReaction("reaction_id");

    // Add a substrate (SBO:0000015) and product (SBO:0000011) to the reaction.
    SpeciesReference subs = sbReaction.createReactant(specOne);
    subs.setSBOTerm(15);
    SpeciesReference prod = sbReaction.createProduct(specTwo);
    prod.setSBOTerm(11);

    // For brevity, we omit error checking, BUT YOU SHOULD CALL doc.checkConsistency() and check the error log.

    // Write the SBML document to a file.
    SBMLWriter.write(doc, "test.xml", "JSBMLexample", "1.0");
  }

  /** Main routine.  This does not take any arguments. */
  public static void main(String[] args) throws Exception {
    new JSBMLexample();
  }

  /* Methods for TreeNodeChangeListener, to respond to events from SBaseChangedListener. */
  @Override
  public void nodeAdded(TreeNode sb) {
    System.out.println("[ADD] " + sb);
  }

  @Override
  public void nodeRemoved(TreeNodeRemovedEvent evt) {
    System.out.println("[RMV] " + evt.getSource());
  }

  @Override
  public void propertyChange(PropertyChangeEvent ev) {
    System.out.println("[CHG] " + ev);
  }
}

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
package org.sbml.jsbml.ext.spatial;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;

/**
 * 
 * @author Alex Thomas
 * @since 1.0
 */
public class SpatialTest {

  /**
   * @param args
   * @throws SBMLException
   * @throws XMLStreamException
   */
  public static void main(String[] args) throws SBMLException, XMLStreamException {
    int level = 3, version = 1;
    Species spec1,spec2,spec3;
    Reaction rxn1;

    SBMLDocument doc = new SBMLDocument(level, version);
    Model model = doc.createModel("my_model");

    // Normal model

    Compartment comp1 = model.createCompartment("comp1");

    spec1 = model.createSpecies("a", comp1);
    spec2 = model.createSpecies("b", comp1);
    spec3 = model.createSpecies("c", comp1);

    rxn1 = model.createReaction("r1");

    rxn1.addReactant(new SpeciesReference(spec1));
    rxn1.addReactant(new SpeciesReference(spec2));
    rxn1.addProduct(new SpeciesReference(spec3));

    // Creating the spatial model extension and adding it to the document

    // Create spatial extensions for model and compartment
    SpatialModelPlugin spatialModelPlugin = new SpatialModelPlugin(model);
    model.addExtension(SpatialConstants.getNamespaceURI(level, version), spatialModelPlugin);

    SpatialCompartmentPlugin spatialComp = new SpatialCompartmentPlugin(comp1);
    comp1.addExtension(SpatialConstants.getNamespaceURI(level, version), spatialComp);

    // Add non-SBML-core classes

    /*Geometry geo =*/
    spatialModelPlugin.createGeometry();

    CompartmentMapping spatialCompMap = new CompartmentMapping();
    spatialComp.setCompartmentMapping(spatialCompMap);
    spatialCompMap.setDomainType("DomainType1");

    SBMLWriter.write(doc, System.out, ' ', (short) 2);

  }
}

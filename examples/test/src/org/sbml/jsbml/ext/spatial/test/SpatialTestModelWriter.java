/*
 * $Id: SpatialTestModelWriter.java 2109 2015-01-05 04:50:45Z andreas-draeger $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/examples/test/src/org/sbml/jsbml/ext/spatial/test/SpatialTestModelWriter.java $
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
package org.sbml.jsbml.ext.spatial.test;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.spatial.Geometry;
import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.ext.spatial.SpatialModelPlugin;
import org.sbml.jsbml.util.ResourceManager;

/**
 * @author Andreas Dr&auml;ger
 * @version $Rev: 2109 $
 * @since 1.0
 * @date 07.02.2014
 */
public class SpatialTestModelWriter {

  /**
   * @param args
   * @throws XMLStreamException
   * @throws SBMLException
   */
  public static void main(String[] args) throws SBMLException, XMLStreamException {

    ResourceManager.getBundle("org.sbml.jsbml.ext.spatial.Messages");

    SBMLDocument doc = new SBMLDocument(3, 1);
    Model model = doc.createModel("m1");
    Compartment comp = model.createCompartment("default");
    Species s1 = model.createSpecies("s1", comp);
    Species s2 = model.createSpecies("s2", comp);
    Species p1 = model.createSpecies("p1", comp);
    Reaction r1 = model.createReaction("r1");
    r1.setCompartment(comp);
    r1.createReactant(s1);
    r1.createReactant(s2);
    r1.createProduct(p1);


    SpatialModelPlugin plugin = new SpatialModelPlugin(model);
    model.addExtension(SpatialConstants.namespaceURI, plugin);
    Geometry geometry = plugin.createGeometry();
    geometry.createAdjacentDomain();
    geometry.createCoordinateComponent("test");


    SBMLWriter.write(doc, System.out, ' ', (short) 2);
  }

}

/*
 * $Id$
 * $URL$
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
package org.sbml.jsbml.ext.distrib.util;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.ext.distrib.DistribFunctionDefinitionPlugin;
import org.sbml.jsbml.ext.distrib.DistribInput;
import org.sbml.jsbml.ext.distrib.DrawFromDistribution;
import org.sbml.jsbml.xml.XMLAttributes;
import org.sbml.jsbml.xml.XMLNamespaces;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.XMLTriple;

/**
 *
 * This class provides a collection of convenient methods to create SBML elements
 * related to the distrib package.
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 */
public class DistribModelBuilder {


  /**
   * Creates the constructs needed for the given distribution in the distrib package.
   * 
   * <p>If any {@link DrawFromDistribution} instance was set in the given {@link FunctionDefinition}, it
   * will be replaced. First we create a new {@link DrawFromDistribution}. Then we create one {@link DistribInput}
   * for each String from the {@code inputs} array. To finish the UncertML {@link XMLNode} is created using the given
   * distribution name, the inputs and inputTypes.
   * 
   * @param f a functionDefinition where we will add distrib info
   * @param distribution the name of the UncertML distribution as it will be set in the XML.
   * @param inputTypes the array of input types, used to create the XML child element of the UncertML distribution.
   * @param inputs the array of inputs, used to create the {@link DistribInput} id and the 'varId' attribute value
   * for the 'var' UncertML elements.
   */
  public static void createDistribution(FunctionDefinition f, String distribution, String[] inputTypes, String[] inputs) {
    String annotationStr = "<annotation>" +
        "<distribution xmlns=\"http://sbml.org/annotations/distribution\" definition=\"http://en.wikipedia.org/wiki/Normal_distribution\"/>" +
        "</annotation>";

    try {
      f.getAnnotation().setNonRDFAnnotation(annotationStr);
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }

    DistribFunctionDefinitionPlugin distrib = (DistribFunctionDefinitionPlugin) f.getPlugin("distrib");
    DrawFromDistribution draw = distrib.createDrawFromDistribution();

    for (int i=0; i < inputs.length; i++) {
      DistribInput input = draw.createDistribInput();
      input.setId(inputs[i]);
    }

    // UncertML element
    XMLNode xmlNode = new XMLNode(new XMLTriple("UncertML"), new XMLAttributes(), new XMLNamespaces());
    xmlNode.addNamespace("http://www.uncertml.org/3.0");

    // NormalDistribution element
    XMLNode distNode = new XMLNode(new XMLTriple(distribution), new XMLAttributes(), new XMLNamespaces());
    distNode.addAttr("definition", "http://www.uncertml.org/distributions");
    xmlNode.addChild(distNode);


    for (int i=0; i < inputs.length; i++) {
      XMLNode inputNode = new XMLNode(new XMLTriple(inputTypes[i]), new XMLAttributes(), new XMLNamespaces());
      distNode.addChild(inputNode);
      distNode.addChild(new XMLNode("\n              "));

      XMLNode varNode = new XMLNode(new XMLTriple("var"), new XMLAttributes(), new XMLNamespaces());
      varNode.addAttr("varId", inputs[i]);
      inputNode.addChild(varNode);
      inputNode.addChild(new XMLNode("\n              "));
    }

    // adding the UncertML XMLNode to the DrawFromDistribution object
    draw.setUncertML(xmlNode);
  }

  /**
   * @param args
   * @throws SBMLException
   * @throws XMLStreamException
   */
  public static void main(String[] args) throws SBMLException, XMLStreamException {
    // TODO: move this main to the examples folder.
    SBMLDocument doc = new SBMLDocument(3, 1);
    Model m = doc.createModel("m");

    FunctionDefinition f = m.createFunctionDefinition("f");

    createDistribution(f, "NormalDistribution", new String[] { "mean", "stddev" }, new String[] {"avg", "sd"});

    String docStr = new SBMLWriter().writeSBMLToString(doc);

    System.out.println("Document = \n" + docStr);

    SBMLDocument doc2 = new SBMLReader().readSBMLFromString(docStr);

    // System.out.println("Function f, type = " + r.getKineticLaw().getMath().getType());

    String docStr2 = new SBMLWriter().writeSBMLToString(doc2);

    System.out.println(docStr.equals(docStr2));
  }

}

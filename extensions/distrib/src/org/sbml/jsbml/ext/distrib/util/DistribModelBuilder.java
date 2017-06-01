/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2017 jointly by the following organizations:
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
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.distrib.DistribFunctionDefinitionPlugin;
import org.sbml.jsbml.ext.distrib.DistribInput;
import org.sbml.jsbml.ext.distrib.DistribSBasePlugin;
import org.sbml.jsbml.ext.distrib.DrawFromDistribution;
import org.sbml.jsbml.ext.distrib.Uncertainty;
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

    String distribUrl = distribution;
    
    if (distribUrl.endsWith("Distribution")) {
      distribUrl = distribUrl.substring(0, distribUrl.length() - 12);
    }
    String definition = "http://www.uncertml.org/distributions/" + toUncertmlURL(distribUrl);
    
    // UncertML element
    XMLNode xmlNode = new XMLNode(new XMLTriple("UncertML"), new XMLAttributes(), new XMLNamespaces());
    xmlNode.addNamespace("http://www.uncertml.org/3.0");

    // NormalDistribution element
    XMLNode distNode = new XMLNode(new XMLTriple(distribution), new XMLAttributes(), new XMLNamespaces());
    distNode.addAttr("definition", definition);
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
   * Creates the constructs needed for a uncertml Range.
   * 
   * <p>If any {@link Uncertainty} instance was set in the given {@link SBase}, it
   * will be replaced.
   * 
   * @param sbase an SBase where we will add uncertainty information
   * @param lower the lower value of the Range
   * @param upper the upper value of the Range
   * 
   */
  public static void createRange(SBase sbase, String lower, String upper) {

    DistribSBasePlugin distrib = (DistribSBasePlugin) sbase.getPlugin("distrib");
    Uncertainty draw = distrib.createUncertainty();

    // UncertML element
    XMLNode xmlNode = new XMLNode(new XMLTriple("UncertML"), new XMLAttributes(), new XMLNamespaces());
    xmlNode.addNamespace("http://www.uncertml.org/3.0");

    // Range element
    XMLNode distNode = new XMLNode(new XMLTriple("Range"), new XMLAttributes(), new XMLNamespaces());
    distNode.addAttr("definition", "http://www.uncertml.org/statistics/range"); // TODO - check which definitions to use 
    xmlNode.addChild(distNode);


    XMLNode lowerNode = new XMLNode(new XMLTriple("lower"), new XMLAttributes(), new XMLNamespaces());
    XMLNode rValNode = new XMLNode(new XMLTriple("rVal"), new XMLAttributes(), new XMLNamespaces());
    
    
    rValNode.addChild(new XMLNode(lower));
    lowerNode.addChild(rValNode);
    lowerNode.addChild(new XMLNode("\n              "));
    distNode.addChild(lowerNode);
    distNode.addChild(new XMLNode("\n              "));

    
    XMLNode upperNode = new XMLNode(new XMLTriple("upper"), new XMLAttributes(), new XMLNamespaces());
    rValNode = new XMLNode(new XMLTriple("rVal"), new XMLAttributes(), new XMLNamespaces());

    rValNode.addChild(new XMLNode(upper));
    upperNode.addChild(rValNode);
    upperNode.addChild(new XMLNode("\n              "));
    distNode.addChild(upperNode);
    distNode.addChild(new XMLNode("\n            "));

    // adding the UncertML XMLNode to the DrawFromDistribution object
    draw.setUncertML(xmlNode);
    
  }

  /**
   * Creates the constructs needed for a StatisticsCollection.
   * 
   * <p>If any {@link Uncertainty} instance was set in the given {@link SBase}, it
   * will be replaced.
   * 
   * 
   * @param sbase an SBase where we will add uncertainty information
   * @param inputTypes the array of input types, used to create the XML child element of the UncertML StatisticsCollection.
   * @param inputs the array of inputs, used to create the child element of the value element
   * @param values the array of statistics values
   * 
   */
  public static void createStatisticsCollection(SBase sbase, String[] inputTypes, String[] inputs, String[] values) 
  {
    createStatisticsCollection(sbase, inputTypes, inputs, values, new String[inputs.length], new String[inputs.length]);
  }
  
  /**
   * Creates the constructs needed for a StatisticsCollection.
   * 
   * <p>If any {@link Uncertainty} instance was set in the given {@link SBase}, it
   * will be replaced.
   * 
   * 
   * @param sbase an SBase where we will add uncertainty information
   * @param inputTypes the array of input types, used to create the XML child element of the UncertML StatisticsCollection.
   * @param inputs the array of inputs, used to create the child element of the value element
   * @param values the array of statistics values
   * @param attributeNames the array of attributes, one per inputs.
   * @param attributeValues the array of attribute values, one per inputs.
   */
  public static void createStatisticsCollection(SBase sbase, String[] inputTypes, String[] inputs, String[] values, String[] attributeNames, String[] attributeValues) {

    DistribSBasePlugin distrib = (DistribSBasePlugin) sbase.getPlugin("distrib");
    Uncertainty draw = distrib.createUncertainty();
    String definition_base = "http://www.uncertml.org/statistics";
    
    if (attributeNames == null) {
      attributeNames = new String[inputs.length];
    }
    if (attributeValues == null) {
      attributeValues = new String[inputs.length];
    }
    
    // UncertML element
    XMLNode xmlNode = new XMLNode(new XMLTriple("UncertML"), new XMLAttributes(), new XMLNamespaces());
    xmlNode.addNamespace("http://www.uncertml.org/3.0");

    // Range element
    XMLNode distNode = new XMLNode(new XMLTriple("StatisticsCollection"), new XMLAttributes(), new XMLNamespaces());
    distNode.addAttr("definition", definition_base + "/statistics-collection"); 
    xmlNode.addChild(distNode);

    for (int i=0; i < inputs.length; i++) {

      String definition = definition_base  + "/" + toUncertmlURL(inputTypes[i]);
      
      XMLNode inputNode = new XMLNode(new XMLTriple(inputTypes[i]), new XMLAttributes(), new XMLNamespaces());
      inputNode.addAttr("definition", definition);

      if (attributeNames[i] != null && attributeValues[i] != null) {
        inputNode.addAttr(attributeNames[i], attributeValues[i]);
      }
      
      distNode.addChild(inputNode);
      distNode.addChild(new XMLNode("\n              "));

      XMLNode valueNode = new XMLNode(new XMLTriple("value"), new XMLAttributes(), new XMLNamespaces());
      inputNode.addChild(valueNode);
      inputNode.addChild(new XMLNode("\n                "));
            
      XMLNode varNode = new XMLNode(new XMLTriple(inputs[i]), new XMLAttributes(), new XMLNamespaces());
      varNode.addChild(new XMLNode(values[i]));
      valueNode.addChild(varNode);
      valueNode.addChild(new XMLNode("\n                "));
    }

    // adding the UncertML XMLNode to the DrawFromDistribution object
    draw.setUncertML(xmlNode);
    
  }

  /**
   * Returns a new String with the first upper case character transformed to lower case
   * and all following upper case characters transformed to '-' + lower case.
   * 
   *  <p>For example: 'StandardDeviation' ==> 'standard-deviation', 'CoefficientOfVariation' ==> 'coefficient-of-variation'
   * 
   * @param string
   * @return
   */
  private static String toUncertmlURL(String string) {

    StringBuffer sb = new StringBuffer();
    String lowercase = string.toLowerCase();
    
    sb.append(lowercase.charAt(0));
    
    for (int i = 1; i < string.length(); i++) {
      char currentChar = string.charAt(i);
      
      if (Character.isUpperCase(currentChar)) {
        sb.append('-').append(Character.toLowerCase(currentChar));
      } else {
        sb.append(currentChar);
      }
    }
    
    return sb.toString();
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

    createDistribution(f, "NormalDistribution", new String[] {"mean", "stddev"}, new String[] {"avg", "sd"});

    Parameter p = m.createParameter("p1");
    
    createRange(p, "2.1", "4.5");

    Parameter p2 = m.createParameter("p2");

    createStatisticsCollection(p2, new String[] {"Mean", "StandardDeviation"}, new String[] {"rVal", "prVal"}, new String[] {"4.1", "0.5"});

    Parameter p3 = m.createParameter("p3");

    createStatisticsCollection(p3, new String[] {"Moment", "Moment"}, new String[] {"rVal", "rVal"}, new String[] {"4.1", "0.5"},
        new String[] {"order", "order"}, new String[] {"1", "2"});

    String docStr = new SBMLWriter().writeSBMLToString(doc);

    System.out.println("Document = \n" + docStr);

    SBMLDocument doc2 = new SBMLReader().readSBMLFromString(docStr);

    // System.out.println("Function f, type = " + r.getKineticLaw().getMath().getType());

    String docStr2 = new SBMLWriter().writeSBMLToString(doc2);

    System.out.println(docStr.equals(docStr2));
  }

}

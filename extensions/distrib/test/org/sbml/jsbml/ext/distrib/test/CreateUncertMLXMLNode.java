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
package org.sbml.jsbml.ext.distrib.test;

import java.io.StringReader;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.TidySBMLWriter;
import org.sbml.jsbml.ext.distrib.DistribFunctionDefinitionPlugin;
import org.sbml.jsbml.ext.distrib.DrawFromDistribution;
import org.sbml.jsbml.text.parser.FormulaParserLL3;
import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.xml.XMLAttributes;
import org.sbml.jsbml.xml.XMLNamespaces;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.XMLTriple;


/**
 * @author Nicolas Rdodriguez
 * @version $Rev$
 * @since 1.1
 * @date 26.03.2015
 */
public class CreateUncertMLXMLNode {

  /**
   * @param args
   * @throws ParseException
   * @throws SBMLException
   * @throws XMLStreamException
   */
  public static void main(String[] args) throws ParseException, SBMLException, XMLStreamException {

    String uncertML = "<UncertML xmlns=\"http://www.uncertml.org/3.0\">\n" +
        "  <NormalDistribution definition=\"http://www.uncertml.org/distributions\">\n" +
        "    <mean>\n" +
        "      <var varId=\"avg\"/>" +
        "    </mean>" +
        "    <stddev>" +
        "      <var varId=\"sd\"/>" +
        "    </stddev>" +
        "</NormalDistribution>" +
        "</UncertML>";

    SBMLDocument doc = new SBMLDocument(3, 1);
    Model m = doc.createModel("m");

    FunctionDefinition f = m.createFunctionDefinition("f");
    ASTNode lambda = ASTNode.parseFormula("lamdba(x, y, x + y)", new FormulaParserLL3(new StringReader("")));
    // System.out.println(lambda.toMathML()); // not sure how to create lambda from formula !!
    // f.setMath(lambda);

    Reaction r = m.createReaction("r");
    r.createKineticLaw().setMath(ASTNode.parseFormula("f(x, y)"));

    // UncertML element
    XMLNode xmlNode = new XMLNode(new XMLTriple("UncertML"), new XMLAttributes(), new XMLNamespaces());
    xmlNode.addNamespace("http://www.uncertml.org/3.0");

    // NormalDistribution element
    XMLNode distNode = new XMLNode(new XMLTriple("NormalDistribution"), new XMLAttributes(), new XMLNamespaces());
    distNode.addAttr("definition", "http://www.uncertml.org/distributions");
    xmlNode.addChild(distNode);

    // mean element
    XMLNode meanNode = new XMLNode(new XMLTriple("mean"), new XMLAttributes(), new XMLNamespaces());
    distNode.addChild(meanNode);

    // var element
    XMLNode varNode = new XMLNode(new XMLTriple("var"), new XMLAttributes(), new XMLNamespaces());
    varNode.addAttr("varId", "avg");
    meanNode.addChild(varNode);

    // stddev element
    XMLNode stddevNode = new XMLNode(new XMLTriple("stddev"), new XMLAttributes(), new XMLNamespaces());
    distNode.addChild(stddevNode);

    // var element
    varNode = new XMLNode(new XMLTriple("var"), new XMLAttributes(), new XMLNamespaces());
    varNode.addAttr("varId", "sd");
    stddevNode.addChild(varNode);

    DistribFunctionDefinitionPlugin dfd = (DistribFunctionDefinitionPlugin) f.getPlugin("distrib");
    DrawFromDistribution drawfd = dfd.createDrawFromDistribution();
    drawfd.setUncertML(xmlNode);

    FunctionDefinition g = m.createFunctionDefinition("g");
    dfd = (DistribFunctionDefinitionPlugin) g.getPlugin("distrib");
    drawfd = dfd.createDrawFromDistribution();

    XMLNode uncertMLNode = XMLNode.convertStringToXMLNode(uncertML);
    drawfd.setUncertML(uncertMLNode);

    String docStr = new TidySBMLWriter().writeSBMLToString(doc);

    System.out.println("Document = \n" + docStr);

    SBMLDocument doc2 = new SBMLReader().readSBMLFromString(docStr);

    // System.out.println("Function f, type = " + r.getKineticLaw().getMath().getType());

    String docStr2 = new TidySBMLWriter().writeSBMLToString(doc2);

    System.out.println(docStr.equals(docStr2));
  }

}

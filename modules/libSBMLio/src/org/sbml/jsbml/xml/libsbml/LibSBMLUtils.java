/*
 * $Id: LibSBMLUtils.java 2109 2015-01-05 04:50:45Z andreas-draeger $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/modules/libSBMLio/src/org/sbml/jsbml/xml/libsbml/LibSBMLUtils.java $
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
package org.sbml.jsbml.xml.libsbml;

import static org.sbml.jsbml.xml.libsbml.LibSBMLConstants.LINK_TO_LIBSBML;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.CVTerm.Qualifier;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.CompartmentType;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Creator;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.ExplicitRule;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.History;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Priority;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.SimpleSpeciesReference;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.StoichiometryMath;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.libsbml.ModelCreator;
import org.sbml.libsbml.ModelHistory;
import org.sbml.libsbml.libsbmlConstants;

/**
 * This class consists of help-methods which are used in all classes of this
 * package
 * 
 * @author Meike Aichele
 * @version $Rev: 2109 $
 * @since 1.0
 */
@SuppressWarnings("deprecation")
public class LibSBMLUtils {

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(LibSBMLUtils.class);

  /**
   * 
   * @param math
   * @param parent
   * @return
   */
  public static ASTNode convert(org.sbml.libsbml.ASTNode math, MathContainer parent) {
    ASTNode ast;
    switch (math.getType()) {
    case libsbmlConstants.AST_REAL:
      ast = new ASTNode(ASTNode.Type.REAL, parent);
      ast.setValue(math.getReal());
      break;
    case libsbmlConstants.AST_INTEGER:
      ast = new ASTNode(ASTNode.Type.INTEGER, parent);
      ast.setValue(math.getInteger());
      break;
    case libsbmlConstants.AST_FUNCTION_LOG:
      ast = new ASTNode(ASTNode.Type.FUNCTION_LOG, parent);
      break;
    case libsbmlConstants.AST_POWER:
      ast = new ASTNode(ASTNode.Type.POWER, parent);
      break;
    case libsbmlConstants.AST_PLUS:
      ast = new ASTNode(ASTNode.Type.PLUS, parent);
      break;
    case libsbmlConstants.AST_MINUS:
      ast = new ASTNode(ASTNode.Type.MINUS, parent);
      break;
    case libsbmlConstants.AST_TIMES:
      ast = new ASTNode(ASTNode.Type.TIMES, parent);
      break;
    case libsbmlConstants.AST_DIVIDE:
      ast = new ASTNode(ASTNode.Type.DIVIDE, parent);
      break;
    case libsbmlConstants.AST_RATIONAL:
      ast = new ASTNode(ASTNode.Type.RATIONAL, parent);
      ast.setValue(math.getNumerator(), math.getDenominator());
      break;
    case libsbmlConstants.AST_NAME_TIME:
      ast = new ASTNode(ASTNode.Type.NAME_TIME, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_DELAY:
      ast = new ASTNode(ASTNode.Type.FUNCTION_DELAY, parent);
      break;
    case libsbmlConstants.AST_NAME:
      ast = new ASTNode(ASTNode.Type.NAME, parent);
      if (parent instanceof KineticLaw) {
        for (LocalParameter p : ((KineticLaw) parent).getListOfLocalParameters()) {
          if (p.getId().equals(math.getName())) {
            ast.setVariable(p);
            break;
          }
        }
      }
      if (ast.getVariable() == null) {
        ast.setName(math.getName());
      }
      break;
    case libsbmlConstants.AST_CONSTANT_PI:
      ast = new ASTNode(ASTNode.Type.CONSTANT_PI, parent);
      break;
    case libsbmlConstants.AST_CONSTANT_E:
      ast = new ASTNode(ASTNode.Type.CONSTANT_E, parent);
      break;
    case libsbmlConstants.AST_CONSTANT_TRUE:
      ast = new ASTNode(ASTNode.Type.CONSTANT_TRUE, parent);
      break;
    case libsbmlConstants.AST_CONSTANT_FALSE:
      ast = new ASTNode(ASTNode.Type.CONSTANT_FALSE, parent);
      break;
    case libsbmlConstants.AST_REAL_E:
      ast = new ASTNode(ASTNode.Type.REAL_E, parent);
      ast.setValue(math.getMantissa(), math.getExponent());
      break;
    case libsbmlConstants.AST_FUNCTION_ABS:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ABS, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCCOS:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCCOS, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCCOSH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCCOSH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCCOT:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCCOT, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCCOTH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCCOTH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCCSC:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCCSC, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCCSCH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCCSCH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCSEC:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCSEC, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCSECH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCSECH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCSIN:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCSIN, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCSINH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCSINH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCTAN:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCTAN, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCTANH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCTANH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_CEILING:
      ast = new ASTNode(ASTNode.Type.FUNCTION_CEILING, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_COS:
      ast = new ASTNode(ASTNode.Type.FUNCTION_COS, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_COSH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_COSH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_COT:
      ast = new ASTNode(ASTNode.Type.FUNCTION_COT, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_COTH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_COTH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_CSC:
      ast = new ASTNode(ASTNode.Type.FUNCTION_CSC, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_CSCH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_CSCH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_EXP:
      ast = new ASTNode(ASTNode.Type.FUNCTION_EXP, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_FACTORIAL:
      ast = new ASTNode(ASTNode.Type.FUNCTION_FACTORIAL, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_FLOOR:
      ast = new ASTNode(ASTNode.Type.FUNCTION_FLOOR, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_LN:
      ast = new ASTNode(ASTNode.Type.FUNCTION_LN, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_POWER:
      ast = new ASTNode(ASTNode.Type.FUNCTION_POWER, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ROOT:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ROOT, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_SEC:
      ast = new ASTNode(ASTNode.Type.FUNCTION_SEC, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_SECH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_SECH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_SIN:
      ast = new ASTNode(ASTNode.Type.FUNCTION_SIN, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_SINH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_SINH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_TAN:
      ast = new ASTNode(ASTNode.Type.FUNCTION_TAN, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_TANH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_TANH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION:
      ast = new ASTNode(ASTNode.Type.FUNCTION, parent);
      ast.setName(math.getName());
      break;
    case libsbmlConstants.AST_LAMBDA:
      ast = new ASTNode(ASTNode.Type.LAMBDA, parent);
      break;
    case libsbmlConstants.AST_LOGICAL_AND:
      ast = new ASTNode(ASTNode.Type.LOGICAL_AND, parent);
      break;
    case libsbmlConstants.AST_LOGICAL_XOR:
      ast = new ASTNode(ASTNode.Type.LOGICAL_XOR, parent);
      break;
    case libsbmlConstants.AST_LOGICAL_OR:
      ast = new ASTNode(ASTNode.Type.LOGICAL_OR, parent);
      break;
    case libsbmlConstants.AST_LOGICAL_NOT:
      ast = new ASTNode(ASTNode.Type.LOGICAL_NOT, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_PIECEWISE:
      ast = new ASTNode(ASTNode.Type.FUNCTION_PIECEWISE, parent);
      break;
    case libsbmlConstants.AST_RELATIONAL_EQ:
      ast = new ASTNode(ASTNode.Type.RELATIONAL_EQ, parent);
      break;
    case libsbmlConstants.AST_RELATIONAL_GEQ:
      ast = new ASTNode(ASTNode.Type.RELATIONAL_GEQ, parent);
      break;
    case libsbmlConstants.AST_RELATIONAL_GT:
      ast = new ASTNode(ASTNode.Type.RELATIONAL_GT, parent);
      break;
    case libsbmlConstants.AST_RELATIONAL_NEQ:
      ast = new ASTNode(ASTNode.Type.RELATIONAL_NEQ, parent);
      break;
    case libsbmlConstants.AST_RELATIONAL_LEQ:
      ast = new ASTNode(ASTNode.Type.RELATIONAL_LEQ, parent);
      break;
    case libsbmlConstants.AST_RELATIONAL_LT:
      ast = new ASTNode(ASTNode.Type.RELATIONAL_LT, parent);
      break;
    default:
      ast = new ASTNode(ASTNode.Type.UNKNOWN, parent);
      break;
    }
    for (int i = 0; i < math.getNumChildren(); i++) {
      org.sbml.libsbml.ASTNode child = math.getChild(i);
      ast.addChild(convert(child, parent));
    }
    if (math.isSetUnits()) {
      ast.setUnits(math.getUnits());
    }
    if (math.isSetStyle()) {
      ast.setStyle(math.getStyle());
    }
    ast.putUserObject(LINK_TO_LIBSBML, math);
    return ast;
  }

  /**
   * 
   * @param astnode
   * @return
   */
  public static org.sbml.libsbml.ASTNode convertASTNode(ASTNode astnode) {
    org.sbml.libsbml.ASTNode libAstNode = new org.sbml.libsbml.ASTNode();

    libAstNode.setType(convertASTNodeType(astnode.getType()));
    if (astnode.getType() == ASTNode.Type.REAL) {
      libAstNode.setValue(astnode.getReal());
    } else if (astnode.getType() == ASTNode.Type.INTEGER) {
      libAstNode.setValue(astnode.getInteger());
    } else if (astnode.getType() == ASTNode.Type.NAME
        || astnode.getType() == ASTNode.Type.FUNCTION) {
      libAstNode.setName(astnode.getName());
    } else if (astnode.getType() == ASTNode.Type.REAL_E) {
      libAstNode.setValue(astnode.getMantissa(), astnode.getExponent());
    }

    if (astnode.isSetClassName()) {
      libAstNode.setClassName(astnode.getClassName());
    }
    if (astnode.isSetId()) {
      libAstNode.setId(astnode.getId());
    }
    if (astnode.isSetName()) {
      if (!astnode.isOperator() && !astnode.isNumber()) {
        libAstNode.setName(astnode.getName());
      }
    }
    if (astnode.isSetStyle()) {
      libAstNode.setStyle(astnode.getStyle());
    }
    if (astnode.isSetUnits()) {
      libAstNode.setUnits(astnode.getUnits());
    }
    if (astnode.getChildCount() > 0) {
      for (ASTNode child : astnode.getListOfNodes()) {
        libAstNode.addChild(convertASTNode(child));
      }
    }
    astnode.putUserObject(LINK_TO_LIBSBML, libAstNode);

    return libAstNode;
  }

  /**
   * 
   * @param type
   * @return
   */
  public static int convertASTNodeType(Type type) {
    switch (type) {
    case REAL:
      return libsbmlConstants.AST_REAL;
    case INTEGER:
      return libsbmlConstants.AST_INTEGER;
    case FUNCTION_LOG:
      return libsbmlConstants.AST_FUNCTION_LOG;
    case POWER:
      return libsbmlConstants.AST_POWER;
    case PLUS:
      return libsbmlConstants.AST_PLUS;
    case MINUS:
      return libsbmlConstants.AST_MINUS;
    case TIMES:
      return libsbmlConstants.AST_TIMES;
    case DIVIDE:
      return libsbmlConstants.AST_DIVIDE;
    case RATIONAL:
      return libsbmlConstants.AST_RATIONAL;
    case FUNCTION_DELAY:
      return libsbmlConstants.AST_FUNCTION_DELAY;
    case NAME:
      return libsbmlConstants.AST_NAME;
    case NAME_AVOGADRO:
      return libsbmlConstants.AST_NAME_AVOGADRO;
    case NAME_TIME:
      return libsbmlConstants.AST_NAME_TIME;
    case CONSTANT_PI:
      return libsbmlConstants.AST_CONSTANT_PI;
    case CONSTANT_E:
      return libsbmlConstants.AST_CONSTANT_E;
    case CONSTANT_TRUE:
      return libsbmlConstants.AST_CONSTANT_TRUE;
    case CONSTANT_FALSE:
      return libsbmlConstants.AST_CONSTANT_FALSE;
    case REAL_E:
      return libsbmlConstants.AST_REAL_E;
    case FUNCTION_ABS:
      return libsbmlConstants.AST_FUNCTION_ABS;
    case FUNCTION_ARCCOS:
      return libsbmlConstants.AST_FUNCTION_ARCCOS;
    case FUNCTION_ARCCOSH:
      return libsbmlConstants.AST_FUNCTION_ARCCOSH;
    case FUNCTION_ARCCOT:
      return libsbmlConstants.AST_FUNCTION_ARCCOT;
    case FUNCTION_ARCCOTH:
      return libsbmlConstants.AST_FUNCTION_ARCCOTH;
    case FUNCTION_ARCCSC:
      return libsbmlConstants.AST_FUNCTION_ARCCSC;
    case FUNCTION_ARCCSCH:
      return libsbmlConstants.AST_FUNCTION_ARCCSCH;
    case FUNCTION_ARCSEC:
      return libsbmlConstants.AST_FUNCTION_ARCSEC;
    case FUNCTION_ARCSECH:
      return libsbmlConstants.AST_FUNCTION_ARCSECH;
    case FUNCTION_ARCSIN:
      return libsbmlConstants.AST_FUNCTION_ARCSIN;
    case FUNCTION_ARCSINH:
      return libsbmlConstants.AST_FUNCTION_ARCSINH;
    case FUNCTION_ARCTAN:
      return libsbmlConstants.AST_FUNCTION_ARCTAN;
    case FUNCTION_ARCTANH:
      return libsbmlConstants.AST_FUNCTION_ARCTANH;
    case FUNCTION_CEILING:
      return libsbmlConstants.AST_FUNCTION_CEILING;
    case FUNCTION_COS:
      return libsbmlConstants.AST_FUNCTION_COS;
    case FUNCTION_COSH:
      return libsbmlConstants.AST_FUNCTION_COSH;
    case FUNCTION_COT:
      return libsbmlConstants.AST_FUNCTION_COT;
    case FUNCTION_COTH:
      return libsbmlConstants.AST_FUNCTION_COTH;
    case FUNCTION_CSC:
      return libsbmlConstants.AST_FUNCTION_CSC;
    case FUNCTION_CSCH:
      return libsbmlConstants.AST_FUNCTION_CSCH;
    case FUNCTION_EXP:
      return libsbmlConstants.AST_FUNCTION_EXP;
    case FUNCTION_FACTORIAL:
      return libsbmlConstants.AST_FUNCTION_FACTORIAL;
    case FUNCTION_FLOOR:
      return libsbmlConstants.AST_FUNCTION_FLOOR;
    case FUNCTION_LN:
      return libsbmlConstants.AST_FUNCTION_LN;
    case FUNCTION_POWER:
      return libsbmlConstants.AST_FUNCTION_POWER;
    case FUNCTION_ROOT:
      return libsbmlConstants.AST_FUNCTION_ROOT;
    case FUNCTION_SEC:
      return libsbmlConstants.AST_FUNCTION_SEC;
    case FUNCTION_SECH:
      return libsbmlConstants.AST_FUNCTION_SECH;
    case FUNCTION_SIN:
      return libsbmlConstants.AST_FUNCTION_SIN;
    case FUNCTION_SINH:
      return libsbmlConstants.AST_FUNCTION_SINH;
    case FUNCTION_TAN:
      return libsbmlConstants.AST_FUNCTION_TAN;
    case FUNCTION_TANH:
      return libsbmlConstants.AST_FUNCTION_TANH;
    case FUNCTION:
      return libsbmlConstants.AST_FUNCTION;
    case LAMBDA:
      return libsbmlConstants.AST_LAMBDA;
    case LOGICAL_AND:
      return libsbmlConstants.AST_LOGICAL_AND;
    case LOGICAL_XOR:
      return libsbmlConstants.AST_LOGICAL_XOR;
    case LOGICAL_OR:
      return libsbmlConstants.AST_LOGICAL_OR;
    case LOGICAL_NOT:
      return libsbmlConstants.AST_LOGICAL_NOT;
    case FUNCTION_PIECEWISE:
      return libsbmlConstants.AST_FUNCTION_PIECEWISE;
    case RELATIONAL_EQ:
      return libsbmlConstants.AST_RELATIONAL_EQ;
    case RELATIONAL_GEQ:
      return libsbmlConstants.AST_RELATIONAL_GEQ;
    case RELATIONAL_GT:
      return libsbmlConstants.AST_RELATIONAL_GT;
    case RELATIONAL_NEQ:
      return libsbmlConstants.AST_RELATIONAL_NEQ;
    case RELATIONAL_LEQ:
      return libsbmlConstants.AST_RELATIONAL_LEQ;
    case RELATIONAL_LT:
      return libsbmlConstants.AST_RELATIONAL_LT;
    default:
      return libsbmlConstants.AST_UNKNOWN;
    }
  }

  /**
   * 
   * @param compartment
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.Compartment convertCompartment(
    Compartment compartment) throws XMLStreamException {
    org.sbml.libsbml.Compartment c = (org.sbml.libsbml.Compartment) compartment.getUserObject(LINK_TO_LIBSBML);
    if (c == null) {
      c = new org.sbml.libsbml.Compartment(compartment.getLevel(), compartment.getVersion());
      compartment.putUserObject(LINK_TO_LIBSBML, c);
    }
    transferNamedSBaseProperties(compartment, c);
    if (compartment.isSetCompartmentType()) {
      c.setCompartmentType(compartment.getCompartmentType());
    }
    if (compartment.isSetOutside()) {
      c.setOutside(compartment.getOutside());
    }
    if (compartment.isSetSize()) {
      c.setSize(compartment.getSize());
    }
    if (compartment.isSetUnits()) {
      c.setUnits(compartment.getUnits());
    }
    if (compartment.isSetConstant()) {
      c.setConstant(compartment.getConstant());
    }
    if (compartment.isSetSpatialDimensions()) {
      c.setSpatialDimensions(compartment.getSpatialDimensions());
    }
    return c;
  }

  /**
   * 
   * @param compartmentType
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.CompartmentType convertCompartmentType(
    CompartmentType compartmentType) throws XMLStreamException {
    org.sbml.libsbml.CompartmentType ct = (org.sbml.libsbml.CompartmentType) compartmentType.getUserObject(LINK_TO_LIBSBML);
    if (ct == null) {
      ct = new org.sbml.libsbml.CompartmentType(compartmentType.getLevel(), compartmentType.getVersion());
      compartmentType.putUserObject(LINK_TO_LIBSBML, ct);
    }
    transferNamedSBaseProperties(compartmentType, ct);
    return ct;
  }

  /**
   * 
   * @param constraint
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.Constraint convertConstraint(Constraint constraint) throws XMLStreamException {
    org.sbml.libsbml.Constraint c = (org.sbml.libsbml.Constraint) constraint.getUserObject(LINK_TO_LIBSBML);
    if (c == null) {
      c = new org.sbml.libsbml.Constraint(constraint.getLevel(), constraint.getVersion());
      constraint.putUserObject(LINK_TO_LIBSBML, c);
    }
    transferMathContainerProperties(constraint, c);
    if (constraint.isSetMessage()) {
      c.setMessage(new org.sbml.libsbml.XMLNode(constraint.getMessageString()));
      link(constraint.getMessage(), c.getMessage());
    }
    return c;
  }

  /**
   * 
   * @param term
   * @return a new libSBMl-CVTerm with the attributes of the incoming JSBML-CVTerm t
   */
  public static org.sbml.libsbml.CVTerm convertCVTerm(CVTerm term) {
    org.sbml.libsbml.CVTerm libCVt = (org.sbml.libsbml.CVTerm) term.getUserObject(LINK_TO_LIBSBML);
    if (libCVt == null) {
      libCVt = new org.sbml.libsbml.CVTerm();
      term.putUserObject(LINK_TO_LIBSBML, libCVt);
    }
    if (term.isSetQualifierType()) {
      libCVt.setQualifierType(convertCVTermQualifierType(term.getQualifierType()));
      if (term.isSetModelQualifierType()) {
        libCVt.setModelQualifierType(convertCVTermQualifier(term.getModelQualifierType()));
      } else if (term.isSetBiologicalQualifierType()) {
        libCVt.setBiologicalQualifierType(convertCVTermQualifier(term.getBiologicalQualifierType()));
      }
    }
    if (term.getResourceCount() > 0) {
      for (String resource : term.getResources()) {
        libCVt.addResource(resource);
      }
    }
    return libCVt;
  }

  /**
   * 
   * @param term
   * @return
   */
  public static CVTerm convertCVTerm(org.sbml.libsbml.CVTerm term) {
    // TODO: Extract switch in separate method
    org.sbml.libsbml.CVTerm libCVt = term;
    CVTerm cvTerm = new CVTerm();
    switch (libCVt.getQualifierType()) {
    case libsbmlConstants.MODEL_QUALIFIER:
      cvTerm.setQualifierType(CVTerm.Type.MODEL_QUALIFIER);
      switch (libCVt.getModelQualifierType()) {
      case libsbmlConstants.BQM_IS:
        cvTerm.setModelQualifierType(Qualifier.BQM_IS);
        break;
      case libsbmlConstants.BQM_IS_DERIVED_FROM:
        cvTerm.setModelQualifierType(Qualifier.BQM_IS_DERIVED_FROM);
        break;
      case libsbmlConstants.BQM_IS_DESCRIBED_BY:
        cvTerm.setModelQualifierType(Qualifier.BQM_IS_DESCRIBED_BY);
        break;
      case libsbmlConstants.BQM_UNKNOWN:
        cvTerm.setModelQualifierType(Qualifier.BQM_UNKNOWN);
        break;
      default:
        cvTerm.setModelQualifierType(Qualifier.BQM_UNKNOWN);
        break;
      }
      break;
    case libsbmlConstants.BIOLOGICAL_QUALIFIER:
      cvTerm.setQualifierType(CVTerm.Type.BIOLOGICAL_QUALIFIER);
      switch (libCVt.getBiologicalQualifierType()) {
      case libsbmlConstants.BQB_ENCODES:
        cvTerm.setBiologicalQualifierType(Qualifier.BQB_ENCODES);
        break;
      case libsbmlConstants.BQB_HAS_PART:
        cvTerm.setBiologicalQualifierType(Qualifier.BQB_HAS_PART);
        break;
      case libsbmlConstants.BQB_HAS_VERSION:
        cvTerm.setBiologicalQualifierType(Qualifier.BQB_HAS_VERSION);
        break;
      case libsbmlConstants.BQB_IS:
        cvTerm.setBiologicalQualifierType(Qualifier.BQB_IS);
        break;
      case libsbmlConstants.BQB_IS_DESCRIBED_BY:
        cvTerm.setBiologicalQualifierType(Qualifier.BQB_IS_DESCRIBED_BY);
        break;
      case libsbmlConstants.BQB_IS_ENCODED_BY:
        cvTerm.setBiologicalQualifierType(Qualifier.BQB_IS_ENCODED_BY);
        break;
      case libsbmlConstants.BQB_HAS_PROPERTY:
        cvTerm.setBiologicalQualifierType(Qualifier.BQB_HAS_PROPERTY);
        break;
      case libsbmlConstants.BQB_IS_HOMOLOG_TO:
        cvTerm.setBiologicalQualifierType(Qualifier.BQB_IS_HOMOLOG_TO);
        break;
      case libsbmlConstants.BQB_IS_PART_OF:
        cvTerm.setBiologicalQualifierType(Qualifier.BQB_IS_PART_OF);
        break;
      case libsbmlConstants.BQB_IS_PROPERTY_OF:
        cvTerm.setBiologicalQualifierType(Qualifier.BQB_IS_PROPERTY_OF);
        break;
      case libsbmlConstants.BQB_IS_VERSION_OF:
        cvTerm.setBiologicalQualifierType(Qualifier.BQB_IS_VERSION_OF);
        break;
      case libsbmlConstants.BQB_OCCURS_IN:
        cvTerm.setBiologicalQualifierType(Qualifier.BQB_OCCURS_IN);
        break;
      case libsbmlConstants.BQB_UNKNOWN:
        cvTerm.setBiologicalQualifierType(Qualifier.BQB_UNKNOWN);
        break;
      default:
        cvTerm.setBiologicalQualifierType(Qualifier.BQB_UNKNOWN);
        break;
      }
      break;
    default:
      break;
    }
    for (int j = 0; j < libCVt.getNumResources(); j++) {
      cvTerm.addResourceURI(libCVt.getResourceURI(j));
    }
    cvTerm.putUserObject(LINK_TO_LIBSBML, term);
    return cvTerm;
  }

  /**
   * 
   * @param qualifier
   * @return
   */
  public static int convertCVTermQualifier(CVTerm.Qualifier qualifier) {
    switch (qualifier) {
    case BQM_IS:
      return libsbmlConstants.BQM_IS;
    case BQM_IS_DESCRIBED_BY:
      return libsbmlConstants.BQM_IS_DESCRIBED_BY;
    case BQM_UNKNOWN:
      return libsbmlConstants.BQM_UNKNOWN;
    case BQB_ENCODES:
      return libsbmlConstants.BQB_ENCODES;
    case BQB_HAS_PART:
      return libsbmlConstants.BQB_HAS_PART;
    case BQB_HAS_VERSION:
      return libsbmlConstants.BQB_HAS_VERSION;
    case BQB_IS:
      return libsbmlConstants.BQB_IS;
    case BQB_IS_DESCRIBED_BY:
      return libsbmlConstants.BQB_IS_DESCRIBED_BY;
    case BQB_IS_ENCODED_BY:
      return libsbmlConstants.BQB_IS_ENCODED_BY;
    case BQB_IS_HOMOLOG_TO:
      return libsbmlConstants.BQB_IS_HOMOLOG_TO;
    case BQB_IS_PART_OF:
      return libsbmlConstants.BQB_IS_PART_OF;
    case BQB_IS_VERSION_OF:
      return libsbmlConstants.BQB_IS_VERSION_OF;
    case BQB_OCCURS_IN:
      return libsbmlConstants.BQB_OCCURS_IN;
    case BQB_UNKNOWN:
      return libsbmlConstants.BQB_UNKNOWN;
    default:
      return -1;
    }
  }

  /**
   * 
   * @param type
   * @return
   */
  public static int convertCVTermQualifierType(CVTerm.Type type) {
    switch (type) {
    case MODEL_QUALIFIER:
      return libsbmlConstants.MODEL_QUALIFIER;
    case BIOLOGICAL_QUALIFIER:
      return libsbmlConstants.BIOLOGICAL_QUALIFIER;
    default:
      return -1;
    }
  }

  /**
   * 
   * @param createdDate
   * @return a libSBML Date-Object with the same attributes as the incoming parameter createdDate
   */
  public static org.sbml.libsbml.Date convertDate(Date createdDate) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(createdDate);
    return new org.sbml.libsbml.Date(calendar.get(Calendar.YEAR),
      calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
      calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),
      calendar.get(Calendar.SECOND),
      (int) Math.signum(calendar.getTimeZone().getRawOffset()),
      calendar.getTimeZone().getRawOffset() / 3600000,
      calendar.getTimeZone().getRawOffset() / 60000);
  }

  /**
   * 
   * @param date
   * @return
   */
  public static Date convertDate(org.sbml.libsbml.Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeZone(TimeZone.getTimeZone(TimeZone.getAvailableIDs((int) (date.getSignOffset() * date.getMinutesOffset() * 60000l))[0]));
    calendar.set((int) date.getYear(), (int) date.getMonth(), (int) date.getDay(), (int) date.getHour(), (int) date.getMinute(), (int) date.getSecond());
    return calendar.getTime();
  }

  /**
   * 
   * @param delay
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.Delay convertDelay(Delay delay) throws XMLStreamException {
    org.sbml.libsbml.Delay d = (org.sbml.libsbml.Delay) delay.getUserObject(LINK_TO_LIBSBML);
    if (d == null) {
      d = new org.sbml.libsbml.Delay(delay.getLevel(), delay.getVersion());
      delay.putUserObject(LINK_TO_LIBSBML, d);
    }
    transferMathContainerProperties(delay, d);
    return d;
  }

  /**
   * 
   * @param event
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.Event convertEvent(Event event) throws XMLStreamException {
    org.sbml.libsbml.Event e = (org.sbml.libsbml.Event) event.getUserObject(LINK_TO_LIBSBML);
    if (e == null) {
      e = new org.sbml.libsbml.Event(event.getLevel(), event.getVersion());
      event.putUserObject(LINK_TO_LIBSBML, e);
    }
    transferNamedSBaseProperties(event, e);
    if (event.isSetDelay()) {
      event.getDelay().putUserObject(LINK_TO_LIBSBML, e.createDelay());
      convertDelay(event.getDelay());
    }
    if (event.isSetPriority()) {
      event.getPriority().putUserObject(LINK_TO_LIBSBML, e.createPriority());
      convertPriority(event.getPriority());
    }
    if (event.isSetListOfEventAssignments()) {
      for (EventAssignment ea : event.getListOfEventAssignments()) {
        ea.putUserObject(LINK_TO_LIBSBML, e.createEventAssignment());
        convertEventAssignment(ea);
      }
      event.getListOfEventAssignments().putUserObject(LINK_TO_LIBSBML, e.getListOfEventAssignments());
    }
    if (event.isSetTimeUnits()) {
      e.setTimeUnits(event.getTimeUnits());
    }
    if (event.isSetTrigger()) {
      event.getTrigger().putUserObject(LINK_TO_LIBSBML, e.createTrigger());
      e.setTrigger(convertTrigger(event.getTrigger()));
    }
    if (event.isSetUseValuesFromTriggerTime()) {
      e.setUseValuesFromTriggerTime(event.getUseValuesFromTriggerTime());
    }
    return e;
  }

  /**
   * 
   * @param eventAssignment
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.EventAssignment convertEventAssignment(
    EventAssignment eventAssignment) throws XMLStreamException {
    org.sbml.libsbml.EventAssignment ea = (org.sbml.libsbml.EventAssignment) eventAssignment.getUserObject(LINK_TO_LIBSBML);
    if (ea == null) {
      ea = new org.sbml.libsbml.EventAssignment(eventAssignment.getLevel(), eventAssignment.getVersion());
      eventAssignment.putUserObject(LINK_TO_LIBSBML, ea);
    }
    transferMathContainerProperties(eventAssignment, ea);
    if (eventAssignment.isSetVariable()) {
      ea.setVariable(eventAssignment.getVariable());
    }
    return ea;
  }

  /**
   * 
   * @param functionDefinition
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.FunctionDefinition convertFunctionDefinition(
    FunctionDefinition functionDefinition) throws XMLStreamException {
    org.sbml.libsbml.FunctionDefinition fd = (org.sbml.libsbml.FunctionDefinition) functionDefinition.getUserObject(LINK_TO_LIBSBML);
    if (fd == null) {
      fd = new org.sbml.libsbml.FunctionDefinition(functionDefinition.getLevel(), functionDefinition.getVersion());
      functionDefinition.putUserObject(LINK_TO_LIBSBML, fd);
    }
    transferMathContainerProperties(functionDefinition, fd);
    return fd;
  }

  /**
   * 
   * @param history
   * @return
   */
  public static ModelHistory convertHistory(History history) {
    org.sbml.libsbml.ModelHistory mo = (ModelHistory) history.getUserObject(LINK_TO_LIBSBML);
    if (mo == null) {
      mo = new org.sbml.libsbml.ModelHistory();
      history.putUserObject(LINK_TO_LIBSBML, mo);
    }
    if (history.isSetCreatedDate()) {
      mo.setCreatedDate(convertDate(history.getCreatedDate()));
    }
    if (history.isSetListOfModification()) {
      for (Date modification : history.getListOfModifiedDates()) {
        mo.addModifiedDate(convertDate(modification));
      }
    }
    if (history.isSetModifiedDate()) {
      mo.setModifiedDate(convertDate(history.getModifiedDate()));
    }
    if (history.isSetListOfCreators()) {
      for (long i = mo.getNumCreators() - 1; i >= 0; i--) {
        mo.getListCreators().remove(i);
      }
      for (Creator creator : history.getListOfCreators()) {
        mo.addCreator(convertModelCreator(creator));
        creator.putUserObject(LINK_TO_LIBSBML, mo.getCreator(mo.getNumCreators() - 1));
      }
    }
    return mo;
  }

  /**
   * 
   * @param libHist
   * @return
   */
  public static History convertHistory(org.sbml.libsbml.ModelHistory libHist) {
    int i;
    History history = new History();
    for (i = 0; i < libHist.getNumCreators(); i++) {
      Creator creator = new Creator();
      org.sbml.libsbml.ModelCreator libCreator = libHist.getCreator(i);
      creator.setGivenName(libCreator.getGivenName());
      creator.setFamilyName(libCreator.getFamilyName());
      creator.setEmail(libCreator.getEmail());
      creator.setOrganization(libCreator.getOrganization());
      creator.putUserObject(LINK_TO_LIBSBML, libCreator);
      history.addCreator(creator);
    }
    if (libHist.isSetCreatedDate()) {
      history.setCreatedDate(convertDate(libHist.getCreatedDate()));
    }
    if (libHist.isSetModifiedDate()) {
      history.setModifiedDate(convertDate(libHist.getModifiedDate()));
    }
    for (i = 0; i < libHist.getNumModifiedDates(); i++) {
      history.addModifiedDate(convertDate(libHist.getModifiedDate(i)));
    }
    history.putUserObject(LINK_TO_LIBSBML, libHist);
    return history;
  }


  /**
   * 
   * @param initialAssignment
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.InitialAssignment convertInitialAssignment(
    InitialAssignment initialAssignment) throws XMLStreamException {
    org.sbml.libsbml.InitialAssignment ia = (org.sbml.libsbml.InitialAssignment) initialAssignment.getUserObject(LINK_TO_LIBSBML);
    if (ia == null) {
      ia = new org.sbml.libsbml.InitialAssignment(initialAssignment.getLevel(),
        initialAssignment.getVersion());
      initialAssignment.putUserObject(LINK_TO_LIBSBML, ia);
    }
    transferMathContainerProperties(initialAssignment, ia);
    if (initialAssignment.isSetVariable()) {
      ia.setSymbol(initialAssignment.getVariable());
    }
    return ia;
  }

  /**
   * 
   * @param kinteticLaw
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.KineticLaw convertKineticLaw(KineticLaw kinteticLaw) throws XMLStreamException {
    org.sbml.libsbml.KineticLaw k = (org.sbml.libsbml.KineticLaw) kinteticLaw.getUserObject(LINK_TO_LIBSBML);
    if (k == null) {
      k = new org.sbml.libsbml.KineticLaw(kinteticLaw.getLevel(), kinteticLaw.getVersion());
      kinteticLaw.putUserObject(LINK_TO_LIBSBML, k);
    }
    transferMathContainerProperties(kinteticLaw, k);
    if (kinteticLaw.isSetListOfLocalParameters()) {
      for (LocalParameter p : kinteticLaw.getListOfLocalParameters()) {
        if (k.getLevel() < 3) {
          p.putUserObject(LINK_TO_LIBSBML, k.createParameter());
          convertLocalParameter(p);
        } else {
          p.putUserObject(LINK_TO_LIBSBML, k.createLocalParameter());
          convertLocalParameter(p);
        }
      }
      kinteticLaw.getListOfLocalParameters().putUserObject(LINK_TO_LIBSBML,
        k.getLevel() < 3 ? k.getListOfParameters() : k.getListOfLocalParameters());
    }
    return k;
  }

  /**
   * 
   * @param parameter
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.SBase convertLocalParameter(LocalParameter parameter) throws XMLStreamException {
    org.sbml.libsbml.LocalParameter p1 = null;
    org.sbml.libsbml.Parameter p2 = null;

    Object object = parameter.getUserObject(LINK_TO_LIBSBML);
    if (object != null) {
      if (object instanceof org.sbml.libsbml.LocalParameter) {
        p1 = (org.sbml.libsbml.LocalParameter) object;
      } else {
        p2 = (org.sbml.libsbml.Parameter) object;
      }
    } else {
      p1 = new org.sbml.libsbml.LocalParameter(parameter.getLevel(), parameter.getVersion());
      parameter.putUserObject(LINK_TO_LIBSBML, p1);
    }

    transferNamedSBaseProperties(parameter, p1 != null ? p1 : p2);
    if (p1 != null) {
      if (parameter.isExplicitlySetConstant()) {
        p1.setConstant(true);
      }
      if (parameter.isSetUnits()) {
        p1.setUnits(parameter.getUnits());
      }
      if (parameter.isSetValue()) {
        p1.setValue(parameter.getValue());
      }
      return p1;
    }
    if (parameter.isExplicitlySetConstant()) {
      p2.setConstant(true);
    }
    if (parameter.isSetUnits()) {
      p2.setUnits(parameter.getUnits());
    }
    if (parameter.isSetValue()) {
      p2.setValue(parameter.getValue());
    }
    return p2;
  }

  /**
   * 
   * @param model
   * @return
   * @throws SBMLException
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.Model convertModel(Model model) throws SBMLException, XMLStreamException {
    org.sbml.libsbml.Model libModel = (org.sbml.libsbml.Model) model.getUserObject(LINK_TO_LIBSBML);
    if (libModel == null) {
      libModel = new org.sbml.libsbml.Model(model.getLevel(), model.getVersion());
      model.putUserObject(LINK_TO_LIBSBML, libModel);
    }
    transferNamedSBaseProperties(model, libModel);

    if (model.isSetListOfUnitDefinitions()) {
      for (UnitDefinition ud : model.getListOfUnitDefinitions()) {
        ud.putUserObject(LINK_TO_LIBSBML, libModel.createUnitDefinition());
        convertUnitDefinition(ud);
      }
      model.getListOfUnitDefinitions().putUserObject(LINK_TO_LIBSBML, libModel.getListOfUnitDefinitions());
    }
    if (model.isSetListOfFunctionDefinitions()) {
      for (FunctionDefinition fd : model.getListOfFunctionDefinitions()) {
        fd.putUserObject(LINK_TO_LIBSBML, libModel.createFunctionDefinition());
        convertFunctionDefinition(fd);
      }
      model.getListOfFunctionDefinitions().putUserObject(LINK_TO_LIBSBML, libModel.getListOfFunctionDefinitions());
    }
    if (model.isSetListOfCompartmentTypes()) {
      for (CompartmentType ct : model.getListOfCompartmentTypes()) {
        ct.putUserObject(LINK_TO_LIBSBML, libModel.createCompartmentType());
        convertCompartmentType(ct);
      }
      model.getListOfCompartmentTypes().putUserObject(LINK_TO_LIBSBML, libModel.getListOfCompartmentTypes());
    }
    if (model.isSetListOfSpeciesTypes()) {
      for (SpeciesType st : model.getListOfSpeciesTypes()) {
        st.putUserObject(LINK_TO_LIBSBML, libModel.createSpeciesType());
        convertSpeciesType(st);
      }
      model.getListOfSpeciesTypes().putUserObject(LINK_TO_LIBSBML, libModel.getListOfSpeciesTypes());
    }
    if (model.isSetListOfCompartments()) {
      for (Compartment c : model.getListOfCompartments()) {
        c.putUserObject(LINK_TO_LIBSBML, libModel.createCompartment());
        convertCompartment(c);
      }
      model.getListOfCompartments().putUserObject(LINK_TO_LIBSBML, libModel.getListOfCompartments());
    }
    if (model.isSetListOfSpecies()) {
      for (Species s : model.getListOfSpecies()) {
        s.putUserObject(LINK_TO_LIBSBML, libModel.createSpecies());
        libModel.addSpecies(convertSpecies(s));
      }
      model.getListOfSpecies().putUserObject(LINK_TO_LIBSBML, libModel.getListOfSpecies());
    }
    if (model.isSetListOfParameters()) {
      for (Parameter p : model.getListOfParameters()) {
        p.putUserObject(LINK_TO_LIBSBML, libModel.createParameter());
        convertParameter(p);
      }
      model.getListOfParameters().putUserObject(LINK_TO_LIBSBML, libModel.getListOfParameters());
    }
    if (model.isSetListOfConstraints()) {
      for (Constraint c : model.getListOfConstraints()) {
        c.putUserObject(LINK_TO_LIBSBML, libModel.createConstraint());
        convertConstraint(c);
      }
      model.getListOfConstraints().putUserObject(LINK_TO_LIBSBML, libModel.getListOfConstraints());
    }
    if (model.isSetListOfInitialAssignments()) {
      for (InitialAssignment ia : model.getListOfInitialAssignments()) {
        ia.putUserObject(LINK_TO_LIBSBML, libModel.createInitialAssignment());
        convertInitialAssignment(ia);
      }
      model.getListOfInitialAssignments().putUserObject(LINK_TO_LIBSBML, libModel.getListOfInitialAssignments());
    }
    if (model.isSetListOfRules()) {
      for (Rule r : model.getListOfRules()) {
        org.sbml.libsbml.Rule libRule;
        if (r.isAlgebraic()) {
          libRule = libModel.createAlgebraicRule();
        } else if (r.isAssignment()) {
          libRule = libModel.createAssignmentRule();
        } else { // if (r.isRate())
          libRule = libModel.createRateRule();
        }
        r.putUserObject(LINK_TO_LIBSBML, libRule);
        convertRule(r);
      }
      model.getListOfRules().putUserObject(LINK_TO_LIBSBML, libModel.getListOfRules());
    }
    if (model.isSetListOfReactions()) {
      for (Reaction r : model.getListOfReactions()) {
        r.putUserObject(LINK_TO_LIBSBML, libModel.createReaction());
        convertReaction(r);
      }
      model.getListOfReactions().putUserObject(LINK_TO_LIBSBML, libModel.getListOfReactions());
    }
    if (model.isSetListOfEvents()) {
      for (Event e : model.getListOfEvents()) {
        e.putUserObject(LINK_TO_LIBSBML, libModel.createEvent());
        convertEvent(e);
      }
      model.getListOfEvents().putUserObject(LINK_TO_LIBSBML, libModel.getListOfEvents());
    }

    if (model.isSetAreaUnits()) {
      libModel.setAreaUnits(model.getAreaUnits());
    }
    if (model.isSetConversionFactor()) {
      libModel.setConversionFactor(model.getConversionFactor());
    }
    if (model.isSetExtentUnits()) {
      libModel.setExtentUnits(model.getExtentUnits());
    }
    if (model.isSetLengthUnits()) {
      libModel.setLengthUnits(model.getExtentUnits());
    }
    if (model.isSetSubstanceUnits()) {
      libModel.setSubstanceUnits(model.getSubstanceUnits());
    }
    if (model.isSetTimeUnits()) {
      libModel.setTimeUnits(model.getTimeUnits());
    }
    if (model.isSetVolumeUnits()) {
      libModel.setVolumeUnits(model.getVolumeUnits());
    }

    return libModel;
  }

  /**
   * this method creates a new {@link ModelCreator}
   * and fills it with the attributes of the given jsbml-{@link Creator}
   * @param creator
   * @return
   */
  public static ModelCreator convertModelCreator(Creator creator) {
    org.sbml.libsbml.ModelCreator modelCreator = (ModelCreator) creator.getUserObject(LINK_TO_LIBSBML);
    if (modelCreator == null) {
      modelCreator = new org.sbml.libsbml.ModelCreator();
      creator.putUserObject(LINK_TO_LIBSBML, modelCreator);
    }
    if (creator.isSetEmail()) {
      modelCreator.setEmail(creator.getEmail());
    }
    if (creator.isSetFamilyName()) {
      modelCreator.setFamilyName(creator.getFamilyName());
    }
    if (creator.isSetGivenName()) {
      modelCreator.setGivenName(creator.getGivenName());
    }
    if (creator.isSetOrganization()) {
      modelCreator.setOrganization(creator.getOrganization());
    }
    return modelCreator;
  }

  /**
   * 
   * @param modifierSpeciesReference
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.ModifierSpeciesReference convertModifierSpeciesReference(
    ModifierSpeciesReference modifierSpeciesReference) throws XMLStreamException {
    org.sbml.libsbml.ModifierSpeciesReference m = (org.sbml.libsbml.ModifierSpeciesReference) modifierSpeciesReference.getUserObject(LINK_TO_LIBSBML);
    if (m == null) {
      m = new org.sbml.libsbml.ModifierSpeciesReference(modifierSpeciesReference.getLevel(), modifierSpeciesReference.getVersion());
      modifierSpeciesReference.putUserObject(LINK_TO_LIBSBML, m);
    }
    transferNamedSBaseProperties(modifierSpeciesReference, m);
    if (modifierSpeciesReference.isSetSpecies()) {
      m.setSpecies(modifierSpeciesReference.getSpecies());
    }
    return m;
  }

  /**
   * 
   * @param parameter
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.Parameter convertParameter(Parameter parameter) throws XMLStreamException {
    org.sbml.libsbml.Parameter p = (org.sbml.libsbml.Parameter) parameter.getUserObject(LINK_TO_LIBSBML);
    if (p == null) {
      p = new org.sbml.libsbml.Parameter(parameter.getLevel(), parameter.getVersion());
      parameter.putUserObject(LINK_TO_LIBSBML, p);
    }
    transferNamedSBaseProperties(parameter, p);
    p.setConstant(parameter.isConstant());
    if (parameter.isSetUnits()) {
      p.setUnits(parameter.getUnits());
    }
    if (parameter.isSetValue()) {
      p.setValue(parameter.getValue());
    }
    return p;
  }

  /**
   * 
   * @param priority
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.Priority convertPriority(Priority priority) throws XMLStreamException {
    org.sbml.libsbml.Priority p = (org.sbml.libsbml.Priority) priority.getUserObject(LINK_TO_LIBSBML);
    if (p == null) {
      p = new org.sbml.libsbml.Priority(priority.getLevel(), priority.getVersion());
      priority.putUserObject(LINK_TO_LIBSBML, p);
    }
    transferMathContainerProperties(priority, p);
    return p;
  }

  /**
   * 
   * @param reaction
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.Reaction convertReaction(Reaction reaction) throws XMLStreamException {
    org.sbml.libsbml.Reaction r = (org.sbml.libsbml.Reaction) reaction.getUserObject(LINK_TO_LIBSBML);
    if (r == null) {
      r = new org.sbml.libsbml.Reaction(reaction.getLevel(), reaction.getVersion());
      reaction.putUserObject(LINK_TO_LIBSBML, r);
    }
    transferNamedSBaseProperties(reaction, r);
    if (reaction.isSetFast()) {
      r.setFast(reaction.isFast());
    }
    if (reaction.isSetReversible()) {
      r.setReversible(reaction.getReversible());
    }
    if (reaction.isSetKineticLaw()){
      reaction.getKineticLaw().putUserObject(LINK_TO_LIBSBML, r.createKineticLaw());
      convertKineticLaw(reaction.getKineticLaw());
    }
    if (reaction.isSetCompartment()) {
      r.setCompartment(reaction.getCompartment());
    }
    if (reaction.isSetListOfReactants()) {
      for (SpeciesReference sr : reaction.getListOfReactants()) {
        sr.putUserObject(LINK_TO_LIBSBML, r.createReactant());
        convertSpeciesReference(sr);
      }
      reaction.getListOfReactants().putUserObject(LINK_TO_LIBSBML, r.getListOfReactants());
    }
    if (reaction.isSetListOfProducts()) {
      for (SpeciesReference sr : reaction.getListOfProducts()) {
        sr.putUserObject(LINK_TO_LIBSBML, r.createProduct());
        convertSpeciesReference(sr);
      }
      reaction.getListOfProducts().putUserObject(LINK_TO_LIBSBML, r.getListOfProducts());
    }
    if (reaction.isSetListOfModifiers()) {
      for (ModifierSpeciesReference mr : reaction.getListOfModifiers()) {
        mr.putUserObject(LINK_TO_LIBSBML, r.createModifier());
        convertModifierSpeciesReference(mr);
      }
      reaction.getListOfModifiers().putUserObject(LINK_TO_LIBSBML, r.getListOfModifiers());
    }
    return r;
  }

  /**
   * 
   * @param rule
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.Rule convertRule(Rule rule) throws XMLStreamException {
    org.sbml.libsbml.Rule r = (org.sbml.libsbml.Rule) rule.getUserObject(LINK_TO_LIBSBML);
    if (rule.isAlgebraic()) {
      if (r == null) {
        r = new org.sbml.libsbml.AlgebraicRule(rule.getLevel(), rule.getVersion());
      }
    } else {
      if (rule.isAssignment()) {
        if (r == null) {
          r = new org.sbml.libsbml.AssignmentRule(rule.getLevel(), rule.getVersion());
        }
        if (((AssignmentRule) rule).isSetVariable()) {
          r.setVariable(((AssignmentRule) rule).getVariable());
        }
      } else {
        if (r == null) {
          r = new org.sbml.libsbml.RateRule(rule.getLevel(), rule.getVersion());
        }
        if (((RateRule) rule).isSetVariable()) {
          r.setVariable(((RateRule) rule).getVariable());
        }
      }
      ExplicitRule er = (ExplicitRule) rule;
      if (er.isSetUnits()) {
        r.setUnits(er.getUnits());
      }
    }
    transferMathContainerProperties(rule, r);
    rule.putUserObject(LINK_TO_LIBSBML, r);
    return r;
  }

  /**
   * 
   * @param doc
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.SBMLDocument convertSBMLDocument(SBMLDocument doc) throws XMLStreamException {
    org.sbml.libsbml.SBMLDocument libDoc = (org.sbml.libsbml.SBMLDocument) doc.getUserObject(LINK_TO_LIBSBML);
    if (libDoc == null) {
      libDoc = new org.sbml.libsbml.SBMLDocument(doc.getLevel(), doc.getVersion());
      doc.putUserObject(LINK_TO_LIBSBML, libDoc);
      doc.addTreeNodeChangeListener(new LibSBMLChangeListener(libDoc));
    }
    transferSBaseProperties(doc, libDoc);
    if (doc.isSetModel()) {
      doc.getModel().putUserObject(LINK_TO_LIBSBML, libDoc.createModel());
      convertModel(doc.getModel());
    }
    //    org.sbml.libsbml.XMLNamespaces libNamespaces = libDoc.getNamespaces();
    //    for (String namespace : doc.getNamespaces()) {
    //      libNamespaces.add(namespace);
    //    }
    String keyWord = ":required";
    for (Map.Entry<String, String> entry : doc.getSBMLDocumentAttributes().entrySet()) {
      String key = entry.getKey();
      if (key.endsWith(keyWord)) {
        libDoc.setPackageRequired(
          key.substring(0, key.length() - keyWord.length() - 1),
          Boolean.parseBoolean(entry.getValue()));
      }
    }
    return libDoc;
  }

  /**
   * 
   * @param species
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.Species convertSpecies(Species species) throws XMLStreamException {
    org.sbml.libsbml.Species s = (org.sbml.libsbml.Species) species.getUserObject(LINK_TO_LIBSBML);
    if (s == null) {
      s = new org.sbml.libsbml.Species(species.getLevel(), species.getVersion());
      species.putUserObject(LINK_TO_LIBSBML, s);
    }
    transferNamedSBaseProperties(species, s);
    if (species.isSetBoundaryCondition()) {
      s.setBoundaryCondition(species.getBoundaryCondition());
    }
    if (species.isSetCharge()) {
      s.setCharge(species.getCharge());
    }
    if (species.isSetCompartment()) {
      s.setCompartment(species.getCompartment());
    }
    if (species.isSetConstant()) {
      s.setConstant(species.getConstant());
    }
    if (species.isSetHasOnlySubstanceUnits()) {
      s.setHasOnlySubstanceUnits(species.hasOnlySubstanceUnits());
    }
    if (species.isSetInitialAmount()) {
      s.setInitialAmount(species.getInitialAmount());
    } else if (species.isSetInitialConcentration()) {
      s.setInitialConcentration(species.getInitialConcentration());
    }
    if (species.isSetSpeciesType()) {
      s.setSpeciesType(species.getSpeciesType());
    }
    if (species.isSetSubstanceUnits()) {
      species.getSubstanceUnits();
    }
    return s;
  }

  /**
   * 
   * @param speciesReference
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.SpeciesReference convertSpeciesReference(SpeciesReference speciesReference) throws XMLStreamException {
    org.sbml.libsbml.SpeciesReference libSpeciesReference = (org.sbml.libsbml.SpeciesReference) speciesReference.getUserObject(LINK_TO_LIBSBML);
    if (libSpeciesReference == null) {
      libSpeciesReference = new org.sbml.libsbml.SpeciesReference(speciesReference.getLevel(), speciesReference.getVersion());
      speciesReference.putUserObject(LINK_TO_LIBSBML, libSpeciesReference);
    }
    transferSpeciesReferenceProperties(speciesReference, libSpeciesReference);
    return libSpeciesReference;
  }


  /**
   * 
   * @param speciesType
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.SpeciesType convertSpeciesType(SpeciesType speciesType) throws XMLStreamException {
    org.sbml.libsbml.SpeciesType st = (org.sbml.libsbml.SpeciesType) speciesType.getUserObject(LINK_TO_LIBSBML);
    if (st == null) {
      st = new org.sbml.libsbml.SpeciesType(speciesType.getLevel(), speciesType.getVersion());
      speciesType.putUserObject(LINK_TO_LIBSBML, st);
    }
    transferNamedSBaseProperties(speciesType, st);
    return st;
  }

  /**
   * 
   * @param sbase
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.StoichiometryMath convertStoichiometryMath(StoichiometryMath sbase) throws XMLStreamException {
    org.sbml.libsbml.StoichiometryMath libSBase = (org.sbml.libsbml.StoichiometryMath) sbase.getUserObject(LINK_TO_LIBSBML);
    if (libSBase == null) {
      libSBase = new org.sbml.libsbml.StoichiometryMath(sbase.getLevel(), sbase.getVersion());
      sbase.putUserObject(LINK_TO_LIBSBML, libSBase);
    }
    transferMathContainerProperties(sbase, libSBase);
    return libSBase;
  }

  /**
   * 
   * @param trigger
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.Trigger convertTrigger(Trigger trigger) throws XMLStreamException {
    org.sbml.libsbml.Trigger t = (org.sbml.libsbml.Trigger) trigger.getUserObject(LINK_TO_LIBSBML);
    if (t == null) {
      t = new org.sbml.libsbml.Trigger(trigger.getLevel(), trigger.getVersion());
      trigger.putUserObject(LINK_TO_LIBSBML, t);
    }
    transferMathContainerProperties(trigger, t);
    if (trigger.isSetInitialValue()) {
      t.setInitialValue(trigger.getInitialValue());
    }
    if (trigger.isSetPersistent()) {
      t.setPersistent(trigger.isPersistent());
    }
    return t;
  }

  /**
   * 
   * @param unit
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.Unit convertUnit(Unit unit) throws XMLStreamException {
    org.sbml.libsbml.Unit u = (org.sbml.libsbml.Unit) unit.getUserObject(LINK_TO_LIBSBML);
    if (u == null) {
      u = new org.sbml.libsbml.Unit(unit.getLevel(), unit.getVersion());
      unit.putUserObject(LINK_TO_LIBSBML, u);
    }
    transferSBaseProperties(unit, u);
    if (unit.isSetKind()) {
      u.setKind(convertUnitKind(unit.getKind()));
    }
    if (unit.isSetExponent()) {
      u.setExponent(unit.getExponent());
    }
    if (unit.isSetMultiplier()) {
      u.setMultiplier(unit.getMultiplier());
    }
    if (unit.isSetOffset()) {
      u.setOffset(unit.getOffset());
    }
    if (unit.isSetScale()) {
      u.setScale(unit.getScale());
    }
    return u;
  }

  /**
   * 
   * @param unitDefinition
   * @return
   * @throws XMLStreamException
   */
  public static org.sbml.libsbml.UnitDefinition convertUnitDefinition(
    UnitDefinition unitDefinition) throws XMLStreamException {
    org.sbml.libsbml.UnitDefinition ud = (org.sbml.libsbml.UnitDefinition) unitDefinition.getUserObject(LINK_TO_LIBSBML);
    if (ud == null) {
      ud = new org.sbml.libsbml.UnitDefinition(unitDefinition.getLevel(), unitDefinition.getVersion());
      unitDefinition.putUserObject(LINK_TO_LIBSBML, ud);
    }
    transferNamedSBaseProperties(unitDefinition, ud);
    if (unitDefinition.isSetListOfUnits()) {
      for (Unit u : unitDefinition.getListOfUnits()) {
        u.putUserObject(LINK_TO_LIBSBML, ud.createUnit());
        convertUnit(u);
      }
      unitDefinition.getListOfUnits().putUserObject(LINK_TO_LIBSBML, ud.getListOfUnits());
    }
    return ud;
  }

  /**
   * 
   * @param kind
   * @return
   */
  public static Unit.Kind convertUnitKind(int kind) {
    switch (kind) {
    case libsbmlConstants.UNIT_KIND_AMPERE:
      return Unit.Kind.AMPERE;
    case libsbmlConstants.UNIT_KIND_AVOGADRO:
      return Unit.Kind.AVOGADRO;
    case libsbmlConstants.UNIT_KIND_BECQUEREL:
      return Unit.Kind.BECQUEREL;
    case libsbmlConstants.UNIT_KIND_CANDELA:
      return Unit.Kind.CANDELA;
    case libsbmlConstants.UNIT_KIND_CELSIUS:
      return Unit.Kind.CELSIUS;
    case libsbmlConstants.UNIT_KIND_COULOMB:
      return Unit.Kind.COULOMB;
    case libsbmlConstants.UNIT_KIND_DIMENSIONLESS:
      return Unit.Kind.DIMENSIONLESS;
    case libsbmlConstants.UNIT_KIND_FARAD:
      return Unit.Kind.FARAD;
    case libsbmlConstants.UNIT_KIND_GRAM:
      return Unit.Kind.GRAM;
    case libsbmlConstants.UNIT_KIND_GRAY:
      return Unit.Kind.GRAY;
    case libsbmlConstants.UNIT_KIND_HENRY:
      return Unit.Kind.HENRY;
    case libsbmlConstants.UNIT_KIND_HERTZ:
      return Unit.Kind.HERTZ;
    case libsbmlConstants.UNIT_KIND_INVALID:
      return Unit.Kind.INVALID;
    case libsbmlConstants.UNIT_KIND_ITEM:
      return Unit.Kind.ITEM;
    case libsbmlConstants.UNIT_KIND_JOULE:
      return Unit.Kind.JOULE;
    case libsbmlConstants.UNIT_KIND_KATAL:
      return Unit.Kind.KATAL;
    case libsbmlConstants.UNIT_KIND_KELVIN:
      return Unit.Kind.KELVIN;
    case libsbmlConstants.UNIT_KIND_KILOGRAM:
      return Unit.Kind.KILOGRAM;
    case libsbmlConstants.UNIT_KIND_LITER:
      return Unit.Kind.LITER;
    case libsbmlConstants.UNIT_KIND_LITRE:
      return Unit.Kind.LITRE;
    case libsbmlConstants.UNIT_KIND_LUMEN:
      return Unit.Kind.LUMEN;
    case libsbmlConstants.UNIT_KIND_LUX:
      return Unit.Kind.LUX;
    case libsbmlConstants.UNIT_KIND_METER:
      return Unit.Kind.METER;
    case libsbmlConstants.UNIT_KIND_METRE:
      return Unit.Kind.METRE;
    case libsbmlConstants.UNIT_KIND_MOLE:
      return Unit.Kind.MOLE;
    case libsbmlConstants.UNIT_KIND_NEWTON:
      return Unit.Kind.NEWTON;
    case libsbmlConstants.UNIT_KIND_OHM:
      return Unit.Kind.OHM;
    case libsbmlConstants.UNIT_KIND_PASCAL:
      return Unit.Kind.PASCAL;
    case libsbmlConstants.UNIT_KIND_RADIAN:
      return Unit.Kind.RADIAN;
    case libsbmlConstants.UNIT_KIND_SECOND:
      return Unit.Kind.SECOND;
    case libsbmlConstants.UNIT_KIND_SIEMENS:
      return Unit.Kind.SIEMENS;
    case libsbmlConstants.UNIT_KIND_SIEVERT:
      return Unit.Kind.SIEVERT;
    case libsbmlConstants.UNIT_KIND_STERADIAN:
      return Unit.Kind.STERADIAN;
    case libsbmlConstants.UNIT_KIND_TESLA:
      return Unit.Kind.TESLA;
    case libsbmlConstants.UNIT_KIND_VOLT:
      return Unit.Kind.VOLT;
    case libsbmlConstants.UNIT_KIND_WATT:
      return Unit.Kind.WATT;
    case libsbmlConstants.UNIT_KIND_WEBER:
      return Unit.Kind.WEBER;
    }
    return null;
  }

  /**
   * 
   * @param kind
   * @return
   */
  public static int convertUnitKind(Unit.Kind kind) {
    switch (kind) {
    case AMPERE:
      return libsbmlConstants.UNIT_KIND_AMPERE;
    case AVOGADRO:
      return libsbmlConstants.UNIT_KIND_AVOGADRO;
    case BECQUEREL:
      return libsbmlConstants.UNIT_KIND_BECQUEREL;
    case CANDELA:
      return libsbmlConstants.UNIT_KIND_CANDELA;
    case CELSIUS:
      return libsbmlConstants.UNIT_KIND_CELSIUS;
    case COULOMB:
      return libsbmlConstants.UNIT_KIND_COULOMB;
    case DIMENSIONLESS:
      return libsbmlConstants.UNIT_KIND_DIMENSIONLESS;
    case FARAD:
      return libsbmlConstants.UNIT_KIND_FARAD;
    case GRAM:
      return libsbmlConstants.UNIT_KIND_GRAM;
    case GRAY:
      return libsbmlConstants.UNIT_KIND_GRAY;
    case HENRY:
      return libsbmlConstants.UNIT_KIND_HENRY;
    case HERTZ:
      return libsbmlConstants.UNIT_KIND_HERTZ;
    case INVALID:
      return libsbmlConstants.UNIT_KIND_INVALID;
    case ITEM:
      return libsbmlConstants.UNIT_KIND_ITEM;
    case JOULE:
      return libsbmlConstants.UNIT_KIND_JOULE;
    case KATAL:
      return libsbmlConstants.UNIT_KIND_KATAL;
    case KELVIN:
      return libsbmlConstants.UNIT_KIND_KELVIN;
    case KILOGRAM:
      return libsbmlConstants.UNIT_KIND_KILOGRAM;
    case LITER:
      return libsbmlConstants.UNIT_KIND_LITER;
    case LITRE:
      return libsbmlConstants.UNIT_KIND_LITRE;
    case LUMEN:
      return libsbmlConstants.UNIT_KIND_LUMEN;
    case LUX:
      return libsbmlConstants.UNIT_KIND_LUX;
    case METER:
      return libsbmlConstants.UNIT_KIND_METER;
    case METRE:
      return libsbmlConstants.UNIT_KIND_METRE;
    case MOLE:
      return libsbmlConstants.UNIT_KIND_MOLE;
    case NEWTON:
      return libsbmlConstants.UNIT_KIND_NEWTON;
    case OHM:
      return libsbmlConstants.UNIT_KIND_OHM;
    case PASCAL:
      return libsbmlConstants.UNIT_KIND_PASCAL;
    case RADIAN:
      return libsbmlConstants.UNIT_KIND_RADIAN;
    case SECOND:
      return libsbmlConstants.UNIT_KIND_SECOND;
    case SIEMENS:
      return libsbmlConstants.UNIT_KIND_SIEMENS;
    case SIEVERT:
      return libsbmlConstants.UNIT_KIND_SIEVERT;
    case STERADIAN:
      return libsbmlConstants.UNIT_KIND_STERADIAN;
    case TESLA:
      return libsbmlConstants.UNIT_KIND_TESLA;
    case VOLT:
      return libsbmlConstants.UNIT_KIND_VOLT;
    case WATT:
      return libsbmlConstants.UNIT_KIND_WATT;
    case WEBER:
      return libsbmlConstants.UNIT_KIND_WEBER;
    default:
      return -1;
    }
  }

  /**
   * Determines whether the two ASTNode objects are equal.
   * 
   * @param math
   * @param libMath
   * @return
   */
  public static boolean equal(ASTNode math, org.sbml.libsbml.ASTNode libMath) {
    if (math == null || libMath == null) {
      return false;
    }
    boolean equal = convertASTNodeType(math.getType()) == libMath.getType();
    if (equal) {
      if (math.getType() == ASTNode.Type.REAL) {
        equal &= libMath.getReal() == math.getReal();
      } else if (math.getType() == ASTNode.Type.INTEGER) {
        equal &= libMath.getInteger() == math.getInteger();
      } else if (math.getType() == ASTNode.Type.NAME || math.getType() == ASTNode.Type.FUNCTION) {
        equal &= math.getName().equals(libMath.getName());
      } else if (math.getType() == ASTNode.Type.REAL_E) {
        equal &= libMath.getMantissa() == math.getMantissa();
        equal &= libMath.getExponent() == math.getExponent();
      }
    }

    equal &= math.isSetClassName() == libMath.isSetClass();
    if (equal && math.isSetClassName()) {
      equal &= math.getClassName().equals(libMath.getClassName());
    }
    equal &= math.isSetId() == libMath.isSetId();
    if (equal && math.isSetId()) {
      equal &= math.getId().equals(libMath.getId());
    }
    equal &= !math.isSetName() == (libMath.getName() == null
        || libMath.getName().length() == 0);
    if (equal && math.isSetName()) {
      equal &= math.getName().equals(libMath.getName());
    }
    equal &= math.isSetStyle() == libMath.isSetStyle();
    if (equal && math.isSetStyle()) {
      equal &= math.getStyle().equals(libMath.getStyle());
    }
    equal &= math.isSetUnits() == libMath.isSetUnits();
    if (equal && math.isSetUnits()) {
      equal &= math.getUnits().equals(libMath.getUnits());
    }

    equal &= math.getChildCount() == libMath.getNumChildren();
    if (equal && math.getChildCount() > 0) {
      for (int i = 0; i < math.getChildCount(); i++) {
        equal &= equal(math.getChild(i), libMath.getChild(i));
      }
    }

    return equal;
  }

  /**
   * Checks whether these two units are identical.
   * 
   * @param u
   * @param unit
   * @return
   */
  public static boolean equal(Unit u, org.sbml.libsbml.Unit unit) {
    if (u == null || unit == null) {
      return false;
    }
    boolean equal = convertUnitKind(u.getKind()) == unit.getKind();
    // Java/C interface can lead to numerical incorrectness:
    double tolerance = 1E-9d;
    equal &= u.getExponent() - unit.getExponentAsDouble() < tolerance;
    equal &= u.getMultiplier() - unit.getMultiplier() < tolerance;
    equal &= u.getScale() == unit.getScale();
    equal &= u.getOffset() == unit.getOffset();
    return equal;
  }

  /**
   * 
   * @param math
   * @param libASTNode
   */
  public static void link(ASTNode math, org.sbml.libsbml.ASTNode libASTNode) {
    math.putUserObject(LINK_TO_LIBSBML, libASTNode);
    for (int i = 0; i < math.getChildCount(); i++) {
      link(math.getChild(i), libASTNode.getChild(i));
    }
  }

  /**
   * 
   * @param message
   * @param libMessage
   */
  public static void link(XMLNode message, org.sbml.libsbml.XMLNode libMessage) {
    message.putUserObject(LINK_TO_LIBSBML, libMessage);
    for (int i = 0; i < message.getChildCount(); i++) {
      link(message.getChildAt(i), libMessage.getChild(i));
    }
  }

  /**
   * 
   * @param code
   */
  public static void logLibSBMLCode(int code) {
    switch (code) {
    case libsbmlConstants.LIBSBML_OPERATION_SUCCESS:
      break;
    case libsbmlConstants.LIBSBML_LEVEL_MISMATCH:
      logger.error("level mismatch");
      break;
    case libsbmlConstants.LIBSBML_VERSION_MISMATCH:
      logger.error("version mismatch");
      break;
    case libsbmlConstants.LIBSBML_DUPLICATE_OBJECT_ID:
      logger.error("duplicate object id");
      break;
    case libsbmlConstants.LIBSBML_OPERATION_FAILED:
      logger.error("operation failed");
      break;
    default:
      logger.error("error code: " + code);
      break;
    }
  }

  /**
   * 
   * @param mathContainer
   * @param libSBase
   */
  public static void transferMath(MathContainer mathContainer,
    org.sbml.libsbml.SBase libSBase) {
    if (mathContainer.isSetMath()) {
      org.sbml.libsbml.ASTNode libASTNode = convertASTNode(mathContainer.getMath());
      try {
        Method method = libSBase.getClass().getMethod("setMath", org.sbml.libsbml.ASTNode.class);
        Object result = method.invoke(libSBase, libASTNode);
        if (result != null
            && result.getClass() == Integer.class
            && ((Integer) result).intValue() != libsbmlConstants.LIBSBML_OPERATION_SUCCESS) {
          logger.warn("Could not set math for element " + libSBase.getClass().getSimpleName());
        } else {
          method = libSBase.getClass().getMethod("getMath");
          libASTNode = (org.sbml.libsbml.ASTNode) method.invoke(libSBase);
          link(mathContainer.getMath(), libASTNode);
        }
      } catch (Throwable t) {
        logger.warn(t.getMessage(), t);
      }
    }
  }

  /**
   * sets the math {@link ASTNode} in the libSBML object if it's set in the JSBML object
   * and calls the convert-method for the ASTNodes.
   * @param mathCont
   * @param libMathCont
   * @throws XMLStreamException
   */
  public static void transferMathContainerProperties(MathContainer mathCont, org.sbml.libsbml.SBase libMathCont) throws XMLStreamException {
    if (mathCont instanceof NamedSBase) {
      transferNamedSBaseProperties((NamedSBase) mathCont, libMathCont);
    } else {
      transferSBaseProperties(mathCont, libMathCont);
    }
    transferMath(mathCont, libMathCont);
  }

  /**
   * Sets the name and the id in the libSBML object, when it's set in the JSBML
   * object and calls the method
   * {@link #transferSBaseProperties(SBase, org.sbml.libsbml.SBase)}.
   * 
   * @param sbase
   * @param libSBase
   * @throws XMLStreamException
   */
  public static void transferNamedSBaseProperties(NamedSBase sbase, org.sbml.libsbml.SBase libSBase) throws XMLStreamException {
    transferSBaseProperties(sbase, libSBase);
    if (sbase.isSetId()) {
      libSBase.setId(sbase.getId());
    }
    if (sbase.isSetName()) {
      libSBase.setName(sbase.getName());
    }
  }

  /**
   * Copies the id and name properties from the libSBML object to the JSBML
   * object.
   * 
   * @param libSBase
   * @param sbase
   */
  public static void transferNamedSBaseProperties(org.sbml.libsbml.SBase libSBase, NamedSBase sbase) {
    transferSBaseProperties(libSBase, sbase);
    if (libSBase.isSetId()) {
      sbase.setId(libSBase.getId());
    }
    if (libSBase.isSetName()) {
      sbase.setName(libSBase.getName());
    }
  }

  /**
   * Copies the properties of the given libSBML object to the given JSBML object.
   * 
   * @param libSBase
   * @param sbase
   */
  public static void transferSBaseProperties(org.sbml.libsbml.SBase libSBase, SBase sbase) {

    // Memorize the corresponding original element for each SBase:
    sbase.putUserObject(LINK_TO_LIBSBML, libSBase);

    if (libSBase.isSetSBOTerm()) {
      sbase.setSBOTerm(libSBase.getSBOTerm());
    }
    if (libSBase.isSetNotes()) {
      try {
        // TODO: convert all UTF-8 characters appropriately.
        sbase.setNotes(libSBase.getNotesString());
      } catch (Throwable t) {
        logger.warn(t.getLocalizedMessage());
      }
    }
    if (libSBase.getAnnotation() != null) {
      for (int i = 0; i < libSBase.getNumCVTerms(); i++) {
        sbase.addCVTerm(LibSBMLUtils.convertCVTerm(libSBase.getCVTerm(i)));
      }
      if (libSBase.isSetModelHistory()) {
        sbase.setHistory(convertHistory(libSBase.getModelHistory()));
      }
      // Parse the XML annotation nodes that are non-RDF
      org.sbml.libsbml.XMLNode annotation = libSBase.getAnnotation();
      StringBuilder sb = new StringBuilder();
      boolean newLine = false;
      for (long i = 0; i < annotation.getNumChildren(); i++) {
        String annot = annotation.getChild(i).toXMLString();
        if (!annot.trim().startsWith("<rdf:")) {
          if (newLine) {
            sb.append('\n');
          }
          sb.append(annot);
          newLine = true;
        }
      }
      if (sb.toString().trim().length() > 0) {
        try {
          sbase.getAnnotation().setNonRDFAnnotation(sb.toString());
        } catch (XMLStreamException exc) {
          logger.warn(exc.getLocalizedMessage() != null ? exc.getLocalizedMessage() : exc.getMessage(), exc);
        }
      }
      sbase.getAnnotation().putUserObject(LINK_TO_LIBSBML, libSBase.getAnnotation());
    }
    if (libSBase.isSetMetaId()) {
      sbase.setMetaId(libSBase.getMetaId());
    }
  }

  /**
   * Sets metaId, SBO term, notes, and {@link Annotation} in the libSBML
   * object, if it's set in the JSBML object.
   * 
   * @param sbase
   * @param libSBase
   * @throws XMLStreamException
   */
  public static void transferSBaseProperties(SBase sbase,
    org.sbml.libsbml.SBase libSBase) throws XMLStreamException {
    if (sbase.isSetMetaId() && libSBase.isSetMetaId()
        && !sbase.getMetaId().equals(libSBase.getMetaId())) {
      libSBase.setMetaId(sbase.getMetaId());
    }
    if (sbase.isSetSBOTerm() && sbase.getSBOTerm() != libSBase.getSBOTerm()) {
      libSBase.setSBOTerm(sbase.getSBOTerm());
    }
    if (sbase.isSetNotes() &&
        !sbase.getNotesString().equals(libSBase.getNotesString())) {
      libSBase.setNotes(sbase.getNotesString());
    }
    if (sbase.isSetAnnotation()) {
      if (sbase.getAnnotation().isSetNonRDFannotation()) {
        libSBase.setAnnotation(sbase.getAnnotationString());
      }
      if (sbase.isSetHistory()) {
        if (libSBase.isSetModelHistory()) {
          libSBase.unsetModelHistory();
        }
        libSBase.setModelHistory(new ModelHistory());
        History history = sbase.getHistory();
        history.putUserObject(LINK_TO_LIBSBML, libSBase.getModelHistory());
        convertHistory(history);
      }
      if (sbase.getCVTermCount() > 0) {
        if (libSBase.getNumCVTerms() > 0) {
          libSBase.unsetCVTerms();
        }
        for (CVTerm term : sbase.getCVTerms()) {
          libSBase.addCVTerm(convertCVTerm(term));
          term.putUserObject(LINK_TO_LIBSBML, libSBase.getCVTerm(libSBase.getNumCVTerms() - 1));
        }
      }
    }
  }

  /**
   * sets the species in libSBML object if the species is set in the JSBML object
   * and calls the method {@link #transferNamedSBaseProperties(NamedSBase, org.sbml.libsbml.SBase)}.
   * 
   * @param sbase
   * @param libSBase
   * @throws XMLStreamException
   */
  public static void transferSimpleSpeciesReferenceProperties(SimpleSpeciesReference sbase, org.sbml.libsbml.SimpleSpeciesReference libSBase) throws XMLStreamException {
    transferNamedSBaseProperties(sbase, libSBase);
    if (sbase.isSetSpecies()
        && (!libSBase.isSetSpecies() || !libSBase.getSpecies().equals(sbase.getSpecies()))) {
      libSBase.setSpecies(sbase.getSpecies());
    }
  }

  /**
   * 
   * @param sbase
   * @param libSBase
   * @throws XMLStreamException
   */
  public static void transferSpeciesReferenceProperties(SpeciesReference sbase, org.sbml.libsbml.SpeciesReference libSBase) throws XMLStreamException {
    transferSimpleSpeciesReferenceProperties(sbase, libSBase);
    if (sbase.isSetConstant()) {
      libSBase.setConstant(sbase.getConstant());
    }
    if (sbase.isSetStoichiometry()) {
      libSBase.setStoichiometry(sbase.getStoichiometry());
    } else if (sbase.isSetStoichiometryMath()) {
      libSBase.setStoichiometryMath(convertStoichiometryMath(sbase.getStoichiometryMath()));
    }
  }

}

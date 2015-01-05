/*
 * $Id$
 * $URL$
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
package org.sbml.jsbml.celldesigner.libsbml;

import static org.sbml.jsbml.celldesigner.libsbml.LibSBMLConstants.LINK_TO_LIBSBML;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.CVTerm.Qualifier;
import org.sbml.jsbml.Creator;
import org.sbml.jsbml.History;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.StoichiometryMath;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.Unit;
import org.sbml.libsbml.ModelCreator;
import org.sbml.libsbml.ModelHistory;
import org.sbml.libsbml.libsbmlConstants;


/**
 * Simplified {@link LibSBMLUtils} for CellDesigner's older version of libSBML.
 * 
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 1.0
 * @date 03.02.2014
 */
@SuppressWarnings("deprecation")
public class LibSBMLUtils {

  /**
   * A {@link Logger} for this class
   */
  private static final Logger logger = Logger.getLogger(LibSBMLUtils.class);

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

    //    if (astnode.isSetClassName()) {
    //      libAstNode.setClassName(astnode.getClassName());
    //    }
    //    if (astnode.isSetId()) {
    //      libAstNode.setId(astnode.getId());
    //    }
    if (astnode.isSetName()) {
      if (!astnode.isOperator() && !astnode.isNumber()) {
        libAstNode.setName(astnode.getName());
      }
    }
    //    if (astnode.isSetStyle()) {
    //      libAstNode.setStyle(astnode.getStyle());
    //    }
    //    if (astnode.isSetUnits()) {
    //      libAstNode.setUnits(astnode.getUnits());
    //    }
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
        method.invoke(libSBase, libASTNode);
        method = libSBase.getClass().getMethod("getMath");
        libASTNode = (org.sbml.libsbml.ASTNode) method.invoke(libSBase);
        link(mathContainer.getMath(), libASTNode);
      } catch (Throwable t) {
        logger.warn(t.getMessage(), t);
      }
    }
  }

  /**
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
        if (libSBase instanceof org.sbml.libsbml.Model) {
          org.sbml.libsbml.Model model = (org.sbml.libsbml.Model) libSBase;
          model.setModelHistory(new ModelHistory());
          History history = sbase.getHistory();
          history.putUserObject(LINK_TO_LIBSBML, model.getModelHistory());
          convertHistory(history);
        }
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
   * @param createdDate
   * @return
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
      if (libSBase instanceof org.sbml.libsbml.Model) {
        org.sbml.libsbml.Model model = (org.sbml.libsbml.Model) libSBase;
        if (model.isSetModelHistory()) {
          sbase.setHistory(convertHistory(model.getModelHistory()));
        }
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
      //      t.setInitialValue(trigger.getInitialValue());
    }
    if (trigger.isSetPersistent()) {
      //      t.setPersistent(trigger.isPersistent());
    }
    return t;
  }

  /**
   * 
   * @param term
   * @return
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
   * @param kind
   * @return
   */
  public static int convertUnitKind(Unit.Kind kind) {
    switch (kind) {
    case AMPERE:
      return libsbmlConstants.UNIT_KIND_AMPERE;
      //    case AVOGADRO:
      //      return libsbmlConstants.UNIT_KIND_AVOGADRO;
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
   * @param type
   * @return
   */
  public static int convertASTNodeType(ASTNode.Type type) {
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
      //    case NAME_AVOGADRO:
      //      return libsbmlConstants.AST_NAME_AVOGADRO;
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
   * @param libCVt
   * @return
   */
  public static CVTerm convertCVTerm(org.sbml.libsbml.CVTerm libCVt) {
    // TODO: Extract switch in separate method
    CVTerm cvTerm = new CVTerm();
    switch (libCVt.getQualifierType()) {
    case libsbmlConstants.MODEL_QUALIFIER:
      cvTerm.setQualifierType(CVTerm.Type.MODEL_QUALIFIER);
      switch (libCVt.getModelQualifierType()) {
      case libsbmlConstants.BQM_IS:
        cvTerm.setModelQualifierType(Qualifier.BQM_IS);
        break;
        //      case libsbmlConstants.BQM_IS_DERIVED_FROM:
        //        cvTerm.setModelQualifierType(Qualifier.BQM_IS_DERIVED_FROM);
        //        break;
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
        //      case libsbmlConstants.BQB_HAS_PROPERTY:
        //        cvTerm.setBiologicalQualifierType(Qualifier.BQB_HAS_PROPERTY);
        //        break;
      case libsbmlConstants.BQB_IS_HOMOLOG_TO:
        cvTerm.setBiologicalQualifierType(Qualifier.BQB_IS_HOMOLOG_TO);
        break;
      case libsbmlConstants.BQB_IS_PART_OF:
        cvTerm.setBiologicalQualifierType(Qualifier.BQB_IS_PART_OF);
        break;
        //      case libsbmlConstants.BQB_IS_PROPERTY_OF:
        //        cvTerm.setBiologicalQualifierType(Qualifier.BQB_IS_PROPERTY_OF);
        //        break;
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
    cvTerm.putUserObject(LINK_TO_LIBSBML, libCVt);
    return cvTerm;
  }

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
    //    if (math.isSetUnits()) {
    //      ast.setUnits(math.getUnits());
    //    }
    //    if (math.isSetStyle()) {
    //      ast.setStyle(math.getStyle());
    //    }
    ast.putUserObject(LINK_TO_LIBSBML, math);
    return ast;
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
      //    case libsbmlConstants.UNIT_KIND_AVOGADRO:
      //      return Unit.Kind.AVOGADRO;
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

}

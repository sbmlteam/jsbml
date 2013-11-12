/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.cdplugin;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import jp.sbi.celldesigner.plugin.PluginAlgebraicRule;
import jp.sbi.celldesigner.plugin.PluginAssignmentRule;
import jp.sbi.celldesigner.plugin.PluginCompartment;
import jp.sbi.celldesigner.plugin.PluginCompartmentType;
import jp.sbi.celldesigner.plugin.PluginConstraint;
import jp.sbi.celldesigner.plugin.PluginEvent;
import jp.sbi.celldesigner.plugin.PluginEventAssignment;
import jp.sbi.celldesigner.plugin.PluginFunctionDefinition;
import jp.sbi.celldesigner.plugin.PluginInitialAssignment;
import jp.sbi.celldesigner.plugin.PluginKineticLaw;
import jp.sbi.celldesigner.plugin.PluginModel;
import jp.sbi.celldesigner.plugin.PluginModifierSpeciesReference;
import jp.sbi.celldesigner.plugin.PluginParameter;
import jp.sbi.celldesigner.plugin.PluginRateRule;
import jp.sbi.celldesigner.plugin.PluginReaction;
import jp.sbi.celldesigner.plugin.PluginRule;
import jp.sbi.celldesigner.plugin.PluginSBase;
import jp.sbi.celldesigner.plugin.PluginSimpleSpeciesReference;
import jp.sbi.celldesigner.plugin.PluginSpecies;
import jp.sbi.celldesigner.plugin.PluginSpeciesAlias;
import jp.sbi.celldesigner.plugin.PluginSpeciesReference;
import jp.sbi.celldesigner.plugin.PluginSpeciesType;
import jp.sbi.celldesigner.plugin.PluginUnit;
import jp.sbi.celldesigner.plugin.PluginUnitDefinition;
import jp.sbi.celldesigner.plugin.util.PluginSpeciesSymbolType;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AlgebraicRule;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.CompartmentType;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLInputConverter;
import org.sbml.jsbml.SBO;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.StoichiometryMath;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.xml.libsbml.LibSBMLUtils;
import org.sbml.libsbml.libsbml;
import org.sbml.libsbml.libsbmlConstants;

/**
 * 
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 0.8
 */
@SuppressWarnings("deprecation")
public class PluginSBMLReader implements SBMLInputConverter<PluginModel> {

  /**
   * 
   */
  private static final int level = 2;
  /**
   * 
   */
  private static final int version = 4;
  /**
   * 
   */
  protected LinkedList<TreeNodeChangeListener> listOfTreeNodeChangeListeners;
  /**
   * 
   */
  private Model model;

  /**
   * 
   */
  private PluginModel originalmodel;

  /**
   * 
   */
  private Set<Integer> possibleEnzymes;

  /**
   * get a model from the CellDesigner output, converts it to JSBML
   * format and stores it
   * 
   * @param model
   */
  public PluginSBMLReader(PluginModel model, Set<Integer> possibleEnzymes) {
    this(possibleEnzymes);
    originalmodel = model;
    this.model = convertModel(model);
  }

  /**
   * @return the model
   */
  public Model getModel() {
    return model;
  }

  /**
   * 
   */
  public PluginSBMLReader(Set<Integer> possibleEnzymes) {
    super();
    this.possibleEnzymes = possibleEnzymes;
    listOfTreeNodeChangeListeners = new LinkedList<TreeNodeChangeListener>();
  }

  /**
   * 
   * @param sb
   */
  public void addAllSBaseChangeListenersTo(SBase sb) {
    for (TreeNodeChangeListener listener : listOfTreeNodeChangeListeners) {
      sb.addTreeNodeChangeListener(listener);
    }
  }

  /**
   * 
   * @param math
   * @param parent
   * @return
   */
  public ASTNode convert(org.sbml.libsbml.ASTNode math, MathContainer parent) {
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
        for (LocalParameter p : ((KineticLaw) parent)
            .getListOfParameters()) {
          if (p.getId().equals(math.getName())) {
            ast.setVariable(p);
            break;
          }
        }
      }
      if (ast.getVariable() == null) {
        CallableSBase csb = model
            .findCallableSBase(math.getName());
        if (csb == null) {
          ast.setName(math.getName());
        } else {
          ast.setVariable(csb);
        }
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
      ast.addChild(convert(math.getChild(i), parent));
    }
    return ast;
  }

  /**
   * 
   * @param c
   * @param pc
   */
  private void copyNamedSBaseProperties(NamedSBase n, PluginSBase ps) {
    copySBaseProperties(n, ps);
    if (ps instanceof PluginCompartment) {
      PluginCompartment c = (PluginCompartment) ps;
      if (c.getId() != null) {
        n.setId(c.getId());
      }
      if (c.getName() != null) {
        n.setName(c.getName());
      }
    } else if (ps instanceof PluginCompartmentType) {
      PluginCompartmentType c = (PluginCompartmentType) ps;
      if (c.getId() != null) {
        n.setId(c.getId());
      }
      if (c.getName() != null) {
        n.setName(c.getName());
      }
    } else if (ps instanceof PluginEvent) {
      PluginEvent c = (PluginEvent) ps;
      if (c.getId() != null) {
        n.setId(c.getId());
      }
      if (c.getName() != null) {
        n.setName(c.getName());
      }
    } else if (ps instanceof PluginModel) {
      PluginModel c = (PluginModel) ps;
      if (c.getId() != null) {
        n.setId(c.getId());
      }
      if (c.getName() != null) {
        n.setName(c.getName());
      }
    } else if (ps instanceof PluginReaction) {
      PluginReaction c = (PluginReaction) ps;
      if (c.getId() != null) {
        n.setId(c.getId());
      }
      if (c.getName() != null) {
        n.setName(c.getName());
      }
    } else if (ps instanceof PluginSimpleSpeciesReference) {
      // PluginSimpleSpeciesReference c = (PluginSimpleSpeciesReference)
      // ps;
      // if (c.getId() != null)
      // n.setId(c.getId());
      // if (c.getName() != null)
      // sbase.setName(c.getName());
    } else if (ps instanceof PluginSpeciesType) {
      PluginSpeciesType c = (PluginSpeciesType) ps;
      if (c.getId() != null) {
        n.setId(c.getId());
      }
      if (c.getName() != null) {
        n.setName(c.getName());
      }
    } else if (ps instanceof PluginParameter) {
      PluginParameter c = (PluginParameter) ps;
      if (c.getId() != null) {
        n.setId(c.getId());
      }
      if (c.getName() != null) {
        n.setName(c.getName());
      }
    } else if (ps instanceof PluginSpecies) {
      PluginSpecies c = (PluginSpecies) ps;
      if (c.getId() != null) {
        n.setId(c.getId());
      }
      if (c.getName() != null) {
        n.setName(c.getName());
      }
    } else if (ps instanceof PluginUnitDefinition) {
      PluginUnitDefinition c = (PluginUnitDefinition) ps;
      if (c.getId() != null) {
        n.setId(c.getId());
      }
      if (c.getName() != null) {
        n.setName(c.getName());
      }
    } else if (ps instanceof PluginFunctionDefinition) {
      PluginFunctionDefinition c = (PluginFunctionDefinition) ps;
      if (c.getId() != null) {
        n.setId(c.getId());
      }
      if (c.getName() != null) {
        n.setName(c.getName());
      }
    }

  }

  /**
   * 
   * @param c
   * @param pc
   */
  private void copySBaseProperties(SBase c, PluginSBase pc) {
    if (pc.getNotesString().length() > 0) {
      c.setNotes(pc.getNotesString());
    }
    for (int i = 0; i < pc.getNumCVTerms(); i++) {
      c.addCVTerm(LibSBMLUtils.convertCVTerm(pc.getCVTerm(i)));
    }
  }

  /**
   * 
   * @return
   */
  public int getNumErrors() {
    return getErrorCount();
  }

  /**
   * 
   * @return
   */
  public int getErrorCount() {
    return 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLInputConverter#getOriginalModel()
   */
  @Override
  public PluginModel getOriginalModel() {
    return originalmodel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLReader#getWarnings()
   */
  @Override
  public List<SBMLException> getWarnings() {
    return new LinkedList<SBMLException>();
  }

  /**
   * 
   * @param compartment
   * @return
   */
  private Compartment readCompartment(PluginCompartment compartment) {
    Compartment c = new Compartment(compartment.getId(), level, version);
    copyNamedSBaseProperties(c, compartment);
    if (compartment.getOutside().length() > 0) {
      Compartment outside = model.getCompartment(compartment.getOutside());
      if (outside == null) {
        outside = readCompartment(originalmodel.getCompartment(compartment
          .getOutside()));
        model.addCompartment(outside);
      }
      c.setOutside(outside);
    }
    if (compartment.getCompartmentType().length() > 0) {
      c.setCompartmentType(model.getCompartmentType(compartment
        .getCompartmentType()));
    }
    c.setConstant(compartment.getConstant());
    c.setSize(compartment.getSize());
    c.setSpatialDimensions((short) compartment.getSpatialDimensions());
    if (compartment.getUnits().length() > 0) {
      String size = compartment.getUnits();
      if (model.getUnitDefinition(size) != null) {
        c.setUnits(model.getUnitDefinition(size));
      } else {
        c.setUnits(Unit.Kind.valueOf(size.toUpperCase()));
      }
    }
    addAllSBaseChangeListenersTo(c);
    return c;
  }

  /**
   * 
   * @param compartmentType
   * @return
   */
  private CompartmentType readCompartmentType(PluginCompartmentType compartmentType) {
    CompartmentType com = new CompartmentType(compartmentType.getId(), level, version);
    copyNamedSBaseProperties(com, compartmentType);
    return com;
  }

  /**
   * 
   * @param constraint
   * @return
   */
  private Constraint readConstraint(PluginConstraint constraint) {
    Constraint c = new Constraint(level, version);
    copySBaseProperties(c, constraint);
    if (constraint.getMath() != null) {
      c.setMath(convert(libsbml.parseFormula(constraint.getMath()), c));
    }
    if (constraint.getMessage().length() > 0) {
      c.setMessage(constraint.getMessage());
    }
    return c;
  }

  /**
   * 
   * @param delay
   * @return
   */
  private Delay readDelay(org.sbml.libsbml.Delay delay) {
    Delay de = new Delay(level, version);
    if (delay.getNotesString().length() > 0) {
      de.setNotes(delay.getNotesString());
    }
    if (delay.isSetMath()) {
      de.setMath(convert(delay.getMath(), de));
    }
    return de;
  }

  /**
   * 
   * @param event
   * @return
   */
  private Event readEvent(PluginEvent event) {
    Event e = new Event(level, version);
    copyNamedSBaseProperties(e, event);
    if (event.getDelay() != null) {
      e.setDelay(readDelay(event.getDelay()));
    }
    for (int i = 0; i < event.getNumEventAssignments(); i++) {
      e.addEventAssignment(readEventAssignment(event
        .getEventAssignment(i)));
    }
    if (event.getTimeUnits().length() > 0) {
      String st = event.getTimeUnits();
      if (model.getUnitDefinition(st) != null) {
        e.setTimeUnits(model.getUnitDefinition(st));
      } else {
        e.setTimeUnits(st);
      }
    }
    if (event.getTrigger() != null) {
      e.setTrigger(readTrigger(event.getTrigger()));
    }
    e.setUseValuesFromTriggerTime(event.getUseValuesFromTriggerTime());
    addAllSBaseChangeListenersTo(e);
    return e;
  }

  /**
   * 
   * @param eventass
   * @return
   */
  private EventAssignment readEventAssignment(PluginEventAssignment eventass) {
    EventAssignment ev = new EventAssignment(level, version);
    copySBaseProperties(ev, eventass);
    if (eventass.getVariable() != null
        && eventass.getVariable().length() > 0) {
      ev.setVariable(model.findVariable(eventass.getVariable()));
    }
    if (eventass.getMath() != null) {
      ev.setMath(convert(eventass.getMath(), ev));
    }
    addAllSBaseChangeListenersTo(ev);
    return ev;
  }

  /**
   * 
   * @param functionDefinition
   * @return
   */
  private FunctionDefinition readFunctionDefinition(PluginFunctionDefinition functionDefinition) {
    FunctionDefinition f = new FunctionDefinition(functionDefinition.getId(), level,
      version);
    copyNamedSBaseProperties(f, functionDefinition);
    if (functionDefinition.getMath() != null) {
      f.setMath(convert(functionDefinition.getMath(), f));
    }
    addAllSBaseChangeListenersTo(f);
    return f;
  }

  /**
   * 
   * @param initialAssignment
   * @return
   */
  private InitialAssignment readInitialAssignment(PluginInitialAssignment initialAssignment) {
    if (initialAssignment.getSymbol() == null) {
      throw new IllegalArgumentException(
          "Symbol attribute not set for InitialAssignment");
    }
    InitialAssignment ia = new InitialAssignment(model.findVariable(initialAssignment
      .getSymbol()));
    copySBaseProperties(ia, initialAssignment);
    if (initialAssignment.getMath() != null) {
      ia.setMath(convert(libsbml.parseFormula(initialAssignment.getMath()), ia));
    }
    addAllSBaseChangeListenersTo(ia);
    return ia;
  }

  /**
   * 
   * @param kineticLaw
   * @return
   */
  private KineticLaw readKineticLaw(PluginKineticLaw kineticLaw) {
    KineticLaw kinlaw = new KineticLaw(level, version);
    for (int i = 0; i < kineticLaw.getNumParameters(); i++) {
      kinlaw.addParameter(readLocalParameter(kineticLaw.getParameter(i)));
    }
    if (kineticLaw.getMath() != null) {
      kinlaw.setMath(convert(kineticLaw.getMath(), kinlaw));
    } else if (kineticLaw.getFormula().length() > 0) {
      kinlaw.setMath(convert(libsbml.readMathMLFromString(kineticLaw
        .getFormula()), kinlaw));
    }
    addAllSBaseChangeListenersTo(kinlaw);
    return kinlaw;
  }

  /**
   * 
   * @param parameter
   * @return
   */
  private LocalParameter readLocalParameter(PluginParameter parameter) {
    LocalParameter para = new LocalParameter(parameter.getId(), level, version);
    copyNamedSBaseProperties(para, parameter);
    if (parameter.getUnits().length() > 0) {
      String substance = parameter.getUnits();
      if (model.getUnitDefinition(substance) != null) {
        para.setUnits(model.getUnitDefinition(substance));
      } else {
        para.setUnits(Unit.Kind.valueOf(substance.toUpperCase()));
      }
    }
    para.setValue(parameter.getValue());
    addAllSBaseChangeListenersTo(para);
    return para;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLReader#readModel(java.lang.Object)
   */
  @Override
  public Model convertModel(PluginModel originalmodel) {
    this.originalmodel = originalmodel;
    int i;
    model = new Model(originalmodel.getId(), level, version);
    for (i = 0; i < originalmodel.getNumFunctionDefinitions(); i++) {
      model
      .addFunctionDefinition(readFunctionDefinition(originalmodel
        .getFunctionDefinition(i)));
    }
    for (i = 0; i < originalmodel.getNumUnitDefinitions(); i++) {
      model.addUnitDefinition(readUnitDefinition(originalmodel.getUnitDefinition(i)));
    }
    for (i = 0; i < originalmodel.getNumCompartmentTypes(); i++) {
      model.addCompartmentType(readCompartmentType(originalmodel
        .getCompartmentType(i)));
    }
    for (i = 0; i < originalmodel.getNumSpeciesTypes(); i++) {
      model.addSpeciesType(readSpeciesType(originalmodel
        .getSpeciesType(i)));
    }
    for (i = 0; i < originalmodel.getNumCompartments(); i++) {
      model.addCompartment(readCompartment(originalmodel
        .getCompartment(i)));
    }
    for (i = 0; i < originalmodel.getNumSpecies(); i++) {
      model.addSpecies(readSpecies(originalmodel.getSpecies(i)));
    }
    for (i = 0; i < originalmodel.getNumParameters(); i++) {
      model
      .addParameter(readParameter(originalmodel.getParameter(i)));
    }
    for (i = 0; i < originalmodel.getNumInitialAssignments(); i++) {
      model.addInitialAssignment(readInitialAssignment(originalmodel
        .getInitialAssignment(i)));
    }
    for (i = 0; i < originalmodel.getNumRules(); i++) {
      model.addRule(readRule(originalmodel.getRule(i)));
    }
    for (i = 0; i < originalmodel.getNumConstraints(); i++) {
      model.addConstraint(readConstraint(originalmodel
        .getConstraint(i)));
    }
    for (i = 0; i < originalmodel.getNumReactions(); i++) {
      model.addReaction(readReaction(originalmodel.getReaction(i)));
    }
    for (i = 0; i < originalmodel.getNumEvents(); i++) {
      model.addEvent(readEvent(originalmodel.getEvent(i)));
    }
    addAllSBaseChangeListenersTo(model);
    return model;
  }

  /**
   * 
   * @param modifierSpeciesReference
   * @return
   */
  private ModifierSpeciesReference readModifierSpeciesReference(
    PluginModifierSpeciesReference modifierSpeciesReference) {
    ModifierSpeciesReference mod = new ModifierSpeciesReference(
      new Species(modifierSpeciesReference.getSpeciesInstance().getId(), level, version));
    copyNamedSBaseProperties(mod, modifierSpeciesReference);
    /*
     * Set SBO term.
     */
    mod.setSBOTerm(SBO.convertAlias2SBO(modifierSpeciesReference.getModificationType()));
    if (SBO.isCatalyst(mod.getSBOTerm())) {
      PluginSpecies species = modifierSpeciesReference.getSpeciesInstance();
      String speciesAliasType = species.getSpeciesAlias(0).getType()
          .equals("PROTEIN") ? species.getSpeciesAlias(0)
            .getProtein().getType() : species.getSpeciesAlias(0)
            .getType();
            int sbo = SBO.convertAlias2SBO(speciesAliasType);
            if (possibleEnzymes.contains(Integer.valueOf(sbo))) {
              mod.setSBOTerm(SBO.getEnzymaticCatalysis());
            }
    }
    mod.setSpecies(model.getSpecies(modifierSpeciesReference.getSpecies()));
    addAllSBaseChangeListenersTo(mod);
    return mod;
  }

  /**
   * 
   * @param parameter
   * @return
   */
  private Parameter readParameter(PluginParameter parameter) {
    Parameter para = new Parameter(parameter.getId(), level, version);
    copyNamedSBaseProperties(para, parameter);
    para.setConstant(parameter.getConstant());
    if (parameter.getUnits().length() > 0) {
      String substance = parameter.getUnits();
      if (model.getUnitDefinition(substance) != null) {
        para.setUnits(model.getUnitDefinition(substance));
      } else {
        para.setUnits(Unit.Kind.valueOf(substance.toUpperCase()));
      }
    }
    para.setValue(parameter.getValue());
    addAllSBaseChangeListenersTo(para);
    return para;
  }

  /**
   * 
   * @param reac
   * @return
   */
  private Reaction readReaction(PluginReaction reac) {
    Reaction reaction = new Reaction(reac.getId(), level, version);
    for (int i = 0; i < reac.getNumReactants(); i++) {
      reaction.addReactant(readSpeciesReference(reac.getReactant(i)));
    }
    for (int i = 0; i < reac.getNumProducts(); i++) {
      reaction.addProduct(readSpeciesReference(reac.getProduct(i)));
    }
    for (int i = 0; i < reac.getNumModifiers(); i++) {
      reaction.addModifier(readModifierSpeciesReference(reac.getModifier(i)));
    }
    int sbo = SBO.convertAlias2SBO(reac.getReactionType());
    if (SBO.checkTerm(sbo)) {
      reaction.setSBOTerm(sbo);
    }
    if (reac.getKineticLaw() != null) {
      reaction.setKineticLaw(readKineticLaw(reac.getKineticLaw()));
    }
    reaction.setFast(reac.getFast());
    reaction.setReversible(reac.getReversible());
    addAllSBaseChangeListenersTo(reaction);
    return reaction;
  }

  /**
   * 
   * @param rule
   * @return
   */
  private Rule readRule(PluginRule rule) {
    Rule r;
    if (rule instanceof PluginAlgebraicRule) {
      r = new AlgebraicRule(level, version);
    } else {
      if (rule instanceof PluginAssignmentRule) {
        r = new AssignmentRule(model
          .findVariable(((PluginAssignmentRule) rule).getVariable()));
      } else {
        r = new RateRule(model.findVariable(((PluginRateRule) rule)
          .getVariable()));
      }
    }
    copySBaseProperties(r, rule);
    if (rule.getMath() != null) {
      r.setMath(convert(rule.getMath(), r));
    }
    addAllSBaseChangeListenersTo(r);
    return r;
  }

  /**
   * 
   * @param species
   * @return
   */
  private Species readSpecies(PluginSpecies species) {
    Species s = new Species(species.getId(), level, version);
    copyNamedSBaseProperties(s, species);
    int sbo = SBO.convertAlias2SBO(species.getSpeciesAlias(0).getType());
    PluginSpeciesAlias alias = species.getSpeciesAlias(0);
    String type = alias.getType();
    if (alias.getType().equals(PluginSpeciesSymbolType.PROTEIN)) {
      type = alias.getProtein().getType();
    }
    sbo = SBO.convertAlias2SBO(type);
    if (SBO.checkTerm(sbo)) {
      s.setSBOTerm(sbo);
    }
    s.setBoundaryCondition(species.getBoundaryCondition());
    if (species.getCompartment().length() > 0) {
      s.setCompartment(model.getCompartment(species.getCompartment()));
    }
    s.setCharge(species.getCharge());
    s.setConstant(species.getConstant());
    s.setHasOnlySubstanceUnits(species.getHasOnlySubstanceUnits());
    if (species.isSetInitialAmount()) {
      s.setInitialAmount(species.getInitialAmount());
    } else if (species.isSetInitialConcentration()) {
      s.setInitialConcentration(species.getInitialConcentration());
    }
    // before L2V3...
    species.getSpatialSizeUnits();
    if (species.getSpeciesType().length() > 0) {
      s.setSpeciesType(model.getSpeciesType(species.getSpeciesType()));
    }
    if (species.getSubstanceUnits().length() > 0) {
      String substance = species.getSubstanceUnits();
      if (model.getUnitDefinition(substance) != null) {
        s.setSubstanceUnits(model.getUnitDefinition(substance));
      } else {
        s.setSubstanceUnits(Unit.Kind.valueOf(substance.toUpperCase()));
      }
    } else if (species.getUnits().length() > 0) {
      String substance = species.getUnits();
      if (model.getUnitDefinition(substance) != null) {
        s.setSubstanceUnits(model.getUnitDefinition(substance));
      } else {
        s.setSubstanceUnits(Unit.Kind.valueOf(substance.toUpperCase()));
      }
    }
    addAllSBaseChangeListenersTo(s);
    return s;
  }

  /**
   * 
   * @param speciesReference
   * @return
   */
  private SpeciesReference readSpeciesReference(PluginSpeciesReference speciesReference) {
    SpeciesReference spec = new SpeciesReference(new Species(speciesReference
      .getSpeciesInstance().getId(), level, version));
    copyNamedSBaseProperties(spec, speciesReference);
    if (speciesReference.getStoichiometryMath() == null) {
      spec.setStoichiometry(speciesReference.getStoichiometry());
    } else {
      spec.setStoichiometryMath(readStoichiometricMath(speciesReference
        .getStoichiometryMath()));
    }
    if (speciesReference.getReferenceType().length() > 0) {
      int sbo = SBO.convertAlias2SBO(speciesReference.getReferenceType());
      if (SBO.checkTerm(sbo)) {
        spec.setSBOTerm(sbo);
      }
    }
    spec.setSpecies(model.getSpecies(speciesReference.getSpecies()));
    addAllSBaseChangeListenersTo(spec);
    return spec;
  }

  /**
   * 
   * @param speciesType
   * @return
   */
  private SpeciesType readSpeciesType(PluginSpeciesType speciesType) {
    SpeciesType st = new SpeciesType(speciesType.getId(), level, version);
    copyNamedSBaseProperties(st, speciesType);
    addAllSBaseChangeListenersTo(st);
    return st;
  }

  /**
   * 
   * @param stoichiometryMath
   * @return
   */
  private StoichiometryMath readStoichiometricMath(org.sbml.libsbml.StoichiometryMath stoichiometryMath) {
    StoichiometryMath s = new StoichiometryMath(level, version);
    s.setMath(convert(stoichiometryMath.getMath(), s));
    addAllSBaseChangeListenersTo(s);
    return s;
  }

  /**
   * 
   * @param trigger
   * @return
   */
  private Trigger readTrigger(org.sbml.libsbml.Trigger trigger) {
    Trigger trig = new Trigger(level, version);
    if (trigger.getNotesString().length() > 0) {
      trig.setNotes(trigger.getNotesString());
    }
    if (trigger.isSetMath()) {
      trig.setMath(convert(trigger.getMath(), trig));
    }
    return trig;
  }

  /**
   * 
   * @param unit
   * @return
   */
  private Unit readUnit(PluginUnit unit) {
    Unit u = new Unit(level, version);
    copySBaseProperties(u, unit);
    switch (unit.getKind()) {
    case libsbmlConstants.UNIT_KIND_AMPERE:
      u.setKind(Unit.Kind.AMPERE);
      break;
    case libsbmlConstants.UNIT_KIND_BECQUEREL:
      u.setKind(Unit.Kind.BECQUEREL);
      break;
    case libsbmlConstants.UNIT_KIND_CANDELA:
      u.setKind(Unit.Kind.CANDELA);
      break;
    case libsbmlConstants.UNIT_KIND_CELSIUS:
      u.setKind(Unit.Kind.CELSIUS);
      break;
    case libsbmlConstants.UNIT_KIND_COULOMB:
      u.setKind(Unit.Kind.COULOMB);
      break;
    case libsbmlConstants.UNIT_KIND_DIMENSIONLESS:
      u.setKind(Unit.Kind.DIMENSIONLESS);
      break;
    case libsbmlConstants.UNIT_KIND_FARAD:
      u.setKind(Unit.Kind.FARAD);
      break;
    case libsbmlConstants.UNIT_KIND_GRAM:
      u.setKind(Unit.Kind.GRAM);
      break;
    case libsbmlConstants.UNIT_KIND_GRAY:
      u.setKind(Unit.Kind.GRAY);
      break;
    case libsbmlConstants.UNIT_KIND_HENRY:
      u.setKind(Unit.Kind.HENRY);
      break;
    case libsbmlConstants.UNIT_KIND_HERTZ:
      u.setKind(Unit.Kind.HERTZ);
      break;
    case libsbmlConstants.UNIT_KIND_INVALID:
      u.setKind(Unit.Kind.INVALID);
      break;
    case libsbmlConstants.UNIT_KIND_ITEM:
      u.setKind(Unit.Kind.ITEM);
      break;
    case libsbmlConstants.UNIT_KIND_JOULE:
      u.setKind(Unit.Kind.JOULE);
      break;
    case libsbmlConstants.UNIT_KIND_KATAL:
      u.setKind(Unit.Kind.KATAL);
      break;
    case libsbmlConstants.UNIT_KIND_KELVIN:
      u.setKind(Unit.Kind.KELVIN);
      break;
    case libsbmlConstants.UNIT_KIND_KILOGRAM:
      u.setKind(Unit.Kind.KILOGRAM);
      break;
    case libsbmlConstants.UNIT_KIND_LITER:
      u.setKind(Unit.Kind.LITER);
      break;
    case libsbmlConstants.UNIT_KIND_LITRE:
      u.setKind(Unit.Kind.LITRE);
      break;
    case libsbmlConstants.UNIT_KIND_LUMEN:
      u.setKind(Unit.Kind.LUMEN);
      break;
    case libsbmlConstants.UNIT_KIND_LUX:
      u.setKind(Unit.Kind.LUX);
      break;
    case libsbmlConstants.UNIT_KIND_METER:
      u.setKind(Unit.Kind.METER);
      break;
    case libsbmlConstants.UNIT_KIND_METRE:
      u.setKind(Unit.Kind.METRE);
      break;
    case libsbmlConstants.UNIT_KIND_MOLE:
      u.setKind(Unit.Kind.MOLE);
      break;
    case libsbmlConstants.UNIT_KIND_NEWTON:
      u.setKind(Unit.Kind.NEWTON);
      break;
    case libsbmlConstants.UNIT_KIND_OHM:
      u.setKind(Unit.Kind.OHM);
      break;
    case libsbmlConstants.UNIT_KIND_PASCAL:
      u.setKind(Unit.Kind.PASCAL);
      break;
    case libsbmlConstants.UNIT_KIND_RADIAN:
      u.setKind(Unit.Kind.RADIAN);
      break;
    case libsbmlConstants.UNIT_KIND_SECOND:
      u.setKind(Unit.Kind.SECOND);
      break;
    case libsbmlConstants.UNIT_KIND_SIEMENS:
      u.setKind(Unit.Kind.SIEMENS);
      break;
    case libsbmlConstants.UNIT_KIND_SIEVERT:
      u.setKind(Unit.Kind.SIEVERT);
      break;
    case libsbmlConstants.UNIT_KIND_STERADIAN:
      u.setKind(Unit.Kind.STERADIAN);
      break;
    case libsbmlConstants.UNIT_KIND_TESLA:
      u.setKind(Unit.Kind.TESLA);
      break;
    case libsbmlConstants.UNIT_KIND_VOLT:
      u.setKind(Unit.Kind.VOLT);
      break;
    case libsbmlConstants.UNIT_KIND_WATT:
      u.setKind(Unit.Kind.WATT);
      break;
    case libsbmlConstants.UNIT_KIND_WEBER:
      u.setKind(Unit.Kind.WEBER);
      break;
    }
    u.setExponent(unit.getExponent());
    u.setMultiplier(unit.getMultiplier());
    u.setScale(unit.getScale());
    if (u.isSetOffset()) {
      u.setOffset(unit.getOffset());
    }
    addAllSBaseChangeListenersTo(u);
    return u;
  }

  /**
   * 
   * @param unitDefinition
   * @return
   */
  private UnitDefinition readUnitDefinition(PluginUnitDefinition unitDefinition) {
    UnitDefinition ud = new UnitDefinition(unitDefinition.getId(), level, version);
    copyNamedSBaseProperties(ud, unitDefinition);
    for (int i = 0; i < unitDefinition.getNumUnits(); i++) {
      ud.addUnit(readUnit(unitDefinition.getUnit(i)));
    }
    addAllSBaseChangeListenersTo(ud);
    return ud;
  }

}

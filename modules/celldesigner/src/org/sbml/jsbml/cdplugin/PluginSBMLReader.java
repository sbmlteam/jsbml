/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2012 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.cdplugin;

import java.util.Date;
import java.util.HashSet;
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
import org.sbml.jsbml.CVTerm;
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
import org.sbml.jsbml.CVTerm.Qualifier;
import org.sbml.jsbml.util.IOProgressListener;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.libsbml.libsbml;
import org.sbml.libsbml.libsbmlConstants;

/**
 * 
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 0.8
 */
@SuppressWarnings("deprecation")
public class PluginSBMLReader implements SBMLInputConverter {

	/**
	 * 
	 */
	private static final String error = " must be an instance of ";
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

	private Set<IOProgressListener> setIOListeners;

	/**
	 * get a model from the CellDesigner output, converts it to JSBML
	 * format and stores it
	 * 
	 * @param model
	 */
	public PluginSBMLReader(PluginModel model, Set<Integer> possibleEnzymes) {
		this(possibleEnzymes);
		this.originalmodel = model;
		this.setIOListeners = new HashSet<IOProgressListener>();
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
		this.setIOListeners = new HashSet<IOProgressListener>();
		this.listOfTreeNodeChangeListeners = new LinkedList<TreeNodeChangeListener>();
	}

	/**
	 * 
	 * @param sb
	 */
	public void addAllSBaseChangeListenersTo(SBase sb) {
		for (TreeNodeChangeListener listener : listOfTreeNodeChangeListeners)
			sb.addTreeNodeChangeListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.sbml.jsbml.SBMLReader#addIOProgressListener(org.sbml.jsbml.io.
	 * IOProgressListener)
	 */
	public void addIOProgressListener(IOProgressListener listener) {
		setIOListeners.add(listener);
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
			if (parent instanceof KineticLaw)
				for (LocalParameter p : ((KineticLaw) parent)
						.getListOfParameters())
					if (p.getId().equals(math.getName())) {
						ast.setVariable(p);
						break;
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
		for (int i = 0; i < math.getNumChildren(); i++)
			ast.addChild(convert(math.getChild(i), parent));
		return ast;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBMLReader#convertDate(java.lang.Object)
	 */
	public Date convertDate(Object d) {
		// TODO Auto-generated method stub
		return null;
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
			c.addCVTerm(readCVTerm(pc.getCVTerm(i)));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBMLReader#getNumErrors()
	 */
	public int getNumErrors() {
		return getErrorCount();
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.SBMLInputConverter#getErrorCount()
	 */
	public int getErrorCount() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.io.AbstractSBMLReader#getOriginalModel()
	 */
	// @Override
	public Object getOriginalModel() {
		return originalmodel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBMLReader#getWarnings()
	 */
	public List<SBMLException> getWarnings() {
		return new LinkedList<SBMLException>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readCompartment(java.lang.Object)
	 */
	public Compartment readCompartment(Object compartment) {
		if (!(compartment instanceof PluginCompartment))
			throw new IllegalArgumentException("compartment" + error
					+ "PluginCompartment.");
		PluginCompartment pc = (PluginCompartment) compartment;
		Compartment c = new Compartment(pc.getId(), level, version);
		copyNamedSBaseProperties(c, pc);
		if (pc.getOutside().length() > 0) {
			Compartment outside = model.getCompartment(pc.getOutside());
			if (outside == null) {
				outside = readCompartment(originalmodel.getCompartment(pc
						.getOutside()));
				model.addCompartment(outside);
			}
			c.setOutside(outside);
		}
		if (pc.getCompartmentType().length() > 0)
			c.setCompartmentType(model.getCompartmentType(pc
					.getCompartmentType()));
		c.setConstant(pc.getConstant());
		c.setSize(pc.getSize());
		c.setSpatialDimensions((short) pc.getSpatialDimensions());
		if (pc.getUnits().length() > 0) {
			String size = pc.getUnits();
			if (model.getUnitDefinition(size) != null)
				c.setUnits(model.getUnitDefinition(size));
			else
				c.setUnits(Unit.Kind.valueOf(size.toUpperCase()));
		}
		addAllSBaseChangeListenersTo(c);
		return c;
	}

	/**
	 * 
	 * @param compartmentType
	 * @return
	 */
	private CompartmentType readCompartmentType(Object compartmentType) {
		if (!(compartmentType instanceof PluginCompartmentType))
			throw new IllegalArgumentException("compartmentType" + error
					+ "PluginCompartmentType");
		PluginCompartmentType comp = (PluginCompartmentType) compartmentType;
		CompartmentType com = new CompartmentType(comp.getId(), level, version);
		copyNamedSBaseProperties(com, comp);
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
		if (constraint.getMath() != null)
			c.setMath(convert(libsbml.parseFormula(constraint.getMath()), c));
		if (constraint.getMessage().length() > 0)
			c.setMessage(constraint.getMessage());
		return c;
	}

	/**
	 * 
	 * @param term
	 * @return
	 */
	private CVTerm readCVTerm(Object term) {
		if (!(term instanceof org.sbml.libsbml.CVTerm)) {
			throw new IllegalArgumentException("term" + error
					+ "org.sbml.libsbml.CVTerm.");
		}
		org.sbml.libsbml.CVTerm libCVt = (org.sbml.libsbml.CVTerm) term;
		CVTerm t = new CVTerm();
		switch (libCVt.getQualifierType()) {
		case libsbmlConstants.MODEL_QUALIFIER:
			t.setQualifierType(CVTerm.Type.MODEL_QUALIFIER);
			switch (libCVt.getModelQualifierType()) {
			case libsbmlConstants.BQM_IS:
				t.setModelQualifierType(Qualifier.BQM_IS);
				break;
			case libsbmlConstants.BQM_IS_DESCRIBED_BY:
				t.setModelQualifierType(Qualifier.BQM_IS_DESCRIBED_BY);
				break;
			case libsbmlConstants.BQM_UNKNOWN:
				t.setModelQualifierType(Qualifier.BQM_UNKNOWN);
				break;
			default:
				break;
			}
			break;
		case libsbmlConstants.BIOLOGICAL_QUALIFIER:
			t.setQualifierType(CVTerm.Type.BIOLOGICAL_QUALIFIER);
			switch (libCVt.getBiologicalQualifierType()) {
			case libsbmlConstants.BQB_ENCODES:
				t.setBiologicalQualifierType(Qualifier.BQB_ENCODES);
				break;
			case libsbmlConstants.BQB_HAS_PART:
				t.setBiologicalQualifierType(Qualifier.BQB_HAS_PART);
				break;
			case libsbmlConstants.BQB_HAS_VERSION:
				t.setBiologicalQualifierType(Qualifier.BQB_HAS_VERSION);
				break;
			case libsbmlConstants.BQB_IS:
				t.setBiologicalQualifierType(Qualifier.BQB_IS);
				break;
			case libsbmlConstants.BQB_IS_DESCRIBED_BY:
				t.setBiologicalQualifierType(Qualifier.BQB_IS_DESCRIBED_BY);
				break;
			case libsbmlConstants.BQB_IS_ENCODED_BY:
				t.setBiologicalQualifierType(Qualifier.BQB_IS_ENCODED_BY);
				break;
			case libsbmlConstants.BQB_IS_HOMOLOG_TO:
				t.setBiologicalQualifierType(Qualifier.BQB_IS_HOMOLOG_TO);
				break;
			case libsbmlConstants.BQB_IS_PART_OF:
				t.setBiologicalQualifierType(Qualifier.BQB_IS_PART_OF);
				break;
			case libsbmlConstants.BQB_IS_VERSION_OF:
				t.setBiologicalQualifierType(Qualifier.BQB_IS_VERSION_OF);
				break;
			case libsbmlConstants.BQB_OCCURS_IN:
				t.setBiologicalQualifierType(Qualifier.BQB_OCCURS_IN);
				break;
			case libsbmlConstants.BQB_UNKNOWN:
				t.setBiologicalQualifierType(Qualifier.BQB_UNKNOWN);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		for (int j = 0; j < libCVt.getNumResources(); j++) {
			t.addResourceURI(libCVt.getResourceURI(j));
		}
		return t;
	}

	/**
	 * 
	 * @param delay
	 * @return
	 */
	private Delay readDelay(org.sbml.libsbml.Delay delay) {
		Delay de = new Delay(level, version);
		if (delay.getNotesString().length() > 0)
			de.setNotes(delay.getNotesString());
		if (delay.isSetMath())
			de.setMath(convert(delay.getMath(), de));
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
		if (event.getDelay() != null)
			e.setDelay(readDelay(event.getDelay()));
		for (int i = 0; i < event.getNumEventAssignments(); i++)
			e.addEventAssignment(readEventAssignment(event
					.getEventAssignment(i)));
		if (event.getTimeUnits().length() > 0) {
			String st = event.getTimeUnits();
			if (model.getUnitDefinition(st) != null)
				e.setTimeUnits(model.getUnitDefinition(st));
			else
				e.setTimeUnits(st);
		}
		if (event.getTrigger() != null)
			e.setTrigger(readTrigger(event.getTrigger()));
		e.setUseValuesFromTriggerTime(event.getUseValuesFromTriggerTime());
		addAllSBaseChangeListenersTo(e);
		return e;
	}

	/**
	 * 
	 * @param eventass
	 * @return
	 */
	private EventAssignment readEventAssignment(Object eventass) {
		if (!(eventass instanceof PluginEventAssignment))
			throw new IllegalArgumentException("eventass" + error
					+ "PluginEventAssignment");
		PluginEventAssignment plugEveAss = (PluginEventAssignment) eventass;
		EventAssignment ev = new EventAssignment(level, version);
		copySBaseProperties(ev, plugEveAss);
		if (plugEveAss.getVariable() != null
				&& plugEveAss.getVariable().length() > 0)
			ev.setVariable(model.findVariable(plugEveAss.getVariable()));
		if (plugEveAss.getMath() != null)
			ev.setMath(convert(plugEveAss.getMath(), ev));
		addAllSBaseChangeListenersTo(ev);
		return ev;
	}

	/**
	 * 
	 * @param functionDefinition
	 * @return
	 */
	private FunctionDefinition readFunctionDefinition(Object functionDefinition) {
		if (!(functionDefinition instanceof PluginFunctionDefinition))
			throw new IllegalArgumentException("functionDefinition" + error
					+ "PluginFunctionDefinition.");
		PluginFunctionDefinition fd = (PluginFunctionDefinition) functionDefinition;
		FunctionDefinition f = new FunctionDefinition(fd.getId(), level,
				version);
		copyNamedSBaseProperties(f, fd);
		if (fd.getMath() != null)
			f.setMath(convert(fd.getMath(), f));
		addAllSBaseChangeListenersTo(f);
		return f;
	}

	/**
	 * 
	 * @param initialAssignment
	 * @return
	 */
	private InitialAssignment readInitialAssignment(Object initialAssignment) {
		if (!(initialAssignment instanceof PluginInitialAssignment))
			throw new IllegalArgumentException("initialAssignment" + error
					+ "PluginInitialAssignment.");
		PluginInitialAssignment sbIA = (PluginInitialAssignment) initialAssignment;
		if (sbIA.getSymbol() == null)
			throw new IllegalArgumentException(
					"Symbol attribute not set for InitialAssignment");
		InitialAssignment ia = new InitialAssignment(model.findVariable(sbIA
				.getSymbol()));
		copySBaseProperties(ia, sbIA);
		if (sbIA.getMath() != null)
			ia.setMath(convert(libsbml.parseFormula(sbIA.getMath()), ia));
		addAllSBaseChangeListenersTo(ia);
		return ia;
	}

	/**
	 * 
	 * @param kineticLaw
	 * @return
	 */
	private KineticLaw readKineticLaw(Object kineticLaw) {
		if (!(kineticLaw instanceof PluginKineticLaw))
			throw new IllegalArgumentException("kineticLaw" + error
					+ "PluginKineticLaw.");
		PluginKineticLaw plukinlaw = (PluginKineticLaw) kineticLaw;
		KineticLaw kinlaw = new KineticLaw(level, version);
		for (int i = 0; i < plukinlaw.getNumParameters(); i++)
			kinlaw.addParameter(readLocalParameter(plukinlaw.getParameter(i)));
		if (plukinlaw.getMath() != null)
			kinlaw.setMath(convert(plukinlaw.getMath(), kinlaw));
		else if (plukinlaw.getFormula().length() > 0)
			kinlaw.setMath(convert(libsbml.readMathMLFromString(plukinlaw
					.getFormula()), kinlaw));
		addAllSBaseChangeListenersTo(kinlaw);
		return kinlaw;
	}

	/**
	 * 
	 * @param parameter
	 * @return
	 */
	private LocalParameter readLocalParameter(PluginParameter parameter) {
		if (!(parameter instanceof PluginParameter))
			throw new IllegalArgumentException("parameter" + error
					+ "PluginParameter.");
		PluginParameter pp = (PluginParameter) parameter;
		LocalParameter para = new LocalParameter(pp.getId(), level, version);
		copyNamedSBaseProperties(para, pp);
		if (pp.getUnits().length() > 0) {
			String substance = pp.getUnits();
			if (model.getUnitDefinition(substance) != null)
				para.setUnits(model.getUnitDefinition(substance));
			else
				para.setUnits(Unit.Kind.valueOf(substance.toUpperCase()));
		}
		para.setValue(pp.getValue());
		addAllSBaseChangeListenersTo(para);
		return para;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBMLReader#readModel(java.lang.Object)
	 */
	public Model convertModel(Object model) {
		if (!(model instanceof PluginModel)) {
			throw new IllegalArgumentException("model" + error
					+ "PluginModel but received "
					+ model.getClass().getSimpleName());
		}
		int i;
		this.originalmodel = (PluginModel) model;
		this.model = new Model(originalmodel.getId(), level, version);
		for (i = 0; i < originalmodel.getNumFunctionDefinitions(); i++) {
			this.model
					.addFunctionDefinition(readFunctionDefinition(originalmodel
							.getFunctionDefinition(i)));
		}
		for (i = 0; i < originalmodel.getNumUnitDefinitions(); i++) {
			this.model.addUnitDefinition(readUnitDefinition(originalmodel
					.getUnitDefinition(i)));
		}
		for (i = 0; i < originalmodel.getNumCompartmentTypes(); i++) {
			this.model.addCompartmentType(readCompartmentType(originalmodel
					.getCompartmentType(i)));
		}
		for (i = 0; i < originalmodel.getNumSpeciesTypes(); i++) {
			this.model.addSpeciesType(readSpeciesType(originalmodel
					.getSpeciesType(i)));
		}
		for (i = 0; i < originalmodel.getNumCompartments(); i++) {
			this.model.addCompartment(readCompartment(originalmodel
					.getCompartment(i)));
		}
		for (i = 0; i < originalmodel.getNumSpecies(); i++) {
			this.model.addSpecies(readSpecies(originalmodel.getSpecies(i)));
		}
		for (i = 0; i < originalmodel.getNumParameters(); i++) {
			this.model
					.addParameter(readParameter(originalmodel.getParameter(i)));
		}
		for (i = 0; i < originalmodel.getNumInitialAssignments(); i++) {
			this.model.addInitialAssignment(readInitialAssignment(originalmodel
					.getInitialAssignment(i)));
		}
		for (i = 0; i < originalmodel.getNumRules(); i++) {
			this.model.addRule(readRule(originalmodel.getRule(i)));
		}
		for (i = 0; i < originalmodel.getNumConstraints(); i++) {
			this.model.addConstraint(readConstraint(originalmodel
					.getConstraint(i)));
		}
		for (i = 0; i < originalmodel.getNumReactions(); i++) {
			this.model.addReaction(readReaction(originalmodel.getReaction(i)));
		}
		for (i = 0; i < originalmodel.getNumEvents(); i++) {
			this.model.addEvent(readEvent(originalmodel.getEvent(i)));
		}
		addAllSBaseChangeListenersTo(this.model);
		return this.model;
	}

	/**
	 * 
	 * @param modifierSpeciesReference
	 * @return
	 */
	private ModifierSpeciesReference readModifierSpeciesReference(
			Object modifierSpeciesReference) {
		if (!(modifierSpeciesReference instanceof PluginModifierSpeciesReference)) {
			throw new IllegalArgumentException("modifierSpeciesReference"
					+ error + "PluginModifierSpeciesReference.");
		}
		PluginModifierSpeciesReference plumod = (PluginModifierSpeciesReference) modifierSpeciesReference;
		ModifierSpeciesReference mod = new ModifierSpeciesReference(
				new Species(plumod.getSpeciesInstance().getId(), level, version));
		copyNamedSBaseProperties(mod, plumod);
		/*
		 * Set SBO term.
		 */
		mod.setSBOTerm(SBO.convertAlias2SBO(plumod.getModificationType()));
		if (SBO.isCatalyst(mod.getSBOTerm())) {
			PluginSpecies species = plumod.getSpeciesInstance();
			String speciesAliasType = species.getSpeciesAlias(0).getType()
					.equals("PROTEIN") ? species.getSpeciesAlias(0)
					.getProtein().getType() : species.getSpeciesAlias(0)
					.getType();
			int sbo = SBO.convertAlias2SBO(speciesAliasType);
			if (possibleEnzymes.contains(Integer.valueOf(sbo))) {
				mod.setSBOTerm(SBO.getEnzymaticCatalysis());
			}
		}
		mod.setSpecies(model.getSpecies(plumod.getSpecies()));
		addAllSBaseChangeListenersTo(mod);
		return mod;
	}

	/**
	 * 
	 * @param parameter
	 * @return
	 */
	private Parameter readParameter(Object parameter) {
		if (!(parameter instanceof PluginParameter)) {
			throw new IllegalArgumentException("parameter" + error
					+ "PluginParameter.");
		}
		PluginParameter pp = (PluginParameter) parameter;
		Parameter para = new Parameter(pp.getId(), level, version);
		copyNamedSBaseProperties(para, pp);
		para.setConstant(pp.getConstant());
		if (pp.getUnits().length() > 0) {
			String substance = pp.getUnits();
			if (model.getUnitDefinition(substance) != null) {
				para.setUnits(model.getUnitDefinition(substance));
			} else {
				para.setUnits(Unit.Kind.valueOf(substance.toUpperCase()));
			}
		}
		para.setValue(pp.getValue());
		addAllSBaseChangeListenersTo(para);
		return para;
	}

	/**
	 * 
	 * @param reac
	 * @return
	 */
	private Reaction readReaction(Object reac) {
		if (!(reac instanceof PluginReaction)) {
			throw new IllegalArgumentException("reaction" + error
					+ "PluginReaction");
		}
		PluginReaction r = (PluginReaction) reac;
		Reaction reaction = new Reaction(r.getId(), level, version);
		for (int i = 0; i < r.getNumReactants(); i++) {
			reaction.addReactant(readSpeciesReference(r.getReactant(i)));
		}
		for (int i = 0; i < r.getNumProducts(); i++)
			reaction.addProduct(readSpeciesReference(r.getProduct(i)));
		for (int i = 0; i < r.getNumModifiers(); i++)
			reaction
					.addModifier(readModifierSpeciesReference(r.getModifier(i)));
		int sbo = SBO.convertAlias2SBO(r.getReactionType());
		if (SBO.checkTerm(sbo))
			reaction.setSBOTerm(sbo);
		if (r.getKineticLaw() != null)
			reaction.setKineticLaw(readKineticLaw(r.getKineticLaw()));
		reaction.setFast(r.getFast());
		reaction.setReversible(r.getReversible());
		addAllSBaseChangeListenersTo(reaction);
		return reaction;
	}

	/**
	 * 
	 * @param rule
	 * @return
	 */
    private Rule readRule(Object rule) {
	if (!(rule instanceof PluginRule)) {
	    throw new IllegalArgumentException("rule" + error + "PluginRule.");
	}
	PluginRule libRule = (PluginRule) rule;
	Rule r;
	if (libRule instanceof PluginAlgebraicRule) {
	    r = new AlgebraicRule(level, version);
	} else {
	    if (libRule instanceof PluginAssignmentRule) {
		r = new AssignmentRule(model
			.findVariable(((PluginAssignmentRule) libRule)
				.getVariable()));
	    } else {
		r = new RateRule(model.findVariable(((PluginRateRule) libRule)
			.getVariable()));
	    }
	}
	copySBaseProperties(r, libRule);
	if (libRule.getMath() != null) {
	    r.setMath(convert(libRule.getMath(), r));
	}
	addAllSBaseChangeListenersTo(r);
	return r;
    }

	/**
	 * 
	 * @param species
	 * @return
	 */
	private Species readSpecies(Object species) {
		if (!(species instanceof PluginSpecies))
			throw new IllegalArgumentException("species" + error
					+ "PluginSpecies");
		PluginSpecies spec = (PluginSpecies) species;
		Species s = new Species(spec.getId(), level, version);
		copyNamedSBaseProperties(s, spec);
		int sbo = SBO.convertAlias2SBO(spec.getSpeciesAlias(0).getType());
		PluginSpeciesAlias alias = spec.getSpeciesAlias(0);
		String type = alias.getType();
		if (alias.getType().equals(PluginSpeciesSymbolType.PROTEIN))
			type = alias.getProtein().getType();
		sbo = SBO.convertAlias2SBO(type);
		if (SBO.checkTerm(sbo))
			s.setSBOTerm(sbo);
		s.setBoundaryCondition(spec.getBoundaryCondition());
		if (spec.getCompartment().length() > 0)
			s.setCompartment(model.getCompartment(spec.getCompartment()));
		s.setCharge(spec.getCharge());
		s.setConstant(spec.getConstant());
		s.setHasOnlySubstanceUnits(spec.getHasOnlySubstanceUnits());
		if (spec.isSetInitialAmount())
			s.setInitialAmount(spec.getInitialAmount());
		else if (spec.isSetInitialConcentration())
			s.setInitialConcentration(spec.getInitialConcentration());
		// before L2V3...
		spec.getSpatialSizeUnits();
		if (spec.getSpeciesType().length() > 0)
			s.setSpeciesType(model.getSpeciesType(spec.getSpeciesType()));
		if (spec.getSubstanceUnits().length() > 0) {
			String substance = spec.getSubstanceUnits();
			if (model.getUnitDefinition(substance) != null)
				s.setSubstanceUnits(model.getUnitDefinition(substance));
			else
				s.setSubstanceUnits(Unit.Kind.valueOf(substance.toUpperCase()));
		} else if (spec.getUnits().length() > 0) {
			String substance = spec.getUnits();
			if (model.getUnitDefinition(substance) != null)
				s.setSubstanceUnits(model.getUnitDefinition(substance));
			else
				s.setSubstanceUnits(Unit.Kind.valueOf(substance.toUpperCase()));
		}
		addAllSBaseChangeListenersTo(s);
		return s;
	}

	/**
	 * 
	 * @param speciesReference
	 * @return
	 */
	private SpeciesReference readSpeciesReference(Object speciesReference) {
		if (!(speciesReference instanceof PluginSpeciesReference))
			throw new IllegalArgumentException("speciesReference" + error
					+ "PluginSpeciesReference.");
		PluginSpeciesReference specref = (PluginSpeciesReference) speciesReference;
		SpeciesReference spec = new SpeciesReference(new Species(specref
				.getSpeciesInstance().getId(), level, version));
		copyNamedSBaseProperties(spec, specref);
		if (specref.getStoichiometryMath() == null)
			spec.setStoichiometry(specref.getStoichiometry());
		else
			spec.setStoichiometryMath(readStoichiometricMath(specref
					.getStoichiometryMath()));
		if (specref.getReferenceType().length() > 0) {
			int sbo = SBO.convertAlias2SBO(specref.getReferenceType());
			if (SBO.checkTerm(sbo))
				spec.setSBOTerm(sbo);
		}
		spec.setSpecies(model.getSpecies(specref.getSpecies()));
		addAllSBaseChangeListenersTo(spec);
		return spec;
	}

	/**
	 * 
	 * @param speciesType
	 * @return
	 */
	private SpeciesType readSpeciesType(Object speciesType) {
		if (!(speciesType instanceof PluginSpeciesType))
			throw new IllegalArgumentException("speciesType" + error
					+ "PluginSpeciesType.");
		PluginSpeciesType libST = (PluginSpeciesType) speciesType;
		SpeciesType st = new SpeciesType(libST.getId(), level, version);
		copyNamedSBaseProperties(st, libST);
		addAllSBaseChangeListenersTo(st);
		return st;
	}

	/**
	 * 
	 * @param stoichiometryMath
	 * @return
	 */
	private StoichiometryMath readStoichiometricMath(Object stoichiometryMath) {
		if (!(stoichiometryMath instanceof org.sbml.libsbml.StoichiometryMath))
			throw new IllegalArgumentException("stoichiometryMath" + error
					+ "org.sbml.libsbml.StoichiometryMath");
		org.sbml.libsbml.StoichiometryMath sm = (org.sbml.libsbml.StoichiometryMath) stoichiometryMath;
		StoichiometryMath s = new StoichiometryMath(level, version);
		s.setMath(convert(sm.getMath(), s));
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
		if (trigger.getNotesString().length() > 0)
			trig.setNotes(trigger.getNotesString());
		if (trigger.isSetMath())
			trig.setMath(convert(trigger.getMath(), trig));
		return trig;
	}

	/**
	 * 
	 * @param unit
	 * @return
	 */
    private Unit readUnit(Object unit) {
	if (!(unit instanceof PluginUnit))
	    throw new IllegalArgumentException("unit" + error + "PluginUnit");
	PluginUnit libUnit = (PluginUnit) unit;
	Unit u = new Unit(level, version);
	copySBaseProperties(u, libUnit);
	switch (libUnit.getKind()) {
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
	u.setExponent(libUnit.getExponent());
	u.setMultiplier(libUnit.getMultiplier());
	u.setScale(libUnit.getScale());
	if (u.isSetOffset()) {
	    u.setOffset(libUnit.getOffset());
	}
	addAllSBaseChangeListenersTo(u);
	return u;
    }

	/**
	 * 
	 * @param unitDefinition
	 * @return
	 */
	private UnitDefinition readUnitDefinition(Object unitDefinition) {
		if (!(unitDefinition instanceof PluginUnitDefinition))
			throw new IllegalArgumentException("unitDefinition" + error
					+ "PluginUnitDefinition");
		PluginUnitDefinition libUD = (PluginUnitDefinition) unitDefinition;
		UnitDefinition ud = new UnitDefinition(libUD.getId(), level, version);
		copyNamedSBaseProperties(ud, libUD);
		for (int i = 0; i < libUD.getNumUnits(); i++)
			ud.addUnit(readUnit(libUD.getUnit(i)));
		addAllSBaseChangeListenersTo(ud);
		return ud;
	}
}

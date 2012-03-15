/*
 * $Id: LibSBMLWriter.java 102 2009-12-13 19:52:50Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/io/LibSBMLWriter.java $
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

package org.sbml.jsbml.xml.libsbml;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AlgebraicRule;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.CompartmentType;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.ExplicitRule;
import org.sbml.jsbml.FunctionDefinition;
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
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLOutputConverter;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.StoichiometryMath;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.IOProgressListener;
import org.sbml.libsbml.libsbmlConstants;

/**
 * @author Andreas Dr&auml;ger
 */
@SuppressWarnings("deprecation")
public class LibSBMLWriter implements SBMLOutputConverter {

	private static final String error = " must be an instance of org.sbml.libsbml.";
	private Set<IOProgressListener> setIOListeners = new HashSet<IOProgressListener>();
	private org.sbml.libsbml.Model modelOrig;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBMLWriter#addEventListener(java.util.EventListener)
	 */
	public void addIOProgressListener(IOProgressListener listener) {
		setIOListeners.add(listener);
	}

//	/**
//	 * 
//	 * @param ast
//	 * @return
//	 */
//	private org.sbml.libsbml.ASTNode convert(ASTNode astnode) {
//		org.sbml.libsbml.ASTNode libAstNode;
//		switch (astnode.getType()) {
//		case REAL:
//			libAstNode = new org.sbml.libsbml.ASTNode(libsbmlConstants.AST_REAL);
//			libAstNode.setValue(astnode.getReal());
//			break;
//		case INTEGER:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_INTEGER);
//			libAstNode.setValue(astnode.getInteger());
//			break;
//		case FUNCTION_LOG:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_LOG);
//			break;
//		case POWER:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_POWER);
//			break;
//		case PLUS:
//			libAstNode = new org.sbml.libsbml.ASTNode(libsbmlConstants.AST_PLUS);
//			break;
//		case MINUS:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_MINUS);
//			break;
//		case TIMES:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_TIMES);
//			break;
//		case DIVIDE:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_DIVIDE);
//			break;
//		case RATIONAL:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_RATIONAL);
//			break;
//		case NAME_TIME:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_NAME_TIME);
//			break;
//		case FUNCTION_DELAY:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_DELAY);
//			break;
//		case NAME:
//			libAstNode = new org.sbml.libsbml.ASTNode(libsbmlConstants.AST_NAME);
//			libAstNode.setName(astnode.getName());
//			break;
//		case CONSTANT_PI:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_CONSTANT_PI);
//			break;
//		case CONSTANT_E:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_CONSTANT_E);
//			break;
//		case CONSTANT_TRUE:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_CONSTANT_TRUE);
//			break;
//		case CONSTANT_FALSE:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_CONSTANT_FALSE);
//			break;
//		case REAL_E:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_REAL_E);
//			libAstNode.setValue(astnode.getMantissa(), astnode.getExponent());
//			break;
//		case FUNCTION_ABS:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_ABS);
//			break;
//		case FUNCTION_ARCCOS:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_ARCCOS);
//			break;
//		case FUNCTION_ARCCOSH:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_ARCCOSH);
//			break;
//		case FUNCTION_ARCCOT:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_ARCCOT);
//			break;
//		case FUNCTION_ARCCOTH:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_ARCCOTH);
//			break;
//		case FUNCTION_ARCCSC:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_ARCCSC);
//			break;
//		case FUNCTION_ARCCSCH:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_ARCCSCH);
//			break;
//		case FUNCTION_ARCSEC:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_ARCSEC);
//			break;
//		case FUNCTION_ARCSECH:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_ARCSECH);
//			break;
//		case FUNCTION_ARCSIN:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_ARCSIN);
//			break;
//		case FUNCTION_ARCSINH:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_ARCSINH);
//			break;
//		case FUNCTION_ARCTAN:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_ARCTAN);
//			break;
//		case FUNCTION_ARCTANH:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_ARCTANH);
//			break;
//		case FUNCTION_CEILING:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_CEILING);
//			break;
//		case FUNCTION_COS:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_COS);
//			break;
//		case FUNCTION_COSH:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_COSH);
//			break;
//		case FUNCTION_COT:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_COT);
//			break;
//		case FUNCTION_COTH:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_COTH);
//			break;
//		case FUNCTION_CSC:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_CSC);
//			break;
//		case FUNCTION_CSCH:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_CSCH);
//			break;
//		case FUNCTION_EXP:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_EXP);
//			break;
//		case FUNCTION_FACTORIAL:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_FACTORIAL);
//			break;
//		case FUNCTION_FLOOR:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_FLOOR);
//			break;
//		case FUNCTION_LN:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_LN);
//			break;
//		case FUNCTION_POWER:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_POWER);
//			break;
//		case FUNCTION_ROOT:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_ROOT);
//			break;
//		case FUNCTION_SEC:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_SEC);
//			break;
//		case FUNCTION_SECH:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_SECH);
//			break;
//		case FUNCTION_SIN:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_SIN);
//			break;
//		case FUNCTION_SINH:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_SINH);
//			break;
//		case FUNCTION_TAN:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_TAN);
//			break;
//		case FUNCTION_TANH:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_TANH);
//			break;
//		case FUNCTION:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION);
//			libAstNode.setName(astnode.getName());
//			break;
//		case LAMBDA:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_LAMBDA);
//			break;
//		case LOGICAL_AND:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_LOGICAL_AND);
//			break;
//		case LOGICAL_XOR:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_LOGICAL_XOR);
//			break;
//		case LOGICAL_OR:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_LOGICAL_OR);
//			break;
//		case LOGICAL_NOT:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_LOGICAL_NOT);
//			break;
//		case FUNCTION_PIECEWISE:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_FUNCTION_PIECEWISE);
//			break;
//		case RELATIONAL_EQ:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_RELATIONAL_EQ);
//			break;
//		case RELATIONAL_GEQ:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_RELATIONAL_GEQ);
//			break;
//		case RELATIONAL_GT:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_RELATIONAL_GT);
//			break;
//		case RELATIONAL_NEQ:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_RELATIONAL_NEQ);
//			break;
//		case RELATIONAL_LEQ:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_RELATIONAL_LEQ);
//			break;
//		case RELATIONAL_LT:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_RELATIONAL_LT);
//			break;
//		default:
//			libAstNode = new org.sbml.libsbml.ASTNode(
//					libsbmlConstants.AST_UNKNOWN);
//			break;
//		}
//		for (ASTNode child : astnode.getListOfNodes())
//			libAstNode.addChild(convert(child));
//		return libAstNode;
//	}

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.sbml.jlibsbml.SBMLWriter#convertDate(java.util.Date)
//	 */
//	private org.sbml.libsbml.Date convertDate(Date date) {
//		Calendar c = Calendar.getInstance();
//		c.setTime(date);
//		return new org.sbml.libsbml.Date(c.get(Calendar.YEAR), c
//				.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), c
//				.get(Calendar.HOUR), c.get(Calendar.MINUTE), c
//				.get(Calendar.SECOND), (int) Math.signum(c.getTimeZone()
//				.getRawOffset()), c.getTimeZone().getRawOffset() / 3600000, c
//				.getTimeZone().getRawOffset() / 60000);
//	}

	/**
	 * Determins whether the two ASTNode objects are equal.
	 * 
	 * @param math
	 * @param libMath
	 * @return
	 */
	private boolean equal(ASTNode math, org.sbml.libsbml.ASTNode libMath) {
		if (math == null || libMath == null)
			return false;
		boolean equal = true;
		switch (math.getType()) {
		case REAL:
			equal &= libMath.getType() == libsbmlConstants.AST_REAL;
			equal &= libMath.getReal() == math.getReal();
			break;
		case INTEGER:
			equal &= libMath.getType() == libsbmlConstants.AST_INTEGER;
			equal &= libMath.getInteger() == math.getInteger();
			break;
		case FUNCTION_LOG:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_LOG;
			break;
		case POWER:
			equal &= libMath.getType() == libsbmlConstants.AST_POWER;
			break;
		case PLUS:
			equal &= libMath.getType() == libsbmlConstants.AST_PLUS;
			break;
		case MINUS:
			equal &= libMath.getType() == libsbmlConstants.AST_MINUS;
			break;
		case TIMES:
			equal &= libMath.getType() == libsbmlConstants.AST_TIMES;
			break;
		case DIVIDE:
			equal &= libMath.getType() == libsbmlConstants.AST_DIVIDE;
			break;
		case RATIONAL:
			equal &= libMath.getType() == libsbmlConstants.AST_RATIONAL;
			break;
		case NAME_TIME:
			equal &= libMath.getType() == libsbmlConstants.AST_NAME_TIME;
			break;
		case FUNCTION_DELAY:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_DELAY;
			break;
		case NAME:
			equal &= libMath.getType() == libsbmlConstants.AST_NAME;
			equal &= math.getName().equals(libMath.getName());
			break;
		case CONSTANT_PI:
			equal &= libMath.getType() == libsbmlConstants.AST_CONSTANT_PI;
			break;
		case CONSTANT_E:
			equal &= libMath.getType() == libsbmlConstants.AST_CONSTANT_E;
			break;
		case CONSTANT_TRUE:
			equal &= libMath.getType() == libsbmlConstants.AST_CONSTANT_TRUE;
			break;
		case CONSTANT_FALSE:
			equal &= libMath.getType() == libsbmlConstants.AST_CONSTANT_FALSE;
			break;
		case REAL_E:
			equal &= libMath.getType() == libsbmlConstants.AST_REAL_E;
			equal &= libMath.getMantissa() == math.getMantissa();
			equal &= libMath.getExponent() == math.getExponent();
			break;
		case FUNCTION_ABS:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_ABS;
			break;
		case FUNCTION_ARCCOS:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_ARCCOS;
			break;
		case FUNCTION_ARCCOSH:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_ARCCOSH;
			break;
		case FUNCTION_ARCCOT:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_ARCCOT;
			break;
		case FUNCTION_ARCCOTH:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_ARCCOTH;
			break;
		case FUNCTION_ARCCSC:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_ARCCSC;
			break;
		case FUNCTION_ARCCSCH:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_ARCCSCH;
			break;
		case FUNCTION_ARCSEC:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_ARCSEC;
			break;
		case FUNCTION_ARCSECH:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_ARCSECH;
			break;
		case FUNCTION_ARCSIN:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_ARCSIN;
			break;
		case FUNCTION_ARCSINH:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_ARCSINH;
			break;
		case FUNCTION_ARCTAN:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_ARCTAN;
			break;
		case FUNCTION_ARCTANH:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_ARCTANH;
			break;
		case FUNCTION_CEILING:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_CEILING;
			break;
		case FUNCTION_COS:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_COS;
			break;
		case FUNCTION_COSH:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_COSH;
			break;
		case FUNCTION_COT:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_COT;
			break;
		case FUNCTION_COTH:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_COTH;
			break;
		case FUNCTION_CSC:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_CSC;
			break;
		case FUNCTION_CSCH:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_CSCH;
			break;
		case FUNCTION_EXP:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_EXP;
			break;
		case FUNCTION_FACTORIAL:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_FACTORIAL;
			break;
		case FUNCTION_FLOOR:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_FLOOR;
			break;
		case FUNCTION_LN:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_LN;
			break;
		case FUNCTION_POWER:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_POWER;
			break;
		case FUNCTION_ROOT:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_ROOT;
			break;
		case FUNCTION_SEC:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_SEC;
			break;
		case FUNCTION_SECH:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_SECH;
			break;
		case FUNCTION_SIN:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_SIN;
			break;
		case FUNCTION_SINH:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_SINH;
			break;
		case FUNCTION_TAN:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_TAN;
			break;
		case FUNCTION_TANH:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_TANH;
			break;
		case FUNCTION:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION;
			equal &= libMath.getName().equals(math.getName());
			break;
		case LAMBDA:
			equal &= libMath.getType() == libsbmlConstants.AST_LAMBDA;
			break;
		case LOGICAL_AND:
			equal &= libMath.getType() == libsbmlConstants.AST_LOGICAL_AND;
			break;
		case LOGICAL_XOR:
			equal &= libMath.getType() == libsbmlConstants.AST_LOGICAL_XOR;
			break;
		case LOGICAL_OR:
			equal &= libMath.getType() == libsbmlConstants.AST_LOGICAL_OR;
			break;
		case LOGICAL_NOT:
			equal &= libMath.getType() == libsbmlConstants.AST_LOGICAL_NOT;
			break;
		case FUNCTION_PIECEWISE:
			equal &= libMath.getType() == libsbmlConstants.AST_FUNCTION_PIECEWISE;
			break;
		case RELATIONAL_EQ:
			equal &= libMath.getType() == libsbmlConstants.AST_RELATIONAL_EQ;
			break;
		case RELATIONAL_GEQ:
			equal &= libMath.getType() == libsbmlConstants.AST_RELATIONAL_GEQ;
			break;
		case RELATIONAL_GT:
			equal &= libMath.getType() == libsbmlConstants.AST_RELATIONAL_GT;
			break;
		case RELATIONAL_NEQ:
			equal &= libMath.getType() == libsbmlConstants.AST_RELATIONAL_NEQ;
			break;
		case RELATIONAL_LEQ:
			equal &= libMath.getType() == libsbmlConstants.AST_RELATIONAL_LEQ;
			break;
		case RELATIONAL_LT:
			equal &= libMath.getType() == libsbmlConstants.AST_RELATIONAL_LT;
			break;
		default:
			equal &= libMath.getType() == libsbmlConstants.AST_UNKNOWN;
			break;
		}
		equal &= math.getNumChildren() == libMath.getNumChildren();
		if (equal && math.getNumChildren() > 0)
			for (int i = 0; i < math.getNumChildren(); i++)
				equal &= equal(math.getChild(i), libMath.getChild(i));
		return equal;
	}

	/**
	 * Checks wheter these two units are identical.
	 * 
	 * @param u
	 * @param unit
	 * @return
	 */
	private boolean equal(Unit u, org.sbml.libsbml.Unit unit) {
		if (u == null || unit == null)
			return false;
		boolean equal = true;
		switch (unit.getKind()) {
		case libsbmlConstants.UNIT_KIND_AMPERE:
			equal &= u.getKind() == Unit.Kind.AMPERE;
			break;
		case libsbmlConstants.UNIT_KIND_BECQUEREL:
			equal &= u.getKind() == Unit.Kind.BECQUEREL;
			break;
		case libsbmlConstants.UNIT_KIND_CANDELA:
			equal &= u.getKind() == Unit.Kind.CANDELA;
			break;
		case libsbmlConstants.UNIT_KIND_CELSIUS:
			equal &= u.getKind() == Unit.Kind.CELSIUS;
			break;
		case libsbmlConstants.UNIT_KIND_COULOMB:
			equal &= u.getKind() == Unit.Kind.COULOMB;
			break;
		case libsbmlConstants.UNIT_KIND_DIMENSIONLESS:
			equal &= u.getKind() == Unit.Kind.DIMENSIONLESS;
			break;
		case libsbmlConstants.UNIT_KIND_FARAD:
			equal &= u.getKind() == Unit.Kind.FARAD;
			break;
		case libsbmlConstants.UNIT_KIND_GRAM:
			equal &= u.getKind() == Unit.Kind.GRAM;
			break;
		case libsbmlConstants.UNIT_KIND_GRAY:
			equal &= u.getKind() == Unit.Kind.GRAY;
			break;
		case libsbmlConstants.UNIT_KIND_HENRY:
			equal &= u.getKind() == Unit.Kind.HENRY;
			break;
		case libsbmlConstants.UNIT_KIND_HERTZ:
			equal &= u.getKind() == Unit.Kind.HERTZ;
			break;
		case libsbmlConstants.UNIT_KIND_INVALID:
			equal &= u.getKind() == Unit.Kind.INVALID;
			break;
		case libsbmlConstants.UNIT_KIND_ITEM:
			equal &= u.getKind() == Unit.Kind.ITEM;
			break;
		case libsbmlConstants.UNIT_KIND_JOULE:
			equal &= u.getKind() == Unit.Kind.JOULE;
			break;
		case libsbmlConstants.UNIT_KIND_KATAL:
			equal &= u.getKind() == Unit.Kind.KATAL;
			break;
		case libsbmlConstants.UNIT_KIND_KELVIN:
			equal &= u.getKind() == Unit.Kind.KELVIN;
			break;
		case libsbmlConstants.UNIT_KIND_KILOGRAM:
			equal &= u.getKind() == Unit.Kind.KILOGRAM;
			break;
		case libsbmlConstants.UNIT_KIND_LITER:
			equal &= u.getKind() == Unit.Kind.LITER;
			break;
		case libsbmlConstants.UNIT_KIND_LITRE:
			equal &= u.getKind() == Unit.Kind.LITRE;
			break;
		case libsbmlConstants.UNIT_KIND_LUMEN:
			equal &= u.getKind() == Unit.Kind.LUMEN;
			break;
		case libsbmlConstants.UNIT_KIND_LUX:
			equal &= u.getKind() == Unit.Kind.LUX;
			break;
		case libsbmlConstants.UNIT_KIND_METER:
			equal &= u.getKind() == Unit.Kind.METER;
			break;
		case libsbmlConstants.UNIT_KIND_METRE:
			equal &= u.getKind() == Unit.Kind.METRE;
			break;
		case libsbmlConstants.UNIT_KIND_MOLE:
			equal &= u.getKind() == Unit.Kind.MOLE;
			break;
		case libsbmlConstants.UNIT_KIND_NEWTON:
			equal &= u.getKind() == Unit.Kind.NEWTON;
			break;
		case libsbmlConstants.UNIT_KIND_OHM:
			equal &= u.getKind() == Unit.Kind.OHM;
			break;
		case libsbmlConstants.UNIT_KIND_PASCAL:
			equal &= u.getKind() == Unit.Kind.PASCAL;
			break;
		case libsbmlConstants.UNIT_KIND_RADIAN:
			equal &= u.getKind() == Unit.Kind.RADIAN;
			break;
		case libsbmlConstants.UNIT_KIND_SECOND:
			equal &= u.getKind() == Unit.Kind.SECOND;
			break;
		case libsbmlConstants.UNIT_KIND_SIEMENS:
			equal &= u.getKind() == Unit.Kind.SIEMENS;
			break;
		case libsbmlConstants.UNIT_KIND_SIEVERT:
			equal &= u.getKind() == Unit.Kind.SIEVERT;
			break;
		case libsbmlConstants.UNIT_KIND_STERADIAN:
			equal &= u.getKind() == Unit.Kind.STERADIAN;
			break;
		case libsbmlConstants.UNIT_KIND_TESLA:
			equal &= u.getKind() == Unit.Kind.TESLA;
			break;
		case libsbmlConstants.UNIT_KIND_VOLT:
			equal &= u.getKind() == Unit.Kind.VOLT;
			break;
		case libsbmlConstants.UNIT_KIND_WATT:
			equal &= u.getKind() == Unit.Kind.WATT;
			break;
		case libsbmlConstants.UNIT_KIND_WEBER:
			equal &= u.getKind() == Unit.Kind.WEBER;
			break;
		}
		equal &= u.getExponent() == unit.getExponent();
		equal &= u.getMultiplier() == unit.getMultiplier();
		equal &= u.getScale() == unit.getScale();
		equal &= u.getOffset() == unit.getOffset();
		return equal;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jlibsbml.SBMLWriter#getNumErrors(java.lang.Object)
	 */
	public int getNumErrors(Object sbase) {
		return getErrorCount(sbase);
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.SBMLOutputConverter#getErrorCount(java.lang.Object)
	 */
	public int getErrorCount(Object sbase) {
		if (!(sbase instanceof org.sbml.libsbml.SBase))
			throw new IllegalArgumentException("sbase" + error + "SBase.");
		org.sbml.libsbml.SBMLDocument doc = ((org.sbml.libsbml.SBase) sbase)
				.getSBMLDocument();
		doc.checkConsistency();
		return (int) doc.getNumErrors();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBMLWriter#getWriteWarnings()
	 */
	public List<SBMLException> getWriteWarnings(Object sbase) {
		if (!(sbase instanceof org.sbml.libsbml.SBase))
			throw new IllegalArgumentException(
					"sbmlDocument must be an instance of org.sbml.libsbml.SBase.");
		org.sbml.libsbml.SBMLDocument doc = ((org.sbml.libsbml.SBase) sbase)
				.getSBMLDocument();
		doc.checkConsistency();
		List<SBMLException> sb = new LinkedList<SBMLException>();
		for (int i = 0; i < doc.getNumErrors(); i++)
			sb.add(LibSBMLReader.convert(doc.getError(i)));
		return sb;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.io.AbstractSBMLWriter#removeUnneccessaryElements(org.sbml
	 * .jsbml.Model, java.lang.Object)
	 */
	public void removeUnneccessaryElements(Model model, Object orig) {
		if (!(orig instanceof org.sbml.libsbml.Model))
			throw new IllegalArgumentException(
					"only instances of org.sbml.libsbml.Model can be considered.");
		org.sbml.libsbml.Model mo = (org.sbml.libsbml.Model) orig;
		long i;

		// remove unnecessary function definitions
		for (i = mo.getNumFunctionDefinitions() - 1; i >= 0; i--) {
			org.sbml.libsbml.FunctionDefinition c = mo.getFunctionDefinition(i);
			if (model.getFunctionDefinition(c.getId()) == null) {
				fireIOEvent(mo.getListOfFunctionDefinitions().remove(i));
			}
		}

		// remove unnecessary units
		for (i = mo.getNumUnitDefinitions() - 1; i >= 0; i--) {
			org.sbml.libsbml.UnitDefinition ud = mo.getUnitDefinition(i);
			if (model.getUnitDefinition(ud.getId()) == null) {
				fireIOEvent(mo.getListOfUnitDefinitions().remove(i));
			}
		}

		// remove unnecessary compartmentTypes
		for (i = mo.getNumCompartmentTypes() - 1; i >= 0; i--) {
			org.sbml.libsbml.CompartmentType c = mo.getCompartmentType(i);
			if (model.getCompartmentType(c.getId()) == null) {
				fireIOEvent(mo.getListOfCompartmentTypes().remove(i));
			}
		}

		// remove unnecessary speciesTypes
		for (i = mo.getNumSpeciesTypes() - 1; i >= 0; i--) {
			org.sbml.libsbml.SpeciesType c = mo.getSpeciesType(i);
			if (model.getSpeciesType(c.getId()) == null) {
				fireIOEvent(mo.getListOfSpeciesTypes().remove(i));
			}
		}

		// remove unnecessary compartments
		for (i = mo.getNumCompartments() - 1; i >= 0; i--) {
			org.sbml.libsbml.Compartment c = mo.getCompartment(i);
			if (model.getCompartment(c.getId()) == null) {
				fireIOEvent(mo.getListOfCompartments().remove(i));
			}
		}

		// remove unnecessary species
		for (i = mo.getNumSpecies() - 1; i >= 0; i--) {
			org.sbml.libsbml.Species s = mo.getSpecies(i);
			if (model.getSpecies(s.getId()) == null) {
				fireIOEvent(mo.getListOfSpecies().remove(i));
			}
		}

		// remove parameters
		for (i = mo.getNumParameters() - 1; i >= 0; i--) {
			org.sbml.libsbml.Parameter p = mo.getParameter(i);
			if (model.getParameter(p.getId()) == null) {
				fireIOEvent(mo.getListOfParameters().remove(i));
			}
		}

		// remove unnecessary initial assignments
		for (i = mo.getNumInitialAssignments() - 1; i >= 0; i--) {
			org.sbml.libsbml.InitialAssignment c = mo.getInitialAssignment(i);
			boolean contains = false;
			for (int j = 0; j < model.getNumInitialAssignments() && !contains; j++) {
				InitialAssignment ia = model.getInitialAssignment(j);
				if (ia.getVariable().equals(c.getSymbol())
						&& equal(ia.getMath(), c.getMath()))
					contains = true;
			}
			if (!contains) {
				fireIOEvent(mo.getListOfInitialAssignments().remove(i));
			}
		}

		// remove unnecessary rules
		for (i = mo.getNumRules() - 1; i >= 0; i--) {
			org.sbml.libsbml.Rule c = mo.getRule(i);
			boolean contains = false;
			for (int j = 0; j < model.getNumRules() && !contains; j++) {
				Rule r = model.getRule(j);
				if (((c instanceof org.sbml.libsbml.RateRule)
						&& (r instanceof RateRule) && ((org.sbml.libsbml.RateRule) c)
						.getVariable().equals(((RateRule) r).getVariable()))
						|| (c instanceof org.sbml.libsbml.AssignmentRule
								&& r instanceof AssignmentRule && ((AssignmentRule) r)
								.getVariable().equals(
										((org.sbml.libsbml.AssignmentRule) c)
												.getVariable()))
						|| ((c instanceof org.sbml.libsbml.AlgebraicRule) && (r instanceof AlgebraicRule)))
					if (equal(r.getMath(), c.getMath())) {
						contains = true;
					}
			}
			if (!contains) {
				fireIOEvent(mo.getListOfRules().remove(i));
			}
		}

		// remove unnecessary constraints
		for (i = mo.getNumConstraints() - 1; i >= 0; i--) {
			org.sbml.libsbml.Constraint c = mo.getConstraint(i);
			boolean contains = false;
			for (int j = 0; j < model.getNumConstraints() && !contains; j++) {
				Constraint ia = model.getConstraint(j);
				if (equal(ia.getMath(), c.getMath()))
					contains = true;
			}
			if (!contains) {
				fireIOEvent(mo.getListOfConstraints().remove(i));
			}
		}

		// remove reactions
		for (i = mo.getNumReactions() - 1; i >= 0; i--) {
			org.sbml.libsbml.Reaction r = mo.getReaction(i);
			if (model.getReaction(r.getId()) == null) {
				fireIOEvent(mo.getListOfReactions().remove(i));
			}
		}

		// remove events
		for (i = mo.getNumEvents() - 1; i >= 0; i--) {
			org.sbml.libsbml.Event eventOrig = mo.getEvent(i);
			if (model.getEvent(eventOrig.getId()) == null) {
				fireIOEvent(mo.getListOfEvents().remove(i));
			}
		}
		fireIOEvent(null);
	}

	/**
	 * 
	 * @param currObject
	 */
	private void fireIOEvent(Object currObject) {
		for (IOProgressListener iopl : setIOListeners) {
			iopl.ioProgressOn(currObject);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.squeezer.io.AbstractSBMLWriter#saveChanges(org.sbml.Model,
	 * java.lang.Object)
	 */
	public boolean saveChanges(Model model, Object orig) throws SBMLException {
		if (!(orig instanceof org.sbml.libsbml.Model))
			throw new IllegalArgumentException(
					"only instances of org.sbml.libsbml.Model can be considered.");
		modelOrig = (org.sbml.libsbml.Model) orig;
		long i;

		// Function definitions
		for (FunctionDefinition c : model.getListOfFunctionDefinitions()) {
			if (modelOrig.getFunctionDefinition(c.getId()) == null) {
				modelOrig.addFunctionDefinition(writeFunctionDefinition(c));
			} else {
				saveMathContainerProperties(c, modelOrig
						.getFunctionDefinition(c.getId()));
			}
			fireIOEvent(c);
		}

		// Unit definitions
		for (UnitDefinition ud : model.getListOfUnitDefinitions())
			if (!(UnitDefinition.areIdentical(ud, UnitDefinition.substance(ud
					.getLevel(), ud.getVersion())) && ud.getId().equals(
					"substance"))
					&& !(UnitDefinition.areIdentical(ud, UnitDefinition.volume(
							ud.getLevel(), ud.getVersion())) && ud.getId()
							.equals("volume"))
					&& !(UnitDefinition.areIdentical(ud, UnitDefinition.area(ud
							.getLevel(), ud.getVersion())) && ud.getId()
							.equals("area"))
					&& !(UnitDefinition.areIdentical(ud, UnitDefinition.length(
							ud.getLevel(), ud.getVersion())) && ud.getId()
							.equals("length"))
					&& !(UnitDefinition.areIdentical(ud, UnitDefinition.time(ud
							.getLevel(), ud.getVersion())) && ud.getId()
							.equals("time"))) {
				org.sbml.libsbml.UnitDefinition libU = modelOrig
						.getUnitDefinition(ud.getId());
				if (libU != null) {
					saveUnitDefinitionProperties(ud, libU);
				} else {
					modelOrig.addUnitDefinition(writeUnitDefinition(ud));
				}
				fireIOEvent(ud);
			}

		// Compartment types
		for (CompartmentType c : model.getListOfCompartmentTypes()) {
			if (modelOrig.getCompartmentType(c.getId()) == null)
				modelOrig.addCompartmentType(writeCompartmentType(c));
			else
				saveNamedSBaseProperties(c, modelOrig.getCompartmentType(c
						.getId()));
			fireIOEvent(c);
		}

		// Species types
		for (SpeciesType c : model.getListOfSpeciesTypes()) {
			if (modelOrig.getSpeciesType(c.getId()) == null) {
				modelOrig.addSpeciesType(writeSpeciesType(c));
			} else {
				saveNamedSBaseProperties(c, modelOrig.getSpeciesType(c.getId()));
			}
			fireIOEvent(c);
		}

		// Compartments
		for (Compartment c : model.getListOfCompartments()) {
			if (modelOrig.getCompartment(c.getId()) == null) {
				modelOrig.addCompartment(writeCompartment(c));
			} else {
				saveCompartmentProperties(c, modelOrig
						.getCompartment(c.getId()));
			}
			fireIOEvent(c);
		}

		// Species
		for (Species s : model.getListOfSpecies()) {
			if (modelOrig.getSpecies(s.getId()) == null) {
				modelOrig.addSpecies(writeSpecies(s));
			} else {
				saveSpeciesProperties(s, modelOrig.getSpecies(s.getId()));
			}
			fireIOEvent(s);
		}

		// add or change parameters
		for (Parameter p : model.getListOfParameters()) {
			if (modelOrig.getParameter(p.getId()) == null) {
				modelOrig.addParameter(writeParameter(p));
			} else {
				saveParameterProperties(p, modelOrig.getParameter(p.getId()));
			}
			fireIOEvent(p);
		}

		// initial assignments
		for (i = 0; i < model.getNumInitialAssignments(); i++) {
			InitialAssignment ia = model.getInitialAssignment((int) i);
			long contains = -1;
			for (long j = 0; j < modelOrig.getNumInitialAssignments()
					&& contains < 0; j++) {
				org.sbml.libsbml.InitialAssignment libIA = modelOrig
						.getInitialAssignment(j);
				if (libIA.getSymbol().equals(ia.getVariable())
						&& equal(ia.getMath(), libIA.getMath()))
					contains = j;
			}
			if (contains < 0) {
				modelOrig.addInitialAssignment(writeInitialAssignment(ia));
			} else {
				saveMathContainerProperties(ia, modelOrig
						.getInitialAssignment(contains));
			}
			fireIOEvent(ia);
		}

		// rules
		for (i = 0; i < model.getNumRules(); i++) {
			Rule rule = model.getRule((int) i);
			long contains = -1;
			for (long j = 0; j < modelOrig.getNumRules() && contains < 0; j++) {
				boolean equal = false;
				org.sbml.libsbml.Rule ruleOrig = modelOrig.getRule(j);
				if ((rule instanceof AlgebraicRule)
						&& (ruleOrig instanceof org.sbml.libsbml.AlgebraicRule)) {
					equal = true;
				} else {
					if ((rule instanceof RateRule)
							&& (ruleOrig instanceof org.sbml.libsbml.RateRule)) {
						equal = ((RateRule) rule).getVariable().equals(
								((org.sbml.libsbml.RateRule) ruleOrig)
										.getVariable());
					} else if ((rule instanceof AssignmentRule)
							&& (ruleOrig instanceof org.sbml.libsbml.AssignmentRule)) {
						equal = ((AssignmentRule) rule).getVariable().equals(
								((org.sbml.libsbml.AssignmentRule) ruleOrig)
										.getVariable());
					}
					equal &= ((ExplicitRule) rule).isSetUnits()
							&& ruleOrig.isSetUnits();
					if (equal && ruleOrig.isSetUnits()) {
						equal &= ruleOrig.getUnits().equals(((ExplicitRule) rule).getUnits());
					}
				}
				if (equal) {
					equal &= equal(rule.getMath(), ruleOrig.getMath());
				}
				if (equal) {
					contains = j;
				}
			}
			if (contains < 0) {
				modelOrig.addRule(writeRule(rule));
			} else {
				org.sbml.libsbml.Rule ruleOrig = modelOrig.getRule(contains);
				// math is equal anyway...
				saveSBaseProperties(rule, ruleOrig);
				if (ruleOrig.isSetUnits() && (rule instanceof ExplicitRule)
						&& (((ExplicitRule) rule).isSetUnits())) {
					ruleOrig.setUnits(((ExplicitRule) rule).getUnits());
				}
			}
			fireIOEvent(rule);
		}

		// constraints
		for (i = 0; i < model.getNumConstraints(); i++) {
			Constraint ia = model.getConstraint((int) i);
			long contains = -1;
			for (long j = 0; j < modelOrig.getNumConstraints() && contains < 0; j++) {
				org.sbml.libsbml.Constraint c = modelOrig.getConstraint(j);
				if (equal(ia.getMath(), c.getMath()))
					contains = j;
			}
			if (contains < 0) {
				modelOrig.addConstraint(writeConstraint(ia));
			} else {
				saveMathContainerProperties(ia, modelOrig
						.getConstraint(contains));
			}
			fireIOEvent(ia);
		}

		// add or change reactions
		for (Reaction r : model.getListOfReactions()) {
			if (modelOrig.getReaction(r.getId()) == null) {
				modelOrig.addReaction(writeReaction(r));
			} else {
				saveReactionProperties(r, modelOrig.getReaction(r.getId()));
			}
			fireIOEvent(r);
		}

		// events
		for (Event event : model.getListOfEvents()) {
			if (modelOrig.getEvent(event.getId()) == null) {
				modelOrig.addEvent(writeEvent(event));
			} else {
				saveEventProperties(event, modelOrig.getEvent(event.getId()));
			}
			fireIOEvent(event);
		}
		removeUnneccessaryElements(model, orig);
		fireIOEvent(null);
		saveNamedSBaseProperties(model, modelOrig);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.io.AbstractSBMLWriter#saveChanges(org.sbml.jsbml.Reaction,
	 * java.lang.Object)
	 */
	public boolean saveChanges(Reaction reaction, Object model)
			throws SBMLException {
		if (!(model instanceof org.sbml.libsbml.Model))
			throw new IllegalArgumentException("model" + error
					+ "org.sbml.libsbml.Model");
		org.sbml.libsbml.Model pluMo = (org.sbml.libsbml.Model) model;
		for (SpeciesReference specRef : reaction.getListOfReactants())
			saveChanges(specRef.getSpeciesInstance(), pluMo);
		for (SpeciesReference specRef : reaction.getListOfProducts())
			saveChanges(specRef.getSpeciesInstance(), pluMo);
		for (ModifierSpeciesReference modSpecRef : reaction
				.getListOfModifiers())
			saveChanges(modSpecRef.getSpeciesInstance(), pluMo);
		if (reaction.isSetKineticLaw() && reaction.getKineticLaw().isSetMath()) {
			ASTNode math = reaction.getKineticLaw().getMath();
			Model m = reaction.getModel();
			for (FunctionDefinition fd : m.getListOfFunctionDefinitions())
				if (math.refersTo(fd.getId())) {
					if (pluMo.getFunctionDefinition(fd.getId()) == null)
						pluMo
								.addFunctionDefinition(writeFunctionDefinition(fd));
					else
						saveMathContainerProperties(fd, pluMo
								.getFunctionDefinition(fd.getId()));
				}
			for (Compartment c : m.getListOfCompartments())
				if (math.refersTo(c.getId())) {
					if (c.isSetUnits()
							&& !Unit.isUnitKind(c.getUnits(), c.getLevel(), c
									.getVersion()))
						pluMo.addUnitDefinition(writeUnitDefinition(c
								.getUnitsInstance()));
					if (c.isSetCompartmentType()
							&& pluMo.getCompartmentType(c.getCompartmentType()) == null)
						pluMo.addCompartmentType(writeCompartmentType(c
								.getCompartmentTypeInstance()));
					if (pluMo.getCompartment(c.getId()) == null)
						pluMo.addCompartment(writeCompartment(c));
					else
						saveCompartmentProperties(c, pluMo.getCompartment(c
								.getId()));
				}
			for (Parameter p : m.getListOfParameters())
				if (math.refersTo(p.getId())) {
					if (p.isSetUnits()
							&& !Unit.isUnitKind(p.getUnits(), p.getLevel(), p
									.getVersion())) {
						if (pluMo.getUnitDefinition(p.getUnits()) == null)
							pluMo.addUnitDefinition(writeUnitDefinition(p
									.getUnitsInstance()));
						else
							saveUnitDefinitionProperties(p.getUnitsInstance(),
									pluMo.getUnitDefinition(p.getUnits()));
					}
					if (pluMo.getParameter(p.getId()) == null)
						pluMo.addParameter(writeParameter(p, pluMo));
					else
						saveParameterProperties(p, pluMo
								.getParameter(p.getId()));
				}
		}
		saveReactionProperties(reaction, pluMo.getReaction(reaction.getId()));
		removeUnneccessaryElements(reaction.getModel(), pluMo);
		return true;
	}

	/**
	 * 
	 * @param speciesInstance
	 * @param pluMo
	 */
	private void saveChanges(Species species, org.sbml.libsbml.Model pluMo) {
		org.sbml.libsbml.Species pluSpec = pluMo.getSpecies(species.getId());
		if (species.isSetSubstanceUnits()
				&& !Unit.isUnitKind(species.getSubstanceUnits(), species
						.getLevel(), species.getVersion())) {
			if (pluMo.getUnitDefinition(species.getSubstanceUnits()) == null)
				pluMo.addUnitDefinition(writeUnitDefinition(species
						.getSubstanceUnitsInstance()));
			else
				saveUnitDefinitionProperties(species
						.getSubstanceUnitsInstance(), pluMo
						.getUnitDefinition(species.getSubstanceUnits()));
		}
		if (species.isSetCompartment()
				&& pluMo.getCompartment(species.getCompartment()) == null) {
			Compartment c = species.getCompartmentInstance();
			if (c.isSetCompartmentType()) {
				if (pluMo.getCompartmentType(c.getCompartmentType()) == null)
					pluMo.addCompartmentType(writeCompartmentType(c
							.getCompartmentTypeInstance()));
				else
					saveNamedSBaseProperties(c.getCompartmentTypeInstance(),
							pluMo.getCompartmentType(c.getCompartmentType()));
			}
			if (c.isSetUnits()
					&& !Unit.isUnitKind(c.getUnits(), c.getLevel(), c
							.getVersion())) {
				if (pluMo.getUnitDefinition(c.getUnits()) == null)
					pluMo.addUnitDefinition(writeUnitDefinition(c
							.getUnitsInstance()));
				else
					saveUnitDefinitionProperties(c.getUnitsInstance(), pluMo
							.getUnitDefinition(c.getUnits()));
			}
			pluMo.addCompartment(writeCompartment(species
					.getCompartmentInstance()));
		}
		if (species.isSetSpeciesType()) {
			if (pluMo.getSpeciesType(species.getSpeciesType()) == null)
				pluMo.addSpeciesType(writeSpeciesType(species
						.getSpeciesTypeInstance()));
			else
				saveNamedSBaseProperties(species.getSpeciesTypeInstance(),
						pluMo.getSpeciesType(species.getSpeciesType()));
		}
		saveSpeciesProperties(species, pluSpec);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jlibsbml.SBMLWriter#saveCompartmentProperties(org.sbml.jlibsbml
	 * .Compartment, java.lang.Object)
	 */
	private void saveCompartmentProperties(Compartment c, Object compartment) {
		if (!(compartment instanceof org.sbml.libsbml.Compartment))
			throw new IllegalArgumentException(
					"compartment must be an instance of org.sbml.libsbml.Compartment.");
		org.sbml.libsbml.Compartment comp = (org.sbml.libsbml.Compartment) compartment;
		saveNamedSBaseProperties(c, comp);
		if (c.isSetSize())
			comp.setSize(c.getSize());
		if (c.isSetCompartmentType()
				&& !c.getCompartmentType().equals(comp.getCompartmentType()))
			comp.setCompartmentType(c.getCompartmentType());
		if (c.getSpatialDimensions() != comp.getSpatialDimensions())
			comp.setSpatialDimensions(c.getSpatialDimensions());
		if (c.isSetUnits() && !c.getUnits().equals(comp.getUnits()))
			comp.setUnits(c.getUnits());
		if (c.isSetOutside() && !c.getOutside().equals(comp.getOutside()))
			comp.setOutside(c.getOutside());
		if (c.getConstant() != comp.getConstant())
			comp.setConstant(c.getConstant());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jlibsbml.SBMLWriter#saveCVTermProperties(org.sbml.jlibsbml.CVTerm
	 * , java.lang.Object)
	 */
	private void saveCVTermProperties(CVTerm cvt, Object term) {
		if (!(term instanceof org.sbml.libsbml.CVTerm))
			throw new IllegalArgumentException(
					"term must be an instance of org.sbml.libsbml.CVTerm.");
		org.sbml.libsbml.CVTerm t = (org.sbml.libsbml.CVTerm) term;
		org.sbml.libsbml.CVTerm myTerm = LibSBMLUtils.convertCVTerm(cvt);
		if (myTerm.getQualifierType() != t.getQualifierType())
			t.setQualifierType(myTerm.getQualifierType());
		if (myTerm.getBiologicalQualifierType() != t
				.getBiologicalQualifierType())
			t.setBiologicalQualifierType(myTerm.getBiologicalQualifierType());
		if (myTerm.getModelQualifierType() != t.getModelQualifierType())
			t.setModelQualifierType(myTerm.getModelQualifierType());
		// add missing resources
		for (int i = 0; i < myTerm.getNumResources(); i++) {
			boolean contains = false;
			for (int j = 0; j < t.getNumResources() && !contains; j++) {
				if (myTerm.getResourceURI(i).equals(t.getResourceURI(j)))
					contains = true;
			}
			if (!contains)
				t.addResource(myTerm.getResourceURI(i));
		}
		// remove old resources
		for (long i = t.getNumResources() - 1; i >= 0; i--) {
			boolean contains = false;
			for (int j = 0; j < myTerm.getNumResources() && !contains; j++)
				if (myTerm.getResourceURI(j).equals(t.getResourceURI(i)))
					contains = true;
			if (!contains)
				t.removeResource(t.getResourceURI(i));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jlibsbml.SBMLWriter#saveEventProperties(org.sbml.jlibsbml.Event,
	 * java.lang.Object)
	 */
	private void saveEventProperties(Event ev, Object event)
			throws SBMLException {
		if (!(event instanceof org.sbml.libsbml.Event)) {
			throw new IllegalArgumentException("event" + error + "Event.");
		}
		org.sbml.libsbml.Event e = (org.sbml.libsbml.Event) event;
		saveNamedSBaseProperties(ev, e);
		if (ev.getUseValuesFromTriggerTime() != e.getUseValuesFromTriggerTime())
			e.setUseValuesFromTriggerTime(ev.getUseValuesFromTriggerTime());
		if (ev.isSetTimeUnits() && ev.getTimeUnits() != e.getTimeUnits())
			e.setTimeUnits(ev.getTimeUnits());
		if (ev.isSetDelay()) {
			if (!e.isSetDelay()) {
				e.setDelay(writeDelay(ev.getDelay()));
			} else {
				saveMathContainerProperties(ev.getDelay(), e.getDelay());
			}
		} else if (e.isSetDelay()) {
			e.unsetDelay();
		}
		if (ev.isSetTrigger()) {
			if (!e.isSetTrigger()) {
				e.setTrigger(writeTrigger(ev.getTrigger()));
			} else {
				saveMathContainerProperties(ev.getTrigger(), e.getTrigger());
			}
		}
		if (ev.isSetPriority()) {
			if (!e.isSetPriority()) {
				e.setPriority(writePriority(ev.getPriority()));
			} else {
				saveMathContainerProperties(ev.getPriority(), e.getPriority());
			}
		}

		// synchronize event assignments

		for (EventAssignment ea : ev.getListOfEventAssignments()) {
			long contains = -1;
			for (long i = 0; i < e.getNumEventAssignments() && contains < 0; i++) {
				org.sbml.libsbml.EventAssignment libEA = e
						.getEventAssignment(i);
				if (libEA.getVariable().equals(ea.getVariable())
						&& equal(ea.getMath(), libEA.getMath())) {
					contains = i;
				}
			}
			if (contains < 0) {
				e.addEventAssignment(writeEventAssignment(ea));
			} else {
				saveMathContainerProperties(ea, e.getEventAssignment(contains));
			}
		}
		// remove unnecessary event assignments
		for (long i = e.getNumEventAssignments() - 1; i >= 0; i--) {
			org.sbml.libsbml.EventAssignment ea = e.getEventAssignment(i);
			boolean contains = false;
			for (int j = 0; j < ev.getNumEventAssignments() && !contains; j++) {
				EventAssignment eventA = ev.getEventAssignment(j);
				if (eventA.isSetVariable()
						&& eventA.getVariable().equals(ea.getVariable())
						&& equal(eventA.getMath(), ea.getMath())) {
					contains = true;
				}
			}
			if (!contains) {
				e.removeEventAssignment(i);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jlibsbml.SBMLWriter#saveKineticLawProperties(org.sbml.jlibsbml
	 * .KineticLaw, java.lang.Object)
	 */
	private void saveKineticLawProperties(KineticLaw kl, Object kineticLaw)
			throws SBMLException {
		if (!(kineticLaw instanceof org.sbml.libsbml.KineticLaw))
			throw new IllegalArgumentException(
					"kineticLaw must be an instance of org.sbml.libsbml.KineticLaw.");
		org.sbml.libsbml.KineticLaw libKinLaw = (org.sbml.libsbml.KineticLaw) kineticLaw;
		// add or change parameters
		for (LocalParameter p : kl.getListOfParameters()) {
			org.sbml.libsbml.Parameter libParam = libKinLaw.getParameter(p
					.getId());
			if (p.isSetUnits()
					&& !Unit.isUnitKind(p.getUnits(), p.getLevel(), p
							.getVersion())
					&& libKinLaw.getModel().getUnitDefinition(p.getUnits()) == null)
				libKinLaw.getModel().addUnitDefinition(
						writeUnitDefinition(p.getUnitsInstance()));
			if (libParam == null)
				libKinLaw.addParameter(writeParameter(new Parameter(p)));
			else
				saveParameterProperties(new Parameter(p), libParam);
		}
		// remove parameters
		for (long i = libKinLaw.getNumParameters() - 1; i >= 0; i--) {
			org.sbml.libsbml.Parameter p = libKinLaw.getParameter(i);
			if (kl.getParameter(p.getId()) == null)
				libKinLaw.getListOfParameters().remove(i);
		}
		saveMathContainerProperties(kl, libKinLaw);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jlibsbml.SBMLWriter#saveMathContainerProperties(org.sbml.jlibsbml
	 * .MathContainer, java.lang.Object)
	 */
	private void saveMathContainerProperties(MathContainer mc, Object sbase)
			throws SBMLException {
		if (mc instanceof NamedSBase)
			saveNamedSBaseProperties((NamedSBase) mc, sbase);
		else
			saveSBaseProperties(mc, sbase);
		if (sbase instanceof org.sbml.libsbml.Constraint) {
			org.sbml.libsbml.Constraint kl = (org.sbml.libsbml.Constraint) sbase;
			boolean equal = kl.isSetMath() && mc.isSetMath()
					&& equal(mc.getMath(), kl.getMath());
			if (mc.isSetMath()
					&& !equal
					&& kl.setMath(LibSBMLUtils.convertASTNode(mc.getMath())) != libsbmlConstants.LIBSBML_OPERATION_SUCCESS)
				throw new SBMLException("Unable to set math of "
						+ mc.getClass().getSimpleName() + " in "
						+ kl.getClass().getName());
		} else if (sbase instanceof org.sbml.libsbml.Delay) {
			org.sbml.libsbml.Delay kl = (org.sbml.libsbml.Delay) sbase;
			boolean equal = kl.isSetMath() && mc.isSetMath()
					&& equal(mc.getMath(), kl.getMath());
			if (mc.isSetMath()
					&& !equal
					&& kl.setMath(LibSBMLUtils.convertASTNode(mc.getMath())) != libsbmlConstants.LIBSBML_OPERATION_SUCCESS)
				throw new SBMLException("Unable to set math of "
						+ mc.getClass().getSimpleName() + " in "
						+ kl.getClass().getName());
		} else if (sbase instanceof org.sbml.libsbml.EventAssignment) {
			org.sbml.libsbml.EventAssignment kl = (org.sbml.libsbml.EventAssignment) sbase;
			boolean equal = kl.isSetMath() && mc.isSetMath()
					&& equal(mc.getMath(), kl.getMath());
			if (mc.isSetMath()
					&& !equal
					&& kl.setMath(LibSBMLUtils.convertASTNode(mc.getMath())) != libsbmlConstants.LIBSBML_OPERATION_SUCCESS)
				throw new SBMLException("Unable to set math of "
						+ mc.getClass().getSimpleName() + " in "
						+ kl.getClass().getName());
		} else if (sbase instanceof org.sbml.libsbml.FunctionDefinition) {
			org.sbml.libsbml.FunctionDefinition kl = (org.sbml.libsbml.FunctionDefinition) sbase;
			boolean equal = kl.isSetMath() && mc.isSetMath()
					&& equal(mc.getMath(), kl.getMath());
			if (mc.isSetMath()
					&& !equal
					&& kl.setMath(LibSBMLUtils.convertASTNode(mc.getMath())) != libsbmlConstants.LIBSBML_OPERATION_SUCCESS)
				throw new SBMLException("Unable to set math of "
						+ mc.getClass().getSimpleName() + " in "
						+ kl.getClass().getName());
		} else if (sbase instanceof org.sbml.libsbml.InitialAssignment) {
			org.sbml.libsbml.InitialAssignment kl = (org.sbml.libsbml.InitialAssignment) sbase;
			boolean equal = kl.isSetMath() && mc.isSetMath()
					&& equal(mc.getMath(), kl.getMath());
			if (mc.isSetMath()
					&& !equal
					&& kl.setMath(LibSBMLUtils.convertASTNode(mc.getMath())) != libsbmlConstants.LIBSBML_OPERATION_SUCCESS)
				throw new SBMLException("Unable to set math of "
						+ mc.getClass().getSimpleName() + " in "
						+ kl.getClass().getName());
		} else if (sbase instanceof org.sbml.libsbml.KineticLaw) {
			org.sbml.libsbml.KineticLaw kl = (org.sbml.libsbml.KineticLaw) sbase;
			boolean equal = kl.isSetMath() && mc.isSetMath()
					&& equal(mc.getMath(), kl.getMath());
			if (mc.isSetMath()
					&& !equal
					&& kl.setMath(LibSBMLUtils.convertASTNode(mc.getMath())) != libsbmlConstants.LIBSBML_OPERATION_SUCCESS)
				throw new SBMLException("Unable to set math of "
						+ mc.getClass().getSimpleName() + " in "
						+ kl.getClass().getName());
		} else if (sbase instanceof org.sbml.libsbml.Rule) {
			org.sbml.libsbml.Rule kl = (org.sbml.libsbml.Rule) sbase;
			boolean equal = kl.isSetMath() && mc.isSetMath()
					&& equal(mc.getMath(), kl.getMath());
			if (mc.isSetMath()
					&& !equal
					&& kl.setMath(LibSBMLUtils.convertASTNode(mc.getMath())) != libsbmlConstants.LIBSBML_OPERATION_SUCCESS)
				throw new SBMLException("Unable to set math of "
						+ mc.getClass().getSimpleName() + " in "
						+ kl.getClass().getName());
		} else if (sbase instanceof org.sbml.libsbml.StoichiometryMath) {
			org.sbml.libsbml.StoichiometryMath kl = (org.sbml.libsbml.StoichiometryMath) sbase;
			boolean equal = kl.isSetMath() && mc.isSetMath()
					&& equal(mc.getMath(), kl.getMath());
			if (mc.isSetMath()
					&& !equal
					&& kl.setMath(LibSBMLUtils.convertASTNode(mc.getMath())) != libsbmlConstants.LIBSBML_OPERATION_SUCCESS)
				throw new SBMLException("Unable to set math of "
						+ mc.getClass().getSimpleName() + " in "
						+ kl.getClass().getName());
		} else if (sbase instanceof org.sbml.libsbml.Trigger) {
			org.sbml.libsbml.Trigger kl = (org.sbml.libsbml.Trigger) sbase;
			boolean equal = kl.isSetMath() && mc.isSetMath()
					&& equal(mc.getMath(), kl.getMath());
			if (mc.isSetMath()
					&& !equal
					&& kl.setMath(LibSBMLUtils.convertASTNode(mc.getMath())) != libsbmlConstants.LIBSBML_OPERATION_SUCCESS)
				throw new SBMLException("Unable to set math of "
						+ mc.getClass().getSimpleName() + " in "
						+ kl.getClass().getName());
		}
	}

	// this method is now in LibSBMLUtils, called convertHistory()
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jlibsbml.SBMLWriter#saveModelHistoryProperties(org.sbml.jlibsbml
	 * .Model, java.lang.Object)
	 
	private void saveHistoryProperties(History m, Object modelHistory) {
		if (!(modelHistory instanceof org.sbml.libsbml.ModelHistory))
			throw new IllegalArgumentException(
					"model must be an instance of org.sbml.libsbml.Model.");
		org.sbml.libsbml.ModelHistory mo = (org.sbml.libsbml.ModelHistory) modelHistory;
		if (m.isSetCreatedDate())
			mo.setCreatedDate(LibSBMLUtils.convertDate(m.getCreatedDate()));
		if (m.isSetModifiedDate())
			mo.setModifiedDate(LibSBMLUtils.convertDate(m.getModifiedDate()));
		// add creators
		for (Creator mc : m.getListOfCreators()) {
			boolean equal = false;
			boolean nothingSet = true;
			for (long i = 0; i < mo.getNumCreators() && !equal; i++) {
				org.sbml.libsbml.ModelCreator moc = mo.getCreator(i);
				equal = moc.isSetEmail() == mc.isSetEmail();
				if (moc.isSetEmail() && mc.isSetEmail()) {
					equal &= moc.getEmail().equals(mc.getEmail());
					nothingSet = false;
				}
				equal &= moc.isSetFamilyName() == mc.isSetFamilyName();
				if (moc.isSetFamilyName() && mc.isSetFamilyName()) {
					equal &= moc.getFamilyName().equals(mc.getFamilyName());
					nothingSet = false;
				}
				equal &= moc.isSetGivenName() == mc.isSetGivenName();
				if (moc.isSetGivenName() && mc.isSetGivenName()) {
					equal &= moc.getGivenName().equals(mc.getGivenName());
					nothingSet = false;
				}
				equal &= moc.isSetOrganization() == mc.isSetOrganization();
				if (moc.isSetOrganization() && mc.isSetOrganization()) {
					equal &= moc.getOrganization().equals(mc.getOrganization());
					nothingSet = false;
				}
			}
			if (!equal || (equal && nothingSet)) {
				org.sbml.libsbml.ModelCreator moc = new org.sbml.libsbml.ModelCreator();
				moc.setEmail(mc.getEmail());
				moc.setFamilyName(mc.getFamilyName());
				moc.setGivenName(mc.getGivenName());
				moc.setOrganization(mc.getOrganization());
				mo.addCreator(moc);
			}
		}
		// remove unnecessary creators
		for (long i = mo.getNumCreators() - 1; i >= 0; i--) {
			boolean contains = false;
			boolean nothingSet = true;
			org.sbml.libsbml.ModelCreator moc = mo.getCreator(i);
			for (int j = 0; j < m.getNumCreators() && !contains; j++) {
				Creator mc = m.getCreator(j);
				contains = moc.isSetEmail() == mc.isSetEmail();
				if (moc.isSetEmail() && mc.isSetEmail()) {
					contains &= moc.getEmail().equals(mc.getEmail());
					nothingSet = false;
				}
				contains &= moc.isSetFamilyName() == mc.isSetFamilyName();
				if (moc.isSetFamilyName() && mc.isSetFamilyName()) {
					contains &= moc.getFamilyName().equals(mc.getFamilyName());
					nothingSet = false;
				}
				contains &= moc.isSetGivenName() == mc.isSetGivenName();
				if (moc.isSetGivenName() && mc.isSetGivenName()) {
					contains &= moc.getGivenName().equals(mc.getGivenName());
					nothingSet = false;
				}
				contains &= moc.isSetOrganization() == mc.isSetOrganization();
				if (moc.isSetOrganization() && mc.isSetOrganization()) {
					contains &= moc.getOrganization().equals(
							mc.getOrganization());
					nothingSet = false;
				}
				if (nothingSet)
					contains = false;
			}
			if (!contains)
				mo.getListCreators().remove(i);
		}

		// add modified dates
		for (int i = 0; i < m.getNumModifiedDates(); i++) {
			String d = LibSBMLUtils.convertDate(m.getModifiedDate(i)).toString();
			long contains = -1;
			for (long j = 0; j < mo.getNumModifiedDates() && contains < 0; j++)
				if (mo.getModifiedDate(j).toString().equals(d.toString()))
					contains = j;
			if (contains < 0)
				mo.addModifiedDate(new org.sbml.libsbml.Date(d));
		}

		// remove modified dates
		for (long i = mo.getNumModifiedDates() - 1; i >= 0; i--) {
			String d = mo.getModifiedDate(i).toString();
			boolean contains = false;
			for (int j = 0; j < m.getNumModifiedDates() && !contains; j++)
				if (LibSBMLUtils.convertDate(m.getModifiedDate(j)).toString().equals(d))
					contains = true;
			if (!contains)
				mo.getListModifiedDates().remove(i);
		}
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.sbml.SBMLWriter#saveModifierSpeciesReferenceProperties(org.sbml.
	 * ModifierSpeciesReference, java.lang.Object)
	 */
	private void saveModifierSpeciesReferenceProperties(
			ModifierSpeciesReference msr, Object modifierSpeciesReference) {
		if (!(modifierSpeciesReference instanceof org.sbml.libsbml.ModifierSpeciesReference)) {
			throw new IllegalArgumentException("modifierSpeciesReference"
					+ error + "ModifierSpeciesReference.");
		}
		saveNamedSBaseProperties(msr, modifierSpeciesReference);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLWriter#saveNamedSBaseProperties(org.sbml.NamedSBase,
	 * java.lang.Object)
	 */
	private void saveNamedSBaseProperties(NamedSBase nsb, Object sb) {
		if (!(sb instanceof org.sbml.libsbml.SBase))
			throw new IllegalArgumentException(
					"sb must be an instance of org.sbml.libsbml.SBase.");
		saveSBaseProperties(nsb, sb);
		org.sbml.libsbml.SBase libSBase = (org.sbml.libsbml.SBase) sb;
		if (libSBase instanceof org.sbml.libsbml.Compartment) {
			org.sbml.libsbml.Compartment c = (org.sbml.libsbml.Compartment) libSBase;
			if (!nsb.getId().equals(c.getId()))
				c.setId(nsb.getId());
			if (!nsb.getName().equals(c.getName()))
				c.setName(nsb.getName());
		} else if (libSBase instanceof org.sbml.libsbml.CompartmentType) {
			org.sbml.libsbml.CompartmentType c = (org.sbml.libsbml.CompartmentType) libSBase;
			if (!nsb.getId().equals(c.getId()))
				c.setId(nsb.getId());
			if (!nsb.getName().equals(c.getName()))
				c.setName(nsb.getName());
		} else if (libSBase instanceof org.sbml.libsbml.Event) {
			org.sbml.libsbml.Event c = (org.sbml.libsbml.Event) libSBase;
			if (!nsb.getId().equals(c.getId()))
				c.setId(nsb.getId());
			if (!nsb.getName().equals(c.getName()))
				c.setName(nsb.getName());
		} else if (libSBase instanceof org.sbml.libsbml.FunctionDefinition) {
			org.sbml.libsbml.FunctionDefinition c = (org.sbml.libsbml.FunctionDefinition) libSBase;
			if (!nsb.getId().equals(c.getId()))
				c.setId(nsb.getId());
			if (!nsb.getName().equals(c.getName()))
				c.setName(nsb.getName());
		} else if (libSBase instanceof org.sbml.libsbml.Model) {
			org.sbml.libsbml.Model c = (org.sbml.libsbml.Model) libSBase;
			if (!nsb.getId().equals(c.getId()))
				c.setId(nsb.getId());
			if (!nsb.getName().equals(c.getName()))
				c.setName(nsb.getName());
		} else if (libSBase instanceof org.sbml.libsbml.Parameter) {
			org.sbml.libsbml.Parameter c = (org.sbml.libsbml.Parameter) libSBase;
			if (!nsb.getId().equals(c.getId()))
				c.setId(nsb.getId());
			if (!nsb.getName().equals(c.getName()))
				c.setName(nsb.getName());
		} else if (libSBase instanceof org.sbml.libsbml.Reaction) {
			org.sbml.libsbml.Reaction c = (org.sbml.libsbml.Reaction) libSBase;
			if (!nsb.getId().equals(c.getId()))
				c.setId(nsb.getId());
			if (!nsb.getName().equals(c.getName()))
				c.setName(nsb.getName());
		} else if (libSBase instanceof org.sbml.libsbml.SimpleSpeciesReference) {
			org.sbml.libsbml.SimpleSpeciesReference c = (org.sbml.libsbml.SimpleSpeciesReference) libSBase;
			if (!nsb.getId().equals(c.getId()))
				c.setId(nsb.getId());
			if (!nsb.getName().equals(c.getName()))
				c.setName(nsb.getName());
		} else if (libSBase instanceof org.sbml.libsbml.Species) {
			org.sbml.libsbml.Species c = (org.sbml.libsbml.Species) libSBase;
			if (!nsb.getId().equals(c.getId()))
				c.setId(nsb.getId());
			if (!nsb.getName().equals(c.getName()))
				c.setName(nsb.getName());
		} else if (libSBase instanceof org.sbml.libsbml.SpeciesType) {
			org.sbml.libsbml.SpeciesType c = (org.sbml.libsbml.SpeciesType) libSBase;
			if (!nsb.getId().equals(c.getId()))
				c.setId(nsb.getId());
			if (!nsb.getName().equals(c.getName()))
				c.setName(nsb.getName());
		} else if (libSBase instanceof org.sbml.libsbml.UnitDefinition) {
			org.sbml.libsbml.UnitDefinition c = (org.sbml.libsbml.UnitDefinition) libSBase;
			if (!nsb.getId().equals(c.getId()))
				c.setId(nsb.getId());
			if (!nsb.getName().equals(c.getName()))
				c.setName(nsb.getName());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jlibsbml.SBMLWriter#saveParameterProperties(org.sbml.jlibsbml
	 * .Parameter, java.lang.Object)
	 */
	private void saveParameterProperties(Parameter p, Object parameter) {
		if (!(parameter instanceof org.sbml.libsbml.Parameter))
			throw new IllegalArgumentException(
					"parameter must be an instance of org.sbml.libsbml.Parameter.");
		org.sbml.libsbml.Parameter po = (org.sbml.libsbml.Parameter) parameter;
		saveNamedSBaseProperties(p, po);
		if (p.isSetValue() && p.getValue() != po.getValue())
			po.setValue(p.getValue());
		if (p.getConstant() != po.getConstant())
			po.setConstant(p.getConstant());
		if (p.isSetUnits() && !p.getUnits().equals(po.getUnits()))
			po.setUnits(p.getUnits());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jlibsbml.SBMLWriter#saveReactionProperties(org.sbml.jlibsbml
	 * .Reaction, java.lang.Object)
	 */
	private void saveReactionProperties(Reaction r, Object reaction)
			throws SBMLException {
		if (!(reaction instanceof org.sbml.libsbml.Reaction))
			throw new IllegalArgumentException(
					"reaction must be an instance of org.sbml.libsbml.Reaction.");
		org.sbml.libsbml.Reaction ro = (org.sbml.libsbml.Reaction) reaction;
		this.modelOrig = ro.getModel();
		long i;
		saveNamedSBaseProperties(r, ro);
		if (r.getFast() != ro.getFast())
			ro.setFast(r.getFast());
		if (r.getReversible() != ro.getReversible())
			ro.setReversible(r.getReversible());
		long contains;
		// reactants.
		for (SpeciesReference sr : r.getListOfReactants()) {
			contains = -1;
			for (i = 0; i < ro.getNumReactants() && contains < 0; i++)
				if (sr.getSpecies().equals(ro.getReactant(i).getSpecies()))
					contains = i;
			if (contains < 0)
				ro.addReactant(writeSpeciesReference(sr));
			else
				saveSpeciesReferenceProperties(sr, ro.getReactant(contains));
		}
		// remove unnecessary reactants.
		for (i = ro.getNumReactants() - 1; i >= 0; i--) {
			org.sbml.libsbml.SpeciesReference roreactant = ro.getReactant(i);
			boolean keep = false;
			for (int j = 0; j < r.getNumReactants() && !keep; j++)
				if (r.getReactant(j).getSpecies().equals(
						roreactant.getSpecies()))
					keep = true;
			if (!keep)
				ro.getListOfReactants().remove(i);
		}
		for (SpeciesReference sr : r.getListOfProducts()) {
			contains = -1;
			for (i = 0; i < ro.getNumProducts() && contains < 0; i++)
				if (sr.getSpecies().equals(ro.getProduct(i).getSpecies()))
					contains = i;
			if (contains < 0)
				ro.addProduct(writeSpeciesReference(sr));
			else
				saveSpeciesReferenceProperties(sr, ro.getProduct(contains));
		}
		// remove unnecessary products.
		for (i = ro.getNumProducts() - 1; i >= 0; i--) {
			org.sbml.libsbml.SpeciesReference msr = ro.getProduct(i);
			boolean keep = false;
			for (int j = 0; j < r.getNumProducts() && !keep; j++)
				if (r.getProduct(j).getSpecies().equals(msr.getSpecies()))
					keep = true;
			if (!keep)
				ro.getListOfProducts().remove(i);
		}
		// check modifiers
		for (ModifierSpeciesReference mr : r.getListOfModifiers()) {
			contains = -1;
			for (i = 0; i < ro.getNumModifiers() && contains < 0; i++)
				if (mr.getSpecies().equals(ro.getModifier(i).getSpecies()))
					contains = i;
			if (contains < 0)
				ro.addModifier(writeModifierSpeciesReference(mr));
			else
				saveModifierSpeciesReferenceProperties(mr, ro
						.getModifier(contains));
		}
		// remove unnecessary modifiers.
		for (i = ro.getNumModifiers() - 1; i >= 0; i--) {
			org.sbml.libsbml.ModifierSpeciesReference msr = ro.getModifier(i);
			boolean keep = false;
			for (int j = 0; j < r.getNumModifiers() && !keep; j++)
				if (r.getModifier(j).getSpecies().equals(msr.getSpecies()))
					keep = true;
			if (!keep)
				ro.getListOfModifiers().remove(i);
		}
		if (r.isSetKineticLaw()) {
			if (!ro.isSetKineticLaw())
				ro.setKineticLaw(writeKineticLaw(r.getKineticLaw()));
			else if (ro.isSetKineticLaw())
				saveKineticLawProperties(r.getKineticLaw(), ro.getKineticLaw());
		} else if (ro.isSetKineticLaw())
			ro.unsetKineticLaw();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLWriter#saveSBaseProperties(org.sbml.SBase,
	 * java.lang.Object)
	 */

	private void saveSBaseProperties(SBase s, Object sb) {
		if (!(sb instanceof org.sbml.libsbml.SBase)) {
			throw new IllegalArgumentException("sb" + error + "SBase.");
		}
		org.sbml.libsbml.SBase po = (org.sbml.libsbml.SBase) sb;
		if (s.isSetMetaId() && po.isSetMetaId()
				&& !s.getMetaId().equals(po.getMetaId())) {
			po.setMetaId(s.getMetaId());
		}
		if (s.isSetNotes() && !s.getNotesString().equals(po.getNotesString())) {
			po.setNotes(s.getNotesString());
		}
		if (s.isSetSBOTerm() && s.getSBOTerm() != po.getSBOTerm()) {
			po.setSBOTerm(s.getSBOTerm());
		}
		for (CVTerm cvt : s.getCVTerms()) {
			long contains = -1;
			for (int i = 0; i < po.getNumCVTerms() && contains < 0; i++) {
				org.sbml.libsbml.CVTerm cvo = po.getCVTerm(i);
				boolean equal = cvo.getNumResources() == cvt.getNumResources();
				if (equal) {
					for (int j = 0; j < cvo.getNumResources(); j++) {
						equal &= cvo.getResourceURI(j).equals(
								cvt.getResourceURI(j));
					}
				}
				if (equal) {
					contains = i;
				}
			}
			if (contains < 0) {
				po.addCVTerm(LibSBMLUtils.convertCVTerm(cvt));
			} else {
				saveCVTermProperties(cvt, po.getCVTerm(contains));
			}
		}
		if (s.isSetHistory()) {
			if (!po.isSetModelHistory()) {
				po.setModelHistory(LibSBMLUtils.convertHistory(s.getHistory()));
			}
		}
		// remove CVTerms that are not needed anymore.
		for (long i = po.getNumCVTerms() - 1; i >= 0; i--) {
			long contains = -1;
			org.sbml.libsbml.CVTerm cvo = po.getCVTerm(i);
			for (int j = 0; j < s.getNumCVTerms() && contains < 0; j++) {
				CVTerm cvt = s.getCVTerm(j);
				boolean equal = cvo.getNumResources() == cvt.getNumResources();
				if (equal) {
					for (int k = 0; k < cvo.getNumResources(); k++) {
						equal &= cvo.getResourceURI(k).equals(
								cvt.getResourceURI(k));
					}
				}
				if (equal) {
					contains = i;
				}
			}
			if (contains < 0) {
				po.getCVTerms().remove(i);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jlibsbml.SBMLWriter#saveSpeciesProperties(org.sbml.jlibsbml.
	 * Species, java.lang.Object)
	 */
	private void saveSpeciesProperties(Species s, Object species) {
		if (!(species instanceof org.sbml.libsbml.Species)) {
			throw new IllegalArgumentException(
					"species must be an instance of org.sbml.libsbml.Species.");
		}
		org.sbml.libsbml.Species spec = (org.sbml.libsbml.Species) species;
		saveNamedSBaseProperties(s, spec);
		if (s.isSetSpeciesType()
				&& !s.getSpeciesType().equals(spec.getSpeciesType()))
			spec.setSpeciesType(s.getSpeciesType());
		if (s.isSetCompartment()
				&& !s.getCompartment().equals(spec.getCompartment()))
			spec.setCompartment(s.getCompartment());
		if (s.isSetInitialAmount()) {
			if (!spec.isSetInitialAmount()
					|| s.getInitialAmount() != spec.getInitialAmount())
				spec.setInitialAmount(s.getInitialAmount());
		} else if (s.isSetInitialConcentration())
			if (!spec.isSetInitialConcentration()
					|| s.getInitialConcentration() != spec
							.getInitialConcentration())
				spec.setInitialConcentration(s.getInitialConcentration());
		if (s.isSetSubstanceUnits())
			spec.setSubstanceUnits(s.getSubstanceUnits());
		if (s.getHasOnlySubstanceUnits() != spec.getHasOnlySubstanceUnits())
			spec.setHasOnlySubstanceUnits(s.getHasOnlySubstanceUnits());
		if (s.getBoundaryCondition() != spec.getBoundaryCondition())
			spec.setBoundaryCondition(spec.getBoundaryCondition());
		if (s.isSetCharge() && s.getCharge() != spec.getCharge())
			spec.setCharge(s.getCharge());
		if (s.getConstant() != spec.getConstant())
			spec.setConstant(s.getConstant());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.squeezer.io.AbstractSBMLWriter#saveSpeciesReferenceProperties
	 * (org.sbml.SpeciesReference, java.lang.Object)
	 */

	private void saveSpeciesReferenceProperties(SpeciesReference sr,
			Object specRef) throws SBMLException {
		if (!(specRef instanceof org.sbml.libsbml.SpeciesReference))
			throw new IllegalArgumentException(
					"specRef must be an instance of org.sbml.libsbml.SpeciesReference.");
		org.sbml.libsbml.SpeciesReference sp = (org.sbml.libsbml.SpeciesReference) specRef;
		saveNamedSBaseProperties(sr, sp);
		if (sr.isSetSpecies() && !sr.getSpecies().equals(sp.getSpecies()))
			sp.setSpecies(sr.getSpecies());
		if (sr.isSetStoichiometryMath()) {
			if (sp.isSetStoichiometryMath()
					&& !equal(sr.getStoichiometryMath().getMath(), sp
							.getStoichiometryMath().getMath()))
				saveMathContainerProperties(sr.getStoichiometryMath(), sp
						.getStoichiometryMath());
			else
				sp.setStoichiometryMath(writeStoichoimetryMath(sr
						.getStoichiometryMath()));
		} else
			sp.setStoichiometry(sr.getStoichiometry());
	}

	/**
	 * 
	 * @param ud
	 * @param libU
	 */
	private void saveUnitDefinitionProperties(UnitDefinition ud,
			org.sbml.libsbml.UnitDefinition libU) {
		saveNamedSBaseProperties(ud, libU);
		boolean contains;
		// remove unnecessary elements
		for (long i = libU.getNumUnits() - 1; i >= 0; i--) {
			contains = false;
			for (Unit u : ud.getListOfUnits()) {
				if (equal(u, libU.getUnit(i))) {
					contains = true;
					break;
				}
			}
			if (!contains)
				libU.removeUnit(i);
		}
		for (Unit u : ud.getListOfUnits()) {
			contains = false;
			for (int j = 0; j < libU.getNumUnits() && !contains; j++) {
				if (equal(u, libU.getUnit(j)))
					contains = true;
			}
			if (!contains)
				libU.addUnit(writeUnit(u));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLWriter#writeCompartment(org.sbml.Compartment)
	 */

	private org.sbml.libsbml.Compartment writeCompartment(
			Compartment compartment) {
		org.sbml.libsbml.Compartment c = new org.sbml.libsbml.Compartment(
				compartment.getLevel(), compartment.getVersion());
		saveNamedSBaseProperties(compartment, c);
		if (compartment.isSetCompartmentType()
				&& !c.getCompartmentType().equals(
						compartment.getCompartmentType()))
			c.setCompartmentType(compartment.getCompartmentType());
		if (compartment.isSetOutside() && !c.equals(compartment.getOutside()))
			c.setOutside(compartment.getOutside());
		if (compartment.isSetSize() && compartment.getSize() != c.getSize())
			c.setSize(compartment.getSize());
		if (compartment.isSetUnits())
			c.setUnits(compartment.getUnits());
		c.setConstant(compartment.getConstant());
		c.setSpatialDimensions(compartment.getSpatialDimensions());
		return c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLWriter#writeCompartmentType(org.sbml.CompartmentType)
	 */

	private org.sbml.libsbml.CompartmentType writeCompartmentType(
			CompartmentType compartmentType) {
		org.sbml.libsbml.CompartmentType ct = new org.sbml.libsbml.CompartmentType(
				compartmentType.getLevel(), compartmentType.getVersion());
		saveNamedSBaseProperties(compartmentType, ct);
		return ct;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLWriter#writeConstraint(org.sbml.Constraint)
	 */

	private org.sbml.libsbml.Constraint writeConstraint(Constraint constraint) {
		org.sbml.libsbml.Constraint c = new org.sbml.libsbml.Constraint(
				constraint.getLevel(), constraint.getVersion());
		saveSBaseProperties(constraint, c);
		if (constraint.isSetMath() && !equal(constraint.getMath(), c.getMath())) {
			c.setMath(LibSBMLUtils.convertASTNode(constraint.getMath()));
		}
		if (constraint.isSetMessage()
				&& !constraint.getMessage().equals(c.getMessageString())) {
			c.setMessage(new org.sbml.libsbml.XMLNode(constraint
					.getMessageString()));
		}
		return c;
	}

//	/**
//	 * 
//	 * @param t
//	 * @return
//	 */
//	private org.sbml.libsbml.CVTerm writeCVTerm(CVTerm t) {
//		org.sbml.libsbml.CVTerm libCVt = new org.sbml.libsbml.CVTerm();
//		switch (t.getQualifierType()) {
//		case MODEL_QUALIFIER:
//			libCVt.setQualifierType(libsbmlConstants.MODEL_QUALIFIER);
//			switch (t.getModelQualifierType()) {
//			case BQM_IS:
//				libCVt.setModelQualifierType(libsbmlConstants.BQM_IS);
//				break;
//			case BQM_IS_DESCRIBED_BY:
//				libCVt
//						.setModelQualifierType(libsbmlConstants.BQM_IS_DESCRIBED_BY);
//				break;
//			case BQM_UNKNOWN:
//				libCVt.setModelQualifierType(libsbmlConstants.BQM_UNKNOWN);
//				break;
//			default:
//				break;
//			}
//			break;
//		case BIOLOGICAL_QUALIFIER:
//			libCVt.setQualifierType(libsbmlConstants.BIOLOGICAL_QUALIFIER);
//			switch (t.getBiologicalQualifierType()) {
//			case BQB_ENCODES:
//				libCVt.setBiologicalQualifierType(libsbmlConstants.BQB_ENCODES);
//				break;
//			case BQB_HAS_PART:
//				libCVt
//						.setBiologicalQualifierType(libsbmlConstants.BQB_HAS_PART);
//				break;
//			case BQB_HAS_VERSION:
//				libCVt
//						.setBiologicalQualifierType(libsbmlConstants.BQB_HAS_VERSION);
//				break;
//			case BQB_IS:
//				libCVt.setBiologicalQualifierType(libsbmlConstants.BQB_IS);
//				break;
//			case BQB_IS_DESCRIBED_BY:
//				libCVt
//						.setBiologicalQualifierType(libsbmlConstants.BQB_IS_DESCRIBED_BY);
//				break;
//			case BQB_IS_ENCODED_BY:
//				libCVt
//						.setBiologicalQualifierType(libsbmlConstants.BQB_IS_ENCODED_BY);
//				break;
//			case BQB_IS_HOMOLOG_TO:
//				libCVt
//						.setBiologicalQualifierType(libsbmlConstants.BQB_IS_HOMOLOG_TO);
//				break;
//			case BQB_IS_PART_OF:
//				libCVt
//						.setBiologicalQualifierType(libsbmlConstants.BQB_IS_PART_OF);
//				break;
//			case BQB_IS_VERSION_OF:
//				libCVt
//						.setBiologicalQualifierType(libsbmlConstants.BQB_IS_VERSION_OF);
//				break;
//			case BQB_OCCURS_IN:
//				libCVt
//						.setBiologicalQualifierType(libsbmlConstants.BQB_OCCURS_IN);
//				break;
//			case BQB_UNKNOWN:
//				libCVt.setBiologicalQualifierType(libsbmlConstants.BQB_UNKNOWN);
//				break;
//			default:
//				break;
//			}
//			break;
//		default:
//			break;
//		}
//		for (int j = 0; j < t.getNumResources(); j++) {
//			libCVt.addResource(t.getResourceURI(j));
//		}
//		return libCVt;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLWriter#writeDelay(org.sbml.Delay)
	 */

	private org.sbml.libsbml.Delay writeDelay(Delay delay) {
		org.sbml.libsbml.Delay d = new org.sbml.libsbml.Delay(delay.getLevel(),
				delay.getVersion());
		saveSBaseProperties(delay, d);
		if (delay.isSetMath()) {
			d.setMath(LibSBMLUtils.convertASTNode(delay.getMath()));
		}
		return d;
	}

	private org.sbml.libsbml.Priority writePriority(Priority priority) {
		org.sbml.libsbml.Priority p = new org.sbml.libsbml.Priority(priority
				.getLevel(), priority.getVersion());
		saveSBaseProperties(priority, p);
		if (priority.isSetMath()) {
			p.setMath(LibSBMLUtils.convertASTNode(priority.getMath()));
		}
		return p;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLWriter#writeEvent(org.sbml.Event)
	 */

	private org.sbml.libsbml.Event writeEvent(Event event) throws SBMLException {
		org.sbml.libsbml.Event e = new org.sbml.libsbml.Event(event.getLevel(),
				event.getVersion());
		saveNamedSBaseProperties(event, e);
		if (event.isSetDelay()) {
			e.setDelay(writeDelay(event.getDelay()));
		}
		if (event.isSetPriority()) {
			e.setPriority(writePriority(event.getPriority()));
		}
		for (EventAssignment ea : event.getListOfEventAssignments()) {
			e.addEventAssignment(writeEventAssignment(ea));
		}
		if (event.isSetTimeUnits()) {
			e.setTimeUnits(event.getTimeUnits());
		}
		if (e.isSetTrigger()) {
			e.setTrigger(writeTrigger(event.getTrigger()));
		}
		e.setUseValuesFromTriggerTime(event.getUseValuesFromTriggerTime());
		return e;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLWriter#writeEventAssignment(org.sbml.EventAssignment)
	 */

	private org.sbml.libsbml.EventAssignment writeEventAssignment(
			EventAssignment eventAssignment, Object... args)
			throws SBMLException {
		org.sbml.libsbml.EventAssignment ea = new org.sbml.libsbml.EventAssignment(
				eventAssignment.getLevel(), eventAssignment.getVersion());
		saveMathContainerProperties(eventAssignment, ea);
		if (eventAssignment.isSetVariable())
			ea.setVariable(eventAssignment.getVariable());
		return ea;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.SBMLWriter#writeFunctionDefinition(org.sbml.FunctionDefinition)
	 */

	private org.sbml.libsbml.FunctionDefinition writeFunctionDefinition(
			FunctionDefinition functionDefinition) throws SBMLException {
		org.sbml.libsbml.FunctionDefinition fd = new org.sbml.libsbml.FunctionDefinition(
				functionDefinition.getLevel(), functionDefinition.getVersion());
		saveMathContainerProperties(functionDefinition, fd);
		return fd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.SBMLWriter#writeInitialAssignment(org.sbml.InitialAssignment)
	 */

	private org.sbml.libsbml.InitialAssignment writeInitialAssignment(
			InitialAssignment initialAssignment) throws SBMLException {
		org.sbml.libsbml.InitialAssignment ia = new org.sbml.libsbml.InitialAssignment(
				initialAssignment.getLevel(), initialAssignment.getVersion());
		saveMathContainerProperties(initialAssignment, ia);
		if (initialAssignment.isSetVariable())
			ia.setSymbol(initialAssignment.getVariable());
		return ia;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jlibsbml.SBMLWriter#writeKineticLaw(org.sbml.jlibsbml.KineticLaw
	 * )
	 */
	private org.sbml.libsbml.KineticLaw writeKineticLaw(KineticLaw kinteicLaw,
			Object... args) throws SBMLException {
		org.sbml.libsbml.KineticLaw k = new org.sbml.libsbml.KineticLaw(
				kinteicLaw.getLevel(), kinteicLaw.getVersion());
		saveMathContainerProperties(kinteicLaw, k);
		for (LocalParameter p : kinteicLaw.getListOfParameters()) {
			if (p.isSetUnits()) {
				if (!Unit
						.isUnitKind(p.getUnits(), p.getLevel(), p.getVersion())) {
					if (modelOrig.getUnitDefinition(p.getUnits()) == null) {
						modelOrig.addUnitDefinition(writeUnitDefinition(p
								.getUnitsInstance()));
					}
				}
			}
			k.addParameter(writeParameter(new Parameter(p)));
		}
		return k;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLWriter#writeModel(org.sbml.Model)
	 */
	public Object writeModel(Model model) throws SBMLException {
		org.sbml.libsbml.Model m = new org.sbml.libsbml.Model(model.getLevel(),
				model.getVersion());
		saveNamedSBaseProperties(model, m);
		for (UnitDefinition ud : model.getListOfUnitDefinitions()) {
			m.addUnitDefinition(writeUnitDefinition(ud));
		}
		for (FunctionDefinition fd : model.getListOfFunctionDefinitions()) {
			m.addFunctionDefinition(writeFunctionDefinition(fd));
		}
		for (CompartmentType ct : model.getListOfCompartmentTypes()) {
			m.addCompartmentType(writeCompartmentType(ct));
		}
		for (SpeciesType st : model.getListOfSpeciesTypes()) {
			m.addSpeciesType(writeSpeciesType(st));
		}
		for (Compartment c : model.getListOfCompartments()) {
			m.addCompartment(writeCompartment(c));
		}
		for (Species s : model.getListOfSpecies()) {
			m.addSpecies(writeSpecies(s));
		}
		for (Parameter p : model.getListOfParameters()) {
			m.addParameter(writeParameter(p));
		}
		for (Constraint c : model.getListOfConstraints()) {
			m.addConstraint(writeConstraint(c));
		}
		for (InitialAssignment ia : model.getListOfInitialAssignments()) {
			m.addInitialAssignment(writeInitialAssignment(ia));
		}
		for (Rule r : model.getListOfRules()) {
			m.addRule(writeRule(r));
		}
		for (Reaction r : model.getListOfReactions()) {
			m.addReaction(writeReaction(r));
		}
		for (Event e : model.getListOfEvents()) {
			m.addEvent(writeEvent(e));
		}
		return m;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.sbml.SBMLWriter#writeModifierSpeciesReference(org.sbml.
	 * ModifierSpeciesReference)
	 */

	private org.sbml.libsbml.ModifierSpeciesReference writeModifierSpeciesReference(
			ModifierSpeciesReference modifierSpeciesReference, Object... args) {
		org.sbml.libsbml.ModifierSpeciesReference m = new org.sbml.libsbml.ModifierSpeciesReference(
				modifierSpeciesReference.getLevel(), modifierSpeciesReference
						.getVersion());
		saveNamedSBaseProperties(modifierSpeciesReference, m);
		if (modifierSpeciesReference.isSetSpecies())
			m.setSpecies(modifierSpeciesReference.getSpecies());
		return m;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLWriter#writeParameter(org.sbml.Parameter)
	 */

	private org.sbml.libsbml.Parameter writeParameter(Parameter parameter,
			Object... args) {
		org.sbml.libsbml.Parameter p = new org.sbml.libsbml.Parameter(parameter
				.getLevel(), parameter.getVersion());
		saveNamedSBaseProperties(parameter, p);
		p.setConstant(parameter.getConstant());
		if (parameter.isSetUnits())
			p.setUnits(parameter.getUnits());
		if (parameter.isSetValue())
			p.setValue(parameter.getValue());
		return p;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLWriter#writeReaction(org.sbml.Reaction)
	 */
	private org.sbml.libsbml.Reaction writeReaction(Reaction reaction)
			throws SBMLException {
		org.sbml.libsbml.Reaction r = new org.sbml.libsbml.Reaction(reaction
				.getLevel(), reaction.getVersion());
		saveNamedSBaseProperties(reaction, r);
		r.setFast(reaction.getFast());
		r.setReversible(reaction.getReversible());
		r.setKineticLaw(writeKineticLaw(reaction.getKineticLaw()));
		for (SpeciesReference sr : reaction.getListOfReactants())
			r.addReactant(writeSpeciesReference(sr));
		for (SpeciesReference sr : reaction.getListOfProducts())
			r.addProduct(writeSpeciesReference(sr));
		for (ModifierSpeciesReference mr : reaction.getListOfModifiers())
			r.addModifier(writeModifierSpeciesReference(mr));
		return r;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLWriter#writeRule(org.sbml.Rule)
	 */

	private org.sbml.libsbml.Rule writeRule(Rule rule, Object... args) {
		org.sbml.libsbml.Rule r;
		if (rule.isAlgebraic()) {
			r = new org.sbml.libsbml.AlgebraicRule(rule.getLevel(), rule
					.getVersion());
		} else {
			if (rule.isAssignment()) {
				r = new org.sbml.libsbml.AssignmentRule(rule.getLevel(), rule
						.getVersion());
				if (((AssignmentRule) rule).isSetVariable()) {
					r.setVariable(((AssignmentRule) rule).getVariable());
				}
			} else {
				r = new org.sbml.libsbml.RateRule(rule.getLevel(), rule
						.getVersion());
				if (((RateRule) rule).isSetVariable()) {
					r.setVariable(((RateRule) rule).getVariable());
				}
			}
			if (((ExplicitRule) rule).isSetUnits()) {
				r.setUnits(((ExplicitRule) rule).getUnits());
			}
		}
		if (rule.isSetMath()) {
			r.setMath(LibSBMLUtils.convertASTNode(rule.getMath()));
		}
		saveSBaseProperties(rule, r);
		return r;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLWriter#writeSBML(java.lang.Object, java.lang.String)
	 */
	public boolean writeSBML(Object sbmlDocument, String filename)
			throws SBMLException {
		return writeSBML(sbmlDocument, filename, null, null);
	}

	/**
	 * 
	 * @param sbmlDocument
	 * @param filename
	 * @param programName
	 * @param programVersionNumber
	 * @return
	 * @throws SBMLException
	 */
	public boolean writeSBML(Object sbmlDocument, String filename,
			String programName, String programVersionNumber)
			throws SBMLException {
		if (!(sbmlDocument instanceof org.sbml.libsbml.SBMLDocument)
				&& !(sbmlDocument instanceof org.sbml.libsbml.Model))
			throw new IllegalArgumentException(
					"sbmlDocument must be an instance of an org.sbml.libsbml.SBMLDocument or a org.sbml.libsbml.Model");
		org.sbml.libsbml.SBMLDocument d;
		if (sbmlDocument instanceof org.sbml.libsbml.SBMLDocument)
			d = (org.sbml.libsbml.SBMLDocument) sbmlDocument;
		else
			d = ((org.sbml.libsbml.Model) sbmlDocument).getSBMLDocument();
		org.sbml.libsbml.SBMLWriter writer = new org.sbml.libsbml.SBMLWriter();
		if (programName != null)
			writer.setProgramName(programName);
		if (programVersionNumber != null)
			writer.setProgramVersion(programVersionNumber);

		d.checkInternalConsistency();
		d.checkConsistency();
		boolean errorFatal = false;
		StringBuilder builder = new StringBuilder();
		for (long i = 0; i < d.getNumErrors(); i++) {
			org.sbml.libsbml.SBMLError e = d.getError(i);
			builder.append(e.getMessage());
			builder.append(System.getProperty("line.separator"));
			if (e.isError() || e.isFatal())
				errorFatal = true;
		}
		if (errorFatal)
			System.err.println(builder.toString());
		boolean success = writer.writeSBML(d, filename);
		if (!success)
			throw new SBMLException(builder.toString());
		return success;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLWriter#writeSpecies(org.sbml.Species)
	 */

	private org.sbml.libsbml.Species writeSpecies(Species species) {
		org.sbml.libsbml.Species s = new org.sbml.libsbml.Species(species
				.getLevel(), species.getVersion());
		saveNamedSBaseProperties(species, s);
		s.setBoundaryCondition(species.getBoundaryCondition());
		s.setCharge(species.getCharge());
		s.setCompartment(species.getCompartment());
		s.setConstant(species.getConstant());
		s.setHasOnlySubstanceUnits(species.getHasOnlySubstanceUnits());
		if (species.isSetInitialAmount())
			s.setInitialAmount(species.getInitialAmount());
		else if (species.isSetInitialConcentration())
			s.setInitialConcentration(species.getInitialConcentration());
		if (species.isSetSpeciesType())
			s.setSpeciesType(species.getSpeciesType());
		if (species.isSetSubstanceUnits())
			species.getSubstanceUnits();
		return s;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLWriter#writeSpeciesReference(org.sbml.SpeciesReference)
	 */

	private org.sbml.libsbml.SpeciesReference writeSpeciesReference(
			SpeciesReference speciesReference, Object... args) {
		org.sbml.libsbml.SpeciesReference sr = new org.sbml.libsbml.SpeciesReference(
				speciesReference.getLevel(), speciesReference.getVersion());
		saveNamedSBaseProperties(speciesReference, sr);
		if (speciesReference.isSetSpecies())
			sr.setSpecies(speciesReference.getSpecies());
		if (speciesReference.isSetStoichiometryMath())
			sr.setStoichiometryMath(writeStoichoimetryMath(speciesReference
					.getStoichiometryMath()));
		else
			sr.setStoichiometry(speciesReference.getStoichiometry());
		return sr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLWriter#writeSpeciesType(org.sbml.SpeciesType)
	 */

	private org.sbml.libsbml.SpeciesType writeSpeciesType(
			SpeciesType speciesType) {
		org.sbml.libsbml.SpeciesType st = new org.sbml.libsbml.SpeciesType(
				speciesType.getLevel(), speciesType.getVersion());
		saveNamedSBaseProperties(speciesType, st);
		return st;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.SBMLWriter#writeStoichoimetryMath(org.sbml.StoichiometryMath)
	 */

	private org.sbml.libsbml.StoichiometryMath writeStoichoimetryMath(
			StoichiometryMath stoichiometryMath) {
		org.sbml.libsbml.StoichiometryMath sm = new org.sbml.libsbml.StoichiometryMath(
				stoichiometryMath.getLevel(), stoichiometryMath.getVersion());
		saveSBaseProperties(stoichiometryMath, sm);
		if (stoichiometryMath.isSetMath())
			sm.setMath(LibSBMLUtils.convertASTNode(stoichiometryMath.getMath()));
		return sm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLWriter#writeTrigger(org.sbml.Trigger)
	 */
	private org.sbml.libsbml.Trigger writeTrigger(Trigger trigger) {
		org.sbml.libsbml.Trigger t = new org.sbml.libsbml.Trigger(trigger
				.getLevel(), trigger.getVersion());
		saveSBaseProperties(trigger, t);
		if (trigger.isSetInitialValue()) {
			t.setInitialValue(trigger.getInitialValue());
		}
		if (trigger.isSetPersistent()) {
			t.setPersistent(trigger.getPersistent());
		}
		if (trigger.isSetMath()) {
			t.setMath(LibSBMLUtils.convertASTNode(trigger.getMath()));
		}
		return t;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLWriter#writeUnit(org.sbml.Unit)
	 */

	private org.sbml.libsbml.Unit writeUnit(Unit unit, Object... args) {
		org.sbml.libsbml.Unit u = new org.sbml.libsbml.Unit(unit.getLevel(),
				unit.getVersion());
		saveSBaseProperties(unit, u);
		switch (unit.getKind()) {
		case AMPERE:
			u.setKind(libsbmlConstants.UNIT_KIND_AMPERE);
			break;
		case BECQUEREL:
			u.setKind(libsbmlConstants.UNIT_KIND_BECQUEREL);
			break;
		case CANDELA:
			u.setKind(libsbmlConstants.UNIT_KIND_CANDELA);
			break;
		case CELSIUS:
			u.setKind(libsbmlConstants.UNIT_KIND_CELSIUS);
			break;
		case COULOMB:
			u.setKind(libsbmlConstants.UNIT_KIND_COULOMB);
			break;
		case DIMENSIONLESS:
			u.setKind(libsbmlConstants.UNIT_KIND_DIMENSIONLESS);
			break;
		case FARAD:
			u.setKind(libsbmlConstants.UNIT_KIND_FARAD);
			break;
		case GRAM:
			u.setKind(libsbmlConstants.UNIT_KIND_GRAM);
			break;
		case GRAY:
			u.setKind(libsbmlConstants.UNIT_KIND_GRAY);
			break;
		case HENRY:
			u.setKind(libsbmlConstants.UNIT_KIND_HENRY);
			break;
		case HERTZ:
			u.setKind(libsbmlConstants.UNIT_KIND_HERTZ);
			break;
		case INVALID:
			u.setKind(libsbmlConstants.UNIT_KIND_INVALID);
			break;
		case ITEM:
			u.setKind(libsbmlConstants.UNIT_KIND_ITEM);
			break;
		case JOULE:
			u.setKind(libsbmlConstants.UNIT_KIND_JOULE);
			break;
		case KATAL:
			u.setKind(libsbmlConstants.UNIT_KIND_KATAL);
			break;
		case KELVIN:
			u.setKind(libsbmlConstants.UNIT_KIND_KELVIN);
			break;
		case KILOGRAM:
			u.setKind(libsbmlConstants.UNIT_KIND_KILOGRAM);
			break;
		case LITER:
			u.setKind(libsbmlConstants.UNIT_KIND_LITER);
			break;
		case LITRE:
			u.setKind(libsbmlConstants.UNIT_KIND_LITRE);
			break;
		case LUMEN:
			u.setKind(libsbmlConstants.UNIT_KIND_LUMEN);
			break;
		case LUX:
			u.setKind(libsbmlConstants.UNIT_KIND_LUX);
			break;
		case METER:
			u.setKind(libsbmlConstants.UNIT_KIND_METER);
			break;
		case METRE:
			u.setKind(libsbmlConstants.UNIT_KIND_METRE);
			break;
		case MOLE:
			u.setKind(libsbmlConstants.UNIT_KIND_MOLE);
			break;
		case NEWTON:
			u.setKind(libsbmlConstants.UNIT_KIND_NEWTON);
			break;
		case OHM:
			u.setKind(libsbmlConstants.UNIT_KIND_OHM);
			break;
		case PASCAL:
			u.setKind(libsbmlConstants.UNIT_KIND_PASCAL);
			break;
		case RADIAN:
			u.setKind(libsbmlConstants.UNIT_KIND_RADIAN);
			break;
		case SECOND:
			u.setKind(libsbmlConstants.UNIT_KIND_SECOND);
			break;
		case SIEMENS:
			u.setKind(libsbmlConstants.UNIT_KIND_SIEMENS);
			break;
		case SIEVERT:
			u.setKind(libsbmlConstants.UNIT_KIND_SIEVERT);
			break;
		case STERADIAN:
			u.setKind(libsbmlConstants.UNIT_KIND_STERADIAN);
			break;
		case TESLA:
			u.setKind(libsbmlConstants.UNIT_KIND_TESLA);
			break;
		case VOLT:
			u.setKind(libsbmlConstants.UNIT_KIND_VOLT);
			break;
		case WATT:
			u.setKind(libsbmlConstants.UNIT_KIND_WATT);
			break;
		case WEBER:
			u.setKind(libsbmlConstants.UNIT_KIND_WEBER);
			break;
		}
		u.setExponent((int) Math.round(unit.getExponent()));
		u.setMultiplier(unit.getMultiplier());
		u.setOffset(unit.getOffset());
		u.setScale(unit.getScale());
		return u;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLWriter#writeUnitDefinition(org.sbml.UnitDefinition)
	 */
	private org.sbml.libsbml.UnitDefinition writeUnitDefinition(
			UnitDefinition unitDefinition) {
		org.sbml.libsbml.UnitDefinition ud = new org.sbml.libsbml.UnitDefinition(
				unitDefinition.getLevel(), unitDefinition.getVersion());
		saveNamedSBaseProperties(unitDefinition, ud);
		for (Unit u : unitDefinition.getListOfUnits()) {
			org.sbml.libsbml.Unit unit = writeUnit(u);
			int code = ud.addUnit(unit);
			switch (code) {
			case libsbmlConstants.LIBSBML_OPERATION_SUCCESS:
				break;
			case libsbmlConstants.LIBSBML_LEVEL_MISMATCH:
				System.err.println("level mismatch");
				break;
			case libsbmlConstants.LIBSBML_VERSION_MISMATCH:
				System.err.println("version mismatch");
				System.err.println("unit L" + unit.getLevel() + "\tV"
						+ unit.getVersion());
				System.err.println("udef L" + ud.getLevel() + "\tV"
						+ ud.getVersion());
				break;
			case libsbmlConstants.LIBSBML_DUPLICATE_OBJECT_ID:
				System.err.println("duplicate object id");
				break;
			case libsbmlConstants.LIBSBML_OPERATION_FAILED:
				System.err.println("operation failed");
				break;
			default:
				System.err.println("error code:\t" + code);
				break;
			}
		}
		return ud;
	}

}

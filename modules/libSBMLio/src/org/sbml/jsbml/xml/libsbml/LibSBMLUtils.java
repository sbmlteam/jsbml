/*
 * $Id:  LibSBMLUtils.java 15:16:19 maichele$
 * $URL$
 * ---------------------------------------------------------------------
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
 * ---------------------------------------------------------------------
 */
package org.sbml.jsbml.xml.libsbml;

import java.util.Calendar;
import java.util.Date;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AlgebraicRule;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Creator;
import org.sbml.jsbml.History;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.SimpleSpeciesReference;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.libsbml.ModelCreator;
import org.sbml.libsbml.ModelHistory;
import org.sbml.libsbml.SBMLDocument;
import org.sbml.libsbml.libsbmlConstants;

/**
 * this class consists of help-methods which are used in all classes of this package
 * @author Meike Aichele
 * @version $Rev$
 * @since 1.0
 */
public class LibSBMLUtils {

	/**
	 * 
	 * @param astnode
	 * @return
	 */
	public static org.sbml.libsbml.ASTNode convertASTNode(ASTNode astnode) {
		org.sbml.libsbml.ASTNode libAstNode;
		switch (astnode.getType()) {
		case REAL:
			libAstNode = new org.sbml.libsbml.ASTNode(libsbmlConstants.AST_REAL);
			libAstNode.setValue(astnode.getReal());
			break;
		case INTEGER:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_INTEGER);
			libAstNode.setValue(astnode.getInteger());
			break;
		case FUNCTION_LOG:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_LOG);
			break;
		case POWER:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_POWER);
			break;
		case PLUS:
			libAstNode = new org.sbml.libsbml.ASTNode(libsbmlConstants.AST_PLUS);
			break;
		case MINUS:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_MINUS);
			break;
		case TIMES:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_TIMES);
			break;
		case DIVIDE:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_DIVIDE);
			break;
		case RATIONAL:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_RATIONAL);
			break;
		case NAME_TIME:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_NAME_TIME);
			break;
		case FUNCTION_DELAY:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_DELAY);
			break;
		case NAME:
			libAstNode = new org.sbml.libsbml.ASTNode(libsbmlConstants.AST_NAME);
			libAstNode.setName(astnode.getName());
			break;
		case CONSTANT_PI:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_CONSTANT_PI);
			break;
		case CONSTANT_E:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_CONSTANT_E);
			break;
		case CONSTANT_TRUE:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_CONSTANT_TRUE);
			break;
		case CONSTANT_FALSE:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_CONSTANT_FALSE);
			break;
		case REAL_E:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_REAL_E);
			libAstNode.setValue(astnode.getMantissa(), astnode.getExponent());
			break;
		case FUNCTION_ABS:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_ABS);
			break;
		case FUNCTION_ARCCOS:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_ARCCOS);
			break;
		case FUNCTION_ARCCOSH:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_ARCCOSH);
			break;
		case FUNCTION_ARCCOT:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_ARCCOT);
			break;
		case FUNCTION_ARCCOTH:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_ARCCOTH);
			break;
		case FUNCTION_ARCCSC:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_ARCCSC);
			break;
		case FUNCTION_ARCCSCH:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_ARCCSCH);
			break;
		case FUNCTION_ARCSEC:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_ARCSEC);
			break;
		case FUNCTION_ARCSECH:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_ARCSECH);
			break;
		case FUNCTION_ARCSIN:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_ARCSIN);
			break;
		case FUNCTION_ARCSINH:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_ARCSINH);
			break;
		case FUNCTION_ARCTAN:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_ARCTAN);
			break;
		case FUNCTION_ARCTANH:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_ARCTANH);
			break;
		case FUNCTION_CEILING:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_CEILING);
			break;
		case FUNCTION_COS:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_COS);
			break;
		case FUNCTION_COSH:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_COSH);
			break;
		case FUNCTION_COT:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_COT);
			break;
		case FUNCTION_COTH:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_COTH);
			break;
		case FUNCTION_CSC:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_CSC);
			break;
		case FUNCTION_CSCH:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_CSCH);
			break;
		case FUNCTION_EXP:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_EXP);
			break;
		case FUNCTION_FACTORIAL:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_FACTORIAL);
			break;
		case FUNCTION_FLOOR:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_FLOOR);
			break;
		case FUNCTION_LN:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_LN);
			break;
		case FUNCTION_POWER:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_POWER);
			break;
		case FUNCTION_ROOT:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_ROOT);
			break;
		case FUNCTION_SEC:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_SEC);
			break;
		case FUNCTION_SECH:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_SECH);
			break;
		case FUNCTION_SIN:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_SIN);
			break;
		case FUNCTION_SINH:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_SINH);
			break;
		case FUNCTION_TAN:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_TAN);
			break;
		case FUNCTION_TANH:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_TANH);
			break;
		case FUNCTION:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION);
			libAstNode.setName(astnode.getName());
			break;
		case LAMBDA:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_LAMBDA);
			break;
		case LOGICAL_AND:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_LOGICAL_AND);
			break;
		case LOGICAL_XOR:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_LOGICAL_XOR);
			break;
		case LOGICAL_OR:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_LOGICAL_OR);
			break;
		case LOGICAL_NOT:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_LOGICAL_NOT);
			break;
		case FUNCTION_PIECEWISE:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_FUNCTION_PIECEWISE);
			break;
		case RELATIONAL_EQ:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_RELATIONAL_EQ);
			break;
		case RELATIONAL_GEQ:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_RELATIONAL_GEQ);
			break;
		case RELATIONAL_GT:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_RELATIONAL_GT);
			break;
		case RELATIONAL_NEQ:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_RELATIONAL_NEQ);
			break;
		case RELATIONAL_LEQ:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_RELATIONAL_LEQ);
			break;
		case RELATIONAL_LT:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_RELATIONAL_LT);
			break;
		default:
			libAstNode = new org.sbml.libsbml.ASTNode(
					libsbmlConstants.AST_UNKNOWN);
			break;
		}
		for (ASTNode child : astnode.getListOfNodes())
			libAstNode.addChild(convertASTNode(child));
		return libAstNode;
	}

	/**
	 * 
	 * @param t
	 * @return a new libSBMl-CVTerm with the attributes of the incoming JSBML-CVTerm t
	 */
	public static org.sbml.libsbml.CVTerm convertCVTerm(CVTerm t) {
		org.sbml.libsbml.CVTerm libCVt = new org.sbml.libsbml.CVTerm();
		switch (t.getQualifierType()) {
		case MODEL_QUALIFIER:
			libCVt.setQualifierType(libsbmlConstants.MODEL_QUALIFIER);
			switch (t.getModelQualifierType()) {
			case BQM_IS:
				libCVt.setModelQualifierType(libsbmlConstants.BQM_IS);
				break;
			case BQM_IS_DESCRIBED_BY:
				libCVt
				.setModelQualifierType(libsbmlConstants.BQM_IS_DESCRIBED_BY);
				break;
			case BQM_UNKNOWN:
				libCVt.setModelQualifierType(libsbmlConstants.BQM_UNKNOWN);
				break;
			default:
				break;
			}
			break;
		case BIOLOGICAL_QUALIFIER:
			libCVt.setQualifierType(libsbmlConstants.BIOLOGICAL_QUALIFIER);
			switch (t.getBiologicalQualifierType()) {
			case BQB_ENCODES:
				libCVt.setBiologicalQualifierType(libsbmlConstants.BQB_ENCODES);
				break;
			case BQB_HAS_PART:
				libCVt
				.setBiologicalQualifierType(libsbmlConstants.BQB_HAS_PART);
				break;
			case BQB_HAS_VERSION:
				libCVt
				.setBiologicalQualifierType(libsbmlConstants.BQB_HAS_VERSION);
				break;
			case BQB_IS:
				libCVt.setBiologicalQualifierType(libsbmlConstants.BQB_IS);
				break;
			case BQB_IS_DESCRIBED_BY:
				libCVt
				.setBiologicalQualifierType(libsbmlConstants.BQB_IS_DESCRIBED_BY);
				break;
			case BQB_IS_ENCODED_BY:
				libCVt
				.setBiologicalQualifierType(libsbmlConstants.BQB_IS_ENCODED_BY);
				break;
			case BQB_IS_HOMOLOG_TO:
				libCVt
				.setBiologicalQualifierType(libsbmlConstants.BQB_IS_HOMOLOG_TO);
				break;
			case BQB_IS_PART_OF:
				libCVt
				.setBiologicalQualifierType(libsbmlConstants.BQB_IS_PART_OF);
				break;
			case BQB_IS_VERSION_OF:
				libCVt
				.setBiologicalQualifierType(libsbmlConstants.BQB_IS_VERSION_OF);
				break;
			case BQB_OCCURS_IN:
				libCVt
				.setBiologicalQualifierType(libsbmlConstants.BQB_OCCURS_IN);
				break;
			case BQB_UNKNOWN:
				libCVt.setBiologicalQualifierType(libsbmlConstants.BQB_UNKNOWN);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		for (int j = 0; j < t.getResourceCount(); j++) {
			libCVt.addResource(t.getResourceURI(j));
		}
		return libCVt;
	}

	/**
	 * 
	 * @param createdDate
	 * @return a libSBML Date-Object with the same attributes as the incoming parameter createdDate
	 */
	public static org.sbml.libsbml.Date convertDate(Date createdDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(createdDate);
		return new org.sbml.libsbml.Date(c.get(Calendar.YEAR), c
				.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), c
				.get(Calendar.HOUR), c.get(Calendar.MINUTE), c
				.get(Calendar.SECOND), (int) Math.signum(c.getTimeZone()
						.getRawOffset()), c.getTimeZone().getRawOffset() / 3600000, c
						.getTimeZone().getRawOffset() / 60000);
	}

	/**
	 * 
	 * @param history
	 * @return
	 */
	public static ModelHistory convertHistory(History history) {
		org.sbml.libsbml.ModelHistory mo = new org.sbml.libsbml.ModelHistory();
		if (history.isSetCreatedDate())
			mo.setCreatedDate(LibSBMLUtils.convertDate(history.getCreatedDate()));
		if (history.isSetModifiedDate())
			mo.setModifiedDate(LibSBMLUtils.convertDate(history.getModifiedDate()));
		// add creators
		for (Creator mc : history.getListOfCreators()) {
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
			for (int j = 0; (j < history.getCreatorCount()) && !contains; j++) {
				Creator mc = history.getCreator(j);
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
			if (!contains){
				mo.getListCreators().remove(i);
			}
		}
		return mo;
	}


	/**
	 * this method creates a new {@link ModelCreator} 
	 * and fills it with the attributes of the given jsbml-{@link Creator}
	 * @param c
	 * @return
	 */
	public static ModelCreator convertToModelCreator(Creator c) {
		org.sbml.libsbml.ModelCreator mc = new org.sbml.libsbml.ModelCreator();
		if (c.isSetEmail()){
			mc.setEmail(c.getEmail());
		}
		if (c.isSetFamilyName()){
			mc.setFamilyName(c.getFamilyName());
		}
		if (c.isSetGivenName()){
			mc.setGivenName(c.getGivenName());
		}
		if (c.isSetOrganisation()){
			mc.setOrganisation(c.getOrganisation());
		}
		if (c.isSetOrganization()){
			mc.setOrganization(c.getOrganization());
		}
		return mc;
	}

	/**
	 * 
	 * @param con
	 * @param doc
	 * @return
	 */
	public static int getContraintIndex(Constraint con, org.sbml.jsbml.SBMLDocument doc) {
		int index = 0;
		for (int k = 0; k < doc.getModel().getListOfConstraints().size(); k++){
			Constraint c = doc.getModel().getConstraint(k);
			if (c.equals(con)){
				index = k;
				break;
			}
		}
		return index;
	}

	/**
	 * this method compares the strings of the formulas of the incoming {@link AlgebraicRule} and the AlgebraicRules in the libSBMlDocument
	 * @param libDoc
	 * @param algRule
	 * @return null if the rule was not found in the libSBMLDocument, else the corresponding rule
	 */
	@SuppressWarnings("deprecation")
	public static org.sbml.libsbml.AlgebraicRule getCorrespondingAlgRule(
			SBMLDocument libDoc, AlgebraicRule algRule) {
		if (libDoc.getModel().getListOfRules() != null){
			for (int i=0; i<libDoc.getModel().getListOfRules().size(); i++){
				org.sbml.libsbml.Rule r = libDoc.getModel().getListOfRules().get(i);
				if (r.isAlgebraic()){
					org.sbml.libsbml.AlgebraicRule ar = (org.sbml.libsbml.AlgebraicRule) r;
					if (ar.getFormula().equals(algRule.getFormula())){
						return ar;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param creator
	 * @param doc
	 * @return integer index
	 */
	public static long getCreatorIndex(Creator creator, org.sbml.jsbml.SBMLDocument doc) {
		int index = 0;
		for (int k = 0; k < doc.getHistory().getCreatorCount(); k++){
			Creator c = doc.getHistory().getCreator(k);
			if (c.equals(creator)){
				index = k;
				break;
			}
		}
		return index;
	}

	/**
	 * searches the index of the incoming {@link Unit} in the ListOfUnits of the {@link UnitDefinition}
	 * @param unit
	 * @param udef
	 * @return
	 */
	public static int getUnitIndex(Unit unit, UnitDefinition udef) {
		int index = 0;
		for (int k = 0; k < udef.getListOfUnits().size();k++){
			Unit u = udef.getUnit(k);
			if (u.equals(unit)){
				index = k;
				break;
			}
		}
		return index;
	}

	/**
	 * 
	 * @param unit
	 * @param u
	 */
	@SuppressWarnings("deprecation")
	public static int transferKindProperties(Unit unit, org.sbml.libsbml.Unit u) {
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
		return u.getKind();
	}


	/**
	 * sets the math ASTNode in the libSBML object if it's set in the JSBML object
	 * and calls the convert-method for the ASTNodes.
	 * @param mathCont
	 * @param libMathCont
	 */
	public static void transferMathContainerProperties(MathContainer mathCont, org.sbml.libsbml.SBase libMathCont){
		LibSBMLUtils.transferSBaseProperties(mathCont, libMathCont);
		if (mathCont.isSetMath()){
			if (libMathCont instanceof org.sbml.libsbml.FunctionDefinition){
				((org.sbml.libsbml.FunctionDefinition) libMathCont).setMath(convertASTNode(mathCont.getMath()));
			}else if (libMathCont instanceof org.sbml.libsbml.KineticLaw){
				((org.sbml.libsbml.KineticLaw) libMathCont).setMath(convertASTNode(mathCont.getMath()));
			}else if (libMathCont instanceof org.sbml.libsbml.Rule){
				((org.sbml.libsbml.Rule) libMathCont).setMath(convertASTNode(mathCont.getMath()));
			}else if (libMathCont instanceof org.sbml.libsbml.StoichiometryMath){
				((org.sbml.libsbml.StoichiometryMath) libMathCont).setMath(convertASTNode(mathCont.getMath()));
			}else if (libMathCont instanceof org.sbml.libsbml.Trigger){
				((org.sbml.libsbml.Trigger) libMathCont).setMath(convertASTNode(mathCont.getMath()));
			}else if (libMathCont instanceof org.sbml.libsbml.EventAssignment){
				((org.sbml.libsbml.EventAssignment) libMathCont).setMath(convertASTNode(mathCont.getMath()));
			}else if (libMathCont instanceof org.sbml.libsbml.InitialAssignment){
				((org.sbml.libsbml.InitialAssignment) libMathCont).setMath(convertASTNode(mathCont.getMath()));
			}else if (libMathCont instanceof org.sbml.libsbml.Constraint){
				((org.sbml.libsbml.Constraint) libMathCont).setMath(convertASTNode(mathCont.getMath()));
			}else if (libMathCont instanceof org.sbml.libsbml.Delay){
				((org.sbml.libsbml.Delay) libMathCont).setMath(convertASTNode(mathCont.getMath()));
			}else if (libMathCont instanceof org.sbml.libsbml.Priority){
				((org.sbml.libsbml.Priority) libMathCont).setMath(convertASTNode(mathCont.getMath()));
			}
		}

	}

	/**
	 * sets the name and the id in the libSBML object, when it's set in the JSBML object
	 * and calls the method transferSBaseProperties(SBase, libSBase).
	 * @param sbase
	 * @param libSBase
	 */
	public static void transferNamedSBaseProperties(SBase sbase, org.sbml.libsbml.SBase libSBase){
		if (((org.sbml.jsbml.NamedSBase) sbase).isSetName()){
			libSBase.setName(((org.sbml.jsbml.NamedSBase) sbase).getName());
		}
		if (((org.sbml.jsbml.NamedSBase) sbase).isSetId()){
			libSBase.setId(((org.sbml.jsbml.NamedSBase) sbase).getId());
		}
		LibSBMLUtils.transferSBaseProperties(sbase, libSBase);
	}

	/**
	 * sets {@link MetaId}, {@link SBOTerm}, {@link Notes} and {@link Annotation} in the libSBML object, 
	 * if it's set in the JSBML object.
	 * @param sbase
	 * @param libSBase
	 */
	public static void transferSBaseProperties(SBase sbase,
			org.sbml.libsbml.SBase libSBase) {
		if (sbase.isSetMetaId()) {
			libSBase.setMetaId(libSBase.getMetaId());
		}
		if (sbase.isSetSBOTerm()) {
			libSBase.setSBOTerm(libSBase.getSBOTerm());
		}
		if (sbase.isSetNotes()) {
			libSBase.setNotes(libSBase.getNotesString());
		}
		if (sbase.isSetAnnotation()){
			libSBase.setAnnotation(sbase.getAnnotationString());
		}
	}

	/**
	 * sets the species in libSBML object if the species is set in the JSBML object
	 * and calls the method {@link transferNamedSBaseProperties(SBase, libSBase)}.
	 * @param sbase
	 * @param libSBase
	 */
	public static void transferSimpleSpeciesReferenceProperties(SBase sbase, org.sbml.libsbml.SBase libSBase){
		if (((SimpleSpeciesReference) sbase).isSetSpecies()){
			((org.sbml.libsbml.SimpleSpeciesReference) libSBase).setSpecies(((SimpleSpeciesReference) sbase).getSpecies());
		}
		transferNamedSBaseProperties(sbase, libSBase);
	}
}

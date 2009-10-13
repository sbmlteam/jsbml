/*
 *  SBMLsqueezer creates rate equations for reactions in SBML files
 *  (http://sbml.org).
 *  Copyright (C) 2009 ZBIT, University of Tübingen, Andreas Dräger
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.sbml.jsbml.io;

import java.util.LinkedList;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.SBaseChangedListener;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.libsbml.libsbmlConstants;

/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 */
public abstract class AbstractSBMLReader implements SBMLReader {

	/**
	 * 
	 */
	protected Model model;
	/**
	 * 
	 */
	protected LinkedList<SBaseChangedListener> listOfSBaseChangeListeners;

	/**
	 * 
	 */
	public AbstractSBMLReader() {
		listOfSBaseChangeListeners = new LinkedList<SBaseChangedListener>();
	}

	/**
	 * 
	 * @param model
	 */
	public AbstractSBMLReader(Object model) {
		this.model = readModel(model);
	}

	/**
	 * 
	 * @param model
	 */
	public static final void addPredefinedUnitDefinitions(Model model) {
		if (model.getUnitDefinition("substance") == null)
			model.addUnitDefinition(UnitDefinition.SUBSTANCE);
		if (model.getUnitDefinition("volume") == null)
			model.addUnitDefinition(UnitDefinition.VOLUME);
		if (model.getUnitDefinition("area") == null)
			model.addUnitDefinition(UnitDefinition.AREA);
		if (model.getUnitDefinition("length") == null)
			model.addUnitDefinition(UnitDefinition.LENGTH);
		if (model.getUnitDefinition("time") == null)
			model.addUnitDefinition(UnitDefinition.TIME);
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
				for (Parameter p : ((KineticLaw) parent).getListOfParameters())
					if (p.getId().equals(math.getName())) {
						ast.setVariable(p);
						break;
					}
			if (ast.getVariable() == null) {
				NamedSBase nsb = model.findNamedSBase(math.getName());
				if (nsb == null)
					ast.setName(math.getName());
				else
					ast.setVariable(nsb);
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

	public Model getModel() {
		return model;
	}

	/**
	 * 
	 * @param sbcl
	 */
	public void addSBaseChangeListener(SBaseChangedListener sbcl) {
		if (!listOfSBaseChangeListeners.contains(sbcl))
			listOfSBaseChangeListeners.add(sbcl);
	}

	/**
	 * 
	 * @param sb
	 */
	public void addAllSBaseChangeListenersTo(SBase sb) {
		for (SBaseChangedListener listener : listOfSBaseChangeListeners)
			sb.addChangeListener(listener);
	}

	/**
	 * 
	 * @return
	 */
	public abstract Object getOriginalModel();

}

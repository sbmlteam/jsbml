/*
 * $Id$
 * $URL$
 * ---------------------------------------------------------------------
 * This file is part of the SBMLeditor API library.
 *
 * Copyright (C) 2011 by the University of Tuebingen, Germany.
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation. A copy of the license
 * agreement is provided in the file named "LICENSE.txt" included with
 * this software distribution and also available online as
 * <http://www.gnu.org/licenses/lgpl-3.0-standalone.html>.
 * ---------------------------------------------------------------------
 */
package org.sbml.jsbml.cdplugin;

import java.beans.PropertyChangeEvent;
import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import jp.sbi.celldesigner.plugin.CellDesignerPlugin;
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
import jp.sbi.celldesigner.plugin.PluginListOf;
import jp.sbi.celldesigner.plugin.PluginModel;
import jp.sbi.celldesigner.plugin.PluginModifierSpeciesReference;
import jp.sbi.celldesigner.plugin.PluginParameter;
import jp.sbi.celldesigner.plugin.PluginRateRule;
import jp.sbi.celldesigner.plugin.PluginReaction;
import jp.sbi.celldesigner.plugin.PluginRule;
import jp.sbi.celldesigner.plugin.PluginSBase;
import jp.sbi.celldesigner.plugin.PluginSpecies;
import jp.sbi.celldesigner.plugin.PluginSpeciesAlias;
import jp.sbi.celldesigner.plugin.PluginSpeciesReference;
import jp.sbi.celldesigner.plugin.PluginSpeciesType;
import jp.sbi.celldesigner.plugin.PluginUnit;
import jp.sbi.celldesigner.plugin.PluginUnitDefinition;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AbstractMathContainer;
import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractNamedSBaseWithUnit;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.AbstractTreeNode;
import org.sbml.jsbml.AlgebraicRule;
import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.AnnotationElement;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.CVTerm;
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
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ListOf.Type;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Priority;
import org.sbml.jsbml.QuantityWithUnit;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBO;
import org.sbml.jsbml.SimpleSpeciesReference;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.StoichiometryMath;
import org.sbml.jsbml.Symbol;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.xml.XMLToken;
import org.sbml.libsbml.XMLNode;
import org.sbml.libsbml.libsbml;
import org.sbml.libsbml.libsbmlConstants;

/**
 * @author Alexander Peltzer
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @date 10:50:22
 */
@SuppressWarnings("deprecation")
public class PluginChangeListener implements TreeNodeChangeListener {

	/**
   * 
   */
	private CellDesignerPlugin plugin;

	/**
	 * 
	 */
	private static final transient Logger logger = Logger
			.getLogger(PluginChangeListener.class);

	/**
	 * 
	 */
	private PluginModel plugModel;

	/**
	 * 
	 * @param plugin
	 */
	public PluginChangeListener(SBMLDocument doc, CellDesignerPlugin plugin) {
		this.plugin = plugin;
		this.plugModel = plugin.getSelectedModel();
		if (doc != null) {
			Model model = doc.getModel();
			if (model != null) {

			}
		}
	}

	/**
	 * This method updates the Model on propertyChanges. It follows the TreeNodeChangeEvent order in a lexicographic way.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.
	 * PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		Object eventsource = event.getSource();
		String prop = event.getPropertyName();
		if (prop.equals(TreeNodeChangeEvent.addCVTerm)) {
			Annotation anno = (Annotation) eventsource;
			CVTerm term = event.getNewValue() != null? (CVTerm) event.getNewValue(): null;
			anno.addCVTerm(term);
		} else if (prop.equals(TreeNodeChangeEvent.addExtension)) {
			logger.log(Level.DEBUG, String.format("Couldn't change the %s in the Model", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.addNamespace)) {
			logger.log(Level.DEBUG, String.format("Couldn't change the %s in the Model", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.annotation)) {
			logger.log(Level.DEBUG, String.format("Couldn't change the %s in the Model", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.annotationNameSpaces)) {
			logger.log(Level.DEBUG, String.format("Couldn't change the %s in the Model", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.areaUnits)) {
			logger.log(Level.DEBUG, String.format("Changing %s in the Model only supported with SBML version > 3.", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.baseListType)) {
			//Method is only called in creating a new List, which means we don't need to update anything here.
		} else if (prop.equals(TreeNodeChangeEvent.boundaryCondition)) {
			Species species = (Species) eventsource;
			PluginSpecies plugSpec = plugModel.getSpecies(species.getId());
			plugSpec.setBoundaryCondition(species.getBoundaryCondition());
			plugin.notifySBaseChanged(plugSpec);
		} else if (prop.equals(TreeNodeChangeEvent.charge)) {
			Species species = (Species) eventsource;
			PluginSpecies plugSpec = plugModel.getSpecies(species.getId());
			plugSpec.setCharge(species.getCharge());
			plugin.notifySBaseChanged(plugSpec);
		} else if (prop.equals(TreeNodeChangeEvent.childNode)) {
			ASTNode node = (ASTNode) eventsource;
			MathContainer mc = node.getParentSBMLObject();
			mc.setMath((ASTNode) event.getNewValue());
			plugin.notifySBaseChanged(getCorrespondingElementInJSBML(mc));
		} else if (prop.equals(TreeNodeChangeEvent.className)) {
			ASTNode node = (ASTNode) eventsource;
			MathContainer mc = node.getParentSBMLObject();
			mc.setMath((ASTNode) event.getNewValue());
			plugin.notifySBaseChanged(getCorrespondingElementInJSBML(mc));
		} else if (prop.equals(TreeNodeChangeEvent.compartment)) {
			if (eventsource instanceof Species){
				Species spec = (Species) eventsource;
				PluginSpecies plugSpecies = plugModel.getSpecies(spec.getId());
				plugSpecies.setCompartment(spec.getCompartment());
				plugin.notifySBaseChanged(plugSpecies);
			} else if(eventsource instanceof Reaction){
				logger.log(Level.DEBUG, String.format("Changing %s in the Model only supported with SBML version > 3.", eventsource.getClass().getSimpleName()));
			}
		} else if (prop.equals(TreeNodeChangeEvent.compartmentType)) {
			Compartment c = (Compartment) eventsource;
			PluginCompartment pc = plugModel.getCompartment(c.getId());
			pc.setCompartmentType(c.getCompartmentType());
			plugin.notifySBaseChanged(pc);
		} else if (prop.equals(TreeNodeChangeEvent.constant)) {
			if (event.getSource() instanceof SpeciesReference){
				logger.log(Level.DEBUG, String.format("Changing %s in the Model only supported with SBML version > 3.", eventsource.getClass().getSimpleName()));
			} else if (event.getSource() instanceof Symbol){
				if (eventsource instanceof Compartment){
					Compartment c = (Compartment) eventsource;
					PluginCompartment pc = plugModel.getCompartment(c.getId());
					pc.setConstant(c.getConstant());
					plugin.notifySBaseChanged(pc);
				} else if (eventsource instanceof Parameter){
					Parameter p = (Parameter) eventsource;
					PluginParameter pp = plugModel.getParameter(p.getId());
					pp.setConstant(p.getConstant());
					plugin.notifySBaseChanged(pp);
				} else if (eventsource instanceof Species){
					Species sp = (Species) eventsource;
					PluginSpecies pspec = plugModel.getSpecies(sp.getId());
					pspec.setConstant(sp.getConstant());
					plugin.notifySBaseChanged(pspec);
				}
			}
		} else if (prop.equals(TreeNodeChangeEvent.conversionFactor)) {
			logger.log(Level.DEBUG, String.format("Changing %s in the Model only supported with SBML version > 3.", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.created)) {
			logger.log(Level.DEBUG, String.format("Couldn't change %s in the model.", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.creator)) {
			logger.log(Level.DEBUG, String.format("Couldn't change %s in the model.", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.currentList)) {
			logger.log(Level.DEBUG, String.format("Unused propertychange %s", event.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.definitionURL)) {
			ASTNode node = (ASTNode) eventsource;
			MathContainer mc = node.getParentSBMLObject();
			int index = searchMathContainerASTNode(mc, node);
			ASTNode currNode = (ASTNode) mc.getChildAt(index);
			currNode.setDefinitionURL((String) event.getNewValue());
			plugin.notifySBaseChanged(getCorrespondingElementInJSBML(mc));
		} else if (prop.equals(TreeNodeChangeEvent.denominator)) {
			if (eventsource instanceof SpeciesReference){
				//PluginSpeciesReference does not allow the setting of a Denominator
			} else {
				logger.log(Level.DEBUG, String.format("Couldn't change %s in the model.", event.getClass().getSimpleName()));
			}
		} else if (prop.equals(TreeNodeChangeEvent.email)) {
			logger.log(Level.DEBUG, String.format("Couldn't change %s in the model.", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.encoding)) {
			ASTNode n = (ASTNode) eventsource;
			MathContainer mc = n.getParentSBMLObject();
			n.setEncoding((String) event.getNewValue());
			plugin.notifySBaseChanged(getCorrespondingElementInJSBML(mc));
		} else if (prop.equals(TreeNodeChangeEvent.exponent)) {
			if (event.getSource() instanceof ASTNode){
				ASTNode node = (ASTNode) eventsource;
				MathContainer mc = node.getParentSBMLObject();
				mc.setMath((ASTNode) event.getNewValue());
				plugin.notifySBaseChanged(getCorrespondingElementInJSBML(mc));
			} else if (event.getSource() instanceof Unit){
				Unit ut = (Unit) event.getSource();
				UnitDefinition put = (UnitDefinition) ut.getParent().getParentSBMLObject();
				PluginUnitDefinition plugUt = plugModel.getUnitDefinition(put.getId());
				int index = getUnitIndex(put, ut);
				PluginUnit pluginUnit = plugUt.getUnit(index);
				pluginUnit.setExponent((Integer) event.getNewValue());
				plugin.notifySBaseChanged(pluginUnit);
			}
		} else if (prop.equals(TreeNodeChangeEvent.extentUnits)) {
			logger.log(Level.DEBUG, String.format("Changing %s in the Model only supported with SBML version > 3.", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.familyName)) {
			logger.log(Level.DEBUG, String.format("Couldn't change %s in the model.", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.fast)) {
			Reaction r = (Reaction) eventsource;
			PluginReaction plugR = plugModel.getReaction(r.getId());
			plugR.setFast(r.getFast());
			plugin.notifySBaseChanged(plugR);
		} else if (prop.equals(TreeNodeChangeEvent.formula)) {
			logger.log(Level.DEBUG, String.format("Cannot fire propertychange %s", event.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.givenName)) {
			logger.log(Level.DEBUG, String.format("Couldn't change %s in the model.", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.hasOnlySubstanceUnits)) {
			Species spec = (Species) eventsource;
			PluginSpecies plugSpec = plugModel.getSpecies(spec.getId());
			plugSpec.setHasOnlySubstanceUnits(spec.getHasOnlySubstanceUnits());
			plugin.notifySBaseChanged(plugSpec);
		} else if (prop.equals(TreeNodeChangeEvent.history)) {
			logger.log(Level.DEBUG, String.format("Couldn't change %s in the model.", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.id)) {
			//This is unused in  this form
		} else if (prop.equals(TreeNodeChangeEvent.initialAmount)) {
			Species spec = (Species) eventsource;
			PluginSpecies plugSpec = plugModel.getSpecies(spec.getId());
			plugSpec.setInitialAmount(spec.getInitialAmount());
			plugSpec.setInitialConcentration(spec.getInitialConcentration());
		} else if (prop.equals(TreeNodeChangeEvent.initialValue)) {
			if (eventsource instanceof Trigger){
				logger.log(Level.DEBUG, String.format("Changing %s in the Model only supported with SBML version > 3.", eventsource.getClass().getSimpleName()));
			} else if (eventsource instanceof ASTNode){
				logger.log(Level.DEBUG, String.format("Changing %s in the Model only supported with SBML version > 3.", eventsource.getClass().getSimpleName()));
			}
		} else if (prop.equals(TreeNodeChangeEvent.isEOF)) {
			logger.log(Level.DEBUG, String.format("Cannot fire propertychange %s", event.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.isExplicitlySetConstant)) {
			LocalParameter lpam = (LocalParameter) eventsource;
			KineticLaw klaw = (KineticLaw) lpam.getParentSBMLObject().getParentSBMLObject();
			PluginKineticLaw pklaw = plugModel.getReaction(klaw.getParent().getId()).getKineticLaw();
			PluginParameter ppam = (PluginParameter) getCorrespondingElementInJSBML(lpam);
			plugin.notifySBaseChanged(ppam);
		} else if (prop.equals(TreeNodeChangeEvent.isSetNumberType)) {
			ASTNode n = (ASTNode) eventsource;
			MathContainer mc = n.getParentSBMLObject();
			n.setIsSetNumberType((Boolean) event.getNewValue());
			plugin.notifySBaseChanged((PluginSBase) mc.getParentSBMLObject());
		} else if (prop.equals(TreeNodeChangeEvent.kind)) {
			Unit ut = (Unit) eventsource;
			UnitDefinition unitDef = (UnitDefinition) ut.getParentSBMLObject().getParentSBMLObject();
			int index = getUnitIndex(unitDef, ut);
			PluginUnitDefinition plugUnDef = plugModel.getUnitDefinition(unitDef.getId());
			PluginUnit plugUnit = plugUnDef.getUnit(index);
			//the new value can be either a string or an integer ==> therefore this separation is necessary
			if(event.getNewValue() instanceof String){
				plugUnit.setKind((String) event.getNewValue());
			} else {
				plugUnit.setKind((Integer) event.getNewValue());
			}
		} else if (prop.equals(TreeNodeChangeEvent.kineticLaw)) {
			logger.log(Level.DEBUG, String.format("Cannot fire propertychange %s", event.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.lengthUnits)) {
			logger.log(Level.DEBUG, String.format("Changing %s in the Model only supported with SBML version > 3.", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.level)) {
			AbstractSBase base = (AbstractSBase) eventsource;
			base.setLevel((Integer) event.getNewValue());
			PluginSBase pbase = getCorrespondingElementInJSBML(base);
			plugin.notifySBaseChanged(pbase);
		} else if (prop.equals(TreeNodeChangeEvent.listOfUnits)) {
			logger.log(Level.DEBUG, String.format("Unused function %s in the Model.", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.mantissa)) {
			ASTNode n = (ASTNode) eventsource;
			n.setValue((Double) event.getNewValue());
			MathContainer mc = n.getParentSBMLObject();
			plugin.notifySBaseChanged(getCorrespondingElementInJSBML(mc));
		} else if (prop.equals(TreeNodeChangeEvent.math)) {
			MathContainer mathContainer = (MathContainer) event.getSource();
			saveMathContainerProperties(mathContainer);
		} else if (prop.equals(TreeNodeChangeEvent.message)) {
			logger.log(Level.DEBUG, String.format("Cannot fire propertychange %s", event.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.messageBuffer)) {
			logger.log(Level.DEBUG, String.format("Cannot fire propertychange %s", event.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.metaId)) {
			logger.log(Level.DEBUG, String.format("Cannot fire propertychange %s", event.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.model)) {
			logger.log(Level.DEBUG, String.format("Changing %s in the Model not supported.", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.modified)) {
			logger.log(Level.DEBUG, String.format("Cannot fire propertychange in %s.", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.multiplier)) {
			Unit u = (Unit) eventsource;
			UnitDefinition ud = (UnitDefinition) u.getParentSBMLObject().getParentSBMLObject();
			int index = getUnitIndex(ud, u);
			PluginUnitDefinition pud = plugModel.getUnitDefinition(ud.getId());
			u.setMultiplier((Double) event.getNewValue());
			plugin.notifySBaseChanged(pud);
		} else if (prop.equals(TreeNodeChangeEvent.name)) {
			if (event.getSource() instanceof FunctionDefinition){
				FunctionDefinition funcDef = (FunctionDefinition) event.getSource();
				PluginFunctionDefinition plugFuncDef = plugModel.getFunctionDefinition(funcDef.getId());
				plugFuncDef.setName(funcDef.getName());
				plugin.notifySBaseChanged(plugFuncDef);
			}
		} else if (prop.equals(TreeNodeChangeEvent.namespace)) {
			logger.log(Level.DEBUG, String.format("Cannot fire propertychange %s", event.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.nonRDFAnnotation)) {
			logger.log(Level.DEBUG, String.format("Changing %s in the Model not supported", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.notes)) {
			logger.log(Level.DEBUG, String.format("Cannot fire propertychange %s", event.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.notesBuffer)) {
			logger.log(Level.DEBUG, String.format("Cannot fire propertychange %s", event.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.numerator)) {
			ASTNode n = (ASTNode) eventsource;
			n.setValue((Double) event.getNewValue());
			MathContainer mc = n.getParentSBMLObject();
			plugin.notifySBaseChanged(getCorrespondingElementInJSBML(mc));
		} else if (prop.equals(TreeNodeChangeEvent.offset)) {
			Unit u = (Unit) eventsource;
			UnitDefinition ud = (UnitDefinition) u.getParent().getParent();
			PluginUnitDefinition pu = plugModel.getUnitDefinition(ud.getId());
			int index = getUnitIndex(ud, u);
			PluginUnit plugU = pu.getUnit(index);
			plugU.setOffset((Double) event.getNewValue());
			plugin.notifySBaseChanged(plugU);
		} else if (prop.equals(TreeNodeChangeEvent.organisation)) {
			logger.log(Level.DEBUG, String.format("Couldn't change %s in the model.", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.outside)) {
			Compartment c = (Compartment) event.getSource();
			PluginCompartment plugC = plugModel.getCompartment(c.getId());
			plugC.setOutside(c.getOutside());
			plugin.notifySBaseChanged(plugC);
		} else if (prop.equals(TreeNodeChangeEvent.parentSBMLObject)) {
			logger.log(Level.DEBUG, String.format("Couldn't change %s in the model.", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.persistent)) {
			Trigger t = (Trigger) eventsource;
			Event evt = t.getParent();
			PluginEvent pevt = plugModel.getEvent(evt.getId());
			pevt.setTrigger(PluginUtils.convert(evt.getTrigger().getMath()));
			plugin.notifySBaseChanged(pevt);
		} else if (prop.equals(TreeNodeChangeEvent.priority)) {
			logger.log(Level.DEBUG, String.format("Changing %s in the Model not supported.", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.qualifier)) {
			//CVterm - libsbml
		} else if (prop.equals(TreeNodeChangeEvent.rdfAnnotationNamespaces)) {
			logger.log(Level.DEBUG, String.format("Changing %s in the Model not supported.", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.resource)) {
			logger.log(Level.DEBUG, String.format("Unused method %s in the model.", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.reversible)) {
			Reaction r = (Reaction) event.getSource();
			PluginReaction plugR = plugModel.getReaction(r.getId());
			plugR.setReversible(r.getReversible());
		} else if (prop.equals(TreeNodeChangeEvent.sboTerm)) {
			AbstractSBase abs = (AbstractSBase) eventsource;
			abs.setSBOTerm((Integer) event.getNewValue());
			PluginSBase pabs = getCorrespondingElementInJSBML(abs);
			plugin.notifySBaseChanged(pabs);
		} else if (prop.equals(TreeNodeChangeEvent.scale)) {
			Unit u = (Unit) eventsource;
			u.setScale((Integer) event.getNewValue());
			PluginSBase psb = getCorrespondingElementInJSBML(u);
			plugin.notifySBaseChanged(psb);
		} else if (prop.equals(TreeNodeChangeEvent.setAnnotation)) {
			AbstractSBase abs = (AbstractSBase) eventsource;
			abs.setAnnotation((Annotation) event.getNewValue());
			PluginSBase pabs = getCorrespondingElementInJSBML(abs);
			plugin.notifySBaseChanged(pabs);
		} else if (prop.equals(TreeNodeChangeEvent.size)) {
			Compartment c = (Compartment) event.getSource();
			PluginCompartment plugC = plugModel.getCompartment(c.getId());
			plugC.setSize(c.getSize());
			plugin.notifySBaseChanged(plugC);
		} else if (prop.equals(TreeNodeChangeEvent.spatialDimensions)) {
			Compartment c = (Compartment) event.getSource();
			PluginCompartment plugC = plugModel.getCompartment(c.getId());
			plugC.setSpatialDimensions((long) c.getSpatialDimensions());
			plugin.notifySBaseChanged(plugC);
		} else if (prop.equals(TreeNodeChangeEvent.spatialSizeUnits)) {
			Species spec = (Species) event.getSource();
			PluginSpecies plugSpec = plugModel.getSpecies(spec.getId());
			plugSpec.setSpatialSizeUnits(spec.getSpatialSizeUnits());
			plugin.notifySBaseChanged(plugSpec);
		} else if (prop.equals(TreeNodeChangeEvent.species)) {
			if (event.getSource() instanceof SpeciesReference){
				SpeciesReference specRef = (SpeciesReference) event.getSource();
				specRef.setSpecies((Species) event.getNewValue());
				PluginSpeciesReference pSpecRef = (PluginSpeciesReference) getCorrespondingElementInJSBML(specRef);
				plugin.notifySBaseChanged(pSpecRef);
			} else if (event.getSource() instanceof ModifierSpeciesReference){
				ModifierSpeciesReference modspecRef = (ModifierSpeciesReference) event.getSource();
				modspecRef.setSpecies((Species) event.getNewValue());
				PluginModifierSpeciesReference pmodspecRef = (PluginModifierSpeciesReference) getCorrespondingElementInJSBML(modspecRef);
				plugin.notifySBaseChanged(pmodspecRef);
			}
		} else if (prop.equals(TreeNodeChangeEvent.speciesType)) {
			Species spec = (Species) event.getSource();
			PluginSpecies plugSpec = plugModel.getSpecies(spec.getId());
			plugSpec.setSpeciesType(spec.getSpeciesType());
			plugin.notifySBaseChanged(plugSpec);
		} else if (prop.equals(TreeNodeChangeEvent.stoichiometry)) {
			logger.log(Level.DEBUG, String.format("Unused method %s", event.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.style)) {
			ASTNode n = (ASTNode) eventsource;
			n.setStyle((String) event.getNewValue());
			MathContainer mc = n.getParentSBMLObject();
			plugin.notifySBaseChanged(getCorrespondingElementInJSBML(mc));
		} else if (prop.equals(TreeNodeChangeEvent.substanceUnits)) {
			KineticLaw klaw = (KineticLaw) event.getSource();
			PluginReaction plugReac = plugModel.getReaction(klaw.getParent().getId());
			plugReac.getKineticLaw().setSubstanceUnits(klaw.getSubstanceUnits());
			plugin.notifySBaseChanged(plugReac);
		} else if (prop.equals(TreeNodeChangeEvent.symbol)) {
			logger.log(Level.DEBUG, String.format("Cannot fire propertychange %s", event.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.SBMLDocumentAttributes)) {
			logger.log(Level.DEBUG, String.format("Cannot fire propertychange %s", event.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.text)) {
			logger.log(Level.DEBUG, String.format("Cannot fire propertychange %s", event.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.timeUnits)) {
			if (eventsource instanceof Event){
				Event evt = (Event) eventsource;
				PluginEvent pEvt = plugModel.getEvent(evt.getId());
				pEvt.setTimeUnits(evt.getTimeUnits());
				plugin.notifySBaseChanged(pEvt);
			} else if (eventsource instanceof KineticLaw){
				KineticLaw klaw = (KineticLaw) eventsource;
				PluginReaction pReac = plugModel.getReaction(klaw.getParent().getId());
				PluginKineticLaw pKlaw = pReac.getKineticLaw();
				pKlaw.setTimeUnits(klaw.getTimeUnits());
				plugin.notifySBaseChanged(pKlaw);
			} else if (eventsource instanceof Model){
				logger.log(Level.DEBUG, String.format("Changing %s in the Model only supported with SBML version > 3.", eventsource.getClass().getSimpleName()));
			}
		} else if (prop.equals(TreeNodeChangeEvent.type)) {
			ASTNode n = (ASTNode) eventsource;
			n.setType((String) event.getNewValue());
			MathContainer mc = n.getParentSBMLObject();
			plugin.notifySBaseChanged(getCorrespondingElementInJSBML(mc));
		} else if (prop.equals(TreeNodeChangeEvent.units)) {
			if (eventsource instanceof KineticLaw){
				KineticLaw klaw = (KineticLaw) event.getSource();
				klaw.setUnits((Unit) event.getNewValue());
				PluginKineticLaw pklaw = (PluginKineticLaw) getCorrespondingElementInJSBML(klaw);
				plugin.notifySBaseChanged(pklaw);
			} else if (eventsource instanceof ExplicitRule){
				ExplicitRule er = (ExplicitRule) eventsource;
				er.setUnits((Unit) event.getNewValue());
				PluginRule pr = (PluginRule) getCorrespondingElementInJSBML(er);
				plugin.notifySBaseChanged(pr);
			} else if (eventsource instanceof ASTNode){
				ASTNode n = (ASTNode) eventsource;
				n.setUnits((String) event.getNewValue());
				MathContainer mc = n.getParentSBMLObject();
				plugin.notifySBaseChanged(getCorrespondingElementInJSBML(mc));
			} else if (eventsource instanceof Model){
				//can be ignored, since we can't change the model in Celldesigner?
			}
		} else if (prop.equals(TreeNodeChangeEvent.unsetCVTerms)) {
			if (eventsource instanceof AbstractSBase){
				AbstractSBase abs = (AbstractSBase) eventsource;
				abs.unsetCVTerms();
				PluginSBase psb = getCorrespondingElementInJSBML(abs);
				plugin.notifySBaseChanged(psb);
			}
		} else if (prop.equals(TreeNodeChangeEvent.userObject)) {
			ASTNode n = (ASTNode) eventsource;
			n.setUserObject(event.getNewValue());
			MathContainer mc = n.getParentSBMLObject();
			plugin.notifySBaseChanged(getCorrespondingElementInJSBML(mc));
		} else if (prop.equals(TreeNodeChangeEvent.useValuesFromTriggerTime)) {
			Event evt = (Event) event.getSource();
			PluginEvent plugEvt = plugModel.getEvent(evt.getId());
			plugEvt.setUseValuesFromTriggerTime(evt.getUseValuesFromTriggerTime());
			plugin.notifySBaseChanged(plugEvt);
		} else if (prop.equals(TreeNodeChangeEvent.value)) {
			ASTNode n = (ASTNode) eventsource;
			n.setValue((Double) event.getNewValue());
			MathContainer mc = n.getParentSBMLObject();
			plugin.notifySBaseChanged(getCorrespondingElementInJSBML(mc));
		} else if (prop.equals(TreeNodeChangeEvent.variable)) {
			Object evtSrc = event.getSource();
			if (evtSrc instanceof EventAssignment){
				//TODO
			} else if (evtSrc instanceof ExplicitRule){
				//TODO
			} else if (evtSrc instanceof InitialAssignment){
				InitialAssignment ia = (InitialAssignment) evtSrc;
				PluginInitialAssignment plI = plugModel.getInitialAssignment(ia.getSymbol());
				//TODO Can't call setVariable on PluginInitialAssignment object
			}
		} else if (prop.equals(TreeNodeChangeEvent.version)) {
			//TODO Search method required
		} else if (prop.equals(TreeNodeChangeEvent.volume)) {
			Compartment c = (Compartment) event.getSource();
			PluginCompartment plugC = plugModel.getCompartment(c.getId());
			plugC.setVolume(c.getVolume());
			plugin.notifySBaseChanged(plugC);
		} else if (prop.equals(TreeNodeChangeEvent.volumeUnits)) {
			logger.log(Level.DEBUG, String.format("Changing %s in the Model only supported with SBML version > 3.", eventsource.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.xmlTriple)) {
			logger.log(Level.DEBUG, String.format("Cannot fire propertychange %s", event.getClass().getSimpleName()));
		}  
	}

	/**
	 * This method saves the properties of a MathContainer input Object.
	 * 
	 * @param mathcontainer
	 */

	private void saveMathContainerProperties(MathContainer mathcontainer){
		if (mathcontainer instanceof FunctionDefinition){
			FunctionDefinition funcDef = (FunctionDefinition) mathcontainer;
			PluginFunctionDefinition plugFuncDef = plugModel.getFunctionDefinition(funcDef.getId());
			boolean equals = (plugFuncDef.getMath() != null) && funcDef.isSetMath() && PluginUtils.equal(funcDef.getMath(), plugFuncDef.getMath());
			if (funcDef.isSetMath() && !equals) {
				plugFuncDef.setMath(PluginUtils.convert(funcDef.getMath()));
			} else {
				logger.log(Level.DEBUG, String.format("Couldn't save math properties of %s", funcDef.getClass().getSimpleName()));
			}
		} else if (mathcontainer instanceof KineticLaw) {
			Reaction r = ((KineticLaw) mathcontainer).getParent();
			PluginReaction plugReac = plugModel.getReaction(r.getId());
			if (plugReac != null) {
				PluginKineticLaw plugKl = plugReac.getKineticLaw();
				plugKl.setMath(PluginUtils.convert(r.getKineticLaw().getMath()));
				plugin.notifySBaseChanged(plugKl);
			} else {
				logger.log(Level.DEBUG, String.format("Couldn't save math properties of %s", r.getClass().getSimpleName()));
			}
		} else if (mathcontainer instanceof InitialAssignment){
			InitialAssignment initAss = (InitialAssignment) mathcontainer;
			PluginInitialAssignment pluginit = plugModel.getInitialAssignment(initAss.getSymbol());
			boolean equals = (initAss.getMath() != null) && initAss.isSetMath() && PluginUtils.equal(initAss.getMath(), libsbml.parseFormula(pluginit.getMath()));
			if (initAss.isSetMath() && !equals){
				pluginit.setMath(libsbml.formulaToString(PluginUtils.convert(initAss.getMath())));
				plugin.notifySBaseChanged(pluginit);
			} else {
				logger.log(Level.DEBUG, String.format("Couldn't save math properties of %s", initAss.getClass().getSimpleName()));
			}
		} else if (mathcontainer instanceof EventAssignment){
			EventAssignment eventAss = (EventAssignment) mathcontainer;
			//TODO Unclear how to get the EventAssignment
		} else if (mathcontainer instanceof StoichiometryMath){
			//TODO Does not exist in Celldesigner or ?
		} else if (mathcontainer instanceof Trigger){
			Trigger trig = (Trigger) mathcontainer;
			PluginEvent plugEvent = plugModel.getEvent(trig.getParent().getId());
			boolean equals = plugEvent.getTrigger().isSetMath() && trig.isSetMath() && PluginUtils.equal(trig.getMath(), plugEvent.getTrigger().getMath());
			if (trig.isSetMath() && !equals){
				plugEvent.getTrigger().setMath(PluginUtils.convert(trig.getMath()));
				plugin.notifySBaseChanged(plugEvent);
			} else {
				logger.log(Level.DEBUG, String.format("Couldn't save math properties of %s", trig.getClass().getSimpleName()));
			}
		} else if (mathcontainer instanceof Rule){
			Rule r = (Rule) mathcontainer;
			if (mathcontainer instanceof AlgebraicRule){
				//TODO how to get the proper Algebraic Rule ?
				
			} else if (mathcontainer instanceof ExplicitRule){
				if (mathcontainer instanceof RateRule){
					//TODO how to get the Right Rate Rule
				}else if (mathcontainer instanceof AssignmentRule){
					//TODO
				}
			}
		} else if (mathcontainer instanceof Constraint) {
			Constraint c = (Constraint) mathcontainer;
			PluginConstraint plugC  = plugModel.getConstraint(c.getMathMLString());
			boolean equals = (plugC.getMath() != null) && c.isSetMath() && PluginUtils.equal(c.getMath(), libsbml.parseFormula(plugC.getMath()));
			if (c.isSetMath() && !equals){
				logger.log(Level.DEBUG, String.format("Couldn't save math properties of %s", c.getClass().getSimpleName()));
			}
		} else if (mathcontainer instanceof Delay){
			Delay d = (Delay) mathcontainer;
			PluginEvent plugEvent = plugModel.getEvent(d.getParent().getId());
			org.sbml.libsbml.Delay plugDelay = plugEvent.getDelay();
			boolean equals = (plugDelay.getMath() != null && d.isSetMath() && PluginUtils.equal(d.getMath(), plugDelay.getMath()));
			if (d.isSetMath() && !equals){
				plugDelay.setMath(PluginUtils.convert(d.getMath()));
				plugin.notifySBaseChanged(plugEvent);
			} else {
				logger.log(Level.DEBUG, String.format("Couldn't save math properties of %s", d.getClass().getSimpleName()));
			}
		} else if (mathcontainer instanceof Priority){
			Priority p = (Priority) mathcontainer;
			PluginEvent plugEvent = plugModel.getEvent(p.getParent().getId());
			org.sbml.libsbml.Delay plugPriority = plugEvent.getDelay();
			boolean equals = (plugPriority.getMath() != null && p.isSetMath() && PluginUtils.equal(p.getMath(), plugPriority.getMath()));
			if (p.isSetMath() && !equals){
				plugPriority.setMath(PluginUtils.convert(p.getMath()));
				plugin.notifySBaseChanged(plugEvent);
			} else {
				logger.log(Level.DEBUG, String.format("Couldn't save math properties of %s", p.getClass().getSimpleName()));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.util.TreeNodeChangeListener#nodeAdded(javax.swing.tree
	 * .TreeNode)
	 */
	public void nodeAdded(TreeNode node) {
		if (node instanceof AbstractSBase) {
			if (node instanceof AbstractNamedSBase) {
				if (node instanceof CompartmentType) {
					CompartmentType ct = (CompartmentType) node;
					PluginCompartmentType pt = new PluginCompartmentType(
							ct.getId());
					if (ct.isSetName() && !pt.getName().equals(ct.getName())) {
						pt.setName(ct.getName());
						plugin.notifySBaseAdded(pt);
					} else {
						logger.log(Level.DEBUG, "Cannot add node"
								+ node.getClass().getSimpleName());
					}
				} else if (node instanceof UnitDefinition) {
					UnitDefinition undef = (UnitDefinition) node;
					PluginUnitDefinition plugundef = new PluginUnitDefinition(
							undef.getId());
					if (undef.isSetName()
							&& !plugundef.getName().equals(undef.getName())) {
						plugundef.setName(undef.getName());
						plugin.notifySBaseAdded(plugundef);
					} else {
						logger.log(Level.DEBUG, "Cannot add node"
								+ node.getClass().getSimpleName());
					}
				} else if (node instanceof Reaction) {
					Reaction react = (Reaction) node;
					PluginReaction plugreac = new PluginReaction();
					if (react.isSetName()
							&& !react.getName().equals(plugreac.getName())) {
						plugreac.setName(react.getName());
						plugin.notifySBaseAdded(plugreac);
					} else {
						logger.log(Level.DEBUG, "Cannot add node"
								+ node.getClass().getSimpleName());
					}
				} else if (node instanceof SpeciesType) {
					SpeciesType speciestype = (SpeciesType) node;
					PluginSpeciesType plugspectype = new PluginSpeciesType(
							speciestype.getId());
					if (speciestype.isSetName()
							&& !speciestype.getName().equals(
									plugspectype.getName())) {
						plugspectype.setName(speciestype.getName());
						plugin.notifySBaseAdded(plugspectype);
					} else {
						logger.log(Level.DEBUG, "Cannot add node"
								+ node.getClass().getSimpleName());
					}
				} else if (node instanceof SimpleSpeciesReference) {
					SimpleSpeciesReference simspec = (SimpleSpeciesReference) node;
					String type = SBO.convertSBO2Alias(simspec.getSBOTerm());
					if (node instanceof ModifierSpeciesReference) {
						if (type.length() == 0) {
							// use "unknown"
							int sbo = 285;
							type = SBO.convertSBO2Alias(sbo);
							logger.log(Level.DEBUG, String.format(
									"No SBO term defined for %s, using %d",
									simspec.getElementName(), sbo));
						}
						if (simspec.isSetSpecies()) {
							PluginSpeciesAlias alias = new PluginSpeciesAlias(
									plugModel.getSpecies(simspec.getSpecies()),
									type);
							PluginModifierSpeciesReference plugModRef = new PluginModifierSpeciesReference(
									(PluginReaction) simspec.getParent(), alias);
							plugin.notifySBaseAdded(plugModRef);
						} else {
							logger.log(Level.DEBUG,
									"Cannot create PluginSpeciesReference due to missing species annotation.");
						}

					} else if (node instanceof SpeciesReference) {
						if (type.length() == 0) {
							// use "unknown"
							int sbo = 285;
							type = SBO.convertSBO2Alias(sbo);
							logger.log(Level.DEBUG, String.format(
									"No SBO term defined for %s, using %d",
									simspec.getElementName(), sbo));
						}
						// TODO: use SBML layout extension (later than JSBML
						// 0.8)
						if (simspec.isSetSpecies()) {
							PluginSpeciesAlias alias = new PluginSpeciesAlias(
									plugModel.getSpecies(simspec.getSpecies()),
									type);
							PluginSpeciesReference plugspecRef = new PluginSpeciesReference(
									(PluginReaction) simspec.getParent(), alias);
							plugin.notifySBaseAdded(plugspecRef);
						} else {
							logger.log(Level.DEBUG,
									"Cannot create PluginSpeciesReference due to missing species annotation.");
						}
					}
				} else if (node instanceof AbstractNamedSBaseWithUnit) {
					if (node instanceof Event) {
						Event event = (Event) node;
						PluginEvent plugevent = new PluginEvent(event.getId());
						if (event.isSetName()
								&& !event.getName().equals(plugevent.getName())) {
							plugevent.setName(event.getName());
							plugin.notifySBaseAdded(plugevent);
						} else {
							logger.log(Level.DEBUG, "Cannot add node"
									+ node.getClass().getSimpleName());
						}
					} else if (node instanceof QuantityWithUnit) {
						if (node instanceof LocalParameter) {
							LocalParameter locparam = (LocalParameter) node;
							ListOf<LocalParameter> lop = locparam
									.getParentSBMLObject();
							KineticLaw kl = (KineticLaw) lop
									.getParentSBMLObject();
							/*
							 * TODO Crosscheck if this is okay.
							 */
							for (LocalParameter p : kl
									.getListOfLocalParameters()) {
								if (p.isSetUnits()
										&& !Unit.isUnitKind(p.getUnits(),
												p.getLevel(), p.getVersion())
										&& plugModel.getUnitDefinition(p
												.getUnits()) == null) {
									PluginUnitDefinition plugUnitDefinition = new PluginUnitDefinition(
											p.getUnitsInstance().getId());
									plugModel
											.addUnitDefinition(plugUnitDefinition);
									plugin.notifySBaseAdded(plugUnitDefinition);

								}
							}

						} else if (node instanceof Symbol) {
							if (node instanceof Compartment) {
								Compartment comp = (Compartment) node;
								PluginCompartment plugcomp = new PluginCompartment(
										comp.getCompartmentType());
								if (comp.isSetName()
										&& !plugcomp.getName().equals(
												comp.getName())) {
									plugcomp.setName(comp.getName());
									plugin.notifySBaseAdded(plugcomp);
								} else {
									logger.log(Level.DEBUG, "Cannot add node"
											+ node.getClass().getSimpleName());
								}
							} else if (node instanceof Species) {
								Species sp = (Species) node;
								PluginSpecies plugsp = new PluginSpecies(
										sp.getSpeciesType(), sp.getName());
								if (sp.isSetName()
										&& !sp.getName().equals(
												plugsp.getName())) {
									plugin.notifySBaseAdded(plugsp);
								} else {
									logger.log(Level.DEBUG, "Cannot add node"
											+ node.getClass().getSimpleName());
								}
							} else if (node instanceof org.sbml.jsbml.Parameter) {
								org.sbml.jsbml.Parameter param = (org.sbml.jsbml.Parameter) node;
								if (param.getParent() instanceof KineticLaw) {
									PluginParameter plugparam = new PluginParameter(
											(PluginKineticLaw) param
													.getParent());
									if (param.isSetName()
											&& !param.getName().equals(
													plugparam.getName())) {
										plugparam.setName(param.getName());
										plugin.notifySBaseAdded(plugparam);
									} else {
										logger.log(
												Level.DEBUG,
												"Cannot add node"
														+ node.getClass()
																.getSimpleName());
									}
								} else if (param.getParent() instanceof Model) {
									PluginParameter plugparam = new PluginParameter(
											(PluginModel) param.getParent());
									if (param.isSetName()
											&& !param.getName().equals(
													plugparam.getName())) {
										plugparam.setName(param.getName());
										plugin.notifySBaseAdded(plugparam);
									} else {
										logger.log(
												Level.DEBUG,
												"Cannot add node"
														+ node.getClass()
																.getSimpleName());
									}
								}
							}
						}
					}
				}
			}
			if (node instanceof Unit) {
				/*
				 * TODO This needs to be crosschecked if thats the way it should
				 * work.
				 */
				Unit ut = (Unit) node;
				PluginUnitDefinition plugUnitDef = new PluginUnitDefinition(
						((UnitDefinition) ut.getParentSBMLObject()).getId());
				PluginUnit plugut = new PluginUnit(plugUnitDef);
				switch (ut.getKind()) {
				case AMPERE:
					plugut.setKind(libsbmlConstants.UNIT_KIND_AMPERE);
					break;
				case BECQUEREL:
					plugut.setKind(libsbmlConstants.UNIT_KIND_BECQUEREL);
					break;
				case CANDELA:
					plugut.setKind(libsbmlConstants.UNIT_KIND_CANDELA);
					break;
				case CELSIUS:
					plugut.setKind(libsbmlConstants.UNIT_KIND_CELSIUS);
					break;
				case COULOMB:
					plugut.setKind(libsbmlConstants.UNIT_KIND_COULOMB);
					break;
				case DIMENSIONLESS:
					plugut.setKind(libsbmlConstants.UNIT_KIND_DIMENSIONLESS);
					break;
				case FARAD:
					plugut.setKind(libsbmlConstants.UNIT_KIND_FARAD);
					break;
				case GRAM:
					plugut.setKind(libsbmlConstants.UNIT_KIND_GRAM);
					break;
				case GRAY:
					plugut.setKind(libsbmlConstants.UNIT_KIND_GRAY);
					break;
				case HENRY:
					plugut.setKind(libsbmlConstants.UNIT_KIND_HENRY);
					break;
				case HERTZ:
					plugut.setKind(libsbmlConstants.UNIT_KIND_HERTZ);
					break;
				case INVALID:
					plugut.setKind(libsbmlConstants.UNIT_KIND_INVALID);
					break;
				case ITEM:
					plugut.setKind(libsbmlConstants.UNIT_KIND_ITEM);
					break;
				case JOULE:
					plugut.setKind(libsbmlConstants.UNIT_KIND_JOULE);
					break;
				case KATAL:
					plugut.setKind(libsbmlConstants.UNIT_KIND_KATAL);
					break;
				case KELVIN:
					plugut.setKind(libsbmlConstants.UNIT_KIND_KELVIN);
					break;
				case KILOGRAM:
					plugut.setKind(libsbmlConstants.UNIT_KIND_KILOGRAM);
					break;
				case LITER:
					plugut.setKind(libsbmlConstants.UNIT_KIND_LITER);
					break;
				case LITRE:
					plugut.setKind(libsbmlConstants.UNIT_KIND_LITRE);
					break;
				case LUMEN:
					plugut.setKind(libsbmlConstants.UNIT_KIND_LUMEN);
					break;
				case LUX:
					plugut.setKind(libsbmlConstants.UNIT_KIND_LUX);
					break;
				case METER:
					plugut.setKind(libsbmlConstants.UNIT_KIND_METER);
					break;
				case METRE:
					plugut.setKind(libsbmlConstants.UNIT_KIND_METRE);
					break;
				case MOLE:
					plugut.setKind(libsbmlConstants.UNIT_KIND_MOLE);
					break;
				case NEWTON:
					plugut.setKind(libsbmlConstants.UNIT_KIND_NEWTON);
					break;
				case OHM:
					plugut.setKind(libsbmlConstants.UNIT_KIND_OHM);
					break;
				case PASCAL:
					plugut.setKind(libsbmlConstants.UNIT_KIND_PASCAL);
					break;
				case RADIAN:
					plugut.setKind(libsbmlConstants.UNIT_KIND_RADIAN);
					break;
				case SECOND:
					plugut.setKind(libsbmlConstants.UNIT_KIND_SECOND);
					break;
				case SIEMENS:
					plugut.setKind(libsbmlConstants.UNIT_KIND_SIEMENS);
					break;
				case SIEVERT:
					plugut.setKind(libsbmlConstants.UNIT_KIND_SIEVERT);
					break;
				case STERADIAN:
					plugut.setKind(libsbmlConstants.UNIT_KIND_STERADIAN);
					break;
				case TESLA:
					plugut.setKind(libsbmlConstants.UNIT_KIND_TESLA);
					break;
				case VOLT:
					plugut.setKind(libsbmlConstants.UNIT_KIND_VOLT);
					break;
				case WATT:
					plugut.setKind(libsbmlConstants.UNIT_KIND_WATT);
					break;
				case WEBER:
					plugut.setKind(libsbmlConstants.UNIT_KIND_WEBER);
					break;
				}
				plugut.setExponent((int) Math.round(ut.getExponent()));
				plugut.setMultiplier(ut.getMultiplier());
				plugut.setOffset(ut.getOffset());
				plugut.setScale(ut.getScale());
				plugin.notifySBaseAdded(plugut);

			} else if (node instanceof SBMLDocument) {
				SBMLDocument doc = (SBMLDocument) node;
				logger.log(Level.DEBUG, "No counter class in CellDesigner"
						+ node.getClass().getSimpleName());
				// TODO
			} else if (node instanceof ListOf<?>) {
				ListOf<?> listOf = (ListOf<?>) node;
				PluginListOf pluli = new PluginListOf();
				PluginReaction ro = (PluginReaction) listOf
						.getParentSBMLObject();

				switch (listOf.getSBaseListType()) {
				case listOfCompartments:
					// FIXME
					//ListOfCompartments ll = new ListOfCompartments();
					
					break;
				case listOfCompartmentTypes:
					break;
				case listOfConstraints:
					break;
				case listOfEventAssignments:
					break;
				case listOfEvents:
					break;
				case listOfFunctionDefinitions:
					break;
				case listOfInitialAssignments:
					break;
				case listOfLocalParameters:
					break;
				case listOfModifiers:
					break;
				case listOfParameters:
					break;
				case listOfProducts:
					break;
				case listOfReactants:
					break;
				case listOfReactions:
					break;
				case listOfRules:
					break;
				case listOfSpecies:
					break;
				case listOfSpeciesTypes:
					break;
				case listOfUnitDefinitions:
					break;
				case listOfUnits:
					break;
				case other:
					// TODO for JSBML packages (later than 0.8).
				default:
					// unknown
					break;
				}

			} else if (node instanceof AbstractMathContainer) {
				if (node instanceof FunctionDefinition) {
					FunctionDefinition funcdef = (FunctionDefinition) node;
					PluginFunctionDefinition plugfuncdef = new PluginFunctionDefinition(
							funcdef.getId());
					if (funcdef.isSetName()
							&& !plugfuncdef.getName().equals(funcdef.getName())) {
						plugfuncdef.setName(funcdef.getName());
						plugin.notifySBaseAdded(plugfuncdef);
					} else {
						logger.log(Level.DEBUG, "Cannot add node "
								+ node.getClass().getSimpleName());
					}
				} else if (node instanceof KineticLaw) {
					KineticLaw klaw = (KineticLaw) node;
					Reaction parentreaction = klaw.getParentSBMLObject();
					PluginKineticLaw plugklaw = plugModel.getReaction(
							parentreaction.getId()).getKineticLaw();
					PluginReaction plugreac = plugModel
							.getReaction(parentreaction.getId());
					plugreac.setKineticLaw(plugklaw);
					plugin.notifySBaseAdded(plugreac);
				} else if (node instanceof InitialAssignment) {
					InitialAssignment iAssign = (InitialAssignment) node;
					PluginInitialAssignment plugiassign = new PluginInitialAssignment(
							iAssign.getSymbol());
					plugiassign.setMath(iAssign.getMathMLString());
					plugiassign.setNotes(iAssign.getNotesString());
					plugin.notifySBaseAdded(plugiassign);
				} else if (node instanceof EventAssignment) {
					EventAssignment eassign = (EventAssignment) node;
					// TODO PluginEventAssignemnt requires a new PluginEvent -
					// we do not know this event. What shall we do here ?
				} else if (node instanceof StoichiometryMath) {
					logger.log(Level.DEBUG, String.format(
							"No counter class for %s in CellDesigner.", node
									.getClass().getSimpleName()));
				} else if (node instanceof Trigger) {
					Trigger trig = (Trigger) node;
					PluginEvent plugEvent = new PluginEvent(trig.getParent()
							.getId());
					plugEvent.setTrigger(PluginUtils.convert(trig.getMath()));
					plugin.notifySBaseAdded(plugEvent);
				} else if (node instanceof Constraint) {
					Constraint ct = (Constraint) node;
					PluginConstraint plugct = new PluginConstraint(
							ct.getMathMLString());
					plugct.setMessage(ct.getMessageString());
					plugct.setNotes(ct.getNotesString());
					plugin.notifySBaseAdded(plugct);
				} else if (node instanceof Delay) {
					Delay dl = (Delay) node;
					PluginEvent plugEvent = new PluginEvent(dl.getParent()
							.getId());
					plugEvent.setDelay(PluginUtils.convert(dl.getMath()));
					plugin.notifySBaseAdded(plugEvent);
				} else if (node instanceof Priority) {
					logger.log(Level.DEBUG, String.format(
							"No counter class for %s in CellDesigner.", node
									.getClass().getSimpleName()));
				} else if (node instanceof Rule) {
					if (node instanceof AlgebraicRule) {
						AlgebraicRule alrule = (AlgebraicRule) node;
						PluginAlgebraicRule plugalrule = new PluginAlgebraicRule(
								plugModel);
						plugalrule
								.setMath(PluginUtils.convert(alrule.getMath()));
						plugin.notifySBaseAdded(plugalrule);
					} else if (node instanceof ExplicitRule) {
						if (node instanceof RateRule) {
							RateRule rule = (RateRule) node;
							PluginRateRule plugraterule = new PluginRateRule(
									plugModel);

							plugraterule.setMath(PluginUtils.convert(rule
									.getMath()));
							plugraterule.setVariable(rule.getVariable());
							plugraterule.setNotes(rule.getNotes().getName());
							plugin.notifySBaseAdded(plugraterule);

						} else if (node instanceof AssignmentRule) {
							AssignmentRule assignRule = (AssignmentRule) node;
							PluginAssignmentRule plugassignRule = new PluginAssignmentRule(
									plugModel);

							plugassignRule.setL1TypeCode(assignRule.getLevel());
							plugassignRule.setMath(PluginUtils
									.convert(assignRule.getMath()));
							plugassignRule
									.setVariable(assignRule.getVariable());
							plugassignRule
									.setNotes(assignRule.getNotesString());
							plugin.notifySBaseAdded(plugassignRule);
						}
					} else {
						Rule rule = (Rule) node;
						PluginRule plugrule = new PluginRule();
						plugrule.setMath(PluginUtils.convert(rule.getMath()));
						plugrule.setNotes(rule.getNotesString());
						plugin.notifySBaseAdded(plugrule);
					}
				}
			}
		} else if (node instanceof AbstractTreeNode) {
			if (node instanceof XMLToken) {
				if (node instanceof XMLNode) {
					logger.log(Level.DEBUG, String.format(
							"Parsing of node %s not successful.", node
									.getClass().getSimpleName()));
				}
			} else if (node instanceof ASTNode) {
				logger.log(Level.DEBUG, String.format(
						"Parsing of node %s not successful.", node.getClass()
								.getSimpleName()));
			} else if (node instanceof AnnotationElement) {
				if (node instanceof CVTerm) {

					// TODO This has to be done with the libsbml.CVTerm Class,
					// fix this.
				} else if (node instanceof History) {
					logger.log(Level.DEBUG, "No counter class in CellDesigner"
							+ node.getClass().getSimpleName());
				} else if (node instanceof Creator) {
					logger.log(Level.DEBUG, "No counter class in CellDesigner"
							+ node.getClass().getSimpleName());
				} else {
					logger.warn(String.format("Could not process %s.",
							node.toString()));
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.util.TreeNodeChangeListener#nodeRemoved(javax.swing.tree
	 * .TreeNode)
	 */

	public void nodeRemoved(TreeNode node) {
		if (node instanceof AbstractSBase) {
			if (node instanceof AbstractNamedSBase) {
				if (node instanceof CompartmentType) {
					CompartmentType ct = (CompartmentType) node;
					PluginCompartmentType pt = plugModel.getCompartmentType(ct
							.getId());
					plugModel.removeCompartmentType(ct.getId());
					plugin.notifySBaseDeleted(pt);
				} else if (node instanceof UnitDefinition) {
					UnitDefinition undef = (UnitDefinition) node;
					PluginUnitDefinition plugUndef = plugModel
							.getUnitDefinition(undef.getId());
					plugModel.removeUnitDefinition(undef.getId());
					plugin.notifySBaseDeleted(plugUndef);
				} else if (node instanceof Reaction) {
					Reaction react = (Reaction) node;
					PluginReaction preac = plugModel.getReaction(react.getId());
					plugModel.removeReaction(react.getId());
					plugin.notifySBaseDeleted(preac);
				} else if (node instanceof SpeciesType) {
					SpeciesType speciestype = (SpeciesType) node;
					PluginSpeciesType pspec = plugModel
							.getSpeciesType(speciestype.getId());
					plugModel.removeSpeciesType(pspec);
					plugin.notifySBaseDeleted(pspec);
				} else if (node instanceof SimpleSpeciesReference) {
					SimpleSpeciesReference simspec = (SimpleSpeciesReference) node;
					String type = SBO.convertSBO2Alias(simspec.getSBOTerm());
					if (node instanceof ModifierSpeciesReference) {
						ModifierSpeciesReference modSpecRef = (ModifierSpeciesReference) node;
						PluginSpeciesAlias alias = plugModel.getSpecies(
								modSpecRef.getId()).getSpeciesAlias(type);
						PluginModifierSpeciesReference ref = new PluginModifierSpeciesReference(
								plugModel.getReaction(simspec.getId()), alias);
						plugModel.getReaction(simspec.getId()).removeModifier(
								ref);
						plugin.notifySBaseDeleted(ref);
					} else if (node instanceof SpeciesReference) {
						SpeciesReference specref = (SpeciesReference) node;
						PluginSpeciesAlias alias = plugModel.getSpecies(
								specref.getId()).getSpeciesAlias(type);
						PluginSpeciesReference ref = new PluginSpeciesReference(
								plugModel.getReaction(simspec.getId()), alias);
						plugModel.getReaction(simspec.getId()).removeProduct(
								ref);
						plugin.notifySBaseDeleted(ref);
					}
				} else if (node instanceof AbstractNamedSBaseWithUnit) {
					if (node instanceof Event) {
						Event event = (Event) node;
						PluginEvent plugEvent = plugModel.getEvent(event
								.getId());
						plugModel.removeEvent(event.getId());
						plugin.notifySBaseDeleted(plugEvent);
					} else if (node instanceof QuantityWithUnit) {
						if (node instanceof LocalParameter) {
							/*
							 * TODO: What to do with Localparameters? There are
							 * no LocalParameters in CD available.
							 */
						} else if (node instanceof Symbol) {
							if (node instanceof Compartment) {
								Compartment comp = (Compartment) node;
								PluginCompartment plugComp = plugModel
										.getCompartment(comp.getId());
								plugModel.removeCompartment(comp.getId());
								plugin.notifySBaseDeleted(plugComp);
							} else if (node instanceof Species) {
								Species sp = (Species) node;
								PluginSpecies ps = plugModel.getSpecies(sp
										.getId());
								plugModel.removeSpecies(sp.getId());
								plugin.notifySBaseDeleted(ps);
							} else if (node instanceof org.sbml.jsbml.Parameter) {
								org.sbml.jsbml.Parameter param = (org.sbml.jsbml.Parameter) node;
								PluginParameter plugParam = plugModel
										.getParameter(param.getId());
								plugModel.removeParameter(param.getId());
								plugin.notifySBaseDeleted(plugParam);
							}
						}
					}
				}
			}
			if (node instanceof Unit) {
				Unit ut = (Unit) node;
				// TODO
			} else if (node instanceof SBMLDocument) {
				SBMLDocument doc = (SBMLDocument) node;
				// TODO This needs to be hashed somehow.
			} else if (node instanceof ListOf<?>) {
				ListOf<?> listOf = (ListOf<?>) node;
				switch (listOf.getSBaseListType()) {
				case listOfCompartments:
//					ListOfCompartments ll = new ListOfCompartments();

					break;
				case listOfCompartmentTypes:
					break;
				case listOfConstraints:
					break;
				case listOfEventAssignments:
					break;
				case listOfEvents:
					break;
				case listOfFunctionDefinitions:
					break;
				case listOfInitialAssignments:
					break;
				case listOfLocalParameters:
					break;
				case listOfModifiers:
					break;
				case listOfParameters:
					break;
				case listOfProducts:
					break;
				case listOfReactants:
					break;
				case listOfReactions:
					break;
				case listOfRules:
					break;
				case listOfSpecies:
					break;
				case listOfSpeciesTypes:
					break;
				case listOfUnitDefinitions:
					break;
				case listOfUnits:
					break;
				case other:
					// TODO for JSBML packages (later than 0.8).
				default:
					// unknown
					break;
				}

			} else if (node instanceof AbstractMathContainer) {
				if (node instanceof FunctionDefinition) {
					FunctionDefinition funcdef = (FunctionDefinition) node;
					PluginFunctionDefinition plugFuncdef = plugModel
							.getFunctionDefinition(funcdef.getId());
					plugModel.removeFunctionDefinition(funcdef.getId());
					plugin.notifySBaseDeleted(plugFuncdef);
				} else if (node instanceof KineticLaw) {
					KineticLaw klaw = (KineticLaw) node;
					Reaction parentreaction = klaw.getParentSBMLObject();
					PluginReaction plugReac = plugModel
							.getReaction(parentreaction.getId());
					PluginKineticLaw plugklaw = plugReac.getKineticLaw();
					plugReac.setKineticLaw(null);
					plugin.notifySBaseDeleted(plugklaw);
				} else if (node instanceof InitialAssignment) {
					InitialAssignment iAssign = (InitialAssignment) node;
					PluginInitialAssignment plugiAssign = plugModel
							.getInitialAssignment(iAssign.getSymbol());
					plugModel.removeInitialAssignment(plugiAssign);
					plugin.notifySBaseDeleted(plugiAssign);
				} else if (node instanceof EventAssignment) {
					EventAssignment eAssign = (EventAssignment) node;
					ListOf<EventAssignment> elist = eAssign.getParent();
					Event e = (Event) elist.getParentSBMLObject();
					PluginEventAssignment plugEventAssignment = plugModel
							.getEvent(e.getId()).getEventAssignment(
									eAssign.getIndex(node));
					plugin.notifySBaseDeleted(plugEventAssignment);
				} else if (node instanceof StoichiometryMath) {
					logger.log(Level.DEBUG, String.format(
							"No counter class for %s in CellDesigner.", node
									.getClass().getSimpleName()));
				} else if (node instanceof Trigger) {
					Trigger trig = (Trigger) node;
					PluginEvent plugEvent = plugModel.getEvent(trig.getParent()
							.getId());
					logger.log(
							Level.DEBUG,
							String.format(
									"Trying to remove trigger from event %s, but there is no remove method. Please check the result.",
									plugEvent.getId()));
					plugEvent.setTrigger((org.sbml.libsbml.Trigger) null);
					plugin.notifySBaseChanged(plugEvent);
				} else if (node instanceof Constraint) {
					Constraint ct = (Constraint) node;
					PluginConstraint plugct = plugModel.getConstraint(ct
							.getMathMLString());
					plugModel.removeConstraint(ct.getMathMLString());
					plugin.notifySBaseDeleted(plugct);
				} else if (node instanceof Delay) {
					Delay dl = (Delay) node;
					PluginEvent plugEvent = plugModel.getEvent(dl.getParent()
							.getId());
					Delay dlnew = new Delay();
					plugEvent.setDelay(PluginUtils.convert(dlnew.getMath()));
					plugin.notifySBaseChanged(plugEvent);
				} else if (node instanceof Priority) {
					logger.log(Level.DEBUG, String.format(
							"No counter class for %s in CellDesigner.", node
									.getClass().getSimpleName()));
				} else if (node instanceof Rule) {
					if (node instanceof AlgebraicRule) {
						AlgebraicRule alrule = (AlgebraicRule) node;
						// TODO how to get the right algebraic rule ?
					} else if (node instanceof ExplicitRule) {
						if (node instanceof RateRule) {
							RateRule rrule = (RateRule) node;
							// TODO howto get the right Rate Rule ?
						} else if (node instanceof AssignmentRule) {
							AssignmentRule assignRule = (AssignmentRule) node;
							// TODO howto get the right AssignmentRule?
						}
					} else {
						// TODO case when we only have a "Rule" without anything
						// else
					}
				}
			}
		} else if (node instanceof AbstractTreeNode) {
			if (node instanceof XMLToken) {
				if (node instanceof XMLNode) {
					logger.log(Level.DEBUG, String.format(
							"Parsing of node %s not successful.", node
									.getClass().getSimpleName()));
				}
			} else if (node instanceof ASTNode) {
				logger.log(Level.DEBUG, String.format(
						"Parsing of node %s not successful.", node.getClass()
								.getSimpleName()));
			} else if (node instanceof AnnotationElement) {
				if (node instanceof CVTerm) {
					CVTerm term = (CVTerm) node;
					// TODO Here I dont know how to get the right CVTerm
				} else if (node instanceof History) {
					logger.log(Level.DEBUG, "No counter class in CellDesigner"
							+ node.getClass().getSimpleName());
				} else if (node instanceof Creator) {
					logger.log(Level.DEBUG, "No counter class in CellDesigner"
							+ node.getClass().getSimpleName());
				}
			}
		}
	}

	/**
	 * 
	 * @param evtSrc
	 * @return
	 */
	public PluginSBase getCorrespondingElementInJSBML(Object evtSrc) {
		if (evtSrc instanceof AbstractSBase) {
			if (evtSrc instanceof AbstractNamedSBase) {
				if (evtSrc instanceof CompartmentType) {
					CompartmentType ct = (CompartmentType) evtSrc;
					return plugModel.getCompartmentType(ct.getId());
				} else if (evtSrc instanceof UnitDefinition) {
					UnitDefinition ud = (UnitDefinition) evtSrc;
					return plugModel.getUnitDefinition(ud.getId());
				} else if (evtSrc instanceof Model) {
					return plugModel;
				} else if (evtSrc instanceof Reaction) {
					Reaction r = (Reaction) evtSrc;
					return plugModel.getReaction(r.getId());
				} else if (evtSrc instanceof SimpleSpeciesReference) {
					if (evtSrc instanceof ModifierSpeciesReference) {
						ModifierSpeciesReference mod = (ModifierSpeciesReference) evtSrc;
						if (mod.isSetParent()
								&& mod.getParent().isSetParentSBMLObject()) {
							Reaction r = (Reaction) mod.getParentSBMLObject().getParentSBMLObject();
							String reactionID = r.getId();
							return plugModel.getReaction(reactionID)
									.getModifier(getModifier(r, mod));
						}
					} else if (evtSrc instanceof SpeciesReference) {
						SpeciesReference specRef = (SpeciesReference) evtSrc;
						
						if (specRef.isSetParent() &&specRef.getParentSBMLObject().isSetParentSBMLObject()){
							String rID = ((Reaction) specRef.getParentSBMLObject().getParentSBMLObject()).getId();
						}
					}
				} else if (evtSrc instanceof AbstractNamedSBaseWithUnit) {
					if (evtSrc instanceof Event) {
						Event e = (Event) evtSrc;
						return plugModel.getEvent(e.getId());
					} else if (evtSrc instanceof QuantityWithUnit) {
						if (evtSrc instanceof LocalParameter) {
							LocalParameter lp = (LocalParameter) evtSrc;
							if (lp.isSetParent()
									&& lp.getParent().isSetParent()) {
								KineticLaw k = (KineticLaw) lp
										.getParentSBMLObject()
										.getParentSBMLObject();
								if (k.isSetParent()) {
									Reaction r = k.getParentSBMLObject();
									return plugModel.getReaction(r.getId()).getKineticLaw().getParameter(searchKineticLaw(k, lp));
								}
							}
						} else if (evtSrc instanceof Symbol) {
							if (evtSrc instanceof Compartment) {
								Compartment c = (Compartment) evtSrc;
								return plugModel.getCompartment(c.getId());
							} else if (evtSrc instanceof Species) {
								Species s = (Species) evtSrc;
								return plugModel.getSpecies(s.getId());
							} else if (evtSrc instanceof Parameter) {
								Parameter p = (Parameter) evtSrc;
								return plugModel.getParameter(p.getId());
							}
						}
					}
				}
			} else if (evtSrc instanceof Unit) {
				Unit u = (Unit) evtSrc;
				if (u.isSetParent()){
					UnitDefinition ut = (UnitDefinition) u.getParent().getParent();
					return plugModel.getUnitDefinition(ut.getId()).getUnit(getUnitIndex(ut, u));
				}
				
			} else if (evtSrc instanceof SBMLDocument) {
				return plugModel;
			} else if (evtSrc instanceof ListOf<?>) {
				Type listType = ((ListOf<?>) evtSrc).getSBaseListType();
				if (listType.equals(Type.listOfCompartments)) {
					return plugModel.getListOfCompartments();
				} else if (listType.equals(Type.listOfCompartmentTypes)) {
					return plugModel.getListOfCompartmentTypes();
				} else if (listType.equals(Type.listOfConstraints)) {
					return plugModel.getListOfConstraints();
				} else if (listType.equals(Type.listOfEvents)) {
					return plugModel.getListOfEvents();
				} else if (listType.equals(Type.listOfFunctionDefinitions)) {
					return plugModel.getListOfFunctionDefinitions();
				} else if (listType.equals(Type.listOfInitialAssignments)) {
					return plugModel.getListOfInitialAssignments();
				} else if (listType.equals(Type.listOfLocalParameters)) {
					if (((ListOf<?>) evtSrc).getParentSBMLObject() != null) {
						KineticLaw kinLaw = (KineticLaw) ((ListOf<?>) evtSrc)
								.getParentSBMLObject();
						return plugModel
								.getReaction(
										kinLaw.getParentSBMLObject().getId())
								.getKineticLaw().getListOfParameters();
					}
				} else if (listType.equals(Type.listOfModifiers)) {
					if (((ListOf<?>) evtSrc).getParentSBMLObject() != null) {
						Reaction reac = (Reaction) ((ListOf<?>) evtSrc)
								.getParentSBMLObject();
						return plugModel.getReaction(reac.getId())
								.getListOfModifiers();
					}
				} else if (listType.equals(Type.listOfParameters)) {
					return plugModel.getListOfParameters();
				} else if (listType.equals(Type.listOfProducts)) {
					if (((ListOf<?>) evtSrc).getParentSBMLObject() != null) {
						Reaction reac = (Reaction) ((ListOf<?>) evtSrc)
								.getParentSBMLObject();
						return plugModel.getReaction(reac.getId())
								.getListOfProducts();
					}
				} else if (listType.equals(Type.listOfReactants)) {
					if (((ListOf<?>) evtSrc).getParentSBMLObject() != null) {
						Reaction reac = (Reaction) ((ListOf<?>) evtSrc)
								.getParentSBMLObject();
						return plugModel.getReaction(reac.getId())
								.getListOfReactants();
					}
				} else if (listType.equals(Type.listOfReactions)) {
					return plugModel.getListOfReactions();
				} else if (listType.equals(Type.listOfRules)) {
					return plugModel.getListOfRules();
				} else if (listType.equals(Type.listOfSpecies)) {
					return plugModel.getListOfSpecies();
				} else if (listType.equals(Type.listOfSpeciesTypes)) {
					return plugModel.getListOfSpeciesTypes();
				} else if (listType.equals(Type.listOfUnitDefinitions)) {
					return plugModel.getListOfUnitDefinitions();
				} else if (listType.equals(Type.listOfUnits)) {
					if (((ListOf<?>) evtSrc).getParentSBMLObject() != null) {
						UnitDefinition udef = (UnitDefinition) ((ListOf<?>) evtSrc)
								.getParentSBMLObject();
						return plugModel.getUnitDefinition(udef.getId())
								.getListOfUnits();
					}
				}
			} else if (evtSrc instanceof AbstractMathContainer) {
				if (evtSrc instanceof FunctionDefinition) {
					FunctionDefinition fd = (FunctionDefinition) evtSrc;
					return plugModel.getFunctionDefinition(fd.getId());
				} else if (evtSrc instanceof KineticLaw) {
					KineticLaw k = (KineticLaw) evtSrc;
					Reaction r = (Reaction) k.getParentSBMLObject();
					if (r != null) {
						return plugModel.getReaction(r.getId()).getKineticLaw();
					} else {
						logger.log(Level.DEBUG, String.format(
								"Couldn't find node %s", evtSrc.getClass()
										.getSimpleName()));
					}
				} else if (evtSrc instanceof InitialAssignment) {
					InitialAssignment ia = (InitialAssignment) evtSrc;
					if (ia.isSetSymbol()) {
						return plugModel.getInitialAssignment(ia.getSymbol());
					} else {
						logger.log(Level.DEBUG, String.format(
								"Couldn't find node %s", evtSrc.getClass()
										.getSimpleName()));
					}
				} else if (evtSrc instanceof EventAssignment) {
					EventAssignment ea = (EventAssignment) evtSrc;
					Event e = (Event) ea.getParentSBMLObject();
					if (ea.isSetVariable()) {
						// TODO index access required, string using the variable
						// doesnt help here...
					}
				} else if (evtSrc instanceof StoichiometryMath) {
					StoichiometryMath sm = (StoichiometryMath) evtSrc;
					SpeciesReference sp = (SpeciesReference) sm
							.getParentSBMLObject();
					if (sp != null) {
						// TODO get SpeciesReference in plugmodel...return
						// plugModel.getSp
					} else {
						logger.log(Level.DEBUG, String.format(
								"Couldn't find node %s", evtSrc.getClass()
										.getSimpleName()));
					}
				} else if (evtSrc instanceof Trigger) {
					Trigger t = (Trigger) evtSrc;
					Event e = (Event) t.getParentSBMLObject();
					if (e != null) {
						// TODO check which return type is required now return
						// plugModel.getEvent(e.getId()).getTrigger();
					} else {
						logger.log(Level.DEBUG, String.format(
								"Couldn't find node %s", evtSrc.getClass()
										.getSimpleName()));
					}
				} else if (evtSrc instanceof Rule) {
					if (evtSrc instanceof AlgebraicRule) {
						// TODO getCorrespondingAlgebraicRule
					} else if (evtSrc instanceof RateRule) {
						RateRule rr = (RateRule) evtSrc;
						if (rr.isSetVariable()) {
							// TODO only indexed access available through :
							// return plugModel.getRule(index)
						} else {
							logger.log(Level.DEBUG, String.format(
									"Couldn't find node %s", evtSrc.getClass()
											.getSimpleName()));
						}
					} else if (evtSrc instanceof Constraint) {
						Constraint c = (Constraint) evtSrc;
						if (c.isSetMath()) {
							return plugModel.getConstraint(c.getMathMLString());
						} else {
							logger.log(Level.DEBUG, String.format(
									"Couldn't find node %s", evtSrc.getClass()
											.getSimpleName()));
						}
					} else if (evtSrc instanceof Delay) {
						Delay d = (Delay) evtSrc;
						if (d.isSetParent()) {
							Event e = d.getParent();
							// TODO Return Type needs to be fixed return
							// plugModel.getEvent(e.getId()).getDelay();
						} else {
							logger.log(Level.DEBUG, String.format(
									"Couldn't find node %s", evtSrc.getClass()
											.getSimpleName()));
						}
					} else if (evtSrc instanceof Priority) {
						Priority p = (Priority) evtSrc;
						Event e = p.getParent();
						if (e != null) {
							// TODO get Priority not applicable here ....return
							// plugModel.getEvent(e.getId()).get
						} else {
							logger.log(Level.DEBUG, String.format(
									"Couldn't find node %s", evtSrc.getClass()
											.getSimpleName()));
						}
					}
				}
			}
		}
		return null;
	}
	
	public int searchMathContainerASTNode(MathContainer c, ASTNode n){
		int counter = 0;
		Enumeration<TreeNode> e = c.children();
		TreeNode currNode = null;
		while ((currNode = e.nextElement()) != null){
			if(n.equals(currNode)){
				return counter;
			} else {
				counter++;
			}
		}
		return 0;
	}
	
	public int searchKineticLaw(KineticLaw k, LocalParameter p){
		ListOf<LocalParameter> lp = k.getListOfParameters();
		int temp = 0;
		for (int i = 0; i < lp.size(); i++){
			LocalParameter locp = lp.get(i);
			if (locp.equals(p)){
				temp = i;
				return temp;
			} else {
				continue;
			}
		}
		return temp;
	}
	
	public int getModifier(Reaction r, ModifierSpeciesReference m){
		ListOf<ModifierSpeciesReference> lmod = r.getListOfModifiers();
		int temp = 0;
		for (int i = 0; i < lmod.size(); i++){
			ModifierSpeciesReference mod = lmod.get(i);
			if (mod.equals(m)){
				temp = i;
				return temp;
			} else {
				continue;
			}
		}
		
		return temp;
	}
	
	public int getUnitIndex(UnitDefinition ud, Unit u){
		ListOf<Unit> lu = ud.getListOfUnits();
		int temp = 0;
		for (int i = 0; i < lu.size(); i++){
			if (lu.get(i).equals(u)){
				temp = i;
				return temp;
			} else {
				continue;
			}
		}
		return temp;
	}
}
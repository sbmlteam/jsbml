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
package org.sbml.jsbml.xml.libsbml;

import java.beans.PropertyChangeEvent;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AbstractMathContainer;
import org.sbml.jsbml.AbstractNamedSBaseWithUnit;
import org.sbml.jsbml.AbstractSBase;
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
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Priority;
import org.sbml.jsbml.QuantityWithUnit;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.SimpleSpeciesReference;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.StoichiometryMath;
import org.sbml.jsbml.Symbol;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.TreeNodeAdapter;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.xml.XMLToken;
import org.sbml.libsbml.ModelCreator;
import org.sbml.libsbml.SBMLDocument;
import org.sbml.libsbml.XMLNode;

/**
 * This class listens to the changes in the JSBML-Document and synchronizes the corresponding LibSBML-Document 
 * with the JSBML-Document. 
 * @author Meike Aichele
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 0.8
 */
@SuppressWarnings("deprecation")
public class LibSBMLChangeListener implements TreeNodeChangeListener {

	/**
	 * 
	 */
	private SBMLDocument libDoc;
	private org.sbml.jsbml.SBMLDocument doc;

	/**
	 * 
	 */
	private static final transient Logger logger = Logger
	.getLogger(LibSBMLChangeListener.class);

	/**
	 * 
	 * @param doc
	 * @param libDoc
	 */
	public LibSBMLChangeListener(org.sbml.jsbml.SBMLDocument doc,
			org.sbml.libsbml.SBMLDocument libDoc) {
		this.libDoc = libDoc;
		this.doc = doc;
	}


	/* 
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.util.TreeNodeChangeListener#nodeAdded(javax.swing.tree.TreeNode)
	 */
	public void nodeAdded(TreeNode node) {
		if (node instanceof AbstractSBase) {
			if (node instanceof org.sbml.jsbml.AbstractNamedSBase) {
				if (node instanceof CompartmentType) {
					CompartmentType ct = (CompartmentType) node;
					org.sbml.libsbml.CompartmentType libCt = libDoc.getModel().createCompartmentType();
					LibSBMLUtils.transferNamedSBaseProperties(ct, libCt);
					if (ct.isSetNotes()) {
						libCt.setNotes(ct.getNotesString());
					}
				} else if (node instanceof SpeciesType){
					SpeciesType specType = (SpeciesType) node;
					org.sbml.libsbml.SpeciesType LibSpecType = libDoc.getModel().createSpeciesType();
					LibSBMLUtils.transferNamedSBaseProperties(specType, LibSpecType);
				} else if (node instanceof UnitDefinition) {
					UnitDefinition udef = (UnitDefinition) node;
					org.sbml.libsbml.UnitDefinition libUdef = libDoc.getModel().createUnitDefinition();
					LibSBMLUtils.transferNamedSBaseProperties(udef, libUdef);
					if (udef.isSetListOfUnits()) {
						for (org.sbml.jsbml.Unit u : udef.getListOfUnits()) {
							//makes a recursion for every unit u in the list of units
							DefaultMutableTreeNode newnode = new DefaultMutableTreeNode(u);
							nodeAdded(newnode);
						}
					}
				} else if (node instanceof Model) {
					// uses the LibSBMLWriter to write the model-attributes in the libSBML-model
					Model model = (Model) node;
					LibSBMLWriter writer = new LibSBMLWriter();
					libDoc.setModel((org.sbml.libsbml.Model) writer.writeModel(model));

				} else if (node instanceof Reaction) {
					Reaction reac = (Reaction) node;
					org.sbml.libsbml.Reaction libReac = libDoc.getModel().createReaction();
					LibSBMLUtils.transferNamedSBaseProperties(reac, libReac);
					if (reac.isSetCompartment()) {
						libReac.setCompartment(reac.getCompartment());
					}
					if (reac.isSetFast()) {
						libReac.setFast(reac.getFast());
					}
					if (reac.isSetKineticLaw()) {
						DefaultMutableTreeNode kinlawnode = new DefaultMutableTreeNode(reac.getKineticLaw());
						nodeAdded(kinlawnode);
					}
					if (reac.isSetListOfModifiers()) {
						// makes a recursion for all modifiers in the list
						for (ModifierSpeciesReference modi : reac.getListOfModifiers()) {
							DefaultMutableTreeNode modinode = new DefaultMutableTreeNode(modi);
							nodeAdded(modinode);
						}
					}
					if (reac.isSetListOfProducts()) {
						// makes a recursion for all products in the list
						for (SpeciesReference product : reac.getListOfProducts()) {
							DefaultMutableTreeNode productnode = new DefaultMutableTreeNode(product);
							nodeAdded(productnode);
						}
					}
					if (reac.isSetListOfReactants()) {
						// makes a recursion for all reactants in the list
						for (SpeciesReference reactant : reac.getListOfReactants()) {
							DefaultMutableTreeNode reactantnode = new DefaultMutableTreeNode(reactant);
							nodeAdded(reactantnode);
						}
					}
					if (reac.isSetReversible()) {
						libReac.setReversible(reac.getReversible());
					}
				} else if (node instanceof SimpleSpeciesReference) {
					SimpleSpeciesReference siSpecRef = (SimpleSpeciesReference) node;
					if (node instanceof ModifierSpeciesReference) {
						org.sbml.libsbml.ModifierSpeciesReference libModifier = libDoc.getModel().createModifier();
						LibSBMLUtils.transferSimpleSpeciesReferenceProperties(siSpecRef, libModifier);
					}
					if (node instanceof SpeciesReference) {
						SpeciesReference specRef = (SpeciesReference) node;
						// Ask if the SpeciesReference object is a product or a reactant
						if (((ListOf<?>)specRef.getParentSBMLObject()).getSBaseListType().equals(Type.listOfProducts)) {
							org.sbml.libsbml.SpeciesReference libProduct = libDoc.getModel().createProduct();
							if (specRef.isSetConstant()) {
								libProduct.setConstant(specRef.getConstant());
							}	
							if (specRef.isSetStoichiometry()) {
								libProduct.setStoichiometry(specRef.getStoichiometry());
							}
							LibSBMLUtils.transferSimpleSpeciesReferenceProperties(specRef, libProduct);
						} else if (((ListOf<?>)specRef.getParentSBMLObject()).getSBaseListType().equals(Type.listOfReactants)) {
							org.sbml.libsbml.SpeciesReference libReactant = libDoc.getModel().createReactant();
							if (specRef.isSetConstant()) {
								libReactant.setConstant(specRef.getConstant());
							}	
							if (specRef.isSetStoichiometry()) {
								libReactant.setStoichiometry(specRef.getStoichiometry());
							}
							LibSBMLUtils.transferSimpleSpeciesReferenceProperties(specRef, libReactant);
						}
					}
				} else if (node instanceof AbstractNamedSBaseWithUnit) {
					if (node instanceof Event) {
						Event event = (Event) node;
						org.sbml.libsbml.Event libEvent = libDoc.getModel().createEvent();
						LibSBMLUtils.transferNamedSBaseProperties(event, libEvent);
						if (event.isSetDelay()) {
							Delay delay = event.getDelay();
							org.sbml.libsbml.Delay libDelay = libEvent.createDelay();
							LibSBMLUtils.transferMathContainerProperties(delay, libDelay);
							libEvent.setDelay(libDelay);
						}
						if (event.isSetListOfEventAssignments()) {
							for (EventAssignment eventassign : event.getListOfEventAssignments()) {
								DefaultMutableTreeNode eventassignnode = new DefaultMutableTreeNode(eventassign);
								nodeAdded(eventassignnode);
							}
						}
						if (event.isSetPriority()) {
							Priority prio = event.getPriority();
							org.sbml.libsbml.Priority libPrio = libEvent.createPriority();
							LibSBMLUtils.transferMathContainerProperties(prio, libPrio);
							libEvent.setPriority(libPrio);

						}
						if (event.isSetTimeUnits()) {
							libEvent.setTimeUnits(event.getTimeUnits());
						}
						if (event.isSetTrigger()) {
							DefaultMutableTreeNode triggerNode = new DefaultMutableTreeNode(event.getTrigger());
							nodeAdded(triggerNode);
						}
						// this case can be dropped, because the units were already set with the case of TimoUnits above
						// if (event.isSetUnits());
						if (event.isSetUseValuesFromTriggerTime()) {
							libEvent.setUseValuesFromTriggerTime(event.getUseValuesFromTriggerTime());
						}
					}
					else if (node instanceof QuantityWithUnit) {
						if (node instanceof LocalParameter) {
							LocalParameter locParam = (LocalParameter) node;
							org.sbml.libsbml.LocalParameter libLocParam = libDoc.getModel().createKineticLawLocalParameter();
							LibSBMLUtils.transferNamedSBaseProperties(locParam, libLocParam);
							if (locParam.isSetValue()) {
								libLocParam.setValue(locParam.getValue());
							}
						}
						else if (node instanceof Symbol) {
							if (node instanceof Compartment) {
								Compartment c = (Compartment) node;
								org.sbml.libsbml.Compartment libC = libDoc.getModel().createCompartment();
								LibSBMLUtils.transferNamedSBaseProperties(c, libC);
								if (c.isSetCompartmentType()) {
									libC.setCompartmentType(c.getCompartmentType());
								}
								if (c.isSetConstant()) {
									libC.setConstant(c.getConstant());
								}
								if (c.isSetOutside()) {
									libC.setOutside(c.getOutside());
								}
								if (c.isSetSize()) {
									libC.setSize(c.getSize());
								}
								if (c.isSetSpatialDimensions()) {
									libC.setSpatialDimensions(c.getSpatialDimensions());
								}
								if (c.isSetUnits()) {
									libC.setUnits(c.getUnits());
								}
								if (c.isSetVolume()) {
									libC.setVolume(c.getVolume());
								}
							} else if (node instanceof Species) {
								Species spec = (Species) node;
								org.sbml.libsbml.Species libS = libDoc.getModel().createSpecies();
								LibSBMLUtils.transferNamedSBaseProperties(spec, libS);
								if (spec.isSetBoundaryCondition()) {
									libS.setBoundaryCondition(spec.getBoundaryCondition());
								}
								if (spec.isSetCharge()) {
									libS.setCharge(spec.getCharge());
								}
								if (spec.isSetCompartment()) {
									libS.setCompartment(spec.getCompartment());
								}
								if (spec.isSetConstant()) {
									libS.setConstant(spec.getConstant());
								}
								if (spec.isSetConversionFactor()) {
									libS.setConversionFactor(spec.getConversionFactor());
								}
								if (spec.isSetHasOnlySubstanceUnits()) {
									libS.setHasOnlySubstanceUnits(spec.getHasOnlySubstanceUnits());
								}
								if (spec.isSetInitialAmount()) {
									libS.setInitialAmount(spec.getInitialAmount());
								}
								if (spec.isSetInitialConcentration()) {
									libS.setInitialConcentration(spec.getInitialConcentration());
								}
								if (spec.isSetSpatialSizeUnits()) {
									libS.setSpatialSizeUnits(spec.getSpatialSizeUnits());
								}
								if (spec.isSetSpeciesType()) {
									libS.setSpeciesType(spec.getSpeciesType());
								}
								if (spec.isSetSubstanceUnits()) {
									libS.setSubstanceUnits(spec.getSubstanceUnits());
								}
								if (spec.isSetUnits()) {
									libS.setUnits(spec.getUnits());
								}
							} else if (node instanceof Parameter) {
								Parameter param = (Parameter) node;
								org.sbml.libsbml.Parameter libParam = libDoc.getModel().createParameter();
								LibSBMLUtils.transferNamedSBaseProperties(param, libParam);
								if (param.isSetConstant()) {
									libParam.setConstant(param.getConstant());
								}
								if (param.isSetUnits()) {
									libParam.setUnits(param.getUnits());
								}
								if (param.isSetValue()) {
									libParam.setValue(param.getValue());
								}
							} 
						}
					}
				}
			} else if (node instanceof Unit) {
				Unit unit = (Unit) node;
				org.sbml.libsbml.Unit libUnit = libDoc.getModel().createUnit();
				LibSBMLUtils.transferSBaseProperties(unit, libUnit);
				if (unit.isSetExponent()) {
					libUnit.setExponent(unit.getExponent());
				}
				if (unit.isSetMultiplier()) {
					libUnit.setMultiplier(unit.getMultiplier());
				}
				if (unit.isSetOffset()) {
					libUnit.setOffset(unit.getOffset());
				}
				if (unit.isSetScale()) {
					libUnit.setScale(unit.getScale());
				}
				if (unit.isSetKind()) {
					LibSBMLUtils.transferKindProperties(unit, libUnit);
				}

				//else if (node instanceof SBMLDocument) 
				// This can never happen because the top-level element SBMLDocument is never added to anything.
			} else if (node instanceof ListOf<?>) {
				// I don't have to ask what type of list the node is, 
				// because of the recursion you get always the right case for every element in the list
				for (SBase sb : (ListOf<?>) node) {
					DefaultMutableTreeNode sbasenode = new DefaultMutableTreeNode(sb);
					nodeAdded(sbasenode);
				}	

			} else if (node instanceof AbstractMathContainer) {
				if (node instanceof FunctionDefinition) {
					FunctionDefinition funcDef = (FunctionDefinition) node;
					org.sbml.libsbml.FunctionDefinition libFuncDef = libDoc.getModel().createFunctionDefinition();
					LibSBMLUtils.transferNamedSBaseProperties(funcDef, libFuncDef);
					LibSBMLUtils.transferMathContainerProperties(funcDef, libFuncDef);
				}
				else if (node instanceof KineticLaw) {
					KineticLaw kinLaw = (KineticLaw) node;
					org.sbml.libsbml.KineticLaw libKinLaw = libDoc.getModel().createKineticLaw();
					LibSBMLUtils.transferMathContainerProperties(kinLaw, libKinLaw);
					if (kinLaw.isSetListOfLocalParameters()) {
						//makes a recursion 
						for (LocalParameter locParam : kinLaw.getListOfLocalParameters()) {
							DefaultMutableTreeNode locParamnode = new DefaultMutableTreeNode(locParam);
							nodeAdded(locParamnode);
						}
					}
					if (kinLaw.isSetSubstanceUnits()) {
						libKinLaw.setSubstanceUnits(kinLaw.getSubstanceUnits());
					}
					if (kinLaw.isSetTimeUnits()) {
						libKinLaw.setTimeUnits(kinLaw.getTimeUnits());
					}
					//this case can be dropped, because all units are already set with the two cases above
					//if (kinLaw.isSetUnits());					
				}
				else if (node instanceof InitialAssignment) {
					InitialAssignment initAssign = (InitialAssignment) node;
					org.sbml.libsbml.InitialAssignment libInitAssign = libDoc.getModel().createInitialAssignment();
					LibSBMLUtils.transferMathContainerProperties(initAssign, libInitAssign);
					if (initAssign.isSetSymbol()) {
						libInitAssign.setSymbol(initAssign.getSymbol());
					}
					//this case can be dropped, because the Symbol is the same as the Variable in this object
					//if (initAssign.isSetVariable());
				}
				else if (node instanceof EventAssignment) {
					EventAssignment eventAssign = (EventAssignment) node;
					org.sbml.libsbml.EventAssignment libEventAssign = libDoc.getModel().createEventAssignment();
					LibSBMLUtils.transferMathContainerProperties(eventAssign, libEventAssign);
					if (eventAssign.isSetVariable()) {
						libEventAssign.setVariable(eventAssign.getVariable());
					}
				}
				else if (node instanceof StoichiometryMath) {
					StoichiometryMath sMath = (StoichiometryMath) node;
					org.sbml.libsbml.StoichiometryMath libSMath = libDoc.getModel().getSpeciesReference(sMath.getParent().getId()).createStoichiometryMath();
					LibSBMLUtils.transferMathContainerProperties(sMath, libSMath);
				}
				else if (node instanceof Trigger) {
					Trigger trig = (Trigger) node;
					org.sbml.libsbml.Trigger LibTrig = libDoc.getModel().getEvent(trig.getParent().getId()).createTrigger();
					LibSBMLUtils.transferMathContainerProperties(trig, LibTrig);
					if (trig.isSetInitialValue()) {
						LibTrig.setInitialValue(trig.getInitialValue());
					}
					if (trig.isSetPersistent()) {
						LibTrig.setPersistent(trig.getPersistent());
					}
				}
				else if (node instanceof Rule) {
					Rule rule = (Rule) node;
					if (node instanceof AlgebraicRule) {
						org.sbml.libsbml.AlgebraicRule libAlgRule = libDoc.getModel().createAlgebraicRule();
						LibSBMLUtils.transferMathContainerProperties(rule, libAlgRule);
					}
					else{
						ExplicitRule expRule = (ExplicitRule) rule;
						if (node instanceof RateRule) {
							org.sbml.libsbml.RateRule libRateRule = libDoc.getModel().createRateRule();
							LibSBMLUtils.transferMathContainerProperties(expRule, libRateRule);
							if (expRule.isSetVariable()) {
								libRateRule.setVariable(expRule.getVariable());
							}
							if (expRule.isSetUnits()) {
								libRateRule.setUnits(expRule.getUnits());
							}
						}
						else if (node instanceof AssignmentRule) {
							org.sbml.libsbml.AssignmentRule libAssignRule = libDoc.getModel().createAssignmentRule();
							LibSBMLUtils.transferMathContainerProperties(expRule, libAssignRule);
							if (expRule.isSetVariable()) {
								libAssignRule.setVariable(expRule.getVariable());
							}
							if (expRule.isSetUnits()) {
								libAssignRule.setUnits(expRule.getUnits());
							}
						}
					}
				}
				else if (node instanceof Constraint) {
					Constraint constr = (Constraint) node;
					org.sbml.libsbml.Constraint libConstr = libDoc.getModel().createConstraint();
					LibSBMLUtils.transferMathContainerProperties(constr, libConstr);
					if (constr.isSetMessage()) {
						libConstr.setMessage(XMLNode.convertStringToXMLNode(constr.getMessage().toXMLString()));
					}
				}
				else if (node instanceof Delay) {
					Delay delay = (Delay) node;
					// libDoc.getModel().createDelay() would be shorter but can't be used, 
					// because this would create a new Delay inside the last Event object created in this Model
					org.sbml.libsbml.Delay libDelay = libDoc.getModel().getEvent(delay.getParent().getId()).createDelay();
					LibSBMLUtils.transferMathContainerProperties(delay, libDelay);
				}
				else if (node instanceof Priority) {
					Priority prio = (Priority) node;
					org.sbml.libsbml.Priority libPrio = libDoc.getModel().getEvent(prio.getParent().getId()).createPriority();
					LibSBMLUtils.transferMathContainerProperties(prio, libPrio);
				}
			}
		} else if (node instanceof AnnotationElement) {
			if (node instanceof CVTerm) {
				CVTerm cvt = (CVTerm) node;
				libDoc.addCVTerm(LibSBMLUtils.convertCVTerm(cvt));
			}
			else if (node instanceof History) {
				History his = (History) node;
				libDoc.setModelHistory(LibSBMLUtils.convertHistory(his));
			}
			else if (node instanceof Annotation) {
				// there is no comparable object in LibSBML to the Annotation-element in JSBML,
				// therefore we have to set the attributes in other elements in LibSBML
				Annotation annot = (Annotation) node;
				// we first have to ask if the Annotation has a Parent
				if (annot.isSetParent()) {
					SBase sbase = null;
					org.sbml.libsbml.SBase libSBase = null;
					if (annot.getParent() instanceof SBase) {
						sbase = (SBase) annot.getParent();
						// then we can find the annotation in LibSBML over it's parent
						libSBase = getCorrespondingSBaseElementInLibSBML(sbase);
					}
					if (annot.isSetAnnotation() && (libSBase != null)) {
						if (annot.isSetAbout()) {
							if (!libSBase.isSetMetaId()) {
								libSBase.setMetaId(annot.getAbout().substring(1));
							}
						}
						if (annot.isSetHistory()) {
							libSBase.setModelHistory(LibSBMLUtils.convertHistory(annot.getHistory()));
						}
						if (annot.isSetListOfCVTerms()) {
							for (CVTerm term : annot.getListOfCVTerms()) {
								libSBase.addCVTerm(LibSBMLUtils
										.convertCVTerm(term));
							}
						}
						if (annot.isSetNonRDFannotation()) {
							libSBase.setAnnotation(annot.getNonRDFannotation());
						}
					}
				}
			}
			else if (node instanceof Creator) {
				Creator creator = (Creator) node;
				org.sbml.libsbml.ModelCreator libCreator = new ModelCreator();
				if (creator.isSetEmail()) {
					libCreator.setEmail(creator.getEmail());
				}
				if (creator.isSetFamilyName()) {
					libCreator.setFamilyName(creator.getFamilyName());
				}
				if (creator.isSetGivenName()) {
					libCreator.setGivenName(creator.getGivenName());
				}
				if (creator.isSetOrganisation()) {
					libCreator.setOrganisation(creator.getOrganisation());
				}
				libDoc.getModelHistory().addCreator(libCreator);
			}
		} else if (node instanceof ASTNode) {
			ASTNode astnode = (ASTNode) node;
			logger.log(Level.DEBUG, String.format("Cannot add node" + astnode.getClass().getSimpleName()));
		} else if (node instanceof TreeNodeAdapter) {
			TreeNodeAdapter treeNodeAd = (TreeNodeAdapter) node;
			logger.log(Level.DEBUG, String.format("Cannot add node" + treeNodeAd.getClass().getSimpleName()));
		} else if (node instanceof XMLToken) {
			XMLToken token = (XMLToken) node;
			logger.log(Level.DEBUG, String.format("Cannot add node" + token.getClass().getSimpleName()));
		}
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.util.TreeNodeChangeListener#nodeRemoved(javax.swing.tree.TreeNode)
	 */
	public void nodeRemoved(TreeNode node) {
		org.sbml.libsbml.Model libModel = libDoc.getModel();
		if (node instanceof AbstractSBase) {
			if (node instanceof org.sbml.jsbml.AbstractNamedSBase) {
				if (node instanceof CompartmentType) {
					libModel.removeCompartmentType(((CompartmentType) node).getId());
				} else if (node instanceof SpeciesType){
					libModel.removeSpeciesType(((SpeciesType) node).getId());
				} else if (node instanceof UnitDefinition) {
					libModel.removeUnitDefinition(((UnitDefinition) node).getId());
				} else if (node instanceof Model) {
					libModel.delete();
				} else if (node instanceof Reaction) {
					libModel.removeReaction(((Reaction) node).getId());
				} else if (node instanceof SimpleSpeciesReference) {
					if (node instanceof ModifierSpeciesReference) {
						//get the corresponding reaction-ID
						String reacID =((Reaction) ((ModifierSpeciesReference)node).getParentSBMLObject().getParentSBMLObject()).getId();
						// search the corresponding reaction and remove the modifier
						libModel.getReaction(reacID).removeModifier(((ModifierSpeciesReference) node).getId());
					}
					if (node instanceof SpeciesReference) {
						SpeciesReference specRef = (SpeciesReference) node;
						String reacID = ((Reaction)specRef.getParentSBMLObject().getParentSBMLObject()).getId();
						if (specRef.getParentSBMLObject().equals(Type.listOfProducts)) {
							//search reaction and remove the product
							libModel.getReaction(reacID).removeProduct(specRef.getId());
						} else if (specRef.getParentSBMLObject().equals(Type.listOfReactants)) {
							// search reaction and remove the reactant
							libModel.getReaction(reacID).removeReactant(specRef.getId());
						}
					}
				} else if (node instanceof AbstractNamedSBaseWithUnit) {
					if (node instanceof Event) {
						libModel.removeEvent(((Event) node).getId());
					}
					else if (node instanceof QuantityWithUnit) {
						if (node instanceof LocalParameter) {
							// is a LocalParameter a Parameter?
							libModel.removeParameter(((LocalParameter) node).getId());
						}
						else if (node instanceof Symbol) {
							if (node instanceof Compartment) {
								libModel.removeCompartment(((Compartment) node).getId());
							} else if (node instanceof Species) {
								libModel.removeSpecies(((Species) node).getId());
							} else if (node instanceof Parameter) {
								libModel.removeParameter(((Parameter) node).getId());
							} 
						}
					}
				}
			} else if (node instanceof Unit) {
				// search corresponding UnitDefinition and remove the unit
				Unit unit = (Unit) node;
				UnitDefinition udef = (UnitDefinition) unit.getParentSBMLObject().getParentSBMLObject();
				// search the index of this Unit object and remove it
				int index = LibSBMLUtils.getUnitIndex(unit, udef);
				libModel.getUnitDefinition(udef.getId()).removeUnit(index);
			} else if (node instanceof SBMLDocument) {
				libDoc.delete();
			} else if (node instanceof ListOf<?>) {
				// make recursion and remove all elements in the list
				for(Object o : (ListOf<?>)node ) {
					TreeNode newNode = new DefaultMutableTreeNode(o);
					nodeRemoved(newNode);
				}
			} else if (node instanceof AbstractMathContainer) {
				if (node instanceof FunctionDefinition) {
					libModel.removeFunctionDefinition(((FunctionDefinition) node).getId());
				}
				else if (node instanceof KineticLaw) {
					// get the corresponding reaction and unset the kinetikLaw in there
					Reaction corresreac = ((KineticLaw) node).getParentSBMLObject();
					libModel.getReaction(corresreac.getId()).unsetKineticLaw();
				}
				else if (node instanceof InitialAssignment) {
					// get the InitialAssignment object based on the symbol and delete it
					InitialAssignment initAssign = (InitialAssignment) node;
					libModel.removeInitialAssignment(initAssign.getSymbol());
				}
				else if (node instanceof EventAssignment) {
					// search corresponding event and remove the EventAssignment indicated by the variable
					Event event = (Event) (((EventAssignment) node).getParentSBMLObject().getParentSBMLObject());
					libModel.getEvent(event.getId()).removeEventAssignment(((EventAssignment) node).getVariable());
				}
				else if (node instanceof StoichiometryMath) {
					// search corresponding SpeciesReference and unset the StoichiometryMath of it
					SpeciesReference specRef = (SpeciesReference) ((StoichiometryMath) node).getParentSBMLObject();
					libModel.getSpeciesReference(specRef.getId()).unsetStoichiometryMath();
				}
				else if (node instanceof Trigger) {
					//get corresponding event and delete it's trigger
					Event trigEvent = (Event) ((Trigger) node).getParentSBMLObject();
					libModel.getEvent(trigEvent.getId()).unsetTrigger();
				}
				else if (node instanceof Rule) {
					// find the variable in the Rule object to remove it
					if (node instanceof AlgebraicRule) {
						AlgebraicRule rule = (AlgebraicRule) node;
						if (LibSBMLUtils.getCorrespondingAlgRule(libDoc, rule) != null) {
							LibSBMLUtils.getCorrespondingAlgRule(libDoc, rule).delete();
						}
					} else if (node instanceof AssignmentRule) {
						AssignmentRule rule = (AssignmentRule) node;
						libModel.removeRule(rule.getVariable());
					} else if (node instanceof RateRule) {
						RateRule rule = (RateRule) node;
						libModel.removeRule(rule.getVariable());
					}
				}
				else if (node instanceof Constraint) {
					Constraint con = (Constraint) node;
					// find the index of this Constraint
					for (int k=0; k<con.getParent().size(); k++) {
						Constraint c = con.getParent().get(k);
						if (con.equals(c)) {
							libModel.removeConstraint(k);
							break;
						}
					}				
				}
				else if (node instanceof Delay) {
					// find corresponding Event and delete it's Delay
					Delay delay = (Delay) node;
					libModel.getEvent(delay.getParent().getId()).unsetDelay();
				}
				else if (node instanceof Priority) {
					//find the corresponding Event and delete it's Priority
					Priority prio = (Priority) node;
					Event prioEvent = prio.getParent();
					libModel.getEvent(prioEvent.getId()).unsetPriority();		
				}
			}
		} else if (node instanceof AnnotationElement) {
			if (node instanceof CVTerm) {
				//search index of CVTerm object
				CVTerm cvTerm = (CVTerm) node;
				for(int k=0; k<doc.getNumCVTerms();k++) {
					CVTerm cv = doc.getCVTerm(k);
					if(cv.equals(cvTerm)) {
						libDoc.getModel().getCVTerms().remove(k);
						break;
					}
				}
			}
			else if (node instanceof History) {
				libDoc.unsetModelHistory();
			}
			else if (node instanceof Annotation) {
				// it's not possible to find the right sbase in libSBML-Document
				// and when we could find it, there would be no method to delete it
				logger.log(Level.DEBUG, String.format("Cannot remove this %s in the libSBML-Document", node.getClass().getSimpleName()));
			}
			else if (node instanceof Creator) {
			    libDoc.getModelHistory().getListCreators().remove(LibSBMLUtils.getCreatorIndex((Creator) node, doc));
			}
		} else if (node instanceof ASTNode) {
			logger.log(Level.DEBUG, String.format("Cannot remove this %s in the libSBML-Document", node.getClass().getSimpleName()));
		} else if (node instanceof TreeNodeAdapter) {
			logger.log(Level.DEBUG, String.format("Cannot remove this %s in the libSBML-Document", node.getClass().getSimpleName()));
		} else if (node instanceof XMLToken) {
			logger.log(Level.DEBUG, String.format("Cannot remove this %s in the libSBML-Document", node.getClass().getSimpleName()));
		}
	}


	/* 
	 * (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		Object evtSrc = evt.getSource();
		String prop = evt.getPropertyName();

		if (prop.equals(TreeNodeChangeEvent.about)) {	
			Annotation anno = (Annotation) evtSrc;
			logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", anno.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.addCVTerm)) {	
			//then the evtSrc is a SBase
			org.sbml.libsbml.SBase correspondingElement = getCorrespondingSBaseElementInLibSBML(evtSrc);
			correspondingElement.addCVTerm(LibSBMLUtils.convertCVTerm((CVTerm) evt.getNewValue()));
			//		} else if (prop.equals(TreeNodeChangeEvent.addDeclaredNamespace)) {
			//this case can be disregarded because libSBML does this without a hint
		} else if (prop.equals(TreeNodeChangeEvent.addExtension)) {
			Annotation anno = (Annotation) evtSrc;
			logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", anno.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.addNamespace)) {
			// evtSrc is a SBase
			org.sbml.libsbml.SBase correspondingElement = getCorrespondingSBaseElementInLibSBML(evtSrc);
			correspondingElement.getNamespaces().add((String) evt.getNewValue());
		} else if (prop.equals(TreeNodeChangeEvent.annotation)) {
			// evtSrc is a SBase, Event is to UNset the annotation
			org.sbml.libsbml.SBase correspondingElement = getCorrespondingSBaseElementInLibSBML(evtSrc);
			correspondingElement.unsetAnnotation();	
		} else if (prop.equals(TreeNodeChangeEvent.annotationNameSpaces)) {
			Annotation anno = (Annotation) evtSrc;
			logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", anno.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.areaUnits)) {
			if (evtSrc instanceof Model) {
				libDoc.getModel().setAreaUnits(((Model) evtSrc).getAreaUnits());
			}
		} else if (prop.equals(TreeNodeChangeEvent.baseListType)) {
			// the element is a list, but this Event is only called when it's a new Type of List.
			// so there is nothing more to do in libDoc
		} else if (prop.equals(TreeNodeChangeEvent.boundaryCondition)) {
			Species spec = (Species) evtSrc;
			libDoc.getModel().getSpecies(spec.getId()).setBoundaryCondition(spec.getBoundaryCondition());
		} else if (prop.equals(TreeNodeChangeEvent.charge)) {
			Species spec = (Species) evtSrc;
			org.sbml.libsbml.Model libMod = libDoc.getModel();
			libMod.getSpecies(spec.getId()).setCharge(spec.getCharge());
		} else if (prop.equals(TreeNodeChangeEvent.childNode)) {
			ASTNode node = (ASTNode) evtSrc;
			logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", node.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.className)) {
			ASTNode node = (ASTNode) evtSrc;
			logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", node.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.compartment)) {
			if (evtSrc instanceof Species) {
				Species spec = (Species) evtSrc;
				libDoc.getModel().getSpecies(spec.getId()).setCompartment(spec.getCompartment());
			} else if (evtSrc instanceof Reaction) {
				Reaction reac = (Reaction) evtSrc;
				libDoc.getModel().getReaction(reac.getId()).setCompartment(reac.getCompartment());
			} 
		} else if (prop.equals(TreeNodeChangeEvent.compartmentType)) {
			if (evtSrc instanceof Compartment) {
				Compartment comp = (Compartment) evtSrc;
				libDoc.getModel().getCompartment(comp.getId()).setCompartmentType(comp.getCompartmentType());
			}
		} else if (prop.equals(TreeNodeChangeEvent.constant)) {
			// evtSrc can only be a type of Species, Parameter or SpeciesReference
			if (evtSrc instanceof Species) {
				Species spec = (Species) evtSrc;
				libDoc.getModel().getSpecies(spec.getId()).setConstant(spec.getConstant());
			} else if (evtSrc instanceof Parameter) {
				Parameter param = (Parameter) evtSrc;
				libDoc.getModel().getParameter(param.getId()).setConstant(param.getConstant());
			} else if (evtSrc instanceof SpeciesReference) {
				SpeciesReference specRef = (SpeciesReference) evtSrc;
				libDoc.getModel().getSpeciesReference(specRef.getId()).setConstant(specRef.getConstant());
			}
		} else if (prop.equals(TreeNodeChangeEvent.conversionFactor)) {
			if (evtSrc instanceof Species) {
				Species spec = (Species) evtSrc;
				libDoc.getModel().getSpecies(spec.getId()).setConversionFactor(spec.getConversionFactor());
			} else if (evtSrc instanceof Model) {
				libDoc.getModel().setConversionFactor(((Model) evtSrc).getConversionFactor());
			}
		} else if (prop.equals(TreeNodeChangeEvent.created)) {
			// evtSrc is a History-element
			libDoc.getModelHistory().setCreatedDate(LibSBMLUtils.convertDate(((History) evtSrc).getCreatedDate()));
		} else if (prop.equals(TreeNodeChangeEvent.creator)) {
			// evtSrc is a History-element
			libDoc.getModelHistory().addCreator(LibSBMLUtils.convertToModelCreator((Creator)evt.getNewValue()));
		} else if (prop.equals(TreeNodeChangeEvent.definitionURL)) {
			ASTNode node = (ASTNode) evtSrc;
			logger.log(Level.DEBUG, String.format("Couldn't find the %s in the libSBML-Document", node.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.denominator)) {
			if (evtSrc instanceof ASTNode) {
				ASTNode node = (ASTNode) evtSrc;
				logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", node.getClass().getSimpleName()));
			} else if (evtSrc instanceof SpeciesReference) {
				SpeciesReference specRef = (SpeciesReference) evtSrc;
				libDoc.getModel().getSpeciesReference(specRef.getId()).setDenominator(specRef.getDenominator());
			}
		} else if (prop.equals(TreeNodeChangeEvent.email)) {
			// evtSrc is a Creator
			Creator cr = (Creator) evtSrc;
			libDoc.getModelHistory().getCreator(LibSBMLUtils.getCreatorIndex(cr,doc)).setEmail(cr.getEmail());
		} else if (prop.equals(TreeNodeChangeEvent.encoding)) {
			ASTNode node = (ASTNode) evtSrc;
			logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", node.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.exponent)) {
			ASTNode node = (ASTNode) evtSrc;
			logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", node.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.extentUnits)) {
			if (evtSrc instanceof Model) {
				libDoc.getModel().setExtentUnits(((Model) evtSrc).getExtentUnits());
			}
		} else if (prop.equals(TreeNodeChangeEvent.familyName)) {
			Creator cr = (Creator) evtSrc;
			libDoc.getModelHistory().getCreator(LibSBMLUtils.getCreatorIndex(cr,doc)).setFamilyName(cr.getFamilyName());		
		} else if (prop.equals(TreeNodeChangeEvent.fast)) {
			if (evtSrc instanceof Reaction) {
				Reaction reac = (Reaction) evtSrc;
				libDoc.getModel().getReaction(reac.getId()).setFast(reac.getFast());
			}
		} else if (prop.equals(TreeNodeChangeEvent.formula)) {
			if (evtSrc instanceof KineticLaw) {
				KineticLaw kinLaw = (KineticLaw) evtSrc;
				Reaction parentKinLaw = kinLaw.getParent();
				libDoc.getModel().getReaction(parentKinLaw.getId()).getKineticLaw().setFormula(kinLaw.getFormula());
			}
		} else if (prop.equals(TreeNodeChangeEvent.givenName)) {
			Creator cr = (Creator) evtSrc;
			libDoc.getModelHistory().getCreator(LibSBMLUtils.getCreatorIndex(cr,doc)).setGivenName(cr.getGivenName());
		} else if (prop.equals(TreeNodeChangeEvent.hasOnlySubstanceUnits)) {
			if (evtSrc instanceof Species) {
				Species spec = (Species) evtSrc;
				libDoc.getModel().getSpecies(spec.getId()).setHasOnlySubstanceUnits(spec.getHasOnlySubstanceUnits());
			}
		} else if (prop.equals(TreeNodeChangeEvent.history)) {
			// evtSrc is an Annotation-element
			Annotation anno = (Annotation) evtSrc;
			if (anno.getParent() != null) {
				org.sbml.libsbml.SBase sb = getCorrespondingSBaseElementInLibSBML(anno.getParent());
				sb.setModelHistory(LibSBMLUtils.convertHistory(anno.getHistory()));
			}

		} else if (prop.equals(TreeNodeChangeEvent.id)) {
			if (evtSrc instanceof ASTNode) {
				ASTNode node = (ASTNode) evtSrc;
				logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", node.getClass().getSimpleName()));
			} else if (evtSrc instanceof FunctionDefinition) {
				FunctionDefinition funDef = (FunctionDefinition) evtSrc;
				libDoc.getModel().getFunctionDefinition((String)evt.getOldValue()).setId(funDef.getId());
			} else {
				// evtSrc is an instance of AbstractNamedSBase
				libDoc.getElementBySId((String)evt.getOldValue()).setId((String) evt.getNewValue());
			}
		} else if (prop.equals(TreeNodeChangeEvent.initialAmount)) {
			//evtSrc is a Species
			Species spec = (Species) evtSrc;
			libDoc.getModel().getSpecies(spec.getId()).setInitialAmount(spec.getInitialAmount());
		} else if (prop.equals(TreeNodeChangeEvent.initialValue)) {
			if (evtSrc instanceof Trigger) {
				Trigger trig = (Trigger) evtSrc;
				Event parentTrig = (Event) trig.getParent();
				libDoc.getModel().getEvent(parentTrig.getId()).getTrigger().setInitialValue(trig.getInitialValue());
			}
		} else if (prop.equals(TreeNodeChangeEvent.isEOF)) {
			if (evtSrc instanceof XMLToken) {
				XMLToken token = (XMLToken) evtSrc;
				logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", token.getClass().getSimpleName()));
			}
		} else if (prop.equals(TreeNodeChangeEvent.isExplicitlySetConstant)) {
			if (evtSrc instanceof LocalParameter) {
				LocalParameter loc = (LocalParameter) evtSrc;
				// there is no method in libSBML to set the LocalParameter explicitly constant
				logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", loc.getClass().getSimpleName()));
			}
		} else if (prop.equals(TreeNodeChangeEvent.isSetNumberType)) {
			ASTNode node = (ASTNode) evtSrc;
			logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", node.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.kind)) {
			if (evtSrc instanceof Unit) {
				Unit u = (Unit) evtSrc;
				UnitDefinition udef = (UnitDefinition) u.getParent().getParent();
				LibSBMLUtils.transferKindProperties(u, libDoc.getModel().getUnitDefinition(udef.getId()).getUnit(LibSBMLUtils.getUnitIndex(u, udef)));
			}
		} else if (prop.equals(TreeNodeChangeEvent.lengthUnits)) {
			if (evtSrc instanceof Model) {
				libDoc.getModel().setLengthUnits(((Model) evtSrc).getLengthUnits());
			}
		} else if (prop.equals(TreeNodeChangeEvent.level)) {
			libDoc.setLevelAndVersion(((AbstractSBase) evtSrc).getLevel(), ((AbstractSBase) evtSrc).getVersion());
		} else if (prop.equals(TreeNodeChangeEvent.mantissa)) {
			ASTNode node = (ASTNode) evtSrc;
			logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", node.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.math)) {
			MathContainer mathContainer = (MathContainer) evt.getSource();
			if (mathContainer instanceof KineticLaw) {
				Reaction r = ((KineticLaw) mathContainer).getParent();
				org.sbml.libsbml.KineticLaw libKl = libDoc.getModel().getReaction(r.getId()).getKineticLaw();
				libKl.setFormula(mathContainer.getFormula());
			} else if (mathContainer instanceof Constraint) {
				Constraint con = (Constraint) mathContainer;
				int index = LibSBMLUtils.getContraintIndex(con,doc);
				libDoc.getModel().getConstraint(index).setMath(LibSBMLUtils.convertASTNode(mathContainer.getMath()));
			} else if (mathContainer instanceof Delay) {
				Delay delay = (Delay) mathContainer;
				libDoc.getModel().getEvent(delay.getParent().getId()).getDelay().setMath(LibSBMLUtils.convertASTNode(mathContainer.getMath()));
			} else if (mathContainer instanceof FunctionDefinition) {
				FunctionDefinition funcDef = (FunctionDefinition) mathContainer;
				libDoc.getModel().getFunctionDefinition(funcDef.getId()).setMath(LibSBMLUtils.convertASTNode(mathContainer.getMath()));
			} else if (mathContainer instanceof StoichiometryMath) {
				StoichiometryMath sto = (StoichiometryMath) mathContainer;
				libDoc.getModel().getSpeciesReference(sto.getParent().getId()).getStoichiometryMath().setMath(LibSBMLUtils.convertASTNode(mathContainer.getMath()));
			} else if (mathContainer instanceof InitialAssignment) {
				InitialAssignment initAssign = (InitialAssignment) mathContainer;
				libDoc.getModel().getInitialAssignment(initAssign.getVariable()).setMath(LibSBMLUtils.convertASTNode(mathContainer.getMath()));
			} else if (mathContainer instanceof Priority) {
				Priority prio = (Priority) mathContainer;
				libDoc.getModel().getEvent(prio.getParent().getId()).getPriority().setMath(LibSBMLUtils.convertASTNode(mathContainer.getMath()));
			} else if (mathContainer instanceof Trigger) {
				Trigger trig = (Trigger) mathContainer;
				libDoc.getModel().getEvent(trig.getParent().getId()).getTrigger().setMath(LibSBMLUtils.convertASTNode(mathContainer.getMath()));
			}
		} else if (prop.equals(TreeNodeChangeEvent.message)) {
			if (evtSrc instanceof Constraint) {
				Constraint con = (Constraint) evtSrc;
				int index = LibSBMLUtils.getContraintIndex(con,doc);
				org.sbml.libsbml.XMLNode xml = new XMLNode(con.getMessageString());
				libDoc.getModel().getConstraint(index).setMessage(xml);
			}
		} else if (prop.equals(TreeNodeChangeEvent.metaId)) {
			org.sbml.libsbml.SBase correspondingElement = getCorrespondingSBaseElementInLibSBML(evtSrc);
			correspondingElement.setMetaId((String) evt.getNewValue());
		} else if (prop.equals(TreeNodeChangeEvent.model)) {
			org.sbml.jsbml.SBMLDocument doc = (org.sbml.jsbml.SBMLDocument) evtSrc;
			LibSBMLWriter write = new LibSBMLWriter();
			libDoc.setModel((org.sbml.libsbml.Model) write.writeModel(doc.getModel()));
		} else if (prop.equals(TreeNodeChangeEvent.modified)) {
			libDoc.getModelHistory().setModifiedDate(LibSBMLUtils.convertDate(((History)evtSrc).getModifiedDate()));
		} else if (prop.equals(TreeNodeChangeEvent.multiplier)) {
			if (evtSrc instanceof Unit) {
				Unit u = (Unit) evtSrc;
				UnitDefinition udef = (UnitDefinition) u.getParent().getParent();
				libDoc.getModel().getUnitDefinition(udef.getId()).getUnit(LibSBMLUtils.getUnitIndex(u, udef)).setMultiplier(u.getMultiplier());
			}
		} else if (prop.equals(TreeNodeChangeEvent.name)) {
			// evtSrc must be a type of namedSBase
			NamedSBase nsb = (NamedSBase) evtSrc;
			org.sbml.libsbml.SBase sb = getCorrespondingSBaseElementInLibSBML(nsb);
			sb.setName(nsb.getName());
		} else if (prop.equals(TreeNodeChangeEvent.namespace)) {
			// evtSrc is a XMLToken-element
			logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", evtSrc.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.nonRDFAnnotation)) {
			//evtSrc is an Annotation-element
			Annotation annot = (Annotation) evtSrc;
			if (annot.getParent() != null) {
				org.sbml.libsbml.SBase sb = getCorrespondingSBaseElementInLibSBML(annot.getParent());
				sb.setAnnotation(annot.getNonRDFannotation());
			} else {
				logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document, because there was no ParentSBMLObject found.", evtSrc.getClass().getSimpleName()));
			}
		} else if (prop.equals(TreeNodeChangeEvent.notes)) {
			org.sbml.libsbml.SBase correspondingElement = getCorrespondingSBaseElementInLibSBML(evtSrc);
			correspondingElement.setNotes((String) evt.getNewValue());
		} else if (prop.equals(TreeNodeChangeEvent.numerator)) {
			ASTNode node = (ASTNode) evtSrc;
			logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", node.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.offset)) {
			if (evtSrc instanceof Unit) {
				Unit u = (Unit) evtSrc;
				UnitDefinition udef = (UnitDefinition) u.getParent().getParent();
				libDoc.getModel().getUnitDefinition(udef.getId()).getUnit(LibSBMLUtils.getUnitIndex(u, udef)).setOffset(u.getOffset());
			}
		} else if (prop.equals(TreeNodeChangeEvent.organisation)) {
			Creator cr = (Creator) evtSrc;
			libDoc.getModelHistory().getCreator(LibSBMLUtils.getCreatorIndex(cr,doc)).setOrganisation(cr.getOrganisation());
		} else if (prop.equals(TreeNodeChangeEvent.outside)) {
			if (evtSrc instanceof Compartment) {
				Compartment comp = (Compartment) evtSrc;
				libDoc.getModel().getCompartment(comp.getId()).setOutside(comp.getOutside());
			}
		} else if (prop.equals(TreeNodeChangeEvent.parentSBMLObject)) {
			if (evtSrc instanceof ASTNode) {
				ASTNode node = (ASTNode) evtSrc;
				logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", node.getClass().getSimpleName()));
			}
		} else if (prop.equals(TreeNodeChangeEvent.persistent)) {
			if (evtSrc instanceof Trigger) {
				Trigger trig = (Trigger) evtSrc;
				Event parentTrig = (Event) trig.getParent();
				libDoc.getModel().getEvent(parentTrig.getId()).getTrigger().setPersistent(trig.getPersistent());
			}
		} else if (prop.equals(TreeNodeChangeEvent.priority)) {
			if (evtSrc instanceof Event) {
				Event event = (Event) evtSrc;
				org.sbml.libsbml.Priority prio = new org.sbml.libsbml.Priority(event.getPriority().getLevel(), event.getPriority().getVersion());
				LibSBMLUtils.transferMathContainerProperties(event.getPriority(), prio);
				libDoc.getModel().getEvent(event.getId()).setPriority(prio);
			}
		} else if (prop.equals(TreeNodeChangeEvent.qualifier)) {
			// evtSrc is a CVTerm
			CVTerm cvt = (CVTerm) evtSrc;
			// convert evtSrc to a libSBML-CVTerm to compare the attributes and save the changes
			org.sbml.libsbml.CVTerm myLibCvt = LibSBMLUtils.convertCVTerm(cvt);
			for(int k=0; k<doc.getNumCVTerms();k++) {
				CVTerm cv = doc.getCVTerm(k);
				if(cv.equals(cvt)) {
					org.sbml.libsbml.CVTerm libCvt = libDoc.getCVTerm(k);
					if (myLibCvt.getQualifierType() != libCvt.getQualifierType())
						libCvt.setQualifierType(myLibCvt.getQualifierType());
					if (myLibCvt.getBiologicalQualifierType() != libCvt.getBiologicalQualifierType())
						libCvt.setBiologicalQualifierType(myLibCvt.getBiologicalQualifierType());
					if (myLibCvt.getModelQualifierType() != libCvt.getModelQualifierType())
						libCvt.setModelQualifierType(myLibCvt.getModelQualifierType());
					break;
				}
			}

			// else if (prop.equals(TreeNodeChangeEvent.rdfAnnotationNamespaces));
			// then evtSrc is an Annotation, but
			// this case can be disregarded because libSBML does this without a hint
		} else if (prop.equals(TreeNodeChangeEvent.reversible)) {
			Reaction reac= (Reaction) evtSrc;
			libDoc.getModel().getReaction(reac.getId()).setReversible(reac.getReversible());
		} else if (prop.equals(TreeNodeChangeEvent.SBMLDocumentAttributes)) {
			org.sbml.jsbml.SBMLDocument sbmlDoc = (org.sbml.jsbml.SBMLDocument) evtSrc;
			if (!sbmlDoc.getSBMLDocumentAttributes().containsValue(libDoc.getLevel())) {
				propertyChange(new TreeNodeChangeEvent(sbmlDoc, TreeNodeChangeEvent.level, libDoc.getLevel() , sbmlDoc.getLevel()));
			}
			if (!sbmlDoc.getSBMLDocumentAttributes().containsValue(libDoc.getVersion())) {
				propertyChange(new TreeNodeChangeEvent(sbmlDoc, TreeNodeChangeEvent.version, libDoc.getVersion() , sbmlDoc.getVersion()));
			}
		} else if (prop.equals(TreeNodeChangeEvent.sboTerm)) {
			// evtSrc is a SBase
			org.sbml.libsbml.SBase correspondingElement = getCorrespondingSBaseElementInLibSBML(evtSrc);
			correspondingElement.setSBOTerm((Integer) evt.getNewValue());
		} else if (prop.equals(TreeNodeChangeEvent.scale)) {
			if (evtSrc instanceof Unit) {
				Unit u = (Unit) evtSrc;
				UnitDefinition udef = (UnitDefinition) u.getParent().getParent();
				libDoc.getModel().getUnitDefinition(udef.getId()).getUnit(LibSBMLUtils.getUnitIndex(u, udef)).setScale(u.getScale());
			}
		} else if (prop.equals(TreeNodeChangeEvent.setAnnotation)) {
			// evtSrc is a SBase
			org.sbml.libsbml.SBase correspondingElement = getCorrespondingSBaseElementInLibSBML(evtSrc);
			correspondingElement.setAnnotation((String)evt.getNewValue());
		} else if (prop.equals(TreeNodeChangeEvent.size)) {
			if (evtSrc instanceof Compartment) {
				Compartment comp = (Compartment) evtSrc;
				libDoc.getModel().getCompartment(comp.getId()).setSize(comp.getSize());
			}
		} else if (prop.equals(TreeNodeChangeEvent.spatialDimensions)) {
			if (evtSrc instanceof Compartment) {
				Compartment comp = (Compartment) evtSrc;
				libDoc.getModel().getCompartment(comp.getId()).setSpatialDimensions(comp.getSpatialDimensions());
			}
		} else if (prop.equals(TreeNodeChangeEvent.spatialSizeUnits)) {
			if (evtSrc instanceof Species) {
				Species spec = (Species) evtSrc;
				libDoc.getModel().getSpecies(spec.getId()).setSpatialSizeUnits(spec.getSpatialSizeUnits());
			}
		} else if (prop.equals(TreeNodeChangeEvent.species)) {
			if (evtSrc instanceof SimpleSpeciesReference) {
				SimpleSpeciesReference ref = (SimpleSpeciesReference) evtSrc;
				libDoc.getModel().getSpeciesReference(ref.getId()).setSpecies(ref.getSpecies());
			}
		} else if (prop.equals(TreeNodeChangeEvent.speciesType)) {
			if (evtSrc instanceof Species) {
				Species spec = (Species) evtSrc;
				libDoc.getModel().getSpecies(spec.getId()).setSpeciesType(spec.getSpeciesType());
			}
		} else if (prop.equals(TreeNodeChangeEvent.stoichiometry)) {
			SpeciesReference specRef = (SpeciesReference) evtSrc;
			libDoc.getModel().getSpeciesReference(specRef.getId()).setStoichiometry(specRef.getStoichiometry());
		} else if (prop.equals(TreeNodeChangeEvent.style)) {
			ASTNode node = (ASTNode) evtSrc;
			logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", node.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.substanceUnits)) {
			if (evtSrc instanceof Species) {
				Species spec = (Species) evtSrc;
				libDoc.getModel().getSpecies(spec.getId()).setSubstanceUnits(spec.getSubstanceUnits());
			} else if (evtSrc instanceof KineticLaw) {
				KineticLaw kinLaw = (KineticLaw) evtSrc;
				Reaction parentKinLaw = (Reaction) kinLaw.getParent();
				libDoc.getModel().getReaction(parentKinLaw.getId()).getKineticLaw().setSubstanceUnits(kinLaw.getSubstanceUnits());
			} else if (evtSrc instanceof Model) {
				libDoc.getModel().setSubstanceUnits(((Model) evtSrc).getSubstanceUnits());
			}
		} else if (prop.equals(TreeNodeChangeEvent.symbol)) {
			if(evtSrc instanceof InitialAssignment) {
				libDoc.getModel().getInitialAssignment(((InitialAssignment) evtSrc).getVariable()).setSymbol(((InitialAssignment) evtSrc).getSymbol());
			}
		} else if (prop.equals(TreeNodeChangeEvent.text)) {
			ASTNode node = (ASTNode) evtSrc;
			logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", node.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.timeUnits)) {
			// evtSrc must be a type of Event, KineticLaw or Model
			if (evtSrc instanceof Event) {
				Event event = (Event) evtSrc;
				libDoc.getModel().getEvent(event.getId()).setTimeUnits(event.getTimeUnits());
			} else if (evtSrc instanceof KineticLaw) {
				// find  the corresponding reaction and set the timeUnits
				KineticLaw kinLaw = (KineticLaw) evtSrc;
				Reaction parentKinLaw = (Reaction) kinLaw.getParent();
				libDoc.getModel().getReaction(parentKinLaw.getId()).getKineticLaw().setTimeUnits(kinLaw.getTimeUnits());
			} else if (evtSrc instanceof Model) {
				libDoc.getModel().setTimeUnits(((Model) evtSrc).getTimeUnits());
			}
		} else if (prop.equals(TreeNodeChangeEvent.type)) {
			ASTNode node = (ASTNode) evtSrc;
			logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", node.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.units)) {
			if (evtSrc instanceof Species) {
				Species spec = (Species) evtSrc;
				libDoc.getModel().getSpecies(spec.getId()).setUnits(spec.getUnits());
			} else if (evtSrc instanceof Compartment) {
				Compartment comp = (Compartment) evtSrc;
				libDoc.getModel().getCompartment(comp.getId()).setUnits(comp.getUnits());
			} else if (evtSrc instanceof Parameter) {
				Parameter param = (Parameter) evtSrc;
				libDoc.getModel().getParameter(param.getId()).setUnits(param.getUnits());
			} else if (evtSrc instanceof Rule) {
				Rule rule = (Rule) evtSrc;
				if (rule instanceof RateRule) {
					RateRule rrule = (RateRule) rule;
					libDoc.getModel().getRule(rrule.getVariable()).setUnits(rrule.getUnits());
				} else if (rule instanceof AlgebraicRule) {
					AlgebraicRule algRule = (AlgebraicRule) rule;
					org.sbml.libsbml.AlgebraicRule libAlgRule = LibSBMLUtils.getCorrespondingAlgRule(libDoc, algRule);
					if (libAlgRule != null) {
						libAlgRule.setUnits(algRule.getDerivedUnits());
					}
				} else if (rule instanceof AssignmentRule) {
					AssignmentRule assRule = (AssignmentRule) rule;
					libDoc.getModel().getRule(assRule.getVariable()).setUnits(assRule.getUnits());
				}
			}
		} else if (prop.equals(TreeNodeChangeEvent.unsetCVTerms)) {
			//all SBases can unset the CVTerms
			org.sbml.libsbml.SBase correspondingElement = getCorrespondingSBaseElementInLibSBML(evtSrc);
			correspondingElement.unsetCVTerms();
		} else if (prop.equals(TreeNodeChangeEvent.userObject)) {
			ASTNode node = (ASTNode) evtSrc;
			logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", node.getClass().getSimpleName()));
		} else if (prop.equals(TreeNodeChangeEvent.useValuesFromTriggerTime)) {
			Event event = (Event) evtSrc;
			libDoc.getModel().getEvent(event.getId()).setUseValuesFromTriggerTime(event.getUseValuesFromTriggerTime());
		} else if (prop.equals(TreeNodeChangeEvent.value)) {
			if (evtSrc instanceof ASTNode) {
				ASTNode node = (ASTNode) evtSrc;
				logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", node.getClass().getSimpleName()));
			} else if (evtSrc instanceof QuantityWithUnit){
				if (evtSrc instanceof Parameter) {
					Parameter param = (Parameter) evtSrc;
					libDoc.getModel().getParameter(param.getId()).setValue(param.getValue());
				} else if (evtSrc instanceof Species) {
					Species spec = (Species) evtSrc;
					if (spec.isSetInitialAmount()) {
						libDoc.getModel().getSpecies(spec.getId()).setInitialAmount(spec.getInitialAmount());
					} else {
						libDoc.getModel().getSpecies(spec.getId()).setInitialConcentration(spec.getInitialConcentration());
					}
				} else if (evtSrc instanceof Compartment){
					Compartment comp = (Compartment) evtSrc;
					libDoc.getModel().getCompartment(comp.getId()).setSize(comp.getSize());
				} else if (evtSrc instanceof LocalParameter) {
					LocalParameter locParam = (LocalParameter) evtSrc;
					org.sbml.libsbml.LocalParameter libLocParam = (org.sbml.libsbml.LocalParameter) getCorrespondingSBaseElementInLibSBML(locParam);
					libLocParam.setValue(locParam.getValue());
				}
			}
		} else if (prop.equals(TreeNodeChangeEvent.variable)) {
			if (evtSrc instanceof EventAssignment) {
				EventAssignment evAssign = (EventAssignment) evtSrc;
				Event parentEvAssign = (Event) evAssign.getParent().getParent();
				org.sbml.libsbml.Event libev = libDoc.getModel().getEvent(parentEvAssign.getId());
				if (evt.getOldValue() != null) {
					libev.getEventAssignment((String)evt.getOldValue()).setVariable(evAssign.getVariable());
				} else if (libev.getListOfEventAssignments().size() == 1){
					libev.getEventAssignment(0).setVariable(evAssign.getVariable());
				} else {
					// there is no way to find the EventAssignment
					logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", evAssign.getClass().getSimpleName()));
				}
			} else if (evtSrc instanceof Rule) {
				Rule rule = (Rule) evtSrc;
				if (rule instanceof RateRule) {
					RateRule rrule = (RateRule) rule;
					libDoc.getModel().getRule((String)evt.getOldValue()).setVariable(rrule.getVariable());
				} else if (rule instanceof AlgebraicRule) {
					AlgebraicRule algRule = (AlgebraicRule) rule;
					org.sbml.libsbml.AlgebraicRule libAlgRule = LibSBMLUtils.getCorrespondingAlgRule(libDoc, algRule);
					libAlgRule.setVariable((String) evt.getNewValue());
				} else if (rule instanceof AssignmentRule) {
					AssignmentRule assRule = (AssignmentRule) rule;
					libDoc.getModel().getRule((String)evt.getOldValue()).setVariable(assRule.getVariable());
				}
			}
		} else if (prop.equals(TreeNodeChangeEvent.version)) {
			libDoc.setLevelAndVersion(((AbstractSBase) evtSrc).getLevel(), ((AbstractSBase) evtSrc).getVersion());
		} else if (prop.equals(TreeNodeChangeEvent.volume)) {
			if (evtSrc instanceof Compartment) {
				Compartment comp = (Compartment) evtSrc;
				libDoc.getModel().getCompartment(comp.getId()).setVolume(comp.getVolume());
			}
		} else if (prop.equals(TreeNodeChangeEvent.volumeUnits)) {
			if (evtSrc instanceof Model) {
				libDoc.getModel().setVolumeUnits(((Model) evtSrc).getVolumeUnits());
			}
		} else if (prop.equals(TreeNodeChangeEvent.xmlTriple)) {
			XMLToken token = (XMLToken) evtSrc;
			logger.log(Level.DEBUG, String.format("Couldn't change the %s in the libSBML-Document", token.getClass().getSimpleName()));
		}
	}

	/**
	 * this method returns the corresponding SBase element of the incoming JSBML-SBase in the libSBML document,
	 * by using the hierarchy of JSBML
	 * @param evtSrc
	 * @return org.sbml.libsbml.SBase
	 */
	private org.sbml.libsbml.SBase getCorrespondingSBaseElementInLibSBML(Object evtSrc) {
		if (evtSrc instanceof AbstractSBase) {
			if (evtSrc instanceof org.sbml.jsbml.AbstractNamedSBase) {
				if (evtSrc instanceof CompartmentType) {
					CompartmentType ct = (CompartmentType) evtSrc;
					return libDoc.getModel().getCompartmentType(ct.getId());
				} else if (evtSrc instanceof UnitDefinition) {
					UnitDefinition udef = (UnitDefinition) evtSrc;
					return libDoc.getModel().getUnitDefinition(udef.getId());
				} else if (evtSrc instanceof Model) {
					return libDoc.getModel();
				} else if (evtSrc instanceof Reaction) {
					Reaction reac = (Reaction) evtSrc;
					return libDoc.getModel().getReaction(reac.getId());
				} else if (evtSrc instanceof SimpleSpeciesReference) {
					if (evtSrc instanceof ModifierSpeciesReference) {
						if (((ModifierSpeciesReference) evtSrc).isSetParent() && ((ModifierSpeciesReference) evtSrc).getParent().isSetParentSBMLObject()){
							String reacID =((Reaction) ((ModifierSpeciesReference)evtSrc).getParentSBMLObject().getParentSBMLObject()).getId();
							return libDoc.getModel().getReaction(reacID).getModifier(((ModifierSpeciesReference) evtSrc).getId());
						}
					}
					if (evtSrc instanceof SpeciesReference) {
						SpeciesReference specRef = (SpeciesReference) evtSrc;
						return libDoc.getModel().getSpeciesReference(specRef.getId());
					}
				} else if (evtSrc instanceof AbstractNamedSBaseWithUnit) {
					if (evtSrc instanceof Event) {
						Event event = (Event) evtSrc;
						return libDoc.getModel().getEvent(event.getId());
					}
					else if (evtSrc instanceof QuantityWithUnit) {
						if (evtSrc instanceof LocalParameter) {
							LocalParameter locParam = (LocalParameter) evtSrc;
							if (locParam.isSetParent() && locParam.getParent().isSetParent()){
								KineticLaw kinLaw = (KineticLaw) locParam.getParentSBMLObject().getParentSBMLObject();
								if (kinLaw.isSetParent()){
									Reaction reac = kinLaw.getParentSBMLObject();
									return libDoc.getModel().getReaction(reac.getId()).getKineticLaw().getLocalParameter(locParam.getId());
								}
							}
						}
						else if (evtSrc instanceof Symbol) {
							if (evtSrc instanceof Compartment) {
								Compartment c = (Compartment) evtSrc;
								return libDoc.getModel().getCompartment(c.getId());
							} else if (evtSrc instanceof Species) {
								Species spec = (Species) evtSrc;
								return libDoc.getModel().getSpecies(spec.getId());
							} else if (evtSrc instanceof Parameter) {
								Parameter param = (Parameter) evtSrc;
								return libDoc.getModel().getParameter(param.getId());
							} 
						}
					}
				}
			} else if (evtSrc instanceof Unit) {
				Unit unit = (Unit) evtSrc;
				if (unit.getParentSBMLObject() != null) {
					return libDoc.getModel().getUnitDefinition(((UnitDefinition) unit.getParentSBMLObject()).getId()).getUnit(LibSBMLUtils.getUnitIndex(unit, (UnitDefinition) unit.getParentSBMLObject()));
				} else {
					logger.log(Level.DEBUG, String.format("Couldn't find the %s in the libSBML-Document", evtSrc.getClass().getSimpleName()));
				}
			} else if (evtSrc instanceof SBMLDocument) {
				return libDoc;
			} else if (evtSrc instanceof ListOf<?>) {
				Type listType = ((ListOf<?>) evtSrc).getSBaseListType();
				if (listType.equals(Type.listOfCompartments)) {
					return libDoc.getModel().getListOfCompartments();
				} else if (listType.equals(Type.listOfCompartmentTypes)) {
					return libDoc.getModel().getListOfCompartmentTypes();
				} else if (listType.equals(Type.listOfConstraints)) {
					return libDoc.getModel().getListOfConstraints();
				} else if (listType.equals(Type.listOfEvents)) {
					return libDoc.getModel().getListOfEvents();
				} else if (listType.equals(Type.listOfFunctionDefinitions)) {
					return libDoc.getModel().getListOfFunctionDefinitions();
				} else if (listType.equals(Type.listOfInitialAssignments)) {
					return libDoc.getModel().getListOfInitialAssignments();
				} else if (listType.equals(Type.listOfLocalParameters)) {
					if(((ListOf<?>) evtSrc).getParentSBMLObject() != null) {
						KineticLaw kinLaw = (KineticLaw) ((ListOf<?>) evtSrc).getParentSBMLObject();
						return libDoc.getModel().getReaction(kinLaw.getParentSBMLObject().getId()).getKineticLaw().getListOfLocalParameters();
					}
				} else if (listType.equals(Type.listOfModifiers)) {
					if(((ListOf<?>) evtSrc).getParentSBMLObject() != null) {
						Reaction reac = (Reaction) ((ListOf<?>) evtSrc).getParentSBMLObject();
						return libDoc.getModel().getReaction(reac.getId()).getListOfModifiers();
					}
				} else if (listType.equals(Type.listOfParameters)) {
					return libDoc.getModel().getListOfParameters();
				} else if (listType.equals(Type.listOfProducts)) {
					if(((ListOf<?>) evtSrc).getParentSBMLObject() != null) {
						Reaction reac = (Reaction) ((ListOf<?>) evtSrc).getParentSBMLObject();
						return libDoc.getModel().getReaction(reac.getId()).getListOfProducts();
					}
				} else if (listType.equals(Type.listOfReactants)) {
					if(((ListOf<?>) evtSrc).getParentSBMLObject() != null) {
						Reaction reac = (Reaction) ((ListOf<?>) evtSrc).getParentSBMLObject();
						return libDoc.getModel().getReaction(reac.getId()).getListOfReactants();
					}
				} else if (listType.equals(Type.listOfReactions)) {
					return libDoc.getModel().getListOfReactions();
				} else if (listType.equals(Type.listOfRules)) {
					return libDoc.getModel().getListOfRules();
				} else if (listType.equals(Type.listOfSpecies)) {
					return libDoc.getModel().getListOfSpecies();
				} else if (listType.equals(Type.listOfSpeciesTypes)) {
					return libDoc.getModel().getListOfSpeciesTypes();
				} else if (listType.equals(Type.listOfUnitDefinitions)) {
					return libDoc.getModel().getListOfUnitDefinitions();
				} else if (listType.equals(Type.listOfUnits)) {
					if(((ListOf<?>) evtSrc).getParentSBMLObject() != null) {
						UnitDefinition udef = (UnitDefinition) ((ListOf<?>) evtSrc).getParentSBMLObject();
						return libDoc.getModel().getUnitDefinition(udef.getId()).getListOfUnits();
					}
				}
			} else if (evtSrc instanceof AbstractMathContainer) {
				if (evtSrc instanceof FunctionDefinition) {
					FunctionDefinition funcDef = (FunctionDefinition) evtSrc;
					org.sbml.libsbml.FunctionDefinition func = libDoc.getModel().getFunctionDefinition(funcDef.getId());
					return func;
				} else if (evtSrc instanceof KineticLaw) {
					KineticLaw kinLaw = (KineticLaw) evtSrc;
					Reaction reac = kinLaw.getParentSBMLObject();
					if (reac != null) {
						return libDoc.getModel().getReaction(reac.getId()).getKineticLaw();
					} else {
						logger.log(Level.DEBUG, String.format("Couldn't find the %s in the libSBML-Document", evtSrc.getClass().getSimpleName()));
					}
				} else if (evtSrc instanceof InitialAssignment) {
					InitialAssignment initAssign = (InitialAssignment) evtSrc;
					if (initAssign.isSetSymbol()) {
						return libDoc.getModel().getInitialAssignment(initAssign.getSymbol());
					} else {
						logger.log(Level.DEBUG, String.format("Couldn't find the %s in the libSBML-Document", evtSrc.getClass().getSimpleName()));
					}
				} else if (evtSrc instanceof EventAssignment) {
					EventAssignment eventAssign = (EventAssignment) evtSrc;
					Event event = (Event) eventAssign.getParentSBMLObject();
					if (eventAssign.isSetVariable()) {
						return libDoc.getModel().getEvent(event.getId()).getEventAssignment(eventAssign.getVariable());
					} else {
						logger.log(Level.DEBUG, String.format("Couldn't find the %s in the libSBML-Document", evtSrc.getClass().getSimpleName()));
					}
				} else if (evtSrc instanceof StoichiometryMath) {
					StoichiometryMath sMath = (StoichiometryMath) evtSrc;
					SpeciesReference sbref = (SpeciesReference) sMath.getParentSBMLObject();
					if (sbref != null) {
						return libDoc.getModel().getSpeciesReference(sbref.getId()).getStoichiometryMath();
					} else {
						logger.log(Level.DEBUG, String.format("Couldn't find the %s in the libSBML-Document", evtSrc.getClass().getSimpleName()));
					}
				} else if (evtSrc instanceof Trigger) {
					Trigger trig = (Trigger) evtSrc;
					Event event = (Event) trig.getParentSBMLObject();
					if (event != null) {
						return libDoc.getModel().getEvent(event.getId()).getTrigger();
					} else {
						logger.log(Level.DEBUG, String.format("Couldn't find the %s in the libSBML-Document", evtSrc.getClass().getSimpleName()));
					}
				} else if (evtSrc instanceof Rule) {
					if (evtSrc instanceof AlgebraicRule) {
						if (LibSBMLUtils.getCorrespondingAlgRule(libDoc, (AlgebraicRule) evtSrc) != null) {
							return LibSBMLUtils.getCorrespondingAlgRule(libDoc, (AlgebraicRule) evtSrc);
						} else {
							logger.log(Level.DEBUG, String.format("Couldn't find the %s in the libSBML-Document", evtSrc.getClass().getSimpleName()));
						}
					} else {
						if (evtSrc instanceof RateRule) {
							if (((RateRule) evtSrc).isSetVariable()) {
								return libDoc.getModel().getRule(((RateRule) evtSrc).getVariable());
							} else {
								logger.log(Level.DEBUG, String.format("Couldn't find the %s in the libSBML-Document", evtSrc.getClass().getSimpleName()));
							}
						} else if (evtSrc instanceof Constraint) {
							Constraint constr = (Constraint) evtSrc;
							return libDoc.getModel().getConstraint(LibSBMLUtils.getContraintIndex(constr,doc));
						} else if (evtSrc instanceof Delay) {
							Delay delay = (Delay) evtSrc;
							if (delay.isSetParent()) {
								return libDoc.getModel().getEvent(delay.getParent().getId()).getDelay();
							} else {
								logger.log(Level.DEBUG, String.format("Couldn't find the %s in the libSBML-Document", evtSrc.getClass().getSimpleName()));
							}
						} else if (evtSrc instanceof Priority) {
							Priority prio = (Priority) evtSrc;
							Event prioEvent = prio.getParent();
							if (prioEvent != null) {
								return libDoc.getModel().getEvent(prioEvent.getId()).getPriority();
							} else {
								logger.log(Level.DEBUG, String.format("Couldn't find the %s in the libSBML-Document", evtSrc.getClass().getSimpleName()));
							}
						}
					}
				} 
				/*
				  else if (evtSrc instanceof AnnotationElement) {
				  // this case can not appear because an AnnotationElement is no SBase
				} else if (evtSrc instanceof ASTNode) {
					// this case can not appear because ASTNode is no SBase
				} else if (evtSrc instanceof TreeNodeAdapter) {
					// this case can not appear because TreeNodeAdapter is no SBase
				} else if (evtSrc instanceof XMLToken) {
					// this case can not appear because XMLToken is no SBase
				}
				 */		
			}
		}
		return null;
	}

}

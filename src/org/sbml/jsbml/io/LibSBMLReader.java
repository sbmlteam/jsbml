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

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import org.sbml.jsbml.AlgebraicRule;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.CompartmentType;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ModelCreator;
import org.sbml.jsbml.ModelHistory;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.StoichiometryMath;
import org.sbml.jsbml.Symbol;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.CVTerm.Qualifier;
import org.sbml.libsbml.libsbmlConstants;

/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 */
public class LibSBMLReader extends AbstractSBMLReader {

	private Set<org.sbml.libsbml.SBMLDocument> setOfDocuments;
	private static final String error = " must be an instance of ";
	private org.sbml.libsbml.Model originalModel;

	public LibSBMLReader() {
		setOfDocuments = new HashSet<org.sbml.libsbml.SBMLDocument>();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBMLReader#convertDate(java.lang.Object)
	 */
	public Date convertDate(Object date) {
		if (!(date instanceof org.sbml.libsbml.Date))
			throw new IllegalArgumentException("date" + error
					+ "org.sbml.libsbml.Date.");
		org.sbml.libsbml.Date d = (org.sbml.libsbml.Date) date;
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone(TimeZone.getAvailableIDs((int) (d
				.getSignOffset()
				* d.getMinutesOffset() * 60000))[0]));
		c.set((int) d.getYear(), (int) d.getMonth(), (int) d.getDay(), (int) d
				.getHour(), (int) d.getMinute(), (int) d.getSecond());
		return c.getTime();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.squeezer.io.AbstractSBMLReader#getOriginalModel()
	 */
	// @Override
	public org.sbml.libsbml.Model getOriginalModel() {
		return originalModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readCompartment(java.lang.Object)
	 */
	public Compartment readCompartment(Object compartment) {
		if (!(compartment instanceof org.sbml.libsbml.Compartment))
			throw new IllegalArgumentException("compartment" + error
					+ "org.sbml.libsbml.Compartment");
		org.sbml.libsbml.Compartment comp = (org.sbml.libsbml.Compartment) compartment;
		Compartment c = new Compartment(comp.getId(), (int) comp.getLevel(),
				(int) comp.getVersion());
		copyNamedSBaseProperties(c, comp);
		if (comp.isSetOutside()) {
			Compartment outside = getModel().getCompartment(comp.getOutside());
			if (outside == null)
				getModel().addCompartment(readCompartment(compartment));
			c.setOutside(outside);
		}
		if (comp.isSetCompartmentType())
			c
					.setCompartmentType(readCompartmentType(comp
							.getCompartmentType()));
		c.setConstant(comp.getConstant());
		c.setSize(comp.getSize());
		c.setSpatialDimensions((short) comp.getSpatialDimensions());
		if (comp.isSetUnits())
			c.setUnits(getModel().getUnitDefinition(comp.getUnits()));
		return c;
	}

	/**
	 * 
	 * @param compartmenttype
	 * @return
	 */
	public CompartmentType readCompartmentType(Object compartmenttype) {
		if (!(compartmenttype instanceof org.sbml.libsbml.CompartmentType))
			throw new IllegalArgumentException("compartmenttype" + error
					+ "org.sbml.libsbml.CompartmentType");
		org.sbml.libsbml.CompartmentType comp = (org.sbml.libsbml.CompartmentType) compartmenttype;
		CompartmentType com = new CompartmentType(comp.getId(), (int) comp
				.getLevel(), (int) comp.getVersion());
		copyNamedSBaseProperties(com, comp);
		return com;
	}

	/**
	 * 
	 * @param constraint
	 * @return
	 */
	public Constraint readConstraint(Object constraint) {
		if (!(constraint instanceof org.sbml.libsbml.Constraint))
			throw new IllegalArgumentException("constraint" + error
					+ "org.sbml.libsml.Constraint");
		org.sbml.libsbml.Constraint cons = (org.sbml.libsbml.Constraint) constraint;
		Constraint con = new Constraint((int) cons.getLevel(), (int) cons
				.getVersion());
		copySBaseProperties(con, cons);
		if (cons.isSetMath())
			con.setMath(convert(cons.getMath(), con));
		if (cons.isSetMessage())
			con.setMessage(cons.getMessageString());
		return con;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBMLReader#readCVTerm(java.lang.Object)
	 */
	public CVTerm readCVTerm(Object term) {
		if (!(term instanceof org.sbml.libsbml.CVTerm))
			throw new IllegalArgumentException("term" + error
					+ "org.sbml.libsbml.CVTerm.");
		org.sbml.libsbml.CVTerm libCVt = (org.sbml.libsbml.CVTerm) term;
		CVTerm t = new CVTerm();
		switch (libCVt.getQualifierType()) {
		case libsbmlConstants.MODEL_QUALIFIER:
			t.setQualifierType(CVTerm.Qualifier.MODEL_QUALIFIER);
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
			t.setQualifierType(CVTerm.Qualifier.BIOLOGICAL_QUALIFIER);
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
	public Delay readDelay(Object delay) {
		if (!(delay instanceof org.sbml.libsbml.Delay))
			throw new IllegalArgumentException("delay" + error
					+ "org.sbml.libsbml.Delay");
		org.sbml.libsbml.Delay del = (org.sbml.libsbml.Delay) delay;
		Delay de = new Delay((int) del.getLevel(), (int) del.getVersion());
		copySBaseProperties(de, del);
		if (del.isSetMath())
			de.setMath(convert(del.getMath(), de));
		return de;
	}

	/**
	 * 
	 * @param event
	 * @return
	 */
	public Event readEvent(Object event) {
		if (!(event instanceof org.sbml.libsbml.Event))
			throw new IllegalArgumentException("event" + error
					+ "org.sbml.libsbml.Event");
		org.sbml.libsbml.Event eve = (org.sbml.libsbml.Event) event;
		Event ev = new Event((int) eve.getLevel(), (int) eve.getVersion());
		copyNamedSBaseProperties(ev, eve);
		if (eve.isSetTrigger())
			ev.setTrigger(readTrigger(eve.getTrigger()));
		if (eve.isSetDelay())
			ev.setDelay(readDelay(eve.getDelay()));
		for (int i = 0; i < eve.getNumEventAssignments(); i++) {
			ev.addEventAssignement(readEventAssignment(eve
					.getEventAssignment(i)));
		}
		if (eve.isSetTimeUnits())
			ev.setTimeUnits(eve.getTimeUnits());
		ev.setUseValuesFromTriggerTime(eve.getUseValuesFromTriggerTime());
		return ev;

	}

	/**
	 * 
	 * @param eventAssignment
	 * @return
	 */
	public EventAssignment readEventAssignment(Object eventass) {
		if (!(eventass instanceof org.sbml.libsbml.EventAssignment))
			throw new IllegalArgumentException("eventassignment" + error
					+ "org.sbml.libsbml.EventAssignment");
		org.sbml.libsbml.EventAssignment eve = (org.sbml.libsbml.EventAssignment) eventass;
		EventAssignment ev = new EventAssignment((int) eve.getLevel(),
				(int) eve.getVersion());
		copySBaseProperties(ev, eve);
		if (eve.isSetVariable()) {
			Symbol variable = model.findSymbol(eve.getVariable());
			if (variable == null)
				ev.setVariable(eve.getVariable());
			else
				ev.setVariable(variable);
		}
		if (eve.isSetMath())
			ev.setMath(convert(eve.getMath(), ev));
		return ev;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readFunctionDefinition(java.lang.Object)
	 */
	public FunctionDefinition readFunctionDefinition(Object functionDefinition) {
		if (!(functionDefinition instanceof org.sbml.libsbml.FunctionDefinition))
			throw new IllegalArgumentException("functionDefinition" + error
					+ "org.sbml.libsbml.FunctionDefinition.");
		org.sbml.libsbml.FunctionDefinition fd = (org.sbml.libsbml.FunctionDefinition) functionDefinition;
		FunctionDefinition f = new FunctionDefinition(fd.getId(), (int) fd
				.getLevel(), (int) fd.getVersion());
		copyNamedSBaseProperties(f, fd);
		if (fd.isSetMath())
			f.setMath(convert(fd.getMath(), f));
		return f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readInitialAssignment(java.lang.Object)
	 */
	public InitialAssignment readInitialAssignment(Object initialAssignment) {
		if (!(initialAssignment instanceof org.sbml.libsbml.InitialAssignment))
			throw new IllegalArgumentException("initialAssignment" + error
					+ "org.sbml.libsbml.InitialAssignment.");
		org.sbml.libsbml.InitialAssignment sbIA = (org.sbml.libsbml.InitialAssignment) initialAssignment;
		if (!sbIA.isSetSymbol())
			throw new IllegalArgumentException(
					"Symbol attribute not set for InitialAssignment");
		InitialAssignment ia = new InitialAssignment(model.findSymbol(sbIA
				.getSymbol()));
		copySBaseProperties(ia, sbIA);
		if (sbIA.isSetMath())
			ia.setMath(convert(sbIA.getMath(), ia));
		return ia;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readKineticLaw(java.lang.Object)
	 */
	public KineticLaw readKineticLaw(Object kineticLaw) {
		if (!(kineticLaw instanceof org.sbml.libsbml.KineticLaw))
			throw new IllegalArgumentException("kineticLaw" + error
					+ "org.sbml.libsbml.KineticLaw.");
		org.sbml.libsbml.KineticLaw kl = (org.sbml.libsbml.KineticLaw) kineticLaw;
		KineticLaw kinlaw = new KineticLaw((int) kl.getLevel(), (int) kl
				.getVersion());
		copySBaseProperties(kinlaw, kl);
		for (int i = 0; i < kl.getNumParameters(); i++)
			kinlaw.addParameter(readParameter(kl.getParameter(i)));
		if (kl.isSetMath())
			kinlaw.setMath(convert(kl.getMath(), kinlaw));
		addAllSBaseChangeListenersTo(kinlaw);
		return kinlaw;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readModel(java.lang.Object)
	 */
	public Model readModel(Object model) {
		if (model instanceof String) {
			org.sbml.libsbml.SBMLDocument doc = (new org.sbml.libsbml.SBMLReader())
					.readSBML(model.toString());
			setOfDocuments.add(doc);
			model = doc.getModel();
		}
		if (model instanceof org.sbml.libsbml.Model) {
			this.originalModel = (org.sbml.libsbml.Model) model;
			SBMLDocument sbmldoc = new SBMLDocument((int) originalModel
					.getLevel(), (int) originalModel.getVersion());
			copySBaseProperties(sbmldoc, originalModel.getSBMLDocument());
			this.model = sbmldoc.createModel(originalModel.getId());
			copyNamedSBaseProperties(this.model, originalModel);
			int i;
			if (originalModel.isSetModelHistory()) {
				ModelHistory mh = new ModelHistory();
				org.sbml.libsbml.ModelHistory libHist = originalModel
						.getModelHistory();
				for (i = 0; i < libHist.getNumCreators(); i++) {
					ModelCreator mc = new ModelCreator();
					org.sbml.libsbml.ModelCreator creator = originalModel
							.getModelHistory().getCreator(i);
					mc.setGivenName(creator.getGivenName());
					mc.setFamilyName(creator.getFamilyName());
					mc.setEmail(creator.getEmail());
					mc.setOrganization(creator.getOrganization());
					mh.addCreator(mc);
				}
				if (libHist.isSetCreatedDate())
					mh.setCreatedDate(convertDate(libHist.getCreatedDate()));
				if (libHist.isSetModifiedDate())
					mh.setModifiedDate(convertDate(libHist.getModifiedDate()));
				for (i = 0; i < libHist.getNumModifiedDates(); i++)
					mh.addModifiedDate(convertDate(libHist.getModifiedDate(i)));
				this.model.setModelHistory(mh);
			}
			for (i = 0; i < originalModel.getNumFunctionDefinitions(); i++)
				this.model
						.addFunctionDefinition(readFunctionDefinition(originalModel
								.getFunctionDefinition(i)));
			for (i = 0; i < originalModel.getNumUnitDefinitions(); i++)
				this.model.addUnitDefinition(readUnitDefinition(originalModel
						.getUnitDefinition(i)));
			// This is something, libSBML wouldn't do...
			addPredefinedUnitDefinitions(this.model);
			for (i = 0; i < originalModel.getNumCompartmentTypes(); i++)
				this.model.addCompartmentType(readCompartmentType(originalModel
						.getCompartmentType(i)));
			for (i = 0; i < originalModel.getNumSpeciesTypes(); i++)
				this.model.addSpeciesType(readSpeciesType(originalModel
						.getSpeciesType(i)));
			for (i = 0; i < originalModel.getNumCompartments(); i++)
				this.model.addCompartment(readCompartment(originalModel
						.getCompartment(i)));
			for (i = 0; i < originalModel.getNumSpecies(); i++)
				this.model.addSpecies(readSpecies(originalModel.getSpecies(i)));
			for (i = 0; i < originalModel.getNumParameters(); i++)
				this.model.addParameter(readParameter(originalModel
						.getParameter(i)));
			for (i = 0; i < originalModel.getNumInitialAssignments(); i++)
				this.model
						.addInitialAssignment(readInitialAssignment(originalModel
								.getInitialAssignment(i)));
			for (i = 0; i < originalModel.getNumRules(); i++)
				this.model.addRule(readRule(originalModel.getRule(i)));
			for (i = 0; i < originalModel.getNumConstraints(); i++)
				this.model.addConstraint(readConstraint(originalModel
						.getConstraint(i)));
			for (i = 0; i < originalModel.getNumReactions(); i++) {
				org.sbml.libsbml.Reaction rOrig = originalModel.getReaction(i);
				Reaction r = readReaction(rOrig);
				this.model.addReaction(r);
				if (rOrig.isSetKineticLaw())
					r.setKineticLaw(readKineticLaw(rOrig.getKineticLaw()));
			}
			for (i = 0; i < originalModel.getNumEvents(); i++)
				this.model.addEvent(readEvent(originalModel.getEvent(i)));
			addAllSBaseChangeListenersTo(this.model);
			return this.model;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readModifierSpeciesReference(java.lang.Object)
	 */
	public ModifierSpeciesReference readModifierSpeciesReference(
			Object modifierSpeciesReference) {
		if (!(modifierSpeciesReference instanceof org.sbml.libsbml.ModifierSpeciesReference))
			throw new IllegalArgumentException("modifierSpeciesReference"
					+ error + "org.sbml.libsbml.ModifierSpeciesReference.");
		org.sbml.libsbml.ModifierSpeciesReference msr = (org.sbml.libsbml.ModifierSpeciesReference) modifierSpeciesReference;
		ModifierSpeciesReference mod = new ModifierSpeciesReference(model
				.getSpecies(msr.getSpecies()));
		copyNamedSBaseProperties(mod, msr);
		if (msr.isSetSBOTerm()) {
			mod.setSBOTerm(msr.getSBOTerm());
/*			if (!SBO.isEnzymaticCatalysis(mod.getSBOTerm())
					&& possibleEnzymes.contains(Integer.valueOf(mod
							.getSpeciesInstance().getSBOTerm())))
				mod.setSBOTerm(SBO.getEnzymaticCatalysis());*/
		}
		addAllSBaseChangeListenersTo(mod);
		return mod;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readParameter(java.lang.Object)
	 */
	public Parameter readParameter(Object parameter) {
		if (!(parameter instanceof org.sbml.libsbml.Parameter))
			throw new IllegalArgumentException("parameter" + error
					+ "org.sbml.libsbml.Parameter.");
		org.sbml.libsbml.Parameter p = (org.sbml.libsbml.Parameter) parameter;
		Parameter para = new Parameter(p.getId(), (int) p.getLevel(), (int) p
				.getVersion());
		copyNamedSBaseProperties(para, p);
		if (p.isSetValue())
			para.setValue(p.getValue());
		para.setConstant(p.getConstant());
		if (p.isSetUnits())
			para.setUnits(this.model.getUnitDefinition(p.getUnits()));
		addAllSBaseChangeListenersTo(para);
		return para;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readReaction(java.lang.Object)
	 */
	public Reaction readReaction(Object reac) {
		if (!(reac instanceof org.sbml.libsbml.Reaction))
			throw new IllegalArgumentException("reaction" + error
					+ "org.sbml.libsbml.Reaction.");
		org.sbml.libsbml.Reaction r = (org.sbml.libsbml.Reaction) reac;
		Reaction reaction = new Reaction(r.getId(), (int) r.getLevel(), (int) r
				.getVersion());
		copyNamedSBaseProperties(reaction, r);
		for (int i = 0; i < r.getNumReactants(); i++)
			reaction.addReactant(readSpeciesReference(r.getReactant(i)));
		for (int i = 0; i < r.getNumProducts(); i++)
			reaction.addProduct(readSpeciesReference(r.getProduct(i)));
		for (int i = 0; i < r.getNumModifiers(); i++)
			reaction
					.addModifier(readModifierSpeciesReference(r.getModifier(i)));
		if (r.isSetKineticLaw())
			reaction.setKineticLaw(readKineticLaw(r.getKineticLaw()));
		reaction.setFast(r.getFast());
		reaction.setReversible(r.getReversible());
		addAllSBaseChangeListenersTo(reaction);
		return reaction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readRule(java.lang.Object)
	 */
	public Rule readRule(Object rule) {
		if (!(rule instanceof org.sbml.libsbml.Rule))
			throw new IllegalArgumentException("rule" + error
					+ "org.sbml.libsbml.Rule.");
		org.sbml.libsbml.Rule libRule = (org.sbml.libsbml.Rule) rule;
		Rule r;
		if (libRule.isAlgebraic())
			r = new AlgebraicRule((int) libRule.getLevel(), (int) libRule
					.getVersion());
		else {
			Symbol s = model.findSymbol(libRule.getVariable());
			if (libRule.isAssignment())
				r = new AssignmentRule(s);
			else
				r = new RateRule(s);
		}
		copySBaseProperties(r, libRule);
		if (libRule.isSetMath())
			r.setMath(convert(libRule.getMath(), r));
		return r;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readSpecies(java.lang.Object)
	 */
	public Species readSpecies(Object species) {
		if (!(species instanceof org.sbml.libsbml.Species))
			throw new IllegalArgumentException("species" + error
					+ "org.sbml.libsbml.Species.");
		org.sbml.libsbml.Species spec = (org.sbml.libsbml.Species) species;
		Species s = new Species(spec.getId(), (int) spec.getLevel(), (int) spec
				.getVersion());
		copyNamedSBaseProperties(s, spec);
		if (spec.isSetCharge())
			s.setCharge(spec.getCharge());
		if (spec.isSetCompartment())
			s.setCompartment(getModel().getCompartment(spec.getCompartment()));
		s.setBoundaryCondition(spec.getBoundaryCondition());
		s.setConstant(spec.getConstant());
		s.setHasOnlySubstanceUnits(spec.getHasOnlySubstanceUnits());
		if (spec.isSetInitialAmount())
			s.setInitialAmount(spec.getInitialAmount());
		else if (spec.isSetInitialConcentration())
			s.setInitialConcentration(spec.getInitialConcentration());
		if (spec.isSetSubstanceUnits())
			s.setSubstanceUnits(this.model.getUnitDefinition(spec.getUnits()));
		if (spec.isSetSpeciesType())
			s.setSpeciesType(this.model.getSpeciesType(spec.getSpeciesType()));
		addAllSBaseChangeListenersTo(s);
		return s;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readSpeciesReference(java.lang.Object)
	 */
	public SpeciesReference readSpeciesReference(Object speciesReference) {
		if (!(speciesReference instanceof org.sbml.libsbml.SpeciesReference))
			throw new IllegalArgumentException("speciesReference" + error
					+ "org.sbml.libsbml.SpeciesReference.");
		org.sbml.libsbml.SpeciesReference specref = (org.sbml.libsbml.SpeciesReference) speciesReference;
		SpeciesReference spec = new SpeciesReference(model.getSpecies(specref
				.getSpecies()));
		copyNamedSBaseProperties(spec, specref);
		if (specref.isSetStoichiometryMath())
			spec.setStoichiometryMath(readStoichiometricMath(specref
					.getStoichiometryMath()));
		else
			spec.setStoichiometry(specref.getStoichiometry());
		addAllSBaseChangeListenersTo(spec);
		return spec;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readSpeciesType(java.lang.Object)
	 */
	public SpeciesType readSpeciesType(Object speciesType) {
		if (!(speciesType instanceof org.sbml.libsbml.SpeciesType))
			throw new IllegalArgumentException("speciesType" + error
					+ "org.sbml.libsbml.SpeciesType.");
		org.sbml.libsbml.SpeciesType libST = (org.sbml.libsbml.SpeciesType) speciesType;
		SpeciesType st = new SpeciesType(libST.getId(), (int) libST.getLevel(),
				(int) libST.getVersion());
		copyNamedSBaseProperties(st, libST);
		return st;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readStoichiometricMath(java.lang.Object)
	 */
	public StoichiometryMath readStoichiometricMath(Object stoichiometryMath) {
		org.sbml.libsbml.StoichiometryMath s = (org.sbml.libsbml.StoichiometryMath) stoichiometryMath;
		StoichiometryMath sm = new StoichiometryMath((int) s.getLevel(),
				(int) s.getVersion());
		copySBaseProperties(sm, s);
		if (s.isSetMath())
			sm.setMath(convert(s.getMath(), sm));
		return sm;
	}

	/**
	 * 
	 * @param trigger
	 * @return
	 */
	public Trigger readTrigger(Object trigger) {
		if (!(trigger instanceof org.sbml.libsbml.Trigger))
			throw new IllegalArgumentException("trigger" + error
					+ "org.sbml.libsbml.Trigger");
		org.sbml.libsbml.Trigger trigg = (org.sbml.libsbml.Trigger) trigger;
		Trigger trig = new Trigger((int) trigg.getLevel(), (int) trigg
				.getVersion());
		copySBaseProperties(trig, trigg);
		if (trigg.isSetMath())
			trig.setMath(convert(trigg.getMath(), trig));
		return trig;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readUnit(java.lang.Object)
	 */
	public Unit readUnit(Object unit) {
		if (!(unit instanceof org.sbml.libsbml.Unit))
			throw new IllegalArgumentException("unit" + error
					+ "org.sbml.libsbml.Unit");
		org.sbml.libsbml.Unit libUnit = (org.sbml.libsbml.Unit) unit;
		Unit u = new Unit((int) libUnit.getLevel(), (int) libUnit.getVersion());
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
		u.setOffset(libUnit.getOffset());
		return u;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readUnitDefinition(java.lang.Object)
	 */
	public UnitDefinition readUnitDefinition(Object unitDefinition) {
		if (!(unitDefinition instanceof org.sbml.libsbml.UnitDefinition))
			throw new IllegalArgumentException("unitDefinition" + error
					+ "org.sbml.libsbml.UnitDefinition");
		org.sbml.libsbml.UnitDefinition libUD = (org.sbml.libsbml.UnitDefinition) unitDefinition;
		UnitDefinition ud = new UnitDefinition(libUD.getId(), (int) libUD
				.getLevel(), (int) libUD.getVersion());
		copyNamedSBaseProperties(ud, libUD);
		for (int i = 0; i < libUD.getNumUnits(); i++)
			ud.addUnit(readUnit(libUD.getUnit(i)));
		return ud;
	}

	/**
	 * 
	 * @param sbase
	 * @param libSBase
	 */
	private void copyNamedSBaseProperties(NamedSBase sbase,
			org.sbml.libsbml.SBase libSBase) {
		copySBaseProperties(sbase, libSBase);
		if (libSBase instanceof org.sbml.libsbml.Compartment) {
			org.sbml.libsbml.Compartment c = (org.sbml.libsbml.Compartment) libSBase;
			if (c.isSetId())
				sbase.setId(c.getId());
			if (c.isSetName())
				sbase.setName(c.getName());
		} else if (libSBase instanceof org.sbml.libsbml.CompartmentType) {
			org.sbml.libsbml.CompartmentType c = (org.sbml.libsbml.CompartmentType) libSBase;
			if (c.isSetId())
				sbase.setId(c.getId());
			if (c.isSetName())
				sbase.setName(c.getName());
		} else if (libSBase instanceof org.sbml.libsbml.Event) {
			org.sbml.libsbml.Event c = (org.sbml.libsbml.Event) libSBase;
			if (c.isSetId())
				sbase.setId(c.getId());
			if (c.isSetName())
				sbase.setName(c.getName());
		} else if (libSBase instanceof org.sbml.libsbml.FunctionDefinition) {
			org.sbml.libsbml.FunctionDefinition c = (org.sbml.libsbml.FunctionDefinition) libSBase;
			if (c.isSetId())
				sbase.setId(c.getId());
			if (c.isSetName())
				sbase.setName(c.getName());
		} else if (libSBase instanceof org.sbml.libsbml.Model) {
			org.sbml.libsbml.Model c = (org.sbml.libsbml.Model) libSBase;
			if (c.isSetId())
				sbase.setId(c.getId());
			if (c.isSetName())
				sbase.setName(c.getName());
		} else if (libSBase instanceof org.sbml.libsbml.Parameter) {
			org.sbml.libsbml.Parameter c = (org.sbml.libsbml.Parameter) libSBase;
			if (c.isSetId())
				sbase.setId(c.getId());
			if (c.isSetName())
				sbase.setName(c.getName());
		} else if (libSBase instanceof org.sbml.libsbml.Reaction) {
			org.sbml.libsbml.Reaction c = (org.sbml.libsbml.Reaction) libSBase;
			if (c.isSetId())
				sbase.setId(c.getId());
			if (c.isSetName())
				sbase.setName(c.getName());
		} else if (libSBase instanceof org.sbml.libsbml.SimpleSpeciesReference) {
			org.sbml.libsbml.SimpleSpeciesReference c = (org.sbml.libsbml.SimpleSpeciesReference) libSBase;
			if (c.isSetId())
				sbase.setId(c.getId());
			if (c.isSetName())
				sbase.setName(c.getName());
		} else if (libSBase instanceof org.sbml.libsbml.Species) {
			org.sbml.libsbml.Species c = (org.sbml.libsbml.Species) libSBase;
			if (c.isSetId())
				sbase.setId(c.getId());
			if (c.isSetName())
				sbase.setName(c.getName());
		} else if (libSBase instanceof org.sbml.libsbml.SpeciesType) {
			org.sbml.libsbml.SpeciesType c = (org.sbml.libsbml.SpeciesType) libSBase;
			if (c.isSetId())
				sbase.setId(c.getId());
			if (c.isSetName())
				sbase.setName(c.getName());
		} else if (libSBase instanceof org.sbml.libsbml.UnitDefinition) {
			org.sbml.libsbml.UnitDefinition c = (org.sbml.libsbml.UnitDefinition) libSBase;
			if (c.isSetId())
				sbase.setId(c.getId());
			if (c.isSetName())
				sbase.setName(c.getName());
		}
	}

	/**
	 * 
	 * @param sbase
	 * @param libSBase
	 */
	private void copySBaseProperties(SBase sbase,
			org.sbml.libsbml.SBase libSBase) {
		if (libSBase.isSetMetaId())
			sbase.setMetaId(libSBase.getMetaId());
		if (libSBase.isSetSBOTerm())
			sbase.setSBOTerm(libSBase.getSBOTerm());
		if (libSBase.isSetNotes())
			sbase.setNotes(libSBase.getNotesString());
		for (int i = 0; i < libSBase.getNumCVTerms(); i++)
			sbase.addCVTerm(readCVTerm(libSBase.getCVTerm(i)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBMLReader#getWarnings()
	 */
	public String getWarnings() {
		org.sbml.libsbml.SBMLDocument doc = originalModel.getSBMLDocument();
		doc.checkConsistency();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < doc.getNumErrors(); i++) {
			sb.append(doc.getError(i).getMessage());
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBMLReader#getNumErrors()
	 */
	public int getNumErrors() {
		org.sbml.libsbml.SBMLDocument doc = originalModel.getSBMLDocument();
		doc.checkConsistency();
		return (int) doc.getNumErrors();
	}
}

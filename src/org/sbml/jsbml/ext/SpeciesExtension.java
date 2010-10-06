package org.sbml.jsbml.ext;

import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBaseChangedListener;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.Unit.Kind;

/**
 * 
 * @author Andreas Dr&auml;ger
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 * 
 */
@SuppressWarnings("deprecation")
public class SpeciesExtension extends Species {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 5737769017078627348L;
	/**
	 * 
	 */
	private Species species;

	/**
	 * 
	 * @param species
	 */
	public SpeciesExtension(Species species) {
		this.species = species;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.sbml.jsbml.AbstractSBase#addChangeListener(org.sbml.jsbml.
	 * SBaseChangedListener)
	 */
	@Override
	public void addChangeListener(SBaseChangedListener l) {
		species.addChangeListener(l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#appendNotes(java.lang.String)
	 */
	@Override
	public void appendNotes(String notes) {
		species.appendNotes(notes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#clone()
	 */
	@Override
	public Species clone() {
		return species.clone();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getAnnotation()
	 */
	@Override
	public Annotation getAnnotation() {
		return species.getAnnotation();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#getBoundaryCondition()
	 */
	public boolean getBoundaryCondition() {
		return species.getBoundaryCondition();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#getCharge()
	 */
	@Deprecated
	public int getCharge() {
		return species.getCharge();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#getCompartment()
	 */
	@Override
	public String getCompartment() {
		return species.getCompartment();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#getCompartmentInstance()
	 */
	@Override
	public Compartment getCompartmentInstance() {
		return species.getCompartmentInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#getConversionFactor()
	 */
	@Override
	public String getConversionFactor() {
		return species.getConversionFactor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#getConversionFactorInstance()
	 */
	@Override
	public Parameter getConversionFactorInstance() {
		return species.getConversionFactorInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getElementName()
	 */
	@Override
	public String getElementName() {
		return species.getElementName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#getHasOnlySubstanceUnits()
	 */
	@Override
	public boolean getHasOnlySubstanceUnits() {
		return species.getHasOnlySubstanceUnits();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#getInitialAmount()
	 */
	@Override
	public double getInitialAmount() {
		return species.getInitialAmount();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#getInitialConcentration()
	 */
	@Override
	public double getInitialConcentration() {
		return species.getInitialConcentration();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getLevel()
	 */
	@Override
	public int getLevel() {
		return species.getLevel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getMetaId()
	 */
	@Override
	public String getMetaId() {
		return species.getMetaId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getModel()
	 */
	@Override
	public Model getModel() {
		return species.getModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getNotesBuffer()
	 */
	@Override
	public StringBuffer getNotesBuffer() {
		return species.getNotesBuffer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getNotesString()
	 */
	@Override
	public String getNotesString() {
		return species.getNotesString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getSBMLDocument()
	 */
	@Override
	public SBMLDocument getSBMLDocument() {
		return species.getSBMLDocument();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getSBOTerm()
	 */
	@Override
	public int getSBOTerm() {
		return species.getSBOTerm();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getSBOTermID()
	 */
	@Override
	public String getSBOTermID() {
		return species.getSBOTermID();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#getSpeciesType()
	 */
	@Override
	@Deprecated
	public String getSpeciesType() {
		return species.getSpeciesType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#getSpeciesTypeInstance()
	 */
	@Override
	@Deprecated
	public SpeciesType getSpeciesTypeInstance() {
		return species.getSpeciesTypeInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#getSubstanceUnits()
	 */
	@Override
	public String getSubstanceUnits() {
		return species.getSubstanceUnits();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#getSubstanceUnitsInstance()
	 */
	@Override
	public UnitDefinition getSubstanceUnitsInstance() {
		return species.getSubstanceUnitsInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getVersion()
	 */
	@Override
	public int getVersion() {
		return species.getVersion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#hasValidAnnotation()
	 */
	@Override
	public boolean hasValidAnnotation() {
		return species.hasValidAnnotation();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.AbstractSBase#hasValidLevelVersionNamespaceCombination()
	 */
	@Override
	public boolean hasValidLevelVersionNamespaceCombination() {
		return species.hasValidLevelVersionNamespaceCombination();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#initDefaults()
	 */
	@Override
	public void initDefaults() {
		species.initDefaults();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#isBoundaryCondition()
	 */
	@Override
	public boolean isBoundaryCondition() {
		return species.getBoundaryCondition();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#isHasOnlySubstanceUnits()
	 */
	@Override
	public boolean isHasOnlySubstanceUnits() {
		return species.isHasOnlySubstanceUnits();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#isSetAnnotation()
	 */
	@Override
	public boolean isSetAnnotation() {
		return species.isSetAnnotation();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#isSetCharge()
	 */
	@Override
	public boolean isSetCharge() {
		return species.isSetCharge();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#isSetCompartment()
	 */
	@Override
	public boolean isSetCompartment() {
		return species.isSetCompartment();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#isSetCompartmentInstance()
	 */
	@Override
	public boolean isSetCompartmentInstance() {
		return species.isSetCompartmentInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#isSetConversionFactor()
	 */
	@Override
	public boolean isSetConversionFactor() {
		return species.isSetConversionFactor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#isSetConversionFactorInstance()
	 */
	@Override
	public boolean isSetConversionFactorInstance() {
		return species.isSetConversionFactorInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#isSetInitialAmount()
	 */
	@Override
	public boolean isSetInitialAmount() {
		return species.isSetInitialAmount();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#isSetInitialConcentration()
	 */
	@Override
	public boolean isSetInitialConcentration() {
		return species.isSetInitialConcentration();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#isSetMetaId()
	 */
	@Override
	public boolean isSetMetaId() {
		return species.isSetMetaId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#isSetNotes()
	 */
	@Override
	public boolean isSetNotes() {
		return species.isSetNotes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#isSetSBOTerm()
	 */
	@Override
	public boolean isSetSBOTerm() {
		return species.isSetSBOTerm();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#isSetSpeciesType()
	 */
	@Override
	public boolean isSetSpeciesType() {
		return species.isSetSpeciesType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#isSetSpeciesTypeInstance()
	 */
	@Override
	@Deprecated
	public boolean isSetSpeciesTypeInstance() {
		return species.isSetSpeciesTypeInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#isSetSubstanceUnits()
	 */
	@Override
	public boolean isSetSubstanceUnits() {
		return species.isSetSubstanceUnits();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#isSetSubstanceUnitsInstance()
	 */
	@Override
	public boolean isSetSubstanceUnitsInstance() {
		return species.isSetSubstanceUnitsInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#readAttribute(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		return species.readAttribute(attributeName, prefix, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#sbaseAdded()
	 */
	@Override
	public void sbaseAdded() {
		species.sbaseAdded();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#sbaseRemoved()
	 */
	@Override
	public void sbaseRemoved() {
		species.sbaseRemoved();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.AbstractSBase#setAnnotation(org.sbml.jsbml.Annotation)
	 */
	@Override
	public void setAnnotation(Annotation annotation) {
		species.setAnnotation(annotation);
	}

	/**
	 * 
	 * @param boundaryCondition
	 */
	public void setBoundaryCondition(boolean boundaryCondition) {
		species.setBoundaryCondition(boundaryCondition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#setCharge(int)
	 */
	@Override
	public void setCharge(int charge) {
		species.setCharge(charge);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#setCompartment(org.sbml.jsbml.Compartment)
	 */
	@Override
	public void setCompartment(Compartment compartment) {
		species.setCompartment(compartment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#setCompartment(java.lang.String)
	 */
	@Override
	public void setCompartment(String compartment) {
		species.setCompartment(compartment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#setConversionFactor(org.sbml.jsbml.Parameter)
	 */
	@Override
	public void setConversionFactor(Parameter conversionFactor) {
		species.setConversionFactor(conversionFactor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#setConversionFactor(java.lang.String)
	 */
	@Override
	public void setConversionFactor(String conversionFactorID) {
		species.setConversionFactor(conversionFactorID);
	}

	/**
	 * 
	 * @param hasOnlySubstanceUnits
	 */
	public void setHasOnlySubstanceUnits(boolean hasOnlySubstanceUnits) {
		species.setHasOnlySubstanceUnits(hasOnlySubstanceUnits);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#setInitialAmount(double)
	 */
	@Override
	public void setInitialAmount(double initialAmount) {
		species.setInitialAmount(initialAmount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#setInitialConcentration(double)
	 */
	@Override
	public void setInitialConcentration(double initialConcentration) {
		species.setInitialConcentration(initialConcentration);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#setMetaId(java.lang.String)
	 */
	@Override
	public void setMetaId(String metaid) {
		species.setMetaId(metaid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#setNotes(java.lang.String)
	 */
	@Override
	public void setNotes(String notes) {
		species.setNotes(notes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#setNotesBuffer(java.lang.StringBuffer)
	 */
	@Override
	public void setNotesBuffer(StringBuffer notes) {
		species.setNotesBuffer(notes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#setSBOTerm(int)
	 */
	@Override
	public void setSBOTerm(int term) {
		species.setSBOTerm(term);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#setSBOTerm(java.lang.String)
	 */
	@Override
	public void setSBOTerm(String sboid) {
		species.setSBOTerm(sboid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#setSpeciesType(org.sbml.jsbml.SpeciesType)
	 */
	@Override
	@Deprecated
	public void setSpeciesType(SpeciesType speciesType) {
		species.setSpeciesType(speciesType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#setSpeciesType(java.lang.String)
	 */
	@Override
	@Deprecated
	public void setSpeciesType(String speciesType) {
		species.setSpeciesType(speciesType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#setSubstanceUnits(org.sbml.jsbml.Unit.Kind)
	 */
	@Override
	public void setSubstanceUnits(Kind unitKind) {
		species.setSubstanceUnits(unitKind);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Species#setSubstanceUnits(org.sbml.jsbml.Unit)
	 */
	@Override
	public void setSubstanceUnits(Unit unit) {
		species.setSubstanceUnits(unit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.Species#setSubstanceUnits(org.sbml.jsbml.UnitDefinition)
	 */
	@Override
	public void setSubstanceUnits(UnitDefinition units) {
		species.setSubstanceUnits(units);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#stateChanged()
	 */
	@Override
	public void stateChanged() {
		species.stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#unsetAnnotation()
	 */
	@Override
	public void unsetAnnotation() {
		species.unsetAnnotation();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#unsetMetaId()
	 */
	@Override
	public void unsetMetaId() {
		species.unsetMetaId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#unsetNotes()
	 */
	@Override
	public void unsetNotes() {
		species.unsetNotes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#unsetSBOTerm()
	 */
	@Override
	public void unsetSBOTerm() {
		species.unsetSBOTerm();
	}
}

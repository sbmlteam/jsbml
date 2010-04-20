package org.sbml.jsbml.sbmlExtensions;

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
public class SpeciesExtension extends Species{

	/**
	 * 
	 */
	private Species species;
	
	/**
	 * 
	 * @param species
	 */
	public SpeciesExtension(Species species){
		this.species = species;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#clone()
	 */
	public org.sbml.jsbml.Species clone(){
		return species.clone();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#getBoundaryCondition()
	 */
	public boolean getBoundaryCondition() {
		return species.getBoundaryCondition();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#getCharge()
	 */
	public int getCharge() {
		return species.getCharge();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#getCompartment()
	 */
	public String getCompartment() {
		return species.getCompartment();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#getCompartmentInstance()
	 */
	public Compartment getCompartmentInstance() {
		return species.getCompartmentInstance();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#getConversionFactorInstance()
	 */
	public Parameter getConversionFactorInstance() {
		return species.getConversionFactorInstance();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#getConversionFactor()
	 */
	public String getConversionFactor() {
		return species.getConversionFactor();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#getHasOnlySubstanceUnits()
	 */
	public boolean getHasOnlySubstanceUnits() {
		return species.getHasOnlySubstanceUnits();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#getInitialAmount()
	 */
	public double getInitialAmount() {
		return species.getInitialAmount();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#getInitialConcentration()
	 */
	public double getInitialConcentration() {
		return species.getInitialConcentration();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#getSpeciesType()
	 */
	public String getSpeciesType() {
		return species.getSpeciesType();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#getSpeciesTypeInstance()
	 */
	public SpeciesType getSpeciesTypeInstance() {
		return species.getSpeciesTypeInstance();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#getSubstanceUnits()
	 */
	public String getSubstanceUnits() {
		return species.getSubstanceUnits();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#getSubstanceUnitsInstance()
	 */
	public UnitDefinition getSubstanceUnitsInstance() {
		return species.getSubstanceUnitsInstance();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#initDefaults()
	 */
	public void initDefaults() {
		species.initDefaults();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#isBoundaryCondition()
	 */
	public boolean isBoundaryCondition() {
		return species.getBoundaryCondition();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#isHasOnlySubstanceUnits()
	 */
	public boolean isHasOnlySubstanceUnits() {
		return species.isHasOnlySubstanceUnits();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#isSetCharge()
	 */
	public boolean isSetCharge() {
		return species.isSetCharge();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#isSetCompartmentInstance()
	 */
	public boolean isSetCompartmentInstance() {
		return species.isSetCompartmentInstance();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#isSetCompartment()
	 */
	public boolean isSetCompartment() {
		return species.isSetCompartment();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#isSetConversionFactor()
	 */
	public boolean isSetConversionFactor() {
		return species.isSetConversionFactor();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#isSetConversionFactorInstance()
	 */
	public boolean isSetConversionFactorInstance() {
		return species.isSetConversionFactorInstance();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#isSetInitialAmount()
	 */
	public boolean isSetInitialAmount() {
		return species.isSetInitialAmount();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#isSetInitialConcentration()
	 */
	public boolean isSetInitialConcentration() {
		return species.isSetInitialConcentration();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#isSetSpeciesType()
	 */
	public boolean isSetSpeciesType() {
		return species.isSetSpeciesType();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#isSetSpeciesTypeInstance()
	 */
	public boolean isSetSpeciesTypeInstance() {
		return species.isSetSpeciesTypeInstance();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#isSetSubstanceUnitsInstance()
	 */
	public boolean isSetSubstanceUnitsInstance() {
		return species.isSetSubstanceUnitsInstance();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#isSetSubstanceUnits()
	 */
	public boolean isSetSubstanceUnits() {
		return species.isSetSubstanceUnits();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		return species.readAttribute(attributeName, prefix, value);
	}

	/**
	 * 
	 * @param boundaryCondition
	 */
	public void setBoundaryCondition(boolean boundaryCondition) {
		species.setBoundaryCondition(boundaryCondition);
	}

	/**
	 * 
	 * @param charge
	 */
	public void setCharge(int charge) {
		species.setCharge(charge);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#setCompartment(org.sbml.jsbml.Compartment)
	 */
	public void setCompartment(Compartment compartment) {
		species.setCompartment(compartment);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#setCompartment(java.lang.String)
	 */
	public void setCompartment(String compartment) {
		species.setCompartment(compartment);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#setConversionFactor(org.sbml.jsbml.Parameter)
	 */
	public void setConversionFactor(Parameter conversionFactor) {
		species.setConversionFactor(conversionFactor);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#setConversionFactor(java.lang.String)
	 */
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
	 * @see org.sbml.jsbml.Species#setInitialAmount(double)
	 */
	public void setInitialAmount(double initialAmount) {
		species.setInitialAmount(initialAmount);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#setInitialConcentration(double)
	 */
	public void setInitialConcentration(double initialConcentration) {
		species.setInitialConcentration(initialConcentration);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#setSpeciesType(org.sbml.jsbml.SpeciesType)
	 */
	public void setSpeciesType(SpeciesType speciesType) {
		species.setSpeciesType(speciesType);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#setSpeciesType(java.lang.String)
	 */
	public void setSpeciesType(String speciesType) {
		species.setSpeciesType(speciesType);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#setSubstanceUnits(org.sbml.jsbml.Unit)
	 */
	public void setSubstanceUnits(Unit unit) {
		species.setSubstanceUnits(unit);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#setSubstanceUnits(org.sbml.jsbml.Unit.Kind)
	 */
	public void setSubstanceUnits(Kind unitKind) {
		species.setSubstanceUnits(unitKind);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Species#setSubstanceUnits(org.sbml.jsbml.UnitDefinition)
	 */
	public void setSubstanceUnits(UnitDefinition units) {
		species.setSubstanceUnits(units);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#addChangeListener(org.sbml.jsbml.SBaseChangedListener)
	 */
	public void addChangeListener(SBaseChangedListener l) {
		species.addChangeListener(l);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#appendNotes(java.lang.String)
	 */
	public void appendNotes(String notes) {
		species.appendNotes(notes);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getAnnotation()
	 */
	public Annotation getAnnotation() {
		return species.getAnnotation();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getElementName()
	 */
	public String getElementName() {
		return species.getElementName();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getLevel()
	 */
	public int getLevel() {
		return species.getLevel();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getMetaId()
	 */
	public String getMetaId() {
		return species.getMetaId();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getModel()
	 */
	public Model getModel() {
		return species.getModel();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getNotesBuffer()
	 */
	public StringBuffer getNotesBuffer() {
		return species.getNotesBuffer();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getNotesString()
	 */
	public String getNotesString() {
		return species.getNotesString();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getSBMLDocument()
	 */
	public SBMLDocument getSBMLDocument() {
		return species.getSBMLDocument();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getSBOTerm()
	 */
	public int getSBOTerm() {
		return species.getSBOTerm();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getSBOTermID()
	 */
	public String getSBOTermID() {
		return species.getSBOTermID();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getVersion()
	 */
	public int getVersion() {
		return species.getVersion();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#hasValidAnnotation()
	 */
	public boolean hasValidAnnotation() {
		return species.hasValidAnnotation();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#hasValidLevelVersionNamespaceCombination()
	 */
	public boolean hasValidLevelVersionNamespaceCombination() {
		return species.hasValidLevelVersionNamespaceCombination();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#isSetAnnotation()
	 */
	public boolean isSetAnnotation() {
		return species.isSetAnnotation();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#isSetMetaId()
	 */
	public boolean isSetMetaId() {
		return species.isSetMetaId();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#isSetNotes()
	 */
	public boolean isSetNotes() {
		return species.isSetNotes();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#isSetSBOTerm()
	 */
	public boolean isSetSBOTerm() {
		return species.isSetSBOTerm();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#sbaseAdded()
	 */
	public void sbaseAdded() {
		species.sbaseAdded();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#sbaseRemoved()
	 */
	public void sbaseRemoved() {
		species.sbaseRemoved();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#setAnnotation(org.sbml.jsbml.Annotation)
	 */
	public void setAnnotation(Annotation annotation) {
		species.setAnnotation(annotation);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#setMetaId(java.lang.String)
	 */
	public void setMetaId(String metaid) {
		species.setMetaId(metaid);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#setNotes(java.lang.String)
	 */
	public void setNotes(String notes) {
		species.setNotes(notes);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#setNotesBuffer(java.lang.StringBuffer)
	 */
	public void setNotesBuffer(StringBuffer notes) {
		species.setNotesBuffer(notes);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#setSBOTerm(int)
	 */
	public void setSBOTerm(int term) {
		species.setSBOTerm(term);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#setSBOTerm(java.lang.String)
	 */
	public void setSBOTerm(String sboid) {
		species.setSBOTerm(sboid);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#stateChanged()
	 */
	public void stateChanged() {
		species.stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#unsetAnnotation()
	 */
	public void unsetAnnotation() {
		species.unsetAnnotation();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#unsetMetaId()
	 */
	public void unsetMetaId() {
		species.unsetMetaId();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#unsetNotes()
	 */
	public void unsetNotes() {
		species.unsetNotes();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#unsetSBOTerm()
	 */
	public void unsetSBOTerm() {
		species.unsetSBOTerm();
	}
}

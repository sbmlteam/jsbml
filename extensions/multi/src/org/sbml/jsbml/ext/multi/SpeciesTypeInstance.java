package org.sbml.jsbml.ext.multi;

import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.CVTerm.Qualifier;
import org.sbml.jsbml.History;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.util.ValuePair;
import org.sbml.jsbml.util.filters.Filter;
import org.sbml.jsbml.xml.XMLNode;

public class SpeciesTypeInstance extends AbstractNamedSBase implements UniqueNamedSBase {

	/**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 1775590492963078468L;

  private double initialAmount;
	
	private double initialConcentration;
	
	private ListOf<SelectorReference> listOfSelectorReferences;
	
	public boolean isIdMandatory() {
		return true;
	}

	@Override
	public AbstractSBase clone() {
		// TODO
		return null;
	}

	/**
	 * Returns the initialAmount.
	 * 
	 * @return the initialAmount
	 */
	public double getInitialAmount() {
		return initialAmount;
	}

	/**
	 * Sets the initialAmount.
	 * 
	 * @param initialAmount the initialAmount to set
	 */
	public void setInitialAmount(double initialAmount) {
		this.initialAmount = initialAmount;
	}

	/**
	 * Returns the initialConcentration.
	 * 
	 * @return the initialConcentration
	 */
	public double getInitialConcentration() {
		return initialConcentration;
	}

	/**
	 * Sets the initialConcentration.
	 * 
	 * @param initialConcentration the initialConcentration to set
	 */
	public void setInitialConcentration(double initialConcentration) {
		this.initialConcentration = initialConcentration;
	}

	/**
	 * @return the listOfSelectorReferences
	 */
	public ListOf<SelectorReference> getListOfSelectorReferences() {
		return listOfSelectorReferences;
	}

	/**
	 * @param listOfSelectorReferences the listOfSelectorReferences to set
	 */
	public void addSelectorReference(SelectorReference selectorReference) {
		getListOfSelectorReferences().add(selectorReference);
	}
}

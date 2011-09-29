package org.sbml.jsbml.extension.qual;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;

public class Transition extends AbstractNamedSBase {

	private TemporisationType temporisationType;
	
	private ListOf<Input> listOfInputs;
	private ListOf<Output> listOfOutputs;
	private ListOf<FunctionTerm> listOfFunctiontTerms;
	
	@Override
	public AbstractSBase clone() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isIdMandatory() {
		return false;
	}

	/**
	 * @return the temporisationType
	 */
	public TemporisationType getTemporisationType() {
		return temporisationType;
	}

	/**
	 * @param temporisationType the temporisationType to set
	 */
	public void setTemporisationType(TemporisationType temporisationType) {
		this.temporisationType = temporisationType;
	}

	/**
	 * @return the listOfInputs
	 */
	public ListOf<Input> getListOfInputs() {
		return listOfInputs;
	}

	/**
	 * @param listOfInputs the listOfInputs to set
	 */
	public void addInput(Input input) {
		this.listOfInputs.add(input);
	}

	/**
	 * @return the listOfOutputs
	 */
	public ListOf<Output> getListOfOutputs() {
		return listOfOutputs;
	}

	/**
	 * @param listOfOutputs the listOfOutputs to set
	 */
	public void addOutput(Output output) {
		this.listOfOutputs.add(output);
	}

	/**
	 * @return the listOfFunctiontTerms
	 */
	public ListOf<FunctionTerm> getListOfFunctiontTerms() {
		return listOfFunctiontTerms;
	}

	/**
	 * @param listOfFunctiontTerms the listOfFunctiontTerms to set
	 */
	public void addFunctiontTerm(FunctionTerm functiontTerm) {
		this.listOfFunctiontTerms.add(functiontTerm);
	}

	
	
	// TODO : add all the necessary methods to manipulate the lists
	
	
}

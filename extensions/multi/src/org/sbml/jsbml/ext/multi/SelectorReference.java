package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.AbstractSBase;

public class SelectorReference extends AbstractSBase {

	/**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 8313057744716585955L;

  private boolean negation;
	
	private String selector;
	
	@Override
	public AbstractSBase clone() {
		// TODO
		return null;
	}

	
	/**
	 * Returns the negation.
	 * 
	 * @return the negation
	 */
	public boolean isNegation() {
		return negation;
	}


	/**
	 * Sets the negation.
	 * 
	 * @param negation the negation to set
	 */
	public void setNegation(boolean negation) {
		this.negation = negation;
	}


	/**
	 * Returns the selector.
	 * 
	 * @return the selector
	 */
	public String getSelector() {
		return selector;
	}


	/**
	 * Sets the selector.
	 * 
	 * @param selector the selector to set
	 */
	public void setSelector(String selector) {
		this.selector = selector;
	}


	@Override
	public String toString() {
		// TODO
		return null;
	}

}

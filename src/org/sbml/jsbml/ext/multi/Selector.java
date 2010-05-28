package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.AbstractNamedSBase;

/**
 *
 */
public class Selector extends AbstractNamedSBase {

	/**
	 * 
	 */
	public Selector() {
	}

	/**
	 * 
	 * @param selector
	 */
	public Selector(Selector selector) {
		super(selector);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public Selector clone() {
		return new Selector(this);
	}
}

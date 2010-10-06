package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.AbstractNamedSBase;

/**
 * @author
 */
public class Selector extends AbstractNamedSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 1103757869624885889L;

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

/*
 * Copyright (C) 2009 ZBIT, University of Tübingen, Andreas Dräger
 */
package org.sbml.jsbml;


/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 */
public class Trigger extends MathContainer {

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public Trigger(int level, int version) {
		super(level, version);
	}

	/**
	 * 
	 * @param trigger
	 */
	public Trigger(Trigger trigger) {
		super(trigger);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.MathElement#clone()
	 */
	// @Override
	public Trigger clone() {
		return new Trigger(this);
	}
}

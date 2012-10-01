/*
 * $Id$
 * $URL$
 * ---------------------------------------------------------------------
 * This file is part of the SysBio API library.
 *
 * Copyright (C) 2009-2012 by the University of Tuebingen, Germany.
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation. A copy of the license
 * agreement is provided in the file named "LICENSE.txt" included with
 * this software distribution and also available online as
 * <http://www.gnu.org/licenses/lgpl-3.0-standalone.html>.
 * ---------------------------------------------------------------------
 */

package de.zbit.sbml.layout;

/**
 * interface with methods that shall be used to create and return the different
 * types of entity pool nodes and connecting arcs
 * 
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev: 135 $
 */
public interface LayoutFactory {
		
	/**
	 * @return a {@link Macromolecule}
	 */
	public Macromolecule createMacromolecule();
	
	/**
	 * @return a {@link SourceSink}
	 */
	public SourceSink createSourceSink();
	
	/**
	 * @return a {@link UnspecifiedNode}
	 */
	public UnspecifiedNode createUnspecifiedNode();
	
	/**
	 * @return a {@link SimpleChemical}
	 */
	public SimpleChemical createSimpleChemical();
	
	/**
	 * @return a {@link Production}
	 */
	public Production createProduction();
	
	/**
	 * @return a {@link Consumption}
	 */
	public Consumption createConsumption();
	
	/**
	 * @return a {@link Catalysis}
	 */
	public Catalysis createCatalysis();
	
	/**
	 * @return a {@link Inhibition}
	 */
	public Inhibition createInhibition();
	
}

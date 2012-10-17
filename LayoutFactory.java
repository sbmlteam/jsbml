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
 * @version $Rev$
 */
public interface LayoutFactory<T> {
		
	/**
	 * @return a {@link Macromolecule}
	 */
	public Macromolecule<T> createMacromolecule();
	
	/**
	 * @return a {@link SourceSink}
	 */
	public SourceSink<T> createSourceSink();
	
	/**
	 * @return a {@link UnspecifiedNode}
	 */
	public UnspecifiedNode<T> createUnspecifiedNode();
	
	/**
	 * @return a {@link SimpleChemical}
	 */
	public SimpleChemical<T> createSimpleChemical();
	
	/**
	 * @return a {@link Compartment}
	 */
	public Compartment<T> createCompartment();
	
	/**
	 * @return a {@link Production}
	 */
	public Production<T> createProduction();
	
	/**
	 * @return a {@link Consumption}
	 */
	public Consumption<T> createConsumption();
	
	/**
	 * @return a {@link Catalysis}
	 */
	public Catalysis<T> createCatalysis();
	
	/**
	 * @return a {@link Inhibition}
	 */
	public Inhibition<T> createInhibition();
	
}

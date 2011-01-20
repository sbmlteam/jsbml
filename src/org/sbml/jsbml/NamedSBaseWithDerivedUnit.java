/*
 * $Id$
 * $URL$
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */
package org.sbml.jsbml;

/**
 * All objects that implement this interface can be referenced in abstract
 * syntax trees as implemented in {@link ASTNode}. The necessary requirement for
 * this is that these objects can be accessed with an identifier and are
 * associated with some value that can be evaluated to a unit. The value may be
 * defined within the class or have to be computed in a simulation.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-04-22
 */
public interface NamedSBaseWithDerivedUnit extends NamedSBase,
		SBaseWithDerivedUnit {

}

/*
 *
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2018 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml;

/**
 * This interface is used to tag all those elements
 * whose identifier must be unique within an SBML {@link Model} before SBML Level 3 Version 2. Since Level 2
 * Version 1 {@link UnitDefinition}s are allowed to have their own id-name
 * space. Therefore, {@link UnitDefinition}s should not implement this interface
 * introducing an additional check for Level 1 models.
 * 
 * <p>To identified elements whose identifier must be unique for SBML Level 3 Version 2
 * or above, we created the interface {@link UniqueSId}.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @see UniqueSId
 */
public interface UniqueNamedSBase extends NamedSBase, UniqueSId {

}

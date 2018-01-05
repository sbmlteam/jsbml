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
 * The interface {@link CallableSBase} represents an {@link SBase} that can be
 * called in mathematical expressions, i.e., {@link ASTNode}, via its
 * identifier. In terms of serialized SBML files, the identifiers of instances
 * of {@link CallableSBase} can be used in {@code ci} elements within
 * MathML expressions. All these elements can be found in the specification of
 * SBML Level 3 Version 1 Core, Table 1 on page 21. Furthermore,
 * {@link LocalParameter} also belongs to these elements.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public interface CallableSBase extends NamedSBaseWithDerivedUnit {

}

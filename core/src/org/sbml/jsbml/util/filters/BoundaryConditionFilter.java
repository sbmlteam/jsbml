/*
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
package org.sbml.jsbml.util.filters;

import org.sbml.jsbml.Species;

/**
 * This filter accepts species whose boundary condition is set to true.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class BoundaryConditionFilter implements Filter {

  /**
   * Constructs a new boundary condition filter.
   */
  public BoundaryConditionFilter() {
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.Filter#accepts(java.lang.Object)
   */
  @Override
  public boolean accepts(Object o) {
    if (o instanceof Species) {
      Species s = (Species) o;
      return s.isSetBoundaryCondition() && s.getBoundaryCondition();
    }
    return false;
  }

}

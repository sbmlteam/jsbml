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

package org.sbml.jsbml.validator.offline.constraints;

import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;

/**
 * Specific {@link ConstraintGroup} class that is linked to one {@link CHECK_CATEGORY} and so will only 
 * contain constraints linked to this category.
 * 
 * @author rodrigue
 * @since 1.3
 */
public class CategoryConstraintGroup<T> extends ConstraintGroup<T> {

  /**
   * the category of the group
   */
  private final CHECK_CATEGORY category;
  
  
  /**
   * Creates a new {@link CategoryConstraintGroup} instance linked to a specific {@link CHECK_CATEGORY}.
   * 
   * @param category the category of the group
   */
  public CategoryConstraintGroup(CHECK_CATEGORY category) {
    super();
    this.category = category;
  }


  /**
   * Returns the {@link CHECK_CATEGORY} this {@link ConstraintGroup} is linked to.
   * 
   * @return the category
   */
  public CHECK_CATEGORY getCategory() {
    return category;
  }
  
  
}

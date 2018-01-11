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
package org.sbml.jsbml.util;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;

/**
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 * @param <T> the type of element this ListOf will contain
 * @deprecated since JSBML 1.2 and the introduction of {@link ListOf#setOtherListName(String)}, this class is not necessary any more.
 */
public class ListOfWithName<T extends SBase> extends ListOf<T> {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -1585956493391213596L;

  /**
   * 
   */
  private String elementName;

  /**
   * 
   * @param elementName
   */
  public ListOfWithName(String elementName) {
    super();
    this.elementName = elementName;
  }

  /**
   * 
   * @param level
   * @param version
   * @param elementName
   */
  public ListOfWithName(int level, int version, String elementName) {
    super(level, version);
    this.elementName = elementName;
  }

  /**
   * 
   * @param listOf
   */
  public ListOfWithName(ListOfWithName<? extends SBase> listOf) {
    super(listOf);
    elementName = listOf.elementName;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ListOf#getElementName()
   */
  @Override
  public String getElementName()
  {
    return elementName;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ListOf#clone()
   */
  @Override
  public ListOf<T> clone() {
    return new ListOfWithName<T>(this);
  }
  
  

}

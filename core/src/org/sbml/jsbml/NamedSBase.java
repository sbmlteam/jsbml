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
package org.sbml.jsbml;

/**
 * Base class for all the SBML components with an id and a name (optional or
 * not) in SBML Level 3 Version 1 or lower. Since SBML Level 3 Version 2, every
 * {@link SBase} have an id an a name. This interface is kept as it is to be able to know
 * which elements could have an id or name before SBML L3V2, not every {@link SBase} will
 * be {@link NamedSBase} in jsbml. If you support L3V2 and want to manipulate id and name, prefer
 * to use the {@link SBase} interface instead of {@link NamedSBase}. 
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * @since 0.8
 */
public interface NamedSBase extends SBase {

  /**
   * Returns the id of the element if it is set, an empty string otherwise.
   * 
   * @return the id of the element if it is set, an empty string otherwise.
   */
  public String getId();

  /**
   * Returns the name of the element if it is set, an empty string otherwise.
   * 
   * @return the name of the element if it is set, an empty string otherwise.
   */
  public String getName();

  /**
   * Returns {@code true}  if the identifier of this
   * {@link NamedSBase} is required to be defined (i.e., not {@code null})
   * in the definition of SBML.
   * 
   * @return {@code true} if the identifier of this element must be set in
   *         order to create a valid SBML representation. {@code false}
   *         otherwise, i.e., if the identifier can be understood as an optional
   *         attribute.
   */
  public boolean isIdMandatory();

  /**
   * Returns {@code true} if the id is not {@code null}.
   * 
   * @return {@code true} if the id is not {@code null}.
   */
  public boolean isSetId();

  /**
   * Returns {@code true} if the name is not {@code null}.
   * 
   * @return {@code true} if the name is not {@code null}.
   */
  public boolean isSetName();

  /**
   * Sets the id value with 'id'
   * 
   * @param id the id to set
   */
  public void setId(String id);

  /**
   * Sets the name value with 'name'. If level is 1, sets automatically the id
   * to 'name' as well.
   * 
   * @param name the name to set
   */
  public void setName(String name);

  /**
   * Sets the id value to {@code null}.
   */
  public void unsetId();

  /**
   * Sets the name value to {@code null}.
   */
  public void unsetName();

}

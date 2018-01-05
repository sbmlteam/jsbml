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
 * @author Andreas Dr&auml;ger
 * @since 1.1
 */
public interface CompartmentalizedSBase extends NamedSBase {

  /**
   * Returns the value of the referenced compartment.
   *
   * @return the value of the referenced compartment or an empty {@link String}
   *         if it is not set.
   */
  public String getCompartment();

  /**
   * Returns the {@link Compartment} that is referenced by this {@link SBase}.
   * 
   * Note that the return type of this method is {@link NamedSBase} because it
   * could be possible that some subclasses link other elements than
   * {@link Compartment}.
   * 
   * @return the object in the {@link Model} that represents the linked
   *         {@link Compartment} or {@code null} if it does not exist or cannot
   *         be retrieved (e.g., if this component has not yet been linked to a
   *         {@link Model}).
   */
  public NamedSBase getCompartmentInstance();

  /**
   * @return {@code true} if for this object the compartment attribute is
   *         mandatory, {@code false} otherwise.
   */
  public boolean isCompartmentMandatory();

  /**
   * @return {@code true} if compartment attribute is set, i.e., not
   *         {@code null}, {@code false} otherwise.
   */
  public boolean isSetCompartment();

  /**
   * @return {@code true} if the {@link Compartment} which has the compartment
   *         identifier stored in this {@link CompartmentalizedSBase} as id is
   *         not {@code null} and this {@link Compartment} can be retrieved from
   *         the {@link Model} that contains this {@link CompartmentalizedSBase}.
   *         In other words, this method also returns {@code false} if the
   *         this object is not linked to any model.
   */
  public boolean isSetCompartmentInstance();

  /**
   * Sets the compartment identifiers of this {@link CompartmentalizedSBase} to
   * the id of the {@link Compartment} 'compartment'.
   * <p>
   * The compartment attribute is used to add a reference to the id of
   * the corresponding {@link Compartment} in the {@link Model}.
   * In some situations, the compartment might be optional, so that the user can
   * even specify {@link Compartment}s that are not part of the {@link Model}.
   * 
   * @param compartment the {@link Compartment} whose identifier should be referenced.
   * @return {@code true} if this operation caused any change.
   * @see #setCompartment(String)
   * @see #isCompartmentMandatory()
   */
  public boolean setCompartment(Compartment compartment);

  /**
   * Sets the compartment attribute.
   * <p>
   * The required attribute compartment is used to identify the
   * {@link Compartment} in which the {@link CompartmentalizedSBase} is located.
   * The attribute's value should be the identifier of an existing
   * {@link Compartment} object in the model.
   * 
   * @param compartmentId
   *        the identifier of the compartment to be set.
   * @return {@code true} if this operation caused any change.
   * @see #setCompartment(Compartment)
   * @see #isCompartmentMandatory()
   */
  public boolean setCompartment(String compartmentId);

  /**
   * Remove the reference to a compartment, i.e., unset the value of the
   * variable 'compartment'.
   *
   * @return {@code true} if compartment was set before, otherwise {@code false}.
   */
  public boolean unsetCompartment();

}

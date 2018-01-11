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
package org.sbml.jsbml.ext.comp;

import org.sbml.jsbml.Model;

/**
 *  Holder class for model definitions, which are {@link Model}s.
 *  
 *  <p>However, modelDefinitions are non-instantiated models
 *  that can be called by submodels within the {@link Model}
 *  class of an SBML document, or can be called externally
 *  with references stored in {@link ExternalModelDefinition}s.</p>
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class ModelDefinition extends Model {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 11908580298395050L;

  /**
   * Creates a new {@link ModelDefinition} instance.
   */
  public ModelDefinition() {
    super();
    init();
  }

  /**
   * Creates a new {@link ModelDefinition} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public ModelDefinition(int level, int version) {
    super(level, version);
    init();
  }

  /**
   * Creates a new {@link ModelDefinition} instance cloned from the given core {@link Model} instance.
   * 
   * @param model the core {@link Model} to clone
   */
  public ModelDefinition(Model model) {
    super(model);

    // just in case we are cloning a Model that has a namespace set already.
    unsetNamespace();
    init();
  }

  /**
   * Creates a new {@link ModelDefinition} instance.
   * 
   * @param id the id
   */
  public ModelDefinition(String id) {
    super(id);
    init();
  }

  /**
   * Creates a new {@link ModelDefinition} instance.
   * 
   * @param id the id
   * @param level the SBML level
   * @param version the SBML version
   */
  public ModelDefinition(String id, int level, int version) {
    super(id, level, version);
    init();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Model#clone()
   */
  @Override
  public ModelDefinition clone() {
    return new ModelDefinition(this);
  }

  /**
   * 
   */
  public void init()
  {
    setPackageVersion(-1);
    packageName = CompConstants.shortLabel;
  }

}

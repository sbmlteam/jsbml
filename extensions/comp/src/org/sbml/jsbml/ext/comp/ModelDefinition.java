/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
 *  Holder class for model definitions, which are models.
 *  However, modelDefinitions are non-instantiated models
 *  that can be called by submodels within the {@link Model}
 *  class of an SBML document, or can be called externally
 *  with references stored in {@link ExternalModelDefinition}s.
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 */
public class ModelDefinition extends Model {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 11908580298395050L;

  /**
   * 
   */
  public ModelDefinition() {
    super();
    init();
  }

  /**
   * @param level
   * @param version
   */
  public ModelDefinition(int level, int version) {
    super(level, version);
    init();
  }

  /**
   * @param model
   */
  public ModelDefinition(Model model) {
    super(model);

    // just in case we are cloning a Model that has a namespace set already.
    unsetNamespace();
    init();
  }

  /**
   * @param id
   */
  public ModelDefinition(String id) {
    super(id);
    init();
  }

  /**
   * @param id
   * @param level
   * @param version
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
    setNamespace(CompConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
    setPackageVersion(-1);
    packageName = CompConstants.shortLabel;
  }

}

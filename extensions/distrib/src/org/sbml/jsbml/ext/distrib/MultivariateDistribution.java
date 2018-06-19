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
package org.sbml.jsbml.ext.distrib;

/**
 * The {@link MultivariateDistribution} class is an abstract class with no derived classes in the current specification, but some
 * could be added in the future. 
 * 
 * <p>Most likely, it will be removed from the final version of the spec.</p>
 * 
 * @author rodrigue
 * @since 1.4
 */
public abstract class MultivariateDistribution extends Distribution {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Creates a new instance of {@link MultivariateDistribution}
   * 
   */
  public MultivariateDistribution() {
    super();
  }

  /**
   * Creates a new instance of {@link MultivariateDistribution}
   * 
   * @param level
   * @param version
   */
  public MultivariateDistribution(int level, int version) {
    super(level, version);
  }

  /**
   * Creates a new instance of {@link MultivariateDistribution}
   * 
   * @param sb
   */
  public MultivariateDistribution(MultivariateDistribution sb) {
    super(sb);
  }

  /**
   * Creates a new instance of {@link MultivariateDistribution}
   * 
   * @param id
   * @param level
   * @param version
   */
  public MultivariateDistribution(String id, int level, int version) {
    super(id, level, version);
  }

  /**
   * Creates a new instance of {@link MultivariateDistribution}
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public MultivariateDistribution(String id, String name, int level,
    int version) {
    super(id, name, level, version);
  }

  /**
   * Creates a new instance of {@link MultivariateDistribution}
   * 
   * @param id
   */
  public MultivariateDistribution(String id) {
    super(id);
  }

}

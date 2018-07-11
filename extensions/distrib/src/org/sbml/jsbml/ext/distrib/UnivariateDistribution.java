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
 * 
 * @author rodrigue
 * @since 1.4
 */
public abstract class UnivariateDistribution extends Distribution {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Creates a new instance of {@link UnivariateDistribution}
   * 
   */
  public UnivariateDistribution() {
    super();
  }

  /**
   * Creates a new instance of {@link UnivariateDistribution}
   * 
   * @param level
   * @param version
   */
  public UnivariateDistribution(int level, int version) {
    super(level, version);
  }

  /**
   * Creates a new instance of {@link UnivariateDistribution}
   * 
   * @param sb
   */
  public UnivariateDistribution(UnivariateDistribution sb) {
    super(sb);
  }

  /**
   * Creates a new instance of {@link UnivariateDistribution}
   * 
   * @param id
   * @param level
   * @param version
   */
  public UnivariateDistribution(String id, int level, int version) {
    super(id, level, version);
  }

  /**
   * Creates a new instance of {@link UnivariateDistribution}
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public UnivariateDistribution(String id, String name, int level,
    int version) {
    super(id, name, level, version);
  }

  /**
   * Creates a new instance of {@link UnivariateDistribution}
   * 
   * @param id
   */
  public UnivariateDistribution(String id) {
    super(id);
  }




}

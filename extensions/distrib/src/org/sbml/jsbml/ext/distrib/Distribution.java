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
 * The {@link Distribution} class is the abstract class from which all distributions are derived.
 * 
 * <p>They are organized
 * here in much the same way they were in UncertML, by whether they are univariate or multivariate, and whether
 * they are continuous, discrete, or categorical. In addition, the {@link ExternalDistribution} inherits from Distribution, as a
 * 'generic' distribution definition class that allows the user to define any distribution in an external ontology such as
 * ProbOnto.</p>
 * 
 * <p>When a Distribution is encountered, its parent FunctionDefinition is defined as sampling from the defined distribution,
 * and returning that sample. It may contain any number of UncertIdRef strings, each of which must correspond
 * to an UncertId defined in a DistribInput in the same function.</p>
 * 
 * @author rodrigue
 * @since 1.4
 */
public abstract class Distribution extends AbstractDistrictSBase {

  // TODO - ExponentialDistribution, Gamma, Lognormal, Chi-squared, Laplace, Cauchy, Rayleigh, Binomial, Bernoulli
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Creates a new instance of {@link Distribution}
   * 
   */
  public Distribution() {
    super();
  }

  /**
   * Creates a new instance of {@link Distribution}
   * 
   * @param level
   * @param version
   */
  public Distribution(int level, int version) {
    super(level, version);
  }

  /**
   * Creates a new instance of {@link Distribution}
   * 
   * @param sb
   */
  public Distribution(Distribution sb) {
    super(sb);
  }

  /**
   * Creates a new instance of {@link Distribution}
   * 
   * @param id
   * @param level
   * @param version
   */
  public Distribution(String id, int level, int version) {
    super(id, level, version);
  }

  /**
   * Creates a new instance of {@link Distribution}
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public Distribution(String id, String name, int level, int version) {
    super(id, name, level, version);
  }

  /**
   * Creates a new instance of {@link Distribution}
   * 
   * @param id
   */
  public Distribution(String id) {
    super(id);
  }

  @Override
  public abstract Distribution clone();
  
}

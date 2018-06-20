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
 * The {@link CategoricalUnivariateDistribution} abstract class includes distributions where the various possible sampled
 * values are each explicitly listed, along with the probability for that sampled value. 
 * 
 * <p>The sum of these probabilities
 * must therefore equal 1.0, in order to be valid. This type of distribution class is used for things such as weighted die
 * rolls, or other situations where particular values are obtained at arbitrary probabilities.
 * Because each possible sampled value is explicitly listed in an CategoricalUnivariateDistribution, it does not have
 * the optional UncertBound values that the other univariate distributions do: if a particular value is not allowed, it is
 * simply dropped from the list of options, and the probabilities of the other values are scaled accordingly.</p>
 *
 * @author rodrigue
 * @since 1.4
 */
public abstract class CategoricalUnivariateDistribution extends UnivariateDistribution {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  /**
   * Creates an CategoricalUnivariateDistribution instance 
   */
  public CategoricalUnivariateDistribution() {
    super();
    initDefaults();
  }


  /**
   * Creates a CategoricalUnivariateDistribution instance with an id.
   * 
   * @param id the identifier for the new element.
   */
  public CategoricalUnivariateDistribution(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a CategoricalUnivariateDistribution instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public CategoricalUnivariateDistribution(int level, int version) {
    this(null, null, level, version);
  }


  /**
   * Creates a CategoricalUnivariateDistribution instance with an id, level, and version.
   * 
   * @param id the identifier for this element.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public CategoricalUnivariateDistribution(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a CategoricalUnivariateDistribution instance with an id, name, level, and version.
   * 
   * @param id the identifier for this element.
   * @param name a human-readable name for this element that can be used for display purposes.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public CategoricalUnivariateDistribution(String id, String name, int level,
    int version) {
    super(id, name, level, version);
    initDefaults();
  }


  /**
   * Clone constructor
   */
  public CategoricalUnivariateDistribution(
    CategoricalUnivariateDistribution obj) {
    super(obj);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setNamespace(DistribConstants.namespaceURI);
    setPackageVersion(-1);
  }

}

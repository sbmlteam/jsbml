/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2018  jointly by the following organizations:
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
package org.sbml.jsbml.util.converters;

import org.sbml.jsbml.SBase;
import org.sbml.jsbml.util.ValuePair;

/**
 * @author Andreas Dr&auml;ger
 * @since 1.4
 * @date 05.01.2018
 */
public abstract class LevelVersionConverter {

  private ValuePair<Integer, Integer> sourceLV;
  private ValuePair<Integer, Integer> targetLV;

  /**
   * Creates a converter for which only the target level version matters.
   * 
   * @param targetLV
   */
  public LevelVersionConverter(ValuePair<Integer, Integer> targetLV) {
    this(ValuePair.of(-1, -1), targetLV);
  }

  /**
   * 
   * @param sourceLV can be (-1, -1) if irrelevant.
   * @param targetLV
   */
  public LevelVersionConverter(ValuePair<Integer, Integer> sourceLV, ValuePair<Integer, Integer> targetLV) {
    this.sourceLV = sourceLV;
    this.targetLV = targetLV;
  }

  /**
   * @return the sourceLV
   */
  public ValuePair<Integer, Integer> getSourceLV() {
    return sourceLV;
  }
  /**
   * @return the targetLV
   */
  public ValuePair<Integer, Integer> getTargetLV() {
    return targetLV;
  }

  /**
   * Test if a given instance of SBase requires an action during conversion.
   * 
   * @param sbase
   * @return
   */
  public abstract boolean needsAction(SBase sbase);

  /**
   * Executes a conversion action on the given instance of {@link SBase}.
   * 
   * @param sbase
   * @return
   */
  public abstract <T extends SBase> boolean performAction(T sbase);

}

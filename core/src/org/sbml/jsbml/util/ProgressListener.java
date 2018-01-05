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

import java.util.EventListener;

/**
 * The listener interface for receiving progress events. The class interested in
 * handling these events should implement this interface.
 * 
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public interface ProgressListener extends EventListener {

  /**
   * Reports the total number of expected steps. Note that in some use-cases
   * the total number of steps might be unknown and therefore this method might
   * be called with a very high default number, e.g., {@link Integer#MAX_VALUE}.
   * 
   * @param total
   *        the estimated total number of computation steps to be performed by
   *        this process.
   */
  public void progressStart(int total);

  /**
   * Reports the current progress to this listener, i.e., progress &lt; total.
   * Note that with every call of this method an increasing progress must be
   * reported, i.e., two subsequent calls of this method must ensure that
   * p<sub>1</sub> &lt; p<sub>2</sub> (where p<sub>1</sub> and p<sub>2</sub>
   * are the arguments passed to this method in the two subsequent calls).
   * 
   * @param progress
   *        the number of steps that have been completed already
   * @param message
   *        can be {@code null}, a more detailed message to be displayed
   *        to the user in some way.
   */
  public void progressUpdate(int progress, String message);

  /**
   * This method is called when the process is finished, irrespective of the
   * progress/total ratio.
   */
  public void progressFinish();

}

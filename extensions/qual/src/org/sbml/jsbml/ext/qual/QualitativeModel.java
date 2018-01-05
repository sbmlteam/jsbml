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
package org.sbml.jsbml.ext.qual;

import org.sbml.jsbml.Model;

/**
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @author Florian Mittag
 * @author Clemens Wrzodek
 * @since 1.0
 * @deprecated use {@link QualModelPlugin} instead.
 */
@Deprecated
public class QualitativeModel extends QualModelPlugin {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -2167310699373978921L;

  /**
   * Creates a new QualitativeModel instance.
   * 
   * @param model the core model linked to this instance 
   * @deprecated use {@link QualModelPlugin} instead.
   */
  @Deprecated
  public QualitativeModel(Model model) {
    super(model);
  }

  /**
   * Creates a new QualitativeModel instance.
   * 
   * @param qualitativeModel the instance to clone
   * @deprecated use {@link QualModelPlugin} instead.
   */
  @Deprecated
  public QualitativeModel(QualModelPlugin qualitativeModel) {
    super(qualitativeModel);
  }

}

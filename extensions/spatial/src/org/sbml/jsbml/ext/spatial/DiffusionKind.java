/*
 * $Id: DiffusionKind.java 2109 2015-01-05 04:50:45Z pdp10 $
 * $URL: svn+ssh://pdp10@svn.code.sf.net/p/jsbml/code/trunk/extensions/spatial/src/org/sbml/jsbml/ext/spatial/DiffusionKind.java $
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
package org.sbml.jsbml.ext.spatial;

/**
 * This enum type was created following the specifications defined in Spatial Package v0.90.
 * @author Piero Dalle Pezze
 * @version $Rev$
 * @since 1.0
 * @date 23 Apr 2015
 */
public enum DiffusionKind {
  /**
   * Isotropic
   */
  isotropic,
  /**
   * Tensor
   */
  tensor,
  /**
   * Anisotropic
   */
  anisotropic;
}
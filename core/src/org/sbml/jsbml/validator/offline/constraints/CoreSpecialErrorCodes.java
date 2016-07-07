/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * Copyright (C) 2009-2016 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml.validator.offline.constraints;

public interface CoreSpecialErrorCodes {

  public static final int ID_FALSE_CONSTRAINT       = -1;
  public static final int ID_TRUE_CONSTRAINT        = -6;
  public static final int ID_DO_NOT_CACHE           = -2;
  public static final int ID_GROUP                  = -3;
  public static final int ID_VALIDATE_DOCUMENT_TREE = -4;
  public static final int ID_VALIDATE_MODEL_TREE    = -5;

  public static final int ID_VALIDATE_TREE_NODE     = -7;

}

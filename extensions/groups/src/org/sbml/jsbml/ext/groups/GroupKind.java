/*
 * $Id: GroupKind.java 2109 2015-01-05 04:50:45Z andreas-draeger $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/extensions/groups/src/org/sbml/jsbml/ext/groups/GroupKind.java $
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
package org.sbml.jsbml.ext.groups;

/**
 * This is a collection of possible values for the {@code kind} attribute within
 * a {@link Group}. It has been defined in version 3 of the Groups proposal.
 * 
 * @author Clemens Wrzodek
 * @since 3.0
 * @version $Rev: 2109 $
 */
public enum GroupKind {
  /**
   * the group is a class, and its members have an <i>is-a</i> relationship to the group
   */
  classification,
  /**
   * the group is a collection of parts, and its members have a <i>part-of</i> relationship to the group
   */
  partonomy,
  /**
   * the grouping is one of convenience, without an implied relationship
   */
  collection
}

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

import java.util.Set;

import org.sbml.jsbml.validator.offline.factory.SBMLErrorCodes;

public final class L3V1CoreConstraintList extends AbstractConstraintList
implements SBMLErrorCodes {

  public static void addGeneralSBMLDocumentIds(Set<Integer> list) {
    list.add(CoreSpecialErrorCodes.ID_VALIDATE_DOCUMENT_TREE);
  }


  public static void addGeneralModelIds(Set<Integer> list) {
    addRangeToList(list, CORE_20204, CORE_20232);
    list.add(CORE_20705);
    list.add(CoreSpecialErrorCodes.ID_VALIDATE_MODEL_TREE);
  }


  public static void addGeneralFunctionDefinitionIds(Set<Integer> list) {
    list.add(CORE_20301);
    addRangeToList(list, CORE_20303, CORE_20307);
  }


  public static void addGeneralCompartmentIds(Set<Integer> list) {
    addRangeToList(list, CORE_20507, CORE_20509);
    list.add(CORE_20517);
  }


  public static void addGeneralSpeciesIds(Set<Integer> list) {
    list.add(CORE_20601);
    addRangeToList(list, CORE_20608, CORE_20610);
    list.add(CORE_20614);
    list.add(CORE_20617);
    list.add(CORE_20623);
  }


  public static void addGeneralSpeciesReferenceIds(Set<Integer> list) {
    list.add(CORE_20611);
  }

}

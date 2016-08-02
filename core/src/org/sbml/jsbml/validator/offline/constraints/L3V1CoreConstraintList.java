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

import org.sbml.jsbml.SimpleSpeciesReference;
import org.sbml.jsbml.validator.offline.factory.SBMLErrorCodes;

public final class L3V1CoreConstraintList extends AbstractConstraintList
implements SBMLErrorCodes {

  public static void addGeneralSBMLDocumentErrorCodes(Set<Integer> list) {

  }


  public static void addGeneralModelErrorCodes(Set<Integer> list) {
    addRangeToList(list, CORE_20204, CORE_20232);
    list.add(CORE_20705);
  }


  public static void addGeneralFunctionDefinitionErrorCodes(Set<Integer> list) {
    list.add(CORE_20301);
    addRangeToList(list, CORE_20303, CORE_20307);
  }


  public static void addGeneralUnitDefinitionErrorCodes(Set<Integer> list) {
    list.add(CORE_20401);
    list.add(CORE_20410);
    addRangeToList(list, CORE_20413, CORE_20421);
  }


  public static void addGeneralCompartmentErrorCodes(Set<Integer> list) {
    addRangeToList(list, CORE_20507, CORE_20509);
    list.add(CORE_20517);
  }


  public static void addGeneralSpeciesErrorCodes(Set<Integer> list) {
    list.add(CORE_20601);
    addRangeToList(list, CORE_20608, CORE_20610);
    list.add(CORE_20614);
    list.add(CORE_20617);
    list.add(CORE_20623);
  }


  public static void addGeneralParameterErrorCodes(Set<Integer> list) {
    list.add(CORE_20701);
    list.add(CORE_20706);
  }


  public static void addGeneralInitialAssignmentErrorCodes(Set<Integer> list) {
    addRangeToList(list, CORE_20801, CORE_20805);
  }


  public static void addGeneralExplicitRuleErrorCodes(Set<Integer> list) {
    addRangeToList(list, CORE_20901, CORE_20910);
  }


  public static void addGeneralConstraintErrorCodes(Set<Integer> list) {
    addRangeToList(list, CORE_21001, CORE_21009);
  }


  public static void addGeneralReactionErrorCodes(Set<Integer> list) {
    addRangeToList(list, CORE_21101, CORE_21110);

  }


  public static void addGeneralSimpleSpeciesReferenceErrorCodes(Set<Integer> list) {
    list.add(CORE_20611);
    list.add(CORE_21111);
  }

  public static void addGeneralKineticLawErrorCodes(Set<Integer> list) {
    list.add(CORE_21121);
  }
  
  public static void addGeneralEventErrorCodes(Set<Integer> list) {
    list.add(CORE_21201);
    list.add(CORE_21203);
    list.add(CORE_21204);
    list.add(CORE_21206);
    list.add(CORE_21207);
  }
  
  public static void addGeneralTriggerErrorCodes(Set<Integer> list) {
    list.add(CORE_21202);
    list.add(CORE_21209);
  }
  
  public static void addGeneralDelayErrorCodes(Set<Integer> list) {
    list.add(CORE_21210);
  }

  public static void addSizeCompartmentErrorCodes(Set<Integer> set) {
    set.add(CORE_20501);
  }

  public static void addGeneralEventAssignmentErrorCodes(Set<Integer> list) {
    addRangeToList(list, CORE_21211, CORE_21213);
  }
  
  public static void addGeneralPriorityErrorCodes(Set<Integer> list) {
    list.add(CORE_21231);
  }

  public static void addCompartmentSizeCompartmentErrorCodes(Set<Integer> set) {

  }

}

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

package org.sbml.jsbml.validator.offline.constraints;


import java.util.Set;

import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.util.ValuePair;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedMathValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.ElementOrderValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.SBOValidationConstraints;
import org.sbml.jsbml.validator.offline.constraints.helper.UniqueValidation;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.ValidationTools;;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link KineticLaw} class.
 * 
 * @author Roman
 * @since 1.2
 */
public class KineticLawConstraints extends AbstractConstraintDeclaration {

  /**
   * 
   *
   */
  public static String[] KINETIC_LAW_ELEMENTS_ORDER = 
    {"notes", "annotation", "math", "listOfParameters"}; // validation apply only for L2 so no need for listOfLocalParameters

  
  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) {
    // TODO Auto-generated method stub

  }


  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:
      
      if (level == 1)
      {
        set.add(CORE_99129);
      }
      
      if (level > 1) {
        set.add(CORE_21129);        
        set.add(CORE_21130);
        set.add(CORE_21132);        
      }
      if (level == 2) {
        set.add(CORE_21122);
        set.add(CORE_21131);
        if (version > 1) {
          set.add(CORE_21125);
          set.add(CORE_21126);
        }
      }
      if (level > 2) {
        set.add(CORE_21127);
        set.add(CORE_21128);
      }
      
      // For level and version before L3V2
      if (ValuePair.of(level, version).compareTo(3, 2) < 0) {
        set.add(CORE_21123);        
      }
      
      break;
    case IDENTIFIER_CONSISTENCY:
      set.add(CORE_10303);
      break;
    case MATHML_CONSISTENCY:
      break;
    case MODELING_PRACTICE:
      break;
    case OVERDETERMINED_MODEL:
      break;
    case SBO_CONSISTENCY:
      if ((level == 2 && version > 1) || level > 2)
      {
        set.add(CORE_10709);
      }
      break;
    case UNITS_CONSISTENCY:
      set.add(CORE_10541);
      
      if (level == 1)
      {
        set.add(CORE_99127);
        set.add(CORE_99128);
      }
      else if (level == 2)
      {
        if (version == 1)
        {
          set.add(CORE_99127);
          set.add(CORE_99128);
        }
      }
      break;
    }
    
  }


  @Override
  @SuppressWarnings("deprecation")
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<KineticLaw> func = null;

    switch (errorCode) {

      case CORE_10303:
        func = new UniqueValidation<KineticLaw, String>() {

          @Override
          public int getNumObjects(ValidationContext ctx, KineticLaw kl) {
            return kl.getLocalParameterCount();
          }


          @Override
          public String getNextObject(ValidationContext ctx, KineticLaw m, int n) {

            int num = m.getLocalParameterCount();

            if (n < num) {
              return m.getLocalParameter(n).getId();
            }

            return null;
          }
        };
        break;

      case CORE_10541:
      {
        func = new ValidationFunction<KineticLaw>() {
          
          @Override
          public boolean check(ValidationContext ctx, KineticLaw kl) {
          
            // checking that the unit are equivalent to substance / time for < L3
            // and extend / time for L3
            if (!ValidationTools.hasCorrectUnits(ctx, kl)) {
              return false;
            }
            
            return true;
          }
        };
        
        break;
      }
      
    case CORE_10709:
      return SBOValidationConstraints.isRateLaw;

    case CORE_21122:
      func = new ElementOrderValidationFunction<KineticLaw>(KINETIC_LAW_ELEMENTS_ORDER);
      break;
      
    case CORE_21123:
      func = new ValidationFunction<KineticLaw>() {
        
        @Override
        public boolean check(ValidationContext ctx, KineticLaw kl) {
          
          if (kl.isListOfLocalParametersEmpty()) {
            return false;
          }
          
          return true;
        }
      };
      break;
      
    case CORE_21125:
      func = new ValidationFunction<KineticLaw>() {

        @Override
        public boolean check(ValidationContext ctx, KineticLaw kl) {
          return !kl.isSetSubstanceUnits();
        }
      };
      break;

    case CORE_21126:
      func = new ValidationFunction<KineticLaw>() {

        @Override
        public boolean check(ValidationContext ctx, KineticLaw kl) {

          return !kl.isSetTimeUnits();
        }
      };
      break;

    case CORE_21127:
      func = new DuplicatedElementValidationFunction<KineticLaw>("listOfLocalParameters");
      break;
      
    case CORE_21128:
      func = new ValidationFunction<KineticLaw>() {
        
        @Override
        public boolean check(ValidationContext ctx, KineticLaw kl) {
          
          if (kl.isSetListOfLocalParameters() || kl.isListOfLocalParametersEmpty()) {
            UnknownElementValidationFunction<ListOf<LocalParameter>> unknownFunc = new UnknownElementValidationFunction<ListOf<LocalParameter>>();
            return unknownFunc.check(ctx, kl.getListOfLocalParameters());
          }
          
          return true;
        }
      };
      break;      

    case CORE_21129:
      func = new ValidationFunction<KineticLaw>() {
        
        @Override
        public boolean check(ValidationContext ctx, KineticLaw kl) {
          
          if (kl.isSetListOfLocalParameters() || kl.isListOfLocalParametersEmpty()) {
            UnknownAttributeValidationFunction<ListOf<LocalParameter>> unknownFunc = new UnknownAttributeValidationFunction<ListOf<LocalParameter>>();
            return unknownFunc.check(ctx, kl.getListOfLocalParameters());
          }
          
          return true;
        }
      };
      break;
      
    case CORE_21130:
      func = new DuplicatedMathValidationFunction<KineticLaw>();
      break;

    case CORE_21132:
      func = new UnknownAttributeValidationFunction<KineticLaw>();
      break;
      
    case CORE_99129:
      func = new ValidationFunction<KineticLaw>() {
        
        
        @Override
        public boolean check(ValidationContext ctx, KineticLaw kl) {

          return ValidationTools.containsMathOnlyPredefinedFunctions(kl.getMath());
        }
      };
      break;
    }
    
    

    return func;
  }
}

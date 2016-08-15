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

import java.util.HashSet;
import java.util.Set;

import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.ExplicitRule;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.validator.OverdeterminationValidator;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.SBOValidationConstraints;
import org.sbml.jsbml.validator.offline.constraints.helper.UniqueValidation;;

/**
 * @author Roman
 * @since 1.2
 * @date 04.08.2016
 */
public class ModelConstraints extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName) {
    // TODO Auto-generated method stub

  }


  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category) {

    switch (category) {
    case GENERAL_CONSISTENCY:
      set.add(CORE_20203);
      set.add(CORE_20204);

      if (level > 2 || (level == 2 && version > 1)) {
        set.add(CORE_20802);
        set.add(CORE_20803);
      }
      if (level == 3) {
        set.add(CORE_20216);
        set.add(CORE_20705);
      }
      break;
    case IDENTIFIER_CONSISTENCY:
      addRangeToSet(set, CORE_10301, CORE_10304);

      break;
    case MATHML_CONSISTENCY:
      if (level > 2)
      {
        addRangeToSet(set, CORE_20217, CORE_20221);
      }
      break;
    case MODELING_PRACTICE:
      break;
    case OVERDETERMINED_MODEL:
      set.add(CORE_10601);
      break;
    case SBO_CONSISTENCY:
      if ((level == 2 && version > 1) || level > 2)
      {
        set.add(CORE_10701);
      }
      
      break;
    case UNITS_CONSISTENCY:
      break;
    }
  }


  @Override
  @SuppressWarnings("deprecation")
  public ValidationFunction<?> getValidationFunction(int errorCode) {
    ValidationFunction<Model> func = null;

    switch (errorCode) {
    case CORE_10301:
      func = new UniqueValidation<Model, String>() {

        @Override
        public int getNumObjects(ValidationContext ctx, Model m) {

          return 1 + m.getNumFunctionDefinitions() + m.getNumCompartmentTypes()
            + m.getNumCompartments() + m.getNumSpeciesTypes()
            + m.getNumSpecies() + m.getNumReactions()
            + m.getNumSpeciesReferences() + m.getNumModifierSpeciesReferences()
            + m.getNumEvents() + m.getNumParameters();
        }


        @Override
        public String getNextObject(ValidationContext ctx, Model m, int n) {
          int offset = 0;

          if (n == 0) {
            return m.getId();
          }

          offset++;

          if (n < offset + m.getNumFunctionDefinitions()) {
            return m.getFunctionDefinition(n - offset).getId();
          }

          offset += m.getNumFunctionDefinitions();

          if (n < offset + m.getNumCompartmentTypes()) {
            return m.getCompartmentType(n - offset).getId();
          }

          offset += m.getNumCompartmentTypes();

          if (n < offset + m.getNumCompartments()) {
            return m.getCompartment(n - offset).getId();
          }

          offset += m.getNumCompartments();

          if (n < offset + m.getNumSpeciesTypes()) {
            return m.getSpeciesType(n - offset).getId();
          }

          offset += m.getNumSpeciesTypes();

          if (n < offset + m.getNumSpecies()) {
            return m.getSpecies(n - offset).getId();
          }

          offset += m.getNumSpecies();

          if (n < offset + m.getNumReactions()) {
            return m.getReaction(n - offset).getId();
          }

          offset += m.getNumReactions();

          if (n < offset + m.getNumEvents()) {
            return m.getEvent(n - offset).getId();
          }

          offset += m.getNumEvents();

          if (n < offset + m.getNumParameters()) {
            return m.getParameter(n - offset).getId();
          }

          offset += m.getNumParameters();

          for (Reaction r : m.getListOfReactions()) {
            if (n < offset + r.getReactantCount()) {
              return r.getReactant(n - offset).getId();
            }

            offset += r.getReactantCount();

            if (n < offset + r.getProductCount()) {
              return r.getProduct(n - offset).getId();
            }

            offset += r.getProductCount();

            if (n < offset + r.getModifierCount()) {
              return r.getModifier(n - offset).getId();
            }

            offset += r.getModifierCount();
          }

          return null;
        }

      };
      break;
      
    case CORE_10302:
      func = new UniqueValidation<Model, String>() {

        @Override
        public String getNextObject(ValidationContext ctx, Model m, int n) {

          return m.getUnitDefinition(n).getId();
        }


        @Override
        public int getNumObjects(ValidationContext ctx, Model m) {
          return m.getNumUnitDefinitions();
        }
      };
      break;

    case CORE_10303:
      func = new UniqueValidation<Model, String>() {

        @Override
        public int getNumObjects(ValidationContext ctx, Model m) {
          int count = 0;

          for (Reaction r : m.getListOfReactions()) {
            if (r.isSetKineticLaw())
            {
              count += r.getKineticLaw().getNumLocalParameters();
            }
            
          }

          return count;
        }


        @Override
        public String getNextObject(ValidationContext ctx, Model m, int n) {
          int offset = 0;

          for (Reaction r : m.getListOfReactions()) {
            int num = r.getKineticLaw().getNumLocalParameters();

            if (n < offset + num) {
              return r.getKineticLaw().getLocalParameter(n - offset).getId();
            }

            offset += num;
          }

          return null;
        }
      };
      break;

    case CORE_10304:
      func = new UniqueValidation<Model, String>() {

        @Override
        public int getNumObjects(ValidationContext ctx, Model m) {
          int count = 0;

          for (Rule r : m.getListOfRules()) {
            if (r instanceof ExplicitRule) {
              count++;
            }
          }

          return count;
        }


        @Override
        public String getNextObject(ValidationContext ctx, Model m, int n) {

          int count = 0;

          for (Rule r : m.getListOfRules()) {
            if (r instanceof ExplicitRule) {
              if (count == n) {
                return ((ExplicitRule) r).getVariable();
              } else {
                count++;
              }
            }
          }

          return null;
        }
      };
      break;

    case CORE_10601:
      func = new ValidationFunction<Model>() {
        
        
        @Override
        public boolean check(ValidationContext ctx, Model m) {
          OverdeterminationValidator val = new OverdeterminationValidator(m);
          
          return !val.isOverdetermined();
        }
      };
      break;
      
    case CORE_10701:
      func = new ValidationFunction<Model>() {
        
        
        @Override
        public boolean check(ValidationContext ctx, Model m) {
          
          if (ctx.getLevel() == 2 && ctx.getVersion() < 4)
          {
            return SBOValidationConstraints.isModellingFramework.check(ctx, m);
          }
          else
          {
            return SBOValidationConstraints.isInteraction.check(ctx, m);
          }
        }
      };
      break;
      
    case CORE_20203:
      func = new ValidationFunction<Model>() {

        public boolean check(ValidationContext ctx, Model m) {
          
          return (m.getCompartmentCount() > 0 || !m.isSetListOfCompartments()) ||
              (m.getCompartmentTypeCount() > 0 || !m.isSetListOfCompartmentTypes()) ||
              (m.getConstraintCount() > 0 || !m.isSetListOfConstraints()) ||
              (m.getEventCount() > 0 || !m.isSetListOfEvents()) ||
              (m.getFunctionDefinitionCount() > 0 || !m.isSetListOfFunctionDefinitions()) ||
              (m.getInitialAssignmentCount() > 0 || !m.isSetListOfInitialAssignments()) ||
              (m.getParameterCount() > 0 || !m.isSetListOfParameters()) ||
              (m.getReactionCount() > 0 || !m.isSetListOfReactions()) ||
              (m.getRuleCount() > 0 || !m.isSetListOfRules()) ||
              (m.getSpeciesCount() > 0 || !m.isSetListOfSpecies()) ||
              (m.getSpeciesTypeCount() > 0 || !m.isSetListOfSpeciesTypes()) ||
              (m.getUnitDefinitionCount() > 0 || !m.isSetListOfUnitDefinitions());
        };
      };
      break;
      
    case CORE_20204:
      func = new ValidationFunction<Model>() {

        @Override
        public boolean check(ValidationContext ctx, Model m) {
          if (m.getNumSpecies() > 0) {
            return m.getNumCompartments() > 0;
          }

          return true;
        }
      };
      break;

    case CORE_20216:
      func = new ValidationFunction<Model>() {

        @Override
        public boolean check(ValidationContext ctx, Model m) {
          if (m.isSetConversionFactor()) {
            return m.getConversionFactorInstance() != null;
          }
          return true;
        }
      };
      break;
      
    case CORE_20217:
      func = new ValidationFunction<Model>() {
        
        
        @Override
        public boolean check(ValidationContext ctx, Model m) {
          if (m.isSetTimeUnits())
          {
            UnitDefinition ud = m.getTimeUnitsInstance();
            
            return ud.isVariantOfTime() || ud.isVariantOfDimensionless();
          }
          
          return true;
        }
      };
      break;
      
    case CORE_20218:
      func = new ValidationFunction<Model>() {
        
        
        @Override
        public boolean check(ValidationContext ctx, Model m) {
          if (m.isSetVolumeUnits())
          {
            UnitDefinition ud = m.getVolumeUnitsInstance();
            
            return ud.isVariantOfVolume() || ud.isVariantOfDimensionless();
          }
          
          return true;
        }
      };
      break;
      
    case CORE_20219:
      func = new ValidationFunction<Model>() {
        
        
        @Override
        public boolean check(ValidationContext ctx, Model m) {
          if (m.isSetAreaUnits())
          {
            UnitDefinition ud = m.getAreaUnitsInstance();
            
            return ud.isVariantOfArea() || ud.isVariantOfDimensionless();
          }
          
          return true;
        }
      };
      break;
      
    case CORE_20220:
      func = new ValidationFunction<Model>() {
        
        
        @Override
        public boolean check(ValidationContext ctx, Model m) {
          if (m.isSetLengthUnits())
          {
            UnitDefinition ud = m.getLengthUnitsInstance();
            
            return ud.isVariantOfLength() || ud.isVariantOfDimensionless();
          }
          
          return true;
        }
      };
      break;
      
    case CORE_20221:
      func = new ValidationFunction<Model>() {
        
        
        @Override
        public boolean check(ValidationContext ctx, Model m) {
          if (m.isSetExtentUnits())
          {
            UnitDefinition ud = m.getExtentUnitsInstance().simplify();
            
            // Quick check for 'avogadro'
            if (ud.getNumChildren() == 1)
            {
              if (ud.getUnit(0).isAvogadro())
              {
                return true;
              }
            }
            
            return ud.isVariantOfSubstance() || ud.isVariantOfDimensionless();
          }
          
          return true;
        }
      };
      break;
    case CORE_20705:
      func = new ValidationFunction<Model>() {

        @Override
        public boolean check(ValidationContext ctx, Model m) {

          if (m.isSetConversionFactor()) {
            Parameter fac = m.getConversionFactorInstance();
            
            return fac != null && fac.isConstant();
          }

          return true;
        }
      };
      break;
      
    case CORE_20802:
      func = new UniqueValidation<Model, String>() {

        @Override
        public int getNumObjects(ValidationContext ctx, Model m) {

          return m.getNumInitialAssignments();
        }


        @Override
        public String getNextObject(ValidationContext ctx, Model m, int n) {

          return m.getInitialAssignment(n).getVariable();
        }
      };
      break;
      
    case CORE_20803:
      func = new ValidationFunction<Model>() {

        @Override
        public boolean check(ValidationContext ctx, Model m) {
          Set<String> iaIds = new HashSet<String>();

          for (InitialAssignment ia : m.getListOfInitialAssignments()) {
            iaIds.add(ia.getVariable());
          }

          for (Rule r : m.getListOfRules()) {
            String id = null;

            if (r.isRate()) {
              id = ((RateRule) r).getVariable();
            } else if (r.isAssignment()) {
              id = ((AssignmentRule) r).getVariable();
            }

            // Is the id already used by a InitialAssignment?
            if (id != null && iaIds.contains(id)) {
              return false;
            }
          }

          return true;
        }
      };
      break;
    }

    return func;
  }
}

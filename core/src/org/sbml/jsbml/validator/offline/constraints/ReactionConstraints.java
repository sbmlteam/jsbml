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

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.AssignmentCycleValidation;
import org.sbml.jsbml.validator.offline.constraints.helper.SBOValidationConstraints;
import org.sbml.jsbml.validator.offline.constraints.helper.ValidationTools;;

/**
 * @author Roman
 * @since 1.2
 * @date 04.08.2016
 */
public class ReactionConstraints extends AbstractConstraintDeclaration {

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
      set.add(CORE_21101);
      set.add(CORE_21121);
      if (level == 2) {
        set.add(CORE_21131);
        
        if (version > 1)
        {
          set.add(CORE_20906);
        }
      }
      if (level == 3) {
        set.add(CORE_20906);
        
        set.add(CORE_21106);
        set.add(CORE_21107);
        set.add(CORE_21110);
      }
      break;
    case IDENTIFIER_CONSISTENCY:
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
        set.add(CORE_10707);
      }
      break;
    case UNITS_CONSISTENCY:
      break;
    }
  }


  @Override
  @SuppressWarnings("deprecation")
  public ValidationFunction<?> getValidationFunction(int errorCode) {
    ValidationFunction<Reaction> func = null;

    switch (errorCode) {
    case CORE_10707:
      return SBOValidationConstraints.isInteraction;
      
    case CORE_20906:
      return new AssignmentCycleValidation();
      
    case CORE_21101:
      func = new ValidationFunction<Reaction>() {

        @Override
        public boolean check(ValidationContext ctx, Reaction r) {

          return r.getNumReactants() > 0 || r.getNumProducts() > 0;
        }
      };
      break;

    case CORE_21107:
      func = new ValidationFunction<Reaction>() {

        @Override
        public boolean check(ValidationContext ctx, Reaction r) {

          if (r.isSetCompartment()) {

            return r.getCompartmentInstance() != null;
          }

          return true;
        }
      };
      break;
      
    case CORE_21121:
      func = new ValidationFunction<Reaction>() {

        @Override
        public boolean check(ValidationContext ctx, Reaction r) {

          Model m = r.getModel();

          if (m != null && r.isSetKineticLaw()
            && r.getKineticLaw().isSetMath()) {
            Set<String> definedSpecies = ValidationTools.getDefinedSpecies(r);

            List<ASTNode> names = r.getKineticLaw().getMath().getListOfNodes(
              ValidationTools.FILTER_IS_NAME);
            
            for (ASTNode node : names) {
              String name = node.getName();

              // Is a Species but not in list of defined species?
              if (m.getSpecies(name) != null
                && !definedSpecies.contains(name)) {
                return false;
              }
            }
          }

          return true;
        }
      };
      break;
      
    case CORE_21131:
      func = new ValidationFunction<Reaction>() {

        @Override
        public boolean check(ValidationContext ctx, Reaction r) {

          Model m = r.getModel();

          if (m != null) {

            Set<String> definedSpecies = ValidationTools.getDefinedSpecies(r);

            for (SpeciesReference ref : r.getListOfProducts()) {

              if (!checkStoichiometryMath(m, ref, definedSpecies))
              {
                return false;
              }
            }

            for (SpeciesReference ref : r.getListOfReactants()) {
 
              if (!checkStoichiometryMath(m, ref, definedSpecies))
              {
                return false;
              }
            }

           
          }

          return true;
        }
        
        private boolean checkStoichiometryMath(Model m, SpeciesReference ref, Set<String> definedSpecies)
        {
          if (ref.isSetStoichiometryMath() && ref.getStoichiometryMath().isSetMath())
          {

            
            Queue<ASTNode> queue = new LinkedList<ASTNode>();
            queue.offer(ref.getStoichiometryMath().getMath());
            
            while (!queue.isEmpty())
            {
              ASTNode node = queue.poll();
              
              if (node.isName())
              {
                String name = (node.getName() != null) ? node.getName() : "";
                
                if (m.getSpecies(name) != null && !definedSpecies.contains(name))
                {
                  return false;
                }
              }
              
              queue.addAll(node.getListOfNodes());
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

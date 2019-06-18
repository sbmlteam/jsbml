/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2019 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The Babraham Institute, Cambridge, UK
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

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ext.spatial.Boundary;
import org.sbml.jsbml.ext.spatial.BoundaryCondition;
import org.sbml.jsbml.ext.spatial.CoordinateComponent;
import org.sbml.jsbml.ext.spatial.Geometry;
import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link BoundaryCondition} class.
 * 
 * @author Bhavye Jain
 * @since 1.5
 */
public class BoundaryConditionConstraints extends AbstractConstraintDeclaration {

  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForAttribute(java.util.Set, int, int, java.lang.String)
   */
  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) 
  {
    // TODO 

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForCheck(java.util.Set, int, int, org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY)
   */
  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch (category) {
    case GENERAL_CONSISTENCY:
      if(level >= 3){		    		
        addRangeToSet(set, SPATIAL_23601, SPATIAL_23607);
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
      break;
    case UNITS_CONSISTENCY:
      break;
    }
  }

  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context){
    ValidationFunction<BoundaryCondition> func = null;

    switch (errorCode) {

    case SPATIAL_23601:
    {
      // A BoundaryCondition object may have the optional SBML Level 3 Core attributes metaid and 
      // sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on a 
      // BoundaryCondition.

      func = new UnknownCoreAttributeValidationFunction<BoundaryCondition>();
      break;
    }

    case SPATIAL_23602:
    {
      // A BoundaryCondition object may have the optional SBML Level 3 Core subobjects for notes 
      // and annotations. No other elements from the SBML Level 3 Core namespaces are permitted 
      // on a BoundaryCondition. 

      func = new UnknownCoreElementValidationFunction<BoundaryCondition>();
      break;
    }

    case SPATIAL_23603:
    {
      // A BoundaryCondition object must have the required attributes spatial:variable and spatial:type,
      // and may have the optional attributes spatial:coordinateBoundary and spatial:boundaryDomainType.
      // No other attributes from the SBML Level 3 Spatial Processes namespaces are permitted on a
      // BoundaryCondition object.

      func = new UnknownPackageAttributeValidationFunction<BoundaryCondition>(SpatialConstants.shortLabel) {

        @Override
        public boolean check(ValidationContext ctx, BoundaryCondition bc) {

          if(!bc.isSetVariable()) {
            return false;
          }
          if(!bc.isSetType()) {
            return false;
          }

          return super.check(ctx, bc);
        }
      };
      break;
    }

    case SPATIAL_23604:
    {
      // The value of the attribute spatial:variable of a BoundaryCondition object must be the 
      // identifier of an existing Species object defined in the enclosing Model object.

      func = new ValidationFunction<BoundaryCondition>() {

        @Override
        public boolean check(ValidationContext ctx, BoundaryCondition bc) {

          if(bc.isSetVariable()) {

            Model m = bc.getModel();
            if(m.getSpecies(bc.getVariable()) != null) {
              return true;
            }

            return false;
          }

          return true;
        }
      };
      break;
    }

    case SPATIAL_23605:
    {
      // The value of the attribute spatial:type of a BoundaryCondition object must conform to 
      // the syntax of SBML data type BoundaryKind and may only take on the allowed values of
      // BoundaryKind defined in SBML; that is, the value must be one of the following: 
      // “Robin_sum”, "Robin_valueCoefficient”, “Robin_inwardNormalGradientCoefficient”, 
      // “Neumann” or “Dirichlet”.

      func = new InvalidAttributeValidationFunction<BoundaryCondition>(SpatialConstants.type);
      break;
    }

    case SPATIAL_23606:
    {
      // The value of the attribute spatial:coordinateBoundary of a BoundaryCondition object 
      // must be the identifier of an existing Boundary object defined in the enclosing Model 
      // object.

      func = new ValidationFunction<BoundaryCondition>() {
        @Override
        public boolean check(ValidationContext ctx, BoundaryCondition bc) {

          if(bc.isSetVariable()) {

            Geometry g = bc.getGeometryInstance();
            ListOf<CoordinateComponent> locc = g.getListOfCoordinateComponents();
            int n = locc.getNumChildren();

            for(int i = 0; i < n; i++) {
              CoordinateComponent cc = (CoordinateComponent) locc.getChildAt(i);
              Boundary b1 = (Boundary) cc.getChildAt(0);
              Boundary b2 = (Boundary) cc.getChildAt(1);
              if(b1.getId().compareTo(bc.getCoordinateBoundary()) == 0 || b2.getId().compareTo(bc.getCoordinateBoundary()) == 0) {
                return true;
              }
            }

            return false;
          }

          return true;
        }
      };
      break;
    }

    case SPATIAL_23607:
    {
      // The value of the attribute spatial:boundaryDomainType of a BoundaryCondition object 
      // must be the identifier of an existing DomainType object defined in the enclosing Model 
      // object.

      func = new ValidationFunction<BoundaryCondition>() {
        @Override
        public boolean check(ValidationContext ctx, BoundaryCondition bc) {

          if(bc.isSetVariable()) {

            Geometry g = bc.getGeometryInstance();
            if(g.getDomainType(bc.getBoundaryDomainType()) != null) {
              return true;
            }

            return false;
          }

          return true;
        }
      };
      break;
    }
    }

    return func;
  }

}
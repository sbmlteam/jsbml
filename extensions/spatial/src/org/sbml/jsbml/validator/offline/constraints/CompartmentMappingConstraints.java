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

import org.sbml.jsbml.Model;
import org.sbml.jsbml.ext.spatial.CompartmentMapping;
import org.sbml.jsbml.ext.spatial.DomainType;
import org.sbml.jsbml.ext.spatial.Geometry;
import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.ext.spatial.SpatialModelPlugin;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link CompartmentMapping} class.
 * 
 * @author Bhavye Jain
 * @since 1.5
 */
public class CompartmentMappingConstraints extends AbstractConstraintDeclaration {

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
        addRangeToSet(set, SPATIAL_21301, SPATIAL_21306);
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
    ValidationFunction<CompartmentMapping> func = null;

    switch (errorCode) {

    case SPATIAL_21301:
    {
      // A CompartmentMapping object may have the optional SBML Level 3 Core attributes metaid 
      // and sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on a 
      // CompartmentMapping.

      func = new UnknownCoreAttributeValidationFunction<CompartmentMapping>();
      break;
    }

    case SPATIAL_21302:
    {
      // A CompartmentMapping object may have the optional SBML Level 3 Core subobjects for notes 
      // and annotations. No other elements from the SBML Level 3 Core namespaces are permitted 
      // on a CompartmentMapping. 

      func = new UnknownCoreElementValidationFunction<CompartmentMapping>();
      break;
    }

    case SPATIAL_21303:
    {
      // A CompartmentMapping object must have the required attributes spatial:id, spatial:domainType
      // and spatial:unitSize, and may have the optional attribute spatial:name. No other attributes
      // from the SBML Level 3 Spatial Processes namespaces are permitted on a CompartmentMapping object.

      func = new UnknownPackageAttributeValidationFunction<CompartmentMapping>(SpatialConstants.shortLabel) {

        @Override
        public boolean check(ValidationContext ctx, CompartmentMapping cm) {

          if(!cm.isSetId()) {
            return false;
          }
          if(!cm.isSetDomainType()) {
            return false;
          }
          if(!cm.isSetUnitSize()) {
            return false;
          }
          return super.check(ctx, cm);
        }
      };
      break;
    }

    case SPATIAL_21304:
    {
      // The value of the attribute spatial:domainType of a CompartmentMapping object must be the 
      // identifier of an existing DomainType object defined in the enclosing Model object.

      func = new ValidationFunction<CompartmentMapping>() {

        @Override
        public boolean check(ValidationContext ctx, CompartmentMapping cm) {

          if(cm.isSetDomainType()) {
            Model m = cm.getModel();
            SpatialModelPlugin smp = (SpatialModelPlugin) m.getPlugin(SpatialConstants.shortLabel);

            if (smp.isSetGeometry()) {
              Geometry g = smp.getGeometry();
              DomainType dt = g.getDomainType(cm.getDomainType());
              if(dt == null) {
                return false;
              }
            }
          }

          return true;
        }
      };

      break;
    }

    case SPATIAL_21305:
    {
      // The attribute spatial:unitSize on a CompartmentMapping must have a value of data type double.

      func = new InvalidAttributeValidationFunction<CompartmentMapping>(SpatialConstants.unitSize);
      break;
    }

    case SPATIAL_21306:
    {
      // The attribute spatial:name on a CompartmentMapping must have a value of data type string.

      func = new ValidationFunction<CompartmentMapping>() {

        @Override
        public boolean check(ValidationContext ctx, CompartmentMapping cm) {

          // nothing to check as Java reads any kind of string.
          return true;
        }
      };

      break;
    }
    }

    return func;
  }

}
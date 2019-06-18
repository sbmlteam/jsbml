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
import org.sbml.jsbml.ext.spatial.AdvectionCoefficient;
import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link AdvectionCoefficient} class.
 * 
 * @author Bhavye Jain
 * @since 1.5
 */
public class AdvectionCoefficientConstraints extends AbstractConstraintDeclaration {
	
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
		    		addRangeToSet(set, SPATIAL_23501, SPATIAL_23505);
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
		  ValidationFunction<AdvectionCoefficient> func = null;
		  
		  switch (errorCode) {
		  	
		  	case SPATIAL_23501:
		  	{
		  		// An AdvectionCoefficient object may have the optional SBML Level 3 Core attributes metaid 
		  		// and sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on 
		  		// an AdvectionCoefficient.
		  		
		  		func = new UnknownCoreAttributeValidationFunction<AdvectionCoefficient>();
		  		break;
		  	}
		  	
		  	case SPATIAL_23502:
		  	{
		  		// An AdvectionCoefficient object may have the optional SBML Level 3 Core subobjects for notes 
		  		// and annotations. No other elements from the SBML Level 3 Core namespaces are permitted on an 
		  		// AdvectionCoefficient.
		  		
		  		func = new UnknownCoreElementValidationFunction<AdvectionCoefficient>();
		  		break;
		  	}
		  	
		  	case SPATIAL_23503:
		  	{
		  		// An AdvectionCoefficient object must have the required attributes spatial:variable and 
		  		// spatial:coordinate. No other attributes from the SBML Level 3 Spatial Processes namespaces  
		  		// are permitted on an AdvectionCoefficient object. 
		  		
		  		func = new UnknownPackageAttributeValidationFunction<AdvectionCoefficient>(SpatialConstants.shortLabel) {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, AdvectionCoefficient ac) {
		  				
		  				if(!ac.isSetVariable()) {
		  					return false;
		  				}
		  				if(!ac.isSetCoordinate()) {
		  					return false;
		  				}
		  				
		  				return super.check(ctx, ac);
		  			}
		  		};
		  		break;
		  	}
		  	
		  	case SPATIAL_23504:
		  	{
		  		// The value of the attribute spatial:variable of an AdvectionCoefficient object must be the 
		  		// identifier of an existing Species object defined in the enclosing Model object.
		  		
		  		func = new ValidationFunction<AdvectionCoefficient>() {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, AdvectionCoefficient ac) {
		  				
		  				if(ac.isSetVariable()) {
		  					
		  					Model m = ac.getModel();
		  					if(m.getSpecies(ac.getVariable()) != null) {
		  						return true;
		  					}
		  					
		  					return false;
		  				}
		  				
		  				return true;
		  			}
		  		};
		  		break;
		  	}
		  	
		  	case SPATIAL_23505:
		  	{
		  		// The value of the attribute spatial:coordinate of an AdvectionCoefficient object must conform 
		  		// to the syntax of SBML data type CoordinateKind and may only take on the allowed values of 
		  		// CoordinateKind defined in SBML; that is, the value must be one of the following: 
		  		// “cartesianX”, “cartesianY” or “cartesianZ”.
		  		
		  		func = new InvalidAttributeValidationFunction<AdvectionCoefficient>(SpatialConstants.coordinate);
		  		break;
		  	}
		  }
		  
		  return func;
	  }
	
}
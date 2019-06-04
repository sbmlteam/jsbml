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

import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.ext.spatial.SpatialReactionPlugin;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link SpatialReactionPlugin} class.
 * 
 * @author Bhavye Jain
 * @since 1.5
 */
public class SpatialReactionPluginConstraints extends AbstractConstraintDeclaration {

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
		    		addRangeToSet(set, SPATIAL_20601, SPATIAL_20602);
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
		  ValidationFunction<SpatialReactionPlugin> func = null;

		  switch (errorCode) {
		  	case SPATIAL_20601:
		  	{
		  		// A Reaction object must have the required attribute spatial:isLocal. No other attributes 
		  		// from the SBML Level 3 Spatial Processes namespaces are permitted on a Reaction object.

		  		func = new UnknownPackageAttributeValidationFunction<SpatialReactionPlugin>(SpatialConstants.shortLabel) {

		  		    @Override
		  		    public boolean check(ValidationContext ctx, SpatialReactionPlugin srp) {
		  		    	if (!srp.isSetIsLocal()) {	          
		  		          return false;
		  		        }
		  		        else {
		  		          return super.check(ctx, srp);
		  		        }
		  		    }

		  		};

		  		break;
		  	}
		  	case SPATIAL_20602:
		  	{
		  		// The attribute spatial:isLocal on a Reaction must have a value of data type boolean.

		  		func = new InvalidAttributeValidationFunction<SpatialReactionPlugin>(SpatialConstants.isLocal);

		  		break;
		  	}
		  }

		  return func;
	  }

}
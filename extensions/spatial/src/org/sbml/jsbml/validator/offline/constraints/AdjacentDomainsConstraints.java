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

import org.sbml.jsbml.ext.spatial.AdjacentDomains;
import org.sbml.jsbml.ext.spatial.Domain;
import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.ext.spatial.SpatialModelPlugin;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link AdjacentDomains} class.
 * 
 * @author Bhavye Jain
 * @since 1.5
 */
public class AdjacentDomainsConstraints extends AbstractConstraintDeclaration {
	
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
		    		addRangeToSet(set, SPATIAL_21101, SPATIAL_21106);
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
		  ValidationFunction<AdjacentDomains> func = null;
		  
		  switch (errorCode) {
		  	
		  	case SPATIAL_21101:
		  	{
		  		// An AdjacentDomains object may have the optional SBML Level 3 Core attributes metaid and 
		  		//sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on an AdjacentDomains.
		  		
		  		func = new UnknownCoreAttributeValidationFunction<AdjacentDomains>();
		  		break;
		  	}
		  	
		  	case SPATIAL_21102:
		  	{
		  		// An AdjacentDomains object may have the optional SBML Level 3 Core subobjects for notes 
		  		// and annotations. No other elements from the SBML Level 3 Core namespaces are permitted 
		  		// on an AdjacentDomains.
		  		
		  		func = new UnknownCoreElementValidationFunction<AdjacentDomains>();
		  		break;
		  	}
		  	
		  	case SPATIAL_21103:
		  	{
		  		// An AdjacentDomains object must have the required attributes spatial:id, spatial:domainOne
		  		// and spatial:domainTwo, and may have the optional attribute spatial:name. No other attributes   
		  		// from the SBML Level 3 Spatial Processes namespaces are permitted on an AdjacentDomains object.
		  		
		  		func = new UnknownPackageAttributeValidationFunction<AdjacentDomains>(SpatialConstants.shortLabel) {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, AdjacentDomains adj) {
		  				
		  				if(!adj.isSetId()) {
		  					return false;
		  				}
		  				if(!adj.isSetDomain1()) {
		  					return false;
		  				}
		  				if(!adj.isSetDomain2()) {
		  					return false;
		  				}
		  				
		  				return super.check(ctx, adj);
		  			}
		  		};
		  		
		  		break;
		  	}
		  	
		  	case SPATIAL_21104:
		  	{
		  		// The value of the attribute spatial:domainOne of an AdjacentDomains object must be the
		  		// identifier of an existing Domain object defined in the enclosing Model object.
		  		
		  		func = new ValidationFunction<AdjacentDomains>() {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, AdjacentDomains adj) {
		  				
		  				if(adj.isSetDomain1()) {
		  					SpatialModelPlugin smp = (SpatialModelPlugin) adj.getModel().getPlugin(SpatialConstants.shortLabel);
			  				Domain dom = smp.getGeometry().getDomain(adj.getDomain1());
			  				if(dom == null) {
			  					return false;
			  				}
		  				}
		  				
		  				return true;
		  			}
		  		};
		  		
		  		break;
		  	}
		  	
		  	case SPATIAL_21105:
		  	{
		  		// The value of the attribute spatial:domainTwo of an AdjacentDomains object must be the 
		  		// identifier of an existing Domain object defined in the enclosing Model object.
		  		
		  		func = new ValidationFunction<AdjacentDomains>() {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, AdjacentDomains adj) {
		  				
		  				if(adj.isSetDomain2()) {
		  					SpatialModelPlugin smp = (SpatialModelPlugin) adj.getModel().getPlugin(SpatialConstants.shortLabel);
			  				Domain dom = smp.getGeometry().getDomain(adj.getDomain2());
			  				if(dom == null) {
			  					return false;
			  				}
		  				}
		  				
		  				return true;
		  			}
		  		};
		  		break;
		  	}
		  	
		  	case SPATIAL_21106:
		  	{
		  		// The attribute spatial:name on an AdjacentDomains must have a value of data type string.
		  		
		  		func = new ValidationFunction<AdjacentDomains>() {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, AdjacentDomains adj) {
		  				
		  				// nothing to check since Java reads any kind of string.
		  				return true;
		  			}
		  		};
		  		
		  		break;
		  	}
		  }
		  
		  return func;
	  }
	
}
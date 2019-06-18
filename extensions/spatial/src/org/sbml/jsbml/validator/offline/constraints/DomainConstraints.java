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
import org.sbml.jsbml.ext.spatial.Domain;
import org.sbml.jsbml.ext.spatial.DomainType;
import org.sbml.jsbml.ext.spatial.Geometry;
import org.sbml.jsbml.ext.spatial.InteriorPoint;
import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.ext.spatial.SpatialModelPlugin;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link Domain} class.
 * 
 * @author Bhavye Jain
 * @since 1.5
 */

public class DomainConstraints extends AbstractConstraintDeclaration {
	
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
		    	if(level >= 3) {
		    		addRangeToSet(set, SPATIAL_20801, SPATIAL_20808);
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
		  ValidationFunction<Domain> func = null;
		  
		  switch (errorCode) {
		  	case SPATIAL_20801:
		  	{	
		  		// A Domain object may have the optional SBML Level 3 Core attributes metaid and sboTerm. 
		  		// No other attributes from the SBML Level 3 Core namespaces are permitted on a Domain.
		  		
		  		func = new UnknownCoreAttributeValidationFunction<Domain>();
		  		break;
		  	}
		  	
		  	case SPATIAL_20802:
		  	{
		  		// A Domain object may have the optional SBML Level 3 Core subobjects for notes and annotations.
		  		// No other elements from the SBML Level 3 Core namespaces are permitted on a Domain.
		  		
		  		func = new UnknownCoreElementValidationFunction<Domain>();
		  		break;
		  	}
		  	
		  	case SPATIAL_20803:
		  	{
		  		// A Domain object must have the required attributes spatial:id and spatial:domainType and 
		  		// may have the optional attribute spatial:name. No other attributes from the SBML Level 3
		  		// Spatial Processes namespaces are permitted on a Domain object. 
		  		
		  		func = new UnknownPackageAttributeValidationFunction<Domain>(SpatialConstants.shortLabel) {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, Domain dom) {
		  				
		  				if(!dom.isSetId()) {
		  					return false;
		  				}		  				
		  				if(!dom.isSetDomainType()) {
		  					return false;
		  				}
		  				
		  				return super.check(ctx, dom);
		  			}
		  		};
		  		break;
		  	}
		  	
		  	case SPATIAL_20804:
		  	{
		  		// A Domain object may contain one and only one instance of the ListOfInteriorPoints element.
		  		// No other elements from the SBML Level 3 Spatial Processes namespaces are permitted on a 
		  		// Domain object.
		  		
		  		func = new ValidationFunction<Domain>() {
		  			@Override
		  	        public boolean check(ValidationContext ctx, Domain dom) {
		  				
		  				boolean onlyOneList = new DuplicatedElementValidationFunction<Domain>(SpatialConstants.listOfInteriorPoints).check(ctx, dom);
		  				boolean noOtherElements = new UnknownPackageElementValidationFunction<Domain>(SpatialConstants.shortLabel).check(ctx, dom);
		  				return (onlyOneList && noOtherElements);
		  			}
		  		};
		  		
		  		break;
		  	}
		  	
		  	case SPATIAL_20805:
		  	{
		  		// The value of the attribute spatial:domainType of a Domain object must be the identifier 
		  		// of an existing DomainType object defined in the enclosing Model object. 
		  		
		  		func = new ValidationFunction<Domain>() {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, Domain dom) {
		  				
		  				if(dom.isSetDomainType()) {		  					
		  					Model m = dom.getModel();
		  					SpatialModelPlugin smp = (SpatialModelPlugin) m.getPlugin(SpatialConstants.shortLabel);		  					
		  					if(smp.isSetGeometry()) {		  						
		  						Geometry g = smp.getGeometry();
		  						DomainType dt = g.getDomainType(dom.getDomainType());
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
		  	
		  	case SPATIAL_20806:
		  	{
		  		// The attribute spatial:name on a Domain must have a value of data type string.
		  		
		  		func = new ValidationFunction<Domain>() {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, Domain dom) {
		  				
		  				// nothing to check as java read any kind of String
		  		        
		  		        return true;
		  			}
		  		};
		  	}
		  	
		  	case SPATIAL_20807:
		  	{
		  		// Apart from the general notes and annotations subobjects permitted on all SBML objects,
		  		// a ListOfInteriorPoints container object may only contain InteriorPoint objects.
		  		
		  		func = new ValidationFunction<Domain>() {

		  		    @Override
		  		    public boolean check(ValidationContext ctx, Domain dom) {
		  		    	
		  		        if (dom.isSetListOfInteriorPoints()) {		  		        	
		  		        	return new UnknownElementValidationFunction<ListOf<InteriorPoint>>().check(ctx, dom.getListOfInteriorPoints());
		  		        }
		  		        
		  		    return true;
		  		    }
		  		};
		  		
		  		break;
		  	}
		  	
		  	case SPATIAL_20808:
		  	{
		  		// A ListOfInteriorPoints object may have the optional SBML Level 3 Core attributes metaid 
		  		// and sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted 
		  		// on a ListOfInteriorPoints object. 
		  		
		  		func = new ValidationFunction<Domain>() {

		  		    @Override
		  		    public boolean check(ValidationContext ctx, Domain dom) {        
		  	
		  		        if (dom.isSetListOfInteriorPoints()) {		  		        	
		  		        	return new UnknownAttributeValidationFunction<ListOf<InteriorPoint>>().check(ctx, dom.getListOfInteriorPoints());
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
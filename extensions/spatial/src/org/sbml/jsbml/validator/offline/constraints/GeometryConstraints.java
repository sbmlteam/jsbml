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
import org.sbml.jsbml.ext.spatial.AdjacentDomains;
import org.sbml.jsbml.ext.spatial.CoordinateComponent;
import org.sbml.jsbml.ext.spatial.Domain;
import org.sbml.jsbml.ext.spatial.DomainType;
import org.sbml.jsbml.ext.spatial.Geometry;
import org.sbml.jsbml.ext.spatial.GeometryDefinition;
import org.sbml.jsbml.ext.spatial.SampledField;
import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link Geometry} class.
 * 
 * @author Bhavye Jain
 * @since 1.5
 */
public class GeometryConstraints extends AbstractConstraintDeclaration {
	
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
		    		addRangeToSet(set, SPATIAL_23701, SPATIAL_23717);
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
		  ValidationFunction<Geometry> func = null;
		  
		  switch (errorCode) {
		  
		  	case SPATIAL_23701:
		  	{
		  		// A Geometry object may have the optional SBML Level 3 Core attributes metaid and sboTerm. 
		  		// No other attributes from the SBML Level 3 Core namespaces are permitted on a Geometry.
		  		
		  		func = new UnknownCoreAttributeValidationFunction<Geometry>();
		  		break;
		  	}
		  	
		  	case SPATIAL_23702:
		  	{
		  		// A Geometry object may have the optional SBML Level 3 Core subobjects for notes and annotations.
		  		// No other elements from the SBML Level 3 Core namespaces are permitted on a Geometry.
		  		
		  		func = new UnknownCoreElementValidationFunction<Geometry>();
		  		break;
		  	}
		  	
		  	case SPATIAL_23703:
		  	{
		  		// A Geometry object must have the required attribute spatial:coordinateSystem, and may 
		  		// have the optional attribute spatial:id. No other attributes from the SBML Level 3 Spatial
		  		// Processes namespaces are permitted on a Geometry object.
		  		
		  		func = new UnknownPackageAttributeValidationFunction<Geometry>(SpatialConstants.shortLabel) {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, Geometry geom) {
		  				
		  				if(!geom.isSetCoordinateSystem()) {
		  					return false;
		  				}
		  				
		  				return super.check(ctx, geom);
		  			}
		  		};
		  		break;
		  	}
		  	
		  	case SPATIAL_23704:
		  	{
		  		// A Geometry object may contain one and only one instance of each of the ListOfCoordinateComponents,
		  		// ListOfDomainTypes, ListOfDomains, ListOfAdjacentDomains, ListOfGeometryDefinitions and
		  		// ListOfSampledFields elements. No other elements from the SBML Level 3 Spatial Processes
		  		// namespaces are permitted on a Geometry object.
		  		
		  		func = new ValidationFunction<Geometry>() {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, Geometry geom) {
		  				
		  				boolean check = true;
		  				if(check && geom.isSetListOfCoordinateComponents()) {
		  					check &= new DuplicatedElementValidationFunction<Geometry>(SpatialConstants.listOfCoordinateComponents).check(ctx, geom);
		  				}
		  				if(check && geom.isSetListOfDomainTypes()) {
		  					check &= new DuplicatedElementValidationFunction<Geometry>(SpatialConstants.listOfDomainTypes).check(ctx, geom);
		  				}
		  				if(check && geom.isSetListOfDomains()) {
		  					check &= new DuplicatedElementValidationFunction<Geometry>(SpatialConstants.listOfDomains).check(ctx, geom);
		  				}
		  				if(check && geom.isSetListOfAdjacentDomains()) {
		  					check &= new DuplicatedElementValidationFunction<Geometry>(SpatialConstants.listOfAdjacentDomains).check(ctx, geom);
		  				}
		  				if(check && geom.isSetListOfGeometryDefinitions()) {
		  					check &= new DuplicatedElementValidationFunction<Geometry>(SpatialConstants.listOfGeometryDefinitions).check(ctx, geom);
		  				}
		  				if(check && geom.isSetListOfSampledFields()) {
		  					check &= new DuplicatedElementValidationFunction<Geometry>(SpatialConstants.listOfSampledFields).check(ctx, geom);
		  				}
		  				if(check) {
		  					check &= new UnknownPackageElementValidationFunction<Geometry>(SpatialConstants.shortLabel).check(ctx, geom);
		  				}		  				
		  				
		  				return check;
		  			}
		  		};
		  		break;
		  	}
		  	
		  	case SPATIAL_23705:
		  	{
		  		// The value of the attribute spatial:coordinateSystem of a Geometry object must conform 
		  		// to the syntax of SBML data type GeometryKind and may only take on the allowed values of 
		  		// GeometryKind defined in SBML; that is, the value must be one of the following: “cartesian”.
		  		
		  		func = new InvalidAttributeValidationFunction<Geometry>(SpatialConstants.coordinateSystem);
		  		break;
		  	}
		  	
		  	case SPATIAL_23706:
		  	{
		  		// Apart from the general notes and annotations subobjects permitted on all SBML objects, 
		  		// a ListOfCoordinateComponents container object may only contain CoordinateComponent objects.		  		
		  		
		  		func = new ValidationFunction<Geometry>() {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, Geometry geom) {
		  				
		  				if(geom.isSetListOfCoordinateComponents()) {
		  					return new UnknownElementValidationFunction<ListOf<CoordinateComponent>>().check(ctx, geom.getListOfCoordinateComponents());
		  				}
		  				return true;
		  			}
		  		};
		  		break;
		  	}
		  	
		  	case SPATIAL_23707:
		  	{
		  		// Apart from the general notes and annotations subobjects permitted on all SBML objects, 
		  		// a ListOfDomainTypes container object may only contain DomainType objects.
		  		
		  		func = new ValidationFunction<Geometry>() {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, Geometry geom) {
		  				
		  				if(geom.isSetListOfDomainTypes()) {
		  					return new UnknownElementValidationFunction<ListOf<DomainType>>().check(ctx, geom.getListOfDomainTypes());
		  				}
		  				return true;
		  			}
		  		};
		  		break;
		  	}
		  	
		  	case SPATIAL_23708:
		  	{
		  		// Apart from the general notes and annotations subobjects permitted on all SBML objects, a 
		  		// ListOfDomains container object may only contain Domain objects. 
		  		
		  		func = new ValidationFunction<Geometry>() {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, Geometry geom) {
		  				
		  				if(geom.isSetListOfDomains()) {
		  					return new UnknownElementValidationFunction<ListOf<Domain>>().check(ctx, geom.getListOfDomains());
		  				}
		  				return true;
		  			}
		  		};
		  		break;
		  	}
		  	
		  	case SPATIAL_23709:
		  	{
		  		// Apart from the general notes and annotations subobjects permitted on all SBML objects, a 
		  		// ListOfAdjacentDomains container object may only contain AdjacentDomains objects.
		  		
		  		func = new ValidationFunction<Geometry>() {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, Geometry geom) {
		  				
		  				if(geom.isSetListOfAdjacentDomains()) {
		  					return new UnknownElementValidationFunction<ListOf<AdjacentDomains>>().check(ctx, geom.getListOfAdjacentDomains());
		  				}
		  				return true;
		  			}
		  		};
		  		break;
		  	}
		  	
		  	case SPATIAL_23710:
		  	{
		  		// Apart from the general notes and annotations subobjects permitted on all SBML objects, a 
		  		// ListOfGeometryDefinitions container object may only contain GeometryDefinition objects.
		  		
		  		func = new ValidationFunction<Geometry>() {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, Geometry geom) {
		  				
		  				if(geom.isSetListOfGeometryDefinitions()) {
		  					return new UnknownElementValidationFunction<ListOf<GeometryDefinition>>().check(ctx, geom.getListOfGeometryDefinitions());
		  				}
		  				return true;
		  			}
		  		};
		  		break;
		  	}
		  	
		  	case SPATIAL_23711:
		  	{
		  		// Apart from the general notes and annotations subobjects permitted on all SBML objects, a 
		  		// ListOfSampledFields container object may only contain SampledField objects. 
		  		
		  		func = new ValidationFunction<Geometry>() {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, Geometry geom) {
		  				
		  				if(geom.isSetListOfSampledFields()) {
		  					return new UnknownElementValidationFunction<ListOf<SampledField>>().check(ctx, geom.getListOfSampledFields());
		  				}
		  				return true;
		  			}
		  		};
		  		break;
		  	}
		  	
		  	case SPATIAL_23712:
		  	{
		  		// A ListOfCoordinateComponents object may have the optional SBML Level 3 Core attributes 
		  		// metaid and sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted 
		  		// on a ListOfCoordinateComponents object.
		  		
		  		func = new ValidationFunction<Geometry>() {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, Geometry geom) {
		  				
		  				if(geom.isSetListOfCoordinateComponents()) {
		  					return new UnknownAttributeValidationFunction<ListOf<CoordinateComponent>>().check(ctx, geom.getListOfCoordinateComponents());		  						
		  				}
		  				return true;
		  			}
		  		};
		  		break;
		  	}
		  	
		  	case SPATIAL_23713:
		  	{
		  		// A ListOfDomainTypes object may have the optional SBML Level 3 Core attributes metaid 
		  		// and sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on 
		  		// a ListOfDomainTypes object.
		  		
		  		func = new ValidationFunction<Geometry>() {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, Geometry geom) {
		  				
		  				if(geom.isSetListOfDomainTypes()) {
		  					return new UnknownAttributeValidationFunction<ListOf<DomainType>>().check(ctx, geom.getListOfDomainTypes());		  						
		  				}
		  				return true;
		  			}
		  		};
		  		break;
		  	}
		  	
		  	case SPATIAL_23714:
		  	{
		  		// A ListOfDomains object may have the optional SBML Level 3 Core attributes metaid and 
		  		// sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on a 
		  		// ListOfDomains object.
		  		
		  		func = new ValidationFunction<Geometry>() {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, Geometry geom) {
		  				
		  				if(geom.isSetListOfDomains()) {
		  					return new UnknownAttributeValidationFunction<ListOf<Domain>>().check(ctx, geom.getListOfDomains());		  						
		  				}
		  				return true;
		  			}
		  		};
		  		break;
		  	}
		  	
		  	case SPATIAL_23715:
		  	{
		  		// A ListOfAdjacentDomains object may have the optional SBML Level 3 Core attributes metaid 
		  		// and sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on a 
		  		// ListOfAdjacentDomains object.
		  		
		  		func = new ValidationFunction<Geometry>() {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, Geometry geom) {
		  				
		  				if(geom.isSetListOfAdjacentDomains()) {
		  					return new UnknownAttributeValidationFunction<ListOf<AdjacentDomains>>().check(ctx, geom.getListOfAdjacentDomains());
		  				}
		  				return true;
		  			}
		  		};
		  		break;
		  	}
		  	
		  	case SPATIAL_23716:
		  	{
		  		// A ListOfGeometryDefinitions object may have the optional SBML Level 3 Core attributes metaid 
		  		// and sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on a  
		  		// ListOfGeometryDefinitions object.
		  		
		  		func = new ValidationFunction<Geometry>() {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, Geometry geom) {
		  				
		  				if(geom.isSetListOfGeometryDefinitions()) {
		  					return new UnknownAttributeValidationFunction<ListOf<GeometryDefinition>>().check(ctx, geom.getListOfGeometryDefinitions());		  						
		  				}
		  				return true;
		  			}
		  		};
		  		break;
		  	}
		  	
		  	case SPATIAL_23717:
		  	{
		  		// A ListOfSampledFields object may have the optional SBML Level 3 Core attributes metaid 
		  		// and sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on 
		  		// a ListOfSampledFields object.
		  		
		  		func = new ValidationFunction<Geometry>() {
		  			
		  			@Override
		  			public boolean check(ValidationContext ctx, Geometry geom) {
		  				
		  				if(geom.isSetListOfSampledFields()) {
		  					return new UnknownAttributeValidationFunction<ListOf<SampledField>>().check(ctx, geom.getListOfSampledFields());
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
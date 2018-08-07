package org.sbml.jsbml.validator.offline.constraints;

import java.util.HashSet;
import java.util.Set;

import org.sbml.jsbml.ext.qual.Output;
import org.sbml.jsbml.ext.qual.OutputTransitionEffect;
import org.sbml.jsbml.ext.qual.QualConstants;
import org.sbml.jsbml.ext.qual.QualitativeSpecies;
import org.sbml.jsbml.ext.qual.Transition;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeAbstractValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link Output} class.
 * 
 * @author Nicolas Rodriguez, Lisa Falk
 * @since 1.3
 */
public class OutputConstraints extends AbstractConstraintDeclaration {

  protected static final String SET_20311 = "SET_20311";

  @Override
	public void addErrorCodesForAttribute(Set<Integer> set, int level, int version, String attributeName,
			ValidationContext context) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addErrorCodesForCheck(Set<Integer> set, int level, int version, CHECK_CATEGORY category,
			ValidationContext context) {
		switch (category) {
		case GENERAL_CONSISTENCY:
		  if (level >= 3) {
		    addRangeToSet(set, QUAL_20601, QUAL_20610);
		  }

		case MODELING_PRACTICE:
		  if (level >= 3) {
		    set.add(QUAL_20311);
		  }

			break;
		case SBO_CONSISTENCY:
			break;
		case IDENTIFIER_CONSISTENCY:
			break;
		case MATHML_CONSISTENCY:
			break;
		case OVERDETERMINED_MODEL:
			break;
		case UNITS_CONSISTENCY:

		}
	}

	@Override
	public ValidationFunction<?> getValidationFunction(int errorCode, final ValidationContext context) {
	  ValidationFunction<Output> func = null;

	  switch (errorCode) {

	    case QUAL_20311: {
          // A QualitativeSpecies that is referenced by an Output with the
          // qual:transitionEffect attribute
          // set to 'assignmentLevel' cannot be referenced by any other Output with the
          // same transitionEffect throughout the set of transitions for the containing model.
	      
	      func = new AbstractValidationFunction<Output>() {
	        @Override
	        public boolean check(ValidationContext ctx, Output o) {
	          
	          if (o.isSetTransitionEffect() && o.isSetQualitativeSpecies() && 
	              o.getTransitionEffect() == OutputTransitionEffect.assignmentLevel) 
	          {
	            // TODO - write that in a global list
	            @SuppressWarnings("unchecked")
	            Set<String> qlWithAssignmentLevel = (Set<String>) context.getHashMap().get(SET_20311);
	            
	            if (qlWithAssignmentLevel == null) {
	              qlWithAssignmentLevel = new HashSet<String>();
	              context.getHashMap().put(SET_20311, qlWithAssignmentLevel);
	            }
	            if (qlWithAssignmentLevel.contains(o.getQualitativeSpecies())) {
	              // creating a proper error message
	              ValidationConstraint.logError(ctx, QUAL_20311, o, ((Transition) o.getParent().getParent()).getId(), o.getQualitativeSpecies());
	              return false;	              
	            } else {
	              qlWithAssignmentLevel.add(o.getQualitativeSpecies());
	            }
	            
	          }
	          return true;  
	        }
	      };
	      
	      break;
	    }
		  
		case QUAL_20601:
			// May have the optional attributes metaid and sboTerm.
			// No other namespaces are permitted.
				
	     func = new AbstractValidationFunction<Output>() {
	        @Override
	        public boolean check(ValidationContext ctx, Output o) {
	          return new UnknownCoreAttributeAbstractValidationFunction<Output>().check(ctx, o, QUAL_20601);
	        }
	      };
	      break;
	  

		case QUAL_20602:
			// May have the optional subobjects for notes and annotations.
			// No other namespaces are permitted.
			
			func = new UnknownCoreElementValidationFunction<Output>();
			break;

		case QUAL_20603:
			// must have the attributes qual:qualitativeSpecies, qual:transitionEffect
			// may have the attributes qual:id, qual:name, qual:outputLevel
			// No other attributes are permitted.
			
			func = new UnknownPackageAttributeValidationFunction<Output>(QualConstants.shortLabel) {

				@Override
				public boolean check(ValidationContext ctx, Output o) {
					if (!o.isSetQualitativeSpecies() || !o.isSetTransitionEffect()) {
						return false;
					}
					return super.check(ctx, o);
				}
			};
			break;

		case QUAL_20604:
			// The attribute qual:name in Output must be of the data type string.
			
			func = new InvalidAttributeValidationFunction<Output>(QualConstants.name);
			break;


		case QUAL_20605:
			// The value of the attribute qual:transitionEffect must conform the
			// syntax of the SBML data type transitionOutputEffect and may only take on the
			// allowed values of transitionOutputEffect defined in SBML; that is, the value
			// must be one of the following: 'production' or 'assignmentLevel'.
		  
		  func = new InvalidAttributeValidationFunction<Output>(QualConstants.transitionEffect);
      break;

		case QUAL_20606:
			// The attribute qual:outputLevel must be of the data type integer.
			
			func = new InvalidAttributeValidationFunction<Output>(QualConstants.outputLevel);
			break;

		case QUAL_20607:
			// The value of the attribute qual:qualitativeSpecies must be the identifier
			// of an existing QualitativeSpecies object defined in the enclosing Model
		  
      func = new AbstractValidationFunction<Output>() {
        @Override
        public boolean check(ValidationContext ctx, Output o) {

          if (o.isSetQualitativeSpecies() && o.getQualitativeSpeciesInstance() == null) {
            ValidationConstraint.logError(ctx, QUAL_20607, o, o.getQualitativeSpecies());
            return false;
          }
          return true;  
        }
      };
      break;

		case QUAL_20608:
			// The QualitativeSpecies referred to by the attribute qual:qualitativeSpecies
			// in an Output object must have the value of its qual:constant attribute set to
			// 'false'.
		  
		  func = new AbstractValidationFunction<Output>() {
		    @Override
		    public boolean check(ValidationContext ctx, Output o) {
		      if (o.isSetQualitativeSpecies() && o.getQualitativeSpeciesInstance() != null && o.getQualitativeSpeciesInstance().isSetConstant() && o.getQualitativeSpeciesInstance().getConstant()) {
		        ValidationConstraint.logError(ctx, QUAL_20608, o, o.getId(), Boolean.toString(o.getQualitativeSpeciesInstance().getConstant()));
		        return false;
		      }
		      return true;
		    }
		  };
		  break;

		case QUAL_20609:
			// When the value of the attribute qual:transitionEffect of a Output object is
			// set to the value 'production' the attribute qual:outputLevel for that
			// particular Output object must have a value set.
		  
      func = new AbstractValidationFunction<Output>() {
        @Override
        public boolean check(ValidationContext ctx, Output o) {
          if (o.isSetTransitionEffect() && o.getTransitionEffect() == OutputTransitionEffect.production && !o.isSetOutputLevel()) {
            ValidationConstraint.logError(ctx, QUAL_20609, o, o.getTransitionEffect().name(), Boolean.toString(o.isSetOutputLevel()));
            return false;
          }
          return true;
        }
      };
      break;

		case QUAL_20610:
			// The attribute qual:outputLevel must not be negative

			func = new AbstractValidationFunction<Output>() {
			  @Override
			  public boolean check(ValidationContext ctx, Output o) {
			    if (o.isSetOutputLevel() && o.getOutputLevel() < 0) {
			      ValidationConstraint.logError(ctx, QUAL_20610, o, o.getId(), Integer.toString(o.getOutputLevel()));
			      return false;
			    }
			    return true;
				}
			};
			break;
		}

		return func;
	}
}

/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2019 jointly by the following organizations:
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

import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.comp.CompConstants;
import org.sbml.jsbml.ext.comp.CompModelPlugin;
import org.sbml.jsbml.ext.comp.CompSBMLDocumentPlugin;
import org.sbml.jsbml.ext.comp.ExternalModelDefinition;
import org.sbml.jsbml.ext.comp.ModelDefinition;
import org.sbml.jsbml.ext.comp.Port;
import org.sbml.jsbml.ext.comp.SBaseRef;
import org.sbml.jsbml.ext.comp.Submodel;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.SyntaxChecker;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link SBaseRef} class.
 *  
 * @author rodrigue
 * @since 1.5
 */
public class SBaseRefConstraints extends AbstractConstraintDeclaration {

  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForAttribute(java.util.Set, int, int, java.lang.String)
   */
  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) 
  {
    // no specific attribute, so nothing to do.

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForCheck(java.util.Set, int, int, org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY)
   */
  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:

      addRangeToSet(set, COMP_20701, COMP_20714 );
      
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
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<SBaseRef> func = null;

    switch (errorCode) {

    case COMP_20701:
    {
      // The value of a portRef attribute on an SBaseRef object must be the identifier of a Port object in the Model referenced by that SBaseRef
      func = new ValidationFunction<SBaseRef>() {

        @Override
        public boolean check(ValidationContext ctx, SBaseRef sbaseRef) {
          
          if (sbaseRef.isSetPortRef()) {
            Model m = getParentModel(sbaseRef);
            
            if (m.isSetPlugin(CompConstants.shortLabel)) {
              CompModelPlugin compModel = (CompModelPlugin) m.getPlugin(CompConstants.shortLabel);
              
              Port p = compModel.getPort(sbaseRef.getPortRef());
                  
              return p != null;              
            }
          }
          
          return true;
        }
      };
      break;
    }
    case COMP_20702:
    {
      // The value of a idRef attribute on an SBaseRef object must be the identifier of a SBase object in the Model referenced by that SBaseRef
      func = new ValidationFunction<SBaseRef>() {

        @Override
        public boolean check(ValidationContext ctx, SBaseRef sbaseRef) {
          
          if (sbaseRef.isSetIdRef()) {
            Model m = getParentModel(sbaseRef);
              
            SBase sb = m.getSBaseById(sbaseRef.getIdRef());
                  
            return sb != null;              
          }
          
          return true;
        }
      };
      break;
    }
    case COMP_20703:
    {
      // The value of a unitRef attribute on an SBaseRef object must be the identifier of a UnitDefinition object in the Model referenced by that SBaseRef
      func = new ValidationFunction<SBaseRef>() {

        @Override
        public boolean check(ValidationContext ctx, SBaseRef sbaseRef) {
          
          if (sbaseRef.isSetUnitRef()) {
            Model m = getParentModel(sbaseRef);
              
            SBase sb = m.getUnitDefinition(sbaseRef.getUnitRef());
                  
            return sb != null;              
          }
          
          return true;
        }
      };
      break;
    }
    case COMP_20704:
    {
      // The value of a metaIdRef attribute on an SBaseRef object must be the identifier of a SBase object in the Model referenced by that SBaseRef
      func = new ValidationFunction<SBaseRef>() {

        @Override
        public boolean check(ValidationContext ctx, SBaseRef sbaseRef) {
          
          if (sbaseRef.isSetMetaIdRef()) {
            Model m = getParentModel(sbaseRef);
            SBMLDocument doc = m.getSBMLDocument();
            
            SBase sb = doc != null ? doc.getElementByMetaId(sbaseRef.getMetaIdRef()) : null;
                  
            return sb != null;              
          }
          
          return true;
        }
      };
      break;
    }
    case COMP_20705:
    {
      // If an SBaseRef object contains an SBaseRef child, the parent SBaseRef must point to a
      // Submodel object, or a Port that itself points to a Submodel object.
      func = new ValidationFunction<SBaseRef>() {

        @Override
        public boolean check(ValidationContext ctx, SBaseRef sbaseRef) {
          
          if (sbaseRef.isSetSBaseRef()) {
            Model m = getParentModel(sbaseRef);
            SBMLDocument doc = m.getSBMLDocument();
            
            SBase sb = doc != null ? doc.getElementByMetaId(sbaseRef.getMetaIdRef()) : null;
                  
            return sb != null;              
          }
          
          return true;
        }
      };
      break;
    }
    case COMP_20706: // portRef syntax
    {
      func = new ValidationFunction<SBaseRef>() {

        @Override
        public boolean check(ValidationContext ctx, SBaseRef sbaseRef) {
          
          if (sbaseRef.isSetPortRef()) {
                  
            return SyntaxChecker.isValidId(sbaseRef.getPortRef(), ctx.getLevel(), ctx.getVersion());
          }
          
          return true;
        }
      };
      break;
    }
    case COMP_20707: // idRef syntax
    {
      func = new ValidationFunction<SBaseRef>() {

        @Override
        public boolean check(ValidationContext ctx, SBaseRef sbaseRef) {
          
          if (sbaseRef.isSetIdRef()) {
                  
            return SyntaxChecker.isValidId(sbaseRef.getIdRef(), ctx.getLevel(), ctx.getVersion());
          }
          
          return true;
        }
      };
      break;
    }
    case COMP_20708: // unitRef syntax
    {
      func = new ValidationFunction<SBaseRef>() {

        @Override
        public boolean check(ValidationContext ctx, SBaseRef sbaseRef) {
          
          if (sbaseRef.isSetUnitRef()) {
                  
            return SyntaxChecker.isValidId(sbaseRef.getUnitRef(), ctx.getLevel(), ctx.getVersion());
          }
          
          return true;
        }
      };
      break;
    }
    case COMP_20709: // metaidRef syntax
    {
      func = new ValidationFunction<SBaseRef>() {

        @Override
        public boolean check(ValidationContext ctx, SBaseRef sbaseRef) {
          
          if (sbaseRef.isSetMetaIdRef()) {
                  
            return SyntaxChecker.isValidMetaId(sbaseRef.getMetaIdRef());
          }
          
          return true;
        }
      };
      break;
    }
    case COMP_20710: // no other elements than notes, annotation and one SBaseRef 
    {
      // TODO
      func = new UnknownCoreElementValidationFunction<SBaseRef>(); // TODO add unknownPackage element and nb sbaseRef elements
      break;
    }
    // 20711 ?? The 'sbaseRef' spelling of an SBaseRef child of an SBaseRef object is considered deprecated, and 'sBaseRef' should be used instead.
    case COMP_20712: // must always have a value for one of the attributes portRef, idRef, unitRef, or metaIdRef. 
    {
      func = new ValidationFunction<SBaseRef>() {

        @Override
        public boolean check(ValidationContext ctx, SBaseRef ref) {
          
          return ref.isSetPortRef() || ref.isSetIdRef() || ref.isSetUnitRef() || ref.isSetMetaIdRef();
        }
      };
      break;
    }
    case COMP_20713: // can only have a value for one of the attributes portRef, idRef, unitRef, or metaIdRef. 
    {
      // No other attributes from the HierarchicalModel Composition namespace are permitted on an SBaseRef object
      func = new ValidationFunction<SBaseRef>() {

        @Override
        public boolean check(ValidationContext ctx, SBaseRef replBy) {
          int nbDefined = 0;

          // can only have a value for one and only one of the following attributes: portRef, idRef, unitRef, or metaIdRef.          
          if (replBy.isSetPortRef()) {
            nbDefined++;
          }
          if (replBy.isSetIdRef()) {
            nbDefined++;
          }
          if (replBy.isSetUnitRef()) {
            nbDefined++;
          }
          if (replBy.isSetMetaIdRef()) {
            nbDefined++;
          }
          
          // No other attributes from the HierarchicalModel Composition namespace are permitted on a ReplacedBy object.
          boolean otherAttributes = new UnknownPackageAttributeValidationFunction<SBaseRef>(CompConstants.shortLabel).check(ctx, replBy);
          
          // TODO - custom error messages for each different issues?
          
          return nbDefined == 1 && otherAttributes;
        }
      };      
      break;
    }
    // case COMP_20714: // Implemented in the CompSBMLDocumentPlugin
    // {
      // 20714 - Any one SBML object may only be referenced in one of the following ways: referenced by a single
      // Port object; referenced by a single Deletion object; referenced by a single ReplacedElement;
      // be the parent of a single ReplacedBy child; be referenced by one ormore ReplacedBy objects;
      // or be referenced by one or more ReplacedElement objects all using the deletion attribute.
      // Essentially, once an object has been referenced in one of these ways it cannot be referenced
      // again.
    // }
    }

    return func;
  }
  
  /**
   * Gets the model associated with a given {@link SBaseRef}.
   * 
   * @param sbaseRef
   * @return
   */
  private Model getParentModel(SBaseRef sbaseRef) {

    // TODO - make a cache of the referenced Model in the SBaseRef so that we don't have to do the whole recursion each time
    // TODO - check for submodel only if there is no parent SBaseRef
    SBase parentM = getModelOrSubmodel(sbaseRef);
    
    Submodel subModel = null;
    Model m = sbaseRef.getModel(); // TODO - if we encounter a submodel, select it;
    
    
    if (sbaseRef.getParent() != null && sbaseRef.getParent() instanceof SBaseRef) {
      SBaseRef parent = (SBaseRef) sbaseRef.getParent();
      
      if (parent.isSetPortRef() && m.isSetPlugin(CompConstants.shortLabel)) {
        CompModelPlugin compModel = (CompModelPlugin) m.getPlugin(CompConstants.shortLabel);
        
        Port p = compModel.getPort(parent.getPortRef());
        
        if (p == null) {
          m = null;
        } else if (p.isSetIdRef()) {
          Submodel subM = compModel.getSubmodel(p.getIdRef());
          
          if (subM != null) {
            String mStr = subM.getModelRef();
            
            SBMLDocument doc = m.getSBMLDocument();
            
            if (doc != null && doc.isSetPlugin(CompConstants.shortLabel)) {
              CompSBMLDocumentPlugin compDoc = (CompSBMLDocumentPlugin) doc.getPlugin(CompConstants.shortLabel);
              
              ModelDefinition md = compDoc.getModelDefinition(mStr);
              ExternalModelDefinition emd = compDoc.getExternalModelDefinition(mStr);
              
              if (md != null) {
                m = md;
              } else if (emd != null) {
                // TODO - load external model definition
              } else {
                m = null;
              }
            } else {
              m = null;
            }
          } else {
            m = null;
          }
        } else {
          m = null;
        }
      } else if (parent.isSetIdRef() && m.isSetPlugin(CompConstants.shortLabel)) {
        CompModelPlugin compModel = (CompModelPlugin) m.getPlugin(CompConstants.shortLabel);
        Submodel subM = compModel.getSubmodel(parent.getIdRef());
        
        if (subM != null) {
          String mStr = subM.getModelRef();
          
          SBMLDocument doc = m.getSBMLDocument();
          
          if (doc != null && doc.isSetPlugin(CompConstants.shortLabel)) {
            CompSBMLDocumentPlugin compDoc = (CompSBMLDocumentPlugin) doc.getPlugin(CompConstants.shortLabel);
            
            ModelDefinition md = compDoc.getModelDefinition(mStr);
            ExternalModelDefinition emd = compDoc.getExternalModelDefinition(mStr);
            
            if (md != null) {
              m = md;
            } else if (emd != null) {
              // TODO - load external model definition
            } else {
              m = null;
            }
          } else {
            m = null;
          }
        } else {
          m = null;
        }
      }
    }
    
    return m;
  }

  /**
   * 
   * @param sbase
   * @return
   */
  public SBase getModelOrSubmodel(SBase sbase) {
    if (sbase instanceof Model) {
      return sbase;
    } else if (sbase instanceof Submodel) {
      return sbase;
    }
    return sbase.getParentSBMLObject() != null ? getModelOrSubmodel(sbase.getParentSBMLObject()) : null;
  }

}

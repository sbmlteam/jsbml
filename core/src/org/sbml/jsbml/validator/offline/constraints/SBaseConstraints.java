/*
 * $IdSBaseConstraints.java 16:06:11 roman $
 * $URLSBaseConstraints.java $
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2016  jointly by the following organizations: 
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

import java.util.HashSet;
import java.util.Set;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UniqueValidation;
import org.sbml.jsbml.validator.offline.constraints.helper.ValidationTools;


/**
 * @author Roman
 * @since 1.2
 * @date 06.08.2016
 */
public class SBaseConstraints extends AbstractConstraintDeclaration {

  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForCheck(java.util.Set, int, int, org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY)
   */
  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category) {
    
    switch (category) {
    case GENERAL_CONSISTENCY:
      break;
    case IDENTIFIER_CONSISTENCY:
      set.add(CORE_10307);
      set.add(CORE_10308);
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


  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForAttribute(java.util.Set, int, int, java.lang.String)
   */
  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName) {
    

  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#getValidationFunction(int)
   */
  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode) {
    ValidationFunction<SBase> func = null;
    
    switch (errorCode) {
    case CORE_10307:
      func = new ValidationFunction<SBase>() {
        
        
        @SuppressWarnings("unchecked")
        @Override
        public boolean check(ValidationContext ctx, SBase sb) {
          
          Object o = ctx.getHashMap().get(ValidationTools.KEY_META_ID_SET);
          Set<String> metaIds;
          
          if (o != null && o instanceof Set)
          {
            metaIds = (Set<String>)o;
          }
          else
          {
            metaIds = new HashSet<String>();
            ctx.getHashMap().put(ValidationTools.KEY_META_ID_SET, metaIds);
          }
          
          
          return metaIds.add(sb.getMetaId());
        }
      };
      
    case CORE_10308:
      func = new ValidationFunction<SBase>() {
        
        
        @Override
        public boolean check(ValidationContext ctx, SBase sb) {
          // TODO Auto-generated method stub
          return ValidationContext.isSboTerm(sb.getSBOTermID());
        }
      };
    }
    return func;
  }

}

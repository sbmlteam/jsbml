/*
 * $Id$
 * $URL$
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2014  jointly by the following organizations: 
 * 1. The University of Tuebingen, Germany 
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK 
 * 3. The California Institute of Technology, Pasadena, CA, USA 
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 6. The University of Utah, Salt Lake City, UT, USA
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation. A copy of the license agreement is provided 
 * in the file named "LICENSE.txt" included with this software distribution 
 * and also available online as <http://sbml.org/Software/JSBML/License>. 
 * ---------------------------------------------------------------------------- 
 */
package org.sbml.jsbml.ext.arrays.validator.constraints;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.arrays.Index;
import org.sbml.jsbml.ext.arrays.util.ArraysMath;


/**
 * This checks that a given {@link Index} object references a valid referenced attribute and that
 * it doesn't go out of bounds.
 * 
 * @author Leandro Watanabe
 * @version $Rev$
 * @since 1.0
 * @date Jun 17, 2014
 */
public class IndexAttributesCheck extends ArraysConstraint {

  Index index;

  public IndexAttributesCheck(Model model, Index index)
  {
    super(model);
    this.index = index;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.arrays.constraints.ArraysConstraint#check()
   */
  @Override
  public void check() {
    if(model == null || index == null) {
      return;
    }
    
      String refAttribute = index.getReferencedAttribute();
      if(refAttribute == null) {
        String msg = "Index objects should have a value for attribute arrays:referencedAttribute but "
          + index.toString() + " doesn't have a value.";
        logIndexMissingAttribute(msg);
        return;
      }
      
      SBase parent = index.getParentSBMLObject().getParentSBMLObject();
      String refValue = parent.writeXMLAttributes().get(refAttribute);
      
      // TODO: split at ':'
      if(refValue == null) {
        String msg = "Index objects attribute arrays:referencedAttribute should reference a valid attribute but "
            + index.toString() + " references an attribute that doesn't exist.";
        logInvalidRefAttribute(msg);
        return;
      }
      
      SBase refSBase = model.findNamedSBase(refValue);
      
      if(refSBase == null) {
        String msg = "Index objects should reference a valid SIdRef "
            + index.toString() + " references an unknown SBase.";
        logInvalidRefAttribute(msg);
        return;
      }

      ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) refSBase.getExtension(ArraysConstants.shortLabel);
      if(!index.isSetArrayDimension()) {
        String msg = "Index objects should have attribute arrays:arrayDimension set but "
            + refSBase.toString() + " doesn't.";
        
        logIndexMissingAttribute(msg);
      }
      int arrayDimension = index.getArrayDimension();
      Dimension dim = arraysSBasePlugin.getDimensionByArrayDimension(arrayDimension);
      
      if(dim == null) {
        String msg = "The SIdRef of an Index object should have arrays:arrayDimension of same value of the Index object but "
            + refSBase.toString() + " doesn't have a Dimension object with arrays:arrayDimension " + arrayDimension + ".";
        logDimensionMismatch(msg);
      }
      
      boolean isStaticComp = ArraysMath.isStaticallyComputable(model, index);
      
      if(!isStaticComp) {
        String msg = "Index math should be statically computable, meaning that it should only contain dimension ids or constant values but "
            + refSBase.toString() + " is not statically computable.";
        logNotStaticComp(msg);
      }
      
      //TODO: needs to check all bounds
      boolean isBounded = ArraysMath.evaluateIndexBounds(model, index);
      
      if(!isBounded) {
        String msg = "Index math should not go out-of-bounds but index for "
            + refSBase.toString() + " does.";
        logNotBounded(msg);

      }
  }


  private void logDimensionMismatch(String shortMsg) {
    int code = 20305, severity = 2, category = 0, line = -1, column = -1;

    String pkg = "arrays";
    String msg = "The object referenced by the SIdRef indicated by the arrays:referencedAttribute attribute"+
                 "must include an arrays:arrayDimension matching the arrays:arrayDimension for"+
                 "the Index. (Reference: SBML Level 3 Package Specification for Arrays, Version 1, Section 3.4 on 37 page 8.)";


    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }
  
  private void logNotBounded(String shortMsg) {
    int code = 20308, severity = 2, category = 0, line = -1, column = -1;

    String pkg = "arrays";
    String msg = "For each possible value of each Dimension id (i.e., 0 to size-1 of the Dimension referred to)"+
                 "that appears in the MathML math element, there should be no array out-of-bounds problems. Namely," +
                 "it must evaluate to a non-negative integer that is less than the size of the corresponding"
                 + " Dimension for the object being indexed.";



    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }
  
  private void logNotStaticComp(String shortMsg) {
    int code = 20307, severity = 2, category = 0, line = -1, column = -1;

    String pkg = "arrays";
    String msg = "The MathML math element in an Index object must be statically computable. In other words,"+
                 "any identifier that appears in the math element, other than a Dimension id for the object with" +
                 "this Index , must be a constant. (Reference: SBML Level 3 Package Specification for Arrays,"+
                 "Version 1, Section 3.4 on page 8.)";



    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }
  
  private void logIndexMissingAttribute(String shortMsg) {
    int code = 20302, severity = 2, category = 0, line = -1, column = -1;

    String pkg = "arrays";
    String msg = "An Index object must have a value for the attributes arrays:arrayDimension," +
                 "and arrays:referencedAttribute . (Reference: SBML Level 3 Package Specification"+
                 "for Arrays, Version 1, Section 3.4 on page 8.)";


    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }

  private void logInvalidRefAttribute(String shortMsg) {
    int code = 20303, severity = 2, category = 0, line = -1, column = -1;

    String pkg = "arrays";
    String msg = "The value of the arrays:referencedAttribute attribute, if set on a given Index object, must"+
                 "be an existing attribute of type SIdRef with a value that references a valid SId. (Reference:"+
                 "SBML Level 3 Package Specification for Arrays, Version 1, Section 3.4 on page 8.)";


    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }

}

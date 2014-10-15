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

import java.util.HashMap;
import java.util.Map;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Index;


/**
 * This checks if the {@link Index} objects of a given {@link SBase} have valid array dimension. 
 * 
 * @author Leandro Watanabe
 * @version $Rev$
 * @since 1.0
 * @date Jun 18, 2014
 */
public class IndexArrayDimCheck extends ArraysConstraint {

  private final SBase sbase;
  
  /**
   * Constructs a new IndexArrayDimCheck with a model and sbase
   * 
   * @param model
   * @param sbase
   */
  public IndexArrayDimCheck(Model model, SBase sbase)
  {
    super(model);
    this.sbase = sbase;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.arrays.constraints.ArraysConstraint#check()
   */
  @Override
  public void check() {
    
    if(model == null || sbase == null) {
      return;
    }
    
    ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);

    Map<String, Integer> attributeToMaxDim = new HashMap<String, Integer>();
    
    if(arraysSBasePlugin == null || arraysSBasePlugin.getIndexCount() == 0) {
      return;
    }

    
    for(Index index : arraysSBasePlugin.getListOfIndices())
    {
      if(!attributeToMaxDim.containsKey(index.getReferencedAttribute())
          || index.getArrayDimension() > attributeToMaxDim.get(index.getReferencedAttribute())) {
        attributeToMaxDim.put(index.getReferencedAttribute(), index.getArrayDimension());
      }
    }
    
    for(String attribute : attributeToMaxDim.keySet())
    {
      int max = attributeToMaxDim.get(attribute);
      
      boolean[] isSetArrayDimAt = new boolean[max+1];
      
      for(Index index : arraysSBasePlugin.getListOfIndices())
      {
        
        if(!index.getReferencedAttribute().equals(attribute))
        {
          continue;
        }
        
        int arrayDim = index.getArrayDimension();

        if(!isSetArrayDimAt[arrayDim]) {
          isSetArrayDimAt[arrayDim] = true;
        }
        else 
        {
          String shortMsg = "A listOfIndices should have Index objects with"
              + "unique attribute arrays:arrayDimension, but the value " + arrayDim +
              "is used multiple times.";
          logArrayDimensionUniqueness(shortMsg);
        }
      }

      for(int i = 0; i <= max; i++) {
        if(!isSetArrayDimAt[i]) {
          String shortMsg = "A listOfIndices should have an Index with arrays:arrayDimension " 
              + i + " before adding an Index object with arrays:arrayDimension " + max;
          logArrayDimensionMissing(shortMsg);
          return;
        }
      }
    }
   
    
  }

  /**
   * Log an error indicating that two or more index objects have the same array dimension.
   * 
   * @param shortMsg
   */
  private void logArrayDimensionUniqueness(String shortMsg) {
    int code = 20111, severity = 2, category = 0, line = -1, column = -1;

    String pkg = "arrays";
    String msg = "The  ListOfIndices  associated with an SBase object must not have multiple Index"+
                 "objects with the same arrays:arrayDimension attribute. (Reference: SBML Level 3 Package"+ 
                 "Specification for Arrays, Version 1, Section 3.3 on page 6.)";
   
    
    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }
  
  
  /**
   * Log an error indicating a listOfIndices have a Index with array dimension n
   * but not an Index with array dimension from 0...n-1.
   * 
   * @param shortMsg
   */
  private void logArrayDimensionMissing(String shortMsg) {
    int code = 20110, severity = 2, category = 0, line = -1, column = -1;

    String pkg = "arrays";
    String msg = "The ListOfIndices associated with an SBase object must have a Index object"+
                 "with arrays:arrayDimension attribute set to 0 before adding a Index object with"+
                 "arrays:arrayDimension attribute set to 1. Similarly, the ListOfIndices in an SBase"+
                 "object must have Index objects, where one of them has arrays:arrayDimension attribute"+
                 "set to 0 and the other set to 1 before adding a Index object with arrays:arrayDimension"+
                 "attribute set to 2. (Reference: SBML Level 3 Package Specification for Arrays, Version 1,"+
                 "Section 3.3 on page 6.)";
   
    
    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }
  
}

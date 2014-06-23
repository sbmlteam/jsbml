/*
 * $Id:  IndexRefAttributeCheck.java 4:28:27 PM lwatanabe $
 * $URL: IndexRefAttributeCheck.java $
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
package org.sbml.jsbml.ext.arrays;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;


/**
 * This checks that a given {@link Index} object references a valid referenced attribute and that
 * it doesn't go out of bounds.
 * 
 * @author Leandro Watanabe
 * @version $Rev$
 * @since 1.0
 * @date Jun 17, 2014
 */
public class IndexRefAttributeCheck extends ArraysConstraint {

  Index index;

  public IndexRefAttributeCheck(Model model, Index index)
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
      SBase parent = index.getParentSBMLObject().getParentSBMLObject();
      String refValue = parent.writeXMLAttributes().get(refAttribute);
      // TODO: split at ':'
      if(refValue == null) {
        //TODO: log error
        System.err.println("Cannot find referenced object.");
      }
      ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) parent.getExtension(ArraysConstants.shortLabel);
      int arrayDimension = index.getArrayDimension();
      Dimension dim = arraysSBasePlugin.getDimensionByArrayDimension(arrayDimension);
      if(dim == null) {
        //TODO: log error
        System.err.println("Dimension mismatch in index math.");
      }
      
      //TODO: needs to check bounds
      
  }



}

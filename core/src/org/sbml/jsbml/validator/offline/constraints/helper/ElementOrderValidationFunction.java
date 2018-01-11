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
package org.sbml.jsbml.validator.offline.constraints.helper;

import java.util.List;

import org.apache.log4j.Logger;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.ValidationFunction;
import org.sbml.jsbml.xml.parsers.SBMLCoreParser;


/**
 * Class used to validate the order of the XML elements.
 * 
 * @author rodrigue
 */
public class ElementOrderValidationFunction<T extends SBase> implements ValidationFunction<T> {

  /**
   * Log4j logger
   */
  private static final transient Logger logger = Logger.getLogger(SBMLCoreParser.class);

  /**
   * an arrays holding the expected order of the (XML) elements
   */
  private String[] elementsOrder;
  
  /**
   * 
   */
  public ElementOrderValidationFunction(String[] elementsOrder) {
    this.elementsOrder = elementsOrder;
  }

  @Override
  public boolean check(ValidationContext ctx, T t) {

    if (elementsOrder == null) {
      return true;
    }
    
    if (t.isSetUserObjects() && t.getUserObject(JSBML.CHILD_ELEMENT_NAMES) != null)
    {
      @SuppressWarnings("unchecked")
      List<String> childElementNames = (List<String>) t.getUserObject(JSBML.CHILD_ELEMENT_NAMES);

      if (childElementNames == null || childElementNames.size() == 0) {
        return true;
      }
     
      int lastElementIndex = -1;
      String lastElementName = "";
      
      for (String elementName : childElementNames) {
        int currentElementIndex = -1;
        
        for (int i = 0; i < elementsOrder.length; i++) {
          if (elementsOrder[i].equals(elementName)) {
            currentElementIndex = i;
            break;
          }
        }
        
        if (currentElementIndex == -1) {
          // There was a problem, the element name does not seems to be part of the expected children.
          // But it can happen when we have L3 package children.
          if (logger.isDebugEnabled()) {
            logger.debug("ElementOrderValidationFunction - '" + elementName + "' is not recognized for element '" + t.getElementName() + "'");
          }
          // we continue as we don't check for that in this rule
          continue;
        }
        
        if (lastElementIndex != -1 && (currentElementIndex < lastElementIndex))
        {
          logger.warn("ElementOrderValidationFunction - '" + elementName + "' is placed before '" + lastElementName + "'");
              // + " indexes: " + currentElementIndex + " vs " + lastElementIndex);          
          return false;
        }
        
        lastElementIndex = currentElementIndex;
        lastElementName = elementName;
      }      
    }
    
    return true;
  }
  
  
}

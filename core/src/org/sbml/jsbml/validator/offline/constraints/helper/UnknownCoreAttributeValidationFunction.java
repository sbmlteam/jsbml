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

import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;
import org.sbml.jsbml.validator.offline.LoggingValidationContext;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.ValidationConstraint;
import org.sbml.jsbml.validator.offline.constraints.ValidationFunction;
import org.sbml.jsbml.xml.XMLNode;


/**
 * Class used to check if any unknown XML attributes from SBML core where found.
 * 
 * @author rodrigue
 */
public class UnknownCoreAttributeValidationFunction<T extends TreeNodeWithChangeSupport> implements ValidationFunction<T> {

  @Override
  public boolean check(ValidationContext ctx, T t) {

    if (t.isSetUserObjects() && t.getUserObject(JSBML.UNKNOWN_XML) != null)
    {
      XMLNode unknownNode = (XMLNode) t.getUserObject(JSBML.UNKNOWN_XML);

      // System.out.println("UnknownAttributeValidationFunction - attributes.length = " + unknownNode.getAttributesLength());

      if (unknownNode.getAttributesLength() > 0) {
        
        int attLength = unknownNode.getAttributesLength();
        
        SBMLDocument doc = (SBMLDocument) t.getRoot();
        String sbmlNamespace = doc.getURI();
        
        for (int i = 0; i < attLength; i++) {
          String attributePrefix = unknownNode.getAttrPrefix(i);
          String attributeURI = unknownNode.getAttrURI(i);
        
          
          
          // We consider that if the prefix is empty, the attribute belong to core
          if (attributePrefix.trim().length() == 0 || attributeURI.equals(sbmlNamespace)) {
            // TODO - create the proper SBMLError that contain useful information for the user.
            return false;
          }
        }
      }
    }

    return true;
  }
  
  
  /**
   * 
   * @param ctx
   * @param t
   * @param errorCode
   * @return
   */
  public boolean check(ValidationContext ctx, T t, int errorCode) {

    boolean ret = true;
    
    if (t.isSetUserObjects() && t.getUserObject(JSBML.UNKNOWN_XML) != null)
    {
      XMLNode unknownNode = (XMLNode) t.getUserObject(JSBML.UNKNOWN_XML);

      // System.out.println("UnknownAttributeValidationFunction - attributes.length = " + unknownNode.getAttributesLength());

      if (unknownNode.getAttributesLength() > 0) {
        
        int attLength = unknownNode.getAttributesLength();
        
        SBMLDocument doc = (SBMLDocument) t.getRoot();
        String sbmlNamespace = doc.getURI();
        
        
        for (int i = 0; i < attLength; i++) {
          String attributePrefix = unknownNode.getAttrPrefix(i);
          String attributeURI = unknownNode.getAttrURI(i);
        
          
          
          // We consider that if the prefix is empty, the attribute belong to core
          if (attributePrefix.trim().length() == 0 || attributeURI.equals(sbmlNamespace)) {
            // TODO - create the proper SBMLError that contain useful information for the user. ValidationConstraint.logError
            SBase source = null;
            
            if (t instanceof SBase) {
              source = (SBase) t; 
            } else {
              source = LoggingValidationContext.getParentSBase(t);
            }
            
            ret = false;
            ValidationConstraint.logError(ctx, errorCode, source, unknownNode.getAttrName(i));
            }
          }
        }
      }

    return ret;
  }
}

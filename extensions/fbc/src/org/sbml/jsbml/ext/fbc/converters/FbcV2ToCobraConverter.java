/*
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2022 jointly by the following organizations: 
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
package org.sbml.jsbml.ext.fbc.converters;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.ext.fbc.CobraConstants;
import org.sbml.jsbml.util.converters.SBMLConverter;

/**
 * Converts SBML FBC Version 2 files to old COBRA SBML files.
 * 
 * @author Thomas Hamm
 * @author Nicolas Rodriguez
 * @author Thorsten Tiede
 * @since 1.3
 */
public class FbcV2ToCobraConverter implements SBMLConverter {

  // Options for FbcV2ToFbcV1Converter
  String defaultGeneAssociationSpelling = null;
  
  // Options for FbcV1ToCobraConverter
  Double defaultLowerBound = null;
  Double defaultUpperBound = null;
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.converters.SBMLConverter#convert(org.sbml.jsbml.SBMLDocument)
   */
  @Override
  public SBMLDocument convert(SBMLDocument sbmlDocument) throws SBMLException {
   // convert SBML FBCV2 file to SBML FBCV1
    FbcV2ToFbcV1Converter fbcV2ToFbcV1Converter = new FbcV2ToFbcV1Converter();
    if (this.defaultGeneAssociationSpelling != null) {
      fbcV2ToFbcV1Converter.setOption(CobraConstants.DEFAULT_GENE_ASSOCIATION_SPELLING, defaultGeneAssociationSpelling);
    }
    sbmlDocument = fbcV2ToFbcV1Converter.convert(sbmlDocument);
   // convert SBML FBCV1 file to old COBRA SBML
    FbcV1ToCobraConverter fbcV1ToCobraConverter = new FbcV1ToCobraConverter();
    if (this.defaultLowerBound != null)
    {
      fbcV1ToCobraConverter.setOption(CobraConstants.DEFAULT_LOWER_BOUND_NAME, this.defaultLowerBound.toString());
    }
    if (this.defaultUpperBound != null)
    {
      fbcV1ToCobraConverter.setOption(CobraConstants.DEFAULT_UPPER_BOUND_NAME, this.defaultUpperBound.toString());
    }
    sbmlDocument = fbcV1ToCobraConverter.convert(sbmlDocument);  
    
    return sbmlDocument;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.converters.SBMLConverter#setOption(java.lang.String, java.lang.String)
   */
  @Override
  public void setOption(String name, String value) {
    if (value == null) return;
    if (name.equals(CobraConstants.DEFAULT_LOWER_BOUND_NAME)) {
      try {
        this.defaultLowerBound = Double.valueOf(value);
      } catch (NumberFormatException e) {
        this.defaultLowerBound = null;
      }
    }
    if (name.equals(CobraConstants.DEFAULT_UPPER_BOUND_NAME)) {
      try {
        this.defaultUpperBound = Double.valueOf(value);
      } catch (NumberFormatException e) {
        this.defaultUpperBound = null;
      }
    }
    if (name.equals(CobraConstants.DEFAULT_GENE_ASSOCIATION_SPELLING)) {
      this.defaultGeneAssociationSpelling = value;
    }
  }
}

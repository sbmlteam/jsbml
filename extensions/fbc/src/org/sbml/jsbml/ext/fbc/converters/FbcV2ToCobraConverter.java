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
package org.sbml.jsbml.ext.fbc.converters;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
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

  // Options for FbcV1ToCobraConverter
  Double defaultLowerFluxBound = null;
  Double defaultUpperFluxBound = null;
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.converters.SBMLConverter#convert(org.sbml.jsbml.SBMLDocument)
   */
  @Override
  public SBMLDocument convert(SBMLDocument sbmlDocument) throws SBMLException {
   // convert SBML FBCV2 file to SBML FBCV1
    FbcV2ToFbcV1Converter fbcV2ToFbcV1Converter = new FbcV2ToFbcV1Converter();
    sbmlDocument = fbcV2ToFbcV1Converter.convert(sbmlDocument);
   // convert SBML FBCV1 file to old COBRA SBML
    FbcV1ToCobraConverter fbcV1ToCobraConverter = new FbcV1ToCobraConverter();
    if (this.defaultLowerFluxBound != null)
    {
      fbcV1ToCobraConverter.setOption("defaultLowerFluxBound", this.defaultLowerFluxBound.toString());
    }
    if (this.defaultUpperFluxBound != null)
    {
      fbcV1ToCobraConverter.setOption("defaultUpperFluxBound", this.defaultUpperFluxBound.toString());
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
    if (name.equals("defaultLowerFluxBound")) {
      try {
        this.defaultLowerFluxBound = Double.valueOf(value);
      } catch (NumberFormatException e) {
        this.defaultLowerFluxBound = null;
      }
    }
    if (name.equals("defaultUpperFluxBound")) {
      try {
        this.defaultUpperFluxBound = Double.valueOf(value);
      } catch (NumberFormatException e) {
        this.defaultUpperFluxBound = null;
      }
    }
    
  }
}

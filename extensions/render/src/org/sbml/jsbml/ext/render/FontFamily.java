/*
 *
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
package org.sbml.jsbml.ext.render;

/**
 * The FontFamily enumeration gives a hint as to which font is to be used when rendering {@link Text} elements.
 * 
 * @author Jakob Matthes
 * @author Onur &Oumlzel
 * @since 1.0
 */
public enum FontFamily {
  
  SERIF("serif"), SANS_SERIF("sans-serif"), MONOSPACE("monospace");
  
  private String fontFamily;
  
  private FontFamily(String fontFamily) {
    this.fontFamily = fontFamily;
  }
  
  @Override
  public String toString() { 
    return this.fontFamily;
  }
  
  /**
   * Checks if a given String value matches one of the 
   * {@link FontFamily} hints. 
   * @param value
   * @return the matching FontFamily, or null otherwise
   */
  public static FontFamily toFontFamily(String value) {
    for (FontFamily f : FontFamily.values()) {
      if(f.toString().equals(value)) {
        return f; 
      }
    }
    return null;
  }
}

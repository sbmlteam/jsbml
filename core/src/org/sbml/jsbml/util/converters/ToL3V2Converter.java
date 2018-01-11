/*
 * $IdToL3V2Converter.java 09:44:51 draeger $
 * $URLToL3V2Converter.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2018  jointly by the following organizations:
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
package org.sbml.jsbml.util.converters;

import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.util.ValuePair;


/**
 * @author Andreas Dr&auml;ger
 * @since 1.2
 * @date 05.01.2018
 */
public class ToL3V2Converter extends ToL3Converter {

  /**
   * @param targetLV
   */
  public ToL3V2Converter() {
    super(ValuePair.of(3, 2));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.converters.LevelVersionConverter#needsAction(org.sbml.jsbml.SBase)
   */
  @Override
  public boolean needsAction(SBase sbase) {
    return (sbase instanceof Reaction) && ((Reaction) sbase).isSetFast();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.converters.LevelVersionConverter#performAction(org.sbml.jsbml.SBase)
   */
  @Override
  public <T extends SBase> boolean performAction(T sbase) {
    super.performAction(sbase);
    if (sbase instanceof Reaction) {
      Reaction r = (Reaction) sbase;
      if (r.isSetFast()) {
        if (r.isFast()) {
          return false;
        }
        r.unsetFast();
      }
      return true;
    }
    return false;
  }

}

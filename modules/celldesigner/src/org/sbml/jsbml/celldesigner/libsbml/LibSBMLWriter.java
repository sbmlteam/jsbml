/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
package org.sbml.jsbml.celldesigner.libsbml;

import java.io.IOException;
import java.util.List;

import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLOutputConverter;
import org.sbml.libsbml.Model;


/**
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 1.0
 * @date 03.02.2014
 */
public class LibSBMLWriter implements SBMLOutputConverter<org.sbml.libsbml.Model> {

  @Override
  public List<SBMLException> getWriteWarnings(Model model) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean writeSBML(Model model, String filename) throws SBMLException,
  IOException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean writeSBML(Model model, String filename, String programName,
    String versionNumber) throws SBMLException, IOException {
    // TODO Auto-generated method stub
    return false;
  }
}

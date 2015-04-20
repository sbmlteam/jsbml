/*
 * $Id: LibSBMLReader.java 2109 2015-01-05 04:50:45Z andreas-draeger $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/modules/celldesigner/src/org/sbml/jsbml/celldesigner/libsbml/LibSBMLReader.java $
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

import java.io.File;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.Delay;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLInputConverter;
import org.sbml.jsbml.StoichiometryMath;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.util.ProgressListener;

/**
 * This is a helper class for the CellDesigner plugin interface and is designed
 * to only work in connection with CellDesigner. If you want to really
 * inter-convert between LibSBML and JSBML data structures, the classes in this
 * entire package are not suitable. Please have a look into the package
 * {@link org.sbml.jsbml.xml.libsbml}.
 * 
 * @author Andreas Dr&auml;ger
 * @version $Rev: 2109 $
 * @since 1.0
 * +@date 03.02.2014
 */
@SuppressWarnings("deprecation")
public class LibSBMLReader implements SBMLInputConverter<org.sbml.libsbml.Model> {

  /**
   * @param trigger
   * @return
   * @throws XMLStreamException
   */
  public static Trigger readTrigger(org.sbml.libsbml.Trigger trigger) throws XMLStreamException {
    Trigger trig = new Trigger();
    LibSBMLUtils.transferSBaseProperties(trig, trigger);
    //    if (trigger.isSetInitialValue()) {
    //      trig.setInitialValue(trigger.getInitialValue());
    //    }
    //    if (trigger.isSetPersistent()) {
    //      trig.setPersistent(trigger.getPersistent());
    //    }
    if (trigger.isSetMath()) {
      trig.setMath(LibSBMLUtils.convert(trigger.getMath(), trig));
    }
    return trig;
  }

  /**
   * @param delay
   * @return
   */
  public static Delay readDelay(org.sbml.libsbml.Delay delay) {
    Delay de = new Delay();
    LibSBMLUtils.transferSBaseProperties(delay, de);
    if (delay.isSetMath()) {
      de.setMath(LibSBMLUtils.convert(delay.getMath(), de));
    }
    return de;
  }

  /**
   * @param stoichiometryMath
   * @return
   * @throws XMLStreamException
   */
  public static StoichiometryMath readStoichiometricMath(org.sbml.libsbml.StoichiometryMath stoichiometryMath) throws XMLStreamException {
    StoichiometryMath sm = new StoichiometryMath();
    LibSBMLUtils.transferSBaseProperties(sm, stoichiometryMath);
    if (stoichiometryMath.isSetMath()) {
      sm.setMath(LibSBMLUtils.convert(stoichiometryMath.getMath(), sm));
    }
    return sm;
  }

  @Override
  public Model convertModel(org.sbml.libsbml.Model model) throws Exception {
    return null;
  }

  @Override
  public SBMLDocument convertSBMLDocument(File sbmlFile) throws Exception {
    return null;
  }

  @Override
  public SBMLDocument convertSBMLDocument(String fileName) throws Exception {
    return null;
  }

  @Override
  public org.sbml.libsbml.Model getOriginalModel() {
    return null;
  }

  @Override
  public List<SBMLException> getWarnings() {
    return null;
  }

  @Override
  public void setListener(ProgressListener listener) {
  }

}

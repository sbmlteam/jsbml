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
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.test;

import java.io.IOException;
import java.util.logging.Logger;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.QuantityWithUnit;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.libsbml.libsbml;

/**
 * 
 * @author  Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 * @date 15.12.2011
 */
public class ReactionReadTest {


  /**
   * Either or not the test cloning.
   * If set to true, the cloned jsbml object will be compared to the original
   * libsbml object. Then both object will be cloned and compared.
   */
  private static boolean TEST_CLONING = false;


  /**
   * This value should be set to true after the static block is executed
   * otherwise, there is not need to run this test !! You will need
   * to configure libSBML properly before being able to make use of
   * this class.
   * 
   */
  private static boolean isLibSBMLAvailable = false;

  static {
    try {
      System.loadLibrary("sbmlj");

      Class.forName("org.sbml.libsbml.libsbml");

      isLibSBMLAvailable = true;

    } catch (SecurityException e) {
      e.printStackTrace();

      System.out
      .println("SecurityException exception catched: Could not load libsbml library.");
    } catch (UnsatisfiedLinkError e) {
      e.printStackTrace();
      System.out
      .println("UnsatisfiedLinkError exception catched: Could not load libsbml library.");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();

      System.out
      .println("ClassNotFoundException exception catched: Could not load libsbml class file.");

    } catch (RuntimeException e) {
      e.printStackTrace();
      System.out
      .println("Could not load libsbml.\n "
          + "Control that the libsbmlj.jar that you are using is synchronized with your current libSBML installation.");

    }
  }

  private static Logger loggerErr = Logger.getLogger("org.sbml.jsbml.test");
  // private static Logger loggerFull = Logger.getLogger("org.sbml.jsbml.test.SBMLReaderTest");


  public static void main(String[] args) throws IOException, XMLStreamException {

    if (args.length < 1) {
      loggerErr.warning("Usage: java org.sbml.jsbml.xml.test.SBMLReaderTest sbmlFile");
      return;
    }

    if (!isLibSBMLAvailable) {
      loggerErr.warning("Warning: libsbml need to be installed !!");
      return;
    }

    String fileName = args[0];

    SBMLDocument jsbmlDocument = new SBMLReader().readSBML(fileName);

    org.sbml.libsbml.SBMLDocument libsbmlDocument = libsbml.readSBML(fileName);

    Model jsbmlModel = jsbmlDocument.getModel();
    org.sbml.libsbml.Model libsbmlModel = libsbmlDocument.getModel();


    // Compartment
    if (jsbmlModel.getNumCompartments() == libsbmlModel.getNumCompartments()) {
      int i = 0;
      for (Compartment jsbmlCompartment : jsbmlModel.getListOfCompartments()) {
        // comparing Compartment
        org.sbml.libsbml.Compartment libSbmlCompartment = libsbmlModel.getCompartment(i);
        i++;
        compareCompartment(jsbmlCompartment, libSbmlCompartment);
      }

    } else {
      // log error
      loggerErr.warning("!!!!! Different number of Compartment found !!!!");
    }


    // Species
    if (jsbmlModel.getNumSpecies() == libsbmlModel.getNumSpecies()) {
      int i = 0;
      for (Species jsbmlSpecies : jsbmlModel.getListOfSpecies()) {
        // comparing Species
        org.sbml.libsbml.Species libSbmlSpecies = libsbmlModel.getSpecies(i);
        i++;
        compareSpecies(jsbmlSpecies, libSbmlSpecies);
      }
    } else {
      // log error
      loggerErr.warning("!!!!! Different number of Species found !!!!");
    }


    // Parameter
    if (jsbmlModel.getNumParameters() == libsbmlModel.getNumParameters()) {
      int i = 0;
      for (Parameter jsbmlParameter : jsbmlModel.getListOfParameters()) {
        // comparing Parameter
        org.sbml.libsbml.Parameter libSbmlParameter = libsbmlModel.getParameter(i);
        i++;
        compareParameter(jsbmlParameter, libSbmlParameter);
      }

    } else {
      // log error
      loggerErr.warning("!!!!! Different number of Parameter found !!!!");
    }


    // Reaction
    if (jsbmlModel.getNumReactions() == libsbmlModel.getNumReactions()) {
      int i = 0;
      for (Reaction jsbmlReaction : jsbmlModel.getListOfReactions()) {
        // comparing Reaction
        org.sbml.libsbml.Reaction libSbmlReaction = libsbmlModel.getReaction(i);
        i++;
        compareReaction(jsbmlReaction, libSbmlReaction);

        if (TEST_CLONING) {
          // cloning the object and comparing again
          Reaction clonedReaction = jsbmlReaction.clone();

          compareReaction(clonedReaction, libSbmlReaction);
          compareReaction(clonedReaction, libSbmlReaction.cloneObject());
        }
      }

    } else {
      // log error
      loggerErr.warning("!!!!! Different number of Reaction found !!!!");
    }

  }

  private static void compareSBase(SBase jsbmlSbase, org.sbml.libsbml.SBase libSbmlSbase)
  {

    // TODO: other stuffs to test, like additional namespaces declarations, extensions objects?

    if (libSbmlSbase == null) {
      loggerErr.warning("SBase libSBML object is null !!! ");
      return;
    }
    if (jsbmlSbase == null) {
      loggerErr.warning("SBase JSBML object is null !!! ");
      return;
    }

    // level
    if (jsbmlSbase.getLevel() != libSbmlSbase.getLevel()) {
      // log error
      loggerErr.warning("SBase " + libSbmlSbase.getMetaId() + ": level differ.");
      loggerErr.warning("SBase " + jsbmlSbase.getElementName() + ": level = " + jsbmlSbase.getLevel());
    }

    // version
    if (jsbmlSbase.getVersion() != libSbmlSbase.getVersion()) {
      // log error
      loggerErr.warning("SBase " + libSbmlSbase.getMetaId() + ": version differ.");
      loggerErr.warning("SBase " + jsbmlSbase.getElementName() + ": version = " + jsbmlSbase.getVersion());
    }

    // Metaid
    if (jsbmlSbase.isSetMetaId() && libSbmlSbase.isSetMetaId()) {
      if (!jsbmlSbase.getMetaId().equals(libSbmlSbase.getMetaId())) {
        // log error
        loggerErr.warning("SBase " + libSbmlSbase.getMetaId() + ": metaid differ.");
        loggerErr.warning("SBase: libsbml metaid = '" + libSbmlSbase.getMetaId() + "', jsbml = '" + jsbmlSbase.getMetaId() + "'");
      }
    } else if (jsbmlSbase.isSetMetaId() != libSbmlSbase.isSetMetaId()) {
      // log error
      loggerErr.warning("SBase " + libSbmlSbase.getMetaId() + ": isSetMetaId differ.");
    }

    // SBOTerm
    if (jsbmlSbase.isSetSBOTerm() && libSbmlSbase.isSetSBOTerm()) {
      if (jsbmlSbase.getSBOTerm() !=(libSbmlSbase.getSBOTerm())) {
        // log error
        loggerErr.warning("SBase " + libSbmlSbase.getMetaId() + ": sboTerm differ.");
      }
    } else if (jsbmlSbase.isSetSBOTerm() != libSbmlSbase.isSetSBOTerm()) {
      // log error
      loggerErr.warning("SBase " + libSbmlSbase.getMetaId() + ": isSetSBOTerm differ.");
    }

  }

  /**
   * 
   * @param jsbmlSbase
   * @param libSbmlSbase
   */
  private static void compareNamedSBase(NamedSBase jsbmlSbase, org.sbml.libsbml.SBase libSbmlSbase)
  {
    // id
    if (jsbmlSbase.isSetId() && libSbmlSbase.isSetId()) {
      if (!jsbmlSbase.getId().equals(libSbmlSbase.getId())) {
        // log error
        loggerErr.warning("SBase " + libSbmlSbase.getId() + ": id differ.");
      }
    } else if (jsbmlSbase.isSetId() && libSbmlSbase.isSetId()) {
      // log error
      loggerErr.warning("SBase " + libSbmlSbase.getMetaId() + ": isSetId differ.");
    }

    // name
    if (jsbmlSbase.isSetName() && libSbmlSbase.isSetName()) {
      if (!jsbmlSbase.getName().equals(libSbmlSbase.getName())) {
        // log error
        loggerErr.warning("SBase " + libSbmlSbase.getId() + ": name differ.");
      }
    } else if (jsbmlSbase.isSetName() != libSbmlSbase.isSetName()) {
      // log error
      loggerErr.warning("SBase " + libSbmlSbase.getId() + ": isSetName differ.");
      loggerErr.warning("SBase jsbml isSetName = " + jsbmlSbase.isSetName() + ": libsbml = " + libSbmlSbase.isSetName());
    }

  }

  private static void compareCompartment(Compartment jsbmlCompartment, org.sbml.libsbml.Compartment libSbmlCompartment) {

    compareSBase(jsbmlCompartment, libSbmlCompartment);
    compareNamedSBase(jsbmlCompartment, libSbmlCompartment);

  }

  private static void compareReaction(Reaction jsbmlReaction,	org.sbml.libsbml.Reaction libSbmlReaction) {

    compareSBase(jsbmlReaction, libSbmlReaction);
    compareNamedSBase(jsbmlReaction, libSbmlReaction);

  }

  private static void compareSpecies(Species jsbmlSpecies, org.sbml.libsbml.Species libSbmlSpecies) {

    compareSBase(jsbmlSpecies, libSbmlSpecies);
    compareNamedSBase(jsbmlSpecies, libSbmlSpecies);
  }

  private static void compareParameter(QuantityWithUnit jsbmlParameter, org.sbml.libsbml.Parameter libSbmlParameter) {

    compareSBase(jsbmlParameter, libSbmlParameter);
    compareNamedSBase(jsbmlParameter, libSbmlParameter);
  }

}

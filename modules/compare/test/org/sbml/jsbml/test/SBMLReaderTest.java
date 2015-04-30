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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.CompartmentType;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.ExplicitRule;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.QuantityWithUnit;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.SimpleSpeciesReference;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.StoichiometryMath;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.SBMLtools;
import org.sbml.jsbml.util.compilers.LibSBMLFormulaCompiler;
import org.sbml.jsbml.xml.XMLAttributes;
import org.sbml.jsbml.xml.XMLNamespaces;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.stax.SBMLReader;
import org.sbml.libsbml.libsbml;

import com.topologi.diffx.DiffXException;
import com.topologi.diffx.config.DiffXConfig;

/**
 * Compares recursively a libSBML SBMLDocument with a jsbml {@link SBMLDocument} to find
 * any differences in the manner they read SBML files.
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 */
@SuppressWarnings("deprecation")
public class SBMLReaderTest {


  /**
   * Either or not the test cloning.
   * If set to true, the cloned jsbml object will be compared to the original libsbml object.
   * Then both object will be cloned and compared.
   */
  private static boolean TEST_CLONING = false;


  /**
   * The comparison of math formula, MathML and ASTNode
   * generate a lot of output mostly about non errors, so
   * if you set this value to false you can see much better
   * other errors, not related to mathML which otherwise can
   * be kind of lost in many many lines of math related output.
   * 
   */
  private static boolean TEST_MATHML_ASTNODE = false;

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


  /**
   * Tests two cloned SBase objects to check that they are equals, using the equals method on both objects.
   * Tests as well that their hasCode() method return the same value.
   * 
   * @param sbase1
   * @param sbase2
   * 
   */
  private static void testEqualsAndHashCodeOnClone(SBase sbase1, SBase sbase2) {

    String elementName = sbase1.getElementName();
    String id = sbase1.getMetaId();

    if (sbase1 instanceof AbstractNamedSBase) {
      id = ((AbstractNamedSBase) sbase1).getId();
    }

    if ((! sbase1.equals(sbase2)) || (! sbase2.equals(sbase1)) || (! sbase1.equals(sbase1))) {
      loggerErr.warning("Cloned " + elementName + " different from the original " + elementName + " !!! (" + id + ")");
    }
    if (sbase1.hashCode() != sbase2.hashCode()) {
      loggerErr.warning("Cloned " + elementName + " hashCode different from the original " + elementName + " !!! (" + id + ")");
    }
  }

  /**
   * Tests two different SBase objects to check that they are not equals, using the equals method on both objects.
   * Tests as well that their hasCode() method does not return the same value.
   * 
   * @param sbase1
   * @param sbase2
   * @param libSBMLSBase
   */
  private static void testEqualsAndHashCodeDifferent(SBase sbase1, SBase sbase2) {

    String elementName = sbase1.getElementName();
    String id1 = sbase1.getMetaId();
    String id2 = sbase2.getMetaId();

    if (sbase1 instanceof AbstractNamedSBase) {
      id1 = ((AbstractNamedSBase) sbase1).getId();
      id2 = ((AbstractNamedSBase) sbase2).getId();
    }

    if (sbase1.equals(sbase2) || sbase2.equals(sbase1)) {
      loggerErr.warning("The " + elementName + "(" + id1 + ") does not differ from the " + elementName + " (" + id2 + ") !!! ");
    }
    if (sbase1.hashCode() == sbase2.hashCode()) {
      loggerErr.warning("The " + elementName + "(" + id1 + ") hashCode does not differ from the " + elementName + " (" + id2 + ") !!! ");
    }

  }


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

    /*
		Model model = jsbmlDocument.getModel();
		Annotation modelAnnotation = model.getAnnotation();

		System.out.println("Model Annotation about: \n@" + modelAnnotation.getAbout() + "@");

		System.out.println("Model NonRDFAnnotation : \n@" + modelAnnotation.getNonRDFannotation() + "@");

		System.out.println("\n\n\n\n\n !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! \n\n\n Model Annotation String: \n@" + model.getAnnotationString() + "@");

		try {
			SBMLWriter.write(jsbmlDocument, "testWriteAnnotation.xml", ' ', (short) 2);
		} catch (SBMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.exit(1);
     */

    try {
      SBMLWriter.write(jsbmlDocument, "testWriteNotes-jsbml.xml", ' ', (short) 2);
    } catch (SBMLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


    org.sbml.libsbml.SBMLDocument libsbmlDocument = libsbml.readSBML(fileName);

    // compare SBMLDocument
    compareSBMLDocument(jsbmlDocument, libsbmlDocument);

    Model jsbmlModel = jsbmlDocument.getModel();
    org.sbml.libsbml.Model libsbmlModel = libsbmlDocument.getModel();

    // compare Model
    compareModel(jsbmlModel, libsbmlModel);

    // FunctionDefinition
    if (jsbmlModel.getNumFunctionDefinitions() == libsbmlModel.getNumFunctionDefinitions()) {
      int i = 0;
      for (FunctionDefinition jsbmlFunctionDefinition : jsbmlModel.getListOfFunctionDefinitions()) {
        // comparing FunctionDefinition
        org.sbml.libsbml.FunctionDefinition libSbmlFunctionDefinition = libsbmlModel.getFunctionDefinition(i);
        i++;
        compareFunctionDefinition(jsbmlFunctionDefinition, libSbmlFunctionDefinition);
        if (TEST_CLONING) {
          // cloning the object and comparing again
          FunctionDefinition jsbmlClonedFD = jsbmlFunctionDefinition.clone();

          testEqualsAndHashCodeOnClone(jsbmlFunctionDefinition, jsbmlClonedFD);

          compareFunctionDefinition(jsbmlClonedFD, libSbmlFunctionDefinition);
          compareFunctionDefinition(jsbmlClonedFD, libSbmlFunctionDefinition.cloneObject());
        }
        if (i > 0 && i < jsbmlModel.getNumFunctionDefinitions()) {
          testEqualsAndHashCodeDifferent(jsbmlFunctionDefinition, jsbmlModel.getFunctionDefinition(i));
        }
      }

    } else {
      // log error
      loggerErr.warning("!!!!! Different number of FunctionDefinition found !!!!");
    }

    // UnitDefinition
    if (jsbmlModel.getNumUnitDefinitions() == libsbmlModel.getNumUnitDefinitions()) {
      int i = 0;
      for (UnitDefinition jsbmlUnitDefinition : jsbmlModel.getListOfUnitDefinitions()) {
        // comparing UnitDefinition
        org.sbml.libsbml.UnitDefinition libSbmlUnitDefinition = libsbmlModel.getUnitDefinition(i);
        i++;
        // DEBUG loggerErr.warning("  Comparing UnitDefinition " + libSbmlUnitDefinition.getId());

        compareUnitDefinition(jsbmlUnitDefinition, libSbmlUnitDefinition);

        if (TEST_CLONING) {
          // cloning the object and comparing again
          UnitDefinition jsbmlClonedUD = jsbmlUnitDefinition.clone();

          testEqualsAndHashCodeOnClone(jsbmlUnitDefinition, jsbmlClonedUD);

          loggerErr.fine("\tjsbml.clone against libSBML");
          compareUnitDefinition(jsbmlClonedUD, libSbmlUnitDefinition);
          loggerErr.fine("\tjsbml.clone against libSBML.clone");
          compareUnitDefinition(jsbmlClonedUD, libSbmlUnitDefinition.cloneObject());
        }
      }

    } else {
      // log error
      loggerErr.warning("!!!!! Different number of UnitDefinition found !!!!");
      loggerErr.warning(" jsbml = " + jsbmlModel.getNumUnitDefinitions() + ": libsbml = " + libsbmlModel.getNumUnitDefinitions());
      for (UnitDefinition jsbmlUnitDefinition : jsbmlModel.getListOfUnitDefinitions()) {
        loggerErr.info(jsbmlUnitDefinition.toString());
      }
    }


    // CompartmentType
    if (jsbmlModel.getNumCompartmentTypes() == libsbmlModel.getNumCompartmentTypes()) {
      int i = 0;
      for (CompartmentType jsbmlCompartmentType : jsbmlModel.getListOfCompartmentTypes()) {
        // comparing CompartmentType
        org.sbml.libsbml.CompartmentType libSbmlCompartmentType = libsbmlModel.getCompartmentType(i);
        i++;
        compareCompartmentType(jsbmlCompartmentType, libSbmlCompartmentType);

        if (TEST_CLONING) {
          // cloning the object and comparing again
          compareCompartmentType(jsbmlCompartmentType.clone(), libSbmlCompartmentType);
          compareCompartmentType(jsbmlCompartmentType.clone(), libSbmlCompartmentType.cloneObject());
        }
      }

    } else {
      // log error
      loggerErr.warning("!!!!! Different number of CompartmentType found !!!!");
    }


    // SpeciesType
    if (jsbmlModel.getNumSpeciesTypes() == libsbmlModel.getNumSpeciesTypes()) {
      int i = 0;
      for (SpeciesType jsbmlSpeciesType : jsbmlModel.getListOfSpeciesTypes()) {
        // comparing SpeciesType
        org.sbml.libsbml.SpeciesType libSbmlSpeciesType = libsbmlModel.getSpeciesType(i);
        i++;
        compareSpeciesType(jsbmlSpeciesType, libSbmlSpeciesType);

        if (TEST_CLONING) {
          // cloning the object and comparing again
          compareSpeciesType(jsbmlSpeciesType.clone(), libSbmlSpeciesType);
          compareSpeciesType(jsbmlSpeciesType.clone(), libSbmlSpeciesType.cloneObject());
        }
      }

    } else {
      // log error
      loggerErr.warning("!!!!! Different number of SpeciesType found !!!!");
    }


    // Compartment
    if (jsbmlModel.getNumCompartments() == libsbmlModel.getNumCompartments()) {
      int i = 0;
      for (Compartment jsbmlCompartment : jsbmlModel.getListOfCompartments()) {
        // comparing Compartment
        org.sbml.libsbml.Compartment libSbmlCompartment = libsbmlModel.getCompartment(i);
        i++;
        compareCompartment(jsbmlCompartment, libSbmlCompartment);

        if (TEST_CLONING) {
          // cloning the object and comparing again
          Compartment jsbmlClonedComp = jsbmlCompartment.clone();

          testEqualsAndHashCodeOnClone(jsbmlCompartment, jsbmlClonedComp);

          loggerErr.fine("cloning the jsbml object");
          compareCompartment(jsbmlClonedComp, libSbmlCompartment);
          loggerErr.fine("cloning both objects");
          compareCompartment(jsbmlClonedComp, libSbmlCompartment.cloneObject());
        }
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

        if (TEST_CLONING) {
          // cloning the object and comparing again
          Species clonedSpecies = jsbmlSpecies.clone();

          testEqualsAndHashCodeOnClone(jsbmlSpecies, clonedSpecies);

          compareSpecies(clonedSpecies, libSbmlSpecies);
          compareSpecies(clonedSpecies, libSbmlSpecies.cloneObject());
        }
        if (i > 0 && i < jsbmlModel.getNumSpecies()) {
          testEqualsAndHashCodeDifferent(jsbmlSpecies, jsbmlModel.getSpecies(i));
        }
      }

      i = 0;
      for (Species jsbmlSpecies1 : jsbmlModel.getListOfSpecies()) {

        for (Species jsbmlSpecies2 : jsbmlModel.getListOfSpecies()) {
          if (jsbmlSpecies1.getId().equals(jsbmlSpecies2.getId())) {
            testEqualsAndHashCodeOnClone(jsbmlSpecies1, jsbmlSpecies2);
          } else {
            testEqualsAndHashCodeDifferent(jsbmlSpecies1, jsbmlSpecies2);
          }
        }
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

        if (TEST_CLONING) {
          // cloning the object and comparing again
          Parameter clonedParameter = jsbmlParameter.clone();

          testEqualsAndHashCodeOnClone(jsbmlParameter, clonedParameter);

          compareParameter(clonedParameter, libSbmlParameter);
          compareParameter(clonedParameter, libSbmlParameter.cloneObject());
        }
        if (i > 0 && i < jsbmlModel.getNumParameters()) {
          testEqualsAndHashCodeDifferent(jsbmlParameter, jsbmlModel.getParameter(i));
        }
      }

    } else {
      // log error
      loggerErr.warning("!!!!! Different number of Parameter found !!!!");
    }


    // InitialAssignment
    if (jsbmlModel.getNumInitialAssignments() == libsbmlModel.getNumInitialAssignments()) {
      int i = 0;
      for (InitialAssignment jsbmlInitialAssignment : jsbmlModel.getListOfInitialAssignments()) {
        // comparing InitialAssignment
        org.sbml.libsbml.InitialAssignment libSbmlInitialAssignment = libsbmlModel.getInitialAssignment(i);
        i++;
        compareInitialAssignment(jsbmlInitialAssignment, libSbmlInitialAssignment);

        if (TEST_CLONING) {
          // cloning the object and comparing again
          compareInitialAssignment(jsbmlInitialAssignment.clone(), libSbmlInitialAssignment);
          compareInitialAssignment(jsbmlInitialAssignment.clone(), libSbmlInitialAssignment.cloneObject());
        }
      }

    } else {
      // log error
      loggerErr.warning("!!!!! Different number of InitialAssignment found !!!!");
    }


    // Rule
    if (jsbmlModel.getNumRules() == libsbmlModel.getNumRules()) {
      int i = 0;
      for (Rule jsbmlRule : jsbmlModel.getListOfRules()) {
        // comparing Rule
        org.sbml.libsbml.Rule libSbmlRule = libsbmlModel.getRule(i);
        i++;
        compareRule(jsbmlRule, libSbmlRule);

        if (TEST_CLONING) {
          // cloning the object and comparing again
          compareRule(jsbmlRule.clone(), libSbmlRule);
          compareRule(jsbmlRule.clone(), libSbmlRule.cloneObject());
        }
      }

    } else {
      // log error
      loggerErr.warning("!!!!! Different number of Rule found !!!!");
    }


    // Constraint
    if (jsbmlModel.getNumConstraints() == libsbmlModel.getNumConstraints()) {
      int i = 0;
      for (Constraint jsbmlConstraint : jsbmlModel.getListOfConstraints()) {
        // comparing Constraint
        org.sbml.libsbml.Constraint libSbmlConstraint = libsbmlModel.getConstraint(i);
        i++;
        compareConstraint(jsbmlConstraint, libSbmlConstraint);

        if (TEST_CLONING) {
          // cloning the object and comparing again
          compareConstraint(jsbmlConstraint.clone(), libSbmlConstraint);
          compareConstraint(jsbmlConstraint.clone(), libSbmlConstraint.cloneObject());
        }
      }

    } else {
      // log error
      loggerErr.warning("!!!!! Different number of Constraint found !!!!");
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

          testEqualsAndHashCodeOnClone(jsbmlReaction, clonedReaction);

          compareReaction(clonedReaction, libSbmlReaction);
          compareReaction(clonedReaction, libSbmlReaction.cloneObject());
        }
        libSbmlReaction.delete();

        if (i > 0 && i < jsbmlModel.getNumReactions()) {
          testEqualsAndHashCodeDifferent(jsbmlReaction, jsbmlModel.getReaction(i));
        }
      }

    } else {
      // log error
      loggerErr.warning("!!!!! Different number of Reaction found !!!!");
    }


    // Event
    if (jsbmlModel.getNumEvents() == libsbmlModel.getNumEvents()) {
      int i = 0;
      for (Event jsbmlEvent : jsbmlModel.getListOfEvents()) {
        // comparing Event
        org.sbml.libsbml.Event libSbmlEvent = libsbmlModel.getEvent(i);
        i++;
        compareEvent(jsbmlEvent, libSbmlEvent);

        if (TEST_CLONING) {
          // cloning the object and comparing again
          compareEvent(jsbmlEvent.clone(), libSbmlEvent);
          compareEvent(jsbmlEvent.clone(), libSbmlEvent.cloneObject());
        }
      }

    } else {
      // log error
      loggerErr.warning("!!!!! Different number of Event found !!!!");
    }


  }

  private static void compareSBMLDocument(SBMLDocument jsbmlDocument,	org.sbml.libsbml.SBMLDocument libsbmlDocument) {

    compareSBase(jsbmlDocument, libsbmlDocument);
  }

  private static void compareModel(Model jsbmlModel, org.sbml.libsbml.Model libsbmlModel) {

    compareSBase(jsbmlModel, libsbmlModel);

    // substanceUnits: UnitSIdRef { use="optional" }
    if (libsbmlModel.isSetSubstanceUnits() != jsbmlModel.isSetSubstanceUnits()) {
      if (jsbmlModel.getLevelAndVersion().compareTo(3, 0) >= 0) {
        // log error
        loggerErr.warning("Model " + jsbmlModel + ": isSetSubstanceUnits differ.");
      }
    } else if (libsbmlModel.isSetSubstanceUnits() && jsbmlModel.isSetSubstanceUnits()) {
      if (!jsbmlModel.getSubstanceUnits().equals(libsbmlModel.getSubstanceUnits())) {
        // log error
        loggerErr.warning("Model " + jsbmlModel + ": SubstanceUnits differ.");
        loggerErr.warning("jsbml substanceUnits = " + jsbmlModel.getSubstanceUnits() + ", libsbml = " + libsbmlModel.getSubstanceUnits());
      }
    }

    //		loggerErr.warning("jsbml substanceUnits = " + jsbmlModel.getSubstanceUnits() + ", libsbml = " + libsbmlModel.getSubstanceUnits());
    //		loggerErr.warning("jsbml substanceUnits instance = " + jsbmlModel.getSubstanceUnitsInstance());
    //		loggerErr.warning("jsbml timeUnits = " + jsbmlModel.getTimeUnits() + ", libsbml = " + libsbmlModel.getTimeUnits());
    //		loggerErr.warning("jsbml volumeUnits = " + jsbmlModel.getVolumeUnits() + ", libsbml = " + libsbmlModel.getVolumeUnits());
    //		loggerErr.warning("jsbml areaUnits = " + jsbmlModel.getAreaUnits() + ", libsbml = " + libsbmlModel.getAreaUnits());
    //		loggerErr.warning("jsbml lengthUnits = " + jsbmlModel.getLengthUnits() + ", libsbml = " + libsbmlModel.getLengthUnits());
    //		loggerErr.warning("jsbml extendsUnits = " + jsbmlModel.getExtentUnits() + ", libsbml = " + libsbmlModel.getExtentUnits());

    // timeUnits: UnitsSIdRef { use="optional" }
    if (jsbmlModel.isSetTimeUnits() && libsbmlModel.isSetTimeUnits()) {
      if (!jsbmlModel.getTimeUnits().equals(libsbmlModel.getTimeUnits())) {
        // log error
        loggerErr.warning("Model " + jsbmlModel + ": TimeUnits differ.");
      }
    } else if (libsbmlModel.isSetTimeUnits() != jsbmlModel.isSetTimeUnits()) {
      // log error
      loggerErr.warning("Model " + jsbmlModel + ": isSetTimeUnits differ.");
      loggerErr.warning("jsbml isSetTimeUnits = " + jsbmlModel.isSetSubstanceUnits() + ", libsbml = " + libsbmlModel.isSetSubstanceUnits());
    }

    // volumeUnits: UnitSIdRef { use="optional" }
    if (jsbmlModel.isSetVolumeUnits() && libsbmlModel.isSetVolumeUnits()) {
      if (!jsbmlModel.getVolumeUnits().equals(libsbmlModel.getVolumeUnits())) {
        // log error
        loggerErr.warning("Model " + jsbmlModel + ": VolumeUnits differ.");
      }
    } else if (libsbmlModel.isSetVolumeUnits() != jsbmlModel.isSetVolumeUnits()) {
      // log error
      loggerErr.warning("Model " + jsbmlModel + ": isSetVolumeUnits differ.");
    }

    // areaUnits: UnitsSIdRef { use="optional" }
    if (jsbmlModel.isSetAreaUnits() && libsbmlModel.isSetAreaUnits()) {
      if (!jsbmlModel.getAreaUnits().equals(libsbmlModel.getAreaUnits())) {
        // log error
        loggerErr.warning("Model " + jsbmlModel + ": AreaUnits differ.");
      }
    } else if (libsbmlModel.isSetAreaUnits() != jsbmlModel.isSetAreaUnits()) {
      // log error
      loggerErr.warning("Model " + jsbmlModel + ": isSetAreaUnits differ.");
    }

    // lengthUnits: UnitsSIdRef { use="optional" }
    if (jsbmlModel.isSetLengthUnits() && libsbmlModel.isSetLengthUnits()) {
      if (!jsbmlModel.getLengthUnits().equals(libsbmlModel.getLengthUnits())) {
        // log error
        loggerErr.warning("Model " + jsbmlModel + ": LengthUnits differ.");
      }
    } else if (libsbmlModel.isSetLengthUnits() != jsbmlModel.isSetLengthUnits()) {
      // log error
      loggerErr.warning("Model " + jsbmlModel + ": isSetLengthUnits differ.");
    }

    // extentUnits: UnitsSIdRef { use="optional" }
    if (jsbmlModel.isSetExtentUnits() && libsbmlModel.isSetExtentUnits()) {
      if (!jsbmlModel.getExtentUnits().equals(libsbmlModel.getExtentUnits())) {
        // log error
        loggerErr.warning("Model " + jsbmlModel + ": ExtentUnits differ.");
      }
    } else if (libsbmlModel.isSetExtentUnits() != jsbmlModel.isSetExtentUnits()) {
      // log error
      loggerErr.warning("Model " + jsbmlModel + ": isSetExtentUnits differ.");
    }

    // conversionFactor: SIdRef { use="optional" }
    if (jsbmlModel.isSetConversionFactor() && libsbmlModel.isSetConversionFactor()) {
      if (!jsbmlModel.getConversionFactor().equals(libsbmlModel.getConversionFactor())) {
        // log error
        loggerErr.warning("Model " + jsbmlModel + ": ConversionFactor differ.");
      }
    } else if (libsbmlModel.isSetConversionFactor() != jsbmlModel.isSetConversionFactor()) {
      // log error
      loggerErr.warning("Model " + jsbmlModel + ": isSetConversionFactor differ.");
    }
  }


  private static void compareEvent(Event jsbmlEvent, org.sbml.libsbml.Event libSbmlEvent) {

    compareSBase(jsbmlEvent, libSbmlEvent);
    compareNamedSBase(jsbmlEvent, libSbmlEvent);

    String eventId = jsbmlEvent.getMetaId(); // find a proper and valid/defined id like ?

    // trigger
    if (jsbmlEvent.isSetTrigger() && libSbmlEvent.isSetTrigger()) {
      Trigger jsbmlTrigger = jsbmlEvent.getTrigger();
      org.sbml.libsbml.Trigger libsbmlTrigger = libSbmlEvent.getTrigger();

      compareMathContainer(jsbmlTrigger, libsbmlTrigger.getMath());

      if (jsbmlTrigger.isSetInitialValue() && libsbmlTrigger.isSetInitialValue()) {
        if (jsbmlTrigger.getInitialValue() != libsbmlTrigger.getInitialValue()) {
          loggerErr.warning("Event " + eventId + ": trigger.initialValue differ !!");
        }
      } else if (jsbmlTrigger.isSetInitialValue() != libsbmlTrigger.isSetInitialValue()) {
        loggerErr.warning("Event " + eventId + ": trigger.isSetInitialValue differ !!");
      }

      if (jsbmlTrigger.isSetPersistent() && libsbmlTrigger.isSetPersistent()) {
        if (jsbmlTrigger.getPersistent() != libsbmlTrigger.getPersistent()) {
          loggerErr.warning("Event " + eventId + ": trigger.persistent differ !!");
        }
      } else if (jsbmlTrigger.isSetPersistent() != libsbmlTrigger.isSetPersistent()) {
        loggerErr.warning("Event " + eventId + ": trigger.isSetPersistent differ !!");
      }

    } else if (jsbmlEvent.isSetTrigger() != libSbmlEvent.isSetTrigger()) {
      loggerErr.warning("Event " + eventId + ": isSetTrigger differ !!");
    }
    // delay
    if (jsbmlEvent.isSetDelay() && libSbmlEvent.isSetDelay()) {
      compareMathContainer(jsbmlEvent.getDelay(), libSbmlEvent.getDelay().getMath());
    } else if (jsbmlEvent.isSetDelay() != libSbmlEvent.isSetDelay()) {
      loggerErr.warning("Event " + eventId + ": isSetDelay differ !!");
    }

    // timeUnits
    if (libSbmlEvent.isSetTimeUnits() && jsbmlEvent.isSetTimeUnits()) {
      if (!libSbmlEvent.getTimeUnits().equals(jsbmlEvent.getTimeUnits())) {
        // log error
        loggerErr.warning("Event " + eventId + ": TimeUnits differ.");
      }
    } else if (libSbmlEvent.isSetTimeUnits() != jsbmlEvent.isSetTimeUnits()) {
      // log error
      loggerErr.warning("Event " + eventId + ": isSetTimeUnits differ.");
    }

    // useValuesFromTriggerTime: For level lower than L2V4, libSBML will always return true !
    if (jsbmlEvent.getUseValuesFromTriggerTime() != libSbmlEvent.getUseValuesFromTriggerTime()) {
      loggerErr.warning("Event " + eventId + ": useValuesFromTriggerTime differ !!");
      loggerErr.warning("jsbml UVFTT = " + jsbmlEvent.getUseValuesFromTriggerTime() + ", libsbml = " + libSbmlEvent.getUseValuesFromTriggerTime());
    }
    if (jsbmlEvent.isSetUseValuesFromTriggerTime() != libSbmlEvent.isSetUseValuesFromTriggerTime()
        && jsbmlEvent.getLevelAndVersion().compareTo(2, 4) > 0)
    {
      loggerErr.warning("Event " + eventId + ": isSetUseValuesFromTriggerTime differ !!");
      loggerErr.warning("jsbml isSetUVFTT = " + jsbmlEvent.isSetUseValuesFromTriggerTime() + ", libsbml = " + libSbmlEvent.isSetUseValuesFromTriggerTime());
    }

    // priority
    if (jsbmlEvent.isSetPriority() && libSbmlEvent.isSetPriority()) {
      compareMathContainer(jsbmlEvent.getPriority(), libSbmlEvent.getPriority().getMath());
    } else if (jsbmlEvent.isSetPriority() != libSbmlEvent.isSetPriority()) {
      loggerErr.warning("Event " + eventId + ": isSetPriority differ !!");
    }


    // Compare EventAssignments
    if (jsbmlEvent.getListOfEventAssignments().size() == libSbmlEvent.getListOfEventAssignments().size()) {
      int i = 0;
      for (EventAssignment jsbmlEventAssignment: jsbmlEvent.getListOfEventAssignments()) {
        org.sbml.libsbml.EventAssignment libSbmlEventAssignment = libSbmlEvent.getEventAssignment(i);
        i++;

        loggerErr.fine("Testing original EventAssignment objects");

        compareEventAssignment(jsbmlEventAssignment, libSbmlEventAssignment);

        if (TEST_CLONING) {
          loggerErr.fine("Cloning jsbml object");
          compareEventAssignment(jsbmlEventAssignment.clone(), libSbmlEventAssignment);
          loggerErr.fine("Cloning both objects");
          compareEventAssignment(jsbmlEventAssignment.clone(), libSbmlEventAssignment.cloneObject());
        }
      }
    } else {
      loggerErr.warning("Event " + eventId + ": listOfEventAssignments differ in size !!");
      loggerErr.warning("jsbml size = " + jsbmlEvent.getListOfEventAssignments().size() + ", libsbml = " + libSbmlEvent.getListOfEventAssignments().size());
    }

  }

  private static void compareEventAssignment(EventAssignment jsbmlEventAssignment, org.sbml.libsbml.EventAssignment libSbmlEventAssignment) {

    compareSBase(jsbmlEventAssignment, libSbmlEventAssignment);
    compareMathContainer(jsbmlEventAssignment, libSbmlEventAssignment.getMath());

    String eventAssignementMetaId = jsbmlEventAssignment.getMetaId();
    // String eventId = jsbmlEventAssignment.getParent().getMetaId();

    // variable
    if (libSbmlEventAssignment.isSetVariable() && jsbmlEventAssignment.isSetVariable()) {
      if (!libSbmlEventAssignment.getVariable().equals(jsbmlEventAssignment.getVariable())) {
        // log error
        loggerErr.warning("EventAssignment " + eventAssignementMetaId + ": variable differ.");
        loggerErr.warning("EventAssignment jsbml variable = " + jsbmlEventAssignment.getVariable() + ": libsbml = " + libSbmlEventAssignment.getVariable());
      }
    } else if (libSbmlEventAssignment.isSetVariable() != jsbmlEventAssignment.isSetVariable()) {
      // log error
      loggerErr.warning("EventAssignment " + eventAssignementMetaId + ": isSetVariable differ.");
      loggerErr.warning("EventAssignment jsbml isSetVariable = " + jsbmlEventAssignment.isSetVariable() + ": libsbml = " + libSbmlEventAssignment.isSetVariable());
    }
  }

  private static void compareReaction(Reaction jsbmlReaction,	org.sbml.libsbml.Reaction libSbmlReaction) {

    compareSBase(jsbmlReaction, libSbmlReaction);
    compareNamedSBase(jsbmlReaction, libSbmlReaction);

    // fast
    if (libSbmlReaction.isSetFast() && jsbmlReaction.isSetFast()) {
      if (libSbmlReaction.getFast() != jsbmlReaction.getFast()) {
        // 	log error
        loggerErr.warning("Reaction " + libSbmlReaction.getId() + ": fast differ.");
      }
    } else if (libSbmlReaction.isSetFast() != jsbmlReaction.isSetFast()) {

    }

    // reversible
    if (libSbmlReaction.isSetReversible() != jsbmlReaction.isSetReversible()) {
      if (libSbmlReaction.getReversible() != jsbmlReaction.getReversible()) {
        // 	log error
        loggerErr.warning("Reaction " + libSbmlReaction.getId() + ": reversible differ.");
        loggerErr.warning("jsbml reversible = " + jsbmlReaction.getReversible() + ": libsbml = " + libSbmlReaction.getReversible());
      }
    } else if (libSbmlReaction.isSetReversible() != jsbmlReaction.isSetReversible()) {
      loggerErr.warning("Reaction " + libSbmlReaction.getId() + ": isSetReversible differ.");
    }

    // compartment
    if (libSbmlReaction.isSetCompartment() != jsbmlReaction.isSetCompartment()) {
      if (! libSbmlReaction.getCompartment().equals(jsbmlReaction.getCompartment())) {
        // 	log error
        loggerErr.warning("Reaction " + libSbmlReaction.getId() + ": compartment differ.");
      }
    } else if (libSbmlReaction.isSetCompartment() != jsbmlReaction.isSetCompartment()) {
      loggerErr.warning("Reaction " + libSbmlReaction.getId() + ": isSetCompartment differ.");
    }


    // Reactants
    if (jsbmlReaction.getListOfReactants().size() == libSbmlReaction.getListOfReactants().size()) {
      int i = 0;
      for (SpeciesReference jsbmlSpeciesRef : jsbmlReaction.getListOfReactants()) {
        org.sbml.libsbml.SpeciesReference libsbmlSpeciesRef = libSbmlReaction.getReactant(i);
        i++;

        compareSpeciesReferences(jsbmlSpeciesRef, libsbmlSpeciesRef);

        if (TEST_CLONING) {
          compareSpeciesReferences(jsbmlSpeciesRef.clone(), libsbmlSpeciesRef);
          compareSpeciesReferences(jsbmlSpeciesRef.clone(), libsbmlSpeciesRef.cloneObject());
        }
        libsbmlSpeciesRef.delete();
      }
    } else {
      loggerErr.warning("Reaction " + libSbmlReaction.getId() + ": nb reactants differ.");
    }

    // Products
    if (jsbmlReaction.getListOfProducts().size() == libSbmlReaction.getListOfProducts().size()) {
      int i = 0;
      for (SpeciesReference jsbmlSpeciesRef : jsbmlReaction.getListOfProducts()) {
        org.sbml.libsbml.SpeciesReference libsbmlSpeciesRef = libSbmlReaction.getProduct(i);
        i++;

        compareSpeciesReferences(jsbmlSpeciesRef, libsbmlSpeciesRef);

        if (TEST_CLONING) {
          compareSpeciesReferences(jsbmlSpeciesRef.clone(), libsbmlSpeciesRef);
          compareSpeciesReferences(jsbmlSpeciesRef.clone(), libsbmlSpeciesRef.cloneObject());
        }
        libsbmlSpeciesRef.delete();
      }
    } else {
      loggerErr.warning("Reaction " + libSbmlReaction.getId() + ": nb products differ.");
    }

    // Modifiers
    if (jsbmlReaction.getListOfModifiers().size() == libSbmlReaction.getListOfModifiers().size()) {
      int i = 0;
      for (ModifierSpeciesReference jsbmlModSpeciesRef : jsbmlReaction.getListOfModifiers()) {
        org.sbml.libsbml.ModifierSpeciesReference libSbmlModSpeciesRef = libSbmlReaction.getModifier(i);
        i++;

        compareSimpleSpeciesReferences(jsbmlModSpeciesRef, libSbmlModSpeciesRef);

        if (TEST_CLONING) {
          compareSimpleSpeciesReferences(jsbmlModSpeciesRef.clone(), libSbmlModSpeciesRef);
          compareSimpleSpeciesReferences(jsbmlModSpeciesRef.clone(),
            (org.sbml.libsbml.ModifierSpeciesReference) libSbmlModSpeciesRef.cloneObject());
        }
        libSbmlModSpeciesRef.delete();
      }
    } else {
      loggerErr.warning("Reaction " + libSbmlReaction.getId() + ": nb modifiers differ.");
    }

    // KineticLaw
    if (jsbmlReaction.isSetKineticLaw() && libSbmlReaction.isSetKineticLaw()) {
      compareKineticLaw(jsbmlReaction.getKineticLaw(), libSbmlReaction.getKineticLaw());
    } else if (jsbmlReaction.isSetKineticLaw() != libSbmlReaction.isSetKineticLaw()) {
      loggerErr.warning("Reaction " + jsbmlReaction.getId() + ": isSetKineticLaw differ.");
    }
  }

  private static void compareSimpleSpeciesReferences(SimpleSpeciesReference jsbmlSSpeciesRef,
    org.sbml.libsbml.SimpleSpeciesReference libSbmlSSpeciesRef)
  {
    compareSBase(jsbmlSSpeciesRef, libSbmlSSpeciesRef);

    // species
    if (libSbmlSSpeciesRef.isSetSpecies() && jsbmlSSpeciesRef.isSetSpecies()) {
      if (!libSbmlSSpeciesRef.getSpecies().equals(jsbmlSSpeciesRef.getSpecies())) {
        // log error
        loggerErr.warning("SimpleSpeciesReference " + jsbmlSSpeciesRef.getMetaId() + ": species differ.");
        loggerErr.warning("\t  jsbml = " + jsbmlSSpeciesRef.getSpecies() + ", libsbml = " + libSbmlSSpeciesRef.getSpecies());
      }
    } else if (libSbmlSSpeciesRef.isSetSpecies() != jsbmlSSpeciesRef.isSetSpecies()) {
      // log error
      loggerErr.warning("SimpleSpeciesReference " + jsbmlSSpeciesRef.getMetaId() + ": isSetSpecies differ.");
    }

  }

  private static void compareSpeciesReferences(SpeciesReference jsbmlSpeciesRef,
    org.sbml.libsbml.SpeciesReference libSbmlSpeciesRef)
  {
    compareSimpleSpeciesReferences(jsbmlSpeciesRef, libSbmlSpeciesRef);

    // StoichiometryMath
    if (jsbmlSpeciesRef.isSetStoichiometryMath() && libSbmlSpeciesRef.isSetStoichiometryMath()) {

      loggerErr.fine("Comparing originals StoichiometryMath");

      compareSBase(jsbmlSpeciesRef.getStoichiometryMath(), libSbmlSpeciesRef.getStoichiometryMath());

      compareMathContainer(jsbmlSpeciesRef.getStoichiometryMath(), libSbmlSpeciesRef.getStoichiometryMath().getMath());

      if (TEST_CLONING) {
        StoichiometryMath jsbmlStochioMMClone = jsbmlSpeciesRef.getStoichiometryMath().clone();
        org.sbml.libsbml.StoichiometryMath libSbmlStochioMMClone = libSbmlSpeciesRef.getStoichiometryMath().cloneObject();

        loggerErr.fine("Cloning jsbml StoichiometryMath");
        compareSBase(jsbmlStochioMMClone, libSbmlSpeciesRef.getStoichiometryMath());
        loggerErr.fine("Cloning both StoichiometryMath");
        compareMathContainer(jsbmlStochioMMClone, libSbmlSpeciesRef.getStoichiometryMath().getMath());

        compareSBase(jsbmlStochioMMClone, libSbmlStochioMMClone);
        compareMathContainer(jsbmlStochioMMClone, libSbmlStochioMMClone.getMath());
      }
    } else if (jsbmlSpeciesRef.isSetStoichiometryMath() && libSbmlSpeciesRef.isSetStoichiometryMath()) {
      loggerErr.warning("SpeciesReference " + jsbmlSpeciesRef.getMetaId() + ": isSetStoichiometryMath differ.");
    }

    // Stoichiometry ==> isSetStoichiometry() differ in libsbml and jsbml if there is a default value in the specs, libsbml return always true
    if (libSbmlSpeciesRef.getLevel() > 2 && libSbmlSpeciesRef.isSetStoichiometry() != jsbmlSpeciesRef.isSetStoichiometry()) {
      loggerErr.warning("SpeciesReference '" + jsbmlSpeciesRef.getMetaId() + "':'" + jsbmlSpeciesRef.getSpecies() + "': isSetStoichiometry differ.");
      loggerErr.warning("\t  jsbml = " + jsbmlSpeciesRef.isSetStoichiometry() + ", libsbml = " + libSbmlSpeciesRef.isSetStoichiometry());
    }
    if (libSbmlSpeciesRef.getStoichiometry() != jsbmlSpeciesRef.getStoichiometry()) {

      if (Double.isNaN(libSbmlSpeciesRef.getStoichiometry()) && Double.isNaN(jsbmlSpeciesRef.getStoichiometry())) {
        loggerErr.finest("SpeciesReference: both stoichiometry is NaN.");
      } else {
        loggerErr.warning("SpeciesReference " + jsbmlSpeciesRef.getMetaId() + "':'" + jsbmlSpeciesRef.getSpecies() + "': stoichiometry differ.");
        loggerErr.warning("\t  jsbml = " + jsbmlSpeciesRef.getStoichiometry() + ", libsbml = " + libSbmlSpeciesRef.getStoichiometry());
      }
    }

    // Denominator - SBML level 1 attribute
    // not isSetDenominator in libsbml 4.x or 5.0
    if (libSbmlSpeciesRef.getDenominator() != jsbmlSpeciesRef.getDenominator()) {
      loggerErr.warning("SpeciesReference " + jsbmlSpeciesRef.getMetaId() + ": denominator differ.");
      loggerErr.warning("\t  jsbml = " + jsbmlSpeciesRef.getDenominator() + ", libsbml = " + libSbmlSpeciesRef.getDenominator());
    }

    // constant - SBML level 3 attribute
    if (libSbmlSpeciesRef.isSetConstant() != jsbmlSpeciesRef.isSetConstant()) {
      loggerErr.warning("SpeciesReference " + jsbmlSpeciesRef.getMetaId() + "':'" + jsbmlSpeciesRef.getSpecies() + "': isSetConstant differ.");
      loggerErr.warning("\t  jsbml = " + jsbmlSpeciesRef.isSetConstant() + ", libsbml = " + libSbmlSpeciesRef.isSetConstant());
    } else if (libSbmlSpeciesRef.isSetConstant() && jsbmlSpeciesRef.isSetConstant()) {
      if (libSbmlSpeciesRef.getConstant() != jsbmlSpeciesRef.getConstant()) {
        loggerErr.warning("SpeciesReference " + jsbmlSpeciesRef.getMetaId() + "':'" + jsbmlSpeciesRef.getSpecies() + "': getConstant differ.");
        loggerErr.warning("\t  jsbml = " + jsbmlSpeciesRef.getConstant() + ", libsbml = " + libSbmlSpeciesRef.getConstant());
      }
    }
  }

  private static void compareKineticLaw(KineticLaw jsbmlKineticLaw, org.sbml.libsbml.KineticLaw libSbmlKineticLaw) {

    String reactionId = jsbmlKineticLaw.getParent().getId();
    // loggerErr.warning("KineticLaw (" + reactionId + ")");

    compareSBase(jsbmlKineticLaw, libSbmlKineticLaw);
    compareMathContainer(jsbmlKineticLaw, libSbmlKineticLaw.getMath());

    // timeUnits
    if (libSbmlKineticLaw.isSetTimeUnits() && jsbmlKineticLaw.isSetTimeUnits()) {
      if (!(libSbmlKineticLaw.getTimeUnits().equals(jsbmlKineticLaw.getTimeUnits()))) {
        loggerErr.warning("KineticLaw " + reactionId + ": timeUnits differ.");
      }
    } else if (libSbmlKineticLaw.isSetTimeUnits() != jsbmlKineticLaw.isSetTimeUnits()) {
      loggerErr.warning("KineticLaw " + reactionId + ": isSetTimeUnits differ.");
    }

    // substanceUnits
    if (libSbmlKineticLaw.isSetSubstanceUnits() && jsbmlKineticLaw.isSetSubstanceUnits()) {
      if (!(libSbmlKineticLaw.getSubstanceUnits().equals(jsbmlKineticLaw.getSubstanceUnits()))) {
        loggerErr.warning("KineticLaw " + reactionId + ": substanceUnits differ.");
      }
    } else if (libSbmlKineticLaw.isSetSubstanceUnits() != jsbmlKineticLaw.isSetSubstanceUnits()) {
      loggerErr.warning("KineticLaw " + reactionId + ": isSetSubstanceUnits differ.");
    }

    // compare list of parameters
    if (jsbmlKineticLaw.getNumParameters() == libSbmlKineticLaw.getNumParameters()) {
      int i = 0;
      for (LocalParameter jsbmlParameter : jsbmlKineticLaw.getListOfParameters()) {
        org.sbml.libsbml.Parameter libSbmlParameter = libSbmlKineticLaw.getParameter(i);
        i++;

        compareParameter(jsbmlParameter, libSbmlParameter);

        if (TEST_CLONING) {
          compareParameter(jsbmlParameter.clone(), libSbmlParameter);
          compareParameter(jsbmlParameter.clone(), libSbmlParameter.cloneObject());
        }
      }
    } else {
      loggerErr.warning("KineticLaw " + reactionId + ": nb parameters differ.");
    }

  }

  private static void compareConstraint(Constraint jsbmlConstraint,	org.sbml.libsbml.Constraint libSbmlConstraint) {

    compareSBase(jsbmlConstraint, libSbmlConstraint);
    compareMathContainer(jsbmlConstraint, libSbmlConstraint.getMath());

    // compare message
    if (libSbmlConstraint.isSetMessage()) {

      String jsbmlMessageString = SBMLtools.toXML(jsbmlConstraint.getMessage());
      String libSbmlMessageString = libSbmlConstraint.getMessageString();

      if (jsbmlMessageString == null) {
        jsbmlMessageString = "";
      }
      boolean notesAreEquals = compareString(jsbmlMessageString, libSbmlMessageString);

      if (!notesAreEquals) {
        loggerErr.warning("\nSBase String getMessage differ.\n");
        loggerErr.warning("jsbml = \n@" + jsbmlMessageString + "@");
        loggerErr.warning("libsbml = \n@" + libSbmlMessageString + "@");
      }

      compareXMLNode(jsbmlConstraint.getMessage(), libSbmlConstraint.getMessage());
    }
  }

  private static void compareRule(Rule jsbmlRule,	org.sbml.libsbml.Rule libSbmlRule) {


    compareSBase(jsbmlRule, libSbmlRule);
    // compareNamedSBase(jsbmlRule, libSbmlRule);
    compareMathContainer(jsbmlRule, libSbmlRule.getMath());

    if (jsbmlRule instanceof ExplicitRule) {
      ExplicitRule jsbmlExplicitRule = (ExplicitRule) jsbmlRule;

      // loggerErr.warning("Rule on " + jsbmlExplicitRule.getVariable());

      // Variable
      if (libSbmlRule.isSetVariable() && jsbmlExplicitRule.isSetVariable()) {
        if (!libSbmlRule.getVariable().equals(jsbmlExplicitRule.getVariable())) {
          // log error
          loggerErr.warning("Rule " + jsbmlExplicitRule.getVariable() + ": variable differ.");
        }
      } else if (libSbmlRule.isSetVariable() != jsbmlExplicitRule.isSetVariable()) {
        // log error
        loggerErr.warning("EventAssignment " + jsbmlExplicitRule.getVariable()  + ": isSetVariable differ.");
      }
    }
  }

  private static void compareInitialAssignment(InitialAssignment jsbmlInitialAssignment,
    org.sbml.libsbml.InitialAssignment libSbmlInitialAssignment)
  {

    compareSBase(jsbmlInitialAssignment, libSbmlInitialAssignment);
    // compareNamedSBase(jsbmlInitialAssignment, libSbmlInitialAssignment);
    compareMathContainer(jsbmlInitialAssignment, libSbmlInitialAssignment.getMath());

    // symbol
    if (libSbmlInitialAssignment.isSetSymbol() && jsbmlInitialAssignment.isSetVariable()) {
      if (!libSbmlInitialAssignment.getSymbol().equals(jsbmlInitialAssignment.getSymbol())) {
        // log error
        loggerErr.warning("EventAssignment " + jsbmlInitialAssignment.getMetaId() + ": symbol/variable differ.");
      }
    } else if (libSbmlInitialAssignment.isSetSymbol() != jsbmlInitialAssignment.isSetSymbol()) {
      // log error
      loggerErr.warning("EventAssignment " + jsbmlInitialAssignment.getMetaId() + ": isSetSymbol differ.");
    }

  }

  private static void compareParameter(QuantityWithUnit jsbmlParameter, org.sbml.libsbml.Parameter libSbmlParameter) {

    compareSBase(jsbmlParameter, libSbmlParameter);
    compareNamedSBase(jsbmlParameter, libSbmlParameter);

    // value
    if (libSbmlParameter.isSetValue() && jsbmlParameter.isSetValue()) {
      if (libSbmlParameter.getValue() != jsbmlParameter.getValue()) {
        // log error
        if (Double.isNaN(libSbmlParameter.getValue()) && Double.isNaN(jsbmlParameter.getValue())) {
          // fine
        } else {
          loggerErr.warning("Parameter " + libSbmlParameter.getId() + ": value differ.");
        }
      }
    } else if (libSbmlParameter.isSetValue() != jsbmlParameter.isSetValue()) {
      // log error
      loggerErr.warning("Parameter " + libSbmlParameter.getId() + ": isSetValue differ.");
      loggerErr.warning(" libsbml isSetValue = " + libSbmlParameter.isSetValue() + ", jsbml = " + jsbmlParameter.isSetValue());
    }

    // units
    if (libSbmlParameter.isSetUnits() && jsbmlParameter.isSetUnits()) {
      if (!libSbmlParameter.getUnits().equals(jsbmlParameter.getUnits())) {
        // log error
        loggerErr.warning("Parameter " + libSbmlParameter.getId() + ": units differ.");
      }
    } else if (libSbmlParameter.isSetUnits() != jsbmlParameter.isSetUnits()) {
      // log error
      loggerErr.warning("Parameter " + libSbmlParameter.getId() + ": isSetUnits differ.");
    }

    // constant
    if (jsbmlParameter instanceof Parameter) {
      Parameter jsbmlPar = (Parameter) jsbmlParameter;

      /*
       * isSetConstant() vary in libsbml and jsbml so no need to test it
       * 
       * 
			if (libSbmlParameter.isSetConstant() != jsbmlPar.isSetConstant()) {
				loggerErr.warning("Parameter " + libSbmlParameter.getId() + ": isSetConstant differ.");
				loggerErr.warning(" libsbml isSetConstant = " + libSbmlParameter.isSetConstant() + ", jsbml = " + jsbmlPar.isSetConstant());
			}
       */

      if (libSbmlParameter.getLevel() > 1) { // in level 1 libsbml return always true and jsbml always false
        if (libSbmlParameter.getConstant() != jsbmlPar.getConstant()) {
          // log error
          loggerErr.warning("Parameter " + libSbmlParameter.getId() + ": constant differ.");
          loggerErr.warning(" libsbml constant = " + libSbmlParameter.getConstant() + ", jsbml = " + jsbmlPar.getConstant());
        }
      }
    }
  }

  private static void compareSpeciesType(SpeciesType jsbmlSpeciesType, org.sbml.libsbml.SpeciesType libSbmlSpeciesType) {

    compareSBase(jsbmlSpeciesType, libSbmlSpeciesType);
    compareNamedSBase(jsbmlSpeciesType, libSbmlSpeciesType);
  }

  private static void compareUnitDefinition(UnitDefinition jsbmlUnitDefinition, org.sbml.libsbml.UnitDefinition libSbmlUnitDefinition) {

    compareSBase(jsbmlUnitDefinition, libSbmlUnitDefinition);
    compareNamedSBase(jsbmlUnitDefinition, libSbmlUnitDefinition);

    // Compare list of Units
    if (libSbmlUnitDefinition.getNumUnits() == jsbmlUnitDefinition.getNumUnits()) {
      int i = 0;
      for (Unit jsbmlUnit : jsbmlUnitDefinition.getListOfUnits()) {
        org.sbml.libsbml.Unit libSbmlUnit = libSbmlUnitDefinition.getUnit(i);
        i++;

        compareUnit(jsbmlUnit, libSbmlUnit);

        if (TEST_CLONING) {
          compareUnit(jsbmlUnit.clone(), libSbmlUnit);
          compareUnit(jsbmlUnit.clone(), libSbmlUnit.cloneObject());
        }
      }
    } else {
      loggerErr.warning("UnitDefinition " + libSbmlUnitDefinition.getId() + ": nb units differ.");
    }
  }

  private static void compareUnit(Unit jsbmlUnit,	org.sbml.libsbml.Unit libSbmlUnit) {

    compareSBase(jsbmlUnit, libSbmlUnit);
    // compareNamedSBase(jsbmlUnitDefinition, libSbmlUnitDefinition);

    // kind
    if (libSbmlUnit.isSetKind() && jsbmlUnit.isSetKind()) {
      if (!(libsbml.UnitKind_toString(libSbmlUnit.getKind())).toUpperCase().equals(jsbmlUnit.getKind().toString())) {
        // log error
        loggerErr.warning("Unit " + jsbmlUnit.getKind() + ": kind differ.");
      }
    } else if (libSbmlUnit.isSetKind() != jsbmlUnit.isSetKind()) {
      // log error
      loggerErr.warning("Unit " + jsbmlUnit.getKind() + ": isSetKind differ.");
    }

    // exponent
    // no isSetXXX for exponent, scale, multiplier or offset in libsbml 4.0
    if (libSbmlUnit.getExponent() != jsbmlUnit.getExponent()) {
      loggerErr.warning("Unit " + jsbmlUnit.getKind() + ": exponent differ.");
    }

    // scale
    if (libSbmlUnit.getScale() != jsbmlUnit.getScale()) {
      loggerErr.warning("Unit " + jsbmlUnit.getKind() + ": scale differ.");
    }

    // multiplier
    if (libSbmlUnit.getMultiplier() != jsbmlUnit.getMultiplier()) {
      loggerErr.warning("Unit " + jsbmlUnit.getKind() + ": multiplier differ.");
    }

    // offset
    if (libSbmlUnit.getOffset() != jsbmlUnit.getOffset()) {
      loggerErr.warning("Unit " + jsbmlUnit.getKind() + ": offset differ.");
    }

  }

  private static void compareFunctionDefinition(FunctionDefinition jsbmlFunctionDefinition,
    org.sbml.libsbml.FunctionDefinition libSbmlFunctionDefinition)
  {
    compareSBase(jsbmlFunctionDefinition, libSbmlFunctionDefinition);
    compareNamedSBase(jsbmlFunctionDefinition, libSbmlFunctionDefinition);
    compareMathContainer(jsbmlFunctionDefinition, libSbmlFunctionDefinition.getMath());

  }

  private static void compareCompartmentType(CompartmentType jsbmlCompartmentType,
    org.sbml.libsbml.CompartmentType libSbmlCompartmentType)
  {

    compareSBase(jsbmlCompartmentType, libSbmlCompartmentType);
    compareNamedSBase(jsbmlCompartmentType, libSbmlCompartmentType);
  }

  private static void compareCompartment(Compartment jsbmlCompartment, org.sbml.libsbml.Compartment libSbmlCompartment) {

    compareSBase(jsbmlCompartment, libSbmlCompartment);
    compareNamedSBase(jsbmlCompartment, libSbmlCompartment);

    if (libSbmlCompartment.isSetCompartmentType() && jsbmlCompartment.isSetCompartmentType()) {
      if (!libSbmlCompartment.getCompartmentType().equals(jsbmlCompartment.getCompartmentType())) {
        // log error
        loggerErr.warning("Compartment " + libSbmlCompartment.getId() + ": compartmentType differ.");
      }
    } else if (libSbmlCompartment.isSetCompartmentType() != jsbmlCompartment.isSetCompartmentType()) {
      // log error
      loggerErr.warning("Compartment " + libSbmlCompartment.getId() + ": isSetCompartmentType differ.");
    }

    // spatialDimensions
    /*
     * isSetSpatialDimensions() vary in libsbml and jsbml so no need to test it
     * 
     * 

		if (libSbmlCompartment.isSetSpatialDimensions() != jsbmlCompartment.isSetSpatialDimensions()) {
			// log error
			loggerErr.warning("Compartment " + libSbmlCompartment.getId() + ": isSetSpatialDimensions differ.");
			loggerErr.warning("Compartment: libsbml isSetSD = " + libSbmlCompartment.isSetSpatialDimensions() + ", jsbml isSetSD: " + jsbmlCompartment.isSetSpatialDimensions());

		}
     */

    if (libSbmlCompartment.getLevel() < 3) {
      if (libSbmlCompartment.getSpatialDimensions() != jsbmlCompartment.getSpatialDimensions()) {
        // log error
        loggerErr.warning("Compartment " + libSbmlCompartment.getId() + ": spatialDimensions differ.");
        loggerErr.warning("Compartment: libsbml SD = " + libSbmlCompartment.getSpatialDimensions() + ", jsbml SD: "
            + jsbmlCompartment.getSpatialDimensions());
      }
    } else {
      if (libSbmlCompartment.getSpatialDimensionsAsDouble() != jsbmlCompartment.getSpatialDimensions()) {
        if (Double.isNaN(libSbmlCompartment.getSpatialDimensionsAsDouble()) && Double.isNaN(jsbmlCompartment.getSpatialDimensionsAsDouble())) {
          // everything is good
        } else {
          // 	log error
          loggerErr.warning("Compartment " + libSbmlCompartment.getId() + ": spatialDimensions differ.");
          loggerErr.warning("Compartment: libsbml SD double = " + libSbmlCompartment.getSpatialDimensionsAsDouble() +
            ", jsbml SD: " + jsbmlCompartment.getSpatialDimensions());
        }
      }
    }

    // size
    if (libSbmlCompartment.isSetSize() && jsbmlCompartment.isSetSize()) {
      if (libSbmlCompartment.getSize() != jsbmlCompartment.getSize()) {
        // log error
        loggerErr.warning("Compartment " + libSbmlCompartment.getId() + ": size differ.");
      }
    } else if (libSbmlCompartment.isSetSize() != jsbmlCompartment.isSetSize()) {
      // log error
      loggerErr.warning("Compartment " + libSbmlCompartment.getId() + ": isSetSize differ.");
      loggerErr.warning("Compartment: libsbml isSetSize = " + libSbmlCompartment.isSetSize() + ", jsbml = " + jsbmlCompartment.isSetSize());
      loggerErr.warning("Compartment: libsbml size = " + libSbmlCompartment.getSize() + ", jsbml = " + jsbmlCompartment.getSize());
    }

    // units
    if (libSbmlCompartment.isSetUnits() && jsbmlCompartment.isSetUnits()) {
      if (!libSbmlCompartment.getUnits().equals(jsbmlCompartment.getUnits())) {
        // log error
        loggerErr.warning("Compartment " + libSbmlCompartment.getId() + ": units differ.");
      }
    } else if (libSbmlCompartment.isSetUnits() != jsbmlCompartment.isSetUnits()) {
      // log error
      loggerErr.warning("Compartment " + libSbmlCompartment.getId() + ": isSetUnits differ.");
    }

    // outside
    if (libSbmlCompartment.isSetOutside() && jsbmlCompartment.isSetOutside()) {
      if (!libSbmlCompartment.getOutside().equals(jsbmlCompartment.getOutside())) {
        // log error
        loggerErr.warning("Compartment " + libSbmlCompartment.getId() + ": outside differ.");
      }
    } else if (libSbmlCompartment.isSetOutside() != jsbmlCompartment.isSetOutside()) {
      // log error
      loggerErr.warning("Compartment " + libSbmlCompartment.getId() + ": isSetOutside differ.");
    }

    // constant
    /*
     * it vary in libsbml and jsbml so no need to test it
     * 
     *
		if (libSbmlCompartment.isSetConstant() && jsbmlCompartment.isSetConstant()) {
		} else
		if (libSbmlCompartment.isSetConstant() != jsbmlCompartment.isSetConstant()) {
			// log error
			loggerErr.warning("Compartment " + libSbmlCompartment.getId() + ": isSetConstant differ.");
			loggerErr.warning("Compartment: libsbml isSetconstant = " + libSbmlCompartment.isSetConstant() + ", jsbml isSetconstant: " + jsbmlCompartment.isSetConstant());
		}

     */

    if (! (libSbmlCompartment.getConstant() == jsbmlCompartment.getConstant())) {
      // log error
      loggerErr.warning("Compartment " + libSbmlCompartment.getId() + ": constant differ.");
      loggerErr.warning("Compartment: libsbml constant = " + libSbmlCompartment.getConstant() + ", jsbml constant: " + jsbmlCompartment.getConstant());
    }

  }

  private static void compareSpecies(Species jsbmlSpecies, org.sbml.libsbml.Species libSbmlSpecies) {

    compareSBase(jsbmlSpecies, libSbmlSpecies);
    compareAnnotation(jsbmlSpecies, libSbmlSpecies);
    compareNamedSBase(jsbmlSpecies, libSbmlSpecies);

    if (libSbmlSpecies.isSetSpeciesType() && jsbmlSpecies.isSetSpeciesType()) {
      if (!libSbmlSpecies.getSpeciesType().equals(jsbmlSpecies.getSpeciesType())) {
        // log error
        loggerErr.warning("Species " + libSbmlSpecies.getId() + ": speciesType differ.");
      }
    } else if (libSbmlSpecies.isSetSpeciesType() != jsbmlSpecies.isSetSpeciesType()) {
      // log error
      loggerErr.warning("Species " + libSbmlSpecies.getId() + ": isSetSpeciesType differ.");
      loggerErr.warning("jsbml isSetSpeciesType = " + jsbmlSpecies.isSetSpeciesType() + ", libsbml = " + libSbmlSpecies.isSetSpeciesType());
      loggerErr.warning("jsbml speciesType = " + jsbmlSpecies.getSpeciesType() + ", libsbml = " + libSbmlSpecies.getSpeciesType());
    }

    if (libSbmlSpecies.isSetCompartment() && jsbmlSpecies.isSetCompartment()) {
      if (!libSbmlSpecies.getCompartment().equals(jsbmlSpecies.getCompartment())) {
        // log error
        loggerErr.warning("Species " + libSbmlSpecies.getId() + ": compartment differ.");
      }
    } else if (libSbmlSpecies.isSetCompartment() != jsbmlSpecies.isSetCompartment()) {
      // log error
      loggerErr.warning("Species " + libSbmlSpecies.getId() + ": isSetCompartment differ.");
    }

    // initialAmount
    if (libSbmlSpecies.isSetInitialAmount() && jsbmlSpecies.isSetInitialAmount()) {
      if (! (libSbmlSpecies.getInitialAmount() == jsbmlSpecies.getInitialAmount())) {
        // log error
        loggerErr.warning("Species " + libSbmlSpecies.getId() + ": initialAmount differ.");
      }
    } else if (libSbmlSpecies.isSetInitialAmount() != jsbmlSpecies.isSetInitialAmount()) {
      // log error
      loggerErr.warning("Species " + libSbmlSpecies.getId() + ": isSetInitialAmount differ.");
    }

    // initialConcentration
    if (libSbmlSpecies.isSetInitialConcentration() && jsbmlSpecies.isSetInitialConcentration()) {
      if (! (libSbmlSpecies.getInitialConcentration() == jsbmlSpecies.getInitialConcentration())) {
        // log error
        loggerErr.warning("Species " + libSbmlSpecies.getId() + ": initialConcentration differ.");
      }
    } else if (libSbmlSpecies.isSetInitialConcentration() != jsbmlSpecies.isSetInitialConcentration()) {
      // log error
      loggerErr.warning("Species " + libSbmlSpecies.getId() + ": isSetInitialConcentration differ.");
    }

    // substanceUnits
    if (libSbmlSpecies.isSetSubstanceUnits() && jsbmlSpecies.isSetSubstanceUnits()) {
      if (!libSbmlSpecies.getSubstanceUnits().equals(jsbmlSpecies.getSubstanceUnits())) {
        // log error
        loggerErr.warning("Species " + libSbmlSpecies.getId() + ": substanceUnits differ.");
      }
    } else if (libSbmlSpecies.isSetSubstanceUnits() != jsbmlSpecies.isSetSubstanceUnits()) {
      // log error
      loggerErr.warning("Species " + libSbmlSpecies.getId() + ": isSetSpeciesType differ.");
    }

    // hasOnlySubstanceUnits
    // No isSetHasOnlySubstanceUnits in libsbml
    if (! (libSbmlSpecies.getHasOnlySubstanceUnits() == jsbmlSpecies.getHasOnlySubstanceUnits())) {
      // log error
      loggerErr.warning("Species " + libSbmlSpecies.getId() + ": hasOnlySubstanceUnits differ.");
    }

    // boundaryCondition
    // No isSetBoundaryCondition in libsbml
    if (! (libSbmlSpecies.getBoundaryCondition() == jsbmlSpecies.getBoundaryCondition())) {
      // log error
      loggerErr.warning("Species " + libSbmlSpecies.getId() + ": boundaryCondition differ.");
    }

    // charge
    if (libSbmlSpecies.isSetCharge() && jsbmlSpecies.isSetCharge()) {
      if (! (libSbmlSpecies.getCharge() == jsbmlSpecies.getCharge())) {
        // log error
        loggerErr.warning("Species " + libSbmlSpecies.getId() + ": charge differ.");
      }
    } else if (libSbmlSpecies.isSetCharge() != jsbmlSpecies.isSetCharge()) {
      // log error
      loggerErr.warning("Species " + libSbmlSpecies.getId() + ": isSetCharge differ.");
    }

    // constant
    // libSBML.Species.isSetConstant always return true for models <= L2V4
    if (! (libSbmlSpecies.getConstant() == jsbmlSpecies.getConstant())) {
      // log error
      loggerErr.warning("Species " + libSbmlSpecies.getId() + ": constant differ.");
    }

    // conversionFactor
    if (jsbmlSpecies.isSetConversionFactor() && libSbmlSpecies.isSetConversionFactor()) {
      if (! jsbmlSpecies.getConversionFactor().equals(libSbmlSpecies.getConversionFactor())) {
        loggerErr.warning("Species " + libSbmlSpecies.getId() + ": conversionFactor differ.");
      }
    } else if (jsbmlSpecies.isSetConversionFactor() != libSbmlSpecies.isSetConversionFactor()) {
      loggerErr.warning("Species " + libSbmlSpecies.getId() + ": isSetConversionFactor differ.");
      loggerErr.warning("jsbml isSetConversionFactor = " + jsbmlSpecies.isSetConversionFactor() + ": libsbml = " + libSbmlSpecies.isSetConversionFactor());
    }

  }

  private static void compareAnnotation(SBase jsbmlSBase, org.sbml.libsbml.SBase libSbmlSBase) {

    // if (libSbmlSBase.isSetAnnotation()) {
    // loggerErr.warning("SBase " + jsbmlSBase.getMetaId() + ": jsbml annotation: @" + jsbmlSBase.getAnnotation().getNonRDFannotation() + "@");
    // loggerErr.warning("SBase " + libSbmlSBase.getMetaId() + ": libSBML annotation: @" + libSbmlSBase.getAnnotationString() + "@");

    loggerErr.fine("SBase comparing Annotation: " + libSbmlSBase.getElementName() + " (" + libSbmlSBase.getId() + ")");

    if (jsbmlSBase instanceof Model && jsbmlSBase.isSetHistory() && jsbmlSBase.getHistory().getListOfModifiedDates().size() > 1) {
      jsbmlSBase.getHistory().getListOfModifiedDates().remove(1);
    }

    // loggerErr.info("SBase jsbml Non RDF annotation:\n@" + jsbmlSBase.getAnnotation().getNonRDFannotation() + "@");

    String jsbmlAnnotStr = "";
    try {
      jsbmlAnnotStr = jsbmlSBase.getAnnotationString();
    } catch (XMLStreamException exc) {
    }
    String libsbmlAnnotStr = libSbmlSBase.getAnnotationString();

    boolean annotationsAreEquals = true;

    if (!(jsbmlSBase instanceof Model)) {
      annotationsAreEquals = compareString(jsbmlAnnotStr, libsbmlAnnotStr);
    }

    if (!annotationsAreEquals && !(jsbmlSBase instanceof Model)) {
      loggerErr.warning("SBase Annotation Strings are different");
      loggerErr.info("SBase comparing Annotation: libSbml annotationStr = \n@" + libsbmlAnnotStr + "@\n");
      loggerErr.info("SBase comparing Annotation: jSbml annotationStr = \n@" + jsbmlAnnotStr + "@\n");
    } else if (! jsbmlAnnotStr.equals(libsbmlAnnotStr) && !(jsbmlSBase instanceof Model)) {
      loggerErr.fine("SBase Annotation Strings differ by some whitespaces");
    }

    if (jsbmlSBase instanceof Model && jsbmlSBase.isSetHistory() && libSbmlSBase.isSetModelHistory())
    {
      // TODO: compare History

    } else if (jsbmlSBase.isSetHistory() != libSbmlSBase.isSetModelHistory())
    {
      loggerErr.warning(jsbmlSBase.getElementName() + " " + libSbmlSBase.getId() + ": isSetHistory() differ.");
      loggerErr.warning("jsbml isSetHistory = " + jsbmlSBase.isSetHistory() + ", libsbml " + libSbmlSBase.isSetModelHistory());

      if (libSbmlSBase instanceof org.sbml.libsbml.Model) {
        // loggerErr.warning("libsbml created date = " + libSbmlSBase.getModelHistory().getCreatedDate());
      }
    }

    if (jsbmlSBase.getNumCVTerms() == libSbmlSBase.getNumCVTerms()) {

      // compare each CVTerm
      int i = 0;
      for (CVTerm jsbmlCVTerm : jsbmlSBase.getCVTerms()) {
        org.sbml.libsbml.CVTerm libsbmlCVTerm = libSbmlSBase.getCVTerm(i);
        compareCVTerm(jsbmlCVTerm, libsbmlCVTerm);
        i++;
      }

    } else if (libSbmlSBase.getNumCVTerms() > 0) {
      loggerErr.warning("SBase " + libSbmlSBase.getMetaId() + ": nb CVTerm differ.");
    }
  }

  private static void compareCVTerm(CVTerm jsbmlCVTerm, org.sbml.libsbml.CVTerm libsbmlCVTerm) {

    if (jsbmlCVTerm.getNumResources() == libsbmlCVTerm.getNumResources()) {

      // TODO : compare the qualifier

      int i = 0;
      for (String jsbmlResourceURI : jsbmlCVTerm.getResources()) {
        String libsbmlResourceURI = libsbmlCVTerm.getResourceURI(i);

        if (!jsbmlResourceURI.equals(libsbmlResourceURI)) {
          loggerErr.warning("SBase " + libsbmlResourceURI + ": annotation differ.");
        }
        i++;
      }
    }

  }

  private static void compareSBase(SBase jsbmlSbase, org.sbml.libsbml.SBase libSbmlSbase)
  {

    // TODO : other stuffs to test, like additional namespaces declarations, extensions objects ??

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

    // notes
    compareNotes(jsbmlSbase, libSbmlSbase);

    // annotation
    compareAnnotation(jsbmlSbase, libSbmlSbase);

  }

  private static void compareNotes(SBase jsbmlSbase, org.sbml.libsbml.SBase libSbmlSbase) {

    String jsbmlNotesString = SBMLtools.toXML(jsbmlSbase.getNotes());
    String libSbmlNotesString = libSbmlSbase.getNotesString();

    if (jsbmlNotesString == null) {
      jsbmlNotesString = "";
    }
    boolean notesAreEquals = true; // TODO: remove comment !! compareString(jsbmlNotesString, libSbmlNotesString);

    // After the changes from Andreas the Notes are totally different, no way to compare them ...

    if (!notesAreEquals) {
      loggerErr.warning("\nSBase String getNotes differ.\n");
      loggerErr.warning("jsbml = \n@" + jsbmlNotesString + "@");
      loggerErr.warning("libsbml = \n@" + libSbmlNotesString + "@");
    } else if (!jsbmlNotesString.equals(libSbmlNotesString)) {
      // TODO: remove comment !! loggerErr.fine("\nSBase String getNotes differ by some white spaces.\n");
    }

    if (jsbmlSbase.isSetNotes() == libSbmlSbase.isSetNotes() && jsbmlSbase.isSetNotes()) {

      loggerErr.fine("SBase comparing notes " + jsbmlSbase.getMetaId());

      compareXMLNode(jsbmlSbase.getNotes(), libSbmlSbase.getNotes().getChild(0));

    } else if (jsbmlSbase.isSetNotes() != libSbmlSbase.isSetNotes()) {
      loggerErr.warning("SBase String isSetNotes differ.");
    }

  }

  /**
   * Compares two String line by line, removing any trailing white spaces.
   * 
   * @param jsbmlString
   * @param libSbmlString
   * @return {@code true} if the String are equals, ignoring white spaces at the beginning or the end of each lines
   */
  private static boolean compareString(String jsbmlString, String libSbmlString)
  {
    boolean equal = true;

    String[] jsbmlLines = jsbmlString.split("\n");
    String[] libSbmlLines = libSbmlString.split("\n");

    if (jsbmlLines.length != libSbmlLines.length) {
      loggerErr.warning("compareString: the two String have a different number of lines: " + jsbmlLines.length +
        ", " + libSbmlLines.length);
      return false;
    }

    int i = 0;
    int nbDiff = 0;

    for (String jsbmlLine : jsbmlLines) {
      String libSbmlLine = libSbmlLines[i];

      if (jsbmlLine.contains("rdf:RDF")) {
        // the namespaces are written in a different order in jsbml and libsbml
        i++;
        continue;
      }

      if (!jsbmlLine.trim().equals(libSbmlLine.trim())) {
        loggerErr.warning("compareStrings: the notes or annotation have a different line: \n@" + jsbmlLine +
          "@\n@" + libSbmlLine + "@");
        equal = false;
        nbDiff++;
        // break;
      }

      i++;
    }

    if (!equal) {
      loggerErr.warning("compareNotes: there is " + nbDiff + " different line(s)");
    }

    return equal;
  }

  private static void compareXMLNode(XMLNode jsbmlXMLNode, org.sbml.libsbml.XMLNode libSbmlXMLNode) {

    if (jsbmlXMLNode.isStart()) {
      loggerErr.fine("XMLNode: element name = " + jsbmlXMLNode.getName());
    } else if (jsbmlXMLNode.isText()) {
      loggerErr.fine("XMLNode: text element  = $" + jsbmlXMLNode.getCharacters() + "$");
    } else {
      loggerErr.fine("XMLNode: element is neither start or text !!!");
    }

    // attributesEmpty
    if (jsbmlXMLNode.isAttributesEmpty() != libSbmlXMLNode.isAttributesEmpty()) {
      loggerErr.fine("XMLNode: isAttributesEmpty() differ");
    }

    // isStartElement
    if (jsbmlXMLNode.isStart() != libSbmlXMLNode.isStart()) {
      loggerErr.fine("XMLNode: isStart() differ");
    }

    // isEndElement
    if (jsbmlXMLNode.isEnd() != libSbmlXMLNode.isEnd()) {
      loggerErr.fine("XMLNode: isEnd() differ");
    }

    // isText
    if (jsbmlXMLNode.isText() != libSbmlXMLNode.isText()) {
      loggerErr.fine("XMLNode: isText() differ");
    }

    // isElement
    if (jsbmlXMLNode.isElement() != libSbmlXMLNode.isElement()) {
      loggerErr.fine("XMLNode: isElement() differ");
    }

    // namespacesEmpty
    if (jsbmlXMLNode.isNamespacesEmpty() != libSbmlXMLNode.isNamespacesEmpty()) {
      loggerErr.fine("XMLNode: isNamespacesEmpty() differ");
    }

    // eof
    if (jsbmlXMLNode.isEOF() != libSbmlXMLNode.isEOF()) {
      loggerErr.fine("XMLNode: isStart() differ");
    }

    // isEndFor ???

    // Characters
    if (!jsbmlXMLNode.getCharacters().equals(libSbmlXMLNode.getCharacters())) {
      loggerErr.fine("XMLNode: characters differ");
    }

    // Compare namespaces
    if (jsbmlXMLNode.getNamespacesLength() == libSbmlXMLNode.getNamespacesLength()) {
      XMLNamespaces jsbmlNamespaces = jsbmlXMLNode.getNamespaces();
      org.sbml.libsbml.XMLNamespaces libSbmlNamespaces = libSbmlXMLNode.getNamespaces();

      if (jsbmlNamespaces != null) {
        for (int i = 0; i < jsbmlNamespaces.getLength(); i++) {

          if (!jsbmlNamespaces.getPrefix(i).equals(libSbmlNamespaces.getPrefix(i))) {
            loggerErr.fine("XMLNode: namespace prefix differ");
          }
          if (!jsbmlNamespaces.getURI(i).equals(libSbmlNamespaces.getURI(i))) {
            loggerErr.fine("XMLNode: namespace uri differ");
          }
        }
      }
    } else {
      loggerErr.fine("XMLNode: namespace length differ");
    }

    // Compare attributes
    if (jsbmlXMLNode.getAttributesLength() == libSbmlXMLNode.getAttributesLength()) {
      XMLAttributes jsbmlAttributes = jsbmlXMLNode.getAttributes();
      org.sbml.libsbml.XMLAttributes libSbmlAttributes = libSbmlXMLNode.getAttributes();

      for (int i = 0; i < jsbmlAttributes.getLength(); i++) {

        if (!jsbmlAttributes.getName(i).equals(libSbmlAttributes.getName(i))) {
          loggerErr.fine("XMLNode: attribute name differ");
        }
        if (!jsbmlAttributes.getPrefix(i).equals(libSbmlAttributes.getPrefix(i))) {
          loggerErr.fine("XMLNode: attribute prefix differ");
        }
        if (!jsbmlAttributes.getURI(i).equals(libSbmlAttributes.getURI(i))) {
          loggerErr.fine("XMLNode: attribute uri differ");
        }
        if (!jsbmlAttributes.getValue(i).equals(libSbmlAttributes.getValue(i))) {
          loggerErr.fine("XMLNode: attribute value differ");
        }
      }
    } else {
      loggerErr.fine("XMLNode: attribute length differ");
    }

    // Compare XMLNode Children
    if (jsbmlXMLNode.getNumChildren() == libSbmlXMLNode.getNumChildren()) {

      for (int i = 0; i < jsbmlXMLNode.getNumChildren(); i++) {
        compareXMLNode(jsbmlXMLNode.getChildAt(i), libSbmlXMLNode.getChild(i));
      }
    } else {
      loggerErr.fine("XMLNode: children length differ: jsbml = " + jsbmlXMLNode.getNumChildren() + ", libsbml = " + libSbmlXMLNode.getNumChildren());
      displayLisbmlXMLNode(libSbmlXMLNode);
    }

  }

  private static void displayLisbmlXMLNode(org.sbml.libsbml.XMLNode libSbmlXMLNode) {

    loggerErr.fine("LXMLNode: element name = " + libSbmlXMLNode.getName());

    for (int i = 0; i < libSbmlXMLNode.getNumChildren(); i++) {
      loggerErr.fine("LXMLNode: " + libSbmlXMLNode.getName() + " child " + i);
      displayLisbmlXMLNode(libSbmlXMLNode.getChild(i));
    }

  }


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

  // loggerErr.warning("jsbml.isSetName = " + jsbmlSbase.isSetName() + ", libsbml.isSetName = " + libSbmlSbase.isSetName());


  private static void compareMathContainer(MathContainer jsbmlMathContainer,	org.sbml.libsbml.ASTNode libSbmlASTNode) {

    if (!TEST_MATHML_ASTNODE) {
      loggerErr.fine("Not Testing the mathContainer !!");
      return;
    }
    //compare both ASTNode tree
    if (jsbmlMathContainer.getLevel() > 1 || loggerErr.isLoggable(Level.FINE)) {
      compareASTNode(jsbmlMathContainer.getMath(), libSbmlASTNode);
    }

    // compare also the generated mathML string and infix formula
    try {
      String libsbmlMathML = libsbml.writeMathMLToString(libSbmlASTNode);
      String jsbmlMathML = jsbmlMathContainer.getMath().toMathML().replace("'", "\"");
      // jsbmlMathML = jsbmlMathML.replace(".0 ", " ");

      if (!jsbmlMathML.equals(libsbmlMathML)) {
        if (jsbmlMathContainer.getLevel() > 1 || loggerErr.isLoggable(Level.FINE)) {
          loggerErr.warning("MathContainer: " + jsbmlMathContainer.getMetaId() + " mathML output differ.");
          loggerErr.warning("JSBML mathML: \n" + jsbmlMathML);
          loggerErr.warning("libSBML mathML: \n" + libsbmlMathML);
        }

        if (jsbmlMathContainer.getLevel() > 1 || loggerErr.isLoggable(Level.FINE)) {

          StringWriter diffOutout = new StringWriter();
          DiffXConfig diffXConfig = new DiffXConfig();
          diffXConfig.setNamespaceAware(false);
          com.topologi.diffx.Main.diff(new StringReader(jsbmlMathML), new StringReader(libsbmlMathML), diffOutout, diffXConfig);

          loggerErr.warning(" DiffX: \n");
          loggerErr.finest("DiffX file = @" + diffOutout.toString() + "@");

          BufferedReader diffOutputReader = new BufferedReader(new StringReader(diffOutout.toString()));
          while (diffOutputReader.ready()) {
            String line = diffOutputReader.readLine();
            if (line == null) {
              break;
            }
            if (line.indexOf("<del>") != -1 || line.indexOf("<ins>") != -1
                || line.indexOf("del:") != -1 || line.indexOf("ins:") != -1)
            {
              loggerErr.warning(line);
            }
          }
          loggerErr.warning("\n End DiffX: \n");
        }
      }
    }  catch (DiffXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (RuntimeException e) {
      loggerErr.warning("Exception occured when creating the mathML output: " + e.getMessage());
      e.printStackTrace();
    }

    String libsbmlInfix = libsbml.formulaToString(libSbmlASTNode).replace(" ", "");

    try {
      String jsbmlInfix = jsbmlMathContainer.getMath().toString().replace(" ", "");

      loggerErr.info("math output = " + jsbmlInfix);
      loggerErr.info("latex output = " + jsbmlMathContainer.getMath().toLaTeX());

      if (!jsbmlInfix.equals(libsbmlInfix)) {

        if (jsbmlInfix.indexOf('<') == -1 && jsbmlInfix.indexOf('>') == -1 && jsbmlInfix.indexOf('=') == -1) {
          loggerErr.fine("MathContainer: " + jsbmlMathContainer.getMetaId() + " infix formula output differ.");
          loggerErr.fine("JSBML formula: @" + jsbmlInfix + "@");
          loggerErr.fine("libSBML formula: @" + libsbmlInfix + "@");
        } else {
          //					loggerErr.warning("MathContainer: " + jsbmlMathContainer.getMetaId() + " infix formula relational output differ.");
          // loggerErr.warning("JSBML formula: \n" + jsbmlInfix);
          // loggerErr.warning("libSBML formula: \n" + libsbmlInfix);
        }
      }
    } catch (SBMLException e) {
      e.printStackTrace();
    } catch (RuntimeException e) {
      loggerErr.warning("Exception occured when creating an infix formula: " + e.getMessage());
    }

    try {
      String jsbmlInfix = jsbmlMathContainer.getMath().compile(new LibSBMLFormulaCompiler()).toString().replace(" ", "");

      if (!jsbmlInfix.equals(libsbmlInfix)) {

        if (libsbmlInfix.indexOf("+-") != -1 || jsbmlInfix.indexOf("(-1)") != -1) {
          return;
        }
        if (libsbmlInfix.startsWith("(") && libsbmlInfix.startsWith(")")) {
          return;
        }

        if (jsbmlInfix.indexOf('<') == -1 && jsbmlInfix.indexOf('>') == -1 && jsbmlInfix.indexOf('=') == -1) {
          loggerErr.warning("MathContainer: " + jsbmlMathContainer.getMetaId() + " jlibSBML infix formula output differ.");
          loggerErr.warning("JSBML formula: @" + jsbmlInfix + "@");
          loggerErr.warning("libSBML formula: @" + libsbmlInfix + "@");
        } else {
          //					loggerErr.warning("MathContainer: " + jsbmlMathContainer.getMetaId() + " infix formula relational output differ.");
          // loggerErr.warning("JSBML formula: \n" + jsbmlInfix);
          // loggerErr.warning("libSBML formula: \n" + libsbmlInfix);
        }
      }
    } catch (SBMLException e) {
      e.printStackTrace();
    } catch (RuntimeException e) {
      loggerErr.warning("Exception occured when creating an infix formula: " + e.getMessage());
    }
  }

  private static void compareASTNode(ASTNode jsbmlASTNode, org.sbml.libsbml.ASTNode libSbmlASTNode) {

    if (jsbmlASTNode == null && libSbmlASTNode != null) {
      loggerErr.warning("ASTNode: jsbml ASTNode is null !!!");
      return;
    }
    // compare values

    // type
    // Not possible to compare the type easily
    // loggerErr.warning("\nASTNode - jsbml ASTNode = " + jsbmlASTNode + ", libsbml = " + libSbmlASTNode);
    // loggerErr.warning("\nASTNode - jsbml type = " + jsbmlASTNode.getType().toString() + ", libsbml = " + libSbmlASTNode.getType());

    // character
    if (jsbmlASTNode.isOperator() && libSbmlASTNode.isOperator() && jsbmlASTNode.getCharacter() != libSbmlASTNode.getCharacter()) {
      loggerErr.warning("ASTNode: getCharacter differ !!!");
    }

    // definitionURL
    // not existing in jsbml


    // denominator
    if (jsbmlASTNode.isRational() && libSbmlASTNode.isRational()
        && jsbmlASTNode.getDenominator() != libSbmlASTNode.getDenominator())
    {
      loggerErr.warning("ASTNode: getDenominator differ !!!");
    }


    // exponent
    if ((jsbmlASTNode.getType() == Type.REAL || jsbmlASTNode.getType() == Type.REAL_E)
        && jsbmlASTNode.getExponent() != libSbmlASTNode.getExponent())
    {
      if (Double.isNaN(jsbmlASTNode.getExponent()) && Double.isNaN(libSbmlASTNode.getExponent())) {
        // nothing
      } else {
        loggerErr.warning("ASTNode: getExponent differ !!!");
      }
    }

    // integer
    if (jsbmlASTNode.isInteger() && jsbmlASTNode.getInteger() != libSbmlASTNode.getInteger()) {
      loggerErr.warning("ASTNode: getInteger differ !!!");
    }

    // mantissa
    if ((jsbmlASTNode.getType() == Type.REAL || jsbmlASTNode.getType() == Type.REAL_E)
        && jsbmlASTNode.getMantissa() != libSbmlASTNode.getMantissa())
    {
      if (Double.isNaN(jsbmlASTNode.getMantissa()) && Double.isNaN(libSbmlASTNode.getMantissa())) {
        // nothing
      } else {
        loggerErr.warning("ASTNode: getMantissa differ !!!");
      }
    }

    // name
    if ((!(jsbmlASTNode.isOperator() || jsbmlASTNode.isNumber()))
        && jsbmlASTNode.getName() != null && libSbmlASTNode.getName() != null
        && !(jsbmlASTNode.getName().equals(libSbmlASTNode.getName())))
    {
      loggerErr.warning("ASTNode: getName differ !!!");
      loggerErr.warning("ASTNode: jsbml name = " + jsbmlASTNode.getName() + ", libsbml = " + libSbmlASTNode.getName());
    }

    // numerator
    if (jsbmlASTNode.isRational() && jsbmlASTNode.getNumerator() != libSbmlASTNode.getNumerator()) {
      loggerErr.warning("ASTNode: getNumerator differ !!!");
    }

    // precedence (level 1)
    // Does not exist in jsbml

    // real
    if (jsbmlASTNode.isReal() && !jsbmlASTNode.isRational() && jsbmlASTNode.getReal() != libSbmlASTNode.getReal())
    {
      if (Double.isNaN(jsbmlASTNode.getReal()) && Double.isNaN(libSbmlASTNode.getReal())) {
        // nothing
      } else {
        loggerErr.warning("ASTNode: getReal differ !!!");
        loggerErr.warning("ASTNode: jsbml getReal = " + jsbmlASTNode.getReal() + ", libsbml = " + libSbmlASTNode.getReal());
      }
    }

    // semantic-annotation
    // not implemented on jsbml, need to get some examples models

    // hasCorrectNumberArguments
    // not present in jsbml


    if (jsbmlASTNode.isBoolean() != libSbmlASTNode.isBoolean()) {
      loggerErr.warning("ASTNode: isBoolean differ !!!");
      loggerErr.warning("ASTNode: jsbml isBoolean = " + jsbmlASTNode.isBoolean() + ", libsbml = " + libSbmlASTNode.isBoolean());
    }

    // isConstant
    if (jsbmlASTNode.isConstant() != libSbmlASTNode.isConstant()) {
      loggerErr.warning("ASTNode: isConstant differ !!!");
    }

    // isFunction
    if (jsbmlASTNode.isFunction() != libSbmlASTNode.isFunction()) {
      loggerErr.warning("ASTNode: isFunction differ !!!");
      loggerErr.warning("ASTNode: jsbml isFunction = " + jsbmlASTNode.isFunction() + ", libsbml = " + libSbmlASTNode.isFunction());
      loggerErr.warning("ASTNode: jsbml functionName = " + jsbmlASTNode.getName() + ", libsbml = " + libSbmlASTNode.getName());
    }

    // isInfinity
    if (jsbmlASTNode.isInfinity() != libSbmlASTNode.isInfinity()) {
      loggerErr.warning("ASTNode: isInfinity differ !!!");
    }

    // isInteger
    if (jsbmlASTNode.isInteger() != libSbmlASTNode.isInteger()) {
      loggerErr.warning("ASTNode: isInteger differ !!!");
    }

    // isLambda
    if (jsbmlASTNode.isLambda() != libSbmlASTNode.isLambda()) {
      loggerErr.warning("ASTNode: isLambda differ !!!");
    }

    // isLog10
    if (jsbmlASTNode.isLog10() != libSbmlASTNode.isLog10()) {
      loggerErr.warning("ASTNode: isLog10 differ !!!");
    }

    // isLogical
    if (jsbmlASTNode.isLogical() != libSbmlASTNode.isLogical()) {
      loggerErr.warning("ASTNode: isLogical differ !!!");
    }

    // isName
    if (jsbmlASTNode.isName() != libSbmlASTNode.isName()) {
      loggerErr.warning("ASTNode: isName differ !!!");
    }

    // isNaN
    if (jsbmlASTNode.isNaN() != libSbmlASTNode.isNaN()) {
      loggerErr.warning("ASTNode: isNaN differ !!!");
    }

    // isNeqInfinity
    if (jsbmlASTNode.isNegInfinity() != libSbmlASTNode.isNegInfinity()) {
      loggerErr.warning("ASTNode: isNeqInfinity differ !!!");
    }

    // isNumber
    if (jsbmlASTNode.isNumber() != libSbmlASTNode.isNumber()) {
      loggerErr.warning("ASTNode: isNumber differ !!!");
    }

    // isOperator
    if (jsbmlASTNode.isOperator() != libSbmlASTNode.isOperator()) {
      loggerErr.warning("ASTNode: isOperator differ !!!");
      loggerErr.warning("ASTNode: jsbml isOperator = " + jsbmlASTNode.isOperator() + ", libsbml = " + libSbmlASTNode.isOperator());
    }

    // isPiecewise
    if (jsbmlASTNode.isPiecewise() != libSbmlASTNode.isPiecewise()) {
      loggerErr.warning("ASTNode: isPiecewise differ !!!");
    }

    // isRational
    if (jsbmlASTNode.isRational() != libSbmlASTNode.isRational()) {
      loggerErr.warning("ASTNode: isRational differ !!!");
    }

    // isReal
    if (jsbmlASTNode.isReal() != libSbmlASTNode.isReal()) {
      loggerErr.warning("ASTNode: isReal differ !!!");
    }

    // isRelational
    if (jsbmlASTNode.isRelational() != libSbmlASTNode.isRelational()) {
      loggerErr.warning("ASTNode: isRelational differ !!!");
    }

    // isSqrt
    if (jsbmlASTNode.isSqrt() != libSbmlASTNode.isSqrt()) {
      loggerErr.warning("ASTNode: isSqrt differ !!!");
    }

    // isUMinus
    if (jsbmlASTNode.isUMinus() != libSbmlASTNode.isUMinus()) {
      loggerErr.warning("ASTNode: isUMinus differ !!!");
    }

    // isUnknown
    if (jsbmlASTNode.isUnknown() != libSbmlASTNode.isUnknown()) {
      loggerErr.warning("ASTNode: isUnknown differ !!!");
    }

    // isWellFormedASTNode
    // not existing in jsbml

    if (jsbmlASTNode.getNumChildren() == libSbmlASTNode.getNumChildren()) {

      int i = 0;
      for (ASTNode jsbmlChild : jsbmlASTNode.getChildren()) {
        compareASTNode(jsbmlChild, libSbmlASTNode.getChild(i));
        i++;
      }

    } else {
      // TODO: would need to make a function ASTNode.toBinaryTree() to be able to compare the tree easily
      // otherwise, the case where they are differences in size seems correct each time.

      // loggerErr.warning("ASTNode: num children differ !!! ");
      // loggerErr.warning("ASTNode: jsbml nb child = " + jsbmlASTNode.getNumChildren() + ", libsbml = " + libSbmlASTNode.getNumChildren());
    }

  }


}

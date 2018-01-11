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
package org.sbml.jsbml.xml.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.sbml.jsbml.test.sbml.TestCVTerms;
import org.sbml.jsbml.test.sbml.TestCompartment;
import org.sbml.jsbml.test.sbml.TestEvent;
import org.sbml.jsbml.test.sbml.TestL3Parameter;
import org.sbml.jsbml.test.sbml.TestModel;
import org.sbml.jsbml.test.sbml.TestParameter;
import org.sbml.jsbml.test.sbml.TestReaction;
import org.sbml.jsbml.test.sbml.TestReadFromFile1;
import org.sbml.jsbml.test.sbml.TestReadFromFile5;
import org.sbml.jsbml.test.sbml.TestSpecies;

/**
 * JUnit suite of tests, including test classes imported from libSBML with as less changes as possible.
 * 
 * <p>Every changes should be commented in the test classes. 
 * 
 * @author Nicolas Rodriguez
 * @since 0.8
 */
@RunWith(value=Suite.class)
@SuiteClasses(value={TestCompartment.class, TestSpecies.class, TestParameter.class, TestL3Parameter.class,
  TestReaction.class, TestEvent.class, TestModel.class, TestReadFromFile1.class, TestCVTerms.class,
  TestReadFromFile5.class})
public class LibsbmlCompatibilityTests {

}

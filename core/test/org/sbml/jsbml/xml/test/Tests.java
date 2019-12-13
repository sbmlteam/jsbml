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
import org.sbml.jsbml.math.test.ASTNodeInfixParsingTest;
import org.sbml.jsbml.math.test.ASTNodeTest;
import org.sbml.jsbml.math.test.TestInfixOperatorPrecedence;
import org.sbml.jsbml.test.IdRegistrationTest;
import org.sbml.jsbml.test.RemoveFromParentTest;

/**
 * JUnit suite of tests, including all test classes for SBML core.
 * 
 * @author Nicolas Rodriguez
 * @since 0.8
 */
@RunWith(value=Suite.class)
@SuiteClasses(value={SBML_L1VxTests.class, SBML_L2V1Test.class, CheckConsistencyTests.class, GetNotesStringTests.class,
  UnregisterTests.class, RemoveFromParentTest.class, CVTermTests.class, RemoveFromParentTest.class, ASTNodeTest.class,
  ASTNodeInfixParsingTest.class, TestInfixOperatorPrecedence.class, IdRegistrationTest.class, XMLTokenTest.class,
  CreatorTests.class, NestedCVTermTests.class, XXEInjectionTests.class, LibsbmlCompatibilityTests.class})
public class Tests {

}

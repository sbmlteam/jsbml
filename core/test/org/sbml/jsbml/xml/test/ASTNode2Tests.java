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
 * 6. The University of Toronto, Toronto, ON, Canada
 * 
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation. A copy of the license agreement is provided 
 * in the file named "LICENSE.txt" included with this software distribution 
 * and also available online as <http://sbml.org/Software/JSBML/License>. 
 * ---------------------------------------------------------------------------- 
 */
package org.sbml.jsbml.xml.test;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.sbml.jsbml.math.test.ASTArithmeticOperatorNodeTest;
import org.sbml.jsbml.math.test.ASTBinaryFunctionNodeTest;
import org.sbml.jsbml.math.test.ASTBooleanTest;
import org.sbml.jsbml.math.test.ASTCSymbolAvogadroNodeTest;
import org.sbml.jsbml.math.test.ASTCSymbolDelayNodeTest;
import org.sbml.jsbml.math.test.ASTCSymbolTimeNodeTest;
import org.sbml.jsbml.math.test.ASTCiFunctionNodeTest;
import org.sbml.jsbml.math.test.ASTCiNumberNodeTest;
import org.sbml.jsbml.math.test.ASTCnExponentialNodeTest;
import org.sbml.jsbml.math.test.ASTCnIntegerNodeTest;
import org.sbml.jsbml.math.test.ASTCnNumberNodeTest;
import org.sbml.jsbml.math.test.ASTCnRationalNodeTest;
import org.sbml.jsbml.math.test.ASTCnRealNodeTest;
import org.sbml.jsbml.math.test.ASTConstantNumberTest;
import org.sbml.jsbml.math.test.ASTDivideNodeTest;
import org.sbml.jsbml.math.test.ASTFactoryTest;
import org.sbml.jsbml.math.test.ASTFunctionTest;
import org.sbml.jsbml.math.test.ASTHyperbolicNodeTest;
import org.sbml.jsbml.math.test.ASTLambdaFunctionTest;
import org.sbml.jsbml.math.test.ASTLogarithmNodeTest;
import org.sbml.jsbml.math.test.ASTLogicalOperatorNodeTest;
import org.sbml.jsbml.math.test.ASTMinusNodeTest;
import org.sbml.jsbml.math.test.ASTNodeInfixParsingTest;
import org.sbml.jsbml.math.test.ASTNodeTest;
import org.sbml.jsbml.math.test.ASTPiecewiseFunctionNodeTest;
import org.sbml.jsbml.math.test.ASTPlusNodeTest;
import org.sbml.jsbml.math.test.ASTPowerNodeTest;
import org.sbml.jsbml.math.test.ASTQualifierNodeTest;
import org.sbml.jsbml.math.test.ASTRelationalOperatorNodeTest;
import org.sbml.jsbml.math.test.ASTRootNodeTest;
import org.sbml.jsbml.math.test.ASTTimesNodeTest;
import org.sbml.jsbml.math.test.ASTTrigonometricNodeTest;
import org.sbml.jsbml.math.test.ASTUnaryFunctionNodeTest;

/**
 * JUnit suite of tests for the new math package. 
 * 
 * @author Victor Kofia
 * @since 1.0
 */
@RunWith(value=Suite.class)
@SuiteClasses(value={ASTArithmeticOperatorNodeTest.class,
                     ASTBinaryFunctionNodeTest.class,
                     ASTBooleanTest.class,
                     ASTCiFunctionNodeTest.class,
                     ASTCiNumberNodeTest.class,
                     ASTCnExponentialNodeTest.class,
                     ASTCnIntegerNodeTest.class,
                     ASTCnNumberNodeTest.class,
                     ASTCnRationalNodeTest.class,
                     ASTCnRealNodeTest.class,
                     ASTConstantNumberTest.class,
                     ASTCSymbolAvogadroNodeTest.class,
                     ASTCSymbolDelayNodeTest.class,
                     ASTCSymbolTimeNodeTest.class,
                     ASTDivideNodeTest.class,
                     ASTFactoryTest.class,
                     ASTFunctionTest.class,
                     ASTHyperbolicNodeTest.class,
                     ASTLambdaFunctionTest.class,
                     ASTLogarithmNodeTest.class,
                     ASTLogicalOperatorNodeTest.class,
                     ASTMinusNodeTest.class,
                     ASTNodeInfixParsingTest.class,
                     ASTNodeTest.class,
                     ASTPiecewiseFunctionNodeTest.class,
                     ASTPlusNodeTest.class,
                     ASTPowerNodeTest.class,
                     ASTQualifierNodeTest.class,
                     ASTRelationalOperatorNodeTest.class,
                     ASTRootNodeTest.class,
                     ASTTimesNodeTest.class,
                     ASTTrigonometricNodeTest.class,
                     ASTUnaryFunctionNodeTest.class})
public class ASTNode2Tests {

  @Test
  public void test() {
    fail("Not yet implemented");
  }

}

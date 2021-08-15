/*
 *
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
package org.sbml.jsbml.test.sbml;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.*;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.util.ModelBuilder;


/**
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class TestUnitSimplification {

  /**
   * 
   */
  private static final int level = 3;
  /**
   * SBML version used in {}
   */
  private static final int version = 2;

  /**
   * Mol per litre
   */
  UnitDefinition molPerL_ud;
  UnitDefinition molPerL_ud2;
  /**
   * Litre per mole
   */
  UnitDefinition lPerMol_ud;
  /**
   * Micro litre
   */
  UnitDefinition muL_ud;
  /**
   * Hour
   */
  UnitDefinition h_ud;
  /**
   * Dimensionless
   */
  UnitDefinition dimless_ud;
  /**
   * Invalid
   */
  UnitDefinition invalid_ud;
  /**
   * Empty
   */
  UnitDefinition empty_ud;
  /**
   * Containing dimensionless unit
   */
  UnitDefinition contDimless_ud;
  /**
   * Only dimensionless units
   */
  UnitDefinition mulDimless_ud;
  /**
   * Containing one invalid
   */
  UnitDefinition oneInvalid_ud;
  /**
   * Containing two times the same kind of unit (minute)
   */
  UnitDefinition twoNormalSame_ud;
  /**
   * Containing two times the different kind of unit
   */
  UnitDefinition twoNormalDiff_ud;
  /**
   * Containing three units of the same kind (litre)
   */
  UnitDefinition threeNormalSame_ud;
  /**
   * Containing three different kinds of units
   */
  UnitDefinition threeNormalDiff_ud;
  /**
   * Containing two same and one different unit
   */
  UnitDefinition twoSameOneDiff_ud;
  /**
   *
   * First complex unit definition with more than three units:
   * (0.45 * min * l * mol * s^2 * l) / l
   *
   */
  UnitDefinition complex_ud1;
  /**
   * Second complex unit definition with more than three units:
   * (6.75 * h * μmol * μl^3*l) / (mol*ml^2*l)
   */
  UnitDefinition complex_ud2;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    Locale.setDefault(Locale.ENGLISH);

    // Definition of general Units

    // mol
    Unit mol = new Unit(1d, 0, Kind.MOLE, 1d, level, version);
    // 1 / mol
    Unit invMole = mol.clone();
    invMole.setExponent(-1d);
    // litre
    Unit litre = new Unit(1d, 0, Kind.LITRE, 1d, level, version);
    // 1 / l
    Unit invLitre = litre.clone();
    invLitre.setExponent(-1d);
    // micro litre
    Unit muL = new Unit(1d, -6, Kind.LITRE, 1d, level, version);
    // hour
    Unit h = new Unit(3600d, 0, Kind.SECOND, 1d, level, version);
    // minutes
    Unit min = new Unit(60d, 0, Kind.SECOND, 1d, level, version);
    // micro mol
    Unit muMol = new Unit(1d, -6, Kind.MOLE, 1d, level, version);
    // milli litre
    Unit ml = new Unit(1d, -3, Kind.LITRE, 1d, level, version);
    // seconds
    Unit s = new Unit(1d, 0, Kind.SECOND, 1d, level, version);
    // 4*dimensionless
    Unit dimless4 = new Unit(4d, 0, Kind.DIMENSIONLESS, 1d, level, version);
    // 0.45*dimensionless
    Unit dimless045 = new Unit(0.45,0, Kind.DIMENSIONLESS, 1d, level, version);
    // 6.75*dimensionless
    Unit dimless675 = new Unit(6.75, 0, Kind.DIMENSIONLESS, 1d, level, version);
    // invalid
    Unit invalid = new Unit(5d, 5, Kind.INVALID, 1d, level, version);
    // seconds squared
    Unit sSquared = s.clone();
    sSquared.setExponent(2d);
    // mu litre cubed
    Unit muLitreCubed = muL.clone();
    muLitreCubed.setExponent(3d);
    // 1 / ml^2
    Unit invMilliLitreSquared = ml.clone();
    invMilliLitreSquared.setExponent(-2d);


    //Setting values of Unit Definitions later used in testing

    UnitDefinition baseUD = new UnitDefinition();
    baseUD.setLevel(level);
    baseUD.setVersion(version);

    // mol per l
    molPerL_ud = baseUD.clone();
    molPerL_ud.addUnit(mol.clone());
    molPerL_ud.addUnit(invLitre.clone());

    // mol per l
    molPerL_ud2 = baseUD.clone();
    molPerL_ud2.addUnit(invLitre.clone());
    molPerL_ud2.addUnit(mol.clone());

    // micro litre
    muL_ud = baseUD.clone();
    muL_ud.addUnit(muL.clone());

    // dimensionless
    dimless_ud = baseUD.clone();
    dimless_ud.addUnit(dimless4.clone());

    // invalid
    invalid_ud = baseUD.clone();
    invalid_ud.addUnit(invalid.clone());

    // l / mol
    lPerMol_ud = baseUD.clone();
    lPerMol_ud.addUnit(litre.clone());
    lPerMol_ud.addUnit(invMole.clone());

    // hour
    h_ud = baseUD.clone();
    h_ud.addUnit(h.clone());

    //empty
    empty_ud = baseUD.clone();

    // containing one dimensionless
    contDimless_ud = baseUD.clone();
    contDimless_ud.addUnit(dimless4.clone());
    contDimless_ud.addUnit(min.clone());

    // multiple dimensionless
    mulDimless_ud = baseUD.clone();
    mulDimless_ud.addUnit(dimless4.clone());
    mulDimless_ud.addUnit(dimless4.clone());

    // containing one invalid unit
    oneInvalid_ud = baseUD.clone();
    oneInvalid_ud.addUnit(h.clone());
    oneInvalid_ud.addUnit(invalid.clone());

    // containing two times same kind of unit (minute)
    twoNormalSame_ud = baseUD.clone();
    twoNormalSame_ud.addUnit(min.clone());
    twoNormalSame_ud.addUnit(min.clone());

    // containing two different kind of units
    twoNormalDiff_ud = baseUD.clone();
    twoNormalDiff_ud.addUnit(min.clone());
    twoNormalDiff_ud.addUnit(invLitre.clone());

    // containing three times same unit (litre)
    threeNormalSame_ud = baseUD.clone();
    threeNormalSame_ud.addUnit(litre.clone());
    threeNormalSame_ud.addUnit(invLitre.clone());
    threeNormalSame_ud.addUnit(litre.clone());

    // containing three times a different unit
    threeNormalDiff_ud = baseUD.clone();
    threeNormalDiff_ud.addUnit(litre.clone());
    threeNormalDiff_ud.addUnit(min.clone());
    threeNormalDiff_ud.addUnit(muMol.clone());

    // containing two times the same unit (minute) and one different unit
    twoSameOneDiff_ud = baseUD.clone();
    twoSameOneDiff_ud.addUnit(muL.clone());
    twoSameOneDiff_ud.addUnit(min.clone());
    twoSameOneDiff_ud.addUnit(litre.clone());

    // first complex unit definition with more than 3 units
    complex_ud1 = baseUD.clone();
    complex_ud1.addUnit(min.clone());
    complex_ud1.addUnit(litre.clone());
    complex_ud1.addUnit(mol.clone());
    complex_ud1.addUnit(invLitre.clone());
    complex_ud1.addUnit(sSquared.clone());
    complex_ud1.addUnit(litre.clone());
    complex_ud1.addUnit(dimless045);

    // second complex unit definition with more than 3 units
    complex_ud2 = baseUD.clone();
    complex_ud2.addUnit(h.clone());
    complex_ud2.addUnit(muMol.clone());
    complex_ud2.addUnit(muLitreCubed.clone());
    complex_ud2.addUnit(invMole.clone());
    complex_ud2.addUnit(invMilliLitreSquared.clone());
    complex_ud2.addUnit(invLitre.clone());
    complex_ud2.addUnit(litre.clone());
    complex_ud2.addUnit(dimless675.clone());
  }

  @Test
  public void test_specfic_law() {
    SBMLReader r = new SBMLReader();
    SBMLDocument sbmlDoc = null;
    try {
      sbmlDoc = r.readSBML("/home/eikept/Documents/Studium/HiWi/git_repos/SBMLsqueezer/core/src/test/resources/additional_xml_files/limax_pkpd_39.xml");
    }
    catch (Exception e) {
      System.out.println("Couldn't read model file.");;
    }
    Model model = sbmlDoc.getModel();
    ListOf<Rule> lor = model.getListOfRules();
    AssignmentRule ar = (AssignmentRule) lor.get(lor.size()-1);
    UnitDefinition ud = ar.getDerivedUnitDefinition();

    System.out.println("Assignment Rule: " + ar.getMath().toFormula());
    System.out.println("Final Unit: " + UnitDefinition.printUnits(ud, true));
  }



  //////////////////////
  // divideBy testing
  //////////////////////

  /**
   * Testing {@link UnitDefinition#divideBy(UnitDefinition)} with two simple valid, non-dimensionless unit definitions.
   */
  @Test
  public void testDivideByInGeneralSimple() { ;
    assertEquals("mol*ml^(-2)", UnitDefinition.printUnits(molPerL_ud.clone().divideBy(muL_ud), true));
  }

  /**
   * Testing {@link UnitDefinition#divideBy(UnitDefinition)} with one complex and one simple valid, non-dimensionless
   * unit definition.
   */
  @Test
  public void testDivideByInGeneralComplex() { ;
    assertEquals("(3*s)^3*l^2", UnitDefinition.printUnits(complex_ud1.clone().divideBy(molPerL_ud), true));
  }

  /**
   * Testing {@link UnitDefinition#divideBy(UnitDefinition)} with two valid, non-dimensionless unit definitions
   * which cancel out.
   */
  @Test
  public void testDivideByWhenUnitsCancelOut() {
    assertEquals("dimensionless", UnitDefinition.printUnits(molPerL_ud.clone().divideBy(molPerL_ud), true));
  }

  /**
   * Testing {@link UnitDefinition#divideBy(UnitDefinition)} with one valid, non-dimensionless and one dimensionless
   * unit definition.
   */
  @Test
  public void testDivideByDimlessUnit() {
    assertEquals("mol*(4*l)^(-1)", UnitDefinition.printUnits(molPerL_ud.clone().divideBy(dimless_ud), true));
  }

  @Test
  public void testDivideDimlessByDimless() {
    assertEquals("dimensionless", UnitDefinition.printUnits(dimless_ud.clone().divideBy(dimless_ud), true));
  }

  /**
   * Testing {@link UnitDefinition#divideBy(UnitDefinition)} with one invalid unit definition.
   */
  @Test
  public void testDivideByInvalidUnit() {
    assertEquals("invalid", UnitDefinition.printUnits(molPerL_ud.clone().divideBy(invalid_ud), true));
  }


  ////////////////////////////
  // multiplyWith testing
  //////////////////////////


  /**
   * Testing {@link UnitDefinition#multiplyWith(UnitDefinition)} with two simpple valid, non-dimensionless unit definitions.
   */
  @Test
  public void testMultiplyWithInGeneralSimple() {
   assertEquals("mol^2*l^(-2)", UnitDefinition.printUnits(molPerL_ud.clone().multiplyWith(molPerL_ud), true));
  }

  /**
   * Testing {@link UnitDefinition#multiplyWith(UnitDefinition)} with one complex and one simple valid, non-dimensionless
   * unit definition.
   */
  @Test
  public void testMultiplyWithInGeneralComplex() { ;
    assertEquals("(3*s)^3*mol^2", UnitDefinition.printUnits(complex_ud1.clone().multiplyWith(molPerL_ud), true));
  }

  /**
   * Testing {@link UnitDefinition#multiplyWith(UnitDefinition)} with two valid, non-dimensionless unit definitions
   * which cancel out.
   */
  @Test
  public void testMultiplyWithWhenUnitsCancelOut() {
    assertEquals("dimensionless", UnitDefinition.printUnits(molPerL_ud.clone().multiplyWith(lPerMol_ud), true));
  }

  /**
   * Testing {@link UnitDefinition#multiplyWith(UnitDefinition)} with one valid, non-dimensionless and one dimensionless
   * unit definition.
   */
  @Test
  public void testMultiplyWithDimlessUnit() {
    assertEquals("4*mol*l^(-1)", UnitDefinition.printUnits(molPerL_ud.clone().multiplyWith(dimless_ud), true));
  }

  /**
   * Testing {@link UnitDefinition#multiplyWith(UnitDefinition)} with two dimensionless unit definitions.
   */
  @Test
  public void testMultiplyDimlessWithDimless() {
    assertEquals("16*dimensionless", UnitDefinition.printUnits(dimless_ud.clone().multiplyWith(dimless_ud), true));
  }

  /**
   * Testing {@link UnitDefinition#multiplyWith(UnitDefinition)} with one invalid unit definition.
   */
  @Test
  public void testMultiplyWithInvalidUnit() {
    assertEquals("mol*5*10^5*invalid*l^(-1)", UnitDefinition.printUnits(molPerL_ud.clone().multiplyWith(invalid_ud), true));
  }


  ////////////////////////////
  // raiseByThePowerOf testing
  ////////////////////////////


  /**
   * Testing {@link UnitDefinition#raiseByThePowerOf(double)} with 0 as exponent.
   */
  @Test
  public void testRaiseByThePowerOfZero() {
    assertEquals("", UnitDefinition.printUnits(complex_ud1.clone().raiseByThePowerOf(0d), true));
  }

  /**
   * Testing {@link UnitDefinition#raiseByThePowerOf(double)} with positive integer as exponent.
   */
  @Test
  public void testRaiseByThePowerOfPositiveInt() {
    assertEquals("mol^3*l^(-3)", UnitDefinition.printUnits(molPerL_ud.clone().raiseByThePowerOf(3d), true));
  }

  /**
   * Testing {@link UnitDefinition#raiseByThePowerOf(double)} with negative integer as exponent.
   */
  @Test
  public void testRaiseByThePowerOfNegativeInt() {
    assertEquals("\u03BCl^(-2)", UnitDefinition.printUnits(muL_ud.clone().raiseByThePowerOf(-2d), true));
  }

  /**
   * Testing {@link UnitDefinition#raiseByThePowerOf(double)} with rational number as exponent.
   */
  @Test
  public void testRaiseByThePowerOfReal() {
    assertEquals("(3600*s)^3.4", UnitDefinition.printUnits(h_ud.clone().raiseByThePowerOf(3.4d), true));
  }

  /**
   * Testing {@link UnitDefinition#raiseByThePowerOf(double)} with (positive) infinity as exponent.
   */
  @Test
  public void testRaiseByThePowerOfInfinity() {
    assertEquals("μl^INF", UnitDefinition.printUnits(muL_ud.clone().raiseByThePowerOf(Double.POSITIVE_INFINITY), true));
  }

  /**
   * Testing {@link UnitDefinition#raiseByThePowerOf(double)} with invalid unit definition as base.
   */
  @Test
  public void testRaiseInvalidByThePowerOf() {
    assertEquals("invalid", UnitDefinition.printUnits(invalid_ud.clone().raiseByThePowerOf(3.4d), true));
  }

  /**
   * Testing {@link UnitDefinition#raiseByThePowerOf(double)} with dimensionless unit definition as base
   */
  @Test
  public void testRaiseDimensionlessByThePowerOf() {
    assertEquals("(4*dimensionless)^3", UnitDefinition.printUnits(dimless_ud.clone().raiseByThePowerOf(3d), true));
  }



  ////////////////////////////
  // removeMutliplier testing
  ////////////////////////////

  /**
   * Test method for {@link Unit#removeMultiplier()}.
   */
  @Test
  public void testRemoveMultiplier() {
    UnitDefinition uuu = new UnitDefinition("energy", level, version);
    int scale = 12;
    double multiplier = 10E-10d;
    double noise = 1E-23d;
    double exp = 1d;
    uuu.addUnit(new Unit(multiplier + noise, scale, Kind.JOULE, exp, level, version));
    uuu.getUnit(0).removeMultiplier();
    assertEquals("kJ", UnitDefinition.printUnits(uuu, true));
  }

  ////////////////////////////
  // simplify testing
  ////////////////////////////

  /**
   * Testing {@link UnitDefinition#simplify()} with empty unit definition
   */
  @Test
  public void testSimplifyEmptyUD() {
    assertEquals("", UnitDefinition.printUnits(empty_ud.simplify(), true));
  }

  /**
   * Testing {@link UnitDefinition#simplify()} with unit definition containing one valid non-dimensionless unit
   */
  @Test
  public void testSimplifyOneUnitUD() {
    assertEquals("μl", UnitDefinition.printUnits(muL_ud.simplify(), true));
  }

  /**
   * Testing {@link UnitDefinition#simplify()} with unit definition containing single invalid unit
   */
  @Test
  public void testSimplifyOnlyInvalidUD() {
    assertEquals("5*10^5*invalid", UnitDefinition.printUnits(invalid_ud.simplify(), true));
  }

  /**
   * Testing {@link UnitDefinition#simplify()} with unit definition containing two units:
   *  - valid non-dimensionless unit
   *  - valid dimensionless unit
   */
  @Test
  public void testSimplifyOneDimlessOneNormalUD() {
    assertEquals("240*s", UnitDefinition.printUnits(contDimless_ud.simplify(), true));
  }

  /**
   * Testing {@link UnitDefinition#simplify()} with unit definition containing two valid dimensionless units:
   */
  @Test
  public void testSimplifyOnlyDimlessUD() {
    assertEquals("16*dimensionless", UnitDefinition.printUnits(mulDimless_ud.simplify(), true));
  }

  /**
   * Testing {@link UnitDefinition#simplify()} with unit definition containing units:
   * - invalid unit
   * - valid non-dimensionless unit
   */
  @Test
  public void testSimplifyOneInvalidOneNormalUD() {
    assertEquals("3600*s*5*10^5*invalid", UnitDefinition.printUnits(oneInvalid_ud.simplify(), true));
  }

  /**
   * Testing {@link UnitDefinition#simplify()} with unit definition containing two valid non-dimensionless
   * units of the same kind
   */
  @Test
  public void testSimplifyTwoNormalSameUD() {
    assertEquals("(60*s)^2", UnitDefinition.printUnits(twoNormalSame_ud.simplify(), true));
  }

  /**
   * Testing {@link UnitDefinition#simplify()} with unit definition containing two valid non-dimensionless
   * units of different kind
   */
  @Test
  public void testSimplifyTwoNormalDiffUD() {
    assertEquals("60*s*l^(-1)", UnitDefinition.printUnits(twoNormalDiff_ud.simplify(), true));
  }

  /**
   * Testing {@link UnitDefinition#simplify()} with unit definition containing three valid non-dimensionless
   * units of the same kind
   */
  @Test
  public void testSimplifyThreeNormalSameUD() {
    assertEquals("l", UnitDefinition.printUnits(threeNormalSame_ud.simplify(), true));
  }

  /**
   * Testing {@link UnitDefinition#simplify()} with unit definition containing three valid non-dimensionless
   * units, two of which are of the same kind.
   */
  @Test
  public void testSimplifyTwoSameUD() {
    assertEquals("ml^2*60*s", UnitDefinition.printUnits(twoSameOneDiff_ud.simplify(), true));
  }

  /**
   * Testing {@link UnitDefinition#simplify()} with unit definition containing three valid non-dimensionless
   * units of different kind
   */
  @Test
  public void testSimplifyThreeNormalDiffUD() {
    assertEquals("l*60*s*μmol", UnitDefinition.printUnits(threeNormalDiff_ud.simplify(), true));
  }

  /**
   * Testing {@link UnitDefinition#simplify()} with unit definition containing more than 3 valid units of a
   * variety of kind ({@link TestUnitSimplification#complex_ud1})
   */
  @Test
  public void testSimplifyComplexUD1() {
    assertEquals("(3*s)^3*l*mol", UnitDefinition.printUnits(complex_ud1.simplify(), true));
  }

  /**
   * Testing {@link UnitDefinition#simplify()} with unit definition containing more than 3 valid units of a
   * variety of kind ({@link TestUnitSimplification#complex_ud2})
   */
  @Test
  public void testSimplifyComplexUD2() {
    assertEquals("0.0243*s*pl", UnitDefinition.printUnits(complex_ud2.simplify(), true));
  }

  /**
   * Test {@link UnitDefinition#simplify()} of product of the more complex unit definitions defined above
   * ({@link TestUnitSimplification#complex_ud1} & {@link TestUnitSimplification#complex_ud2})
   */
  @Test
  public void testSimplifyMultipliedComplexUDs() {
    assertEquals("(0.9*s)^4*μl^2*mol", UnitDefinition.printUnits((complex_ud1.clone().simplify().multiplyWith(complex_ud2.clone().simplify())), true));
  }

  /**
   * Test {@link UnitDefinition#simplify()} of quotient of the more complex unit definitions defined above
   * ({@link TestUnitSimplification#complex_ud1} & {@link TestUnitSimplification#complex_ud2})
   */
  @Test
  public void testSimplifyDividedComplexUDs() {
    assertEquals("(3.3333333333333336E7*s)^2*mol", UnitDefinition.printUnits(complex_ud1.clone().simplify().divideBy(complex_ud2.clone().simplify()), true));
  }
  /**
   * Test {@link UnitDefinition#simplify()} with {@link TestUnitSimplification#complex_ud1} raised by the power of 3
   */
  @Test
  public void testSimplifyComplexUDToPowerOf() {
    assertEquals( "(3*s)^9*l^3*mol^3", UnitDefinition.printUnits(complex_ud1.clone().raiseByThePowerOf(3d).simplify(), true));
  }

  /**
   * Test {@link UnitDefinition#simplify()} with unit definition containing multiple dimensionless units raised by the power of 3
   */
  @Test
  public void testSimplifyMultiDimensionlessUDToPowerOf() {
    assertEquals("4096*dimensionless", UnitDefinition.printUnits(mulDimless_ud.clone().raiseByThePowerOf(3d).simplify(), true));
  }


  ////////////////////////////
  // utility methods
  ////////////////////////////

  /**
   * Convenient helper method.
   * 
   * @param symbol
   * @param input1
   * @param input2
   * @param result
   */
  private static void printTask(char symbol, UnitDefinition input1,
    UnitDefinition input2, UnitDefinition result) {
    System.out.printf("%s %s %s = %s\n",
      UnitDefinition.printUnits(input1, true), Character.valueOf(symbol),
      UnitDefinition.printUnits(input2, true),
      UnitDefinition.printUnits(result, true));
  }

  /**
   * Compact {@link String} representation of a {@link UnitDefinition}.
   * 
   * @param udef
   * @return
   */
  private static String c(UnitDefinition udef) {
    return UnitDefinition.printUnits(udef, true);
  }

}

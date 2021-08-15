package org.sbml.jsbml.test.sbml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.*;
import org.sbml.jsbml.util.Maths;

import java.util.Locale;

/**
 * Testing methods for {@link Unit#merge(Unit, Unit)}.
 */
public class TestUnitMerging {

    /**
     * General SBML level for which Units are defined.
     */
    private static final int levelNormal = 3;

    /**
     * General SBML version for which Units are defined.
     */
    private static final int versionNormal = 2;

    /**
     * SBML level for offset tests for which Units are defined.
     * Needed because offsets are only defined in version 2 level 1
     */
    private static final int levelOffset = 2;

    /**
     * SBML version for offset tests for which Units are defined.
     * Needed because offsets are only defined in version 2 level 1
     */
    private static final int versionOffset = 1;

    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Test
    public void testTwoEmptyUnits() {
        Unit u1 = new Unit(levelNormal, versionNormal);
        Unit u2 = new Unit(levelNormal, versionNormal);
        Unit expUnit = new Unit(0, Unit.Kind.INVALID, 2, levelNormal, versionNormal);
        Unit.merge(u1, u2);
        assertEquals(expUnit, u1);
    }

    @Test
    public void testOneEmptyUnit() {
        Unit u1 = new Unit(4, 0, Unit.Kind.LITER, 1, levelNormal, versionNormal);
        Unit u2 = new Unit(levelNormal, versionNormal);
        Unit expUnit = new Unit(2, 0, Unit.Kind.INVALID, 2, levelNormal, versionNormal);
        Unit.merge(u1, u2);
        assertEquals(expUnit, u1);
    }

    /**
     * Tests {@link Unit#merge(Unit, Unit)} with two valid non-dimensionless units of same kind.
     * Additionally also test if conversion from deprecated {@link Unit.Kind#LITER} to {@link Unit.Kind#LITRE} works.
     */
    @Test
    public void testNoneDimensionless() {
        Unit u1 = new Unit(8, -4, Unit.Kind.LITER, 1, levelNormal, versionNormal);
        Unit u2 = new Unit(2, 2, Unit.Kind.LITER, 1, levelNormal, versionNormal);
        Unit expUnit = new Unit(0.4, 0, Unit.Kind.LITRE, 2,levelNormal, versionNormal);
        Unit.merge(u1, u2);
        assertEquals(expUnit, u1);
    }

    /**
     * Tests {@link Unit#merge(Unit, Unit)} with one valid non-dimensionless unit and one dimensionless units.
     */
    @Test
    public void testOneDimensionless() {
        Unit u1 = new Unit(1, -2, Unit.Kind.GRAM, 1, levelNormal, versionNormal);
        Unit u2 = new Unit(1, 2, Unit.Kind.DIMENSIONLESS, 1, levelNormal, versionNormal);
        Unit expUnit = new Unit(1, 0, Unit.Kind.GRAM, 2, levelNormal, versionNormal);
        Unit.merge(u1, u2);
        assertEquals(expUnit, u1);
    }

    /**
     * Test {@link Unit#merge(Unit, Unit)} with two dimensionless units.
     */
    @Test
    public void testTwoDimensionless() {
        Unit u1 = new Unit(2, 0, Unit.Kind.DIMENSIONLESS, 1, levelNormal, versionNormal);
        Unit u2 = new Unit(8, 0, Unit.Kind.DIMENSIONLESS, 1, levelNormal, versionNormal);
        Unit expUnit = new Unit( 4,0, Unit.Kind.DIMENSIONLESS, 2, levelNormal, versionNormal);
        Unit.merge(u1, u2);
        assertEquals(expUnit, u1);
    }

    /**
     * Tests {@link Unit#merge(Unit, Unit)} with two valid non-dimensionless units of different kind.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUnequivalentUnits() {
        Unit u1 = new Unit(1, -7, Unit.Kind.GRAM, 1, levelNormal, versionNormal);
        Unit u2 = new Unit(1, 2, Unit.Kind.LITRE, 1, levelNormal, versionNormal);
        Unit expUnit = u1.clone();
        Unit.merge(u1, u2);
    }

    /**
     * Tests {@link Unit#merge(Unit, Unit)} with two valid non-dimensionless units of same kind with offset (deprecated feature).
     * Additionally also test if conversion from deprecated {@link Unit.Kind#METER} to {@link Unit.Kind#METRE} works.
     */
    @Test
    public void testUnitWithOffset() {
        Unit u1 = new Unit(2, 3, Unit.Kind.METER, 1, levelOffset, versionOffset);
        u1.setOffset(1000);
        Unit u2 = new Unit(3, 2, Unit.Kind.METER, -1, levelOffset, versionOffset);
        Unit expUnit = new Unit(10, 0, Unit.Kind.METRE, 0, levelOffset, versionOffset);
        Unit.merge(u1, u2);
        assertEquals(expUnit, u1);
    }

    /**
     * Tests {@link Unit#merge(Unit, Unit)} with one valid non-dimensionless and one invalid unit
     */
    @Test
    public void testWithOneInvalidUnit() {
        Unit u1 = new Unit(3, 1, Unit.Kind.INVALID, 1, levelNormal, versionNormal);
        Unit u2 = new Unit(3, 2, Unit.Kind.METRE, -1, levelNormal, versionNormal);
        Unit expUnit = new Unit(0.1, 0, Unit.Kind.INVALID, 0, levelNormal, versionNormal);
        Unit.merge(u1, u2);
        assertEquals(expUnit, u1);
    }

}

package org.sbml.jsbml.ext.render.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.sbml.jsbml.ext.render.RelAbsVector;


public class RelAbsVectorTest {

  private static final double TOLERANCE = 1e-10;
  
  /** Test for {@link RelAbsVector#RelAbsVector(RelAbsVector)} */
  @Test
  public void testRelAbsVectorRelAbsVector() {
    RelAbsVector original = new RelAbsVector(1.2d, 35d);
    RelAbsVector copy = new RelAbsVector(original);
    assertEquals(1.2d, copy.getAbsoluteValue(), TOLERANCE);
    assertEquals(35d, copy.getRelativeValue(), TOLERANCE);
    
    original.setAbsoluteValue(3d);
    assertEquals(1.2d, copy.getAbsoluteValue(), TOLERANCE);
  }

  /** Test for {@link RelAbsVector#RelAbsVector(double)} */
  @Test
  public void testRelAbsVectorDouble() {
    RelAbsVector rav = new RelAbsVector(3.4d);
    assertEquals(3.4d, rav.getAbsoluteValue(), TOLERANCE);
    assertFalse(rav.isSetRelativeValue());
  }

  
  /**
   * Test for {@link RelAbsVector#RelAbsVector(String)}, if both relative and
   * absolute are specified
   */
  @Test
  public void testRelAbsVectorStringBothPresent() {
    RelAbsVector rav = new RelAbsVector("1.54+31.7%");
    assertEquals(1.54d, rav.getAbsoluteValue(), TOLERANCE);
    assertEquals(31.7d, rav.getRelativeValue(), TOLERANCE);
    
    rav = new RelAbsVector("-0.54 +  1%");
    assertEquals(-0.54d, rav.getAbsoluteValue(), TOLERANCE);
    assertEquals(1d, rav.getRelativeValue(), TOLERANCE);
    
    rav = new RelAbsVector("39- 50.00%");
    assertEquals(39d, rav.getAbsoluteValue(), TOLERANCE);
    assertEquals(-50d, rav.getRelativeValue(), TOLERANCE);
    
    rav = new RelAbsVector("-19-60%");
    assertEquals(-19d, rav.getAbsoluteValue(), TOLERANCE);
    assertEquals(-60d, rav.getRelativeValue(), TOLERANCE);
  }
  
  /**
   * Test for {@link RelAbsVector#RelAbsVector(String)}, if only relative is specified
   */
  @Test
  public void testRelAbsVectorStringOnlyRelative() {
    RelAbsVector rav = new RelAbsVector("31.7 %");
    assertFalse(rav.isSetAbsoluteValue());
    assertEquals(31.7d, rav.getRelativeValue(), TOLERANCE);
    
    rav = new RelAbsVector("  - 110%");
    assertFalse(rav.isSetAbsoluteValue());
    assertEquals(-110d, rav.getRelativeValue(), TOLERANCE);
  }
  
  /**
   * Test for {@link RelAbsVector#RelAbsVector(String)}, if only absolute is specified
   */
  @Test
  public void testRelAbsVectorStringOnlyAbsolute() {
    RelAbsVector rav = new RelAbsVector("  19.2 ");
    assertFalse(rav.isSetRelativeValue());
    assertEquals(19.2d, rav.getAbsoluteValue(), TOLERANCE);
    
    rav = new RelAbsVector("-11");
    assertFalse(rav.isSetRelativeValue());
    assertEquals(-11d, rav.getAbsoluteValue(), TOLERANCE);
  }
  
  /**
   * Test for {@link RelAbsVector#RelAbsVector(String)}, if given String is invalid
   */
  @Test
  public void testRelAbsVectorStringInvalid() {
    RelAbsVector rav = new RelAbsVector("   \t ");
    assertFalse(rav.isSetAbsoluteValue());
    assertFalse(rav.isSetRelativeValue());
    
    rav = new RelAbsVector("-11+32  -11%");
    assertFalse(rav.isSetAbsoluteValue());
    assertFalse(rav.isSetRelativeValue());
    
    rav = new RelAbsVector((String) null);
    assertFalse(rav.isSetAbsoluteValue());
    assertFalse(rav.isSetRelativeValue());
    
    rav = new RelAbsVector("");
    assertFalse(rav.isSetAbsoluteValue());
    assertFalse(rav.isSetRelativeValue());
    
    rav = new RelAbsVector("-32Ab#u+%");
    assertFalse(rav.isSetAbsoluteValue());
    assertFalse(rav.isSetRelativeValue());
    
    // This is of particular interest: Order is specified as absolute +/-
    // relative% (page 10 of render-spec)
    rav = new RelAbsVector("-11%+32");
    assertFalse(rav.isSetAbsoluteValue());
    assertFalse(rav.isSetRelativeValue());
  }

  /** Test for {@link RelAbsVector#equals(RelAbsVector)} */
  @Test
  public void testEqualsObject() {
    RelAbsVector rav1 = new RelAbsVector(10d);
    assertTrue(rav1.equals(rav1));
    RelAbsVector rav2 = new RelAbsVector(10d);
    assertEquals(rav1, rav2);
    
    rav2.setRelativeValue(95);
    assertFalse(rav1.equals(rav2));
  }

  /** Test for {@link RelAbsVector#getAbsoluteValue()} */
  @Test
  public void testGetAbsoluteValue() {
    RelAbsVector rav = new RelAbsVector(50.3d);
    assertEquals(50.3d, rav.getAbsoluteValue(), TOLERANCE);
  }

  /** Test for {@link RelAbsVector#getRelativeValue()} */
  @Test
  public void testGetRelativeValue() {
    RelAbsVector rav = new RelAbsVector(50.3d, -19d);
    assertEquals(-19d, rav.getRelativeValue(), TOLERANCE);
  }

  /** Test for {@link RelAbsVector#setAbsoluteValue(double)} */
  @Test
  public void testSetAbsoluteValue() {
    RelAbsVector rav = new RelAbsVector(50.3d);
    assertEquals(50.3d, rav.getAbsoluteValue(), TOLERANCE);
    rav.setAbsoluteValue(180d);
    assertEquals(180d, rav.getAbsoluteValue(), TOLERANCE);
  }

  /** Test for {@link RelAbsVector#setRelativeValue(double)} */
  @Test
  public void testSetRelativeValue() {
    RelAbsVector rav = new RelAbsVector(50.3d, -19d);
    assertEquals(-19d, rav.getRelativeValue(), TOLERANCE);
    rav.setRelativeValue(32);
    assertEquals(32d, rav.getRelativeValue(), TOLERANCE);
  }

  /** Test for {@link RelAbsVector#isSetAbsoluteValue()} */
  @Test
  public void testIsSetAbsoluteValue() {
    RelAbsVector rav = new RelAbsVector();
    assertFalse(rav.isSetAbsoluteValue());
    rav.setAbsoluteValue(12);
    assertTrue(rav.isSetAbsoluteValue());
    rav.setAbsoluteValue(Double.NaN);
    assertFalse(rav.isSetAbsoluteValue());
  }

  /** Test for {@link RelAbsVector#isSetRelativeValue()} */
  @Test
  public void testIsSetRelativeValue() {
    RelAbsVector rav = new RelAbsVector();
    assertFalse(rav.isSetRelativeValue());
    rav.setRelativeValue(12);
    assertTrue(rav.isSetRelativeValue());
    rav.setRelativeValue(Double.NaN);
    assertFalse(rav.isSetRelativeValue());
  }

  /** Test for {@link RelAbsVector#unsetAbsoluteValue()} */
  @Test
  public void testUnsetAbsoluteValue() {
    RelAbsVector rav = new RelAbsVector();
    assertFalse(rav.unsetAbsoluteValue()); // cannot unset
    rav.setAbsoluteValue(35);
    assertTrue(rav.isSetAbsoluteValue());
    assertTrue(rav.unsetAbsoluteValue()); // can unset
    assertFalse(rav.isSetAbsoluteValue());
  }

  /** Test for {@link RelAbsVector#unsetRelativeValue()} */
  @Test
  public void testUnsetRelativeValue() {
    RelAbsVector rav = new RelAbsVector();
    assertFalse(rav.unsetRelativeValue());
    rav.setRelativeValue(-42);
    assertTrue(rav.isSetRelativeValue());
    assertTrue(rav.unsetRelativeValue());
    assertFalse(rav.isSetRelativeValue());
  }


  /**
   * Test for {@link RelAbsVector#setCoordinate(String)} when both absolute and
   * relative part are given
   */
  @Test
  public void testSetCoordinateBothGiven() {
    RelAbsVector rav = new RelAbsVector();
    rav.setCoordinate("  -23 -\t14% \t");
    assertEquals(-23d, rav.getAbsoluteValue(), TOLERANCE);
    assertEquals(-14d, rav.getRelativeValue(), TOLERANCE);
    
    rav.setCoordinate("38.2-14%");
    assertEquals(38.2d, rav.getAbsoluteValue(), TOLERANCE);
    assertEquals(-14d, rav.getRelativeValue(), TOLERANCE);
    
    rav.setCoordinate("-23-1.45%");
    assertEquals(-23d, rav.getAbsoluteValue(), TOLERANCE);
    assertEquals(-1.45d, rav.getRelativeValue(), TOLERANCE);
    
    rav.setCoordinate("50+10%");
    assertEquals(50d, rav.getAbsoluteValue(), TOLERANCE);
    assertEquals(10d, rav.getRelativeValue(), TOLERANCE);
  }
  
  
  /**
   * Test for {@link RelAbsVector#setCoordinate(String)} when only absolute part
   * is given
   */
  @Test
  public void testSetCoordinateOnlyAbsolute() {
    RelAbsVector rav = new RelAbsVector();
    rav.setCoordinate("  -23 \t");
    assertEquals(-23d, rav.getAbsoluteValue(), TOLERANCE);
    assertFalse(rav.isSetRelativeValue());
    
    rav.setCoordinate("38.2");
    assertEquals(38.2d, rav.getAbsoluteValue(), TOLERANCE);
    assertFalse(rav.isSetRelativeValue());
  }
  
  /**
   * Test for {@link RelAbsVector#setCoordinate(String)} when only relative part
   * is given
   */
  @Test
  public void testSetCoordinateOnlyRelative() {
    RelAbsVector rav = new RelAbsVector();
    rav.setCoordinate("  -23 % \t");
    assertEquals(-23d, rav.getRelativeValue(), TOLERANCE);
    assertFalse(rav.isSetAbsoluteValue());
    
    rav.setCoordinate("38.2%");
    assertEquals(38.2d, rav.getRelativeValue(), TOLERANCE);
    assertFalse(rav.isSetAbsoluteValue());
  }
  
  /**
   * Test for {@link RelAbsVector#setCoordinate(String)} for invalid strings
   */
  @Test
  public void testSetCoordinateInvalid() {
    RelAbsVector rav = new RelAbsVector();
    rav.setCoordinate("32%-10");
    assertFalse(rav.isSetAbsoluteValue());
    assertFalse(rav.isSetRelativeValue());
    
    rav.setCoordinate("32%-10%");
    assertFalse(rav.isSetAbsoluteValue());
    assertFalse(rav.isSetRelativeValue());
    
    rav.setCoordinate("10+30-10%");
    assertFalse(rav.isSetAbsoluteValue());
    assertFalse(rav.isSetRelativeValue());
    
    rav.setCoordinate("3A89-#asdf");
    assertFalse(rav.isSetAbsoluteValue());
    assertFalse(rav.isSetRelativeValue());
    
    rav.setCoordinate("");
    assertFalse(rav.isSetAbsoluteValue());
    assertFalse(rav.isSetRelativeValue());
    
    rav.setCoordinate((String) null);
    assertFalse(rav.isSetAbsoluteValue());
    assertFalse(rav.isSetRelativeValue());
  }

  /** Test for {@link RelAbsVector#getCoordinate()} 
   * @throws Exception */
  @Test
  public void testGetCoordinate() throws Exception {
    RelAbsVector rav = new RelAbsVector(10, 35);
    assertEquals("10.0+35.0%", rav.getCoordinate());
    RelAbsVector parsed = new RelAbsVector(rav.getCoordinate());
    assertEquals(rav, parsed);    
    
    rav.setAbsoluteValue(-94.3);
    assertEquals("-94.3+35.0%", rav.getCoordinate());
    
    rav.setRelativeValue(-10);
    assertEquals("-94.3-10.0%", rav.getCoordinate());
    
    rav.unsetAbsoluteValue();
    assertEquals("-10.0%", rav.getCoordinate());
    
    rav.unsetRelativeValue();
    rav.setAbsoluteValue(2.53);
    assertEquals("2.53", rav.getCoordinate());
  }
  
  
  /**
   * Trying to get the coordinate-string of a fully unset RelAbsVector should
   * give rise to an Exception
   * 
   * TODO: or should the return just be "0"?
   * 
   * @throws Exception
   */
  @Test(expected = Exception.class)
  public void testGetCoordinateException() throws Exception {
    RelAbsVector rav = new RelAbsVector();
    rav.getCoordinate();
  }
}

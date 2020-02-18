package org.sbml.jsbml.ext.render.director.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.render.director.Geometry;


public class GeometryTest {

  private static final double TOLERANCE = 1e-10;
  
  @Test
  public void testWeightedSumTrivialWeights() {
    Point a = new Point(2d, 1d, 3d);
    Point b = new Point(5d, -3d, -0.3);
    Point sum = Geometry.weightedSum(1d, a, 1d, b);
    assertEquals("simple sum: x mismatch", 7d, sum.getX(), TOLERANCE);
    assertEquals("simple sum: y mismatch", -2d, sum.getY(), TOLERANCE);
    assertEquals("simple sum: z mismatch", 2.7d, sum.getZ(), TOLERANCE);
  }
  
  @Test
  public void testWeightedSumDifference() {
    Point a = new Point(3d, 1d);
    Point b = new Point(1.5d, -3d);
    Point difference = Geometry.weightedSum(1d, a, -1d, b);
    assertEquals("simple difference: x mismatch", 1.5d, difference.getX(), TOLERANCE);
    assertEquals("simple difference: y mismatch", 4d, difference.getY(), TOLERANCE);
  }
  
  @Test
  public void testWeightedSumNontrivialWeights() {
    Point a = new Point(3d, 1d, 2.5d);
    Point b = new Point(1.5d, -3d, .2d);
    Point result = Geometry.weightedSum(-0.4, a, 3.9d, b);
    assertEquals("nontrivial weights: x mismatch",  4.65d, result.getX(), TOLERANCE);
    assertEquals("nontrivial weights: y mismatch", -12.1d, result.getY(), TOLERANCE);
    assertEquals("nontrivial weights: z mismatch", -0.22d, result.getZ(), TOLERANCE);
  }
}

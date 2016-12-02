package org.sbml.jsbml.ext.layout;

import java.util.List;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.util.TreeNodeChangeListener;

/**
 * 
 * 
 * @author rodrigue
 *
 */
public interface ICurve {

  /**
   * 
   * @param curveSegment
   * @return
   * @see List#add(Object)
   */
  public boolean addCurveSegment(CurveSegment curveSegment);

  /**
   * 
   * @param index
   * @param element
   */
  public void addCurveSegment(int index, CurveSegment element);

  /**
   * Creates a new {@link CubicBezier} instance, adds it to this {@link ICurve}.
   * and returns it.
   * 
   * @return the new CubicBezier instance
   */
  public CubicBezier createCubicBezier();

  /**
   * 
   * @param n
   * @return
   */
  public CurveSegment getCurveSegment(int n);

  /**
   * 
   * @return
   */
  public int getCurveSegmentCount();

  /**
   * 
   * @return
   */
  public ListOf<CurveSegment> getListOfCurveSegments();

  /**
   * 
   * @return
   */
  public boolean isSetListOfCurveSegments();

  /**
   * 
   * @param cs
   * @return
   */
  public boolean removeCurveSegment(CurveSegment cs);

  /**
   * The listOfCurveSegments element contains arbitrary number of curve segments that
   * can be either of type {@link LineSegment} or of type {@link CubicBezier}. Here,
   * both classes are child classes of the abstract type {@link CurveSegment}.
   * 
   * @param listOfCurveSegments
   */
  public void setListOfCurveSegments(ListOf<CurveSegment> listOfCurveSegments);

  /**
   * Removes the {@link #listOfCurveSegments} from this
   * {@link org.sbml.jsbml.Model} and notifies
   * all registered instances of
   * {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfCurveSegments();

}
package examples;

import org.sbml.jsbml.ext.layout.CubicBezier;
import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.render.director.SBGNArc;


public abstract class LaTeXSBGNArc implements SBGNArc<String> {

  @Override
  public String draw(CurveSegment curveSegment, double lineWidth) {
    if(curveSegment.isCubicBezier())  {
      return String.format(
        "\t\\draw[line width=%s] %s;",
        lineWidth, coordinatesForCurveSegment(curveSegment));
    } else {
      return String.format("\t\\draw[line width=%s] %s;",
        lineWidth, coordinatesForCurveSegment(curveSegment));  
    }
  }
  
  @Override
  public String draw(Curve curve, double lineWidth) {
    StringBuffer result = new StringBuffer();
    for (int i = 0; i < curve.getCurveSegmentCount(); i++) {
      if(i == curve.getCurveSegmentCount() - 1) {
        result.append(drawHead(curve.getCurveSegment(i), lineWidth));
      } else {
        result.append(draw(curve.getCurveSegment(i), lineWidth));
      }
      result.append(System.lineSeparator());
    }
    return result.toString();
  }


  @Override
  public String draw(Curve curve) {
    return draw(curve, 1);
  }
  
  public String coordinatesForCurveSegment(CurveSegment curveSegment) {
    if(curveSegment.isCubicBezier())  {
      return String.format(
        "(%spt, %spt) .. controls (%spt,%spt) and (%spt, %spt) .. (%spt, %spt)",
        curveSegment.getStart().getX(),
        curveSegment.getStart().getY(),
        ((CubicBezier) curveSegment).getBasePoint1().getX(),
        ((CubicBezier) curveSegment).getBasePoint1().getY(),
        ((CubicBezier) curveSegment).getBasePoint2().getX(),
        ((CubicBezier) curveSegment).getBasePoint2().getY(),
        curveSegment.getEnd().getX(), curveSegment.getEnd().getY());
    } else {
      return String.format("(%spt, %spt) -- (%spt, %spt)",
        curveSegment.getStart().getX(), curveSegment.getStart().getY(),
        curveSegment.getEnd().getX(), curveSegment.getEnd().getY());  
    }
  }
  
  
  /**
   * Draws the final segment of a curve: This one (and only this one) carries
   * the arrow-head marking the type of curve (Production, Consumption etc)
   * 
   * @param curveSegment
   * @param lineWidth
   * @return
   */
  public abstract String drawHead(CurveSegment curveSegment, double lineWidth);
}

package examples;

import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.render.director.SBGNArc;


public abstract class LaTeXSBGNArc implements SBGNArc<String> {

  @Override
  public String draw(CurveSegment curveSegment, double lineWidth) {
    // TODO: use isCubicBezier to decide how to draw.
    return String.format("\\draw[line width=%s] (%spt, %spt) -- (%spt, %spt);",
      lineWidth, curveSegment.getStart().getX(), curveSegment.getStart().getY(),
      curveSegment.getEnd().getX(), curveSegment.getEnd().getY());
  }
  
  @Override
  public String draw(Curve curve, double lineWidth) {
    StringBuffer result = new StringBuffer("% Curve: ");
    result.append(curve.getId());
    result.append(System.lineSeparator());
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

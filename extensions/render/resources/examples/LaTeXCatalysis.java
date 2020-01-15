package examples;

import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.render.director.Catalysis;


public class LaTeXCatalysis extends LaTeXSBGNArc implements Catalysis<String> {
  
  private double arrowScale;
  
  public LaTeXCatalysis(double arrowScale) {
    this.arrowScale = arrowScale;
  }

  @Override
  public String drawHead(CurveSegment curveSegment, double lineWidth) {
    return String.format(
      "\\draw[line width=%s, arrows={-Circle[open,scale=%s]}] %s;", lineWidth,
      arrowScale, coordinatesForCurveSegment(curveSegment));
  }
}

package examples;

import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.render.director.Production;


public class LaTeXProduction extends LaTeXSBGNArc implements Production<String> {

  private double arrowScale;
  
  public LaTeXProduction(double arrowScale) {
    this.arrowScale = arrowScale;
  }


  @Override
  public String drawHead(CurveSegment curveSegment, double lineWidth) {
    return String.format("\t\\draw[line width=%s, arrows={-Triangle[scale=%s]}] %s;",
      lineWidth, arrowScale, coordinatesForCurveSegment(curveSegment));
  }
}

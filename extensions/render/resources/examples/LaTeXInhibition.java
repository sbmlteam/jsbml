package examples;

import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.render.director.Inhibition;


public class LaTeXInhibition extends LaTeXSBGNArc
  implements Inhibition<String> {

  private double arrowScale;
  
  public LaTeXInhibition(double arrowScale) {
    this.arrowScale = arrowScale;
  }

  @Override
  public String drawHead(CurveSegment curveSegment, double lineWidth) {
    return String.format("\t\\draw[line width=%s, arrows={-|[scale=%s]}] %s;",
      lineWidth, arrowScale, coordinatesForCurveSegment(curveSegment));
  }
}

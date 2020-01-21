package examples;

import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.render.director.Modulation;


public class LaTeXModulation extends LaTeXSBGNArc
  implements Modulation<String> {

  private double arrowScale;
  
  public LaTeXModulation(double arrowScale) {
    this.arrowScale = arrowScale;
  }


  @Override
  public String drawHead(CurveSegment curveSegment, double lineWidth) {
    return String.format("\t\\draw[line width=%s, arrows={-Turned Square[open,scale=%s]}] %s;",
      lineWidth, arrowScale, coordinatesForCurveSegment(curveSegment));
  }
}

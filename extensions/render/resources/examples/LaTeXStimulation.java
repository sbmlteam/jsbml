package examples;

import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.render.director.Stimulation;


public class LaTeXStimulation extends LaTeXSBGNArc
  implements Stimulation<String> {
  
  private double arrowScale;
  
  public LaTeXStimulation(double arrowScale) {
    this.arrowScale = arrowScale;
  }

  @Override
  public String drawHead(CurveSegment curveSegment, double lineWidth) {
    return String.format(
      "\\draw[line width=%s, arrows={-Triangle[open,scale=%s]}] %s;", lineWidth,
      arrowScale, coordinatesForCurveSegment(curveSegment));
  }
}

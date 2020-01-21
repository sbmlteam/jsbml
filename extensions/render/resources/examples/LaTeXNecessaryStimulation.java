package examples;

import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.render.director.NecessaryStimulation;


public class LaTeXNecessaryStimulation extends LaTeXSBGNArc
  implements NecessaryStimulation<String> {
  private double arrowScale;
  
  public LaTeXNecessaryStimulation(double arrowScale) {
    this.arrowScale = arrowScale;
  }

  @Override
  public String drawHead(CurveSegment curveSegment, double lineWidth) {
    return String.format("\t\\draw[line width=%s, arrows={-|[scale=%s]Butt Cap[scale=%s]Triangle[open,scale=%s]}] %s;",
      lineWidth, arrowScale, 2*arrowScale, arrowScale, coordinatesForCurveSegment(curveSegment));
  }
}

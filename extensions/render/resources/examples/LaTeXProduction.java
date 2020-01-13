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
    return String.format("\\draw[line width=%s, arrows={-Triangle[scale=%s]}] (%spt, %spt) -- (%spt, %spt);",
      lineWidth, arrowScale, curveSegment.getStart().getX(), curveSegment.getStart().getY(),
      curveSegment.getEnd().getX(), curveSegment.getEnd().getY());
  }
}

package examples;

import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.render.director.Consumption;


public class LaTeXConsumption extends LaTeXSBGNArc implements Consumption<String> {

  public LaTeXConsumption() {
  }

  @Override
  public String drawHead(CurveSegment curveSegment, double lineWidth) {
    return draw(curveSegment, lineWidth);
  }

}

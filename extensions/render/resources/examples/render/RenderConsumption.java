package examples.render;

import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.render.director.Consumption;


public class RenderConsumption extends RenderSBGNArc
  implements Consumption<String> {

  @Override
  public String draw(Curve curve) {
    return "consumptionStyle";
  }
}

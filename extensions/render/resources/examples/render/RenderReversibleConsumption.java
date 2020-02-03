package examples.render;

import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.render.director.ReversibleConsumption;


public class RenderReversibleConsumption extends RenderSBGNArc
  implements ReversibleConsumption<String> {

  @Override
  public String draw(Curve curve) {
    return "reversibleConsumptionStyle";
  }
}

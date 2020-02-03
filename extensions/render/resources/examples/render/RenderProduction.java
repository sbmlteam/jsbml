package examples.render;

import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.render.director.Production;


public class RenderProduction extends RenderSBGNArc
  implements Production<String> {

  @Override
  public String draw(Curve curve) {
    return "productionStyle";
  }
}

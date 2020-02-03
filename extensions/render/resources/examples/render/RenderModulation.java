package examples.render;

import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.render.director.Modulation;


public class RenderModulation extends RenderSBGNArc
  implements Modulation<String> {

  @Override
  public String draw(Curve curve) {
    return "modulationStyle";
  }
}

package examples.render;

import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.render.director.Inhibition;


public class RenderInhibition extends RenderSBGNArc
  implements Inhibition<String> {

  @Override
  public String draw(Curve curve) {
    return "inhibitionStyle";
  }
}

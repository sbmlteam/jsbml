package examples.render;

import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.render.director.Catalysis;


public class RenderCatalysis extends RenderSBGNArc
  implements Catalysis<String> {

  @Override
  public String draw(Curve curve) {
    return "catalysisStyle";
  }
}

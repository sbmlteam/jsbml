package examples.render;

import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.render.director.Stimulation;


public class RenderStimulation extends RenderSBGNArc
  implements Stimulation<String> {

  @Override
  public String draw(Curve curve) {
    return "stimulationStyle";
  }
}

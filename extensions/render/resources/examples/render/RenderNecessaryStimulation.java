package examples.render;

import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.render.director.NecessaryStimulation;


public class RenderNecessaryStimulation extends RenderSBGNArc
  implements NecessaryStimulation<String> {

  @Override
  public String draw(Curve curve) {
    return "necessaryStimulationStyle";
  }
}

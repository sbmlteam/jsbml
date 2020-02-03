package examples.render;

import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.render.director.SBGNArc;

/**
 * Class for redirecting all draw-calls to the draw(Curve)-method: Will always return just the 
 * id of the style to be applied.
 * 
 * @author DavidVetter
 */
public abstract class RenderSBGNArc implements SBGNArc<String> {

  @Override
  public String draw(CurveSegment curveSegment, double lineWidth) {
    return draw(null);
  }
  
  @Override
  public String draw(Curve curve, double lineWidth) {
    return draw(null);
  }

}

package examples.render;

import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Polygon;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.director.PerturbingAgent;


public class RenderPerturbingAgent extends PerturbingAgent<LocalStyle> {
  private String stroke, fill, clone;
  private double inset;
  
  public RenderPerturbingAgent(double strokeWidth, String stroke, String fill, String clone) {
    super();
    setLineWidth(strokeWidth);
    this.stroke = stroke;
    this.fill = fill;
    this.clone = clone;
    this.inset = 0.29;
  }
  
  /**
   * @param strokeWidth
   * @param stroke
   * @param fill
   * @param clone
   * @param insetProportion
   *        how deep are the sides to be indented (factor to be applied to
   *        height), i.e. insetProportion * height will be the
   *        side-indentation-depth
   *        (Default: 1/(2*sqrt(3)) approx 0.29)
   */
  public RenderPerturbingAgent(double strokeWidth, String stroke, String fill, String clone, double insetProportion) {
    super();
    setLineWidth(strokeWidth);
    this.stroke = stroke;
    this.fill = fill;
    this.clone = clone;
    this.inset = insetProportion;
  }
  
  @Override
  public LocalStyle draw(double x, double y, double z, double width, double height,
    double depth) {
    RenderGroup agent = new RenderGroup();
    
    if(hasCloneMarker()) {
      // TODO -> use clone here.
    }
    Polygon agentPoly = agent.createPolygon();
    RenderLayoutBuilder.addRenderPoint(agentPoly, 0, 0);
    RenderLayoutBuilder.addRenderPoint(agentPoly, inset * height,  height / 2);
    RenderLayoutBuilder.addRenderPoint(agentPoly, 0,  height);
    RenderLayoutBuilder.addRenderPoint(agentPoly, width, height);
    RenderLayoutBuilder.addRenderPoint(agentPoly, width - (inset * height),  height / 2);  
    RenderLayoutBuilder.addRenderPoint(agentPoly, width, 0);  
    
    agentPoly.setStroke(stroke);
    agentPoly.setStrokeWidth(getLineWidth());
    agentPoly.setFill(fill);
    
    return new LocalStyle(agent);
  }
}

package examples.render;

import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.render.Ellipse;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.director.DissociationNode;


public class RenderDissociationNode extends RenderSBGNProcessNode
  implements DissociationNode<LocalStyle> {

  public RenderDissociationNode(double strokeWidth, String stroke, String fill,
    double nodeSize) {
    super(strokeWidth, stroke, fill, nodeSize);
  }


  @Override
  public LocalStyle draw(double x, double y, double z, double width,
    double height, double depth, double rotationAngle, Point rotationCenter) {
    RenderGroup group = new RenderGroup();
    group.setStroke(getStroke());
    group.setStrokeWidth(getLineWidth());
    
    Ellipse outer = group.createEllipse();
    outer.setCx(width/2); outer.setAbsoluteCx(true);
    outer.setCy(height/2); outer.setAbsoluteCy(true);
    outer.setRx(getNodeSize()/2); outer.setAbsoluteRx(true);
    
    outer.setFill(getFill());
    outer.setStroke(getStroke());
    
    Ellipse inner = group.createEllipse();
    inner.setCx(width/2); inner.setAbsoluteCx(true);
    inner.setCy(height/2); inner.setAbsoluteCy(true);
    inner.setRx(getNodeSize()/4); inner.setAbsoluteRx(true);
    
    inner.setFill(getFill());
    inner.setStroke(getStroke());
    
    return new LocalStyle(group);
  }
}

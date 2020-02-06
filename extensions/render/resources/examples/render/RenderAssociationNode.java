package examples.render;

import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.render.Ellipse;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.director.AssociationNode;


public class RenderAssociationNode extends RenderSBGNProcessNode
  implements AssociationNode<LocalStyle> {

  public RenderAssociationNode(double strokeWidth, String stroke, String fill,
    double nodeSize) {
    super(strokeWidth, stroke, fill, nodeSize);
  }


  @Override
  public LocalStyle draw(double x, double y, double z, double width,
    double height, double depth, double rotationAngle, Point rotationCenter) {
    RenderGroup group = new RenderGroup();
    // This applies to the r.g.'s curve too.
    group.setStroke(getStroke());
    group.setStrokeWidth(getLineWidth());
    
    Ellipse circle = group.createEllipse();
    circle.setCx(width/2); circle.setAbsoluteCx(true);
    circle.setCy(height/2); circle.setAbsoluteCy(true);
    circle.setRx(getNodeSize()/2); circle.setAbsoluteRx(true);
    
    circle.setFill(getStroke());
    circle.setStroke(getStroke());
    
    return new LocalStyle(group);
  }
}

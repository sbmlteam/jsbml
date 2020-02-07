package examples.render;

import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Polygon;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.director.OmittedProcessNode;


public class RenderOmittedProcessNode extends RenderSBGNProcessNode
  implements OmittedProcessNode<LocalStyle> {

  public RenderOmittedProcessNode(double strokeWidth, String stroke,
    String fill, double nodeSize) {
    super(strokeWidth, stroke, fill, nodeSize);
  }


  @Override
  public LocalStyle draw(double x, double y, double z, double width,
    double height, double depth, double rotationAngle, Point rotationCenter) {
    RenderGroup group = new RenderGroup();
    // This applies to the r.g.'s curve too.
    group.setStroke(getStroke());
    group.setStrokeWidth(getLineWidth());
    double rotationRadians = Math.toRadians(rotationAngle);

    Polygon square = group.createPolygon();
    
    x = -getNodeSize() / 2;
    y = -getNodeSize() / 2;
    addRotatedRenderPoint(square, x, y, rotationRadians, width / 2, height / 2);
    
    y = getNodeSize() / 2;
    addRotatedRenderPoint(square, x, y, rotationRadians, width / 2, height / 2);
    
    x = getNodeSize() / 2;
    addRotatedRenderPoint(square, x, y, rotationRadians, width / 2, height / 2);
    
    y = -getNodeSize() / 2;
    addRotatedRenderPoint(square, x, y, rotationRadians, width / 2, height / 2);
    square.setFill(getFill());
    
    Polygon stroke1 = group.createPolygon();
    addRotatedRenderPoint(stroke1, -getNodeSize() / 3, getNodeSize() / 3,
      rotationRadians, width / 2, height / 2);
    addRotatedRenderPoint(stroke1, 0, -getNodeSize() / 3,
      rotationRadians, width / 2, height / 2);
    
    Polygon stroke2 = group.createPolygon();
    addRotatedRenderPoint(stroke2, 0, getNodeSize() / 3,
      rotationRadians, width / 2, height / 2);
    addRotatedRenderPoint(stroke2, getNodeSize() / 3, -getNodeSize() / 3,
      rotationRadians, width / 2, height / 2);
    
    return new LocalStyle(group);
  }
}

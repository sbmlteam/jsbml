package examples.render;

import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Polygon;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.director.ProcessNode;


public class RenderProcessNode extends RenderSBGNProcessNode implements ProcessNode<LocalStyle> {

  /**
   * 
   * @param strokeWidth
   * @param stroke
   * @param fill
   * @param nodeSize side-length of the process-node's square
   */
  public RenderProcessNode(double strokeWidth, String stroke, String fill, double nodeSize) {
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
    return new LocalStyle(group);
  }
}

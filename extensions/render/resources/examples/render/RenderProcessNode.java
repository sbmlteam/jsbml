package examples.render;

import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Polygon;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.director.ProcessNode;


public class RenderProcessNode implements ProcessNode<LocalStyle> {
  private Point substratePort, productPort;
  private double lineWidth, nodeSize;
  private String stroke, fill;
  
  /**
   * 
   * @param strokeWidth
   * @param stroke
   * @param fill
   * @param nodeSize side-length of the process-node's square
   */
  public RenderProcessNode(double strokeWidth, String stroke, String fill, double nodeSize) {
    lineWidth = strokeWidth;
    this.stroke = stroke;
    this.fill = fill;
    this.nodeSize = nodeSize;
  }

  @Override
  public LocalStyle draw(Curve curve, double rotationAngle,
    Point rotationCenter) {
    // TODO Auto-generated method stub
    // a) Add general curve-info (group-wide)
    // b) Add rotated rectangle
    return null;
  }

  @Override
  public LocalStyle drawCurveSegment(CurveSegment segment, double rotationAngle,
    Point rotationCenter) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public LocalStyle draw(double x, double y, double z, double width,
    double height, double depth, double rotationAngle, Point rotationCenter) {
    RenderGroup group = new RenderGroup();
    // This should apply to the r.g.'s curve too.
    group.setStroke(stroke);
    group.setStrokeWidth(getLineWidth());
    double rotationRadians = Math.toRadians(rotationAngle);
    
    Polygon square = group.createPolygon();
    x = - nodeSize/2;
    y = - nodeSize/2;
    addRotatedRenderPoint(square, x, y, rotationRadians, width/2, height/2);
    
    y = nodeSize/2;
    addRotatedRenderPoint(square, x, y, rotationRadians, width/2, height/2);
    
    x = nodeSize/2;
    addRotatedRenderPoint(square, x, y, rotationRadians, width/2, height/2);
    
    y = - nodeSize/2;
    addRotatedRenderPoint(square, x, y, rotationRadians, width/2, height/2);
    square.setFill(fill);
    return new LocalStyle(group);
  }


  private void addRotatedRenderPoint(Polygon poly, double x, double y,
    double radians, double centreX, double centreY) {
    RenderLayoutBuilder.addRenderPoint(poly,
      (Math.cos(radians) * x - Math.sin(radians) * y) + centreX,
      (Math.sin(radians) * x + Math.cos(radians) * y) + centreY);
  }
  
  @Override
  public LocalStyle draw(double x, double y, double z, double width,
    double height, double depth) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setPointOfContactToSubstrate(Point pointOfContactToSubstrate) {
    substratePort = pointOfContactToSubstrate;
  }

  @Override
  public Point getPointOfContactToSubstrate() {
    return substratePort;
  }

  @Override
  public void setPointOfContactToProduct(Point pointOfContactToProduct) {
    productPort = pointOfContactToProduct;
  }

  @Override
  public Point getPointOfContactToProduct() {
    return productPort;
  }

  @Override
  public double getLineWidth() {
    return lineWidth;
  }

  @Override
  public void setLineWidth(double lineWidth) {
    this.lineWidth = lineWidth;
  }
}

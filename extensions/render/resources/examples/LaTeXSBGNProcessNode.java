package examples;

import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.render.director.SBGNProcessNode;


public abstract class LaTeXSBGNProcessNode implements SBGNProcessNode<String> {
  private Point substratePort, productPort;
  private double lineWidth;
  private double processNodeSize;
  
  /**
   * @param lineWidth
   * @param processSquareSize
   *        the edge-length of the process-square in case the curve-attribute is
   *        set
   */
  public LaTeXSBGNProcessNode(double lineWidth, double processNodeSize) {
    this.lineWidth = lineWidth;
    this.processNodeSize = processNodeSize;
  }

  @Override
  public String draw(Curve curve, double rotationAngle, Point rotationCenter) {
    StringBuffer result = new StringBuffer();
    for (CurveSegment cs : curve.getListOfCurveSegments()) {
      result.append(drawCurveSegment(cs, rotationAngle, rotationCenter));
    }
    result.append(draw(rotationCenter.getX() - getProcessNodeSize() / 2,
      rotationCenter.getY() - getProcessNodeSize() / 2,
      rotationCenter.getZ() - getProcessNodeSize() / 2, getProcessNodeSize(),
      getProcessNodeSize(), getProcessNodeSize(), rotationAngle, rotationCenter));
    return result.toString();
  }

  @Override
  public String drawCurveSegment(CurveSegment segment, double rotationAngle,
    Point rotationCenter) {
    // TODO What to use the rotation-angle for?
    // TODO: this could be cubic bezier!
    return String.format("\\draw[line width=%s] (%spt, %spt) -- (%spt, %spt);%s",
      lineWidth, segment.getStart().getX(), segment.getStart().getY(),
      segment.getEnd().getX(), segment.getEnd().getY(), System.lineSeparator());
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
  
  /**
   * @return the size of the process-node element (square or circle), if a curve is set
   */
  public double getProcessNodeSize() {
    return processNodeSize;
  }
  
  /**
   * @param size the size of the process-node element (square or circle), if a curve is set
   */
  public void setProcessNodeSize(double size) {
    processNodeSize = size;
  }
}

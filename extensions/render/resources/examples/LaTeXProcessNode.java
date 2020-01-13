package examples;

import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.render.director.ProcessNode;


public class LaTeXProcessNode implements ProcessNode<String> {

  private Point substratePort, productPort;
  private double lineWidth;
  private double processSquareSize;
  
  /**
   * @param lineWidth
   * @param processSquareSize
   *        the edge-length of the process-square in case the curve-attribute is
   *        set
   */
  public LaTeXProcessNode(double lineWidth, double processSquareSize) {
    this.lineWidth = lineWidth;
    this.processSquareSize = processSquareSize;
  }


  @Override
  public String draw(Curve curve, double rotationAngle, Point rotationCenter) {
    // Rotation-angle is the rotation of the process-rectangle
    StringBuffer result = new StringBuffer();
    result.append(draw(rotationCenter.getX() - processSquareSize / 2,
      rotationCenter.getY() - processSquareSize / 2,
      rotationCenter.getZ() - processSquareSize / 2, processSquareSize,
      processSquareSize, processSquareSize, rotationAngle, rotationCenter));
    for (CurveSegment cs : curve.getListOfCurveSegments()) {
      result.append(drawCurveSegment(cs, rotationAngle, rotationCenter));
    }
    return result.toString();
  }


  @Override
  public String drawCurveSegment(CurveSegment segment, double rotationAngle,
    Point rotationCenter) {
    // TODO What to use the rotation-angle for?
    return String.format("\\draw[line width=%s] (%spt, %spt) -- (%spt, %spt);%s",
      lineWidth, segment.getStart().getX(), segment.getStart().getY(),
      segment.getEnd().getX(), segment.getEnd().getY(), System.lineSeparator());
  }

  /**
   * Only use this, if no curve is set (see Layout-documentation page 16)
   */
  @Override
  public String draw(double x, double y, double z, double width, double height,
    double depth, double rotationAngle, Point rotationCenter) {
    // TODO: draw the little whiskers?
    return String.format(
      "\\draw[line width=%s, rotate around={%s:(%spt,%spt)}] (%spt, %spt) rectangle ++(%spt, %spt);%s",
      lineWidth, rotationAngle, rotationCenter.getX(), rotationCenter.getY(), x,
      y, width, height, System.lineSeparator());
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

  /**
   * Only use this, if no curve is set (see Layout-documentation page 16) <br>
   * {@inheritDoc}
   */
  @Override
  public String draw(double x, double y, double z, double width, double height,
    double depth) {
    return String.format(
      "\\draw[line width=%s] (%spt, %spt) rectangle ++(%spt, %spt);%s", lineWidth,
      x, y, width, height, System.lineSeparator());
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

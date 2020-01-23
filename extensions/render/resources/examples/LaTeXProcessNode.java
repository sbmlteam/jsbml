package examples;

import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.render.director.ProcessNode;


public class LaTeXProcessNode extends LaTeXSBGNProcessNode implements ProcessNode<String> {
  
  /**
   * @param lineWidth
   * @param processSquareSize
   *        the edge-length of the process-square in case the curve-attribute is
   *        set
   */
  public LaTeXProcessNode(double lineWidth, double processSquareSize) {
    super(lineWidth, processSquareSize);
  }


  /**
   * Only use this, if no curve is set (see Layout-documentation page 16)
   */
  @Override
  public String draw(double x, double y, double z, double width, double height,
    double depth, double rotationAngle, Point rotationCenter) {
    return String.format(
      "\\draw[line width=%s, fill=white, draw=black, rotate around={%s:(%spt,%spt)}] (%spt, %spt) rectangle ++(%spt, %spt);%s",
      getLineWidth(), rotationAngle, rotationCenter.getX(), rotationCenter.getY(), x,
      y, width, height, System.lineSeparator());
  }

  /**
   * Only use this, if no curve is set (see Layout-documentation page 16) <br>
   * {@inheritDoc}
   */
  @Override
  public String draw(double x, double y, double z, double width, double height,
    double depth) {
    return String.format(
      "\\draw[line width=%s, fill=white, draw=black] (%spt, %spt) rectangle ++(%spt, %spt);%s", getLineWidth(),
      x, y, width, height, System.lineSeparator());
  }
}

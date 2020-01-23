package examples;

import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.render.director.DissociationNode;

public class LaTeXDissociationNode extends LaTeXSBGNProcessNode
  implements DissociationNode<String> {

  public LaTeXDissociationNode(double lineWidth, double processNodeSize) {
    super(lineWidth, processNodeSize);
  }


  @Override
  public String draw(double x, double y, double z, double width, double height,
    double depth, double rotationAngle, Point rotationCenter) {
    // Like AssociationNode: Circular, thus rotation is irrelevant.
    return draw(x, y, z, width, height, depth);
  }


  @Override
  public String draw(double x, double y, double z, double width, double height,
    double depth) {
    return String.format(
      "\\draw[line width=%s, draw=black, fill=white] (%spt, %spt) ellipse (%spt and %spt);%s\\draw[line width=%s, draw=black, fill=white] (%spt, %spt) ellipse (%spt and %spt);%s",
      getLineWidth(), x + width / 2, y + width / 2, width / 2, height / 2,
      System.lineSeparator(), getLineWidth(), x + width / 2, y + width / 2, width / 4,
      height / 4, System.lineSeparator());
  }
}

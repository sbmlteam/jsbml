package examples;

import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.render.director.OmittedProcessNode;


public class LaTeXOmittedProcessNode extends LaTeXSBGNProcessNode
  implements OmittedProcessNode<String> {

  public LaTeXOmittedProcessNode(double lineWidth, double processNodeSize) {
    super(lineWidth, processNodeSize);
  }


  @Override
  public String draw(double x, double y, double z, double width, double height,
    double depth, double rotationAngle, Point rotationCenter) {
    StringBuffer result = new StringBuffer();
    result.append(String.format(
      "\\draw[line width=%s, fill=white, draw=black, rotate around={%s:(%spt,%spt)}] (%spt, %spt) rectangle ++(%spt, %spt);%s",
      getLineWidth(), rotationAngle, rotationCenter.getX(),
      rotationCenter.getY(), x, y, width, height, System.lineSeparator()));
    result.append(String.format("\\node[rotate=%s] (uncertain%s%s) at (%spt, %spt) {//};", -rotationAngle, x, y, x + width/2, y + height/2));
    return result.toString();
  }


  @Override
  public String draw(double x, double y, double z, double width, double height,
    double depth) {
    StringBuffer result = new StringBuffer();
    result.append(String.format(
      "\\draw[line width=%s, fill=white, draw=black] (%spt, %spt) rectangle ++(%spt, %spt);%s",
      getLineWidth(), x, y, width, height, System.lineSeparator()));
    result.append(String.format("\\node[] (uncertain%s%s) at (%spt, %spt) {//};", x, y, x + width/2, y + height/2));
    return result.toString();
  }
}

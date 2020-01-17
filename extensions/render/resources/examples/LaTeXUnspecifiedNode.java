package examples;

import org.sbml.jsbml.ext.render.director.UnspecifiedNode;


public class LaTeXUnspecifiedNode extends UnspecifiedNode<String> {
  
  public LaTeXUnspecifiedNode(double lineWidth) {
    super();
    setLineWidth(lineWidth);
  }
  
  @Override
  public String draw(double x, double y, double z, double width, double height,
    double depth) {
    return String.format("\\draw[line width=%spt] (%spt, %spt) ellipse (%spt and %spt);",
      getLineWidth(), x + width / 2, y + height / 2, width/2, height/2);
  }
}

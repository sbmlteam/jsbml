package examples;

import org.sbml.jsbml.ext.render.director.SimpleChemical;


public class LaTeXSimpleChemical extends SimpleChemical<String> {
  
  public LaTeXSimpleChemical(double lineWidth) {
    super();
    setLineWidth(lineWidth);
  }
  
  @Override
  public String draw(double x, double y, double z, double width, double height,
    double depth) {
    // To stay within bounding-box:
    double radius = Math.min(width, height) / 2; 
    return String.format("\\draw[line width=%spt] (%spt, %spt) ellipse (%spt and %spt);",
      getLineWidth(), x + width / 2, y + height / 2, radius, radius);
  }
}

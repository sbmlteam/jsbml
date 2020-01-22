package examples;

import org.sbml.jsbml.ext.render.director.PerturbingAgent;


public class LaTeXPerturbingAgent extends PerturbingAgent<String> {

  public LaTeXPerturbingAgent(double lineWidth) {
    super();
    setLineWidth(lineWidth);
  }
  
  @Override
  public String draw(double x, double y, double z, double width, double height,
    double depth) {
    return String.format(
      "\\draw[line width=%s] (%spt, %spt) -- (%spt, %spt) -- (%spt, %spt) -- (%spt, %spt) -- (%spt, %spt) -- (%spt, %spt) -- cycle;",
      getLineWidth(), x, y, x + height / 2, y + height / 2, x, y + height,
      x + width, y + height, x + width - height / 2, y + height / 2, x + width,
      y);
  }
}

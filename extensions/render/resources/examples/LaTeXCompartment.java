package examples;

import org.sbml.jsbml.ext.render.director.Compartment;


public class LaTeXCompartment extends Compartment<String> {
  
  public LaTeXCompartment(double lineWidth) {
    super();
    setLineWidth(lineWidth);
  }
  
  @Override
  public String draw(double x, double y, double z, double width, double height,
    double depth) {
    return String.format(
      "\\draw[blue,line width=%spt] (%spt, %spt) rectangle ++(%spt, %spt); %% Compartment: ",
      getLineWidth(), x, y, width, height);
  }
}

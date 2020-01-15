package examples;

import org.sbml.jsbml.ext.render.director.Macromolecule;


public class LaTeXMacromolecule extends Macromolecule<String> {

  public LaTeXMacromolecule(double lineWidth) {
    super();
    setLineWidth(lineWidth);
  }
  
  @Override
  public String draw(double x, double y, double z, double width, double height,
    double depth) {
    StringBuffer result = new StringBuffer();
    if(hasCloneMarker()) {
      result.append(String.format("\\fill[fill=red] (%spt,%spt) -- ++(%spt,0) {[rounded corners=5] -- ++(0,%spt) -- ++(-%spt,0)} -- cycle;", x, y + 0.7*height, width, 0.3*height, width));
      result.append(System.lineSeparator());
    }
    result.append(String.format(
      "\\draw[line width=%spt, rounded corners] (%spt, %spt) rectangle (%spt, %spt);",
      getLineWidth(), x, y, x+width, y+height));
    return result.toString(); 
  }
}

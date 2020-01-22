package examples;

import org.sbml.jsbml.ext.render.director.NucleicAcidFeature;


public class LaTeXNucleicAcidFeature extends NucleicAcidFeature<String> {

  public LaTeXNucleicAcidFeature(double lineWidth) {
    super();
    setLineWidth(lineWidth);
  }
  
  @Override
  public String draw(double x, double y, double z, double width, double height,
    double depth) {
    return String.format("\\draw[line width=%s] (%spt,%spt) -- ++(%spt,0) {[rounded corners=5] -- ++(0,%spt) -- ++(-%spt,0)} -- cycle;", 
      getLineWidth(), x, y, width, height, width);
  }
}

package examples.render;

import org.sbml.jsbml.ext.render.Ellipse;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.director.SimpleChemical;


public class RenderSimpleChemical extends SimpleChemical<LocalStyle> {
  private String stroke, fill, clone;
  
  public RenderSimpleChemical(double strokeWidth, String stroke, String fill, String clone) {
    super();
    setLineWidth(strokeWidth);
    this.stroke = stroke;
    this.fill = fill;
    this.clone = clone;
  }
  
  @Override
  public LocalStyle draw(double x, double y, double z, double width, double height,
    double depth) {
    RenderGroup chemical = new RenderGroup();
    
    if(hasCloneMarker()) {
      // TODO -> use clone here.
    }
    Ellipse chemicalEllipse = chemical.createEllipse();
    chemicalEllipse.setCx(width/2); chemicalEllipse.setAbsoluteCx(true);
    chemicalEllipse.setCy(height/2); chemicalEllipse.setAbsoluteCy(true);
    chemicalEllipse.setRx(Math.min(width, height)/2); chemicalEllipse.setAbsoluteRx(true);
    chemicalEllipse.setStroke(stroke);
    chemicalEllipse.setStrokeWidth(getLineWidth());
    chemicalEllipse.setFill(fill);
    
    return new LocalStyle(chemical);
  }
}

package examples.render;

import org.sbml.jsbml.ext.render.Ellipse;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Polygon;
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
    double radius = Math.min(width, height) / 2;
    
    Ellipse background = chemical.createEllipse();
    background.setCx(width/2); background.setAbsoluteCx(true);
    background.setCy(height/2); background.setAbsoluteCy(true);
    background.setRx(radius); background.setAbsoluteRx(true);
    background.setStroke(stroke);
    background.setStrokeWidth(0);
    background.setFill(fill);
    
    if(hasCloneMarker()) {
      Polygon cloneMarker = chemical.createPolygon();
      // The factor before radius is cos(asin(2*(0.7-0.5)))
      RenderLayoutBuilder.addRenderPoint(cloneMarker, width/2 - 0.9165151*radius, 0.7*height);
      
      double baseStrength = radius/3; // manually approximated
      RenderLayoutBuilder.addRenderCubicBezier(cloneMarker, 
        width/2 - 0.9165151*radius + baseStrength, 0.7*height + 2.291288*baseStrength, 
        width/2 + 0.9165151*radius - baseStrength, 0.7*height + 2.291288*baseStrength, 
        width/2 + 0.9165151*radius, 0.7*height);
      cloneMarker.setStroke(clone);
      cloneMarker.setStrokeWidth(0);
      cloneMarker.setFill(clone);
    }
    Ellipse chemicalEllipse = background.clone();
    chemicalEllipse.setStroke(stroke);
    chemicalEllipse.setStrokeWidth(getLineWidth());
    chemicalEllipse.unsetFill();
    chemical.addElement(chemicalEllipse);
    
    return new LocalStyle(chemical);
  }
}

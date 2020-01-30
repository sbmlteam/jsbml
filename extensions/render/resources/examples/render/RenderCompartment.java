package examples.render;

import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Rectangle;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.director.Compartment;


public class RenderCompartment extends Compartment<LocalStyle> {

  private String stroke, fill;
  
  public RenderCompartment(double strokeWidth, String stroke, String fill) {
    super();
    setLineWidth(strokeWidth);
    this.stroke = stroke;
    this.fill = fill;
  }
  
  @Override
  public LocalStyle draw(double x, double y, double z, double width,
    double height, double depth) {
    RenderGroup compartment = new RenderGroup();
    Rectangle rect = compartment.createRectangle();
    rect.setX(0); rect.setAbsoluteX(true);
    rect.setY(0); rect.setAbsoluteY(true);
    rect.setWidth(width); rect.setAbsoluteWidth(true);
    rect.setHeight(height); rect.setAbsoluteHeight(true);
    rect.setStroke(stroke);
    rect.setStrokeWidth(getLineWidth());
    rect.setFill(fill);
    
    return new LocalStyle(compartment);
  }
}

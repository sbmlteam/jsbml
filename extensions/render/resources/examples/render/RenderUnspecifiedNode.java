package examples.render;

import org.sbml.jsbml.ext.render.Ellipse;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.director.UnspecifiedNode;


public class RenderUnspecifiedNode extends UnspecifiedNode<LocalStyle> {
private String stroke, fill, clone;
  
  public RenderUnspecifiedNode(double strokeWidth, String stroke, String fill, String clone) {
    super();
    setLineWidth(strokeWidth);
    this.stroke = stroke;
    this.fill = fill;
    this.clone = clone;
  }
  
  @Override
  public LocalStyle draw(double x, double y, double z, double width, double height,
    double depth) {
    RenderGroup node = new RenderGroup();
    
    if(hasCloneMarker()) {
      // TODO -> use clone here.
    }
    Ellipse chemicalEllipse = node.createEllipse();
    chemicalEllipse.setCx(width/2); chemicalEllipse.setAbsoluteCx(true);
    chemicalEllipse.setCy(height/2); chemicalEllipse.setAbsoluteCy(true);
    chemicalEllipse.setRx(width/2); chemicalEllipse.setAbsoluteRx(true);
    chemicalEllipse.setRy(height/2); chemicalEllipse.setAbsoluteRy(true);
    
    chemicalEllipse.setStroke(stroke);
    chemicalEllipse.setStrokeWidth(getLineWidth());
    chemicalEllipse.setFill(fill);
    
    return new LocalStyle(node);
  }
}

package examples.render;

import org.sbml.jsbml.ext.render.Ellipse;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Polygon;
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
    
    Ellipse background = node.createEllipse();
    background.setCx(width/2); background.setAbsoluteCx(true);
    background.setCy(height/2); background.setAbsoluteCy(true);
    background.setRx(width/2); background.setAbsoluteRx(true);
    background.setRy(height/2); background.setAbsoluteRy(true);
    
    background.setStroke(stroke);
    background.setStrokeWidth(0);
    background.setFill(fill);
    
    if(hasCloneMarker()) {
      // unit circle --> stretch coordinates along x-axis by factor width/2 and
      // along y-axis by factor height/2
      double radius = 1; 
      double stretchX = width/2;
      double stretchY = height/2;
      
      Polygon cloneMarker = node.createPolygon();
      // The factor before radius is cos(asin(2*(0.7-0.5)))
      RenderLayoutBuilder.addRenderPoint(cloneMarker, stretchX*(1 - 0.9165151*radius), stretchY * (1 + 0.4*radius));
      
      double baseStrength = radius/3; // manually approximated
      RenderLayoutBuilder.addRenderCubicBezier(cloneMarker, 
        stretchX*(1 - 0.9165151*radius + baseStrength), stretchY * (1 + 0.4*radius + 2.291288*baseStrength), 
        stretchX*(1 + 0.9165151*radius - baseStrength), stretchY * (1 + 0.4*radius + 2.291288*baseStrength), 
        stretchX * (1 + 0.9165151*radius), 0.7*height);
      cloneMarker.setStroke(clone);
      cloneMarker.setStrokeWidth(0);
      cloneMarker.setFill(clone);
    }
    Ellipse nodeEllipse = background.clone();
    
    nodeEllipse.setStroke(stroke);
    nodeEllipse.setStrokeWidth(getLineWidth());
    nodeEllipse.unsetFill();
    
    node.addElement(nodeEllipse);
    
    return new LocalStyle(node);
  }
}

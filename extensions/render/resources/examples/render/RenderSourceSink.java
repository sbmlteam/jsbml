package examples.render;

import org.sbml.jsbml.ext.render.Ellipse;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Polygon;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.director.SourceSink;


public class RenderSourceSink extends SourceSink<LocalStyle> {

  private String stroke, fill;
  
  public RenderSourceSink(double strokeWidth, String stroke, String fill) {
    super();
    setLineWidth(strokeWidth);
    this.stroke = stroke;
    this.fill = fill;
  }
  
  @Override
  public LocalStyle draw(double x, double y, double z, double width,
    double height, double depth) {
    RenderGroup result = new RenderGroup();
    Ellipse circle = result.createEllipse();
    circle.setCx(width/2);
    circle.setAbsoluteCx(true);
    circle.setCy(height/2);
    circle.setAbsoluteCy(true);
    circle.setRx(0.45*Math.min(width, height));
    circle.setAbsoluteRx(true);
    
    circle.setStroke(stroke);
    circle.setStrokeWidth(getLineWidth());
    circle.setFill(fill);
    
    Polygon diagonal = result.createPolygon();
    if(width > height) {
      RenderLayoutBuilder.addRenderPoint(diagonal, (width+height)/2, 0);
      RenderLayoutBuilder.addRenderPoint(diagonal, (width-height)/2, height);
    } else {
      RenderLayoutBuilder.addRenderPoint(diagonal, width, (height-width)/2);
      RenderLayoutBuilder.addRenderPoint(diagonal, 0, (width+height)/2);
    }
    diagonal.setStroke(stroke);
    diagonal.setStrokeWidth(getLineWidth());
    diagonal.setFill(fill);
    
    return new LocalStyle(result);
  }
}

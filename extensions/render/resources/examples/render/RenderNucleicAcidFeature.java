package examples.render;

import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Polygon;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.director.NucleicAcidFeature;


public class RenderNucleicAcidFeature extends NucleicAcidFeature<LocalStyle> {
  private String stroke, fill, clone;
  private double borderRadius;
  
  /**
   * 
   * @param strokeWidth
   * @param stroke
   * @param fill
   * @param clone
   * @param borderRadius the 'radius' (absolute measure) of the rounded corners (bottom left and right)
   */
  public RenderNucleicAcidFeature(double strokeWidth, String stroke, String fill, String clone, double borderRadius) {
    super();
    setLineWidth(strokeWidth);
    this.stroke = stroke;
    this.fill = fill;
    this.clone = clone;
    this.borderRadius = borderRadius;
  }
  
  @Override
  public LocalStyle draw(double x, double y, double z, double width, double height,
    double depth) {
    RenderGroup naFeature = new RenderGroup();
    
    if(hasCloneMarker()) {
      // TODO -> use clone here.
    }
    Polygon naFeaturePoly = naFeature.createPolygon();
    RenderLayoutBuilder.addRenderPoint(naFeaturePoly, 0, height - borderRadius);
    RenderLayoutBuilder.addRenderPoint(naFeaturePoly, 0, 0);
    RenderLayoutBuilder.addRenderPoint(naFeaturePoly, width, 0);    
    RenderLayoutBuilder.addRenderPoint(naFeaturePoly, width, height - borderRadius);
    // Bottom right corner:
    RenderLayoutBuilder.addRenderCubicBezier(naFeaturePoly, 
      width, height - (borderRadius/2), width - (borderRadius/2), height, width - borderRadius, height);
    
    RenderLayoutBuilder.addRenderPoint(naFeaturePoly, borderRadius, height);
    // Bottom left corner:
    RenderLayoutBuilder.addRenderCubicBezier(naFeaturePoly, 
      borderRadius / 2, height, 0, height - (borderRadius/2), 0, height - borderRadius);
    
    naFeaturePoly.setStroke(stroke);
    naFeaturePoly.setStrokeWidth(getLineWidth());
    naFeaturePoly.setFill(fill);
    
    return new LocalStyle(naFeature);
  }
}

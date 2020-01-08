package examples;

import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.CubicBezier;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.TextGlyph;
import org.sbml.jsbml.ext.render.ListOfLocalRenderInformation;
import org.sbml.jsbml.ext.render.director.LayoutBuilder;

// TODO: is this intended usage?
public class BasicLayoutBuilder implements LayoutBuilder<ListOfLocalRenderInformation> {

  private ListOfLocalRenderInformation product;
  
  public BasicLayoutBuilder() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public void builderStart(Layout layout) {
    // TODO Auto-generated method stub
    product = new ListOfLocalRenderInformation(layout.getLevel(), layout.getVersion());
    
  }

  @Override
  public void buildCompartment(CompartmentGlyph compartmentGlyph) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void buildConnectingArc(SpeciesReferenceGlyph srg, ReactionGlyph rg,
    double curveWidth) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void buildCubicBezier(CubicBezier cubicBezier, double lineWidth) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void buildEntityPoolNode(SpeciesGlyph speciesGlyph,
    boolean cloneMarker) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void buildProcessNode(ReactionGlyph reactionGlyph,
    double rotationAngle, double curveWidth) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void buildTextGlyph(TextGlyph textGlyph) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void builderEnd() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public ListOfLocalRenderInformation getProduct() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isProductReady() {
    // TODO Auto-generated method stub
    return false;
  }
  
  
}

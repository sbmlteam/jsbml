package examples.render;

import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.render.HTextAnchor;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Polygon;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.Text;
import org.sbml.jsbml.ext.render.VTextAnchor;
import org.sbml.jsbml.ext.render.director.UncertainProcessNode;


public class RenderUncertainProcessNode extends RenderSBGNProcessNode
  implements UncertainProcessNode<LocalStyle> {

  public RenderUncertainProcessNode(double strokeWidth, String stroke,
    String fill, double nodeSize) {
    super(strokeWidth, stroke, fill, nodeSize);
  }


  @Override
  public LocalStyle draw(double x, double y, double z, double width,
    double height, double depth, double rotationAngle, Point rotationCenter) {
    RenderGroup group = new RenderGroup();
    // This applies to the r.g.'s curve too.
    group.setStroke(getStroke());
    group.setStrokeWidth(getLineWidth());
    double rotationRadians = Math.toRadians(rotationAngle);
    double cos = Math.cos(rotationRadians);
    double sin = Math.sin(rotationRadians);
    
    Polygon square = group.createPolygon();
    x = -getNodeSize() / 2;
    y = -getNodeSize() / 2;
    addRotatedRenderPoint(square, x, y, rotationRadians, width / 2, height / 2);
    
    y = getNodeSize() / 2;
    addRotatedRenderPoint(square, x, y, rotationRadians, width / 2, height / 2);
    
    x = getNodeSize() / 2;
    addRotatedRenderPoint(square, x, y, rotationRadians, width / 2, height / 2);
    
    y = -getNodeSize() / 2;
    addRotatedRenderPoint(square, x, y, rotationRadians, width / 2, height / 2);
    square.setFill(getFill());
    
    Text questionmark = group.createText();
    double breadth = 13; // based on font-size, found manually
    double fontHeight = 20; // based on font-size, found manually 
    questionmark.setFontSize((short) 10);
    questionmark.setFontFamily("monospace");
    questionmark.setTextAnchor(HTextAnchor.START);
    questionmark.setVTextAnchor(VTextAnchor.TOP);
    questionmark.setTransform(new Double[] {new Double(cos), new Double(sin),
      new Double(-sin), new Double(cos),
      new Double(0.5 * (width - breadth * cos + fontHeight * sin)),
      new Double(0.5 * (height - breadth * sin - fontHeight * cos))});
    
    questionmark.setX(0); questionmark.setAbsoluteX(true);
    questionmark.setY(0); questionmark.setAbsoluteY(true);
    // TODO: <render:text ...>?</render:text> how to put a questionmark in there?
    // questionmark.registerChild("?");
    
    // System.out.println("Questionmark has " + questionmark.getChildCount() + " children");
    
    return new LocalStyle(group);
  }
}

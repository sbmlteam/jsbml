/**
 * 
 */
package org.sbml.jsbml.ext.layout.test;

import static org.junit.Assert.*;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.ext.layout.CubicBezier;
import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;
import org.sbml.jsbml.ext.layout.LineSegment;
import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.layout.ReactionGlyph;


/**
 * Tests select methods of {@link org.sbml.jsbml.ext.layout.CurveSegment},
 * {@link org.sbml.jsbml.ext.layout.LineSegment} and
 * {@link org.sbml.jsbml.ext.layout.CubicBezier}
 * 
 * @author DavidVetter
 */
public class CurveSegmentTest {

  /**
   * Test method for {@link org.sbml.jsbml.ext.layout.CurveSegment#readAttribute(java.lang.String, java.lang.String, java.lang.String)}.
   * when reading a line-segment
   * @throws XMLStreamException 
   * @throws SBMLException 
   */
  @Test
  public void testReadAttributeTypeLineSegment() throws SBMLException, XMLStreamException {
    SBMLDocument doc = new SBMLDocument(3,1);
    Model model = doc.createModel("testLayoutWriting");

    LayoutModelPlugin lModel = new LayoutModelPlugin(model);
    Layout layout = lModel.createLayout("layout");

    model.addExtension(LayoutConstants.namespaceURI, lModel);
    
    ReactionGlyph rg = layout.createReactionGlyph("RG");
    Curve curve = rg.createCurve();
    curve.createLineSegment(new Point(0d, 10d), new Point(20d, 10d)); 

    String writtenDocument = JSBML.writeSBMLToString(doc);
    // System.out.println(writtenDocument);
    SBMLDocument readDoc = JSBML.readSBMLFromString(writtenDocument);
    Layout readLayout = ((LayoutModelPlugin) readDoc.getModel().getExtension(
      LayoutConstants.namespaceURI)).getLayout(0);
    
    CurveSegment readSegment = readLayout.getReactionGlyph("RG").getCurve().getCurveSegment(0); 
    assertFalse(readSegment.isCubicBezier());
    assertTrue(readSegment.isLineSegment());
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ext.layout.CurveSegment#readAttribute(java.lang.String, java.lang.String, java.lang.String)}.
   * when reading a line-segment
   * @throws XMLStreamException 
   * @throws SBMLException 
   */
  @Test
  public void testReadAttributeTypeCubicBezier() throws SBMLException, XMLStreamException {
    SBMLDocument doc = new SBMLDocument(3,1);
    Model model = doc.createModel("testLayoutWriting");

    LayoutModelPlugin lModel = new LayoutModelPlugin(model);
    Layout layout = lModel.createLayout("layout");

    model.addExtension(LayoutConstants.namespaceURI, lModel);
    
    ReactionGlyph rg = layout.createReactionGlyph("RG");
    Curve curve = rg.createCurve();
    curve.createCubicBezier(new Point(0d, 10d), new Point(5d, 10d),
      new Point(10d, 15d), new Point(10d, 20d)); 

    String writtenDocument = JSBML.writeSBMLToString(doc);
    // System.out.println(writtenDocument);
    SBMLDocument readDoc = JSBML.readSBMLFromString(writtenDocument);
    Layout readLayout = ((LayoutModelPlugin) readDoc.getModel().getExtension(
      LayoutConstants.namespaceURI)).getLayout(0);
    
    CurveSegment readSegment = readLayout.getReactionGlyph("RG").getCurve().getCurveSegment(0); 
    assertTrue(readSegment.isCubicBezier());
    assertFalse(readSegment.isLineSegment());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.layout.CurveSegment#isCubicBezier()}.
   */
  @Test
  public void testIsCubicBezier() {
    CubicBezier cb = new CubicBezier();
    assertTrue(cb.isCubicBezier());
    
    LineSegment ls = new LineSegment();
    assertFalse(ls.isCubicBezier());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.layout.CurveSegment#isLineSegment()}.
   */
  @Test
  public void testIsLineSegment() {
    CubicBezier cb = new CubicBezier();
    assertFalse(cb.isLineSegment());
    
    LineSegment ls = new LineSegment();
    assertTrue(ls.isLineSegment());
  }
}

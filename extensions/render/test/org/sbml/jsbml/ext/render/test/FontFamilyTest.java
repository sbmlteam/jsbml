package org.sbml.jsbml.ext.render.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.ext.render.FontFamily;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.ext.render.Text;

/**
 * Tests if the font-family attribute of a SBML Document persists if 
 * written to a String and read back from String to a SBMLDocument. 
 * @author Onur &Oumlzel		
 * @since 1.5
 */
public class FontFamilyTest {
  
  private SBMLDocument sb;
  private Text txt;
  
  @Before 
  public void setUp() {
    sb = new SBMLDocument(3, 1);
    txt = new Text(); 
    txt.setFontFamily(FontFamily.MONOSPACE);
    Map<String, String> attributes = new HashMap<String, String>();
    attributes.put(RenderConstants.fontFamily, txt.getFontFamily()); 
    sb.setSBMLDocumentAttributes(attributes);
  }
  
  /**
   * Tests if the SBML Document actually has set the font-family attribute
   * before writing or reading operations.
   */
  @Test
  public void isSetFontFamilyTest() {
    Map<String, String> sbAttributes = sb.getSBMLDocumentAttributes();
    String fontFamily = sbAttributes.get(RenderConstants.fontFamily);
    assertEquals("monospace", fontFamily);
  }
  
  /**
   * Tests if the font-family attribute persists when writing and reading to String. 
   */
  @Test 
  public void writeAndReadTest() {
    SBMLReader reader = new SBMLReader(); 
    SBMLWriter writer = new SBMLWriter(); 
    SBMLDocument sbFromString = new SBMLDocument(1, 1);
    
    try {
      String stringDocument = writer.writeSBMLToString(sb);
      sbFromString = reader.readSBMLFromString(stringDocument);
    } catch (SBMLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (XMLStreamException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    Map<String, String> sbFromStringAttributes = sbFromString.getSBMLDocumentAttributes();
    String fontFamily = sbFromStringAttributes.get(RenderConstants.fontFamily);
    assertEquals("monospace", fontFamily);
    assertEquals(sbFromString.getLevel(), 3); //if reading was successful level has to be 3
  }
}

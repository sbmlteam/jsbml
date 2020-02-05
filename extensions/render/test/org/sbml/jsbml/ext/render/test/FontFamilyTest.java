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

/**
 * Tests if the font-family attribute of a SBML Document persists if 
 * written to a String and read back from String to a SBMLDocument. 
 * @author Onur &Oumlzel		
 * @since 1.5
 */
public class FontFamilyTest {
  
  private SBMLDocument sb;
  
  @Before 
  public void setUp() {
    sb = new SBMLDocument(3, 1);
    Map<String, String> attributes = new HashMap<String, String>();
    attributes.put(RenderConstants.fontFamily, FontFamily.MONOSPACE.toString()); 
    sb.setSBMLDocumentAttributes(attributes);
  }
  
  /**
   * Tests if the SBML Document actually has set the font-family attribute
   * before writing or reading operations.
   */
  @Test
  public void getFontFamilyTest() {
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
    SBMLDocument sbFromString = new SBMLDocument(1, 1); //dummy level and version
    
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
    
    //if reading was successfull lvl should be again the value of sb 
    assertEquals(sbFromString.getLevel(), sb.getLevel()); 
    assertEquals("monospace", fontFamily);
  }
}

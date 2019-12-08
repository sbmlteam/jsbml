package org.sbml.jsbml.xml.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBMLReader;

/**
 * Tests if SBMLDocument containing /u0E94 as starting letter 
 * in metaId is considered valid as expected. 
 * 
 * @author Onur Özel
 * @since 1.5
 * @date 07.12.2019
 */

public class ExoticMetaIdTest {
   
  private SBMLDocument doc; 
  
  @Test
  public void metaIdTest() {
    SBMLReader reader = new SBMLReader();
    try {
      doc = reader.readSBMLFromString("<?xml version='1.0' encoding='UTF-8\'?>\r\n" + 
          "<sbml xmlns='http://www.sbml.org/sbml/level2/version4' level='2' version='4'>\r\n" + 
          "  <model metaid='ດA१'/>\r\n" + 
          "</sbml>");
    } catch (XMLStreamException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    doc.checkConsistencyOffline();
    List<SBMLError> valErrors = doc.getListOfErrors().getValidationErrors();
    System.out.println(valErrors);
    assertTrue(valErrors.isEmpty());
  }
  
  //TODO - Test more exoting things and do it with digits also 
}

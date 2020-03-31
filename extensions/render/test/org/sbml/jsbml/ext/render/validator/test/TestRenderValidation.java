package org.sbml.jsbml.ext.render.validator.test;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;


public class TestRenderValidation {

  @Test
  public void test() throws XMLStreamException {
    
    // Read some file
//    InputStream in = getClass().getResourceAsStream("/org/sbml/jsbml/ext/render/validator/test/data/invalid.xml");
//    SBMLDocument doc = SBMLReader.read(in);
//    int nErrors = doc.checkConsistencyOffline();
//    SBMLErrorLog errors = doc.getErrorLog();
//    System.out.println("\n" +  nErrors + " Errors: ");
//    for(int i = 0; i < errors.getErrorCount(); i++) {
//      SBMLError error = errors.getError(i);
//      System.out.println(
//        error.getCode() + (("" + error.getCode()).startsWith("13")
//          ? (": " + error.getMessage() + "\n") : "")); // Do assertions here
//    }
//    
    // fail("Not yet implemented");
  }
}

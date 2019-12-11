package org.sbml.jsbml.xml.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLError.SEVERITY;
import org.sbml.jsbml.test.sbml.TestReadFromFile5;
import org.sbml.jsbml.validator.SBMLValidator;

/**
 * @author Onur Özel
 * @since 1.5
 */
public class CheckConsistensyOfflineTests {

  private HashMap<String, SBMLDocument> docs;

  /**
   * Sets up the files to be tested.
   * 
   * @throws XMLStreamException
   */
  @Before
  public void setUp() throws XMLStreamException {
    InputStream fileStream025 = TestReadFromFile5.class.getResourceAsStream(
      "/org/sbml/jsbml/xml/test/data/l2v1/BIOMD0000000025.xml");
    InputStream fileStream191 = TestReadFromFile5.class.getResourceAsStream(
      "/org/sbml/jsbml/xml/test/data/l2v3/BIOMD0000000191.xml");
    InputStream fileStream227 = TestReadFromFile5.class.getResourceAsStream(
      "/org/sbml/jsbml/xml/test/data/l2v1/BIOMD0000000227.xml");
    InputStream fileStream228 = TestReadFromFile5.class.getResourceAsStream(
      "/org/sbml/jsbml/xml/test/data/l2v4/BIOMD0000000228.xml");
    InputStream fileStream229 = TestReadFromFile5.class.getResourceAsStream(
      "/org/sbml/jsbml/xml/test/data/l2v4/BIOMD0000000229.xml");
    SBMLDocument doc25 = new SBMLReader().readSBMLFromStream(fileStream025);
    SBMLDocument doc191 = new SBMLReader().readSBMLFromStream(fileStream191);
    SBMLDocument doc227 = new SBMLReader().readSBMLFromStream(fileStream227);
    SBMLDocument doc228 = new SBMLReader().readSBMLFromStream(fileStream228);
    SBMLDocument doc229 = new SBMLReader().readSBMLFromStream(fileStream229);
    docs = new HashMap<String, SBMLDocument>();
    docs.put("25", doc25);
    docs.put("191", doc191);
    docs.put("227", doc227);
    docs.put("228", doc228);
    docs.put("229", doc229);
  }


  /**
   * Iterates over hashmap values and tries to validate each SBMLDocument.
   * 
   * @throws IOException
   * @throws XMLStreamException
   */
  @Test
  public void checkConsistency() throws IOException, XMLStreamException {
    try {
      Iterator<Entry<String, SBMLDocument>> it = docs.entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry<String, SBMLDocument> pair =
          (Map.Entry<String, SBMLDocument>) it.next();
        SBMLDocument currentDoc = pair.getValue();
        String currentDocKey = pair.getKey();
        int nbErrors = currentDoc.checkConsistencyOffline();
        System.out.println(
          "Found " + nbErrors + " errors/warnings on Biomodels " + currentDocKey
            + " with the unit checking turned off.");
        it.remove();
      }
    } catch (Exception e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }


  /**
   * Tries to validate biomodels file with id BIOMD0000000025, with all checks
   * on.
   * 
   * @throws IOException
   * @throws XMLStreamException
   */
  @Test
  public void checkConsistencyAll025() throws IOException, XMLStreamException {
    SBMLDocument doc = docs.get("25");
    doc.setConsistencyChecks(SBMLValidator.CHECK_CATEGORY.UNITS_CONSISTENCY,
      true);
    int nbErrors = doc.checkConsistencyOffline();
//	    int numRealErrors = doc.getErrorLog().getNumFailsWithSeverity(SEVERITY.ERROR);
//	    if (numRealErrors > 0) {
//	      System.out.println("# Found " + numRealErrors + " VALIDATION ERRORS !!!");
//	      doc.printErrors(System.out);
//	    }
    System.out.println("Found " + nbErrors
      + " errors/warnings on Biomodels 025 with the unit checking turned on.");
    assertTrue(nbErrors > 0);
//	    assertTrue(doc.getErrorLog().getNumFailsWithSeverity(SEVERITY.ERROR) == 0); 
//	    4 errors with offline validator
//	    0 with online
    assertTrue(nbErrors == doc.getErrorCount());
    assertTrue(nbErrors == doc.getErrorLog().getValidationErrors().size());
  }


  /**
   * Tries to validate biomodels file with id BIOMD0000000191, with all checks
   * on.
   * 
   * @throws IOException
   * @throws XMLStreamException
   */
  @Test
  public void checkConsistencyAll191() throws IOException, XMLStreamException {
    SBMLDocument doc = docs.get("191");
    doc.setConsistencyChecks(SBMLValidator.CHECK_CATEGORY.UNITS_CONSISTENCY,
      true);
    int nbErrors = doc.checkConsistencyOffline();
//	    int numRealErrors = doc.getErrorLog().getNumFailsWithSeverity(SEVERITY.ERROR);
//	    if (numRealErrors > 0) {
//	      System.out.println("# Found " + numRealErrors + " VALIDATION ERRORS !!!");
//	      doc.printErrors(System.out);
//	    }
    System.out.println("Found " + nbErrors
      + " errors/warnings on Biomodels 191 with the unit checking turned on.");
    assertTrue(nbErrors > 0);
//	  	assertTrue(doc.getErrorLog().getNumFailsWithSeverity(SEVERITY.ERROR) == 0);
//	  	3 Errors with offline validator
//	  	0 with online validator
    assertTrue(nbErrors == doc.getErrorCount());
    assertTrue(nbErrors == doc.getErrorLog().getValidationErrors().size());
  }


  /**
   * Tries to validate biomodels file with id BIOMD0000000227, with all checks
   * on.
   * 
   * @throws IOException
   * @throws XMLStreamException
   */
  @Test
  public void checkConsistencyAll227() throws IOException, XMLStreamException {
    SBMLDocument doc = docs.get("227");
    doc.setConsistencyChecks(SBMLValidator.CHECK_CATEGORY.UNITS_CONSISTENCY,
      true);
    int nbErrors = doc.checkConsistencyOffline();
//	    int numRealErrors = doc.getErrorLog().getNumFailsWithSeverity(SEVERITY.ERROR);
//	    if (numRealErrors > 0) {
//	      System.out.println("# Found " + numRealErrors + " VALIDATION ERRORS !!!");
//	      doc.printErrors(System.out);
//	    }
    System.out.println("Found " + nbErrors
      + " errors/warnings on Biomodels 227 with the unit checking turned on.");
    assertTrue(nbErrors > 0);
//	    assertTrue(doc.getErrorLog().getNumFailsWithSeverity(SEVERITY.ERROR) == 0);
//	    57 errors with offline validator
//	    0 with online
    assertTrue(nbErrors == doc.getErrorCount());
    assertTrue(nbErrors == doc.getErrorLog().getValidationErrors().size());
  }


  /**
   * Tries to validate biomodels file with id BIOMD0000000228, with all checks
   * on.
   * 
   * @throws IOException
   * @throws XMLStreamException
   */
  @Test
  public void checkConsistencyAll228() throws IOException, XMLStreamException {
    SBMLDocument doc = docs.get("228");
    doc.setConsistencyChecks(SBMLValidator.CHECK_CATEGORY.UNITS_CONSISTENCY,
      true);
    int nbErrors = doc.checkConsistencyOffline();
    int numRealErrors =
      doc.getErrorLog().getNumFailsWithSeverity(SEVERITY.ERROR);
    if (numRealErrors > 0) {
      System.out.println("# Found " + numRealErrors + " VALIDATION ERRORS !!!");
      doc.printErrors(System.out);
    }
    System.out.println("Found " + nbErrors
      + " errors/warnings on Biomodels 228 with the unit checking turned on.");
    assertTrue(nbErrors > 0);
    assertTrue(doc.getErrorLog().getNumFailsWithSeverity(SEVERITY.ERROR) == 0);
    assertTrue(nbErrors == doc.getErrorCount());
    assertTrue(nbErrors == doc.getErrorLog().getValidationErrors().size());
  }


  /**
   * Tries to validate biomodels file with id BIOMD0000000229, with all checks
   * on.
   * 
   * @throws IOException
   * @throws XMLStreamException
   */
  @Test
  public void checkConsistencyAll229() throws IOException, XMLStreamException {
    SBMLDocument doc = docs.get("229");
    doc.setConsistencyChecks(SBMLValidator.CHECK_CATEGORY.UNITS_CONSISTENCY,
      true);
    int nbErrors = doc.checkConsistencyOffline();
    int numRealErrors =
      doc.getErrorLog().getNumFailsWithSeverity(SEVERITY.ERROR);
    if (numRealErrors > 0) {
      System.out.println("# Found " + numRealErrors + " VALIDATION ERRORS !!!");
      doc.printErrors(System.out);
    }
    System.out.println("Found " + nbErrors
      + " errors/warnings on Biomodels 229 with the unit checking turned on.");
    assertTrue(nbErrors > 0);
    assertTrue(doc.getErrorLog().getNumFailsWithSeverity(SEVERITY.ERROR) == 0);
    assertTrue(nbErrors == doc.getErrorCount());
    assertTrue(nbErrors == doc.getErrorLog().getValidationErrors().size());
  }
}

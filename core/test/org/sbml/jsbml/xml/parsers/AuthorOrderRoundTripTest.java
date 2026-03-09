package org.sbml.jsbml.xml.parsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.io.InputStream;

import org.junit.Test;
import org.sbml.jsbml.Creator;
import org.sbml.jsbml.History;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;

/**
 * Regression test for issue #219: ensure that the order of authors/creators
 * in the RDF annotation is preserved after a read-write-read round trip.
 */
public class AuthorOrderRoundTripTest {

  private static final String RESOURCE = "author-order-example.xml";

  private void assertCreatorsInExpectedOrder(java.util.List<org.sbml.jsbml.Creator> creators) {
    assertEquals(3, creators.size());
    assertEquals("Given1 Family1", creators.get(0).printCreator());
    assertEquals("Given2 Family2", creators.get(1).printCreator());
    assertEquals("Given3 Family3", creators.get(2).printCreator());
  }

  @Test
  public void preservesCreatorOrderAfterRoundTrip() throws Exception {
    SBMLDocument doc1;
    try (InputStream is = AuthorOrderRoundTripTest.class.getResourceAsStream(RESOURCE)) {
      assertNotNull("Test SBML resource not found: " + RESOURCE, is);
      doc1 = new SBMLReader().readSBMLFromStream(is);
    }

    Model m1 = doc1.getModel();
    History h1 = m1.getHistory();
    List<Creator> creators1 = h1.getListOfCreators();
    assertCreatorsInExpectedOrder(creators1);

    // Write back to string
    String xml = new SBMLWriter().writeSBMLToString(doc1);

    // Read again
    SBMLDocument doc2 = new SBMLReader().readSBMLFromString(xml);
    Model m2 = doc2.getModel();
    History h2 = m2.getHistory();
    List<Creator> creators2 = h2.getListOfCreators();
    assertCreatorsInExpectedOrder(creators2);
  }
}
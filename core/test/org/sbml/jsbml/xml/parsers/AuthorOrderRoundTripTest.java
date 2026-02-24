package org.sbml.jsbml.xml.parsers;

import static org.junit.Assert.assertEquals;

import java.util.List;

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

  private static final String SBML_WITH_CREATORS =
      "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
      "<sbml xmlns=\"http://www.sbml.org/sbml/level2/version4\" level=\"2\" version=\"4\">\n" +
      "  <model id=\"m\">\n" +
      "    <annotation>\n" +
      "      <rdf:RDF\n" +
      "          xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n" +
      "          xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\n" +
      "          xmlns:vCard=\"http://www.w3.org/2001/vcard-rdf/3.0#\">\n" +
      "        <rdf:Description rdf:about=\"#m\">\n" +
      "          <dc:creator>\n" +
      "            <rdf:Bag>\n" +
      "              <rdf:li rdf:parseType=\"Resource\">\n" +
      "                <vCard:N rdf:parseType=\"Resource\">\n" +
      "                  <vCard:Family>Family1</vCard:Family>\n" +
      "                  <vCard:Given>Given1</vCard:Given>\n" +
      "                </vCard:N>\n" +
      "              </rdf:li>\n" +
      "              <rdf:li rdf:parseType=\"Resource\">\n" +
      "                <vCard:N rdf:parseType=\"Resource\">\n" +
      "                  <vCard:Family>Family2</vCard:Family>\n" +
      "                  <vCard:Given>Given2</vCard:Given>\n" +
      "                </vCard:N>\n" +
      "              </rdf:li>\n" +
      "              <rdf:li rdf:parseType=\"Resource\">\n" +
      "                <vCard:N rdf:parseType=\"Resource\">\n" +
      "                  <vCard:Family>Family3</vCard:Family>\n" +
      "                  <vCard:Given>Given3</vCard:Given>\n" +
      "                </vCard:N>\n" +
      "              </rdf:li>\n" +
      "            </rdf:Bag>\n" +
      "          </dc:creator>\n" +
      "        </rdf:Description>\n" +
      "      </rdf:RDF>\n" +
      "    </annotation>\n" +
      "  </model>\n" +
      "</sbml>\n";

  @Test
  public void preservesCreatorOrderAfterRoundTrip() throws Exception {
    // First read
    SBMLDocument doc1 = new SBMLReader().readSBMLFromString(SBML_WITH_CREATORS);
    Model m1 = doc1.getModel();
    History h1 = m1.getHistory();
    List<Creator> creators1 = h1.getListOfCreators();

    assertEquals(3, creators1.size());
    assertEquals("Given1 Family1", creators1.get(0).printCreator());
    assertEquals("Given2 Family2", creators1.get(1).printCreator());
    assertEquals("Given3 Family3", creators1.get(2).printCreator());

    // Write back to string
    String xml = new SBMLWriter().writeSBMLToString(doc1);

    // Read again
    SBMLDocument doc2 = new SBMLReader().readSBMLFromString(xml);
    Model m2 = doc2.getModel();
    History h2 = m2.getHistory();
    List<Creator> creators2 = h2.getListOfCreators();

    assertEquals(3, creators2.size());
    assertEquals("Given1 Family1", creators2.get(0).printCreator());
    assertEquals("Given2 Family2", creators2.get(1).printCreator());
    assertEquals("Given3 Family3", creators2.get(2).printCreator());
  }
}
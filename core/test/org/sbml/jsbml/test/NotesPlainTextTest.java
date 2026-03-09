package org.sbml.jsbml.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;

/**
 * Regression test for issue #149:
 * setNotes(String) should handle plain text containing '<' by escaping it,
 * instead of throwing a low-level XML parser exception.
 */
public class NotesPlainTextTest {

  @Test
  public void setNotesWithPlainTextIsEscapedAndDoesNotThrow() throws Exception {
    SBMLDocument doc = new SBMLDocument(3, 1);
    Model m = doc.createModel("m");

    // This would previously cause an XMLStreamException inside setNotes(String).
    m.setNotes("x<y<z");

    String xml = new SBMLWriter().writeSBMLToString(doc);

    // The serialized SBML should contain the escaped text.
    // We don't assert the exact surrounding XHTML structure, only that the text is escaped.
    assertTrue(xml.contains("x&lt;y&lt;z"));

    // And it should still be readable.
    SBMLDocument doc2 = new SBMLReader().readSBMLFromString(xml);
    // Just ensure we can get the model and its notes without error.
    doc2.getModel().getNotesString();
  }
}
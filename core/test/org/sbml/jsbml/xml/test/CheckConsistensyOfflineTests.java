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
import org.sbml.jsbml.test.sbml.TestReadFromFile5;

public class CheckConsistensyOfflineTests {

	private HashMap<String, SBMLDocument> docs;
	private SBMLDocument doc25;
	private SBMLDocument doc191;
	private SBMLDocument doc227;
	private SBMLDocument doc228;
	private SBMLDocument doc229;

	/**
	 * @throws XMLStreamException 
	 * 
	 */
	@Before 
	public void setUp() throws XMLStreamException {
		InputStream fileStream025 = TestReadFromFile5.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/l2v1/BIOMD0000000025.xml");
		InputStream fileStream191 = TestReadFromFile5.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/l2v3/BIOMD0000000191.xml");
		InputStream fileStream227 = TestReadFromFile5.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/l2v1/BIOMD0000000227.xml");
		InputStream fileStream228 = TestReadFromFile5.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/l2v4/BIOMD0000000228.xml");
		InputStream fileStream229 = TestReadFromFile5.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/l2v4/BIOMD0000000229.xml");

		doc25 = new SBMLReader().readSBMLFromStream(fileStream025);
		doc191 = new SBMLReader().readSBMLFromStream(fileStream191);
		doc227 = new SBMLReader().readSBMLFromStream(fileStream227);
		doc228 = new SBMLReader().readSBMLFromStream(fileStream228);
		doc229 = new SBMLReader().readSBMLFromStream(fileStream229);

		docs = new HashMap<String, SBMLDocument>();

		docs.put("25", doc25);
		docs.put("191", doc191);
		docs.put("227", doc227);
		docs.put("228", doc228);
		docs.put("229", doc229);
	}

	/**
	 * Tries to validate biomodels file with id: 
	 * BIOMD0000000025, BIOMD0000000191, BIOMD0000000227, BIOMD0000000228, BIOMD0000000229
	 * @throws IOException
	 * @throws XMLStreamException
	 */
	@Test public void checkConsistency() throws IOException, XMLStreamException {
		try {
			Iterator<Entry<String, SBMLDocument>> it = docs.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, SBMLDocument> pair = (Map.Entry<String, SBMLDocument>)it.next();
				SBMLDocument currentDoc = pair.getValue();
				String currentDocKey = pair.getKey();

				int nbErrors = currentDoc.checkConsistencyOffline();
				System.out.println("Found " + nbErrors + " errors/warnings on Biomodels " + currentDocKey + " with the unit checking turned on.");
				
				it.remove(); // avoids a ConcurrentModificationException
			}
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}

package org.sbml.jsbml.test;

import java.io.BufferedOutputStream;
import java.util.Calendar;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Creator;
import org.sbml.jsbml.History;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.CVTerm.Qualifier;
import org.sbml.jsbml.CVTerm.Type;
import org.sbml.jsbml.xml.stax.SBMLWriter;

public class SBMLWriterTest {

	public static void main(String args[]) {
		SBMLDocument doc = new SBMLDocument(2, 4);
		doc.setNotes("<body>Senseless test commentar</body>");
		
		Model m = doc.createModel("model");

		CVTerm term = new CVTerm();
		term.setQualifierType(Type.MODEL_QUALIFIER);
		term.setModelQualifierType(Qualifier.BQM_IS);
		term.addResource("urn:miriam:kegg.pathway:hsa00010");
		m.addCVTerm(term);

		History history = new History();
		Creator creator = new Creator();
		creator.setFamilyName("Dr\u00e4ger");
		creator.setGivenName("Andreas");
		creator.setEmail("andreas.draeger@uni-tuebingen.de");
		creator.setOrganization("Universit\u00e4t T\u00fcbingen");
		history.addCreator(creator);
		history.setCreatedDate(Calendar.getInstance().getTime());
		history.addModifiedDate(Calendar.getInstance().getTime());
		m.setModelHistory(history);

		m.setNotes("<body>A senseless test model with a senseless notes element.</body>");
		
		m.getUnitDefinition("substance").getUnit(0).setScale(-3);

		try {
			SBMLWriter.write(doc, new BufferedOutputStream(System.out));
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}

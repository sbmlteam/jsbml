package org.sbml.jsbml.test;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.xml.stax.SBMLWriter;

/**
 * @author Andreas Dr&auml;ger
 * @date 2010-11-12
 */
public class EventTest {

	/**
	 * @param args
	 * @throws ParseException
	 * @throws SBMLException
	 * @throws XMLStreamException
	 */
	public static void main(String[] args) throws ParseException,
			XMLStreamException, SBMLException {
		SBMLDocument doc = new SBMLDocument(3, 1);
		Model model = doc.createModel("event_model");
		Compartment c = model.createCompartment("compartment");
		model.createSpecies("s1", c);
		model.createSpecies("s2", c);
		Event ev = model.createEvent();
		ev.createTrigger(false, true, ASTNode.leq(new ASTNode(3),
				new ASTNode(2)));
		ev.createPriority(JSBML.parseFormula("25"));
		ev.createDelay(JSBML.parseFormula("2"));
		ev.createEventAssignment("s1", JSBML.parseFormula("s2"));
		SBMLWriter.write(doc, System.out);
	}
}

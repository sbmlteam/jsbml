package org.sbml.jsbml.test;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.xml.stax.SBMLWriter;

/**
 * @author Andreas Dr&auml;ger
 * @date 2010-11-12
 */
public class EventTest extends SimpleSBaseChangeListener {

	/**
	 * 
	 * @throws ParseException
	 * @throws XMLStreamException
	 * @throws SBMLException
	 */
	public EventTest() throws ParseException, XMLStreamException, SBMLException {
		SBMLDocument doc = new SBMLDocument(3, 1);
		doc.addChangeListener(this);
		Model model = doc.createModel("event_model");
		Compartment c = model.createCompartment("compartment");
		model.createSpecies("s1", c);
		model.createSpecies("s2", c);
		Event ev = model.createEvent();
		Trigger trigger = ev.createTrigger(false, true, JSBML.parseFormula("3 >= 2"));
		trigger.setMath(ASTNode.geq(new ASTNode(ASTNode.Type.NAME_TIME),
				new ASTNode(10)));
		ev.createPriority(JSBML.parseFormula("25"));
		ev.createDelay(JSBML.parseFormula("2"));
		ev.createEventAssignment("s1", JSBML.parseFormula("s2"));
		System.out.println("==================================");
		SBMLWriter.write(doc, System.out);
		System.out.println("==================================");
		doc.setLevelAndVersion(2, 4);
		System.out.println("==================================");
		SBMLWriter.write(doc, System.out);
	}

	/**
	 * @param args
	 * @throws ParseException
	 * @throws SBMLException
	 * @throws XMLStreamException
	 */
	public static void main(String[] args) throws ParseException,
			XMLStreamException, SBMLException {
		new EventTest();
	}
}

package org.sbml.jsbml.test;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Priority;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.text.parser.ParseException;

/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class PriorityTest {

	/**
	 * @param args
	 * @throws ParseException
	 * @throws SBMLException
	 * @throws XMLStreamException
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws ParseException,
			XMLStreamException, SBMLException {
		SBMLDocument doc = new SBMLDocument(3, 1);
		Model model = doc.createModel("test_model");
		Parameter p = model.createParameter("p1");
		p.setValue(1d);
		Event e = model.createEvent("e1");
		Priority prior = e.createPriority();
		prior.setMath(new ASTNode(1));
		Trigger t = e.createTrigger();
		t.setFormula("time == 1");
		EventAssignment ea = e.createEventAssignment(p, ASTNode
				.parseFormula("3"));
		System.out.println((new SBMLWriter()).writeSBMLToString(doc));
	}

}

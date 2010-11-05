package org.sbml.jsbml.test;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.xml.stax.SBMLWriter;

public class Test {

	/**
	 * @param args
	 * @throws SBMLException
	 * @throws XMLStreamException
	 */
	public static void main(String[] args) throws XMLStreamException,
			SBMLException {
		SBMLDocument doc = new SBMLDocument(2, 4);
		Model model = doc.createModel("test_model");
		
		Parameter k1 = model.createParameter("k1");
		Parameter k2 = model.createParameter("k2");
		
		k1.setConstant(false);
		k2.setConstant(false);
		
		Event event = model.createEvent("test_event");
		
		Trigger trigger = event.createTrigger();
		trigger.setMath(ASTNode.geq(new ASTNode(ASTNode.Type.NAME_TIME),
				new ASTNode(10)));
		
		EventAssignment assignment1 = event.createEventAssignment();
		assignment1.setVariable(k1);
		assignment1.setMath(new ASTNode(34));
		
		EventAssignment assignment2 = event.createEventAssignment();
		assignment2.setVariable(k2);
		assignment2.setMath(new ASTNode(k1));
		
		SBMLWriter.write(doc, System.out);
	}

}

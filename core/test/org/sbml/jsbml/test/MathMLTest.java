package org.sbml.jsbml.test;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.text.parser.ParseException;

public class MathMLTest {

	/**
	 * 
	 * @param args
	 * @throws ParseException
	 * @throws XMLStreamException
	 * @throws SBMLException
	 */
	@SuppressWarnings("deprecation")
	public static void main(String args[]) throws ParseException, XMLStreamException, SBMLException {
		SBMLDocument doc = new SBMLDocument(1, 2);
		Model m = doc.createModel();
		Compartment c = m.createCompartment("c1");
		Species s1 = m.createSpecies("s1", c);
		Species s2 = m.createSpecies("s2", c);
		Reaction r = m.createReaction("r1");
		r.createReactant(null, s1);
		r.createProduct(null, s2);
		KineticLaw kl = r.createKineticLaw();
		kl.setFormula("s1 * 3");
		SBMLWriter.write(doc, System.out, ' ', (short) 2);
	}
	
}

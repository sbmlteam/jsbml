package org.sbml.jsbml.test;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLWriter;

public class AddNamespace {

	/**
	 * @param args
	 * @throws XMLStreamException 
	 * @throws SBMLException 
	 */
	public static void main(String[] args) throws SBMLException, XMLStreamException 
	{

		SBMLDocument sbmlDoc = new SBMLDocument(3, 1);
		sbmlDoc.addDeclaredNamespace("xmlns:html", "http://www.w3.org/1999/xhtml");
		sbmlDoc.addNamespace("ns1", "xmlns", "http://www.test.com");
		Model sbmlModel = sbmlDoc.createModel("test_model");
		sbmlModel.addDeclaredNamespace("html", "http://www.w3.org/1999/xhtml");
		sbmlModel.addDeclaredNamespace("ns1", "http://www.test.com");
		// sbmlModel.addDeclaredNamespace("toto:ns2", "http://www.test.com");
		sbmlModel.addDeclaredNamespace("xmlns:ns3", "http://www.test.com");

		// but it is not working. In the output file written with:
		
		SBMLWriter ttt = new SBMLWriter();
		System.out.println( ttt.writeSBMLToString(sbmlDoc) );

		// the "html" namespace is missing (no line with xmlns:html="http://www.w3.org/1999/xhtml")
		// Could you please tell me if it is a bug or if I am doing something wrong?

	}

}

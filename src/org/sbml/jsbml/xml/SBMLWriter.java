package org.sbml.jsbml.xml;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.stream.XMLStreamException;

import org.codehaus.stax2.ri.Stax2EventWriterImpl;
import org.sbml.jsbml.element.SBMLDocument;

import com.ctc.wstx.stax.WstxOutputFactory;

public class SBMLWriter {
	
	public static void write(SBMLDocument sbmlDcument){
		
		sbmlDcument.getSBMLDocumentAttributes();
		
		WstxOutputFactory outputFactory = new WstxOutputFactory();
		Writer writer = new BufferedWriter(new OutputStreamWriter(System.out));
		
		try {
			Stax2EventWriterImpl eventWriter = (Stax2EventWriterImpl) outputFactory.createXMLEventWriter(writer);
			
			
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

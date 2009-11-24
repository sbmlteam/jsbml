package org.sbml.jsbml.xml;

import java.io.File;

import javax.xml.stream.XMLStreamException;

import org.codehaus.stax2.XMLStreamWriter2;
import org.codehaus.staxmate.SMOutputFactory;
import org.codehaus.staxmate.out.SMNamespace;
import org.codehaus.staxmate.out.SMOutputContext;
import org.codehaus.staxmate.out.SMOutputDocument;
import org.codehaus.staxmate.out.SMOutputElement;
import org.sbml.jsbml.element.SBMLDocument;

import com.ctc.wstx.stax.WstxOutputFactory;

public class SBMLWriter {
	
	private static String getNamespaceFrom(int level, int version){
		
		if (level == 3 && version == 1){
			return "http://www.sbml.org/sbml/level3/version1/core";
		}
		else if (level == 2 && version == 4){
			return "http://www.sbml.org/sbml/level2/version4";
		}
		else if (level == 2 && version == 3){
			return "http://www.sbml.org/sbml/level2/version3";
		}
		else if (level == 2 && version == 2){
			return "http://www.sbml.org/sbml/level2/version2";
		}
		else if (level == 2 && version == 1){
			return "http://www.sbml.org/sbml/level2";
		}
		else if (level == 1 && version == 1){
			return "http://www.sbml.org/sbml/level1";
		}
		return null;
	}
	
	public static void write(SBMLDocument sbmlDocument){
		SBMLReader.initializePackageParserNamespaces();
		sbmlDocument.getSBMLDocumentAttributes();
		
		SMOutputFactory smFactory = new SMOutputFactory(WstxOutputFactory.newInstance());
		SMOutputDocument outputDocument;
		try {
			XMLStreamWriter2 streamWriter = smFactory.createStax2Writer(new File("test.xml"));
			outputDocument = SMOutputFactory.createOutputDocument(streamWriter, "1.0", "UTF-8", false);
			outputDocument.setIndentation("\n ", 1, 1);
			
			String SBMLNamespace = getNamespaceFrom(sbmlDocument.getLevel(), sbmlDocument.getVersion());
			SMOutputContext context = outputDocument.getContext();
			SMNamespace namespace = context.getNamespace(SBMLNamespace, "");

			SMOutputElement sbmlElement = outputDocument.addElement(namespace, "sbml");
			
			System.out.println(sbmlElement.getNamespace().getURI());
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){		
		//SBMLDocument testDocument = readSBMLFile("/home/compneur/Desktop/LibSBML-Project/MultiExamples/glutamateReceptor.xml");
		SBMLDocument testDocument = SBMLReader.readSBMLFile("/home/compneur/Desktop/LibSBML-Project/BIOMD0000000002.xml");		
		write(testDocument);
	}


}

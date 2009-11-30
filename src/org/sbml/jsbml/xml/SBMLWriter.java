package org.sbml.jsbml.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import java.util.Map.Entry;

import javax.xml.stream.XMLStreamException;

import org.codehaus.stax2.XMLStreamWriter2;
import org.codehaus.staxmate.SMOutputFactory;
import org.codehaus.staxmate.out.SMNamespace;
import org.codehaus.staxmate.out.SMOutputContext;
import org.codehaus.staxmate.out.SMOutputDocument;
import org.codehaus.staxmate.out.SMOutputElement;
import org.sbml.jsbml.element.Annotation;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.element.SBase;

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
	
	private static void writeNotes(SBase sbase, SMOutputElement element, SBMLParser notesParser, String sbmlNamespace){
		
		SMNamespace namespace = element.getNamespace(sbmlNamespace);
		namespace.setPreferredPrefix("");
		try {
			SMOutputElement notes = element.addElementWithCharacters(namespace, "notes", sbase.getNotesString());
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void writeAnnotation(SBase sbase, SMOutputElement element, ArrayList<SBMLParser> sbmlParsers, String sbmlNamespace){
		SMNamespace namespace = element.getNamespace(sbmlNamespace);
		namespace.setPreferredPrefix("");
		SMOutputElement annotationElement;
		try {
			Annotation annotation = sbase.getAnnotation();
			if (annotation.getAnnotation() != null){
				annotationElement = element.addElementWithCharacters(namespace, "annotation", annotation.getAnnotation());
			}
			else {
				annotationElement = element.addElement(namespace, "annotation");
			}
			
			HashMap<String, String> otherNamespaces = annotation.getAnnotationNamespaces();
			Iterator<Entry<String, String>> it = otherNamespaces.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, String> entry = it.next();
				annotationElement.addAttribute(entry.getKey(), entry.getValue());
			}
			
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void writeSBMLElements(SBMLObjectForXML xmlObject, SMOutputElement parentElement, Object objectToWrite, ArrayList<SBMLParser> listOfPackages){
		Iterator<SBMLParser> iterator = listOfPackages.iterator();
			
		while(iterator.hasNext()){
			SBMLParser parser = iterator.next();
			ArrayList<Object> SBMLElementsToWrite = parser.getListOfSBMLElementsToWrite(objectToWrite);
			
			if (SBMLElementsToWrite == null){
				// TODO test if there are some characters to write.
			}
			else {
				for (int i = 0; i < SBMLElementsToWrite.size(); i++){
					Object nextObjectToWrite = SBMLElementsToWrite.get(i);

					if (!(objectToWrite instanceof SBMLDocument)){
						xmlObject.clear();
						parser.writeElement(xmlObject, nextObjectToWrite);
					}
					parser.writeNamespaces(xmlObject, nextObjectToWrite);
					parser.writeAttributes(xmlObject, nextObjectToWrite);
					SMOutputElement newOutPutElement = null;
					if (xmlObject.isSetName()){
						try {
							if (xmlObject.isSetNamespace()){
								SMNamespace namespaceContext = parentElement.getNamespace(xmlObject.getNamespace(), xmlObject.getPrefix());
								newOutPutElement = parentElement.addElement(namespaceContext, xmlObject.getName());
							}
							else {
								newOutPutElement = parentElement.addElement(xmlObject.getName());
							}
							writeSBMLElements(xmlObject, newOutPutElement, nextObjectToWrite, listOfPackages);
						} catch (XMLStreamException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	private static ArrayList<SBMLParser> getInitializedParsers(SBMLDocument sbmlDocument, String SBMLNamespace){
		SBMLReader.initializePackageParserNamespaces();
		Collection<String> packageNamespaces = sbmlDocument.getNamespaces();
		ArrayList<SBMLParser> SBMLParsers = new ArrayList<SBMLParser>();
		
		if (!packageNamespaces.contains(SBMLNamespace)){
			try {
				SBMLParser sbmlParser = SBMLReader.getPackageParsers(SBMLNamespace).newInstance();
				SBMLParsers.add(sbmlParser);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Iterator<String> iterator = packageNamespaces.iterator();
		while(iterator.hasNext()){
			String namespace = iterator.next();
			try {
				SBMLParser parser = SBMLReader.getPackageParsers(namespace).newInstance();
				SBMLParsers.add(parser);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return SBMLParsers;
	}
	
	public static void write(SBMLDocument sbmlDocument){
		SBMLReader.initializePackageParserNamespaces();
		sbmlDocument.getSBMLDocumentAttributes();
		
		SMOutputFactory smFactory = new SMOutputFactory(WstxOutputFactory.newInstance());
		Stack<Object> SBMLElements = new Stack<Object>();
		SMOutputDocument outputDocument;
		try {
			XMLStreamWriter2 streamWriter = smFactory.createStax2Writer(new File("test.xml"));
			outputDocument = SMOutputFactory.createOutputDocument(streamWriter, "1.0", "UTF-8", false);
			outputDocument.setIndentation("\n ", 1, 1);

			String SBMLNamespace = getNamespaceFrom(sbmlDocument.getLevel(), sbmlDocument.getVersion());
			SMOutputContext context = outputDocument.getContext();
			SMNamespace namespace = context.getNamespace(SBMLNamespace);
			namespace.setPreferredPrefix("");
			SMOutputElement sbmlElement = outputDocument.addElement(namespace, "sbml");
			
			ArrayList<SBMLParser> SBMLParsers = getInitializedParsers(sbmlDocument, SBMLNamespace);
			SBMLObjectForXML xmlObject = new SBMLObjectForXML();
			xmlObject.setName("sbml");
			xmlObject.setNamespace(SBMLNamespace);
			writeSBMLElements(xmlObject, sbmlElement, sbmlDocument, SBMLParsers);
			
			outputDocument.closeRoot();
			
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){		
		//SBMLDocument testDocument = readSBMLFile("/home/compneur/Desktop/LibSBML-Project/MultiExamples/glutamateReceptor.xml");
		SBMLDocument testDocument = SBMLReader.readSBMLFile("/home/compneur/Desktop/LibSBML-Project/BIOMD0000000002.xml");		
		//System.out.println(testDocument.getModel().getAnnotation().getRdfAnnotationNamespaces().size());
		write(testDocument);
	}


}

package org.sbml.jsbml.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.Map.Entry;

import javax.xml.stream.XMLStreamException;

import org.codehaus.stax2.XMLStreamWriter2;
import org.codehaus.staxmate.SMOutputFactory;
import org.codehaus.staxmate.dom.DOMConverter;
import org.codehaus.staxmate.out.SMNamespace;
import org.codehaus.staxmate.out.SMOutputContext;
import org.codehaus.staxmate.out.SMOutputDocument;
import org.codehaus.staxmate.out.SMOutputElement;
import org.sbml.jsbml.element.Annotation;
import org.sbml.jsbml.element.CVTerm;
import org.sbml.jsbml.element.MathContainer;
import org.sbml.jsbml.element.ModelCreator;
import org.sbml.jsbml.element.ModelHistory;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.element.SBase;
import org.sbml.jsbml.element.CVTerm.Qualifier;
import org.w3c.dom.Document;

import uk.ac.ebi.compneur.xml.JAXPFacade;

import com.ctc.wstx.stax.WstxOutputFactory;

public class SBMLWriter {
	
	private static HashMap<String, SBMLParser> instantiatedSBMLParsers = new HashMap<String, SBMLParser>();
	
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
	
	private static void writeNotes(SBase sbase, SMOutputElement element, XMLStreamWriter2 writer, SBMLParser notesParser, String sbmlNamespace){
		
		SMNamespace namespace = element.getNamespace(sbmlNamespace);
		namespace.setPreferredPrefix("");
		try {
			element.addCharacters(" \n");

			DOMConverter converter = new DOMConverter();
			SMOutputElement note = element.addElementWithCharacters(namespace, "notes", " \n");
			String notes = sbase.getNotesString().replaceAll("&", "&amp;");
			Document domDocument = JAXPFacade.getInstance().create(new BufferedReader(new StringReader(notes)), true);
			converter.writeFragment(domDocument.getChildNodes(), writer);
			note.addCharacters("\n");
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private static void writeMathML(MathContainer m, SMOutputElement element, XMLStreamWriter2 writer, SBMLParser mathParser){
		
		try {
			DOMConverter converter = new DOMConverter();
			SMOutputElement mathElement = element.addElement("math");
			
			SMNamespace namespace = mathElement.getNamespace("http://www.w3.org/1998/Math/MathML");
			namespace.setPreferredPrefix("");
			mathElement.addCharacters("\n");
			
			String math = m.getMathBufferToString().replaceAll("&", "&amp;");
			Document domDocument = JAXPFacade.getInstance().create(new BufferedReader(new StringReader(math)), true);
			converter.writeFragment(domDocument.getChildNodes(), writer);
			mathElement.addCharacters("\n");
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private static void writeModelHistory(ModelHistory modelHistory, SMOutputElement rdfElement, SMOutputElement descriptionElement){
		try {
			SMNamespace rdfNamespace = rdfElement.getNamespace();

			if (modelHistory.getNumCreators() > 0){
				SMNamespace creatorNamespace = rdfElement.getNamespace("http://purl.org/dc/elements/1.1/");
				SMOutputElement creatorElement = descriptionElement.addElement(creatorNamespace, "creator");
				creatorElement.addCharacters(" \n");
				
				SMOutputElement bagElement = creatorElement.addElement(creatorNamespace, "Bag");
				
				for (int i = 0; i < modelHistory.getNumCreators(); i++){
					bagElement.addCharacters("\n");
					
					ModelCreator modelCreator = modelHistory.getCreator(i);
					SMOutputElement liElement = bagElement.addElement(rdfNamespace, "li");
					liElement.addAttribute(rdfNamespace, "parseType", "Resource");
					SMNamespace vCardNamespace = rdfElement.getNamespace("http://www.w3.org/2001/vcard-rdf/3.0#");

					if (modelCreator.isSetFamilyName() || modelCreator.isSetGivenName()){
						liElement.addCharacters(" \n");
						SMOutputElement NElement = liElement.addElement(vCardNamespace, "N");
						NElement.addAttribute(rdfNamespace, "parseType", "Resource");
						
						if (modelCreator.isSetFamilyName()){
							NElement.addCharacters(" \n");
							NElement.addElementWithCharacters(vCardNamespace, "Family", modelCreator.getFamilyName());
						}
						if (modelCreator.isSetGivenName()){
							NElement.addCharacters(" \n");
							NElement.addElementWithCharacters(vCardNamespace, "Given", modelCreator.getGivenName());
						}
						NElement.addCharacters(" \n");
					}
					
					if (modelCreator.isSetEmail()){
						liElement.addCharacters(" \n");
						liElement.addElementWithCharacters(vCardNamespace, "EMAIL", modelCreator.getEmail());
					}
					if (modelCreator.isSetOrganization()){
						liElement.addCharacters(" \n");
						SMOutputElement orgElement = liElement.addElement(vCardNamespace, "ORG");
						orgElement.addAttribute(rdfNamespace, "parseType", "Resource");
						
						orgElement.addCharacters(" \n");
						orgElement.addElementWithCharacters(vCardNamespace, "Orgname", modelCreator.getOrganization());
						orgElement.addCharacters(" \n");
					}
					liElement.addCharacters(" \n");
				}
				bagElement.addCharacters(" \n");
				creatorElement.addCharacters(" \n");
			}
			SMNamespace dateNamespace = rdfElement.getNamespace("http://purl.org/dc/terms/");

			if (modelHistory.isSetCreatedDate()){
				descriptionElement.addCharacters(" \n");
				SMOutputElement createdElement = descriptionElement.addElement(dateNamespace, "created");
				createdElement.addAttribute(rdfNamespace, "parseType", "Resource");
				
				createdElement.addCharacters(" \n");
				createdElement.addElementWithCharacters(dateNamespace, "W3CDTF", modelHistory.getCreatedDate().toString());
				createdElement.addCharacters(" \n");
			}
			if (modelHistory.isSetModifiedDate()){
				for (int i = 0; i < modelHistory.getNumModifiedDates(); i++){
					descriptionElement.addCharacters(" \n");
					
					SMOutputElement modifiedElement = descriptionElement.addElement(dateNamespace, "modified");
					modifiedElement.addAttribute(rdfNamespace, "parseType", "Resource");
					
					modifiedElement.addCharacters(" \n");
					modifiedElement.addElementWithCharacters(dateNamespace, "W3CDTF", modelHistory.getModifiedDate(i).toString());
					modifiedElement.addCharacters(" \n");
				}
			}
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
	}
	
	private static void writeCVTerms(List<CVTerm> listOfCVTerms,  SMOutputElement rdfElement, SMOutputElement descriptionElement){
		try {
			SMNamespace rdfNamespace = rdfElement.getNamespace("http://www.w3.org/1999/02/22-rdf-syntax-ns#");
			if (listOfCVTerms.size() > 0){
				
				for (int i = 0; i < listOfCVTerms.size(); i++){
					CVTerm cvTerm = listOfCVTerms.get(i);
					SMNamespace namespaceURI = null;
					String elementName = null;
					if (cvTerm.getQualifierType().equals(Qualifier.BIOLOGICAL_QUALIFIER)){
						namespaceURI = rdfElement.getNamespace("http://biomodels.net/biology-qualifiers/");
						elementName = Annotation.getElementNameEquivalentToQualifier(cvTerm.getBiologicalQualifierType());
					}
					else if (cvTerm.getQualifierType().equals(Qualifier.MODEL_QUALIFIER)){
						namespaceURI = rdfElement.getNamespace("http://biomodels.net/model-qualifiers/");
						elementName = Annotation.getElementNameEquivalentToQualifier(cvTerm.getModelQualifierType());
					}
					
					if (namespaceURI != null && elementName != null){
						descriptionElement.addCharacters(" \n");
						
						SMOutputElement cvTermElement = descriptionElement.addElement(namespaceURI, elementName);
						cvTermElement.addCharacters(" \n");
						if (cvTerm.getNumResources() > 0){
							SMOutputElement bagElement = cvTermElement.addElement(rdfNamespace, "Bag");
							for (int j = 0; j < cvTerm.getNumResources(); j++){
								bagElement.addCharacters(" \n");

								SMOutputElement liElement = bagElement.addElement(rdfNamespace, "li");
								liElement.addAttribute(rdfNamespace, "resource", cvTerm.getResourceURI(j));
							}
							bagElement.addCharacters(" \n");
							cvTermElement.addCharacters(" \n");
						}
					}
				}
			}
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
	}
	
	private static void writeRDFAnnotation(Annotation annotation, SMOutputElement annotationElement){
		try {
			SMNamespace namespace = annotationElement.getNamespace("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "rdf");
			SMOutputElement rdfElement = annotationElement.addElement(namespace, "RDF");
			rdfElement.addCharacters(" \n");
			
			HashMap<String, String> rdfNamespaces = annotation.getRdfAnnotationNamespaces();
			Iterator<Entry<String, String>> it = rdfNamespaces.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, String> entry = it.next();
				
				if (entry.getKey().contains(":")){
					String [] key = entry.getKey().split(":");
					SMNamespace nam = rdfElement.getNamespace(entry.getValue(), key[1]);
				}
				else {
					SMNamespace nam = rdfElement.getNamespace(entry.getValue(), "");
				}
			}
			
			SMOutputElement descriptionElement = rdfElement.addElement(namespace, "Description");
			descriptionElement.addAttribute(namespace, "about", annotation.getAbout());
			descriptionElement.addCharacters(" \n");
			
			if (annotation.isSetModelHistory()){
				writeModelHistory(annotation.getModelHistory(), rdfElement, descriptionElement);
			}
			if (annotation.getListOfCVTerms().size() > 0){
				writeCVTerms(annotation.getListOfCVTerms(), rdfElement, descriptionElement);
			}
			descriptionElement.addCharacters("\n");
			rdfElement.addCharacters(" \n");
			annotationElement.addCharacters(" \n");
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void writeAnnotation(SBase sbase, SMOutputElement element, XMLStreamWriter2 writer, String sbmlNamespace){
		SMNamespace namespace = element.getNamespace(sbmlNamespace);
		namespace.setPreferredPrefix("");
		Annotation annotation = sbase.getAnnotation();
		SMOutputElement annotationElement;
		try {
			element.addCharacters(" \n");
			annotationElement = element.addElement(namespace, "annotation");

			if (annotation.getAnnotation() != null){
				StringBuffer annotationBeginning = new StringBuffer("<annotation");
				
				HashMap<String, String> otherNamespaces = annotation.getAnnotationNamespaces();
				Iterator<Entry<String, String>> it = otherNamespaces.entrySet().iterator();
				while(it.hasNext()){
					Entry<String, String> entry = it.next();
					annotationBeginning.append(" "+entry.getKey() + "=\"" + entry.getValue() + "\"");
					if (entry.getKey().contains(":")){
						String [] key = entry.getKey().split(":");
						annotationElement.getNamespace(key[1], entry.getValue());
					}
					else {
						annotationElement.getNamespace("", entry.getValue());
					}
				}
				annotationBeginning.append("> \n").append(annotation.getAnnotation()).append("</annotation> \n");
				
				DOMConverter converter = new DOMConverter();
				String annotationString = annotationBeginning.toString().replaceAll("&", "&amp;");
				Document domDocument = JAXPFacade.getInstance().create(new BufferedReader(new StringReader(annotationString)), true);
				converter.writeFragment(domDocument.getFirstChild().getChildNodes(), writer);
			}
			
			if (annotation.isSetModelHistory() || annotation.getListOfCVTerms().size() > 0){
				writeRDFAnnotation(annotation, annotationElement);
			}
			SBMLObjectForXML xmlObject = new SBMLObjectForXML();
			writeSBMLElements(xmlObject, annotationElement, writer, annotation, null, null);
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void writeSBMLElements(SBMLObjectForXML xmlObject, SMOutputElement parentElement, XMLStreamWriter2 streamWriter, Object objectToWrite, SBMLParser notesParser, SBMLParser MathMLParser){
		ArrayList<SBMLParser> listOfPackages = getInitializedParsers(objectToWrite, parentElement.getNamespace().getURI());

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

					xmlObject.clear();
					parser.writeElement(xmlObject, nextObjectToWrite);

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
								newOutPutElement = parentElement.addElement(parentElement.getNamespace(), xmlObject.getName());
							}
							
							Iterator<Entry<String, String>> it = xmlObject.getAttributes().entrySet().iterator();
							while (it.hasNext()){
								Entry<String, String> entry = it.next();
								newOutPutElement.addAttribute(entry.getKey(), entry.getValue());
							}
							if (nextObjectToWrite instanceof SBase){
								SBase s = (SBase) nextObjectToWrite;
								if (s.isSetNotes() && notesParser != null){
									writeNotes(s, newOutPutElement, streamWriter, notesParser, newOutPutElement.getNamespace().getURI());
								}
								if (s.isSetAnnotation()){
									writeAnnotation(s, newOutPutElement, streamWriter, newOutPutElement.getNamespace().getURI());
								}
							}
							
							if (nextObjectToWrite instanceof MathContainer && MathMLParser != null){
								MathContainer mathContainer = (MathContainer) nextObjectToWrite;
								if (!mathContainer.isSetMath() && mathContainer.isSetMathBuffer()){
									writeMathML(mathContainer, newOutPutElement, streamWriter, MathMLParser);
								}
							}
							newOutPutElement.addCharacters(" \n");
							writeSBMLElements(xmlObject, newOutPutElement, streamWriter, nextObjectToWrite, notesParser, MathMLParser);
							parentElement.addCharacters(" \n");
						} catch (XMLStreamException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	private static ArrayList<SBMLParser> getInitializedParsers(Object object, String SBMLNamespace){
		Collection<String> packageNamespaces = null;

		if (object instanceof SBase){
			SBase sbase = (SBase) object;
			packageNamespaces = sbase.getNamespaces();
		}
		else if (object instanceof Annotation){
			Annotation annotation = (Annotation) object;
			packageNamespaces = annotation.getNamespaces();
		}
		ArrayList<SBMLParser> SBMLParsers = new ArrayList<SBMLParser>();
		
		if (packageNamespaces != null){
			if (!packageNamespaces.contains(SBMLNamespace)){
				try {
					if (SBMLWriter.instantiatedSBMLParsers.containsKey(SBMLNamespace)){
						SBMLParser sbmlParser = SBMLWriter.instantiatedSBMLParsers.get(SBMLNamespace);
						SBMLParsers.add(sbmlParser);
					}
					else {
						SBMLParser sbmlParser = SBMLReader.getPackageParsers(SBMLNamespace).newInstance();
						SBMLWriter.instantiatedSBMLParsers.put(SBMLNamespace, sbmlParser);
						SBMLParsers.add(sbmlParser);
					}
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
					if (SBMLWriter.instantiatedSBMLParsers.containsKey(namespace)){
						SBMLParser parser = SBMLWriter.instantiatedSBMLParsers.get(namespace);
						SBMLParsers.add(parser);
					}
					else {
						SBMLParser parser = SBMLReader.getPackageParsers(namespace).newInstance();
						SBMLWriter.instantiatedSBMLParsers.put(SBMLNamespace, parser);
						SBMLParsers.add(parser);
					}
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			outputDocument.setIndentation("\n ", 1, 4);

			String SBMLNamespace = getNamespaceFrom(sbmlDocument.getLevel(), sbmlDocument.getVersion());
			SMOutputContext context = outputDocument.getContext();
			SMNamespace namespace = context.getNamespace(SBMLNamespace);
			namespace.setPreferredPrefix("");
			SMOutputElement sbmlElement = outputDocument.addElement(namespace, "sbml");
			
			ArrayList<SBMLParser> SBMLParsers = getInitializedParsers(sbmlDocument, SBMLNamespace);
			SBMLObjectForXML xmlObject = new SBMLObjectForXML();
			xmlObject.setName("sbml");
			xmlObject.setNamespace(SBMLNamespace);
			
			Iterator<Entry<String, String>> it = xmlObject.getAttributes().entrySet().iterator();
			while (it.hasNext()){
				Entry<String, String> entry = it.next();
				sbmlElement.addAttribute(entry.getKey(), entry.getValue());
			}
			SBMLParser notesParser = SBMLReader.getPackageParsers("http://www.w3.org/1999/xhtml").newInstance();
			SBMLParser MathMLParser = SBMLReader.getPackageParsers("http://www.w3.org/1998/Math/MathML").newInstance();
			if (sbmlDocument.isSetNotes()){
				writeNotes(sbmlDocument, sbmlElement, streamWriter, notesParser, SBMLNamespace);
			}
			if (sbmlDocument.isSetAnnotation()){
				writeAnnotation(sbmlDocument, sbmlElement, streamWriter, SBMLNamespace);
			}
			
			writeSBMLElements(xmlObject, sbmlElement, streamWriter, sbmlDocument, notesParser, MathMLParser);
			
			outputDocument.closeRoot();
			
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){		
		//SBMLDocument testDocument = readSBMLFile("/home/compneur/Desktop/LibSBML-Project/MultiExamples/glutamateReceptor.xml");
		SBMLDocument testDocument = SBMLReader.readSBMLFile("/home/compneur/Desktop/LibSBML-Project/BIOMD0000000002.xml");		
		//System.out.println(testDocument.getModel().getNotesString());
		write(testDocument);
	}


}

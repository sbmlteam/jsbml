package org.sbml.jsbml.test;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndDocument;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.log4j.Logger;
import org.codehaus.stax2.evt.XMLEvent2;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.util.TreeNodeChangeListener;

import com.ctc.wstx.stax.WstxInputFactory;


public class PerformanceTestPureStax {

	private static Logger logger = Logger.getLogger(PerformanceTestPureStax.class);
	
	@SuppressWarnings("unchecked")
	private static StringBuilder readXMLFromXMLEventReader(XMLEventReader xmlEventReader, TreeNodeChangeListener listener)  throws XMLStreamException 
	{
		XMLEvent event;
		StartElement startElement = null;
		QName currentNode = null;
		StringBuilder stringWriter = new StringBuilder();
		
		// Read all the elements of the file
		while (xmlEventReader.hasNext()) {
			event = (XMLEvent2) xmlEventReader.nextEvent();

			// StartDocument
			if (event.isStartDocument()) 
			{
				@SuppressWarnings("unused")
				StartDocument startDocument = (StartDocument) event;
				logger.info("Start document");
				// nothing to do
			}
			// EndDocument
			else if (event.isEndDocument()) 
			{
				@SuppressWarnings("unused")
				EndDocument endDocument = (EndDocument) event;
				logger.info("End document");
				// nothing to do?
			}
			// StartElement
			else if (event.isStartElement()) 
			{
				
				startElement = event.asStartElement();
				currentNode = startElement.getName();

				stringWriter.append(currentNode.getLocalPart()).append("\t[");
				
				for (Iterator<Attribute> iterator = startElement.getAttributes(); iterator.hasNext();) 
				{
					Attribute attr = iterator.next();
					stringWriter.append(attr.getName().getLocalPart()).append(" = ").append(attr.getValue()).append(", ");
				}

				if (startElement.getAttributes().hasNext())
				{
					stringWriter.delete(stringWriter.lastIndexOf(","), stringWriter.length());
				}
				stringWriter.append("]\n");
				
			}
			// Characters
			else if (event.isCharacters()) 
			{
				Characters characters = event.asCharacters();
				String text = characters.getData();
				
				if (text.trim().length() > 0)
				{
					stringWriter.append(text).append("\n");
				}
			}
			// EndElement
			else if (event.isEndElement()) {
				// nothing to do
			}
		}
		
		return stringWriter;
	}
	
	
	/**
	 * Test class used to check the jsbml speed to read and write SBML models.
	 * 
	 * @param args
	 * @throws SBMLException
	 */
	public static void main(String[] args) throws SBMLException {

		// Making sure that we use the good XML library
		System.setProperty("javax.xml.stream.XMLOutputFactory", "com.ctc.wstx.stax.WstxOutputFactory");
		System.setProperty("javax.xml.stream.XMLInputFactory", "com.ctc.wstx.stax.WstxInputFactory");
		System.setProperty("javax.xml.stream.XMLEventFactory", "com.ctc.wstx.stax.WstxEventFactory");

		if (args.length < 1) {
			System.out.println("Usage: java org.sbml.jsbml.test.PerformanceTest sbmlFileName|folder");
			System.exit(0);
		}
		
		// this JOptionPane is added here to be able to start visualVM profiling
		// before the reading or writing is started.
		// JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.");

		File argsAsFile = new File(args[0]);
		File[] files = null;

		if (argsAsFile.isDirectory())
		{
			files = argsAsFile.listFiles(new FileFilter() {

				@Override
				public boolean accept(File pathname) 
				{
					if (pathname.getName().contains("-jsbml.xml"))
					{
						return false;
					}

					if (pathname.getName().endsWith(".xml"))
					{
						return true;
					}

					return false;
				}
			});
		}
		else
		{
			files = new File[1];
			files[0] = argsAsFile;
		}

		double globalInit = Calendar.getInstance().getTimeInMillis();
		double globalEnd = 0;
		WstxInputFactory inputFactory = new WstxInputFactory();

		for (File file : files) 
		{

			double init = Calendar.getInstance().getTimeInMillis();
			System.out.println(Calendar.getInstance().getTime());

			String fileName = file.getAbsolutePath();
			String jsbmlWriteFileName = fileName.replaceFirst(".xml", "-pureStax.txt");

			System.out.printf("Reading %s and writing %s\n", 
					fileName, jsbmlWriteFileName);

			long afterRead = 0;
			try {
				XMLEventReader xmlEventReader = inputFactory.createXMLEventReader(new FileInputStream(file));
				StringBuilder stringBuilder = readXMLFromXMLEventReader(xmlEventReader, null);

				System.out.printf("Reading done\n");
				System.out.println(Calendar.getInstance().getTime());
				afterRead = Calendar.getInstance().getTimeInMillis();

				System.out.printf("Starting writing\n");

				PrintStream out = new PrintStream(new File(jsbmlWriteFileName));
				out.print(stringBuilder.toString());
				out.flush();out.close();
				
			} 
			catch (XMLStreamException e) 
			{
				e.printStackTrace();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}

			System.out.println(Calendar.getInstance().getTime());
			double end = Calendar.getInstance().getTimeInMillis();
			globalEnd = end;
			double nbMilliseconds = end - init;
			double nbSeconds = nbMilliseconds / 1000;
			double nbSecondsRead = (afterRead - init)/1000;
			double nbSecondsWrite = (end - afterRead)/1000;

			if (nbSeconds > 120) {
				System.out.println("It took " + nbSeconds/60 + " minutes.");
			} else {
				System.out.println("It took " + nbSeconds + " seconds.");			
			}
			System.out.println("Reading: " + nbSecondsRead + " seconds.");
			System.out.println("Writing: " + nbSecondsWrite + " seconds.");
			
			if (files.length == 1)
			{
				System.out.println((int)nbMilliseconds);
			}
		}

		if (files.length > 1)
		{
			double globalNbMilliseconds = globalEnd - globalInit;
			double globalSeconds = globalNbMilliseconds / 1000;

			System.out.println("Reading and writing " + files.length + " models took : " + globalSeconds + " seconds.");
			System.out.println("Mean per model = " + globalSeconds / files.length + " seconds (" + globalNbMilliseconds / files.length + " ms).");
			
			System.out.println((int)globalNbMilliseconds);
		}
	}

}

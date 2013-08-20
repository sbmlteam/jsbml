/**
 * 
 */
package org.sbml.jsbml.test;

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.util.SimpleTreeNodeChangeListener;

/**
 * @author draeger
 *
 */
public class T1 {

	/**
	 * @param args
	 * @throws XMLStreamException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws XMLStreamException, IOException {
		System.setProperty("log4j.configuration", System.getProperty("user.dir") + "/log4j.properties");
		SBMLWriter.write(SBMLReader.read(new File(args[0]),  new SimpleTreeNodeChangeListener()), System.out, ' ', (short) 2);
	}

}

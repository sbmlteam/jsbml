package org.sbml.jsbml.test;

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;

/**
 * 
 * @author 
 * @since 0.8
 * @version $Rev$
 */
public class ValidateSBML {

	public static void main (String[] args) throws XMLStreamException, IOException
	{
		if (args.length < 1)
		{
			System.out.println("Usage: java validateSBML filename");
			System.exit(1);
		}

		String filename       = args[0];
		SBMLReader reader     = new SBMLReader();
		SBMLDocument document;
		long start, stop;

		start    = System.currentTimeMillis();
		document = reader.readSBML(filename);
		stop     = System.currentTimeMillis();

		if (document.getNumErrors() > 0)
		{
			print("Encountered the following errors while reading the SBML file:\n");
			document.printErrors();
			print("\nFurther consistency checking and validation aborted.\n");
			System.exit(1);
		}
		else
		{
			long errors = document.checkConsistency();
			long size   = new File(filename).length();

			println("            filename: " + filename);
			println("           file size: " + size);
			println("      read time (ms): " + (stop - start));
			println(" validation error(s): " + errors);

			if (errors > 0)
			{
				document.printErrors();
				System.exit(1);
			}
		}
	}


	static void print (String msg)
	{
		System.out.print(msg);
	}

	static void println (String msg)
	{
		System.out.println(msg);
	}

}

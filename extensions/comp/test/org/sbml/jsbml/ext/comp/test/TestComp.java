/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml.ext.comp.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.ext.comp.CompConstants;
import org.sbml.jsbml.ext.comp.CompModelPlugin;
import org.sbml.jsbml.xml.stax.SBMLReader;
import org.sbml.jsbml.xml.stax.SBMLWriter;

public class TestComp {

	
	/**
	 * Tests this class
	 * 
	 * @param args
	 * @throws SBMLException
	 */
	public static void main(String[] args) throws SBMLException {

		if (args.length < 1) {
			System.out.println(
			  "Usage: java org.sbml.jsbml.xml.stax.SBMLWriter sbmlFileName");
			System.exit(0);
		}

		// this JOptionPane is added here to be able to start visualVM profiling
		// before the reading or writing is started.
		// JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.");
		
		long init = Calendar.getInstance().getTimeInMillis();
		System.out.println(Calendar.getInstance().getTime());
		
		String fileName = args[0];
		String jsbmlWriteFileName = fileName.replaceFirst(".xml", "-jsbml.xml");
		
		System.out.printf("Reading %s and writing %s\n", 
		  fileName, jsbmlWriteFileName);

		SBMLDocument testDocument;
		long afterRead = 0;
		try {
			testDocument = new SBMLReader().readSBMLFile(fileName);
			System.out.printf("Reading done\n");
			System.out.println(Calendar.getInstance().getTime());
			afterRead = Calendar.getInstance().getTimeInMillis();

			
			CompModelPlugin cmodel = (CompModelPlugin) testDocument.getModel().getExtension(CompConstants.namespaceURI);
			
			System.out.println(cmodel.getListOfPorts().size());
			
			// testDocument.checkConsistency(); 
			
//			Compartment c = testDocument.getModel().getCompartment("compartment");
//
//			System.out.println("compartment nb child = " + c.getChildCount());
//			System.out.println("compartment child nb child = " + c.getChildAt(0).getChildCount());
//			System.out.println(((List) c.getChildAt(0)).get(1));
			

			System.out.printf("Starting writing\n");
			
			new SBMLWriter().write(testDocument.clone(), jsbmlWriteFileName);
		} 
		catch (XMLStreamException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		System.out.println(Calendar.getInstance().getTime());
		long end = Calendar.getInstance().getTimeInMillis();
		long nbSecondes = (end - init)/1000;
		long nbSecondesRead = (afterRead - init)/1000;
		long nbSecondesWrite = (end - afterRead)/1000;
		
		if (nbSecondes > 120) {
			System.out.println("It took " + nbSecondes/60 + " minutes.");
		} else {
			System.out.println("It took " + nbSecondes + " secondes.");			
		}
		System.out.println("Reading: " + nbSecondesRead + " secondes.");
		System.out.println("Writing: " + nbSecondesWrite + " secondes.");
	}
}

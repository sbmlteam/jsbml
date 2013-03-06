/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.test;

import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.CVTerm.Qualifier;
import org.sbml.jsbml.CVTerm.Type;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 0.8
 * @date 27.10.2011
 */
public class TestAnnotation {

	public static void main(String[] args) {
		
		// TODO : transform this file into a proper junit test class that is included in the jsbml standard tests.
		// The junit test file would test the Annotation class methods.  
		  
		// Setup : creating some simple objects 
	    SBMLDocument doc = new SBMLDocument(2,4);

	    Model m = doc.createModel("model1");
	    Model m2 = new Model("model1", 2, 4);
	    
	    Species s1 = m.createSpecies("id1");
	    
	    Annotation annotation = new Annotation();
	    CVTerm cvterm = new CVTerm();
	    cvterm.addResource("urn.miriam.obo.go#GO%3A1234567");
	    cvterm.setQualifierType(Type.BIOLOGICAL_QUALIFIER);
	    cvterm.setBiologicalQualifierType(Qualifier.BQB_IS);
	    
	    annotation.addCVTerm(cvterm);
	    
	    System.out.println("The Annotation is still empty as there is no metaid defined on the species !!");
	    System.out.println("@" + s1.getAnnotationString() + "@");
	    
	    s1.setMetaId("meta4");
	    s1.setAnnotation(annotation);
	    
	    System.out.println("After adding a metaid on the species, the Annotation String get generated as it should.");
	    System.out.println("@" + s1.getAnnotationString() + "@");
	    


	    String urn_id = "C00001";
	    
	    Species specie = m2.createSpecies();
	    specie.setId("S2");
	    specie.setName("S2");
	    // specie.setCompartment(compartment);
	    specie.setInitialAmount(1);

	    annotation = new Annotation();
	    CVTerm cvTerm = new CVTerm(Qualifier.BQB_IS);
	    cvTerm.setQualifierType(Type.BIOLOGICAL_QUALIFIER);
	    
	    if (urn_id.startsWith("C"))
	    {
	    cvTerm.addResource("urn:miriam:kegg.compound:" + urn_id);
	    annotation.addCVTerm(cvTerm);
	    annotation.appendNoRDFAnnotation("http://www.genome.jp/dbget-bin/www_bget?cpd:"+urn_id);
	    }
	    
	    if (urn_id.startsWith("G"))
	    {
	    cvTerm.addResource("urn:miriam:kegg.glycan:" + urn_id);
	    annotation.addCVTerm(cvTerm);
	    annotation.appendNoRDFAnnotation("<myApp:xxx>http://www.genome.jp/dbget-bin/www_bget?gl:"+urn_id + "</myApp:xxx>");
	    }
	    
	    if (urn_id.contains("."))
	    {
	    cvTerm.addResource("urn:miriam:ec-code:" + urn_id);
	    annotation.addCVTerm(cvTerm);
	    annotation.appendNoRDFAnnotation("http://www.genome.jp/dbget-bin/www_bget?ec:"+urn_id);
	    }

	    System.out.println("The Annotation is still empty as there is no metaid defined on the species !!");
	    System.out.println("@" + specie.getAnnotationString() + "@");

	    specie.setMetaId("S1");
	    specie.setAnnotation(annotation);


	    System.out.println("After adding a metaid on the species, the Annotation String get generated as it should.");
	    System.out.println("@" + specie.getAnnotationString() + "@");

	    // this.sbmlModel.addSpecies(specie);

	}
}

package org.sbml.jsbml.test;

import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.CVTerm.Qualifier;
import org.sbml.jsbml.CVTerm.Type;


public class TestAnnotation {

	public static void main(String[] args) {
		
		// TODO : transform this file into a proper junit test class that is included in the jsbml standard tests.
		// The junit test file would test the Annotation class methods.  
		  
		// Setup : creating some simple objects 
	    SBMLDocument doc=new SBMLDocument(2,4);
	    Model m=doc.createModel("model1");

	    
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
	    

	}
}

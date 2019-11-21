package org.sbml.jsbml.ext.comp.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.ext.comp.CompConstants;
import org.sbml.jsbml.ext.comp.CompModelPlugin;
import org.sbml.jsbml.ext.comp.CompSBMLDocumentPlugin;
import org.sbml.jsbml.ext.comp.ExternalModelDefinition;
import org.sbml.jsbml.ext.comp.ModelDefinition;

public class TestExternalModelDefinition {

	@Test
	public void testGetReferencedModel() throws URISyntaxException, XMLStreamException, IOException {
		ClassLoader cl = this.getClass().getClassLoader();
		URL urlExpectation = cl.getResource("testGathering/spec_example1.xml");
		URL urlFile = cl.getResource("testGathering/spec_example2.xml");
		assert urlExpectation != null;
		assert urlFile != null;
		
		File expectation = new File(urlExpectation.toURI());
		File file = new File(urlFile.toURI());
		
		assert expectation != null;
		assert file != null;
		
		String absolutePath = file.getPath();
		absolutePath = absolutePath.substring(0, absolutePath.length() - "spec_example2.xml".length());
		
		SBMLReader reader = new SBMLReader();
		SBMLDocument document = reader.readSBML(file);
		ModelDefinition expectedModelDefinition = ((CompSBMLDocumentPlugin) reader.readSBML(expectation).getExtension(CompConstants.shortLabel)).getModelDefinition("linked");
		
		CompSBMLDocumentPlugin compSBMLDocPlugin = (CompSBMLDocumentPlugin) document.getExtension(CompConstants.shortLabel);
		ExternalModelDefinition externalModel = compSBMLDocPlugin.getExternalModelDefinition("ExtMod1");
		Model referenced = externalModel.getReferencedModel(absolutePath);
		
		assertEquals(expectedModelDefinition.getSpeciesCount(), referenced.getSpeciesCount());
		fail("Not yet sufficiently implemented");
	}

}

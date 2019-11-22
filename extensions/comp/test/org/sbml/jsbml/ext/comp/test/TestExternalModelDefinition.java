package org.sbml.jsbml.ext.comp.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.comp.CompConstants;
import org.sbml.jsbml.ext.comp.CompModelPlugin;
import org.sbml.jsbml.ext.comp.CompSBMLDocumentPlugin;
import org.sbml.jsbml.ext.comp.ExternalModelDefinition;
import org.sbml.jsbml.ext.comp.ModelDefinition;

public class TestExternalModelDefinition {

	private void assertModelsEqual(Model expected, Model observed) {
		assertEquals(expected, observed);
		assertEquals(expected.getSpeciesCount(), observed.getSpeciesCount());
		
		for(int i = 0; i < expected.getSpeciesCount(); i++) {
			assertEquals(expected.getSpecies(i), observed.getSpecies(i));
		}
		
		for(int i = 0; i < expected.getReactionCount(); i++) {
			assertEquals(expected.getReaction(i), observed.getReaction(i));
		}
	}
	
	/**
	 * Checks behaviour when the model references an external model by a relative Path, and the referenced file 
	 * contains the definition (but not as the main model).
	 * @throws URISyntaxException
	 * @throws XMLStreamException
	 * @throws IOException
	 */
	@Test
	public void testGetReferencedModel_relativPath() throws URISyntaxException, XMLStreamException, IOException {
		ClassLoader cl = this.getClass().getClassLoader();
		URL urlExpectation = cl.getResource("testGathering/spec_example1.xml");
		// 'spec_example2' references ...1 by relative path 
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
		
		assertModelsEqual(expectedModelDefinition, referenced);		
	}
	
	/**
	 * Checks behaviour when the model references an external model by an absolute Path, and the referenced file 
	 * contains the definition (but not as the main model).
	 * @throws URISyntaxException
	 * @throws XMLStreamException
	 * @throws IOException
	 */
	@Test
	public void testGetReferencedModel_absolutePath() throws URISyntaxException, XMLStreamException, IOException {
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

		// Set the source to an absolute Path
		externalModel.setSource(absolutePath + "spec_example1.xml");
		
		Model referenced = externalModel.getReferencedModel(absolutePath);
		
		assertModelsEqual(expectedModelDefinition, referenced);
	}
	
	
	/**
	 * Checks behaviour when the model references an external model by a URL, and the referenced file 
	 * contains the definition (but not as the main model).
	 * @throws URISyntaxException
	 * @throws XMLStreamException
	 * @throws IOException
	 */
	@Test
	public void testGetReferencedModel_URL() throws URISyntaxException, XMLStreamException, IOException {
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

		// Set the source to a URL
		externalModel.setSource(urlExpectation.toString());
		
		Model referenced = externalModel.getReferencedModel(absolutePath);
		
		assertModelsEqual(expectedModelDefinition, referenced);
	}
	
	/**
	 * Checks behaviour when the model references an external model, which is the main model
	 * of the referenced file.
	 * @throws URISyntaxException
	 * @throws XMLStreamException
	 * @throws IOException
	 */
	@Test
	public void testGetReferencedModel_mainModel() throws URISyntaxException, XMLStreamException, IOException {
		ClassLoader cl = this.getClass().getClassLoader();
		URL urlExpectation = cl.getResource("testGathering/main_model_to_be_referenced.xml");
		URL urlFile = cl.getResource("testGathering/references_main_model.xml");
	
		assert urlExpectation != null;
		assert urlFile != null;
		
		File expectation = new File(urlExpectation.toURI());
		File file = new File(urlFile.toURI());
		
		assert expectation != null;
		assert file != null;
		
		String absolutePath = file.getPath();
		absolutePath = absolutePath.substring(0, absolutePath.length() - "references_main_model.xml".length());
		
		SBMLReader reader = new SBMLReader();
		SBMLDocument document = reader.readSBML(file);
		Model expectedModel = reader.readSBML(expectation).getModel();
		
		CompSBMLDocumentPlugin compSBMLDocPlugin = (CompSBMLDocumentPlugin) document.getExtension(CompConstants.shortLabel);
		ExternalModelDefinition externalModel = compSBMLDocPlugin.getExternalModelDefinition("ExtMod1");

		Model referenced = externalModel.getReferencedModel(absolutePath);
		assertModelsEqual(expectedModel, referenced);
	
		// Set the source to an absolute Path
		externalModel.setSource(absolutePath + "main_model_to_be_referenced.xml");
		referenced = externalModel.getReferencedModel(absolutePath);
		assertModelsEqual(expectedModel, referenced);
		
		// Set the source to a URL
		externalModel.setSource(urlExpectation.toString());
		referenced = externalModel.getReferencedModel(absolutePath);
		assertModelsEqual(expectedModel, referenced);
	}
	
	
	/**
	 * Checks behaviour when the model references an external model, which references an external model and so on down
	 * to a model with the referenced ModelDefinition
	 * @throws URISyntaxException
	 * @throws XMLStreamException
	 * @throws IOException
	 */
	@Test
	public void testGetReferencedModel_chainReference() throws URISyntaxException, XMLStreamException, IOException {
		ClassLoader cl = this.getClass().getClassLoader();
		URL urlExpectation = cl.getResource("testGathering/spec_example1.xml");
		URL urlFile = cl.getResource("testGathering/chain_reference_head.xml");
	
		assert urlExpectation != null;
		assert urlFile != null;
		
		File expectation = new File(urlExpectation.toURI());
		File file = new File(urlFile.toURI());
		
		assert expectation != null;
		assert file != null;
		
		String absolutePath = file.getPath();
		absolutePath = absolutePath.substring(0, absolutePath.length() - "chain_reference_head.xml".length());
		
		SBMLReader reader = new SBMLReader();
		SBMLDocument document = reader.readSBML(file);
		ModelDefinition expectedModelDefinition = ((CompSBMLDocumentPlugin) reader.readSBML(expectation).getExtension(CompConstants.shortLabel)).getModelDefinition("linked");
		
		CompSBMLDocumentPlugin compSBMLDocPlugin = (CompSBMLDocumentPlugin) document.getExtension(CompConstants.shortLabel);
		ExternalModelDefinition externalModel = compSBMLDocPlugin.getExternalModelDefinition("ExtMod1");

		Model referenced = externalModel.getReferencedModel(absolutePath);
		assertModelsEqual(expectedModelDefinition, referenced);
	
		// Set the source to an absolute Path
		externalModel.setSource(absolutePath + "chain_reference_intermediate1.xml");
		referenced = externalModel.getReferencedModel(absolutePath);
		assertModelsEqual(expectedModelDefinition, referenced);
		
		// Set the source to a URL
		externalModel.setSource(cl.getResource("testGathering/chain_reference_intermediate1.xml").toString());
		referenced = externalModel.getReferencedModel(absolutePath);
		assertModelsEqual(expectedModelDefinition, referenced);
	}
	
	/**
	 * Checks behaviour when the model references an external model in a different directory
	 * 
	 * @throws URISyntaxException
	 * @throws XMLStreamException
	 * @throws IOException
	 */
	@Test
	public void testGetReferencedModel_differentDirectory() throws URISyntaxException, XMLStreamException, IOException {
		ClassLoader cl = this.getClass().getClassLoader();
		URL urlExpectation = cl.getResource("testGathering/somewhere_else/hidden_spec_example1.xml");
	  URL urlFile = cl.getResource("testGathering/chain_reference_different_directory.xml");
	
		assert urlExpectation != null;
		assert urlFile != null;
		
		File expectation = new File(urlExpectation.toURI());
		File file = new File(urlFile.toURI());
		
		assert expectation != null;
		assert file != null;
		
		String absolutePath = file.getPath();
		absolutePath = absolutePath.substring(0, absolutePath.length() - "chain_reference_different_directory.xml".length());
		
		SBMLReader reader = new SBMLReader();
		SBMLDocument document = reader.readSBML(file);
		ModelDefinition expectedModelDefinition = ((CompSBMLDocumentPlugin) reader.readSBML(expectation).getExtension(CompConstants.shortLabel)).getModelDefinition("linked");
		
		CompSBMLDocumentPlugin compSBMLDocPlugin = (CompSBMLDocumentPlugin) document.getExtension(CompConstants.shortLabel);
		ExternalModelDefinition externalModel = compSBMLDocPlugin.getExternalModelDefinition("ExtMod1");

		Model referenced = externalModel.getReferencedModel(absolutePath);
		assertModelsEqual(expectedModelDefinition, referenced);
	
		// Set the source to an absolute Path
		externalModel.setSource(absolutePath + "somewhere_else/chain_reference_intermediate.xml");
		referenced = externalModel.getReferencedModel(absolutePath);
		assertModelsEqual(expectedModelDefinition, referenced);
		
		// Set the source to a URL
		externalModel.setSource(cl.getResource("testGathering/somewhere_else/chain_reference_intermediate.xml").toString());
		referenced = externalModel.getReferencedModel(absolutePath);
		assertModelsEqual(expectedModelDefinition, referenced);
	}
}

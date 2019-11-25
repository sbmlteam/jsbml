package org.sbml.jsbml.ext.comp.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
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
	
	private ClassLoader cl = this.getClass().getClassLoader();
	private Model expectedModel;
	private URL urlExpectation;
	private ExternalModelDefinition externalModel;
	private String absolutePath;
	
	private void setUpExpectation(String pathFromClassPath) throws URISyntaxException, XMLStreamException, IOException {
		urlExpectation = cl.getResource(pathFromClassPath);
		assert urlExpectation != null;
		File expectation = new File(urlExpectation.toURI());
		assert expectation != null;
		expectedModel = ((CompSBMLDocumentPlugin) SBMLReader.read(expectation).getExtension(CompConstants.shortLabel)).getModelDefinition("linked");
	}
	
	/**
	 * 
	 * @param pathFromClassPath ending in / or \
	 * @param fileName
	 * @param modelId the externalModelDefinition's comp:id-attribute
	 * @throws URISyntaxException
	 * @throws XMLStreamException
	 * @throws IOException
	 */
	private void setUpExternalModelDefinition(String pathFromClassPath, String fileName, String modelId) throws URISyntaxException, XMLStreamException, IOException {
		URL urlFile = cl.getResource(pathFromClassPath + fileName);
		assert urlFile != null;
		
		File file = new File(urlFile.toURI());
		assert file != null;
		
		absolutePath = urlFile.toURI().toString();
		absolutePath = absolutePath.substring(0, absolutePath.length() - fileName.length());
		
		SBMLDocument document = SBMLReader.read(file);
		
		CompSBMLDocumentPlugin compSBMLDocPlugin = (CompSBMLDocumentPlugin) document.getExtension(CompConstants.shortLabel);
		externalModel = compSBMLDocPlugin.getExternalModelDefinition(modelId);
	}
	
	/**
	 * Checks behaviour when the model references an external model by a relative Path, and the referenced file 
	 * contains the definition (but not as the main model).
	 * @throws URISyntaxException
	 * @throws XMLStreamException
	 * @throws IOException
	 */
	@Test
	public void testGetReferencedModel_relativePath() throws URISyntaxException, XMLStreamException, IOException {
		setUpExpectation("testGathering/spec_example1.xml");
		// 'spec_example2' references ...1 by relative path 
		setUpExternalModelDefinition("testGathering/", "spec_example2.xml", "ExtMod1");
		Model referenced = externalModel.getReferencedModel(new URI(absolutePath));
		
		assertModelsEqual(expectedModel, referenced);		
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
		setUpExpectation("testGathering/spec_example1.xml");
		setUpExternalModelDefinition("testGathering/", "spec_example2.xml", "ExtMod1");

		// Set the source to an absolute Path
		externalModel.setSource(absolutePath.substring(6) + "spec_example1.xml");
		
		Model referenced = externalModel.getReferencedModel(new URI(absolutePath));
		
		assertModelsEqual(expectedModel, referenced);
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
		setUpExpectation("testGathering/spec_example1.xml");
		setUpExternalModelDefinition("testGathering/", "spec_example2.xml", "ExtMod1");
		
		// Set the source to a URL
		externalModel.setSource(urlExpectation.toString());
		
		Model referenced = externalModel.getReferencedModel(new URI(absolutePath));
		
		assertModelsEqual(expectedModel, referenced);
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
		setUpExpectation("testGathering/main_model_to_be_referenced.xml");
		setUpExternalModelDefinition("testGathering/", "references_main_model.xml", "ExtMod1");
		
		File expectation = new File(urlExpectation.toURI());
		assert expectation != null;

		SBMLReader reader = new SBMLReader();
		expectedModel = reader.readSBML(expectation).getModel();
		
		Model referenced = externalModel.getReferencedModel(new URI(absolutePath));
		assertModelsEqual(expectedModel, referenced);
	
		// Set the source to an absolute Path
		externalModel.setSource(absolutePath + "main_model_to_be_referenced.xml");
		referenced = externalModel.getReferencedModel(new URI(absolutePath));
		assertModelsEqual(expectedModel, referenced);
		
		// Set the source to a URL
		externalModel.setSource(urlExpectation.toString());
		referenced = externalModel.getReferencedModel(new URI(absolutePath));
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
		setUpExpectation("testGathering/spec_example1.xml");
		setUpExternalModelDefinition("testGathering/", "chain_reference_head.xml", "ExtMod1");

		Model referenced = externalModel.getReferencedModel(new URI(absolutePath));
		assertModelsEqual(expectedModel, referenced);
	
		// Set the source to an absolute Path
		externalModel.setSource(absolutePath + "chain_reference_intermediate1.xml");
		referenced = externalModel.getReferencedModel(new URI(absolutePath));
		assertModelsEqual(expectedModel, referenced);
		
		// Set the source to a URL
		externalModel.setSource(cl.getResource("testGathering/chain_reference_intermediate1.xml").toString());
		referenced = externalModel.getReferencedModel(new URI(absolutePath));
		assertModelsEqual(expectedModel, referenced);
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
		setUpExpectation("testGathering/somewhere_else/hidden_spec_example1.xml");
	  setUpExternalModelDefinition("testGathering/", "chain_reference_different_directory.xml", "ExtMod1");

		Model referenced = externalModel.getReferencedModel(new URI(absolutePath));
		assertModelsEqual(expectedModel, referenced);
	
		// Set the source to an absolute Path
		externalModel.setSource(absolutePath + "somewhere_else/chain_reference_intermediate.xml");
		referenced = externalModel.getReferencedModel(new URI(absolutePath));
		assertModelsEqual(expectedModel, referenced);
		
		// Set the source to a URL
		externalModel.setSource(cl.getResource("testGathering/somewhere_else/chain_reference_intermediate.xml").toString());
		referenced = externalModel.getReferencedModel(new URI(absolutePath));
		assertModelsEqual(expectedModel, referenced);
	}
	
	/**
	 * WARNING! This test requires an internet connection
	 * @throws URISyntaxException
	 * @throws XMLStreamException
	 * @throws IOException
	 */
	@Test
	public void testGetReferencedModel_simpleOnline() throws URISyntaxException, XMLStreamException, IOException {
  	// The online-file is the same as this one, but in the github repo
		setUpExpectation("testGathering/spec_example1.xml"); 
	  setUpExternalModelDefinition("testGathering/", "simple_online_url.xml", "ExtMod1");
		
		Model referenced = externalModel.getReferencedModel(new URI(absolutePath));
		assertModelsEqual(expectedModel, referenced);
	}
	// TODO: test behaviour for actual online-URL combined with chain reference 
	// and online-URL combined with relative Paths
	// TODO: test behaviour for relative URI using ../..
}

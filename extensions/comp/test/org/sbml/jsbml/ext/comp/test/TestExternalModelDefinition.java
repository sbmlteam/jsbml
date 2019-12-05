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
 * 5. The Babraham Institute, Cambridge, UK
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.comp.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.ext.comp.CompConstants;
import org.sbml.jsbml.ext.comp.CompSBMLDocumentPlugin;
import org.sbml.jsbml.ext.comp.ExternalModelDefinition;

public class TestExternalModelDefinition {

  private void assertModelsEqual(Model expected, Model observed) {
    assertEquals(expected, observed);
    assertEquals(expected.getSpeciesCount(), observed.getSpeciesCount());
    for (int i = 0; i < expected.getSpeciesCount(); i++) {
      assertEquals(expected.getSpecies(i), observed.getSpecies(i));
    }
    for (int i = 0; i < expected.getReactionCount(); i++) {
      assertEquals(expected.getReaction(i), observed.getReaction(i));
    }
  }

  private ClassLoader             cl = this.getClass().getClassLoader();
  private Model                   expectedModel;
  private URL                     urlExpectation;
  private ExternalModelDefinition externalModel;
  private String                  absolutePath;

  private void setUpExpectation(String pathFromClassPath)
    throws URISyntaxException, XMLStreamException, IOException {
    urlExpectation = cl.getResource(pathFromClassPath);
    assert urlExpectation != null;
    File expectation = new File(urlExpectation.toURI());
    assert expectation != null;
    try {
      expectedModel =
        ((CompSBMLDocumentPlugin) SBMLReader.read(expectation).getExtension(
          CompConstants.shortLabel)).getModelDefinition("linked");
    } catch (NullPointerException e) {
      // No 'linked' modelDefinition --> assume that the test calling this will take care of this.
      expectedModel = null;
    }
  }


  /**
   * @param pathFromClassPath
   *        ending in / or \
   * @param fileName
   * @param modelId
   *        the externalModelDefinition's comp:id-attribute
   * @throws URISyntaxException
   * @throws XMLStreamException
   * @throws IOException
   */
  private void setUpExternalModelDefinition(String pathFromClassPath,
    String fileName, String modelId)
    throws URISyntaxException, XMLStreamException, IOException {
    URL urlFile = cl.getResource(pathFromClassPath + fileName);
    assert urlFile != null;
    File file = new File(urlFile.toURI());
    assert file != null;
    absolutePath = urlFile.toURI().toString();
    absolutePath =
      absolutePath.substring(0, absolutePath.length() - fileName.length());
    SBMLDocument document = SBMLReader.read(file);
    CompSBMLDocumentPlugin compSBMLDocPlugin =
      (CompSBMLDocumentPlugin) document.getExtension(CompConstants.shortLabel);
    externalModel = compSBMLDocPlugin.getExternalModelDefinition(modelId);
  }


  /**
   * Checks behaviour when the model references an external model by a relative
   * Path, and the referenced file contains the definition (but not as the main
   * model).
   * 
   * @throws URISyntaxException
   * @throws XMLStreamException
   * @throws IOException
   */
  @Test
  public void testGetReferencedModel_relativePath()
    throws URISyntaxException, XMLStreamException, IOException {
    setUpExpectation("testGathering/spec_example1.xml");
    // 'spec_example2' references ...1 by relative path
    setUpExternalModelDefinition("testGathering/", "spec_example2.xml",
      "ExtMod1");
    Model referenced = externalModel.getReferencedModel(new URI(absolutePath));
    assertModelsEqual(expectedModel, referenced);
  }


  /**
   * Checks behaviour when the model references an external model by an absolute
   * Path, and the referenced file contains the definition (but not as the main
   * model).
   * 
   * @throws URISyntaxException
   * @throws XMLStreamException
   * @throws IOException
   */
  @Test
  public void testGetReferencedModel_absolutePath()
    throws URISyntaxException, XMLStreamException, IOException {
    setUpExpectation("testGathering/spec_example1.xml");
    setUpExternalModelDefinition("testGathering/", "spec_example2.xml",
      "ExtMod1");
    // Set the source to an absolute Path
    externalModel.setSource(absolutePath.substring(6) + "spec_example1.xml");
    Model referenced = externalModel.getReferencedModel(new URI(absolutePath));
    assertModelsEqual(expectedModel, referenced);
  }


  /**
   * Checks behaviour when the model references an external model by a URL, and
   * the referenced file contains the definition (but not as the main model).
   * 
   * @throws URISyntaxException
   * @throws XMLStreamException
   * @throws IOException
   */
  @Test
  public void testGetReferencedModel_URL()
    throws URISyntaxException, XMLStreamException, IOException {
    setUpExpectation("testGathering/spec_example1.xml");
    setUpExternalModelDefinition("testGathering/", "spec_example2.xml",
      "ExtMod1");
    // Set the source to a URL
    externalModel.setSource(urlExpectation.toString());
    Model referenced = externalModel.getReferencedModel(new URI(absolutePath));
    assertModelsEqual(expectedModel, referenced);
  }


  /**
   * Checks behaviour when the model references an external model, which is the
   * main model of the referenced file.
   * 
   * @throws URISyntaxException
   * @throws XMLStreamException
   * @throws IOException
   */
  @Test
  public void testGetReferencedModel_mainModel()
    throws URISyntaxException, XMLStreamException, IOException {
    setUpExpectation("testGathering/main_model_to_be_referenced.xml");
    setUpExternalModelDefinition("testGathering/", "references_main_model.xml",
      "ExtMod1");
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
   * Checks behaviour when the model references an external model, which
   * references an external model and so on down to a model with the referenced
   * ModelDefinition
   * 
   * @throws URISyntaxException
   * @throws XMLStreamException
   * @throws IOException
   */
  @Test
  public void testGetReferencedModel_chainReference()
    throws URISyntaxException, XMLStreamException, IOException {
    setUpExpectation("testGathering/spec_example1.xml");
    setUpExternalModelDefinition("testGathering/", "chain_reference_head.xml",
      "ExtMod1");
    Model referenced = externalModel.getReferencedModel(new URI(absolutePath));
    assertModelsEqual(expectedModel, referenced);
    // Set the source to an absolute Path
    externalModel.setSource(absolutePath + "chain_reference_intermediate1.xml");
    referenced = externalModel.getReferencedModel(new URI(absolutePath));
    assertModelsEqual(expectedModel, referenced);
    // Set the source to a URL
    externalModel.setSource(
      cl.getResource("testGathering/chain_reference_intermediate1.xml")
        .toString());
    referenced = externalModel.getReferencedModel(new URI(absolutePath));
    assertModelsEqual(expectedModel, referenced);
  }


  /**
   * Checks behaviour when the model references an external model in a different
   * directory
   * 
   * @throws URISyntaxException
   * @throws XMLStreamException
   * @throws IOException
   */
  @Test
  public void testGetReferencedModel_differentDirectory()
    throws URISyntaxException, XMLStreamException, IOException {
    setUpExpectation("testGathering/somewhere_else/hidden_spec_example1.xml");
    setUpExternalModelDefinition("testGathering/",
      "chain_reference_different_directory.xml", "ExtMod1");
    Model referenced = externalModel.getReferencedModel(new URI(absolutePath));
    assertModelsEqual(expectedModel, referenced);
    // Set the source to an absolute Path
    externalModel.setSource(
      absolutePath + "somewhere_else/chain_reference_intermediate.xml");
    referenced = externalModel.getReferencedModel(new URI(absolutePath));
    assertModelsEqual(expectedModel, referenced);
    // Set the source to a URL
    externalModel.setSource(cl.getResource(
      "testGathering/somewhere_else/chain_reference_intermediate.xml")
                              .toString());
    referenced = externalModel.getReferencedModel(new URI(absolutePath));
    assertModelsEqual(expectedModel, referenced);
  }


  /**
   * Tests behaviour for relative paths using .. (parent directory)
   * 
   * @throws URISyntaxException
   * @throws XMLStreamException
   * @throws IOException
   */
  @Test
  public void testGetReferencedModel_outwardRelative()
    throws URISyntaxException, XMLStreamException, IOException {
    // The online-file is the same as this one, but in the github repo
    setUpExpectation("testGathering/spec_example1.xml");
    setUpExternalModelDefinition("testGathering/somewhere_else/",
      "outward_relative_path.xml", "ExtMod1");
    Model referenced = externalModel.getReferencedModel(new URI(absolutePath));
    assertModelsEqual(expectedModel, referenced);
  }


  /**
   * WARNING! This test requires an internet connection Tests behaviour for a
   * model that references a model on the internet (which already contains the
   * desired definition)
   * 
   * @throws URISyntaxException
   * @throws XMLStreamException
   * @throws IOException
   */
  @Test
  public void testGetReferencedModel_simpleOnline()
    throws URISyntaxException, XMLStreamException, IOException {
    // The online-file is the same as this one, but in the github repo
    setUpExpectation("testGathering/spec_example1.xml");
    setUpExternalModelDefinition("testGathering/", "simple_online_url.xml",
      "ExtMod1");
    Model referenced = externalModel.getReferencedModel(new URI(absolutePath));
    assertModelsEqual(expectedModel, referenced);
  }


  /**
   * WARNING! This test requires an internet connection Tests behaviour for a
   * reference to an online model that references another model by relative path
   * 
   * @throws URISyntaxException
   * @throws XMLStreamException
   * @throws IOException
   */
  @Test
  public void testGetReferencedModel_onlineChainAndRelative()
    throws URISyntaxException, XMLStreamException, IOException {
    // The online-file is the same as this one, but in the github repo
    setUpExpectation("testGathering/somewhere_else/hidden_spec_example1.xml");
    setUpExternalModelDefinition("testGathering/",
      "online_chain_and_relative.xml", "ExtMod1");
    Model referenced = externalModel.getReferencedModel(new URI(absolutePath));
    assertModelsEqual(expectedModel, referenced);
  }


  /**
   * WARNING! This test requires an internet connection Tests behaviour for a
   * reference (https) to an online model that itself references an online
   * model.
   * 
   * @throws URISyntaxException
   * @throws XMLStreamException
   * @throws IOException
   */
  @Test
  public void testGetReferencedModel_onlineChainOnline()
    throws URISyntaxException, XMLStreamException, IOException {
    // The online-file is the same as this one, but in the github repo
    setUpExpectation("testGathering/spec_example1.xml");
    setUpExternalModelDefinition("testGathering/", "online_chain_online.xml",
      "ExtMod1");
    Model referenced = externalModel.getReferencedModel(new URI(absolutePath));
    assertModelsEqual(expectedModel, referenced);
  }
  
  /**
   * Tests behaviour of the method getReferencedModel(): Check whether reading of locationURI works and 
   * is correctly used here.
   * @throws URISyntaxException
   * @throws XMLStreamException
   * @throws IOException
   */
  @Test
  public void testGetReferencedModel_noPath() throws URISyntaxException, XMLStreamException, IOException {
    setUpExpectation("testGathering/spec_example1.xml");
    // spec_example2 references spec_example1 by relative path -> SBMLDocument's locationURI is needed.
    setUpExternalModelDefinition("testGathering/", "spec_example2.xml", "ExtMod1");
    Model referenced = externalModel.getReferencedModel();
    assertModelsEqual(expectedModel, referenced);
  }
  
  /**
   * Warning, requires internet connection
   * @throws URISyntaxException
   * @throws XMLStreamException
   * @throws IOException
   */
  @Test
  public void testGetReferencedModel_noPath_online() throws URISyntaxException, XMLStreamException, IOException {
    setUpExpectation("testGathering/spec_example1.xml");
    setUpExternalModelDefinition("testGathering/", "simple_online_url.xml",
      "ExtMod1");
    Model referenced = externalModel.getReferencedModel();
    assertModelsEqual(expectedModel, referenced);
  }
  
  /**
   * Checks behaviour of getReferencedModel() when the model references an external model in a different
   * directory
   * 
   * @throws URISyntaxException
   * @throws XMLStreamException
   * @throws IOException
   */
  @Test
  public void testGetReferencedModel_noPath_differentDirectory()
    throws URISyntaxException, XMLStreamException, IOException {
    setUpExpectation("testGathering/somewhere_else/hidden_spec_example1.xml");
    setUpExternalModelDefinition("testGathering/",
      "chain_reference_different_directory.xml", "ExtMod1");
    Model referenced = externalModel.getReferencedModel();
    assertModelsEqual(expectedModel, referenced);
    // Set the source to an absolute Path
    externalModel.setSource(
      absolutePath + "somewhere_else/chain_reference_intermediate.xml");
    referenced = externalModel.getReferencedModel();
    assertModelsEqual(expectedModel, referenced);
    // Set the source to a URL
    externalModel.setSource(cl.getResource(
      "testGathering/somewhere_else/chain_reference_intermediate.xml")
                              .toString());
    referenced = externalModel.getReferencedModel();
    assertModelsEqual(expectedModel, referenced);
  }
   
  
  /**
   * Tests behaviour of {@link ExternalModelDefinition#getReferencedModel} if
   * the {@link SBMLDocument}'s locationURI is not set
   * 
   * @throws URISyntaxException
   * @throws XMLStreamException
   * @throws IOException
   */
  @Test(expected = NullPointerException.class)
  public void testGetReferencedModel_noLocation() throws URISyntaxException, XMLStreamException, IOException {
    URL urlFile = cl.getResource("testGathering/spec_example2.xml");
    assert urlFile != null;
    File file = new File(urlFile.toURI());
    assert file != null;
    SBMLDocument document = SBMLReader.read(file);
    CompSBMLDocumentPlugin compSBMLDocPlugin =
      (CompSBMLDocumentPlugin) document.getExtension(CompConstants.shortLabel);
    externalModel = compSBMLDocPlugin.getExternalModelDefinition("ExtMod1");
    
    document.setLocationURI(null);
    // this should now throw an exception
    externalModel.getReferencedModel();
  }
  
  /**
   * Tests for case where the referenced modelDef is not found in the specified file: Returning null
   * @throws IOException 
   * @throws XMLStreamException 
   * @throws URISyntaxException 
   */
  @Test
  public void testGetReferencedModel_nonexistant() throws URISyntaxException, XMLStreamException, IOException {
    setUpExternalModelDefinition("testGathering/", "references_nonexistant.xml",
      "ExtMod1");
    Model result = externalModel.getReferencedModel();
    assertNull(result);
  }
  
  /**
   * Tests behaviour of {@link ExternalModelDefinition#getAbsoluteSourceURI} for
   * an already absolute source (no change expected, except 'file:/' specifying
   * the scheme)
   * 
   * @throws URISyntaxException
   * @throws XMLStreamException
   * @throws IOException
   */
  @Test
  public void testGetAbsoluteSourceURI_alreadyAbsolute() throws URISyntaxException, XMLStreamException, IOException {
    setUpExternalModelDefinition("testGathering/", "spec_example2.xml", "ExtMod1");
    setUpExpectation("testGathering/spec_example1.xml");
    String absolute = absolutePath.substring(6) + "spec_example1.xml";
    externalModel.setSource(absolute);
    assertEquals(new URI("file:/" + absolute), externalModel.getAbsoluteSourceURI());
    
    externalModel.setSource(urlExpectation.toString());
    assertEquals(urlExpectation.toURI(), externalModel.getAbsoluteSourceURI());
  }
  
  /**
   * Tests behaviour if source is relative and SBMLDocument's locationURI is a file:/-URI
   * @throws URISyntaxException
   * @throws XMLStreamException
   * @throws IOException
   */
  @Test
  public void testGetAbsoluteSourceURI_relativeToFile() throws URISyntaxException, XMLStreamException, IOException {
    setUpExternalModelDefinition("testGathering/", "spec_example2.xml", "ExtMod1");
    String absolute = "file:/" + absolutePath.substring(6) + "spec_example1.xml";
    System.out.println(new URI(absolute));
    assertEquals(new URI(absolute), externalModel.getAbsoluteSourceURI());
  }
  
  /**
   * Tests behaviour if the containing URI is already online (may happen when reading from https-URL)
   * @throws URISyntaxException
   * @throws XMLStreamException
   * @throws IOException
   */
  @Test
  public void testGetAbsoluteSourceURI_online() throws URISyntaxException, XMLStreamException, IOException {
    setUpExternalModelDefinition("testGathering/", "chain_reference_different_directory.xml",
      "ExtMod1");
    String commonUrl = "https://raw.githubusercontent.com/sbmlteam/jsbml/vetter-comp-single-file/extensions/comp/resources/testGathering/";
    assertEquals(
      new URI(commonUrl + "somewhere_else/chain_reference_intermediate.xml"),
      externalModel.getAbsoluteSourceURI(
        new URI(commonUrl) // The containing directory
        ));
  }
  
  /**
   * Tests behaviour of {@link ExternalModelDefinition#getAbsoluteSourceURI} if
   * the {@link SBMLDocument}'s locationURI is not set
   * @throws URISyntaxException
   * @throws XMLStreamException
   * @throws IOException
   */
  @Test(expected = NullPointerException.class)
  public void testGetAbsoluteSourceURI_noLocation() throws URISyntaxException, XMLStreamException, IOException {
    URL urlFile = cl.getResource("testGathering/spec_example2.xml");
    assert urlFile != null;
    File file = new File(urlFile.toURI());
    assert file != null;

    SBMLDocument document = SBMLReader.read(file);
    document.setLocationURI(null);
    CompSBMLDocumentPlugin compSBMLDocPlugin =
      (CompSBMLDocumentPlugin) document.getExtension(CompConstants.shortLabel);
    externalModel = compSBMLDocPlugin.getExternalModelDefinition("ExtMod1");
    
    // This should now cause an exception
    externalModel.getAbsoluteSourceURI();
  }
}

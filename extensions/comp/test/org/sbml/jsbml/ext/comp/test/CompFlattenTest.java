package org.sbml.jsbml.ext.comp.test;

import org.junit.Assert;
import org.junit.Test;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.ext.comp.CompConstants;
import org.sbml.jsbml.ext.comp.CompModelPlugin;
import org.sbml.jsbml.ext.comp.CompSBMLDocumentPlugin;
import org.sbml.jsbml.ext.comp.ModelDefinition;
import org.sbml.jsbml.ext.comp.Submodel;
import org.sbml.jsbml.ext.comp.util.CompFlatteningConverter;

import javax.xml.stream.XMLStreamException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

public class CompFlattenTest {

  private final static Logger LOGGER      =
    Logger.getLogger(CompFlatteningConverter.class.getName());
  private ClassLoader         classLoader = getClass().getClassLoader();

  private SBMLDocument        expected, original;
  
  /**
   * @param pathOriginal
   *        the path for the classLoader (from comp/resources/) to the file
   *        containing the original document
   * @param pathExpected
   *        the path for the classloader to the file containing the expected
   *        document
   * @throws URISyntaxException
   * @throws XMLStreamException
   * @throws IOException
   */
  private void setUpOriginalAndExpected(String pathOriginal,
    String pathExpected)
    throws URISyntaxException, XMLStreamException, IOException {
    URL urlOriginal = classLoader.getResource(pathOriginal);
    URL urlExpected = classLoader.getResource(pathExpected);
    assert urlOriginal != null;
    assert urlExpected != null;
    File fileOriginal = new File(urlOriginal.toURI());
    File fileExpected = new File(urlExpected.toURI());
    assert fileOriginal != null;
    assert fileExpected != null;
    original = SBMLReader.read(fileOriginal);
    expected = SBMLReader.read(fileExpected);
  }
  
  private boolean equalCompPlugin(SBMLDocument expected, SBMLDocument actual) {
    CompSBMLDocumentPlugin expectedPlugin = (CompSBMLDocumentPlugin) expected.getExtension(CompConstants.shortLabel);
    CompSBMLDocumentPlugin actualPlugin = (CompSBMLDocumentPlugin) actual.getExtension(CompConstants.shortLabel);
    return expectedPlugin.equals(actualPlugin);
  }
  
  /**
   * Tests behaviour of
   * {@link CompFlatteningConverter#internaliseExternalModelDefintions(SBMLDocument)}
   * for an {@link SBMLDocument} without any {@link ExternalModelDefinition}s
   * (trivial case).
   * @throws Exception 
   */
  @Test
  public void testInternaliseExternalModelDefintions_noExternals()
    throws Exception {
    setUpOriginalAndExpected("testGathering/spec_example1.xml", "testGathering/spec_example1.xml");
    SBMLDocument result =
      CompFlatteningConverter.internaliseExternalModelDefintions(original);
    assertEquals(original, result);
    assertFalse(((CompSBMLDocumentPlugin) result.getExtension(
      CompConstants.shortLabel)).isSetListOfExternalModelDefinitions());
    assertTrue(equalCompPlugin(original, result));
  }


  /**
   * Tests behaviour of
   * {@link CompFlatteningConverter#internaliseExternalModelDefintions(SBMLDocument)}
   * for an {@link SBMLDocument} with a single {@link ExternalModelDefinition},
   * that leads directly to a {@link ModelDefinition}.
   * @throws Exception 
   */
  @Test
  public void testInternaliseExternalModelDefintions_simpleExternal()
    throws Exception {
    // spec_example2 references a single external ModelDefinition
    setUpOriginalAndExpected("testGathering/spec_example2.xml",
      "testGathering/single_files/spec_example2_single.xml");
    SBMLDocument result =
      CompFlatteningConverter.internaliseExternalModelDefintions(original);
    assertEquals(expected, result);
    assertTrue(equalCompPlugin(expected, result));
  }
  
  /**
   * Tests behaviour of
   * {@link CompFlatteningConverter#internaliseExternalModelDefintions(SBMLDocument)}
   * for an {@link SBMLDocument} with a single {@link ExternalModelDefinition},
   * that references another file's main {@link Model}.
   * @throws Exception 
   */
  @Test
  public void testInternaliseExternalModelDefintions_simpleRefToMainModel()
    throws Exception {
    // spec_example2 references a single external ModelDefinition
    setUpOriginalAndExpected("testGathering/references_main_model.xml",
      "testGathering/single_files/references_main_model_single.xml");
    SBMLDocument result =
      CompFlatteningConverter.internaliseExternalModelDefintions(original);
    assertEquals(expected, result);
    assertTrue(equalCompPlugin(expected, result));
  }
  
  
  /**
   * Tests behaviour of
   * {@link CompFlatteningConverter#internaliseExternalModelDefintions(SBMLDocument)}
   * for an {@link SBMLDocument} with a single {@link ExternalModelDefinition}
   * that references another file, and a local {@link ModelDefinition},
   * @throws Exception 
   */
  @Test
  public void testInternaliseExternalModelDefintions_hasExternalAndLocal()
    throws Exception {
    // spec_example2 references a single external ModelDefinition
    setUpOriginalAndExpected("testGathering/has_external_and_local.xml",
      "testGathering/single_files/has_external_and_local_single.xml");
    SBMLDocument result =
      CompFlatteningConverter.internaliseExternalModelDefintions(original);
    assertEquals(expected, result);
    assertTrue(equalCompPlugin(expected, result));
  }

  // TODO: check recursion/chain reference
  
  private void printSubmodelInfo(SBMLDocument doc) {
    System.out.println("\nDOC overview:");
    CompSBMLDocumentPlugin docPlugin =
      (CompSBMLDocumentPlugin) doc.getExtension(CompConstants.shortLabel);
    for (ModelDefinition md : docPlugin.getListOfModelDefinitions()) {
      System.out.print(md);
      CompModelPlugin cmp =
        (CompModelPlugin) md.getExtension(CompConstants.shortLabel);
      System.out.println(" -> " + cmp);
      if (cmp != null) {
        for (Submodel sm : cmp.getListOfSubmodels()) {
          System.out.println("\t" + sm);
          System.out.println(
            "\t" + docPlugin.getModelDefinition(sm.getModelRef()));
        }
      } else {
        System.out.println();
      }
    }
  }
  
  @Test
  public void testInternaliseExternalModelDefinitions_simpleChain()
    throws Exception {
    setUpOriginalAndExpected("testGathering/internalise_simple_chain_head.xml",
      "testGathering/single_files/internalise_simple_chain_single.xml");
    printSubmodelInfo(expected);
    SBMLDocument result =
        CompFlatteningConverter.internaliseExternalModelDefintions(original);
    printSubmodelInfo(result);
    assertEquals(expected, result);
    assertTrue(equalCompPlugin(expected, result));
  }
  
  
  
  // @Test
  public void testAllData() {
    ClassLoader cl = this.getClass().getClassLoader();
    for (int i = 1; i < 62; i++) {
      URL urlFile = cl.getResource("testFlattening/" + "test" + i + ".xml");
      URL urlExpected =
        cl.getResource("testFlattening/" + "test" + i + "_flat.xml");
      assert urlFile != null;
      assert urlExpected != null;
      runTestOnFiles(urlFile, urlExpected, String.valueOf(i));
    }
  }


  // @Test
  public void testSpecificFile() {
    int i = 6;
    ClassLoader cl = this.getClass().getClassLoader();
    URL urlFile = cl.getResource("testFlattening/" + "test" + i + ".xml");
    URL urlExpected =
      cl.getResource("testFlattening/" + "test" + i + "_flat.xml");
    assert urlFile != null;
    assert urlExpected != null;
    runTestOnFiles(urlFile, urlExpected, String.valueOf(i));
  }


  private void runTestOnFiles(URL urlFile, URL urlExpected, String name) {
    try {
      File file = new File(urlFile.toURI());
      File expectedFile = new File(urlExpected.toURI());
      SBMLReader reader = new SBMLReader();
      SBMLDocument document = reader.readSBML(file);
      SBMLDocument expectedDocument = reader.readSBML(expectedFile);
      CompFlatteningConverter compFlatteningConverter =
        new CompFlatteningConverter();
      SBMLDocument flattenedDocument =
        compFlatteningConverter.flatten(document);
      LOGGER.info("Testing Model " + name + ": ");
      SBMLWriter.write(flattenedDocument, System.out, ' ', (short) 2);
      System.out.println("\n-------");
      Assert.assertTrue("Success Testing Model",
        expectedDocument.equals(flattenedDocument));
    } catch (XMLStreamException | IOException e) {
      LOGGER.warning("Failed testing Model " + name + ": ");
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }
}

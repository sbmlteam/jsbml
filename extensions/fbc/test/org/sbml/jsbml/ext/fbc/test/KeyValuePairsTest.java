/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
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
package org.sbml.jsbml.ext.fbc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.KeyValuePair;
import org.sbml.jsbml.ext.fbc.KeyValuePairs;

/**
 * Tests {@link KeyValuePair} and {@link KeyValuePairs}.
 * 
 * @since 1.7
 */
public class KeyValuePairsTest {

  /**
   * The URI of the key-value pairs of the libSBML model.
   */
  private static final String KVP_URI = "https://github.com/matthiaskoenig/cy3sbml/kvp";

  /**
   * The key-value pairs of the fbc version 3 model written by libSBML: with
   * and without value and URI, with id and name.
   */
  @Test
  public void getLibsbml() throws XMLStreamException {
    SBMLDocument doc = FBCVersion3Test.readLibsbmlModel();
    Model model = doc.getModel();

    assertEquals(Arrays.asList(
      new KeyValuePair("author", "cy3sbml", KVP_URI),
      new KeyValuePair("created", "2026-09-29", null, "kvp_created", null)),
      KeyValuePairs.get(model));
    assertEquals(Collections.singletonList(
      new KeyValuePair("compartment_label", "cytosol", KVP_URI, null, "compartment label")),
      KeyValuePairs.get(model.getSpecies("D")));
    List<KeyValuePair> reaction = KeyValuePairs.get(model.getReaction("RGLX"));
    assertEquals(Arrays.asList(new KeyValuePair("confidence", "4", KVP_URI), new KeyValuePair("curated")), reaction);
    assertFalse(reaction.get(1).isSetValue());
    assertFalse(reaction.get(1).isSetUri());
    assertEquals(Collections.singletonList(new KeyValuePair("source", "fbc v3 specification", KVP_URI)),
      KeyValuePairs.get(FBCVersion3Test.fbc(doc).getUserDefinedConstraint("uc1")));

    // no annotation
    assertTrue(KeyValuePairs.get(model.getSpecies("A")).isEmpty());
    assertTrue(KeyValuePairs.get(model.getCompartment("c")).isEmpty());
  }

  /**
   * Writing the libSBML model keeps the key-value pairs.
   */
  @Test
  public void writeAndReadLibsbml() throws XMLStreamException {
    SBMLDocument doc = FBCVersion3Test.readLibsbmlModel();
    SBMLDocument read = FBCVersion3Test.writeAndRead(doc);
    assertEquals(KeyValuePairs.get(doc.getModel()), KeyValuePairs.get(read.getModel()));
    assertEquals(KeyValuePairs.get(doc.getModel().getReaction("RGLX")),
      KeyValuePairs.get(read.getModel().getReaction("RGLX")));
  }

  /**
   * {@link KeyValuePair#equals(Object)}, {@link KeyValuePair#hashCode()} and
   * the required key.
   */
  @Test
  public void keyValuePair() {
    KeyValuePair pair = new KeyValuePair("k", "v", "u", "i", "n");
    assertEquals("k", pair.getKey());
    assertEquals("v", pair.getValue());
    assertEquals("u", pair.getUri());
    assertEquals("i", pair.getId());
    assertEquals("n", pair.getName());
    assertEquals(pair, new KeyValuePair("k", "v", "u", "i", "n"));
    assertEquals(pair.hashCode(), new KeyValuePair("k", "v", "u", "i", "n").hashCode());
    assertNotEquals(pair, new KeyValuePair("k", "v", "u", "i", null));
    assertNotEquals(new KeyValuePair("k"), new KeyValuePair("k", ""));
    assertTrue(pair.toString(), pair.toString().contains("key=k"));
    try {
      new KeyValuePair(null, "v");
      throw new AssertionError("the key is required");
    } catch (NullPointerException exc) {
      // expected
    }
  }

  /**
   * Sets the pairs of an element without annotation, writes the XML libSBML
   * writes and reads it back.
   * 
   * <p>libSBML 5.21 (python-libsbml-experimental) reads the written XML
   * without errors and gets the same keys, values and URIs from its
   * {@code getListOfKeyValuePairs()}; it returns no id and name of a pair,
   * not even for the model it wrote itself.</p>
   */
  @Test
  public void setWithoutAnnotation() throws XMLStreamException {
    SBMLDocument doc = FBCVersion3Test.createDocument(FBCConstants.namespaceURI_L3V1V3);
    Model model = doc.getModel();
    List<KeyValuePair> pairs = Arrays.asList(new KeyValuePair("k1", "v1", "http://example.org/k1", "kvp1", "pair 1"),
      new KeyValuePair("k2"));
    KeyValuePairs.set(model, pairs);
    assertEquals(pairs, KeyValuePairs.get(model));

    String xml = new SBMLWriter().writeSBMLToString(doc);
    assertTrue(xml, xml.contains("<listOfKeyValuePairs xmlns=\"http://sbml.org/fbc/keyvaluepair\">"));
    assertTrue(xml, xml.contains(
      "<keyValuePair id=\"kvp1\" name=\"pair 1\" key=\"k1\" value=\"v1\" uri=\"http://example.org/k1\"/>"));
    assertTrue(xml, xml.contains("<keyValuePair key=\"k2\"/>"));

    SBMLDocument read = FBCVersion3Test.writeAndRead(doc);
    assertEquals(pairs, KeyValuePairs.get(read.getModel()));
    assertEquals(xml, new SBMLWriter().writeSBMLToString(read));
  }

  /**
   * Replacing and removing the pairs leaves the rest of the annotation
   * unchanged.
   */
  @Test
  public void replaceAndRemove() throws XMLStreamException {
    SBMLDocument doc = FBCVersion3Test.createDocument(FBCConstants.namespaceURI_L3V1V3);
    Model model = doc.getModel();
    Species species = model.createSpecies("s", model.getCompartment("c"));
    species.setMetaId("meta_s");
    species.addCVTerm(new CVTerm(CVTerm.Qualifier.BQB_IS, "http://identifiers.org/chebi/CHEBI:17234"));
    species.getAnnotation().appendNonRDFAnnotation("<my:before xmlns:my=\"http://example.org/my\" a=\"1\"/>");
    KeyValuePairs.set(species, Collections.singletonList(new KeyValuePair("k1", "v1")));
    species.getAnnotation().appendNonRDFAnnotation("<my:after xmlns:my=\"http://example.org/my\"/>");

    // replaced at the same position
    List<KeyValuePair> pairs = Arrays.asList(new KeyValuePair("k2", "v2"), new KeyValuePair("k3", null, "u3"));
    KeyValuePairs.set(species, pairs);
    assertEquals(pairs, KeyValuePairs.get(species));
    String annotation = species.getAnnotation().getNonRDFannotationAsString();
    assertFalse(annotation, annotation.contains("k1"));
    int before = annotation.indexOf("my:before");
    int list = annotation.indexOf("listOfKeyValuePairs");
    int after = annotation.indexOf("my:after");
    assertTrue(annotation, (0 < before) && (before < list) && (list < after));

    SBMLDocument read = FBCVersion3Test.writeAndRead(doc);
    Species readSpecies = read.getModel().getSpecies("s");
    assertEquals(pairs, KeyValuePairs.get(readSpecies));
    assertEquals(1, readSpecies.getCVTermCount());

    // removed, the other annotation stays
    KeyValuePairs.set(species, Collections.<KeyValuePair>emptyList());
    assertTrue(KeyValuePairs.get(species).isEmpty());
    annotation = species.getAnnotation().getNonRDFannotationAsString();
    assertFalse(annotation, annotation.contains("listOfKeyValuePairs"));
    assertTrue(annotation, annotation.contains("my:before") && annotation.contains("my:after"));
    assertEquals(1, species.getCVTermCount());
    read = FBCVersion3Test.writeAndRead(doc);
    assertTrue(KeyValuePairs.get(read.getModel().getSpecies("s")).isEmpty());
    assertEquals(1, read.getModel().getSpecies("s").getCVTermCount());

    // removing the only content removes the annotation
    KeyValuePairs.set(model, Collections.singletonList(new KeyValuePair("k")));
    assertTrue(model.isSetAnnotation());
    KeyValuePairs.set(model, null);
    assertFalse(model.isSetAnnotation());
  }

}

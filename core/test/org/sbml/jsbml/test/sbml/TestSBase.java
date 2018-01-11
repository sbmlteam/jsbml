/*
 * @file    TestSBase.java
 * @brief   SBase unit tests
 *
 * This test file was converted from libsbml http://sbml.org/software/libsbml
 *
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

package org.sbml.jsbml.test.sbml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.xml.XMLAttributes;
import org.sbml.jsbml.xml.XMLNamespaces;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.XMLToken;
import org.sbml.jsbml.xml.XMLTriple;

/**
 * Tests for the {@link SBase} class, mainly about manipulation of 'notes' {@link XMLNode} and {@link CVTerm}.
 * 
 * @author  Nicolas Rodriguez
 * @author  Akiya Jouraku
 * @author  Ben Bornstein
 * @since 1.0
 */
public class TestSBase {

  /**
   * 
   */
  private SBase sbase;

  /**
   * @throws Exception
   */
  @Before public void setUp() throws Exception
  {
    sbase = new Model(2,4);
  }

  /**
   * @throws Exception
   */
  @After public void tearDown() throws Exception
  {
  }

  /**
   * 
   */
  @Test public void test_SBase_CVTerms()
  {
    CVTerm cv = new  CVTerm(CVTerm.Qualifier.BQB_IS); // DIFF - the jsbml enum is different from the libsbml int value and placed in different classes.

    cv.addResource("foo");
    assertTrue(sbase.getNumCVTerms() == 0);
    assertTrue(sbase.getCVTerms().size() == 0);

    sbase.setMetaId("_id");
    sbase.addCVTerm(cv);
    assertTrue(sbase.getNumCVTerms() == 1);
    assertTrue(sbase.getCVTerms() != null);
    assertTrue(sbase.getCVTerm(0).equals(cv));
    cv = null;
  }

  /**
   * 
   */
  @Test public void test_SBase_addCVTerms()
  {
    CVTerm cv = new  CVTerm(CVTerm.Qualifier.BQB_ENCODES);

    cv.addResource("foo");
    sbase.setMetaId("sbase1");
    sbase.addCVTerm(cv);
    assertTrue(sbase.getNumCVTerms() == 1);
    assertTrue(sbase.getCVTerms() != null);

    List<String> uriList = sbase.getCVTerm(0).getResources();
    assertTrue(uriList.get(0).equals("foo"));
    CVTerm cv1 = new  CVTerm(CVTerm.Qualifier.BQB_IS);

    cv1.addResource("bar");
    sbase.addCVTerm(cv1);
    assertTrue(sbase.getNumCVTerms() == 2);

    CVTerm cv2 = new  CVTerm(CVTerm.Qualifier.BQB_IS);

    cv2.addResource("bar1");
    sbase.addCVTerm(cv2);
    assertTrue(sbase.getNumCVTerms() == 3); // DIFF - why 2 here ?!? Is libsbml doing some merging of the CVTerms base on the qualifier !!!!

    uriList = sbase.getCVTerm(1).getResources();
    assertTrue(uriList.size() == 1);
    assertTrue(uriList.get(0).equals("bar"));

    CVTerm cv4 = new  CVTerm(CVTerm.Qualifier.BQB_IS);

    cv4.addResource("bar1");
    sbase.addCVTerm(cv4);
    assertTrue(sbase.getNumCVTerms() == 4);

    uriList = sbase.getCVTerm(3).getResources();
    assertTrue(uriList.size() == 1);
    assertTrue(uriList.get(0).equals("bar1"));

    CVTerm cv5 = new  CVTerm(CVTerm.Qualifier.BQB_HAS_PART);

    cv5.addResource("bar1"); // DIFF - Does libsbml not allow to have twice the same URI in one hasPart CVTerm ???!?
    sbase.addCVTerm(cv5);
    assertTrue(sbase.getNumCVTerms() == 5);
    uriList = sbase.getCVTerm(4).getResources();
    assertTrue(uriList.size() == 1);
    assertTrue(uriList.get(0).equals("bar1"));
    cv = null;
    cv2 = null;
    cv1 = null;
    cv4 = null;
  }

  /**
   * @throws XMLStreamException
   */
  @Test public void test_SBase_appendNotes() throws XMLStreamException
  {
    XMLToken token;
    XMLNode node;
    XMLToken token1;
    XMLNode node1;
    XMLNode node2;
    XMLTriple triple = new  XMLTriple("p", "", "");
    XMLAttributes att = new  XMLAttributes();
    XMLNamespaces ns = new  XMLNamespaces();
    ns.add("http://www.w3.org/1999/xhtml", "");
    XMLToken token4 = new  XMLNode("This is my text"); // DIFF - we cannot instantiate XMLToken in jsbml
    XMLNode node4 = new XMLNode(token4);
    XMLToken token5 = new  XMLNode("This is additional text");
    XMLNode node5 = new XMLNode(token5);

    System.out.println("Triple name = " + triple.getName());

    token = new  XMLNode(triple,att,ns);
    node = new XMLNode(token);

    node.addChild(node4);
    sbase.setNotes(node); // TODO - add the notes XMLNode around if not present !

    assertTrue(sbase.isSetNotes() == true);

    token1 = new  XMLNode(triple,att,ns);
    node1 = new XMLNode(token1);
    node1.addChild(node5);

    sbase.appendNotes(node1);

    System.out.println("Token name = " + token.getName());
    System.out.println("Node name = " + node.getName());

    System.out.println("Token name = " + token1.getName());
    System.out.println("Node name = " + node1.getName());

    assertTrue(sbase.isSetNotes() == true);
    node2 = sbase.getNotes();

    assertTrue(node2.getNumChildren() == 2);

    System.out.println("Node name = " + node2.getChild(0).getName());
    System.out.println("Node name = " + node.getChild(0).getName());
    System.out.println("Notes:" + node2.toXMLString());

    assertTrue(node2.getChild(0).getName().equals("p"));
    assertTrue(node2.getChild(0).getNumChildren() == 1);
    assertTrue(node2.getChild(1).getName().equals("p"));
    assertTrue(node2.getChild(1).getNumChildren() == 1);
    String chars1 = node2.getChild(0).getChild(0).getCharacters();
    String chars2 = node2.getChild(1).getChild(0).getCharacters();
    assertTrue(chars1.equals("This is my text"));
    assertTrue(chars2.equals("This is additional text"));
    node = null;
    node1 = null;
  }

  /**
   * 
   */
  @Test public void test_SBase_appendNotes1()
  {
    XMLAttributes att = new  XMLAttributes();
    XMLNamespaces ns = new  XMLNamespaces();
    ns.add("http://www.w3.org/1999/xhtml", "");
    XMLTriple html_triple = new  XMLTriple("html", "", "");
    XMLTriple head_triple = new  XMLTriple("head", "", "");
    XMLTriple title_triple = new  XMLTriple("title", "", "");
    XMLTriple body_triple = new  XMLTriple("body", "", "");
    XMLTriple p_triple = new  XMLTriple("p", "", "");
    XMLToken html_token = new  XMLNode(html_triple,att,ns);
    XMLToken head_token = new  XMLNode(head_triple,att);
    XMLToken title_token = new  XMLNode(title_triple,att);
    XMLToken body_token = new  XMLNode(body_triple,att);
    XMLToken p_token = new  XMLNode(p_triple,att);
    XMLToken text_token = new  XMLNode("This is my text");
    XMLNode html_node = new XMLNode(html_token);
    XMLNode head_node = new XMLNode(head_token);
    XMLNode title_node = new XMLNode(title_token);
    XMLNode body_node = new XMLNode(body_token);
    XMLNode p_node = new XMLNode(p_token);
    XMLNode text_node = new XMLNode(text_token);
    XMLToken text_token1 = new  XMLNode("This is more text");
    XMLNode html_node1 = new XMLNode(html_token);
    XMLNode head_node1 = new XMLNode(head_token);
    XMLNode title_node1 = new XMLNode(title_token);
    XMLNode body_node1 = new XMLNode(body_token);
    XMLNode p_node1 = new XMLNode(p_token);
    XMLNode text_node1 = new XMLNode(text_token1);
    XMLNode notes;
    XMLNode child, child1;
    p_node.addChild(text_node);
    body_node.addChild(p_node);
    head_node.addChild(title_node);
    html_node.addChild(head_node);
    html_node.addChild(body_node);
    p_node1.addChild(text_node1);
    body_node1.addChild(p_node1);
    head_node1.addChild(title_node1);
    html_node1.addChild(head_node1);
    html_node1.addChild(body_node1);
    sbase.setNotes(html_node);
    sbase.appendNotes(html_node1);
    notes = sbase.getNotes();
    assertTrue(notes.getName().equals("notes"));
    assertTrue(notes.getNumChildren() == 1);
    child = notes.getChild(0);
    assertTrue(child.getName().equals("html"));
    assertTrue(child.getNumChildren() == 2);
    child = child.getChild(1);
    assertTrue(child.getName().equals("body"));
    assertTrue(child.getNumChildren() == 2);
    child1 = child.getChild(0);
    assertTrue(child1.getName().equals("p"));
    assertTrue(child1.getNumChildren() == 1);
    child1 = child1.getChild(0);
    assertTrue(child1.getCharacters().equals("This is my text"));
    assertTrue(child1.getNumChildren() == 0);
    child1 = child.getChild(1);
    assertTrue(child1.getName().equals("p"));
    assertTrue(child1.getNumChildren() == 1);
    child1 = child1.getChild(0);
    assertTrue(child1.getCharacters().equals("This is more text"));
    assertTrue(child1.getNumChildren() == 0);
    att = null;
    ns = null;
    html_triple = null;
    head_triple = null;
    body_triple = null;
    p_triple = null;
    html_token = null;
    head_token = null;
    body_token = null;
    p_token = null;
    text_token = null;
    text_token1 = null;
    html_node = null;
    head_node = null;
    body_node = null;
    p_node = null;
    text_node = null;
    html_node1 = null;
    head_node1 = null;
    body_node1 = null;
    p_node1 = null;
    text_node1 = null;
  }

  /**
   * 
   */
  @Test public void test_SBase_appendNotes2()
  {
    XMLAttributes att = new  XMLAttributes();
    XMLNamespaces ns = new  XMLNamespaces();
    ns.add("http://www.w3.org/1999/xhtml", "");
    XMLTriple html_triple = new  XMLTriple("html", "", "");
    XMLTriple head_triple = new  XMLTriple("head", "", "");
    XMLTriple title_triple = new  XMLTriple("title", "", "");
    XMLTriple body_triple = new  XMLTriple("body", "", "");
    XMLTriple p_triple = new  XMLTriple("p", "", "");
    XMLToken html_token = new  XMLNode(html_triple,att,ns);
    XMLToken head_token = new  XMLNode(head_triple,att);
    XMLToken title_token = new  XMLNode(title_triple,att);
    XMLToken body_token = new  XMLNode(body_triple,att);
    XMLToken p_token = new  XMLNode(p_triple,att);
    XMLToken text_token = new  XMLNode("This is my text");
    XMLNode html_node = new XMLNode(html_token);
    XMLNode head_node = new XMLNode(head_token);
    XMLNode title_node = new XMLNode(title_token);
    XMLNode body_node = new XMLNode(body_token);
    XMLNode p_node = new XMLNode(p_token);
    XMLNode text_node = new XMLNode(text_token);
    XMLToken body_token1 = new  XMLNode(body_triple,att,ns);
    XMLToken text_token1 = new  XMLNode("This is more text");
    XMLNode body_node1 = new XMLNode(body_token1);
    XMLNode p_node1 = new XMLNode(p_token);
    XMLNode text_node1 = new XMLNode(text_token1);
    XMLNode notes;
    XMLNode child, child1;
    p_node.addChild(text_node);
    body_node.addChild(p_node);
    head_node.addChild(title_node);
    html_node.addChild(head_node);
    html_node.addChild(body_node);
    p_node1.addChild(text_node1);
    body_node1.addChild(p_node1);
    sbase.setNotes(html_node);
    sbase.appendNotes(body_node1);
    notes = sbase.getNotes();
    assertTrue(notes.getName().equals("notes"));
    assertTrue(notes.getNumChildren() == 1);
    child = notes.getChild(0);
    assertTrue(child.getName().equals("html"));
    assertTrue(child.getNumChildren() == 2);
    child = child.getChild(1);
    assertTrue(child.getName().equals("body"));
    assertTrue(child.getNumChildren() == 2);
    child1 = child.getChild(0);
    assertTrue(child1.getName().equals("p"));
    assertTrue(child1.getNumChildren() == 1);
    child1 = child1.getChild(0);
    assertTrue(child1.getCharacters().equals("This is my text"));
    assertTrue(child1.getNumChildren() == 0);
    child1 = child.getChild(1);
    assertTrue(child1.getName().equals("p"));
    assertTrue(child1.getNumChildren() == 1);
    child1 = child1.getChild(0);
    assertTrue(child1.getCharacters().equals("This is more text"));
    assertTrue(child1.getNumChildren() == 0);
    att = null;
    ns = null;
    html_triple = null;
    head_triple = null;
    body_triple = null;
    p_triple = null;
    html_token = null;
    head_token = null;
    body_token = null;
    p_token = null;
    text_token = null;
    text_token1 = null;
    body_token1 = null;
    html_node = null;
    head_node = null;
    body_node = null;
    p_node = null;
    text_node = null;
    body_node1 = null;
    p_node1 = null;
    text_node1 = null;
  }

  /**
   * 
   */
  @Test public void test_SBase_appendNotes3()
  {
    XMLAttributes att = new  XMLAttributes();
    XMLNamespaces ns = new  XMLNamespaces();
    ns.add("http://www.w3.org/1999/xhtml", "");
    XMLTriple html_triple = new  XMLTriple("html", "", "");
    XMLTriple head_triple = new  XMLTriple("head", "", "");
    XMLTriple title_triple = new  XMLTriple("title", "", "");
    XMLTriple body_triple = new  XMLTriple("body", "", "");
    XMLTriple p_triple = new  XMLTriple("p", "", "");
    XMLToken html_token = new  XMLNode(html_triple,att,ns);
    XMLToken head_token = new  XMLNode(head_triple,att);
    XMLToken title_token = new  XMLNode(title_triple,att);
    XMLToken body_token = new  XMLNode(body_triple,att);
    XMLToken p_token = new  XMLNode(p_triple,att);
    XMLToken text_token = new  XMLNode("This is my text");
    XMLNode html_node = new XMLNode(html_token);
    XMLNode head_node = new XMLNode(head_token);
    XMLNode title_node = new XMLNode(title_token);
    XMLNode body_node = new XMLNode(body_token);
    XMLNode p_node = new XMLNode(p_token);
    XMLNode text_node = new XMLNode(text_token);
    XMLToken p_token1 = new  XMLNode(p_triple,att,ns);
    XMLToken text_token1 = new  XMLNode("This is more text");
    XMLNode p_node1 = new XMLNode(p_token1);
    XMLNode text_node1 = new XMLNode(text_token1);
    XMLNode notes;
    XMLNode child, child1;
    p_node.addChild(text_node);
    body_node.addChild(p_node);
    head_node.addChild(title_node);
    html_node.addChild(head_node);
    html_node.addChild(body_node);
    p_node1.addChild(text_node1);
    sbase.setNotes(html_node);
    sbase.appendNotes(p_node1);
    notes = sbase.getNotes();
    assertTrue(notes.getName().equals("notes"));
    assertTrue(notes.getNumChildren() == 1);
    child = notes.getChild(0);
    assertTrue(child.getName().equals("html"));
    assertTrue(child.getNumChildren() == 2);
    child = child.getChild(1);
    assertTrue(child.getName().equals("body"));
    assertTrue(child.getNumChildren() == 2);
    child1 = child.getChild(0);
    assertTrue(child1.getName().equals("p"));
    assertTrue(child1.getNumChildren() == 1);
    child1 = child1.getChild(0);
    assertTrue(child1.getCharacters().equals("This is my text"));
    assertTrue(child1.getNumChildren() == 0);
    child1 = child.getChild(1);
    assertTrue(child1.getName().equals("p"));
    assertTrue(child1.getNumChildren() == 1);
    child1 = child1.getChild(0);
    assertTrue(child1.getCharacters().equals("This is more text"));
    assertTrue(child1.getNumChildren() == 0);
    att = null;
    ns = null;
    html_triple = null;
    head_triple = null;
    body_triple = null;
    p_triple = null;
    html_token = null;
    head_token = null;
    body_token = null;
    p_token = null;
    text_token = null;
    text_token1 = null;
    p_token1 = null;
    html_node = null;
    head_node = null;
    body_node = null;
    p_node = null;
    text_node = null;
    p_node1 = null;
    text_node1 = null;
  }

  /**
   * 
   */
  @Test public void test_SBase_appendNotes4()
  {
    XMLAttributes att = new  XMLAttributes();
    XMLNamespaces ns = new  XMLNamespaces();
    ns.add("http://www.w3.org/1999/xhtml", "");
    XMLTriple html_triple = new  XMLTriple("html", "", "");
    XMLTriple head_triple = new  XMLTriple("head", "", "");
    XMLTriple title_triple = new  XMLTriple("title", "", "");
    XMLTriple body_triple = new  XMLTriple("body", "", "");
    XMLTriple p_triple = new  XMLTriple("p", "", "");
    XMLToken html_token = new  XMLNode(html_triple,att,ns);
    XMLToken head_token = new  XMLNode(head_triple,att);
    XMLToken title_token = new  XMLNode(title_triple,att);
    XMLToken body_token = new  XMLNode(body_triple,att);
    XMLToken p_token = new  XMLNode(p_triple,att);
    XMLToken body_token1 = new  XMLNode(body_triple,att,ns);
    XMLToken text_token = new  XMLNode("This is my text");
    XMLNode body_node = new XMLNode(body_token1);
    XMLNode p_node = new XMLNode(p_token);
    XMLNode text_node = new XMLNode(text_token);
    XMLToken text_token1 = new  XMLNode("This is more text");
    XMLNode html_node1 = new XMLNode(html_token);
    XMLNode head_node1 = new XMLNode(head_token);
    XMLNode title_node1 = new XMLNode(title_token);
    XMLNode body_node1 = new XMLNode(body_token);
    XMLNode p_node1 = new XMLNode(p_token);
    XMLNode text_node1 = new XMLNode(text_token1);
    XMLNode notes;
    XMLNode child, child1;
    p_node.addChild(text_node);
    body_node.addChild(p_node);
    p_node1.addChild(text_node1);
    body_node1.addChild(p_node1);
    head_node1.addChild(title_node1);
    html_node1.addChild(head_node1);
    html_node1.addChild(body_node1);
    sbase.setNotes(body_node);
    sbase.appendNotes(html_node1);
    notes = sbase.getNotes();
    assertTrue(notes.getName().equals("notes"));
    assertTrue(notes.getNumChildren() == 1);
    child = notes.getChild(0);
    assertTrue(child.getName().equals("html"));
    assertTrue(child.getNumChildren() == 2);
    child = child.getChild(1);
    assertTrue(child.getName().equals("body"));
    assertTrue(child.getNumChildren() == 2);
    child1 = child.getChild(0);
    assertTrue(child1.getName().equals("p"));
    assertTrue(child1.getNumChildren() == 1);
    child1 = child1.getChild(0);
    assertTrue(child1.getCharacters().equals("This is my text"));
    assertTrue(child1.getNumChildren() == 0);
    child1 = child.getChild(1);
    assertTrue(child1.getName().equals("p"));
    assertTrue(child1.getNumChildren() == 1);
    child1 = child1.getChild(0);
    assertTrue(child1.getCharacters().equals("This is more text"));
    assertTrue(child1.getNumChildren() == 0);
    att = null;
    ns = null;
    html_triple = null;
    head_triple = null;
    body_triple = null;
    p_triple = null;
    body_token = null;
    p_token = null;
    text_token = null;
    text_token1 = null;
    body_token1 = null;
    body_node = null;
    p_node = null;
    text_node = null;
    html_node1 = null;
    head_node1 = null;
    body_node1 = null;
    p_node1 = null;
    text_node1 = null;
  }

  /**
   * 
   */
  @Test public void test_SBase_appendNotes5()
  {
    XMLAttributes att = new  XMLAttributes();
    XMLNamespaces ns = new  XMLNamespaces();
    ns.add("http://www.w3.org/1999/xhtml", "");
    XMLTriple html_triple = new  XMLTriple("html", "", "");
    XMLTriple head_triple = new  XMLTriple("head", "", "");
    XMLTriple title_triple = new  XMLTriple("title", "", "");
    XMLTriple body_triple = new  XMLTriple("body", "", "");
    XMLTriple p_triple = new  XMLTriple("p", "", "");
    XMLToken html_token = new  XMLNode(html_triple,att,ns);
    XMLToken head_token = new  XMLNode(head_triple,att);
    XMLToken title_token = new  XMLNode(title_triple,att);
    XMLToken body_token = new  XMLNode(body_triple,att);
    XMLToken p_token = new  XMLNode(p_triple,att);
    XMLToken p_token1 = new  XMLNode(p_triple,att,ns);
    XMLToken text_token = new  XMLNode("This is my text");
    XMLNode p_node = new XMLNode(p_token1);
    XMLNode text_node = new XMLNode(text_token);
    XMLToken text_token1 = new  XMLNode("This is more text");
    XMLNode html_node1 = new XMLNode(html_token);
    XMLNode head_node1 = new XMLNode(head_token);
    XMLNode title_node1 = new XMLNode(title_token);
    XMLNode body_node1 = new XMLNode(body_token);
    XMLNode p_node1 = new XMLNode(p_token);
    XMLNode text_node1 = new XMLNode(text_token1);
    XMLNode notes;
    XMLNode child, child1;
    p_node.addChild(text_node);
    p_node1.addChild(text_node1);
    body_node1.addChild(p_node1);
    head_node1.addChild(title_node1);
    html_node1.addChild(head_node1);
    html_node1.addChild(body_node1);
    sbase.setNotes(p_node);
    sbase.appendNotes(html_node1);
    notes = sbase.getNotes();
    assertTrue(notes.getName().equals("notes"));
    assertTrue(notes.getNumChildren() == 1);
    child = notes.getChild(0);
    assertTrue(child.getName().equals("html"));
    assertTrue(child.getNumChildren() == 2);
    child = child.getChild(1);
    assertTrue(child.getName().equals("body"));
    assertTrue(child.getNumChildren() == 2);
    child1 = child.getChild(0);
    assertTrue(child1.getName().equals("p"));
    assertTrue(child1.getNumChildren() == 1);
    child1 = child1.getChild(0);
    assertTrue(child1.getCharacters().equals("This is my text"));
    assertTrue(child1.getNumChildren() == 0);
    child1 = child.getChild(1);
    assertTrue(child1.getName().equals("p"));
    assertTrue(child1.getNumChildren() == 1);
    child1 = child1.getChild(0);
    assertTrue(child1.getCharacters().equals("This is more text"));
    assertTrue(child1.getNumChildren() == 0);
    att = null;
    ns = null;
    html_triple = null;
    head_triple = null;
    body_triple = null;
    p_triple = null;
    body_token = null;
    p_token = null;
    p_token1 = null;
    text_token = null;
    text_token1 = null;
    p_node = null;
    text_node = null;
    html_node1 = null;
    head_node1 = null;
    body_node1 = null;
    p_node1 = null;
    text_node1 = null;
  }

  /**
   * 
   */
  @Test public void test_SBase_appendNotes6()
  {
    XMLAttributes att = new  XMLAttributes();
    XMLNamespaces ns = new  XMLNamespaces();
    ns.add("http://www.w3.org/1999/xhtml", "");
    XMLTriple body_triple = new  XMLTriple("body", "", "");
    XMLTriple p_triple = new  XMLTriple("p", "", "");
    XMLToken body_token = new  XMLNode(body_triple,att,ns);
    XMLToken p_token = new  XMLNode(p_triple,att);
    XMLToken text_token = new  XMLNode("This is my text");
    XMLNode body_node = new XMLNode(body_token);
    XMLNode p_node = new XMLNode(p_token);
    XMLNode text_node = new XMLNode(text_token);
    XMLToken text_token1 = new  XMLNode("This is more text");
    XMLNode body_node1 = new XMLNode(body_token);
    XMLNode p_node1 = new XMLNode(p_token);
    XMLNode text_node1 = new XMLNode(text_token1);
    XMLNode notes;
    XMLNode child, child1;
    p_node.addChild(text_node);
    body_node.addChild(p_node);
    p_node1.addChild(text_node1);
    body_node1.addChild(p_node1);
    sbase.setNotes(body_node);
    sbase.appendNotes(body_node1);
    notes = sbase.getNotes();
    assertTrue(notes.getName().equals("notes"));
    assertTrue(notes.getNumChildren() == 1);
    child = notes.getChild(0);
    assertTrue(child.getName().equals("body"));
    assertTrue(child.getNumChildren() == 2);
    child1 = child.getChild(0);
    assertTrue(child1.getName().equals("p"));
    assertTrue(child1.getNumChildren() == 1);
    child1 = child1.getChild(0);
    assertTrue(child1.getCharacters().equals("This is my text"));
    assertTrue(child1.getNumChildren() == 0);
    child1 = child.getChild(1);
    assertTrue(child1.getName().equals("p"));
    assertTrue(child1.getNumChildren() == 1);
    child1 = child1.getChild(0);
    assertTrue(child1.getCharacters().equals("This is more text"));
    assertTrue(child1.getNumChildren() == 0);
    att = null;
    ns = null;
    body_triple = null;
    p_triple = null;
    body_token = null;
    p_token = null;
    text_token = null;
    text_token1 = null;
    body_node = null;
    p_node = null;
    text_node = null;
    body_node1 = null;
    p_node1 = null;
    text_node1 = null;
  }

  /**
   * 
   */
  @Test public void test_SBase_appendNotes7()
  {
    XMLAttributes att = new  XMLAttributes();
    XMLNamespaces ns = new  XMLNamespaces();
    ns.add("http://www.w3.org/1999/xhtml", "");
    XMLTriple body_triple = new  XMLTriple("body", "", "");
    XMLTriple p_triple = new  XMLTriple("p", "", "");
    XMLToken body_token = new  XMLNode(body_triple,att,ns);
    XMLToken p_token1 = new  XMLNode(p_triple,att,ns);
    XMLToken text_token = new  XMLNode("This is my text");
    XMLToken p_token = new  XMLNode(p_triple,att);
    XMLNode p_node = new XMLNode(p_token1);
    XMLNode text_node = new XMLNode(text_token);
    XMLToken text_token1 = new  XMLNode("This is more text");
    XMLNode body_node1 = new XMLNode(body_token);
    XMLNode p_node1 = new XMLNode(p_token);
    XMLNode text_node1 = new XMLNode(text_token1);
    XMLNode notes;
    XMLNode child, child1;
    p_node.addChild(text_node);
    p_node1.addChild(text_node1);
    body_node1.addChild(p_node1);
    sbase.setNotes(p_node);
    sbase.appendNotes(body_node1);
    notes = sbase.getNotes();
    assertTrue(notes.getName().equals("notes"));
    assertTrue(notes.getNumChildren() == 1);
    child = notes.getChild(0);
    assertTrue(child.getName().equals("body"));
    assertTrue(child.getNumChildren() == 2);
    child1 = child.getChild(0);
    assertTrue(child1.getName().equals("p"));
    assertTrue(child1.getNumChildren() == 1);
    child1 = child1.getChild(0);
    assertTrue(child1.getCharacters().equals("This is my text"));
    assertTrue(child1.getNumChildren() == 0);
    child1 = child.getChild(1);
    assertTrue(child1.getName().equals("p"));
    assertTrue(child1.getNumChildren() == 1);
    child1 = child1.getChild(0);
    assertTrue(child1.getCharacters().equals("This is more text"));
    assertTrue(child1.getNumChildren() == 0);
    att = null;
    ns = null;
    body_triple = null;
    p_triple = null;
    body_token = null;
    p_token = null;
    p_token1 = null;
    text_token = null;
    text_token1 = null;
    p_node = null;
    text_node = null;
    body_node1 = null;
    p_node1 = null;
    text_node1 = null;
  }

  /**
   * 
   */
  @Test public void test_SBase_appendNotes8()
  {
    XMLAttributes att = new  XMLAttributes();
    XMLNamespaces ns = new  XMLNamespaces();
    ns.add("http://www.w3.org/1999/xhtml", "");
    XMLTriple body_triple = new  XMLTriple("body", "", "");
    XMLTriple p_triple = new  XMLTriple("p", "", "");
    XMLToken body_token = new  XMLNode(body_triple,att,ns);
    XMLToken p_token = new  XMLNode(p_triple,att);
    XMLToken text_token = new  XMLNode("This is my text");
    XMLNode body_node = new XMLNode(body_token);
    XMLNode p_node = new XMLNode(p_token);
    XMLNode text_node = new XMLNode(text_token);
    XMLToken p_token1 = new  XMLNode(p_triple,att,ns);
    XMLToken text_token1 = new  XMLNode("This is more text");
    XMLNode p_node1 = new XMLNode(p_token1);
    XMLNode text_node1 = new XMLNode(text_token1);
    XMLNode notes;
    XMLNode child, child1;
    p_node.addChild(text_node);
    body_node.addChild(p_node);
    p_node1.addChild(text_node1);
    sbase.setNotes(body_node);
    sbase.appendNotes(p_node1);
    notes = sbase.getNotes();
    assertTrue(notes.getName().equals("notes"));
    assertTrue(notes.getNumChildren() == 1);
    child = notes.getChild(0);
    assertTrue(child.getName().equals("body"));
    assertTrue(child.getNumChildren() == 2);
    child1 = child.getChild(0);
    assertTrue(child1.getName().equals("p"));
    assertTrue(child1.getNumChildren() == 1);
    child1 = child1.getChild(0);
    assertTrue(child1.getCharacters().equals("This is my text"));
    assertTrue(child1.getNumChildren() == 0);
    child1 = child.getChild(1);
    assertTrue(child1.getName().equals("p"));
    assertTrue(child1.getNumChildren() == 1);
    child1 = child1.getChild(0);
    assertTrue(child1.getCharacters().equals("This is more text"));
    assertTrue(child1.getNumChildren() == 0);
    att = null;
    ns = null;
    body_triple = null;
    p_triple = null;
    body_token = null;
    p_token = null;
    text_token = null;
    text_token1 = null;
    p_token1 = null;
    body_node = null;
    p_node = null;
    text_node = null;
    p_node1 = null;
    text_node1 = null;
  }

  /**
   * @throws XMLStreamException
   */
  @Test public void test_SBase_appendNotesString() throws XMLStreamException
  {
    String notes =  "<p xmlns=\"http://www.w3.org/1999/xhtml\">This is a test note </p>";
    String taggednewnotes = "<notes>\n" + "<p xmlns=\"http://www.w3.org/1999/xhtml\">This is a test note </p>" +
        "<p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes </p>\n" +
        "</notes>";
    String taggednewnotes2 = "<notes>\n" + "<p xmlns=\"http://www.w3.org/1999/xhtml\">This is a test note </p>" +
        "<p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 1</p>" +
        "<p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 2</p>\n" +
        "</notes>";
    String newnotes =  "<p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes </p>";
    String newnotes2 = "<p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 1</p>\n" + "<p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 2</p>";
    String newnotes3 = "<notes>\n" + "  <p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes </p>\n" + "</notes>";
    String newnotes4 = "<notes>\n" + "  <p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 1</p>\n" +
        "  <p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 2</p>\n" +
        "</notes>";

    sbase.setNotes(notes);
    assertTrue(sbase.isSetNotes() == true);
    sbase.appendNotes(newnotes);
    String notes1 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(stripIndentation(taggednewnotes).equals(notes1));

    sbase.setNotes(notes);
    sbase.appendNotes(newnotes2);
    String notes2 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(stripIndentation(taggednewnotes2).equals(notes2));

    sbase.setNotes(notes);
    sbase.appendNotes(newnotes3);
    String notes3 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(stripIndentation(taggednewnotes).equals(notes3));

    sbase.setNotes(notes);
    sbase.appendNotes(newnotes4);
    String notes4 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(stripIndentation(taggednewnotes2).equals(notes4));
  }

  /**
   * Appends some 'notes' with html/head/body elements on a 'notes' with html/head/body elements
   * 
   * @throws XMLStreamException
   */
  @Test public void test_SBase_appendNotesString1() throws XMLStreamException
  {
    String notes = "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "  <head>\n" +
        "    <title/>\n" +
        "  </head>\n" +
        "  <body>\n" +
        "    <p>This is a test note </p>\n" +
        "  </body>\n" +
        "</html>";
    @SuppressWarnings("unused")
    String taggednewnotesCorrectIndentation = "<notes>\n" + // ideal resulting notes
        "  <html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "    <head>\n" +
        "      <title/>\n" +
        "    </head>\n" +
        "    <body>\n" +
        "      <p>This is a test note </p>\n" +
        "      <p>This is more test notes </p>\n" +
        "    </body>\n" +
        "  </html>\n" +
        "</notes>";
    String taggednewnotes = "<notes>\n" + // no indentation to avoid problem when comparing String
        "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "<head>\n" +
        "<title/>\n" +
        "</head>\n" +
        "<body>\n" +
        "<p>This is a test note </p>\n\n" +  // TODO - jsbml add an empty line at the moment at the insertion point
        "<p>This is more test notes </p>\n" +
        "</body>\n" +
        "</html>\n" +
        "</notes>";
    String addnotes = "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "  <head>\n" +
        "    <title/>\n" +
        "  </head>\n" +
        "  <body>\n" +
        "    <p>This is more test notes </p>\n" +
        "  </body>\n" +
        "</html>";
    String addnotes2 = "<notes>\n" +
        "  <html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "    <head>\n" +
        "      <title/>\n" +
        "    </head>\n" +
        "    <body>\n" +
        "    <p>This is more test notes </p>\n" +
        "  </body>\n" +
        "  </html>\n" +
        "</notes>";

    sbase.setNotes(notes);

    sbase.appendNotes(addnotes);

    String notes1 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(stripIndentation(taggednewnotes).equals(notes1));

    sbase.setNotes(notes);
    sbase.appendNotes(addnotes2);
    String notes2 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(stripIndentation(taggednewnotes).equals(notes2));

    // TODO - test that we have top level notes element and two 'p' element inside the body ?
  }

  /**
   * Return a String where each line as been trimmed of any white spaces
   * to allow to compare String with different indentations.
   * 
   * @param notesString
   * @return
   */
  private String stripIndentation(String notesString) {
    String[] lineTokens = notesString.split("\n");
    String notesWithoutIndentation = "";

    // int i = 0;
    for (String line : lineTokens) {

      if (line.trim().length() > 0) {
        notesWithoutIndentation += line.trim();
      }

      // we remove the line return as well
      //      if (i < lineTokens.length - 1) {
      //        notesWithoutIndentation += "\n";
      //      }
      //      i++;
    }

    return notesWithoutIndentation;
  }

  /**
   * Appends some 'notes' with a body element on a 'notes' with html/head/body elements
   * 
   * @throws XMLStreamException
   */
  @Test public void test_SBase_appendNotesString2() throws XMLStreamException
  {
    String notes = "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "  <head>\n" +
        "    <title/>\n" +
        "  </head>\n" +
        "  <body>\n" +
        "    <p>This is a test note </p>\n" +
        "  </body>\n" +
        "</html>";
    String taggednewnotes = "<notes>\n" +
        "  <html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "    <head>\n" +
        "      <title/>\n" +
        "    </head>\n" +
        "    <body>\n" +
        "      <p>This is a test note </p>\n\n" +
        "      <p>This is more test notes </p>\n" +
        "    </body>\n" +
        "  </html>\n" +
        "</notes>";
    String addnotes = "<body xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "  <p>This is more test notes </p>\n" + "</body>\n";
    String addnotes2 = "<notes>\n" +
        "  <body xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "    <p>This is more test notes </p>\n" +
        "  </body>\n" +
        "</notes>";

    sbase.setNotes(notes);
    sbase.appendNotes(addnotes);
    String notes1 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(stripIndentation(taggednewnotes).equals(notes1));

    sbase.setNotes(notes);
    sbase.appendNotes(addnotes2);
    String notes2 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(stripIndentation(taggednewnotes).equals(notes2));
  }

  /**
   * Appends some 'notes' with only a p element on a 'notes' with html/head/body elements
   * 
   * @throws XMLStreamException
   */
  @Test public void test_SBase_appendNotesString3() throws XMLStreamException
  {
    String notes = "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "  <head>\n" +
        "    <title/>\n" +
        "  </head>\n" +
        "  <body>\n" +
        "    <p>This is a test note </p>\n" +
        "  </body>\n" +
        "</html>";
    String taggednewnotes = "<notes>\n" +
        "  <html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "    <head>\n" +
        "      <title/>\n" +
        "    </head>\n" +
        "    <body>\n" +
        "      <p>This is a test note </p>\n" +
        "      <p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes </p>" +
        "</body>\n" +
        "  </html>\n" +
        "</notes>";
    String taggednewnotes2 = "<notes>\n" +
        "  <html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "    <head>\n" +
        "      <title/>\n" +
        "    </head>\n" +
        "    <body>\n" +
        "      <p>This is a test note </p>\n" +
        "      <p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 1</p>" +
        "<p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 2</p>" +
        "</body>\n" +
        "  </html>\n" +
        "</notes>";
    String addnotes =  "<p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes </p>\n";
    String addnotes2 = "<p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 1</p>\n" + "<p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 2</p>";
    String addnotes3 = "<notes>\n" + "  <p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes </p>\n" + "</notes>";
    String addnotes4 = "<notes>\n" + "  <p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 1</p>\n" +
        "  <p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 2</p>\n" +
        "</notes>";
    sbase.setNotes(notes);
    sbase.appendNotes(addnotes);
    String notes1 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(stripIndentation(taggednewnotes).equals(notes1));

    sbase.setNotes(notes);
    sbase.appendNotes(addnotes2);
    String notes2 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(stripIndentation(taggednewnotes2).equals(notes2));

    sbase.setNotes(notes);
    sbase.appendNotes(addnotes3);
    String notes3 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(stripIndentation(taggednewnotes).equals(notes3));

    sbase.setNotes(notes);
    sbase.appendNotes(addnotes4);
    String notes4 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(stripIndentation(taggednewnotes2).equals(notes4));
  }

  /**
   * Appends some 'notes' with html/head/body elements on a 'notes' with only a body element
   * 
   * @throws XMLStreamException
   */
  @Test public void test_SBase_appendNotesString4() throws XMLStreamException
  {
    String notes = "<body xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "  <p>This is a test note </p>\n" + "</body>";
    String taggednewnotes = "<notes>\n" +
        "  <html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "    <head>\n" +
        "      <title/>\n" +
        "    </head>\n" +
        "    <body>\n" +
        "      <p>This is a test note </p>\n\n" +
        "      <p>This is more test notes </p>\n" +
        "    </body>\n" +
        "  </html>\n" +
        "</notes>";
    String addnotes = "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "  <head>\n" +
        "    <title/>\n" +
        "  </head>\n" +
        "  <body>\n" +
        "    <p>This is more test notes </p>\n" +
        "  </body>\n" +
        "</html>";
    String addnotes2 = "<notes>\n" +
        "  <html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "    <head>\n" +
        "      <title/>\n" +
        "    </head>\n" +
        "    <body>\n" +
        "      <p>This is more test notes </p>\n" +
        "    </body>\n" +
        "  </html>\n" +
        "</notes>";

    sbase.setNotes(notes);

    sbase.appendNotes(addnotes);
    String notes1 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(stripIndentation(taggednewnotes).equals(notes1));

    sbase.setNotes(notes);
    sbase.appendNotes(addnotes2);
    String notes2 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(stripIndentation(taggednewnotes).equals(notes2));
  }

  /**
   * Appends some 'notes' with html/head/body elements on a 'notes' with only a p element
   * 
   * @throws XMLStreamException
   */
  @Test public void test_SBase_appendNotesString5() throws XMLStreamException
  {
    String notes =  "<p xmlns=\"http://www.w3.org/1999/xhtml\">This is a test note </p>";
    String taggednewnotes = "<notes>\n" +
        "  <html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "    <head>\n" +
        "      <title/>\n" +
        "    </head>\n" +
        "    <body>\n" +
        "      <p>This is more test notes </p>\n" +
        "      <p xmlns=\"http://www.w3.org/1999/xhtml\">This is a test note </p>\n" +
        "    </body>\n" +
        "  </html>\n" +
        "</notes>";
    String addnotes = "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "  <head>\n" +
        "    <title/>\n" +
        "  </head>\n" +
        "  <body>\n" +
        "    <p>This is more test notes </p>\n" +
        "  </body>\n" +
        "</html>";
    String addnotes2 = "<notes>\n" +
        "  <html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "    <head>\n" +
        "      <title/>\n" +
        "    </head>\n" +
        "    <body>\n" +
        "      <p>This is more test notes </p>\n" +
        "    </body>\n" +
        "  </html>\n" +
        "</notes>";

    sbase.setNotes(notes);
    sbase.appendNotes(addnotes);
    String notes1 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(stripIndentation(taggednewnotes).equals(notes1));

    sbase.setNotes(notes);
    sbase.appendNotes(addnotes2);
    String notes2 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(stripIndentation(taggednewnotes).equals(notes2));
  }

  /**
   * Appends some 'notes' with a body element on a 'notes' with a body element
   * 
   * @throws XMLStreamException
   */
  @Test public void test_SBase_appendNotesString6() throws XMLStreamException
  {
    String notes = "<body xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "  <p>This is a test note </p>\n" + "</body>";
    String taggednewnotes = "<notes>\n" +
        "  <body xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "    <p>This is a test note </p>\n" +
        "    <p>This is more test notes </p>\n" +
        "  </body>\n" +
        "</notes>";
    String addnotes = "<body xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "  <p>This is more test notes </p>\n" + "</body>";
    String addnotes2 = "<notes>\n" +
        "  <body xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "    <p>This is more test notes </p>\n" +
        "  </body>\n" +
        "</notes>";

    sbase.setNotes(notes);
    sbase.appendNotes(addnotes);
    String notes1 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(stripIndentation(taggednewnotes).equals(notes1));

    sbase.setNotes(notes);
    sbase.appendNotes(addnotes2);
    String notes2 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(stripIndentation(taggednewnotes).equals(notes2));
  }

  /**
   * Appends some 'notes' with a body element on a 'notes' with only a p element
   * 
   * @throws XMLStreamException
   */
  @Test public void test_SBase_appendNotesString7() throws XMLStreamException
  {
    String notes =  "<p xmlns=\"http://www.w3.org/1999/xhtml\">This is a test note </p>";
    String taggednewnotes = "<notes>\n" +
        "  <body xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "    <p>This is more test notes </p>\n" +
        "    <p xmlns=\"http://www.w3.org/1999/xhtml\">This is a test note </p>\n" +
        "  </body>\n" +
        "</notes>";
    String addnotes = "<body xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "  <p>This is more test notes </p>\n" + "</body>";
    String addnotes2 = "<notes>\n" +
        "  <body xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "    <p>This is more test notes </p>\n" +
        "  </body>\n" +
        "</notes>";

    sbase.setNotes(notes);
    sbase.appendNotes(addnotes);
    String notes1 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(stripIndentation(taggednewnotes).equals(notes1));

    sbase.setNotes(notes);
    sbase.appendNotes(addnotes2);
    String notes2 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(stripIndentation(taggednewnotes).equals(notes2));
  }

  /**
   * Appends some 'notes' with a body element on a 'notes' with a body element
   * 
   * @throws XMLStreamException
   */
  @Test public void test_SBase_appendNotesString8() throws XMLStreamException
  {
    String notes = "<body xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "  <p>This is a test note </p>\n" + "</body>";
    String taggednewnotes = "<notes>\n" +
        "  <body xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "    <p>This is a test note </p>\n" +
        "    <p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes </p>\n" +
        "  </body>\n" +
        "</notes>";
    String taggednewnotes2 = "<notes>\n" +
        "  <body xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "    <p>This is a test note </p>\n" +
        "    <p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 1</p>\n" +
        "    <p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 2</p>\n" +
        "  </body>\n" +
        "</notes>";
    String addnotes =  "<p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes </p>";
    String addnotes2 = "<p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 1</p>\n" + "<p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 2</p>";
    String addnotes3 = "<notes>\n" +
        "  <p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes </p>\n" +
        "</notes>";
    String addnotes4 = "<notes>\n" +
        "  <p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 1</p>\n" +
        "  <p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 2</p>\n" +
        "</notes>";

    taggednewnotes = stripIndentation(taggednewnotes);
    taggednewnotes2 = stripIndentation(taggednewnotes2);

    sbase.setNotes(notes);
    sbase.appendNotes(addnotes);
    String notes1 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(taggednewnotes.equals(notes1));

    sbase.setNotes(notes);
    sbase.appendNotes(addnotes2);
    String notes2 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(taggednewnotes2.equals(notes2));

    sbase.setNotes(notes);
    sbase.appendNotes(addnotes3);
    String notes3 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(taggednewnotes.equals(notes3));

    sbase.setNotes(notes);
    sbase.appendNotes(addnotes4);
    String notes4 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(taggednewnotes2.equals(notes4));
  }


  /**
   * Appends some 'notes' with only text on a 'notes' with only text.
   * 
   * @throws XMLStreamException
   */
  @Test public void test_SBase_appendNotesString9() throws XMLStreamException
  {
    String notes = "This is a test note";
    String taggednewnotes = "<notes>\n" +
        "  <body xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "    <p>This is a test note</p>\n" +
        "    <p>This is more test notes</p>\n" +
        "  </body>\n" +
        "</notes>";
    String taggednewnotes3 = "<notes>\n" +
        "  <body xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "    <p>This is a test note</p>\n" +
        "    <p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes</p>\n" +
        "  </body>\n" +
        "</notes>";
    String taggednewnotes2 = "<notes>\n" +
        "  <body xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "    <p>This is a test note</p>\n" +
        "    <p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 1</p>\n" +
        "    <p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 2</p>\n" +
        "  </body>\n" +
        "</notes>";
    String addnotes =  "This is more test notes";
    String addnotes2 = "<p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 1</p>\n" + "<p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 2</p>";
    String addnotes3 = "<notes>\n" +
        "  <p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes</p>\n" +
        "</notes>";
    String addnotes4 = "<notes>\n" +
        "  <p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 1</p>\n" +
        "  <p xmlns=\"http://www.w3.org/1999/xhtml\">This is more test notes 2</p>\n" +
        "</notes>";

    taggednewnotes = stripIndentation(taggednewnotes);
    taggednewnotes2 = stripIndentation(taggednewnotes2);
    taggednewnotes3 = stripIndentation(taggednewnotes3);

    sbase.setNotes(notes);
    sbase.appendNotes(addnotes);
    String notes1 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(taggednewnotes.equals(notes1));

    sbase.setNotes(notes);
    sbase.appendNotes(addnotes2);
    String notes2 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(taggednewnotes2.equals(notes2));

    sbase.setNotes(notes);
    sbase.appendNotes(addnotes3);
    String notes3 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    //    System.out.println("Notes1:" + notes3);
    //    System.out.println("Notes2:" + taggednewnotes3);
    //
    assertTrue(taggednewnotes3.equals(notes3));

    sbase.setNotes(notes);
    sbase.appendNotes(addnotes4);
    String notes4 = stripIndentation(sbase.getNotesString());
    assertTrue(sbase.isSetNotes() == true);

    assertTrue(taggednewnotes2.equals(notes4));
  }

  /* // DIFF - we don't have the sbase.getResourceBiologicalQualifier(String) method
  @Test public void test_SBase_getQualifiersFromResources()
  {
    CVTerm cv = new  CVTerm(CVTerm.Qualifier.BQB_ENCODES);

    cv.addResource("foo");
    sbase.setMetaId("sbase1");
    sbase.addCVTerm(cv);
    assertTrue(sbase.getResourceBiologicalQualifier("foo") == CVTerm.Qualifier.BQB_ENCODES);

    CVTerm cv1 = new  CVTerm(libsbml.MODEL_QUALIFIER);
    cv1.setModelQualifierType(libsbml.BQM_IS);
    cv1.addResource("bar");
    sbase.addCVTerm(cv1);
    assertTrue(sbase.getResourceModelQualifier("bar") == libsbml.BQM_IS);
    cv = null;
    cv1 = null;
  }
   */

  /*
  // TODO - adapt later
  @Test public void test_SBase_setAnnotation()
  {
    XMLToken token;
    XMLNode node;
    token = new  XMLNode("This is a test note");
    node = new XMLNode(token);
    sbase.setAnnotation(node);
    assertTrue(sbase.isSetAnnotation() == true);
    XMLNode t1 = sbase.getAnnotation();
    assertTrue(t1.getNumChildren() == 1);
    assertTrue(t1.getChild(0).getCharacters().equals("This is a test note"));
    if (sbase.getAnnotation() == node);
    {
    }
    sbase.setAnnotation(sbase.getAnnotation());
    assertTrue(sbase.getAnnotation().getChild(0).getCharacters().equals("This is a test note"));
    sbase.setAnnotation((XMLNode)null);
    assertTrue(sbase.isSetAnnotation() == false);
    if (sbase.getAnnotation() != null);
    {
    }
    sbase.setAnnotation(node);
    assertTrue(sbase.isSetAnnotation() == true);
    sbase.unsetAnnotation();
    assertTrue(sbase.isSetAnnotation() == false);
    token = new  XMLNode("(CR) &#0168; &#x00a8; &#x00A8; (NOT CR) &#; &#x; &#00a8; &#0168 &#x00a8");
    node = new XMLNode(token);
    sbase.setAnnotation(node);
    t1 = sbase.getAnnotation();
    assertTrue(t1.getNumChildren() == 1);
    String s = t1.getChild(0).toXMLString();
    String expected =  "(CR) &#0168; &#x00a8; &#x00A8; (NOT CR) &amp;#; &amp;#x; &amp;#00a8; &amp;#0168 &amp;#x00a8";
    assertTrue(s.equals(expected));
    token = new  XMLNode("& ' > < \" &amp; &apos; &gt; &lt; &quot;");
    node = new XMLNode(token);
    sbase.setAnnotation(node);
    t1 = sbase.getAnnotation();
    assertTrue(t1.getNumChildren() == 1);
    String s2 = t1.getChild(0).toXMLString();
    String expected2 =  "&amp; &apos; &gt; &lt; &quot; &amp; &apos; &gt; &lt; &quot;";
    assertTrue(s2.equals(expected2));
    token = null;
    node = null;
  }

  @Test public void test_SBase_setAnnotationString()
  {
    String annotation =  "This is a test note";
    String taggedannotation =  "<annotation>This is a test note</annotation>";
    sbase.setAnnotation(annotation);
    assertTrue(sbase.isSetAnnotation() == true);
    if (!sbase.getAnnotationString().equals(taggedannotation));
    {
    }
    XMLNode t1 = sbase.getAnnotation();
    assertTrue(t1.getNumChildren() == 1);
    assertTrue(t1.getChild(0).getCharacters().equals("This is a test note"));
    sbase.setAnnotation(sbase.getAnnotationString());
    t1 = sbase.getAnnotation();
    assertTrue(t1.getNumChildren() == 1);
    String chars = sbase.getAnnotationString();
    assertTrue(chars.equals(taggedannotation));
    sbase.setAnnotation("");
    assertTrue(sbase.isSetAnnotation() == false);
    if (sbase.getAnnotationString() != null);
    {
    }
    sbase.setAnnotation(taggedannotation);
    assertTrue(sbase.isSetAnnotation() == true);
    if (!sbase.getAnnotationString().equals(taggedannotation));
    {
    }
    t1 = sbase.getAnnotation();
    assertTrue(t1.getNumChildren() == 1);
    XMLNode t2 = t1.getChild(0);
    assertTrue(t2.getCharacters().equals("This is a test note"));
  }
   */

  /**
   * 
   */
  @Test public void test_SBase_setMetaId()
  {
    String metaid =  "x12345";
    sbase.setMetaId(metaid);
    assertTrue(sbase.getMetaId().equals(metaid));
    assertEquals(true, sbase.isSetMetaId());
    if (sbase.getMetaId() == metaid) {
      ;
    }
    {
    }
    sbase.setMetaId(sbase.getMetaId());
    assertTrue(sbase.getMetaId().equals(metaid));
    sbase.unsetMetaId(); // DIFF - SBase.setMetaId("") does not unset the metaid
    assertEquals(false, sbase.isSetMetaId());
    if (sbase.getMetaId() != null) {
      ;
    }
    {
    }
  }

  /**
   * @throws XMLStreamException
   */
  @Test public void test_SBase_setNotes() throws XMLStreamException
  {
    SBase c = new Model(1,2);
    XMLToken token;
    XMLNode node;
    token = new  XMLNode("This is a test note");
    node = new XMLNode(token);
    c.setNotes(node);
    assertTrue(c.isSetNotes() == true);
    if (c.getNotes() == node) {
      ;
    }
    {
    }
    XMLNode t1 = c.getNotes();
    assertTrue(t1.getNumChildren() == 1);
    assertTrue(t1.getChild(0).getCharacters().equals("This is a test note"));
    c.setNotes(c.getNotes());
    t1 = c.getNotes();
    assertTrue(t1.getNumChildren() == 1);
    String chars = t1.getChild(0).getCharacters();
    assertTrue(chars.equals("This is a test note"));
    c.setNotes((XMLNode)null);
    assertTrue(c.isSetNotes() == false);
    if (c.getNotes() != null) {
      ;
    }
    {
    }
    c.setNotes(node);
    assertTrue(c.isSetNotes() == true);
    token = new  XMLNode("(CR) &#0168; &#x00a8; &#x00A8; (NOT CR) &#; &#x; &#00a8; &#0168 &#x00a8");
    node = new XMLNode(token);
    c.setNotes(node);
    t1 = c.getNotes();
    assertTrue(t1.getNumChildren() == 1);
    String s = t1.getChild(0).toXMLString();
    String expected =  "(CR) &#0168; &#x00a8; &#x00A8; (NOT CR) &amp;#; &amp;#x; &amp;#00a8; &amp;#0168 &amp;#x00a8";
    assertTrue(s.equals(expected));
    token = new  XMLNode("& ' > < \" &amp; &apos; &gt; &lt; &quot;");
    node = new XMLNode(token);
    c.setNotes(node);
    t1 = c.getNotes();
    assertTrue(t1.getNumChildren() == 1);
    String s2 = t1.getChild(0).toXMLString();
    String expected2 =  "&amp; &apos; &gt; &lt; &quot; &amp; &apos; &gt; &lt; &quot;";
    assertTrue(s2.equals(expected2));
    token = null;
    node = null;
  }

  /**
   * @throws XMLStreamException
   */
  @Test public void test_SBase_setNotesString() throws XMLStreamException
  {
    SBase c = new Model(1,2);
    String notes =  "This is a test note";
    String taggednotes =  "<notes>This is a test note</notes>";
    c.setNotes(notes);
    assertTrue(c.isSetNotes() == true);
    if (!c.getNotesString().equals(taggednotes)) {
      ;
    }
    {
    }
    XMLNode t1 = c.getNotes();
    assertTrue(t1.getNumChildren() == 1);
    XMLNode t2 = t1.getChild(0);
    assertTrue(t2.getCharacters().equals("This is a test note"));
    c.setNotes(c.getNotesString());
    t1 = c.getNotes();
    assertTrue(t1.getNumChildren() == 1);
    String chars = c.getNotesString();
    assertTrue(chars.equals(taggednotes));
    c.setNotes("");
    assertTrue(c.isSetNotes() == false);
    if (c.getNotesString() != null) {
      ;
    }
    {
    }
    c.setNotes(taggednotes);
    assertTrue(c.isSetNotes() == true);
    if (!c.getNotesString().equals(taggednotes)) {
      ;
    }
    {
    }
    t1 = c.getNotes();
    assertTrue(t1.getNumChildren() == 1);
    t2 = t1.getChild(0);
    assertTrue(t2.getCharacters().equals("This is a test note"));
  }

  /**
   * @throws XMLStreamException
   */
  @Test public void test_SBase_setNotesString_l3() throws XMLStreamException
  {
    SBase c = new Model(3,1);
    String notes =  "This is a test note";
    //    String taggednotes = "<notes>\n" + "  <p xmlns=\"http://www.w3.org/1999/xhtml\">This is a test note</p>\n" + "</notes>";
    c.setNotes(notes);
    assertTrue(c.isSetNotes() == false);
  }

  /**
   * @throws XMLStreamException
   */
  @Test public void test_SBase_setNotesString_l3_addMarkup() throws XMLStreamException
  {
    SBase c = new Model(3,1);
    String notes =  "This is a test note";
    String taggednotes = "<notes>\n" + "  <p xmlns=\"http://www.w3.org/1999/xhtml\">This is a test note</p>\n" + "</notes>";
    c.setNotes(notes); // DIFF - we don't have the method SBase.setNotes(String, boolean)
    assertTrue(c.isSetNotes() == true);
    if (!c.getNotesString().equals(taggednotes)) {
      ;
    }
    {
    }
    XMLNode t1 = c.getNotes();
    assertTrue(t1.getNumChildren() == 1);
    XMLNode t2 = t1.getChild(0);
    assertTrue(t2.getNumChildren() == 1);
    XMLNode t3 = t2.getChild(0);
    assertTrue(t3.getCharacters().equals("This is a test note"));
    c.setNotes(c.getNotesString());
    t1 = c.getNotes();
    assertTrue(t1.getNumChildren() == 1);
    String chars = c.getNotesString();
    assertTrue(chars.equals(taggednotes));
    c.setNotes("");
    assertTrue(c.isSetNotes() == false);
    if (c.getNotesString() != null) {
      ;
    }
    {
    }
    c.setNotes(taggednotes);
    assertTrue(c.isSetNotes() == true);
    if (!c.getNotesString().equals(taggednotes)) {
      ;
    }
    {
    }
    t1 = c.getNotes();
    assertTrue(t1.getNumChildren() == 1);
    t2 = t1.getChild(0);
    assertTrue(t2.getNumChildren() == 1);
    t3 = t2.getChild(0);
    assertTrue(t3.getCharacters().equals("This is a test note"));
  }

  /*
  // TODO - adapt later
  @Test public void test_SBase_unsetAnnotationWithCVTerms()
  {
    CVTerm cv;
    String annt = "<annotation>\n" +
    "  <test:test xmlns:test=\"http://test.org/test\">this is a test node</test:test>\n" +
    "</annotation>";
    String annt_with_cvterm = "<annotation>\n" +
    "  <test:test xmlns:test=\"http://test.org/test\">this is a test node</test:test>\n" +
    "  <rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" " +
    "xmlns:dc=\"http://purl.org/dc/elements/1.1/\" " +
    "xmlns:dcterms=\"http://purl.org/dc/terms/\" " +
    "xmlns:vCard=\"http://www.w3.org/2001/vcard-rdf/3.0#\" " +
    "xmlns:bqbiol=\"http://biomodels.net/biology-qualifiers/\" " +
    "xmlns:bqmodel=\"http://biomodels.net/model-qualifiers/\">\n" +
    "    <rdf:Description rdf:about=\"#_000001\">\n" +
    "      <bqbiol:is>\n" +
    "        <rdf:Bag>\n" +
    "          <rdf:li rdf:resource=\"http://www.geneontology.org/#GO:0005895\"/>\n" +
    "        </rdf:Bag>\n" +
    "      </bqbiol:is>\n" +
    "    </rdf:Description>\n" +
    "  </rdf:RDF>\n" +
    "</annotation>";
    sbase.setAnnotation(annt);
    assertTrue(sbase.isSetAnnotation() == true);
    assertTrue(sbase.getAnnotationString().equals(annt));
    sbase.unsetAnnotation();
    assertTrue(sbase.isSetAnnotation() == false);
    assertTrue(sbase.getAnnotation() == null);
    sbase.setAnnotation(annt);
    sbase.setMetaId("_000001");
    cv = new  CVTerm(libsbml.BIOLOGICAL_QUALIFIER);
    cv.setBiologicalQualifierType(libsbml.BQB_IS);
    cv.addResource("http://www.geneontology.org/#GO:0005895");
    sbase.addCVTerm(cv);
    assertTrue(sbase.isSetAnnotation() == true);
    assertTrue(sbase.getAnnotationString().equals(annt_with_cvterm));
    sbase.unsetAnnotation();
    assertTrue(sbase.isSetAnnotation() == false);
    assertTrue(sbase.getAnnotation() == null);
    cv = null;
  }
   */

  /*
  // TODO - adapt later
  @Test public void test_SBase_unsetAnnotationWithModelHistory()
  {
    ModelHistory h = new  ModelHistory();
    ModelCreator c = new  ModelCreator();
    Date dc;
    Date dm;
    String annt = "<annotation>\n" +
    "  <test:test xmlns:test=\"http://test.org/test\">this is a test node</test:test>\n" +
    "</annotation>";
    String annt_with_modelhistory = "<annotation>\n" +
    "  <test:test xmlns:test=\"http://test.org/test\">this is a test node</test:test>\n" +
    "  <rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" " +
    "xmlns:dc=\"http://purl.org/dc/elements/1.1/\" " +
    "xmlns:dcterms=\"http://purl.org/dc/terms/\" " +
    "xmlns:vCard=\"http://www.w3.org/2001/vcard-rdf/3.0#\" " +
    "xmlns:bqbiol=\"http://biomodels.net/biology-qualifiers/\" " +
    "xmlns:bqmodel=\"http://biomodels.net/model-qualifiers/\">\n" +
    "    <rdf:Description rdf:about=\"#_000001\">\n" +
    "      <dc:creator>\n" +
    "        <rdf:Bag>\n" +
    "          <rdf:li rdf:parseType=\"Resource\">\n" +
    "            <vCard:N rdf:parseType=\"Resource\">\n" +
    "              <vCard:Family>Keating</vCard:Family>\n" +
    "              <vCard:Given>Sarah</vCard:Given>\n" +
    "            </vCard:N>\n" +
    "            <vCard:EMAIL>sbml-team@caltech.edu</vCard:EMAIL>\n" +
    "          </rdf:li>\n" +
    "        </rdf:Bag>\n" +
    "      </dc:creator>\n" +
    "      <dcterms:created rdf:parseType=\"Resource\">\n" +
    "        <dcterms:W3CDTF>2005-12-29T12:15:45+02:00</dcterms:W3CDTF>\n" +
    "      </dcterms:created>\n" +
    "      <dcterms:modified rdf:parseType=\"Resource\">\n" +
    "        <dcterms:W3CDTF>2005-12-30T12:15:45+02:00</dcterms:W3CDTF>\n" +
    "      </dcterms:modified>\n" +
    "    </rdf:Description>\n" +
    "  </rdf:RDF>\n" +
    "</annotation>";
    sbase.setAnnotation(annt);
    assertTrue(sbase.isSetAnnotation() == true);
    assertTrue(sbase.getAnnotationString().equals(annt));
    sbase.unsetAnnotation();
    assertTrue(sbase.isSetAnnotation() == false);
    assertTrue(sbase.getAnnotation() == null);
    sbase.setAnnotation(annt);
    sbase.setMetaId("_000001");
    c.setFamilyName("Keating");
    c.setGivenName("Sarah");
    c.setEmail("sbml-team@caltech.edu");
    h.addCreator(c);
    dc = new  Date(2005,12,29,12,15,45,1,2,0);
    h.setCreatedDate(dc);
    dm = new  Date(2005,12,30,12,15,45,1,2,0);
    h.setModifiedDate(dm);
    sbase.setModelHistory(h);
    assertTrue(sbase.isSetAnnotation() == true);
    assertTrue(sbase.getAnnotationString().equals(annt_with_modelhistory));
    sbase.unsetAnnotation();
    assertTrue(sbase.isSetAnnotation() == false);
    assertTrue(sbase.getAnnotation() == null);
    c = null;
    h = null;
  }
   */

  /**
   * 
   */
  @Test public void test_SBase_unsetCVTerms()
  {
    CVTerm cv = new  CVTerm(CVTerm.Qualifier.BQB_ENCODES);

    cv.addResource("foo");
    sbase.setMetaId("sbase1");
    sbase.addCVTerm(cv);

    CVTerm cv1 = new  CVTerm(CVTerm.Qualifier.BQB_IS);
    cv1.addResource("bar");
    sbase.addCVTerm(cv1);

    CVTerm cv2 = new  CVTerm(CVTerm.Qualifier.BQB_IS);
    cv2.addResource("bar1");
    sbase.addCVTerm(cv2);

    CVTerm cv4 = new  CVTerm(CVTerm.Qualifier.BQB_IS);
    cv4.addResource("bar1");
    sbase.addCVTerm(cv4);

    assertTrue(sbase.getNumCVTerms() == 4);
    sbase.unsetCVTerms();
    assertTrue(sbase.getNumCVTerms() == 0);
    assertTrue(sbase.getCVTerms().size() == 0); // DIFF - SBase.getCVTerms() never return null
    cv = null;
    cv2 = null;
    cv1 = null;
    cv4 = null;
  }

}


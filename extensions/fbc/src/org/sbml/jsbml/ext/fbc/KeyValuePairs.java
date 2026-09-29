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
package org.sbml.jsbml.ext.fbc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.xml.XMLAttributes;
import org.sbml.jsbml.xml.XMLNamespaces;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.XMLTriple;

/**
 * Reads and writes the key-value pairs of FBC version 3: the
 * {@code listOfKeyValuePairs} element in the namespace
 * {@link FBCConstants#KEY_VALUE_PAIR_NAMESPACE} in the annotation of any
 * {@link SBase}.
 * 
 * <p>The pairs are not copied into the SBML elements: the non RDF annotation
 * ({@link Annotation#getNonRDFannotation()}), which JSBML reads and writes
 * unchanged, is the only place they are stored in, so there is nothing to
 * keep in sync. {@link #get(SBase)} parses the pairs from the annotation,
 * {@link #set(SBase, List)} replaces the {@code listOfKeyValuePairs} in the
 * annotation with the XML libSBML writes:</p>
 * 
 * <pre>
 * &lt;listOfKeyValuePairs xmlns="http://sbml.org/fbc/keyvaluepair"&gt;
 *   &lt;keyValuePair id=".." name=".." key=".." value=".." uri=".."/&gt;
 * &lt;/listOfKeyValuePairs&gt;
 * </pre>
 * 
 * @since 1.7
 * @see KeyValuePair
 */
public final class KeyValuePairs {

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(KeyValuePairs.class);

  /**
   * The number of spaces {@link org.sbml.jsbml.SBMLWriter} indents an XML
   * element by default.
   */
  private static final int INDENT = 2;

  /**
   * The attributes of a {@code keyValuePair} in the order libSBML writes them.
   */
  private static final String[] ATTRIBUTES = {"id", "name", FBCConstants.key, FBCConstants.value, FBCConstants.uri};

  /**
   * No instances, only static methods.
   */
  private KeyValuePairs() {
  }

  /**
   * Returns the key-value pairs of the given element, in document order.
   * 
   * <p>A {@code keyValuePair} without the required key is skipped (with a
   * warning), since it is no key-value pair.</p>
   * 
   * @param sbase an SBML element.
   * @return the key-value pairs of the {@code listOfKeyValuePairs} in the
   *         annotation of the element, an empty list if the element has no
   *         annotation or no {@code listOfKeyValuePairs} in it.
   */
  public static List<KeyValuePair> get(SBase sbase) {
    XMLNode list = getListOfKeyValuePairs(sbase);
    if (list == null) {
      return Collections.emptyList();
    }
    List<KeyValuePair> pairs = new ArrayList<KeyValuePair>();
    for (int i = 0; i < list.getChildCount(); i++) {
      XMLNode child = list.getChildAt(i);
      if (!child.isElement() || !child.getName().equals(FBCConstants.keyValuePair)
          || !isKeyValuePairNamespace(child.getURI())) {
        continue;
      }
      String key = attribute(child, FBCConstants.key);
      if (key == null) {
        logger.warn("A keyValuePair without the required key is ignored.");
        continue;
      }
      pairs.add(new KeyValuePair(key, attribute(child, FBCConstants.value),
        attribute(child, FBCConstants.uri), attribute(child, "id"), attribute(child, "name")));
    }
    return pairs;
  }

  /**
   * Sets the key-value pairs of the given element: replaces the
   * {@code listOfKeyValuePairs} of its annotation (at the same position), or
   * adds one if there is none. An empty list (or {@code null}) removes the
   * {@code listOfKeyValuePairs}, and an annotation without other content. The
   * rest of the annotation is left unchanged.
   * 
   * @param sbase an SBML element.
   * @param pairs the key-value pairs, can be empty or {@code null}.
   */
  public static void set(SBase sbase, List<KeyValuePair> pairs) {
    XMLNode annotation = sbase.isSetAnnotation() ? sbase.getAnnotation().getNonRDFannotation() : null;
    XMLNode oldList = getListOfKeyValuePairs(sbase);

    if ((pairs == null) || pairs.isEmpty()) {
      if (oldList != null) {
        int index = indexOf(annotation, oldList);
        annotation.removeChild(index);
        // the indentation before the list
        if ((index > 0) && isWhitespace(annotation.getChildAt(index - 1))) {
          annotation.removeChild(index - 1);
        }
        if (!hasElement(annotation)) {
          sbase.getAnnotation().unsetNonRDFannotation();
          if (sbase.getAnnotation().isEmpty()) {
            sbase.unsetAnnotation();
          }
        }
      }
      return;
    }

    // the indentation of the annotation element, as SBMLWriter indents the
    // element, so that the written XML is indented like the rest
    int depth = depth(sbase) + 1;
    XMLNode list = createListOfKeyValuePairs(pairs, depth + 1);
    if (oldList != null) {
      int index = indexOf(annotation, oldList);
      annotation.removeChild(index);
      annotation.insertChild(index, list);
    } else if (annotation != null) {
      // before the whitespace that indents the end tag of the annotation
      int index = annotation.getChildCount();
      if ((index > 0) && isWhitespace(annotation.getChildAt(index - 1))) {
        index--;
      } else {
        annotation.addChild(new XMLNode(indentation(depth)));
      }
      annotation.insertChild(index, list);
      annotation.insertChild(index, new XMLNode(indentation(depth + 1)));
    } else {
      XMLNode newAnnotation = new XMLNode(new XMLTriple("annotation", null, null), new XMLAttributes());
      newAnnotation.addChild(new XMLNode(indentation(depth + 1)));
      newAnnotation.addChild(list);
      newAnnotation.addChild(new XMLNode(indentation(depth)));
      sbase.getAnnotation().setNonRDFAnnotation(newAnnotation);
    }
  }

  /**
   * @param sbase an SBML element.
   * @return the depth of the element in the XML document (0 for the
   *         {@code sbml} element, 1 for the {@code model}).
   */
  private static int depth(SBase sbase) {
    int depth = 0;
    for (TreeNode parent = sbase.getParent(); parent != null; parent = parent.getParent()) {
      depth++;
    }
    return depth;
  }

  /**
   * @param depth the depth of an element in the XML document.
   * @return a line break and the indentation of an element at the given
   *         depth, as {@link org.sbml.jsbml.SBMLWriter} indents it by default.
   */
  private static String indentation(int depth) {
    StringBuilder indentation = new StringBuilder("\n");
    for (int i = 0; i < depth * INDENT; i++) {
      indentation.append(' ');
    }
    return indentation.toString();
  }

  /**
   * Creates the {@code listOfKeyValuePairs} element of the given pairs.
   * 
   * @param pairs the key-value pairs.
   * @param depth the depth of the element in the XML document.
   * @return the {@code listOfKeyValuePairs} element.
   */
  private static XMLNode createListOfKeyValuePairs(List<KeyValuePair> pairs, int depth) {
    XMLNamespaces namespaces = new XMLNamespaces();
    namespaces.add(FBCConstants.KEY_VALUE_PAIR_NAMESPACE, "");
    XMLNode list = new XMLNode(new XMLTriple(FBCConstants.listOfKeyValuePairs,
      FBCConstants.KEY_VALUE_PAIR_NAMESPACE, ""), new XMLAttributes(), namespaces);
    for (KeyValuePair pair : pairs) {
      XMLAttributes attributes = new XMLAttributes();
      String[] values = {pair.getId(), pair.getName(), pair.getKey(), pair.getValue(), pair.getUri()};
      for (int i = 0; i < ATTRIBUTES.length; i++) {
        if (values[i] != null) {
          attributes.add(ATTRIBUTES[i], values[i], "", "");
        }
      }
      list.addChild(new XMLNode(indentation(depth + 1)));
      list.addChild(new XMLNode(new XMLTriple(FBCConstants.keyValuePair,
        FBCConstants.KEY_VALUE_PAIR_NAMESPACE, ""), attributes));
    }
    list.addChild(new XMLNode(indentation(depth)));
    return list;
  }

  /**
   * @param sbase an SBML element.
   * @return the {@code listOfKeyValuePairs} element of the non RDF annotation
   *         of the element, {@code null} if there is none.
   */
  private static XMLNode getListOfKeyValuePairs(SBase sbase) {
    if (!sbase.isSetAnnotation()) {
      return null;
    }
    XMLNode annotation = sbase.getAnnotation().getNonRDFannotation();
    if (annotation == null) {
      return null;
    }
    return annotation.getChildElement(FBCConstants.listOfKeyValuePairs, FBCConstants.KEY_VALUE_PAIR_NAMESPACE);
  }

  /**
   * @param uri the namespace URI of an element of the {@code listOfKeyValuePairs}.
   * @return whether it is the key-value pair namespace (or not set, as for
   *         elements created without namespace).
   */
  private static boolean isKeyValuePairNamespace(String uri) {
    return (uri == null) || uri.isEmpty() || uri.equals(FBCConstants.KEY_VALUE_PAIR_NAMESPACE);
  }

  /**
   * @param node a {@code keyValuePair} element.
   * @param name the name of an attribute.
   * @return the value of the attribute without prefix, {@code null} if it is
   *         not set.
   */
  private static String attribute(XMLNode node, String name) {
    for (int i = 0; i < node.getAttributesLength(); i++) {
      if (node.getAttrName(i).equals(name) && isKeyValuePairNamespace(node.getAttrURI(i))) {
        return node.getAttrValue(i);
      }
    }
    return null;
  }

  /**
   * @param parent an XML element.
   * @param child a child of the element.
   * @return the index of the child (the same instance) in the element.
   */
  private static int indexOf(XMLNode parent, XMLNode child) {
    for (int i = 0; i < parent.getChildCount(); i++) {
      if (parent.getChildAt(i) == child) {
        return i;
      }
    }
    throw new IllegalStateException("The node is no child of the annotation.");
  }

  /**
   * @param node an XML node.
   * @return whether the node is text that consists of whitespace only.
   */
  private static boolean isWhitespace(XMLNode node) {
    return node.isText() && node.getCharacters().trim().isEmpty();
  }

  /**
   * @param node an XML element.
   * @return whether the element has an element as child.
   */
  private static boolean hasElement(XMLNode node) {
    for (int i = 0; i < node.getChildCount(); i++) {
      if (node.getChildAt(i).isElement()) {
        return true;
      }
    }
    return false;
  }

}

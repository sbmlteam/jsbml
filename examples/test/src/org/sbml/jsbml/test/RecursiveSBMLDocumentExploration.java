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
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBase;


/**
 * @author Andreas Dr&auml;ger
 * @since 1.1
 */
public class RecursiveSBMLDocumentExploration {

  /**
   * @param args must be the absolute or relative path to one file.
   * @throws IOException
   * @throws XMLStreamException
   */
  public static void main(String[] args) throws XMLStreamException, IOException {
    Set<SBase> setOfSBase = traverse(SBMLReader.read(new File(args[0])));
    for (SBase sbase : setOfSBase) {
      System.out.println(sbase.toString());
    }
  }

  /**
   * Recursively traverses all children of the given {@link SBase}.
   * 
   * @param sbase
   * @return
   */
  public static Set<SBase> traverse(SBase sbase) {
    Set<SBase> setOfSBase = new HashSet<SBase>();
    traverse(sbase, setOfSBase);
    return setOfSBase;
  }

  /**
   * More efficient private method that does not create new sets each time.
   * 
   * @param sbase
   * @param setOfSBase
   */
  private static void traverse(SBase sbase, Set<SBase> setOfSBase) {
    setOfSBase.add(sbase);
    for (int i = 0; i < sbase.getChildCount(); i++) {
      TreeNode child = sbase.getChildAt(i);
      if (child instanceof SBase) {
        traverse((SBase) child, setOfSBase);
      }
    }
  }

}

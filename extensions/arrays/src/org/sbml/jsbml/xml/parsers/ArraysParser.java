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
 * 6. The University of Utah, Salt Lake City, UT, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.xml.parsers;

import java.util.List;

import org.apache.log4j.Logger;
import org.mangosdk.spi.ProviderFor;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.ASTNodePlugin;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.arrays.Index;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * @author Leandro Watanabe
 * @since 1.0
 */
@ProviderFor(ReadingParser.class)
public class ArraysParser extends AbstractReaderWriter implements PackageParser {

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(ArraysParser.class);

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#getNamespaces()
   */
  @Override
  public List<String> getNamespaces() {
    return ArraysConstants.namespaces;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#createPluginFor(org.sbml.jsbml.SBase)
   */
  @Override
  public SBasePlugin createPluginFor(SBase sbase) {
    if (sbase != null) {
      //if (sbase instanceof Model) {
      return new ArraysSBasePlugin(sbase);
      //}
    }
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#getNamespaceFor(int, int, int)
   */
  @Override
  public String getNamespaceFor(int level, int version, int packageVersion) {
    if (level == 3 && version == 1 && packageVersion == 1) {
      return ArraysConstants.namespaceURI_L3V1V1;
    }
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#getPackageNamespaces()
   */
  @Override
  public List<String> getPackageNamespaces() {
    return getNamespaces();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#getPackageName()
   */
  @Override
  public String getPackageName() {
    return ArraysConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#isRequired()
   */
  @Override
  public boolean isRequired() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#processStartElement(java.lang.String, java.lang.String, java.lang.String, boolean, boolean, java.lang.Object)
   */
  @Override
  public Object processStartElement(String elementName, String uri,
    String prefix, boolean hasAttributes, boolean hasNamespaces,
    Object contextObject) {

    if (contextObject instanceof ListOf<?>)
    {
      @SuppressWarnings("unchecked")
      ListOf<SBase> listOf = (ListOf<SBase>) contextObject;
      SBase sBase = listOf.getParent();
      ArraysSBasePlugin arraysPlugin = (ArraysSBasePlugin) sBase.getPlugin(ArraysConstants.shortLabel);

      if (elementName.equals("dimension")) {
        Dimension dimension = new Dimension();
        arraysPlugin.addDimension(dimension);
        return dimension;
      }
      else if (elementName.equals("index")) {
        Index index = new Index();
        arraysPlugin.addIndex(index);
        return index;
      }
    }

    else if (contextObject instanceof SBase)
    {
      SBase sBase = (SBase) contextObject;

      if (elementName.equals("listOfDimensions")) {

        ArraysSBasePlugin arraysPlugin = (ArraysSBasePlugin) sBase.getPlugin(ArraysConstants.shortLabel);

        return arraysPlugin.getListOfDimensions();
      }
      else if (elementName.equals("listOfIndices")) {
        ArraysSBasePlugin arraysPlugin = (ArraysSBasePlugin) sBase.getPlugin(ArraysConstants.shortLabel);

        return arraysPlugin.getListOfIndices();
      }
    }
    return contextObject;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getShortLabel()
   */
  @Override
  public String getShortLabel() {
    return ArraysConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getNamespaceURI()
   */
  @Override
  public String getNamespaceURI() {
    return ArraysConstants.namespaceURI;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#writeElement(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
   */
  @Override
  public void writeElement(SBMLObjectForXML xmlObject, Object sbmlElementToWrite) {

    super.writeElement(xmlObject, sbmlElementToWrite);

    String name = xmlObject.getName();

    if (name.equals("listOfIndexs")) {
      xmlObject.setName(ArraysConstants.listOfIndices);
    }
  }
  
  @Override
  public ASTNodePlugin createPluginFor(ASTNode astNode) {
    // This package does not extends ASTNode
    return null;
  }


}

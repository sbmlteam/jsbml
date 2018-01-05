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
package org.sbml.jsbml.xml.parsers;

import static org.sbml.jsbml.ext.comp.CompConstants.namespaceURI;
import static org.sbml.jsbml.ext.comp.CompConstants.shortLabel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.mangosdk.spi.ProviderFor;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.ASTNodePlugin;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.comp.CompConstants;
import org.sbml.jsbml.ext.comp.CompModelPlugin;
import org.sbml.jsbml.ext.comp.CompSBMLDocumentPlugin;
import org.sbml.jsbml.ext.comp.CompSBasePlugin;
import org.sbml.jsbml.ext.comp.SBaseRef;
import org.sbml.jsbml.ext.comp.Submodel;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * A CompParser is used to parse the comp extension package elements and
 * attributes. The namespaceURI URI of this parser is
 * "http://www.sbml.org/sbml/level3/version1/comp/version1". This parser is
 * able to read and write elements of the comp package (implements
 * ReadingParser and WritingParser).
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
@ProviderFor(ReadingParser.class)
public class CompParser extends AbstractReaderWriter implements PackageParser {

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(CompParser.class);

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getNamespaceURI()
   */
  @Override
  public String getNamespaceURI() {
    // TODO - use the SBMLDocument to know which namespace to return !!!
    return namespaceURI;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getShortLabel()
   */
  @Override
  public String getShortLabel() {
    return shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.WritingParser#getListOfSBMLElementsToWrite(Object sbase)
   */
  @Override
  public List<Object> getListOfSBMLElementsToWrite(Object treeNode) {

    if (logger.isDebugEnabled()) {
      logger.debug("getListOfSBMLElementsToWrite: " + treeNode.getClass().getCanonicalName());
    }

    List<Object> listOfElementsToWrite = new ArrayList<Object>();

    // test if this treeNode is an extended SBase.
    if (treeNode instanceof SBase && (! (treeNode instanceof Model)) && ((SBase) treeNode).getExtension(getNamespaceURI()) != null) {
      SBasePlugin sbasePlugin = ((SBase) treeNode).getExtension(getNamespaceURI());

      if (sbasePlugin != null) {
        listOfElementsToWrite = super.getListOfSBMLElementsToWrite(sbasePlugin);
        logger.debug("getListOfSBMLElementsToWrite: nb children = " + sbasePlugin.getChildCount());
      }
    } else {
      listOfElementsToWrite = super.getListOfSBMLElementsToWrite(treeNode);
    }

    if (treeNode instanceof Model && listOfElementsToWrite != null) {
      String sbmlNamespace = JSBML.getNamespaceFrom(((Model) treeNode).getLevel(), ((Model) treeNode).getVersion());

      for (Object child : listOfElementsToWrite) {
        if (child instanceof AbstractSBase && ((AbstractSBase) child).getNamespace() == null) {
          AbstractSBase sbase = (AbstractSBase) child;
          logger.debug("Found one suspect Model child: " + sbase.getElementName() + ". Setting the SBML namespace to it.");
          sbase.setNamespace(sbmlNamespace);
        }
      }
    }

    return listOfElementsToWrite;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processStartElement(String
   * elementName, String prefix, boolean hasAttributes, boolean hasNamespaces,
   * Object contextObject)
   */
  @Override
  public Object processStartElement(String elementName, String uri, String prefix,
    boolean hasAttributes, boolean hasNamespaces, Object contextObject) {

    // TODO: make it generic by using reflection on the contextObject

    if (contextObject instanceof SBMLDocument)
    {
      SBMLDocument sbmlDoc = (SBMLDocument) contextObject;
      CompSBMLDocumentPlugin compSBMLDoc = null;

      if (sbmlDoc.getExtension(namespaceURI) != null) {
        compSBMLDoc = (CompSBMLDocumentPlugin) sbmlDoc.getExtension(namespaceURI);
      } else {
        compSBMLDoc = new CompSBMLDocumentPlugin(sbmlDoc);
        sbmlDoc.addExtension(namespaceURI, compSBMLDoc);
      }

      if (elementName.equals(CompConstants.listOfExternalModelDefinitions))
      {
        return compSBMLDoc.getListOfExternalModelDefinitions();
      }
      else if (elementName.equals(CompConstants.listOfModelDefinitions))
      {
        return compSBMLDoc.getListOfModelDefinitions();
      }
    } // end SBMLDocument
    else if (contextObject instanceof Model)
    {
      Model model = (Model) contextObject;
      CompModelPlugin compModel = null;

      if (model.getExtension(namespaceURI) != null) {
        compModel = (CompModelPlugin) model.getExtension(namespaceURI);
      } else {
        compModel = new CompModelPlugin(model);
        model.addExtension(namespaceURI, compModel);
      }

      if (elementName.equals(CompConstants.listOfSubmodels))
      {
        return compModel.getListOfSubmodels();
      }
      else if (elementName.equals(CompConstants.listOfPorts))
      {
        return compModel.getListOfPorts();
      }
    } // end Model
    else if (contextObject instanceof Submodel)
    {
      Submodel submodel = (Submodel) contextObject;

      if (elementName.equals(CompConstants.listOfDeletions)) {
        return submodel.getListOfDeletions();
      }
    } // end Submodel
    else if (contextObject instanceof SBaseRef)
    {
      SBaseRef sBaseRef = (SBaseRef) contextObject;

      if (elementName.equalsIgnoreCase(CompConstants.sBaseRef)) {
        return sBaseRef.createSBaseRef();
      }
    } // end SBaseRef
    else if (contextObject instanceof ListOf<?>)
    {
      ListOf<?> listOf = (ListOf<?>) contextObject;

      Object newElement = createListOfChild(listOf, elementName);

      if (newElement != null) {
        return newElement;
      }
    }

    // If not other elements recognized the new element to read, it might be
    // on of the extended SBase children
    if (contextObject instanceof SBase)
    {
      SBase sbase = (SBase) contextObject;
      CompSBasePlugin compSBase = null;

      if (sbase.getExtension(namespaceURI) != null) {
        compSBase = (CompSBasePlugin) sbase.getExtension(namespaceURI);
      } else {
        compSBase = new CompSBasePlugin(sbase);
        sbase.addExtension(namespaceURI, compSBase);
      }

      if (elementName.equals(CompConstants.listOfReplacedElements))
      {
        return compSBase.getListOfReplacedElements();
      }
      else if (elementName.equals(CompConstants.replacedBy))
      {
        return compSBase.createReplacedBy();
      }
    }


    return contextObject;
  }

  /**
   * 
   * @param listOf
   * @param elementName
   * @return
   */
  @Override
  protected Object createListOfChild(ListOf<?> listOf, String elementName) {

    Object parentSBase = listOf.getParent();

    if (parentSBase == null) {
      return null;
    } else if (parentSBase instanceof Model || parentSBase instanceof SBMLDocument) {
      parentSBase = ((SBase) parentSBase).getExtension(namespaceURI);
    }

    // dealing with the extendedSBase
    if (elementName.equals(CompConstants.replacedBy) || elementName.equals(CompConstants.replacedElement)) {
      parentSBase = ((SBase) parentSBase).getExtension(namespaceURI);
    }

    String createMethodName = "create" + elementName.substring(0, 1).toUpperCase() + elementName.substring(1);
    Method createMethod = null;

    if (logger.isDebugEnabled()) {
      logger.debug("Method '" + createMethodName + "' will be used");
    }

    try {
      createMethod = parentSBase.getClass().getMethod(createMethodName, (Class<?>[]) null);

      return createMethod.invoke(parentSBase, (Object[]) null);

    } catch (SecurityException e) {
      if (logger.isDebugEnabled()) {
        logger.debug("Method '" + createMethodName + "' is not accessible on " + parentSBase.getClass().getSimpleName());
        e.printStackTrace();
      }
    } catch (NoSuchMethodException e) {
      if (logger.isDebugEnabled()) {
        logger.debug("Method '" + createMethodName + "' does not exist on " + parentSBase.getClass().getSimpleName());
      }
    } catch (IllegalArgumentException e) {
      if (logger.isDebugEnabled()) {
        logger.debug("Problem invoking the method '" + createMethodName + "' on " + parentSBase.getClass().getSimpleName());
        logger.debug(e.getMessage());
      }
    } catch (IllegalAccessException e) {
      if (logger.isDebugEnabled()) {
        logger.debug("Problem invoking the method '" + createMethodName + "' on " + parentSBase.getClass().getSimpleName());
        logger.debug(e.getMessage());
      }
    } catch (InvocationTargetException e) {
      if (logger.isDebugEnabled()) {
        logger.debug("Problem invoking the method '" + createMethodName + "' on " + parentSBase.getClass().getSimpleName());
        logger.debug(e.getMessage());
      }
    }

    // TODO: try to use the default constructor + the addXX method ??

    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#writeElement(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
   */
  @Override
  public void writeElement(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite)
  {
    super.writeElement(xmlObject, sbmlElementToWrite);

    if (logger.isDebugEnabled()) {
      logger.debug("writeElement: " + sbmlElementToWrite.getClass().getSimpleName());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#getNamespaceFor(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public String getNamespaceFor(int level, int version,	int packageVersion) {

    if (level == 3 && version == 1 && packageVersion == 1) {
      return CompConstants.namespaceURI_L3V1V1;
    }

    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#getNamespaces()
   */
  @Override
  public List<String> getNamespaces() {
    return CompConstants.namespaces;
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
    return CompConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#isRequired()
   */
  @Override
  public boolean isRequired() {
    return true;
  }

  @Override
  public SBasePlugin createPluginFor(SBase sbase) {

    if (sbase != null) {
      if (sbase instanceof Model) {
        return new CompModelPlugin((Model) sbase);
      } else if (sbase instanceof SBMLDocument) {
        return new CompSBMLDocumentPlugin((SBMLDocument) sbase);
      } else {
        return new CompSBasePlugin(sbase);
      }
    }

    return null;
  }

  @Override
  public ASTNodePlugin createPluginFor(ASTNode astNode) {
    // This package does not extends ASTNode
    return null;
  }


}

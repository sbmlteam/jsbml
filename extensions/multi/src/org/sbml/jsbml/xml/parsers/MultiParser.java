/*
 * $Id: MultiParser.java 2179 2015-04-07 16:12:08Z niko-rodrigue $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/extensions/multi/src/org/sbml/jsbml/xml/parsers/MultiParser.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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

import static org.sbml.jsbml.ext.multi.MultiConstants.listOfSpeciesTypes;
import static org.sbml.jsbml.ext.multi.MultiConstants.namespaceURI;
import static org.sbml.jsbml.ext.multi.MultiConstants.shortLabel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.mangosdk.spi.ProviderFor;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.multi.InSpeciesTypeBond;
import org.sbml.jsbml.ext.multi.MultiConstants;
import org.sbml.jsbml.ext.multi.MultiModelPlugin;
import org.sbml.jsbml.ext.multi.MultiSpeciesPlugin;
import org.sbml.jsbml.ext.multi.SpeciesFeatureType;
import org.sbml.jsbml.ext.multi.SpeciesType;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * A MultiParser is used to parse the multi extension package elements and
 * attributes. The namespaceURI URI of this parser is
 * "http://www.sbml.org/sbml/level3/version1/multi/version1". This parser is
 * able to read and write elements of the multi package (implements
 * ReadingParser and WritingParser).
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 * @version $Rev: 2179 $
 */
@ProviderFor(ReadingParser.class)
public class MultiParser extends AbstractReaderWriter implements PackageParser {

  /**
   * A {@link Logger} for this class.
   */
  private Logger logger = Logger.getLogger(MultiParser.class);

  /**
   * 
   * @return the namespaceURI of this parser.
   */
  @Override
  public String getNamespaceURI() {
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
    if (treeNode instanceof SBase && ((SBase) treeNode).getExtension(getNamespaceURI()) != null) {
      SBasePlugin sbasePlugin = ((Model) treeNode).getExtension(getNamespaceURI());

      if (sbasePlugin != null) {
        listOfElementsToWrite = super.getListOfSBMLElementsToWrite(sbasePlugin);
        logger.debug("getListOfSBMLElementsToWrite: nb children = " + sbasePlugin.getChildCount());
      }
    } else {
      listOfElementsToWrite = super.getListOfSBMLElementsToWrite(treeNode);
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

    if (contextObject instanceof Model)
    {
      Model model = (Model) contextObject;
      MultiModelPlugin multiModel = null;

      if (model.getExtension(namespaceURI) != null) {
        multiModel = (MultiModelPlugin) model.getExtension(namespaceURI);
      } else {
        multiModel = new MultiModelPlugin(model);
        model.addExtension(namespaceURI, multiModel);
      }

      if (elementName.equals(listOfSpeciesTypes))
      {
        return multiModel.getListOfSpeciesTypes();
      }
    } // end Model
    else if (contextObject instanceof SpeciesType)
    {
      SpeciesType speciesType = (SpeciesType) contextObject;

      // TODO - update to include all the new children
//      if (elementName.equals(listOfStateFeatures)) {
//        return speciesType.getListOfStateFeatures();
//      }
    } // end SpeciesType
    else if (contextObject instanceof SpeciesFeatureType)
    {
      SpeciesFeatureType stateFeature = (SpeciesFeatureType) contextObject;

      if (elementName.equals(MultiConstants.listOfPossibleSpeciesFeatureValues)) {
        return stateFeature.getListOfPossibleSpeciesFeatureValues();
      }
    } // end SpeciesFeatureType
    else if (contextObject instanceof InSpeciesTypeBond)
    {
      InSpeciesTypeBond bond = (InSpeciesTypeBond) contextObject;

      // TODO
//      if (elementName.equals(bindingSiteReference))
//      {
//        String bindingSiteReference = new String();
//        bond.addBindingSiteReference(bindingSiteReference);
//        return bindingSiteReference;
//      }
    } // end InSpeciesTypeBond
    else if (contextObject instanceof ListOf<?>)
    {
      ListOf<?> listOf = (ListOf<?>) contextObject;

      Object newElement = createListOfChild(listOf, elementName);

      return newElement;

      // TODO: SpeciesTypeInstance, SelectorReference, ....
    }

    return contextObject;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#createListOfChild(org.sbml.jsbml.ListOf, java.lang.String)
   */
  @Override
  protected Object createListOfChild(ListOf<?> listOf, String elementName) {

    Object parentSBase = listOf.getParent();

    if (parentSBase == null) {
      return null;
    } else if (parentSBase instanceof Model) {
      parentSBase = ((Model) parentSBase).getExtension(namespaceURI);
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

    // TODO: try to use the default constructor + the addXX method

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

  @Override
  public String getNamespaceFor(int level, int version, int packageVersion) {

    if (level == 3 && version == 1 && packageVersion == 1) {
      return MultiConstants.namespaceURI_L3V1V1;
    }

    return null;
  }

  @Override
  public List<String> getNamespaces() {
    return MultiConstants.namespaces;
  }

  @Override
  public List<String> getPackageNamespaces() {
    return getNamespaces();
  }

  @Override
  public String getPackageName() {
    return MultiConstants.shortLabel;
  }

  @Override
  public boolean isRequired() {
    return true;
  }

  @Override
  public SBasePlugin createPluginFor(SBase sbase) {

    if (sbase != null) {
      if (sbase instanceof Model) {
        return new MultiModelPlugin((Model) sbase);
      } else if (sbase instanceof Species) {
        return new MultiSpeciesPlugin((Species) sbase);
      } else if (sbase instanceof Reaction) {
        // return new MultiReactionPlugin((Reaction) sbase);
      }
      // TODO : finish when implementation is updated
    }

    return null;
  }


}

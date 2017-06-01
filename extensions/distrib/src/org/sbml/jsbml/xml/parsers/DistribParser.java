/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2017 jointly by the following organizations:
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

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.mangosdk.spi.ProviderFor;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.ASTNodePlugin;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.distrib.DistribConstants;
import org.sbml.jsbml.ext.distrib.DistribFunctionDefinitionPlugin;
import org.sbml.jsbml.ext.distrib.DistribInput;
import org.sbml.jsbml.ext.distrib.DistribSBasePlugin;
import org.sbml.jsbml.ext.distrib.DrawFromDistribution;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * This class is used to parse the distrib extension package elements and
 * attributes. The namespaceURI URI of this parser is
 * {@code http://www.sbml.org/sbml/level3/version1/distrib/version1}. This parser is
 * able to read and write elements of the distrib package (extends
 * {@link AbstractReaderWriter}).
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
@ProviderFor(ReadingParser.class)
public class DistribParser extends AbstractReaderWriter implements PackageParser {

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getNamespaceURI()
   */
  @Override
  public String getNamespaceURI() {
    return DistribConstants.namespaceURI;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getShortLabel()
   */
  @Override
  public String getShortLabel() {
    return DistribConstants.shortLabel;
  }

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(DistribParser.class);

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.WritingParser#getListOfSBMLElementsToWrite(Object sbase)
   */
  @Override
  public List<Object> getListOfSBMLElementsToWrite(Object sbase) {

    if (logger.isDebugEnabled()) {
      logger.debug("getListOfSBMLElementsToWrite: " + sbase.getClass().getCanonicalName());
    }

    List<Object> listOfElementsToWrite = new ArrayList<Object>();

    // test if this sbase is an extended SBase.
    if (sbase instanceof SBase && ((SBase) sbase).getExtension(getNamespaceURI()) != null) {
      SBasePlugin sbasePlugin = ((SBase) sbase).getExtension(getNamespaceURI());

      listOfElementsToWrite = super.getListOfSBMLElementsToWrite(sbasePlugin);
      logger.debug("getListOfSBMLElementsToWrite: nb children = " + sbasePlugin.getChildCount());
    } else {
      listOfElementsToWrite = super.getListOfSBMLElementsToWrite(sbase);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("listOfElementsToWrite.size() not null = " + (listOfElementsToWrite != null ? listOfElementsToWrite.size() : 0));
    }
    
    return listOfElementsToWrite;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#processAttribute(String
   *      elementName, String attributeName, String value, String prefix,
   *      boolean isLastAttribute, Object contextObject)
   */
  @Override
  public boolean processAttribute(String elementName, String attributeName,
    String value, String uri, String prefix, boolean isLastAttribute,
    Object contextObject)
  {
    if (logger.isDebugEnabled()) {
      logger.debug("processAttribute -> " + prefix + ":" + attributeName + " = " + value + " (" + contextObject.getClass().getName() + ")");
    }

    if (contextObject instanceof FunctionDefinition) {
      FunctionDefinition fd = (FunctionDefinition) contextObject;
      DistribFunctionDefinitionPlugin fbcFD = null;

      if (fd.getExtension(DistribConstants.namespaceURI) != null) {
        fbcFD = (DistribFunctionDefinitionPlugin) fd.getExtension(DistribConstants.namespaceURI);
      } else {
        fbcFD = new DistribFunctionDefinitionPlugin(fd);
        fd.addExtension(DistribConstants.namespaceURI, fbcFD);
      }    

      contextObject = fbcFD;
    }
    
    return super.processAttribute(elementName, attributeName, value, uri, prefix, isLastAttribute, contextObject);
  }



  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#processStartElement(java.lang.String, java.lang.String, boolean, boolean, java.lang.Object)
   */
  // Create the proper object and link it to his parent.
  @Override
  public Object processStartElement(String elementName, String uri, String prefix,
    boolean hasAttributes, boolean hasNamespaces, Object contextObject)
  {
    if (contextObject instanceof FunctionDefinition) {
    	FunctionDefinition fd = (FunctionDefinition) contextObject;
      DistribFunctionDefinitionPlugin distribFD = null;

      if (fd.getExtension(DistribConstants.namespaceURI) != null) {
        distribFD = (DistribFunctionDefinitionPlugin) fd.getExtension(DistribConstants.namespaceURI);
      } else {
        distribFD = new DistribFunctionDefinitionPlugin(fd);
        fd.addExtension(DistribConstants.namespaceURI, distribFD);
      }

      // drawFromDistribution
      if (elementName.equals(DistribConstants.drawFromDistribution)) {
        DrawFromDistribution dfd = distribFD.createDrawFromDistribution();;
        return dfd;
      }
    } else if (contextObject instanceof DrawFromDistribution) {
      DrawFromDistribution dfd = (DrawFromDistribution) contextObject;

      // istOfDistribInputs
      if (elementName.equals(DistribConstants.listOfDistribInputs)) {
        ListOf<DistribInput> listOfDistribInputs = dfd.getListOfDistribInputs();
        return listOfDistribInputs;
      }
    }
    
    else if (contextObject instanceof ListOf<?>) {
      ListOf<?> listOf = (ListOf<?>) contextObject;

      if (elementName.equals(DistribConstants.distribInput)) {
        DrawFromDistribution dfd = (DrawFromDistribution) listOf.getParentSBMLObject();

        DistribInput input = dfd.createDistribInput();
        
        return input;
      } 
    }
    
    // If not other elements recognized the new element to read, it might be
    // on of the extended SBase children
    
    if (contextObject instanceof SBase)
    {
      SBase sbase = (SBase) contextObject;
      DistribSBasePlugin distribSBase = null;

      if (sbase.getExtension(DistribConstants.shortLabel) != null) {
        distribSBase = (DistribSBasePlugin) sbase.getExtension(DistribConstants.shortLabel);
      } else {
        distribSBase = new DistribSBasePlugin(sbase);
        sbase.addExtension(DistribConstants.shortLabel, distribSBase);
      }

      if (elementName.equals(DistribConstants.uncertainty))
      {
        return distribSBase.createUncertainty();
      }
    }
    
    return contextObject;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.WritingParser#writeElement(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
   */
  @Override
  public void writeElement(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite) {

    if (sbmlElementToWrite instanceof SBase) {
      SBase sbase = (SBase) sbmlElementToWrite;

      if (!xmlObject.isSetName()) {
        if (sbase instanceof ListOf<?>) {
          ListOf<?> listOf = (ListOf<?>) sbase;

          if (listOf.size() > 0) {
            if (listOf.get(0) instanceof DistribInput) {
              xmlObject.setName(DistribConstants.listOfDistribInputs); // TODO - check that this is needed !!
            } 
          }
        } else {
          xmlObject.setName(sbase.getElementName());
        }
      }
      //      if (!xmlObject.isSetPrefix()) {
      //        xmlObject.setPrefix(getShortLabel());
      //      }
      //			xmlObject.setNamespace(FBCConstants.namespaceURI);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("writeElement " + xmlObject.getName());
    }
  }

  /**
   * Tests this class
   * 
   * @param args
   * @throws SBMLException
   */
  public static void main(String[] args) throws SBMLException {
    if (args.length < 1) {
      System.out.println(
          "Usage: java org.sbml.jsbml.xml.stax.SBMLWriter sbmlFileName");
      System.exit(0);
    }

    // this JOptionPane is added here to be able to start visualVM profiling
    // before the reading or writing is started.
    // JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.");

    File argsAsFile = new File(args[0]);
    File[] files = null;

    if (argsAsFile.isDirectory())
    {
      files = argsAsFile.listFiles(new FileFilter() {

        @Override
        public boolean accept(File pathname)
        {
          if (pathname.getName().contains("-jsbml"))
          {
            return false;
          }

          if (pathname.getName().endsWith(".xml"))
          {
            return true;
          }

          return false;
        }
      });
    }
    else
    {
      files = new File[1];
      files[0] = argsAsFile;
    }

    for (File file : files)
    {

      long init = Calendar.getInstance().getTimeInMillis();
      System.out.println(Calendar.getInstance().getTime());

      String fileName = file.getAbsolutePath();
      String jsbmlWriteFileName = fileName.replaceFirst(".xml", "-jsbml.xml");

      System.out.printf("Reading %s and writing %s\n",
        fileName, jsbmlWriteFileName);

      SBMLDocument testDocument;
      long afterRead = 0;
      try {
        testDocument = new SBMLReader().readSBMLFromFile(fileName);
        System.out.printf("Reading done\n");
        System.out.println(Calendar.getInstance().getTime());
        afterRead = Calendar.getInstance().getTimeInMillis();


        System.out.printf("Starting writing\n");

        new SBMLWriter().write(testDocument.clone(), jsbmlWriteFileName);
      }
      catch (XMLStreamException e)
      {
        e.printStackTrace();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }

      System.out.println(Calendar.getInstance().getTime());
      long end = Calendar.getInstance().getTimeInMillis();
      long nbSecondes = (end - init)/1000;
      long nbSecondesRead = (afterRead - init)/1000;
      long nbSecondesWrite = (end - afterRead)/1000;

      if (nbSecondes > 120) {
        System.out.println("It took " + nbSecondes/60 + " minutes.");
      } else {
        System.out.println("It took " + nbSecondes + " secondes.");
      }
      System.out.println("Reading: " + nbSecondesRead + " secondes.");
      System.out.println("Writing: " + nbSecondesWrite + " secondes.");
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#getNamespaceFor(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public String getNamespaceFor(int level, int version,	int packageVersion) {

     return DistribConstants.namespaceURI_L3V1V1;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#getNamespaces()
   */
  @Override
  public List<String> getNamespaces() {
    return DistribConstants.namespaces;
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
    return DistribConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#isRequired()
   */
  @Override
  public boolean isRequired() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#createPluginFor(org.sbml.jsbml.SBase)
   */
  @Override
  public SBasePlugin createPluginFor(SBase sbase) {
    if (sbase != null) {
      if (sbase instanceof FunctionDefinition) {
        return new DistribFunctionDefinitionPlugin((FunctionDefinition) sbase);
      } else {
        return new DistribSBasePlugin(sbase);
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

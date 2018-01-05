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
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.ASTNodePlugin;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.fbc.And;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FBCList;
import org.sbml.jsbml.ext.fbc.FBCModelPlugin;
import org.sbml.jsbml.ext.fbc.FBCReactionPlugin;
import org.sbml.jsbml.ext.fbc.FBCSpeciesPlugin;
import org.sbml.jsbml.ext.fbc.FluxBound;
import org.sbml.jsbml.ext.fbc.FluxObjective;
import org.sbml.jsbml.ext.fbc.GeneProduct;
import org.sbml.jsbml.ext.fbc.GeneProductRef;
import org.sbml.jsbml.ext.fbc.GeneProductAssociation;
import org.sbml.jsbml.ext.fbc.LogicalOperator;
import org.sbml.jsbml.ext.fbc.Objective;
import org.sbml.jsbml.ext.fbc.Or;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * This class is used to parse the fbc extension package elements and
 * attributes. The namespaceURI URI of this parser is
 * {@code http://www.sbml.org/sbml/level3/version1/fbc/version1}. This parser is
 * able to read and write elements of the fbc package (extends
 * {@link AbstractReaderWriter}).
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
@SuppressWarnings("deprecation")
@ProviderFor(ReadingParser.class)
public class FBCParser extends AbstractReaderWriter implements PackageParser {

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getNamespaceURI()
   */
  @Override
  public String getNamespaceURI() {
    return FBCConstants.namespaceURI;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getShortLabel()
   */
  @Override
  public String getShortLabel() {
    return FBCConstants.shortLabel;
  }

  /**
   * The {@link FBCList} enum which represents the name of the list this parser
   * is currently reading.
   * 
   */
  private FBCList groupList = FBCList.none;

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(FBCParser.class);

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
    logger.debug("processAttribute -> " + prefix + ":" + attributeName + " = " + value + " (" + contextObject.getClass().getName() + ")");

    if (contextObject instanceof Species) {
      Species species = (Species) contextObject;
      FBCSpeciesPlugin fbcSpecies = (FBCSpeciesPlugin) species.getPlugin(FBCConstants.shortLabel);
      contextObject = fbcSpecies;

    } else if (contextObject instanceof Reaction) {
      Reaction reaction = (Reaction) contextObject;
      contextObject = reaction.getPlugin(FBCConstants.shortLabel);

    } else if (contextObject instanceof Model) {

      contextObject = ((Model) contextObject).getPlugin(FBCConstants.shortLabel);

      // TODO - could be generic in AbstractReaderWriter.processAttribute. If packageName = 'core', just call
      // getPlugin. The plugin will be created if needed or returned if already present.

    }

    return super.processAttribute(elementName, attributeName, value, uri, prefix, isLastAttribute, contextObject);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#processEndElement(java.lang.String, java.lang.String, boolean, java.lang.Object)
   */
  @Override
  public boolean processEndElement(String elementName, String prefix,
    boolean isNested, Object contextObject) {
    if (elementName.equals(FBCList.listOfFluxBounds.name())
        || elementName.equals(FBCList.listOfObjectives.name())) {
      groupList = FBCList.none;
    } else if (elementName.equals(FBCList.listOfFluxObjectives.name())) {
      groupList = FBCList.listOfObjectives;
    } else if (elementName.equals(FBCList.listOfGeneProducts.name())) {
      groupList = FBCList.none;
    }

    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#processStartElement(java.lang.String, java.lang.String, boolean, boolean, java.lang.Object)
   */
  // Create the proper object and link it to his parent.
  @Override
  @SuppressWarnings({"unchecked"})
  public Object processStartElement(String elementName, String uri, String prefix,
    boolean hasAttributes, boolean hasNamespaces, Object contextObject)
  {
    if (contextObject instanceof Model) {
      Model model = (Model) contextObject;
      FBCModelPlugin fbcModel = (FBCModelPlugin) model.getPlugin(FBCConstants.shortLabel);

      if (elementName.equals(FBCList.listOfFluxBounds.name())) {

        ListOf<FluxBound> listOfFluxBounds = fbcModel.getListOfFluxBounds();
        groupList = FBCList.listOfFluxBounds;
        return listOfFluxBounds;
      }
      else if (elementName.equals(FBCList.listOfObjectives.name())) {

        ListOf<Objective> listOfObjectives = fbcModel.getListOfObjectives();
        groupList = FBCList.listOfObjectives;
        return listOfObjectives;
      }
      else if (elementName.equals(FBCList.listOfGeneProducts.name())) {
        ListOf<GeneProduct> listOfGeneProducts = fbcModel.getListOfGeneProducts();
        groupList = FBCList.listOfGeneProducts;
        return listOfGeneProducts;
      }
    } else if (contextObject instanceof Objective) {
      Objective objective = (Objective) contextObject;

      if (elementName.equals(FBCConstants.listOfFluxObjectives) || elementName.equals(FBCConstants.listOfFluxes)) {
        // listOfFluxes was the first name of listOfFluxObjectives in the preliminary specifications
        ListOf<FluxObjective> listOfFluxObjectives = objective.getListOfFluxObjectives();
        groupList = FBCList.listOfFluxObjectives;
        return listOfFluxObjectives;
      }
    } else if (contextObject instanceof Reaction) {
      Reaction reaction = (Reaction) contextObject;
      FBCReactionPlugin fbcReaction = (FBCReactionPlugin) reaction.getPlugin(FBCConstants.shortLabel);

      if (elementName.equals(FBCConstants.geneProductAssociation)) {
        GeneProductAssociation gPA = fbcReaction.createGeneProductAssociation();
        return gPA;
      } 
      else if (elementName.equals(FBCConstants.geneProteinAssociation)) {
        // FBCConstants.geneProteinAssociation has been used for a very long time in the FBC v2 release candidates
        // so we are keeping it here in case some files still use it.
        logger.warn("You are using an invalid element name '" + FBCConstants.geneProteinAssociation 
          + "'. It was only used for few FBC V2 release candidates.");
        GeneProductAssociation gPA = fbcReaction.createGeneProductAssociation();
        return gPA;
      }
    } else if (contextObject instanceof GeneProductAssociation) {
      GeneProductAssociation gPA = (GeneProductAssociation) contextObject;

      // and + or + geneProductRef
      if (elementName.equals(FBCConstants.and)) {
        And andAssociation = new And();
        gPA.setAssociation(andAssociation);
        return andAssociation;
      } else if (elementName.equals(FBCConstants.or)) {
        Or orAssociation = new Or();
        gPA.setAssociation(orAssociation);
        return orAssociation;
      } else if (elementName.equals(FBCConstants.geneProductReference)) {
        GeneProductRef genePR = new GeneProductRef();
        gPA.setAssociation(genePR);
        return genePR;
      }
    } else if (contextObject instanceof And || contextObject instanceof Or) {
      LogicalOperator logicalOp = (LogicalOperator) contextObject;

      // and + or + geneProductRef
      if (elementName.equals(FBCConstants.and)) {
        And andAssociation = logicalOp.createAnd();
        return andAssociation;
      } else if (elementName.equals(FBCConstants.or)) {
        Or orAssociation = logicalOp.createOr();
        return orAssociation;
      } else if (elementName.equals(FBCConstants.geneProductReference)) {
        GeneProductRef genePR = logicalOp.createGeneProductRef();
        return genePR;
      }

    }

    else if (contextObject instanceof ListOf<?>) {
      ListOf<SBase> listOf = (ListOf<SBase>) contextObject;

      if (elementName.equals(FBCConstants.fluxBound)
          && groupList.equals(FBCList.listOfFluxBounds)) {
        Model model = (Model) listOf.getParentSBMLObject();
        FBCModelPlugin extendeModel = (FBCModelPlugin) model.getExtension(FBCConstants.shortLabel);

        FluxBound fluxBound = new FluxBound();
        extendeModel.addFluxBound(fluxBound);
        return fluxBound;

      } else if (elementName.equals(FBCConstants.objective)
          && groupList.equals(FBCList.listOfObjectives)) {
        Model model = (Model) listOf.getParentSBMLObject();
        FBCModelPlugin extendeModel = (FBCModelPlugin) model.getExtension(FBCConstants.shortLabel);

        Objective objective = new Objective();
        extendeModel.addObjective(objective);

        return objective;
      } else if (elementName.equals(FBCConstants.fluxObjective)
          && groupList.equals(FBCList.listOfFluxObjectives)) {
        Objective objective = (Objective) listOf.getParentSBMLObject();

        FluxObjective fluxObjective = new FluxObjective();
        objective.addFluxObjective(fluxObjective);

        return fluxObjective;
      } else if (elementName.equals(FBCConstants.geneProduct)
          && groupList.equals(FBCList.listOfGeneProducts)) {
        Model model = (Model) listOf.getParentSBMLObject();
        FBCModelPlugin extendeModel = (FBCModelPlugin) model.getExtension(FBCConstants.shortLabel);

        GeneProduct geneProduct = new GeneProduct();
        extendeModel.addGeneProduct(geneProduct);

        return geneProduct;
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

    if (logger.isDebugEnabled()) {
      logger.debug("FBCParser: writeElement");
    }

    super.writeElement(xmlObject, sbmlElementToWrite);
  }

  /**
   * Tests this class
   * 
   * @param args
   * @throws SBMLException
   */
  public static void main(String[] args) throws SBMLException {
    // TODO: move this test into a separate test class.
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

        FBCModelPlugin fbcModel = (FBCModelPlugin) testDocument.getModel().getExtension(FBCConstants.namespaceURI);

        if (fbcModel != null)
        {
          System.out.println("nb fluxBounds found: " + fbcModel.getListOfFluxBounds().size());
          System.out.println("nb objectives found: " + fbcModel.getListOfObjectives().size());
          System.out.println("nb geneProducts found: " + fbcModel.getListOfGeneProducts().size());
          System.out.println("nb fluxObjectives found: " + fbcModel.getObjective(0).getListOfFluxObjectives().size());
          System.out.println("Active objective: " + fbcModel.getActiveObjective());
          System.out.println("Active objective: " + fbcModel.getListOfObjectives().getActiveObjective());
        }
        else
        {
          System.out.println("!!!!!!!!!! not FBC model plugin defined !!!!!!!!!!!!");
        }


        System.out.printf("Starting writing\n");

        System.out.println(new SBMLWriter().writeSBMLToString(testDocument));
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
    if ((level == 3) && (version == 1)) {
      switch (packageVersion) {
      case 1:
        return FBCConstants.namespaceURI_L3V1V1;
      case 2:
        return FBCConstants.namespaceURI_L3V1V2;
      default:
        break;
      }
    }

    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#getNamespaces()
   */
  @Override
  public List<String> getNamespaces() {
    return FBCConstants.namespaces;
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
    return FBCConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#isRequired()
   */
  @Override
  public boolean isRequired() {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#createPluginFor(org.sbml.jsbml.SBase)
   */
  @Override
  public SBasePlugin createPluginFor(SBase sbase) {
    if (sbase != null) {
      if (sbase instanceof Model) {
        return new FBCModelPlugin((Model) sbase);
      } else if (sbase instanceof Species) {
        return new FBCSpeciesPlugin((Species) sbase);
      } else if (sbase instanceof Reaction) {
        return new FBCReactionPlugin((Reaction) sbase);
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

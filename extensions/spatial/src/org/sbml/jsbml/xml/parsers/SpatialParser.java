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
package org.sbml.jsbml.xml.parsers;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.mangosdk.spi.ProviderFor;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.ASTNodePlugin;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase;
import org.sbml.jsbml.ext.spatial.AdjacentDomains;
import org.sbml.jsbml.ext.spatial.AdvectionCoefficient;
import org.sbml.jsbml.ext.spatial.AnalyticGeometry;
import org.sbml.jsbml.ext.spatial.AnalyticVolume;
import org.sbml.jsbml.ext.spatial.Boundary;
import org.sbml.jsbml.ext.spatial.BoundaryCondition;
import org.sbml.jsbml.ext.spatial.CSGHomogeneousTransformation;
import org.sbml.jsbml.ext.spatial.CSGNode;
import org.sbml.jsbml.ext.spatial.CSGObject;
import org.sbml.jsbml.ext.spatial.CSGPrimitive;
import org.sbml.jsbml.ext.spatial.CSGPseudoPrimitive;
import org.sbml.jsbml.ext.spatial.CSGRotation;
import org.sbml.jsbml.ext.spatial.CSGScale;
import org.sbml.jsbml.ext.spatial.CSGSetOperator;
import org.sbml.jsbml.ext.spatial.CSGTransformation;
import org.sbml.jsbml.ext.spatial.CSGTranslation;
import org.sbml.jsbml.ext.spatial.CSGeometry;
import org.sbml.jsbml.ext.spatial.CompartmentMapping;
import org.sbml.jsbml.ext.spatial.CoordinateComponent;
import org.sbml.jsbml.ext.spatial.DiffusionCoefficient;
import org.sbml.jsbml.ext.spatial.Domain;
import org.sbml.jsbml.ext.spatial.DomainType;
import org.sbml.jsbml.ext.spatial.Geometry;
import org.sbml.jsbml.ext.spatial.GeometryDefinition;
import org.sbml.jsbml.ext.spatial.InteriorPoint;
import org.sbml.jsbml.ext.spatial.MixedGeometry;
import org.sbml.jsbml.ext.spatial.OrdinalMapping;
import org.sbml.jsbml.ext.spatial.ParametricGeometry;
import org.sbml.jsbml.ext.spatial.ParametricObject;
import org.sbml.jsbml.ext.spatial.SampledField;
import org.sbml.jsbml.ext.spatial.SampledFieldGeometry;
import org.sbml.jsbml.ext.spatial.SampledVolume;
import org.sbml.jsbml.ext.spatial.SpatialCompartmentPlugin;
import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.ext.spatial.SpatialModelPlugin;
import org.sbml.jsbml.ext.spatial.SpatialParameterPlugin;
import org.sbml.jsbml.ext.spatial.SpatialPoints;
import org.sbml.jsbml.ext.spatial.SpatialReactionPlugin;
import org.sbml.jsbml.ext.spatial.SpatialSpeciesPlugin;
import org.sbml.jsbml.ext.spatial.SpatialSymbolReference;
import org.sbml.jsbml.ext.spatial.TransformationComponent;
import org.sbml.jsbml.util.ResourceManager;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * @author Andreas Dr&auml;ger
 * @author Piero Dalle Pezze
 * @author rodrigue
 * @since 1.0
 */
@ProviderFor(ReadingParser.class)
public class SpatialParser extends AbstractReaderWriter implements PackageParser {

  // TODO - check that it is properly updated to the 0.90 draft specs
	
  /**
   * Localization support.
   */
  private static final transient ResourceBundle bundle = ResourceManager.getBundle("org.sbml.jsbml.resources.cfg.Messages");

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(SpatialParser.class);

  /**
   * The name space of required elements.
   */
  public static final String namespaceURIrequired = "http://www.sbml.org/sbml/level3/version1/req/version1";

  
  
  @Override
  public void processCharactersOf(String elementName, String characters,
    Object contextObject) {
    if (contextObject instanceof SampledField) {
      SampledField sampledField = (SampledField) contextObject;
      sampledField.append(characters);
    }
    else if (contextObject instanceof SpatialPoints) {
      SpatialPoints spatialPoints = (SpatialPoints) contextObject;
      spatialPoints.append(characters);
    }
    else if (contextObject instanceof ParametricObject) {
      ParametricObject parametricObject = (ParametricObject) contextObject;
      parametricObject.append(characters);
    }
  }

  @Override
  public void writeCharacters(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite) {
    if(sbmlElementToWrite instanceof SampledField) {
      SampledField sampledField = (SampledField) sbmlElementToWrite;
      if(sampledField.isSetSamples()) {
        xmlObject.setCharacters(sampledField.getSamples()); 
      }
    }
    else if(sbmlElementToWrite instanceof SpatialPoints) {
      SpatialPoints spatialPoints = (SpatialPoints) sbmlElementToWrite;
      if(spatialPoints.isSetArrayData()) {
        xmlObject.setCharacters(spatialPoints.getArrayData()); 
      }
    }
    else if(sbmlElementToWrite instanceof ParametricObject) {
      ParametricObject parametricObject = (ParametricObject) sbmlElementToWrite;
      if(parametricObject.isSetPointIndex()) {
        xmlObject.setCharacters(parametricObject.getPointIndex()); 
      }
    }    
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getShortLabel()
   */
  @Override
  public String getShortLabel() {
    return "spatial";
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getNamespaceURI()
   */
  @Override
  public String getNamespaceURI() {
    return SpatialConstants.namespaceURI;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.WritingParser#getListOfSBMLElementsToWrite(java.lang.Object)
   */
  @Override
  public List<Object> getListOfSBMLElementsToWrite(Object treeNode)
  {
    if (logger.isDebugEnabled()) {
      logger.debug("getListOfSBMLElementsToWrite: " + treeNode.getClass().getCanonicalName());
    }

    List<Object> listOfElementsToWrite = new ArrayList<Object>();

    if (treeNode instanceof SBase && (! (treeNode instanceof Model)) && ((SBase) treeNode).getExtension(getNamespaceURI()) != null) {
      SBasePlugin sbasePlugin = ((SBase) treeNode).getExtension(getNamespaceURI());

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
   * @see org.sbml.jsbml.xml.parsers.WritingParser#writeElement(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
   */
  @Override
  public void writeElement(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite)
  {
    super.writeElement(xmlObject, sbmlElementToWrite);

    // Put the correct name for the ListOfGeometryDefinitions    
    // The GeometryDefinition element can be one of AnalyticGeometry, SampledFieldGeometry, CSGeometry, ParametricGeometry.
    if (xmlObject.getName().equals("listOfCSGeometrys") || xmlObject.getName().equals("listOfAnalyticGeometrys")
        || xmlObject.getName().equals("listOfSampledFieldGeometrys") || xmlObject.getName().equals("listOfParametricGeometrys")
        || xmlObject.getName().equals("listOfMixedGeometrys"))
    {
      xmlObject.setName(SpatialConstants.listOfGeometryDefinitions); // TODO - case where listOfGeometryDefinitions is empty ? Or any other ListOf from packages in fact !!
    }
    
    
    if (logger.isDebugEnabled()) {
      logger.debug("writeElement: " + sbmlElementToWrite.getClass().getSimpleName());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#processAttribute(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.Object)
   */
  @Override
  public boolean processAttribute(String elementName, String attributeName,
    String value, String uri, String prefix, boolean isLastAttribute,
    Object contextObject) { 
	  
	boolean isAttributeRead = false;
	  
    if (contextObject instanceof Species) {
      Species species = (Species) contextObject;
      SpatialSpeciesPlugin spatialSpecies = null;
      if (species.getExtension(SpatialConstants.namespaceURI) != null) {
        spatialSpecies = (SpatialSpeciesPlugin) species.getExtension(SpatialConstants.namespaceURI);
      } else {
        spatialSpecies = new SpatialSpeciesPlugin(species);
        species.addExtension(SpatialConstants.namespaceURI, spatialSpecies);
      }
      contextObject = spatialSpecies;
    } else if (contextObject instanceof Reaction) {
      Reaction reaction = (Reaction) contextObject;
      SpatialReactionPlugin spatialReaction = null;
      if (reaction.getExtension(SpatialConstants.namespaceURI) != null) {
        spatialReaction = (SpatialReactionPlugin) reaction.getExtension(SpatialConstants.namespaceURI);
      } else {
        spatialReaction = new SpatialReactionPlugin(reaction);
        reaction.addExtension(SpatialConstants.namespaceURI, spatialReaction);
      }
      contextObject = spatialReaction;
    }
    
    isAttributeRead = super.processAttribute(elementName, attributeName, value, uri, prefix,
      isLastAttribute, contextObject);
    
    if(!isAttributeRead) {
      if(contextObject instanceof SBasePlugin) {
        processUnknownAttribute(attributeName, SpatialConstants.namespaceURI, value, prefix, contextObject);
        return true;
      }
    }
    
    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#processStartElement(java.lang.String, java.lang.String, java.lang.String, boolean, boolean, java.lang.Object)
   */
  @SuppressWarnings("unchecked")
  @Override
  public Object processStartElement(String elementName, String uri,
    String prefix, boolean hasAttributes, boolean hasNamespaces,
    Object contextObject)
  {
    if (contextObject instanceof Model) {
      Model model = (Model) contextObject;
      SpatialModelPlugin spatialModel = null;

      if (model.getExtension(SpatialConstants.namespaceURI) != null) {
        spatialModel = (SpatialModelPlugin) model.getExtension(SpatialConstants.namespaceURI);
      } else {
        spatialModel = new SpatialModelPlugin(model);
        model.addExtension(SpatialConstants.namespaceURI, spatialModel);
      }

      if (elementName.equals(SpatialConstants.geometry)) {
    	
    	//keep order of elements for later validation
        AbstractReaderWriter.storeElementsOrder(elementName, spatialModel);
          
        Geometry geometry = new Geometry();
        spatialModel.setGeometry(geometry);
        return geometry;
      } else {
        logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
        return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
      }
    } else if (contextObject instanceof Compartment) {
      Compartment compartment = (Compartment) contextObject;
      SpatialCompartmentPlugin spatialCompartment = null;
      if (compartment.getExtension(SpatialConstants.namespaceURI) != null) {
        spatialCompartment = (SpatialCompartmentPlugin) compartment.getExtension(SpatialConstants.namespaceURI);
      } else {
        spatialCompartment = new SpatialCompartmentPlugin(compartment);
        compartment.addExtension(SpatialConstants.namespaceURI, spatialCompartment);
      }
      
      // keep order of elements for later validation
      AbstractReaderWriter.storeElementsOrder(elementName, spatialCompartment);
      
      if (elementName.equals(SpatialConstants.compartmentMapping)) {
        CompartmentMapping compartmentMapping = new CompartmentMapping();
        spatialCompartment.setCompartmentMapping(compartmentMapping);
        return compartmentMapping;
      } else {
        logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
        return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
      }
    }  else if (contextObject instanceof Parameter) {
      Parameter param = (Parameter) contextObject;
      SpatialParameterPlugin spatialParam = null;
      if (param.getExtension(SpatialConstants.namespaceURI) != null) {
        spatialParam = (SpatialParameterPlugin) param.getExtension(SpatialConstants.namespaceURI);
      } else {
        spatialParam = new SpatialParameterPlugin(param);
        param.addExtension(SpatialConstants.namespaceURI, spatialParam);
      }
      
      // keep order of elements for later validation
      AbstractReaderWriter.storeElementsOrder(elementName, spatialParam);

      // TODO: CHECK create method. this might be the source of the problem.
      if (elementName.equals(SpatialConstants.spatialSymbolReference)) {
        SpatialSymbolReference ssr = new SpatialSymbolReference();
        spatialParam.setParamType(ssr);
        return ssr;
      } else if (elementName.equals(SpatialConstants.diffusionCoefficient)){
        DiffusionCoefficient dc = new DiffusionCoefficient();
        spatialParam.setParamType(dc);
        return dc;
      } else if (elementName.equals(SpatialConstants.advectionCoefficient)){
        AdvectionCoefficient ac = new AdvectionCoefficient();
        spatialParam.setParamType(ac);
        return ac;
      } else if (elementName.equals(SpatialConstants.boundaryCondition)){
        BoundaryCondition bc = new BoundaryCondition();
        spatialParam.setParamType(bc);
        return bc;
      } else {
        logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
        return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
      }
    } else if (contextObject instanceof Geometry) {
      Geometry geometry = (Geometry) contextObject;
      
      // keep order of elements for later validation
      AbstractReaderWriter.storeElementsOrder(elementName, contextObject);
      
      if (elementName.equals(SpatialConstants.listOfCoordinateComponents)) {
        ListOf<CoordinateComponent> listOfCoordinateComponents = geometry.getListOfCoordinateComponents();
        return listOfCoordinateComponents;
      } else if (elementName.equals(SpatialConstants.listOfDomainTypes)) {
        ListOf<DomainType> listOfDomainTypes = geometry.getListOfDomainTypes();
        return listOfDomainTypes;
      } else if (elementName.equals(SpatialConstants.listOfDomains)) {
        ListOf<Domain> listOfDomains = geometry.getListOfDomains();
        return listOfDomains;
      } else if (elementName.equals(SpatialConstants.listOfAdjacentDomains)) {
        ListOf<AdjacentDomains> listOfAdjacentDomains = geometry.getListOfAdjacentDomains();
        return listOfAdjacentDomains;
      } else if (elementName.equals(SpatialConstants.listOfGeometryDefinitions)) {
        ListOf<GeometryDefinition> listOfGeometryDefinitions = geometry.getListOfGeometryDefinitions();
        return listOfGeometryDefinitions;
      } else if (elementName.equals(SpatialConstants.listOfSampledFields)) {
        ListOf<SampledField> listOfSampledFields = geometry.getListOfSampledFields();
        return listOfSampledFields;
      } else {
        logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
        return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
      }
      
    } else if (contextObject instanceof CoordinateComponent) {
      CoordinateComponent cc = (CoordinateComponent) contextObject;
      
      // keep order of elements for later validation
      AbstractReaderWriter.storeElementsOrder(elementName, contextObject);
      
      if (elementName.equals(SpatialConstants.boundaryMinimum)) {
        Boundary boundary = new Boundary();
        cc.setBoundaryMinimum(boundary);
        return boundary;
      } else if (elementName.equals(SpatialConstants.boundaryMaximum)) {
        Boundary boundary = new Boundary();
        cc.setBoundaryMaximum(boundary);
        return boundary;
      } else {
        logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
        return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
      }

    } else if (contextObject instanceof Domain) {
      Domain domain = (Domain) contextObject;
      
      // keep order of elements for later validation
      AbstractReaderWriter.storeElementsOrder(elementName, contextObject);
      
      if (elementName.equals(SpatialConstants.listOfInteriorPoints)){
        ListOf<InteriorPoint> listOfInteriorPoints = domain.getListOfInteriorPoints();
        return listOfInteriorPoints;
      } else {
        logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
        return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
      }
    } else if (contextObject instanceof AnalyticGeometry) {
      AnalyticGeometry analyticGeometry = (AnalyticGeometry) contextObject;
      
      // keep order of elements for later validation
      AbstractReaderWriter.storeElementsOrder(elementName, contextObject);
      
      if (elementName.equals(SpatialConstants.listOfAnalyticVolumes)){
        ListOf<AnalyticVolume> listOfAnalyticVolumes = analyticGeometry.getListOfAnalyticVolumes();
        return listOfAnalyticVolumes;
      } else {
        logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
        return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
      }
    } else if (contextObject instanceof SampledFieldGeometry) {
      SampledFieldGeometry sfg = (SampledFieldGeometry) contextObject;
      
      // keep order of elements for later validation
      AbstractReaderWriter.storeElementsOrder(elementName, contextObject);
      
      if (elementName.equals(SpatialConstants.listOfSampledVolumes)){
        ListOf<SampledVolume> listOfSampledVolumes = sfg.getListOfSampledVolumes();
        return listOfSampledVolumes;
      } else {
        logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
        return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
      }
    } else if (contextObject instanceof CSGeometry) {
      CSGeometry csg = (CSGeometry) contextObject;
      
      // keep order of elements for later validation
      AbstractReaderWriter.storeElementsOrder(elementName, contextObject);
      
      if (elementName.equals(SpatialConstants.listOfCSGObjects)){
        ListOf<CSGObject> listOfCSGObjects = csg.getListOfCSGObjects();
        return listOfCSGObjects;
      } else {
        logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
        return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
      }
    } else if (contextObject instanceof CSGObject) {
      CSGObject cso = (CSGObject) contextObject;
      
      // keep order of elements for later validation
      AbstractReaderWriter.storeElementsOrder(elementName, contextObject);
      
      if (elementName.equals(SpatialConstants.csgPrimitive)){
        CSGPrimitive csgNode = new CSGPrimitive();
        cso.setCSGNode(csgNode);
        return csgNode;
      } else if (elementName.equals(SpatialConstants.csgPseudoPrimitive)){
        CSGPseudoPrimitive csgNode = new CSGPseudoPrimitive();
        cso.setCSGNode(csgNode);
        return csgNode;
      } else if (elementName.equals(SpatialConstants.csgSetOperator)){
        CSGSetOperator csgNode = new CSGSetOperator();
        cso.setCSGNode(csgNode);
        return csgNode;
      } else if (elementName.equals(SpatialConstants.csgTranslation)){
        CSGTranslation csgNode = new CSGTranslation();
        cso.setCSGNode(csgNode);
        return csgNode;
      } else if (elementName.equals(SpatialConstants.csgRotation)){
        CSGRotation csgNode = new CSGRotation();
        cso.setCSGNode(csgNode);
        return csgNode;
      } else if (elementName.equals(SpatialConstants.csgScale)){
        CSGScale csgNode = new CSGScale();
        cso.setCSGNode(csgNode);
        return csgNode;
      } else if (elementName.equals(SpatialConstants.csgHomogeneousTransformation)){
        CSGHomogeneousTransformation csgNode = new CSGHomogeneousTransformation();
        cso.setCSGNode(csgNode);
        return csgNode;
      } else {
        logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
        return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
      }
    } else if (contextObject instanceof CSGScale) {
      CSGScale csgParent = (CSGScale) contextObject;
      return setCSGNode(csgParent, elementName);
    } else if (contextObject instanceof CSGRotation) {
      CSGRotation csgParent = (CSGRotation) contextObject;
      return setCSGNode(csgParent, elementName);
    } else if (contextObject instanceof CSGTranslation) {
      CSGTranslation csgParent = (CSGTranslation) contextObject;
      return setCSGNode(csgParent, elementName);
    } else if (contextObject instanceof CSGSetOperator) {
      CSGSetOperator csgso = (CSGSetOperator) contextObject;
      
      // keep order of elements for later validation
      AbstractReaderWriter.storeElementsOrder(elementName, contextObject);
      
      if (elementName.equals(SpatialConstants.listOfCSGNodes)) {
        ListOf<CSGNode> listOfCSGNodes = csgso.getListOfCSGNodes();
        return listOfCSGNodes;
      } else {
        logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
        return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
      }
    } else if (contextObject instanceof CSGHomogeneousTransformation) {
      CSGHomogeneousTransformation csght = (CSGHomogeneousTransformation) contextObject;
      
      // keep order of elements for later validation
      AbstractReaderWriter.storeElementsOrder(elementName, contextObject);
      
      if (elementName.equals(SpatialConstants.forwardTransformation)) {
        TransformationComponent tc = new TransformationComponent();
        csght.setForwardTransformation(tc);
        return tc;
      } else if (elementName.equals(SpatialConstants.reverseTransformation)) {
        TransformationComponent tc = new TransformationComponent();
        csght.setReverseTransformation(tc);
        return tc;
      } else {
        logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
        return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
      }
    } else if (contextObject instanceof ParametricGeometry) {
      ParametricGeometry pg = (ParametricGeometry) contextObject;
      
      // keep order of elements for later validation
      AbstractReaderWriter.storeElementsOrder(elementName, contextObject);
      
      if (elementName.equals(SpatialConstants.spatialPoints)){
        SpatialPoints sp = new SpatialPoints();
        pg.setSpatialPoints(sp);
        return sp;
      } else if (elementName.equals(SpatialConstants.listOfParametricObjects)){
        ListOf<ParametricObject> listOfParametricObjects = pg.getListOfParametricObjects();
        return listOfParametricObjects;
      } else {
        logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
        return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
      }
    } else if (contextObject instanceof MixedGeometry) {
      MixedGeometry mg = (MixedGeometry) contextObject;
      
      // keep order of elements for later validation
      AbstractReaderWriter.storeElementsOrder(elementName, contextObject);
      
      if (elementName.contentEquals(SpatialConstants.listOfOrdinalMappings)) {
        ListOf<OrdinalMapping> listOfOrdinalMappings = mg.getListOfOrdinalMappings();
        return listOfOrdinalMappings;
      } else if (elementName.equals(SpatialConstants.listOfGeometryDefinitions)) {
        ListOf<GeometryDefinition> listOfGeometryDefinitions = mg.getListOfGeometryDefinitions();
        return listOfGeometryDefinitions;
      } else {
        logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
        return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
      }
    } else if (contextObject instanceof ListOf<?>) {
      ListOf<SBase> listOf = (ListOf<SBase>) contextObject;
      
      // keep order of elements for later validation
      AbstractReaderWriter.storeElementsOrder(elementName, contextObject);
      
      if (elementName.equals(SpatialConstants.coordinateComponent)) {
        Geometry geo = (Geometry) listOf.getParentSBMLObject();
        CoordinateComponent elem = new CoordinateComponent();
        geo.addCoordinateComponent(elem);
        return elem;
      } else if (elementName.equals(SpatialConstants.domainType)) {
        Geometry geo = (Geometry) listOf.getParentSBMLObject();
        DomainType elem = new DomainType();
        geo.addDomainType(elem);
        return elem;
      } else if (elementName.equals(SpatialConstants.domain)) {
        Geometry geo = (Geometry) listOf.getParentSBMLObject();
        Domain elem = new Domain();
        geo.addDomain(elem);
        return elem;
      } else if (elementName.equals(SpatialConstants.adjacentDomains)) {
        Geometry geo = (Geometry) listOf.getParentSBMLObject();
        AdjacentDomains elem = new AdjacentDomains();
        geo.addAdjacentDomain(elem);
        return elem;
      } else if (elementName.equals(SpatialConstants.sampledFieldGeometry)) {
        SampledFieldGeometry elem = new SampledFieldGeometry();
        addGeometryDefinition((AbstractSpatialNamedSBase) listOf.getParentSBMLObject(), elem);
        return elem;
      } else if (elementName.equals(SpatialConstants.analyticGeometry)) {
        AnalyticGeometry elem = new AnalyticGeometry();
        addGeometryDefinition((AbstractSpatialNamedSBase) listOf.getParentSBMLObject(), elem);
        return elem;
      } else if (elementName.equals(SpatialConstants.mixedGeometry)) {
        MixedGeometry elem = new MixedGeometry();
        addGeometryDefinition((AbstractSpatialNamedSBase) listOf.getParentSBMLObject(), elem);
        return elem;
      } else if (elementName.equals(SpatialConstants.csGeometry)) {
        CSGeometry elem = new CSGeometry();
        addGeometryDefinition((AbstractSpatialNamedSBase) listOf.getParentSBMLObject(), elem);
        return elem;
      } else if (elementName.equals(SpatialConstants.parametricGeometry)) {
        ParametricGeometry elem = new ParametricGeometry();
        addGeometryDefinition((AbstractSpatialNamedSBase) listOf.getParentSBMLObject(), elem);
        return elem;
      } else if (elementName.equals(SpatialConstants.interiorPoint)) {
        Domain domain = (Domain) listOf.getParentSBMLObject();
        InteriorPoint elem = new InteriorPoint();
        domain.addInteriorPoint(elem);
        return elem;
      } else if (elementName.equals(SpatialConstants.analyticVolume)) {
        AnalyticGeometry av = (AnalyticGeometry) listOf.getParentSBMLObject();
        AnalyticVolume elem = new AnalyticVolume();
        av.addAnalyticVolume(elem);
        return elem;
      } else if (elementName.equals(SpatialConstants.sampledVolume)) {
        SampledFieldGeometry sfg = (SampledFieldGeometry) listOf.getParentSBMLObject();
        SampledVolume elem = new SampledVolume();
        sfg.addSampledVolume(elem);
        return elem;
      } else if (elementName.equals(SpatialConstants.sampledField)) {
        Geometry g = (Geometry) listOf.getParentSBMLObject();
        SampledField elem = new SampledField();
        g.addSampledField(elem);
        return elem;        
      } else if (elementName.equals(SpatialConstants.csgObject)) {
        CSGObject elem = new CSGObject();
        CSGeometry g = (CSGeometry) listOf.getParentSBMLObject();
        g.addCSGObject(elem);
        return elem;
      } else if (elementName.equals(SpatialConstants.csgPrimitive)) {
        CSGPrimitive elem = new CSGPrimitive();
        addCSGNode((AbstractSpatialNamedSBase) listOf.getParentSBMLObject(), elem);
        return elem;
      } else if (elementName.equals(SpatialConstants.csgPseudoPrimitive)) {
        CSGPseudoPrimitive elem = new CSGPseudoPrimitive();
        addCSGNode((AbstractSpatialNamedSBase) listOf.getParentSBMLObject(), elem);
        return elem;
      } else if (elementName.equals(SpatialConstants.csgSetOperator)) {
        CSGSetOperator elem = new CSGSetOperator();
        addCSGNode((AbstractSpatialNamedSBase) listOf.getParentSBMLObject(), elem);
        return elem;
      } else if (elementName.equals(SpatialConstants.csgTranslation)) {
        CSGTranslation elem = new CSGTranslation();
        addCSGNode((AbstractSpatialNamedSBase) listOf.getParentSBMLObject(), elem);
        return elem;
      } else if (elementName.equals(SpatialConstants.csgRotation)) {
        CSGRotation elem = new CSGRotation();
        addCSGNode((AbstractSpatialNamedSBase) listOf.getParentSBMLObject(), elem);
        return elem;
      } else if (elementName.equals(SpatialConstants.csgScale)) {
        CSGScale elem = new CSGScale();
        addCSGNode((AbstractSpatialNamedSBase) listOf.getParentSBMLObject(), elem);
        return elem;
      } else if (elementName.equals(SpatialConstants.csgHomogeneousTransformation)) {
        CSGHomogeneousTransformation elem = new CSGHomogeneousTransformation();
        addCSGNode((AbstractSpatialNamedSBase) listOf.getParentSBMLObject(), elem);
        return elem;
      } else if (elementName.equals(SpatialConstants.spatialPoints)) {
        ParametricGeometry pg = (ParametricGeometry) listOf.getParentSBMLObject();
        SpatialPoints elem = new SpatialPoints();
        pg.setSpatialPoints(elem);
        return elem;
      } else if (elementName.equals(SpatialConstants.parametricObject)) {
        ParametricGeometry pg = (ParametricGeometry) listOf.getParentSBMLObject();
        ParametricObject elem = new ParametricObject();
        pg.addParametricObject(elem);
        return elem;
      } else if (elementName.equals(SpatialConstants.ordinalMapping)) {
        MixedGeometry mg = (MixedGeometry) listOf.getParentSBMLObject();
        OrdinalMapping elem = new OrdinalMapping();
        mg.addOrdinalMapping(elem);
        return elem;
      } else {
        logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
        return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
      }
    }

    return contextObject;
  }
  
  /**
   * @param parent
   * @param geometryDefinition
   */
  private void addGeometryDefinition(AbstractSpatialNamedSBase parent, GeometryDefinition geometryDefinition) {
    
    if(parent instanceof Geometry) {
      Geometry geo = (Geometry) parent;
      geo.addGeometryDefinition(geometryDefinition);
    }
    else if(parent instanceof MixedGeometry) {
      MixedGeometry mg = (MixedGeometry) parent;
      mg.addGeometryDefinition(geometryDefinition);
    }
  }

  private CSGNode setCSGNode(CSGTransformation csgT, String elementName) {

    if (elementName.equals(SpatialConstants.csgPrimitive)){
      CSGPrimitive csgNode = new CSGPrimitive();
      csgT.setCSGNode(csgNode);
      return csgNode;
    } else if (elementName.equals(SpatialConstants.csgPseudoPrimitive)){
      CSGPseudoPrimitive csgNode = new CSGPseudoPrimitive();
      csgT.setCSGNode(csgNode);
      return csgNode;
    } else if (elementName.equals(SpatialConstants.csgSetOperator)){
      CSGSetOperator csgNode = new CSGSetOperator();
      csgT.setCSGNode(csgNode);
      return csgNode;
    } else if (elementName.equals(SpatialConstants.csgTranslation)){
      CSGTranslation csgNode = new CSGTranslation();
      csgT.setCSGNode(csgNode);
      return csgNode;
    } else if (elementName.equals(SpatialConstants.csgRotation)){
      CSGRotation csgNode = new CSGRotation();
      csgT.setCSGNode(csgNode);
      return csgNode;
    } else if (elementName.equals(SpatialConstants.csgScale)){
      CSGScale csgNode = new CSGScale();
      csgT.setCSGNode(csgNode);
      return csgNode;
    } else if (elementName.equals(SpatialConstants.csgHomogeneousTransformation)){
      CSGHomogeneousTransformation csgNode = new CSGHomogeneousTransformation();
      csgT.setCSGNode(csgNode);
      return csgNode;
    }
    
    // TODO - throw exception and/or print error messages
    
    return null;
  }

  /**
   * @param parent
   * @param csgNode
   */
  private void addCSGNode(AbstractSpatialNamedSBase parent, CSGNode csgNode) {
    
    if (parent.getParentSBMLObject() instanceof CSGTransformation) 
    {
      CSGTransformation csgso = (CSGTransformation) parent.getParentSBMLObject();
      csgso.setCSGNode(csgNode);
    }
    else if (parent instanceof CSGSetOperator) 
    {
      CSGSetOperator csgso = (CSGSetOperator) parent;
      csgso.addCSGNode(csgNode);
    }
    else if (parent.getParentSBMLObject() instanceof CSGObject) 
    {
      CSGObject csgso = (CSGObject) parent.getParentSBMLObject();
      csgso.setCSGNode(csgNode);
    }
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#processEndDocument(org.sbml.jsbml.SBMLDocument)
   */
  @Override
  public void processEndDocument(SBMLDocument sbmlDocument) {
    
    if(sbmlDocument.isSetModel() && sbmlDocument.getModel().getExtension(getShortLabel()) != null) {      
      Model model = sbmlDocument.getModel();
      
      // Get list of reactions to check if the plugin is added
      if(model.isSetListOfReactions()) {
        ListOf<Reaction> lor = model.getListOfReactions();
        
        // Add extension to the reaction if not already added 
        for(Reaction reaction : lor) {
          if(reaction.getExtension(SpatialConstants.namespaceURI) == null) {
            SpatialReactionPlugin spatialReaction = new SpatialReactionPlugin(reaction);
            reaction.addExtension(SpatialConstants.namespaceURI, spatialReaction);
          }
        }
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#getNamespaceFor(int, int, int)
   */
  @Override
  public String getNamespaceFor(int level, int version,	int packageVersion) {

    if (level == 3 && version == 1 && packageVersion == 1) {
      return SpatialConstants.namespaceURI_L3V1V1;
    }

    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#getNamespaces()
   */
  @Override
  public List<String> getNamespaces() {
    return SpatialConstants.namespaces;
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
    return SpatialConstants.shortLabel;
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
      if (sbase instanceof Model) {
        return new SpatialModelPlugin((Model) sbase);
      } else if (sbase instanceof Compartment) {
        return new SpatialCompartmentPlugin((Compartment) sbase);
      } else if (sbase instanceof Species) {
        return new SpatialSpeciesPlugin((Species) sbase);
      } else if (sbase instanceof Parameter) {
        return new SpatialParameterPlugin((Parameter) sbase);
      } else if (sbase instanceof Reaction) {
        return new SpatialReactionPlugin((Reaction) sbase);
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

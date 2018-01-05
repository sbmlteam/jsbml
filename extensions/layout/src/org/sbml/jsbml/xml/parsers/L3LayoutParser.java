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

import static org.sbml.jsbml.ext.layout.LayoutConstants.basePoint1;
import static org.sbml.jsbml.ext.layout.LayoutConstants.basePoint2;
import static org.sbml.jsbml.ext.layout.LayoutConstants.boundingBox;
import static org.sbml.jsbml.ext.layout.LayoutConstants.compartmentGlyph;
import static org.sbml.jsbml.ext.layout.LayoutConstants.curve;
import static org.sbml.jsbml.ext.layout.LayoutConstants.curveSegment;
import static org.sbml.jsbml.ext.layout.LayoutConstants.dimensions;
import static org.sbml.jsbml.ext.layout.LayoutConstants.end;
import static org.sbml.jsbml.ext.layout.LayoutConstants.generalGlyph;
import static org.sbml.jsbml.ext.layout.LayoutConstants.layout;
import static org.sbml.jsbml.ext.layout.LayoutConstants.listOfAdditionalGraphicalObjects;
import static org.sbml.jsbml.ext.layout.LayoutConstants.listOfCompartmentGlyphs;
import static org.sbml.jsbml.ext.layout.LayoutConstants.listOfCurveSegments;
import static org.sbml.jsbml.ext.layout.LayoutConstants.listOfLayouts;
import static org.sbml.jsbml.ext.layout.LayoutConstants.listOfReactionGlyphs;
import static org.sbml.jsbml.ext.layout.LayoutConstants.listOfReferenceGlyphs;
import static org.sbml.jsbml.ext.layout.LayoutConstants.listOfSpeciesGlyphs;
import static org.sbml.jsbml.ext.layout.LayoutConstants.listOfSpeciesReferenceGlyphs;
import static org.sbml.jsbml.ext.layout.LayoutConstants.listOfSubGlyphs;
import static org.sbml.jsbml.ext.layout.LayoutConstants.listOfTextGlyphs;
import static org.sbml.jsbml.ext.layout.LayoutConstants.position;
import static org.sbml.jsbml.ext.layout.LayoutConstants.reactionGlyph;
import static org.sbml.jsbml.ext.layout.LayoutConstants.referenceGlyph;
import static org.sbml.jsbml.ext.layout.LayoutConstants.speciesGlyph;
import static org.sbml.jsbml.ext.layout.LayoutConstants.speciesReferenceGlyph;
import static org.sbml.jsbml.ext.layout.LayoutConstants.start;
import static org.sbml.jsbml.ext.layout.LayoutConstants.textGlyph;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.mangosdk.spi.ProviderFor;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.ASTNodePlugin;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.layout.BoundingBox;
import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.CubicBezier;
import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.layout.Dimensions;
import org.sbml.jsbml.ext.layout.GeneralGlyph;
import org.sbml.jsbml.ext.layout.GraphicalObject;
import org.sbml.jsbml.ext.layout.IBoundingBox;
import org.sbml.jsbml.ext.layout.ICurve;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;
import org.sbml.jsbml.ext.layout.LineSegment;
import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.ReferenceGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.TextGlyph;
import org.sbml.jsbml.util.ResourceManager;
import org.sbml.jsbml.util.filters.Filter;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * This class is used to parse the layout extension package elements and
 * attributes. The namespaceURI URI of this parser is
 * "http://www.sbml.org/sbml/level3/version1/layout/version1". This parser is
 * able to read and write elements of the layout package (implements
 * ReadingParser and WritingParser).
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
@ProviderFor(ReadingParser.class)
public class L3LayoutParser extends AbstractReaderWriter implements PackageParser {

  /**
   * Localization support.
   */
  private static final transient ResourceBundle bundle = ResourceManager.getBundle("org.sbml.jsbml.resources.cfg.Messages");

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getNamespaceURI()
   */
  @Override
  public String getNamespaceURI() {
    return LayoutConstants.namespaceURI;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getShortLabel()
   */
  @Override
  public String getShortLabel() {
    return LayoutConstants.shortLabel;
  }

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(L3LayoutParser.class);

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.WritingParser#getListOfSBMLElementsToWrite(Object sbase)
   */
  @Override
  public List<Object> getListOfSBMLElementsToWrite(Object sbase) {

    if (logger.isDebugEnabled()) {
      logger.debug("getListOfSBMLElementsToWrite: " + sbase.getClass().getCanonicalName());
    }

    List<Object> listOfElementsToWrite = new ArrayList<Object>();

    if (sbase instanceof Model) {
      SBasePlugin modelPlugin = ((Model) sbase).getExtension(getNamespaceURI());

      if (modelPlugin != null) {
        listOfElementsToWrite = super.getListOfSBMLElementsToWrite(modelPlugin);
      }
    } else {
      listOfElementsToWrite = super.getListOfSBMLElementsToWrite(sbase);
    }

    return listOfElementsToWrite;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#processStartElement(java.lang.String, java.lang.String, boolean, boolean, java.lang.Object)
   */
  // Create the proper object and link it to his parent.
  @Override
  @SuppressWarnings("unchecked")
  public Object processStartElement(String elementName, String uri, String prefix,
    boolean hasAttributes, boolean hasNamespaces, Object contextObject)
  {
    if (contextObject instanceof Model) {
      Model model = (Model) contextObject;

      if (elementName.equals(listOfLayouts)) {

        LayoutModelPlugin layoutModel = new LayoutModelPlugin(model);
        model.addExtension(getNamespaceURI(), layoutModel);

        // keep order of elements for later validation
        AbstractReaderWriter.storeElementsOrder(getPackageName() + ":" + elementName, model);

        return layoutModel.getListOfLayouts();
      } else {
        logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
        return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
      }
    }
    else if (contextObject instanceof Layout) {
      Layout layout = (Layout) contextObject;
      SBase newElement = null;

      // keep order of elements for later validation
      AbstractReaderWriter.storeElementsOrder(elementName, contextObject);
      
      if (elementName.equals(listOfCompartmentGlyphs)) {

        newElement = layout.getListOfCompartmentGlyphs();
      }
      else if (elementName.equals(listOfSpeciesGlyphs)) {

        newElement = layout.getListOfSpeciesGlyphs();
      }
      else if (elementName.equals(listOfReactionGlyphs)) {

        newElement = layout.getListOfReactionGlyphs();
      }
      else if (elementName.equals(listOfTextGlyphs)) {

        newElement = layout.getListOfTextGlyphs();
      }
      else if (elementName.equals(listOfAdditionalGraphicalObjects)) {

        newElement = layout.getListOfAdditionalGraphicalObjects();
      }
      else if (elementName.equals(dimensions)) {
        Dimensions dimension = new Dimensions();
        layout.setDimensions(dimension);

        newElement = dimension;
      }  else {
        logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
        return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
      }

      if (newElement != null) {
        return newElement;
      }
    }
    else if (contextObject instanceof IBoundingBox) {
      IBoundingBox graphicalObject = (IBoundingBox) contextObject;

      // keep order of elements for later validation
      AbstractReaderWriter.storeElementsOrder(elementName, contextObject);      
      
      if (elementName.equals(boundingBox)) {
        BoundingBox bbox = new BoundingBox();
        graphicalObject.setBoundingBox(bbox);

        return bbox;
      }

      if (graphicalObject instanceof ReactionGlyph)
      {
        ReactionGlyph reactionGlyph = (ReactionGlyph) graphicalObject;

        if (elementName.equals(curve)) {
          Curve curve = new Curve();
          reactionGlyph.setCurve(curve);

          return curve;
        }
        else if (elementName.equals(listOfSpeciesReferenceGlyphs))
        {
          ListOf<SpeciesReferenceGlyph> list = reactionGlyph.getListOfSpeciesReferenceGlyphs();
          return list;
        } else {
          logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
          return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
        }
      }
      else if (graphicalObject instanceof SpeciesReferenceGlyph)
      {
        SpeciesReferenceGlyph speciesRefGlyph = (SpeciesReferenceGlyph) contextObject;

        if (elementName.equals(curve)) {
          Curve curve = new Curve();
          speciesRefGlyph.setCurve(curve);

          return curve;
        } else {
          logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
          return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
        }
      }
      else if (graphicalObject instanceof GeneralGlyph)
      {
        GeneralGlyph generalGlyph = (GeneralGlyph) graphicalObject;

        if (elementName.equals(curve)) {
          Curve curve = new Curve();
          generalGlyph.setCurve(curve);

          return curve;
        }
        else if (elementName.equals(listOfSubGlyphs))
        {
          ListOf<GraphicalObject> list = generalGlyph.getListOfSubGlyphs();
          return list;
        }
        else if (elementName.equals(listOfReferenceGlyphs))
        {
          ListOf<ReferenceGlyph> list = generalGlyph.getListOfReferenceGlyphs();
          return list;
        } else {
          logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
          return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
        }
      }
      else if (graphicalObject instanceof ReferenceGlyph)
      {
        ReferenceGlyph refGlyph = (ReferenceGlyph) contextObject;

        if (elementName.equals(curve)) {
          Curve curve = new Curve();
          refGlyph.setCurve(curve);

          return curve;
        } else {
          logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
          return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
        }
      } else {
        logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
        return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
      }
    }
    else if (contextObject instanceof BoundingBox)
    {
      BoundingBox bbox = (BoundingBox) contextObject;

      // keep order of elements for later validation
      AbstractReaderWriter.storeElementsOrder(elementName, contextObject);      
      
      if (elementName.equals(position)) {
        Point position = new Point();
        bbox.setPosition(position);

        return position;
      }
      else if (elementName.equals(dimensions)) {
        Dimensions dimension = new Dimensions();
        bbox.setDimensions(dimension);

        return dimension;
      } else {
        logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
        return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
      }
    }
    else if (contextObject instanceof ICurve) {
      ICurve curve = (ICurve) contextObject;
      SBase newElement = null;

      // keep order of elements for later validation
      AbstractReaderWriter.storeElementsOrder(elementName, contextObject);      

      if (elementName.equals(listOfCurveSegments)) {

        newElement = curve.getListOfCurveSegments();
      } else {
        logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
        return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
      }

      if (newElement != null) {
        return newElement;
      }
    }
    else if (contextObject instanceof CurveSegment) {
      CubicBezier curveSegment = (CubicBezier) contextObject;

      // keep order of elements for later validation
      AbstractReaderWriter.storeElementsOrder(elementName, contextObject);      

      if (elementName.equals(start)) {
        Point point = new Point();
        curveSegment.setStart(point);

        return point;
      }
      else if (elementName.equals(end)) {
        Point point = new Point();
        curveSegment.setEnd(point);

        return point;
      }
      else if (elementName.equals(basePoint1)) {
        Point point = new Point();
        curveSegment.setBasePoint1(point);

        return point;
      }
      else if (elementName.equals(basePoint2)) {
        Point point = new Point();
        curveSegment.setBasePoint2(point);

        return point;
      } else {
        logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
        return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
      }
    }
    else if (contextObject instanceof ListOf<?>) {
      ListOf<SBase> listOf = (ListOf<SBase>) contextObject;
      SBase newElement = null;

      if (elementName.equals(layout)) {
        newElement = new Layout();
      }
      else if (elementName.equals(compartmentGlyph)) {
        newElement = new CompartmentGlyph();
      }
      else if (elementName.equals(speciesGlyph)) {
        newElement = new SpeciesGlyph();
      }
      else if (elementName.equals(reactionGlyph)) {
        newElement = new ReactionGlyph();
      }
      else if (elementName.equals(textGlyph)) {
        newElement = new TextGlyph();
      }
      else if (elementName.equals(curveSegment) || elementName.equals(LayoutConstants.cubicBezier)
          || elementName.equals(LayoutConstants.lineSegment)) // to allow reading of not properly written XML, we add  "cubicBezier" and "lineSegment" here.
      {
        newElement = new CubicBezier(); //Always creating a CubicBezier instance until we know the exact 'type' of the curveElement !!
      }
      else if (elementName.equals(speciesReferenceGlyph)) {
        newElement = new SpeciesReferenceGlyph();
      }
      else if (elementName.equals(referenceGlyph)) {
        newElement = new ReferenceGlyph();
      }
      else if (elementName.equals(generalGlyph)) {
        newElement = new GeneralGlyph();
      }
      else if (elementName.equals(LayoutConstants.graphicalObject)) {
        newElement = new GraphicalObject();
      }
      else {
        logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
        return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
      }

      if (newElement != null) {
        try {
          listOf.add(newElement);
        } catch(ClassCastException e) {
          // as we do not test that we are using the right ListOf, we need to catch the exception here !
          if (logger.isDebugEnabled()) {
            logger.debug("Exception catched: " + e.getMessage());
          }
          logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
          return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
        } catch(RuntimeException e) {
          logger.warn("RuntimeException catched: " + e.getMessage());
        }
      }

      return newElement;

    }
    return contextObject;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#processEndDocument(org.sbml.jsbml.SBMLDocument)
   */
  @Override
  public void processEndDocument(SBMLDocument sbmlDocument) {
    if (sbmlDocument.isSetModel() && sbmlDocument.getModel().getExtension(getShortLabel()) != null)
    {
      // going through the document to find all Curve objects
      // filtering only on the ListOfLayouts
      List<? extends TreeNode> curveElements = sbmlDocument.getModel().getExtension(getShortLabel()).filter(new Filter() {
        /* (non-Javadoc)
         * @see org.sbml.jsbml.util.filters.Filter#accepts(java.lang.Object)
         */
        @Override
        public boolean accepts(Object o) {
          return o instanceof ICurve;
        }
      });

      for (TreeNode curveNode : curveElements) {
        ICurve curve = (ICurve) curveNode;

        // transform the CubicBezier into LineSegment when needed
        int i = 0;
        for (CurveSegment curveSegment : curve.getListOfCurveSegments().clone())
        {
          if (! curveSegment.isSetType())
          {
            if (((CubicBezier) curveSegment).isSetBasePoint1() || ((CubicBezier) curveSegment).isSetBasePoint2())
            {
              // trick to set the 'type' attribute, although the setType method is not visible.
              curveSegment.readAttribute("type", "", CurveSegment.Type.CUBIC_BEZIER.toString());
            }
            else
            {
              curveSegment.readAttribute("type", "", CurveSegment.Type.LINE_SEGMENT.toString());
            }
          }

          if (curveSegment.getType().equals(CurveSegment.Type.LINE_SEGMENT))
          {
            LineSegment realCurveSegment = new LineSegment(curveSegment);
            logger.debug("Transformed a CubicBezier: '" + curveSegment + "' into a LineSegment.");
            curve.getListOfCurveSegments().remove(i);
            curve.getListOfCurveSegments().add(i, realCurveSegment);
          }

          if (logger.isDebugEnabled())
          {
            logger.debug("CurveSegment = " + curve.getListOfCurveSegments().get(i));
          }

          i++;
        }
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#writeElement(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
   */
  @Override
  public void writeElement(SBMLObjectForXML xmlObject, Object sbmlElementToWrite) {
    super.writeElement(xmlObject, sbmlElementToWrite);

    String name = xmlObject.getName();

    if (name.equals(LayoutConstants.lineSegment) || name.equals(LayoutConstants.cubicBezier)) {
      xmlObject.setName(LayoutConstants.curveSegment);
    }

    if (name.equals(LayoutConstants.listOfLineSegments)) { // No such element defined in Layout package: || name.equals("listOfCubicBeziers")) {
      xmlObject.setName(LayoutConstants.listOfCurveSegments);
    }

    if (name.equals(listOfLayouts)) {
      xmlObject.getAttributes().put("xmlns:" + LayoutConstants.xsiShortLabel, LayoutConstants.xsiNamespace);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#getNamespaces()
   */
  @Override
  public List<String> getNamespaces() {
    return LayoutConstants.all_L3_namespaces;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#getPackageNamespaces()
   */
  @Override
  public List<String> getPackageNamespaces() {
    return LayoutConstants.namespaces_L3;
  }

  /**
   * 
   */
  private static final List<String> namespaces = new ArrayList<String>();

  static {
    namespaces.addAll(LayoutConstants.namespaces_L3);
    namespaces.add("http://www.w3.org/2001/XMLSchema-instance");
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#getNamespaceFor(int, int, int)
   */
  @Override
  public String getNamespaceFor(int level, int version, int packageVersion) {

    if ((level == 3) && (version == 1) && (packageVersion == 1)) {
      return LayoutConstants.namespaceURI_L3V1V1;
    }

    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#getPackageName()
   */
  @Override
  public String getPackageName() {
    return LayoutConstants.shortLabel;
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
        return new LayoutModelPlugin((Model) sbase);
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

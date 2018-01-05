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

import static org.sbml.jsbml.ext.layout.LayoutConstants.listOfLayouts;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.mangosdk.spi.ProviderFor;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.JSBML;
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
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;
import org.sbml.jsbml.ext.layout.LineSegment;
import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.TextGlyph;
import org.sbml.jsbml.util.filters.Filter;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * Parses the SBML level 2 layout extension package elements and
 * attributes. The namespaceURI URI of this parser is
 * "http://projects.eml.org/bcb/sbml/level2". This parser is
 * able to read and write elements of the layout package (implements
 * ReadingParser and WritingParser).
 * 
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * 
 */
@ProviderFor(ReadingParser.class)
public class LayoutParser implements ReadingParser, WritingParser, PackageParser {

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(LayoutParser.class);

  /**
   * 
   */
  enum LayoutList {
    /**
     * 
     */
    listOfLayouts,
    /**
     * 
     */
    listOfSpeciesGlyphs,
    /**
     * 
     */
    listOfReactionGlyphs,
    /**
     * 
     */
    listOfTextGlyphs,
    /**
     * 
     */
    listOfCompartmentGlyphs,
    /**
     * 
     */
    none,
    /**
     * 
     */
    listOfCurveSegments,
    /**
     * 
     */
    listOfSpeciesReferenceGlyphs;
  }

  /**
   * The namespace URI of this parser.
   */
  private static final String namespaceURI = LayoutConstants.namespaceURI_L2;

  /**
   * 
   * @return the namespaceURI of this parser.
   */
  public static String getNamespaceURI() {
    return namespaceURI;
  }

  /**
   * The layoutList enum which represents the name of the list this parser is
   * currently reading.
   * 
   */
  private LayoutList groupList = LayoutList.none;

  /**
   * Log4j logger
   */
  private Logger log4jLogger = Logger.getLogger(LayoutParser.class);

  /**
   * This map contains all the relationships XML element name <=> matching
   * java class.
   */
  private HashMap<String, Class<? extends Object>> sbmlLayoutElements;

  /**
   * 
   */
  public LayoutParser() {
    super();
    sbmlLayoutElements = new HashMap<String, Class<? extends Object>>();
    JSBML.loadClasses(
      "org/sbml/jsbml/resources/cfg/SBMLLayoutElements.xml",
      sbmlLayoutElements);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.WritingParser#getListOfSBMLElementsToWrite(java.lang.Object)
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<Object> getListOfSBMLElementsToWrite(Object sbase) {
    log4jLogger.debug(MessageFormat.format("{0}: getListOfSBMLElementsToWrite\n",
      getClass().getSimpleName()));

    List<Object> listOfElementsToWrite = new LinkedList<Object>();

    if (sbase instanceof SBMLDocument) {
      // nothing to do
    } else if (sbase instanceof Model) {
      LayoutModelPlugin layoutModel = (LayoutModelPlugin) ((Model) sbase)
          .getExtension(namespaceURI);

      if (layoutModel != null && layoutModel.isSetListOfLayouts()) {
        listOfElementsToWrite.add(layoutModel.getListOfLayouts());
      }

    } else if (sbase instanceof TreeNode) {
      Enumeration<?> children = ((TreeNode) sbase).children();
      while (children.hasMoreElements()) {
        listOfElementsToWrite.add(children.nextElement());
      }
    }

    if (sbase instanceof SBase) {
      SBase elem = (SBase) sbase;
      log4jLogger.debug("add to write: " + elem.getElementName()
      + " namespace: " + elem.getNamespace().toString());
      if (sbase instanceof ListOf<?>) {
        log4jLogger.debug("process a ListOf instance");
        ListOf<SBase> listOf = (ListOf<SBase>) sbase;
        if (!listOf.isEmpty()) {
          listOfElementsToWrite = new LinkedList<Object>();
          for (int i = 0; i < listOf.size(); i++) {
            SBase element = listOf.get(i);
            if (element != null) {
              listOfElementsToWrite.add(element);
            }
          }
        }
      } else if (sbase instanceof Layout) {
        Layout layout = (Layout) sbase;

        if (layout.isSetListOfSpeciesGlyphs()) {
          listOfElementsToWrite.add(layout.getListOfSpeciesGlyphs());
          ListOf<SpeciesGlyph> listOfSpeciesGlyph = layout
              .getListOfSpeciesGlyphs();
          log4jLogger.debug("found list of species glyph");
          log4jLogger.debug("list of species glyph: "
              + listOfSpeciesGlyph.getElementName());
        } else if (layout.isSetListOfCompartmentGlyphs()) {
          listOfElementsToWrite.add(layout
            .getListOfCompartmentGlyphs());
        } else if (layout.isSetListOfReactionGlyphs()) {
          listOfElementsToWrite.add(layout.getListOfReactionGlyphs());
        } else if (layout.isSetListOfTextGlyphs()) {
          listOfElementsToWrite.add(layout.getListOfTextGlyphs());
        }
      } else if (sbase instanceof Point) {
        Point point = (Point) sbase;
        SBase parent = point.getParentSBMLObject();
        if (parent instanceof CurveSegment) {
          log4jLogger.debug(" curveSegment: point element name: "
              + point.getElementName());
        }
      }
    }

    if (listOfElementsToWrite.isEmpty()) {
      listOfElementsToWrite = null;
    }

    return listOfElementsToWrite;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.WritingParser#writeAttributes(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
   */
  @Override
  public void writeAttributes(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite) {
    if (sbmlElementToWrite instanceof SBase) {
      SBase sbase = (SBase) sbmlElementToWrite;
      xmlObject.addAttributes(sbase.writeXMLAttributes());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.WritingParser#writeCharacters(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
   */
  @Override
  public void writeCharacters(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite) {
    // The SBML core XML element should not have any content, everything should be stored as attribute.
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.WritingParser#writeElement(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
   */
  @Override
  public void writeElement(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite)
  {
    if (sbmlElementToWrite instanceof SBase) {
      SBase sbase = (SBase) sbmlElementToWrite;

      if (log4jLogger.isDebugEnabled())
      {
        log4jLogger.debug(MessageFormat.format("{0} ", sbase.getElementName()));
      }

      if (!xmlObject.isSetName()) {
        xmlObject.setName(sbase.getElementName());
      }
      if (!xmlObject.isSetPrefix()) {
        xmlObject.setPrefix(LayoutConstants.layout);
      }
      xmlObject.setNamespace(namespaceURI);

      String name = xmlObject.getName();

      if (name.equals(LayoutConstants.lineSegment) || name.equals(LayoutConstants.cubicBezier))
      {
        xmlObject.setName(LayoutConstants.curveSegment);
      }

      if (name.equals(LayoutConstants.listOfLineSegments)) // No such list defined in Layout package: name.equals("listOfCubicBeziers"))
      {
        xmlObject.setName(LayoutConstants.listOfCurveSegments);
      }

      if (xmlObject.getName().equals(listOfLayouts))
      {
        xmlObject.getAttributes().put("xmlns:" + LayoutConstants.xsiShortLabel, LayoutConstants.xsiNamespace);
      }

    }

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.WritingParser#writeNamespaces(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
   */
  @Override
  public void writeNamespaces(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite) {}

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#processAttribute(java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.Object)
   */
  @Override
  public boolean processAttribute(String elementName, String attributeName,
    String value, String uri, String prefix, boolean isLastAttribute,
    Object contextObject) {
    log4jLogger.debug("processAttribute\n");

    boolean isAttributeRead = false;

    if (contextObject instanceof SBase) {

      SBase sbase = (SBase) contextObject;

      log4jLogger.debug("processAttribute: level, version = "
          + sbase.getLevel() + ", " + sbase.getVersion());

      try {
        isAttributeRead = sbase.readAttribute(attributeName, prefix,
          value);
      } catch (Throwable exc) {
        System.err.println(exc.getMessage());
      }
    } else if (contextObject instanceof Annotation) {
      Annotation annotation = (Annotation) contextObject;
      isAttributeRead = annotation.readAttribute(attributeName, prefix,
        value);
    } else if (contextObject instanceof SBasePlugin) {
      isAttributeRead = ((SBasePlugin) contextObject).readAttribute(
        attributeName, prefix, value);
    }

    if (!isAttributeRead) {
      log4jLogger.warn("processAttribute: The attribute "
          + attributeName + " on the element " + elementName
          + " is not part of the SBML specifications");
    }

    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#processCharactersOf(java.lang.String, java.lang.String, java.lang.Object)
   */
  @Override
  public void processCharactersOf(String elementName, String characters,
    Object contextObject) {}

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#processEndDocument(org.sbml.jsbml.SBMLDocument)
   */
  @Override
  public void processEndDocument(SBMLDocument sbmlDocument)
  {
    if (sbmlDocument.isSetModel() && sbmlDocument.getModel().getExtension(namespaceURI) != null)
    {
      // going through the document to find all Curve objects
      // filtering only on the ListOfLayouts
      List<? extends TreeNode> curveElements = sbmlDocument.getModel().getExtension(namespaceURI).filter(new Filter() {

        /* (non-Javadoc)
         * @see org.sbml.jsbml.util.filters.Filter#accepts(java.lang.Object)
         */
        @Override
        public boolean accepts(Object o)
        {
          if (o instanceof Curve)
          {
            return true;
          }

          return false;
        }
      });

      for (TreeNode curveNode : curveElements)
      {
        Curve curve = (Curve) curveNode;

        // transform the CubicBezier into LineSegment
        int i = 0;
        for (CurveSegment curveSegment : curve.getListOfCurveSegments().clone())
        {
          // Making sure the type attribute is set
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
            setNamespace(realCurveSegment, namespaceURI);
            logger.debug("Transformed CubicBezier: " + curveSegment + " into LineSegment.");
            curve.getListOfCurveSegments().remove(i);
            curve.getListOfCurveSegments().add(i, realCurveSegment);
          }

          if (logger.isDebugEnabled())
          {
            logger.debug("Transformed CurveSegment: realCurveSegment = " + curve.getListOfCurveSegments().get(i));
          }

          i++;
        }
      }
    }
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#processNamespace(java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, boolean, java.lang.Object)
   */
  @Override
  public void processNamespace(String elementName, String URI, String prefix,
    String localName, boolean hasAttributes, boolean isLastNamespace,
    Object contextObject) {}

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#processStartElement(java.lang.String, java.lang.String, boolean, boolean, java.lang.Object)
   */
  @Override
  @SuppressWarnings("unchecked")
  public Object processStartElement(String elementName, String uri, String prefix,
    boolean hasAttributes, boolean hasNamespaces, Object contextObject) {
    if (elementName.equals(LayoutConstants.basePoint1)) {
      log4jLogger.debug("processing basePoint1");
    }
    if (sbmlLayoutElements.containsKey(elementName)) {
      try {
        Object newContextObject = sbmlLayoutElements.get(elementName).newInstance();

        if (contextObject instanceof Annotation) {
          Annotation annotation = (Annotation) contextObject;

          Model model = (Model) annotation.getParent();
          LayoutModelPlugin layoutModel = null;

          if (model.getExtension(namespaceURI) != null) {
            layoutModel = (LayoutModelPlugin) model
                .getExtension(namespaceURI);
          } else {
            layoutModel = new LayoutModelPlugin(model);
            model.addExtension(namespaceURI, layoutModel);
          }
        }
        if (contextObject instanceof SBase) {
          setLevelAndVersionFor(newContextObject,
            (SBase) contextObject);
        }
        if (contextObject instanceof Annotation) {
          Annotation annotation = (Annotation) contextObject;
          if (elementName.equals(LayoutConstants.listOfLayouts)) {
            ListOf<Layout> listOfLayouts = (ListOf<Layout>) newContextObject;
            listOfLayouts.setSBaseListType(ListOf.Type.other);
            setNamespace(listOfLayouts, namespaceURI);
            groupList = LayoutList.listOfLayouts;
            Model model = (Model) annotation.getParent();
            LayoutModelPlugin layoutModel = (LayoutModelPlugin) model
                .getExtension(namespaceURI);
            layoutModel.setListOfLayouts(listOfLayouts);
            return listOfLayouts;
          } else {
            log4jLogger.warn(MessageFormat.format(
              "Element {0} not recognized!", elementName));
          }
        } else if (contextObject instanceof ListOf<?>) {
          ListOf<?> listOf = (ListOf<?>) contextObject;
          if (listOf.getParentSBMLObject() instanceof Model) {
            if (elementName.equals(LayoutConstants.layout)
                && groupList.equals(LayoutList.listOfLayouts)) {
              ListOf<Layout> listOflayouts = (ListOf<Layout>) listOf;
              Layout layout = (Layout) newContextObject;
              setNamespace(layout, namespaceURI);
              listOflayouts.add(layout);
              return layout;
            }
          } else if (listOf.getParentSBMLObject() instanceof Layout) {
            if (elementName.equals(LayoutConstants.compartmentGlyph)
                && groupList.equals(LayoutList.listOfCompartmentGlyphs)) {
              ListOf<CompartmentGlyph> listOfCompartmentGlyph = (ListOf<CompartmentGlyph>) contextObject;
              CompartmentGlyph compartmentGlyph = (CompartmentGlyph) newContextObject;
              setNamespace(listOfCompartmentGlyph, namespaceURI);
              setNamespace(compartmentGlyph, namespaceURI);
              listOfCompartmentGlyph.add(compartmentGlyph);
              groupList = LayoutList.listOfCompartmentGlyphs;
              return compartmentGlyph;
            } else if (elementName.equals(LayoutConstants.textGlyph)
                && groupList.equals(LayoutList.listOfTextGlyphs)) {
              ListOf<TextGlyph> listOfTextGlyph = (ListOf<TextGlyph>) contextObject;
              TextGlyph textGlyph = (TextGlyph) newContextObject;
              setNamespace(listOfTextGlyph, namespaceURI);
              setNamespace(textGlyph, namespaceURI);
              listOfTextGlyph.add(textGlyph);
              groupList = LayoutList.listOfTextGlyphs;
              return textGlyph;
            } else if (elementName.equals(LayoutConstants.speciesGlyph)
                && groupList.equals(LayoutList.listOfSpeciesGlyphs)) {
              ListOf<SpeciesGlyph> listOfSpeciesGlyph = (ListOf<SpeciesGlyph>) contextObject;
              SpeciesGlyph speciesGlyph = (SpeciesGlyph) newContextObject;
              setNamespace(listOfSpeciesGlyph, namespaceURI);
              setNamespace(speciesGlyph, namespaceURI);
              listOfSpeciesGlyph.add(speciesGlyph);
              groupList = LayoutList.listOfSpeciesGlyphs;
              return speciesGlyph;
            } else if (elementName.equals(LayoutConstants.reactionGlyph)
                && groupList.equals(LayoutList.listOfReactionGlyphs)) {
              ListOf<ReactionGlyph> listOfReactionGlyph = (ListOf<ReactionGlyph>) contextObject;
              ReactionGlyph reactionGlyph = (ReactionGlyph) newContextObject;
              setNamespace(listOfReactionGlyph, namespaceURI);
              setNamespace(reactionGlyph, namespaceURI);
              listOfReactionGlyph.add(reactionGlyph);
              groupList = LayoutList.listOfReactionGlyphs;
              return reactionGlyph;
            } else if (elementName.equals(LayoutConstants.boundingBox)
                && groupList.equals(LayoutList.listOfCompartmentGlyphs)) {
              CompartmentGlyph compartmentGlyph = (CompartmentGlyph) contextObject;
              BoundingBox boundingBox = (BoundingBox) newContextObject;
              setNamespace(boundingBox, namespaceURI);
              compartmentGlyph.setBoundingBox(boundingBox);
              return boundingBox;
            }
          } else if (listOf.getParentSBMLObject() instanceof ReactionGlyph) {
            if (elementName.equals(LayoutConstants.speciesReferenceGlyph)
                && groupList.equals(LayoutList.listOfSpeciesReferenceGlyphs)) {
              SpeciesReferenceGlyph speciesReferenceGlyph = (SpeciesReferenceGlyph) newContextObject;
              setNamespace(speciesReferenceGlyph, namespaceURI);
              ListOf<SpeciesReferenceGlyph> listOfSpeciesReferenceGlyph = (ListOf<SpeciesReferenceGlyph>) contextObject;
              listOfSpeciesReferenceGlyph
              .add(speciesReferenceGlyph);
              return speciesReferenceGlyph;
            }
          } else if (elementName.equals(LayoutConstants.curveSegment)
              && groupList.equals(LayoutList.listOfCurveSegments)) {
            ListOf<CurveSegment> listOfLineSegment = (ListOf<CurveSegment>) contextObject;
            CubicBezier lineSegment = (CubicBezier) newContextObject;
            setNamespace(lineSegment, namespaceURI);
            listOfLineSegment.add(lineSegment);
            groupList = LayoutList.listOfCurveSegments;
            return lineSegment;
          } else if (listOf.getParentSBMLObject() instanceof Curve) {

            if (elementName.equals(LayoutConstants.curveSegment) || elementName.equals(LayoutConstants.cubicBezier)
                || elementName.equals(LayoutConstants.lineSegment))
            {
              ListOf<CurveSegment> listOfLineSegment = (ListOf<CurveSegment>) contextObject;
              CubicBezier lineSegment = (CubicBezier) newContextObject;
              setNamespace(lineSegment, namespaceURI);
              listOfLineSegment.add(lineSegment);
              return lineSegment;
            }
          }

        } else if (contextObject instanceof Layout) {
          Layout layout = (Layout) contextObject;
          groupList = LayoutList.listOfLayouts;
          if (elementName.equals(LayoutConstants.dimensions)
              && groupList.equals(LayoutList.listOfLayouts)) {
            Dimensions dimensions = (Dimensions) newContextObject;
            setNamespace(dimensions, namespaceURI);
            layout.setDimensions(dimensions);
            return dimensions;
          } else if (elementName.equals(LayoutConstants.listOfCompartmentGlyphs)
              && groupList.equals(LayoutList.listOfLayouts)) {
            ListOf<CompartmentGlyph> listOfCompartmentGlyphs = (ListOf<CompartmentGlyph>) newContextObject;
            listOfCompartmentGlyphs.setSBaseListType(ListOf.Type.other);
            layout.setListOfCompartmentGlyphs(listOfCompartmentGlyphs);
            groupList = LayoutList.listOfCompartmentGlyphs;
            return listOfCompartmentGlyphs;
          } else if (elementName.equals(LayoutConstants.listOfSpeciesGlyphs)
              && groupList.equals(LayoutList.listOfLayouts)) {
            ListOf<SpeciesGlyph> listofSpeciesGlyph = (ListOf<SpeciesGlyph>) newContextObject;
            listofSpeciesGlyph.setSBaseListType(ListOf.Type.other);
            layout.setListOfSpeciesGlyphs(listofSpeciesGlyph);
            groupList = LayoutList.listOfSpeciesGlyphs;
            return listofSpeciesGlyph;
          } else if (elementName.equals(LayoutConstants.listOfReactionGlyphs)
              && groupList.equals(LayoutList.listOfLayouts)) {
            ListOf<ReactionGlyph> listOfReactionGlyphs = (ListOf<ReactionGlyph>) newContextObject;
            listOfReactionGlyphs.setSBaseListType(ListOf.Type.other);
            layout.setListOfReactionGlyphs(listOfReactionGlyphs);
            groupList = LayoutList.listOfReactionGlyphs;
            return listOfReactionGlyphs;
          } else if (elementName.equals(LayoutConstants.listOfTextGlyphs)
              && groupList.equals(LayoutList.listOfLayouts)) {
            ListOf<TextGlyph> listOfTextGlyphs = (ListOf<TextGlyph>) newContextObject;
            listOfTextGlyphs.setSBaseListType(ListOf.Type.other);
            layout.setListOfTextGlyphs(listOfTextGlyphs);
            groupList = LayoutList.listOfTextGlyphs;
            return listOfTextGlyphs;
          }
        } else if (contextObject instanceof ReactionGlyph) {
          ReactionGlyph reactionGlyph = (ReactionGlyph) contextObject;
          if (elementName.equals(LayoutConstants.curve)
              && groupList.equals(LayoutList.listOfReactionGlyphs)) {
            Curve curve = (Curve) newContextObject;
            setNamespace(curve, namespaceURI);
            reactionGlyph.setCurve(curve);
            return curve;
          } else if (elementName.equals(LayoutConstants.listOfSpeciesReferenceGlyphs)
              && groupList.equals(LayoutList.listOfReactionGlyphs)) {
            ListOf<SpeciesReferenceGlyph> listOfSpeciesReferenceGlyphs = (ListOf<SpeciesReferenceGlyph>) newContextObject;
            listOfSpeciesReferenceGlyphs.setSBaseListType(ListOf.Type.other);
            reactionGlyph.setListOfSpeciesReferenceGlyphs(listOfSpeciesReferenceGlyphs);
            groupList = LayoutList.listOfSpeciesReferenceGlyphs;
            return listOfSpeciesReferenceGlyphs;
          } else if (elementName.equals(LayoutConstants.boundingBox)) {
            BoundingBox boundingBox = (BoundingBox) newContextObject;
            setNamespace(boundingBox, namespaceURI);
            reactionGlyph.setBoundingBox(boundingBox);
            return boundingBox;
          }
        } else if (contextObject instanceof SpeciesGlyph) {
          SpeciesGlyph speciesGlyph = (SpeciesGlyph) contextObject;
          if (elementName.equals(LayoutConstants.boundingBox)) {
            BoundingBox boundingBox = (BoundingBox) newContextObject;
            setNamespace(boundingBox, namespaceURI);
            speciesGlyph.setBoundingBox(boundingBox);
            return boundingBox;
          }
        } else if (contextObject instanceof CompartmentGlyph) {
          if (elementName.equals(LayoutConstants.boundingBox)
              && groupList.equals(LayoutList.listOfCompartmentGlyphs)) {
            CompartmentGlyph compartmentGlyph = (CompartmentGlyph) contextObject;
            BoundingBox boundingBox = (BoundingBox) newContextObject;
            setNamespace(boundingBox, namespaceURI);
            compartmentGlyph.setBoundingBox(boundingBox);
            return boundingBox;
          }
        } else if (contextObject instanceof TextGlyph) {
          if (elementName.equals(LayoutConstants.boundingBox)
              && groupList.equals(LayoutList.listOfTextGlyphs)) {
            TextGlyph textGlyph = (TextGlyph) contextObject;
            BoundingBox boundingBox = (BoundingBox) newContextObject;
            setNamespace(boundingBox, namespaceURI);
            textGlyph.setBoundingBox(boundingBox);
            return boundingBox;
          }
        } else if (contextObject instanceof Curve) {
          if (elementName.equals(LayoutConstants.listOfCurveSegments)
              && groupList.equals(LayoutList.listOfReactionGlyphs)) {
            Curve curve = (Curve) contextObject;
            ListOf<CurveSegment> listOfCurveSegments = (ListOf<CurveSegment>) newContextObject;
            listOfCurveSegments.setSBaseListType(ListOf.Type.other);
            setNamespace(listOfCurveSegments, namespaceURI);
            curve.setListOfCurveSegments(listOfCurveSegments);
            groupList = LayoutList.listOfReactionGlyphs;
            return listOfCurveSegments;
          } else if (elementName.equals(LayoutConstants.listOfCurveSegments)
              && groupList.equals(LayoutList.listOfSpeciesReferenceGlyphs)) {
            Curve curve = (Curve) contextObject;
            ListOf<CurveSegment> listOfCurveSegments = (ListOf<CurveSegment>) newContextObject;
            listOfCurveSegments.setSBaseListType(ListOf.Type.other);
            setNamespace(listOfCurveSegments, namespaceURI);
            curve.setListOfCurveSegments(listOfCurveSegments);
            groupList = LayoutList.listOfSpeciesReferenceGlyphs;
            return listOfCurveSegments;
          }
        } else if (contextObject instanceof BoundingBox) {
          BoundingBox boundingBox = (BoundingBox) contextObject;
          if (elementName.equals(LayoutConstants.position)) {
            Point point = (Point) newContextObject;
            setNamespace(point, namespaceURI);
            boundingBox.setPosition(point);
            return point;
          } else if (elementName.equals(LayoutConstants.dimensions)) {
            Dimensions dimensions = (Dimensions) newContextObject;
            setNamespace(dimensions, namespaceURI);
            boundingBox.setDimensions(dimensions);
            return dimensions;
          }
        } else if (contextObject instanceof CurveSegment) {
          if (elementName.equals(LayoutConstants.start)) {
            CurveSegment lineSegment = (CurveSegment) contextObject;
            Point start = (Point) newContextObject;
            lineSegment.setStart(start);
            setNamespace(start, namespaceURI);
            return start;
          } else if (elementName.equals(LayoutConstants.end)) {
            CurveSegment lineSegment = (CurveSegment) contextObject;
            Point end = (Point) newContextObject;
            lineSegment.setEnd(end);
            setNamespace(end, namespaceURI);
            return end;
          } else if (elementName.equals(LayoutConstants.basePoint1)) {
            CubicBezier lineSegment = (CubicBezier) contextObject;

            Point basePoint1 = (Point) newContextObject;
            lineSegment.setBasePoint1(basePoint1);
            setNamespace(basePoint1, namespaceURI);
            return basePoint1;
          } else if (elementName.equals(LayoutConstants.basePoint2)) {
            CubicBezier lineSegment = (CubicBezier) contextObject;
            Point basePoint2 = (Point) newContextObject;
            lineSegment.setBasePoint2(basePoint2);
            setNamespace(basePoint2, namespaceURI);
            return basePoint2;
          }
        } else if (contextObject instanceof SpeciesReferenceGlyph
            && groupList.equals(LayoutList.listOfSpeciesReferenceGlyphs)) {
          SpeciesReferenceGlyph speciesReferenceGlyph = (SpeciesReferenceGlyph) contextObject;
          if (elementName.equals(LayoutConstants.curve)) {
            Curve curve = (Curve) newContextObject;
            speciesReferenceGlyph.setCurve(curve);
            setNamespace(curve, namespaceURI);
            return curve;
          } else if (elementName.equals(LayoutConstants.boundingBox)) {
            BoundingBox boundingBox = (BoundingBox) newContextObject;
            setNamespace(boundingBox, namespaceURI);
            speciesReferenceGlyph.setBoundingBox(boundingBox);
            return boundingBox;
          }
        } else {
          log4jLogger.info("Tag " + elementName
            + " could not be recognized.");
          log4jLogger.info("contextObject: "
              + contextObject.toString());
          log4jLogger.info("newContextObject: "
              + newContextObject.toString());
        }
      } catch (Exception e) {
        log4jLogger.error(e.getLocalizedMessage(), e);
      }
    }
    return contextObject;
  }

  /**
   * 
   * @param sbase
   * @param namespace
   */
  private void setNamespace(SBase sbase, String namespace)
  {
    // Setting the correct namespace to the object
    // Will allow to read older packages when we have several versions for each packages.
    ((AbstractSBase) sbase).setNamespace(namespace);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#processEndElement(java.lang.String, java.lang.String, boolean, java.lang.Object)
   */
  @Override
  public boolean processEndElement(String elementName, String prefix,
    boolean isNested, Object contextObject)
  {
    if (log4jLogger.isDebugEnabled())
    {
      log4jLogger.debug("contextObject: " + contextObject.getClass().getName());
      log4jLogger.debug("elementName: " + elementName);
    }

    if (elementName.equals(LayoutConstants.listOfLayouts)
        || elementName.equals(LayoutConstants.listOfSpeciesGlyphs)
        || elementName.equals(LayoutConstants.listOfReactionGlyphs)
        || elementName.equals(LayoutConstants.listOfCompartmentGlyphs)
        || elementName.equals(LayoutConstants.listOfTextGlyphs)) {
      groupList = LayoutList.none;
      log4jLogger.debug("set listType to: none");
    } else if (elementName.equals(LayoutConstants.listOfSpeciesReferenceGlyphs)) {
      groupList = LayoutList.listOfReactionGlyphs;
      log4jLogger.debug("set listType to: " + LayoutConstants.listOfReactionGlyphs);
    }

    return true;
  }

  /**
   * Sets level and version properties of the new object according to the
   * value in the model.
   * 
   * @param newContextObject
   * @param parent
   */
  private void setLevelAndVersionFor(Object newContextObject, SBase parent) {
    if (newContextObject instanceof SBase) {
      SBase sb = (SBase) newContextObject;
      // Level and version will be -1 if not set, so we don't
      // have to check.
      sb.setLevel(parent.getLevel());
      sb.setVersion(parent.getVersion());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#getNamespaces()
   */
  @Override
  public List<String> getNamespaces() {
    return LayoutConstants.namespaces_L2;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#createPluginFor(org.sbml.jsbml.SBase)
   */
  @Override
  public SBasePlugin createPluginFor(SBase sbase) {

    if (sbase != null) {
      if (sbase instanceof Model) {
        LayoutModelPlugin modelPlugin = new LayoutModelPlugin((Model) sbase);
        // set the proper namespace ??
        return modelPlugin;
      }
    }

    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#getNamespaceFor(int, int, int)
   */
  @Override
  public String getNamespaceFor(int level, int version, int packageVersion) {
    if (level < 3) {
      return LayoutConstants.namespaceURI_L2;
    }
    throw new IllegalArgumentException("");
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
    // We have to set a different packageName than the L3LayoutParser so that the map in the ParserManager works properly
    return LayoutConstants.shortLabel + "_L2";
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#isRequired()
   */
  @Override
  public boolean isRequired() {
    return false;
  }

  @Override
  public ASTNodePlugin createPluginFor(ASTNode astNode) {
    // This package does not extends ASTNode
    return null;
  }

}

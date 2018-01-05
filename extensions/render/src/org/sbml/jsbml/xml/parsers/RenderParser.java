/*
 *
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

import java.util.List;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.mangosdk.spi.ProviderFor;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.ASTNodePlugin;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.layout.BoundingBox;
import org.sbml.jsbml.ext.layout.GraphicalObject;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;
import org.sbml.jsbml.ext.render.ColorDefinition;
import org.sbml.jsbml.ext.render.GlobalRenderInformation;
import org.sbml.jsbml.ext.render.GradientBase;
import org.sbml.jsbml.ext.render.GradientStop;
import org.sbml.jsbml.ext.render.LineEnding;
import org.sbml.jsbml.ext.render.LinearGradient;
import org.sbml.jsbml.ext.render.LocalRenderInformation;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Polygon;
import org.sbml.jsbml.ext.render.RadialGradient;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.ext.render.RenderCubicBezier;
import org.sbml.jsbml.ext.render.RenderCurve;
import org.sbml.jsbml.ext.render.RenderCurveSegment;
import org.sbml.jsbml.ext.render.RenderGraphicalObjectPlugin;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.RenderInformationBase;
import org.sbml.jsbml.ext.render.RenderLayoutPlugin;
import org.sbml.jsbml.ext.render.RenderListOfLayoutsPlugin;
import org.sbml.jsbml.ext.render.RenderPoint;
import org.sbml.jsbml.ext.render.Style;
import org.sbml.jsbml.util.filters.Filter;

/**
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Eugen Netz
 * @author Jan Rudolph
 * @since 1.0
 */
@ProviderFor(ReadingParser.class)
public class RenderParser extends AbstractReaderWriter  implements PackageParser {

  /**
   * The logger for this RenderParser
   */
  private static final transient Logger logger = Logger.getLogger(RenderParser.class);

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getShortLabel()
   */
  @Override
  public String getShortLabel() {
    return RenderConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getNamespaceURI()
   */
  @Override
  public String getNamespaceURI() {
    return RenderConstants.namespaceURI;
  }
  
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#processAttribute(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.Object)
   */
  @Override
  public boolean processAttribute(String elementName, String attributeName,
      String value, String uri, String prefix, boolean isLastAttribute,
      Object contextObject) 
  {
    if (contextObject instanceof GraphicalObject) {
      contextObject = ((SBase) contextObject).createPlugin(RenderConstants.shortLabel);
    }
    
    return super.processAttribute(elementName, attributeName, value, uri, prefix,
        isLastAttribute, contextObject);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#processStartElement(java.lang.String, java.lang.String, boolean, boolean, java.lang.Object)
   */
  @SuppressWarnings("unchecked")
  @Override
  public Object processStartElement(String elementName, String uri, String prefix,
    boolean hasAttributes, boolean hasNamespaces, Object contextObject) 
  {
    if (logger.isDebugEnabled()) {
    logger.debug("processStartElement - " + prefix + ":" + elementName + 
        " in context of: '" + ((contextObject instanceof SBase) ? ((SBase) contextObject).getElementName() : contextObject.getClass().getSimpleName()) + "'");
    }
    
    if (contextObject instanceof LayoutModelPlugin) {
      LayoutModelPlugin layoutModel = (LayoutModelPlugin) contextObject;
      
      ListOf<Layout> listOfLayouts = layoutModel.getListOfLayouts();
      SBase newElement = null;

      if (elementName.equals(RenderConstants.listOfGlobalRenderInformation)) {
        RenderListOfLayoutsPlugin renderPlugin = new RenderListOfLayoutsPlugin(listOfLayouts);
        listOfLayouts.addExtension(RenderConstants.shortLabel, renderPlugin);
        newElement = renderPlugin.getListOfGlobalRenderInformation();
      }

      if (newElement != null) {
        return newElement;
      }
    }
    else if (contextObject instanceof Layout) {
      Layout layout = (Layout) contextObject;
      SBase newElement = null;

      // JSBML used "listOfLocalRenderInformation" for a few years so we are keeping the test using "listOfLocalRenderInformation"
      // here to be able to read any incorrect files with JSBML
      if (elementName.equals(RenderConstants.listOfLocalRenderInformation) || 
          elementName.equals("listOfLocalRenderInformation"))
      {
        RenderLayoutPlugin renderPlugin = new RenderLayoutPlugin(layout);
        layout.addExtension(RenderConstants.shortLabel, renderPlugin);
        newElement = renderPlugin.getListOfLocalRenderInformation();
      }
      if (newElement != null) {
        return newElement;
      }
    }
    else if (contextObject instanceof RenderInformationBase) {
      RenderInformationBase renderInformation = (RenderInformationBase) contextObject;
      SBase newElement = null;

      if (elementName.equals(RenderConstants.listOfGradientDefinitions)) {
        newElement = renderInformation.getListOfGradientDefinitions();
      }
      if (elementName.equals(RenderConstants.listOfColorDefinitions)) {
        newElement = renderInformation.getListOfColorDefinitions();
      }
      if (elementName.equals(RenderConstants.listOfLineEndings)) {
        newElement = renderInformation.getListOfLineEndings();
      }

      if (renderInformation instanceof GlobalRenderInformation) {
        GlobalRenderInformation globalRenderInformation =
            (GlobalRenderInformation) renderInformation;
        
        if (elementName.equals(RenderConstants.listOfStyles)) {
          newElement = globalRenderInformation.getListOfStyles();
        }
      }

      if (renderInformation instanceof LocalRenderInformation) {
        LocalRenderInformation localRenderInformation =
            (LocalRenderInformation) renderInformation;
        
        // JSBML used "listOfLocalStyles" for a few years so we are keeping the test using "listOfLocalStyles"
        // here to be able to read any incorrect files with JSBML.
        if (elementName.equals(RenderConstants.listOfLocalStyles)
            || elementName.equals("listOfLocalStyles"))
        {
          newElement = localRenderInformation.getListOfLocalStyles();
        }
      }


      if (newElement != null) {
        return newElement;
      }
    }
    else if (contextObject instanceof Style) 
    {
      Style style = (Style) contextObject;
      
      if (elementName.equals(RenderConstants.group)) {
        RenderGroup g = new RenderGroup();
        style.setGroup(g);

        return g;
      }
    }
    else if (contextObject instanceof RenderGroup) 
    {
      RenderGroup g = (RenderGroup) contextObject;

      if (elementName.equals(RenderConstants.renderCurve)) {
        return g.createCurve();
      } else if (elementName.equals(RenderConstants.group)) {
        return g.createRenderGroup();
      } else if (elementName.equals(RenderConstants.group)) {
        return g.createRenderGroup();
      } else if (elementName.equals(RenderConstants.text)) {
        return g.createText();
      } else if (elementName.equals(RenderConstants.ellipse)) {
        return g.createEllipse();
      } else if (elementName.equals(RenderConstants.polygon)) {
        return g.createPolygon();
      } else if (elementName.equals(RenderConstants.rectangle)) {
        return g.createRectangle();
      } else if (elementName.equals(RenderConstants.image)) {
        return g.createImage();
      }
    }
    else if (contextObject instanceof Polygon) {
      Polygon polygon = (Polygon) contextObject;
      SBase newElement = null;
      
      // JSBML used "listOfRenderPoints" or "listOfRenderCubicBeziers" for a few years so 
      // we are keeping the additional tests to be able to read any incorrect files with JSBML.
      if (elementName.equals(RenderConstants.listOfElements)
          || elementName.equals("listOfRenderPoints")
          || elementName.equals("listOfRenderCubicBeziers")) 
      {
        newElement = polygon.getListOfElements();
      }
      else if (elementName.equals(LayoutConstants.listOfCurveSegments)) {
        newElement = polygon.getListOfCurveSegments();
      }
      
      if (newElement != null) {
        return newElement;
      }
    }

    else if (contextObject instanceof LineEnding) {
      LineEnding lineEnding = (LineEnding) contextObject;
      
      if (elementName.equals(RenderConstants.boundingBox)) {
        BoundingBox bbox = new BoundingBox();
        lineEnding.setBoundingBox(bbox);

        return bbox;
      }
      else if (elementName.equals(RenderConstants.group)) {
        RenderGroup g = new RenderGroup();
        lineEnding.setGroup(g);

        return g;
      }
    }

    else if (contextObject instanceof RenderCurve) {
      RenderCurve curve = (RenderCurve) contextObject;
      SBase newElement = null;

      // JSBML used "listOfRenderPoints" or "listOfRenderCubicBeziers" for a few years so 
      // we are keeping the additional tests to be able to read any incorrect files with JSBML.
      if (elementName.equals(RenderConstants.listOfElements)
          || elementName.equals("listOfRenderPoints")
          || elementName.equals("listOfRenderCubicBeziers")) 
      {
        newElement = curve.getListOfElements();
      }
      else if (elementName.equals(LayoutConstants.listOfCurveSegments)) 
      {
        newElement = curve.getListOfCurveSegments();
      }

      if (newElement != null) {
        return newElement;
      }
    }

    else if (contextObject instanceof GradientBase) {
      GradientBase gradientBase = (GradientBase) contextObject;
      SBase newElement = null;

      // JSBML used to create a "listOfGradientStops" for a few years so we are keeping the test using "listOfGradientStops"
      // here to be able to read any incorrect files with JSBML.
      if (elementName.equals(RenderConstants.listOfGradientStops)) {
        newElement = gradientBase.getListOfGradientStops();
      }
      else if (elementName.equals(RenderConstants.stop)) {
        newElement = new GradientStop();
        gradientBase.addGradientStop((GradientStop) newElement);
      }

      if (newElement != null) {
        return newElement;
      }
    }
    /**
     * parsing lists
     */
    else if (contextObject instanceof ListOf<?>) {
      ListOf<SBase> listOf = (ListOf<SBase>) contextObject;
      SBase newElement = null;

      if (elementName.equals(RenderConstants.element)) { 
        newElement = new RenderCubicBezier();
      }
      // JSBML used "renderPoint" for a few years so we are keeping the test using "renderPoint"
      // here to be able to read any incorrect files with JSBML.
      else if (elementName.equals("renderPoint")) {
        newElement = new RenderPoint();
      }
      // JSBML used "renderCubicBezier" for a few years so we are keeping the test using "renderCubicBezier"
      // here to be able to read any incorrect files with JSBML.
      else if (elementName.equals("renderCubicBezier")) {
        newElement = new RenderCubicBezier();
      }
      else if (elementName.equals(RenderConstants.style)) { 
     // we have to check the context to know if we create a LocalStyle or a GlobalStyle (Style)
        if (listOf.getParent() instanceof LocalRenderInformation) {
          newElement = new LocalStyle();
        } else {
          newElement = new Style();
        }
      }
      // JSBML used "localStyle" for a few years so we are keeping the test using "localStyle"
      // here to be able to read any incorrect files with JSBML.
      else if (elementName.equals("localStyle")) {
        newElement = new LocalStyle();
      }
      else if (elementName.equals(RenderConstants.gradientStop)
    		  || elementName.equals(RenderConstants.stop)) {
        newElement = new GradientStop();
      }
      else if (elementName.equals(RenderConstants.colorDefiniton)) {
        newElement = new ColorDefinition();
      }
      else if (elementName.equals(RenderConstants.gradientBase)) {
        newElement = new GradientBase();
      }
      else if (elementName.equals(RenderConstants.radialGradient)) {
          newElement = new RadialGradient();
       }
      else if (elementName.equals(RenderConstants.linearGradient)) {
          newElement = new LinearGradient();
       }
      else if (elementName.equals(RenderConstants.lineEnding)) {
        newElement = new LineEnding();
      }
      else if (elementName.equals(RenderConstants.renderInformation)) 
      {
        // we have to check the context to know if we create a Local or a Global element
        if (listOf.getElementName().equals(RenderConstants.listOfLocalRenderInformation)) {
          newElement = new LocalRenderInformation();
        } else {
          newElement = new GlobalRenderInformation();
        }
      }
      // JSBML used "localRenderInformation" for a few years so we are keeping the test using "localRenderInformation"
      // here to be able to read any incorrect files with JSBML.
      else if (elementName.equals("localRenderInformation")) 
      {
        newElement = new LocalRenderInformation();
      }
      // JSBML used "globalRenderInformation" for a few years so we are keeping the test using "globalRenderInformation"
      // here to be able to read any incorrect files with JSBML.
      else if (elementName.equals("globalRenderInformation")) 
      {
        newElement = new GlobalRenderInformation();
      }


      if (newElement != null) {
        // listOf.registerChild(newElement); // registerChild is called when adding the element to the list
        listOf.add(newElement);
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
    if (sbmlDocument.isPackageEnabled(RenderConstants.shortLabel) && sbmlDocument.isSetModel())
    {
      SBasePlugin layoutPlugin = sbmlDocument.getModel().getExtension(LayoutConstants.shortLabel);
      
      // If the Model Layout plugin is not defined, just return from the method, there is nothing to check
      if (layoutPlugin == null) {
        return;
      }
      
      // going through the document to find all RenderCurveSegment objects
      List<? extends TreeNode> curveElements = layoutPlugin.filter(new Filter() {
        /* (non-Javadoc)
         * @see org.sbml.jsbml.util.filters.Filter#accepts(java.lang.Object)
         */
        @Override
        public boolean accepts(Object o) {
          return o instanceof Polygon || o instanceof RenderCurve;
        }
      });

      for (TreeNode curveNode : curveElements) {
        ListOf<RenderPoint> listOfElements = null;
        
        if (curveNode instanceof RenderCurve) { 
    	  RenderCurve curve = (RenderCurve) curveNode;
    	  listOfElements = curve.getListOfElements();
        } else {
        	listOfElements = ((Polygon) curveNode).getListOfElements();
        }

        // transform the RenderCubicBezier into RenderPoint when needed
        int i = 0;
        for (RenderPoint renderPoint : listOfElements.clone())
        {
          if (! renderPoint.isSetType())
          {
            if (((RenderCubicBezier) renderPoint).isSetX1() || ((RenderCubicBezier) renderPoint).isSetX2())
            {
              // trick to set the 'type' attribute, although the setType method is not visible.
              renderPoint.readAttribute("type", "", RenderCurveSegment.Type.RENDER_CUBIC_BEZIER.toString());
            }
            else
            {
              renderPoint.readAttribute("type", "", RenderCurveSegment.Type.RENDER_POINT.toString());
            }
          }

          if (renderPoint.getType().equals(RenderCurveSegment.Type.RENDER_POINT))
          {
            RenderPoint realRenderPoint = new RenderPoint(renderPoint);
            logger.debug("Transformed a RenderCubicBezier: '" + renderPoint + "' into a RenderPoint.");
            listOfElements.remove(i);
            listOfElements.add(i, realRenderPoint);
          }

          if (logger.isDebugEnabled())
          {
            logger.debug("RenderCurveSegment = " + listOfElements.get(i));
          }

          i++;
        }
      }
    }
  }

  
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#getNamespaces()
   */
  @Override
  public List<String> getNamespaces() {
    return RenderConstants.namespaces_L3;
  }

  @SuppressWarnings("unchecked")
  @Override
  public SBasePlugin createPluginFor(SBase sbase) {

    if (sbase != null) {
      if (sbase instanceof Layout) {
        return new RenderLayoutPlugin((Layout) sbase);
      } else if (sbase instanceof ListOf<?>) {
        if (sbase.getElementName().equals(LayoutConstants.listOfLayouts)) {
          return new RenderListOfLayoutsPlugin((ListOf<Layout>) sbase);
        }
      } else if (sbase instanceof GraphicalObject) {
        return new RenderGraphicalObjectPlugin((GraphicalObject) sbase);
      }
    }

    return null;
  }

  @Override
  public String getNamespaceFor(int level, int version, int packageVersion) {

    if (level == 3 && version == 1 && packageVersion == 1) {
      return RenderConstants.namespaceURI_L3V1V1;
    }

    return null;
  }

  @Override
  public List<String> getPackageNamespaces() {
    return RenderConstants.namespaces_L3;
  }

  @Override
  public String getPackageName() {
    return getShortLabel();
  }

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

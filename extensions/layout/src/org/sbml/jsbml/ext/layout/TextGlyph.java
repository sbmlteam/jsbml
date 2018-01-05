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
package org.sbml.jsbml.ext.layout;

import java.util.Map;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBase;



/**
 * The {@link TextGlyph} class describes the position and dimension of text labels. It inherits
 * from {@link GraphicalObject} and adds the attributes graphicalObject, text and originOfText.
 * 
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class TextGlyph extends AbstractReferenceGlyph {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -2582985174711830815L;

  /**
   * 
   */
  private String graphicalObject;

  /**
   * 
   */
  private String text;

  /**
   * 
   */
  public TextGlyph() {
    super();
    initDefaults();
  }

  /**
   * 
   * @param level
   * @param version
   */
  public TextGlyph(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * 
   * @param id
   */
  public TextGlyph(String id) {
    super(id);
    initDefaults();
  }

  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public TextGlyph(String id, int level, int version) {
    super(id, level, version);
    initDefaults();
  }

  /**
   * 
   * @param textGlyph
   */
  public TextGlyph(TextGlyph textGlyph) {
    super(textGlyph);
    if (textGlyph.isSetGraphicalObject()) {
      setGraphicalObject(new String(textGlyph.getGraphicalObject()));
    }
    if (textGlyph.isSetText()) {
      setText(new String(textGlyph.getText()));
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public TextGlyph clone() {
    return new TextGlyph(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  @Override
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = LayoutConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      TextGlyph t = (TextGlyph) object;
      equals &= t.isSetText() == isSetText();
      if (equals && isSetText()) {
        equals &= t.getText().equals(getText());
      }
      equals &= t.isSetGraphicalObject() == isSetGraphicalObject();
      if (equals && isSetGraphicalObject()) {
        equals &= t.getGraphicalObject().equals(getGraphicalObject());
      }
    }
    return equals;
  }

  /**
   * 
   * @return
   */
  public String getGraphicalObject() {
    return graphicalObject;
  }

  /**
   * Direct access to the {@link GraphicalObject} linked to this
   * {@link TextGlyph}.
   * @return
   */
  public GraphicalObject getGraphicalObjectInstance() {
    Model model = getModel();
    return (GraphicalObject) (isSetGraphicalObject() && (model != null) ? model.findNamedSBase(getGraphicalObject()) : null);
  }

  /**
   * 
   * @return
   */
  public String getOriginOfText() {
    return getReference();
  }

  /**
   * 
   * @return
   */
  public NamedSBase getOriginOfTextInstance() {
    return getNamedSBaseInstance();
  }

  /**
   * 
   * @return
   */
  public String getText() {
    return text;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 967;
    int hashCode = super.hashCode();
    if (isSetText()) {
      hashCode += prime * getText().hashCode();
    }
    if (isSetGraphicalObject()) {
      hashCode += prime * getGraphicalObject().hashCode();
    }

    return hashCode;
  }

  /**
   * @return
   */
  public boolean isSetGraphicalObject() {
    return graphicalObject != null;
  }

  /**
   * Method to test if an instance of a {@link GraphicalObject} with the id
   * given by {@link #getGraphicalObject()} can be found in the {@link Model}.
   * @return
   */
  public boolean isSetGraphicalObjectInstance() {
    Model model = getModel();
    return isSetGraphicalObject() && (model != null) && (model.getSBaseById(getGraphicalObject()) != null);
  }

  /**
   * @return
   */
  public boolean isSetOriginOfText() {
    return isSetReference();
  }

  /**
   * @return
   */
  public boolean isSetText() {
    return text != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);

    if (!isAttributeRead)
    {

      if (attributeName.equals(LayoutConstants.graphicalObject))
      {
        setGraphicalObject(value);
      }
      else if (attributeName.equals(LayoutConstants.text))
      {
        setText(value);
      }
      else if (attributeName.equals(LayoutConstants.originOfText))
      {
        setOriginOfText(value);
      }
      else
      {
        return false;
      }

      return true;
    }

    return isAttributeRead;
  }

  /**
   * 
   * @param graphicalObject
   */
  public void setGraphicalObject(GraphicalObject graphicalObject) {
    if (graphicalObject == null) {
      unsetGraphicalObject();
    } else {
      setGraphicalObject(graphicalObject.getId());
    }
  }

  /**
   * 
   */
  public void unsetGraphicalObject() {
    setGraphicalObject((String) null);
  }

  /**
   * The graphicalObject attribute contains the id of any {@link GraphicalObject} and specifies that
   * the {@link TextGlyph} should be considered to be a label to that object. This allows modelers to
   * indicate that the label is to be moved together with the object. If the graphicalObject attribute
   * is used together with the metaidRef they need to refer to the same object in the {@link Layout}.
   * 
   * @param graphicalObject
   */
  public void setGraphicalObject(String graphicalObject) {
    String oldValue = this.graphicalObject;
    this.graphicalObject = graphicalObject;
    firePropertyChange(LayoutConstants.graphicalObject, oldValue, this.graphicalObject);
  }

  /**
   * 
   * @param originOfText
   */
  public void setOriginOfText(NamedSBase originOfText) {
    setNamedSBase(originOfText);
  }

  /**
   * Additionally the optional attribute originOfText holds the id of an entity in the {@link Model}.
   * If it is specified, the text to be displayed is taken from the name attribute of the referenced
   * object. If both attributes originOfText and text are specified, the text attribute value
   * overrides the value of originOfText.
   * 
   * @param originOfText
   */
  public void setOriginOfText(String originOfText) {
    setReference(originOfText);
  }

  /**
   * The String text attribute facilitates adding of independent text, like a title or a comment
   * to the diagram.
   * 
   * @param text
   */
  public void setText(String text) {
    String oldText = this.text;
    this.text = text;
    firePropertyChange(LayoutConstants.text, oldText, this.text);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.GraphicalObject#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetGraphicalObject()) {
      attributes.put(LayoutConstants.shortLabel + ':'
        + LayoutConstants.graphicalObject, graphicalObject);
    }
    if (isSetText()) {
      attributes.put(LayoutConstants.shortLabel + ':'
        + LayoutConstants.text, text);
    }
    if (isSetOriginOfText()) {
      attributes.put(LayoutConstants.shortLabel + ':'
        + LayoutConstants.originOfText, getOriginOfText());
    }

    return attributes;
  }

}

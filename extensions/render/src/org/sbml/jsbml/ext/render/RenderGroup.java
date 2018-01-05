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
package org.sbml.jsbml.ext.render;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * The {@link RenderGroup} class from the SBML render extension is used to group graphical primitives together
 * to create composite representations from simple primitives.
 * <p>
 * The {@link RenderGroup} class is derived from {@link GraphicalPrimitive2D} and inherits all its methods and attributes.
 * In addition to those, the class defines attributes to specify text render properties (@see Text),
 * curve decorations (@see RenderCurve), an id and a list of child elements which can be any
 * graphical primitive or other groups.
 * <p>
 * The attributes of a group are inherited by all children of the group unless they specify
 * the attribute themselves.
 * 
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class RenderGroup extends GraphicalPrimitive2D implements UniqueNamedSBase {
  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = 2302368129571619877L;
  /**
   * 
   */
  private FontFamily fontFamily;
  /**
   * 
   */
  private Short fontSize;
  /**
   * 
   */
  private Boolean fontWeightBold;
  /**
   * 
   */
  private Boolean fontStyleItalic;
  /**
   * 
   */
  private HTextAnchor textAnchor;
  /**
   * 
   */
  private VTextAnchor vTextAnchor;
  /**
   * 
   */
  private String startHead;
  /**
   * 
   */
  private String endHead;

  /**
   * 
   */
  private ListOf<Transformation2D> listOfElements;

  /**
   * Creates an Group instance
   */
  public RenderGroup() {
    super();
    initDefaults();
  }

  /**
   * Creates a Group instance with an id.
   * 
   * @param id the id
   */
  public RenderGroup(String id) {
    super();
    setId(id);
    initDefaults();
  }

  /**
   * Creates a Group instance with a level and version.
   * 
   * @param level the SBML level
   * @param version the SBMl version
   */
  public RenderGroup(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * Creates a Group instance with an id, level, and version.
   * 
   * @param id the id
   * @param level the SBML level
   * @param version the SBMl version
   */
  public RenderGroup(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates a Group instance with an id, name, level, and version.
   * 
   * @param id the render group SId
   * @param name the name
   * @param level the SBML level
   * @param version the SBMl version
   */
  public RenderGroup(String id, String name, int level, int version) {
    super(level, version);
    setId(id);
    setName(name);
    initDefaults();
  }

  /**
   * Clone constructor
   * 
   * @param obj the {@link RenderGroup} instance to clone
   */
  public RenderGroup(RenderGroup obj) {
    super(obj);

    if (obj.isSetFontFamily()) {
      setFontFamily(obj.getFontFamily());
    }
    if (obj.isSetFontSize()) {
      setFontSize(obj.getFontSize());
    }
    if (obj.isSetFontStyleItalic()) {
      setFontStyleItalic(obj.getFontStyleItalic());
    }
    if (obj.isSetFontWeightBold()) {
      setFontWeightBold(obj.getFontWeightBold());
    }
    if (obj.isSetTextAnchor()) {
      setTextAnchor(obj.getTextAnchor());
    }
    if (obj.isSetVTextAnchor()) {
      setVTextAnchor(obj.getVTextAnchor());
    }
    if (obj.isSetStartHead()) {
      setStartHead(obj.getStartHead());
    }
    if (obj.isSetEndHead()) {
      setEndHead(obj.getEndHead());
    }
    if (obj.isSetListOfElements()) {
      setListOfElements(obj.getListOfElements().clone());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive2D#clone()
   */
  @Override
  public RenderGroup clone() {
    return new RenderGroup(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive2D#initDefaults()
   */
  @Override
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = RenderConstants.shortLabel;
  }



  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3203;
    int result = super.hashCode();
    result = prime * result + ((endHead == null) ? 0 : endHead.hashCode());
    result = prime * result
        + ((fontFamily == null) ? 0 : fontFamily.hashCode());
    result = prime * result + ((fontSize == null) ? 0 : fontSize.hashCode());
    result = prime * result
        + ((fontStyleItalic == null) ? 0 : fontStyleItalic.hashCode());
    result = prime * result
        + ((fontWeightBold == null) ? 0 : fontWeightBold.hashCode());
    result = prime * result + ((startHead == null) ? 0 : startHead.hashCode());
    result = prime * result
        + ((textAnchor == null) ? 0 : textAnchor.hashCode());
    result = prime * result
        + ((vTextAnchor == null) ? 0 : vTextAnchor.hashCode());
    result = prime * result
        + ((listOfElements == null) ? 0 : listOfElements.hashCode());

    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    RenderGroup other = (RenderGroup) obj;
    if (endHead == null) {
      if (other.endHead != null) {
        return false;
      }
    } else if (!endHead.equals(other.endHead)) {
      return false;
    }
    if (fontFamily != other.fontFamily) {
      return false;
    }
    if (fontSize == null) {
      if (other.fontSize != null) {
        return false;
      }
    } else if (!fontSize.equals(other.fontSize)) {
      return false;
    }
    if (fontStyleItalic == null) {
      if (other.fontStyleItalic != null) {
        return false;
      }
    } else if (!fontStyleItalic.equals(other.fontStyleItalic)) {
      return false;
    }
    if (fontWeightBold == null) {
      if (other.fontWeightBold != null) {
        return false;
      }
    } else if (!fontWeightBold.equals(other.fontWeightBold)) {
      return false;
    }
    if (startHead == null) {
      if (other.startHead != null) {
        return false;
      }
    } else if (!startHead.equals(other.startHead)) {
      return false;
    }
    if (textAnchor != other.textAnchor) {
      return false;
    }
    if (vTextAnchor != other.vTextAnchor) {
      return false;
    }

    if (listOfElements == null) {
      if (other.listOfElements != null) {
        return false;
      }
    } else if (!listOfElements.equals(other.listOfElements)) {
      return false;
    }
    return true;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Group [fontFamily=" + fontFamily + ", fontSize="
        + fontSize + ", fontWeightBold=" + fontWeightBold + ", fontStyleItalic="
        + fontStyleItalic + ", textAnchor=" + textAnchor + ", vTextAnchor="
        + vTextAnchor + ", startHead=" + startHead + ", endHead=" + endHead + ", listOfElements=" + listOfElements + "]";
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();

    if (isSetListOfElements()) {
      count += getListOfElements().size();
    }
    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
    }

    int count = super.getChildCount(), pos = 0;

    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }

    if (isSetListOfElements()) {

      for (Transformation2D drawable : getListOfElements()) {
        if (pos == index) {
          return drawable;
        }
        pos++;
      }
    }

    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getElementName()
   */
  @Override
  public String getElementName() {
    return RenderConstants.group;
  }

  /**
   * @return the value of fontFamily
   */
  public FontFamily getFontFamily() {
    if (isSetFontFamily()) {
      return fontFamily;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.fontFamily, this);
  }

  /**
   * @return whether fontFamily is set
   */
  public boolean isSetFontFamily() {
    return fontFamily != null;
  }

  /**
   * Set the value of fontFamily
   * @param fontFamily
   */
  public void setFontFamily(FontFamily fontFamily) {
    FontFamily oldFontFamily = this.fontFamily;
    this.fontFamily = fontFamily;
    firePropertyChange(RenderConstants.fontFamily, oldFontFamily, this.fontFamily);
  }

  /**
   * Unsets the variable fontFamily
   * @return {@code true}, if fontFamily was set before,
   *         otherwise {@code false}
   */
  public boolean unsetFontFamily() {
    if (isSetFontFamily()) {
      FontFamily oldFontFamily = fontFamily;
      fontFamily = null;
      firePropertyChange(RenderConstants.fontFamily, oldFontFamily, fontFamily);
      return true;
    }
    return false;
  }

  /**
   * @return the value of fontSize
   */
  public Short getFontSize() {
    if (isSetFontSize()) {
      return fontSize;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.fontSize, this);
  }

  /**
   * @return whether fontSize is set
   */
  public boolean isSetFontSize() {
    return fontSize != null;
  }

  /**
   * Set the value of fontSize
   * @param fontSize
   */
  public void setFontSize(Short fontSize) {
    Short oldFontSize = this.fontSize;
    this.fontSize = fontSize;
    firePropertyChange(RenderConstants.fontSize, oldFontSize, this.fontSize);
  }

  /**
   * Unsets the variable fontSize
   * @return {@code true}, if fontSize was set before,
   *         otherwise {@code false}
   */
  public boolean unsetFontSize() {
    if (isSetFontSize()) {
      short oldFontSize = fontSize;
      fontSize = null;
      firePropertyChange(RenderConstants.fontSize, oldFontSize, fontSize);
      return true;
    }
    return false;
  }

  /**
   * Returns the value of fontWeightBold
   * 
   * @return the value of fontWeightBold
   */
  public boolean getFontWeightBold() {
    return isFontWeightBold();
  }

  /**
   * Returns the value of fontWeightBold
   * 
   * @return the value of fontWeightBold
   */
  public boolean isFontWeightBold() {
    if (isSetFontWeightBold()) {
      return fontWeightBold;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.fontWeightBold, this);
  }

  /**
   * @return whether fontWeightBold is set
   */
  public boolean isSetFontWeightBold() {
    return fontWeightBold != null;
  }

  /**
   * Set the value of fontWeightBold
   * @param fontWeightBold
   */
  public void setFontWeightBold(Boolean fontWeightBold) {
    Boolean oldFontWeightBold = this.fontWeightBold;
    this.fontWeightBold = fontWeightBold;
    firePropertyChange(RenderConstants.fontWeightBold, oldFontWeightBold, this.fontWeightBold);
  }

  /**
   * Unsets the variable fontWeightBold
   * @return {@code true}, if fontWeightBold was set before,
   *         otherwise {@code false}
   */
  public boolean unsetFontWeightBold() {
    if (isSetFontWeightBold()) {
      Boolean oldFontWeightBold = fontWeightBold;
      fontWeightBold = null;
      firePropertyChange(RenderConstants.fontWeightBold, oldFontWeightBold, fontWeightBold);
      return true;
    }
    return false;
  }

  /**
   * Returns the value of fontStyleItalic
   * 
   * @return the value of fontStyleItalic
   */
  public boolean getFontStyleItalic() {
    return isFontStyleItalic();
  }

  /**
   * Returns the value of fontStyleItalic
   * 
   * @return the value of fontStyleItalic
   */
  public boolean isFontStyleItalic() {
    if (isSetFontStyleItalic()) {
      return fontStyleItalic;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.fontStyleItalic, this);
  }

  /**
   * Returns whether fontStyleItalic is set
   * 
   * @return whether fontStyleItalic is set
   */
  public boolean isSetFontStyleItalic() {
    return fontStyleItalic != null;
  }

  /**
   * Set the value of fontStyleItalic
   * @param fontStyleItalic
   */
  public void setFontStyleItalic(Boolean fontStyleItalic) {
    Boolean oldFontStyleItalic = this.fontStyleItalic;
    this.fontStyleItalic = fontStyleItalic;
    firePropertyChange(RenderConstants.fontStyleItalic, oldFontStyleItalic, this.fontStyleItalic);
  }

  /**
   * Unsets the variable fontStyleItalic
   * @return {@code true}, if fontStyleItalic was set before,
   *         otherwise {@code false}
   */
  public boolean unsetFontStyleItalic() {
    if (isSetFontStyleItalic()) {
      Boolean oldFontStyleItalic = fontStyleItalic;
      fontStyleItalic = null;
      firePropertyChange(RenderConstants.fontStyleItalic, oldFontStyleItalic, fontStyleItalic);
      return true;
    }
    return false;
  }

  /**
   * @return the value of startHead
   */
  public String getStartHead() {
    if (isSetStartHead()) {
      return startHead;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.startHead, this);
  }

  /**
   * @return whether startHead is set
   */
  public boolean isSetStartHead() {
    return startHead != null;
  }

  /**
   * Set the value of startHead
   * @param startHead
   */
  public void setStartHead(String startHead) {
    String oldStartHead = this.startHead;
    this.startHead = startHead;
    firePropertyChange(RenderConstants.startHead, oldStartHead, this.startHead);
  }

  /**
   * Unsets the variable startHead
   * @return {@code true}, if startHead was set before,
   *         otherwise {@code false}
   */
  public boolean unsetStartHead() {
    if (isSetStartHead()) {
      String oldStartHead = startHead;
      startHead = null;
      firePropertyChange(RenderConstants.startHead, oldStartHead, startHead);
      return true;
    }
    return false;
  }

  /**
   * @return the value of endHead
   */
  public String getEndHead() {
    if (isSetEndHead()) {
      return endHead;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.endHead, this);
  }

  /**
   * @return whether endHead is set
   */
  public boolean isSetEndHead() {
    return endHead != null;
  }

  /**
   * Set the value of endHead
   * @param endHead
   */
  public void setEndHead(String endHead) {
    String oldEndHead = this.endHead;
    this.endHead = endHead;
    firePropertyChange(RenderConstants.endHead, oldEndHead, this.endHead);
  }

  /**
   * Unsets the variable endHead
   * @return {@code true}, if endHead was set before,
   *         otherwise {@code false}
   */
  public boolean unsetEndHead() {
    if (isSetEndHead()) {
      String oldEndHead = endHead;
      endHead = null;
      firePropertyChange(RenderConstants.endHead, oldEndHead, endHead);
      return true;
    }
    return false;
  }

  /**
   * @return the value of textAnchor
   */
  public HTextAnchor getTextAnchor() {
    if (isSetTextAnchor()) {
      return textAnchor;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.textAnchor, this);
  }

  /**
   * @return whether textAnchor is set
   */
  public boolean isSetTextAnchor() {
    return textAnchor != null;
  }

  /**
   * Set the value of textAnchor
   * @param textAnchor
   */
  public void setTextAnchor(HTextAnchor textAnchor) {
    HTextAnchor oldTextAnchor = this.textAnchor;
    this.textAnchor = textAnchor;
    firePropertyChange(RenderConstants.textAnchor, oldTextAnchor, this.textAnchor);
  }

  /**
   * Unsets the variable textAnchor
   * @return {@code true}, if textAnchor was set before,
   *         otherwise {@code false}
   */
  public boolean unsetTextAnchor() {
    if (isSetTextAnchor()) {
      HTextAnchor oldTextAnchor = textAnchor;
      textAnchor = null;
      firePropertyChange(RenderConstants.textAnchor, oldTextAnchor, textAnchor);
      return true;
    }
    return false;
  }

  /**
   * @return the value of vTextAnchor
   */
  public VTextAnchor getVTextAnchor() {
    if (isSetVTextAnchor()) {
      return vTextAnchor;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.vTextAnchor, this);
  }

  /**
   * @return whether vTextAnchor is set
   */
  public boolean isSetVTextAnchor() {
    return vTextAnchor != null;
  }

  /**
   * Set the value of vTextAnchor
   * @param vTextAnchor
   */
  public void setVTextAnchor(VTextAnchor vTextAnchor) {
    VTextAnchor oldVTextAnchor = this.vTextAnchor;
    this.vTextAnchor = vTextAnchor;
    firePropertyChange(RenderConstants.vTextAnchor, oldVTextAnchor, this.vTextAnchor);
  }

  /**
   * Unsets the variable vTextAnchor
   * @return {@code true}, if vTextAnchor was set before,
   *         otherwise {@code false}
   */
  public boolean unsetVTextAnchor() {
    if (isSetVTextAnchor()) {
      VTextAnchor oldVTextAnchor = vTextAnchor;
      vTextAnchor = null;
      firePropertyChange(RenderConstants.vTextAnchor, oldVTextAnchor, vTextAnchor);
      return true;
    }
    return false;
  }


  /**
   * Returns {@code true}, if listOfElements contains at least one element.
   *
   * @return {@code true}, if listOfElements contains at least one element,
   *         otherwise {@code false}.
   */
  public boolean isSetListOfElements() {
    if ((listOfElements == null) || listOfElements.isEmpty()) {
      return false;
    }
    return true;
  }


  /**
   * Returns the listOfElements. Creates it if it is not already existing.
   *
   * @return the listOfElements.
   */
  public ListOf<Transformation2D> getListOfElements() {
    if (!isSetListOfElements()) {
      listOfElements = new ListOf<Transformation2D>();
      listOfElements.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'render'
      listOfElements.setPackageName(null);
      listOfElements.setPackageName(RenderConstants.shortLabel);
      listOfElements.setSBaseListType(ListOf.Type.other);

      registerChild(listOfElements);
    }
    return listOfElements;
  }


  /**
   * Sets the given {@code ListOf<Transformation2D>}. If listOfElements
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfElements
   */
  public void setListOfElements(ListOf<Transformation2D> listOfElements) {
    unsetListOfElements();
    this.listOfElements = listOfElements;

    if (listOfElements != null) {
      listOfElements.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'render'
      listOfElements.setPackageName(null);
      listOfElements.setPackageName(RenderConstants.shortLabel);
      listOfElements.setSBaseListType(ListOf.Type.other);

      registerChild(this.listOfElements);
    }
  }


  /**
   * Returns {@code true}, if listOfElements contain at least one element,
   *         otherwise {@code false}.
   *
   * @return {@code true}, if listOfElements contain at least one element,
   *         otherwise {@code false}.
   */
  public boolean unsetListOfElements() {
    if (isSetListOfElements()) {
      ListOf<Transformation2D> oldElements = listOfElements;
      listOfElements = null;
      oldElements.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link Transformation2D} to the listOfElements.
   * <p>The listOfElements is initialized if necessary.
   *
   * @param field the element to add to the list
   * @return true (as specified by {@link Collection#add})
   */
  public boolean addElement(Transformation2D field) {
    return getListOfElements().add(field);
  }


  /**
   * Removes an element from the listOfElements.
   *
   * @param field the element to be removed from the list.
   * @return true if the list contained the specified element and it was removed.
   * @see List#remove(Object)
   */
  public boolean removeElement(Transformation2D field) {
    if (isSetListOfElements()) {
      return getListOfElements().remove(field);
    }
    return false;
  }


  /**
   * Removes an element from the listOfElements.
   *
   * @param fieldId the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or {@code null}.
   */
  public Transformation2D removeElement(String fieldId) {
    if (isSetListOfElements()) {
      return getListOfElements().remove(fieldId);
    }
    return null;
  }


  /**
   * Removes an element from the listOfElements at the given index.
   *
   * @param i the index where to remove the {@link Transformation2D}.
   * @return the specified element, if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public Transformation2D removeElement(int i) {
    if (!isSetListOfElements()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfElements().remove(i);
  }

  /**
   * Creates a new {@link RenderCurve} element, adds it to the ListOfElements list and returns it.
   * @return
   */
  public RenderCurve createCurve() {
    RenderCurve curve = new RenderCurve();
    addElement(curve);

    return curve;
  }

  /**
   * Creates a new {@link Ellipse} element, adds it to the ListOfElements list and returns it.
   * @return
   */
  public Ellipse createEllipse() {
    Ellipse ellipse = new Ellipse();
    addElement(ellipse);

    return ellipse;
  }

  /**
   * Creates a new {@link Image} element, adds it to the ListOfElements list and returns it.
   * @return
   */
  public Image createImage() {
    Image image = new Image();
    addElement(image);

    return image;
  }

  /**
   * Creates a new {@link Polygon} element, adds it to the ListOfElements list and returns it.
   * @return
   */
  public Polygon createPolygon() {
    Polygon polygon = new Polygon();
    addElement(polygon);

    return polygon;
  }

  /**
   * Creates a new {@link Rectangle} element, adds it to the ListOfElements list and returns it.
   * @return
   */
  public Rectangle createRectangle() {
    Rectangle rectangle = new Rectangle();
    addElement(rectangle);

    return rectangle;
  }

  /**
   * Creates a new {@link RenderGroup} element, adds it to the ListOfElements list and returns it.
   * @return
   */
  public RenderGroup createRenderGroup() {
    RenderGroup group = new RenderGroup();
    addElement(group);

    return group;
  }

  /**
   * Creates a new {@link Text} element, adds it to the ListOfElements list and returns it.
   * @return
   */
  public Text createText() {
    Text rectangle = new Text();
    addElement(rectangle);

    return rectangle;
  }


  /**
   * Gets an element from the listOfElements at the given index.
   *
   * @param i the index of the {@link Transformation2D} element to get.
   * @return an element from the listOfElements at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public Transformation2D getElement(int i) {
    if (!isSetListOfElements()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfElements().get(i);
  }


  /**
   * Gets an element from the listOfElements, with the given id.
   *
   * @param fieldId the id of the {@link Transformation2D} element to get.
   * @return an element from the listOfElements with the given id or {@code null}.
   */
  public Transformation2D getElement(String fieldId) {
    if (isSetListOfElements()) {
      return getListOfElements().get(fieldId);
    }
    return null;
  }


  /**
   * Returns the number of {@link Transformation2D}s in this {@link RenderGroup}.
   * 
   * @return the number of {@link Transformation2D}s in this {@link RenderGroup}.
   */
  public int getElementCount() {
    return isSetListOfElements() ? getListOfElements().size() : 0;
  }


  /**
   * Returns the number of {@link Transformation2D}s in this {@link RenderGroup}.
   * 
   * @return the number of {@link Transformation2D}s in this {@link RenderGroup}.
   * @libsbml.deprecated same as {@link #getElementCount()}
   */
  public int getNumElements() {
    return getElementCount();
  }

  /**
   * Adds the given element to the end of the list of children elements.
   * @param pChild
   * 
   *
   * @see #createEllipse()
   * @see #createRectangle()
   * @see #createPolygon()
   * @see #createText()
   * @see #createCurve()
   * @see #createRenderGroup()
   * @see #createImage()
   */
  public void addChildElement(Transformation2D pChild) {

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetFontFamily()) {
      String fontFamily = getFontFamily().toString().toLowerCase();
      if (fontFamily.equals("sans_serif")) {
        fontFamily = "sans-serif";
      }
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.fontFamily, fontFamily);
    }
    if (isSetTextAnchor()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.textAnchor,
        getTextAnchor().toString().toLowerCase());
    }
    if (isSetVTextAnchor()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.vTextAnchor,
        getVTextAnchor().toString().toLowerCase());
    }
    if (isSetFontSize()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.fontSize,
        Short.toString(getFontSize()));
    }
    if (isSetStartHead()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.startHead,
        getStartHead());
    }
    if (isSetEndHead()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.endHead,
        getEndHead());
    }
    if (isSetFontStyleItalic()) {
      attributes.put(RenderConstants.fontStyleItalic,
        XMLTools.fontStyleItalicToString(isFontStyleItalic()));
    }
    if (isSetFontWeightBold()) {
      attributes.put(RenderConstants.fontWeightBold,
        XMLTools.fontWeightBoldToString(isFontWeightBold()));
    }
    return attributes;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(RenderConstants.fontFamily)) {
        if (value.equals("sans-serif")) {
          value = "SANS_SERIF";
        }
        try {
          setFontFamily(FontFamily.valueOf(value.toUpperCase()));
        } catch (Exception e) {
          throw new SBMLException("Could not recognized the value '" + value
              + "' for the attribute " + RenderConstants.fontFamily
              + " on the 'g' element.");
        }
      }
      else if (attributeName.equals(RenderConstants.fontSize)) {
        setFontSize(Short.valueOf(value));
      }
      else if (attributeName.equals(RenderConstants.fontWeightBold)) {
        setFontWeightBold(XMLTools.parseFontWeightBold(value));
      }
      else if (attributeName.equals(RenderConstants.fontStyleItalic)) {
        setFontStyleItalic(XMLTools.parseFontStyleItalic(value));
      }
      else if (attributeName.equals(RenderConstants.textAnchor)) {
        try {
          setTextAnchor(HTextAnchor.valueOf(value.toUpperCase()));
        } catch (Exception e) {
          throw new SBMLException("Could not recognized the value '" + value
              + "' for the attribute " + RenderConstants.textAnchor
              + " on the 'g' element.");
        }
      }
      else if (attributeName.equals(RenderConstants.vTextAnchor)) {
        try {
          setVTextAnchor(VTextAnchor.valueOf(value.toUpperCase()));
        } catch (Exception e) {
          throw new SBMLException("Could not recognized the value '" + value
              + "' for the attribute " + RenderConstants.vTextAnchor
              + " on the 'g' element.");
        }
      }
      // JSBML used "start-head" for a few years so we are keeping the test using "start-head"
      // here to be able to read any incorrect files with JSBML.
      else if (attributeName.equals(RenderConstants.startHead)
          || attributeName.equals("start-head"))
      {
        setStartHead(value);
      }
      // JSBML used "end-head" for a few years so we are keeping the test using "end-head"
      // here to be able to read any incorrect files with JSBML.
      else if (attributeName.equals(RenderConstants.endHead)
          || attributeName.equals("end-head"))
      {
        setEndHead(value);
      }
      else {
        isAttributeRead = false;
      }
    }

    return isAttributeRead;
  }
}

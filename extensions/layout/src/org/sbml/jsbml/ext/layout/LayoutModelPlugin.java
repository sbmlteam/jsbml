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

import java.text.MessageFormat;
import java.util.Map;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;

/**
 * The {@link Layout} package extends the {@link Model} class with the
 * addition of one child element: the listOfLayouts. A {@link Model} may
 * contain at most one such list, but the list itself can hold many
 * different {@link Layout}s.
 * 
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @author Clemens Wrzodek
 * @since 1.0
 */
public class LayoutModelPlugin extends AbstractSBasePlugin {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 4507170457817702658L;

  /**
   * 
   */
  protected ListOf<Layout> listOfLayouts;


  /**
   * Creates a new instance {@link LayoutModelPlugin} cloned from the given {@link LayoutModelPlugin}.
   * 
   * @param elm the {@link LayoutModelPlugin} we want to clone.
   */
  public LayoutModelPlugin(LayoutModelPlugin elm) {
    super(elm);

    if (elm.isSetListOfLayouts()) {
      setListOfLayouts(elm.listOfLayouts.clone());
    }
  }

  /**
   * Creates a new instance {@link LayoutModelPlugin} associate it with the given {@link Model}.
   * 
   * @param model the {@link Model} where this {@link LayoutModelPlugin} belong.
   */
  public LayoutModelPlugin(Model model) {
    super(model);
    setPackageVersion(-1);
    createListOfLayout();
  }


  /**
   * Adds the given {@link Layout} to the listOfLayouts.
   * 
   * <p>The listOfLayouts is initialized if necessary.
   * 
   * @param layout the {@link Layout} instance to add.
   * @see LayoutModelPlugin#addLayout(Layout)
   */
  public void add(Layout layout) {
    addLayout(layout);
  }

  /**
   * Adds the given {@link Layout} to the listOfLayouts.
   * 
   * <p>The listOfLayouts is initialized if necessary.
   * 
   * @param layout the {@link Layout} instance to add.
   */
  public void addLayout(Layout layout) {
    if (layout != null) {
      getListOfLayouts().add(layout);
    }
  }

  /**
   * Creates a new {@link Layout} and adds it to the current list of layouts.
   * 
   * @return the new {@link Layout}.
   */
  public Layout createLayout() {
    return createLayout(null);
  }

  /**
   * Creates a new {@link Layout} and adds it to the current list of layouts.
   * 
   * @param id the id to be set in the new {@link Layout}.
   * @return the new {@link Layout}.
   */
  public Layout createLayout(String id) {
    Layout layout = new Layout();
    layout.setId(id);
    addLayout(layout);
    return layout;
  }

  /**
   * Creates a new {@link ListOf}, adds it to this object as {@link #listOfLayouts}
   * and returns a pointer to it.
   * 
   * @return a new {@link ListOf} object.
   * 
   */
  private ListOf<Layout> createListOfLayout() {
    listOfLayouts = new ListOf<Layout>();
    listOfLayouts.setPackageVersion(-1);
    // changing the ListOf package name from 'core' to 'layout'
    listOfLayouts.setPackageName(null);
    listOfLayouts.setPackageName(LayoutConstants.shortLabel);
    listOfLayouts.setSBaseListType(ListOf.Type.other);
    listOfLayouts.setOtherListName(LayoutConstants.listOfLayouts);

    if (isSetExtendedSBase()) {
      getExtendedSBase().registerChild(listOfLayouts);
    }

    return listOfLayouts;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 2887;
    int result = super.hashCode();
    result = prime * result
        + ((listOfLayouts == null) ? 0 : listOfLayouts.hashCode());
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
    LayoutModelPlugin other = (LayoutModelPlugin) obj;
    if (listOfLayouts == null) {
      if (other.listOfLayouts != null) {
        return false;
      }
    } else if (!listOfLayouts.equals(other.listOfLayouts)) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getChildAt(int)
   */
  @Override
  public SBase getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
    }
    int pos = 0;

    if (isSetListOfLayouts()) {
      if (pos == index) {
        return getListOfLayouts();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"),
      index, pos));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = 0;
    if (isSetListOfLayouts()) {
      count++;
    }
    return count;
  }

  /**
   * 
   * @param i
   * @return
   */
  public Layout getLayout(int i) {
    return listOfLayouts.get(i);
  }

  /**
   * Returns the number of {@link Layout}s of this {@link LayoutModelPlugin}.
   * 
   * @return the number of {@link Layout}s of this {@link LayoutModelPlugin}.
   */
  public int getLayoutCount() {
    return isSetListOfLayouts() ? listOfLayouts.size() : 0;
  }

  /**
   * 
   * @return
   */
  public ListOf<Layout> getListOfLayouts() {
    if (listOfLayouts == null) {
      createListOfLayout();
    }
    return listOfLayouts;
  }

  /**
   * 
   * @return
   */
  public Model getModel() {
    if (isSetExtendedSBase()) {
      return (Model) extendedSBase;
    }

    return null;
  }

  /**
   * Returns the number of {@link Layout}s of this {@link LayoutModelPlugin}.
   * 
   * @return the number of {@link Layout}s of this {@link LayoutModelPlugin}.
   * @libsbml.deprecated same as {@link #getLayoutCount()}
   */
  public int getNumLayouts() {
    return getLayoutCount();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#getParent()
   */
  @Override
  public SBMLDocument getParent() {
    if (isSetExtendedSBase()) {
      return (SBMLDocument) getExtendedSBase().getParent();
    }

    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#getParentSBMLObject()
   */
  @Override
  public SBMLDocument getParentSBMLObject() {
    return getParent();
  }

  /**
   * 
   * @return
   */
  public boolean isSetListOfLayouts() {
    return (listOfLayouts != null) && !listOfLayouts.isEmpty();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    return false;
  }

  /**
   * The listOfLayouts element must contain at least one {@link Layout}.
   * 
   * @param listOfLayouts
   */
  public void setListOfLayouts(ListOf<Layout> listOfLayouts) {
    unsetListOfLayouts();
    this.listOfLayouts = listOfLayouts;

    if (listOfLayouts != null) {
      listOfLayouts.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'layout'
      listOfLayouts.setPackageName(null);
      listOfLayouts.setPackageName(LayoutConstants.shortLabel);
      listOfLayouts.setSBaseListType(ListOf.Type.other);
      listOfLayouts.setOtherListName(LayoutConstants.listOfLayouts);
    }

    if (isSetExtendedSBase()) {
      getExtendedSBase().registerChild(listOfLayouts);
    }
  }

  /**
   * Removes the {@link #listOfLayouts} from this {@link Model} and notifies
   * all registered instances of {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfLayouts() {
    if (listOfLayouts != null) {
      ListOf<Layout> oldListOfLayouts = listOfLayouts;
      listOfLayouts = null;
      oldListOfLayouts.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#clone()
   */
  @Override
  public LayoutModelPlugin clone() {
    return new LayoutModelPlugin(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getElementNamespace()
   */
  @Override
  public String getElementNamespace() {
    return LayoutConstants.getNamespaceURI(getLevel(), getVersion());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPackageName()
   */
  @Override
  public String getPackageName() {
    return LayoutConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPrefix()
   */
  @Override
  public String getPrefix() {
    return LayoutConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getURI()
   */
  @Override
  public String getURI() {
    return getElementNamespace();
  }

}

/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
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
import org.sbml.jsbml.util.TreeNodeChangeListener;


/**
 * 
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @author Clemens Wrzodek
 * @since 1.0
 * @version $Rev$
 */
public class LayoutModelPlugin extends AbstractSBasePlugin {

  // TODO: need to be adapted to the new way of dealing with L3 packages

  /**
   * 
   */
  private static final long serialVersionUID = 4507170457817702658L;

  /**
   * 
   */
  protected ListOf<Layout> listOfLayouts;


  /**
   * 
   * @param elm
   */
  public LayoutModelPlugin(LayoutModelPlugin elm) {
    super(elm);
    // We don't clone the pointer to the containing model.
    if (elm.listOfLayouts != null) {
      listOfLayouts = elm.listOfLayouts.clone();
    }
  }

  /**
   * 
   * @param model
   */
  public LayoutModelPlugin(Model model) {
    super(model);
    createListOfLayout();
  }


  /**
   * 
   * @param layout
   */
  public void add(Layout layout) {
    addLayout(layout);
  }

  /**
   * 
   * @param layout
   */
  public void addLayout(Layout layout) {
    if (layout != null) {
      getListOfLayouts().add(layout);
    }
  }

  /**
   * Creates a new layout and adds it to the current list of layouts.
   * @return new layout.
   */
  public Layout createLayout() {
    return createLayout(null);
  }

  /**
   * 
   * @param id
   * @return
   */
  public Layout createLayout(String id) {
    Layout layout = new Layout(id, getExtendedSBase().getLevel(), getExtendedSBase().getVersion());
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
    listOfLayouts.setNamespace(LayoutConstants.namespaceURI);
    listOfLayouts.setSBaseListType(ListOf.Type.other);
    getExtendedSBase().registerChild(listOfLayouts);

    return listOfLayouts;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Model#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      // ExtendedLayoutModel elm = (ExtendedLayoutModel) object;
      // An equals call on the model would cause a cyclic check!
      // Actually, I'm not sure if we should compare the model
      // here at all because this would be like checking a pointer
      // to the parent node in the SBML tree, which we never do.
      // Therefore, there's also no hashCode method here, because
      // nothing to check, in my opinion.
      // Hence, we can delete this method here.
      // equals &= getModel() == elm.getModel();
    }
    return equals;
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
      throw new IndexOutOfBoundsException(index + " < 0");
    }
    int pos = 0;

    if (isSetListOfLayouts()) {
      if (pos == index) {
        return getListOfLayouts();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(MessageFormat.format(
      "Index {0,number,integer} >= {1,number,integer}",
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
   * 
   * @return
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
    return (Model) extendedSBase;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#getParent()
   */
  @Override
  public SBMLDocument getParent() {
    return (SBMLDocument) getExtendedSBase().getParent();
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
   * 
   * @param listOfLayouts
   */
  public void setListOfLayouts(ListOf<Layout> listOfLayouts) {
    unsetListOfLayouts();
    if (listOfLayouts == null) {
      this.listOfLayouts = new ListOf<Layout>();
    } else {
      this.listOfLayouts = listOfLayouts;
    }
    if ((this.listOfLayouts != null) && (this.listOfLayouts.getSBaseListType() != ListOf.Type.other)) {
      this.listOfLayouts.setSBaseListType(ListOf.Type.other);
    }
    getExtendedSBase().registerChild(listOfLayouts);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return getClass().getSimpleName() + " [listOfLayouts=" + listOfLayouts + "]";
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
    return LayoutConstants.packageName;
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

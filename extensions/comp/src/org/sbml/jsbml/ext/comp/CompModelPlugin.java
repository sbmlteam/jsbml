/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
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
package org.sbml.jsbml.ext.comp;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.IdManager;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 */
public class CompModelPlugin extends CompSBasePlugin implements IdManager {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 7104644706063096211L;

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(Model.class);

  /**
   * 
   */
  private ListOf<Port> listOfPorts;

  /**
   * 
   */
  private ListOf<Submodel> listOfSubmodels;

  /**
   * Maps between the {@link Port} identifiers and themselves.
   */
  private Map<String, Port> mapOfPorts;

  
  // TODO - create constructors with package version ??

  /**
   * Creates a new {@link CompModelPlugin} instance that is a copy of the current {@link CompModelPlugin}.
   * 
   * @param obj the {@link CompModelPlugin} to clone.
   */
  public CompModelPlugin(CompModelPlugin obj) {
    super(obj);

    if (obj.isSetListOfSubmodels()) {
      setListOfSubmodels(obj.getListOfSubmodels().clone());
    }
    if (obj.isSetListOfPorts()) {
      setListOfPorts(obj.getListOfPorts().clone());
    }
    
    // TODO - copy the id map ?? Should not be needed, it should be constructed when adding the cloned Port
    
  }

  /**
   * Creates a new {@link CompModelPlugin} instance, associated with the given {@link Model}
   * 
   * @param model
   */
  public CompModelPlugin(Model model) {
    super(model);
  }

  /**
   * Adds a new {@link Port} to the listOfPorts.
   * <p>The listOfPorts is initialized if necessary.
   *
   * @param port the element to add to the list
   * @return {@code true} (as specified by {@link Collection.add})
   */
  public boolean addPort(Port port) {
    return getListOfPorts().add(port);
  }

  /**
   * Adds a new {@link Submodel} to the listOfSubmodels.
   * <p>The listOfSubmodels is initialized if necessary.
   *
   * @param submodel the element to add to the list
   * @return {@code true} (as specified by {@link Collection.add})
   */
  public boolean addSubmodel(Submodel submodel) {
    return getListOfSubmodels().add(submodel);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.comp.CompSBasePlugin#clone()
   */
  @Override
  public CompModelPlugin clone() {
    return new CompModelPlugin(this);
  }

  /**
   * Creates a new Port element and adds it to the ListOfPorts list
   */
  public Port createPort() {
    return createPort(null);
  }

  /**
   * Creates a new {@link Port} element and adds it to the ListOfPorts list
   *
   * @return a new {@link Port} element
   */
  public Port createPort(String id) {
    Port port = new Port(id, extendedSBase.getLevel(), extendedSBase.getVersion());
    addPort(port);
    return port;
  }

  /**
   * Creates a new Submodel element and adds it to the ListOfSubmodels list
   */
  public Submodel createSubmodel() {
    return createSubmodel(null);
  }

  /**
   * Creates a new {@link Submodel} element and adds it to the ListOfSubmodels list
   *
   * @return a new {@link Submodel} element
   */
  public Submodel createSubmodel(String id) {
    Submodel submodel = new Submodel(id, extendedSBase.getLevel(), extendedSBase.getVersion());
    addSubmodel(submodel);
    return submodel;
  }

  /**
   * Returns a {@link Port} element that has the given 'id' within
   * this {@link Model} or {@code null} if no such element can be found.
   * 
   * @param id
   *        an id indicating a {@link Port} element of the
   *        {@link Model}.
   * @return a {@link Port} element of the {@link Model} that has
   *         the given 'id' as id or {@code null} if no element with this
   *         'id' can be found.
   */
  public Port findPort(String id) {
    return mapOfPorts == null ? null : mapOfPorts.get(id);
  }

  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.comp.CompSBasePlugin#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.comp.CompSBasePlugin#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int childIndex) {

    if (childIndex < 0) {
      throw new IndexOutOfBoundsException(childIndex + " < 0");
    }

    int pos = 0;
    if (isSetListOfSubmodels()) {
      if (pos == childIndex) {
        return getListOfSubmodels();
      }
      pos++;
    }
    if (isSetListOfPorts()) {
      if (pos == childIndex) {
        return getListOfPorts();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      "Index {0,number,integer} >= {1,number,integer}",
      childIndex, +Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.comp.CompSBasePlugin#getChildCount()
   */
  @Override
  public int getChildCount()
  {
    int count = 0;

    if (isSetListOfSubmodels()) {
      count++;
    }
    if (isSetListOfPorts()) {
      count++;
    }

    return count;
  }

  /**
   * TODO: if the ID is mandatory for Submodel objects,
   * one should also add this methods
   */
  //public void removeSubmodel(String id) {
  //  getListOfSubmodels().removeFirst(new NameFilter(id));
  //}

  /**
   * Returns the listOfPorts. Creates it if it is not already existing.
   *
   * @return the listOfPorts
   */
  public ListOf<Port> getListOfPorts() {
    if (!isSetListOfPorts()) {
      listOfPorts = new ListOf<Port>(extendedSBase.getLevel(),
          extendedSBase.getVersion());
      listOfPorts.setNamespace(CompConstants.namespaceURI);
      listOfPorts.setSBaseListType(ListOf.Type.other);
      extendedSBase.registerChild(listOfPorts);
    }
    return listOfPorts;
  }

  /**
   * Returns the listOfSubmodels. Creates it if it is not already existing.
   *
   * @return the listOfSubmodels
   */
  public ListOf<Submodel> getListOfSubmodels() {
    if (!isSetListOfSubmodels()) {
      listOfSubmodels = new ListOf<Submodel>(extendedSBase.getLevel(),
          extendedSBase.getVersion());
      listOfSubmodels.setNamespace(CompConstants.namespaceURI);
      listOfSubmodels.setSBaseListType(ListOf.Type.other);
      extendedSBase.registerChild(listOfSubmodels);
    }
    return listOfSubmodels;
  }

  
  /**
   * Returns a {@link Port} element that has the given 'id' within
   * this {@link Model} or {@code null} if no such element can be found.
   * 
   * @param id
   *        an id indicating a {@link Port} element of the
   *        {@link Model}.
   * @return a {@link Port} element of the {@link Model} that has
   *         the given 'id' as id or {@code null} if no element with this
   *         'id' can be found.
   * @see CompModelPlugin#findPort(String)       
   */
  public Port getPortById(String id) {
    return findPort(id);
  }

  /**
   * Returns the number of {@link Port} objects in this {@link CompModelPlugin}.
   * 
   * @return the number of {@link Port} objects in this {@link CompModelPlugin}.
   */
  public int getPortCount() {
    if (!isSetListOfPorts()) {
      return 0;
    }

    return getListOfPorts().size();
  }

  /**
   * TODO: optionally, create additional create methods with more
   * variables, for instance "bar" variable
   */
  // public Submodel createSubmodel(String id, int bar) {
  //   Submodel submodel = createSubmodel(id);
  //   submodel.setBar(bar);
  //   return submodel;
  // }


  /**
   * Returns the number of {@link Submodel} objects in this {@link CompModelPlugin}.
   * 
   * @return the number of {@link Submodel} objects in this {@link CompModelPlugin}.
   */
  public int getSubmodelCount() {
    if (!isSetListOfSubmodels()) {
      return 0;
    }

    return getListOfSubmodels().size();
  }

  /**
   * Returns {@code true}, if listOfPorts contains at least one element.
   *
   * @return {@code true}, if listOfPorts contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfPorts() {
    if ((listOfPorts == null) || listOfPorts.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * Returns {@code true}, if listOfSubmodels contains at least one element.
   *
   * @return {@code true}, if listOfSubmodels contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfSubmodels() {
    if ((listOfSubmodels == null) || listOfSubmodels.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * TODO: optionally, create additional create methods with more
   * variables, for instance "bar" variable
   */
  // public Port createPort(String id, int bar) {
  //   Port port = createPort(id);
  //   port.setBar(bar);
  //   return port;
  // }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.comp.CompSBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    return false;
  }

  /**
   * Removes an element from the listOfPorts at the given index.
   *
   * @param i the index where to remove the {@link Port}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index < 0 || index > list.size)
   */
  public void removePort(int i) {
    if (!isSetListOfPorts()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfPorts().remove(i);
  }

  /**
   * Removes an element from the listOfPorts.
   *
   * @param port the element to be removed from the list
   * @return {@code true} if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removePort(Port port) {
    if (isSetListOfPorts()) {
      return getListOfPorts().remove(port);
    }
    return false;
  }

  /**
   * TODO: if the ID is mandatory for Port objects,
   * one should also add this methods
   */
  //public void removePort(String id) {
  //  getListOfPorts().removeFirst(new NameFilter(id));
  //}

  /**
   * Removes an element from the listOfSubmodels at the given index.
   *
   * @param i the index where to remove the {@link Submodel}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index < 0 || index > list.size)
   */
  public void removeSubmodel(int i) {
    if (!isSetListOfSubmodels()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfSubmodels().remove(i);
  }

  /**
   * Removes an element from the listOfSubmodels.
   *
   * @param id the id of the element to be removed from the list
   * @return {@code true} if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeSubmodel(String id) {
    Submodel removedSM = getListOfSubmodels().removeFirst(new NameFilter(id));

    if (removedSM != null) {
      return true;
    }

    return false;
  }

  /**
   * Removes an element from the listOfSubmodels.
   *
   * @param submodel the element to be removed from the list
   * @return {@code true} if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeSubmodel(Submodel submodel) {
    if (isSetListOfSubmodels()) {
      return getListOfSubmodels().remove(submodel);
    }
    return false;
  }

  /**
   * Sets the given {@code ListOf<Port>}. If listOfPorts
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfPorts
   */
  public void setListOfPorts(ListOf<Port> listOfPorts) {
    unsetListOfPorts();
    this.listOfPorts = listOfPorts;
    extendedSBase.registerChild(this.listOfPorts);
  }

  /**
   * Sets the given {@code ListOf<Submodel>}. If listOfSubmodels
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfSubmodels
   */
  public void setListOfSubmodels(ListOf<Submodel> listOfSubmodels) {
    unsetListOfSubmodels();
    this.listOfSubmodels = listOfSubmodels;
    extendedSBase.registerChild(this.listOfSubmodels);
  }

  /**
   * Returns {@code true}, if listOfPorts contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfPorts contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfPorts() {
    if (isSetListOfPorts()) {
      ListOf<Port> oldPorts = listOfPorts;
      listOfPorts = null;
      oldPorts.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Returns {@code true}, if listOfSubmodels contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfSubmodels contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfSubmodels() {
    if (isSetListOfSubmodels()) {
      ListOf<Submodel> oldSubmodels = listOfSubmodels;
      listOfSubmodels = null;
      oldSubmodels.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.comp.CompSBasePlugin#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    return null;
  }

  @Override
  public boolean accept(SBase sbase) {
	  
//	  System.out.println("DEBUG : CompModelPlugin accept method called !!");
	  
	  return sbase instanceof Port;
  }

  @Override
  public boolean register(SBase sbase) {
	  
	  boolean success = true;

	  if (sbase instanceof Port) {
		  Port port = (Port) sbase;
		  
		  if (port.isSetId()) {
			  String portId = port.getId();
			  
			  if (mapOfPorts == null) {
				  mapOfPorts = new HashMap<String, Port>();
			  }
			  
			  if (mapOfPorts.containsKey(portId)) {
				  logger.error(MessageFormat.format(
				          "A Port with the id \"{0}\" is already present in this model {1}. The new element will not be added to the model.",
				          portId, (isSetExtendedSBase() ? ((Model) getExtendedSBase()).getId() : "")));
				  success = false;
			  } else {

				  mapOfPorts.put(portId, port);
				  
				  if (logger.isDebugEnabled()) {
					  logger.debug(MessageFormat.format("registered port id={0} in model {1}",
							  portId, (isSetExtendedSBase() ? ((Model) getExtendedSBase()).getId() : "")));
				  }
			  }
		  }
	  } else {
		  logger.error(MessageFormat.format(
				  "Trying to register something that is not a Port: \"{0}\".", sbase));
	  }

	  // TODO : register all the Port children if any !!

	  return success;
  }

  @Override
  public boolean unregister(SBase sbase) {

	  boolean success = true;

	  if (sbase instanceof Port) {
		  Port port = (Port) sbase;

		  if (port.isSetId()) {
			  String portId = port.getId();

			  if (mapOfPorts == null) {
				  return false;
			  }

			  if (mapOfPorts.containsKey(portId)) {
				  mapOfPorts.remove(portId);

				  if (logger.isDebugEnabled()) {
					  logger.debug(MessageFormat.format("unregistered port id={0} in model {1}",
							  portId, (isSetExtendedSBase() ? ((Model) getExtendedSBase()).getId() : "")));
				  }
			  } else {

				  logger.error(MessageFormat.format(
						  "A Port with the id \"{0}\" is not present in this model {1}. Nothing to be done.",
						  portId, (isSetExtendedSBase() ? ((Model) getExtendedSBase()).getId() : "")));
			  }
		  }
	  } else {
		  logger.error(MessageFormat.format(
				  "Trying to unregister something that is not a Port: \"{0}\".", sbase));
	  }

	  // TODO : unregister all the Port children if any !!

	  return success;
  }


}

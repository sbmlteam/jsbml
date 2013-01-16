package org.sbml.jsbml.ext.comp;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ext.AbstractSBasePlugin;

public class CompModelPlugin extends AbstractSBasePlugin {

	/**
	 * 
	 */
	private ListOf<Port> listOfPorts;
	
	/**
	 * 
	 */
	private ListOf<Submodel> listOfSubmodels;
	
	
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
	 * Returns the listOfSubmodels. Creates it if it is not already existing.
	 *
	 * @return the listOfSubmodels
	 */
	public ListOf<Submodel> getListOfSubmodels() {
		if (!isSetListOfSubmodels()) {
			listOfSubmodels = new ListOf<Submodel>(extendedSBase.getLevel(),
					extendedSBase.getVersion());
			listOfSubmodels.addNamespace(CompConstant.namespaceURI);
			listOfSubmodels.setSBaseListType(ListOf.Type.other);
			extendedSBase.registerChild(listOfSubmodels);
		}
		return listOfSubmodels;
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
	 * Returns {@code true}, if listOfSubmodels contain at least one element, 
	 *         otherwise {@code false}
	 *
	 * @return {@code true}, if listOfSubmodels contain at least one element, 
	 *         otherwise {@code false}
	 */
	public boolean unsetListOfSubmodels() {
		if (isSetListOfSubmodels()) {
			ListOf<Submodel> oldSubmodels = this.listOfSubmodels;
			this.listOfSubmodels = null;
			oldSubmodels.fireNodeRemovedEvent();
			return true;
		}
		return false;
	}

	/**
	 * Adds a new {@link Submodel} to the listOfSubmodels.
	 * <p>The listOfSubmodels is initialized if necessary.
	 *
	 * @param submodel the element to add to the list
	 * @return true (as specified by {@link Collection.add})
	 */
	public boolean addSubmodel(Submodel submodel) {
		return getListOfSubmodels().add(submodel);
	}

	/**
	 * Removes an element from the listOfSubmodels.
	 *
	 * @param submodel the element to be removed from the list
	 * @return true if the list contained the specified element
	 * @see List#remove(Object)
	 */
	public boolean removeSubmodel(Submodel submodel) {
		if (isSetListOfSubmodels()) {
			return getListOfSubmodels().remove(submodel);
		}
		return false;
	}

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
	 * TODO: if the ID is mandatory for Submodel objects, 
	 * one should also add this methods
	 */
	//public void removeSubmodel(String id) {
	//  getListOfSubmodels().removeFirst(new NameFilter(id));
	//}

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
	 * TODO: optionally, create additional create methods with more
	 * variables, for instance "bar" variable
	 */
	// public Submodel createSubmodel(String id, int bar) {
	//   Submodel submodel = createSubmodel(id);
	//   submodel.setBar(bar);
	//   return submodel;
	// }

	
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
	 * Returns the listOfPorts. Creates it if it is not already existing.
	 *
	 * @return the listOfPorts
	 */
	public ListOf<Port> getListOfPorts() {
		if (!isSetListOfPorts()) {
			listOfPorts = new ListOf<Port>(extendedSBase.getLevel(),
					extendedSBase.getVersion());
			listOfPorts.addNamespace(CompConstant.namespaceURI);
			listOfPorts.setSBaseListType(ListOf.Type.other);
			extendedSBase.registerChild(listOfPorts);
		}
		return listOfPorts;
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
	 * Returns {@code true}, if listOfPorts contain at least one element, 
	 *         otherwise {@code false}
	 *
	 * @return {@code true}, if listOfPorts contain at least one element, 
	 *         otherwise {@code false}
	 */
	public boolean unsetListOfPorts() {
		if (isSetListOfPorts()) {
			ListOf<Port> oldPorts = this.listOfPorts;
			this.listOfPorts = null;
			oldPorts.fireNodeRemovedEvent();
			return true;
		}
		return false;
	}

	/**
	 * Adds a new {@link Port} to the listOfPorts.
	 * <p>The listOfPorts is initialized if necessary.
	 *
	 * @param port the element to add to the list
	 * @return true (as specified by {@link Collection.add})
	 */
	public boolean addPort(Port port) {
		return getListOfPorts().add(port);
	}

	/**
	 * Removes an element from the listOfPorts.
	 *
	 * @param port the element to be removed from the list
	 * @return true if the list contained the specified element
	 * @see List#remove(Object)
	 */
	public boolean removePort(Port port) {
		if (isSetListOfPorts()) {
			return getListOfPorts().remove(port);
		}
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
	 * TODO: if the ID is mandatory for Port objects, 
	 * one should also add this methods
	 */
	//public void removePort(String id) {
	//  getListOfPorts().removeFirst(new NameFilter(id));
	//}

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
	 * TODO: optionally, create additional create methods with more
	 * variables, for instance "bar" variable
	 */
	// public Port createPort(String id, int bar) {
	//   Port port = createPort(id);
	//   port.setBar(bar);
	//   return port;
	// }

	
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		// TODO Auto-generated method stub
		return false;
	}

	public Map<String, String> writeXMLAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	public TreeNode getChildAt(int childIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getChildCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean getAllowsChildren() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AbstractSBasePlugin clone() {
		// TODO Auto-generated method stub
		return null;
	}

}

package org.sbml.jsbml.ext.dyn;

import java.text.MessageFormat;
import java.util.Map;
import javax.swing.tree.TreeNode;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.ListOf;

/**
 * @author Harold Gomez
 * @since 1.0
 */
public class DynCompartmentPlugin extends DynSBasePlugin {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -3432424478606541965L;

	/**
	 * List of spatial components which describe compartment in space
	 */
	protected ListOf<SpatialComponent> listOfSpatialComponents;

	/**
	 * Empty constructor
	 */
	public DynCompartmentPlugin() {
		super();
		initDefaults();
	}

	/**
	 * Constructor
	 * 
	 * @param model
	 */
	public DynCompartmentPlugin(Compartment compartment) {
		super(compartment);
		initDefaults();
	}

	/**
	 * Constructor
	 * 
	 * @param dynCompartmentPlugin
	 */
	public DynCompartmentPlugin(DynCompartmentPlugin dynCompartmentPlugin) {
		super(dynCompartmentPlugin);
		if (isSetListOfSpatialComponents()) {
			setListOfSpatialComponents(dynCompartmentPlugin
					.getListOfSpatialComponents().clone());
		}
	}

	/**
	 * Initializes custom Class attributes
	 * */
	private void initDefaults() {
		listOfSpatialComponents = new ListOf<SpatialComponent>();
		listOfSpatialComponents.setNamespace(DynConstants.namespaceURI);
		listOfSpatialComponents.setSBaseListType(ListOf.Type.other);

		if (isSetExtendedSBase()) {
			extendedSBase.registerChild(listOfSpatialComponents);
		}
	}

	/**
	 * Returns the value of listOfSpatialComponents
	 * 
	 * @return the value of listOfSpatialComponents
	 */
	public ListOf<SpatialComponent> getListOfSpatialComponents() {
		if (!isSetListOfSpatialComponents()) {
			listOfSpatialComponents = new ListOf<SpatialComponent>();
			listOfSpatialComponents.setNamespace(DynConstants.namespaceURI);
			listOfSpatialComponents.setSBaseListType(ListOf.Type.other);
			extendedSBase.registerChild(listOfSpatialComponents);
		}
		return listOfSpatialComponents;
	}

	/**
	 * Returns whether listOfSpatialComponents is set
	 * 
	 * @return whether listOfSpatialComponents is set
	 */
	public boolean isSetListOfSpatialComponents() {
		if ((listOfSpatialComponents == null)
				|| listOfSpatialComponents.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Sets the value of listOfSpatialComponents
	 */
	public void setListOfSpatialComponents(
			ListOf<SpatialComponent> listOfSpatialComponents) {
		unsetListOfSpatialComponents();
		if (!isSetListOfSpatialComponents()) {
			this.listOfSpatialComponents = new ListOf<SpatialComponent>();
		} else {
			this.listOfSpatialComponents = listOfSpatialComponents;
		}
		if ((this.listOfSpatialComponents != null)
				&& (this.listOfSpatialComponents.getSBaseListType() != ListOf.Type.other)) {
			this.listOfSpatialComponents.setSBaseListType(ListOf.Type.other);
		}
		if (isSetExtendedSBase()) {
			extendedSBase.registerChild(listOfSpatialComponents);
		}
	}

	/**
	 * Unsets the variable listOfSpatialComponents
	 * 
	 * @return {@code true}, if listOfSpatialComponents was set before,
	 *         otherwise {@code false}
	 */
	public boolean unsetListOfSpatialComponents() {
		if (isSetListOfSpatialComponents()) {
			ListOf<SpatialComponent> oldListOfSpatialComponents = this.listOfSpatialComponents;
			this.listOfSpatialComponents = null;
			firePropertyChange(DynConstants.listOfSpatialComponents,
					oldListOfSpatialComponents, this.listOfSpatialComponents);
			return true;
		}
		return false;
	}

	/**
	 * Creates a new SpatialComponent element and adds it to the
	 * listOfSpatialComponents list
	 */
	public SpatialComponent createSpatialComponent() {
		return createSpatialComponent(null);
	}

	/**
	 * Creates a new {@link SpatialComponent} element and adds it to the
	 * ListOfSpatialComponents list
	 *
	 * @return a new {@link SpatialComponent} element
	 */
	public SpatialComponent createSpatialComponent(String id) {
		SpatialComponent spatialComponent = new SpatialComponent(id, null,
				getLevel(), getVersion());
		addSpatialComponent(spatialComponent);
		return spatialComponent;
	}

	/**
	 * Adds a new {@link SpatialComponent} to the ListOfSpatialComponents. The
	 * ListOfSpatialComponents is initialized if necessary.
	 *
	 * @param spatialComponent
	 *            the element to add to the list
	 * @return {@code true} (as specified by {@link Collection.add})
	 */
	public boolean addSpatialComponent(SpatialComponent spatialComponent) {
		return getListOfSpatialComponents().add(spatialComponent);
	}

	/**
	 * Removes the ith element from the ListOfSpatialComponents
	 * 
	 * @param i
	 */
	public void removeSpatialComponent(int i) {
		if (!isSetListOfSpatialComponents()) {
			throw new IndexOutOfBoundsException(Integer.toString(i));
		}
		getListOfSpatialComponents().remove(i);
	}

	/**
	 * Removes an element from the ListOfSpatialComponents
	 *
	 * @param spatialComponent
	 *            the element to be removed from the list
	 * @return {@code true} if the list contained the specified element
	 * @see List#remove(Object)
	 */
	public boolean removeSpatialComponent(SpatialComponent spatialComponent) {
		if (isSetListOfSpatialComponents()) {
			return getListOfSpatialComponents().remove(spatialComponent);
		}
		return false;
	}

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
		DynCompartmentPlugin other = (DynCompartmentPlugin) obj;

		if (listOfSpatialComponents == null) {
			if (other.listOfSpatialComponents != null) {
				return false;
			}
		} else if (!listOfSpatialComponents
				.equals(other.listOfSpatialComponents)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 2141;
		int hashCode = super.hashCode();
		hashCode = prime
				* hashCode
				+ ((listOfSpatialComponents == null) ? 0
						: listOfSpatialComponents.hashCode());
		return hashCode;
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		if (childIndex < 0 || childIndex >= 1) {
			throw new IndexOutOfBoundsException(MessageFormat.format(
					"Index {0,number,integer} >= {1,number,integer}",
					childIndex, +Math.min(getChildCount(), 0)));
		}

		return listOfSpatialComponents;
	}

	@Override
	public int getChildCount() {
		return isSetListOfSpatialComponents() ? 1 : 0;
	}

	@Override
	public DynCompartmentPlugin clone() {
		return new DynCompartmentPlugin(this);
	}

	@Override
	public String toString() {
		return "DynCompartmentPlugin [listOfSpatialComponents="
				+ listOfSpatialComponents + "]";
	}

	@Override
	public Map<String, String> writeXMLAttributes() {
		return null;
	}

	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		return false;
	}

}

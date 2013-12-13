package org.sbml.jsbml.ext.spatial;

import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;

public abstract class AbstractSpatialSBasePlugin extends AbstractSBasePlugin {

	
	public AbstractSpatialSBasePlugin() {
		super();
	}

	public AbstractSpatialSBasePlugin(AbstractSBasePlugin plugin) {
		super(plugin);
	}

	public AbstractSpatialSBasePlugin(SBase extendedSBase) {
		super(extendedSBase);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractTreeNode#getParent()
	 */
	@Override
	public SBase getParent() {
		return (SBase) getExtendedSBase().getParent();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.AbstractSBasePlugin#getParentSBMLObject()
	 */
	@Override
	public SBase getParentSBMLObject() {
		return getParent();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.SBasePlugin#getElementNamespace()
	 */
	@Override
	public String getElementNamespace() {
		return SpatialConstants.getNamespaceURI(getLevel(), getVersion());
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.SBasePlugin#getPackageName()
	 */
	@Override
	public String getPackageName() {
		return SpatialConstants.packageName;
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.SBasePlugin#getPrefix()
	 */
	@Override
	public String getPrefix() {
		return SpatialConstants.shortLabel;
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.SBasePlugin#getURI()
	 */
	@Override
	public String getURI() {
		return getElementNamespace();
	}
}

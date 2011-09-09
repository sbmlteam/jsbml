/*
 * $Id$
 * $URL$
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */
package org.sbml.jsbml.ext.spatial;

import javax.swing.tree.TreeNode;

/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class SpatialParameter extends SpatialCallableSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -3766260342134204275L;

	/**
	 * 
	 */
	private SpatialParameterQualifier qualifier;
	
	/**
	 * 
	 */
	public SpatialParameter() {
		super();
	}
	
	/**
	 * @param level
	 * @param version
	 */
	public SpatialParameter(int level, int version) {
		super(level, version);
	}
	
	/**
	 * @param sb
	 */
	public SpatialParameter(SpatialParameter sb) {
		super(sb);
		if (sb.isSetQualifier()) {
			this.qualifier = sb.getQualifier().clone();
		}
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public SpatialParameter clone() {
		return new SpatialParameter(this);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean equals = super.equals(object);
		if (equals) {
			SpatialParameter sp = (SpatialParameter) object;
			equals &= sp.isSetQualifier() == isSetQualifier();
			if (equals && isSetQualifier()) {
				equals &= sp.getQualifier().equals(getQualifier());
			}
		}
		return equals;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
	 */
	@Override
	public TreeNode getChildAt(int childIndex) {
		if (childIndex < 0) {
			throw new IndexOutOfBoundsException(childIndex + " < 0");
		}
		int pos = 0;
		if (isSetQualifier())  {
			if (childIndex == pos) {
				return getQualifier();
			}
			pos++;
		}
		throw new IndexOutOfBoundsException(isLeaf() ? String.format(
				"Node %s has no children.", getElementName()) : String.format(
				"Index %d >= %d", childIndex, +((int) Math.min(pos, 0))));
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildCount()
	 */
	@Override
	public int getChildCount() {
		return super.getChildCount() + (isSetQualifier() ? 1 : 0);
	}

	/**
	 * @return the qualifier
	 */
	public SpatialParameterQualifier getQualifier() {
		return qualifier;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 991;
		int hashCode = super.hashCode();
		if (isSetQualifier()) {
			hashCode += prime * getQualifier().hashCode();
		}
		return hashCode;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetQualifier() {
		return qualifier != null;
	}

	/**
	 * @param qualifier the qualifier to set
	 */
	public void setQualifier(SpatialParameterQualifier qualifier) {
		this.qualifier = qualifier;
	}

}

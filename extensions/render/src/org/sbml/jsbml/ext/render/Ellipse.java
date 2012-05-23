/*
 * $Id$
 * $URL$
 *
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2012 jointly by the following organizations: 
 * 1. The University of Tuebingen, Germany 
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK 
 * 3. The California Institute of Technology, Pasadena, CA, USA 
 * 
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation. A copy of the license agreement is provided 
 * in the file named "LICENSE.txt" included with this software distribution 
 * and also available online as <http://sbml.org/Software/JSBML/License>. 
 * ---------------------------------------------------------------------------- 
 */ 
package org.sbml.jsbml.ext.render;

import org.sbml.jsbml.PropertyUndefinedError;


/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @version $Rev$
 * @since 1.0
 * @date 08.05.2012
 */
public class Ellipse extends GraphicalPrimitive2D {
	/**
   * 
   */
  private static final long serialVersionUID = -7679215241987476130L;
  protected Double cx, cy, cz, rx, ry; 
	protected Boolean absoluteCx, absoluteCy, absoluteCz, absoluteRx, absoluteRy;

	
	/**
	 * @return the value of cx
	 */
	public double getCx() {
		if (isSetCx()) {
			return cx;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.cx, this);
	}

	/**
	 * @return whether cx is set 
	 */
	public boolean isSetCx() {
		return this.cx != null;
	}

	/**
	 * Set the value of cx
	 */
	public void setCx(double cx) {
		double oldCx = this.cx;
		this.cx = cx;
		firePropertyChange(RenderConstants.cx, oldCx, this.cx);
	}

	/**
	 * Unsets the variable cx 
	 * @return <code>true</code>, if cx was set before, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetCx() {
		if (isSetCx()) {
			double oldCx = this.cx;
			this.cx = null;
			firePropertyChange(RenderConstants.cx, oldCx, this.cx);
			return true;
		}
		return false;
	}
	
	/**
	 * @return the value of cy
	 */
	public double getCy() {
		if (isSetCy()) {
			return cy;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.cy, this);
	}

	/**
	 * @return whether cy is set 
	 */
	public boolean isSetCy() {
		return this.cy != null;
	}

	/**
	 * Set the value of cy
	 */
	public void setCy(Double cy) {
		Double oldCy = this.cy;
		this.cy = cy;
		firePropertyChange(RenderConstants.cy, oldCy, this.cy);
	}

	/**
	 * Unsets the variable cy 
	 * @return <code>true</code>, if cy was set before, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetCy() {
		if (isSetCy()) {
			Double oldCy = this.cy;
			this.cy = null;
			firePropertyChange(RenderConstants.cy, oldCy, this.cy);
			return true;
		}
		return false;
	}
	
	/**
	 * @return the value of cz
	 */
	public double getCz() {
		if (isSetCz()) {
			return cz;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.cz, this);
	}

	/**
	 * @return whether cz is set 
	 */
	public boolean isSetCz() {
		return this.cz != null;
	}

	/**
	 * Set the value of cz
	 */
	public void setCz(Double cz) {
		Double oldCz = this.cz;
		this.cz = cz;
		firePropertyChange(RenderConstants.cz, oldCz, this.cz);
	}

	/**
	 * Unsets the variable cz 
	 * @return <code>true</code>, if cz was set before, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetCz() {
		if (isSetCz()) {
			Double oldCz = this.cz;
			this.cz = null;
			firePropertyChange(RenderConstants.cz, oldCz, this.cz);
			return true;
		}
		return false;
	}
	
	/**
	 * @return the value of rx
	 */
	public double getRx() {
		if (isSetRx()) {
			return rx;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.rx, this);
	}

	/**
	 * @return whether rx is set 
	 */
	public boolean isSetRx() {
		return this.rx != null;
	}

	/**
	 * Set the value of rx
	 */
	public void setRx(Double rx) {
		Double oldRx = this.rx;
		this.rx = rx;
		firePropertyChange(RenderConstants.rx, oldRx, this.rx);
	}

	/**
	 * Unsets the variable rx 
	 * @return <code>true</code>, if rx was set before, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetRx() {
		if (isSetRx()) {
			Double oldRx = this.rx;
			this.rx = null;
			firePropertyChange(RenderConstants.rx, oldRx, this.rx);
			return true;
		}
		return false;
	}
	
	/**
	 * @return the value of ry
	 */
	public double getRy() {
		if (isSetRy()) {
			return ry;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.ry, this);
	}

	/**
	 * @return whether ry is set 
	 */
	public boolean isSetRy() {
		return this.ry != null;
	}

	/**
	 * Set the value of ry
	 */
	public void setRy(Double ry) {
		Double oldRy = this.ry;
		this.ry = ry;
		firePropertyChange(RenderConstants.ry, oldRy, this.ry);
	}

	/**
	 * Unsets the variable ry 
	 * @return <code>true</code>, if ry was set before, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetRy() {
		if (isSetRy()) {
			Double oldRy = this.ry;
			this.ry = null;
			firePropertyChange(RenderConstants.ry, oldRy, this.ry);
			return true;
		}
		return false;
	}
	
	/**
	 * @return the value of absoluteCx
	 */
	public boolean isAbsoluteCx() {
		if (isSetAbsoluteCx()) {
			return absoluteCx;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.absoluteCx, this);
	}

	/**
	 * @return whether absoluteCx is set 
	 */
	public boolean isSetAbsoluteCx() {
		return this.absoluteCx != null;
	}

	/**
	 * Set the value of absoluteCx
	 */
	public void setAbsoluteCx(boolean absoluteCx) {
		Boolean oldAbsoluteCx = this.absoluteCx;
		this.absoluteCx = absoluteCx;
		firePropertyChange(RenderConstants.absoluteCx, oldAbsoluteCx, this.absoluteCx);
	}

	/**
	 * Unsets the variable absoluteCx 
	 * @return <code>true</code>, if absoluteCx was set before, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetAbsoluteCx() {
		if (isSetAbsoluteCx()) {
			Boolean oldAbsoluteCx = this.absoluteCx;
			this.absoluteCx = null;
			firePropertyChange(RenderConstants.absoluteCx, oldAbsoluteCx, this.absoluteCx);
			return true;
		}
		return false;
	}
	
	/**
	 * @return the value of absoluteCy
	 */
	public boolean isAbsoluteCy() {
		if (isSetAbsoluteCy()) {
			return absoluteCy;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.absoluteCy, this);
	}

	/**
	 * @return whether absoluteCy is set 
	 */
	public boolean isSetAbsoluteCy() {
		return this.absoluteCy != null;
	}

	/**
	 * Set the value of absoluteCy
	 */
	public void setAbsoluteCy(boolean absoluteCy) {
		Boolean oldAbsoluteCy = this.absoluteCy;
		this.absoluteCy = absoluteCy;
		firePropertyChange(RenderConstants.absoluteCy, oldAbsoluteCy, this.absoluteCy);
	}

	/**
	 * Unsets the variable absoluteCy 
	 * @return <code>true</code>, if absoluteCy was set before, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetAbsoluteCy() {
		if (isSetAbsoluteCy()) {
			Boolean oldAbsoluteCy = this.absoluteCy;
			this.absoluteCy = null;
			firePropertyChange(RenderConstants.absoluteCy, oldAbsoluteCy, this.absoluteCy);
			return true;
		}
		return false;
	}
	
	/**
	 * @return the value of absoluteCz
	 */
	public boolean isAbsoluteCz() {
		if (isSetAbsoluteCz()) {
			return absoluteCz;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.absoluteCz, this);
	}

	/**
	 * @return whether absoluteCz is set 
	 */
	public boolean isSetAbsoluteCz() {
		return this.absoluteCz != null;
	}

	/**
	 * Set the value of absoluteCz
	 */
	public void setAbsoluteCz(boolean absoluteCz) {
		Boolean oldAbsoluteCz = this.absoluteCz;
		this.absoluteCz = absoluteCz;
		firePropertyChange(RenderConstants.absoluteCz, oldAbsoluteCz, this.absoluteCz);
	}

	/**
	 * Unsets the variable absoluteCz 
	 * @return <code>true</code>, if absoluteCz was set before, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetAbsoluteCz() {
		if (isSetAbsoluteCz()) {
			Boolean oldAbsoluteCz = this.absoluteCz;
			this.absoluteCz = null;
			firePropertyChange(RenderConstants.absoluteCz, oldAbsoluteCz, this.absoluteCz);
			return true;
		}
		return false;
	}
	
	
	/**
	 * @return the value of absoluteRx
	 */
	public boolean isAbsoluteRx() {
		if (isSetAbsoluteRx()) {
			return absoluteRx;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.absoluteRx, this);
	}

	/**
	 * @return whether absoluteRx is set 
	 */
	public boolean isSetAbsoluteRx() {
		return this.absoluteRx != null;
	}

	/**
	 * Set the value of absoluteRx
	 */
	public void setAbsoluteRx(boolean absoluteRx) {
		Boolean oldAbsoluteRx = this.absoluteRx;
		this.absoluteRx = absoluteRx;
		firePropertyChange(RenderConstants.absoluteRx, oldAbsoluteRx, this.absoluteRx);
	}

	/**
	 * Unsets the variable absoluteRx 
	 * @return <code>true</code>, if absoluteRx was set before, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetAbsoluteRx() {
		if (isSetAbsoluteRx()) {
			Boolean oldAbsoluteRx = this.absoluteRx;
			this.absoluteRx = null;
			firePropertyChange(RenderConstants.absoluteRx, oldAbsoluteRx, this.absoluteRx);
			return true;
		}
		return false;
	}
	
	
	/**
	 * @return the value of absoluteRy
	 */
	public boolean isAbsoluteRy() {
		if (isSetAbsoluteRy()) {
			return absoluteRy;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.absoluteRy, this);
	}

	/**
	 * @return whether absoluteRy is set 
	 */
	public boolean isSetAbsoluteRy() {
		return this.absoluteRy != null;
	}

	/**
	 * Set the value of absoluteRy
	 */
	public void setAbsoluteRy(boolean absoluteRy) {
		Boolean oldAbsoluteRy = this.absoluteRy;
		this.absoluteRy = absoluteRy;
		firePropertyChange(RenderConstants.absoluteRy, oldAbsoluteRy, this.absoluteRy);
	}

	/**
	 * Unsets the variable absoluteRy 
	 * @return <code>true</code>, if absoluteRy was set before, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetAbsoluteRy() {
		if (isSetAbsoluteRy()) {
			Boolean oldAbsoluteRy = this.absoluteRy;
			this.absoluteRy = null;
			firePropertyChange(RenderConstants.absoluteRy, oldAbsoluteRy, this.absoluteRy);
			return true;
		}
		return false;
	}
}

/*
 * $Id: CurveSegmentImpl.java 1445 2013-01-04 08:54:54Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/extensions/layout/src/org/sbml/jsbml/ext/layout/CurveSegmentImpl.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
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
package org.sbml.jsbml.ext.layout;


/**
 * @author Sebastian Fr&oum;hlich
 * @author rodrigue
 * @since 1.0
 * @version $Rev: 1445 $
 */
public class CurveSegmentImpl extends CubicBezier {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -5085246314333062152L;


	/**
	 * 
	 */
	public CurveSegmentImpl() {
	  super();
	  initDefaults();
	}

	/**
	 * 
	 * @param curveSegment
	 */
	public CurveSegmentImpl(CurveSegmentImpl curveSegment) {
		super(curveSegment);
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public CurveSegmentImpl(int level, int version) {
		super(level, version);
		initDefaults();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public CurveSegmentImpl clone() {
		return new CurveSegmentImpl(this);
	}

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      CurveSegmentImpl curveSegment = (CurveSegmentImpl) object;
      equals &= curveSegment.isSetType() == isSetType();
      if (equals && isSetType()) {
        equals &= curveSegment.getType().equals(getType());
      }
    }
    return equals;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 937;
    int hashCode = super.hashCode();
    if (isSetType()) {
      hashCode += prime * getType().hashCode();
    }
    return hashCode;
  }


	/**
	 * 
	 */
	private void initDefaults() {
	  addNamespace(LayoutConstants.namespaceURI);
  }


}

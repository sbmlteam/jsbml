/*
 * $Id: AbstractFBCSBasePlugin.java 2168 2015-03-31 12:59:32Z niko-rodrigue $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/extensions/fbc/src/org/sbml/jsbml/ext/fbc/AbstractFBCSBasePlugin.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2015  jointly by the following organizations:
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
package org.sbml.jsbml.ext.fbc;

import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;

/**
 * 
 * @author Andreas Dr&auml;ger
 * @version $Rev: 2168 $
 * @since 1.1
 * @date 06.03.2015
 */
public abstract class AbstractFBCSBasePlugin extends AbstractSBasePlugin {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 4959025739069449677L;

  /**
   * 
   */
  public AbstractFBCSBasePlugin() {
    super();
    setPackageVersion(-1);
    elementNamespace = FBCConstants.namespaceURI; // TODO - removed once the mechanism are in place to set package version and namespace
  }

  /**
   * 
   * @param plugin
   */
  public AbstractFBCSBasePlugin(AbstractFBCSBasePlugin plugin) {
    super(plugin);
  }

  /**
   * 
   * @param sbase
   */
  public AbstractFBCSBasePlugin(SBase sbase) {
    super(sbase);
    setPackageVersion(-1);
    elementNamespace = FBCConstants.namespaceURI; // TODO - removed once the mechanism are in place to set package version and namespace
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPackageName()
   */
  @Override
  public String getPackageName() {
    return FBCConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPrefix()
   */
  @Override
  public String getPrefix() {
    return FBCConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getURI()
   */
  @Override
  public String getURI() {
    return getElementNamespace();
  }

}

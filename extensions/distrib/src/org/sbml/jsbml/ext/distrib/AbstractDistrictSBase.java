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
package org.sbml.jsbml.ext.distrib;

import java.util.Map;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.SBase;


/**
 * 
 * @author rodrigue
 * @since 1.4
 */
public abstract class AbstractDistrictSBase extends AbstractSBase {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  /**
   * 
   */
  public AbstractDistrictSBase() {
  }


  /**
   * @param level
   * @param version
   */
  public AbstractDistrictSBase(int level, int version) {
    super(level, version);
  }


  /**
   * @param sb
   */
  public AbstractDistrictSBase(SBase sb) {
    super(sb);
  }


  /**
   * @param id
   */
  public AbstractDistrictSBase(String id) {
    super(id);
  }


  /**
   * @param id
   * @param level
   * @param version
   */
  public AbstractDistrictSBase(String id, int level, int version) {
    super(id, level, version);
  }


  /**
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public AbstractDistrictSBase(String id, String name, int level, int version) {
    super(id, name, level, version);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

      if (isSetId() && getVersion() == 1) {
        attributes.remove("id");
        attributes.put(DistribConstants.shortLabel + ":id", getId());
      }
      if (isSetName() && getVersion() == 1) {
        attributes.remove("name");
        attributes.put(DistribConstants.shortLabel + ":name", getId());
      }

      return attributes;
  }

}

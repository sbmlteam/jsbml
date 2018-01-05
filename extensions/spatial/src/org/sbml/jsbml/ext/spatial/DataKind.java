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
package org.sbml.jsbml.ext.spatial;


/**
 * This enum type was created following the specifications defined in Spatial Package v0.90. 
 * 
 * The fields here are intentionally left upper case (in contrast to the spec where they are lower case) 
 * because float and double are in conflict with the Java primitive types float and double. 
 * Classes using DataKind need to use the methods toLowerCase() and toUpperCase() to deal with these values 
 * in the methods writeXMLAttributes() and readAttribute(). 
 * @author Piero Dalle Pezze
 * @since 1.0
 */
public enum DataKind {
  /**
   * To indicate 8-bit unsigned integer
   */
  UINT8,
  /**
   * To indicate 16-bit unsigned integer
   */
  UINT16,
  /**
   * To indicate 32-bit unsigned integer
   */
  UINT32,
  /**
   * To indicate single-precision (32-bit) floating point values
   */
  FLOAT,
  /**
   * To indicate double-precision (64-bit) floating point values
   */
  DOUBLE;
}
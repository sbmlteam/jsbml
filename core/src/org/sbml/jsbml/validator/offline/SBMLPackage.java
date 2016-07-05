/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * Copyright (C) 2009-2016 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml.validator.offline;

/**
 * A enum which contains all the supported packages for offline validation.
 * Every SBMLPackage value stores the offset of their error codes.
 * 
 * @author Roman
 * @since 1.2
 * @date 05.07.2016
 */
public enum SBMLPackage {
                         CORE(0),
                         COMP(1_000_000),
                         REQ(1_100_000),
                         SPATIAL(1_200_000),
                         RENDER(1_300_000),
                         FBC(2_000_000),
                         QUAL(3_000_000),
                         GROUPS(4_000_000),
                         DISTRIB(5_000_000),
                         LAYOUT(6_000_000),
                         MULTI(7_000_000),
                         ARRAYS(8_000_000),
                         DYN(9_000_000);

  public final int offset;


  private SBMLPackage(int offset) {
    this.offset = offset;
  }


  @Override
  /**
   * @return the package name in lower case.
   */
  public String toString() {
    return super.toString().toLowerCase();
  }


  /**
   * @return the {@link SBMLPackage} in which offset the error code was defined.
   */
  public static SBMLPackage getPackageForError(int errorId) {
    // Clear the last five digits
    int offset = (int) ((double) (errorId) / 100_000.0) * 100_000;
    return getPackageWithOffset(offset);
  }


  /**
   * @param offset,
   *        should be a multiplied of 100.000
   * @return the {@link SBMLPackage} with the given offset, or {@code null} if
   *         no SBMLPackage matches the offset
   */
  public static SBMLPackage getPackageWithOffset(int offset) {
    for (SBMLPackage p : values()) {
      if (p.offset == offset) {
        return p;
      }
    }
    return null;
  }


  /**
   * @param name,
   *        will be casted to lower case.
   * @return the {@link SBMLPackage} with the given name, or
   *         {@link SBMLPackage#CORE} if {@code name} is empty or {@code null}
   *         if there is no package with the given name.
   */
  public static SBMLPackage getPackageWithName(String name) {
    if (name.isEmpty()) {
      return CORE;
    }
    String n = name.toLowerCase();
    for (SBMLPackage p : values()) {
      if (n.equals(p.toString())) {
        return p;
      }
    }
    return null;
  }


  /**
   * Converts a error code to a String.
   * <p>
   * Example: <br>
   * <table border="1">
   * <tr>
   * <th>errorCode</th>
   * <th>Result (with core)</th>
   * <th>Result (without core)</th>
   * </tr>
   * <tr>
   * <td>{@code 20603}</td>
   * <td>{@code "core-20603"}</td>
   * <td>{@code "20603"}</td>
   * </tr>
   * <tr>
   * <td>{@code 6020603}</td>
   * <td>{@code "layout-6020603"}</td>
   * <td>{@code "layout-6020603"}</td>
   * </tr>
   * </table>
   * <p>
   * 
   * @param errorCode
   * @param withCorePackage,
   *        if {@code true} the error codes from the SBML core package will also
   *        be added.
   * @return the error code as a String, with at least 5 chars.
   */
  public static String convertErrorCodeToString(int errorCode,
    boolean withCorePackage) {
    SBMLPackage p = SBMLPackage.getPackageForError(errorCode);

    String prefix = "";

    if (withCorePackage || !p.equals(CORE)) {
      prefix = p.toString() + "-";
    }
    return prefix + String.format("%05d", errorCode - p.offset);

  }

  /**
   * If the errorCode string is leaded by a package name, this package will be parsed
   * and their offset added
   * @param errorString
   * @return
   */
  public static int convertStringToErrorCode(String errorString) {
    String[] blocks = errorString.split("-");
    if (blocks.length == 1) {
      return Integer.parseInt(blocks[0]);
    } else {
      SBMLPackage p = SBMLPackage.getPackageWithName(blocks[0]);
      int id = Integer.parseInt(blocks[1]);
      return id + p.offset;
    }
  }
}

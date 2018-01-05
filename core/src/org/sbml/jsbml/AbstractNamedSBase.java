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
package org.sbml.jsbml;

import org.sbml.jsbml.validator.SyntaxChecker;

/**
 * Base abstract class for all the SBML components with an id and a name (optional or
 * not) in SBML Level 3 Version 1 or lower. Since SBML Level 3 Version 2, every
 * {@link SBase} have an id an a name. This class is kept for compatibility but it is more or less empty,
 * all code dealing with id and name has been moved to AbstractSBase.
 * 
 * 
 * @author Andreas Dr&auml;ger
 * @author Nicolas Rodriguez
 * @author Marine Dumousseau
 * @since 0.8
 * 
 */
public abstract class AbstractNamedSBase extends AbstractSBase implements
NamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -9186483076164094500L;

  /**
   * Checks whether the given idCandidate is a valid identifier according to
   * the SBML specifications.
   * 
   * @param idCandidate
   *            The {@link String} to be tested.
   * @param level
   *            Level of the SBML to be used.
   * @param version
   *            Version of the SBML to be used.
   * @return True if the argument satisfies the specification of identifiers
   *         in the SBML specifications or false otherwise.
   * @deprecated use {@link SyntaxChecker#isValidId(String, int, int)}
   */
  @Deprecated
  public static final boolean isValidId(String idCandidate, int level,
    int version) {
    return SyntaxChecker.isValidId(idCandidate, level, version);
  }

  /**
   * Creates a new {@link AbstractNamedSBase} instance. By default, id and name are {@code null}.
   */
  public AbstractNamedSBase() {
    super();
  }

  /**
   * Creates an {@link AbstractNamedSBase} from a level and version. By default, id
   * and name are {@code null}.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public AbstractNamedSBase(int level, int version) {
    super(level, version);
  }

  /**
   * Creates an {@link AbstractNamedSBase} with the given identifier. Note
   * that with this constructor the level and version of the element are not
   * specified. These elements are however required to ensure the validity of
   * the SBML data structure. Without level and version, it may not be
   * possible to serialize this class to SBML.
   * 
   * @param id the id of this {@code AbstractNamedSBase}
   */
  public AbstractNamedSBase(String id) {
    this();
    setId(id);
  }

  /**
   * Creates an {@link AbstractNamedSBase} from an id, level and version.
   * 
   * @param id the id of this {@code AbstractNamedSBase}
   * @param level the SBML level
   * @param version the SBML version
   */
  public AbstractNamedSBase(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates an AbctractNamedSBase from an id, name, level and version.
   * 
   * @param id the id of this {@code AbstractNamedSBase}
   * @param name the name of this {@code AbstractNamedSBase}
   * @param level the SBML level
   * @param version the SBML version
   */
  public AbstractNamedSBase(String id, String name, int level, int version) {
    super(level, version);
    setId(id);
    setName(name);
  }

  /**
   * Creates an {@link AbstractNamedSBase} from a given {@link AbstractNamedSBase}.
   * 
   * @param nsb an {@code AbstractNamedSBase} object to clone
   */
  public AbstractNamedSBase(AbstractNamedSBase nsb) {
    super(nsb);
  }

}

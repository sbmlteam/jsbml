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

package org.sbml.jsbml.validator.offline.constraints.helper;

import java.util.HashSet;
import java.util.Set;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SimpleSpeciesReference;
import org.sbml.jsbml.util.filters.Filter;

/**
 * Collection of helpful functions and variables for validation.
 * 
 * @author Roman
 * @since 1.2
 * @date 05.08.2016
 */
public final class ValidationTools {
  
  public static final String KEY_META_ID_SET = "metaIds";

  public static Filter FILTER_IS_FUNCTION = new Filter() {

                                            @Override
                                            public boolean accepts(Object o) {
                                              return ((ASTNode) o).isFunction();
                                            }

                                          };

  public static Filter FILTER_IS_NAME     = new Filter() {

                                            @Override
                                            public boolean accepts(Object o) {
                                              return ((ASTNode) o).isName();
                                            }

                                          };


  public static boolean containsMathOnlyPredefinedFunctions(ASTNode math) {
    if (math != null) {

      if (math.isFunction()) {
        // Can't be user defined
        return math.getType() != ASTNode.Type.FUNCTION;
      } else {
        // Check all children functions
        for (ASTNode func : math.getListOfNodes(FILTER_IS_FUNCTION)) {
          if (func.getType() == Type.FUNCTION) {
            return false;
          }
        }
      }
    }

    return true;
  }
  
  public static Set<String> getDefinedSpecies(Reaction r)
  {
    Set<String> definedSpecies = new HashSet<String>();

    for (SimpleSpeciesReference ref : r.getListOfProducts()) {
      definedSpecies.add(ref.getSpecies());
    }

    for (SimpleSpeciesReference ref : r.getListOfReactants()) {
      definedSpecies.add(ref.getSpecies());
    }

    for (SimpleSpeciesReference ref : r.getListOfModifiers()) {
      definedSpecies.add(ref.getSpecies());
    }
    
    return definedSpecies;
  }
}

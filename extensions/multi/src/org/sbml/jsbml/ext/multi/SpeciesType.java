/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
package org.sbml.jsbml.ext.multi;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.UniqueNamedSBase;

/**
 * 
 * <p/>The element SpeciesType, which is part of SBML Level 2 Version 4 specification, is not part
 * of SBML Level 3 Version 1 Core any more. Instead, it will be defined in the multi package. The
 * SpeciesType element carries not only the basic attributes which it had in SBML Level 2 Version 4
 * (metaid, id, name), but is also extended for the needs of describing multi-component entities
 * with the attribute bindingSite and for the needs of multistate entities by linking it to a list of
 * StateFeatures
 * <p/>A species type can be used to describe a component of a supra-macromolecular assembly,
 * but also a domain of a macromolecule. Such a domain can be a portion of the macromolecule,
 * a non-connex set of atoms forming a functional domain, or just a conceptual construct suiting
 * the needs of the modeler. The type of component can be specified by referring terms from the
 * subbranch functional entity of the <a href="http://biomodels.net/sbo/">Systems Biology Ontology</a>
 * through the optional sboTerm attribute. The following table provides typical examples of
 * component or domains (the list is absolutely not complete).
 * <table>
 *   <tr>
 *     <th> SBO identifier </th><th> Definition </th>
 *   </tr><tr>
 *     <td> SBO:0000242 </td><td> channel </td>
 *   </tr><tr>
 *     <td> SBO:0000244 </td><td> receptor </td>
 *   </tr><tr>
 *     <td> SBO:0000284 </td><td> transporter </td>
 *   </tr><tr>
 *     <td> SBO:0000280 </td><td> ligand </td>
 *   </tr><tr>
 *     <td> SBO:0000493 </td><td> functional domain </td>
 *   </tr><tr>
 *     <td> SBO:0000494 </td><td> binding site </td>
 *   </tr><tr>
 *     <td> SBO:0000495 </td><td> catalytic site </td>
 *   </tr><tr>
 *     <td> SBO:0000496 </td><td> transmembrane domain </td>
 *   </tr>
 * </table>
 * 
 * @author Nicolas Rodriguez
 *
 */
@SuppressWarnings("deprecation")
public class SpeciesType extends org.sbml.jsbml.SpeciesType  implements UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 2L;

  // TODO - compartment BindingSiteSpeciesType listOfSpeciesFeatureTypes listOfSpeciesTypeInstances
  // TODO - listOfSpeciesTypeComponents listOfInSpeciesTypeBonds

  /**
   * 
   */
  public SpeciesType() {
    super();
    initDefaults();
  }

  @Override
  public boolean isIdMandatory() {
    return false;
  }

  @Override
  public SpeciesType clone() {
    // TODO
    return null;
  }



  /**
   * 
   */
  public void initDefaults() {
    setNamespace(MultiConstants.namespaceURI);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(index + " < 0");
    }

    int count = super.getChildCount(), pos = 0;
    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }
    
    // TODO
//    if (isSetListOfStateFeatures()) {
//      if (pos == index) {
//        return getListOfStateFeatures();
//      }
//      pos++;
//    }

    throw new IndexOutOfBoundsException(MessageFormat.format(
      "Index {0,number,integer} >= {1,number,integer}",
      index, +Math.min(pos, 0)));
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();

    // TODO
//    if (isSetListOfStateFeatures()) {
//      count++;
//    }

    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value)
  {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      // TODO
//      if (attributeName.equals(MultiConstants.bindingSite)) {
//        setBindingSite(StringTools.parseSBMLBoolean(value));
//      } else {
//        isAttributeRead = false;
//      }
    }

    return isAttributeRead;

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetId()) {
      attributes.remove("id");
      attributes.put(MultiConstants.shortLabel+ ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(MultiConstants.shortLabel+ ":name", getName());
    }
    // TODO
//    if (isSetBindingSite()) {
//      attributes.put(MultiConstants.shortLabel + ':' + MultiConstants.bindingSite,
//        Boolean.toString(isBindingSite()));
//    }

    return attributes;
  }

  // TODO: equals, hashCode, toString, more constructors, ...
}

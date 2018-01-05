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

import java.text.MessageFormat;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.util.ResourceManager;
import org.sbml.jsbml.util.StringTools;

/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class SpatialSpeciesPlugin extends AbstractSpatialSBasePlugin {



  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 1636970127352490380L;

  /**
   * 
   */
  private Boolean spatial;

  /**
   * Localization support
   */
  private static final ResourceBundle bundle = ResourceManager.getBundle("org.sbml.jsbml.ext.spatial.Messages");



  /**
   * 
   */
  public SpatialSpeciesPlugin() {
    super();
  }

  /**
   * 
   * @param sp
   */
  public SpatialSpeciesPlugin(Species sp) {
    super(sp);
  }

  /**
   * @param sb
   */
  public SpatialSpeciesPlugin(SpatialSpeciesPlugin sb) {
    super(sb);

    if (sb.isSetSpatial()) {
      setSpatial(sb.isSpatial());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public SpatialSpeciesPlugin clone() {
    return new SpatialSpeciesPlugin(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#getExtendedSBase()
   */
  @Override
  public Species getExtendedSBase() {
    if (isSetExtendedSBase()) {
      return (Species) super.getExtendedSBase();
    }

    return null;
  }


  /**
   * @return the isSpatial
   */
  public boolean getSpatial() {
    return spatial;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 997;
    int hashCode = super.hashCode();
    if (isSetSpatial()) {
      hashCode += prime * spatial.hashCode();
    }
    return hashCode;
  }

  /**
   * @return
   */
  public boolean isSetSpatial() {
    return spatial != null;
  }

  /**
   * 
   * @return
   * @see #getSpatial
   */
  public boolean isSpatial() {
    return getSpatial();
  }

  /**
   * @param isSpatial the isSpatial to set
   */
  public void setSpatial(boolean isSpatial) {
    spatial = Boolean.valueOf(isSpatial);

    // Check if the compartment of the Species has a child of CompartmentMapping
    if (isSetExtendedSBase()) {
      Species species = getExtendedSBase();
      Compartment compartment = species.getCompartmentInstance();

      if (compartment != null) {
        SpatialCompartmentPlugin spatialCompartment = (SpatialCompartmentPlugin) compartment.getExtension(SpatialConstants.namespaceURI);

        boolean cmSet = spatialCompartment.isSetCompartmentMapping();

        if (!cmSet) {
          throw new SBMLException(bundle.getString("COMPARTMENT_MAPPING_NOT_SET"));
        }
      }
    }


  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = false;

    if (attributeName.equals(SpatialConstants.isSpatial)) {
      try {
        setSpatial(StringTools.parseSBMLBoolean(value));
        isAttributeRead = true;
      } catch (Exception e) {
        throw new SBMLException(
          MessageFormat.format(bundle.getString("COULD_NOT_READ"), value,
            SpatialConstants.isSpatial));
      }

    }

    return isAttributeRead;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetSpatial()) {
      attributes.put(SpatialConstants.shortLabel + ':' + SpatialConstants.isSpatial, spatial.toString());
    }

    return attributes;
  }


  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return false;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int childIndex) {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    return 0;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      SpatialSpeciesPlugin species = (SpatialSpeciesPlugin) object;
      equal &= species.isSetSpatial() == isSetSpatial();
      equal &= species.getSpatial() == getSpatial();
    }
    return equal;
  }

  //  TODO: redo coefficientCheck for species object
  //  public boolean coefficientCheck() {
  //    boolean check = true;
  //    Model model = getModel();
  //    ListOf<Parameter> params = model.getListOfParameters();
  //    ListOf<DiffusionCoefficient> diffCoeffs = new ListOf<DiffusionCoefficient>();
  //    ListOf<AdvectionCoefficient> advCoeffs = new ListOf<AdvectionCoefficient>();
  //
  //    for (Parameter p: params) {
  //      SpatialParameterPlugin paramPlugin =
  //          (SpatialParameterPlugin) p.getExtension(SpatialConstants.shortLabel);
  //
  //      if (paramPlugin != null) {
  //        ParameterType paramType = paramPlugin.getParamType();
  //        if (paramType.isSetSpeciesReference()) {
  //          if (equals(paramType.getSpeciesInstance())) {
  //
  //            if (paramType instanceof DiffusionCoefficient) {
  //              diffCoeffs.add((DiffusionCoefficient) paramType);
  //            }
  //            else if (paramType instanceof AdvectionCoefficient) {
  //              advCoeffs.add((AdvectionCoefficient) paramType);
  //            }
  //          }
  //        }
  //      }
  //    }
  //
  //    HashSet<Integer> coordDimensions = new HashSet<Integer>();
  //
  //    // Now you have a list of Parameter Type coefficients, go through and check if there is one
  //    if (diffCoeffs.isEmpty()) {
  //      if  (advCoeffs.isEmpty()) {
  //        check = false;
  //        throw new SBMLException("There is no species Id set for this parameter.");
  //      } else {
  //        for (AdvectionCoefficient ac : advCoeffs) {
  //          if (coordDimensions.contains(ac.getCoordinateIndex())) {
  //            check = false;
  //          } else {
  //            coordDimensions.add(ac.getCoordinateIndex());
  //          }
  //        }
  //      }
  //    } else {
  //
  //      coordDimensions = new HashSet<Integer>();
  //
  //      for (DiffusionCoefficient dc : diffCoeffs) {
  //        if (coordDimensions.contains(dc.getCoordinateIndex())) {
  //          check = false;
  //        } else {
  //          coordDimensions.add(dc.getCoordinateIndex());
  //        }
  //      }
  //    }
  //
  //    return check;
  //
  //  }


}

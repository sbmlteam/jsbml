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
package org.sbml.jsbml.ext.render;

import java.awt.Color;
import java.text.MessageFormat;
import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @since 1.0
 */
public class RenderInformationBase extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = -9096154126197866584L;

  /**
   * 
   */
  protected String programName;
  /**
   * 
   */
  protected String programVersion;
  /**
   * 
   */
  protected String referenceRenderInformation;
  /**
   * 
   */
  protected Color backgroundColor;
  /**
   * 
   */
  protected ListOf<ColorDefinition> listOfColorDefinitions;
  /**
   * 
   */
  protected ListOf<GradientBase> listOfGradientDefinitions;
  /**
   * 
   */
  protected ListOf<LineEnding> listOfLineEndings;

  /**
   * Creates an RenderInformationBase instance
   */
  public RenderInformationBase() {
    super();
    initDefaults();
  }

  /**
   * Creates a RenderInformationBase instance with an id.
   * 
   * @param id
   */
  public RenderInformationBase(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a RenderInformationBase instance with a level and version.
   * 
   * @param level
   * @param version
   */
  public RenderInformationBase(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * Creates a RenderInformationBase instance with an id, level, and version.
   * 
   * @param id
   * @param level
   * @param version
   */
  public RenderInformationBase(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates a RenderInformationBase instance with an id, name, level, and version.
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public RenderInformationBase(String id, String name, int level, int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(Integer.valueOf(RenderConstants.MIN_SBML_LEVEL),
      Integer.valueOf(RenderConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }

  /**
   * Clone constructor
   * @param obj
   */
  public RenderInformationBase(RenderInformationBase obj) {
    super(obj);
    
    programName = obj.programName;
    programVersion = obj.programVersion;
    referenceRenderInformation = obj.referenceRenderInformation;
    backgroundColor = obj.backgroundColor;

    if (obj.isSetListOfColorDefinitions()) {
      setListOfColorDefinitions(obj.getListOfColorDefinitions().clone());
    }
    if (obj.isSetListOfGradientDefinitions()) {
      setListOfGradientDefinitions(obj.getListOfGradientDefinitions().clone());
    }
    if (obj.isSetListOfLineEndings()) {
      setListOfLineEndings(obj.getListOfLineEndings().clone());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public RenderInformationBase clone() {
    return new RenderInformationBase(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = RenderConstants.shortLabel;
    
    backgroundColor = new Color(0, 0, 0);
    programName = null;
    programVersion = null;
    referenceRenderInformation = null;
    listOfColorDefinitions = null;
    listOfGradientDefinitions = null;
    listOfLineEndings = null;
  }




  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3041;
    int result = super.hashCode();
    result = prime * result
        + ((backgroundColor == null) ? 0 : backgroundColor.hashCode());
    result = prime
        * result
        + ((listOfColorDefinitions == null) ? 0
          : listOfColorDefinitions.hashCode());
    result = prime
        * result
        + ((listOfGradientDefinitions == null) ? 0
          : listOfGradientDefinitions.hashCode());
    result = prime * result
        + ((listOfLineEndings == null) ? 0 : listOfLineEndings.hashCode());
    result = prime * result
        + ((programName == null) ? 0 : programName.hashCode());
    result = prime * result
        + ((programVersion == null) ? 0 : programVersion.hashCode());
    result = prime
        * result
        + ((referenceRenderInformation == null) ? 0
          : referenceRenderInformation.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    RenderInformationBase other = (RenderInformationBase) obj;
    if (backgroundColor == null) {
      if (other.backgroundColor != null) {
        return false;
      }
    } else if (!backgroundColor.equals(other.backgroundColor)) {
      return false;
    }
    if (listOfColorDefinitions == null) {
      if (other.listOfColorDefinitions != null) {
        return false;
      }
    } else if (!listOfColorDefinitions.equals(other.listOfColorDefinitions)) {
      return false;
    }
    if (listOfGradientDefinitions == null) {
      if (other.listOfGradientDefinitions != null) {
        return false;
      }
    } else if (!listOfGradientDefinitions.equals(other.listOfGradientDefinitions)) {
      return false;
    }
    if (listOfLineEndings == null) {
      if (other.listOfLineEndings != null) {
        return false;
      }
    } else if (!listOfLineEndings.equals(other.listOfLineEndings)) {
      return false;
    }
    if (programName == null) {
      if (other.programName != null) {
        return false;
      }
    } else if (!programName.equals(other.programName)) {
      return false;
    }
    if (programVersion == null) {
      if (other.programVersion != null) {
        return false;
      }
    } else if (!programVersion.equals(other.programVersion)) {
      return false;
    }
    if (referenceRenderInformation == null) {
      if (other.referenceRenderInformation != null) {
        return false;
      }
    } else if (!referenceRenderInformation.equals(other.referenceRenderInformation)) {
      return false;
    }
    return true;
  }

  /**
   * @return the value of programName
   */
  public String getProgramName() {

    if (isSetProgramName()) {
      return programName;
    }
    throw new PropertyUndefinedError(RenderConstants.programName, this);
  }

  /**
   * @return whether programmName is set
   */
  public boolean isSetProgramName() {
    return programName != null;
  }

  /**
   * Set the value of programmName
   * @param programName
   */
  public void setProgramName(String programName) {
    String oldProgramName = this.programName;
    this.programName = programName;
    firePropertyChange(RenderConstants.programName, oldProgramName, this.programName);
  }

  /**
   * Unsets the variable programmName
   * @return {@code true}, if programmName was set before,
   *         otherwise {@code false}
   */
  public boolean unsetProgramName() {
    if (isSetProgramName()) {
      String oldProgramName = programName;
      programName = null;
      firePropertyChange(RenderConstants.programName, oldProgramName, programName);
      return true;
    }
    return false;
  }

  /**
   * @return the value of programVersion
   */
  public String getProgramVersion() {
    if (isSetProgramVersion()) {
      return programVersion;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.programVersion, this);
  }

  /**
   * @return whether programVersion is set
   */
  public boolean isSetProgramVersion() {
    return programVersion != null;
  }

  /**
   * Set the value of programVersion
   * @param programVersion
   */
  public void setProgramVersion(String programVersion) {
    String oldProgramVersion = this.programVersion;
    this.programVersion = programVersion;
    firePropertyChange(RenderConstants.programVersion, oldProgramVersion, this.programVersion);
  }

  /**
   * Unsets the variable programVersion
   * @return {@code true}, if programVersion was set before,
   *         otherwise {@code false}
   */
  public boolean unsetProgramVersion() {
    if (isSetProgramVersion()) {
      String oldProgramVersion = programVersion;
      programVersion = null;
      firePropertyChange(RenderConstants.programVersion, oldProgramVersion, programVersion);
      return true;
    }
    return false;
  }

  /**
   * @return the value of referenceRenderInformation
   */
  public String getReferenceRenderInformation() {
    if (isSetReferenceRenderInformation()) {
      return referenceRenderInformation;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.referenceRenderInformation, this);
  }

  /**
   * @return whether referenceRenderInformation is set
   */
  public boolean isSetReferenceRenderInformation() {
    return referenceRenderInformation != null;
  }

  /**
   * Set the value of referenceRenderInformation
   * @param referenceRenderInformation
   */
  public void setReferenceRenderInformation(String referenceRenderInformation) {
    String oldReferenceRenderInformation = this.referenceRenderInformation;
    this.referenceRenderInformation = referenceRenderInformation;
    firePropertyChange(RenderConstants.referenceRenderInformation, oldReferenceRenderInformation, this.referenceRenderInformation);
  }

  /**
   * Unsets the variable referenceRenderInformation
   * @return {@code true}, if referenceRenderInformation was set before,
   *         otherwise {@code false}
   */
  public boolean unsetReferenceRenderInformation() {
    if (isSetReferenceRenderInformation()) {
      String oldReferenceRenderInformation = referenceRenderInformation;
      referenceRenderInformation = null;
      firePropertyChange(RenderConstants.referenceRenderInformation, oldReferenceRenderInformation, referenceRenderInformation);
      return true;
    }
    return false;
  }

  /**
   * @return the value of backgroundColor
   */
  public Color getBackgroundColor() {
    if (isSetBackgroundColor()) {
      return backgroundColor;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.backgroundColor, this);
  }

  /**
   * @return whether backgroundColor is set
   */
  public boolean isSetBackgroundColor() {
    return backgroundColor != null;
  }

  /**
   * Set the value of backgroundColor
   * @param backgroundColor
   */
  public void setBackgroundColor(Color backgroundColor) {
    Color oldBackgroundColor = this.backgroundColor;
    this.backgroundColor = backgroundColor;
    firePropertyChange(RenderConstants.backgroundColor, oldBackgroundColor, this.backgroundColor);
  }

  /**
   * Unsets the variable backgroundColor
   * @return {@code true}, if backgroundColor was set before,
   *         otherwise {@code false}
   */
  public boolean unsetBackgroundColor() {
    if (isSetBackgroundColor()) {
      Color oldBackgroundColor = backgroundColor;
      backgroundColor = null;
      firePropertyChange(RenderConstants.backgroundColor, oldBackgroundColor, backgroundColor);
      return true;
    }
    return false;
  }

  /**
   * @return {@code true}, if listOfColorDefinitions contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfColorDefinitions() {
    if ((listOfColorDefinitions == null) || listOfColorDefinitions.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * @return the listOfColorDefinitions
   */
  public ListOf<ColorDefinition> getListOfColorDefinitions() {
    if (!isSetListOfColorDefinitions()) {
      listOfColorDefinitions = new ListOf<ColorDefinition>(getLevel(), getVersion());
      listOfColorDefinitions.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'render'
      listOfColorDefinitions.setPackageName(null);
      listOfColorDefinitions.setPackageName(RenderConstants.shortLabel);
      listOfColorDefinitions.setSBaseListType(ListOf.Type.other);
      listOfColorDefinitions.setOtherListName(RenderConstants.listOfColorDefinitions);
      
      registerChild(listOfColorDefinitions);
    }
    return listOfColorDefinitions;
  }

  /**
   * @param listOfColorDefinitions
   */
  public void setListOfColorDefinitions(ListOf<ColorDefinition> listOfColorDefinitions) {
    unsetListOfColorDefinitions();
    this.listOfColorDefinitions = listOfColorDefinitions;

    if (listOfColorDefinitions != null) {
      listOfColorDefinitions.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'render'
      listOfColorDefinitions.setPackageName(null);
      listOfColorDefinitions.setPackageName(RenderConstants.shortLabel);
      listOfColorDefinitions.setSBaseListType(ListOf.Type.other);
      listOfColorDefinitions.setOtherListName(RenderConstants.listOfColorDefinitions);

      registerChild(this.listOfColorDefinitions);
    }
  }

  /**
   * @return {@code true}, if listOfColorDefinitions contained at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfColorDefinitions() {
    if (isSetListOfColorDefinitions()) {
      ListOf<ColorDefinition> oldColorDefinitions = listOfColorDefinitions;
      listOfColorDefinitions = null;
      oldColorDefinitions.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * @param field
   * @return
   */
  public boolean addColorDefinition(ColorDefinition field) {
    return getListOfColorDefinitions().add(field);
  }

  /**
   * @param field
   * @return
   */
  public boolean removeColorDefinition(ColorDefinition field) {
    if (isSetListOfColorDefinitions()) {
      return getListOfColorDefinitions().remove(field);
    }
    return false;
  }

  /**
   * @param i
   */
  public void removeColorDefinition(int i) {
    if (!isSetListOfColorDefinitions()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfColorDefinitions().remove(i);
  }

  /**
   * 
   * @param id
   */
  public void removeColorDefinition(String id) {
    getListOfColorDefinitions().removeFirst(new NameFilter(id));
  }


  /**
   * @return {@code true}, if listOfGradientDefinitions contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfGradientDefinitions() {
    if ((listOfGradientDefinitions == null) || listOfGradientDefinitions.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * @return the listOfGradientDefinitions
   */
  public ListOf<GradientBase> getListOfGradientDefinitions() {
    if (!isSetListOfGradientDefinitions()) {
      listOfGradientDefinitions = new ListOf<GradientBase>(getLevel(), getVersion());
      listOfGradientDefinitions.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'render'
      listOfGradientDefinitions.setPackageName(null);
      listOfGradientDefinitions.setPackageName(RenderConstants.shortLabel);
      listOfGradientDefinitions.setSBaseListType(ListOf.Type.other);
      listOfGradientDefinitions.setOtherListName(RenderConstants.listOfGradientDefinitions);
      
      registerChild(listOfGradientDefinitions);
    }
    return listOfGradientDefinitions;
  }

  /**
   * @param listOfGradientDefintions
   */
  public void setListOfGradientDefinitions(ListOf<GradientBase> listOfGradientDefintions) {
    unsetListOfGradientDefinitions();
    listOfGradientDefinitions = listOfGradientDefintions;

    if (listOfGradientDefintions != null) {
      listOfGradientDefintions.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'render'
      listOfGradientDefintions.setPackageName(null);
      listOfGradientDefintions.setPackageName(RenderConstants.shortLabel);
      listOfGradientDefintions.setSBaseListType(ListOf.Type.other);
      listOfGradientDefinitions.setOtherListName(RenderConstants.listOfGradientDefinitions);


      registerChild(listOfGradientDefinitions);
    }
  }

  /**
   * @return {@code true}, if listOfGradientDefinitions contained at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfGradientDefinitions() {
    if (isSetListOfGradientDefinitions()) {
      ListOf<GradientBase> oldGradientBases = listOfGradientDefinitions;
      listOfGradientDefinitions = null;
      oldGradientBases.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * @param field
   * @return
   */
  public boolean addGradientBase(GradientBase field) {
    return getListOfGradientDefinitions().add(field);
  }

  /**
   * @param field
   * @return
   */
  public boolean removeGradientBase(GradientBase field) {
    if (isSetListOfGradientDefinitions()) {
      return getListOfGradientDefinitions().remove(field);
    }
    return false;
  }

  /**
   * @param i
   */
  public void removeGradientBase(int i) {
    if (!isSetListOfGradientDefinitions()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfGradientDefinitions().remove(i);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return true;
  }


  @Override
  public boolean getAllowsChildren() {
    return false;
  }


  @Override
  public int getChildCount() {
    int count = 0;
    if (isSetListOfColorDefinitions()) {
      count++;
    }
    if (isSetListOfGradientDefinitions()) {
      count++;
    }
    if (isSetListOfLineEndings()) {
      count++;
    }
    return count;
  }


  @Override
  public SBase getChildAt(int childIndex) {
    if (childIndex < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(resourceBundle.getString("IndexSurpassesBoundsException"), childIndex, 0));
    }
    int pos = 0;
    if (isSetListOfColorDefinitions()) {
      if (pos == childIndex) {
        return getListOfColorDefinitions();
      }
      pos++;
    }
    if (isSetListOfGradientDefinitions()) {
      if (pos == childIndex) {
        return getListOfGradientDefinitions();
      }
      pos++;
    }
    if (isSetListOfLineEndings()) {
      if (pos == childIndex) {
        return getListOfLineEndings();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), childIndex,
      Math.min(pos, 0)));
  }


  /**
   * @return {@code true}, if listOfLineEndings contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfLineEndings() {
    if ((listOfLineEndings == null) || listOfLineEndings.isEmpty()) {
      return false;
    }
    return true;
  }


  /**
   * @return the listOfLineEndings
   */
  public ListOf<LineEnding> getListOfLineEndings() {
    if (!isSetListOfLineEndings()) {
      listOfLineEndings = new ListOf<LineEnding>(getLevel(), getVersion());
      listOfLineEndings.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'render'
      listOfLineEndings.setPackageName(null);
      listOfLineEndings.setPackageName(RenderConstants.shortLabel);
      listOfLineEndings.setSBaseListType(ListOf.Type.other);
      listOfLineEndings.setOtherListName(RenderConstants.listOfLineEndings);
      
      registerChild(listOfLineEndings);
    }
    return listOfLineEndings;
  }


  /**
   * @param listOfLineEndings
   */
  public void setListOfLineEndings(ListOf<LineEnding> listOfLineEndings) {
    unsetListOfLineEndings();
    this.listOfLineEndings = listOfLineEndings;

    if (listOfLineEndings != null) {
      listOfLineEndings.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'render'
      listOfLineEndings.setPackageName(null);
      listOfLineEndings.setPackageName(RenderConstants.shortLabel);
      listOfLineEndings.setSBaseListType(ListOf.Type.other);
      listOfLineEndings.setOtherListName(RenderConstants.listOfLineEndings);

      registerChild(this.listOfLineEndings);
    }
  }


  /**
   * @return {@code true}, if listOfLineEndings contained at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfLineEndings() {
    if (isSetListOfLineEndings()) {
      ListOf<LineEnding> oldLineEndings = listOfLineEndings;
      listOfLineEndings = null;
      oldLineEndings.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * @param lineEnding
   * @return
   */
  public boolean addLineEnding(LineEnding lineEnding) {
    return getListOfLineEndings().add(lineEnding);
  }


  /**
   * @param lineEnding
   * @return
   */
  public boolean removeLineEnding(LineEnding lineEnding) {
    if (isSetListOfLineEndings()) {
      return getListOfLineEndings().remove(lineEnding);
    }
    return false;
  }


  /**
   * @param i
   */
  public void removeLineEnding(int i) {
    if (!isSetListOfLineEndings()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfLineEndings().remove(i);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    
    if (isSetId()) {
      attributes.remove("id");
      attributes.put(RenderConstants.shortLabel + ":id", getId());
    }
    
    if (isSetProgramName()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.programName,
        getProgramName());
    }
    if (isSetProgramVersion()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.programVersion,
        getProgramVersion());
    }
    if (isSetReferenceRenderInformation()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.referenceRenderInformation,
        getReferenceRenderInformation());
    }
    if (isSetBackgroundColor()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.backgroundColor,
        XMLTools.encodeColorToString(getBackgroundColor()));
    }
    return attributes;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(RenderConstants.programName)) {
        setProgramName(value);
      }
      else if (attributeName.equals(RenderConstants.programVersion)) {
        setProgramVersion(value);
      }
      else if (attributeName.equals(RenderConstants.referenceRenderInformation)) {
        setReferenceRenderInformation(value);
      }
      else if (attributeName.equals(RenderConstants.backgroundColor)) {
        setBackgroundColor(XMLTools.decodeStringToColor(value));
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }
}

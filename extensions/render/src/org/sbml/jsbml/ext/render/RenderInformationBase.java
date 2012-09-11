/*
 * $Id$
 * $URL$
 *
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2012 jointly by the following organizations: 
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
 * @version $Rev$
 * @since 1.0
 * @date 04.05.2012
 */
public class RenderInformationBase extends AbstractNamedSBase implements UniqueNamedSBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9096154126197866584L;

	protected String programName;
	protected String programVersion;
	protected String referenceRenderInformation;
	protected Color backgroundColor; 
	protected ListOf<ColorDefinition> listOfColorDefinitions;
	protected ListOf<GradientBase> listOfGradientDefintions;
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
	 */
	public RenderInformationBase(RenderInformationBase obj) {
		super(obj);
		this.programName = obj.programName;
		this.programVersion = obj.programVersion;
		this.referenceRenderInformation = obj.referenceRenderInformation;
		this.backgroundColor = obj.backgroundColor; 
		this.listOfColorDefinitions = obj.listOfColorDefinitions;
		this.listOfGradientDefintions = obj.listOfGradientDefintions;
		this.listOfLineEndings = obj.listOfLineEndings;
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
		addNamespace(RenderConstants.namespaceURI);
		this.backgroundColor = new Color(0, 0, 0);
		this.programName = null;
		this.programVersion = null;
		this.referenceRenderInformation = null;
		this.listOfColorDefinitions = null;
		this.listOfGradientDefintions = null;
		this.listOfLineEndings = null;
	}

	/**
	 * @return the value of programName
	 */
	public String getProgramName() {

		if (isSetProgramName()) {
			return this.programName;
		}
		throw new PropertyUndefinedError(RenderConstants.programName, this);
	}

	/**
	 * @return whether programmName is set 
	 */
	public boolean isSetProgramName() {
		return this.programName != null;
	}

	/**
	 * Set the value of programmName
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
			String oldProgramName = this.programName;
			this.programName = null;
			firePropertyChange(RenderConstants.programName, oldProgramName, this.programName);
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
		return this.programVersion != null;
	}

	/**
	 * Set the value of programVersion
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
			String oldProgramVersion = this.programVersion;
			this.programVersion = null;
			firePropertyChange(RenderConstants.programVersion, oldProgramVersion, this.programVersion);
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
		return this.referenceRenderInformation != null;
	}

	/**
	 * Set the value of referenceRenderInformation
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
			String oldReferenceRenderInformation = this.referenceRenderInformation;
			this.referenceRenderInformation = null;
			firePropertyChange(RenderConstants.referenceRenderInformation, oldReferenceRenderInformation, this.referenceRenderInformation);
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
		return this.backgroundColor != null;
	}

	/**
	 * Set the value of backgroundColor
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
			Color oldBackgroundColor = this.backgroundColor;
			this.backgroundColor = null;
			firePropertyChange(RenderConstants.backgroundColor, oldBackgroundColor, this.backgroundColor);
			return true;
		}
		return false;
	}

	/**
	 * @return {@code true}, if listOfColorDefinitions contains at least one element, 
	 *         otherwise {@code false}
	 */
	public boolean isSetListOfColorDefinitions() {
		if ((this.listOfColorDefinitions == null) || listOfColorDefinitions.isEmpty()) {
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
			listOfColorDefinitions.addNamespace(RenderConstants.namespaceURI);
			listOfColorDefinitions.setSBaseListType(ListOf.Type.other);
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
		registerChild(this.listOfColorDefinitions);
	}

	/**
	 * @return {@code true}, if listOfColorDefinitions contained at least one element, 
	 *         otherwise {@code false}
	 */
	public boolean unsetListOfColorDefinitions() {
		if (isSetListOfColorDefinitions()) {
			ListOf<ColorDefinition> oldColorDefinitions = this.listOfColorDefinitions;
			this.listOfColorDefinitions = null;
			oldColorDefinitions.fireNodeRemovedEvent();
			return true;
		}
		return false;
	}

	/**
	 * @param field
	 */
	public boolean addColorDefinition(ColorDefinition field) {
		return getListOfColorDefinitions().add(field);
	}

	/**
	 * @param field
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
	 * @return {@code true}, if listOfGradientDefintions contains at least one element, 
	 *         otherwise {@code false}
	 */
	public boolean isSetListOfGradientDefintions() {
		if ((listOfGradientDefintions == null) || listOfGradientDefintions.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * @return the listOfGradientDefintions
	 */
	public ListOf<GradientBase> getListOfGradientDefintions() {
		if (!isSetListOfGradientDefintions()) {
			listOfGradientDefintions = new ListOf<GradientBase>(getLevel(), getVersion());
			listOfGradientDefintions.addNamespace(RenderConstants.namespaceURI);
			listOfGradientDefintions.setSBaseListType(ListOf.Type.other);
			registerChild(listOfGradientDefintions);
		}
		return listOfGradientDefintions;
	}

	/**
	 * @param listOfGradientDefintions
	 */
	public void setListOfGradientDefintions(ListOf<GradientBase> listOfGradientDefintions) {
		unsetListOfGradientDefintions();
		this.listOfGradientDefintions = listOfGradientDefintions;
		registerChild(this.listOfGradientDefintions);
	}

	/**
	 * @return {@code true}, if listOfGradientDefintions contained at least one element, 
	 *         otherwise {@code false}
	 */
	public boolean unsetListOfGradientDefintions() {
		if (isSetListOfGradientDefintions()) {
			ListOf<GradientBase> oldGradientBases = this.listOfGradientDefintions;
			this.listOfGradientDefintions = null;
			oldGradientBases.fireNodeRemovedEvent();
			return true;
		}
		return false;
	}

	/**
	 * @param field
	 */
	public boolean addGradientBase(GradientBase field) {
		return getListOfGradientDefintions().add(field);
	}

	/**
	 * @param field
	 */
	public boolean removeGradientBase(GradientBase field) {
		if (isSetListOfGradientDefintions()) {
			return getListOfGradientDefintions().remove(field);
		}
		return false;
	}

	/**
	 * @param i
	 */
	public void removeGradientBase(int i) {
		if (!isSetListOfGradientDefintions()) {
			throw new IndexOutOfBoundsException(Integer.toString(i));
		}
		getListOfGradientDefintions().remove(i);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
	 */
  //@Override
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
     if (isSetListOfGradientDefintions()) {
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
      throw new IndexOutOfBoundsException(childIndex + " < 0");
    }
    int pos = 0;
    if (isSetListOfColorDefinitions()) {
      if (pos == childIndex) {
        return getListOfColorDefinitions();
      }
      pos++;
    }
    if (isSetListOfGradientDefintions()) {
      if (pos == childIndex) {
        return getListOfGradientDefintions();
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
      "Index {0,number,integer} >= {1,number,integer}", childIndex,
      +Math.min(pos, 0)));
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
      listOfLineEndings.addNamespace(RenderConstants.namespaceURI);
      listOfLineEndings.setSBaseListType(ListOf.Type.other);
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
    registerChild(this.listOfLineEndings);
  }


  /**
   * @return {@code true}, if listOfLineEndings contained at least one element, 
   *         otherwise {@code false}
   */
  public boolean unsetListOfLineEndings() {
    if (isSetListOfLineEndings()) {
      ListOf<LineEnding> oldLineEndings = this.listOfLineEndings;
      this.listOfLineEndings = null;
      oldLineEndings.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * @param lineEnding
   */
  public boolean addLineEnding(LineEnding lineEnding) {
    return getListOfLineEndings().add(lineEnding);
  }


  /**
   * @param lineEnding
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
    if (isSetProgramName()) {
      attributes.remove(RenderConstants.programName);
      attributes.put(RenderConstants.shortLabel + ":" + RenderConstants.programName,
        getProgramName());
    }
    if (isSetProgramVersion()) {
      attributes.remove(RenderConstants.programVersion);
      attributes.put(RenderConstants.shortLabel + ":" + RenderConstants.programVersion,
        getProgramVersion());
    }
    if (isSetReferenceRenderInformation()) {
      attributes.remove(RenderConstants.referenceRenderInformation);
      attributes.put(RenderConstants.shortLabel + ":" + RenderConstants.referenceRenderInformation,
        getReferenceRenderInformation());
    }
    if (isSetBackgroundColor()) {
      attributes.remove(RenderConstants.backgroundColor);
      attributes.put(RenderConstants.shortLabel + ":" + RenderConstants.backgroundColor,
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

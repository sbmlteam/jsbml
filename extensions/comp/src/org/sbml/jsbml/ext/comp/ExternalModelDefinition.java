/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * Copyright (C) 2009-2018 jointly by the following organizations:
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
package org.sbml.jsbml.ext.comp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.Creator;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.SubModel;

/**
 * The {@link ExternalModelDefinition} objects are model definitions - in and of
 * themselves, they are definitions of the models but not uses of those models.
 * The class provides a way to declare and identify them so that {@link Model}
 * objects in the present {@link SBMLDocument} can use them in {@link SubModel}
 * objects.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class ExternalModelDefinition extends AbstractNamedSBase
  implements UniqueNamedSBase {

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger           =
    Logger.getLogger(Creator.class);
  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 2005205309846284624L;
  /**
   * The source of this model (anyURI): Specifies a file as relative or absolute
   * path, URL or URN
   */
  private String source;
  /**
   * 
   */
  private String modelRef;
  /**
   * 
   */
  private String md5;

  /**
   * Creates an ExternalModelDefinition instance
   */
  public ExternalModelDefinition() {
    super();
    initDefaults();
  }


  /**
   * Creates a ExternalModelDefinition instance with an id.
   * 
   * @param id
   *        the id
   */
  public ExternalModelDefinition(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a ExternalModelDefinition instance with an id, level, and version.
   * 
   * @param id
   *        the id
   * @param level
   *        the SBML level
   * @param version
   *        the SBML version
   */
  public ExternalModelDefinition(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a ExternalModelDefinition instance with an id, name, level, and
   * version.
   * 
   * @param id
   *        the id
   * @param name
   *        the name
   * @param level
   *        the SBML level
   * @param version
   *        the SBML version
   */
  public ExternalModelDefinition(String id, String name, int level,
    int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(
      Integer.valueOf(CompConstants.MIN_SBML_LEVEL),
      Integer.valueOf(CompConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }


  /**
   * Clone constructor
   * 
   * @param obj
   *        the instance to clone
   */
  public ExternalModelDefinition(ExternalModelDefinition obj) {
    super(obj);
    if (obj.isSetSource()) {
      setSource(obj.getSource());
    }
    if (obj.isSetModelRef()) {
      setModelRef(obj.getModelRef());
    }
    if (obj.isSetMd5()) {
      setMd5(obj.getMd5());
    }
  }


  /**
   * clones this class
   */
  @Override
  public ExternalModelDefinition clone() {
    return new ExternalModelDefinition(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = CompConstants.shortLabel;
  }


  /*
   * (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3209;
    int result = super.hashCode();
    result = prime * result + ((md5 == null) ? 0 : md5.hashCode());
    result = prime * result + ((modelRef == null) ? 0 : modelRef.hashCode());
    result = prime * result + ((source == null) ? 0 : source.hashCode());
    return result;
  }


  /*
   * (non-Javadoc)
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
    ExternalModelDefinition other = (ExternalModelDefinition) obj;
    if (md5 == null) {
      if (other.md5 != null) {
        return false;
      }
    } else if (!md5.equals(other.md5)) {
      return false;
    }
    if (modelRef == null) {
      if (other.modelRef != null) {
        return false;
      }
    } else if (!modelRef.equals(other.modelRef)) {
      return false;
    }
    if (source == null) {
      if (other.source != null) {
        return false;
      }
    } else if (!source.equals(other.source)) {
      return false;
    }
    return true;
  }


  /**
   * Returns the value of source or an empty {@link String} if it is not set.
   * 
   * @return the value of source or an empty {@link String} if it is not set.
   */
  public String getSource() {
    if (isSetSource()) {
      return source;
    }
    return "";
  }


  /**
   * Returns whether source is set
   * 
   * @return whether source is set
   */
  public boolean isSetSource() {
    return source != null;
  }


  /**
   * Sets the value of the required source attribute.
   * <p>
   * The source attribute is used to locate the SBML document containing an
   * {@link ExternalModelDefinition}. The value of the attribute must be a URI,
   * which includes URLs, URNs, or relative/absolute file locations. The source
   * attribute must refer specifically to an SBML Level 3 Version 1 document.
   * The
   * entire file at the given location is referenced. The source attribute must
   * have a value for every {@link ExternalModelDefinition}.
   * </p>
   * 
   * @param source
   *        the value of the source
   */
  public void setSource(String source) {
    String oldSource = this.source;
    this.source = source;
    firePropertyChange(CompConstants.source, oldSource, this.source);
  }


  /**
   * Unsets the variable source
   * 
   * @return {@code true }, if source was set before, otherwise {@code false}
   */
  public boolean unsetSource() {
    if (isSetSource()) {
      String oldSource = source;
      source = null;
      firePropertyChange(CompConstants.source, oldSource, source);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of modelRef or an empty {@link String} if it is not set.
   * 
   * @return the value of modelRef or an empty {@link String} if it is not set.
   */
  public String getModelRef() {
    if (isSetModelRef()) {
      return modelRef;
    }
    return "";
  }


  /**
   * Returns whether modelRef is set
   * 
   * @return whether modelRef is set
   */
  public boolean isSetModelRef() {
    return modelRef != null;
  }


  /**
   * Sets the value of the optional modelRef attribute.
   * <p>
   * modelRef is used to identify a {@link Model} or
   * {@link ExternalModelDefinition} object within the SBML document located at
   * source. The object referenced may be the main model in the document, or it
   * may be a model definition contained in the SBML document's
   * listOfModelDefinitions or listOfExternalModelDefinitions. Loops are not
   * allowed: it must be possible to follow a chain of
   * {@link ExternalModelDefinition} objects to its end in a {@link Model}
   * object.
   * </p>
   * <p>
   * In core SBML, the id on {@link Model} is an optional attribute, and
   * therefore, it is possible that the {@link Model} object in a given SBML
   * document does not have an identifier. In that case, there is no value to
   * give
   * to the modelRef attribute in {@link ExternalModelDefinition}. If modelRef
   * does not have a value, then the main model (i.e., the model element within
   * the sbml element) in the referenced file is interpreted as being the model
   * referenced by this {@link ExternalModelDefinition} instance.
   * </p>
   * 
   * @param modelRef
   *        the value of modelRef
   */
  public void setModelRef(String modelRef) {
    String oldModelRef = this.modelRef;
    if (modelRef != null && modelRef.trim().length() == 0) {
      modelRef = null;
    }
    this.modelRef = modelRef;
    firePropertyChange(CompConstants.modelRef, oldModelRef, this.modelRef);
  }


  /**
   * Unsets the variable modelRef
   * 
   * @return {@code true}, if modelRef was set before, otherwise {@code false}
   */
  public boolean unsetModelRef() {
    if (isSetModelRef()) {
      String oldModelRef = modelRef;
      modelRef = null;
      firePropertyChange(CompConstants.modelRef, oldModelRef, modelRef);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of md5 or an empty {@link String} if it is not set.
   *
   * @return the value of md5 or an empty {@link String} if it is not set.
   */
  public String getMd5() {
    if (isSetMd5()) {
      return md5;
    }
    return "";
  }


  /**
   * Returns whether md5 is set
   *
   * @return whether md5 is set
   */
  public boolean isSetMd5() {
    return md5 != null;
  }


  /**
   * Sets the value of the optional md5 attribute.
   * <p>
   * The md5 attribute takes a string value. If set, it must be an MD5 checksum
   * value computed over the document referenced by source. This checksum can
   * serve as a data integrity check over the contents of the source.
   * Applications
   * may use this to verify that the contents have not changed since the time
   * that
   * the {@link ExternalModelDefinition} reference was constructed.
   * </p>
   * 
   * @param md5
   *        the value of md5
   */
  public void setMd5(String md5) {
    String oldMd5 = this.md5;
    if (md5 != null && md5.trim().length() == 0) {
      md5 = null;
    }
    this.md5 = md5;
    firePropertyChange(CompConstants.md5, oldMd5, this.md5);
  }


  /**
   * Unsets the variable md5
   *
   * @return {@code true}, if md5 was set before, otherwise {@code false}
   */
  public boolean unsetMd5() {
    if (isSetMd5()) {
      String oldMd5 = md5;
      md5 = null;
      firePropertyChange(CompConstants.md5, oldMd5, md5);
      return true;
    }
    return false;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetId()) {
      attributes.remove("id");
      attributes.put(CompConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(CompConstants.shortLabel + ":name", getName());
    }
    if (isSetSource()) {
      attributes.put(CompConstants.shortLabel + ":" + CompConstants.source,
        getSource());
    }
    if (isSetModelRef()) {
      attributes.put(CompConstants.shortLabel + ":" + CompConstants.modelRef,
        getModelRef());
    }
    if (isSetMd5()) {
      attributes.put(CompConstants.shortLabel + ":" + CompConstants.md5,
        getMd5());
    }
    return attributes;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String,
   * java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(CompConstants.source)) {
        setSource(value);
      } else if (attributeName.equals(CompConstants.modelRef)) {
        setModelRef(value);
      } else if (attributeName.equals(CompConstants.md5)) {
        setMd5(value);
      } else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return true;
  }

  
  /**
   * Resolves the external {@link Model} referenced by this and
   * returns that model, for a given path to the directory containing the
   * SBML-file wherein this
   * {@link ExternalModelDefinition} was made/the root directory for references
   * by relative paths.
   * <br>
   * The referenced {@link Model} is not modified and may thus contain
   * {@link Submodel}s referencing further (external) {@link ModelDefinition}s.
   * 
   * @param absoluteContainingURI
   *        absolute URI to the directory containing the
   *        file that defines this (needed to resolve local
   *        references): Undefined behaviour for opaque URIs (URNs or other),
   *        resolve URNs before calling this method
   * @return The referenced {@link Model}
   * @throws IOException
   *         if the source cannot be found/resolved
   * @throws XMLStreamException
   *         if the file at source is not a valid SBMLDocument.
   * @throws URISyntaxException
   */
  public Model getReferencedModel(URI absoluteContainingURI)
    throws XMLStreamException, IOException, URISyntaxException {
    String sourceURIString;
    URI sourceURI;
    SBMLDocument externalFile;
    sourceURI = getAbsoluteSourceURI(absoluteContainingURI);
    URL sourceUrl = new URL(sourceURI.toString());
    // Work under the assumption that sourceURI is a URL (file or https, ...),
    // not some kind of opaque URI (like a URN)
    if (sourceUrl.getProtocol().equals("file")) {
      externalFile = org.sbml.jsbml.SBMLReader.read(new File(sourceURI));
    } else {
      logger.info("externalModelDefinition " + getId()
        + " points to an online-resource. Trying to open connection to: "
        + source);
      InputStream stream = sourceUrl.openStream();
      externalFile = org.sbml.jsbml.SBMLReader.read(stream);
      externalFile.getSBMLDocument().setLocationURI(sourceUrl.toURI().toString());
      logger.info("Successfully read online source of externalModelDefinition "
        + getId());
    }
    sourceURIString = sourceURI.toString();

    // The referenced model can be the main model of the referenced File
    // (comp-documentation page 14)
    if (externalFile.getModel().getId().equals(modelRef)) {
      return externalFile.getModel();
    } else if (externalFile.isPackageEnabled(CompConstants.shortLabel)) {
      CompSBMLDocumentPlugin externalFileCompPlugin =
        (CompSBMLDocumentPlugin) externalFile.getExtension(
          CompConstants.shortLabel);
      ModelDefinition localModelDefinition =
        externalFileCompPlugin.getModelDefinition(modelRef);
      ExternalModelDefinition nextLayer =
        externalFileCompPlugin.getExternalModelDefinition(modelRef);
      
      if (localModelDefinition != null) {
        return localModelDefinition;
        
      } else if (nextLayer != null) {
        // Allowed by the specification: This ExternalModelDefinition may
        // reference an ExternalModelDefinition in the source; which may again
        // reference an External definition. As by the specification, no loops
        // are allowed (so they are not checked for here) 
        // The source may be at an entirely different location OR at a location
        // relative to the current one
        if (!sourceURIString.startsWith(absoluteContainingURI.toString())
          || sourceURIString.substring(
            absoluteContainingURI.toString().length()).indexOf("/") != -1) {
          return nextLayer.getReferencedModel(new URI(
            sourceURIString.substring(0, sourceURIString.lastIndexOf("/"))));
          
        // Or just the current one
        } else {
          return nextLayer.getReferencedModel(absoluteContainingURI);
        }
      }
    }
    return null;
  }
  
  
  /**
   * Resolves the external {@link Model} referenced by this and returns that
   * model, under the assumption that the containing {@link SBMLDocument} a)
   * exists and b) has a valid locationURI set (cf
   * {@link SBMLDocument#isSetLocationURI()}). This location of the containing
   * file is needed to resolve references to external models by relative paths.
   * URNs/opaque URIs cannot be resolved by this method, resolve them before
   * calling it.
   * <br>
   * The referenced {@link Model} is not modified and may thus contain
   * {@link Submodel}s referencing further (external) {@link ModelDefinition}s.
   * 
   * @return the {@link Model} referenced by this
   * @throws XMLStreamException
   *         if the file referenced by this is not a valid xml/sbml
   * @throws IOException
   *         if the file referenced by this cannot be found
   * @throws URISyntaxException
   *         if parent's locationURI is not a valid URI
   * @throws NullPointerException
   *         if the parent {@link SBMLDocument}'s locationURI is not set.
   */
  public Model getReferencedModel() throws XMLStreamException, IOException, URISyntaxException {
    if(getSBMLDocument().isSetLocationURI()) {
      // Cut off the file-name of the containing document here:
      String absolutePath = getSBMLDocument().getLocationURI().substring(0, getSBMLDocument().getLocationURI().lastIndexOf("/"));
      return getReferencedModel(new URI(absolutePath));
    } else {
      throw new NullPointerException(
        "The containing model's location is not set. Either set it, or use the getReferencedModel(URI)-variant");
    }
  }
  
  
  /**
   * Finds the absolute URI of the source-file specified: If the source is
   * specified through a relative path, will try to use the containing
   * SBMLDocument's {@link SBMLDocument#getLocationURI} to turn it into an
   * absolute URI in the current context.
   * <br>
   * The actual source is not changed in the process
   * 
   * @return The absolute URI to the source
   *         ({@link ExternalModelDefinition#getSource}) of this
   *         ExternalModelDefinition
   * @throws URISyntaxException
   * @throws MalformedURLException 
   */
  public URI getAbsoluteSourceURI() throws URISyntaxException, MalformedURLException {
    if(getSBMLDocument().isSetLocationURI()) {
      // Cut off the file-name of the containing document here:
      String absolutePath = getSBMLDocument().getLocationURI().substring(0, getSBMLDocument().getLocationURI().lastIndexOf("/"));
      return getAbsoluteSourceURI(new URI(absolutePath));
    } else {
      throw new NullPointerException(
        "The containing model's location is not set. Either set it, or use the getReferencedModel(URI)-variant");
    }
  }
  
  
  /**
   * Finds the absolute URI of the source-file specified: If the source is
   * specified through a relative path, will try to use the given
   * absoluteContainingURI to turn it into an absolute URI in the current
   * context.
   * <br>
   * The actual source is not changed in the process
   * 
   * @param absoluteContainingURI
   *        absolute URI to the directory containing the
   *        file that defines this (needed to resolve local
   *        references)
   * @return The absolute URI to the source
   *         ({@link ExternalModelDefinition#getSource}) of this
   *         ExternalModelDefinition
   * @throws MalformedURLException
   * @throws URISyntaxException
   */
  public URI getAbsoluteSourceURI(URI absoluteContainingURI) throws MalformedURLException, URISyntaxException {
    URI sourceURI;
    String completionOfAbsoluteContainingURI =
      absoluteContainingURI.toString().endsWith("/") ? "" : "/";
    URL sourceUrl;
    try {
      // the source itself is a valid URL: do not need absoluteContainingURI
      sourceUrl = new URL(new URI(source).toString());
    } catch (MalformedURLException e) {
      // the source itself is not a valid URL: it is relative
      StringBuilder workingURI = new StringBuilder();
      if (!new File(source).isAbsolute()) {
        workingURI.append(absoluteContainingURI);
        workingURI.append(completionOfAbsoluteContainingURI);
      } else {
        workingURI.append("file:/");
      }
      workingURI.append(source);
      sourceUrl = new URL(workingURI.toString());
    }
    sourceURI = sourceUrl.toURI();
    return sourceURI;
  }
}

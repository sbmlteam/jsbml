/*
 * $Id$
 * $URL$
 *
 *
 *==================================================================================
 * Copyright (c) 2009 the copyright is held jointly by the individual
 * authors. See the file AUTHORS for the list of authors.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */

package org.sbml.jsbml;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * The interface to implement for each SBML component.
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * @author marine
 */
public interface SBase {

	/**
	 * adds a listener to the SBase object. from now on changes will be saved
	 * 
	 * @param l
	 */
	public void addChangeListener(SBaseChangedListener l);

	/**
	 * Appends 'notes' to the notes String of this object.
	 * @param notes
	 */
	public void appendNotes(String notes);

	/**
	 * Creates a copy of this object, i.e., e new SBase with the same properties
	 * like this one and returns a pointer to it.
	 * 
	 * @return the clone of this object.
	 */
	public SBase clone();

	/**
	 * 
	 * @param sbase
	 * @return true if and only if the given SBase has exactly the same
	 * properties like this SBase instance.
	 */
	public boolean equals(Object sbase);

	/**
	 * @return the content of the 'annotation' subelement of this object as an Annotation instance.
	 */
	public Annotation getAnnotation();

	/**
	 * 
	 * @return the XML element name of this object.
	 */
	public String getElementName();

	/**
	 * Returns the SBML Level of the overall SBML document. Return -1 if it is not set.
	 * 
	 * @return the SBML level of this SBML object.
	 * @see getVersion()
	 */
	public int getLevel();

	/**
	 * 
	 * @return the metaid of this element.
	 */
	public String getMetaId();

	/**
	 * Returns the Model object in which the current object is located.
	 * 
	 * @return
	 */
	public Model getModel();

	/**
	 * 
	 * @return the notes subelement of this object as a String.
	 */
	public String getNotesString();

	/**
	 * This method is convenient when holding an object nested inside other
	 * objects in an SBML model. It allows direct access to the &lt;model&gt;
	 * 
	 * element containing it.
	 * 
	 * @return Returns the parent SBML object.
	 */
	public SBase getParentSBMLObject();

	/**
	 * Returns the parent SBMLDocument object.
	 * 
	 * LibSBML uses the class SBMLDocument as a top-level container for storing
	 * SBML content and data associated with it (such as warnings and error
	 * messages). An SBML model in libSBML is contained inside an SBMLDocument
	 * object. SBMLDocument corresponds roughly to the class Sbml defined in the
	 * SBML Level 2 specification, but it does not have a direct correspondence
	 * in SBML Level 1. (But, it is created by libSBML no matter whether the
	 * model is Level 1 or Level 2.)
	 * 
	 * This method allows the SBMLDocument for the current object to be
	 * retrieved.
	 * 
	 * @return the parent SBMLDocument object of this SBML object.
	 */
	public SBMLDocument getSBMLDocument();

	/**
	 * 
	 * @return the SBOTerm attribute of this element.
	 */
	public int getSBOTerm();

	/**
	 * 
	 * @return the SBO term ID of this element.
	 */
	public String getSBOTermID();

	/**
	 * Returns the Version within the SBML Level of the overall SBML document. Return -1 if it is not set.
	 * 
	 * @return the SBML version of this SBML object.
	 * @see getLevel()
	 */
	public int getVersion();

	/**
	 * Predicate returning true or false depending on whether this object's
	 * level/version and namespace values correspond to a valid SBML
	 * specification.
	 * 
	 * @return
	 */
	public boolean hasValidLevelVersionNamespaceCombination();

	/**
	 * 
	 * @return true if the Annotation 'about' String of this object matches the metaid of this object. 
	 */
	public boolean hasValidAnnotation();
	
	/**
	 * Predicate returning true or false depending on whether this object's
	 * 'annotation' subelement exists and has content.
	 * @return true if the Annotation instance of this object is not null and contains at least one CVTerm
	 * or one String containing other annotations than RDF or a modelHistory instance.
	 */
	public boolean isSetAnnotation();

	/**
	 * Predicate returning true or false depending on whether this object's
	 * 'metaid' attribute has been set.
	 * 
	 * @return true if the metaid is not null.
	 */
	public boolean isSetMetaId();

	/**
	 * Predicate returning true or false depending on whether this object's
	 * 'notes' subelement exists and has content.
	 * 
	 * @return true if the notes String is not null.
	 */
	public boolean isSetNotes();

	/**
	 * 
	 * @return true if the SBOTerm is not -1.
	 */
	public boolean isSetSBOTerm();
	
	/**
	 * 
	 * @return true if the notesBuffer is not null.
	 */
	public boolean isSetNotesBuffer();

	/**
	 * all listeners are informed about the adding of this object to a list
	 * 
	 */
	public void sbaseAdded();

	/**
	 * 
	 * all listeners are informed about the deletion of this object from a list
	 */
	public void sbaseRemoved();

	/**
	 * Sets the value of the 'annotation' subelement of this SBML object to a
	 * copy of annotation given as an Annotation instance.
	 * 
	 * @param annotation
	 */
	public void setAnnotation(Annotation annotation);

	/**
	 * Sets the metaid value with 'metaid'.
	 * @param metaid
	 */
	public void setMetaId(String metaid);

	/**
	 * Sets the notes with 'notes'.
	 * @param notes
	 */
	public void setNotes(String notes);

	/**
	 * Sets the value of the 'sboTerm' attribute.
	 * 
	 * @param term
	 */
	public void setSBOTerm(int term);

	/**
	 * Sets the value of the 'sboTerm' attribute.
	 * 
	 * @param sboid
	 */
	public void setSBOTerm(String sboid);

	/**
	 * all listeners are informed about the change in this object
	 */
	public void stateChanged();

	/**
	 * Unsets the value of the 'annotation' subelement of this SBML object.
	 */
	public void unsetAnnotation();


	/**
	 * Unsets the value of the 'metaid' attribute of this SBML object.
	 */
	public void unsetMetaId();

	/**
	 * Unsets the value of the 'notes' subelement of this SBML object.
	 */
	public void unsetNotes();

	/**
	 * Unsets the value of the 'sboTerm' attribute of this SBML object.
	 */
	public void unsetSBOTerm();
	
	/**
	 * Unsets the value of the notesBuffer of this SBML object.
	 */
	public void unsetNotesBuffer();
	
	/**
	 * @return the StringBuffer notesBuffer containing the notes subelement of this object as a StringBuffer.
	 */
	public StringBuffer getNotesBuffer();
	
	/**
	 * sets the notesBuffer instance to 'notes'.
	 */
	public void setNotesBuffer(StringBuffer notes);
	
	/**
	 * If the attribute is an id or name attribute, it will set the id or name of this object with
	 * the value of the XML attribute ('value').
	 * @param attributeName : localName of the XML attribute
	 * @param prefix : prefix of the XML attribute
	 * @param value : value of the XML attribute
	 * @return true if the attribute has been read.
	 */
	public boolean readAttribute(String attributeName, String prefix, String value);
	
	/**
	 * 
	 * @return the list of CVTerms of this object.
	 */
	public List<CVTerm> getCVTerms();
	
	/**
	 * 
	 * @return the number of CVTerms of this object.
	 */
	public int getNumCVTerms();
	
	/**
	 * 
	 * @param index
	 * @return the CVTerm instance at the position 'index' in the list of CVTerms of this object.
	 */
	public CVTerm getCVTerm(int index);
	
	/**
	 * 
	 * @param term
	 * @return true if a CVTerm instance has been added to the list of CVTerm of this object.
	 */
	public boolean addCVTerm(CVTerm term);
	
	/**
	 * Unsets the list of CVTerm of this object.
	 */
	public void unsetCVTerms();
	
	/**
	 * Unsets the model History of this object.
	 */
	public void unsetModelHistory();
	
	/**
	 * 
	 * @return true if the Annotation instance of this object 
	 */
	public boolean isSetModelHistory();
	
	/**
	 * 
	 * @return the ModelHistory instance of this object.
	 */
	public ModelHistory getModelHistory();
	
	/**
	 * 
	 * @return the ModelHistory instance of this object.
	 */
	public void setModelHistory(ModelHistory modelHistory);
	
	/**
	 * 
	 * @param namespace
	 * @return the SBase extension object which matches this namespace.
	 */
	public SBase getExtension(String namespace);
	
	/**
	 * add a SBase extension object 'sbase' associated with a namespace 'namespace'.
	 * @param namespace
	 * @param sbase
	 */
	public void addExtension(String namespace, SBase sbase);
	
	/**
	 * 
	 * @return all the namespaces of all the packages which are currently extending this object.
	 */
	public Set<String> getNamespaces();
	
	/**
	 * @return a map containing the XML attributes of this object.
	 */
	public HashMap<String, String> writeXMLAttributes();
	
	/**
	 * Sets the level of this object with 'level'. If the SBMLparent of this object is set 
	 * and 'level' is different with the SBMLparent level, an excpetion is thrown.
	 * @param level
	 */
	public void setLevel(int level);
	
	/**
	 * Sets the version of this object with 'version'.  If the SBMLparent of this object is set 
	 * and 'version' is different with the SBMLparent version, an excpetion is thrown.
	 * @param level
	 */
	public void setVersion(int version);
	
	/**
	 * 
	 * @return true if the level is not null.
	 */
	public boolean isSetLevel();
	
	/**
	 * 
	 * @return true if the version is not null.
	 */
	public boolean isSetVersion();
	
	/**
	 * Sets this object as SBMLParent of 'sbase'. Check if the level and version of sbase are set, otherwise
	 * sets the level and version of 'sbase' with those of this object.
	 * 
	 * If the level and version of sbase are set but not valid, an exception is thrown.
	 */
	public void setThisAsParentSBMLObject(AbstractSBase sbase);
	
	/**
	 * 
	 * @return true if this object is extended by other packages.
	 */
	public boolean isExtendedByOtherPackages();
	
	/**
	 * 
	 * @return the map containing all the extension objects of this object.
	 */
	public HashMap<String, SBase> getExtensionPackages();
}

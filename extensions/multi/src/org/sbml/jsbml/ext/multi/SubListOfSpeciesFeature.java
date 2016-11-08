package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.ext.multi.util.ListOfSpeciesFeatureContent;

/**
 * Allows to regroup a set of {@link SpeciesFeature} that have a specific {@link Relation} between them.
 * 
 * <p>This class derives from {@link ListOf}<SpeciesFeature>.
 * 
 * @author rodrigue
 */
public class SubListOfSpeciesFeature extends ListOf<SpeciesFeature> implements UniqueNamedSBase, ListOfSpeciesFeatureContent {

  /**
   * 
   */
  private Relation relation;

  /**
   * 
   */
  private String component;
  
  
  /**
   * Creates an SubListOfSpeciesFeature instance 
   */
  public SubListOfSpeciesFeature() {
    super();
    initDefaults();
  }

  /**
   * Creates a SubListOfSpeciesFeature instance with an id.
   * 
   * @param id the identifier for the new element.
   */
  public SubListOfSpeciesFeature(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a SubListOfSpeciesFeature instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public SubListOfSpeciesFeature(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * Creates a SubListOfSpeciesFeature instance with an id, level, and version.
   * 
   * @param id the identifier for this element.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public SubListOfSpeciesFeature(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates a SubListOfSpeciesFeature instance with an id, name, level, and version.
   * 
   * @param id the identifier for this element.
   * @param name a human-readable name for this element that can be used for display purposes.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public SubListOfSpeciesFeature(String id, String name, int level,
      int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(Integer.valueOf(MultiConstants.MIN_SBML_LEVEL),
        Integer.valueOf(MultiConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }

  /**
   * Clone constructor
   */
  public SubListOfSpeciesFeature(SubListOfSpeciesFeature obj) {
    super(obj);

    // TODO: copy all class attributes, e.g.:
    // bar = obj.bar;
  }

  /**
   * clones this class
   */
  public SubListOfSpeciesFeature clone() {
    return new SubListOfSpeciesFeature(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    packageName = MultiConstants.shortLabel;
    setPackageVersion(-1);
  }

  
  /**
   * Returns the value of {@link #relation}.
   *
   * @return the value of {@link #relation}.
   */
  public Relation getRelation() {
    //TODO: if variable is boolean, create an additional "isVar"
    //TODO: return primitive data type if possible (e.g., int instead of Integer)
    if (isSetRelation()) {
      return relation;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(MultiConstants.relation, this);
  }

  /**
   * Returns whether {@link #relation} is set.
   *
   * @return whether {@link #relation} is set.
   */
  public boolean isSetRelation() {
    return relation != null;
  }

  /**
   * Sets the value of relation
   *
   * @param relation the value of relation to be set.
   */
  public void setRelation(Relation relation) {
    Relation oldRelation = this.relation;
    this.relation = relation;
    firePropertyChange(MultiConstants.relation, oldRelation, this.relation);
  }

  /**
   * Unsets the variable relation.
   *
   * @return {@code true} if relation was set before, otherwise {@code false}.
   */
  public boolean unsetRelation() {
    if (isSetRelation()) {
      Relation oldRelation = this.relation;
      this.relation = null;
      firePropertyChange(MultiConstants.relation, oldRelation, this.relation);
      return true;
    }
    return false;
  }
  
  
  /**
   * Returns the value of {@link #component}.
   *
   * @return the value of {@link #component}.
   */
  public String getComponent() {
    //TODO: if variable is boolean, create an additional "isVar"
    //TODO: return primitive data type if possible (e.g., int instead of Integer)
    if (isSetComponent()) {
      return component;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(MultiConstants.component, this);
  }

  /**
   * Returns whether {@link #component} is set.
   *
   * @return whether {@link #component} is set.
   */
  public boolean isSetComponent() {
    return component != null;
  }

  /**
   * Sets the value of component
   *
   * @param component the value of component to be set.
   */
  public void setComponent(String component) {
    String oldComponent = this.component;
    this.component = component;
    firePropertyChange(MultiConstants.component, oldComponent, this.component);
  }

  /**
   * Unsets the variable component.
   *
   * @return {@code true} if component was set before, otherwise {@code false}.
   */
  public boolean unsetComponent() {
    if (isSetComponent()) {
      String oldComponent = this.component;
      this.component = null;
      firePropertyChange(MultiConstants.component, oldComponent, this.component);
      return true;
    }
    return false;
  }
  
    // TODO - JSBML_XML equals hashcode, ...
}

package org.sbml.jsbml.ext.render;

import org.sbml.jsbml.AbstractSBase;

/**
 * Implements the RelAbsVector-datatype defined in the render-specification:
 * A RelAbsVector encodes a single number as a combination of an absolute and a
 * relative part: "a + r%", where the relative part is with respect to some
 * surrounding space (like a boundingBox).<br>
 * This implementation deviates from the one in libSBML in that the support for
 * ,Coordinate' get/set/isSet is reduced
 * 
 * @author DavidVetter
 */
public class RelAbsVector extends AbstractSBase {

  private static final long serialVersionUID = 7621009594700491178L;
  
  private Double absolute;
  private Double relative;
  
  private static final String ABSOLUTE_PATTERN = "-?((.\\d+)|(\\d+(\\.\\d*)?))";
  private static final String RELATIVE_PATTERN = ABSOLUTE_PATTERN + "\\%";
  
  public RelAbsVector() {
    super();
    initDefaults();
  }
  
  /**
   * Copy-constructor
   * @param original
   */
  public RelAbsVector(RelAbsVector original) {
    super(original);
    absolute = original.getAbsoluteValue();
    relative = original.getRelativeValue();
  }
  
  /**
   * Set only the absolute, but not the relative part, to an initial value
   * @param absolute coordinate-value (in pt)
   */
  public RelAbsVector(double absolute) {
    super();
    initDefaults();
    this.absolute = absolute;
  }
  
  
  /**
   * Set both the relative and absolute parts to initial values
   * 
   * @param absolute
   *        coordinate-value (in pt)
   * @param relative
   *        in % of some reference size (of the enclosing {@link BoundingBox}),
   *        relative=75 means 75% (i.e. 0.75)
   */
  public RelAbsVector(double absolute, double relative) {
    super();
    initDefaults();
    this.absolute = absolute;
    this.relative = relative;
  }
  
  
  /**
   * Set from a XML-String in the format "a" or "r%" or "a+r%" or "a-r%", as
   * described in the render-specification
   * 
   * @param coordinate
   *        String encoding of a RelAbsVector. If invalid, both absolute and
   *        relative part are set to NaN (i.e. unset)
   */
  public RelAbsVector(String coordinate) {
    super();
    initDefaults();
    setCoordinate(coordinate);
  }
  
  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = RenderConstants.shortLabel;
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public RelAbsVector clone() {
    return new RelAbsVector(this);
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if(this == obj) {
      return true;
    }
    if(obj.getClass() != this.getClass()) {
      return false;
    }
    if(!super.equals(obj)) {
      return false;
    }
    
    RelAbsVector other = (RelAbsVector) obj;
    if (absolute == null) {
      if (other.absolute != null) {
        return false;
      }
    } else if (!absolute.equals(other.absolute)) {
      return false;
    }
    
    if (relative == null) {
      if (other.relative != null) {
        return false;
      }
    } else if (!relative.equals(other.relative)) {
      return false;
    }
    return true;
  }
  
  /**
   * @return the absolute part/value
   */
  public double getAbsoluteValue() {
    return absolute;
  }
  
  /**
   * @return the relative part/value (in %, i.e. a return of 100d means 100%)
   */
  public double getRelativeValue() {
    return relative;
  }
  
  /**
   * Sets absolute part to new value and fires appropriate event
   * @param absolute the new absolute value to be taken
   */
  public void setAbsoluteValue(double absolute) {
    Double old = this.absolute;
    this.absolute = absolute;
    firePropertyChange(RenderConstants.absoluteValue, old, absolute);
  }
  
  /**
   * Sets relative part to new value and fires appropriate event
   * @param relative
   *        the new relative value to be taken (in percent, i.e. relative=100d
   *        means 100%)
   */
  public void setRelativeValue(double relative) {
    Double old = this.relative;
    this.relative = relative;
    firePropertyChange(RenderConstants.relativeValue, old, relative);
  }
  
  /**
   * @return whether the absolute value is set to a number
   */
  public boolean isSetAbsoluteValue() {
    return absolute != null && !absolute.isNaN();
  }
  
  /**
   * @return whether the relative value is set to a number
   */
  public boolean isSetRelativeValue() {
    return relative != null && !relative.isNaN();
  }
  
  /**
   * Tries to unset the absolute value
   * @return whether the absolute value could indeed be unset
   */
  public boolean unsetAbsoluteValue() {
    if(isSetAbsoluteValue()) {
      Double old = absolute;
      absolute = null;
      firePropertyChange(RenderConstants.absoluteValue, old, absolute);
      return true;
    }
    return false;
  }
  
  /**
   * Tries to unset the relative value
   * @return whether the relative value could indeed be unset
   */
  public boolean unsetRelativeValue() {
    if(isSetRelativeValue()) {
      Double old = relative;
      relative = null;
      firePropertyChange(RenderConstants.relativeValue, old, relative);
      return true;
    }
    return false;
  }
  
  /**
   * Sets the relative and absolute value based on a String-encoding (like "1",
   * "30%" or "50-20%"; as defined in the render-specification)<br>
   * If the String does not represent a valid value, relative and absolute value
   * are set to NaN
   * 
   * @param coordinates
   *        a RelAbsVector string
   * @return Whether the string was valid
   */
  public boolean setCoordinate(String coordinates) {
    if(coordinates != null) {
      coordinates = coordinates.replaceAll("\\s+", "");
      if(coordinates.length() > 0) {
        
        String[] entries = coordinates.split("(\\+|(?=-))");
        // Allowed variant 1: Only absolute or only relative
        if(entries.length == 1) {
          String entry = entries[0];
          if(entry.matches(RELATIVE_PATTERN)) {
            // Cut off the %
            setRelativeValue(
              Double.parseDouble(entry.substring(0, entry.length() - 1)));
            return true;
            
          } else if(entry.matches(ABSOLUTE_PATTERN)) {
            setAbsoluteValue(Double.parseDouble(entry));
            return true;
          } 
        // Allowed variant 2: Absolute followed by relative
        } else if (entries.length == 2 && entries[0].matches(ABSOLUTE_PATTERN)
          && entries[1].matches(RELATIVE_PATTERN)) {
          
          setAbsoluteValue(Double.parseDouble(entries[0]));
          setRelativeValue(Double.parseDouble(
            entries[1].substring(0, entries[1].length() - 1)));
          return true;
        }
      }
    }
    setAbsoluteValue(Double.NaN);
    setRelativeValue(Double.NaN);
    return false;
  }
  
  
  /**
   * Produces a string of the format "a" or "r%" or "a+r%" or "a-r%" as
   * specified in the render-documentation.
   * 
   * @return the XML-string-encoding of this RelAbsVector
   */
  public String getCoordinate() {
    if(!(isSetAbsoluteValue() || isSetRelativeValue()))
      return "0";
    
    StringBuffer result = new StringBuffer();
    if(isSetAbsoluteValue() && getAbsoluteValue() != 0) {
      result.append(getAbsoluteValue());
    }
    if(isSetRelativeValue() && getRelativeValue() != 0) {
      // Only, if there is a preceding absolute value is the '+' needed
      if(getRelativeValue() > 0 && isSetAbsoluteValue())
        result.append("+");
      result.append(getRelativeValue());
      result.append("%");
    }
    // This happens, if both relative and absolute are 0, or if either is 0 and
    // the other not set 
    if(result.length() == 0)
      result.append("0");
    
    return result.toString();
  }
  
  /**
   * Produces a string for debugging etc. This is not the XML-string-encoding for render,
   * which can be retrieved by {@link RelAbsVector#getCoordinate()}
   */
  public String toString() {
    return String.format("RelAbsVector [absolute=%s, relative=%s]", absolute, relative);
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3061;
    int result = super.hashCode();
    result = prime * result + ((absolute == null) ? 0 : absolute.hashCode());
    result = prime * result + ((relative == null) ? 0 : relative.hashCode());
    return result;
  }
}

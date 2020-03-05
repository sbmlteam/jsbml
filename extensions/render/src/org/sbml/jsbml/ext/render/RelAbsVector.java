package org.sbml.jsbml.ext.render;

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
public class RelAbsVector {

  private Double absolute;
  private Double relative;
  
  private static final String ABSOLUTE_PATTERN = "-?((.\\d+)|(\\d+(\\.\\d*)?))";
  private static final String RELATIVE_PATTERN = ABSOLUTE_PATTERN + "\\%";
  
  public RelAbsVector() {
  }
  
  public RelAbsVector(RelAbsVector original) {
    absolute = original.getAbsoluteValue();
    relative = original.getRelativeValue();
  }
  
  public RelAbsVector(double absolute) {
    this.absolute = absolute;
  }
  
  public RelAbsVector(double absolute, double relative) {
    this.absolute = absolute;
    this.relative = relative;
  }
  
  public RelAbsVector(String coordinate) {
    setCoordinate(coordinate);
  }
  
  @Override
  public RelAbsVector clone() {
    return new RelAbsVector(this);
  }
  
  @Override
  public boolean equals(Object obj) {
    if(this == obj) {
      return true;
    }
    if(obj.getClass() != this.getClass()) {
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
   * @param absolute the new absolute value to be taken
   */
  public void setAbsoluteValue(double absolute) {
    this.absolute = absolute;
  }
  
  /**
   * @param relative
   *        the new relative value to be taken (in percent, i.e. relative=100d
   *        means 100%)
   */
  public void setRelativeValue(double relative) {
    this.relative = relative;
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
      absolute = null;
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
      relative = null;
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
   *        a valid RelAbsVector string
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
   * Produces a string of the format "a" or "r%" or "a+r%" as specified in the
   * render-documentation.
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
      if(getRelativeValue() > 0)
        result.append("+");
      result.append(getRelativeValue());
      result.append("%");
    }
    return result.toString();
  }
  
  /**
   * Produces a string for debugging etc. This is not the XML-string-encoding for render,
   * which can be retrieved by {@link RelAbsVector#getCoordinate()}
   */
  public String toString() {
    return String.format("RelAbsVector [absolute=%s, relative=%s]", absolute, relative);
  }
}

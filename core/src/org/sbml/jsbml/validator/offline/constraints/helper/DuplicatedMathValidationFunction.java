package org.sbml.jsbml.validator.offline.constraints.helper;

import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.ValidationFunction;
import org.sbml.jsbml.xml.parsers.MathMLStaxParser;


/**
 * @author rodrigue
 *
 */
public class DuplicatedMathValidationFunction<T extends MathContainer> implements ValidationFunction<T> {

  @Override
  public boolean check(ValidationContext ctx, T mathContainer) {
    
    if (mathContainer.isSetMath()) {
      if (mathContainer.isSetUserObjects() && mathContainer.getUserObject(MathMLStaxParser.JSBML_MATH_COUNT) != null) {
        int nbMath = (int) mathContainer.getUserObject(MathMLStaxParser.JSBML_MATH_COUNT);
    
        return nbMath == 1;                  
      }
    } else if (mathContainer.getLevelAndVersion().compareTo(3, 2) < 0) { // TODO - get the level and version from the ValidationContext ?
      // math is mandatory before SBML L3V2
      return false;
    }
    
    return true;
  }
}

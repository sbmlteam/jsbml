package org.sbml.jsbml.validator.offline.constraints.helper;

import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.ValidationFunction;
import org.sbml.jsbml.xml.XMLNode;


/**
 * @author rodrigue
 *
 */
public class UnknownAttributeValidationFunction<T extends TreeNodeWithChangeSupport> implements ValidationFunction<T> {

    @Override
    public boolean check(ValidationContext ctx, T t) {
      
        if (t.isSetUserObjects() && t.getUserObject(JSBML.UNKNOWN_XML) != null)
        {
          XMLNode unknownNode = (XMLNode) t.getUserObject(JSBML.UNKNOWN_XML);

          // System.out.println("UnknownAttributeValidationFunction - attributes.length = " + unknownNode.getAttributesLength());

          if (unknownNode.getAttributesLength() > 0) {
            return false;
          }
      }

      return true;
    }
}

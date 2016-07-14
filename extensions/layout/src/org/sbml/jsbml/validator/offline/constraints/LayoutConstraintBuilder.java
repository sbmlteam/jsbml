package org.sbml.jsbml.validator.offline.constraints;

import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.validator.offline.SBMLPackage;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.factory.AbstractConstraintBuilder;

public class LayoutConstraintBuilder extends AbstractConstraintBuilder {

  @Override
  public AnyConstraint<?> createConstraint(int id) {
    // logger.info("Create " + id);

    int firstThree = (id - SBMLPackage.LAYOUT.offset) / 100;

    switch (firstThree) {
    case 203:
      return createLayoutConstraint(id);
    default:
      return null;
    }
  }


  public AnyConstraint<?> createLayoutConstraint(int id) {
    ValidationFunction<Layout> f = null;
    switch (id) {
    case LAYOUT_20315:
      f = new ValidationFunction<Layout>() {

        @Override
        public boolean check(ValidationContext ctx, Layout l) {
          System.out.println("20315! " + l.isSetDimensions());
          return l.isSetDimensions();
        }
      };
      break;
    default:
      break;
    }
    return new ValidationConstraint<Layout>(id, f);
  }
}

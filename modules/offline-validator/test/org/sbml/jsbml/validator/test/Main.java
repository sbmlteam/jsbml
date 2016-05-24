package org.sbml.jsbml.validator.test;

import org.sbml.jsbml.validator.test.*;
import org.sbml.jsbml.validator.factory.ConstraintFactory;

/**
 * 
 * @author Roman Schulte
 * @version $Rev: 2377 $
 * @since 1.0
 * @date May 9, 2016
 */
public class Main {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    
    ConstraintFactory.getInstance(3, 1);
    DemoFrame frame = new DemoFrame(600, 400);
    frame.setVisible(true);
  }
}

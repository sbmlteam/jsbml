/*
 * $IdSBOValidationConstraints.java 14:13:33 roman $
 * $URLSBOValidationConstraints.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * Copyright (C) 2009-2016 jointly by the following organizations:
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
package org.sbml.jsbml.validator.offline.constraints.helper;

import org.sbml.jsbml.SBO;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.ValidationFunction;

/**
 * provides constants for the different SBO checks. Every constant is a
 * {@link ValidationFunction} typed for SBase objects.
 * 
 * @author Roman
 * @since 1.2
 * @date 08.08.2016
 */
public final class SBOValidationConstraints {

  /**
   * Checks if the SBO of the SBase is obsolete.
   */
  public static final ValidationFunction<SBase> isObsolete               =
      new ValidationFunction<SBase>()
  {

    @Override
    public boolean check(
      ValidationContext ctx,
      SBase sb) {

      if (sb.isSetSBOTerm()) {

        return SBO.isObsolete(
          sb.getSBOTerm());

      }

      return false;
    }


    /**
     * Checks if the SBO of the SBase is part of the branch 'Modelling Framework'.
     */                                                                  };
     public static final ValidationFunction<SBase> isModellingFramework     =
         new ValidationFunction<SBase>()
     {

       @Override
       public boolean check(
         ValidationContext ctx,
         SBase sb) {

         if (sb.isSetSBOTerm()) {
           try {
             return SBO.isModellingFramework(
               sb.getSBOTerm());
           } catch (Exception e) {
             return false;
           }

         }

         return true;
       };
     };


     /**
      * Checks if the SBO of the SBase is part of the branch 'Interaction'.
      */      
     public static final ValidationFunction<SBase> isInteraction            =
         new ValidationFunction<SBase>()
     {

       @Override
       public boolean check(
         ValidationContext ctx,
         SBase sb) {

         if (sb.isSetSBOTerm()) {
           try {
             return SBO.isInteraction(
               sb.getSBOTerm());
           } catch (Exception e) {
             return false;
           }

         }

         return true;
       };
     };


     /**
      * Checks if the SBO of the SBase is part of the branch 'Mathematical Expression'.
      */      
     public static final ValidationFunction<SBase> isMathematicalExpression =
         new ValidationFunction<SBase>()
     {

       @Override
       public boolean check(
         ValidationContext ctx,
         SBase sb) {

         if (sb.isSetSBOTerm()) {
           try {
             return SBO.isMathematicalExpression(
               sb.getSBOTerm());
           } catch (Exception e) {
             return false;
           }

         }

         return true;
       };
     };


     /**
      * Checks if the SBO of the SBase is part of the branch 'Material Entity'.
      */      
     public static final ValidationFunction<SBase> isMaterialEntity         =
         new ValidationFunction<SBase>()
     {

       @Override
       public boolean check(
         ValidationContext ctx,
         SBase sb) {

         if (sb.isSetSBOTerm()) {
           try {
             return SBO.isMaterialEntity(
               sb.getSBOTerm());
           } catch (Exception e) {
             return false;
           }

         }

         return true;
       };
     };


     /**
      * Checks if the SBO of the SBase is part of the branch 'Quantitative Parameter'.
      */      
     public static final ValidationFunction<SBase> isQuantitativeParameter  =
         new ValidationFunction<SBase>()
     {

       @Override
       public boolean check(
         ValidationContext ctx,
         SBase sb) {

         if (sb.isSetSBOTerm()) {
           try {
             return SBO.isQuantitativeParameter(
               sb.getSBOTerm());
           } catch (Exception e) {
             return false;
           }

         }

         return true;
       };
     };

     
     /**
      * Checks if the SBO of the SBase is part of the branch 'Participant Role'.
      */      
     public static final ValidationFunction<SBase> isParticipantRole        =
         new ValidationFunction<SBase>()
     {

       @Override
       public boolean check(
         ValidationContext ctx,
         SBase sb) {

         if (sb.isSetSBOTerm()) {
           try {
             return SBO.isParticipantRole(
               sb.getSBOTerm());
           } catch (Exception e) {
             return true;
           }

         }

         return true;
       };
     };

     
     /**
      * Checks if the SBO of the SBase is part of the branch 'Rate Law'.
      */      
     public static final ValidationFunction<SBase> isRateLaw                =
         new ValidationFunction<SBase>()
     {

       @Override
       public boolean check(
         ValidationContext ctx,
         SBase sb) {

         if (sb.isSetSBOTerm()) {
           try {
             return SBO.isRateLaw(
               sb.getSBOTerm());
           } catch (Exception e) {
             return false;
           }

         }

         return true;
       };
     };
}

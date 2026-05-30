package org.sbml.jsbml.util;

/**
 * Interface defining constant keywords and operators used in the Antimony scripting language.
 * Implementing this interface allows direct inheritance of all constants.
 *
 * @author Deepak Yadav
 */
public interface AntimonyConstants {
    public static final String MODEL = "model";
    public static final String END = "end";
    public static final String COMPARTMENT = "compartment";
    public static final String SPECIES = "species";
    public static final String SUBSTANCE_ONLY = "substanceOnly";
    public static final String IN = "in";
    public static final String IRREVERSIBLE = "=>";
    public static final String REVERSIBLE = "->";
    public static final String ASSIGNMENT = ":=";
    public static final String RATE = "'";
    public static final String ALGEBRAIC = "0";
    public static final String AT = "at";
    public static final String AFTER = "after";
    public static final String DELAY = "delay";
    public static final String PRIORITY = "priority";
    public static final String T0_FALSE = "t0";
    public static final String PERSISTENT_FALSE = "persistent";
}
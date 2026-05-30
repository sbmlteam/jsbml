package org.sbml.jsbml.util;

import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.AlgebraicRule;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;

/**
 * Utility class to serialize SBML models and components into the Antimony scripting language.
 * This provides a token-efficient, human-readable format optimized for Large Language Models (LLMs)
 * and bidirectional text-editor plugins.
 * 
 * @author Deepak Yadav
 */
public class AntimonySerializer implements AntimonyConstants {

    /**
     * Generic router for UI plugins (e.g., Eclipse, IntelliJ, VSCode). 
     * Allows dynamic serialization of any selected SBML component without knowing its specific type.
     * 
     * @param element The generic SBase element to serialize.
     * @return An Antimony-formatted string, or a comment if the element is unsupported/null.
     */
    public static String toAntimony(SBase element) {
        if (element == null) return "// Error: Element is null.";
        
        if (element instanceof Model) {
            return toAntimony((Model) element);
        } else if (element instanceof Compartment) {
            return toAntimony((Compartment) element);
        } else if (element instanceof Species) {
            return toAntimony((Species) element);
        } else if (element instanceof Reaction) {
            return toAntimony((Reaction) element);
        } else if (element instanceof Rule) {
            return toAntimony((Rule) element);
        } else if (element instanceof Event) {
            return toAntimony((Event) element);
        }
        
        return "// Unsupported SBML component for Antimony serialization.";
    }

    /**
     * Converts an entire SBML Model into a basic Antimony script string.
     * @param model The SBML Model to serialize.
     * @return An Antimony-formatted string representation of the model.
     */
    public static String toAntimony(Model model) {
        if (model == null) return "// Error: Model is null.";

        StringBuilder ant = new StringBuilder();
        
        String modelName = model.isSetName() ? model.getName() : model.getId();
        ant.append(MODEL).append(" ").append(modelName).append("()\n\n");

        ant.append("  // Compartments\n");
        for (Compartment c : model.getListOfCompartments()) {
            ant.append("  ").append(toAntimony(c)).append("\n");
        }
        ant.append("\n");

        ant.append("  // Species\n");
        for (Species s : model.getListOfSpecies()) {
            ant.append("  ").append(toAntimony(s)).append("\n");
        }
        ant.append("\n");

        ant.append("  // Reactions\n");
        for (Reaction r : model.getListOfReactions()) {
            ant.append("  ").append(toAntimony(r)).append("\n");
        }
        ant.append("\n");

        ant.append("  // Rules\n");
        for (Rule r : model.getListOfRules()) {
            ant.append("  ").append(toAntimony(r)).append("\n");
        }
        ant.append("\n");

        ant.append("  // Events\n");
        for (Event e : model.getListOfEvents()) {
            ant.append("  ").append(toAntimony(e)).append("\n");
        }
        ant.append("\n");

        ant.append(END).append("\n");

        return ant.toString();
    }

    /**
     * Converts an individual SBML Compartment into an Antimony string.
     */
    public static String toAntimony(Compartment c) {
        if (c == null) return "";
        StringBuilder ant = new StringBuilder();
        ant.append(COMPARTMENT).append(" ").append(c.getId());
        if (c.isSetSize()) {
            ant.append(" = ").append(c.getSize());
        }
        ant.append(";");
        return ant.toString();
    }

    /**
     * Converts an individual SBML Species into an Antimony string.
     * Implements rigorous checking for substance units and boundary conditions.
     */
    public static String toAntimony(Species s) {
        if (s == null) return "";
        StringBuilder ant = new StringBuilder();

        boolean hOSU = s.getHasOnlySubstanceUnits();
        boolean boundary = s.getBoundaryCondition();

        // 1. Handle Substance Units
        if (hOSU) {
            ant.append(SUBSTANCE_ONLY).append(" ").append(SPECIES).append(" ");
        } else {
            ant.append(SPECIES).append(" ");
        }

        // 2. Handle Boundary Condition
        if (boundary) {
            ant.append("$");
        }

        ant.append(s.getId());

        // Compartment assignment
        if (s.isSetCompartment()) {
            ant.append(" ").append(IN).append(" ").append(s.getCompartment());
        }

        // 3. Handle Initial Values based on Concentration vs Amount assumptions
        String comp = s.isSetCompartment() ? s.getCompartment() : "1";

        if (hOSU) {
            if (s.isSetInitialAmount()) {
                ant.append(" = ").append(s.getInitialAmount());
            } else if (s.isSetInitialConcentration()) {
                ant.append(" = ").append(s.getInitialConcentration()).append(" * ").append(comp);
            }
        } else {
            if (s.isSetInitialAmount()) {
                ant.append(" = ").append(s.getInitialAmount()).append(" / ").append(comp);
            } else if (s.isSetInitialConcentration()) {
                ant.append(" = ").append(s.getInitialConcentration());
            }
        }

        ant.append(";");
        return ant.toString();
    }

    /**
     * Converts an individual SBML Reaction into an Antimony string.
     * Handles reactants, products, named stoichiometry, reversibility, and kinetic laws.
     */
    public static String toAntimony(Reaction r) {
        if (r == null) return "";
        StringBuilder ant = new StringBuilder();

        // 1. Reaction ID
        ant.append(r.getId()).append(": ");

        // 2. Reactants
        for (int i = 0; i < r.getReactantCount(); i++) {
            SpeciesReference sr = r.getReactant(i);
            
            // Check for Named Stoichiometry (e.g., 'n S2')
            if (sr.isSetId()) {
                ant.append(sr.getId()).append(" ");
            } else if (sr.isSetStoichiometry() && sr.getStoichiometry() != 1d) {
                // Formatting to remove trailing zeros for clean output (e.g. 2.0 -> 2)
                ant.append(sr.getStoichiometry() == (long) sr.getStoichiometry() ? 
                           String.format("%d", (long)sr.getStoichiometry()) : 
                           String.format("%s", sr.getStoichiometry())).append(" ");
            }
            ant.append(sr.getSpecies());
            if (i < r.getReactantCount() - 1) ant.append(" + ");
        }

        // 3. Reversibility (Antimony uses -> for reversible, => for irreversible)
        if (r.isSetReversible() && !r.getReversible()) {
            ant.append(" ").append(IRREVERSIBLE).append(" ");
        } else {
            ant.append(" ").append(REVERSIBLE).append(" ");
        }

        // 4. Products
        for (int i = 0; i < r.getProductCount(); i++) {
            SpeciesReference sr = r.getProduct(i);
            
            if (sr.isSetId()) {
                ant.append(sr.getId()).append(" ");
            } else if (sr.isSetStoichiometry() && sr.getStoichiometry() != 1d) {
                ant.append(sr.getStoichiometry() == (long) sr.getStoichiometry() ? 
                           String.format("%d", (long)sr.getStoichiometry()) : 
                           String.format("%s", sr.getStoichiometry())).append(" ");
            }
            ant.append(sr.getSpecies());
            if (i < r.getProductCount() - 1) ant.append(" + ");
        }

        // 5. Kinetic Law
        if (r.isSetKineticLaw()) {
            KineticLaw kl = r.getKineticLaw();
            if (kl.isSetMath()) {
                // Convert ASTNode to a math string
                ant.append("; ").append(ASTNode.formulaToString(kl.getMath()));
            }
        }
        
        ant.append(";");
        return ant.toString();
    }

    /**
     * Converts an SBML Rule (Assignment, Rate, or Algebraic) into an Antimony string.
     */
    public static String toAntimony(Rule r) {
        if (r == null || !r.isSetMath()) return "";
        
        String math = ASTNode.formulaToString(r.getMath());
        
        if (r instanceof AssignmentRule) {
            return ((AssignmentRule) r).getVariable() + " " + ASSIGNMENT + " " + math + ";";
        } else if (r instanceof RateRule) {
            return ((RateRule) r).getVariable() + RATE + " = " + math + ";";
        } else if (r instanceof AlgebraicRule) {
            return ALGEBRAIC + " = " + math + ";";
        }
        
        return "// Unsupported Rule type.";
    }

    /**
     * Converts an SBML Event into an Antimony string.
     * Supports advanced options including delays, priorities, t0, and persistence.
     */
    public static String toAntimony(Event e) {
        if (e == null) return "";
        StringBuilder ant = new StringBuilder();

        if (e.isSetId()) {
            ant.append(e.getId()).append(": ");
        }

        ant.append(AT).append(" ");
        boolean hasTrigger = e.isSetTrigger() && e.getTrigger().isSetMath();
        boolean hasDelay = e.isSetDelay() && e.getDelay().isSetMath();

        if (hasDelay && hasTrigger) {
            ant.append(ASTNode.formulaToString(e.getDelay().getMath()));
            ant.append(" ").append(AFTER).append(" ");
            ant.append(ASTNode.formulaToString(e.getTrigger().getMath()));
        } else if (hasTrigger) {
            ant.append(ASTNode.formulaToString(e.getTrigger().getMath()));
        }

        // Advanced Event Options
        if (e.isSetPriority() && e.getPriority().isSetMath()) {
            ant.append(", ").append(PRIORITY).append(" = ").append(ASTNode.formulaToString(e.getPriority().getMath()));
        }
        if (e.isSetTrigger()) {
            org.sbml.jsbml.Trigger t = e.getTrigger();
            if (t.isSetInitialValue() && !t.getInitialValue()) {
                ant.append(", ").append(T0_FALSE).append(" = false");
            }
            if (t.isSetPersistent() && !t.getPersistent()) {
                ant.append(", ").append(PERSISTENT_FALSE).append(" = false");
            }
        }
        ant.append(": ");

        int count = e.getEventAssignmentCount();
        for (int i = 0; i < count; i++) {
            EventAssignment ea = e.getEventAssignment(i);
            ant.append(ea.getVariable()).append(" = ");
            if (ea.isSetMath()) {
                ant.append(ASTNode.formulaToString(ea.getMath()));
            }
            if (i < count - 1) ant.append(", ");
        }

        ant.append(";");
        return ant.toString();
    }
}
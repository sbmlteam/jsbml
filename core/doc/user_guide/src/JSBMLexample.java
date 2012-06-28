import java.beans.PropertyChangeEvent;
import javax.swing.tree.TreeNode;
import org.sbml.jsbml.*;
import org.sbml.jsbml.util.TreeNodeChangeListener;

/** Creates an {@link SBMLDocument} and writes it's content to disk. **/ 
public class JSBMLexample implements TreeNodeChangeListener {
    public JSBMLexample() throws Exception  {
        // Create a new SBMLDocument object, using SBML Level 2 Version 4.
        SBMLDocument doc = new SBMLDocument(2, 4);
        doc.addTreeNodeChangeListener(this);
    
        // Create a new SBML model, and add a compartment to it.
        Model model = doc.createModel("test_model");
        Compartment compartment = model.createCompartment("default");
        compartment.setSize(1d);

        // Create a model history object and add author information to it.
        History hist = new History();
        Creator creator = new Creator("Given Name", "Family Name", "Organisation", "My@EMail.com");
        hist.addCreator(creator);
        model.setHistory(hist);
    
        // Create some sample content in the SBML model.
        Species specOne = model.createSpecies("test_spec1", compartment);
        Species specTwo = model.createSpecies("test_spec2", compartment);
        Reaction sbReaction = model.createReaction("reaction_id");
    
        // Add a substrate (SBO: 15) and product (SBO: 11) to the reaction.
        SpeciesReference subs = sbReaction.createReactant(specOne);
        subs.setSBOTerm(15);
        SpeciesReference prod = sbReaction.createProduct (specTwo);
        prod.setSBOTerm(11);
    
        // Write the SBML document to disk.
        // Note that for brevity, THIS DOES NOT PERFORM ANY ERROR CHECKING.
        // A real-life program would check for possible errors.
        SBMLWriter.write(doc, "test.xml", "JSBMLexample", "0.1");
    }
  
    /**
     * Main routine.  This does not take any arguments.
     */
    public static void main(String[] args) throws Exception {
        new JSBMLexample();
    }

    /* Methods for TreeNodeChangeListener, to respond to events from SBaseChangedListener. */
    public void nodeAdded(TreeNode sb) {System.out.println("[ADD] " + sb);}
    public void nodeRemoved(TreeNode sb) {System.out.println("[RMV] " + sb);}
    public void propertyChange(PropertyChangeEvent ev) {System.out.println("[CHG] " + ev);}
}

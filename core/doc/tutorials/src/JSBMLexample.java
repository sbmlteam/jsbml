import java.beans.PropertyChangeEvent;
import javax.swing.tree.TreeNode;
import org.sbml.jsbml.*;
import org.sbml.jsbml.util.TreeNodeChangeListener;

/** Creates an {@link SBMLDocument} and writes it's content to disk. **/ 
public class JSBMLexample implements TreeNodeChangeListener {
  public JSBMLexample() throws Exception  {
    // Create a new SBMLDocument, using SBML level 2 version 4.
    SBMLDocument doc = new SBMLDocument(2, 4);
    doc.addTreeNodeChangeListener(this);
    
    // Create a new SBML-Model and compartment in the document
    Model model = doc.createModel("test_model");
    Compartment compartment = model.createCompartment("default");
    compartment.setSize(1d);

    // Create model history
    History hist = new History();
    Creator creator = new Creator("Given Name", "Family Name",
      "My Organisation", "My@EMail.com");
    hist.addCreator(creator);
    model.setHistory(hist);
    
    // Create some example content in the document
    Species specOne = model.createSpecies("test_spec1", compartment);
    Species specTwo = model.createSpecies("test_spec2", compartment);
    Reaction sbReaction = model.createReaction("reaction_id");
    
    // Add a substrate (SBO: 15) and product (SBO: 11).
    SpeciesReference subs = sbReaction.createReactant(specOne);
    subs.setSBOTerm(15);
    SpeciesReference prod = sbReaction.createProduct (specTwo);
    prod.setSBOTerm(11);
    
    // Write the SBML document to disk
    SBMLWriter.write(doc, "test.sbml.xml", "ProgName", "Version");
  }
  
  /** Just an example main **/
  public static void main(String[] args) throws Exception {
    new JSBMLexample();
  }

  /* Those three methods respond to events from SBaseChangedListener */
  public void nodeAdded(TreeNode sb) {System.out.println("[ADD] " + sb);}
  public void nodeRemoved(TreeNode sb) {System.out.println("[RMV] " + sb);}
  public void propertyChange(PropertyChangeEvent ev) {System.out.println("[CHG] " + ev);}
}
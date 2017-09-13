import static org.sbml.jsbml.util.Pair.pairOf;

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Creator;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.TidySBMLWriter;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.ModelBuilder;

/**
 * @author Andreas Dr&auml;ger
 */
public class JSBMLExample {
  
  /**
   * @param args path to an SBML file
   * @throws IOException
   * @throws XMLStreamException
   */
  public static void main(String[] args) throws XMLStreamException, IOException {
    int level = 3, version = 1;
    ModelBuilder builder = new ModelBuilder(level, version);
    // Define base units for volume (mL) and substance amounts (mmol)
    UnitDefinition volume = builder.buildUnitDefinition(UnitDefinition.VOLUME, "spatial size unit", new Unit(1d, -3, Unit.Kind.LITRE, 1d, level, version));
    UnitDefinition substance = builder.buildUnitDefinition(UnitDefinition.SUBSTANCE, "substance unit", new Unit(1d, -3, Unit.Kind.MOLE, 1d, level, version));
    
    // Create a 3 dimensional compartment with id="c", constant volume = true and a size of 1 litre.
    Compartment c = builder.buildCompartment("c", true, "Cytosol", 3d, 1d, volume);
    // Create a metabolite representing glucose with an initial amount of 1 millimole
    Species glc = builder.buildSpecies("M_glc", "Glucose", c, true, false, false, 1d, substance);
    // Create a reaction
    Reaction r = builder.buildReaction("R_EX_glc", "Glucose exchange", c, false, true);
    ModelBuilder.buildReactants(r, pairOf(1d, glc));
    
    // Document the creator of this model.
    Model m = builder.getModel();
    m.setMetaId("meta_model");
    m.createHistory().addCreator(new Creator("Jane", "Doe", "Institute", "jane.doe@institute.edu"));
    
    // Write the SBML document to the file specified as command-line argument
    TidySBMLWriter.write(builder.getSBMLDocument(), new File(args[0]), ' ', (short) 2);
  }
  
}

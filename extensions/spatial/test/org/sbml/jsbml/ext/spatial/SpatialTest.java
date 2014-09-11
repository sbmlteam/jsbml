package org.sbml.jsbml.ext.spatial;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;


public class SpatialTest {

  public static void main(String[] args) throws SBMLException, XMLStreamException {
    int level = 3, version = 1;
    Species spec1,spec2,spec3;
    Reaction rxn1;

    SBMLDocument doc = new SBMLDocument(level, version);
    Model model = doc.createModel("my_model");

    // Normal model

    Compartment comp1 = model.createCompartment("comp1");

    spec1 = model.createSpecies("a", comp1);
    spec2 = model.createSpecies("b", comp1);
    spec3 = model.createSpecies("c", comp1);

    rxn1 = model.createReaction("r1");

    rxn1.addReactant(new SpeciesReference(spec1));
    rxn1.addReactant(new SpeciesReference(spec2));
    rxn1.addProduct(new SpeciesReference(spec3));

    // Creating the spatial model extension and adding it to the document

    // Create spatial extensions for model and compartment
    SpatialModelPlugin spatialModelPlugin = new SpatialModelPlugin(model);
    model.addExtension(SpatialConstants.getNamespaceURI(level, version), spatialModelPlugin);

    SpatialCompartmentPlugin spatialComp = new SpatialCompartmentPlugin(comp1);
    comp1.addExtension(SpatialConstants.getNamespaceURI(level, version), spatialComp);

    // Add non-SBML-core classes

    Geometry geo = spatialModelPlugin.createGeometry();

    CompartmentMapping spatialCompMap = new CompartmentMapping();
    spatialComp.setCompartmentMapping(spatialCompMap);
    spatialCompMap.setCompartment("comp1");
    spatialCompMap.setDomainType("DomainType1");

    SBMLWriter.write(doc, System.out, ' ', (short) 2);

  }
}

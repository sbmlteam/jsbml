package org.sbml.jsbml.ext.comp.util;
import org.sbml.jsbml.*;
import org.sbml.jsbml.ext.comp.*;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompFlatteningConverter {

    private ArrayList<String> previousModelIDs;
    private ArrayList<String> previousModelMetaIDs;
    private ListOf<ModelDefinition> modelDefinitionListOf;

    private Model previousModel;
    private Model flattenedModel;
    private Model currentModel;

    private SBMLDocument document;


    public CompFlatteningConverter() {
        this.previousModelIDs = new ArrayList<>();
        this.previousModelMetaIDs = new ArrayList<>();
        this.modelDefinitionListOf = new ListOf<>();

        currentModel = new Model();
       // previousModel = new Model();
        flattenedModel = new Model();

    }


    /**
     * Public method to call on a CompflatteningConverter object.
     * Takes a SBML Document and flattens the models of the comp plugin.
     * Returns the SBML Document with a flattend model.
     *
     * @param document
     * @return
     */
    public SBMLDocument flatten(SBMLDocument document) {

        this.document = document;
        this.flattenedModel = new Model(); // this is the model that will be returned

        if (document.isPackageEnabled("comp")) {

            CompSBMLDocumentPlugin compSBMLDocumentPlugin = (CompSBMLDocumentPlugin) document.getExtension("comp");

            this.modelDefinitionListOf = compSBMLDocumentPlugin.getListOfModelDefinitions();

            // do I do this directly with the submodels OR...?
            if (compSBMLDocumentPlugin.getExtendedSBase().getModel().getExtension("comp") != null) {
                this.previousModel = compSBMLDocumentPlugin.getExtendedSBase().getModel();

                CompModelPlugin compModelPlugin = (CompModelPlugin) compSBMLDocumentPlugin.getExtendedSBase().getModel().getExtension("comp");
                instantiateSubModels(compModelPlugin);
            } else {
                System.out.println("why no comp?");
            }

            // ...with model defitions?
//            if (compSBMLDocumentPlugin.getModelDefinitionCount() > 0) {
//
//                ListOf<ModelDefinition> listOfModelDefinitions = compSBMLDocumentPlugin.getListOfModelDefinitions();
//
//                for(ModelDefinition modelDefinition: listOfModelDefinitions){
//
//                    ModelDefinition modelDefinitionClone = modelDefinition; // clone the object
//                    listOfModelDefinitions.remove(modelDefinition);
//                    examineModelDefinition(modelDefinitionClone);
//                }
//
//                // TODO: how to handle external model defintions-> should work the same as with normal model definitions?
//                //ListOf<ExternalModelDefinition> listOfExternalModelDefinitions = compSBMLDocumentPlugin.getListOfExternalModelDefinitions();
//
//                //flattenedModel = getFlattenedModel(listOfModelDefinitions);
//
//
//            } else { //TODO: give back proper error message
//                System.out.println("no model definitions given");
//
//            }
//
        } else { //TODO: give back proper error message
            System.out.println("document has no comp package");
        }

        document.setModel(this.flattenedModel);
        document.unsetExtension("comp");
        return document;

    }


//    /**
//     * @param listOfModelDefinitions
//     * @return
//     */
//    private Model getFlattenedModel(ListOf<ModelDefinition> listOfModelDefinitions) {
//
//        flattenedModel = new Model();
//
//        for (ModelDefinition modelDefinition : listOfModelDefinitions) {
//
//            flattenedModel = examineModelDefinition(modelDefinition);
//
//        }
//
//        return flattenedModel;
//
//    }



    /**
     * Initiates every Submodel in the CompModelPlugin recursively
     *
     * @param compModelPlugin
     */
    private Model instantiateSubModels(CompModelPlugin compModelPlugin) {

        if (compModelPlugin.getSubmodelCount() > 0) {

            ListOf<Submodel> subModelListOf = compModelPlugin.getListOfSubmodels().clone();
            compModelPlugin.getListOfSubmodels().clear();

            // check if submodel has submodel
            for (Submodel submodel : subModelListOf) {

                Submodel submodelClone = submodel;
                compModelPlugin.getListOfSubmodels().removeFromParent();
                submodel = submodelClone;

                Model initModel = this.modelDefinitionListOf.get(submodel.getModelRef());

                if (initModel.getExtension("comp") != null) { // TODO: this does not work?

                    CompModelPlugin compSubModelPlugin = (CompModelPlugin) initModel.getExtension("comp");
                    this.currentModel = instantiateSubModels(compSubModelPlugin);

                } else {

                    this.currentModel = flattenSubModel(submodel);
                    this.flattenedModel = mergeModels(this.previousModel, this.currentModel); // this Model object is made the new child of the SBMLDocument container
                    this.previousModel = this.flattenedModel;
                }

            }
            //this.flattenedModel = mergeModels(this.currentModel, this.flattenedModel);

        } else {
            System.out.println("no more submodels");
        }

        return this.flattenedModel;
    }


    /**
     * All remaining elements are placed in a single Model object;
     * The original Model, ModelDefinition, and ExternalModelDefinition objects are all deleted.
     *
     * @param currentModel
     * @return
     */
    private Model mergeModels(Model previousModel, Model currentModel) {

        if (previousModel != null) {

            // match versions and level
            currentModel.setLevel(previousModel.getLevel());
            currentModel.setVersion(previousModel.getVersion());


            // ... for all lists?
            ListOf<Reaction> reactionListOfClone = previousModel.getListOfReactions().clone();
            previousModel.getListOfReactions().removeFromParent();
            for (Reaction reaction : reactionListOfClone) {
                currentModel.addReaction(reaction.clone());
            }

            ListOf<Compartment> compartmentListOfClone = previousModel.getListOfCompartments().clone();
            previousModel.getListOfCompartments().removeFromParent();
            for (Compartment compartment : compartmentListOfClone) {
                currentModel.addCompartment(compartment.clone());
            }

            ListOf<Species> speciesListOfClone = previousModel.getListOfSpecies().clone();
            previousModel.getListOfSpecies().removeFromParent();
            for (Species species : speciesListOfClone) {
                currentModel.addSpecies(species.clone());
            }

        } // else nothing to merge? -> return model

        return currentModel;
    }


    /**
     * Performs the routine to flatten a submodel into a model
     *
     * @param subModel
     * @return
     */
    private Model flattenSubModel(Submodel subModel) {

        Model model = new Model();

        // 2
        // Let “M” be the identifier of a given submodel.
        String subModelID = subModel.getId();
        String subModelMetaID = subModel.getMetaId();


        System.out.println(subModelID);
        System.out.println(subModelMetaID);

        // Verify that no object identifier or meta identifier of objects in that submodel
        // (i.e., the id or metaid attribute values)
        // begin with the character sequence “M__”;

        // if there is an existing identifier or meta identifier beginning with “M__”,
        // add an underscore to “M__” (i.e., to produce “M___”) and again check that the sequence is unique.
        // Continue adding underscores until you find a unique prefix. Let “P” stand for this final prefix.

        while (this.previousModelIDs.contains(subModelID)) {
            subModelID += "_";
        }

        while (this.previousModelMetaIDs.contains(subModelMetaID)) {
            subModelMetaID += "_";
        }

        this.previousModelIDs.add(subModelID);
        this.previousModelMetaIDs.add(subModelID);

        if (subModel.getModelRef() != null) {

            // initiate a clone of the referenced model
            Model modelOfSubmodel = this.modelDefinitionListOf.get(subModel.getModelRef()).clone();

            // 3
            // Remove all objects that have been replaced or deleted in the submodel.
            for (Deletion deletion : subModel.getListOfDeletions()){

            }

            // model.remove ... ?

            // 4
            // Transform the remaining objects in the submodel as follows:
            // a)
            // Change every identifier (id attribute)
            // to a new value obtained by prepending “P” to the original identifier.
            // b)
            // Change every meta identifier (metaid attribute)
            // to a new value obtained by prepending “P” to the original identifier.

            for (Reaction reaction : modelOfSubmodel.getListOfReactions()) { // like this for all the lists?
                //modelOfSubmodel.removeReaction(reaction); // otherwise there is a warning. can this be ignored because the modelOfSubmodel gets not returned anyway
                reaction.setId(subModelID + reaction.getId());
                //reaction.setMetaId(subModelMetaID + reaction.getMetaId()); // TODO has not always a meta ID?
            }

            for (Species species : modelOfSubmodel.getListOfSpecies()){
                String flattenedSpeciesID = subModelID + species.getId();
                species.setId(flattenedSpeciesID);

            }

            for(Compartment compartment : modelOfSubmodel.getListOfCompartments()){
                String flattenedCompartmentID = subModelID + compartment.getId();
                compartment.setId(flattenedCompartmentID);

            }

            for (Constraint constraint : modelOfSubmodel.getListOfConstraints()){
                String flattenedContraintID = subModelID + constraint.getId();
                constraint.setId(flattenedContraintID);

            }

            // 5
            // Transform every SIdRef and IDREF type value in the remaining objects of the submodel as follows:
            // a)
            // If the referenced object has been replaced by the application of a ReplacedBy or ReplacedElement construct,
            // change the SIdRef value (respectively, IDREF value) to the SId value (respectively, ID value)
            // of the object replacing it.
            // b)
            // If the referenced object has not been replaced, change the SIdRef and IDREF value by prepending “P”
            // to the original value.

            // 6
            // After performing the tasks above for all remaining objects, merge the objects of the remaining submodels
            // into a single model.
            // Merge the various lists (list of species, list of compartments, etc.)
            // in this step, and preserve notes and annotations as well as constructs from other SBML Level 3 packages.

            model = mergeModels(this.previousModel, modelOfSubmodel); // initiate model (?)

        }

        return model;
    }


    public static void main(String[] args) throws IOException, XMLStreamException {

        File file = new File(args[0]);

        SBMLReader reader = new SBMLReader();
        SBMLDocument document = reader.readSBML(file);

        CompFlatteningConverter compFlatteningConverter = new CompFlatteningConverter();

        SBMLDocument flattendSBML = compFlatteningConverter.flatten(document);

        SBMLWriter writer = new SBMLWriter();
        System.out.println(writer.writeSBMLToString(flattendSBML));


    }


    /**
     * Examines a ModelDefinition to check if there is a submodel given to flatten or a submodel has further submodels
     * Returns one flattened Model (?)
     *
     * @param modelDefinition
     * @return
     */
    private Model examineModelDefinition(ModelDefinition modelDefinition) {


        if (modelDefinition.isPackageEnabled("comp")){

            Model model = new Model(modelDefinition);

            CompModelPlugin compModelPlugin = new CompModelPlugin((CompModelPlugin) model.getPlugin("comp"));

            // Each submodel is instantiated;
            // that is, a copy of every Model object referenced by every Submodel object is created.
            // This is a recursive process: if the instantiated Model itself has Submodel children, they are also instantiated.

            this.flattenedModel = instantiateSubModels(compModelPlugin);


        } else {
            System.out.println("Model definition has no comp plugin.");
            //modelDefinition.getModel().unsetExtension("comp");
            this.flattenedModel = mergeModels(modelDefinition, this.flattenedModel); // TODO: flatten comp model into model?
        }

        return this.flattenedModel;

    }


}

//    private CompModelPlugin examineCompModel(CompSBMLDocumentPlugin compSubDocument) {
//
//        CompModelPlugin compModelPlugin = null; // TODO
//
//        for (ModelDefinition modelDefinition : compSubDocument.getListOfModelDefinitions()) {
//
//            compModelPlugin = new CompModelPlugin(modelDefinition);
//
//            if (modelDefinition.isPackageEnabled("comp")) {
//
//                CompSBMLDocumentPlugin compSBMLDocumentPlugin = (CompSBMLDocumentPlugin) compModelPlugin.getSBMLDocument().getExtension("comp");
//
//                if (compSBMLDocumentPlugin.getModelDefinitionCount() > 0) {
//                    compModelPlugin = examineCompModel(compSBMLDocumentPlugin);
//                } else {
//
//                    ListOf<Submodel> subModelList = compModelPlugin.getListOfSubmodels();
//                    // 1
//                    // Examine all submodels of the model being validated.
//                    for (Submodel subModel : subModelList) {
//                        //examineSubModel(subModel);
//                        System.out.println(subModel.getModelRef());
//                    }
//                }
//
//            }
//
//        }
//
//        return compModelPlugin;
//    }

//    private Submodel examineSubModel(Submodel subModel) {
//
//
//        Model model = subModel.getModel();
//
//        CompModelPlugin compSubModel = new CompModelPlugin(subModel.getExtension("comp").getSBMLDocument().getModel());
//        Submodel flattenedSubModel;
//
//        // For any submodel that itself contains submodels, perform this algorithm in 1 its entirety
//        // on each of those inner submodels before proceeding to the next step.
//
//        if (compSubModel.getListOfSubmodels().size() == 0) {
//
//            // has no submodels -> perform algorithm
//            flattenedSubModel = flattenSubModel(subModel);
//
//        } else {
//            // call function recursively with submodel of submodel
//            flattenedSubModel = examineSubModel(subModel);
//
//        }
//
//        return flattenedSubModel;
//
//    }


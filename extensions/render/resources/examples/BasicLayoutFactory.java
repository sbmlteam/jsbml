package examples;

import org.sbml.jsbml.ext.render.director.AssociationNode;
import org.sbml.jsbml.ext.render.director.Catalysis;
import org.sbml.jsbml.ext.render.director.Compartment;
import org.sbml.jsbml.ext.render.director.Consumption;
import org.sbml.jsbml.ext.render.director.DissociationNode;
import org.sbml.jsbml.ext.render.director.Inhibition;
import org.sbml.jsbml.ext.render.director.LayoutFactory;
import org.sbml.jsbml.ext.render.director.Macromolecule;
import org.sbml.jsbml.ext.render.director.Modulation;
import org.sbml.jsbml.ext.render.director.NecessaryStimulation;
import org.sbml.jsbml.ext.render.director.NucleicAcidFeature;
import org.sbml.jsbml.ext.render.director.OmittedProcessNode;
import org.sbml.jsbml.ext.render.director.PerturbingAgent;
import org.sbml.jsbml.ext.render.director.ProcessNode;
import org.sbml.jsbml.ext.render.director.Production;
import org.sbml.jsbml.ext.render.director.ReversibleConsumption;
import org.sbml.jsbml.ext.render.director.SimpleChemical;
import org.sbml.jsbml.ext.render.director.SourceSink;
import org.sbml.jsbml.ext.render.director.Stimulation;
import org.sbml.jsbml.ext.render.director.UncertainProcessNode;
import org.sbml.jsbml.ext.render.director.UnspecifiedNode;


public class BasicLayoutFactory implements LayoutFactory<String, String> {

  private double lineWidth = 1;
  private double arrowScale = 2;
  
  /**
   * 
   * @param lineWidth the width of lines in the resulting drawing; in pt
   * @param arrowScale the scale of arrow-heads
   */
  public BasicLayoutFactory(double lineWidth, double arrowScale) {
    this.lineWidth = lineWidth;
    this.arrowScale = arrowScale;
  }


  @Override
  public AssociationNode<String> createAssociationNode() {
    // TODO Create an AssociationNode-implementation
    return null;
  }


  @Override
  public Compartment<String> createCompartment() {
    return new LaTeXCompartment(lineWidth);
  }


  @Override
  public DissociationNode<String> createDissociationNode() {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public Macromolecule<String> createMacromolecule() {
    return new LaTeXMacromolecule(lineWidth);
  }


  @Override
  public NucleicAcidFeature<String> createNucleicAcidFeature() {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public OmittedProcessNode<String> createOmittedProcessNode() {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public PerturbingAgent<String> createPerturbingAgent() {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public ProcessNode<String> createProcessNode() {
    return new LaTeXProcessNode(lineWidth, 10); // <- TODO: into constructor.
  }


  @Override
  public SimpleChemical<String> createSimpleChemical() {
    return new LaTeXSimpleChemical(lineWidth);
  }


  @Override
  public SourceSink<String> createSourceSink() {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public UncertainProcessNode<String> createUncertainProcessNode() {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public UnspecifiedNode<String> createUnspecifiedNode() {
    return new LaTeXUnspecifiedNode(lineWidth);
  }


  @Override
  public Catalysis<String> createCatalysis() {
    return new LaTeXCatalysis(arrowScale);
  }


  @Override
  public Consumption<String> createConsumption() {
    return new LaTeXConsumption();
  }


  @Override
  public ReversibleConsumption<String> createReversibleConsumption() {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public Inhibition<String> createInhibition() {
    return new LaTeXInhibition(arrowScale);
  }


  @Override
  public Modulation<String> createModulation() {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public NecessaryStimulation<String> createNecessaryStimulation() {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public Production<String> createProduction() {
    return new LaTeXProduction(arrowScale);
  }


  @Override
  public Stimulation<String> createStimulation() {
    return new LaTeXStimulation(arrowScale);
  }
}

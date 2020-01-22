package examples;

import org.sbml.jsbml.ext.render.director.SourceSink;


public class LaTeXSourceSink extends SourceSink<String> {

  private String id;
  
  public LaTeXSourceSink() {
    // TODO: here font-size?
    super();
  }


  @Override
  public String draw(double x, double y, double z, double width, double height,
    double depth) {
    return String.format("\\node[] (%s) at (%spt,%spt) {$\\diameter$};", id, x + width/2, y + height/2);
  }
  
  public String getNodeId() {
    return id;
  }
  
  public void setNodeId(String id) {
    this.id = id;
  }
}

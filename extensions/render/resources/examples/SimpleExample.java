package examples;

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.render.director.LayoutAlgorithm;
import org.sbml.jsbml.ext.render.director.LayoutBuilder;
import org.sbml.jsbml.ext.render.director.LayoutDirector;

public class SimpleExample {
  public static void main(String[] args) {
    
    // File file = new File("extensions/render/resources/examples/smallest_example.xml");
    // File file = new File("extensions/render/resources/examples/layout_spec_example.xml");
    File file = new File("extensions/render/resources/examples/unlaidout_spec_example.xml");
    /*
     * Observations: 
     * a) LayoutDirector cannot handle file w/o any Layout information: needs a 
     * layout with dimensions set
     * b) The basic implementation is utterly insufficient.
     */
    System.out.println("Reading file " + file);
    try {
      LayoutDirector<String> director = new LayoutDirector<String>(file,
        new BasicLayoutBuilder(), new BasicLayoutAlgorithm());
      // Thread t1 = new Thread(director);
      // t1.start();
      director.run();
      System.out.println("LaTeX-document:\n");
      System.out.println(director.getProduct());
    } catch (XMLStreamException | IOException e) {
      e.printStackTrace();
    }
  }
}

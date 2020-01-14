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
    File file = new File("extensions/render/resources/examples/layout_spec_example.xml");
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

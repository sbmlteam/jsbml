package examples.render;

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.ext.render.director.LayoutDirector;

import examples.latex.BasicLayoutAlgorithm;
import examples.latex.BasicLayoutBuilder;

public class RenderExample {

  public static void main(String[] args) {
    File file = new File("extensions/render/resources/examples/render/smallest_example.xml");
    System.out.println("Reading file " + file);
    try {
      LayoutDirector<String> director = new LayoutDirector<String>(file,
        new BasicLayoutBuilder(1, 1.5, 10), new BasicLayoutAlgorithm());
      director.run();
      System.out.println("LaTeX-document:\n");
      System.out.println(director.getProduct());
    } catch (XMLStreamException | IOException e) {
      e.printStackTrace();
    }
  }
}

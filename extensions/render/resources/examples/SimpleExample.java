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
    File file = new File("extensions/render/resources/examples/example_fully_laid_out.sbml.xml");
    System.out.println("Reading file " + file);
    try {
      LayoutDirector<Layout> director = new LayoutDirector<Layout>(file,
        (LayoutBuilder<Layout>) null, new BasicLayoutAlgorithm());
    } catch (XMLStreamException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // LayoutDirector(File inputFile, LayoutBuilder<P> builder, LayoutAlgorithm algorithm)
  }
}

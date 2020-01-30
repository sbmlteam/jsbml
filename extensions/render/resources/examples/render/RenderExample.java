package examples.render;

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;
import org.sbml.jsbml.ext.render.LocalRenderInformation;
import org.sbml.jsbml.ext.render.RenderLayoutPlugin;
import org.sbml.jsbml.ext.render.director.LayoutDirector;

import examples.latex.BasicLayoutAlgorithm;

public class RenderExample {

  public static void main(String[] args) {
    File file = new File("extensions/render/resources/examples/render/smallest_example.xml");
    System.out.println("Reading file " + file);
    try {
      LayoutDirector<LocalRenderInformation> director = new LayoutDirector<LocalRenderInformation>(file,
        new RenderLayoutBuilder(), new BasicLayoutAlgorithm());
      director.run();
      System.out.println("Layout-Information-document:\n");
      System.out.println(director.getProduct());
      SBMLDocument doc = SBMLReader.read(file);
      Layout ly = ((LayoutModelPlugin) doc.getModel().getExtension(LayoutConstants.getNamespaceURI(doc.getLevel(), doc.getVersion()))).getLayout(0);
      RenderLayoutPlugin plugin = new RenderLayoutPlugin(ly); 
      ly.addPlugin(LayoutConstants.getNamespaceURI(doc.getLevel(), doc.getVersion()), plugin);
      plugin.addLocalRenderInformation(director.getProduct());
      SBMLWriter.write(doc, new File("extensions/render/resources/examples/render/result.xml"), "Render-example", "1");
    } catch (XMLStreamException | IOException e) {
      e.printStackTrace();
    }
  }
}

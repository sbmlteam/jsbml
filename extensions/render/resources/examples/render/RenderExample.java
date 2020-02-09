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

/**
 * Main class for a simple example using the
 * {@link org.sbml.jsbml.ext.render.director} classes to generate a
 * LocalRenderInformation (from the render-plugin):<br>
 * 
 * Code-wise, this is very similar to the LaTeX-example, so the explanatory
 * comments will focus on render-specific code.<br>
 * 
 * To view the rendered result.xml-file, use e.g. COPASI
 *
 * @author DavidVetter
 */
public class RenderExample {

  public static void main(String[] args) {
    File file = new File("extensions/render/resources/examples/latex/layout_spec_example.xml");
    System.out.println("Reading file " + file);
    try {
      /**
       * To work on the same document, read it once:
       * The layout-director will then modify the layout (side-effects) that is
       * also later retrieved from the document as ly
       */
      SBMLDocument doc = SBMLReader.read(file);
      
      /**
       * Use the same LayoutAlgorithm as in the LaTeX-example
       */
      LayoutDirector<LocalRenderInformation> director = new LayoutDirector<LocalRenderInformation>(doc,
        new RenderLayoutBuilder(), new BasicLayoutAlgorithm());
      director.run();
      
      System.out.println("Layout-Information-document:\n");
      System.out.println(director.getProduct());
      
      /**
       * Write the generated LocalLayoutInformation into the result-file.
       */
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

package org.sbml.jsbml.ext.dyn.test;

import java.io.InputStream;
import javax.xml.stream.XMLStreamException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.ext.dyn.DynCompartmentPlugin;
import org.sbml.jsbml.ext.dyn.DynElement;
import org.sbml.jsbml.ext.dyn.DynEventPlugin;
import org.sbml.jsbml.ext.dyn.SpatialComponent;
import org.sbml.jsbml.xml.stax.SBMLReader;
import org.sbml.jsbml.xml.stax.SBMLWriter;

/**
 * @author Harold Gomez
 * @since 1.0
 */
public class TestL3Dyn {

	public static String DYN_NAMESPACE = "http://www.sbml.org/sbml/level3/version1/dyn/version1";

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests whether extended models can be read properly
	 */
	@Test
	public void test_L3_dyn_read() throws XMLStreamException {
		InputStream fileStream = TestL3Dyn.class
				.getResourceAsStream("/org/sbml/jsbml/xml/test/data/dyn/dynUseCase.xml");
		SBMLDocument doc = new SBMLReader().readSBMLFromStream(fileStream);
		Model model = doc.getModel();
		
		//Check whether the dyn extension elements are being read in properly!
		System.out.println(doc.getExtensionPackages());
		
		DynEventPlugin extendedEvent;
		Event tempEvent;
		Object tempObject;
		ListOf<DynElement> dynElementList;
		for (int i = 0; i < model.getEventCount(); i++) {
			tempObject = model.getEvent(i).getExtension(DYN_NAMESPACE);
			if (tempObject != null) {
				extendedEvent = (DynEventPlugin) tempObject;
				tempEvent = (Event) extendedEvent.getExtendedSBase();
				System.out.println("Extended event id = " + tempEvent.getId()
						+ ", applyToAll = " + extendedEvent.getApplyToAll());

				dynElementList = extendedEvent.getListOfDynElements();
				System.out.println("Nb DynElements = " + dynElementList.size());

				for (int j = 0; j < dynElementList.size(); j++) {
					System.out.println("DynElement idRef = "
							+ dynElementList.get(j).getIdRef()
							+ ", metaIdRef = "
							+ dynElementList.get(j).getMetaId());
				}
			}
		}

		DynCompartmentPlugin extendedCompartment;
		Compartment tempCompartment;
		ListOf<SpatialComponent> spatialComponentList;
		for (int i = 0; i < model.getCompartmentCount(); i++) {
			tempObject = model.getCompartment(i).getExtension(DYN_NAMESPACE);
			if (tempObject != null) {
				extendedCompartment = (DynCompartmentPlugin) tempObject;
				tempCompartment = (Compartment) extendedCompartment
						.getExtendedSBase();
				System.out.println("Extended compartment id = "
						+ tempCompartment.getId());

				spatialComponentList = extendedCompartment
						.getListOfSpatialComponents();
				System.out.println("Nb SpatialComponents = "
						+ spatialComponentList.size());

				for (int j = 0; j < spatialComponentList.size(); j++) {
					System.out.println("SpatialComponent spatialIndex = "
							+ spatialComponentList.get(j).getSpatialIndex()
							+ ", variable = "
							+ spatialComponentList.get(j).getVariable());
				}
			}
		}
	}

	/**
	 * Tests whether extended model components can be written out properly
	 */
	@Test
	public void test_L3_dyn_write() throws XMLStreamException {

		// Tests round-trip read/write functionality
		InputStream fileStream = TestL3Dyn.class
				.getResourceAsStream("/org/sbml/jsbml/xml/test/data/dyn/dynUseCase.xml");
		SBMLDocument doc = new SBMLReader().readSBMLFromStream(fileStream);
		new SBMLWriter().writeSBMLToString(doc);

		// Add more tests for writing different elements when CBO support is
		// available
	}

	public static void main(String[] args) throws XMLStreamException {
		TestL3Dyn tests= new TestL3Dyn();
		tests.test_L3_dyn_read();
		tests.test_L3_dyn_write();
	}

}

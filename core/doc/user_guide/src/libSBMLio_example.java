/** @param args the path to a valid SBML file. */
public static void main(String[] args) {
    try {
        // Load libSBML:
        System.loadLibrary("sbmlj");
        // Extra check to be sure we have access to libSBML:
        Class.forName("org.sbml.libsbml.libsbml");

        // Read SBML file using libSBML and convert it to JSBML:
        LibSBMLReader reader = new LibSBMLReader();
        SBMLDocument doc = reader.convertSBMLDocument(args[0]);

        // Run some application:
        new JSBMLvisualizer(doc);
    } catch (Throwable e) {
        e.printStackTrace();
    }
}

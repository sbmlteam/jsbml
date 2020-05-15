package org.sbml.jsbml.ext.fbc;

import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLStreamException;
import org.sbml.jsbml.AnnotationElement;
import org.sbml.jsbml.xml.XMLAttributes;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.XMLTriple;
import org.sbml.jsbml.xml.parsers.AnnotationReader;
import org.sbml.jsbml.xml.parsers.AnnotationWriter;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.fbc.FBCSBasePlugin;

/**
 * A KeyValuePair will Read/Write the annotation to XMLNode and vice versa
 * 
 * @author  Mahdi Sadeghi
 * @author  Andreas Drager
 * @author  Nikko Rodrigue
 */

public class KeyValuePair  extends AnnotationElement implements AnnotationReader, AnnotationWriter{
  
  /**
   * 
   */
  private static final long serialVersionUID = 3301110147172374841L;
  /**
   * The Part concerning the value
   * From the given annotation
   * 
   */
  private String value;
  /**
   * The Part concerning the key
   * From the given annotation
   */
  private String key;
  /**
   * The Uniform Resource Identifier
   * which is an optional attribute of this class 
   * according to 2020 Harmony Proposal
   */
  private static String URI;
  
  /**
   * contains all the remaining annotation not mapped to Objects.
   * 
   */
  private XMLNode nonRDFannotation;
  
  public KeyValuePair() {
    // TODO Auto-generated constructor stub
  }

  /**
   * Returns the value of {@link #value}.
   *
   * @return the value of {@link #value}.
   */
  public String getVlaue() {
    //TODO: if variable is boolean, create an additional "isVar"
    //TODO: return primitive data type if possible (e.g., int instead of Integer)
    if (isSetValue()) {
      return value;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(FBCConstants.value, this);
  }

  /**
   * Returns whether {@link #value} is set.
   *
   * @return whether {@link #value} is set.
   */
  public boolean isSetValue() {
    return this.value != null;
  }


  /**
   * Sets the value of value
   *
   * @param value the value of value to be set.
   */
  public void setValue(String value) {
    String oldValue = this.value;
    this.value = value;
    firePropertyChange(FBCConstants.value, oldValue, this.value);
  }


  /**
   * Unsets the variable value.
   *
   * @return {@code true} if value was set before, otherwise {@code false}.
   */
  public boolean unsetValue() {
    if (isSetValue()) {
      String oldField = this.value;
      this.value = null;
      firePropertyChange(FBCConstants.value, oldField, this.value);
      return true;
    }
    return false;
  }
  
  
  /**
   * Returns the value of {@link #key}.
   *
   * @return the value of {@link #key}.
   */
  public String getKey() {
    //TODO: if variable is boolean, create an additional "isVar"
    //TODO: return primitive data type if possible (e.g., int instead of Integer)
    if (isSetKey()) {
      return key;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(FBCConstants.key, this);
  }


  /**
   * Returns whether {@link #key} is set.
   *
   * @return whether {@link #key} is set.
   */
  public boolean isSetKey() {
    return this.key != null;
  }


  /**
   * Sets the value of key
   *
   * @param key the value of key to be set.
   */
  public void setKey(String key) {
    String oldKey = this.key;
    this.key = key;
    firePropertyChange(FBCConstants.key, oldKey, this.key);
  }


  /**
   * Un_sets the variable key.
   *
   * @return {@code true} if key was set before, otherwise {@code false}.
   */
  public boolean unsetKey() {
    if (isSetKey()) {
      String oldKey = this.key;
      this.key = null;
      firePropertyChange(FBCConstants.key, oldKey, this.key);
      return true;
    }
    return false;
  }

  /**
   * This Part is reading the annotation to XMLNODE
   * @param annotation
   */
  public KeyValuePair(XMLNode annotation) {
    this();
    nonRDFannotation = annotation;
  }
  
  public KeyValuePair(String key) throws XMLStreamException {
    // parse the String as an XMLNode
    this(XMLNode.convertStringToXMLNode(key));
  }

  /**
   * Extracting the Key from Annotation in a top level element with the right namespace
   * 
   * @param annotation.
   */
  
  public Object ReadKeyFromAnnotation(XMLNode annotation, Object object) {
    
    Object Anot = null;
    if (annotation.getName().equals("Key")) {      
      
      try {
        Anot = annotation.toXMLString();
        setKey((String) Anot);
      } 
      catch (XMLStreamException e) {
        e.printStackTrace();
      }
    }
    return Anot;
  }
  /**
   * Extracting the Value from Annotation in a top level element with the right namespace
   * 
   * @param annotation.
   */
  public Object ReadValueFromAnnotation(XMLNode annotation, Object object) {
    
    Object Anot = null;
    if (annotation.getName().equals("Value")) {      
      try {
        Anot = annotation.toXMLString();
        setValue((String) Anot);
      } 
      catch (XMLStreamException e) {
        e.printStackTrace();
      }
    }
    return Anot;
  }
  
  
  @Override
  public TreeNode getChildAt(int childIndex) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int getChildCount() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean getAllowsChildren() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public TreeNode clone() {
    // TODO Auto-generated method stub
    return new KeyValuePair();

  }

  @Override
  public XMLNode writeAnnotation(SBase contextObject, XMLNode xmlNode) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  /**
   * Appends some 'KeyValuePair' to the non RDF annotation {@link XMLNode} of this object.
   * @param annotation some non RDF annotations.
   */
  public void processAnnotation(SBase contextObject) {
    //Following I will read the object and load it to the XML node
    //Returns the Object name existing in this package
    //And gets element name which is in the correct namespace
    Object objectName = contextObject.getElementName(); 
    XMLNode annotationToAppend = ((SBase) objectName).getAnnotation().getNonRDFannotation();
    XMLNode oldNonRDFAnnotation = null;
    if (nonRDFannotation == null) {
      // check if the annotation contain an annotation in top level element or not
      // one needs to check if writing "KeyValuePair" reads the corresponding info from the object
      // or should we add each of the Keys and Values individually to the node 
      if (!annotationToAppend.getName().equals("KeyValuePair")) {
        XMLNode annotationXMLNode = new XMLNode(new XMLTriple("KeyValuePair", null, null), new XMLAttributes());
        annotationXMLNode.addChild(new XMLNode("\n  "));
        annotationXMLNode.addChild(annotationToAppend);
        annotationToAppend = annotationXMLNode;
        annotationToAppend.setParent(this);
      }

      nonRDFannotation = annotationToAppend;
    } else {
      oldNonRDFAnnotation = nonRDFannotation.clone();
    //Now we need to extract the key value pairs from annotationToAppend
      if (annotationToAppend.getName().equals("KeyValuePair")) {
        for (int i = 0; i < annotationToAppend.getChildCount(); i++) {
          XMLNode child = annotationToAppend.getChildAt(i);
          nonRDFannotation.addChild(child);
        }
     // if found such an element, create the FBCSBasePlugin and add it to the SBase.
      FBCSBasePlugin plugin = (FBCSBasePlugin) ((SBase) annotationToAppend).getPlugin(FBCConstants.shortLabel);
      //Now we will extract the key value pairs and add them to the FBCSBasePlugin
      plugin.setListOfKeyValuePairs();
      
      } else {
        nonRDFannotation.addChild(annotationToAppend);
      }
      //      //Now we need to extract the key value pairs from annotationToAppend
      //      ReadKeyFromAnnotation(annotationToAppend, matchObject);
      //      ReadValueFromAnnotation(annotationToAppend, matchObject);

      //Now we need to remove the found elements from namespace
      int nodeIndex = annotationToAppend.getParent().getIndex(annotationToAppend);
      if (nodeIndex > 0)
      {
        XMLNode precedingChild = (XMLNode) annotationToAppend.getParent().getChildAt(nodeIndex - 1);

        if (precedingChild.isText() && precedingChild.getCharacters().trim().length() == 0)
        {
          ((XMLNode) annotationToAppend.getParent()).removeChild(nodeIndex - 1);
        }
      }
    
    
  }
  
  
}
  
}



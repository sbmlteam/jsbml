package org.sbml.jsbml.ext.fbc;

import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.AnnotationElement;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.History;
import org.sbml.jsbml.xml.XMLAttributes;
import org.sbml.jsbml.xml.XMLNamespaces;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.XMLTriple;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * A KeyValuePair will Read/Write the annotation to XMLNode and vice versa
 * 
 * @author  Mahdi Sadeghi
 * @author  Andreas Drager
 * @author  Nikko Rodrigue
 */

public class KeyValuePair  extends AnnotationElement{
  
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
   * Unsets the variable key.
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
   * Appends some 'annotation' to the non RDF annotation {@link XMLNode} of this object.
   * 
   * @param annotation some non RDF annotations.
   * @throws XMLStreamException
   */
  public void appendNonRDFAnnotation(String annotation) throws XMLStreamException {
    appendNonRDFAnnotation(XMLNode.convertStringToXMLNode(StringTools.toXMLAnnotationString(annotation)));
  }
  
  /**
   * @param annotationToAppend
   */
  public void appendNonRDFAnnotation(XMLNode annotationToAppend) {
    XMLNode oldNonRDFAnnotation = null;
    if (nonRDFannotation == null) {
      // check if the annotation contain an annotation top level element or not
      if (!annotationToAppend.getName().equals("annotation")) {
        XMLNode annotationXMLNode = new XMLNode(new XMLTriple("annotation", null, null), new XMLAttributes());
        annotationXMLNode.addChild(new XMLNode("\n  "));
        annotationXMLNode.addChild(annotationToAppend);
        annotationToAppend = annotationXMLNode;
        annotationToAppend.setParent(this);
      }

      nonRDFannotation = annotationToAppend;
    } else {
      oldNonRDFAnnotation = nonRDFannotation.clone();

      if (annotationToAppend.getName().equals("annotation")) {
        for (int i = 0; i < annotationToAppend.getChildCount(); i++) {
          XMLNode child = annotationToAppend.getChildAt(i);
          nonRDFannotation.addChild(child);
        }
      } else {
        nonRDFannotation.addChild(annotationToAppend);
      }
    }

    firePropertyChange(TreeNodeChangeEvent.nonRDFAnnotation,
      oldNonRDFAnnotation, nonRDFannotation);
  }
  
  
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
  
}




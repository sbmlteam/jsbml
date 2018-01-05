/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.xml.xstream.converter;

import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.util.Detail;
import org.sbml.jsbml.util.Message;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.path.PathTrackingReader;

/**
 * Converts an {@link SBMLError} from the XML produced by the
 * <a href="http://sbml.org/Facilities/Validator">sbml.org online validator</a>.
 * 
 * <p>The XML format is detailed on the <a href="http://sbml.org/Facilities/Documentation/Validator_Web_API">Validator_Web_API</a>
 * page.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class SBMLErrorConverter implements Converter {

  /**
   * 
   */
  String elementName = "package";

  /**
   * 
   */
  public SBMLErrorConverter() {
    super();
  }

  /* (non-Javadoc)
   * @see com.thoughtworks.xstream.converters.ConverterMatcher#canConvert(java.lang.Class)
   */
  @Override
  public boolean canConvert(@SuppressWarnings("rawtypes") Class arg0) {
    return arg0.equals(SBMLError.class);
  }

  /* (non-Javadoc)
   * @see com.thoughtworks.xstream.converters.Converter#marshal(java.lang.Object, com.thoughtworks.xstream.io.HierarchicalStreamWriter, com.thoughtworks.xstream.converters.MarshallingContext)
   */
  @Override
  public void marshal(Object currentObject, HierarchicalStreamWriter writer,
    MarshallingContext context) {

    // Implement ?? not really needed at the moment
    /*    Message message = (Message) currentObject;

    writer.startNode(elementName);
    writer.setValue(message.getMessage());
    writer.addAttribute("lang", message.getLang());
    writer.endNode();
     */
  }

  /* (non-Javadoc)
   * @see com.thoughtworks.xstream.converters.Converter#unmarshal(com.thoughtworks.xstream.io.HierarchicalStreamReader, com.thoughtworks.xstream.converters.UnmarshallingContext)
   */
  @Override
  public Object unmarshal(HierarchicalStreamReader reader,
    UnmarshallingContext context) {

    SBMLError sbmlError = new SBMLError();
    PathTrackingReader ptr = (PathTrackingReader) reader;

    // boolean used to know if we recognized an element and if we did a moveup after the movedown
    boolean movedUp = true;
    
    sbmlError.setCategory(ptr.getAttribute("category"));
    sbmlError.setSeverity(ptr.getAttribute("severity"));
    sbmlError.setCode(Integer.parseInt(ptr.getAttribute("code")));

    // Testing if each element is present before extracting the information
    
    // location element
    if (ptr.hasMoreChildren()) { 
      ptr.moveDown(); // location element
      movedUp = false;
      
      if (ptr.getNodeName().equals("location")) {
        sbmlError.setLine(Integer.parseInt(ptr.getAttribute("line")));
        sbmlError.setColumn(Integer.parseInt(ptr.getAttribute("column")));

        ptr.moveUp();
        movedUp = true;
      }
    }
    
    // message element
    if (ptr.hasMoreChildren() || !movedUp) {
      if (movedUp) { // going down only if we did call moveUp on the previous block
        ptr.moveDown();
        movedUp = false;
      }

      if (ptr.getNodeName().equals("message")) {

        Message message = new Message();
        message.setLang(ptr.getAttribute("lang"));
        String messageContent = ptr.getValue().trim();
        message.setMessage(messageContent);
        sbmlError.setMessage(message);

        ptr.moveUp();
        movedUp = true;
      }
    }

    // package element
    if (ptr.hasMoreChildren() || !movedUp) { 
      if (movedUp) {
        ptr.moveDown();
        movedUp = false;
      }

      if (ptr.getNodeName().equals("package")) {
        sbmlError.setPackage(ptr.getAttribute("code"));
        ptr.moveUp();
        movedUp = true;
      }
    }
    
    // shortMessage element
    if (ptr.hasMoreChildren() || !movedUp) { 
      if (movedUp) {
        ptr.moveDown();
        movedUp = false;
      }

      if (ptr.getNodeName().equals("shortmessage")) {

        Message shortmessage = new Message();
        shortmessage.setLang(ptr.getAttribute("lang"));
        shortmessage.setMessage(ptr.getValue());
        sbmlError.setShortMessage(shortmessage);

        ptr.moveUp();
        movedUp = true;
      }
    }
    
    // detail element
    if (ptr.hasMoreChildren() || !movedUp) { 
      if (movedUp) {    
        ptr.moveDown();
        movedUp = false;
      }

      if (ptr.getNodeName().equals("detail")) {
        Detail details = new Detail();
        details.setSeverity(Integer.parseInt(ptr.getAttribute("severity")));
        details.setCategory(Integer.parseInt(ptr.getAttribute("category")));
        sbmlError.setDetail(details);

        ptr.moveUp();
        movedUp = true;
      }
    }    

    // excerpt element
    if (ptr.hasMoreChildren() || !movedUp) { 
      if (movedUp) {    
        ptr.moveDown(); // 
        movedUp = false;
      }

      if (ptr.getNodeName().equals("excerpt")) {
        sbmlError.setExcerpt(ptr.getValue()); 

        ptr.moveUp();
        movedUp = true;
      }
    }
    
    return sbmlError;
  }

}

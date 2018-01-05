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

import org.sbml.jsbml.util.Message;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * @author Nicolas Rodriguez
 * @since 0.8
 */
public class MessageConverter implements Converter {

  /**
   * 
   */
  String elementName = "message";

  /**
   * @param elementName
   */
  public MessageConverter(String elementName) {
    super();
    this.elementName = elementName;
  }

  /* (non-Javadoc)
   * @see com.thoughtworks.xstream.converters.ConverterMatcher#canConvert(java.lang.Class)
   */
  @Override
  public boolean canConvert(@SuppressWarnings("rawtypes") Class arg0) {
    return arg0.equals(Message.class);
  }

  /* (non-Javadoc)
   * @see com.thoughtworks.xstream.converters.Converter#marshal(java.lang.Object, com.thoughtworks.xstream.io.HierarchicalStreamWriter, com.thoughtworks.xstream.converters.MarshallingContext)
   */
  @Override
  public void marshal(Object currentObject, HierarchicalStreamWriter writer,
    MarshallingContext context) {
    Message message = (Message) currentObject;

    writer.startNode(elementName);
    writer.setValue(message.getMessage());
    writer.addAttribute("lang", message.getLang());
    writer.endNode();
  }

  /* (non-Javadoc)
   * @see com.thoughtworks.xstream.converters.Converter#unmarshal(com.thoughtworks.xstream.io.HierarchicalStreamReader, com.thoughtworks.xstream.converters.UnmarshallingContext)
   */
  @Override
  public Object unmarshal(HierarchicalStreamReader reader,
    UnmarshallingContext context) {
    Message message = new Message();

    // logger.debug("MessageConverter: nodeName = " +
    // reader.getNodeName());

    message.setLang(reader.getAttribute("lang"));

    // reader.moveDown();
    String messageContent = reader.getValue();

    // logger.debug("MessageConverter: content = " + messageContent);

    message.setMessage(messageContent);
    // reader.moveUp();

    return message;
  }

}

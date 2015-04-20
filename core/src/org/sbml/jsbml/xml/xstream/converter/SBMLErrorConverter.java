/*
 * $Id: SBMLErrorConverter.java 2109 2015-01-05 04:50:45Z andreas-draeger $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/core/src/org/sbml/jsbml/xml/xstream/converter/SBMLErrorConverter.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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

/**
 * Converts an {@link SBMLError} from the XML produced by the
 * <a href="http://sbml.org/Facilities/Validator">sbml.org online validator</a>.
 * 
 * <p>The XML format is detailed on the <a href="http://sbml.org/Facilities/Documentation/Validator_Web_API">Validator_Web_API</a>
 * page.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 * @version $Rev: 2109 $
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

    sbmlError.setCategory(reader.getAttribute("category"));
    sbmlError.setSeverity(reader.getAttribute("severity"));
    sbmlError.setCode(Integer.parseInt(reader.getAttribute("code")));

    reader.moveDown(); // location element
    sbmlError.setLine(Integer.parseInt(reader.getAttribute("line")));
    sbmlError.setColumn(Integer.parseInt(reader.getAttribute("column")));

    reader.moveUp();
    reader.moveDown(); // message element

    Message message = new Message();
    message.setLang(reader.getAttribute("lang"));
    String messageContent = reader.getValue().trim();
    message.setMessage(messageContent);
    sbmlError.setMessage(message);

    reader.moveUp();
    reader.moveDown(); // package element

    if (reader.getNodeName().equals("package")) {
      // TODO - we could make the code in this class more robust by testing each element name before extracting the infos
      // not really necessary at this point as the XML sent from sbml.org is always complete.
      sbmlError.setPackage(reader.getAttribute("code"));
      reader.moveUp();
    }

    reader.moveDown(); // shortMessage
    Message shortmessage = new Message();
    shortmessage.setLang(reader.getAttribute("lang"));
    shortmessage.setMessage(reader.getValue());
    sbmlError.setShortMessage(shortmessage);

    reader.moveUp();
    reader.moveDown(); // detail
    Detail details = new Detail();
    details.setSeverity(Integer.parseInt(reader.getAttribute("severity")));
    details.setCategory(Integer.parseInt(reader.getAttribute("category")));
    sbmlError.setDetail(details);

    reader.moveUp();
    reader.moveDown(); // excerpt
    sbmlError.setExcerpt(reader.getValue());

    reader.moveUp();

    return sbmlError;
  }

}

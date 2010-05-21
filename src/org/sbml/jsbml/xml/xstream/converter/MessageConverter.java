package org.sbml.jsbml.xml.xstream.converter;

import org.sbml.jsbml.util.Message;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class MessageConverter implements Converter {

	@Override
	public void marshal(Object currentObject, HierarchicalStreamWriter writer,
			MarshallingContext context) 
	{
		Message message = (Message) currentObject;
	
		writer.startNode("message");
		writer.setValue(message.getMessage());
		writer.addAttribute("lang", message.getLang());
		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) 
	{
		Message message = new Message();

		// System.out.println("MessageConverter : nodeName = " + reader.getNodeName());
		
		message.setLang(reader.getAttribute("lang"));

		// reader.moveDown();
		String messageContent = reader.getValue();
		
		// System.out.println("MessageConverter : content = " + messageContent);
		
		message.setMessage(messageContent);
		// reader.moveUp();
		
		return message;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean canConvert(Class arg0) {
		return arg0.equals(Message.class);
	}

	
}

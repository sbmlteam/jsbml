package org.sbml.jsbml.util;

import java.io.OutputStream;
import java.io.Writer;

import org.w3c.dom.Node;

/**
 * 
 * A writer for XML DOM nodes.
 * 
 * @author Marco Donizelli
 * @since 1.0
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public interface NodeWriter {

	/**
	 * 
	 * Writes an XML DOM node to a byte stream.
	 * 
	 * @param node
	 *            The <code>org.w3c.dom.Node</code> instance representing the
	 *            XML DOM node to be written to <code>byteStream</code>.
	 * @param byteStream
	 *            The byte stream where <code>node</code> is to be written.
	 * @param indent
	 *            Flag to indicate whether the output should be indented or not.
	 * 
	 */
	public void write(Node node, OutputStream byteStream, boolean indent);

	/**
	 * 
	 * Writes an XML DOM node to a character stream.
	 * 
	 * @param node
	 *            The <code>org.w3c.dom.Node</code> instance representing the
	 *            XML DOM node to be written to <code>characterStream</code>.
	 * @param characterStream
	 *            The character stream where <code>node</code> is to be written.
	 * @param indent
	 *            Flag to indicate whether the output should be indented or not.
	 * 
	 */
	public void write(Node node, Writer characterStream, boolean indent);
}

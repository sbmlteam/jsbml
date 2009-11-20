package org.sbml.jsbml.element;

import java.util.HashMap;
import java.util.List;

/**
 * A ModelAnnotation is an Annotation for Model SBase elements. In addition to the instance variable
 * of an Annotation, it contains an HistoryModel object
 * @author compneur
 *
 */
public class ModelAnnotation extends Annotation {

	/**
	 * The ModelHistory which represents the history section of a RDF annotation
	 */
	private ModelHistory modelHistory;

	/**
	 * 
	 */
	public ModelAnnotation(){
		super();
	}
	
	/**
	 * 
	 * @param annotation
	 * @param cvTerms
	 */
	public ModelAnnotation(String annotation, List<CVTerm> cvTerms){
		super(annotation, cvTerms);
	}
	
	/**
	 *
	 * @param annotation
	 */
	public ModelAnnotation(String annotation){
		super(annotation);
	}
	
	/**
	 * 
	 * @param cvTerms
	 */
	public ModelAnnotation(List<CVTerm> cvTerms){
		super(cvTerms);
	}
	
	/**
	 * 
	 * @param annotation
	 * @param cvTerms
	 * @param history
	 */
	public ModelAnnotation(String annotation, List<CVTerm> cvTerms, ModelHistory history){
		super(annotation, cvTerms);
		this.modelHistory = history;
	}
	
	/**
	 * 
	 * @param history
	 */
	public ModelAnnotation(ModelHistory history){
		super();
		this.modelHistory = history;
	}
	
	/**
	 * 
	 * @param attributes
	 */
	public ModelAnnotation(HashMap<String, String> attributes){
		super(attributes);
	}
	
	/**
	 * changes the modelHistory variable
	 * @param modelHistory
	 */
	public void setModelHistory(ModelHistory modelHistory) {
		this.modelHistory = modelHistory;
	}

	/**
	 * 
	 * @return the modelHistory of the ModelAnnotation
	 */
	public ModelHistory getModelHistory() {
		return modelHistory;
	}
	
	/**
	 * check if the modelHistory is initialised
	 * @return true if the modelHistory is initialised
	 */
	public boolean isSetModelHistory() {
		return modelHistory != null;
	}
	
	/**
	 * check if the ModelAnnotation is initialised. 
	 * The modelHistory variable must be initialised
	 * @return true if the modelHistory is initialised
	 */
	public boolean isSetAnnotation(){
		if (!super.isSetAnnotation() && !isSetModelHistory()){
			return false;
		}
		return true;
	}
	
	/**
	 * writes the RDF annotation element in 'buffer'
	 */
	protected void RDFAnnotationToXML(String indent, StringBuffer buffer, SBase parentElement){

		super.beginRDFAnnotationElement(indent, buffer, parentElement);
		modelHistoryToXML(indent + "    ", buffer);
		super.createCVTermsElements(indent + "    ", buffer);
		super.endRDFAnnotationElement(indent, buffer, parentElement);
	}
		
	/**
	 * writes the history section of the RDF annotation in 'buffer'
	 * @param indent
	 * @param buffer
	 */
	private void modelHistoryToXML(String indent, StringBuffer buffer){
		if (isSetModelHistory()){
			getModelHistory().toXML(indent, buffer);
		}
	}
	
}

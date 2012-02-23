package org.sbml.jsbml.ext.multi;

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * 
 * <p/>The element SpeciesType, which is part of SBML Level 2 Version 4 specification, is not part
 * of SBML Level 3 Version 1 Core any more. Instead, it will be defined in the multi package. The
 * SpeciesType element carries not only the basic attributes which it had in SBML Level 2 Version 4
 * (metaid, id, name), but is also extended for the needs of describing multi-component entities
 * with the attribute bindingSite and for the needs of multistate entities by linking it to a list of
 * StateFeatures
 * <p/>A species type can be used to describe a component of a supra-macromolecular assembly,
 * but also a domain of a macromolecule. Such a domain can be a portion of the macromolecule,
 * a non-connex set of atoms forming a functional domain, or just a conceptual construct suiting
 * the needs of the modeler. The type of component can be specified by referring terms from the
 * subbranch functional entity of the <a href="http://biomodels.net/sbo/">Systems Biology Ontology</a>
 * through the optional sboTerm attribute. The following table provides typical examples of
 * component or domains (the list is absolutely not complete).
 * <pre>
 *   | SBO identifier  |  definition
 *  ----------------------------------- 
 *   | SBO:0000242     |  channel
 *   | SBO:0000244     |  receptor
 *   | SBO:0000284     |  transporter
 *   | SBO:0000280     |  ligand
 *   | SBO:0000493     |  functional domain
 *   | SBO:0000494     |  binding site
 *   | SBO:0000495     |  catalytic site
 *   | SBO:0000496     |  transmembrane domain
 * </pre>
 * 
 * @author rodrigue
 *
 */
@SuppressWarnings("deprecation")
public class SpeciesType extends org.sbml.jsbml.SpeciesType  implements UniqueNamedSBase {

	/**
	 * 
	 */
	ListOf<StateFeature> listOfStateFeatures;
	
	/**
	 * 
	 */
	Boolean bindingSite;

	public SpeciesType() {
		super();
		initDefaults();
	}
	
	public boolean isIdMandatory() {
		return false;
	}

	@Override
	public SpeciesType clone() {
		// TODO
		return null;
	}

	/**
	 * Returns the listOfStateFeatures
	 * 
	 * @return the listOfStateFeatures
	 */
	public ListOf<StateFeature> getListOfStateFeatures() {
		if (listOfStateFeatures == null) {
			listOfStateFeatures = new ListOf<StateFeature>();
			listOfStateFeatures.addNamespace(MultiConstant.namespaceURI);
			this.registerChild(listOfStateFeatures);
			listOfStateFeatures.setSBaseListType(ListOf.Type.other);
		}
		
		return listOfStateFeatures;
	}

	/**
	 * Adds a StateFeature.
	 * 
	 * @param stateFeature the stateFeature to add
	 */
	public void addStateFeature(StateFeature stateFeature) {
		getListOfStateFeatures().add(stateFeature);
	}
	
	/**
	 * Creates a new {@link StateFeature} inside this {@link SpeciesType} and returns it.
	 * <p>
	 * 
	 * @return the {@link StateFeature} object created
	 *         <p>
	 * @see #addStateFeature(StateFeature r)
	 */
	public StateFeature createStateFeature() {
		return createStateFeature(null);
	}

	/**
	 * Creates a new {@link StateFeature} inside this {@link SpeciesType} and returns it.
	 * 
	 * @param id
	 *        the id of the new element to create
	 * @return the {@link StateFeature} object created
	 */
	public StateFeature createStateFeature(String id) {
		StateFeature stateFeature = new StateFeature();
		stateFeature.setId(id);
		addStateFeature(stateFeature);

		return stateFeature;
	}

	/**
	 * Gets the ith {@link StateFeature}.
	 * 
	 * @param i
	 * 
	 * @return the ith {@link StateFeature}
	 * @throws IndexOutOfBoundsException if the index is invalid.
	 */
	public StateFeature getStateFeature(int i) {
		return getListOfStateFeatures().get(i);
	}

	/**
	 * Gets the {@link StateFeature} that has the given id. 
	 * 
	 * @param id
	 * @return the {@link StateFeature} that has the given id or null if
	 * no {@link StateFeature} are found that match <code>id</code>.
	 */
	public StateFeature getStateFeature(String id){
		if(isSetListOfStateFeatures()) {
			return listOfStateFeatures.firstHit(new NameFilter(id));	    
		} 
		return null;
	}

	/**
	 * Returns true if the listOfStateFeature is set.
	 * 
	 * @return true if the listOfStateFeature is set.
	 */
	public boolean isSetListOfStateFeatures() {
		if ((listOfStateFeatures == null) || listOfStateFeatures.isEmpty()) {
			return false;			
		}		
		return true;
	}
	
	/**
	 * Sets the listOfStateFeatures to null
	 * 
	 * @return true is successful
	 */
	public boolean unsetListOfStateFeatures(){
		if(isSetListOfStateFeatures()) {
			// unregister the ids if needed.			  
			this.listOfStateFeatures.fireNodeRemovedEvent();
			this.listOfStateFeatures = null;
			return true;
		}
		return false;
	}


	/**
	 * @return the bindingSite
	 */
	public boolean isBindingSite() {
		return bindingSite;
	}

	/**
	 * 
	 */
	public boolean isSetBindingSite() {
		return bindingSite != null;
	}
	
	/**
	 * @param bindingSite the bindingSite to set
	 */
	public void setBindingSite(boolean bindingSite) {
		this.bindingSite = bindingSite;
	}

	/**
	 * 
	 */
	public void initDefaults() {
		addNamespace(MultiConstant.namespaceURI);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
	 */
	@Override
	public TreeNode getChildAt(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException(index + " < 0");
		}

		int count = super.getChildCount(), pos = 0;
		if (index < count) {
			return super.getChildAt(index);
		} else {
			index -= count;
		}
		if (isSetListOfStateFeatures()) {
			if (pos == index) {
				return getListOfStateFeatures();
			}
			pos++;
		}

		throw new IndexOutOfBoundsException(String.format("Index %d >= %d",
				index, +((int) Math.min(pos, 0))));
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildCount()
	 */
	@Override
	public int getChildCount() {
		int count = super.getChildCount();

		if (isSetListOfStateFeatures()) {
			count++;
		}

		return count;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) 
	{
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

		if (!isAttributeRead) {
			isAttributeRead = true;

			if (attributeName.equals(MultiConstant.bindingSite)){
				setBindingSite(StringTools.parseSBMLBoolean(value));
			} else {
				isAttributeRead = false;
			}
		}	  

		return isAttributeRead;

	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (isSetId()) {
			attributes.remove("id");
			attributes.put(MultiConstant.shortLabel+ ":id", getId());
		}
		if (isSetName()) {
			attributes.remove("name");
			attributes.put(MultiConstant.shortLabel+ ":name", getName());
		}
		if (isSetBindingSite()) {
			attributes.put(MultiConstant.shortLabel+ ":"+MultiConstant.bindingSite, Boolean.toString(isBindingSite()));
		}


		return attributes;
	}

	// TODO : equals, hashCode, toString, more constructors, ...
}

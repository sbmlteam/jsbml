package org.sbml.jsbml.multiTest;

import org.sbml.jsbml.element.AbstractNamedSBase;
import org.sbml.jsbml.element.SBase;

public class InitialSpeciesInstance extends AbstractNamedSBase {
	
	private double initialProportion;
	
	private Selector selector;
	
	private String selectorID;
	
	public InitialSpeciesInstance(){
		this.setSelector(null);
		this.setSelectorID(null);
		this.setInitialProportion(0);
	}
	
	public InitialSpeciesInstance(InitialSpeciesInstance in){
		this.setSelector(in.getSelector());
		this.setSelectorID(in.getSelectorID());
		this.setInitialProportion(in.getInitialProportion());
	}
	
	@Override
	public SBase clone() {
		return new InitialSpeciesInstance(this);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setInitialProportion(double initialProportion) {
		this.initialProportion = initialProportion;
	}

	public double getInitialProportion() {
		return initialProportion;
	}

	public void setSelector(Selector selector) {
		this.selector = selector;
	}

	public Selector getSelector() {
		return selector;
	}

	public void setSelectorID(String selectorID) {
		selectorID = selectorID;
	}

	public String getSelectorID() {
		return selectorID;
	}
	
	public boolean isSetSelectorID(){
		return this.selectorID != null;
	}
	
	public boolean isSetSelector(){
		return this.selector != null;
	}
	
	public boolean isSetInitialProportion(){
		return this.initialProportion != 0;
	}
	
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isReadAttribute = super.readAttribute(attributeName, prefix, value);
		
		if (! isReadAttribute){
			if (attributeName.equals("initialProportion")){
				this.initialProportion = Double.parseDouble(value);
				
				return true;
			}
			else if (attributeName.equals("selector")){
				this.selectorID = value;
				
				return true;
			}
		}
		
		return isReadAttribute;
	}

}

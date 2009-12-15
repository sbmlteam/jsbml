package org.sbml.jsbml.multiTest;

import org.sbml.jsbml.element.AbstractNamedSBase;
import org.sbml.jsbml.element.SBase;

public class Selector extends AbstractNamedSBase{

	public Selector(){
		
	}
	
	public Selector(Selector selector){
		
	}
	
	@Override
	public SBase clone() {
		return new Selector(this);
	}

}

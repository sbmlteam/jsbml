package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.SBase;

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

package org.sbml.jsbml.ext.comp;

import org.sbml.jsbml.Model;

public class ModelDefinition extends Model {

	public ModelDefinition() {
		super();
		init();
	}

	public ModelDefinition(int level, int version) {
		super(level, version);
		init();
	}

	public ModelDefinition(Model model) {
		super(model);
		init();
	}

	public ModelDefinition(String id) {
		super(id);
		init();
	}

	public ModelDefinition(String id, int level, int version) {
		super(id, level, version);
		init();
	}

	public void init()
	{
		addNamespace(CompConstant.namespaceURI);
	}
}

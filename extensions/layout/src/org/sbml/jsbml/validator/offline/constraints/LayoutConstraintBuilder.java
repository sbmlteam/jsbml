package org.sbml.jsbml.validator.offline.constraints;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.GraphicalObject;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.TextGlyph;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.validator.offline.SBMLPackage;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.factory.AbstractConstraintBuilder;
import org.sbml.jsbml.validator.offline.factory.ConstraintFactory;

public class LayoutConstraintBuilder extends AbstractConstraintBuilder implements SpecialLayoutErrorCodes{

    @Override
    public AnyConstraint<?> createConstraint(int id) {
	
//	logger.info("Create " + id);
	
	if (id < 0)
	{
	    return this.createSpecialConstraint(id);
	}
	
	
	int firstThree = (id - SBMLPackage.LAYOUT.offset) / 100;
	switch (firstThree) {
	case 203:
	    return createLayoutConstraint(id);

	default:
	    return null;
	}
    }
    
    

    public AnyConstraint<?> createLayoutConstraint(int id) {
	ValidationFunction<Layout> f = null;

	switch (id) {
	case LAYOUT_20315:
	    f = new ValidationFunction<Layout>() {

		@Override
		public boolean check(ValidationContext ctx, Layout l) {

		    return l.isSetDimensions();
		}
	    };
	    break;

	default:
	    break;
	}

	return new ValidationConstraint<Layout>(id, f);
    }
    
    public AnyConstraint<?> createSpecialConstraint(int id)
    {
	switch (id) 
	{
	case ID_VALIDATE_LAYOUT_MODEL:
	    ValidationFunction<Model> f4 = new ValidationFunction<Model>() {

		@Override
		public boolean check(ValidationContext ctx, Model m) {

		    SBasePlugin extension = null;

		    if (ctx.getLevel() == 3 && ctx.getVersion() == 1) {
			extension = m.getExtension(LayoutConstants.namespaceURI_L3V1V1);
		    } else {
			extension = m.getExtension(LayoutConstants.namespaceURI);
		    }

		    if (extension instanceof LayoutModelPlugin) {
			LayoutModelPlugin layoutModel = (LayoutModelPlugin) extension;
			ConstraintFactory factory = ConstraintFactory.getInstance();

			{
			    AnyConstraint<Layout> c = factory.getConstraintsForClass(Layout.class,
				    ctx.getCheckCategories(), ctx.getPackages(), ctx.getLevel(), ctx.getVersion());

			    for (Layout l : layoutModel.getListOfLayouts()) {
				c.check(ctx, l);
			    }
			}

		    }
		    return true;
		}
	    };

	    return new ValidationConstraint<Model>(id, f4);
	case ID_VALIDATE_LAYOUT_TREE:
	    ValidationFunction<Layout> f5 = new ValidationFunction<Layout>() {

		@Override
		public boolean check(ValidationContext ctx, Layout l) {
		    // TODO Auto-generated method stub

		    ConstraintFactory factory = ConstraintFactory.getInstance();

		    {
			AnyConstraint<GraphicalObject> c = factory.getConstraintsForClass(GraphicalObject.class,
				ctx.getCheckCategories(), ctx.getPackages(), ctx.getLevel(), ctx.getVersion());
			
			for (GraphicalObject go : l.getListOfAdditionalGraphicalObjects())
			{
			    c.check(ctx, go);
			}
		    }
		    
		    {
			AnyConstraint<CompartmentGlyph> c = factory.getConstraintsForClass(CompartmentGlyph.class,
				ctx.getCheckCategories(), ctx.getPackages(), ctx.getLevel(), ctx.getVersion());
			
			for (CompartmentGlyph cg : l.getListOfCompartmentGlyphs())
			{
			    c.check(ctx, cg);
			}
		    }
		    
		    {
			AnyConstraint<ReactionGlyph> c = factory.getConstraintsForClass(ReactionGlyph.class,
				ctx.getCheckCategories(), ctx.getPackages(), ctx.getLevel(), ctx.getVersion());
			
			for (ReactionGlyph rg : l.getListOfReactionGlyphs())
			{
			    c.check(ctx, rg);
			}
		    }
		    
		    {
			AnyConstraint<SpeciesGlyph> c = factory.getConstraintsForClass(SpeciesGlyph.class,
				ctx.getCheckCategories(), ctx.getPackages(), ctx.getLevel(), ctx.getVersion());
			
			for (SpeciesGlyph sg : l.getListOfSpeciesGlyphs())
			{
			    c.check(ctx, sg);
			}
		    }
		    
		    {
			AnyConstraint<TextGlyph> c = factory.getConstraintsForClass(TextGlyph.class,
				ctx.getCheckCategories(), ctx.getPackages(), ctx.getLevel(), ctx.getVersion());
			
			for (TextGlyph tg : l.getListOfTextGlyphs())
			{
			    c.check(ctx, tg);
			}
		    }
		    
		    {
			AnyConstraint<TreeNodeChangeListener> c = factory.getConstraintsForClass(TreeNodeChangeListener.class,
				ctx.getCheckCategories(), ctx.getPackages(), ctx.getLevel(), ctx.getVersion());
			
			for (TreeNodeChangeListener t : l.getListOfTreeNodeChangeListeners())
			{
			    c.check(ctx, t);
			}
		    }

		    return true;
		}

	    };

	    return new ValidationConstraint<Layout>(id, f5);

	default:
	    return null;
	}
    }

}

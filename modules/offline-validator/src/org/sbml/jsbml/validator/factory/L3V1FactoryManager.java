/*
 * 
 */

package org.sbml.jsbml.validator.factory;

import java.util.List;

/**
 * 
 * @author Roman Schulte
 * @version $Rev$
 * @since 1.2
 * @date Jun 3, 2016
 */
public class L3V1FactoryManager extends AbstractFactoryManager {

    public void addCoreGeneralModelIds(List<Integer> list) {
	addRangeToList(list, CORE_20204, CORE_20232);
	list.add(CORE_20705);
    }

    public void addCoreGeneralFunctionDefinitionIds(List<Integer> list) {
	list.add(CORE_20301);
	addRangeToList(list, CORE_20303, CORE_20307);
    }

    public void addCoreGeneralCompartmentIds(List<Integer> list) {
	addRangeToList(list, CORE_20507, CORE_20509);
	list.add(CORE_20517);
    }

    public void addCoreGeneralSpeciesIds(List<Integer> list) {
	list.add(CORE_20601);
	addRangeToList(list, CORE_20608, CORE_20611);
	list.add(CORE_20614);
	list.add(CORE_20617);
	list.add(CORE_20623);
    }
    
    public void addLayoutLayoutIds(List<Integer> list){
	addRangeToList(list, LAYOUT_20301, LAYOUT_20317);

    }
    
    public void addLayoutGraphicalObject(List<Integer> list){
	addRangeToList(list, LAYOUT_20401, LAYOUT_20407);
    }
    
    public void addLayoutSBMLDocument(List<Integer> list){
	addRangeToList(list, LAYOUT_20201, LAYOUT_20204);
    }
    
    public void addLayoutCompartmentGlyph(List<Integer> list){
	addRangeToList(list, LAYOUT_20501, LAYOUT_20510);
    }
    
    public void addLayoutSpeciesGlyph(List<Integer> list){
	addRangeToList(list, LAYOUT_20601, LAYOUT_20609);
    }
    
    public void addLayoutReactionGlyph(List<Integer> list){
	addRangeToList(list, LAYOUT_20701, LAYOUT_20712);
    }
    
    public void addLayoutGeneralGlyph(List<Integer> list){
	addRangeToList(list, LAYOUT_20801, LAYOUT_20813);
    }
    
    public void addLayoutTextGlyph(List<Integer> list){
	addRangeToList(list, LAYOUT_20901, LAYOUT_20912);
    }
    
    public void addLayoutSpeciesReferenceGlyph(List<Integer> list){
	addRangeToList(list, LAYOUT_21001, LAYOUT_21012);
    }

    public void addLayoutReferenceGlyph(List<Integer> list){
	addRangeToList(list, LAYOUT_21101, LAYOUT_21112);
    }
    
    public void addLayoutPoint(List<Integer> list){
	addRangeToList(list, LAYOUT_21201, LAYOUT_21204);
    }
    
    public void addLayoutBoundingBox(List<Integer> list){
	addRangeToList(list, LAYOUT_21301, LAYOUT_21305);
    }

    public void addLayoutCurve(List<Integer> list){
	addRangeToList(list, LAYOUT_21401, LAYOUT_21407);
    }
    
    public void addLayoutLineSegement(List<Integer> list){
	addRangeToList(list, LAYOUT_21501, LAYOUT_21504);
    }

    public void addLayoutCubicBezier(List<Integer> list){
	addRangeToList(list, LAYOUT_21601, LAYOUT_21604);
    }
    
    public void addLayoutDimension(List<Integer> list){
	addRangeToList(list, LAYOUT_21701, LAYOUT_21704);
    }
}

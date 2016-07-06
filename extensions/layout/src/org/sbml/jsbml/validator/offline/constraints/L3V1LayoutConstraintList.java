package org.sbml.jsbml.validator.offline.constraints;

import java.util.List;
import java.util.Set;

public final class L3V1LayoutConstraintList extends AbstractConstraintList {

  public static void addGeneralModelIds(Set<Integer> list) {
    list.add(SpecialLayoutErrorCodes.ID_VALIDATE_LAYOUT_MODEL);
  }


  public static void addGeneralLayoutIds(Set<Integer> list) {
    addRangeToList(list, LAYOUT_20301, LAYOUT_20317);
    list.add(SpecialLayoutErrorCodes.ID_VALIDATE_LAYOUT_TREE);
  }


  public static void addGeneralGraphicalObjectIds(Set<Integer> list) {
    addRangeToList(list, LAYOUT_20401, LAYOUT_20407);
  }


  public static void addGeneralSBMLDocument(Set<Integer> list) {
    addRangeToList(list, LAYOUT_20201, LAYOUT_20204);
  }


  public static void addGeneralCompartmentGlyph(Set<Integer> list) {
    addRangeToList(list, LAYOUT_20501, LAYOUT_20510);
  }


  public static void addGeneralSpeciesGlyph(Set<Integer> list) {
    addRangeToList(list, LAYOUT_20601, LAYOUT_20609);
  }


  public static void addGeneralReactionGlyph(Set<Integer> list) {
    addRangeToList(list, LAYOUT_20701, LAYOUT_20712);
  }


  public static void addGeneralGeneralGlyph(Set<Integer> list) {
    addRangeToList(list, LAYOUT_20801, LAYOUT_20813);
  }


  public static void addGeneralTextGlyph(Set<Integer> list) {
    addRangeToList(list, LAYOUT_20901, LAYOUT_20912);
  }


  public static void addGeneralSpeciesReferenceGlyph(Set<Integer> list) {
    addRangeToList(list, LAYOUT_21001, LAYOUT_21012);
  }


  public static void addGeneralReferenceGlyph(Set<Integer> list) {
    addRangeToList(list, LAYOUT_21101, LAYOUT_21112);
  }


  public static void addLayoutGeneralPoint(Set<Integer> list) {
    addRangeToList(list, LAYOUT_21201, LAYOUT_21204);
  }


  public static void addLayoutGeneralBoundingBox(Set<Integer> list) {
    addRangeToList(list, LAYOUT_21301, LAYOUT_21305);
  }


  public static void addLayoutGeneralCurve(Set<Integer> list) {
    addRangeToList(list, LAYOUT_21401, LAYOUT_21407);
  }


  public static void addLayoutGeneralLineSegement(Set<Integer> list) {
    addRangeToList(list, LAYOUT_21501, LAYOUT_21504);
  }


  public static void addLayoutGeneralCubicBezier(Set<Integer> list) {
    addRangeToList(list, LAYOUT_21601, LAYOUT_21604);
  }


  public static void addLayoutGeneralDimension(Set<Integer> list) {
    addRangeToList(list, LAYOUT_21701, LAYOUT_21704);
  }
}

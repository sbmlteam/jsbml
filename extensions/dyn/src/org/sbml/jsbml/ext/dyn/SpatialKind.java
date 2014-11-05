package org.sbml.jsbml.ext.dyn;

/**
 * This is a collection of possible values for the {@code spatialIndex}
 * attribute within a {@link SpatialComponent}. Supported values this type are
 * bound to a Cartesian coordinate system. This has been defined in version 1 of
 * the Dynamic Structures specification.
 * 
 * @author Harold Gomez
 * @since 1.0
 */
public enum SpatialKind {
	/**
	 * Refers to the X coordinate component of an object's position within a
	 * Cartesian coordinate system
	 */
	cartesianX,
	/**
	 * Refers to the Y coordinate component of an object's position within a
	 * Cartesian coordinate system
	 */
	cartesianY,
	/**
	 * Refers to the Z coordinate component of an object's position within a
	 * Cartesian coordinate system
	 */
	cartesianZ,
	/**
	 * Refers to elemental rotation of an object about the X coordinate axis
	 */
	alpha,
	/**
	 * Refers to elemental rotation of an object about the Y coordinate axis
	 */
	beta,
	/**
	 * Refers to elemental rotation of an object about the Z coordinate axis
	 */
	gamma,
	/**
	 * Refers to the X component of the force vector that drives movement
	 */
	F_x,
	/**
	 * Refers to the Y component of the force vector that drives movement
	 */
	F_y,
	/**
	 * Refers to the Z component of the force vector that drives movement
	 */
	F_z,
}

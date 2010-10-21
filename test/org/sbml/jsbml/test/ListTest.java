package org.sbml.jsbml.test;

import java.lang.reflect.TypeVariable;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Species;

public class ListTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ListOf<Species> los = new ListOf<Species>();
		TypeVariable<?> v[] = los.getClass().getTypeParameters();
		for (int i = 0; i < v.length; i++) {
			System.out.println(v[i].getGenericDeclaration());
		}
	}

}

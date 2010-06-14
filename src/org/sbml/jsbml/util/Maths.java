/*
 * $Id:  Math.java 14:26:57 draeger $
 * $URL: Math.java $
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */
package org.sbml.jsbml.util;

/**
 * This class provides several static methods for mathematical operations such
 * as faculty, logarithms and several trigonometric functions, which are not
 * part of standard Java, but necessary to evaluate the content of SBML files.
 * 
 * @author Andreas Dr&auml;ger
 * @author Diedonn&eacute; Mosu Wouamba
 * @author Alexander D&ouml;rr
 * @date 2007-10-29
 */
public class Maths {

	/**
	 * This method computes the arccosh of n
	 * 
	 * @param n
	 * @return
	 */
	public static final double arccosh(double n) {
		return Math.log(n + (Math.sqrt(Math.pow(n, 2) - 1)));
	}

	/**
	 * This method computes the arccot of n
	 * 
	 * @param n
	 * @return
	 */
	public static final double arccot(double n) {
		if (n == 0) {
			throw new ArithmeticException("arccot(0) undefined");
		}
		return Math.atan(1 / n);
	}

	/**
	 * This method computes the arccoth of n
	 * 
	 * @param n
	 * @return
	 */
	public static final double arccoth(double n) {
		if (n == 0) {
			throw new ArithmeticException("arccoth(0) undefined");
		}
		return (Math.log(1 + (1 / n)) - Math.log(1 - (1 / n))) / 2;
	}

	/**
	 * This method computes the arccsc of n
	 * 
	 * @param n
	 * @return
	 */
	public static final double arccsc(double n) {
		double asin = Math.asin(n);
		if (asin == 0) {
			throw new ArithmeticException("arccsc(" + n + ") undefined");
		}
		return 1 / asin;
	}

	/**
	 * This method computes the arccsch of n
	 * 
	 * @param n
	 * @return
	 */
	public static final double arccsch(double n) {
		if (n == 0) {
			throw new ArithmeticException("arccsch(0) undefined");
		}
		return Math.log(1 / n + Math.sqrt(Math.pow(1 / n, 2) + 1));
	}

	/**
	 * This method computes the arcsec of n
	 * 
	 * @param n
	 * @return
	 */
	public static final double arcsec(double n) {
		if (n == 0) {
			throw new ArithmeticException("arcsec(0) undefined");
		}
		return 1 / n;
	}

	/**
	 * This method computes the arcsech of n
	 * 
	 * @param n
	 * @return
	 */
	public static final double arcsech(double n) {
		if (n == 0) {
			throw new ArithmeticException("arcsech(0) undefined");
		}
		return Math.log((1 / n) + (Math.sqrt(Math.pow(1 / n, 2) - 1)));
	}

	/**
	 * This method computes the arcsinh of n
	 * 
	 * @param n
	 * @return
	 */
	public static final double arcsinh(double n) {
		return Math.log(n + Math.sqrt(Math.pow(n, 2) + 1));
	}

	/**
	 * This method computes the arctanh of n
	 * 
	 * @param n
	 * @return
	 */
	public static final double arctanh(double n) {
		return (Math.log(1 + n) - Math.log(1 - n)) / 2;
	}

	/**
	 * This method computes the cot of n
	 * 
	 * @param n
	 * @return
	 */
	public static final double cot(double n) {
		double sin = Math.sin(n);
		if (sin == 0) {
			throw new ArithmeticException("cot(" + n + ") undefined");
		}
		return Math.cos(n) / sin;
	}

	/**
	 * This method computes the coth of n
	 * 
	 * @param n
	 * @return
	 */
	public static final double coth(double n) {
		double sinh = Math.sinh(n);
		if (sinh == 0) {
			throw new ArithmeticException("coth(" + n + ") undefined");
		}
		return Math.cosh(n) / sinh;
	}

	/**
	 * This method computes the csc of n
	 * 
	 * @param n
	 * @return
	 */
	public static final double csc(double n) {
		double sin = Math.sin(n);
		if (sin == 0) {
			throw new ArithmeticException("csc(" + n + ") undefined");
		}
		return 1 / sin;
	}

	/**
	 * This method computes the csch of n
	 * 
	 * @param n
	 * @return
	 */
	public static final double csch(double n) {
		double sinh = Math.sinh(n);
		if (sinh == 0) {
			throw new ArithmeticException("csch(" + n + " undefined");
		}
		return 1 / sinh;
	}

	/**
	 * This method computes the factorial! function.
	 * 
	 * @param n
	 * @return
	 */
	public static final double factorial(double n) {
		if (n < 0) {
			throw new IllegalArgumentException(
					"cannot compute factorial for values < 0");
		}
		if ((n == 0) || (n == 1)) {
			return 1;
		}
		return n * factorial(n - 1);
	}

	/**
	 * This method computes the ln of n
	 * 
	 * @param n
	 * @return
	 */
	public static final double ln(double n) {
		return Math.log(n);
	}

	/**
	 * This method computes the log of n to the base 10
	 * 
	 * @param n
	 * @return
	 */
	public static final double log(double n) {
		return Math.log10(n);
	}

	/**
	 * This method computes the logarithm of a number x to a giving base b.
	 * 
	 * @param number
	 * @param base
	 * @return
	 */
	public static final double log(double number, double base) {
		double denominator = Math.log(base);
		if (denominator == 0) {
			throw new ArithmeticException("log_e(" + base + ") undefined");
		}
		return Math.log(number) / denominator;
	}

	/**
	 * This method computes the rootExponent-th root of the radiant
	 * 
	 * @param radiant
	 * @param rootExponent
	 * @return
	 */
	public static final double root(double radiant, double rootExponent) {
		if (rootExponent != 0) {
			return Math.pow(radiant, 1 / rootExponent);
		}
		throw new ArithmeticException("root exponent must not be zero");
	}

	/**
	 * This method computes the sec of n
	 * 
	 * @param n
	 * @return
	 */
	public static final double sec(double n) {
		if (n == 0) {
			throw new ArithmeticException("sec(" + n + ") undefined");
		}
		return 1 / n;
	}

	/**
	 * This method computes the sech of n
	 * 
	 * @param n
	 * @return
	 */
	public static final double sech(double n) {
		double cosh = Math.cosh(n);
		if (cosh == 0) {
			throw new ArithmeticException("sech(" + n + ") undefined");
		}
		return 1 / cosh;
	}

	/**
	 * Constructor that should not be used; this class provides static methods
	 * only.
	 */
	private Maths() {
	}
}

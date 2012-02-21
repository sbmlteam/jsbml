/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2012 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
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
 * @since 0.8
 * @version $Rev$
 */
public class Maths {

	/**
	 * Avogadro's constant of 6.02214199 &#8901; 10<sup>23</sup>
	 * mol<sup>-1</sup>. The standard deviation of this constant is 36 &#8901;
	 * 10<sup>16</sup> mol<sup>-1</sup>. See D. R. Linde, CRC Handbook of
	 * Chemistry and Physics, 81st ed., CRC Press, Boca Raton, Florida, 2000.
	 */
	public static final double AVOGADRO = 6.02214199 * Math.pow(10, 23);

	/**
	 * Universal gas constant of 8.314472 J &#8901; mol<sup>-1</sup> &#8901;
	 * K<sup>-1</sup> according to D. R. Linde, CRC Handbook of Chemistry and
	 * PHysics, 81st ed., CRC Press, Boca Raton, Florida, 2000.
	 */
	public static final double R = 8.314472;

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
	 * This method computes the arcus-cotangens of a double value.
	 * 
	 * @param x
	 * @return
	 */
  public static final double arccot(double x) {
    if (x == 0) {
      return Math.PI/2;
    }
    return Math.atan(1 / x);
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
	 * This method computes the arccosecant of a double value
	 * 
	 * @param x
	 * @return
	 */
	public static final double arccsc(double x) {
	  if (x == 0) {
      throw new ArithmeticException(String.format("arccsc(%s) undefined",
          StringTools.toString(x)));
    }
    double asin = Math.asin(1d / x);
    return asin;
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
	 * This method computes the arcsecant of a double value
	 * 
	 * @param x
	 * @return
	 */
	public static final double arcsec(double x) {
	  if (x == 0) {
      throw new ArithmeticException(String.format("arccsc(%s) undefined",
          StringTools.toString(x)));
    }
    double acos = Math.acos(1d / x);
    return acos;
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
			throw new ArithmeticException(String.format("cot(%s) undefined",
					StringTools.toString(n)));
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
			throw new ArithmeticException(String.format("coth(%s) undefined",
					StringTools.toString(n)));
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
			throw new ArithmeticException(String.format("csc(%s) undefined",
					StringTools.toString(n)));
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
			throw new ArithmeticException(String.format("csch(%s) undefined",
					StringTools.toString(n)));
		}
		return 1 / sinh;
	}

	/**
	 * This method computes the factorial! function.
	 * 
	 * @param n
	 * @return
	 */
	public static final long factorial(int n) {
		if (n < 0) {
			throw new IllegalArgumentException(String.format(
					"Cannot compute factorial for values %s < 0", StringTools
							.toString(n)));
		}
		if ((n == 0) || (n == 1)) {
			return 1;
		}
		return n * factorial(n - 1);
	}

	// TODO: implement Gamma function for non-integer cases.

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
			throw new ArithmeticException(String.format("log_e(%s) undefined",
					StringTools.toString(base)));
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
	 * This method computes the secant of a double value.
	 * 
	 * @param x
	 * @return
	 */
	public static final double sec(double x) {
		return 1d / Math.cos(x);
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
			throw new ArithmeticException(String.format("sech(%s) undefined",
					StringTools.toString(n)));
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

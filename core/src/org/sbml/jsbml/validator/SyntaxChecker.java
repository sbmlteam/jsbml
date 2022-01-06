/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.validator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.sbml.jsbml.util.StringTools;


/**
 * A collection of methods for checking the validity of SBML identifiers.
 * <p>
 * This utility class provides static methods for checking the syntax of
 * identifiers and other text used in an {@link org.sbml.jsbml.SBMLDocument}.
 * The methods allow callers to verify that {@link String}s such as SBML
 * identifiers and XHTML notes text conform to the SBML specifications.
 * <p>
 * In order to save memory, all patterns in this class are only initialized
 * upon their first use.
 *
 * @author Andreas Dr&auml;ger
 * @author Onur &Ouml;zel
 * @since 1.0
 */
public class SyntaxChecker {

  /**
   *
   */
  private static Pattern chemicalFormulaPattern;

  /**
   * The only instance of this class.
   */
  private static final SyntaxChecker syntaxChecker = new SyntaxChecker();

  /**
   *
   * @return
   */
  private static Pattern initChemicalFormulaPattern() {
    String period[] = new String[7];
    String lanthanide = "La|Ce|Pr|Nd|Pm|Sm|Eu|Gd|Tb|Dy|Ho|Er|Tm|Yb|Lu";
    String actinide = "Ac|Th|Pa|U|Np|Pu|Am|Cm|Bk|Cf|Es|Fm|Md|No|Lr";
    period[0] = "H|He";
    period[1] = "Li|Be|B|C|N|O|F|Ne";
    period[2] = "Na|Mg|Al|Si|P|S|Cl|Ar";
    period[3] = "K|Ca|Sc|Ti|V|Cr|Mn|Fe|Co|Ni|Cu|Zn|Ga|Ge|As|Se|Br|Kr";
    period[4] = "Rb|Sr|Y|Zr|Nb|Mo|Tc|Ru|Rh|Pd|Ag|Cd|In|Sn|Sb|Te|I|Xe";
    period[5] = "Cs|Ba|" + lanthanide + "|Hf|Ta|W|Re|Os|Ir|Pt|Au|Hg|Tl|Pb|Bi|Po|At|Rn";
    period[6] = "Fr|Ra|" + actinide + "Rf|Db|Sg|Bh|Hs|Mt|Ds|Rg|Cn|Uut|Fl|Uup|Lv|Uus|Uuo";

    StringBuilder atoms = new StringBuilder();
    atoms.append(period[0]);
    for (int i = 1; i < period.length; i++) {
      atoms.append('|');
      atoms.append(period[i]);
    }

    String compoundName = "[A-Z][a-z]*";

    String residues = "[A-Z][a-z]*";

    String regex = "((" + atoms.toString() + "|" + residues + ")+\\d*)*|(" + compoundName + ")?";

    return Pattern.compile(regex);
  }

  /**
   *
   * @param chemicalFormula
   * @return
   */
  public static boolean isValidChemicalFormula(String chemicalFormula) {
    if (chemicalFormulaPattern == null) {
      SyntaxChecker.chemicalFormulaPattern = initChemicalFormulaPattern();
    }
    return SyntaxChecker.chemicalFormulaPattern.matcher(chemicalFormula).matches();
  }

  /**
   * Definition of valid e-mail address {@link String}s.
   * <table>
   *   <tr>
   *     <th>Pattern</th><th>Explanation</th>
   *   </tr><tr>
   *     <td>{@code ^}                   </td><td>start of the line</td>
   *   </tr><tr>
   *     <td>{@code [_A-Za-z0-9-]+}      </td><td>must start with string in the bracket [ ], must contains one or more (+)</td>
   *   </tr><tr>
   *     <td>{@code (}                   </td><td>start of group #1</td>
   *   </tr><tr>
   *     <td>{@code \\.[_A-Za-z0-9-]+}   </td><td>follow by a dot "." and string in the bracket [ ], must contains one or more (+)</td>
   *   </tr><tr>
   *     <td>{@code )*}                  </td><td>end of group #1, this group is optional (*)</td>
   *   </tr><tr>
   *     <td>{@code @}                   </td><td>must contains a "@" symbol</td>
   *   </tr><tr>
   *     <td>{@code [A-Za-z0-9-]+}       </td><td>follow by string in the bracket [ ], must contains one or more (+)</td>
   *   </tr><tr>
   *     <td>{@code (}                   </td><td>start of group #2 - first level TLD checking</td>
   *   </tr><tr>
   *     <td>{@code \\.[A-Za-z0-9-]+}    </td><td>follow by a dot "." and string in the bracket [ ], must contains one or more (+)</td>
   *   </tr><tr>
   *     <td>{@code )*}                  </td><td>end of group #2, this group is optional (*)</td>
   *   </tr><tr>
   *     <td>{@code (}                   </td><td>start of group #3 - second level TLD checking</td>
   *   </tr><tr>
   *     <td><code>\\.[A-Za-z]{2,}</code>     </td><td>follow by a dot "." and string in the bracket [ ], with minimum length of 2</td>
   *   </tr><tr>
   *     <td>{@code )}                   </td><td>end of group #3</td>
   *   </tr><tr>
   *     <td>{@code $}                   </td><td>end of the line</td>
   *   </tr>
   * </table>
   *
   * @param email
   * @return
   */
  public static boolean isValidEmailAddress(String email) {
    if (syntaxChecker.emailPattern == null) {
      //Does not handle non-printable unicode characters, and will return an error. The log error message will return a "?"
      //for any non-printable unicode. Also the regex pattern to capture this is \\p{C}.
      syntaxChecker.emailPattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    }
    return syntaxChecker.emailPattern.matcher(email).matches();
  }


  /**
   * Checks whether the given idCandidate is a valid identifier according to
   * the SBML specifications.
   *
   * @param idCandidate
   *            The {@link String} to be tested.
   * @param level
   *            Level of the SBML to be used.
   * @param version
   *            Version of the SBML to be used.
   * @return True if the argument satisfies the specification of identifiers
   *         in the SBML specifications or false otherwise.
   */
  public static final boolean isValidId(String idCandidate, int level,
    int version) {

    if (level == 1) {
      if (version == 1) {
        if (syntaxChecker.reservedNamesL1V1 == null) {
          syntaxChecker.initReservedNamesL1V1();
        }
        if (syntaxChecker.SNameL1V1 == null) {
          syntaxChecker.initL1V1SNamePattern();
        }
        return !syntaxChecker.reservedNamesL1V1.contains(idCandidate)
            && syntaxChecker.SNameL1V1.matcher(idCandidate).matches();
      } else if (version == 2) {
        if (syntaxChecker.reservedNamesL1V2 == null) {
          syntaxChecker.initReservedNamesL1V2();
        }
        if (syntaxChecker.SNameL1V2 == null) {
          syntaxChecker.initL1V2SNamePattern();
        }
        return !syntaxChecker.reservedNamesL1V2.contains(idCandidate)
            && syntaxChecker.SNameL1V2.matcher(idCandidate).matches();
      } else {
        /* This happens if
         * - id is one of the reserved names
         * - id doesn't match the name patterns
         * - version is invalid
         */
        return false;
      }
    }

    if (syntaxChecker.SIdL2Pattern == null) {
      syntaxChecker.initSIdL2Pattern();
    }

    // level undefined or level > 1
    return syntaxChecker.SIdL2Pattern.matcher(idCandidate).matches();
  }

  /**
   * Checks if the given identifier candidate satisfies the requirements for a
   * valid meta identifier (see SBML L2V4 p. 12 for details).
   *
   * @param idCandidate
   * @return {@code true} if the given argument is a valid meta identifier
   *         {@link String}, {@code false} otherwise.
   */
  public static final boolean isValidMetaId(String idCandidate) {
    return isValidMetaId(idCandidate, 2, 2); //TODO - should version 2 or 4 be the default
  }

  /**
   * Checks if the given identifier candidate satisfies the requirements for a
   * valid meta identifier (for any version and level).
   *
   * @param idCandidate
   * @param level
   * @param version
   * @return {@code true} if the given argument is a valid meta identifier
   *         {@link String}, {@code false} otherwise.
   */
  public static final boolean isValidMetaId(String idCandidate, int level, int version) {

    String metaIdPatternKey = syntaxChecker.initMetaIdPatterns(level, version);

    /* After using initMetaIdPatterns the pattern value of
     * metaIdPatterns or simpleMetaIdPatterns can still be null
     * due to invalid level and version values.
     * So only if the patterns are actually successfully initialized
     * we check the matchers.
     */
    if(!metaIdPatternKey.isEmpty()) {

      // match for Pattern "L2V1" (this key in metaIdPatterns is for L2V1 and L2V2)
      if(metaIdPatternKey.equals("L2V1")) {
        // In the most cases the first check will be sufficient.
        if(syntaxChecker.simpleMetaIdPatterns.get(metaIdPatternKey).matcher(idCandidate).matches()) {
          return true;
        }
        return syntaxChecker.metaIdPatterns.get(metaIdPatternKey).matcher(idCandidate).matches();
      }

      //match for Pattern "L2V3" (this key in metaIdPatterns is for L2V3 and above)
      if(metaIdPatternKey.equals("L2V3")) {
        // In the most cases the first check will be sufficient.
        if(syntaxChecker.simpleMetaIdPatterns.get(metaIdPatternKey).matcher(idCandidate).matches()) {
          return true;
        }
        return syntaxChecker.metaIdPatterns.get(metaIdPatternKey).matcher(idCandidate).matches();
      }
    }

    return false;
  }

  /**
   * Definition of valid e-mail addresses. Initialized upon first use.
   */
  private Pattern emailPattern;

  /**
   * Hashmap with {@link String} "L2V1" or "L2V3" as possible keys and its
   * {@link Pattern} as value to recognize valid meta-identifier strings for SBML elements.
   */
  private HashMap<String, Pattern> metaIdPatterns;

  /**
   * Collection of reserved names that must not be used as identifiers (names)
   * in SBML documents in SBML Level 1 Version 1.
   */
  private Set<String> reservedNamesL1V1;

  /**
   * Collection of reserved names that must not be used as identifiers (names)
   * in SBML documents in SBML Level 1 Version 2.
   */
  private Set<String> reservedNamesL1V2;

  /**
   * Pattern to recognize valid SIds, i.e., identifier strings for SBML elements.
   */
  private Pattern SIdL2Pattern;

  /**
   * Hashmap with {@link String} "L2V1" or "L2V3" as possible keys and its simplified
   * {@link Pattern} as value to recognize valid meta-identifier strings for SBML elements.
   */
  private HashMap<String, Pattern> simpleMetaIdPatterns;

  /**
   * Name {@link Pattern} for SBML Level 1 version 1.
   */
  private Pattern SNameL1V1;

  /**
   * Name {@link Pattern} for SBML Level 1 version 2.
   */
  private Pattern SNameL1V2;

  /**
   * This is a singleton class and should only be used through static methods.
   */
  private SyntaxChecker() {
    super();
  }

  /**
   *
   * @return
   */
  private Set<String> getReservedNamesL1V1() {
    return new TreeSet<String>(Arrays.asList(new String[] {
      "abs", "acos", "and", "asin", "atan", "ceil", "cos", "exp", "floor",
      "hilli", "hillmmr", "hillmr", "hillr", "isouur", "log", "log10", "massi",
      "massr", "not", "or", "ordbbr", "ordbur", "ordubr", "pow", "ppbr", "sin",
      "sqr", "sqrt", "tan", "uai", "ualii", "uar", "ucii", "ucir", "ucti",
      "uctr", "uhmi", "uhmr", "umai", "umar", "umi", "umr", "unii", "unir",
      "usii", "usir", "uuci", "uucr", "uuhr", "uui", "uur", "xor"}));
  }

  /**
   * Initializes the pattern for the type SName (described in SBML L1V1
   * specification, p. 6).
   */
  private void initL1V1SNamePattern() {
    String underscore = "_";
    String letter = "a-zA-Z";
    String digit = "0-9";
    String idChar = '[' + letter + digit + underscore + ']';
    SNameL1V1 = Pattern.compile("^[" + underscore + "]*[" + letter + "]{1}" + idChar + '*');
  }

  /**
   * Creates the pattern for SName as defined in SBML specification for L1V2
   * page 7.
   */
  private void initL1V2SNamePattern() {
    String underscore = "_";
    String letter = "a-zA-Z";
    String digit = "0-9";
    String idChar = '[' + letter + digit + underscore + ']';
    SNameL1V2 = Pattern.compile("^[" + letter + underscore + "]" + idChar + '*');
  }


  /**
   * Build the pattern for metaIds according to their definitions in SBML
   * and the definitions of the corresponding symbols at <a href="https://www.w3.org/TR/xml/#CharClasses">XML 1.0 Specification</a>
   * The returned key is the minimum level-version combination for which document the pattern is initialized.
   *
   * @param level
   *            Level of the SBML to be used.
   * @param version
   *            Version of the SBML to be used.
   * @return The keys "L2V2" or "L2V3" if a pattern is initialized succesfully or was already existing before,
   * or empty {@link String} if level and version are not valid numbers.
   */
  private String initMetaIdPatterns(int level, int version) {

    String metaId = "";
    String simpleMetaId;
    String ncNameChar;
    String simpleNameChar;
    String underscore = "_";
    String dash = "\\-";
    String dot = ".";
    String colon = ":";
    StringBuilder letter, digit, combiningChar;
    String extender;

    if(syntaxChecker.metaIdPatterns == null) {
      syntaxChecker.metaIdPatterns = new HashMap<String, Pattern>();
    }

    if(syntaxChecker.simpleMetaIdPatterns == null) {
      syntaxChecker.simpleMetaIdPatterns = new HashMap<String, Pattern>();
    }

    if(((level == 2) && (version == 1)) || ((level == 2) && (version == 2))) {

      //create metaIdPattern only if it doesn't already exist in the hashmap
      if(!syntaxChecker.metaIdPatterns.containsKey("L2V1")) {
        letter = StringTools.concatStringBuilder("a-zA-Z");
        digit = StringTools.concatStringBuilder("0-9");
        combiningChar = StringTools.concatStringBuilder("[\u0300-\u0345][\u0360-\u0361][\u0483-\u0486][\u0591-\u05A1][\u05A3-\u05B9][\u05BB-\u05BD]"
          , "\u05BF[\u05C1-\u05C2]\u05C4[\u064B-\u0652]\u0670[\u06D6-\u06DC][\u06DD-\u06DF][\u06E0-\u06E4][\u06E7-\u06E8]"
          , "[\u06EA-\u06ED][\u0901-\u0903]\u093C[\u093E-\u094C]\u094D[\u0951-\u0954][\u0962-\u0963][\u0981-\u0983]"
          , "\u09BC\u09BE\u09BF[\u09C0-\u09C4][\u09C7-\u09C8][\u09CB-\u09CD]\u09D7[\u09E2-\u09E3]\u0A02\u0A3C\u0A3E"
          , "\u0A3F[\u0A40-\u0A42][\u0A47-\u0A48][\u0A4B-\u0A4D][\u0A70-\u0A71][\u0A81-\u0A83]\u0ABC[\u0ABE-\u0AC5]"
          , "[\u0AC7-\u0AC9][\u0ACB-\u0ACD][\u0B01-\u0B03]\u0B3C[\u0B3E-\u0B43][\u0B47-\u0B48][\u0B4B-\u0B4D][\u0B56-\u0B57]"
          , "[\u0B82-\u0B83][\u0BBE-\u0BC2][\u0BC6-\u0BC8][\u0BCA-\u0BCD]\u0BD7[\u0C01-\u0C03][\u0C3E-\u0C44][\u0C46-\u0C48]"
          , "[\u0C4A-\u0C4D][\u0C55-\u0C56][\u0C82-\u0C83][\u0CBE-\u0CC4][\u0CC6-\u0CC8][\u0CCA-\u0CCD][\u0CD5-\u0CD6]"
          , "[\u0D02-\u0D03][\u0D3E-\u0D43][\u0D46-\u0D48][\u0D4A-\u0D4D]\u0D57\u0E31[\u0E34-\u0E3A][\u0E47-\u0E4E]"
          , "\u0EB1[\u0EB4-\u0EB9][\u0EBB-\u0EBC][\u0EC8-\u0ECD][\u0F18-\u0F19]\u0F35\u0F37\u0F39\u0F3E\u0F3F"
          , "[\u0F71-\u0F84][\u0F86-\u0F8B][\u0F90-\u0F95]\u0F97[\u0F99-\u0FAD][\u0FB1-\u0FB7]\u0FB9[\u20D0-\u20DC]\u20E1[\u302A-\u302F]\u3099\u309A");
        extender = "\u00B7\u02D0\u02D1\u0387\u0640\u0E46\u0EC6\u3005[\u3031-\u3035][\u309D-\u309E][\u30FC-\u30FE]";
        ncNameChar = "[" + letter + digit + dot + dash + underscore + combiningChar + extender + "]";
        metaId = "[" + letter + underscore + "]" + "[" + ncNameChar + "]*";
        metaIdPatterns.put("L2V1", Pattern.compile(metaId));

        //create simpleMetaIdPattern for faster check if it doesn't exist
        if(!syntaxChecker.simpleMetaIdPatterns.containsKey("L2V1")) {

          simpleNameChar = "[" + letter + digit + dot + dash + underscore + "]";
          simpleMetaId = "[" + letter + underscore + "]" + "[" + simpleNameChar + "]*";
          simpleMetaIdPatterns.put("L2V1", Pattern.compile(simpleMetaId)) ;
        }

        return "L2V1";
      }
      return "L2V1";
    }

    //TODO - Mind that level 3 version 2 is currently the last valid version (method should be updated if new version comes out)
    if(((level == 2) && ((version == 3) || (version == 4) || (version == 5))) ||
        ((level == 3) && ((version == 1) || (version >= 2))) ||
        (level > 3)) {

      //create metaIdPattern only if it doesn't already exist in the hashmap
      if(!syntaxChecker.metaIdPatterns.containsKey("L2V3")) {
        letter = StringTools.concatStringBuilder("[\u0041-\u005A][\u0061-\u007A][\u00C0-\u00D6][\u00D8-\u00F6][\u00F8-\u00FF][\u0100-\u0131][\u0134-\u013E][\u0141-\u0148]"
          , "[\u014A-\u017E][\u0180-\u01C3][\u01CD-\u01F0][\u01F4-\u01F5][\u01FA-\u0217][\u0250-\u02A8][\u02BB-\u02C1]"
          , "\u0386[\u0388-\u038A]\u038C[\u038E-\u03A1][\u03A3-\u03CE][\u03D0-\u03D6]\u03DA\u03DC\u03DE\u03E0[\u03E2-\u03F3]"
          , "[\u0401-\u040C][\u040E-\u044F][\u0451-\u045C][\u045E-\u0481][\u0490-\u04C4][\u04C7-\u04C8][\u04CB-\u04CC][\u04D0-\u04EB]"
          , "[\u04EE-\u04F5][\u04F8-\u04F9][\u0531-\u0556]\u0559[\u0561-\u0586][\u05D0-\u05EA][\u05F0-\u05F2][\u0621-\u063A]"
          , "[\u0641-\u064A][\u0671-\u06B7][\u06BA-\u06BE][\u06C0-\u06CE][\u06D0-\u06D3]\u06D5[\u06E5-\u06E6][\u0905-\u0939]\u093D"
          , "[\u0958-\u0961][\u0985-\u098C][\u098F-\u0990][\u0993-\u09A8][\u09AA-\u09B0]\u09B2[\u09B6-\u09B9][\u09DC-\u09DD]"
          , "[\u09DF-\u09E1][\u09F0-\u09F1][\u0A05-\u0A0A][\u0A0F-\u0A10][\u0A13-\u0A28][\u0A2A-\u0A30][\u0A32-\u0A33][\u0A35-\u0A36]"
          , "[\u0A38-\u0A39][\u0A59-\u0A5C]\u0A5E[\u0A72-\u0A74][\u0A85-\u0A8B]\u0A8D[\u0A8F-\u0A91][\u0A93-\u0AA8][\u0AAA-\u0AB0]"
          , "[\u0AB2-\u0AB3][\u0AB5-\u0AB9]\u0ABD\u0AE0[\u0B05-\u0B0C][\u0B0F-\u0B10][\u0B13-\u0B28][\u0B2A-\u0B30][\u0B32-\u0B33]"
          , "[\u0B36-\u0B39]\u0B3D[\u0B5C-\u0B5D][\u0B5F-\u0B61][\u0B85-\u0B8A][\u0B8E-\u0B90][\u0B92-\u0B95][\u0B99-\u0B9A]\u0B9C"
          , "[\u0B9E-\u0B9F][\u0BA3-\u0BA4][\u0BA8-\u0BAA][\u0BAE-\u0BB5][\u0BB7-\u0BB9][\u0C05-\u0C0C][\u0C0E-\u0C10][\u0C12-\u0C28]"
          , "[\u0C2A-\u0C33][\u0C35-\u0C39][\u0C60-\u0C61][\u0C85-\u0C8C][\u0C8E-\u0C90][\u0C92-\u0CA8][\u0CAA-\u0CB3][\u0CB5-\u0CB9]"
          , "\u0CDE[\u0CE0-\u0CE1][\u0D05-\u0D0C][\u0D0E-\u0D10][\u0D12-\u0D28][\u0D2A-\u0D39][\u0D60-\u0D61][\u0E01-\u0E2E]\u0E30"
          , "[\u0E32-\u0E33][\u0E40-\u0E45][\u0E81-\u0E82]\u0E84[\u0E87-\u0E88]\u0E8A\u0E8D[\u0E94-\u0E97][\u0E99-\u0E9F]"
          , "[\u0EA1-\u0EA3]\u0EA5\u0EA7[\u0EAA-\u0EAB][\u0EAD-\u0EAE]\u0EB0[\u0EB2-\u0EB3]\u0EBD[\u0EC0-\u0EC4][\u0F40-\u0F47]"
          , "[\u0F49-\u0F69][\u10A0-\u10C5][\u10D0-\u10F6]\u1100[\u1102-\u1103][\u1105-\u1107]\u1109[\u110B-\u110C][\u110E-\u1112]"
          , "\u113C\u113E\u1140\u114C\u114E\u1150[\u1154-\u1155]\u1159[\u115F-\u1161]\u1163\u1165\u1167\u1169[\u116D-\u116E]"
          , "[\u1172-\u1173]\u1175\u119E\u11A8\u11AB[\u11AE-\u11AF][\u11B7-\u11B8]\u11BA[\u11BC-\u11C2]\u11EB\u11F0\u11F9"
          , "[\u1E00-\u1E9B][\u1EA0-\u1EF9][\u1F00-\u1F15][\u1F18-\u1F1D][\u1F20-\u1F45][\u1F48-\u1F4D][\u1F50-\u1F57]\u1F59\u1F5B"
          , "\u1F5D[\u1F5F-\u1F7D][\u1F80-\u1FB4][\u1FB6-\u1FBC]\u1FBE[\u1FC2-\u1FC4][\u1FC6-\u1FCC][\u1FD0-\u1FD3][\u1FD6-\u1FDB]"
          , "[\u1FE0-\u1FEC][\u1FF2-\u1FF4][\u1FF6-\u1FFC]\u2126[\u212A-\u212B]\u212E[\u2180-\u2182][\u3041-\u3094][\u30A1-\u30FA]"
          , "[\u3105-\u312C][\uAC00-\uD7A3]"
          , "[\u4E00-\u9FA5]\u3007[\u3021-\u3029]");
        digit = StringTools.concatStringBuilder("[\u0030-\u0039][\u0660-\u0669][\u06F0-\u06F9][\u0966-\u096F][\u09E6-\u09EF]"
          , "[\u0A66-\u0A6F][\u0AE6-\u0AEF][\u0B66-\u0B6F][\u0BE7-\u0BEF][\u0C66-\u0C6F]"
          , "[\u0CE6-\u0CEF][\u0D66-\u0D6F][\u0E50-\u0E59][\u0ED0-\u0ED9][\u0F20-\u0F29]");
        combiningChar = StringTools.concatStringBuilder("[\u0300-\u0345][\u0360-\u0361][\u0483-\u0486][\u0591-\u05A1][\u05A3-\u05B9][\u05BB-\u05BD]"
          , "\u05BF[\u05C1-\u05C2]\u05C4[\u064B-\u0652]\u0670[\u06D6-\u06DC][\u06DD-\u06DF][\u06E0-\u06E4][\u06E7-\u06E8]"
          , "[\u06EA-\u06ED][\u0901-\u0903]\u093C[\u093E-\u094C]\u094D[\u0951-\u0954][\u0962-\u0963][\u0981-\u0983]"
          , "\u09BC\u09BE\u09BF[\u09C0-\u09C4][\u09C7-\u09C8][\u09CB-\u09CD]\u09D7[\u09E2-\u09E3]\u0A02\u0A3C\u0A3E"
          , "\u0A3F[\u0A40-\u0A42][\u0A47-\u0A48][\u0A4B-\u0A4D][\u0A70-\u0A71][\u0A81-\u0A83]\u0ABC[\u0ABE-\u0AC5]"
          , "[\u0AC7-\u0AC9][\u0ACB-\u0ACD][\u0B01-\u0B03]\u0B3C[\u0B3E-\u0B43][\u0B47-\u0B48][\u0B4B-\u0B4D][\u0B56-\u0B57]"
          , "[\u0B82-\u0B83][\u0BBE-\u0BC2][\u0BC6-\u0BC8][\u0BCA-\u0BCD]\u0BD7[\u0C01-\u0C03][\u0C3E-\u0C44][\u0C46-\u0C48]"
          , "[\u0C4A-\u0C4D][\u0C55-\u0C56][\u0C82-\u0C83][\u0CBE-\u0CC4][\u0CC6-\u0CC8][\u0CCA-\u0CCD][\u0CD5-\u0CD6]"
          , "[\u0D02-\u0D03][\u0D3E-\u0D43][\u0D46-\u0D48][\u0D4A-\u0D4D]\u0D57\u0E31[\u0E34-\u0E3A][\u0E47-\u0E4E]"
          , "\u0EB1[\u0EB4-\u0EB9][\u0EBB-\u0EBC][\u0EC8-\u0ECD][\u0F18-\u0F19]\u0F35\u0F37\u0F39\u0F3E\u0F3F"
          , "[\u0F71-\u0F84][\u0F86-\u0F8B][\u0F90-\u0F95]\u0F97[\u0F99-\u0FAD][\u0FB1-\u0FB7]\u0FB9[\u20D0-\u20DC]\u20E1[\u302A-\u302F]\u3099\u309A");
        extender = "\u00B7\u02D0\u02D1\u0387\u0640\u0E46\u0EC6\u3005[\u3031-\u3035][\u309D-\u309E][\u30FC-\u30FE]";
        ncNameChar = "[" + letter + digit + dot + dash + underscore + colon + combiningChar + extender + "]";
        metaId = "[" + letter + underscore + colon + "]" + "[" + ncNameChar + "]*";
        syntaxChecker.metaIdPatterns.put("L2V3", Pattern.compile(metaId));

        //create simpleMetaIdPattern for faster check if it doesn't exist
        if(!syntaxChecker.simpleMetaIdPatterns.containsKey("L2V3")) {
          letter = StringTools.concatStringBuilder("a-zA-Z");
          digit = StringTools.concatStringBuilder("0-9");
          simpleNameChar = "[" + letter + digit + dot + dash + underscore + colon + "]";
          simpleMetaId = "[" + letter + underscore + colon + "]" + "[" + simpleNameChar + "]*";
          syntaxChecker.simpleMetaIdPatterns.put("L2V3", Pattern.compile(simpleMetaId));
        }

        return "L2V3";
      }

      return "L2V3";
    }

    return "";
  }

  /**
   * These reserved words can occur in case of {@link org.sbml.jsbml.UnitDefinition}s:
   * {@code "substance"}, {@code "time"}, {@code "volume"}.
   */
  private void initReservedNamesL1V1() {
    reservedNamesL1V1 = getReservedNamesL1V1();
  }

  /**
   *
   */
  private void initReservedNamesL1V2() {
    reservedNamesL1V2 = new TreeSet<String>(getReservedNamesL1V1());
    reservedNamesL1V2.add("uaii");
  }

  /**
   *
   */
  private void initSIdL2Pattern() {
    String underscore = "_";
    String letter = "a-zA-Z";
    String digit = "0-9";
    String idChar = '[' + letter + digit + underscore + ']';
    SIdL2Pattern = Pattern.compile("^[" + letter + underscore + "]" + idChar + '*');
  }

}

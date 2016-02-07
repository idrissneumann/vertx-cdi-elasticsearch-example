package com.bblvertx.utils;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bblvertx.SeConstants;
import com.bblvertx.exception.BadRequestException;
import com.bblvertx.exception.FonctionalException;
import com.bblvertx.pojo.SearchResult;

/**
 * Classe utilitaire générale.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class CommonUtils {
	private static final Logger LOGGER = LogManager.getLogger(CommonUtils.class);

	/**
	 * Classe statique
	 */
	private CommonUtils() {
	}

	/**
	 * Vérifie si le paramètre d'une requête est vide et renvoie une erreur 400
	 * si c'est le cas
	 * 
	 * @param value
	 * @param msg
	 */
	public static void assertParamNotEmpty(String value, String msg) {
		if (StringUtils.isEmpty(value)) {
			LOGGER.debug("[assertParamNotEmpty] message = " + msg);
			throw new BadRequestException(msg);
		}
	}

	/**
	 * Vérifie si le paramètre d'une requête n'est pas un calendar et renvoie
	 * une erreur 400 si c'est le cas
	 * 
	 * @param value
	 * @param msg
	 * @param la
	 *            valeur castée.
	 */
	public static Calendar assertParamNotCalendar(String value, String msg) {
		Calendar rtn = null;

		try {
			if (StringUtils.isNotEmpty(value) && null == (rtn = DateUtils.stringToCalendar(value, DateUtils.FORMAT_Z))) {
				LOGGER.debug("[assertParamNotEmpty] message = " + msg);
				throw new BadRequestException(msg);
			}
		} catch (ParseException e) {
			LOGGER.debug("[assertParamNotEmpty] message = " + msg);
			throw new BadRequestException(msg);
		}

		return rtn;
	}

	/**
	 * Vérifie si le paramètre d'une requête est non numérique et renvoie une
	 * erreur 400 si c'est le cas
	 * 
	 * @param value
	 * @param msg
	 * @param la
	 *            valeur castée.
	 */
	public static Integer assertParamNotNumeric(String value, String msg) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}

		if (!StringUtils.isNumeric(value)) {
			LOGGER.debug("[assertParamNotEmpty] message = " + msg);
			throw new BadRequestException(msg);
		}

		return Integer.valueOf(value);
	}

	/**
	 * Vérifie que le paramètre d'une requête est obligatoirement vide (cf : id
	 * pour les POST)
	 * 
	 * @param value
	 * @param msg
	 */
	public static void assertParamEmpty(String value, String msg) {
		if (StringUtils.isNotEmpty(value)) {
			LOGGER.debug("[assertParamEmpty] message = " + msg);
			throw new BadRequestException(msg);
		}
	}

	/**
	 * Vérifie si le paramètre d'une requête est vide et renvoie une erreur 400
	 * si c'est le cas
	 * 
	 * @param value
	 * @param msg
	 */
	public static void assertParamNotEmpty(Object value, String msg) {
		if (null == value) {
			LOGGER.debug("[assertParamNotEmpty] message = " + msg);
			throw new BadRequestException(msg);
		}
	}

	/**
	 * Vérifie que le paramètre d'une requête est obligatoirement vide (cf : id
	 * pour les POST)
	 * 
	 * @param value
	 * @param msg
	 */
	public static void assertParamEmpty(Object value, String msg) {
		if (null != value) {
			LOGGER.debug("[assertParamEmpty] message = " + msg);
			throw new BadRequestException(msg);
		}
	}

	/**
	 * Formater les arguments {0}, {1}, etc d'une chaîne
	 * 
	 * @param str
	 * @param args
	 * @param locale
	 * @return String
	 */
	public static String format(String str, Object[] args, Locale locale) {
		if (StringUtils.isEmpty(str) || null == args || args.length <= 0) {
			return str;
		}

		MessageFormat mf = new MessageFormat(str, locale);
		return mf.format(args);
	}

	/**
	 * Comparer deux chaines de carractères potentiellements nulles
	 * 
	 * @param s1
	 * @param s2
	 * @param ignoreCase
	 * @return int (1 si s1 > s2, -1 si s1 < s2 et 0 si s1 == s2)
	 */
	public static int compareTo(String s1, String s2, boolean ignoreCase) {
		if (null == s1 && null == s2) {
			return 0;
		} else if (null == s1) {
			return -1;
		} else if (null == s2) {
			return 1;
		} else {
			return (ignoreCase) ? s1.compareToIgnoreCase(s2) : s1.compareTo(s2);
		}
	}

	/**
	 * Comparer deux chaines de carractères potentiellements nulles
	 * 
	 * @param s1
	 * @param s2
	 * @return int (1 si s1 > s2, -1 si s1 < s2 et 0 si s1 == s2)
	 */
	public static int compareTo(String s1, String s2) {
		return compareTo(s1, s2, false);
	}

	/**
	 * Décomposer une liste d'id séparées en List<Long>
	 * 
	 * @param strListIds
	 * @param separator
	 * @return List<Long>
	 * @throws FonctionalException
	 */
	public static List<Long> string2listIds(String strListIds, String separator) throws FonctionalException {
		if (StringUtils.isEmpty(strListIds)) {
			return new ArrayList<Long>();
		}

		String[] ids = strListIds.split(separator);

		List<Long> rtn = new ArrayList<Long>();

		try {
			if (null == ids || ids.length <= 0) {
				rtn.add(Long.valueOf(strListIds));
			} else {
				for (String id : ids) {
					rtn.add(Long.valueOf(id));
				}
			}

			return rtn;
		} catch (NumberFormatException e) {
			throw new FonctionalException("Les ids sont incorrectes : " + strListIds);
		}
	}

	/**
	 * Décomposer une liste d'id séparées par des ";" en List<Long>
	 * 
	 * @param strListIds
	 * @return List<Long>
	 * @throws FonctionalException
	 */

	public static List<Long> string2listIds(String strListIds) throws FonctionalException {
		if (strListIds.contains(" ")) {
			return string2listIds(strListIds, " ");
		} else {
			return string2listIds(strListIds, SeConstants.SEPARATOR_DEFAULT);
		}
	}

	/**
	 * Cast en String
	 * 
	 * @param Object
	 *            value
	 * @return String
	 */
	public static String objectToString(Object value) {
		return (null != value) ? value.toString() : SeConstants.EMPTY_STRING;
	}

	/**
	 * Parse l'heure sous format (hh:mm ou hhmm) et retourne une liste de long
	 * 
	 * @param strListNums
	 * @return
	 */
	public static List<Long> parseHeures(String strListNums) {

		List<Long> rtn = new ArrayList<Long>();
		String[] ids = strListNums.split(":");
		if (1 == ids.length) {

			rtn.add((Long.valueOf(ids[0].substring(0, 2))));
			rtn.add((Long.valueOf(ids[0].substring(2, 4))));

		} else {
			for (String id : ids) {
				rtn.add(Long.valueOf(id));
			}

		}

		return rtn;

	}

	/**
	 * Methode permettant de faire du padding. (par des zéros, par défaut)
	 * 
	 * @param pToPad
	 *            String à compléter
	 * @param pPadLength
	 *            Int Longueur de la chaine apres padding
	 * @return String
	 */
	public static String pad(String pToPad, int pPadLength) {
		return pad(pToPad, pPadLength, '0');
	}

	/**
	 * Methode permettant de faire du unpadding. (par des zéros, par défaut)
	 * 
	 * @param pToPad
	 *            String à parser
	 * @return String
	 */
	public static String unpad(String pToPad) {
		return unpad(pToPad, '0');
	}

	/**
	 * Methode permettant de faire du padding.
	 * 
	 * @param pToPad
	 *            String à compléter
	 * @param pPadLength
	 *            Int Longueur de la chaine apres padding
	 * @param pChar
	 *            Char caractère de padding
	 * @return String
	 */
	public static String pad(String pToPad, int pPadLength, char pChar) {
		if (pToPad != null) {
			int vStrLen = pToPad.length();
			final StringBuffer vBuffer = new StringBuffer(pToPad);

			if (vStrLen > 0) {
				while (vStrLen < pPadLength) {
					vBuffer.insert(0, pChar);
					vStrLen++;
				}
			}
			return vBuffer.toString();
		} else {
			return null;
		}
	}

	/**
	 * Methode permettant de faire du unpadding.
	 * 
	 * @param pToPad
	 *            String à parser
	 * @param pChar
	 *            Char caractère de padding
	 * @return String
	 */
	public static String unpad(String pToPad, char pChar) {
		if (pToPad != null) {
			final int vStrLen = pToPad.length();
			final StringBuffer vBuffer = new StringBuffer("");
			final int vIndex = 0;
			boolean vEndUnPading = false;

			while ((vIndex < vStrLen) && !vEndUnPading) {
				if (pToPad.charAt(vIndex) != pChar) {
					vEndUnPading = true;
					vBuffer.append(pToPad.substring(vIndex, vStrLen - 1));
				}
			}
			return vBuffer.toString();
		} else {
			return null;
		}
	}

	/**
	 * Initialiser l'objet searchResult
	 * 
	 * @param startIndex
	 * @param maxResults
	 * @return SearchResult<T>
	 */
	public static <T> SearchResult<T> initSearchResult(Long startIndex, Long maxResults) {
		SearchResult<T> rtn = new SearchResult<T>();
		rtn.setTotalResults(0L);
		rtn.setStartIndex(startIndex);
		rtn.setMaxResults(maxResults);
		return rtn;
	}

	/**
	 * Renvoie vrai si l'objet SearchResult est vide.
	 * 
	 * @param result
	 * @return Boolean
	 */
	public static Boolean isNotEmpty(SearchResult<?> result) {
		return null != result && CollectionUtils.isNotEmpty(result.getResults());
	}

	/**
	 * Retrouver le nom d'un getter à partir d'un nom de propriété.
	 * 
	 * @param propertyName
	 * @return String
	 */
	public static String getterFromProperty(String propertyName) {
		return (null == propertyName) ? null : "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
	}

	/**
	 * Retrouver le nom d'un setter à partir d'un nom de propriété.
	 * 
	 * @param propertyName
	 * @return String
	 */
	public static String setterFromProperty(String propertyName) {
		return (null == propertyName) ? null : "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
	}

	/**
	 * Comparaison "safe" de deux integers.
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static Integer safeCompare(Integer o1, Integer o2) {
		if (null == o1 && null == o2) {
			return 0;
		} else if (null == o1) {
			return -1;
		} else if (null == o2) {
			return 1;
		} else {
			return o1.compareTo(o2);
		}
	}
}

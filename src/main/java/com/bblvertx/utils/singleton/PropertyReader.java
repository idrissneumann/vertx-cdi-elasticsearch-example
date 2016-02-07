package com.bblvertx.utils.singleton;

import static org.apache.commons.lang3.StringUtils.isNumeric;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.inject.Singleton;

/**
 * Singleton pour lire un fichier properties.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
@Singleton
public class PropertyReader {
	Map<String, Properties> prop;

	/**
	 * Constructeur.
	 * 
	 */
	public PropertyReader() {
		prop = new HashMap<String, Properties>();
	}

	/**
	 * Charger un fichier en mémoire.
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public void load(String fileName) throws IOException {
		if (!prop.containsKey(fileName)) {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
			Properties p = new Properties();
			p.load(is);
			prop.put(fileName, p);
		}
	}

	/**
	 * Récupérer la valeur d'une propriété.
	 * 
	 * @param fileName
	 * @param key
	 * @return String
	 * @throws IOException
	 */
	public String get(String fileName, String key) throws IOException {
		load(fileName);
		return prop.get(fileName).getProperty(key);
	}

	/**
	 * Récupérer la valeur numérique d'une propriété.
	 * 
	 * @param fileName
	 * @param key
	 * @return Integer
	 * @throws IOException
	 */
	public Integer getInt(String fileName, String key) throws IOException {
		String val = this.get(fileName, key);
		return (!isNumeric(val)) ? null : Integer.valueOf(val);
	}

	/**
	 * Récupérer la valeur d'une propriété.
	 * 
	 * @param fileName
	 * @param key
	 * @return String
	 * @throws IOException
	 */
	public String getQuietly(String fileName, String key) {
		try {
			return this.get(fileName, key);
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Récupérer la valeur numérique d'une propriété.
	 * 
	 * @param fileName
	 * @param key
	 * @return Integer
	 * @throws IOException
	 */
	public Integer getIntQuietly(String fileName, String key) {
		try {
			return this.getInt(fileName, key);
		} catch (IOException e) {
			return null;
		}
	}
}

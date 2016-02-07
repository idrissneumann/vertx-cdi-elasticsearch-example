package com.bblvertx.utils;

import static org.apache.commons.io.FileUtils.writeStringToFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;

/**
 * Classe utilitaire sur les fichiers.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class FileUtils {
	private static final Logger LOGGER = LogManager.getLogger(FileUtils.class);

	/**
	 * Tester rapidement si un fichier existe
	 * 
	 * @param path
	 * @return boolean
	 */
	public static boolean existFile(String path) {
		File f = new File(path);
		return f.exists();
	}

	/**
	 * Supprimer rapidement un fichier
	 * 
	 * @param path
	 * @return boolean
	 */
	public static boolean deleteFileQuietly(String path) {
		if (existFile(path)) {
			File f = new File(path);
			boolean rtn = f.delete();

			// Seconde tentative (fmk de tests)
			if (existFile(path) || !rtn) {
				try {
					Path p = FileSystems.getDefault().getPath(path, new String[0]);
					Files.delete(p);
					return true;
				} catch (IOException e) {
					LOGGER.error("Erreur à la lecture du fichier ", e);
					return false;
				}
			}

			return rtn;
		}

		return false;
	}

	/**
	 * Obtenir un tableau de byte à partir d'un fichier
	 * 
	 * @param path
	 * @return byte[]
	 */
	public static byte[] fileToByteQuietly(String path) {
		Path p = Paths.get(path);
		try {
			return Files.readAllBytes(p);
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Récupérer le chemin absolu
	 * 
	 * @param relatifPath
	 * @return String
	 */
	public static String getAbsolutePath(String relatifPath) {
		File file = new File(relatifPath);
		return file.getAbsolutePath();
	}

	/**
	 * Récupérer le contenu d'un fichier dans une chaîne
	 * 
	 * @param pathFile
	 * @return
	 */
	public static String file2stringQuietly(String pathFile) {
		if (!existFile(pathFile)) {
			LOGGER.warn("[file2string] Le fichier " + pathFile + " n'existe pas !");
			return null;
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile), "UTF8"));
			StringBuilder builder = new StringBuilder();
			String line;

			// For every line in the file, append it to the string builder
			while (null != (line = reader.readLine())) {
				builder.append(line);
			}

			reader.close();
			return builder.toString();
		} catch (IOException e) {
			LOGGER.error("Erreur à la lecture du fichier ", e);
			return null;
		}
	}

	/**
	 * Ecrire dans un fichier
	 * 
	 * @param pathFile
	 * @param content
	 * @throws IOException
	 */
	public static void string2fileQuietly(String pathFile, String content) {
		try {
			writeStringToFile(new File(pathFile), content);
		} catch (IOException e) {
			LOGGER.error("Erreur à la lecture du fichier ", e);
		}
	}

	/**
	 * Retourne vrai si un fichier est de type image.
	 * 
	 * @param mimeType
	 * @return boolean
	 */
	public static boolean isAllowedTypeOfImage(String mimeType) {
		List<String> lstMimesTypes = new ArrayList<String>();
		lstMimesTypes.add("image/gif");
		lstMimesTypes.add("image/png");
		lstMimesTypes.add("image/jpeg");

		return lstMimesTypes.contains(mimeType.toLowerCase());
	}

	/**
	 * Récupérer l'extension d'un fichier à partir de son Mime-type
	 * 
	 * @param mimeType
	 * @return String
	 */
	public static String getExtFromMimeType(String mimeType) {
		MimeType mime;
		try {
			MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
			mime = allTypes.forName(mimeType);
			return mime.getExtension();
		} catch (MimeTypeException e) {
			LOGGER.error("Erreur à la recherche d'une extension");
			return null;
		}

	}

	/**
	 * Classe statique
	 */
	private FileUtils() {
	}
}

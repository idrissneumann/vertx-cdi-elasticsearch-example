package com.bblvertx.pojo.vo;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

/**
 * Utilisateur du chat (correspondant à un profil personnel).
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class UserVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nom;

	private String prenom;

	private String email;

	private String id;

	private String lng;

	private Calendar dateConnect;

	private Calendar dateUpdate;

	private Integer rsSearch;

	private Long idPhotoProfil;

	private String randomId = UUID.randomUUID().toString();

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom
	 *            the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom
	 *            the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the lng
	 */
	public String getLng() {
		return lng;
	}

	/**
	 * @param lng
	 *            the lng to set
	 */
	public void setLng(String lng) {
		this.lng = lng;
	}

	/**
	 * @return the dateConnect
	 */
	public Calendar getDateConnect() {
		return dateConnect;
	}

	/**
	 * @param dateConnect
	 *            the dateConnect to set
	 */
	public void setDateConnect(Calendar dateConnect) {
		this.dateConnect = dateConnect;
	}

	/**
	 * @return the dateUpdate
	 */
	public Calendar getDateUpdate() {
		return dateUpdate;
	}

	/**
	 * @param dateUpdate
	 *            the dateUpdate to set
	 */
	public void setDateUpdate(Calendar dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	/**
	 * @return the rsSearch
	 */
	public Integer getRsSearch() {
		return rsSearch;
	}

	/**
	 * @param rsSearch
	 *            the rsSearch to set
	 */
	public void setRsSearch(Integer rsSearch) {
		this.rsSearch = rsSearch;
	}

	/**
	 * @return the idPhotoProfil
	 */
	public Long getIdPhotoProfil() {
		return idPhotoProfil;
	}

	/**
	 * @param idPhotoProfil
	 *            the idPhotoProfil to set
	 */
	public void setIdPhotoProfil(Long idPhotoProfil) {
		this.idPhotoProfil = idPhotoProfil;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (isNotEmpty(nom) && isNotEmpty(prenom)) {
			return String.format("%s %s", prenom, nom);
		} else if (isNotEmpty(nom)) {
			return nom;
		} else if (isNotEmpty(prenom)) {
			return prenom;
		} else if (isNotEmpty(email)) {
			return email;
		} else {
			return randomId;
		}
	}
}

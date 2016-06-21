/**
 * 
 */
package fr.eni_ecole.expeditor.bean;

import java.io.Serializable;

/**
 * ProjectExpeditor Version 1.0
 * @author d1408davidj
 * 20 juin 2016
 */
public class Article implements Serializable
{
	private static final long serialVersionUID = -5293013352693988471L;
	
	// Attributs
	private String ref;
	private String libelle;
	private String description;
	private int poids;
	
	
	// Constructeurs
	public Article() 
	{
		super();
	}

	public Article(String ref, String libelle, String description, int poids) 
	{
		super();
		this.ref = ref;
		this.libelle = libelle;
		this.description = description;
		this.poids = poids;
	}

	// Accesseurs
	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPoids() {
		return poids;
	}

	public void setPoids(int poids) {
		this.poids = poids;
	}	
}

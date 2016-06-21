package fr.eni_ecole.expeditor.bean;

import java.io.Serializable;

/**
 * ProjectExpeditor Version 1.0
 * @author d1410lheraultj
 * 20 juin 2016
 */
public class Utilisateur implements Serializable
{
	private static final long serialVersionUID = -8358615539378732093L;
	
	// Attributs
	private String id;
	private String nom;
	private String prenom;
	private String login;
	private String motDePasse;
	private String statut;
	
	// Constructeurs
	public Utilisateur()
	{
		super();
	}

	public Utilisateur(String id, String nom, String prenom, String login, String motDePasse, String statut) 
	{
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.login = login;
		this.motDePasse = motDePasse;
		this.statut = statut;
	}

	// Accesseurs
	public String getId() {
		return id;
	}

	public void setId(String string) {
		this.id = string;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}	
}
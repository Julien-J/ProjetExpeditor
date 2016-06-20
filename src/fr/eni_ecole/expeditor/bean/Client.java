package fr.eni_ecole.expeditor.bean;

import java.io.Serializable;

/**
 * ProjectExpeditor Version 1.0
 * @author d1410lheraultj
 * 20 juin 2016
 */
public class Client implements Serializable
{
	// Attributs
	private String num;
	private String nom;
	private String adresse;
	
	// Constructeurs
	public Client()
	{
		super();
	}

	public Client(String num, String nom, String adresse) 
	{
		super();
		this.num = num;
		this.nom = nom;
		this.adresse = adresse;
	}

	// Accesseurs
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}	
}

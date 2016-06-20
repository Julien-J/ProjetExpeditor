package fr.eni_ecole.expeditor.bean;

import java.io.Serializable;

/**
 * ProjectExpeditor Version 1.0
 * @author d1410lheraultj
 * 20 juin 2016
 */
public class LigneCommande implements Serializable
{
	// Attributs
	private String numCommande;
	private String refArticle;
	private int quantite;
	
	// Constructeurs
	public LigneCommande() 
	{
		super();
	}
	public LigneCommande(String numCommande, String refArticle, int quantite) 
	{
		super();
		this.numCommande = numCommande;
		this.refArticle = refArticle;
		this.quantite = quantite;
	}
	
	// Accesseurs
	public String getNumCommande() {
		return numCommande;
	}
	public void setNumCommande(String numCommande) {
		this.numCommande = numCommande;
	}
	public String getRefArticle() {
		return refArticle;
	}
	public void setRefArticle(String refArticle) {
		this.refArticle = refArticle;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
}

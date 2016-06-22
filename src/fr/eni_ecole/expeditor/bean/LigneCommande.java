package fr.eni_ecole.expeditor.bean;

import java.io.Serializable;

/**
 * ProjectExpeditor Version 1.0
 * @author d1410lheraultj
 * 20 juin 2016
 */
public class LigneCommande implements Serializable
{
	private static final long serialVersionUID = -1445841740122843037L;
	
	// Attributs
	private int numCommande;
	private String refArticle;
	private int quantite;
	
	// Constructeurs
	public LigneCommande() 
	{
		super();
	}
	public LigneCommande(int numCommande, String refArticle, int quantite) 
	{
		super();
		this.numCommande = numCommande;
		this.refArticle = refArticle;
		this.quantite = quantite;
	}
	
	// Accesseurs
	public int getNumCommande() {
		return numCommande;
	}
	public void setNumCommande(int numCommande) {
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

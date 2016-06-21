package fr.eni_ecole.expeditor.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import fr.eni_ecole.expeditor.bean.enums.EtatCommande;

/**
 * ProjectExpeditor Version 1.0
 * @author d1410lheraultj
 * 20 juin 2016
 */
public class Commande implements Serializable
{
	private static final long serialVersionUID = 1277087965945937430L;
	
	// Attributs
	private String num;
	private String numClient;
	private String idEmploye;
	private Date date;
	private int poidsTotal;
	private EtatCommande etat;
	private ArrayList<LigneCommande> lesLignes;
	
	// Constructeurs
	public Commande() 
	{
		super();
	}

	public Commande(String num, String numClient, String idEmploye, Date date, int poidsTotal, EtatCommande etat, ArrayList<LigneCommande> lesLignes) 
	{
		super();
		this.num = num;
		this.numClient = numClient;
		this.idEmploye = idEmploye;
		this.date = date;
		this.poidsTotal = poidsTotal;
		this.etat = etat;
		this.lesLignes = lesLignes;
	}
	
	// Accesseurs
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getNumClient() {
		return numClient;
	}

	public void setNumClient(String numClient) {
		this.numClient = numClient;
	}

	public String getIdEmploye() {
		return idEmploye;
	}

	public void setIdEmploye(String idEmploye) {
		this.idEmploye = idEmploye;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getPoidsTotal() {
		return poidsTotal;
	}

	public void setPoidsTotal(int poidsTotal) {
		this.poidsTotal = poidsTotal;
	}

	public EtatCommande getEtat() {
		return etat;
	}

	public void setEtat(EtatCommande etat) {
		this.etat = etat;
	}

	public ArrayList<LigneCommande> getLesLignes() {
		return lesLignes;
	}

	public void setLesLignes(ArrayList<LigneCommande> lesLignes) {
		this.lesLignes = lesLignes;
	}	
}

package fr.eni_ecole.expeditor.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import fr.eni_ecole.expeditor.bean.enums.EtatCommande;

/**
 * ProjectExpeditor Version 1.0
 * @author d1410lheraultj
 * 20 juin 2016
 */
public class Commande implements Serializable
{
	// Attributs
	private String num;
	private String numClient;
	private String idEmploye;
	private Date date;
	private int poidsTotal;
	private EtatCommande etat;
	private List<LigneCommande> lesLignes;
	
	// Constructeurs
	public Commande() 
	{
		super();
	}

	public Commande(String num, String numClient, String idEmploye, Date date, int poidsTotal, EtatCommande etat) 
	{
		super();
		this.num = num;
		this.numClient = numClient;
		this.idEmploye = idEmploye;
		this.date = date;
		this.poidsTotal = poidsTotal;
		this.etat = etat;
	}
	
	public Commande(String num, String numClient, String idEmploye, Date date, int poidsTotal, EtatCommande etat, List<LigneCommande> lesLignes) 
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

	public List<LigneCommande> getLesLignes() {
		return lesLignes;
	}

	public void setLesLignes(List<LigneCommande> lesLignes) {
		this.lesLignes = lesLignes;
	}	
}

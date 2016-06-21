package fr.eni_ecole.expeditor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.eni_ecole.expeditor.bean.Commande;
import fr.eni_ecole.expeditor.bean.enums.EtatCommande;

public class DAOCommande 
{
	/**
	 * M�thode en charge de r�cup�rer une Commande
	 * @param numCommande : num�ro de la Commande � r�cup�rer
	 * @return Objet Commande
	 * @throws SQLException
	 */
	public Commande getCommande(String numCommande) throws SQLException
	{
		Commande laCommande = new Commande();
		
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		
		try
		{
			cnx = AccesBase.getConnect();
			rqt = cnx.prepareStatement("SELECT numCli, date, etat, poidsTotal, idUser FROM COMMANDE WHERE num = ?");
			rqt.setString(1, numCommande);
			rs = rqt.executeQuery();
			
			if (rs.next())
			{
				laCommande.setNum(rs.getString("num"));
				laCommande.setNumClient(rs.getString("numCli"));
				laCommande.setDate(rs.getDate("date"));
				laCommande.setEtat(EtatCommande.getEnum(rs.getString("etat")));
				laCommande.setPoidsTotal(rs.getInt("poidsTotal"));
				laCommande.setIdEmploye(rs.getString("idUser"));
				laCommande.setLesLignes(DAOLigneCommande.getLignes(rs.getString("num")));
			}
			else
			{
				laCommande = null;
			}		
		}
		finally
		{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		return laCommande;
	}
	
	/**
	 * M�thode en charge de r�cup�rer toutes les Commandes en base
	 * @return ArrayList<Commande> : la liste des Commandes
	 * @throws SQLException
	 */
	public ArrayList<Commande> getAllCommande() throws SQLException
	{
		ArrayList<Commande> lesCommandes = new ArrayList<Commande>();
		
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		
		try
		{
			cnx = AccesBase.getConnect();
			rqt = cnx.prepareStatement("SELECT * FROM COMMANDE");			
			rs = rqt.executeQuery();
			Commande uneCommande;
			while (rs.next())
			{
				uneCommande = new Commande(
									rs.getString("num"),
									rs.getString("numCli"),
									rs.getString("idUser"),
									rs.getDate("date"),
									rs.getInt("poidsTotal"),
									EtatCommande.getEnum(rs.getString("etat")),
									DAOLigneCommande.getLignes(rs.getString("num"))
						);
				lesCommandes.add(uneCommande);				
			}		
		}
		finally
		{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		return lesCommandes;
	}
}

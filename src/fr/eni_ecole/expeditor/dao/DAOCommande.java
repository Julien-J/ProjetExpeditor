package fr.eni_ecole.expeditor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.eni_ecole.expeditor.bean.Commande;
import fr.eni_ecole.expeditor.bean.Utilisateur;
import fr.eni_ecole.expeditor.bean.enums.EtatCommande;

public class DAOCommande 
{
	/**
	 * M�thode en charge de r�cup�rer une Commande
	 * @param numCommande : num�ro de la Commande � r�cup�rer
	 * @return Objet Commande
	 * @throws SQLException
	 */
	public static Commande getCommande(int numCommande) throws SQLException
	{
		Commande laCommande = new Commande();
		
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		
		try
		{
			cnx = AccesBase.getConnect();
			rqt = cnx.prepareStatement("SELECT num, numCli, date, etat, poidsTotal, idUser FROM COMMANDE WHERE num = ?");
			rqt.setInt(1, numCommande);
			rs = rqt.executeQuery();
			
			if (rs.next())
			{
				laCommande.setNum(rs.getInt("num"));
				laCommande.setNumClient(rs.getString("numCli"));
				laCommande.setDate(rs.getTimestamp("date"));
				laCommande.setEtat(EtatCommande.getEnum(rs.getString("etat")));
				laCommande.setPoidsTotal(rs.getInt("poidsTotal"));
				laCommande.setIdEmploye(rs.getString("idUser"));
				laCommande.setLesLignes(DAOLigneCommande.getLignes(rs.getInt("num")));
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
	public static ArrayList<Commande> getAllCommande() throws SQLException
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
									rs.getInt("num"),
									rs.getString("numCli"),
									rs.getString("idUser"),
									rs.getTimestamp("date"),
									rs.getInt("poidsTotal"),
									EtatCommande.getEnum(rs.getString("etat")),
									rs.getTimestamp("dateTraitement"),
									DAOLigneCommande.getLignes(rs.getInt("num"))
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
	
	/**
	 * M�thode en charge de retourner le nombre de commandes en attente
	 * @return nbCommandeEA : int
	 * @throws SQLException
	 */
	public static int getCommandeEnAttente() throws SQLException
	{
		int nbCommandeEA = 0;
		
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		
		try 
		{
			cnx = AccesBase.getConnect();
			rqt = cnx.prepareStatement("SELECT COUNT(*) FROM COMMANDE WHERE etat = 'En attente'");
			rs = rqt.executeQuery();
			while (rs.next())
			{
				nbCommandeEA = rs.getInt(1);
			}
		}
		finally 
		{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		return nbCommandeEA;
	}
	
	/**
	 * Méthode en charge de réinialiser la commande en cours d'un utilisateur 
	 * @param userConnecte
	 * @return
	 * @throws SQLException 
	 */
	public static Boolean resetCommandeEnCours(Utilisateur userConnecte) throws SQLException{
		Connection cnx = null;
		PreparedStatement rqt = null;
		
		try 
		{
			cnx = AccesBase.getConnect();
			rqt = cnx.prepareStatement("UPDATE COMMANDE SET etat = 'En attente', idUser = NULL WHERE idUser = ? AND etat = 'En cours de traitement'");
			rqt.setString(1, userConnecte.getId());
			return rqt.executeUpdate() > 0;
		}
		finally 
		{
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
	}

	/**
	 * Méthode en charge de récupérer le nombre de commandes traitées dans la journée par
	 * l'utilisateur passé en paramètre 
	 * @param identifiant Identifiant de l'utilisateur passé en paramètre
	 * @return Le nombre de commandes traitées
	 * @throws SQLException 
	 */
	public static int getCommandesTraitees(String identifiant) throws SQLException {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) FROM COMMANDE "+
					 "WHERE COMMANDE.idUser = ? AND etat = 'Traitée' "+
					 "AND CONVERT(VARCHAR(10),COMMANDE.dateTraitement,110) = CONVERT(VARCHAR(10),GETDATE(),110)";
		
		try 
		{
			cnx = AccesBase.getConnect();
			rqt = cnx.prepareStatement(sql);
			rqt.setString(1, identifiant);
			rs = rqt.executeQuery();
			
			if(rs.next()){
				return rs.getInt(1);
			}else{
				return 0;
			}
			
		}
		finally 
		{
			if(rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
	}

	/**
	 * Méthode en charge de passer la commande à l'état traitée 
	 * @param commande Commande concernée
	 * @return Vrai si il y a eu des modifications sinon faux
	 * @throws SQLException 
	 */
	public static boolean setEtatTraitee(Commande commande) throws SQLException{
		Connection cnx = null;
		PreparedStatement rqt = null;
		String sql = "UPDATE COMMANDE SET etat='Traitée',dateTraitement=GETDATE() "+
					 "WHERE num = ?";
		
		try 
		{
			cnx = AccesBase.getConnect();
			rqt = cnx.prepareStatement(sql);
			rqt.setInt(1, commande.getNum());
			return rqt.executeUpdate() > 0;
			
		}
		finally 
		{
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
	}
}

package fr.eni_ecole.expeditor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.eni_ecole.expeditor.bean.LigneCommande;

public class DAOLigneCommande 
{
	/**
	 * Méthode en charge de récupérer les lignes associées à une commande
	 * @param numCommande : le numéro de la commande
	 * @return ArrayList<LigneCommande> : la liste des lignes composant la commande
	 * @throws SQLException
	 */
	public static ArrayList<LigneCommande> getLignes(int numCommande) throws SQLException
	{
		ArrayList<LigneCommande> lesLignesCommande = new ArrayList<LigneCommande>();
		
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		
		try
		{
			cnx = AccesBase.getConnect();
			rqt = cnx.prepareStatement("SELECT * FROM LIGNE_COMMANDE WHERE numCommande = ?");			
			rqt.setInt(1, numCommande);
			rs = rqt.executeQuery();
			
			LigneCommande uneLigneCommande;
			while (rs.next())
			{
				uneLigneCommande = new LigneCommande(
									rs.getInt("numCommande"),
									rs.getString("refArticle"),
									rs.getInt("quantite")
						);
				lesLignesCommande.add(uneLigneCommande);				
			}		
		}
		finally
		{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		return lesLignesCommande;
	}
}

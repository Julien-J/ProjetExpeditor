package fr.eni_ecole.expeditor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.eni_ecole.expeditor.bean.Utilisateur;


/**
 * ProjectExpeditor Version 1.0
 * @author d1410lheraultj
 * 21 juin 2016
 */
public class DAOUtilisateur
{
	/**
	 * M�thode en charge de r�cup�rer un Utilisateur
	 * @param idUtilisateur : identifiant de l'utilisateur � r�cup�rer
	 * @return Objet Utilisateur
	 * @throws SQLException
	 */
	public Utilisateur getUtilisateur(String idUtilisateur) throws SQLException
	{
		Utilisateur lUtilisateur = new Utilisateur();
		
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		
		try
		{
			cnx = AccesBase.getConnect();
			rqt = cnx.prepareStatement("SELECT login, nom, prenom, mdp, statut FROM UTILISATEUR WHERE login=?");
			rqt.setString(1, idUtilisateur);
			rs = rqt.executeQuery();
			
			if (rs.next())
			{
				lUtilisateur.setId(rs.getString("id"));
				lUtilisateur.setLogin(rs.getString("login"));
				lUtilisateur.setNom(rs.getString("nom"));
				lUtilisateur.setPrenom(rs.getString("prenom"));
				lUtilisateur.setMotDePasse(rs.getString("mdp"));
				lUtilisateur.setStatut(rs.getString("statut"));
			}
			else
			{
				lUtilisateur = null;
			}		
		}
		finally
		{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		return lUtilisateur;
	}
	
	/**
	 * M�thode en charge de r�cup�rer tous les utilisateurs en base
	 * @return ArrayList<Utilisateur> : la liste des utilisateurs
	 * @throws SQLException
	 */
	public ArrayList<Utilisateur> getAllUtilisateur() throws SQLException
	{
		ArrayList<Utilisateur> lesUtilisateurs = new ArrayList<Utilisateur>();
		
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		
		try
		{
			cnx = AccesBase.getConnect();
			rqt = cnx.prepareStatement("SELECT * FROM UTILISATEUR");			
			rs = rqt.executeQuery();
			Utilisateur unUtilisateur;
			while (rs.next())
			{
				unUtilisateur = new Utilisateur(
									rs.getString("id"),
									rs.getString("login"),
									rs.getString("nom"),
									rs.getString("prenom"),
									rs.getString("mdp"),
									rs.getString("statut")
						);
				lesUtilisateurs.add(unUtilisateur);				
			}		
		}
		finally
		{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		return lesUtilisateurs;
	}
	
	/**
	 * M�thode en charge d'ins�rer un nouvel utilisateur en base
	 * @param unUtilisateur : l'utilisateur � ins�rer
	 * @throws SQLException
	 */
	public String insertUtilisateur(Utilisateur unUtilisateur) throws SQLException 
	{
		String retourIdentifiant = null;
		
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		
		try 
		{
			cnx = AccesBase.getConnect();
			rqt = cnx.prepareStatement("INSERT INTO UTILISATEUR(login, nom, prenom, mdp, statut) VALUES (?,?,?,?,?) SELECT SCOPE_IDENTITY()");
			rqt.setString(1, unUtilisateur.getLogin());
			rqt.setString(2, unUtilisateur.getNom());
			rqt.setString(3, unUtilisateur.getPrenom());
			rqt.setString(4, unUtilisateur.getMotDePasse());
			rqt.setString(5, unUtilisateur.getStatut());
			rqt.executeUpdate();
			rs = rqt.getGeneratedKeys();
			while (rs.next())
			{
				retourIdentifiant = rs.getString(1);
			}
		}
		finally 
		{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		return retourIdentifiant;
	}
	
	/**
	 * M�thode en charge de mettre � jour un utilisateur en base
	 * @param unUtilisateur : l'utilisateur � mettre � jour
	 * @throws SQLException
	 */
	public void updateUtilisateur(Utilisateur unUtilisateur) throws SQLException
	{
		Connection cnx = null;
		PreparedStatement rqt = null;
		
		try
		{
			cnx = AccesBase.getConnect();
			rqt = cnx.prepareStatement("UPDATE UTILISATEUR SET login = ?, nom = ?, prenom = ?, mdp = ?, statut = ? WHERE id = ?");
			rqt.setString(1, unUtilisateur.getLogin());
			rqt.setString(2, unUtilisateur.getNom());
			rqt.setString(3, unUtilisateur.getPrenom());
			rqt.setString(4, unUtilisateur.getMotDePasse());
			rqt.setString(5, unUtilisateur.getStatut());
			rqt.setString(6, unUtilisateur.getId());

			rqt.executeUpdate();
		}
		finally
		{
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
	}
	
	/**
	 * M�thode en charge de supprimer un utilisateur de la base
	 * @param unUtilisateur : l'utilisateur � supprimer
	 * @throws SQLException
	 */
	public void deleteUtilisateur(Utilisateur unUtilisateur) throws SQLException
	{
		Connection cnx = null;
		PreparedStatement rqt = null;
		
		try
		{
			cnx=AccesBase.getConnect();
			rqt=cnx.prepareStatement("DELETE FROM UTILISATEUR WHERE id = ?");
			rqt.setString(1, unUtilisateur.getId());
			rqt.executeUpdate();
		}
		finally
		{
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
	}
}

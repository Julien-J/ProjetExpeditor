package fr.eni_ecole.expeditor.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.eni_ecole.expeditor.bean.Utilisateur;
import fr.eni_ecole.expeditor.utils.OutilsString;


/**
 * ProjectExpeditor Version 1.0
 * @author d1410lheraultj
 * 21 juin 2016
 */
public class DAOUtilisateur
{
	/**
	 * M�thode en charge de rechercher la correspondance d'un login/mdp
	 * @param login : login saisi
	 * @param motDePasse : mot de passe saisi
	 * @return Objet Utilisateur ou null
	 * @throws SQLException
	 */
	@SuppressWarnings("null")
	public static Utilisateur rechercher(String login, String motDePasse) throws SQLException
	{
		Utilisateur utilisateurConnecte = null;
		
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		
		try
		{
			cnx = AccesBase.getConnect();
			rqt = cnx.prepareStatement("SELECT id, nom, prenom, statut FROM UTILISATEUR WHERE login = ? and mdp = ?");
			rqt.setString(1, login);
			rqt.setString(2, motDePasse);
			rs=rqt.executeQuery();
			
			if (rs.next())
			{
				utilisateurConnecte = new Utilisateur();
				utilisateurConnecte.setId(rs.getString("id"));
				utilisateurConnecte.setLogin(login);
				utilisateurConnecte.setNom(rs.getString("nom"));
				utilisateurConnecte.setPrenom(rs.getString("prenom"));
				utilisateurConnecte.setMotDePasse(motDePasse);
				utilisateurConnecte.setStatut(rs.getString("statut"));
			}
			else 
			{
				utilisateurConnecte = null;
			}
		}
		finally
		{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		return utilisateurConnecte;
	}
	
	/**
	 * M�thode en charge de r�cup�rer un Utilisateur
	 * @param idUtilisateur : identifiant de l'utilisateur � r�cup�rer
	 * @return Objet Utilisateur
	 * @throws SQLException
	 */
	public static Utilisateur getUtilisateur(String idUtilisateur) throws SQLException
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
	public static ArrayList<Utilisateur> getAllUtilisateur() throws SQLException
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
									rs.getString("nom"),
									rs.getString("prenom"),
									rs.getString("login"),
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
	 * @throws NoSuchAlgorithmException 
	 */
	public static String insertUtilisateur(Utilisateur unUtilisateur) throws SQLException, NoSuchAlgorithmException 
	{
		String retourIdentifiant = null;
		
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		
		try 
		{
			cnx = AccesBase.getConnect();
			rqt = cnx.prepareStatement("DECLARE @id uniqueidentifier; SET @id = newid(); INSERT INTO UTILISATEUR VALUES (@id, ?, ?, ?, ?, ?); SELECT @id;");
			rqt.setString(1, unUtilisateur.getLogin());
			rqt.setString(2, unUtilisateur.getNom());
			rqt.setString(3, unUtilisateur.getPrenom());
			rqt.setString(4, OutilsString.convertTOMD5(unUtilisateur.getMotDePasse()));
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
	 * @throws NoSuchAlgorithmException 
	 */
	public static void updateUtilisateur(Utilisateur unUtilisateur) throws SQLException, NoSuchAlgorithmException
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
			rqt.setString(4, OutilsString.convertTOMD5(unUtilisateur.getMotDePasse()));
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
	public static Boolean deleteUtilisateur(Utilisateur unUtilisateur) throws SQLException
	{
		Connection cnx = null;
		PreparedStatement rqt = null;
		
		try
		{
			cnx=AccesBase.getConnect();
			rqt=cnx.prepareStatement("DELETE FROM UTILISATEUR WHERE id = ?");
			rqt.setString(1, unUtilisateur.getId());
			return rqt.executeUpdate() > 0;
		}
		finally
		{
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
	}
}

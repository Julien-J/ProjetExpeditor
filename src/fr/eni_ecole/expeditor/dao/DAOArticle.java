/**
 * 
 */
package fr.eni_ecole.expeditor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni_ecole.expeditor.bean.Article;

/**
 * ProjectExpeditor Version 1.0
 * @author d1408davidj
 * 20 juin 2016
 */
public class DAOArticle 
{
	/**
	 * Méthode en charge de récupérer un Article
	 * @param refArticle : référence de l'article à récupérer
	 * @return Objet Article
	 * @throws SQLException
	 */
	public Article getArticle(String refArticle) throws SQLException
	{
		Article lArticle = new Article();
		
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		
		try
		{
			cnx = AccesBase.getConnect();
			rqt = cnx.prepareStatement("SELECT ref, libelle, description, poids FROM ARTICLE WHERE ref=?");
			rqt.setString(1, refArticle);
			rs = rqt.executeQuery();
			
			if (rs.next())
			{
				lArticle.setRef(rs.getString("ref"));
				lArticle.setLibelle(rs.getString("libelle"));
				lArticle.setDescription(rs.getString("description"));
				lArticle.setPoids(rs.getInt("poids"));
			}
			else
			{
				lArticle = null;
			}		
		}
		finally
		{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		return lArticle;
	}
	
	/**
	 * Méthode en charge d'insérer un nouvel article en base
	 * @param unArticle : l'article à insérer
	 * @throws SQLException
	 */
	public String insertArticle(Article unArticle) throws SQLException 
	{
		String retourReference = null;
		
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		
		try 
		{
			cnx = AccesBase.getConnect();
			rqt = cnx.prepareStatement("INSERT INTO ARTICLE(libelle, description, poids) VALUES (?,?,?) SELECT SCOPE_IDENTITY()");
			rqt.setString(1, unArticle.getLibelle());
			rqt.setString(2, unArticle.getDescription());
			rqt.setInt(3, unArticle.getPoids());
			rqt.executeUpdate();
			rs = rqt.getGeneratedKeys();
			while (rs.next())
			{
				retourReference = rs.getString(1);
			}
		}
		finally 
		{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		return retourReference;
	}
}

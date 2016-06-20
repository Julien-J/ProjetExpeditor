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
	 * M�thode en charge de r�cup�rer un Article
	 * @param refArticle : r�f�rence de l'article � r�cup�rer
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
	 * M�thode en charge d'ins�rer un nouvel article en base
	 * @param unArticle : l'article � ins�rer
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

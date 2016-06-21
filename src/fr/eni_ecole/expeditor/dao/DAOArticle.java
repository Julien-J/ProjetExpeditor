package fr.eni_ecole.expeditor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	 * M�thode en charge de r�cup�rer tous les articles en base
	 * @return ArrayList<Article> : la liste des articles
	 * @throws SQLException
	 */
	public ArrayList<Article> getAllArticle() throws SQLException
	{
		ArrayList<Article> lesArticles = new ArrayList<Article>();
		
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		
		try
		{
			cnx = AccesBase.getConnect();
			rqt = cnx.prepareStatement("SELECT * FROM ARTICLE");			
			rs = rqt.executeQuery();
			Article unArticle;
			while (rs.next())
			{
				unArticle = new Article(
									rs.getString("ref"),
									rs.getString("libelle"),
									rs.getString("description"),
									rs.getInt("poids")
						);
				lesArticles.add(unArticle);				
			}		
		}
		finally
		{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		return lesArticles;
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
	
	/**
	 * M�thode en charge de mettre � jour un article en base
	 * @param unArticle : l'article � mettre � jour
	 * @throws SQLException
	 */
	public void updateArticle(Article unArticle) throws SQLException
	{
		Connection cnx = null;
		PreparedStatement rqt = null;
		
		try
		{
			cnx = AccesBase.getConnect();
			rqt = cnx.prepareStatement("UPDATE ARTICLE SET libelle = ?, description = ?, poids = ? WHERE ref = ?");
			rqt.setString(1, unArticle.getLibelle());
			rqt.setString(2, unArticle.getDescription());
			rqt.setInt(3, unArticle.getPoids());
			rqt.setString(4, unArticle.getRef());

			rqt.executeUpdate();
		}
		finally
		{
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
	}
	
	/**
	 * M�thode en charge de supprimer un article de la base
	 * @param unArticle : l'article � supprimer
	 * @throws SQLException
	 */
	public void deleteArticle(Article unArticle) throws SQLException
	{
		Connection cnx = null;
		PreparedStatement rqt = null;
		
		try
		{
			cnx=AccesBase.getConnect();
			rqt=cnx.prepareStatement("DELETE FROM ARTICLE WHERE ref = ?");
			rqt.setString(1, unArticle.getRef());
			rqt.executeUpdate();
		}
		finally
		{
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
	}
}

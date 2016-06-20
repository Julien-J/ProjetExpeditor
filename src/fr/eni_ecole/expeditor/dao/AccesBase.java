package fr.eni_ecole.expeditor.dao;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;

/**
 * ProjectExpeditor Version 1.0
 * @author d1410lheraultj
 * 20 juin 2016
 */
public class AccesBase 
{
	public static Connection getConnect() throws SQLException
	{
		Connection cnx = null;

		// Charger l'annuaire JNDI
		InitialContext jndi = null;
		try 
		{
			jndi = new InitialContext();
		} 
		catch (NamingException ne) 
		{
			ne.printStackTrace();
			throw new SQLException("Impossible d'atteindre l'arbre JNDI");
		}
		
				
		// Chercher le pool de connexion 
		DataSource ds = null;
		try 
		{
			ds = (DataSource)jndi.lookup("java:comp/env/jdbc/dsExpeditor");
		} 
		catch (NamingException ne) 
		{
			ne.printStackTrace();
			throw new SQLException("Pool de connexion introuvable dans l'arbre JNDI");
		}
		
		// Activer une connexion du Pool
		cnx = ds.getConnection();
		
		return cnx;
	}
}

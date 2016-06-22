package fr.eni_ecole.expeditor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.eni_ecole.expeditor.bean.Client;

public class DAOClient 
{
	/**
	 * M�thode en charge de r�cup�rer un Client
	 * @param numClient : num�ro du Client � r�cup�rer
	 * @return Objet Client
	 * @throws SQLException
	 */
	public static Client getClient(String numClient) throws SQLException
	{
		Client leClient = new Client();
		
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		try
		{
			cnx = AccesBase.getConnect();
			rqt = cnx.prepareStatement("SELECT num, nom, adresse FROM CLIENT WHERE num = ?");
			rqt.setString(1, numClient);
			rs = rqt.executeQuery();
			
			if (rs.next())
			{
				leClient.setNum(rs.getString("num"));
				leClient.setNom(rs.getString("nom"));
				leClient.setAdresse(rs.getString("adresse"));
			}
			else
			{
				leClient = null;
			}		
		}
		finally
		{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}

		System.out.println("Le client " +leClient);
		return leClient;
	}
	
	/**
	 * M�thode en charge de r�cup�rer tous les Clients en base
	 * @return ArrayList<Client> : la liste des Clients
	 * @throws SQLException
	 */
	public static ArrayList<Client> getAllClient() throws SQLException
	{
		ArrayList<Client> lesClients = new ArrayList<Client>();
		
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		
		try
		{
			cnx = AccesBase.getConnect();
			rqt = cnx.prepareStatement("SELECT * FROM CLIENT");			
			rs = rqt.executeQuery();
			Client unClient;
			while (rs.next())
			{
				unClient = new Client(
									rs.getString("num"),
									rs.getString("nom"),
									rs.getString("adresse")
						);
				lesClients.add(unClient);				
			}		
		}
		finally
		{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		return lesClients;
	}
}

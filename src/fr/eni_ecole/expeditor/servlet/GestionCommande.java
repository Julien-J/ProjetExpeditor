package fr.eni_ecole.expeditor.servlet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni_ecole.expeditor.bean.Client;
import fr.eni_ecole.expeditor.bean.Commande;
import fr.eni_ecole.expeditor.bean.enums.EtatCommande;
import fr.eni_ecole.expeditor.dao.DAOClient;
import fr.eni_ecole.expeditor.dao.DAOCommande;

/**
 * Servlet implementation class GestionCommande
 */
@WebServlet("/employe/commande")
public class GestionCommande extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public GestionCommande() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try {
			processRequest(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try {
			processRequest(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException 
	{
		RequestDispatcher dispatcher = null;
		String action = request.getParameter("action");
		
		if ("valid_cmd".equals(action)) 
		{
			// Récupération du paramètre Numéro de Commande
			Integer numCommande = Integer.parseInt(request.getParameter("cmd"));
			
			// Récupération de la commande associée
			Commande laCommandeTraitee = DAOCommande.getCommande(numCommande);
			
			// Mise à Jour de l'état à "Traitée"
			try 
			{
				DAOCommande.setEtatTraitee(laCommandeTraitee);
			} 
			catch (Exception e) 
			{
				System.out.println("Erreur lors de la mise à jour de la commande : " + e.getMessage());
			}
		}
		else if ("take_charge".equals(action))
		{
			// Récupération du paramètre Numéro de Commande et l'id de l'utilisateur
			Integer numCommande = Integer.parseInt(request.getParameter("cmd"));
			String idUser = request.getParameter("user");
			
			// Récupération de la commande associée
			Commande laCommandeEnCharge = DAOCommande.getCommande(numCommande);
			
			try 
			{
				DAOCommande.setEtatEnCours(laCommandeEnCharge, idUser);
			
				Commande commandeATraiter = getFirstCommand();
				
				request.getSession().setAttribute("commandeATraiter", commandeATraiter);
				dispatcher = request.getRequestDispatcher("/employe/gestionCommande.jsp"); 
				dispatcher.forward(request, response);	
			} catch (Exception e) 
			{
				e.printStackTrace();
			}			
		}
		else
		{
			Commande commandeATraiter = getFirstCommand();
			
			request.getSession().setAttribute("commandeATraiter", commandeATraiter);
			dispatcher = request.getRequestDispatcher("/employe/gestionCommande.jsp"); 
			dispatcher.forward(request, response);			
		}
	}
	
	/**
	 * Méthode en charge de parcourir le fichier de commande 
	 * et de retourner la première commande en attente
	 * @return Objet Commande
	 * @throws IOException
	 * @throws SQLException
	 */
	private Commande getFirstCommand() throws IOException, SQLException
	{
		BufferedReader reader = new BufferedReader(new FileReader(getServletContext().getRealPath("ressources/commandes.csv")));
		List<String> lines = new ArrayList<>();
		
		Commande firstCommand = null;
		String line = null;
		
		while ((line = reader.readLine()) != null) 
		{
			lines.add(line);
		}
		
		for (String l : lines.subList(1, lines.size()))
		{
			String[] strings = l.split(",");
			String numCommande = strings[1].substring(8);
			
			if (DAOCommande.getCommande(Integer.parseInt(numCommande)).getEtat() == EtatCommande.ATTENTE)
			{
				firstCommand = DAOCommande.getCommande(Integer.parseInt(numCommande));
				break;
			}
		}
		
		return firstCommand;
	}
}

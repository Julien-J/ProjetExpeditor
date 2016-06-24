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
			// R�cup�ration du param�tre Num�ro de Commande
			Integer numCommande = Integer.parseInt(request.getParameter("cmd"));
			
			// R�cup�ration de la commande associ�e
			Commande laCommandeTraitee = DAOCommande.getCommande(numCommande);
			
			// Mise � Jour de l'�tat � "Trait�e"
			try 
			{
				DAOCommande.setEtatTraitee(laCommandeTraitee);				
			} 
			catch (Exception e) 
			{
				System.out.println("Erreur lors de la mise � jour de la commande : " + e.getMessage());
			}
		}
		else if ("take_charge".equals(action))
		{
			// R�cup�ration du param�tre Num�ro de Commande et l'id de l'utilisateur
			Integer numCommande = Integer.parseInt(request.getParameter("cmd"));
			String idUser = request.getParameter("user");
			
			// R�cup�ration de la commande associ�e
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
		else if("bon_livraison".equals(action))
		{
			request.getRequestDispatcher("/employe/bonLivraison.jsp").forward(request, response);
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
	 * M�thode en charge de parcourir le fichier de commande 
	 * et de retourner la premi�re commande en attente
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
			String numCommande = strings[1].substring("Cmd N° ".length());
			
			if (DAOCommande.getCommande(Integer.parseInt(numCommande)).getEtat() == EtatCommande.ATTENTE)
			{
				firstCommand = DAOCommande.getCommande(Integer.parseInt(numCommande));
				break;
			}
		}
		
		return firstCommand;
	}
}

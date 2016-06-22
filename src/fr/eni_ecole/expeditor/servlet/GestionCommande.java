package fr.eni_ecole.expeditor.servlet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni_ecole.expeditor.bean.Commande;

/**
 * Servlet implementation class GestionCommande
 */
@WebServlet("/employe/commande")
public class GestionCommande extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public GestionCommande() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		processRequest(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}
	
	private Commande getFirstCommand() throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(""));
		List<String> lines = new ArrayList<>();
		String line = null;
		while ((line = reader.readLine()) != null) 
		{
			
		}
		
		System.out.println(lines.get(1));
		
		return null;
	}
}

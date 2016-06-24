package fr.eni_ecole.expeditor.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.parser.ParserBasicInformation;

import com.google.gson.Gson;

import fr.eni_ecole.expeditor.bean.Article;
import fr.eni_ecole.expeditor.bean.Client;
import fr.eni_ecole.expeditor.bean.Commande;
import fr.eni_ecole.expeditor.bean.Utilisateur;
import fr.eni_ecole.expeditor.dao.DAOArticle;
import fr.eni_ecole.expeditor.dao.DAOClient;
import fr.eni_ecole.expeditor.dao.DAOCommande;
import fr.eni_ecole.expeditor.dao.DAOUtilisateur;

/**
 * Servlet implementation class Commandes
 */
@WebServlet("/manager/commandes")
public class Commandes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Commandes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init();
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.service(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher dispatcher;
		
		String action = request.getParameter("action");
		if ("get_commande".equals(action)){
			PrintWriter out = null;
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			Commande maCommande = new Commande();
			Utilisateur employe = new Utilisateur();
			Client client = new Client();
			Article article = new Article();
			
			Gson gson = new Gson();
			try {
				maCommande = DAOCommande.getCommande(Integer.parseInt(request.getParameter("numero")));
				employe = DAOUtilisateur.getUtilisateur(maCommande.getIdEmploye());
				client = DAOClient.getClient(maCommande.getNumClient()); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				out = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
			map.put("commandeData", maCommande);
			System.out.println(map);
			if (employe==null){
				map.put("employeData", null);
			}else{
				map.put("employeData", employe);
			}
			map.put("clientData", client);
			
			out.println(gson.toJson(map));
			out.flush();
		} else if("get_article".equals(action)){
			PrintWriter out = null;
			Article article = new Article();
			Gson gson = new Gson();
			try {
				article = DAOArticle.getArticle(request.getParameter("idArticle"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				out = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
			out.println(gson.toJson(article));
			out.flush();
		}else{
			try {
				ArrayList<Commande> lesCommandes = new ArrayList<Commande>();
				try {
					lesCommandes = DAOCommande.getAllCommande();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				request.getSession().setAttribute("listeCommandes", lesCommandes);
				request.getRequestDispatcher("/manager/listeCommande.jsp").forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

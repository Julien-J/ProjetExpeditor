package fr.eni_ecole.expeditor.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni_ecole.expeditor.bean.Article;
import fr.eni_ecole.expeditor.bean.Commande;
import fr.eni_ecole.expeditor.dao.DAOArticle;
import fr.eni_ecole.expeditor.dao.DAOCommande;

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
		//String action = request.getParameter("action");
		//if ("getArticles".equals(action)) {
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

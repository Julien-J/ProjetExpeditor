package fr.eni_ecole.expeditor.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import fr.eni_ecole.expeditor.bean.Article;
import fr.eni_ecole.expeditor.dao.DAOArticle;

/**
 * Servlet implementation class Articles
 */
@WebServlet("/manager/articles")
public class Articles extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Articles() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) {

		System.out.println("début_article");
		RequestDispatcher dispatcher;
		String action = request.getParameter("action");
		if ("add_article".equals(action)) {
			System.out.println("add_article");
			Article monArticle = new Article();
			String libelle = request.getParameter("libelle");
			String description = request.getParameter("description");
			Integer poids = Integer.parseInt(request.getParameter("poids"));
			System.out.println(poids);
			if (!libelle.isEmpty()) {
				monArticle.setLibelle(libelle);
			}
			if (!description.isEmpty()) {
				monArticle.setDescription(description);
			}
			monArticle.setPoids(poids);
			try {
				DAOArticle.insertArticle(monArticle);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if ("get_article".equals(action)) {

			PrintWriter out = null;
			Article monArticle = new Article();
			Gson gson = new Gson();
			try {
				monArticle = DAOArticle.getArticle(request.getParameter("reference"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				out = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
			out.println(gson.toJson(monArticle));
			out.flush();

		} else if ("delete_article".equals(action)) {
			System.out.println("delete_article");
			String reference = request.getParameter("reference");
			System.out.println(reference);
			Article monArticle = new Article();
			try {
				monArticle = DAOArticle.getArticle(reference);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				DAOArticle.deleteArticle(monArticle);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("liste_article");
			try {
				ArrayList<Article> mesArticles = new ArrayList<Article>();
				try {
					mesArticles = DAOArticle.getAllArticle();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				request.getSession().setAttribute("listeArticles", mesArticles);
				request.getRequestDispatcher("/manager/gestionArticle.jsp").forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

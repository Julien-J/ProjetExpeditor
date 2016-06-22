package fr.eni_ecole.expeditor.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni_ecole.expeditor.bean.Commande;
import fr.eni_ecole.expeditor.bean.Utilisateur;
import fr.eni_ecole.expeditor.dao.DAOCommande;

/**
 * Servlet implementation class Deconnexion
 */
@WebServlet(description = "Servlet de gestion de la déconnexion", urlPatterns = { "/deconnexion" })
public class Deconnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Deconnexion() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
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
		processRequest(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Utilisateur userConnecte = (Utilisateur)request.getSession().getAttribute("user");
		
		if("employe".equals(userConnecte.getStatut())){			
			try {
				DAOCommande.resetCommandeEnCours(userConnecte);
				request.getSession(false).invalidate();
				response.sendRedirect(request.getContextPath()+"/");
			} catch (SQLException e) {
				request.setAttribute("erreur", e);
				request.getRequestDispatcher("/erreur.jsp").forward(request, response);
			}
		}else{
			request.getSession(false).invalidate();
			response.sendRedirect(request.getContextPath()+"/");
		}
		
	}
	

}

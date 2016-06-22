package fr.eni_ecole.expeditor.servlet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni_ecole.expeditor.bean.Utilisateur;
import fr.eni_ecole.expeditor.dao.DAOUtilisateur;
import fr.eni_ecole.expeditor.utils.OutilsString;

/**
 * Servlet implementation class Connexion
 */
@WebServlet(description = "Servlet d'authentification", urlPatterns = { "/connexion" })
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Connexion() {
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

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String identifiant = request.getParameter("id");
		String motdepasse = request.getParameter("pass");
		Utilisateur user = new Utilisateur();
		RequestDispatcher dp = null;

		try{			
			if(request.getSession().getAttribute("user") == null){
				user = DAOUtilisateur.rechercher(identifiant,OutilsString.convertTOMD5(motdepasse));
				if(user == null){
					dp = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("message", "Identifiant ou mot de passe incorrect");
					dp.forward(request, response);
				} else{
					request.getSession().setAttribute("user", user);
					//Si c'est un employe => écran commande
					//Si c'est un manager => écran gestion des articles
					if("employe".equals(user.getStatut())){
						dp = request.getRequestDispatcher("/gestionCommande");
					}else{
						dp = request.getRequestDispatcher("/articles");
					}
					dp.forward(request, response);
				}			
				
			}else{
				user = (Utilisateur)request.getSession().getAttribute("user");
				if(user.getStatut() == ""){
					dp = request.getRequestDispatcher("/gestionCommande");
				}else{
					dp = request.getRequestDispatcher("/articles");
				}
				dp.forward(request, response);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			dp = request.getRequestDispatcher("/erreur.jsp");
			request.setAttribute("erreur", e);
			dp.forward(request, response);
		}
	}

}

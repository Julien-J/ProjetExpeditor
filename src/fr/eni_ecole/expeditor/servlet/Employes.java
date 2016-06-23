package fr.eni_ecole.expeditor.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import fr.eni_ecole.expeditor.bean.Utilisateur;
import fr.eni_ecole.expeditor.dao.DAOUtilisateur;
import fr.eni_ecole.expeditor.utils.OutilsString;

/**
 * Servlet implementation class Employes
 */
@WebServlet(description = "Servlet de gestion des employés", urlPatterns = { "/manager/employes" })
public class Employes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Employes() {
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
		RequestDispatcher dp = null;
		PrintWriter out = response.getWriter();
		String action = request.getParameter("action");
		Gson gson = null;
		response.setContentType("application/json");
		
		try {
			if("edit".equals(action)){
				String mode = request.getParameter("mode");			
				gson = new Gson();
				
				Utilisateur userToEdit = new Utilisateur();
				userToEdit.setNom(request.getParameter("nom").toUpperCase());
				userToEdit.setPrenom(request.getParameter("prenom"));
				userToEdit.setLogin(request.getParameter("login"));
				userToEdit.setStatut(request.getParameter("statut"));
				
				if("ajouter".equals(mode)){
					String password = OutilsString.generatePassword();
					System.out.println(password);
					userToEdit.setMotDePasse(password);
					String id = DAOUtilisateur.insertUtilisateur(userToEdit);
					
					if(id != null && !id.isEmpty()) {
						userToEdit.setId(id);	
						out.print(gson.toJson(userToEdit));
					}else{
						out.print(false);
					}
				}else if("modifier".equals(mode)){
					userToEdit.setId(request.getParameter("id"));					
					Boolean ok = DAOUtilisateur.updateUtilisateur(userToEdit);
					
					if(ok)
						out.print(gson.toJson(userToEdit));
					else
						out.print(false);
				}
					
				out.flush();
			}else if ("supprimer".equals(action)){
				Utilisateur userToDelete = new Utilisateur();
				userToDelete.setId(request.getParameter("id"));
				out.print(DAOUtilisateur.deleteUtilisateur(userToDelete));
				out.flush();
			} else if("get_employe".equals(action)){
				gson = new Gson();
				out.print(gson.toJson(DAOUtilisateur.getUtilisateur(request.getParameter("id"))));
				out.flush();
			}else{
				dp = request.getRequestDispatcher("/manager/gestionEmployes.jsp");
				request.setAttribute("employes", DAOUtilisateur.getAllUtilisateur());			
				dp.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dp = request.getRequestDispatcher("/erreur.jsp");
			request.setAttribute("erreur", e);
			dp.forward(request, response);
		}
	}


}

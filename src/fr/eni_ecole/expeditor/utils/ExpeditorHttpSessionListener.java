/**
 * 
 */
package fr.eni_ecole.expeditor.utils;

import java.sql.SQLException;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import fr.eni_ecole.expeditor.bean.Utilisateur;
import fr.eni_ecole.expeditor.dao.DAOCommande;

/**
 * ProjectExpeditor Version 1.0
 * @author d1408davidj
 * 22 juin 2016
 *
 */
public class ExpeditorHttpSessionListener implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		event.getSession().setMaxInactiveInterval(120*60);//2h en secondes
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		Utilisateur userConnecte = (Utilisateur)event.getSession().getAttribute("user");		
		if("employe".equals(userConnecte.getStatut())){			
			try {
				DAOCommande.resetCommandeEnCours(userConnecte);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}

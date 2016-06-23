/**
 * Fonction générique
 */

$(document).ready(function(){
	
	/**
	 * Recharge la page au click
	 */
	reloadPage = function(){
		window.location.reload();
	};
	
	/**
	 * Gestion de la séléction d'un élément du menu
	 * @param li Menu cliqué
	 */
	selectionMenu = function(li){
		//On enleve l'ancien li active
		$(".liMenu.active").removeClass("active");
		//ON place le nouveau li active
		$(li).addClass("active");	
	};
	
	/**
	 * Focntion en charge d'afficher un message de succès
	 * @param titre
	 * @param message
	 */
	showDialogSucces = function(titre,message){
		$("#alert_success #alert_success_title h4").html(titre);
		$("#alert_success #alert_success_content").html(message);
		$("#alert_success").modal("show");
	};
	
});
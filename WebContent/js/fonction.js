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
	
});
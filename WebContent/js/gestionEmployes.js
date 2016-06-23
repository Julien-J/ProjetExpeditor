/**
 * Fonctions Gestion Employés
 */
$(document).ready(function(){
	
	/**
	 * Fonction en charge d'ajouter un employé en BDD
	 */
	add_employe = function(){
		console.log($("input#nomEmploye"));
		var message = verificationAjoutEmploye();
		if(message == null){
			
		}else{
			$.ajax({
				url : 'employes',
				method : 'POST',
				data : 'action=ajouter&nom='+$("input#nomEmploye").val()+'&prenom='+$("input#prenomEmploye").val()+
						'&login='+$("input#loginEmploye").val()+'&statut='+$("#statutEmploye").val(),
				success : function(data){
					
				}
			});
		}
	};
	
	/**
	 * Fonction en charge de vérifier les données saisies
	 */
	verificationAjoutEmploye = function(){
		
		return "ok";
	};
	
	/**
	 * Fonction en charge de générer un login en fonction du nom et du prénom
	 */
	createLogin = function(){
		if($("#nomEmploye").val() != "" 
			&& $("#prenomEmploye").val() != ""){
			var regex = /[ -]+/;
			//Login = première lettre du prénom suivi du premier mot du nom de famille
			var login = $("#prenomEmploye").val().charAt(0) + $("#nomEmploye").val().split(regex)[0];
			$("#loginEmploye").val(login.toLowerCase());
		}
	};

	/**
	 * Fonction en charge de supprimer un employé
	 */
	delete_employe = function(){
		
	};
});
/**
 * Fonctions Gestion Employés
 */
$(document).ready(function(){
	
	/**
	 * Contient la ligne en cours de modification
	 */
	var idTrModifie = 0;
	
	/**
	 * Fonction en charge d'afficher la boite de dialog en mode ajout
	 */
	add_employe = function(){
		$("#detailEmploye .modal-title").text("Ajouter un employé");
		$("#btn_enregistrer")[0].dataset.mode = "ajouter";
		$("#alert_erreur_enregistrer").remove();
		$("input#idEmploye").val("");
		$("input#nomEmploye").val("");
		$("input#prenomEmploye").val("");
		$("input#loginEmploye").val("");
		$("input#statutEmploye").val("employe");
		$("#btn_enregistrer")[0].dataset.dismiss = "";
	};
	
	/**
	 * Fonction en charge d'afficher le détail d'un employé
	 * @param button
	 */
	edit_employe = function(button){		
		$("#detailEmploye .modal-title").text("Modifier un employé");
		$("#alert_erreur_enregistrer").remove();
		$("#btn_enregistrer")[0].dataset.mode = "modifier";
		$("#btn_enregistrer")[0].dataset.dismiss = "";
		idTrModifie = $(button)[0].dataset.tr;
		$.ajax({
			url : "employes",
			method : "POST",
			data : "action=get_employe&id="+$(button)[0].dataset.id,
			success : function(data){
				if(Boolean(data) == false){
					
				}else if(data != null){
					$("input#idEmploye").val(data.id);
					$("input#nomEmploye").val(data.nom);
					$("input#prenomEmploye").val(data.prenom);
					$("input#loginEmploye").val(data.login);
					$("input#statutEmploye").val(data.statut);
				}
			}
		});
	};
	
	/**
	 * Fonction en charge d'ajouter un employé en BDD
	 */
	enregistrer_employe = function(button){
		var mode = $(button)[0].dataset.mode;
		var message = verificationAjoutEmploye();
		if(message != null){
			$(message).insertAfter($("#detailEmploye .modal-content .modal-header"));
		}else{
			$("#btn_enregistrer")[0].dataset.dismiss = "modal";
			$.ajax({
				url : 'employes',
				method : 'POST',
				data : 'action=edit&mode='+mode+'&id='+$("input#idEmploye").val()+'&nom='+$("input#nomEmploye").val()+'&prenom='+$("input#prenomEmploye").val()+
						'&login='+$("input#loginEmploye").val()+'&statut='+$("#statutEmploye").val(),
				success : function(data){
					if(Boolean(data) == false){
						
					}else{
						if("ajouter" == mode){
							var numTr = $("#body_employes")[0].childElementCount + 1;
							var tr = '<tr id="tr_employe_'+numTr+'">';
							tr += '<td>'+data.nom.toUpperCase()+' '+data.prenom+'</td>';
							tr += '<td>'+data.login+'</td>';
							tr += '<td>'+data.statut+'</td>';
							tr += '<td class="center">0</td>';
							tr += '<td>	<button type="button" class="btn btn-success" data-tr="'+numTr+'"'			
								+'				data-id="'+data.id+'" data-toggle="modal"'
								+'				data-target="#detailEmploye" onclick="edit_employe(this)">'
								+'			<span class="glyphicon glyphicon-pencil"></span>'
								+'		</button>'
								+'		<button type="button" class="btn btn-danger" data-tr="'+numTr+'"'			
								+'				data-id="'+data.id+'" onclick="delete_employe(this)">'
								+'			<span class="glyphicon glyphicon-trash"></span>'
								+'		</button>'
								+'</td>';
							
							$("#body_employes").append(tr);
						}else if("modifier" == mode){
							var tr = '<td>'+data.nom.toUpperCase()+' '+data.prenom+'</td>';
								tr += '<td>'+data.login+'</td>';
								tr += '<td>'+data.statut+'</td>';
								tr += '<td class="center">0</td>';
								tr += '<td>	<button type="button" class="btn btn-success" data-tr="'+idTrModifie+'"'			
									+'				data-id="'+data.id+'" data-toggle="modal"'
									+'				data-target="#detailEmploye" onclick="edit_employe(this)">'
									+'			<span class="glyphicon glyphicon-pencil"></span>'
									+'		</button>'
									+'		<button type="button" class="btn btn-danger" data-tr="'+idTrModifie+'"'			
									+'				data-id="'+data.id+'" onclick="delete_employe(this)">'
									+'			<span class="glyphicon glyphicon-trash"></span>'
									+'		</button>'
									+'</td>';
							$("#tr_employe_"+idTrModifie).html(tr);
						}
						showDialogSucces("Employé enregistré","Les informations ont été enregistrées avec succès");
					}
				}
			});
		}
	};
	
	/**
	 * Fonction en charge de vérifier les données saisies
	 */
	verificationAjoutEmploye = function(){
		var message = '<div class="alert alert-danger" id="alert_erreur_enregistrer" role="alert">'
					  +'<strong>Erreur de saisie :</strong><ul>';
		var message_erreur = '';
		
		if($("input#nomEmploye").val() == ''){
			message_erreur += '<li class="li_erreur">Le nom de l\'employé est obligatoire</li>';
		}else if($("input#nomEmploye").val().length < 2){
			message_erreur += '<li class="li_erreur">Le nom de l\'employé n\'est pas valide</li>';
		}
		
		if($("input#prenomEmploye").val()== ''){
			message_erreur += '<li class="li_erreur">Le prénom de l\'employé est obligatoire</li>';
		} else if($("input#prenomEmploye").val().length < 2){
			message_erreur += '<li class="li_erreur">Le prénom de l\'employé n\'est pas valide</li>';
		}
		
		if(message_erreur != '')
			return message + message_erreur + '</ul></div>';
		
		return null;
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
	delete_employe  = function(button){
		var idTr = $(button)[0].dataset.tr;
		var idEmploye = $(button)[0].dataset.id;
		$.ajax({
			url : "employes",
			method : "POST",
			data : "action=supprimer&id="+idEmploye,
			success : function(data){
				if(Boolean(data) == true){
					$("tr#tr_employe_"+idTr).remove();
				}
			}
		});
	};

});
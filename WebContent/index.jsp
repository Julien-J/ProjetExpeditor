<% String menu = ""; %>
<%@include file="/fragments/haut.jspf" %>

<% if(session.getAttribute("user") == null ) { %>
<div class="panel panel-primary" id="div_form_connexion">
      <div class="panel-heading">Authentification</div>
      <div class="panel-body" id="panel_body_connexion">
      	<form role="form" action="<%=request.getContextPath()%>/connexion" method="POST">
			<div class="form-group">
				<input type="text" class="form-control" id="form_identifiant" name="id" 
						placeholder="Identifiant">
				<input type="password" class="form-control" id="form_motdepasse" name="pass"
						placeholder="Mot de passe">	
				<% if(request.getAttribute("message") != null) { %>
					<div class="alert alert-danger fade in">
					  <strong>Erreur !</strong> Identifiant ou mot de passe incorrect.
					</div>					
				<% } %>
				<button type="submit" class="btn btn-primary" id="btn_connexion">
					Connexion
				</button>
			</div>
		</form>
	</div>
</div>
<% } else{ 
	if("employe".equals(((Utilisateur)session.getAttribute("user")).getStatut())){
		request.getRequestDispatcher("/employe/commande").forward(request,response);
	}else{
		request.getRequestDispatcher("/manager/articles").forward(request,response);
	}
}%>



<%@include file="/fragments/bas.jspf" %>
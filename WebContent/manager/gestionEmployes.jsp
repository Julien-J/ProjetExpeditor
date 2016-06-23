<% 	
	String menu = "employes";
	DateFormat df_now = new SimpleDateFormat("dd/MM/yyyy");
	ArrayList<Utilisateur> employes = (ArrayList<Utilisateur>) request.getAttribute("employes");
	int index = 0;
%>
<%@include file="/fragments/haut.jspf"%>
<%@ page import="fr.eni_ecole.expeditor.bean.*, java.util.*, java.text.*"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/gestionEmployes.js"></script>
<div style="float: right;">
	<button type="button" class="btn btn-success" data-toggle="modal"
		data-target="#detailEmploye">
		<span class="glyphicon glyphicon-plus glyphicon"></span>
		Ajouter
	</button>
</div>
<table class="table table-striped">
	<thead>
		<tr>
			<th>Employ�</th>
			<th>Login</th>
			<th>Statut</th>
			<th class="center" >Commandes trait�es au <%=df_now.format(new Date())%></th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
		<%
			int i = 0;
			for (Utilisateur unEmploye : employes) {
		%>
		<tr>
			<td id="<%=i%>"><%=unEmploye.getNom().toUpperCase()+" "+unEmploye.getPrenom()%></td>
			<td id="<%=i%>"><%=unEmploye.getLogin()%></td>
			<td id="<%=i%>"><%=unEmploye.getStatut()%></td>
			<td class="center" id="<%=i%>"><%=unEmploye.getNbCmdesTraitee() %></td>
			<td id="<%=i%>">
				<button type="button" class="btn btn-success">
					<span class="glyphicon glyphicon-pencil"></span>
				</button>
				<button type="button" class="btn btn-danger"
					data-id=<%=unEmploye.getId()%> onclick="delete_employe(this)">
					<span class="glyphicon glyphicon-trash"></span>
				</button>
			</td>		
		</tr>
		<%
			}
		%>
	</tbody>
</table>

<div class="modal fade" id="detailEmploye" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Ajouter un employ�</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label>Identifiant</label> <input type="text" class="form-control" 
						id="idEmploye" disabled>
				</div>
				<div class="form-group">
					<label>Nom</label> <input type="text" class="form-control" onchange="createLogin()"
						id="nomEmploye">
				</div>
				<div class="form-group">
					<label>Pr�nom</label> <input type="text" class="form-control" onchange="createLogin()"
						id="prenomEmploye">
				</div>
				<div class="form-group">
					<label>Login</label><input type="text" class="form-control" disabled="disabled"
						id="loginEmploye">
				</div>
				<div class="form-group">
					<label>Mot de passe</label><input type="password" class="form-control"
						id="passEmploye">
				</div>
				<div class="form-group">
					<label>Statut</label>
					  <select class="form-control" id="statutEmploye">
					    <option value="employe" selected="selected">Employ�</option>
					    <option value="manager">Manager</option>
					  </select>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal"
					onclick="add_employe()">Enregistrer</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
			</div>
		</div>
	</div>
</div>
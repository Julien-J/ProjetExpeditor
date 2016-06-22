<% 	
	String menu = "employes"; 
	ArrayList<Utilisateur> employes = (ArrayList<Utilisateur>) request.getAttribute("employes");
	int index = 0;
%>
<%@include file="/fragments/haut.jspf"%>
<%@ page import="fr.eni_ecole.expeditor.bean.*, java.util.*, java.text.*"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/gestionEmployes.js"></script>
<div style="float: right;">
	<button type="button" class="btn btn-success" data-toggle="modal"
		data-target="#ajoutEmploye">
		<span class="glyphicon glyphicon-plus glyphicon"></span>
		Ajouter
	</button>
</div>
<table class="table table-striped">
	<thead>
		<tr>
			<th>Employé</th>
			<th>Login</th>
			<th>Statut</th>
			<th>Nb. Commandes traitées</th>
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
			<td id="<%=i%>"><%=unEmploye.getNbCmdesTraitee() %></td>
			<td id="<%=i%>">
				<button type="button" class="btn btn-success">
					<span class="glyphicon glyphicon-pencil"></span>
				</button>
				<button type="button" class="btn btn-danger"
					data-id=<%=unEmploye.getLogin()%> onclick="delete_employe(this)">
					<span class="glyphicon glyphicon-trash"></span>
				</button>
			</td>		
		</tr>
		<%
			}
		%>
	</tbody>
</table>
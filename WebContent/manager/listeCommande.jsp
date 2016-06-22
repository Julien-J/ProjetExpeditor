<%@include file="/fragments/haut.jspf" %>

<%@ page
	import="fr.eni_ecole.expeditor.bean.*, java.util.*, java.text.*"%>
<%
	ArrayList<Commande> listeCommande = (ArrayList<Commande>) request.getSession().getAttribute("listeCommandes");
	int index = 0;
%>

<select class="form-control">
  <option>Tous</option>
  <option>En attente</option>
  <option>En cours</option>
</select>

<table class="table table-striped">
    <thead>
      <tr>
        <th>Numéro</th>
        <th>Client</th>
        <th>Date</th>
        <th>Nombre d'articles</th>
        <th>État</th>
        <th>Actions</th>
      </tr>
    </thead>
    <tbody>
      <%
			int i = 0;
			for (Commande uneCommande : listeCommande) {
		%>
		<tr>
			<td id=<%=i%>><%=uneCommande.getClient().getNom()%></td>
			<td id=<%=i%>><%=uneCommande.getDate() %> </td>
			<%
				int qte = 0;
				for (LigneCommande uneLigne : uneCommande.getLesLignes()){
					qte = qte + uneLigne.getQuantite();
				}
			%>
			<td id=<%=i%>><%=qte%></td>
			<td id=<%=i%>><%=uneCommande.getEtat()%></td>
			
			<td><button type="button" class="btn btn-success">
					<span class="glyphicon glyphicon-pencil"></span>
				</button>
				<button type="button" class="btn btn-danger">
					<span class="glyphicon glyphicon-trash"></span>
				</button></td>
		</tr>
		<%
			i++;
			}
		%>
    </tbody>
</table>

<%@include file="/fragments/bas.jspf" %>
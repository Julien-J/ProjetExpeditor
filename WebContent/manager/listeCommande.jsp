<%
	String menu = "commandes";
%>

<%@include file="/fragments/haut.jspf" %>

<%@ page
	import="fr.eni_ecole.expeditor.bean.*, java.util.*, java.text.*"%>
<%
	ArrayList<Commande> listeCommande = (ArrayList<Commande>) request.getSession().getAttribute("listeCommandes");
	int index = 0;
%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/listeCommande.js"></script>

<table id="tabCommande" class="table table-striped">
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
			<td id id=<%=i%>><%=uneCommande.getNum() %></td>
			<%
				String str = "";
				if (uneCommande.getClient() != null){
					str = uneCommande.getClient().getNom();
				}
			%>
			<td id=<%=i%>><%=str %></td>
			<%
				Date date;
				String dateFr;
				SimpleDateFormat formatter;
	
				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				date = uneCommande.getDate();
				dateFr = formatter.format(date);
			%>
			<td id=<%=i%>><%=dateFr %> </td>
			<%
				int qte = 0;
				for (LigneCommande uneLigne : uneCommande.getLesLignes()){
					qte = qte + uneLigne.getQuantite();
				}
			%>
			<td id=<%=i%>><%=qte%></td>
			<td id=<%=i%>><%=uneCommande.getEtat()%></td>
			
			<td><button type="button" data-id=<%=uneCommande.getNum()%> onclick="view_commande(this)" class="btn btn-info btn-lg" data-toggle="modal" data-target="#detailCommande"><span class="glyphicon glyphicon-eye-open"></span></button></td>
		</tr>
		<%
			i++;
			}
		%>
    </tbody>
</table>

<!-- Modal -->
<div id="detailCommande" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <%
			Commande maCommande = (Commande) request.getSession().getAttribute("Commande");
        %>
        <%--<%
			Date date;
			String maDateFr;
			SimpleDateFormat formatter;

			formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			date = maCommande.getDate();
			maDateFr = formatter.format(date);
		--%>
        <h4 class="modal-title">Commande <%=maCommande.getNum() + " - " %></h4>
      </div>
      <div class="modal-body">
        <p>Some text in the modal.</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>

<script>
	function view_commande(id) {
		$.ajax({
			url : "manager/commandes",
			method : "GET",
			data : "action=view_commande&numero=" + $(id)[0].dataset.id
		});
	}
</script>
<%@include file="/fragments/bas.jspf" %>
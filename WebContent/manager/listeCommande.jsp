<%@include file="/fragments/haut.jspf" %>

<%@ page
	import="fr.eni_ecole.expeditor.bean.*, java.util.*, java.text.*"%>
<%
	ArrayList<Commande> listeCommande = (ArrayList<Commande>) request.getSession().getAttribute("listeCommandes");
	int index = 0;
%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/listeCommande.js"></script>

<table class="table table-striped">
    <thead>
      <tr>
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
	
				formatter = new SimpleDateFormat("dd/MM/yyyy");
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
			
			<td><button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#detailCommande"><span class="glyphicon glyphicon-eye-open"></span></button></td>
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
        <h4 class="modal-title">Modal Header</h4>
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

<%@include file="/fragments/bas.jspf" %>
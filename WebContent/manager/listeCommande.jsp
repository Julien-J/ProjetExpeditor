
<%
	String menu = "commandes";
%>

<%@include file="/fragments/haut.jspf"%>

<%@ page
	import="fr.eni_ecole.expeditor.bean.*, java.util.*, java.text.*"%>
<%
	ArrayList<Commande> listeCommande = (ArrayList<Commande>) request.getSession().getAttribute("listeCommandes");
	int index = 0;
%>

<table id="tabCommande" class="table table-striped sortable">
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
			<td id=<%=i%>><%=dateFr %></td>
			<%
				int qte = 0;
				for (LigneCommande uneLigne : uneCommande.getLesLignes()){
					qte = qte + uneLigne.getQuantite();
				}
			%>
			<td id=<%=i%>><%=qte%></td>
			<td id=<%=i%>><%=uneCommande.getEtat()%></td>

			<td><button type="button" data-id=<%=uneCommande.getNum()%>
					onclick="get_commande(this)" class="btn btn-info btn-lg"
					data-toggle="modal" data-target="#detailCommande">
					<span class="glyphicon glyphicon-eye-open"></span>
				</button></td>
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
				<h4 class="modal-title" id="titreCommande"></h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-6">
						<div class="panel panel-primary">
							<div class="panel-heading">Commande</div>
							<div class="panel-body">
								<p id="numCommande"></p>
								<p id="dateCommande"></p>
								<p id="nbArticle"></p>
								<p id="etatCommande"></p>
								<p id="empCommande"></p>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="panel panel-primary">
							<div class="panel-heading">Client</div>
							<div class="panel-body">
								<p id="numClient"></p>
								<p id="nomClient"></p>
								<p id="adrClient"></p>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-primary">
							<div class="panel-heading">Article(s)</div>
							<div class="panel-body">
								<table class="table table-striped sortable">
								    <thead>
								      <tr>
								        <th>Référence</th>
								        <th>Libellé</th>
								        <th>Quantité</th>
								        <th>Poids unitaire</th>
								      </tr>
								    </thead>
								    <tbody id="tabArticles">
								      <tr>
								       </tr>
								    </tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>

	<script>
	function get_commande(id) {
		$.ajax({
			type : "GET",
			url : "commandes",
			data : "action=get_commande&numero=" + $(id)[0].dataset.id,
			dataType : 'json',
			success : function(data) {
				$("#titreCommande").html("Commande " + data.commandeData.num + " - " + data.commandeData.date);
				$("#numCommande").html("Numéro : " + data.commandeData.num);
				$("#dateCommande").html("Date : " + data.commandeData.date);
				$("#nbArticle").html("Nombre d'article(s) : ");
				$("#etatCommande").html("État : " + data.commandeData.etat);
				$("#empCommande").html("Employé en charge : " + data.employeData.nom);
				$("#numClient").html("Numéro : " + data.clientData.num);
				$("#nomClient").html("Client : " + data.clientData.nom);
				$("#adrClient").html("Adresse : " + data.clientData.adresse);
				
				for (i = 0; i < data.commandeData.lesLignes.length; i++) {
					var articleLibelle = [];
					var articlePoids = [];
					var articleNum = data.commandeData.lesLignes[i].refArticle;
					function get_article(articleNum){
						$.ajax({
							type : "GET",
							url : "commandes",
							data : "action=get_article&idArticle=" + articleNum,
							dataType : 'json',
							success : function(data) {
								articleLibelle = data.libelle;
								articlePoids = data.poids;
							}
						});
					}	
					var tr ='<tr>';
					tr += '<td>' + data.commandeData.lesLignes[i].refArticle + '</td>';
					tr += '<td>' + articleLibelle + '</td>';
					tr += '<td>' + data.commandeData.lesLignes[i].quantite + '</td>';
					tr += '<td>' + articlePoids + '</td>';
					tr +='</tr>';
					$("#tabArticles").append(tr);
				}
			}
		});
	}
</script>
	<%@include file="/fragments/bas.jspf"%>
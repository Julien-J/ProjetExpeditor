<%@include file="/fragments/haut.jspf" %>
<%@ page import="fr.eni_ecole.expeditor.bean.*, java.util.*, java.util.regex.*, java.text.*"%>
<%
	Commande commande = (Commande) request.getSession().getAttribute("commandeATraiter");

	int qte = 0;		
	String adressePart1 = "";
	String adressePart2 = "";
	
	
	String adresse = commande.getClient().getAdresse();
	String[] strings = adresse.split(" - ");
	adressePart1 = strings[0];
	adressePart2 = strings[1];
		
	for (LigneCommande uneLigne : commande.getLesLignes()){
		qte = qte + uneLigne.getQuantite();
	}
%>
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class = "panel-title">
	     	Commande Client
	   	</h3>
	</div>
	<div class="panel-body">
		<table class="table table-striped">
		    <tbody>
		    	<tr>
		      		<td> Société <%=commande.getClient().getNom()%> </td>
		      		<td> Commande n° <%=commande.getNum()%> </td>
		      	</tr>
		      	<tr>
		      		<td> <%=adressePart1%> </td>
		      		<%
			      		Date date;
						String dateFr;
						SimpleDateFormat formatter;
			
						formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
						date = commande.getDate();
						dateFr = formatter.format(date);
		      		%>
		      		<td> Effectuée le <%=dateFr%></td>
		      	</tr>
		      	<tr>
		      		<td> <%=adressePart2%> </td>
		      		<td> Nombre d'articles commandées : <%=qte%></td>
		      	</tr>		      	
		    </tbody>
		</table>
	</div>
</div>

<%@include file="/fragments/bas.jspf" %>
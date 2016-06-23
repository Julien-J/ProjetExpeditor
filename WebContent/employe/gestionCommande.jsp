<%
	String menu = "";
%>
<%@include file="/fragments/haut.jspf" %>
<%@ page import="fr.eni_ecole.expeditor.bean.*, fr.eni_ecole.expeditor.dao.*, java.util.*, java.util.regex.*, java.text.*"%>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/gestionQuantite.js"></script>
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
			
						formatter = new SimpleDateFormat("dd/MM/yyyy à HH:mm");
						date = commande.getDate();
						dateFr = formatter.format(date);
		      		%>
		      		<td> Effectuée le <%=dateFr%></td>
		      	</tr>
		      	<tr>
		      		<td> <%=adressePart2%> </td>
		      		<td> Nombre d'articles commandés : <%=qte%></td>
		      	</tr>		      	
		    </tbody>
		</table>
	</div>
</div>
<div style="margin-left:auto; margin-right:auto; width:15%;">
	<button type="button" class="btn btn-default" onclick="btnEnCharge(this)">Prendre en Charge</button>
</div>
</br>
<table id="tbl" class="table table-striped">
	<thead>
    	<tr>
      		<th style="text-align:center">Référence</th>
	        <th style="text-align:center">Libellé</th>
	        <th style="text-align:center">Quantité commandée</th>
	        <th style="text-align:center">Poids unitaire</th>
	        <th style="text-align:center">Quantité fournie</th>
    	</tr>
    </thead>
    <tbody>
		<%
			int i = 0;
			for (LigneCommande ligne : commande.getLesLignes()) 
			{
				Article articleConcerne = DAOArticle.getArticle(ligne.getRefArticle());
		%>
			<tr>
				<td style="text-align:center"><%=ligne.getRefArticle()%></td>
				<td style="text-align:center"><%=articleConcerne.getLibelle()%></td>
				<td style="text-align:center"><%=ligne.getQuantite()%> </td>
				<td style="text-align:center"><%=articleConcerne.getPoids()%> g</td>
				<td style="text-align:center"> 
					<div class="input-group number-spinner" style="margin-left:auto; margin-right:auto; width:50%;">
						<input type="text" class="form-control input-sm text-center" value="0" id="qteFournie" data-current="0" data-poids="<%=articleConcerne.getPoids()%>" onchange="changeQte(this)"> 
					</div>											
				</td>
			</tr>
		<%
				i++;
			}
		%>  		      	
    </tbody>
</table>
<div style="float: right;">
	<h4><span class="label label-pill label-info"> Poids total* de la commande : <label id="poidsTotal">300</label> g</span></h4>
</div>
</br></br></br>
<div style="margin-left:auto; margin-right:auto; width:15%;">
	<button type="button" class="btn btn-default" onclick="btnValider()">Valider la commande</button>
</div>
<input type="hidden" id="numCommande" value="<%=commande.getNum()%>">
<script>

	$(document).ready(function() 
	{
		$("#tbl").find("input,button,textarea,select").attr("disabled", "disabled");							         
	});
	
	function changeQte(item)
	{
		var the_label = document.getElementById('poidsTotal');
		var currval = parseInt(the_label.innerHTML);
		var poidsArticle = parseInt($(item)[0].dataset.poids);
		var poidsTotal = 0;
		
		var oldValue = $(item)[0].dataset.current;
		var newValue = item.value;		
		
		if (newValue != oldValue) 
		{
			poidsTotal = currval + ((newValue-oldValue) * poidsArticle);
			the_label.innerHTML = poidsTotal;
			
			$(item)[0].dataset.current = newValue;			  
		}
	}
	
	function btnEnCharge(item)
	{		
		item.disabled=true;
		$("#tbl").find("input,button,textarea,select").removeAttr('disabled');
	}
	
	function btnValider()
	{
		var now = new Date();
		
		$.ajax({
			url : "employe/commande",
			method : "POST",
			data : "action=valid_cmd&cmd=" + $("#numCommande").val(),
			success : function() {
				$.toaster({
					priority : 'success',
					title : 'Notice',
					message : 'Commande traitée !'
				});
			}
		});
	}
</script>

<%@include file="/fragments/bas.jspf" %>
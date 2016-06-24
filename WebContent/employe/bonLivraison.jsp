<%@page import="fr.eni_ecole.expeditor.dao.DAOArticle"%>
<%@page import="fr.eni_ecole.expeditor.bean.Article"%>
<%@page import="fr.eni_ecole.expeditor.bean.LigneCommande"%>
<%@page import="fr.eni_ecole.expeditor.bean.Commande"%>
<%
	Commande commande = (Commande) request.getSession().getAttribute("commandeATraiter");
	String menu = "";
	int qte = 0;
	int qteTotal = 300;
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
<%@include file="/fragments/haut.jspf" %>
<style>
	@media screen  {
		div#date_bon_livraison {
		    float: left;
		    margin-top: 5%;
		}
		
		button#btn_print{
			margin-right : 15px;
		}
		
		span.span_dotted{
		    display: inline-block;
		    width: 150px;
		    border-bottom: 1px dotted;
		    margin-left: 5px;
		    margin-right: 5px;
	    }
	    div#signature {
		    float: right;
		    border: 1px solid #000;
		    width: 200px;
		    height: 100px;
		}
		div#articles_bon_livraison {
		    float: left;
		    width: 100%;
		    margin-bottom: 7%;
		}
		div#bas_bon_livraison {
		    float: left;
		    width: 100%;
		}
		div#div_button_print{
			width: 35%;
		    margin: auto;
	    }
	    div#div_bon_livraison {
		    float: left;
		    width: 100%;
		    margin-bottom: 5%;
		}
		div#div_signature {
		    float: right;
		}
		div#entete_print{
			display : none;
		}
	}
	
	@media print {
	 	* {
	 		font-size : 14px !important;
	 	}
	 	div#milieu {
	 		margin : 0px;
	 	}
	 	div#div_infos,
	 	a#lien_deconnexion,
	 	div#div_button_print,
	 	div#entete{
	 		display : none;
	 	}
	    div#div_bon_livraison {
		    float: left;
		    width: 100%;
		    margin-bottom: 5%;
		}
		div#div_signature {
		    float: right;
		}		
		span.span_dotted{
		    display: inline-block;
		    width: 150px;
		    border-bottom: 1px dotted;
		    margin-left: 5px;
		    margin-right: 5px;
	    }
   	    div#signature {
		    float: right;
		    border: 1px solid #000;
		    width: 200px;
		    height: 100px;
		}
		div#articles_bon_livraison {
		    float: left;
		    width: 100%;
		    margin-bottom: 7%;
		}
		table#tbl {
			font-size : 10px !important;
		}
		div#entete_print{
			float : left;
			width : 100%;
			margin-bottom : 4%;
			border-bottom : 1px solid #337ab7;
			padding-bottom : 2px;
		}
		
		div#entete_print_logo{
			float : left;
		    width: 20%;
		    height: 100%;
		}
		
		div#entete_print_expeditor{
		    float: right;
		    width: 50%;
		    margin-top : 6%;
		    color: #337ab7 !important;
		    font-size: 30px !important;
    	}
	}
	
</style>

<div id="entete_print">
	<div id="entete_print_logo">
		<img src="<%=request.getContextPath()%>/theme/image/expeditor.jpg" />
	</div>
	<div id="entete_print_expeditor">EXPEDITOR</div>
</div>
<div id="div_bon_livraison">
	<div id="entete_bon_livraison">
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
	</div>
	<div id="articles_bon_livraison">
		<table id="tbl" class="table table-striped">
			<thead>
		    	<tr>
		      		<th style="text-align:center">Référence</th>
			        <th style="text-align:center">Libellé</th>
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
						<td style="text-align:center"><%=articleConcerne.getPoids()%> g</td>
						<td style="text-align:center"><%=ligne.getQuantite()%> </td>
					</tr>
				<%
						i++;
						qteTotal += articleConcerne.getPoids() * ligne.getQuantite();
					}
				%>  		      	
		    </tbody>
		</table>
		<div style="float: right;">
			<h4><span class="label label-pill label-info"> Poids total de la commande : <label id="poidsTotal"><%=qteTotal %></label> g</span></h4>
		</div>
	</div>
	<div id="bas_bon_livraison">
		<div id="date_bon_livraison">
			A <span class="span_dotted"></span>, le <%= df.format(new Date()) %>
		</div>
		<div id="div_signature">		
			<div>Signature :</div> 
			<div id="signature"></div>
		</div>
	</div>
</div>

<div id="div_button_print">
	<button type="button" class="btn btn-danger-outline"  id="btn_print" onclick="print()">
		<span class="glyphicon glyphicon-print"></span> Imprimer
	</button>
	<a href="<%=request.getContextPath()%>">
		<button type="button" class="btn btn-danger-outline" id="btn_next_commande" onclick="nextCommande()">
			Commande suivante <span class="glyphicon glyphicon-menu-right"></span> 
		</button>
	</a>
</div>

<%@include file="/fragments/bas.jspf" %>
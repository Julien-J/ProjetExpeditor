
<%
	String menu = "articles";
%>
<%@include file="/fragments/haut.jspf"%>
<%@ page
	import="fr.eni_ecole.expeditor.bean.*, java.util.*, java.text.*"%>
<%
	ArrayList<Article> listeArticle = (ArrayList<Article>) request.getSession().getAttribute("listeArticles");
	int index = 0;
%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/gestionArticle.js"></script>
<div style="float: right;">
	<button type="button" class="btn btn-success" data-toggle="modal"
		data-id="add_article" data-target="#detailArticle"
		onclick="clear_field();set_mode(this)">
		<span class=" glyphicon glyphicon-plus"></span> Ajouter
	</button>
</div>
<table class="table table-striped">
	<thead>
		<tr>
			<th>Libellé</th>
			<th>Poids</th>
			<th>Description</th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
		<%
			int i = 0;
			for (Article unArticle : listeArticle) {
		%>
		<tr>
			<td id=<%=i%>><%=unArticle.getLibelle()%></td>
			<td id=<%=i%>><%=unArticle.getPoids()%> g</td>
			<td id=<%=i%>><%=unArticle.getDescription()%></td>
			<td><button type="button" class="btn btn-success"
					data-toggle="modal" data-target="#detailArticle"
					data-id=<%=unArticle.getRef()%> onclick="get_article(this)">
					<span class="glyphicon glyphicon-pencil"></span>
				</button>
				<button type="button" class="btn btn-danger"
					data-id=<%=unArticle.getRef()%> onclick="delete_article(this)">
					<span class="glyphicon glyphicon-trash"></span>
				</button></td>
		</tr>
		<%
			i++;
			}
		%>
	</tbody>
</table>
<div class="modal fade" id="detailArticle" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Ajouter un article</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label>Référence</label> <input type="text" class="form-control"
						id="referenceArticle" disabled>
				</div>
				<div class="form-group">
					<label>Libellé</label> <input type="text" class="form-control"
						id="libelleArticle">
				</div>
				<div class="form-group">
					<label>Poids (en g) : </label>
					<div class="input-group number-spinner col-xs-3">
						<span class="input-group-btn">
							<button class="btn btn-default input-sm" data-dir="dwn">
								<span class="glyphicon glyphicon-minus"></span>
							</button>
						</span> <input type="text" class="form-control input-sm text-center"
							value="1" id="poidsArticle"> <span
							class="input-group-btn">
							<button class="btn btn-default input-sm" data-dir="up">
								<span class="glyphicon glyphicon-plus"></span>
							</button>
						</span>
					</div>
				</div>
				<div class="form-group">
					<label>Description</label>
					<textarea class="form-control" id="descriptionArticle"></textarea>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal"
					onclick="onArticle()">Enregistrer</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
			</div>
		</div>
	</div>
</div>
<script>
	var mode = null;

	function get_article(id) {
		$.ajax({
			type : "GET",
			url : "manager/articles",
			data : "action=get_article&reference=" + $(id)[0].dataset.id,
			dataType : 'json',
			success : function(data) {
				$("#referenceArticle").val(data.ref);
				$("#libelleArticle").val(data.libelle);
				$("#descriptionArticle").val(data.description);
				$("#poidsArticle").val(data.poids);
			}
		});

	}

	function onArticle() {
		if (mode == "add_article") {
			$.ajax({
				url : "manager/articles",
				method : "POST",
				data : "action=add_article&libelle="
						+ $("#libelleArticle").val() + "&description="
						+ $("#descriptionArticle").val() + "&poids="
						+ $("#poidsArticle").val(),
				success : function() {
				
				}
			});
		} else {
			$.ajax({
				url : "manager/articles",
				method : "POST",
				data : "action=set_article&libelle="
						+ $("#libelleArticle").val() + "&description="
						+ $("#descriptionArticle").val() + "&poids="
						+ $("#poidsArticle").val()+ "&reference="
						+ $("#referenceArticle").val(),
				success : function() {
				
				}
			});
		}
	}

	function set_mode(id) {
		if ($(id)[0].dataset.id == "add_article") {
			mode = "add_article";
		} else {
			mode = "set_article";
		}
	}

	function delete_article(id) {
		$.ajax({
			url : "manager/articles",
			method : "POST",
			data : "action=delete_article&reference=" + $(id)[0].dataset.id
		});
	}

	function add_article() {
		$.ajax({
			url : "manager/articles",
			method : "POST",
			data : "action=add_article&libelle=" + $("#libelleArticle").val()
					+ "&description=" + $("#descriptionArticle").val()
					+ "&poids=" + $("#poidsArticle").val(),
			success : function() {
				$.toaster({
					priority : 'success',
					title : 'Notice',
					message : 'Article enregistré !'
				});
			}
		});
	}

	function clear_field() {
		$("#referenceArticle").val(" ");
		$("#libelleArticle").val(" ");
		$("#descriptionArticle").val(" ");
		$("#poidsArticle").val("1");
	}
</script>
<%@include file="/fragments/bas.jspf"%>


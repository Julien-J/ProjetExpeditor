<%@include file="/fragments/haut.jspf"%>
<script type="text/javascript" src="js/gestionArticle.js"></script>
<div style="float: right;">
	<button type="button" class="btn btn-primary" data-toggle="modal"
		data-target="#ajoutArticle">Ajouter</button>
</div>
<table class="table table-striped">
	<thead>
		<tr>
			<th>Référence</th>
			<th>Libellé</th>
			<th>Poids</th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>0</td>
			<td>Test</td>
			<td>12</td>
			<td>test</td>
		</tr>
		<tr>
			<td>1</td>
			<td>Disque</td>
			<td>300</td>
			<td>un beau disque</td>
		</tr>
	</tbody>
</table>
<div class="modal fade" id="ajoutArticle" role="dialog">
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
							value="1"> <span class="input-group-btn">
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
				<button type="button" class="btn btn-primary" data-dismiss="modal">Enregistrer</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
			</div>
		</div>
	</div>
</div>
<%@include file="/fragments/bas.jspf"%>


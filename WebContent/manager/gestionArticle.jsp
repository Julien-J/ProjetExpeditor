<%@include file="/fragments/haut.jspf" %>
<div>
	<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#ajoutArticle">Ajouter</button>
</div>   
<!-- Modal -->
  <div class="modal fade" id="ajoutArticle" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Ajouter un article</h4>
        </div>
        <div class="modal-body">
           <div class="form-group">
              <label>Référence</label>
              <input type="text" class="form-control" id="">
           </div>
           <div class="form-group">
              <label>Libellé</label>
              <input type="text" class="form-control" id="">
           </div>
        </div>
        <div class="form-group">
        <label>Libellé</label>
           <textarea class="form-control" rows="5" id=""></textarea>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Enregistrer</button>
          <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
        </div>
      </div>
    </div>
  </div>
<%@include file="/fragments/bas.jspf" %>


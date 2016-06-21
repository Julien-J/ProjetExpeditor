<%@include file="/fragments/haut.jspf" %>

<div id="div_erreur">
	<%=((Exception)request.getAttribute("erreur")).getMessage()%>
</div>
<div>
	<a href="<%=request.getContextPath()%>/">Retour à la page d'accueil</a>
</div>


<%@include file="/fragments/bas.jspf" %>
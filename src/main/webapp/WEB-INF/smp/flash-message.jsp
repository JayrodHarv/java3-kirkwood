<c:choose>
  <c:when test="${not empty flashMessageSuccess}">
    <div class="alert alert-success alert-dismissible my-2" role="alert">
        ${flashMessageSuccess}
      <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <c:remove var="flashMessageSuccess" scope="session"></c:remove>
  </c:when>
  <c:when test="${not empty flashMessageWarning}">
    <div class="alert alert-warning alert-dismissible my-2" role="alert">
        ${flashMessageWarning}
      <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <c:remove var="flashMessageWarning" scope="session"></c:remove>
  </c:when>
  <c:when test="${not empty flashMessageDanger}">
    <div class="alert alert-danger alert-dismissible my-2" role="alert">
        ${flashMessageDanger}
      <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <c:remove var="flashMessageDanger" scope="session"></c:remove>
  </c:when>
</c:choose>
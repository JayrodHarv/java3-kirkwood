<c:choose>
  <c:when test="${not empty flashMessageSuccess}">
    <div class='alert alert-success alert-dismissible fade show position-absolute end-0 m-2 z-3' style="top: 75px" role='alert'>
      <strong>${flashMessageSuccess}</strong>
      <button type='button' class='btn-close' data-bs-dismiss='alert' aria-label='Close'></button>
    </div>
    <c:remove var="flashMessageSuccess" scope="session"></c:remove>
  </c:when>
  <c:when test="${not empty flashMessageWarning}">
    <div class='alert alert-warning alert-dismissible fade show position-absolute end-0 m-2 z-3' style="top: 75px" role='alert'>
      <strong>${flashMessageWarning}</strong>
      <button type='button' class='btn-close' data-bs-dismiss='alert' aria-label='Close'></button>
    </div>
    <c:remove var="flashMessageWarning" scope="session"></c:remove>
  </c:when>
  <c:when test="${not empty flashMessageDanger}">
    <div class='alert alert-danger alert-dismissible fade show position-absolute end-0 m-2 z-3' style="top: 75px" role='alert'>
      <strong>${flashMessageDanger}</strong>
      <button type='button' class='btn-close' data-bs-dismiss='alert' aria-label='Close'></button>
    </div>
    <c:remove var="flashMessageDanger" scope="session"></c:remove>
  </c:when>
</c:choose>
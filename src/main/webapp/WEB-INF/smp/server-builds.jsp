<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container-fluid p-3" style="margin-bottom: 40px">

  <!-- Flash Message -->
  <c:choose>
    <c:when test="${not empty flashMessageSuccess}">
      <div class="alert alert-success my-2">
          ${flashMessageSuccess}
      </div>
      <c:remove var="flashMessageSuccess" scope="session"></c:remove>
    </c:when>
    <c:when test="${not empty flashMessageWarning}">
      <div class="alert alert-warning my-2">
          ${flashMessageWarning}
      </div>
      <c:remove var="flashMessageWarning" scope="session"></c:remove>
    </c:when>
  </c:choose>

  <div class="d-flex justify-content-between mb-4">
    <h2>${pageTitle}</h2>
    <div class="text-end">
      <a href="${appURL}/add-build" class="btn btn-primary">Add New Build</a>
    </div>
  </div>
  <div class="container-fluid">
    <c:if test="${not empty results.getBuildListError}">
      <p class="alert alert-danger my-2">${results.getBuildListError}</p>
    </c:if>
    <div class="row row-cols-1 row-cols-xxl-2 g-4">
      <c:if test="${builds.size() > 0}">
        <c:forEach items="${builds}" var="b">
          <div class="col">
            <div class="card">
              <img class="card-img-top" src="data:image/png;base64,${b.getBase64Image()}"/>
              <div class="card-body">
                <div class="d-flex justify-content-between">
                  <h5>${b.getBuildID()}</h5>
                  <p class="text-end m-0">${b.getDateBuilt()}</p>
                </div>
                <p class="card-text">${b.getDescription()}</p>
              </div>
            </div>
          </div>
        </c:forEach>
      </c:if>
    </div>
  </div>
</main>
<%@ include file="/WEB-INF/smp/bottom.jsp"%>

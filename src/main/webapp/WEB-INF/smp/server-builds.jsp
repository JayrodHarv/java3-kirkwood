<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container-fluid">
  <div class="d-flex justify-content-md-between">
    <h2>${pageTitle}</h2>
    <div class="text-end">
      <a href="${appURL}/add-build" class="btn btn-primary">Add New Build</a>
    </div>
  </div>
  <div class="container-fluid">
    <c:if test="${not empty results.getBuildListError}">
      <p class="alert alert-danger my-2">${results.getBuildListError}</p>
    </c:if>
    <div class="row-lg-10">
      <c:if test="${builds.size() > 0}">
        <c:forEach items="${builds}" var="b">
          <div class="card mb-4">
  <%--          <img class="card-img-top" src="data:image/png;base64,${b.BuildingID}">--%>
            <div class="card-body">
              <h5 class="card-title">${b.getName()}</h5>
              <p class="card-text">${b.getDescription()}</p>
            </div>
          </div>
        </c:forEach>
      </c:if>
    </div>
  </div>
</main>
<%@ include file="/WEB-INF/smp/bottom.jsp"%>
